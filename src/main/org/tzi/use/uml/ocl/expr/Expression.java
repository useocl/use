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

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.BufferedToString;

/**
 * Abstract base class of all expressions.
 * @author Mark Richters
 */
public abstract class Expression implements BufferedToString {
	private SrcPos fSourcePosition;
	
	/** result type of the expression */
	private Type fType;

    /** marked "@pre"? */
    private boolean fIsPre = false;

    /** If this attribute is <code>null</code> the child expressions have not
     *  been asked if they require a pre state.
     *  <p>Otherwise, the result is stored in this Integer (lazy initialization).</p>
     */
    private Boolean preStateRequired = null;
    
    protected Expression(Type t) {
        fType = t;
    }
    
    /**
     * Sets the source position of the expression.
     * @param expression
     */
    public void setSourcePosition(SrcPos position) {
    	fSourcePosition = position;
    }
    
    /**
     * The source position (normally the starting position) of this expression.
     * <p>Can be <code>null</code>.</p> 
     * @return
     */
    public SrcPos getSourcePosition() {
    	return fSourcePosition;
    }

    
    /**
     * Returns the result type of the expression.
     */
    public Type type() {
        return fType;
    }

    /**
     * Evaluates the expression and returns a result value.
     */
    public abstract Value eval(EvalContext ctx);

    /**
     * Returns true if this expression has been marked "@pre".
     */
    public boolean isPre() {
        return fIsPre;
    }

    /**
     * Mark this expression with "@pre".
     */
    public void setIsPre() {
        fIsPre = true;
    }

    /**
     * Set value of "@pre".
     */
    public void setIsPre(boolean isPre) {
        fIsPre = isPre;
    }
    
    /**
     * Returns the string "@pre" if this expression has the
     * 
     * @pre modifier, otherwise returns "".
     */
    protected String atPre() {
        return fIsPre ? "@pre" : "";
    }
    
    
    /**
     * Returns <code>true</code> if this expression or one
     * of it child expressions contains an access to the pre state
     * of the system.
     * @return
     */
    public boolean requiresPreState() {
    	if (preStateRequired == null) {
    		boolean result = fIsPre || childExpressionRequiresPreState();
    		preStateRequired = Boolean.valueOf(result);
    	}
    	
    	return preStateRequired.booleanValue();
    }

    /**
     * Primitive operation for the template method {@link #requiresPreState()}.
     * Implementors should return the result of {@link #requiresPreState()}
     * of its sub-expressions.
     * @return
     */
    protected abstract boolean childExpressionRequiresPreState();
    
    /**
     * Every expression can print itself.
     */
    public final String toString() {
    	StringBuilder sb = new StringBuilder();
    	this.toString(sb);
    	return sb.toString();
    }

    /**
     * Every expression can print itself to a StringBuilder.
     */
    public abstract StringBuilder toString(StringBuilder sb);
    
    /**
     * Makes sure this is a boolean expression.
     * 
     * @exception ExpInvalidException
     *                not a boolean expression
     */
    public void assertBoolean() throws ExpInvalidException {
        if (!fType.conformsTo(TypeFactory.mkBoolean()))
            throw new ExpInvalidException("Boolean expression expected, "
                    + "found expression of type `" + this.toString() + "'.");
    }

    /**
     * Sets the result type explicitly. Use this in subclasses when the result
     * type is complex to determine and cannot easily be passed to the
     * superclass constructor.
     */
    protected void setResultType(Type t) {
        fType = t;
    }

    /**
     * returns the type/name of the current expression. this method is
     * overwritten by the subclasses if needed
     */
    public String name() {
        return null;
    }
    
    public abstract void processWithVisitor(ExpressionVisitor visitor);
}
