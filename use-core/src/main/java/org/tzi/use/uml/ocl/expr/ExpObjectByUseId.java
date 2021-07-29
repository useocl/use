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

// $Id: ExpAllInstances.java 2438 2011-08-30 14:40:30Z lhamann $

package org.tzi.use.uml.ocl.expr;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;

/**
 * Type.useById(String)
 * 
 * @author  Lars Hamann
 */
public final class ExpObjectByUseId extends Expression {
    private MClass sourceType;
    
    private Expression idExpr;
    
    public ExpObjectByUseId(MClass sourceType, Expression idExpr)
        throws ExpInvalidException
    {
        super(sourceType);

        if (! sourceType.isTypeOfClass() )
            throw new ExpInvalidException("Expected an object type, found `" + sourceType + "'.");
        
        if (!idExpr.type().isKindOfString(VoidHandling.INCLUDE_VOID))
        	throw new ExpInvalidException("Expected an expression of type `String', found `" + idExpr.type() + "'.");
        
        this.sourceType = sourceType;
        this.idExpr = idExpr;
    }

    /**
     * The type allInstances() is applied to. 
     * @return
     */
    public MClass getSourceType() {
    	return this.sourceType;
    }
    
    public Expression getIdExpression() {
    	return this.idExpr;
    }
    
    @Override
    public String name() {
    	return "byUseId";
    }
    
    /**
     * Evaluates expression and returns result value. 
     */
    @Override
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        MSystemState systemState = isPre() ? ctx.preState() : ctx.postState();

        Value idExprResult = idExpr.eval(ctx);
        if (idExprResult.isUndefined()) return UndefinedValue.instance;
        
        StringValue id = (StringValue)idExprResult;
        
        // get the object 
        MObject obj = systemState.objectByName(id.value());
        
        if (obj == null) return UndefinedValue.instance;
        if (!obj.cls().isSubClassOf(sourceType)) return UndefinedValue.instance;
        
        ObjectValue res = new ObjectValue(sourceType, obj);
       
        ctx.exit(this, res);
        return res;
    }
    
	@Override
    public StringBuilder toString(StringBuilder sb) {
		sourceType.toString(sb);
		sb.append(".");
		sb.append(name());
		sb.append("(");
		idExpr.toString(sb);
		sb.append(")");
		return sb.append(atPre());
    }
	
	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitObjectByUseId(this);
	}

	@Override
	protected boolean childExpressionRequiresPreState() {
		return idExpr.requiresPreState();
	}
}
