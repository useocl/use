/*
 * This is source code of the Snapshot Generator, an extension for USE
 * to generate (valid) system states of UML models.
 * Copyright (C) 2001 Joern Bohling, University of Bremen
 *
 * About USE:
 *   USE - UML based specification environment
 *   Copyright (C) 1999,2000,2001 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/**
 * March 22th 2001 
 * @author  Joern Bohling
 */

package org.tzi.use.gen.assl.dynamics;

import org.tzi.use.gen.assl.statics.GInstrTry_AssocClass_LinkendSeqs;
import org.tzi.use.gen.assl.statics.GInstruction;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.output.UserOutput;
import org.tzi.use.output.VoidUserOutput;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.*;
import org.tzi.use.uml.sys.soil.*;
import org.tzi.use.util.Pair;
import org.tzi.use.util.collections.CollectionUtil;
import org.tzi.use.util.collections.CollectionUtil.UniqueList;
import org.tzi.use.util.collections.MinCombinationsIterator;

import java.util.*;


public class GEvalInstrTry_AssocClass_LinkendSeqs extends GEvalInstrTry
    implements IGCaller {
    protected GInstrTry_AssocClass_LinkendSeqs fInstr;
	static List<List<Value>> emptyQualifiers = Collections.emptyList();
    private IGCaller fCaller;
    private ListIterator<GValueInstruction> fIterator;
    protected List<List<MObject>> fObjectLists;
    private GInstruction fLastEvaluatedInstruction;
    
    public GEvalInstrTry_AssocClass_LinkendSeqs(GInstrTry_AssocClass_LinkendSeqs instr, boolean first ) {
    	super(first);
        fInstr = instr;
    }

    public void eval(GConfiguration conf,
                     IGCaller caller,
                     IGCollector collector) throws GEvaluationException {
		if (collector.doDetailPrinting()) {
			collector.getUserOutput().printlnTrace("evaluating `" + fInstr + "'");
		}

        fCaller = caller;
        fIterator = fInstr.linkendSequences().listIterator();
        fObjectLists = new ArrayList<List<MObject>>();
    
        // fIterator has a next element, because an association has at least
        // two link ends.
        fLastEvaluatedInstruction = fIterator.next();
        fLastEvaluatedInstruction.createEvalInstr().eval(conf,this,collector);
        fIterator.previous();
    }

    public void feedback( GConfiguration conf,
                          Value value,
                          IGCollector collector ) throws GEvaluationException {
        if (value.isUndefined()) {
            collector.invalid( buildCantExecuteMessage(fInstr,
                                                       (GValueInstruction) fLastEvaluatedInstruction));
            return;
        }
    
        List<MObject> objects = new ArrayList<MObject>();

        for (Value elem : (CollectionValue)value) {
            if (elem.isUndefined()) {
                collector.invalid( "Can't execute `" + fInstr +
                                   "', because the result of `" + 
                                   fLastEvaluatedInstruction +
                                   "' contains an undefined value." );
                return;
            }
            objects.add( ((ObjectValue) elem).value() );
        }
        fObjectLists.add( objects );
    
        if (fIterator.hasNext()) {
            fLastEvaluatedInstruction = fIterator.next();
            fLastEvaluatedInstruction.createEvalInstr().eval(conf,this,collector);
            fIterator.previous();
        }
        else {
            if (conf.getArguments().checkStructure() && conf.getArguments().useMinCombinations() &&
            	fInstr.getAssociationClass().associationEnds().size() == 2 &&
            	( !fInstr.getAssociationClass().associationEnds().get(0).isCollection() || 
            	  !fInstr.getAssociationClass().associationEnds().get(1).isCollection()	)	)
            	tryLinksBinaryOne(conf, collector);
            else
            	// every parameter is evaluated, so fObjectLists is complete now
            	tryLinks(conf, collector);
        }
    }
    
    
    protected void tryLinks(
    		GConfiguration conf, 
    		IGCollector collector) throws GEvaluationException {
         
        // 62 Links are 2^62 combinations, which can be represented by a long
    	// (bit i == 1) -> link i exists, (bit i == 0) -> link i does not exist
        // By the way, the search of a valid state within 2^62 combinations 
    	// would not terminate in years.
    	final int MAX_LINKS = 62;
    	
        // Just get combinations of objects and check its size.
        List<List<MObject>> combinations = 
        	CollectionUtil.<MObject>combinations(fObjectLists);
        
        int numLinks = combinations.size();
        
        if (numLinks > MAX_LINKS) {
            collector.invalid(
            		"Can't execute `" + 
            		fInstr + 
            		"', because there are more than 2^" + 
            		MAX_LINKS + 
            		" combinations.");
            
            return;
        }
        
        this.initProgress(numLinks);
        MSystemState state = conf.systemState();
        MSystem system = state.system();
        UserOutput output = collector.getUserOutput();
        
        // for each link we need a statement to insert and delete
        List<MStatement> insertStatements = 
        	new ArrayList<MStatement>(combinations.size());
        List<MStatement> deleteStatements =
        	new ArrayList<MStatement>(combinations.size());
        
        MAssociationClass association = fInstr.getAssociationClass();
          
        // construct the insert and delete statements. during the construction
        // the current configuration (i.e. which links already exist in the
        // current state) is determined and returned
        long initialConfiguration = 
        	constructCreateAndDeleteStatements(
        			combinations, 
        			numLinks, 
        			association, 
        			state, 
        			insertStatements, 
        			deleteStatements);
        
        // in the original version of this algorithm, all links get removed so
        // we do that as well to ensure previous test cases behave the same
        try {
        	system.execute(
					output,
        			constructLinkChangeStatement(
        					initialConfiguration, 
        					0, 
        					insertStatements, 
        					deleteStatements), 
        			true, 
        			false,
        			false);
        	system.getUniqueNameGenerator().popState();
        } catch (MSystemException e) {
        	throw new GEvaluationException(e);
        }
                
        // this is the "all links exist" configuration + 1
        long tooLarge = (1L << insertStatements.size());
        
        // we start with the 0000... (all links off) combination
        long oldConfiguration = 0;
        // configuration after state change
        // in the first iteration old- and newConfiguration are equal, so we 
        // can check the initial state
        long newConfiguration = oldConfiguration;
        
        long numEvaluated = 0;
        long numCut = 0;
        
        boolean continueEvaluation = true;
        StatementEvaluationResult res;
        
        do {
        	// construct the statement that transforms the state from old to
        	// new configuration
        	MStatement statement = constructLinkChangeStatement(
        			oldConfiguration, 
        			newConfiguration, 
        			insertStatements, 
        			deleteStatements);
        	
        	if (collector.doBasicPrinting())
                    output.println(statement.getShellCommand());

        	try {	
        		res = system.execute(output, statement, true, false, false);
			} catch (MSystemException e) {
				throw new GEvaluationException(e);
			}
			
        	if (conf.getArguments().useTryCuts() && conf.getArguments().checkStructure()) {
        		continueEvaluation = system.state().checkStructure(fInstr.getAssociationClass(), VoidUserOutput.getInstance(), false);
        		if (!continueEvaluation) {
        			++numCut;
        			collector.addCut();
        		}
        	}
        	
        	if (continueEvaluation) {
        		++numEvaluated;
        		
        		SetValue result = createResultSet(association, res);
				fCaller.feedback(conf, result, collector);
				
	            if (collector.expectSubsequentReporting()) {
	            	MSequenceStatement changeStatement = 
	            		constructLinkChangeStatement(
	            				// 0, <- in the original the no-links configuration
	            				//       was used, but i'm pretty sure that's wrong
	            				initialConfiguration, // transform initial state
	            				newConfiguration,     // to valid state
	            				insertStatements, 
	            				deleteStatements);
	            	
	            	for (MStatement s : changeStatement.getStatements()) {
	            		if (!s.isEmptyStatement()) {
	            			collector.subsequentlyPrependStatement(s);
	            		}
	            	}
	            }
        	}
        	
            // configurations for next iteration
            oldConfiguration = newConfiguration;
        	++newConfiguration;
        	
        	this.outPutProgress(newConfiguration);
        	
        	// Remove unique name state, because no undo statements are executed
        	system.getUniqueNameGenerator().popState();
        	
          // stop once all configurations have been built or stopping is allowed
        } while ((newConfiguration < tooLarge) && !collector.canStop());
        
        this.endProgress();
        
        if (collector.doBasicPrinting()) {
	        output.print("Evaluated ");
	        output.print(String.valueOf(numEvaluated));
	        output.print(" sub tress (cut ");
	        output.print(String.valueOf(numCut));
	        output.println(")");
        }
        
        // transform state to the initial state if we aren't in it anyways
        if (oldConfiguration != initialConfiguration) {
        	MStatement statement = 
        		constructLinkChangeStatement(
        				oldConfiguration, 
        				initialConfiguration, 
        				insertStatements, 
        				deleteStatements);

                if (collector.doBasicPrinting())
                    output.println(statement.getShellCommand());
                
        	try {
        		system.execute(output, statement, true, false, false);
        		system.getUniqueNameGenerator().popState();
			} catch (MSystemException e) {
				throw new GEvaluationException(e);
			}
        }
    }

	protected SetValue createResultSet(MAssociationClass association,
			StatementEvaluationResult res) {

		Value[] newObjects = new Value[res.getStateDifference().getNewObjects().size()];
		int index = 0;

		for (MObject o : res.getStateDifference().getNewObjects()) {
			newObjects[index] = new ObjectValue(association, o);
			index++;
		}

        return new SetValue(association, newObjects);
	}
    
	private long constructCreateAndDeleteStatements(
    		List<List<MObject>> combinations,
    		int numLinks,
    		MAssociationClass associationClass,
    		MSystemState state,
    		List<MStatement> insertStatements,
    		List<MStatement> deleteStatements) throws GEvaluationException {
    	
    	// initial configuration
        long initConfiguration = 0;
        
        // cache for MObject -> Expression transformations
        Map<MObject, MRValue> participantCache = new HashMap<MObject, MRValue>();
        
        // for each possible link, construct insert and delete statements
        for (int i = 0; i < numLinks; ++i) {
        	List<MObject> objects = combinations.get(i);
        	
        	// construct participants
        	List<MRValue> participants = 
        		new ArrayList<MRValue>(objects.size());
        	
        	for (MObject object : objects) {
        		MRValue participant = participantCache.get(object);
        		if (participant == null) {
        			participant = new MRValueExpression(object);
        			participantCache.put(object, participant);
        		}
        		
        		participants.add(participant);
        	}
        	
        	//FIXME: Support qualified associations in generator!
        	insertStatements.add(
        			new MNewLinkObjectStatement(associationClass, participants, Collections.<List<MRValue>>emptyList(), (String)null));
        	deleteStatements.add(
        			new MLinkDeletionStatement(associationClass, participants, Collections.<List<MRValue>>emptyList()));
        	
        	try {
        		// FIXME: Support qualifiers in generator
				if (state.hasLink(associationClass, objects, emptyQualifiers)) {
					// "turn on" bit i
					initConfiguration |= (1L << i);
				}
			} catch (MSystemException e) {
				throw new GEvaluationException(e);
			}
        }
    
    	return initConfiguration;
    }
    
	protected void tryLinksBinaryOne(
    		GConfiguration conf, 
    		IGCollector collector) throws GEvaluationException {
        
        // Just get combinations of objects and check its size.
        List<Pair<MObject>> links;
        MinCombinationsIterator<MObject> linkSetIter;
        
        UniqueList unique;
        if (!fInstr.getAssociationClass().associationEnds().get(0).isCollection()) {
        	unique = UniqueList.FIRST_IS_UNIQUE;
        } else {
        	unique = UniqueList.SECOND_IS_UNIQUE;
        }
        
        if (conf.getArguments().useRandomTry()) {
        	Collections.shuffle(fObjectLists.get(0), conf.random());
        	Collections.shuffle(fObjectLists.get(1), conf.random());
        }
        
		linkSetIter = new MinCombinationsIterator<MObject>(fObjectLists.get(0), fObjectLists.get(1), unique);
        MSystemState state = conf.systemState();
        MSystem system = state.system();
            	
        //TODO: in the original version of this algorithm, all links get removed so
        //      we do that as well to ensure previous test cases behave the same

        MSequenceStatement statements;
        StatementEvaluationResult res;
        
        double ignoredStates = Math.pow(2, (fObjectLists.get(0).size() * fObjectLists.get(1).size()));
        
        if (unique == UniqueList.SECOND_IS_UNIQUE) {
        	ignoredStates -= Math.pow(fObjectLists.get(0).size() + 1, fObjectLists.get(1).size());
        } else {
        	ignoredStates -= Math.pow(fObjectLists.get(1).size() + 1, fObjectLists.get(0).size());
        }
        
        collector.addIgnoredStates((long)ignoredStates);
        UserOutput output = collector.getUserOutput();
        
        do {
        	statements = new MSequenceStatement();
        	links = linkSetIter.next();
        	
        	for (Pair<MObject> elem : links) {
        		statements.appendStatement(new MLinkInsertionStatement(fInstr.getAssociationClass(), new MObject[]{elem.first, elem.second}, Collections.<List<Value>>emptyList()));
        	}
        	
        	if (collector.doBasicPrinting())
                output.println(statements.getShellCommand());
        	
        	try {	
        		res = system.execute(output, statements, true, false, false);
			} catch (MSystemException e) {
				throw new GEvaluationException(e);
			}

        	SetValue result = createResultSet(fInstr.getAssociationClass(), res);
			fCaller.feedback(conf, result, collector);
			
            if (collector.expectSubsequentReporting()) {
            	for (MStatement s : statements.getStatements()) {
            		if (!s.isEmptyStatement()) {
            			collector.subsequentlyPrependStatement(s);
            		}
            	}
            }
            
        	try {	
        		system.execute(output, res.getInverseStatement(), false, false, false);
			} catch (MSystemException e) {
				throw new GEvaluationException(e);
			}
        	
        	system.getUniqueNameGenerator().popState();
        	system.getUniqueNameGenerator().popState();
          // stop once all configurations have been built or stopping is allowed
        } while (linkSetIter.hasNext() && !collector.canStop());
    }
	
    private MSequenceStatement constructLinkChangeStatement(
    		long oldCombination, 
    		long newCombination,
    		List<MStatement> createStatements,
    		List<MStatement> deleteStatements) {
    	
    	
    	
    	// becomes a sequence of insert/delete statements which transforms 
    	// the state from the old configuration to the new configuration
    	MSequenceStatement result = new MSequenceStatement();
    	
    	if (oldCombination == newCombination) {
    		return result;
    	}
    	
    	// bit i is set if link i must be changed,
    	// i.e. inserted or deleted depending on whether it currently exists
    	long difference = (oldCombination ^ newCombination);
    	// start at lowest bit (the first link)
    	long mask = 1;
    	// last relevant bit
    	int maxBit = createStatements.size();
    	
    	for (int i = 0; i < maxBit; ++i) {
    		// if we need to change this link
    		if ((difference & mask) > 0) {
    			// and it was "on" in the old state
    			if ((oldCombination & mask) > 0) {
    				// it needs to be deleted
    				result.prependStatement(deleteStatements.get(i));
    			} else {
    				// if it was "off" it needs to be inserted
    				result.prependStatement(createStatements.get(i));
    			}
    		}
    		mask <<= 1;
    	}
    	
    	return result;
    }

    public String toString() {
        return "GEvalInstrTry_Assoc_LinkendSeqs";
    }
}
