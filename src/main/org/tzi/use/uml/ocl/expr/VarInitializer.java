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

/** 
 * A Variable declaration associates a variable name with a type.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class VarInitializer {
    protected VarDecl fVarDecl;
    protected Expression fInitExpr;

    public VarInitializer(String v, Type t, Expression initExpr) 
        throws ExpInvalidException
    {
        fVarDecl = new VarDecl(v, t);
        fInitExpr = initExpr;
        if (! initExpr.type().conformsTo(t) )
            throw new ExpInvalidException(
                                          "Type mismatch. Initialization expression has type `" + 
                                          initExpr.type() + "', expected type `" + t + "'.");
    }

    public String name() {
        return fVarDecl.name();
    }

    public Type type() {
        return fVarDecl.type();
    }

    public Expression initExpr() {
        return fInitExpr;
    }

    public String toString() {
        return this.toString(new StringBuilder()).toString();
    }
    
    public StringBuilder toString(StringBuilder sb) {
    	sb.append(fVarDecl);
    	sb.append(" = ");
    	fInitExpr.toString(sb);
    	
    	return sb;
    }

	public VarDecl getVarDecl() {
		return this.fVarDecl;		
	}
}

