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

package org.tzi.use.uml.ocl.expr;

import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.UnlimitedNaturalValue;
import org.tzi.use.uml.ocl.value.Value;

/**
 * 
 * @author Lars Hamann
 *
 */
public class ExpConstUnlimitedNatural extends Expression {
	
    public ExpConstUnlimitedNatural() {
        super(TypeFactory.mkUnlimitedNatural());
    }

    /**
     * Evaluates expression and returns result value.
     */
    @Override
	public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = UnlimitedNaturalValue.UNLIMITED;
        ctx.exit(this, res);
        return res;
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        return sb.append("*");
    }

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitConstUnlimitedNatural(this);
	}

	@Override
	protected boolean childExpressionRequiresPreState() {
		return false;
	}
}
