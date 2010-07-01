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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.tzi.use.gen.assl.statics.GInstrTry_Assoc_LinkendSeqs;
import org.tzi.use.gen.assl.statics.GInstruction;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MCmdDeleteLink;
import org.tzi.use.uml.sys.MCmdInsertLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.util.ListUtil;
import org.tzi.use.util.cmd.CannotUndoException;
import org.tzi.use.util.cmd.CommandFailedException;


class GEvalInstrTry_Assoc_LinkendSeqs extends GEvalInstruction
    implements IGCaller {
    private GInstrTry_Assoc_LinkendSeqs fInstr;
    private IGCaller fCaller;
    private ListIterator<GValueInstruction> fIterator;
    private List<List<MObject>> fObjectLists;
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
        // two linkends.
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
    
    private void tryLinks( GConfiguration conf,
                           IGCollector collector) throws GEvaluationException {
        int MAX_LINKS = 62; // 62 Links are 2^62 combinations, which can
        // be represented by a long (see variable `count').
        // By the way, the search of a valid state within
        // 2^62 combinations would not terminate in
        // years.
    
        // Just get combinations of objects and check its size.
        List<List<MObject>> combinations = ListUtil.<MObject>combinations(fObjectLists);
        
        if (combinations.size() > MAX_LINKS) {
            collector.invalid("Can't execute `" + fInstr + "', because there" +
                              "are more than 2^" +MAX_LINKS+ "combinations.");
            return;
        }

        // compute two collections containing commands:
        // cmds:   each command represents a link which can
        //   be inserted and removed.
        // cmdsToRemoveExistingLinks:   these commands are used to remove
        //   existing links. They will be reinserted after the
        //   search. cmdsToRemoveExistingLinks is a subset of cmds.
        List<MCmd> cmdList = new ArrayList<MCmd>( combinations.size() );
        List<MCmd> cmdsToRemoveExistingLinks = new ArrayList<MCmd>();
        
        for (List<MObject> objects : combinations) {
                        
            Expression[] exprs = new Expression[objects.size()];
            List<String> names = new ArrayList<String>( objects.size() );
            int i = 0;
            
            for (MObject obj : objects) {
                names.add( obj.name() );
                // generate expressions
                exprs[i++] = new ExpVariable( obj.name(), obj.type() );
            }
          
            cmdList.add(new MCmdInsertLink(conf.systemState(),
                                           exprs,
                                           fInstr.association()) );

            try {
                if (conf.systemState().hasLink(fInstr.association(),
                                               objects) )
                    cmdsToRemoveExistingLinks.add(
                                                  new MCmdDeleteLink(conf.systemState(),
                                                                     exprs,                      // ebenfalls ANGEPASST
                                                                     fInstr.association()) );
            } catch (MSystemException e) {
                throw new GEvaluationException(e);
            }
        }
        Object[] cmds = cmdList.toArray();
    
        // Remove existing links
        try {
            for(MCmd cmd : cmdsToRemoveExistingLinks) {
                collector.basicPrintWriter().println(cmd.getUSEcmd());
                cmd.execute();
            }
        } catch (CommandFailedException e) {
            throw new GEvaluationException(e);
        }

        // try every combination
        BigInteger combination = BigInteger.ZERO;
        BigInteger previousCombination = combination;
        long count = (new BigInteger("2")).pow(cmdList.size()).longValue();
        int lowestSetBit;

        try {
            while (combination.longValue()<count && !collector.canStop() ) {

                BigInteger addedBits = combination.andNot(previousCombination);
                lowestSetBit = addedBits.getLowestSetBit();
                while (lowestSetBit != -1) {
                    MCmd cmd = (MCmd) cmds[lowestSetBit];
                    collector.basicPrintWriter().println(cmd.getUSEcmd());
                    try {
                    	cmd.execute();
                    } catch (CommandFailedException e) {}
                    addedBits = addedBits.andNot(
                                                 BigInteger.ZERO.setBit(lowestSetBit));
                    lowestSetBit = addedBits.getLowestSetBit();
                }
        
                BigInteger removedBits=previousCombination.andNot(combination);
                lowestSetBit = removedBits.getLowestSetBit();
                while (lowestSetBit != -1) {
                    MCmd cmd = (MCmd) cmds[lowestSetBit];
                    collector.basicPrintWriter().println("undo: " + cmd.getUSEcmd());
                    if (cmd.hasExecuted())
                    	cmd.undo();
                    removedBits = removedBits.andNot(
                                                     BigInteger.ZERO.setBit(lowestSetBit));
                    lowestSetBit = removedBits.getLowestSetBit();
                }
        
                fCaller.feedback(conf, null, collector);
        
                // this section is just for the collector...
                if (collector.expectSubsequentReporting()) {
                    BigInteger comb = combination;   // makes a copy
                    lowestSetBit = comb.getLowestSetBit();
                    while (lowestSetBit != -1) {
                        collector.subsequentlyPrependCmd((MCmd) cmds[lowestSetBit]);
                        comb = comb.andNot(
                                           BigInteger.ZERO.setBit(lowestSetBit));
                        lowestSetBit = comb.getLowestSetBit();
                    }
                }
        
                previousCombination = combination;
                combination = combination.add(BigInteger.ONE);
            }
        
        //} catch (CommandFailedException e) {
        //   throw new GEvaluationException(e);
        } catch (CannotUndoException e) {
            throw new GEvaluationException(e);
        }

        try {
            // undoing the current link combination (deleting links)
            lowestSetBit = previousCombination.getLowestSetBit();
            while (lowestSetBit != -1) {
                MCmd cmd = (MCmd) cmds[lowestSetBit];
                collector.basicPrintWriter().println("undo: " + cmd.getUSEcmd());
                if (cmd.hasExecuted())
                	cmd.undo();
                previousCombination = previousCombination.andNot(
                                                                 BigInteger.ZERO.setBit(lowestSetBit));
                lowestSetBit = previousCombination.getLowestSetBit();
            }

            // reinsert removed links.
            for (MCmd cmd : cmdsToRemoveExistingLinks) {
                collector.basicPrintWriter().println("undo: " + cmd.getUSEcmd());
                cmd.undo();
            }
        } catch (CannotUndoException e) {
            throw new GEvaluationException(e);
        }
    }

    public String toString() {
        return "GEvalInstrTry_Assoc_LinkendSeqs";
    }

}