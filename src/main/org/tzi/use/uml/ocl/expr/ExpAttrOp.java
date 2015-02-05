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

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.uml.sys.MSystemState;

/**
 * Attribute operation on objects.
 * 
 * @author Mark Richters
 * @author Lars Hamann
 */
public final class ExpAttrOp extends Expression {
    private MAttribute fAttr;
    
    private Expression fObjExp;
    
    public ExpAttrOp(MAttribute a, Expression objExp) {
        super(a.type());
        fAttr = a;
        fObjExp = objExp;
        if (! objExp.type().isTypeOfClass() )
            throw new IllegalArgumentException(
                                               "Target expression of attribute operation must have " +
                                               "object type, found `" + objExp.type() + "'.");
    }

    public MAttribute attr() {
        return fAttr;
    }

    public Expression objExp() {
        return fObjExp;
    }

    /**
     * Evaluates expression and returns result value.
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = UndefinedValue.instance;
        Value val = fObjExp.eval(ctx);
        // if we don't have an object we can't deliver an attribute value
        if (! val.isUndefined() ) {
            ObjectValue objVal = (ObjectValue) val;
            MObject obj = objVal.value();
            
            if (fAttr.isDerived()) {
            	MSystemState state = isPre() ? ctx.preState() : ctx.postState();
            	res = state.evaluateDeriveExpression(obj, fAttr);
            } else {
	            MObjectState objState = isPre() ? 
	                obj.state(ctx.preState()) : obj.state(ctx.postState());
	
	            // if the object is dead the result is undefined
	            if (objState != null )
	                res = objState.attributeValue(fAttr);
            }
        }
        ctx.exit(this, res);
        return res;
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        fObjExp.toString(sb);
        sb.append(".");
        sb.append(fAttr.name());
        return sb.append(atPre());
    }

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitAttrOp(this);
	}

	@Override
	protected boolean childExpressionRequiresPreState() {		
		return fObjExp.requiresPreState();
	}
}

