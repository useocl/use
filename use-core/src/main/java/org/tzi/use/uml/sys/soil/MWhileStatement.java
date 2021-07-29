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
 * This statement executes a while-loop.
 * 
 * @author Fabian Buettner
 *
 */
public class MWhileStatement extends MStatement {
	
	private Expression fCondition;
	
	private MStatement fBody;
	
	/**
	 * Constructs a new while statement.
     * 
	 * @param condition The expression used as the while condition.
	 * @param body The body of the while statement.
	 */
    public MWhileStatement(Expression condition, MStatement body) {
		fCondition= condition;
		fBody = body;
	}
	
	/**
	 * @return the fCondition
	 */
	public Expression getCondition() {
		return fCondition;
	}

	/**
	 * @return the fBody
	 */
	public MStatement getBody() {
		return fBody;
	}

	@Override
    public Value execute(SoilEvaluationContext context, StatementEvaluationResult result)
            throws EvaluationFailedException {
		
		while (true) {
            Value v = EvalUtil.evaluateExpression(context, fCondition);
			if (v.isDefined() && ((BooleanValue)v).value())
                fBody.execute(context, result);
			else
				break;
		}
		
		return null;
	}
	
	@Override
	protected String shellCommand() {
        return "while " + fCondition + " do " + fBody.shellCommand() + " end";
	}
	

	@Override
    protected void toConcreteSyntax(StringBuilder indent, String indentIncrease, StringBuilder target) {

		String newLine = "\n";
		
		target.append(indent);
		target.append("while ");
		target.append(fCondition);
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
