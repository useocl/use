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

package org.tzi.use.parser.soil.ast;

import java.io.PrintWriter;

import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.parser.ocl.ASTType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.uml.sys.soil.MVariableAssignmentStatement;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;


/**
 * AST node of a variable assignment statement. 
 * @author Daniel Gent
 *
 */
public class ASTVariableAssignmentStatement extends ASTStatement {
	
	private String fVariableName;
	
	private ASTType fMandatoryType;
	
	private ASTRValue fRValue;
	
	
	/**
	 * TODO
	 * @param variableName
	 * @param value
	 * @param mandatoryType
	 */
	public ASTVariableAssignmentStatement(
			String variableName, 
			ASTRValue value,
			ASTType mandatoryType) {
		
		fVariableName = variableName;
		fRValue = value;
		fMandatoryType = mandatoryType;
	}


	/**
	 * TODO
	 * @param variableName
	 * @param value
	 */
	public ASTVariableAssignmentStatement(
			String variableName, 
			ASTRValue value) {
		
		this(variableName, value, null);
	}
	
	
	/**
	 * TODO
	 * @param variableName
	 * @param expression
	 * @param mandatoryType
	 */
	public ASTVariableAssignmentStatement(
			String variableName,
			ASTExpression expression,
			ASTType mandatoryType) {
		
		this(
				variableName, 
				new ASTRValueExpressionOrOpCall(expression), 
				mandatoryType);
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
	public ASTType getMandatoryType() {
		return fMandatoryType;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public ASTRValue getRValue() {
		return fRValue;
	}
	
	
	@Override
	protected MStatement generateStatement() throws CompilationFailedException {
			
		MRValue rValue = generateRValue(fRValue);
		Type valueType = rValue.getType();
		
		if (valueType == null) {
			throw new CompilationFailedException(this, StringUtil.inQuotes(fRValue)
					+ " is not a valid rvalue, since the called "
					+ "operation does not return a value");
		}
		
		Type variableType;
		if (fMandatoryType != null) {
			Type mandatoryType = generateType(fMandatoryType);
			
			if (!valueType.isSubtypeOf(mandatoryType)) {
				throw new CompilationFailedException(this,
						"Type of expression does not match declaration. Expected "
								+ StringUtil.inQuotes(mandatoryType)
								+ ", found " + StringUtil.inQuotes(valueType)
								+ ".");
			}
			variableType = mandatoryType;
		} else {
			variableType = valueType;
		}
		
		if (fSymtable.isExplicit()) {
			if (!fSymtable.contains(fVariableName))
				throw new CompilationFailedException(this, "Variable " + fVariableName + " was not declared.");
			Type t = fSymtable.getType(fVariableName);
			if (! variableType.isSubtypeOf(t)) 
				throw new CompilationFailedException(this, "Variable " + fVariableName + " of type " + t + ", which is incompatible with " + variableType);
		} else {
			fBoundSet.add(fVariableName, variableType);
			fAssignedSet.add(fVariableName, variableType);
			fSymtable.setType(fVariableName, variableType);
		}
		
		return new MVariableAssignmentStatement(fVariableName, rValue);
	}

	
	@Override
	protected void printTree(StringBuilder prelude, PrintWriter target) {
		target.println(prelude + "[VARIABLE ASSIGNMENT]");
	}


	@Override
	public String toString() {
		return fVariableName + ":=" + fRValue;
	}
}
