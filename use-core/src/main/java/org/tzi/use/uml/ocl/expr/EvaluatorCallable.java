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

package org.tzi.use.uml.ocl.expr;

import java.util.concurrent.Callable;

import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystemState;

/**
 * A {@link Callable} to evaluate expressions
 * possibly asynchronously.
 * @author Lars Hamann
 *
 */
public class EvaluatorCallable implements Callable<Value> {

	private final MSystemState state;
	private final Expression expression;
	
	private String failureMessage;
	private boolean hasFailed;
	
	public EvaluatorCallable(MSystemState state, Expression expr) {
		this.state = state;
		this.expression = expr;
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Value call() throws Exception {
		
		Evaluator evaluator = new Evaluator();

        Value v = null;
        try {
            v = evaluator.eval(expression, state);
        } catch (Exception ex) {
        	hasFailed = true;
        	failureMessage = ex.getMessage();
        }
        
		return v;
	}
	
	public boolean isEvaluationFailed() {
		return hasFailed;
	}
	
	public String getFailureMessage() {
		return failureMessage;
	}
}
