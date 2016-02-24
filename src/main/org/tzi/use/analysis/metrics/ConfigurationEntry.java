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

import java.util.List;

/**
 * TODO
 * @author ms
 *
 */
public class ConfigurationEntry {

	private final String key;
	private List<String> values;

	/**
	 * @param key
	 * @param values
	 */
	public ConfigurationEntry(String key, List<String> values) {
		this.key = key;
		this.values = values;
	}
	
	public float getWeightValue() {
		// for now assuming format (name -> weight)
		return Float.parseFloat(values.get(0));
	}
	
	public boolean deliversWeightValueByDepth(int depth) {
		return true;
	}
	
	public float getWeightValueByDepth() {
		// for now assuming format (name -> depth, weight)
		return Float.parseFloat(values.get(0));
	}

}
