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

// $Id: ASTIterationStatement.java 1734 2010-09-07 14:56:17Z lhamann $

package org.tzi.use.parser.soil.ast;

import java.io.PrintWriter;

import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.uml.sys.soil.MWhileStatement;
import org.tzi.use.util.soil.exceptions.compilation.CompilationFailedException;


public class ASTWhileStatement extends ASTStatement {
	
	private ASTExpression fCondition;
	
	private ASTStatement fBody;
	
	
	/**
	 * TODO
	 * @param iterVarName
	 * @param range
	 * @param body
	 */
	public ASTWhileStatement(
			ASTExpression condition, 
			ASTStatement body) {
		
		fCondition= condition;
		fBody = body;
		
		addChildStatement(body);
	}
	
	
	@Override
	protected MStatement generateStatement() throws CompilationFailedException {
		
		Expression condition = generateExpression(fCondition);
		if (!condition.type().isBoolean()) {
			throw new CompilationFailedException(this, "Condition expression must be of Boolean type");
		}
		
		MStatement body = generateStatement(fBody);
		
		// assigned(iteration) = assigned(body)
		fAssignedSet.add(fBody.fAssignedSet);
		
		return new MWhileStatement(condition, body);
	}


	@Override
	protected void printTree(StringBuilder prelude, PrintWriter target) {
		target.println(prelude + "[ITERATION]");
		
		if (prelude.length() == 0) {
			prelude.append("+-");
		} else {
			prelude.insert(0, "| ");
		}
		fBody.printTree(prelude, target);
		prelude.delete(0, 2);
	}


	@Override
	public String toString() {
		return 
			"while " + 
			fCondition.getStringRep() + 
			"do ... end";
	}
}