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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.tzi.use.gen.assl.statics.GInstrInsert_Assoc_Linkends;
import org.tzi.use.gen.assl.statics.GInstruction;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MCmdInsertLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.util.cmd.CannotUndoException;
import org.tzi.use.util.cmd.CommandFailedException;


class GEvalInstrInsert_Assoc_Linkends extends GEvalInstruction
    implements IGCaller {
    private GInstrInsert_Assoc_Linkends fInstr;
    private IGCaller fCaller;
    private ListIterator<GValueInstruction> fIterator;
    private List<String> fObjectNames;

    public GEvalInstrInsert_Assoc_Linkends(GInstrInsert_Assoc_Linkends instr ) {
        fInstr = instr;
    }
    
    public void eval(GConfiguration conf,
                     IGCaller caller,
                     IGCollector collector) throws GEvaluationException {
        collector.detailPrintWriter().println("evaluating `" + fInstr + "'");
        fCaller = caller;
        fIterator = fInstr.linkEnds().listIterator();
        fObjectNames = new ArrayList<String>();
    
        // fIterator has a next element, because an association has at least
        // two linkends.
        GCreator.createFor((GInstruction)fIterator.next())
            .eval(conf,this,collector);
        fIterator.previous();
    }

    public void feedback(GConfiguration conf,
                         Value value,
                         IGCollector collector ) throws GEvaluationException {
        if (value.isUndefined()) {
            collector.invalid( buildCantExecuteMessage(fInstr,
                                                       (GValueInstruction) fInstr.linkEnds()
                                                       .get(fIterator.previousIndex())) );
            return;
        }

        fObjectNames.add( ((ObjectValue) value).value().name() );

        if (fIterator.hasNext()) {
            GCreator.createFor((GInstruction)fIterator.next())
                .eval(conf,this,collector);
            fIterator.previous();
        }
        else
            // every parameter is evaluated, so fObjectNames is complete now
            createLink(conf, collector);
    }
    
    private void createLink(GConfiguration conf,
                            IGCollector collector) throws GEvaluationException {

        // generate expressions
        Expression[] exprs = new Expression[fObjectNames.size()];
        int i = 0;
        
        for (String name : fObjectNames) {
            MObject obj =  conf.systemState().objectByName( name ); 
            exprs[i++] = new ExpVariable( obj.name(), obj.type() );
        }

        MCmd cmd = new MCmdInsertLink(conf.systemState(),
                                      exprs,
                                      fInstr.association());
        try {
            collector.basicPrintWriter().println(cmd.getUSEcmd());
            cmd.execute();
            
            fCaller.feedback(conf, null, collector);
            if (collector.expectSubsequentReporting()) {
                collector.subsequentlyPrependCmd( cmd );
            }
            collector.basicPrintWriter().println("undo: " + cmd.getUSEcmd());
            cmd.undo();
        } catch (CommandFailedException e) {
            collector.invalid(e);
        } catch (CannotUndoException e) {
            throw new GEvaluationException(e);
        }
    }

    public String toString() {
        return "GEvalInstrInsert_Assoc_Linkends";
    }

}
