/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

// $Id$

package org.tzi.use.gen.assl.dynamics;

import org.tzi.use.gen.assl.statics.GInstrBarrier;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.Value;

/**
 * Dynamic part of the barrier instruction.
 * @author Lars Hamann
 *
 */
public class GEvalBarrier extends GEvalInstruction implements IGCaller {
	
	protected GInstrBarrier instr;
	
	public GEvalBarrier(GInstrBarrier instr) {
		this.instr = instr;
	}
	
	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.dynamics.GEvalInstruction#eval(org.tzi.use.gen.assl.dynamics.GConfiguration, org.tzi.use.gen.assl.dynamics.IGCaller, org.tzi.use.gen.assl.dynamics.IGCollector)
	 */
	@Override
	public void eval(GConfiguration conf, IGCaller caller, IGCollector collector)
			throws GEvaluationException {
		collector.detailPrintWriter().println(new StringBuilder("evaluating barrier `").append(instr).append("'").toString());
        long start = System.nanoTime();
        
		try {
            Value val = conf.evalExpression(instr.getExpression());
            
			if (collector.doDetailPrinting())
				collector.detailPrintWriter().println(
						"`" + instr + "' == " + val);
            
			boolean valid = false;
			if (val.isDefined()) {
				valid = val.equals(BooleanValue.TRUE); 
			}
			
			if (!valid) {
            	collector.addBarrierBlock();
            	collector.setBlocked(true);
            }
            instr.getStatistic().registerResult(valid, System.nanoTime() - start);
            
            caller.feedback( conf, val, collector );
        } catch (MultiplicityViolationException e) {
            instr.getStatistic().registerException();
        	collector.invalid(e.getMessage());
        }
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.dynamics.IGCaller#feedback(org.tzi.use.gen.assl.dynamics.GConfiguration, org.tzi.use.uml.ocl.value.Value, org.tzi.use.gen.assl.dynamics.IGCollector)
	 */
	@Override
	public void feedback(GConfiguration configuration, Value value,
			IGCollector collector) throws GEvaluationException {

	}

}
