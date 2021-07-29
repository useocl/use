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

// $Id$

package org.tzi.use.uml.ocl.expr;

import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;

/** 
 * OCL any expression.
 *  
 * @author Mark Richters
 * @author Lars Hamann
 * 
 * @since OCL 1.4
 */
public class ExpAny extends ExpQuery {
    
    /**
     * Constructs an any expression. <code>elemVarDecl</code> may be null.
     */
    public ExpAny(VarDecl elemVarDecl,
                  Expression rangeExp, 
                  Expression queryExp) 
        throws ExpInvalidException
    {
        // result is type of range elements
        super(((CollectionType) rangeExp.type()).elemType(),
              ( elemVarDecl != null ) ? 
              new VarDeclList(elemVarDecl) : new VarDeclList(true),
              rangeExp, queryExp);
    
        // queryExp must be a boolean expression
        assertBooleanQuery();
    }

    /** 
     * Return name of query expression.
     */
    public String name() {
        return "any";
    }

    /**
     * Evaluates expression and returns result value.
     */
    public Value eval(EvalContext ctx) {
        Value res = UndefinedValue.instance;
        ctx.enter(this);
        
        // evaluate range
        Value v = fRangeExp.eval(ctx);
        if (v.isUndefined())
            return UndefinedValue.instance;
        
        CollectionValue rangeVal = (CollectionValue) v;

        if (!fElemVarDecls.isEmpty())
            ctx.pushVarBinding(fElemVarDecls.varDecl(0).name(), null);
        
        // loop over range elements
        for (Value elemVal : rangeVal) {

            // bind element variable to range element, if variable was
            // declared
            if (!fElemVarDecls.isEmpty())
                ctx.varBindings().setPeekValue(elemVal);

            // evaluate select expression
            Value queryVal = fQueryExp.eval(ctx);

            // undefined query values default to false
            if (queryVal.isUndefined())
                queryVal = BooleanValue.FALSE;

            if (((BooleanValue) queryVal).value()) {
                res = elemVal;
                break;
            }
        }

        if (!fElemVarDecls.isEmpty())
            ctx.popVarBinding();
        
        ctx.exit(this, res);
        return res;
    }

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitAny(this);
	}
}

