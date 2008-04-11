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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.StringUtil;

/**
 * Abstract base class for collection literals.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public abstract class ExpCollectionLiteral extends Expression {
    private String fKind;
    protected Expression[] fElemExpr;

    protected ExpCollectionLiteral(String kind, Expression[] elemExpr) {
        super(null);
        fKind = kind;
        fElemExpr = elemExpr;
    }

    /**
     * Returns the value for the type parameter of this collection.
     */
    protected Type inferElementType() 
        throws ExpInvalidException
    {
        if (fElemExpr.length == 0 )
            throw new ExpInvalidException("Cannot determine type of empty " + 
                                          fKind + ".");

        // easy case: one or more elements of equal type
        Type t0 = fElemExpr[0].type();
        boolean sameTypes = true;
        for (int i = 1; i < fElemExpr.length; i++)
            if (! t0.equals(fElemExpr[i].type()) ) {
                sameTypes = false;
                break;
            }
        if (sameTypes )
            return t0;

        // determine common supertypes = intersection of all
        // supertypes of all elements
        Set cs = new HashSet();
        cs.addAll(fElemExpr[0].type().allSupertypes());
        for (int i = 1; i < fElemExpr.length; i++) {
            cs.retainAll(fElemExpr[i].type().allSupertypes());
            // return immediately if intersection is empty
            if (cs.isEmpty() )
                throw new ExpInvalidException("Type mismatch, " + fKind + " element " + 
                                              (i + 1) +
                                              " does not have a common supertype " + 
                                              "with previous elements.");
        }
        // System.err.println("*** common supertypes: " + cs);

        // determine the least common supertype

        // if there is only one common supertype return it
        if (cs.size() == 1 ) 
            return (Type) cs.iterator().next();

        // search for a type that is less than or equal to all other types
        t0 = null;
        Iterator it1 = cs.iterator();
        outerLoop: 
        while (it1.hasNext() ) {
            Type t1 = (Type) it1.next();
            Iterator it2 = cs.iterator();
            while (it2.hasNext() ) {
                Type t2 = (Type) it2.next();
                if (! t1.isSubtypeOf(t2) )
                    continue outerLoop;
            }
            t0 = t1;
            break;
        }
        // System.err.println("*** least common supertype: " + t0);
        if (t0 != null )
            return t0;

        // FIXME: deal with other cases: t1 < t, t2 < t, t1 and t2 unrelated.
        throw new ExpInvalidException("Cannot determine type of " + fKind + ".");
    }

    /**
     * Evaluates argument expressions.
     */
    protected Value[] evalArgs(EvalContext ctx) {
        Value argValues[] = new Value[fElemExpr.length];
        for (int i = 0; i < fElemExpr.length; i++)
            argValues[i] = fElemExpr[i].eval(ctx);
        return argValues;
    }

    public String toString() {
        return fKind + " {" + StringUtil.fmtSeq(fElemExpr, ",") + "}";
    }

    public Expression[] getElemExpr() {
        return fElemExpr;
    }
}

