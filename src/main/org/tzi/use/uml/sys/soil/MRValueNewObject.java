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
 *  A RValue which creates a new object.
 * 
 * @author Daniel Gent
 * @author Lars Hamann
 */
public class MRValueNewObject extends MRValue {
	/** The encapsulated new object statement. */
	private MNewObjectStatement fNewObjectStatement;
	
	/**
	 * Constructs a new RValue to create a new object.  
	 * @param newObjectStatement The encapsulated new object statement.
	 */
    public MRValueNewObject(MNewObjectStatement newObjectStatement) {
		fNewObjectStatement = newObjectStatement;
	}

	@Override
	public Value evaluate(SoilEvaluationContext context,
			StatementEvaluationResult result) throws EvaluationFailedException {
        return fNewObjectStatement.execute(context, result);
	}

	/**
	 * @return the fNewObjectStatement
	 */
	public MNewObjectStatement getNewObjectStatement() {
		return fNewObjectStatement;
	}
	
	@Override
	public Type getType() {
		return fNewObjectStatement.getObjectType();
	}

	@Override
	public String toString() {
		return fNewObjectStatement.shellCommand();
	}
}
