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

import org.tzi.use.gen.assl.statics.GInstrSub_Seq_Integer;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.SequenceValue;
import org.tzi.use.uml.ocl.value.IntegerValue;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class GEvalInstrSub_Seq_Integer extends GEvalInstruction implements IGCaller {
    private GInstrSub_Seq_Integer fInstr;
    private IGCaller fCaller;

    private SequenceValue fSequence;
    private int wantedSize;

    public GEvalInstrSub_Seq_Integer(GInstrSub_Seq_Integer instr ) {
        fInstr = instr;
    }

    public void eval(GConfiguration conf,
                     IGCaller caller,
                     IGCollector collector) throws GEvaluationException {
        fSequence = null;
        wantedSize = 0;
        
		if (collector.doDetailPrinting())
			collector.detailPrintWriter().println(
					new StringBuilder("evaluating `").append(fInstr)
							.append("'").toString());
        
        fCaller = caller;
        fInstr.sequenceInstr().createEvalInstr().eval(conf,this,collector );
    }

    public void feedback( GConfiguration conf,
                          Value value,
                          IGCollector collector ) throws GEvaluationException {
        if (fSequence == null) {
            if (value.isUndefined())
                collector.invalid(
                                  buildCantExecuteMessage(fInstr, fInstr.sequenceInstr()));
            else {
                fSequence = (SequenceValue) value;
                fInstr.integerInstr().createEvalInstr().eval(conf,this,collector );
            }
        } else {
            if (value.isUndefined())
                collector.invalid(
                                  buildCantExecuteMessage(fInstr, fInstr.integerInstr()));
            else {
                wantedSize = ((IntegerValue) value).value();
                createSubSequence(conf, collector);
            }
        }
    }
    
    private void createSubSequence(GConfiguration conf,IGCollector collector)
        throws GEvaluationException {
        int origSize = fSequence.size();
        if (wantedSize < 0)
            collector.invalid( "Can't execute `" + fInstr + "', because `"
                               + fInstr.integerInstr() + "' has been "
                               + "evaluated to a negative integer.");
        else if (wantedSize > origSize)
            collector.invalid( "Can't execute `" + fInstr + "', because `"
                               +fInstr.integerInstr() + "' is greater than "
                               +"the size of `"+ fInstr.sequenceInstr() +"'.");
        else {
            List<Integer> mappings = new ArrayList<Integer>(origSize);
            Collection<Value> values = new ArrayList<Value>(wantedSize);
            
            for (int k=0; k < origSize; k++)
                mappings.add( Integer.valueOf(k) );
            
            for (int k=0; k < wantedSize; k++) {
                int r = conf.random().nextInt(origSize-k);
                values.add( fSequence.get(mappings.get(r).intValue() ) );
                mappings.remove(r);
            }
            
            Value subVal = new SequenceValue(fSequence.elemType(), values);
            
			if (collector.doDetailPrinting())
				collector.detailPrintWriter().println(
						"`" + fInstr + "' == " + subVal);
			
            fCaller.feedback( conf, subVal, collector );
        }
    }

    public String toString() {
        return "GEvalInstrSub_Seq_Integer";
    }

}
