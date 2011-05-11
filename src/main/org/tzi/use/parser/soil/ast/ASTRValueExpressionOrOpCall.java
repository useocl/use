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

import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.sys.soil.MOperationCallStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.uml.sys.soil.MRValueExpression;
import org.tzi.use.uml.sys.soil.MRValueOperationCall;
import org.tzi.use.util.soil.exceptions.compilation.CompilationFailedException;


/**
 * AST node which encapsulates a operation call statement
 * or an expression.
 * This lets the USE parser untouched.
 * The generation process tries to generate a operation call statement
 * first. If this is unsuccessful an expression is generated.
 * @author Daniel Gent
 */
public class ASTRValueExpressionOrOpCall extends ASTRValue {
	/**
	 *  The encapsulated expression
	 */
	private ASTExpression fExpressionOrOpCall;
	
	
	/**
	 * Creates a new <code>ASTRValueExpressionOrOpCall</code>.
	 * @param expressionOrOpCall The encapsulated expression
	 */
	public ASTRValueExpressionOrOpCall(ASTExpression expressionOrOpCall) {
		fExpressionOrOpCall = expressionOrOpCall;
	}
	
	
	public ASTExpression getExpressionOrOpCall() {
		return fExpressionOrOpCall;
	}

	
	@Override
	protected MRValue generate() throws CompilationFailedException {
		
		try {
			ASTOperationCallStatement opCall = 
				new ASTOperationCallStatement(fExpressionOrOpCall);
			
			opCall.setSourcePosition(fExpressionOrOpCall.getStartToken());
			
			MOperationCallStatement operationCallStatement = 
				(MOperationCallStatement)fParent.generateStatement(opCall);
			
			return new MRValueOperationCall(operationCallStatement);
			
		} catch (CompilationFailedException e) {
			// Not a soil operation call
			//FIXME: Very bad style to use exceptions for conditional flows!
		}
		
		// the expression is not a soil operation call
		return new MRValueExpression(
				fParent.generateExpression(fExpressionOrOpCall));
	}

	@Override
	public String toString() {
		return fExpressionOrOpCall.getStringRep();
	}
}
