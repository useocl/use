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

import org.tzi.use.gen.assl.statics.GInstrCreate_C;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MCmdCreateObjects;

import org.tzi.use.util.cmd.CommandFailedException;
import org.tzi.use.util.cmd.CannotUndoException;

import java.util.List;
import java.util.ArrayList;

class GEvalInstrCreate_C extends GEvalInstruction {
    private GInstrCreate_C fInstr;

    public GEvalInstrCreate_C(GInstrCreate_C instr ) {
        fInstr = instr;
    }

    public void eval(GConfiguration conf,
                     IGCaller caller,
                     IGCollector collector) throws GEvaluationException {
        collector.detailPrintWriter().println("evaluating `" + fInstr + "'");
        MClass cls = fInstr.cls();
        ObjectType objectType = TypeFactory.mkObjectType( cls );
        List<String> names = new ArrayList<String>();
        
        names.add(conf.systemState().uniqueObjectNameForClass( cls.name() ));
        MCmd cmd = new MCmdCreateObjects(conf.systemState(),
                                         names,
                                         objectType);
        try {
            collector.basicPrintWriter().println(cmd.getUSEcmd());
            cmd.execute();
            ObjectValue ov = new ObjectValue(objectType,
                                             conf.systemState()
                                             .objectByName((String) names.get(0)));
            collector.detailPrintWriter().println("`"+ fInstr + "' == "+ov);
            caller.feedback(conf, ov, collector);
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
