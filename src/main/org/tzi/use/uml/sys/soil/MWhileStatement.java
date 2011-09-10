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

// $Id: MIterationStatement.java 1734 2010-09-07 14:56:17Z lhamann $

package org.tzi.use.uml.sys.soil;

import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.soil.exceptions.evaluation.EvaluationFailedException;


/**
 * This statement executes a while-loop.
 * @author Fabian Buettner
 *
 */
public class MWhileStatement extends MStatement {
	
	private Expression fCondition;
	
	private MStatement fBody;
	
	
	/**
	 * TODO
	 * @param variableName
	 * @param range
	 * @param body
	 */
	public MWhileStatement(
			Expression condition, 
			MStatement body) {
		
		fCondition= condition;
		fBody = body;
	}
	
	
		
	@Override
	protected void evaluate() throws EvaluationFailedException {
		
		while(  true) {
			Value v = evaluateExpression(fCondition);
			if (v.isDefined() && ((BooleanValue)v).value())
				evaluateSubStatement(fBody);
			else
				break;
		}
		
	}
	
	
	@Override
	protected String shellCommand() {
		return 
		"while " + 
		fCondition + 
		" do " + 
		fBody.shellCommand() + 
		" end";
	}
	

	@Override
	public boolean hasSideEffects() {
		return(fCondition.hasSideEffects() || fBody.hasSideEffects());
	}


	@Override
	protected void toVisitorString(
			StringBuilder indent, 
			String indentIncrease,
			StringBuilder target) {
		
		String newLine = "\n";
		
		target.append(indent);
		target.append("while ");
		target.append(fCondition);
		target.append(" do ");
		if (!fBody.isEmptyStatement()) {
			target.append(newLine);
			indent.append(indentIncrease);
			fBody.toVisitorString(indent, indentIncrease, target);
			indent.delete(indent.length() - indentIncrease.length(), indent.length());
			target.append(newLine);
		}
		target.append(indent);
		target.append("end");
	}


	@Override
	public String toString() {
		return shellCommand();
	}
}
