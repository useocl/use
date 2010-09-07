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

import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.parser.soil.ast.ASTStatement;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.util.StringUtil;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class NotACollectionException extends CompilationFailedException {
	/** TODO */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * TODO
	 * @param statement
	 * @param range
	 */
	public NotACollectionException(
			ASTStatement statement, 
			ASTExpression notACollection,
			Type foundType) {
		
		super(
			statement, 
			"Expression " + 
			StringUtil.inQuotes(notACollection.getStringRep()) + 
			" is expected to be of type " +
			StringUtil.inQuotes("Collection") +
			", found " +
			StringUtil.inQuotes(foundType) +
			".");
	}
}
