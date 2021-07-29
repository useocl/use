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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.ocl.expr.ExpCollectionLiteral;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.sys.soil.MEmptyStatement;
import org.tzi.use.uml.sys.soil.MObjectDestructionStatement;
import org.tzi.use.uml.sys.soil.MSequenceStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;


/**
 * AST node of an object deletion statement.
 * @author Daniel Gent
 * @author Lars Hamann
 */
public class ASTObjectDestructionStatement extends ASTStatement {
	/** An expression leading to an object or a collection of objects */
	private ASTExpression fToDelete;

	
	/**
	 * Constructs a new AST node for the deletion of an object
	 * or a collection of objects.
	 * @param toDelete AST node of an expression leading to an object or a collection of objects
	 */
	public ASTObjectDestructionStatement(Token start, ASTExpression toDelete) {
		super(start);
		fToDelete = toDelete;
	}
	
	@Override
	protected MStatement generateStatement() throws CompilationFailedException {
		
		Expression possibleObject = generateExpression(fToDelete);

		Type type = possibleObject.type();
		
		List<Expression> objects;
		
		if (possibleObject instanceof ExpCollectionLiteral) {
			ExpCollectionLiteral collection = 
				(ExpCollectionLiteral)possibleObject;
			
			// check if each element is an object
			for (Expression element : collection.getElemExpr()) {
				if (!element.type().isTypeOfClass()) {
					throw new CompilationFailedException(this, "Expected "
							+ StringUtil.inQuotes(fToDelete.getStringRep())
							+ " to be a collection of objects, found "
							+ StringUtil.inQuotes(type) + ".");
				}
			}
			
			objects = Arrays.asList(collection.getElemExpr());
			
		} else if (type.isTypeOfClass() || 
				(type.isKindOfCollection(VoidHandling.EXCLUDE_VOID) && ((CollectionType)type).elemType().isTypeOfClass())) {
			// note: this could also be a collection, but just not literal
			// (e.g. .allInstances). since those collections must be handled
			// at evaluation time, this is done in MObjectDestructionStatement
			objects = Arrays.asList(possibleObject);
						
		} else {
			throw new CompilationFailedException(this, "Expected " +
					StringUtil.inQuotes(fToDelete.getStringRep()) +
					" to be an object type, found " +
					StringUtil.inQuotes(type) + 
					".");
		}
		
		switch (objects.size()) {
		case  0: return MEmptyStatement.getInstance();
		case  1: return new MObjectDestructionStatement(objects.get(0));
		default: 
			List<MStatement> destructionStatements = 
				new ArrayList<MStatement>(objects.size());
			
			for (Expression object : objects) {
				MObjectDestructionStatement ds =
					new MObjectDestructionStatement(object);
				
				destructionStatements.add(ds);
			}
			
			return new MSequenceStatement(destructionStatements);
		}
	}


	@Override
	protected void printTree(StringBuilder prelude, PrintWriter target) {
		target.println(prelude + "[OBJECT DESTRUCTION]");
	}

	
	@Override
	public String toString() {
		return "destroy " + fToDelete.getStringRep();
	}
}
