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
import org.tzi.use.parser.soil.ast.ASTStatement;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.soil.MEmptyStatement;
import org.tzi.use.uml.sys.soil.MIterationStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.soil.exceptions.compilation.AssignmentToIterVariableException;
import org.tzi.use.util.soil.exceptions.compilation.CompilationFailedException;
import org.tzi.use.util.soil.exceptions.compilation.NotACollectionException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class ASTIterationStatement extends ASTStatement {
	/** TODO */
	private String fIterVarName;
	/** TODO */
	private ASTExpression fRange;
	/** TODO */
	private ASTStatement fBody;
	
	
	/**
	 * TODO
	 * @param iterVarName
	 * @param range
	 * @param body
	 */
	public ASTIterationStatement(
			String iterVarName, 
			ASTExpression range, 
			ASTStatement body) {
		
		fIterVarName = iterVarName;
		fRange = range;
		fBody = body;
		
		addChildStatement(body);
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public String getIterVarName() {
		return fIterVarName;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public ASTExpression getRange() {
		return fRange;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public ASTStatement getBody() {
		return fBody;
	}
	
	
	@Override
	protected MStatement generateStatement() throws CompilationFailedException {
		
		Expression range = generateExpression(fRange);
		if (!range.type().isCollection(false)) {
			throw new NotACollectionException(this, fRange, range.type());
		}
		
		Type iterVarType = ((CollectionType)range.type()).elemType();
		
		fSymtable.storeState();
		fSymtable.setType(fIterVarName, iterVarType);
		MStatement body = generateStatement(fBody);
		fSymtable.restoreState(this);
		
		if (fBody.assigns(fIterVarName)) {
			throw new AssignmentToIterVariableException(this);
		}
		
		// assigned(iteration) = assigned(body)
		fAssignedSet.add(fBody.fAssignedSet);
		fAssignedSet.add(fIterVarName, iterVarType);
		
		// range is empty!
		if (iterVarType.isVoidType()) {
			return MEmptyStatement.getInstance();
		} else {
			return new MIterationStatement(fIterVarName, range, body);
		} 
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
			"for " + 
			fIterVarName + 
			" in " + 
			fRange.getStringRep() + 
			"do ... end";
	}
}