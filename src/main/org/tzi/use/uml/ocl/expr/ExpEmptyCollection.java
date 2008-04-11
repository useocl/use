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

$Id$

package org.tzi.use.uml.ocl.expr;

import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.BagValue;
import org.tzi.use.uml.ocl.value.SequenceValue;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.Value;

/**
 * Expression for creating an empty collection.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public final class ExpEmptyCollection extends Expression {
    
    public ExpEmptyCollection(Type collType)
        throws ExpInvalidException
    {
        // result type is the specified collection type
        super(collType);

        if (! ( collType.isSet() 
                || collType.isBag() 
                || collType.isSequence() ) )
            throw new ExpInvalidException(
                                          "Expected set, bag, or sequence type, found `" + 
                                          collType + "'.");
    }

    /**
     * Evaluates expression and returns result value. 
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = null;
        Type t = type();
        Type elemType = ((CollectionType) t).elemType();
        if (t.isSet() )
            res = new SetValue(elemType);
        else if (t.isBag() )
            res = new BagValue(elemType);
        else if (t.isSequence() )
            res = new SequenceValue(elemType);
        else
            throw new RuntimeException("Unexpected type `" + t + "'.");
        ctx.exit(this, res);
        return res;
    }

    public String toString() {
        return "oclEmpty(" + type() + ")";
    }
}
