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

package org.tzi.use.parser.soil.ast;

import java.io.PrintWriter;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.sys.soil.MEnterOperationStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;


/**
 * AST class for a legacy openter statement
 * @author Daniel Gent
 * @author Lars Hamann
 */
public class ASTEnterOperationStatement extends ASTStatement {
	
	private ASTExpression fObject;
	
	private String fOperationName;
	
	private List<ASTExpression> fArguments;
	
	
	/**
	 * Constructs a new ASTEnterOperationStatement node.
	 * @param object An expression which leads to the receiver.
	 * @param operationName The name of the operation to call.
	 * @param arguments The operation call arguments
	 */
	public ASTEnterOperationStatement(
			Token start,
			ASTExpression object, 
			String operationName, 
			List<ASTExpression> arguments) {
		super(start);
		fObject = object;
		fOperationName = operationName;
		fArguments = arguments;
	}
	
	@Override
	protected MStatement generateStatement() throws CompilationFailedException {
		
		Expression object = generateObjectExpression(fObject);
		
		MOperation operation = getOperationSafe((MClass)object.type(), fOperationName);
		
		if (operation.hasExpression() || operation.hasStatement()) {
			throw new CompilationFailedException(this, "Operation " +
					StringUtil.inQuotes(operation) +
					" is defined by a " +
					(operation.hasExpression() ? "OCL expression" : "soil statement") +
					" and cannot be entered with openter.");
		}
		
		// construct arguments
		Expression[] arguments = generateOperationArguments(operation, fArguments);
		
		return new MEnterOperationStatement(object, operation, arguments);
	}

	
	@Override
	protected void printTree(StringBuilder prelude, PrintWriter target) {
		target.println(prelude + "ENTER OPERATION");
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("openter ");
		sb.append(fOperationName);
		sb.append("(");
		StringUtil.fmtSeq(sb, fArguments, ", ");
		sb.append(")");
		
		return sb.toString();
	}
}
