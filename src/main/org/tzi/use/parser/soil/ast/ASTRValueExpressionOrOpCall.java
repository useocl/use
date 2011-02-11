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
import org.tzi.use.util.soil.exceptions.compilation.NotCallableException;
import org.tzi.use.util.soil.exceptions.compilation.UndefinedOperationException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class ASTRValueExpressionOrOpCall extends ASTRValue {
	/** TODO */
	private ASTExpression fExpressionOrOpCall;
	
	
	/**
	 * TODO
	 * @param expressionOrOpCall
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
			
		} catch (NotCallableException e) {
			// obviously nothing callable...
		} catch (UndefinedOperationException e) {
			// callable, but not SOIL defined
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
