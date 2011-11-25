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

import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class MVariableDestructionStatement extends MStatement {
	/** TODO */
	private String fVariableName;
	
	
	/**
	 * TODO
	 * @param variableName
	 */
	public MVariableDestructionStatement(String variableName) {
		fVariableName = variableName;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public String getVariableName() {
		return fVariableName;
	}
	
	
	@Override
	protected void evaluate() throws EvaluationFailedException {
		
		Value oldValue = fVarEnv.lookUp(fVariableName);
		if (oldValue != null) {
			fResult.getInverseStatement().prependStatement(
					new MVariableAssignmentStatement(fVariableName, oldValue));
		}
		
		fVarEnv.remove(fVariableName);
	}
	
	
	@Override
	protected String shellCommand() {
		return "<variable destruction>";
	}
	
	
	@Override
	public boolean hasSideEffects() {
		// can't be part of a soil defined ocl operation,
		// but let's restrict it as much as possible anyways
		return true;
	}


	@Override
	public String toString() {
		return shellCommand();
	}
}
