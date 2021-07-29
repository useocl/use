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

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;

/**
 * "Compiled" version of an attribute assignment.
 *
 */
public class MAttributeAssignmentStatement extends MStatement {
	/** The expression leading to an object to assign the new value to. */
	private Expression fObject;
	/** The attribute to assign the value to */
	private MAttribute fAttribute;
	/** The value to assign */
	private MRValue fRValue;
	
	/**
	 * Constructs a new attribute assignment statement. 
     * 
	 * @param object The expression leading to an object to assign the new value to.
	 * @param attribute The attribute to assign the value to
	 * @param rValue The value to assign
	 */
    public MAttributeAssignmentStatement(Expression object, MAttribute attribute, MRValue rValue) {
		fObject = object;
		fAttribute = attribute;
		fRValue = rValue;
	}
	
    /**
	 * Constructs a new attribute assignment statement. 
     * 
	 * @param object The expression leading to an object to assign the new value to.
	 * @param attribute The attribute to assign the value to
	 * @param value The expression leading to the value to assign
	 */
    public MAttributeAssignmentStatement(Expression object, MAttribute attribute, Expression value) {
		
		this(object, attribute, new MRValueExpression(value));
	}
	
    /**
	 * Constructs a new attribute assignment statement. 
     * 
	 * @param object The object to assign the new value to.
	 * @param attribute The attribute to assign the value to
	 * @param rValue The expression leading to the value to assign
	 */
    public MAttributeAssignmentStatement(MObject object, MAttribute attribute, Expression value) {
		
        this(new ExpressionWithValue(object.value()), attribute, value);
	}
	
    /**
	 * Constructs a new attribute assignment statement. 
     * 
	 * @param object The object to assign the new value to.
	 * @param attribute The attribute to assign the value to
	 * @param rValue The value to assign
	 */
    public MAttributeAssignmentStatement(MObject object, MAttribute attribute, Value value) {
		
        this(new ExpressionWithValue(object.value()), attribute, new ExpressionWithValue(value));
	}
		
	/**
	 * @return the fObject
	 */
	public Expression getObject() {
		return fObject;
	}

	/**
	 * @return the fAttribute
	 */
	public MAttribute getAttribute() {
		return fAttribute;
	}

	/**
	 * @return the fRValue
	 */
	public MRValue getRValue() {
		return fRValue;
	}

	@Override
    public Value execute(SoilEvaluationContext context, StatementEvaluationResult result)
            throws EvaluationFailedException {
		
		// get the actual object
        MObject object = EvalUtil.evaluateObjectExpression(context, fObject);
		
		// get the new value
        Value newValue = EvalUtil.evaluateRValue(context, result, fRValue, false);
		
        try {
            context.getSystem().assignAttribute(result, object, fAttribute, newValue);
        } catch (MSystemException e) {
            throw new EvaluationFailedException(e.getMessage());
        }
        
        return null;
    }
	
	@Override
	protected String shellCommand() {
        return fObject + "." + fAttribute.name() + " := " + fRValue;
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
