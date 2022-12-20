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

package org.tzi.use.parser.soil.ast;

import java.io.PrintWriter;

import org.antlr.runtime.Token;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.parser.ocl.ASTOperationExpression;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.sys.soil.MLibraryOperationCallStatement;
import org.tzi.use.uml.sys.soil.MObjectOperationCallStatement;
import org.tzi.use.uml.sys.soil.MOperationCallStatement;
import org.tzi.use.uml.sys.soil.library.LibraryOperation;
import org.tzi.use.uml.sys.soil.library.SoilLibrary;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;


/**
 * AST node of a statement, which encapsulates an OCL operation call expression.
 * The operation must be defined as a soil operation.
 * @author Daniel Gent
 *
 */
public class ASTOperationCallStatement extends ASTStatement {
	
	private ASTExpression fOperationCall;
	
	
	/**
	 * Constructs a new operation call statement AST node.
	 * This node encapsulates a call to a soil operation.
	 * @param operationCall
	 */
	public ASTOperationCallStatement(Token start, ASTExpression operationCall) {
		super(start);
		fOperationCall = operationCall;
	}
	
	@Override
	protected MOperationCallStatement generateStatement() throws CompilationFailedException {
		
		// expression needs to be of type ASTOperationExpression
		if (!(fOperationCall instanceof ASTOperationExpression)) {
			throw new CompilationFailedException(this, "Expression " +
					StringUtil.inQuotes(fOperationCall.getStringRep()) +
					" does not give a reference to an operation.");
		}
		
		ASTOperationExpression operationExpression = (ASTOperationExpression)fOperationCall;
		
		// needs to conform to
		// sourceExpression '.'  operation '(' argumentExpressions ')'
		if (!operationExpression.isObjectOperation()) {
			return generateLibraryCall(operationExpression);
		}
		
		ASTExpression objectExpression = operationExpression.getSourceExpression();
		
		// for error message produced when there is no such object
		objectExpression.setStringRep(fOperationCall.getStringRep());
		Expression object = generateObjectExpression(objectExpression);
				
		MClass objectClass = (MClass)object.type();

		String operationName = operationExpression.getOpToken().getText();
		
		// determine the operation
		MOperation operation = getOperationSafe(objectClass, operationName);
		
		if (!operation.hasStatement()) {
			throw new CompilationFailedException(this, "Operation "
					+ StringUtil.inQuotes(objectClass.name() + "::"
							+ operationName)
					+ " is not defined by a soil statement.");
		}
		
		// construct arguments
		Expression[] arguments = 
			generateOperationArguments(
					operation, 
					operationExpression.getArgs());
		
		return new MObjectOperationCallStatement(object, operation, arguments);
	}

	private MOperationCallStatement generateLibraryCall(ASTOperationExpression operationExpression) throws CompilationFailedException {
				
		Expression[] arguments = new Expression[operationExpression.getArgs().size()];
		int i = 0;
		for (ASTExpression astExp : operationExpression.getArgs()) {
			Expression exp = generateExpression(astExp);
			arguments[i] = exp;
			++i;
		}
		
		LibraryOperation op = SoilLibrary.getInstance().findOperation(operationExpression.getOpToken().getText(), arguments);
				
		if (op == null) {
			throw new CompilationFailedException(this, "Expression " +
					StringUtil.inQuotes(fOperationCall.getStringRep()) +
					" does not give a reference to an operation.");
		}
		
		return new MLibraryOperationCallStatement(op, arguments);
	}
	
	@Override
	protected void printTree(StringBuilder prelude, PrintWriter target) {
		target.println(prelude + "OPERATION CALL");
	}

	
	@Override
	public String toString() {
		return fOperationCall.getStringRep();
	}
}
