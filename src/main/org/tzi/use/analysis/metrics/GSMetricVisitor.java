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
import java.util.Stack;

import org.tzi.use.uml.ocl.expr.ExpExists;
import org.tzi.use.uml.ocl.expr.ExpForAll;
import org.tzi.use.uml.ocl.expr.ExpOne;
import org.tzi.use.uml.ocl.expr.ExpSelect;
import org.tzi.use.uml.ocl.expr.Expression;

/**
 * TODO
 * @author ms
 *
 */
public class GSMetricVisitor extends AbstractMetricVisitor {

	private Stack<ExpForAll> forAllStack = new Stack<ExpForAll>(); 
	private Stack<ExpSelect> selectStack = new Stack<ExpSelect>();
	private Stack<ExpExists> existsStack = new Stack<ExpExists>();
	private Stack<ExpOne> oneStack = new Stack<ExpOne>();

	/**
	 * @param expandOperations
	 */
	public GSMetricVisitor(GSMetric measurement, boolean expandOperations) {
		super(measurement, expandOperations);
	}

	@Override
	public void visitForAll(ExpForAll exp) {

		// Log.info("Visiting ", exp);

		forAllStack.push(exp);

		// Log.info();
		// Log.info("LEVEL:", forAllStack.size());
		// Log.info();

		measurement.pushSingleShot(exp, new ArrayList<Expression>(forAllStack));
		visitQuery(exp);
		forAllStack.pop();
	}

	@Override
	public void visitSelect(ExpSelect exp) {
		selectStack.push(exp);
		measurement.pushSingleShot(exp, new ArrayList<Expression>(selectStack));
		visitQuery(exp);
		selectStack.pop();
	}

	@Override
	public void visitExists(ExpExists exp) {
		existsStack.push(exp);
		measurement.pushSingleShot(exp, new ArrayList<Expression>(existsStack));
		visitQuery(exp);
		existsStack.pop();
	}

	@Override
	public void visitOne(ExpOne exp) {
		oneStack.push(exp);
		measurement.pushSingleShot(exp, new ArrayList<Expression>(oneStack));
		visitQuery(exp);
		oneStack.pop();
	}
}
