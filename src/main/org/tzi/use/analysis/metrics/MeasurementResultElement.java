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
import java.util.Iterator;
import java.util.Map.Entry;

import org.tzi.use.uml.mm.MModelElement;

/**
 * TODO
 * @author ms
 *
 */
public class MeasurementResultElement {

	String scope;
	HashMap<MModelElement, Float> client;

	/**
	 * @param interimResult
	 */
	public MeasurementResultElement(String scope, HashMap<MModelElement, Float> interimResult) {
		this.scope = scope;
		this.client = interimResult;
	}

	public ArrayList<String> publishPlain() {
		ArrayList<String> buffer = new ArrayList<String>();

		Iterator<Entry<MModelElement, Float>> each = client.entrySet().iterator();
		while (each.hasNext()) {
			Entry<MModelElement, Float> tuple = each.next();
			buffer.add("O(" + tuple.getKey().name() + "): " + tuple.getValue());
			each.remove(); // avoids ConcurrentModificationExceptions
		}
		return buffer;
	}

}
