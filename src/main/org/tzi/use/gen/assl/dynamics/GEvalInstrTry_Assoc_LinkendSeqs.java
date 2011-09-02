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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.tzi.use.gen.assl.statics.GInstrTry_Assoc_LinkendSeqs;
import org.tzi.use.gen.assl.statics.GInstruction;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.soil.MEmptyStatement;
import org.tzi.use.uml.sys.soil.MLinkDeletionStatement;
import org.tzi.use.uml.sys.soil.MLinkInsertionStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.uml.sys.soil.MRValueExpression;
import org.tzi.use.uml.sys.soil.MSequenceStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.CollectionUtil;


class GEvalInstrTry_Assoc_LinkendSeqs extends GEvalInstruction
    implements IGCaller {
    protected GInstrTry_Assoc_LinkendSeqs fInstr;
	static List<List<Value>> emptyQualifiers = Collections.emptyList();
    private IGCaller fCaller;
    private ListIterator<GValueInstruction> fIterator;
    protected List<List<MObject>> fObjectLists;
    private GInstruction fLastEvaluatedInstruction;
    
    public GEvalInstrTry_Assoc_LinkendSeqs(GInstrTry_Assoc_LinkendSeqs instr ) {
        fInstr = instr;
    }

    public void eval(GConfiguration conf,
                     IGCaller caller,
                     IGCollector collector) throws GEvaluationException {
        collector.detailPrintWriter().println(new StringBuilder("evaluating `").append(fInstr).append("'").toString());
        fCaller = caller;
        fIterator = fInstr.linkendSequences().listIterator();
        fObjectLists = new ArrayList<List<MObject>>();
    
        // fIterator has a next element, because an association has at least
        // two link ends.
        fLastEvaluatedInstruction = (GInstruction) fIterator.next();
        GCreator.createFor(fLastEvaluatedInstruction).eval(conf,this,collector);
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
            fLastEvaluatedInstruction = (GInstruction) fIterator.next();
            GCreator.createFor(fLastEvaluatedInstruction)
                .eval(conf,this,collector);
            fIterator.previous();
        }
        else
            // every parameter is evaluated, so fObjectLists is complete now
            tryLinks(conf, collector);
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
        
        MSystemState state = conf.systemState();
        MSystem system = state.system();
        PrintWriter basicOutput = collector.basicPrintWriter();
        
        // for each link we need a statement to insert and delete
        List<MStatement> insertStatements = 
        	new ArrayList<MStatement>(combinations.size());
        List<MStatement> deleteStatements =
        	new ArrayList<MStatement>(combinations.size());
        
        MAssociation association = fInstr.association();
          
        // construct the insert and delete statements. during the construction
        // the current configuration (i.e. which links already exist in the
        // current state) is determined and returned
        long initialConfiguration = 
        	constructInsertAndDeleteStatements(
        			combinations, 
        			numLinks, 
        			association, 
        			state, 
        			insertStatements, 
        			deleteStatements);
        
        // in the original version of this algorithm, all links get removed so
        // we do that as well to ensure previous test cases behave the same
        try {
        	system.evaluateStatement(
        			constructLinkChangeStatement(
        					initialConfiguration, 
        					0, 
        					insertStatements, 
        					deleteStatements), 
        			true, 
        			false,
        			false);
        } catch (MSystemException e) {
        	throw new GEvaluationException(e);
        }
                
        // this is the "all links exist" configuration + 1
        long tooLarge = (1 << insertStatements.size());
        
        // we start with the 0000... (all links off) combination
        long oldConfiguration = 0;
        // configuration after state change
        // in the first iteration old- and newConfiguration are equal, so we 
        // can check the initial state
        long newConfiguration = oldConfiguration;
        
        do {
        	// construct the statement that transforms the state from old to
        	// new configuration
        	MStatement statement = constructLinkChangeStatement(
        			oldConfiguration, 
        			newConfiguration, 
        			insertStatements, 
        			deleteStatements);
        	
        	basicOutput.println(statement.getShellCommand());
        	try {	
        		system.evaluateStatement(statement, true, false, false);
			} catch (MSystemException e) {
				throw new GEvaluationException(e);
			}
			
			fCaller.feedback(conf, null, collector);    
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
            
            // configurations for next iteration
            oldConfiguration = newConfiguration;
        	++newConfiguration;
        	
          // stop once all configurations have been built or stopping is allowed
        } while ((newConfiguration < tooLarge) && !collector.canStop());
        
        
        // transform state to the initial state if we aren't in it anyways
        if (oldConfiguration != initialConfiguration) {
        	MStatement statement = 
        		constructLinkChangeStatement(
        				oldConfiguration, 
        				initialConfiguration, 
        				insertStatements, 
        				deleteStatements);
        	
        	basicOutput.println(statement.getShellCommand());
        	try {
        		system.evaluateStatement(statement, true, false, false);
			} catch (MSystemException e) {
				throw new GEvaluationException(e);
			}
        }
    }
    
   
    // this is an alternative version of the tryLinks method which iterates
    // the possible link configurations in a different way (only one link gets
    // inserted/deleted between two configurations). since this possibly leads
    // to a different number of states that were checked until a valid one was 
    // found, there can be (and unfortunately are) discrepancies in the output
    // of test cases. since i'm only supposed to make this work with soil 
    // statements, i've reverted to the old behavior, but i leave this version
    // here in case someone tries to tune the generator in the future.
    @SuppressWarnings("unused")
	private void tryLinks_alternative(
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
        
        MSystemState state = conf.systemState();
        MSystem system = state.system();
        PrintWriter basicOutput = collector.basicPrintWriter();
        
        // for each link we need a statement to insert and delete
        List<MStatement> insertStatements = 
        	new ArrayList<MStatement>(numLinks);
        List<MStatement> deleteStatements =
        	new ArrayList<MStatement>(numLinks);
        
        MAssociation association = fInstr.association();
        
        // construct the insert and delete statements. during the construction
        // the current configuration (i.e. which links already exist in the
        // current state) is determined and returned
        long initConfiguration = 
        	constructInsertAndDeleteStatements(
        			combinations, 
        			numLinks, 
        			association, 
        			state, 
        			insertStatements, 
        			deleteStatements);
                
      
        // check all link configurations
        // currentConfiguration gets modified in the method, so even if it
        // throws we know the current state and can try to restore the initial
        // state
        long currentConfiguration = initConfiguration;
        try {
        	tryLinkCombinations(
        			conf, 
        			collector, 
        			currentConfiguration, 
        			numLinks, 
        			insertStatements, 
        			deleteStatements);     	
        } finally {
        	// transform state to the initial state if we aren't in it anyways
            if (currentConfiguration != initConfiguration) {
            	MStatement statement = 
            		constructLinkChangeStatement(
            				currentConfiguration, 
            				initConfiguration, 
            				insertStatements, 
            				deleteStatements);
            	
            	basicOutput.println(statement.getShellCommand());
            	try {
            		system.evaluateStatement(statement, true, false, false);
    			} catch (MSystemException e) {
    				// this shadows the original exception thrown in 
    				// tryLinkCombinations
    				throw new GEvaluationException(e);
    			}
            }
        }
    }
    
    
    private void tryLinkCombinations(
			GConfiguration conf,
			IGCollector collector,
			long configuration,
			int numLinks,
			List<MStatement> insertStatements, 
			List<MStatement> deleteStatements) throws GEvaluationException {
		
		MSystemState state = conf.systemState();
	    MSystem system = state.system();
	    PrintWriter basicOutput = collector.basicPrintWriter();
		
		long numCombinations = (1 << numLinks);
	    long combination = 0;
	    
	    do {
	    	MStatement statement = MEmptyStatement.getInstance();
	    	
	    	// no modifications in the first iteration to be able to check
	    	// whether the initial state is good enough
	    	if (combination > 0) {
	    		// this enumerates all possible configurations in a way that 
	    		// only one bit changes between configurations, and thus only 
	    		// one link has to be inserted or deleted
	    		for (int i = (numLinks - 1); i >= 0 ; --i) {
	    			// this check determines which bit will be changed
	    			if (combination % (1 << i) == 0) {
	    				// check whether link must be deleted or inserted
	    				if ((configuration & (1 << i)) > 0) {
	    					statement = deleteStatements.get(i);
	    				} else {
	    					statement = insertStatements.get(i);
	    				}
	    				configuration ^= (1 << i);
	    				break;
	    			}
	    		}
	    	}
	    	
	    	basicOutput.println(statement.getShellCommand());
	    	try {
	    		system.evaluateStatement(statement, true, false, false);
			} catch (MSystemException e) {
				throw new GEvaluationException(e);
			}
	    	
			fCaller.feedback(conf, null, collector);    
	        if (collector.expectSubsequentReporting()) {
	        	MSequenceStatement changeStatement = 
	        		constructLinkChangeStatement(
	        					0,                 // transform "no links" -> 
	        					configuration,     // "currently existing links"
	        					insertStatements, 
	        					deleteStatements);
	        	
	        	for (MStatement s : changeStatement.getStatements()) {
	        		if (s.isEmptyStatement()) {
	        			continue;
	        		} else {
	        			collector.subsequentlyPrependStatement(s);
	        		}
	        	}
	        }
	        
	    } while ((++combination < numCombinations) && !collector.canStop());
		
	 // combination is already 1 ahead of current combination    	
	    --combination;
	}

    
	private long constructInsertAndDeleteStatements(
    		List<List<MObject>> combinations,
    		int numLinks,
    		MAssociation association,
    		MSystemState state,
    		List<MStatement> insertStatements,
    		List<MStatement> deleteStatements) throws GEvaluationException {
    	
    	// initial configuration
        long initConfiguration = 0;
        
        // cache for MObject -> Expression transformations
        Map<MObject, MRValue> participantCache = 
        	new HashMap<MObject, MRValue>();
        
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
        			new MLinkInsertionStatement(association, participants, Collections.<List<MRValue>>emptyList()));
        	deleteStatements.add(
        			new MLinkDeletionStatement(association, participants));
        	
        	try {
        		// FIXME: Support qualifiers in generator
				if (state.hasLink(association, objects, emptyQualifiers)) {
					// "turn on" bit i
					initConfiguration |= (1 << i);
				}
			} catch (MSystemException e) {
				throw new GEvaluationException(e);
			}
        }
    
    	return initConfiguration;
    }
    
    
    private MSequenceStatement constructLinkChangeStatement(
    		long oldCombination, 
    		long newCombination,
    		List<MStatement> insertStatements,
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
    	int maxBit = insertStatements.size();
    	
    	for (int i = 0; i < maxBit; ++i) {
    		// if we need to change this link
    		if ((difference & mask) > 0) {
    			// and it was "on" in the old state
    			if ((oldCombination & mask) > 0) {
    				// it needs to be deleted
    				result.prependStatement(deleteStatements.get(i));
    			} else {
    				// if it was "off" it needs to be inserted
    				result.prependStatement(insertStatements.get(i));
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
