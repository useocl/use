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

package org.tzi.use.uml.ocl.expr;

import java.util.LinkedList;
import java.util.List;

import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.StringUtil;

/**
 * selectByKind as introduced in OCL 2.4
 * @author Lars Hamann
 *
 */
public class ExpSelectByKind extends Expression {

	Expression sourceExpr;
	
	/**
	 * @param t
	 * @throws SemanticException
	 */
	public ExpSelectByKind(Expression source, Type t) throws SemanticException {
		super(null);

		Type type;
		
		if (source.type().isTypeOfVoidType()) {
        	type = TypeFactory.mkVoidType();
        } else if (source.type().isKindOfCollection(VoidHandling.EXCLUDE_VOID)) {
        	type = ((CollectionType)source.type()).createCollectionType(t);
        } else {
        	throw new SemanticException("The operation " + StringUtil.inQuotes(name()) + " is only applicable on collections." );
        }
		
		this.setResultType(type);
		this.sourceExpr = source;
	}

	public Expression getSourceExpression() {
		return this.sourceExpr;
	}
	
	@Override
	public Value eval(EvalContext ctx) {
		ctx.enter(this);
		Value result;
		Value range = sourceExpr.eval(ctx);		
		
		if (range.isUndefined()) { 
			result = UndefinedValue.instance;
		} else {
			CollectionValue colRange = (CollectionValue)range;
			List<Value> elements = new LinkedList<>();
			
			for (Value v : colRange.collection()) {
				if (v.isUndefined())
					continue;
				
				if (includeElement(v))
					elements.add(v);
			}
			
			result = type().createCollectionValue(elements);
		}
		
		ctx.exit(this, result);
		return result;
	}

	@Override
	public CollectionType type() {
		return (CollectionType)super.type();
	}
	
	protected boolean includeElement(Value v) {
		return v.getRuntimeType().conformsTo(type().elemType());
	}
	
	@Override
	protected boolean childExpressionRequiresPreState() {
		return sourceExpr.requiresPreState();
	}

	public String name() {
		return "selectByKind";
	}
	
	@Override
	public StringBuilder toString(StringBuilder sb) {
		sourceExpr.toString(sb).append("->").append(name()).append("(");
		type().elemType().toString(sb).append(")");
		return sb;
	}

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitSelectByKind(this);
	}

}
