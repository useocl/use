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
import java.util.LinkedHashMap;

import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.parser.ocl.ASTOperationExpression;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.sys.soil.MOperationCallStatement;
import org.tzi.use.util.soil.exceptions.compilation.CompilationFailedException;
import org.tzi.use.util.soil.exceptions.compilation.NotCallableException;
import org.tzi.use.util.soil.exceptions.compilation.UndefinedOperationException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class ASTOperationCallStatement extends ASTStatement {
	/** TODO */
	private ASTExpression fOperationCall;
	
	
	/**
	 * TODO
	 * @param operationCall
	 */
	public ASTOperationCallStatement(ASTExpression operationCall) {
		fOperationCall = operationCall;
	}
		
	
	/**
	 * TODO
	 * @return
	 */
	public ASTExpression getOperationCall() {
		return fOperationCall;
	}
	
	
	@Override
	protected MOperationCallStatement generateStatement() throws CompilationFailedException {
		
		/*Expression object = generateObjectExpression(fObject);
		
		MOperation operation = generateOperation(object, fOperationName);
	
		if (!operation.hasStatement()) {
			
			throw new UndefinedOperationException(
					this, 
					operation.cls(), 
					fOperationName);
		}
		
		LinkedHashMap<String, Expression> arguments = 
			generateOperationArguments(operation, fArguments);
		
		return new MOperationCallStatement(object, operation, arguments);
		*/
		
		// expression needs to be of type ASTOperationExpression
		if (!(fOperationCall instanceof ASTOperationExpression)) {
			throw new NotCallableException(this, fOperationCall);
		}
		
		ASTOperationExpression operationExpression =
			(ASTOperationExpression)fOperationCall;
		
		// needs to conform to
		// sourceExpression '.'  operation '(' argumentExpressions ')'
		if (!operationExpression.isObjectOperation()) {			
			throw new NotCallableException(this, fOperationCall);
		}
		
		ASTExpression objectExpression = 
			operationExpression.getSourceExpression();
		
		// for error message produced when there is no such object
		objectExpression.setStringRep(fOperationCall.getStringRep());
		
		Expression object = 
			generateObjectExpression(objectExpression);
				
		MClass objectClass = ((ObjectType)object.type()).cls();

		String operationName = operationExpression.getOpToken().getText();
		
		// determine the operation
		MOperation operation = 
			generateOperation(objectClass, operationName);
		
		if (!operation.hasStatement()) {
			
			throw new UndefinedOperationException(
					this, 
					objectClass, 
					operationName);
		}
		
		// construct arguments
		LinkedHashMap<String, Expression> arguments = 
			generateOperationArguments(
					operation, 
					operationExpression.getArgs());
		
		return new MOperationCallStatement(object, operation, arguments);
	}

	
	@Override
	protected void printTree(StringBuilder prelude, PrintWriter target) {
		target.println(prelude + "OPERATION CALL");
	}

	
	@Override
	public String toString() {
		/*StringBuilder sB = new StringBuilder();
		sB.append(fObject.getStringRep());
		sB.append(".");
		sB.append(fOperationName);
		sB.append("(");
		for (ASTExpression argument : fArguments) {
			sB.append(argument.getStringRep());
			sB.append(",");
		}
		sB.delete(sB.length() - 1, sB.length());
		sB.append(")");
		
		return sB.toString();*/
		
		return fOperationCall.getStringRep();
	}
}
