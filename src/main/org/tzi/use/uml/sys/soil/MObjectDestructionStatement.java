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

package org.tzi.use.uml.sys.soil;

import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.util.soil.exceptions.evaluation.EvaluationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class MObjectDestructionStatement extends MStatement {
	/** TODO */
	private Expression fToDelete;
	
	
	/**
	 * TODO
	 * @param object
	 */
	public MObjectDestructionStatement(Expression toDelete) {
		fToDelete = toDelete;
	}
	
	
	/**
	 * TODO
	 * @param object
	 */
	public MObjectDestructionStatement(Value object) {
		this(new ExpressionWithValue(object));
	}

	
	/**
	 * TODO
	 * @return
	 */
	public Expression getToDelete() {
		return fToDelete;
	}
	
	
	@Override
	protected void evaluate() throws EvaluationFailedException {
		
		// handle "dynamic" collections (e.g. .allInstances)
		if (fToDelete.type().isCollection(false)) {
			CollectionValue collection = evaluateCollection(fToDelete);
			
			for (Value object : collection.collection()) {
				MObjectDestructionStatement statement = 
					new MObjectDestructionStatement(object);
				
				statement.setIsOperationBody(isOperationBody());
				statement.setSourcePosition(getSourcePosition());
				statement.setSourceStatement(getSourceStatement());
				
				evaluateSubStatement(statement);
			}
			
		} else {
			MObject object = evaluateObjectExpression(fToDelete);
			
			destroyObject(object);
		}
	}
	
	
	@Override
	protected String shellCommand() {
		return "destroy " + fToDelete;
	}
	
	
	@Override
	public boolean hasSideEffects() {
		return true;
	}


	@Override
	public String toString() {
		return shellCommand();
	}


	/* (non-Javadoc)
	 * @see org.tzi.use.uml.sys.soil.MStatement#mayGenerateUnqiueNames()
	 */
	@Override
	public boolean mayGenerateUnqiueNames() {
		return false;
	}
}
