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
import org.tzi.use.uml.ocl.value.Value;

/** 
 * OCL exists expression.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ExpExists extends ExpQuery {

    /**
     * Constructs an exists expression.
     */
    public ExpExists(VarDeclList elemVarDecls,
                     Expression rangeExp, 
                     Expression queryExp) 
        throws ExpInvalidException
    {
        // result is of boolean type
        super(TypeFactory.mkBoolean(), elemVarDecls, rangeExp, queryExp);
    
        // queryExp must be a boolean expression
        assertBooleanQuery();
    }

    /**
     * Constructs an exists expression with one element variable.
     */
    public ExpExists(VarDecl elemVarDecl,
                     Expression rangeExp, 
                     Expression queryExp) 
        throws ExpInvalidException
    {
        this(new VarDeclList(elemVarDecl), rangeExp, queryExp);
    }

    /** 
     * Returns name of query expression.
     */
    public String name() {
        return "exists";
    }

    /**
     * Evaluates expression and returns result value.
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = evalExistsOrForAll(ctx, true);
        ctx.exit(this, res);
        return res;
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.Expression#processWithVisitor(org.tzi.use.uml.ocl.expr.ExpressionVisitor)
	 */
	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitExists(this);
	}
}

