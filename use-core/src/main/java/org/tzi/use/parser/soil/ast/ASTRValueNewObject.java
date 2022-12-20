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

import org.antlr.runtime.Token;
import org.tzi.use.parser.ocl.ASTSimpleType;
import org.tzi.use.uml.sys.soil.MNewObjectStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.uml.sys.soil.MRValueNewObject;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;


/**
 * A AST-node for an RValue encapsulating
 * an AST for a NewObjectStatement. 
 * @author Daniel Gent
 *
 */
public class ASTRValueNewObject extends ASTRValue {
	/** The AST of the encapsulated statement */
	private ASTNewObjectStatement fNewObjectStatement;

	
	/**
	 * Constructs a new ATS node.
	 * @param newObjectStatement
	 */
	public ASTRValueNewObject(
			ASTNewObjectStatement newObjectStatement) {
		fNewObjectStatement = newObjectStatement;
	}
	
	/**
	 * Constructs a new ATS node.
	 * @param objectType
	 */
	public ASTRValueNewObject(
			Token start,
			ASTSimpleType objectType) {
		this(new ASTNewObjectStatement(start, objectType));
	}
	

	@Override
	protected MRValue generate() throws CompilationFailedException {
		
		MStatement subStatement = 
			fParent.generateStatement(fNewObjectStatement);
		
		return new MRValueNewObject((MNewObjectStatement)subStatement);
	}


	@Override
	public String toString() {
		return fNewObjectStatement.toString();
	}
}
