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
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.type.UniqueLeastCommonSupertypeDeterminator;
import org.tzi.use.uml.ocl.value.SequenceValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.StringUtil;

/**
 * Abstract base class for collection literals.
 * 
 * @author  Mark Richters
 */
public abstract class ExpCollectionLiteral extends Expression {
    
	private final String fKind;

	protected final Expression[] fElemExpr;

    protected ExpCollectionLiteral(String kind, Expression[] elemExpr) {
        super(null);
        fKind = kind;
        fElemExpr = elemExpr;
    }

    /**
	 * @return the fKind
	 */
	public String getKind() {
		return fKind;
	}
	
    /**
     * Returns the value for the type parameter of this collection.
     */
    protected Type inferElementType() 
        throws ExpInvalidException
    {
    	if (this.fElemExpr.length == 0)
    		return TypeFactory.mkVoidType();
    	else if (this.fElemExpr.length == 1)
    		return this.fElemExpr[0].type();
    	
    	Set<Type> types = new HashSet<Type>();
        for (int i = 0; i < fElemExpr.length; i++) {
			Type t = fElemExpr[i].type();
			types.add(t);
        }
			
    	Type result = new UniqueLeastCommonSupertypeDeterminator().calculateFor(types); 
    	
    	if (result == null) 
    		throw new ExpInvalidException("No common supertype of the element types");
    	
    	return result;
    }

	@Override
	protected boolean childExpressionRequiresPreState() {
		for (Expression elementExpr : fElemExpr) {
			if (elementExpr.requiresPreState())
				return true;
		}
		
		return false;
	}

	/**
     * Evaluates argument expressions.
     */
    protected Value[] evalArgs(EvalContext ctx) {
    	
        List<Value> argValues = new LinkedList<Value>();
        
        for (Expression exp : fElemExpr) {
        	Value eValue = exp.eval(ctx);
            if (exp instanceof ExpRange) {
            	if (eValue.isUndefined()) {
            		argValues.add(eValue);
            	} else {
            		SequenceValue sVal = (SequenceValue)eValue;
            		for (Value v : sVal.collection()) {
            			argValues.add(v);
            		}
            	}
            } else { 
            	argValues.add(eValue);
            }
        }
        
        return argValues.toArray(new Value[argValues.size()]);
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append(fKind)
          .append(" {");
        StringUtil.fmtSeq(sb, fElemExpr, ",");
        return sb.append("}");
    }

    public Expression[] getElemExpr() {
        return fElemExpr;
    }
}

