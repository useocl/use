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

package org.tzi.use.uml.sys.soil;

import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;


/**
 * "Compiled" version of an <code>if ... then ... else ... end</code> statement. 
 * @author Lars Hamann
 * @author Daniel Gent
 */
public class MConditionalExecutionStatement extends MStatement {
	/** The condition expression */
	private Expression fCondition;
	/** The statement executed if {@link #fCondition} is <code>true</code>. */
	private MStatement fThenStatement;
	/** The statement executed if {@link #fCondition} is <code>false</code>. */
	private MStatement fElseStatement;
	
	
	/**
	 * Constructs a new conditional execution statement.
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
	 * @return the fCondition
	 */
	public Expression getCondition() {
		return fCondition;
	}

	/**
	 * @return the fThenStatement
	 */
	public MStatement getThenStatement() {
		return fThenStatement;
	}

	/**
	 * @return the fElseStatement
	 */
	public MStatement getElseStatement() {
		return fElseStatement;
	}

	/**
	 * <code>true</code> if {@link #fElseStatement} is not the {@link MEmptyStatement}.
	 * @return
	 */
	private boolean hasElseStatement() {
		return !fElseStatement.isEmptyStatement();
	}
	
	@Override
    public Value execute(SoilEvaluationContext context,
			StatementEvaluationResult result) throws EvaluationFailedException {
		
		Value value = EvalUtil.evaluateExpression(context, fCondition, false);
		
		MStatement toEvaluate;
		if ((value.isDefined() 
				&& ((BooleanValue)value).isTrue())) {
			
			toEvaluate = fThenStatement;
			// the else branch also gets evaluated if the 
			// condition's value is the undefined boolean value
		} else {
			toEvaluate = fElseStatement;
		}
		
		toEvaluate.execute(context, result);
		
		return null;
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
	protected void toConcreteSyntax(
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
		fThenStatement.toConcreteSyntax(indent, indentIncr, target);
		indent.delete(indent.length() - indentIncr.length(), indent.length());
		if (hasElseStatement()) {
			target.append(newLine);
			target.append(indent);
			target.append("else");
			target.append(newLine);
			indent.append(indentIncr);
			fElseStatement.toConcreteSyntax(indent, indentIncr, target);
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

	@Override
	public void processWithVisitor(MStatementVisitor v) throws Exception {
		v.visit(this);
	}
}
