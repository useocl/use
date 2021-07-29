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

import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.SequenceValue;
import org.tzi.use.uml.ocl.value.Value;

/**
 * Range expressions are used by
 * collection constructors like Set{1..9}.
 * The result of <code>eval</code> is a sequence of integer values.
 * @author Lars Hamann
 *
 */
public class ExpRange extends Expression {

	private Expression startExp;
	private Expression endExp;
	
	/**
	 * @param first
	 * @param second
	 */
	public ExpRange(Expression first, Expression second) {
		super(TypeFactory.mkInteger());
		this.startExp = first;
		this.endExp = second;
	}

	@Override
	public Value eval(EvalContext ctx) {
		Value start = startExp.eval(ctx);
		if (start.isUndefined())
			return start;
		
		Value end = endExp.eval(ctx);
		if (end.isUndefined())
			return end;
		
		IntegerValue iStart = (IntegerValue)start;
		IntegerValue iEnd = (IntegerValue)end;
		
		List<IntegerValue> values = new LinkedList<>();
		
		for(int i = iStart.value(); i <= iEnd.value(); ++i) {
			values.add(IntegerValue.valueOf(i));
		}
		
		return new SequenceValue(TypeFactory.mkInteger(), values);
	}

	@Override
	protected boolean childExpressionRequiresPreState() {
		return startExp.childExpressionRequiresPreState() || endExp.childExpressionRequiresPreState();
	}

	@Override
	public StringBuilder toString(StringBuilder sb) {
		startExp.toString(sb);
		sb.append("..");
		endExp.toString(sb);
		
		return sb;
	}

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitRange(this);
	}

	public Expression getStart() {
		return this.startExp;
	}
	
	public Expression getEnd() {
		return this.endExp;
	}
}
