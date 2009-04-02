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

import org.tzi.use.gen.assl.statics.GAttributeAssignment;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.ocl.expr.ExpAttrOp;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MCmdSetAttribute;
import org.tzi.use.util.cmd.CannotUndoException;
import org.tzi.use.util.cmd.CommandFailedException;


class GEvalAttributeAssignment extends GEvalInstruction
    implements IGCaller {
    private GAttributeAssignment fInstr;
    private IGCaller fCaller;
    private String fObjectName;

    public GEvalAttributeAssignment(GAttributeAssignment instr) {
        fInstr = instr;
    }

    public void eval(GConfiguration conf,
                     IGCaller caller,
                     IGCollector collector) throws GEvaluationException {
        collector.detailPrintWriter().println("evaluating `" + fInstr + "'");
        fCaller = caller;
        fObjectName = null;
        GCreator.createFor(fInstr.targetObjectInstr()).eval( conf,this,collector );
    }

    public void feedback( GConfiguration conf,
                          Value value,
                          IGCollector collector ) throws GEvaluationException {
        if (fObjectName==null) {
            if (value.isUndefined())
                collector.invalid(
                                  buildCantExecuteMessage( fInstr, fInstr.targetObjectInstr()) );
            else {
                fObjectName = ((ObjectValue) value).value().name();
                GCreator.createFor(fInstr.sourceInstr()).eval(conf,this,collector );
            }
        } else {
            //      MCmd cmd=new MCmdSetAttribute(conf.systemState(),
            //                    fObjectName,
            //                    fInstr.targetAttribute().name(),
            //                    new ExpressionWithValue(value) );

            // um ExprAttrOp zu generieren.
            MAttribute a = fInstr.targetAttribute();
            Value v = new ObjectValue( TypeFactory.mkObjectType( a.owner() ), 
                                       conf.systemState().objectByName( fObjectName ) );

            // this value (`v') needs to be a different value than the given value from
            // the method siganture (`value').
            // I have no clue why!
            ExpAttrOp expA = new ExpAttrOp( a, new ExpressionWithValue( v ) );

            MCmd cmd=new MCmdSetAttribute(conf.systemState(),
                                          expA,
                                          new ExpressionWithValue( value ) );

            try {
                collector.basicPrintWriter().println(cmd.getUSEcmd());
                cmd.execute();
                //collector.detailPrintWriter().println("`"+ fInstr + "' == (no value)");

                fCaller.feedback(conf, null, collector);
                if (collector.expectSubsequentReporting()) {
                    collector.subsequentlyPrependCmd( cmd );
                }
                collector.basicPrintWriter().println("undo: " + cmd.getUSEcmd());
                cmd.undo();
            } catch (CommandFailedException e) {
                throw new GEvaluationException(e);
            } catch (CannotUndoException e) {
                throw new GEvaluationException(e);
            }
        }
    }

    public String toString() {
        return "GEvalAttributeAssignment";
    }
}
