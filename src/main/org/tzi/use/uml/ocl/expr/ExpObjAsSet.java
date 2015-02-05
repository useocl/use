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

import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.Value;

/**
 * Internal operation mapping a single object (resulting from
 * navigation over associations with multiplicity zero or one) to a
 * singleton or empty set.
 *
 * @author      Mark Richters 
 */
public final class ExpObjAsSet extends Expression {
    private Expression fObjExp;
    private Type fElemType;

    public ExpObjAsSet(Expression objExp) {
        super(TypeFactory.mkSet(objExp.type()));
        fObjExp = objExp;
        fElemType = objExp.type();
    }

    /**
     * Returns the left side of the asSet operation.
     * @return
     */
    public Expression getObjectExpression() {
    	return fObjExp;
    }
    
    /**
     * Evaluates expression and returns result value. 
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);

        SetValue res;
        Value val = fObjExp.eval(ctx);
        if (val.isUndefined() ) {
            // return empty set
            res = new SetValue(fElemType);
        } else {
            // return singleton
            res = new SetValue(fElemType, new Value[] { val });
        }

        ctx.exit(this, res);
        return res;
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        // the operation has no explicit representation since it
        // results from a shorthand notation, e.g., a.b->isEmpty. It
        // is hidden in the "->".
        return fObjExp.toString(sb);
    }

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitObjAsSet(this);
	}

	@Override
	protected boolean childExpressionRequiresPreState() {
		return fObjExp.requiresPreState();
	}
}
