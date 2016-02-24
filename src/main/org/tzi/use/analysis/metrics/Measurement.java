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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import jline.internal.Log;

import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MModelElement;

/**
 * TODO
 * @author ms
 *
 */
public class Measurement {

	private MeasurementStrategy strategy;
	private MModel model;

	// TODO abstract results
	private HashMap<MModelElement, Float> results = new HashMap<MModelElement, Float>();

	/**
	 * @param strategy
	 */
	public Measurement(MeasurementStrategy strategy, MModel model) {
		this.strategy = strategy;
		this.model = model;
	}

	public void perform() {

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

		for (MClassInvariant invariant : lsInvariants) {
			MeasuringObject object = new MeasuringObject(invariant.expandedExpression());
			strategy.apply(object);
			results.put(invariant, new Float(strategy.inject()));

			strategy.reset();
		}
	}

	// TODO pull this into MeasurementReport
	public void displayResults() {

		Log.info();

		Log.info("Results:");
		Log.info("========");

		Iterator<Entry<MModelElement, Float>> each = results.entrySet().iterator();
		while (each.hasNext()) {
			Entry<MModelElement, Float> tuple = each.next();
			Log.info("O(" + tuple.getKey().name() + "): " + tuple.getValue());
			each.remove(); // avoids ConcurrentModificationExceptions
		}

		Log.info();
	}
}
