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
import org.tzi.use.uml.ocl.type.VoidType;
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
    	if (this.fElemExpr.length == 0)
    		return VoidType.Instance;
    	else if (this.fElemExpr.length == 1)
    		return this.fElemExpr[0].type();
    	
    	Type commonSuperType = this.fElemExpr[0].type();
    	Type t2;
    	
    	for (int i = 1; i < fElemExpr.length; ++i) {
    		t2 = fElemExpr[i].type();
    		commonSuperType = commonSuperType.getLeastCommonSupertype(t2);
    		
    		if (commonSuperType == null)
    			throw new ExpInvalidException("Type mismatch, " + fKind + " element " + 
                        (i + 1) +
                        " does not have a common supertype " + 
                        "with previous elements.");
    	}
        
    	return commonSuperType; 
    	
        // FIXME: deal with other cases: t1 < t, t2 < t, t1 and t2 unrelated.
        // throw new ExpInvalidException("Cannot determine type of " + fKind + ".");
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

