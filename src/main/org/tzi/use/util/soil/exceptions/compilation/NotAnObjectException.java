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
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.util.StringUtil;


/**
 * This exception is raised by the SOIL-Compiler when
 * an object was expected as a source, but another
 * type is present.
 * @author Daniel Gent
 *
 */
@SuppressWarnings("serial")
public class NotAnObjectException extends CompilationFailedException {
	
	/**
	 * Constructs a new not an object exception.
	 * @param statement The <code>ASTStatement</code> that has failed to compile
	 * @param expression The <code>Expression</code> which was given as a source 
	 */
	public NotAnObjectException(
			ASTStatement statement, 
			Expression expression) {
		
		super(
				statement, 
				"Expected expression with object type, found type " + 
				StringUtil.inQuotes(expression.type()) + 
				".");
	}
}
