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
import java.util.Iterator;

import jline.internal.Log;

/**
 * TODO
 * @author ms
 *
 */
public class MeasurementReport {
	
	private ArrayList<MeasurementResultElement> objects = new ArrayList<MeasurementResultElement>();
	
	public void addObject(MeasurementResultElement resultElement) {
		this.objects.add(resultElement);
	}
	
	// TODO categorize output, therefore group and sort objects
	// TODO delegate sorting to objects
	
	// TODO see http://www.dynamicreports.org/getting-started for sophisticated report output
	
	public void publishPlain() {

		Log.info();

		Log.info("Results:");
		Log.info("========");

		Iterator<MeasurementResultElement> each = objects.iterator();
		
		while(each.hasNext()) {
			MeasurementResultElement resultElement = each.next();
			
			// Log.info(String.join("\r\n", resultElement.publishPlain()));
			Log.info(resultElement.publishPlain());

			each.remove(); // avoids ConcurrentModificationExceptions
		}
		
		Log.info();
	}
	
	/**
	public void _perform() {

		Collection<MClassInvariant> invariants = model.classInvariants();
		List<MClassInvariant> lsInvariants = new ArrayList<MClassInvariant>(invariants);

		Collections.sort(lsInvariants, new Comparator<MClassInvariant>(){

			@Override
			public int compare(MClassInvariant o1, MClassInvariant o2) {
				int clsCmp = o1.cls().compareTo(o2);
				if (clsCmp == 0) {
					return o1.name().compareTo(o2.name());
				} else {
					return clsCmp;
				}
			}

		});
	}
	**/

}
