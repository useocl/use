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

import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;


/**
 * "Compiled" version of an variable assignment statement. 
 * @author Daniel Gent
 * @author Lars Hamann
 */
public class MVariableAssignmentStatement extends MStatement {
	/** The name of the variable to assign a value to */
	private String fVariableName;
	/** The RValue to assign */
	private MRValue fRValue;
	
	/**
	 * Constructs a new variable assignment statement.
	 * @param variableName The name of the variable to assign a value to.
	 * @param value The RValue to assign.
	 */
	public MVariableAssignmentStatement(
			String variableName, 
			MRValue rValue) {
		
		fVariableName = variableName;
		fRValue = rValue;
	}
	
	/**
	 * Constructs a new variable assignment statement.
	 * @param variableName The name of the variable to assign a value to.
	 * @param value The RValue to assign.
	 */
	public MVariableAssignmentStatement(
			String variableName, 
			Value value) {
		
		this(variableName, new MRValueExpression(value));
	}
	
	/**
	 * @return the fVariableName
	 */
	public String getVariableName() {
		return fVariableName;
	}

	/**
	 * Returns the value that is assigned.
	 * @return
	 */
	public MRValue getValue() {
		return fRValue;
	}
	
	@Override
    public Value execute(SoilEvaluationContext context,
			StatementEvaluationResult result) throws EvaluationFailedException {
		
		Value value = EvalUtil.evaluateRValue(context, result, fRValue, false);
		
		context.getSystem().assignVariable(result, fVariableName, value);
		
		return null;
	}
	
	@Override
	protected String shellCommand() {
		return fVariableName + " := " + fRValue;
	}
	
	@Override
	public String toString() {
		return shellCommand();
	}

	@Override
	public void processWithVisitor(MStatementVisitor v) throws Exception {
		v.visit(this);
	}
}
