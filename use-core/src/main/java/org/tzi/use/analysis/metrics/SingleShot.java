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

import org.tzi.use.analysis.metrics.GSMetric.NestingMode;
import org.tzi.use.uml.ocl.expr.Expression;


/**
 * TODO
 * @author ms
 *
 */
public class SingleShot extends ExpressionProxy {

	private final MeasurementStrategy metric;
	private final NestingMode nestingMode;
	private final ArrayList<Expression> stackTrace;

	public SingleShot(MeasurementStrategy metric, NestingMode nestingMode, Expression client, ArrayList<Expression> stackTrace) {
		super(client);
		this.metric = metric;
		this.nestingMode = nestingMode;
		this.stackTrace = stackTrace;
	}

	public float measuredValue() {
		float interim = 0;

		switch(nestingMode) {
		case none: 
			interim = metric.getWeightFor(name());
			break;
		case simpleNesting:
			interim = metric.getWeightFor(name()) * depth();
			break;
		case simpleSymmetricNestingCustom:
			interim = metric.getWeightForDepth(name(), depth());
			break;
		default:
			break;
		}

		return interim;
	}

	public int depth() {
		return stackTrace.size();
	}

}
