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


/**
 * TODO
 * @author ms
 *
 */
public class GSMetric extends MeasurementStrategy {

	private final GSMetricConfiguration configuration;
	private ArrayList<String> artefactsOccurrences;

	/**
	 * 
	 */
	public GSMetric(String fileName) {
		super();
		configuration = GSMetricConfiguration.load(fileName);
	}

	@Override
	public void apply(MeasuringObject object) {
		GSMetricVisitor visitor = new GSMetricVisitor(true);
		object.accept(visitor);
	}

	public void push(String artefact) {
		artefactsOccurrences.add(artefact);
	}
	
	public void calculate() {
		for(String artefact: artefactsOccurrences) {
			configuration.getWeightFor(artefact);
		}
	}
}
