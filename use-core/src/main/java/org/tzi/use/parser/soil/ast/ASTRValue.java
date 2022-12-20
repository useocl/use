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

import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;


/**
 * Abstract base class for all RValue AST-nodes.
 * @author Daniel Gent
 * @author Lars Hamann
 */
public abstract class ASTRValue {
	/** The AST node of the parent statement */
	protected ASTStatement fParent;	

	/**
	 * Generates the {@link MRValue}.
	 * @return
	 * @throws CompilationFailedException 
	 */
	public MRValue generate(ASTStatement parent) throws CompilationFailedException {
		fParent = parent;
		return generate();
	}
	
	/**
	 * Called by template method {link {@link #generate(ASTStatement)} to
	 * generate the RValue.
	 * @return
	 * @throws CompilationFailedException 
	 */
	protected abstract MRValue generate() throws CompilationFailedException;
		
	
	@Override
	public abstract String toString();
}
