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

import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.ocl.expr.ExpCollectionLiteral;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.soil.MEmptyStatement;
import org.tzi.use.uml.sys.soil.MObjectDestructionStatement;
import org.tzi.use.uml.sys.soil.MSequenceStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.soil.exceptions.compilation.CompilationFailedException;
import org.tzi.use.util.soil.exceptions.compilation.NotDestroyableException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class ASTObjectDestructionStatement extends ASTStatement {
	/** TODO */
	private ASTExpression fToDelete;

	
	/**
	 * TODO
	 * @param object
	 */
	public ASTObjectDestructionStatement(ASTExpression toDelete) {
		fToDelete = toDelete;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public ASTExpression getToDelete() {
		return fToDelete;
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
				if (!element.type().isObjectType()) {
					throw new NotDestroyableException(this, fToDelete, type);
				}
			}
			
			objects = Arrays.asList(collection.getElemExpr());
			
		} else if (type.isObjectType() || 
				(type.isCollection(false) && ((CollectionType)type).elemType().isObjectType())) {
			// note: this could also be a collection, but just not literal
			// (e.g. .allInstances). since those collections must be handled
			// at evaluation time, this is done in MObjectDestructionStatement
			objects = Arrays.asList(possibleObject);
						
		} else {
			throw new NotDestroyableException(this, fToDelete, type);
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
