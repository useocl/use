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
 * Inverse version of an variable assignment statement.
 * 
 * @author Daniel Gent
 *
 */
public class MVariableDestructionStatement extends MStatement {
	/** Name of the assigned variable */
	private String fVariableName;
	
	/**
	 * Constructs a new variable destruction statement.
     * 
	 * @param variableName The name of the variable to destroy.
	 */
	public MVariableDestructionStatement(String variableName) {
		fVariableName = variableName;
	}
	
	/**
	 * @return the fVariableName
	 */
	public String getVariableName() {
		return fVariableName;
	}

	@Override
    public Value execute(SoilEvaluationContext context, StatementEvaluationResult result)
            throws EvaluationFailedException {
		
        Value oldValue = context.getVarEnv().lookUp(fVariableName);
		if (oldValue != null) {
            result.getInverseStatement().prependStatement(
					new MVariableAssignmentStatement(fVariableName, oldValue));
		}
		
        context.getVarEnv().remove(fVariableName);
        
        return null;
	}
	
	@Override
	protected String shellCommand() {
		return "<variable destruction>";
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
