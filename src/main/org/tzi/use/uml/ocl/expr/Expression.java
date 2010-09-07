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

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.Value;

/**
 * Abstract base class of all expressions.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Mark Richters
 */
public abstract class Expression {
	private ASTExpression fSourceExpression;
    private Type fType; // result type
    private List<Expression> fChildExpressions = new ArrayList<Expression>();

    private boolean fIsPre = false; // marked "@pre"?

    protected Expression(Type t) {
        fType = t;
    }
    
    
    protected Expression(Type t, Expression... childExpressions) {
    	this(t);
    	addChildExpressions(childExpressions);
    }
    
    
    /**
     * TODO
     * @param expressions
     */
    protected void addChildExpressions(Expression... expressions) {
    	for (Expression e : expressions) {
    		fChildExpressions.add(e);
    	}
    }
    
    
    /**
     * TODO
     * @param expression
     */
    public void setSourceExpression(ASTExpression expression) {
    	fSourceExpression = expression;
    }
    
    
    /**
     * TODO
     * @return
     */
    public ASTExpression getSourceExpression() {
    	return fSourceExpression;
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
     * TODO
     * @return
     */
    public boolean containsPre() {
    	if (fIsPre) {
    		return true;
    	} else {
    		for (Expression childExpression : fChildExpressions) {
    			if (childExpression.containsPre()) {
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }

    
    /**
     * Every expression can print itself.
     */
    public abstract String toString();

    /**
     * Makes sure this is a boolean expression.
     * 
     * @exception ExpInvalidException
     *                not a boolean expression
     */
    public void assertBoolean() throws ExpInvalidException {
        if (!fType.isSubtypeOf(TypeFactory.mkBoolean()))
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
    
    /**
     * TODO
     * @return
     */
    public boolean hasSideEffects() {
    	
    	for (Expression child : fChildExpressions) {
    		if (child.hasSideEffects()) {
    			return true;
    		}
    	}
    	
    	return false;
    }
}
