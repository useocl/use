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

package org.tzi.use.parser.soil.ast;

import java.io.PrintWriter;

import org.antlr.runtime.Token;
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
	 * Constructs a new AST node.
	 * @param start The start token of the statement.
	 * @param variableName The name of the variable to assign the value to.
	 * @param value The RValue that the variable is assigned.
	 * @param mandatoryType
	 */
	public ASTVariableAssignmentStatement(
			Token start,
			String variableName, 
			ASTRValue value,
			ASTType mandatoryType) {
		super(start);
		fVariableName = variableName;
		fRValue = value;
		fMandatoryType = mandatoryType;
	}


	/**
	 * Constructs a new AST node.
	 * @param start The start token of the statement.
	 * @param variableName The name of the variable to assign the value to.
	 * @param value The value to assign.
	 */
	public ASTVariableAssignmentStatement(
			Token start,
			String variableName, 
			ASTRValue value) {
		
		this(start, variableName, value, null);
	}
	
	
	/**
	 * Constructs a new AST node.
	 * @param start The start token of the statement.
	 * @param variableName The name of the variable to assign the value to.
	 * @param expression The expression used to get the value to assign from.
	 * @param mandatoryType The required type of the variable. The type of <code>expression</code> must conform to it.
	 */
	public ASTVariableAssignmentStatement(
			Token start,
			String variableName,
			ASTExpression expression,
			ASTType mandatoryType) {
		
		this(   start,
				variableName, 
				new ASTRValueExpressionOrOpCall(expression), 
				mandatoryType);
	}
	
	/**
	 * Returns the RValue that is assigned to the variable.
	 * @return
	 */
	public ASTRValue getRValue() {
		return fRValue;
	}
	
	
	@Override
	protected MStatement generateStatement() throws CompilationFailedException {
			
		MRValue rValue = fRValue.generate(this);
		Type valueType = rValue.getType();
		
		if (valueType == null) {
			throw new CompilationFailedException(this, StringUtil.inQuotes(fRValue)
					+ " is not a valid rvalue, since the called "
					+ "operation does not return a value");
		}
		
		Type variableType;
		if (fMandatoryType != null) {
			Type mandatoryType = generateType(fMandatoryType);
			
			if (!valueType.conformsTo(mandatoryType)) {
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
			if (! variableType.conformsTo(t)) 
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
