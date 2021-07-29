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

package org.tzi.use.analysis.metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import org.tzi.use.uml.ocl.expr.ExpAllInstances;
import org.tzi.use.uml.ocl.expr.ExpAny;
import org.tzi.use.uml.ocl.expr.ExpAsType;
import org.tzi.use.uml.ocl.expr.ExpCollect;
import org.tzi.use.uml.ocl.expr.ExpCollectNested;
import org.tzi.use.uml.ocl.expr.ExpExists;
import org.tzi.use.uml.ocl.expr.ExpForAll;
import org.tzi.use.uml.ocl.expr.ExpIterate;
import org.tzi.use.uml.ocl.expr.ExpOne;
import org.tzi.use.uml.ocl.expr.ExpReject;
import org.tzi.use.uml.ocl.expr.ExpSelect;
import org.tzi.use.uml.ocl.expr.ExpSortedBy;
import org.tzi.use.uml.ocl.expr.Expression;

/**
 * TODO
 * @author ms
 *
 */
public class GSMetricVisitor extends AbstractMetricVisitor {

	private HashMap<String, Stack<Expression>> stackMap = new HashMap<String, Stack<Expression>>();

	/**
	 * @param expandOperations
	 */
	public GSMetricVisitor(GSMetric measurement, boolean expandOperations) {
		super(measurement, expandOperations);
	}
	
	// TODO visitPlain, visitDescend
	
	@Override
	public void visitAllInstances(ExpAllInstances exp) {
		visitExpression(exp);
		popFromStack(exp);
	}
	
	@Override
	public void visitAny(ExpAny exp) {
		visitExpression(exp);
		visitQuery(exp);
		popFromStack(exp);
	}
	
	@Override
	public void visitAsType(ExpAsType exp) {
		visitExpression(exp);
		popFromStack(exp);
	}

	@Override
	public void visitCollect(ExpCollect exp) {
		visitExpression(exp);
		visitQuery(exp);
		popFromStack(exp);
	}
	
	@Override
	public void visitCollectNested(ExpCollectNested exp) {
		visitExpression(exp);
		visitQuery(exp);
		popFromStack(exp);
	}
	
	@Override
	public void visitExists(ExpExists exp) {
		visitExpression(exp);
		visitQuery(exp);
		popFromStack(exp);
	}
	
	@Override
	public void visitForAll(ExpForAll exp) {
		visitExpression(exp);
		visitQuery(exp);
		popFromStack(exp);
	}
	
	@Override
	public void visitIterate(ExpIterate exp) {
		visitExpression(exp);
		visitQuery(exp);
		popFromStack(exp);
	}
	
	@Override
	public void visitOne(ExpOne exp) {
		visitExpression(exp);
		visitQuery(exp);
		popFromStack(exp);
	}
	
	@Override
	public void visitReject(ExpReject exp) {
		visitExpression(exp);
		visitQuery(exp);
		popFromStack(exp);
	}
	
	@Override
	public void visitSelect(ExpSelect exp) {
		visitExpression(exp);
		visitQuery(exp);
		popFromStack(exp);
	}
	
	@Override
	public void visitSortedBy(ExpSortedBy exp) {
		visitExpression(exp);
		visitQuery(exp);
		popFromStack(exp);
	}
	
	/** private section **/
	
	private String pushToStack(Expression expression) {
		String stackKey = expression.getClass().getName(); 
		Stack<Expression> stack = stackMap.get(stackKey);

		if(stack == null) {
			stack = new Stack<Expression>();
			stackMap.put(stackKey, stack);
		}

		stackMap.get(stackKey).push(expression);

		return stackKey;
	}

	private void popFromStack(Expression expression) {
		String stackKey = expression.getClass().getName();
		stackMap.get(stackKey).pop();
	}

	private void visitExpression(Expression expression) {
		String stackKey = pushToStack(expression);
		measurement.pushSingleShot(expression, new ArrayList<Expression>(stackMap.get(stackKey)));
	}

}
