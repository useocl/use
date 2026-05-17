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

package org.tzi.use.uml.mm.ocl.expr;

import org.tzi.use.uml.mm.ocl.type.TypeFactory;
import org.tzi.use.uml.mm.ocl.value.IntegerValue;
import org.tzi.use.uml.mm.ocl.value.Value;

/**
 * Constant integer expression.
 *
 * @author  Mark Richters
 */
public final class ExpConstInteger extends Expression {
    private final int fValue;

    public ExpConstInteger(int n) {
        super(TypeFactory.mkInteger());
        fValue = n;
    }

    public int value() {
        return fValue;
    }

    /**
     * Evaluates expression and returns result value.
     */
    @Override
	public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = IntegerValue.valueOf(fValue);
        ctx.exit(this, res);
        return res;
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        return sb.append(fValue);
    }

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitConstInteger(this);
	}

	@Override
	protected boolean childExpressionRequiresPreState() {
		return false;
	}
}

