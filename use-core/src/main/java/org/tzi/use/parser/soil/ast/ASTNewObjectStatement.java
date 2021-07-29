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

import static org.tzi.use.util.StringUtil.inQuotes;

import java.io.PrintWriter;

import org.antlr.runtime.Token;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.parser.ocl.ASTSimpleType;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.soil.MNewObjectStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;


/**
 * AST class for a new object statement
 * @author Daniel Gent
 * @author Lars Hamann
 */
public class ASTNewObjectStatement extends ASTStatement {
	/** AST node for the object class */
	private ASTSimpleType fObjectType;
	/** Expression to calculate the new object name from. Can be <code>null</code>. */
	private ASTExpression fObjectName;
	
	
	/**
	 * Constructs a new ASTNewObjectStatement node using the provided information.
	 * @param objectType AST node which leads to an object type.
	 * @param objectName AST node for an expression used to calculate the new object name. Can be <code>null</code>.
	 */
	public ASTNewObjectStatement(
			Token start,
			ASTSimpleType objectType, 
			ASTExpression objectName) {
		super(start);
		fObjectType = objectType;
		fObjectName = objectName;
	}
	
	/**
	 * Constructs a new ASTNewObjectStatement node using the provided information.
	 * <p>The new object name/id is set by the system.</p>
	 * @param objectType AST node which leads to an object type.
	 */
	public ASTNewObjectStatement(
			Token start,
			ASTSimpleType objectType) {
		super(start);
		fObjectType = objectType;
		fObjectName = null;
	}

	@Override
	protected MStatement generateStatement() throws CompilationFailedException {
	
		Type t = generateType(fObjectType);
		
		if (!t.isTypeOfClass()) {
			throw new CompilationFailedException(this, "Expected object type, found "
					+ StringUtil.inQuotes(t) + ".");
		}
		
		MClass objectClass = (MClass)t;
		
		if (objectClass instanceof MAssociationClass ) {
			throw new CompilationFailedException(this,
					"Cannot instantiate association class "
							+ inQuotes(objectClass.name())
							+ " without participants.");
		}
		
		Expression objectName = 
			(fObjectName == null ? 
					null : generateStringExpression(fObjectName));
		
		return new MNewObjectStatement(objectClass, objectName);
	}


	@Override
	protected void printTree(StringBuilder prelude, PrintWriter target) {
		target.println(prelude + "[OBJECT CREATION]");
	}
	
	
	@Override
	public String toString() {
		return 
		"new " + 
		fObjectType + 
		(fObjectName == null ? "" : " " + fObjectName.getStringRep());
	}
}
