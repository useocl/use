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

import org.tzi.use.gen.assl.statics.GInstrDelete_Object;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MCmdDestroyObjects;

import org.tzi.use.util.cmd.CommandFailedException;
import org.tzi.use.util.cmd.CannotUndoException;

import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;


class GEvalInstrDelete_Object extends GEvalInstruction
    implements IGCaller {
    private GInstrDelete_Object fInstr;
    private IGCaller fCaller;

    public GEvalInstrDelete_Object(GInstrDelete_Object instr ) {
        fInstr = instr;
    }

    public void eval(GConfiguration conf,
                     IGCaller caller,
                     IGCollector collector) throws GEvaluationException {
        collector.detailPrintWriter().println(new StringBuilder("evaluating `").append(fInstr).append("'").toString());
        fCaller = caller;
        GCreator.createFor(fInstr.objectInstr()).eval(conf,this,collector );
    }

    public void feedback( GConfiguration conf,
                          Value value,
                          IGCollector collector ) throws GEvaluationException {
        if (value.isUndefined()) {
            collector.invalid(
                              buildCantExecuteMessage(fInstr,fInstr.objectInstr()) );
            return;
        }

        Expression[] exprs = {new ExpressionWithValue( value )};
        MCmd cmd = new MCmdDestroyObjects(conf.systemState(), exprs );

        try {
            collector.basicPrintWriter().println(cmd.getUSEcmd());
            cmd.execute();

            //conf.varBindings().push(fInstr.target(), value);

            fCaller.feedback( conf, null, collector );
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
        return "GEvalInstrDelete_Object";
    }

}

    
