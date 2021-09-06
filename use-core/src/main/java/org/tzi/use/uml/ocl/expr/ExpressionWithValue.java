/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
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

package org.tzi.use.uml.ocl.expr;

import org.tzi.use.uml.ocl.value.Value;

/**
 * This is a class needed to create an expression with
 * a given pre-defined value. This kind of expression is
 * not part of OCL, it is only needed when an operation
 * needs a parameter of kind expression instead of value.
 *
 * @author  Joern Bohling
 */
public class ExpressionWithValue extends Expression  {
    
	private final Value fValue;

    public ExpressionWithValue( Value value ) {
        super(value.type());
        fValue = value;
    }
    
    /**
	 * @return the fValue
	 */
	public Value getValue() {
		return fValue;
	}

	public Value eval(EvalContext ctx) {
        return fValue;
    }
    
    @Override
    public StringBuilder toString(StringBuilder sb) {
    	return sb.append(fValue.toString());
    }

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitWithValue(this);		
	}

	@Override
	protected boolean childExpressionRequiresPreState() {
		return false;
	}
}

