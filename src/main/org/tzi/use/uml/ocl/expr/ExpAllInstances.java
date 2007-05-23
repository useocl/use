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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.uml.ocl.expr;

import java.util.Iterator;
import java.util.Set;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;

/**
 * Type.allInstances
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public final class ExpAllInstances extends Expression {
    private Type fSourceType;
    
    public ExpAllInstances(Type sourceType)
        throws ExpInvalidException
    {
        // result type is a set of sourceType
        super(TypeFactory.mkSet(sourceType));
        fSourceType = sourceType;

        if (! sourceType.isObjectType() )
            throw new ExpInvalidException(
                                          "Expected an object type, found `" + sourceType + "'.");
    }

    /**
     * Evaluates expression and returns result value. 
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        MSystemState systemState = isPre() ? ctx.preState() : ctx.postState();

        //      ObjectType ot = (ObjectType) fSourceType;
        //      System.err.println("*** allSupertypes: " + ot.allSupertypes());

        // the result set will contain all instances of the specified
        // class plus all instances of subclasses

        // get set of objects 
        MClass cls = ((ObjectType) fSourceType).cls();
        Set objSet = systemState.objectsOfClassAndSubClasses(cls);
        Value[] objValues = new Value[objSet.size()];
        Iterator objects = objSet.iterator();
        int i = 0;
        while (objects.hasNext() ) {
            MObject obj = (MObject) objects.next();
            ObjectType t = TypeFactory.mkObjectType(obj.cls());
            objValues[i++] = new ObjectValue(t, obj);
        }

        // create result set with object references
        SetValue res = new SetValue(fSourceType, objValues);
    
        ctx.exit(this, res);
        return res;
    }

    public String toString() {
        return fSourceType + ".allInstances" + atPre();
    }
}
