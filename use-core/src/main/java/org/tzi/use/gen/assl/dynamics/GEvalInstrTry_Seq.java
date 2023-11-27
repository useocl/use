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

import org.tzi.use.gen.assl.statics.GInstrTry_Seq;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.Value;

public class GEvalInstrTry_Seq extends GEvalInstrTry implements IGCaller {
    private GInstrTry_Seq fInstr;
    private IGCaller fCaller;

    public GEvalInstrTry_Seq(GInstrTry_Seq instr, boolean first ) {
    	super(first);
        fInstr = instr;
    }

    public void eval(GConfiguration conf,
                     IGCaller caller,
                     IGCollector collector) throws GEvaluationException {
		if (collector.doDetailPrinting())
			collector.getUserOutput().println(
					new StringBuilder("evaluating `").append(fInstr)
							.append("'").toString());
        
		fCaller = caller;
        fInstr.sequenceInstr().createEvalInstr().eval( conf, this, collector );
    }

    public void feedback( GConfiguration conf,
                          Value value,
                          IGCollector collector ) throws GEvaluationException {
        if (value.isUndefined())
            collector.invalid( buildCantExecuteMessage(fInstr,fInstr.sequenceInstr()) );
        else {
        	CollectionValue col = (CollectionValue)value; 
        	this.initProgress(col.size());
        	int element = 0;
        	
            for (Value elem : col) {
            	if (collector.canStop()) {
                	break;
                }
            	++element;
				if (collector.doDetailPrinting())
					collector.getUserOutput().println(
							"`" + fInstr + "' == " + elem);
                
                this.outPutProgress(element);
                
                fCaller.feedback( conf, elem, collector );
            }
            
            this.endProgress();
        }
    }

    public String toString() {
        return "GEvalInstrTry_Seq";
    }

}
