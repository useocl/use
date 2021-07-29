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

import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;

/**
 * A RValue which evaluates an expression.
 * 
 * @author Daniel Gent
 *
 */
public class MRValueExpression extends MRValue {
	/** The encapsulated expression the value is derived from */
	private Expression fExpression;
	
	/**
	 * Constructs a new RValue encapsulating <code>expression</code>.
     * 
	 * @param expression The encapsulated expression the value is derived from
	 */
	public MRValueExpression(Expression expression) {
		fExpression = expression;
	}

	/**
	 * Constructs a new RValue encapsulating <code>value</code>.
     * 
	 * @param value The encapsulated value
	 */
	public MRValueExpression(Value value) {
		fExpression = new ExpressionWithValue(value);
	}
	
	/**
	 * Constructs a new RValue encapsulating <code>object</code>.
     * 
	 * @param object The encapsulated object
	 */
	public MRValueExpression(MObject object) {
		fExpression = new ExpressionWithValue(object.value());
	}

	@Override
	public Value evaluate(SoilEvaluationContext context,
			StatementEvaluationResult result) throws EvaluationFailedException {
        return EvalUtil.evaluateExpression(context, fExpression);
	}

	/**
	 * @return the fExpression
	 */
	public Expression getExpression() {
		return fExpression;
	}
	
	@Override
	public Type getType() {
		return fExpression.type();
	}

	@Override
	public String toString() {
		return fExpression.toString();
	}

}
