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

/**
 * TODO
 * @author ms
 *
 */
public class GSMetricConfiguration {
	
	private CSVFileReader csvProxy;
	private HashMap<String,ConfigurationEntry> entries = new HashMap<String,ConfigurationEntry>(); 
	
	public static GSMetricConfiguration load(String fileName) {
		CSVFileReader csvProxy = new CSVFileReader("metrics/gs-metric/" + fileName);
		return new GSMetricConfiguration(csvProxy);
	}
	
	/**
	 * 
	 */
	public GSMetricConfiguration(CSVFileReader csvProxy) {
		super();
		this.csvProxy = csvProxy;
		load();
	}
	
	private void load() {
		for(ArrayList<String> tuple: csvProxy.getLines()) {
			String key = tuple.get(0);
			tuple.remove(0);
			entries.put(key, new ConfigurationEntry(key, tuple));
		}
	}

	public float getWeightFor(String name) {
		ConfigurationEntry entry = entries.get(name);
		return entry.getWeightValue();
	}

}
