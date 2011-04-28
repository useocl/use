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
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.sys.soil.MAttributeAssignmentStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.util.soil.exceptions.compilation.AttributeTypeMismatchException;
import org.tzi.use.util.soil.exceptions.compilation.CompilationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class ASTAttributeAssignmentStatement extends ASTStatement {
	
	private ASTExpression fObject;
	
	private String fAttributeName;
	
	private ASTRValue fRValue;
		
	
	/**
	 * TODO
	 * @param object
	 * @param attributeName
	 * @param value
	 */
	public ASTAttributeAssignmentStatement(
			ASTExpression object,
			String attributeName,
			ASTRValue rValue) {
		
		fObject = object;
		fAttributeName = attributeName;
		fRValue = rValue;
	}
	
	
	@Override
	protected MAttributeAssignmentStatement generateStatement() 
	throws CompilationFailedException {
		
		Expression object = generateObjectExpression(fObject);
		MAttribute attribute = generateAttribute(object, fAttributeName);
		MRValue rValue = generateRValue(fRValue);
		
		if (!rValue.getType().isSubtypeOf(attribute.type())) {
			throw new AttributeTypeMismatchException(this, attribute, rValue);
		}
		
		return new MAttributeAssignmentStatement(object, attribute, rValue);
	}

	
	@Override
	protected void printTree(StringBuilder prelude, PrintWriter target) {
		target.println(prelude + "[ATTRIBUTE ASSIGNMENT]");
	}
	
	
	@Override
	public String toString() {
		return 
			fObject.getStringRep() +
			"." +
			fAttributeName +
			" := " +
			fRValue;
	}
}
