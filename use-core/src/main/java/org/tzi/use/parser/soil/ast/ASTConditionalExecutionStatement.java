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

import org.antlr.runtime.Token;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.sys.soil.MConditionalExecutionStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;


/**
 * AST node for a conditional execution statement.
 * @author Daniel Gent
 *
 */
public class ASTConditionalExecutionStatement extends ASTStatement {
	/** 
	 * The AST of the OCL expression representing the condition 
	 */
	private ASTExpression fCondition;
	/**
	 * The statement which is executed when <code>fCondition</code> is <code>true</code> 
	 */
	private ASTStatement fThenStatement;
	/**
	 * The statement which is executed when <code>fCondition</code> is <code>false</code> 
	 */
	private ASTStatement fElseStatement;
	
	
	/**
	 * Constructs a new AST node.
	 * @param condition AST of the condition expression
	 * @param thenStatement AST of the then statement.
	 * @param elseStatement AST of the else statement
	 */
	public ASTConditionalExecutionStatement(
			Token sourcePosition,
			ASTExpression condition,
			ASTStatement thenStatement,
			ASTStatement elseStatement) {
		super(sourcePosition);
		fCondition = condition;
		fThenStatement = thenStatement;
		fElseStatement = elseStatement;
	}

	
	/**
	 * The AST of the OCL expression representing the condition
	 * @return The AST of the condition
	 */
	public ASTExpression getCondition()
	{
		return fCondition;
	}
	
	
	/**
	 * Gets the AST of the statement which is executed when the condition evaluates to <code>true</code>.
	 * @return The AST of the then-statement
	 */
	public ASTStatement getThenStatement() {
		return fThenStatement;
	}

	
	/**
	 * Gets the AST of the statement which is executed when the condition evaluates to <code>false</code>.
	 * @return The AST of the else-statement
	 */
	public ASTStatement getElseStatement() {
		return fElseStatement;
	}

	
	@Override
	protected MConditionalExecutionStatement generateStatement() throws CompilationFailedException {
		
		// generate the condition expression and check if it's boolean
		Expression condition = generateExpression(fCondition);
		if (!condition.type().isTypeOfBoolean()) {
			throw new CompilationFailedException(this, "Expression "
					+ StringUtil.inQuotes(fCondition.getStringRep())
					+ " is expected to be of type "
					+ StringUtil.inQuotes("Boolean") + ", found "
					+ StringUtil.inQuotes(condition.type()) + ".");
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
