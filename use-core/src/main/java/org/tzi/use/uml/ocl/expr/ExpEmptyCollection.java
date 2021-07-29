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
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.Value;

/**
 * Expression for creating an empty collection.
 * (USE specific extension, only for backward compatibility)
 * @author  Mark Richters
 */
public final class ExpEmptyCollection extends Expression {
    
    public ExpEmptyCollection(Type collType)
        throws ExpInvalidException
    {
        // result type is the specified collection type
        super(collType);

        if (!collType.isInstantiableCollection())
            throw new ExpInvalidException(
                                          "Expected set, bag, ordered set, or sequence type, found `" + 
                                          collType + "'.");
    }

    /**
     * Evaluates expression and returns result value. 
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = null;
        CollectionType t = (CollectionType) type();
        
        res = t.createCollectionValue(new Value[0]);
        		
        ctx.exit(this, res);
        return res;
    }

    @Override
    public String name() {
    	return "oclEmpty";
    }
    
    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append(name()).append("(");
        type().toString(sb);
        return sb.append(")");
    }

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitEmptyCollection(this);
	}

	@Override
	protected boolean childExpressionRequiresPreState() {
		return false;
	}
}
