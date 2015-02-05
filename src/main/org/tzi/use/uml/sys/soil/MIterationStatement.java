/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2012 Mark Richters, University of Bremen
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
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;


/**
 * The "compiled" <code>for ... in ... do ... end</code> statement.
 * @author Daniel Gent
 *
 */
public class MIterationStatement extends MStatement {
	/** The name of the iteration variable */
	private String fVariableName;
	/** The range expression of the iteration (the iteration source) */
	private Expression fRange;
	/** The body of the iteration */
	private MStatement fBody;
	
	/**
	 * Constructs a new iteration statement.
	 * @param variableName The name of the iteration variable
	 * @param range The range expression of the iteration (the iteration source)
	 * @param body The body of the iteration
	 */
	public MIterationStatement(
			String variableName, 
			Expression range, 
			MStatement body) {
		
		fVariableName = variableName;
		fRange = range;
		fBody = body;
	}
	
	/**
	 * @return the fVariableName
	 */
	public String getVariableName() {
		return fVariableName;
	}

	/**
	 * @return the fRange
	 */
	public Expression getRange() {
		return fRange;
	}

	/**
	 * @return the fBody
	 */
	public MStatement getBody() {
		return fBody;
	}

	@Override
    public Value execute(SoilEvaluationContext context,
			StatementEvaluationResult result) throws EvaluationFailedException {
	
		Value val = EvalUtil.evaluateExpression(context, fRange);
		
		if (val.isUndefined())
			return null;
		
		CollectionValue range = (CollectionValue)val;
		for (Value elem : range) {
			context.getVarEnv().assign(fVariableName, elem);
			fBody.execute(context, result);
		}
		
		return null;
	}
	
	
	@Override
	protected String shellCommand() {
		return 
		"for " + 
		fVariableName + 
		" in " + 
		fRange + 
		" do " + 
		fBody.shellCommand() + 
		" end";
	}
	

	@Override
	protected void toConcreteSyntax(
			StringBuilder indent, 
			String indentIncrease,
			StringBuilder target) {
		
		String newLine = "\n";
		
		target.append(indent);
		target.append("for ");
		target.append(fVariableName);
		target.append(" in ");
		target.append(fRange);
		target.append(" do ");
		if (!fBody.isEmptyStatement()) {
			target.append(newLine);
			indent.append(indentIncrease);
			fBody.toConcreteSyntax(indent, indentIncrease, target);
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

	@Override
	public void processWithVisitor(MStatementVisitor v) throws Exception {
		v.visit(this);
	}
}
