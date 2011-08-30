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

import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;

/** 
 * OCL 1.4 one expression.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ExpOne extends ExpQuery {
    
    /**
     * Constructs a one expression. <code>elemVarDecl</code> may be null.
     */
    public ExpOne(VarDecl elemVarDecl,
                  Expression rangeExp, 
                  Expression queryExp) 
        throws ExpInvalidException
    {
        // result is of boolean type
        super(TypeFactory.mkBoolean(),
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
        return "one";
    }

    /**
     * Evaluates expression and returns result value.
     */
    public Value eval(EvalContext ctx) {
        Value res;
        ctx.enter(this);
        Value v = evalSelectOrReject(ctx, true);
        if (v.isUndefined() )
            res = UndefinedValue.instance;
        else {
            CollectionValue coll = (CollectionValue) evalSelectOrReject(ctx, true);
            res = BooleanValue.get(coll.size() == 1);
        }
        ctx.exit(this, res);
        return res;
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.Expression#processWithVisitor(org.tzi.use.uml.ocl.expr.ExpressionVisitor)
	 */
	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitOne(this);
	}
}

