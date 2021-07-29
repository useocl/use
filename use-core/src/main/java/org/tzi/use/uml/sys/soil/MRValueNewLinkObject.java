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

package org.tzi.use.uml.sys.soil;

import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;


/**
 * A RValue which creates a new link object.
 */
public class MRValueNewLinkObject extends MRValue {
	/** The encapsulated new link object statement. */
	private MNewLinkObjectStatement fNewLinkObjectStatement;
	
	
	/**
	 * Constructs a new RValue to create a new link object.  
	 * @param newLinkObjectStatement The encapsulated new link object statement.
	 */
	public MRValueNewLinkObject(
			MNewLinkObjectStatement newLinkObjectStatement) {
		
		fNewLinkObjectStatement = newLinkObjectStatement;
	}

	@Override
	public Value evaluate(
			SoilEvaluationContext context,
			StatementEvaluationResult result) throws EvaluationFailedException {
		
		fNewLinkObjectStatement.execute(context, result);
		
		return fNewLinkObjectStatement.getCreatedLinkObject().value();
	}

	/**
	 * @return the fNewLinkObjectStatement
	 */
	public MNewLinkObjectStatement getNewLinkObjectStatement() {
		return fNewLinkObjectStatement;
	}

	@Override
	public Type getType() {
		return fNewLinkObjectStatement.getAssociationClass();
	}


	@Override
	public String toString() {
		return fNewLinkObjectStatement.shellCommand();
	}
}
