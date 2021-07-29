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
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.Value;

/**
 * oclIsTypeOf
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public final class ExpIsTypeOf extends Expression {
    private Expression fSourceExpr;
    private Type fTargetType;
    
    public ExpIsTypeOf(Expression sourceExpr, Type targetType)
        throws ExpInvalidException
    {
        super(TypeFactory.mkBoolean());
        fSourceExpr = sourceExpr;
        fTargetType = targetType;
    }

    /**
     * Returns the source expression.
     * @return The source <code>Expression</code> 
     */
    public Expression getSourceExpr() {
    	return fSourceExpr;
    }
    
    /**
     * Returns the target type.
     * @return The target <code>Type</code> 
     */
    public Type getTargetType() {
    	return fTargetType;
    }
    
    @Override
    public String name() {
    	return "oclIsTypeOf";
    }
    
    /**
     * Evaluates expression and returns result value. 
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = BooleanValue.FALSE;
        Value v = fSourceExpr.eval(ctx);
        Type t;
        
        if (v.isCollection()) {
        	// Collections calculate the runtime type on demand.
        	t = ((CollectionValue)v).getRuntimeType();
        } else {
        	t = v.getRuntimeType();
        }
        
        // Note: the value may be undefined, still the type test is valid!
        if (t.equals(fTargetType) )
            res = BooleanValue.TRUE;
        ctx.exit(this, res);
        return res;
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        fSourceExpr.toString(sb);
        
        if (fSourceExpr.type().isKindOfCollection(VoidHandling.EXCLUDE_VOID))
        	sb.append("->");
        else
        	sb.append(".");
        
        sb.append(name()).append("(");
        fTargetType.toString(sb);
        return sb.append(")");
    }

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitIsTypeOf(this);
	}

	@Override
	protected boolean childExpressionRequiresPreState() {		
		return fSourceExpr.requiresPreState();
	}
}
