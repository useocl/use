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

import java.util.Set;

import jline.internal.Log;

import org.tzi.use.uml.mm.MModel;

import com.google.common.collect.Sets;

/**
 * Provides logic for the shell coverage command.
 * 
 * @author ms
 *
 */
public class ShellMetricCommandProcessor extends AbstractShellCommandProcessor {

	/**
	 * @param model
	 * @param line
	 */
	public ShellMetricCommandProcessor(MModel model, String line) {
		super(model, line);
	}

	protected void dispatch() {
		Set<String> actualArgs = Sets.newHashSet(line.split(" "));
		Set<String> validArgs = Sets.newHashSet("metrics", "--run");
		Set<String> delta = Sets.difference(actualArgs, validArgs);

		if(delta.isEmpty()) {
			//Log.info("Arguments valid");

			// TODO Extract metric name.
			// TODO Extract configuration file name.
			if(actualArgs.contains("--run")) _measure("sg", "default");
		}
		else {
			Log.error("Invalid arguments: ", delta);
		}
	}

	private void _measure(String metricName, String fileName) {
		//Log.info("Measuring");
		
		MeasurementStrategy strategy = null;
		
		switch(metricName) {
		case "sg": 
			strategy = new GSMetric(fileName);
			break;
		}
		
		Measurement measurement = new Measurement(strategy, model);
		measurement.perform();
		MeasurementReport report = measurement.getReport();
		report.publishPlain();
		
		//Log.info("Result/Complexity: " + measurement.getResult());
		
	}
}
