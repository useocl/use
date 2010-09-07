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
import org.tzi.use.util.soil.exceptions.compilation.CompilationFailedException;
import org.tzi.use.util.soil.exceptions.compilation.InvalidRValueException;
import org.tzi.use.util.soil.exceptions.compilation.RValueTypeMismatchException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class ASTVariableAssignmentStatement extends ASTStatement {
	/** TODO */
	private String fVariableName;
	/** TODO */
	private ASTType fMandatoryType;
	/** TODO */
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
			throw new InvalidRValueException(this, fRValue);
		}
		
		Type variableType;
		if (fMandatoryType != null) {
			Type mandatoryType = generateType(fMandatoryType);
			
			if (!valueType.isSubtypeOf(mandatoryType)) {
				throw new RValueTypeMismatchException(
						this, 
						fVariableName, 
						rValue, 
						mandatoryType, 
						valueType);
			}
			variableType = mandatoryType;
		} else {
			variableType = valueType;
		}
		
		fBoundSet.add(fVariableName, variableType);
		fAssignedSet.add(fVariableName, variableType);
		fSymtable.setType(fVariableName, variableType);
		
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
