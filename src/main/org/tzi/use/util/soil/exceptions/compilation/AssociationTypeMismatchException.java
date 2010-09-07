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

import org.tzi.use.parser.soil.ast.ASTRValue;
import org.tzi.use.parser.soil.ast.ASTStatement;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.util.StringUtil;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class AssociationTypeMismatchException extends
		CompilationFailedException {

	/** TODO */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * TODO
	 * @param statement
	 * @param association
	 * @param participant
	 * @param wrongType
	 * @param index
	 */
	public AssociationTypeMismatchException(
			ASTStatement statement, 
			MAssociation association,
			ASTRValue participant,
			Type expectedType,
			Type foundType,
			int index) {
		
		super(
			statement, 
			"Participant " + 
			(index + 1) +
			" of association " + 
			((association instanceof MAssociationClass) ? "class " : "") +
			StringUtil.inQuotes(association.name()) +
			" must be of type " +
			StringUtil.inQuotes(expectedType) +
			", but " +
			StringUtil.inQuotes(participant) +
			" is of type " +
			StringUtil.inQuotes(foundType) +
			".");
	}
}
