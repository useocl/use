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

package org.tzi.use.parser.soil.ast;

import java.io.PrintWriter;

import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.sys.soil.MConditionalExecutionStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.soil.exceptions.compilation.CompilationFailedException;
import org.tzi.use.util.soil.exceptions.compilation.ConditionNotBooleanException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class ASTConditionalExecutionStatement extends ASTStatement {
	/** TODO */
	private ASTExpression fCondition;
	/** TODO */
	private ASTStatement fThenStatement;
	/** TODO */
	private ASTStatement fElseStatement;
	
	
	/**
	 * TODO
	 * @param condition
	 * @param thenStatement
	 * @param elseStatement
	 */
	public ASTConditionalExecutionStatement(
			ASTExpression condition,
			ASTStatement thenStatement,
			ASTStatement elseStatement) {
		
		fCondition = condition;
		fThenStatement = thenStatement;
		fElseStatement = elseStatement;
		
		addChildStatement(fThenStatement);
		addChildStatement(fElseStatement);
	}

	
	/**
	 * TODO
	 * @return
	 */
	public ASTExpression getCondition()
	{
		return fCondition;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public ASTStatement getThenStatement() {
		return fThenStatement;
	}

	
	/**
	 * TODO
	 * @return
	 */
	public ASTStatement getElseStatement() {
		return fElseStatement;
	}

	
	@Override
	protected MConditionalExecutionStatement generateStatement() throws CompilationFailedException {
		
		// generate the condition expression and check if it's boolean
		Expression condition = generateExpression(fCondition);
		if (!condition.type().isBoolean()) {
			throw new ConditionNotBooleanException(
					this, 
					fCondition, 
					condition.type());
		}
		
		// generate the then and else statements
		MStatement[] compiledStatements = new MStatement[2];
		ASTStatement[] statements = { fThenStatement, fElseStatement };
		
		for (int i = 0; i < 2; ++i) {
			fSymtable.storeState();
			compiledStatements[i] = generateStatement(statements[i]);
			fSymtable.restoreState(this);
			
			// assigned(conditional) = assigned(then) U assigned(else)
			fAssignedSet.add(statements[i].fAssignedSet);
		}
		
		return new MConditionalExecutionStatement(
				condition, 
				compiledStatements[0], 
				compiledStatements[1]);
	}
	
	
	@Override
	protected void printTree(StringBuilder prelude, PrintWriter target) {
		
		target.println(prelude + "[CONDITIONAL EXECUTION]");
		
		if (prelude.length() == 0) {
			prelude.append("+-");
		} else {
			prelude.insert(0, "| ");
		}
		fThenStatement.printTree(prelude, target);
		fElseStatement.printTree(prelude, target);
		prelude.delete(0, 2);
	}


	@Override
	public String toString() {
		return 
			"if " + 
			fCondition.getStringRep() + 
			" then ... " + 
			(fElseStatement.isEmptyStatement() ? 
					"end" : "else ... end");
	}
}
