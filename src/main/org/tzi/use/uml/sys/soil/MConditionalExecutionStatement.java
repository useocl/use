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

package org.tzi.use.uml.sys.soil;

import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class MConditionalExecutionStatement extends MStatement {
	/** TODO */
	private Expression fCondition;
	/** TODO */
	private MStatement fThenStatement;
	/** TODO */
	private MStatement fElseStatement;
	
	
	/**
	 * TODO
	 * @param condition
	 * @param thenStatement
	 * @param elseStatement
	 */
	public MConditionalExecutionStatement(
			Expression condition, 
			MStatement thenStatement, 
			MStatement elseStatement) {
		
		fCondition = condition;
		fThenStatement = thenStatement;
		fElseStatement = elseStatement;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public Expression getCondition() {
		return fCondition;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public MStatement getThenStatement() {
		return fThenStatement;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public boolean hasElseStatement() {
		return !fElseStatement.isEmptyStatement();
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public MStatement getElseStatement() {
		return fElseStatement;
	}
	
	
	@Override
	protected void evaluate() throws EvaluationFailedException {
		
		Value value = evaluateExpression(fCondition, false);
		
		MStatement toEvaluate;
		if ((value.isDefined() 
				&& ((BooleanValue)value).isTrue())) {
			
			toEvaluate = fThenStatement;
			// the else branch also gets evaluated if the 
			// condition's value is the undefined boolean value
		} else {
			toEvaluate = fElseStatement;
		}
		
		evaluateSubStatement(toEvaluate);
	}
	
	
	@Override
	protected String shellCommand() {
		
		String result = 
			"if " + fCondition + " then " + fThenStatement.shellCommand();
		
		if (hasElseStatement()) {
			result += " else " + fElseStatement.shellCommand();
		}
		
		result += " end";
		
		return result;
	}
	
	
	@Override
	public boolean hasSideEffects() {		
		return (
				fCondition.hasSideEffects() || 
				fThenStatement.hasSideEffects() || 
				fElseStatement.hasSideEffects());
	}


	@Override
	protected void toVisitorString(
			StringBuilder indent,
			String indentIncr,
			StringBuilder target) {
		
		String newLine = "\n";
		
		target.append(indent);
		target.append("if ");
		target.append(fCondition);
		target.append(" then");
		target.append(newLine);
		indent.append(indentIncr);
		fThenStatement.toVisitorString(indent, indentIncr, target);
		indent.delete(indent.length() - indentIncr.length(), indent.length());
		if (hasElseStatement()) {
			target.append(newLine);
			target.append(indent);
			target.append("else");
			target.append(newLine);
			indent.append(indentIncr);
			fElseStatement.toVisitorString(indent, indentIncr, target);
			indent.delete(indent.length() - indentIncr.length(), indent.length());
		}
		target.append(newLine);
		target.append(indent);
		target.append("end");
	}


	@Override
	public String toString() {
		return shellCommand();
	}
}
