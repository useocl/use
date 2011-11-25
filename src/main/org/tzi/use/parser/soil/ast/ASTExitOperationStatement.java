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
import org.tzi.use.uml.sys.soil.MExitOperationStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class ASTExitOperationStatement extends ASTStatement {
	/** TODO */
	private ASTExpression fResult;

	
	/**
	 * TODO
	 * @param result
	 */
	public ASTExitOperationStatement(ASTExpression result) {
		fResult = result;
	}
	
	
	@Override
	protected MStatement generateStatement() throws CompilationFailedException {
		Expression result = fResult == null ? null : generateExpression(fResult);

		return new MExitOperationStatement(result);
	}
	

	@Override
	protected void printTree(StringBuilder prelude, PrintWriter target) {
		target.println(prelude + "EXIT OPERATION");
	}
		
	
	@Override
	public String toString() {
		return "opexit " + (fResult == null ? "" : fResult.getStringRep());
	}
}
