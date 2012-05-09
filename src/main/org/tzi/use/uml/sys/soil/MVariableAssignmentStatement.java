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
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class MVariableAssignmentStatement extends MStatement {
	/** TODO */
	private String fVariableName;
	/** TODO */
	private MRValue fRValue;

	
	/**
	 * TODO
	 * @param variableName
	 * @param value
	 */
	public MVariableAssignmentStatement(
			String variableName, 
			MRValue rValue) {
		
		fVariableName = variableName;
		fRValue = rValue;
	}
	
	
	/**
	 * TODO
	 * @param variableName
	 * @param value
	 */
	public MVariableAssignmentStatement(
			String variableName, 
			Value value) {
		
		this(variableName, new MRValueExpression(value));
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public String getVariableName() {
		return fVariableName;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public MRValue getValue() {
		return fRValue;
	}
	
	
	@Override
    public void execute(SoilEvaluationContext context,
			StatementEvaluationResult result) throws EvaluationFailedException {
		
		Value value = EvalUtil.evaluateRValue(this, context, result, fRValue, false);
		
		context.getSystem().assignVariable(result, fVariableName, value);
	}
	
	
	@Override
	protected String shellCommand() {
		return fVariableName + " := " + fRValue;
	}
	
	
	@Override
	public String toString() {
		return shellCommand();
	}
}
