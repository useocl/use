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

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class MAttributeAssignmentStatement extends MStatement {
	/** TODO */
	private Expression fObject;
	/** TODO */
	private MAttribute fAttribute;
	/** TODO */
	private MRValue fRValue;
	
	
	/**
	 * TODO
	 * @param object
	 * @param attribute
	 * @param value
	 */
	public MAttributeAssignmentStatement(
			Expression object, 
			MAttribute attribute,
			MRValue rValue) {
		
		fObject = object;
		fAttribute = attribute;
		fRValue = rValue;
	}
	
	
	/**
	 * TODO
	 * @param object
	 * @param attribute
	 * @param value
	 */
	public MAttributeAssignmentStatement(
			Expression object,
			MAttribute attribute,
			Expression value) {
		
		this(object, attribute, new MRValueExpression(value));
	}
	
	
	/**
	 * TODO
	 * @param object
	 * @param attribute
	 * @param value
	 */
	public MAttributeAssignmentStatement(
			Expression object,
			MAttribute attribute,
			Value value) {
		
		this(object, attribute, new MRValueExpression(value));
	}
	
	
	/**
	 * TODO
	 * @param object
	 * @param attribute
	 * @param value
	 */
	public MAttributeAssignmentStatement(
			MObject object,
			MAttribute attribute,
			Expression value) {
		
		this(
				new ExpressionWithValue(object.value()), 
				attribute, 
				value);
	}
	
	
	/**
	 * TODO
	 * @param object
	 * @param attribute
	 * @param value
	 */
	public MAttributeAssignmentStatement(
			MObject object,
			MAttribute attribute,
			Value value) {
		
		this(
				new ExpressionWithValue(object.value()), 
				attribute, 
				new ExpressionWithValue(value));
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public Expression getObject() {
		return fObject;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public MAttribute getAttribute() {
		return fAttribute;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public MRValue getRValue() {
		return fRValue;
		
	}
		
	
	@Override
	protected void evaluate() throws EvaluationFailedException {
		
		// get the actual object
		MObject object = evaluateObjectExpression(fObject);
		
		// get the new value
		Value newValue = evaluateRValue(fRValue);
		
		assignAttribute(object, fAttribute, newValue);
	}
	
	
	@Override
	protected String shellCommand() {
		return 
		fObject + 
		"." + 
		fAttribute.name() + 
		" := " + 
		fRValue;
	}
	

	@Override
	public boolean hasSideEffects() {
		// no need to check expression or rvalue, changing an attribute is
		// always a "side effect"
		return true;
	}


	@Override
	public String toString() {
		return shellCommand();
	}
}
