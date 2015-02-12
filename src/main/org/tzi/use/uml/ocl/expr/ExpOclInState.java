/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2012 Mark Richters, University of Bremen
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
package org.tzi.use.uml.ocl.expr;

import org.tzi.use.uml.mm.statemachines.MState;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;

/**
 * @author Lars Hamann
 *
 */
public class ExpOclInState extends Expression {

	private final Expression sourceExpr;
	
	private final MState stateToCheck;
	
	/**
	 * @param sourceExpr
	 * @param state
	 */
	public ExpOclInState(Expression sourceExpr, MState state) {
		super(TypeFactory.mkBoolean());
		this.sourceExpr = sourceExpr;
		this.stateToCheck = state;
	}

	@Override
	public Value eval(EvalContext ctx) {
		ctx.enter(this);
        Value res = UndefinedValue.instance;
        Value v = sourceExpr.eval(ctx);
    
        if (!v.isUndefined()) {
        	ObjectValue o = (ObjectValue)v;
        	MObject obj = o.value();
        	MObjectState objState = obj.state(ctx.postState());
        	res = BooleanValue.get(objState.isInState(stateToCheck));
        }
        
        ctx.exit(this, res);
        return res;
	}

	@Override
	public StringBuilder toString(StringBuilder sb) {
		sourceExpr.toString(sb);
		sb.append(".oclInState(");
		sb.append(stateToCheck.name());
		sb.append(")");
		
		return sb;
	}

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitOclInState(this);
	}

	/**
	 * @return the sourceExpr
	 */
	public Expression getSourceExpr() {
		return sourceExpr;
	}
	
	/**
	 * Returns the state to check.
	 * @return the stateToCheck
	 */
	public MState getState() {
		return stateToCheck;
	}

	@Override
	protected boolean childExpressionRequiresPreState() {
		return sourceExpr.requiresPreState();
	}

	@Override
	public String name() {
		return "oclInState";
	}
}
