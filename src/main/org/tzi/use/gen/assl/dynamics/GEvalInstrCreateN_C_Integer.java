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

import org.tzi.use.gen.assl.statics.GInstrCreateN_C_Integer;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.SequenceValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MCmdCreateObjects;
import org.tzi.use.util.cmd.CannotUndoException;
import org.tzi.use.util.cmd.CommandFailedException;

class GEvalInstrCreateN_C_Integer extends GEvalInstruction
    implements IGCaller {
    private GInstrCreateN_C_Integer fInstr;
    private IGCaller fCaller;

    public GEvalInstrCreateN_C_Integer(GInstrCreateN_C_Integer instr ) {
        fInstr = instr;
    }

    public void eval(GConfiguration conf,
                     IGCaller caller,
                     IGCollector collector) throws GEvaluationException {
        collector.detailPrintWriter().println(new StringBuilder("evaluating `").append(fInstr).append("'").toString());
        fCaller = caller;
        GCreator.createFor(fInstr.integerInstr()).eval(conf,this,collector );
    }

    public void feedback( GConfiguration conf,
                          Value value,
                          IGCollector collector ) throws GEvaluationException {
        if (value.isUndefined()) {
            collector.invalid(
                              buildCantExecuteMessage(fInstr,fInstr.integerInstr()) );
            return;
        }
        int count = ((IntegerValue) value).value();
        if (count < 0)
            collector.invalid( "Can't execute `" + fInstr + "', because `"
                               + fInstr.integerInstr() + "' has been "
                               + "evaluated to a negative integer.");
        if (count >= 0) {
            MClass cls = fInstr.cls();
            ObjectType objectType = TypeFactory.mkObjectType( cls );
        
            List<String> names = new ArrayList<String>();
            for (int k=1; k <= count; k++)
                names.add( conf.systemState().uniqueObjectNameForClass( cls.name() ) );
            try {
                MCmd cmd = null;
                if (!names.isEmpty()) {
                    cmd = new MCmdCreateObjects(conf.systemState(),
                                                names,
                                                objectType);
                    collector.basicPrintWriter().println(cmd.getUSEcmd());
                    cmd.execute();
                }
                
                List<Value> objects = new ArrayList<Value>();
                for (String name : names) {
                    objects.add( new ObjectValue(objectType,
                                                 conf.systemState().objectByName(name) ));
                }
                
                Value val = new SequenceValue(objectType, objects);
                collector.detailPrintWriter().println(
                                                      "`"+ fInstr + "' == " + val);
                fCaller.feedback( conf, val, collector );
                if (cmd!=null) {
                    if (collector.expectSubsequentReporting())
                        collector.subsequentlyPrependCmd( cmd );
                    collector.basicPrintWriter().println(
                                                         "undo: " + cmd.getUSEcmd());
                    cmd.undo();
                }
            } catch (CommandFailedException e) {
                throw new GEvaluationException(e);
            } catch (CannotUndoException e) {
                throw new GEvaluationException(e);
            }
        }
    }

    public String toString() {
        return "GEvalInstrCreateN_C_Integer";
    }
}
