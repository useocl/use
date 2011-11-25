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

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.expr.ExpConstString;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class MNewObjectStatement extends MStatement {
	/** TODO */
	private MClass fObjectClass;
	/** TODO */
	private Expression fObjectName;
	/** TODO */
	private MObject fCreatedObject;
	
	
	/**
	 * TODO
	 * @param objectClass
	 * @param objectName
	 */
	public MNewObjectStatement(
			MClass objectClass, 
			Expression objectName) {
		
		fObjectClass = objectClass;
		fObjectName = objectName;
	}
	
	
	/**
	 * TODO
	 * @param objectClass
	 * @param objectName
	 */
	public MNewObjectStatement(
			MClass objectClass,
			String objectName) {
		
		fObjectClass = objectClass;
		if (objectName != null) {
			fObjectName = new ExpConstString(objectName);
		}
	}
	
	
	/**
	 * TODO
	 * @param objectClass
	 */
	public MNewObjectStatement(
			MClass objectClass) {
		
		fObjectClass = objectClass;
		fObjectName = null;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public MClass getObjectClass() {
		return fObjectClass;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public Type getObjectType() {
		return fObjectClass.type();
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public Expression getObjectName() {
		return fObjectName;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public MObject getCreatedObject() {
		return fCreatedObject;
	}


	@Override
	protected void evaluate() throws EvaluationFailedException {
		
		String objectName;
		if (fObjectName == null) {
			objectName = fState.uniqueObjectNameForClass(fObjectClass);
		} else {
			objectName = evaluateString(fObjectName);
		}
						
		// create new object
		fCreatedObject = 
			createObject(fObjectClass, objectName);
	}

	
	@Override
	protected String shellCommand() {
		return 
		"new " + 
		fObjectClass.name() + 
		(fObjectName != null ? ("(" + fObjectName + ")") : "");
	}


	@Override
	public boolean hasSideEffects() {
		return true;
	}


	@Override
	public String toString() {
		return shellCommand();
	}
}
