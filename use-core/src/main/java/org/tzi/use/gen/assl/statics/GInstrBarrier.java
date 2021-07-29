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

package org.tzi.use.gen.assl.statics;

import org.tzi.use.gen.assl.dynamics.GEvalBarrier;
import org.tzi.use.gen.assl.dynamics.GEvalInstruction;
import org.tzi.use.gen.tool.statistics.GStatistic;
import org.tzi.use.uml.ocl.expr.Expression;

/**
 * Static part of the barrier instruction
 * @author Lars Hamann
 *
 */
public class GInstrBarrier implements GInstruction {

	private final Expression barrierExpression;
	
	private final GStatistic barrierStatistic;
	
	public GInstrBarrier(Expression barrierExp) {
		this.barrierExpression = barrierExp;
		this.barrierStatistic = new GStatistic(this);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.GInstruction#createEvalInstr()
	 */
	@Override
	public GEvalInstruction createEvalInstr() {
		return new GEvalBarrier(this);
	}
	
	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.GInstruction#processWithVisitor(org.tzi.use.gen.assl.statics.InstructionVisitor)
	 */
	@Override
	public void processWithVisitor(InstructionVisitor v) {
		v.visitBarrier(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Barrier([" + barrierExpression.toString() + "])";
	}

	/**
	 * Returns the expression evaluated by this barrier
	 * @return
	 */
	public Expression getExpression() {
		return barrierExpression;
	}
	
	/**
	 * Returns the statistic information about his barrier.
	 * @return
	 */
	public GStatistic getStatistic() {
		return this.barrierStatistic;
	}
}
