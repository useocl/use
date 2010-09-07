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

package org.tzi.use.util.soil.exceptions.compilation;

import org.tzi.use.parser.soil.ast.ASTStatement;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.util.StringUtil;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class IllegalAssignmentInSequenceException extends
		CompilationFailedException {

	/** TODO */
	private static final long serialVersionUID = 1L;

	
	/**
	 * TODO
	 * @param statement
	 * @param varName
	 * @param typeInS1
	 * @param typeInS2
	 * @param cause
	 */
	public IllegalAssignmentInSequenceException(
			ASTStatement statement,
			String varName,
			Type typeInS1,
			Type typeInS2,
			ASTStatement cause) {
		
		super(
			statement, 
			"Statement " +
			StringUtil.inQuotes(cause) +
			" at line " +
			cause.getSourcePosition().line() +
			", column " +
			cause.getSourcePosition().column() +
			" possibly sets variable " +
			StringUtil.inQuotes(varName) +
			" to type " +
			StringUtil.inQuotes(typeInS2) +
			". This is prohibited, since this is not a subtype " +
			"of its current type " +
			StringUtil.inQuotes(typeInS1) +
			".");
	}
}
