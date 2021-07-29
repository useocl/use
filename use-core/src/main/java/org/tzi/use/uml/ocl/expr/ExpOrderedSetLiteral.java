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

// $Id: ExpSequenceLiteral.java 61 2008-04-11 11:52:15Z opti $

package org.tzi.use.uml.ocl.expr;

import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.OrderedSetValue;
import org.tzi.use.uml.ocl.value.Value;

/**
 * Constant sequence literal.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Lars Hamann
 */
public final class ExpOrderedSetLiteral extends ExpCollectionLiteral {

    public ExpOrderedSetLiteral(Expression[] elemExpr) 
        throws ExpInvalidException
    {
        super("OrderedSet", elemExpr);
        setResultType(TypeFactory.mkOrderedSet(inferElementType()));
    }

    /**
     * Evaluates expression and returns result value.
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = 
            new OrderedSetValue(((CollectionType) type()).elemType(), evalArgs(ctx));
        ctx.exit(this, res);
        return res;
    }

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitOrderedSetLiteral(this);
	}
}

