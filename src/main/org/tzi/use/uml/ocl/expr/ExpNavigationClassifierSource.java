/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.LinkValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;

/**
 * Expression for navigations
 * that start at a classifier, e. g., association or association class. 
 * @author Lars Hamann
 *
 */
public class ExpNavigationClassifierSource extends Expression {

	/**
	 * The source of the navigation
	 */
	private final Expression source;
	
	/**
	 * The destination "end"
	 */
	private final MNavigableElement destination;
	
	/**
	 * @param t
	 */
	public ExpNavigationClassifierSource(Type t, Expression src, MNavigableElement dst) {
		super(t);
		this.source = src;
		this.destination = dst;
	}

	public MNavigableElement getDestination() {
        return this.destination;
    }

    public Expression getObjectExpression() {
        return this.source;
    }
    
	@Override
	public Value eval(EvalContext ctx) {
		ctx.enter(this);
		Value result = UndefinedValue.instance;
		
		Value srcValue = this.source.eval(ctx);
		
		if (!srcValue.isUndefined()) {
			MSystemState state = (this.isPre() ? ctx.preState() : ctx.postState());
			MLink link = null;
			
			if (srcValue.isObject()) {
				ObjectValue objVal = (ObjectValue)srcValue;
				link = (MLink)objVal.value();
			} else if (srcValue.isLink()) {
				LinkValue lnkValue = (LinkValue)srcValue;
				link = lnkValue.getLink();
			} else {
				throw new IllegalArgumentException("Source expression must be of type link or link object!");
			}
			
			MObject obj = state.getNavigableObject(link, this.destination);
			// A link is always connected to objects, i. e., obj cannot be null
			result = new ObjectValue((MClass)this.type(), obj);
		}
		
		ctx.exit(this, result);
		return result;
	}

	@Override
	protected boolean childExpressionRequiresPreState() {
		return this.source.requiresPreState();
	}

	@Override
	public StringBuilder toString(StringBuilder sb) {
		return this.source.toString(sb).append(".").append(destination.nameAsRolename());
	}

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitNavigationClassifierSource(this);		
	}
}
