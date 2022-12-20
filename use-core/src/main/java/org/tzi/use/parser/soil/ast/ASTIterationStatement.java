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
import org.tzi.use.parser.soil.SoilLexer;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.sys.soil.MEmptyStatement;
import org.tzi.use.uml.sys.soil.MIterationStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;


/**
 * AST node of an iteration statement.
 * @author Daniel Gent
 *
 */
public class ASTIterationStatement extends ASTStatement {
	
	private String fIterVarName;
	
	private ASTExpression fRange;
	
	private ASTStatement fBody;
	
	
	/**
	 * Constructs a new AST node.
	 * @param iterVarName
	 * @param range
	 * @param body
	 */
	public ASTIterationStatement(
			Token start,
			String iterVarName, 
			ASTExpression range, 
			ASTStatement body) {
		super(start);
		fIterVarName = iterVarName;
		fRange = range;
		fBody = body;
		
		int type = fRange.getStartToken().getType();
		// 44 is Bag, 48 is Set
		if(type == SoilLexer.T__44 || type == SoilLexer.T__48) {
			System.out.println(
					"Warning: Iteration over a non-ordered collection. Order of the result might not be as expected. "
					+ "(" + toString() + ")");
		}
	}
	
	
	/**
	 * Name of the iteration variable.
	 * @return
	 */
	public String getIterVarName() {
		return fIterVarName;
	}
	
	
	/**
	 * The AST of the source expression
	 * @return
	 */
	public ASTExpression getRange() {
		return fRange;
	}
	
	
	/**
	 * The AST of the body statements.
	 * @return
	 */
	public ASTStatement getBody() {
		return fBody;
	}
	
	
	@Override
	protected MStatement generateStatement() throws CompilationFailedException {
		
		Expression range = generateExpression(fRange);
		if (!range.type().isKindOfCollection(VoidHandling.INCLUDE_VOID)) {
			throw new CompilationFailedException(this, "Expression "
					+ StringUtil.inQuotes(fRange.getStringRep())
					+ " is expected to be of type "
					+ StringUtil.inQuotes("Collection") + ", found "
					+ StringUtil.inQuotes(range.type()) + ".");
		}
		
		Type iterVarType = ((CollectionType)range.type()).elemType();
		
		fSymtable.storeState();
		fSymtable.setType(fIterVarName, iterVarType);
		MStatement body = generateStatement(fBody);
		fSymtable.restoreState(this);
		
		if (fBody.assigns(fIterVarName)) {
			throw new CompilationFailedException(this, 
					"There is possible assignment to the iteration variable " +
					StringUtil.inQuotes(this.getIterVarName()) +
					". This is prohibited.");
		}
		
		// assigned(iteration) = assigned(body)
		if (!fSymtable.isExplicit()) {
			fAssignedSet.add(fBody.fAssignedSet);
			fAssignedSet.add(fIterVarName, iterVarType);
		}
		
		// range is empty!
		if (iterVarType.isTypeOfVoidType()) {
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