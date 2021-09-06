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

import org.antlr.runtime.Token;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.sys.soil.MAttributeAssignmentStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;


/**
 * AST class for an attribute assignment statement.
 * @author Daniel Gent
 *
 */
public class ASTAttributeAssignmentStatement extends ASTStatement {
	
	private ASTExpression fObject;
	
	private String fAttributeName;
	
	private ASTRValue fRValue;
		
	
	/**
	 * Constructs a new AST with the given values.
	 * @param object Expression for the source object
	 * @param attributeName The name of the attribute
	 * @param value The ASt for the rValue. 
	 */
	public ASTAttributeAssignmentStatement(Token start,
			ASTExpression object,
			String attributeName,
			ASTRValue rValue) {
		super(start);
		fObject = object;
		fAttributeName = attributeName;
		fRValue = rValue;
	}
	
	
	@Override
	protected MAttributeAssignmentStatement generateStatement() 
	throws CompilationFailedException {
		
		Expression object = generateObjectExpression(fObject);
		MAttribute attribute = getAttributeSafe(object, fAttributeName);
		MRValue rValue = fRValue.generate(this);
				
		if (!rValue.getType().conformsTo(attribute.type())) {
			throw new CompilationFailedException(this,
					"Type mismatch in assignment expression. Expected type "
							+ StringUtil.inQuotes(attribute.type())
							+ ", found "
							+ StringUtil.inQuotes(rValue.getType()) + ".");
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
