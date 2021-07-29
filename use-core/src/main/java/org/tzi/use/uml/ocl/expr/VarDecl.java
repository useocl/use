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

import org.antlr.runtime.Token;
import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.ocl.type.Type;

/** 
 * A Variable declaration associates a variable name with a type.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class VarDecl {
    private String fVar;
    private Type fType;
    private SrcPos sourcePosition = null;

	public VarDecl(Token v, Type t) {
        fVar = v.getText();
        fType = t;
        if (t == null )
            throw new NullPointerException("type must not be null");
        
        sourcePosition = new SrcPos(v);
    }

    
    public VarDecl(String v, Type t) {
        fVar = v;
        fType = t;
        if (t == null )
            throw new NullPointerException("type must not be null");
    }

    /**
     * Returns the variable name in a declaration.
     */
    public String name() {
        return fVar;
    }

    /**
     * Returns the type of this declaration.
     */
    public Type type() {
        return fType;
    }

    /**
	 * @return the sourcePosition
	 */
	public SrcPos getSourcePosition() {
		return sourcePosition;
	}
	
    public int hashCode() {
        return fVar.hashCode();
    }

    /**
     * Two declarations are considered equal if they have the same
     * variable name. This makes it easier to find colliding
     * declarations.
     */
    public boolean equals(Object obj) {
        if (obj == this )
            return true;
        else if (obj instanceof VarDecl )
            return ((VarDecl) obj).fVar.equals(fVar);
        return false;
    }

    /**
     * Returns the name and the type of the declaration.<br/>
     * E. g. <code>foo : String</code>
     */
    @Override
    public String toString() {
        return fVar + " : " + fType;
    }


	/**
	 * @param generateHTMLExpressionVisitor
	 */
	public void processWithVisitor(ExpressionVisitor v) {
		v.visitVarDecl(this);
	}
}

