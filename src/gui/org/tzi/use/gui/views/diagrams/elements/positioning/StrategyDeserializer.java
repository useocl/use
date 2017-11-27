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

package org.tzi.use.gui.views.diagrams.elements.positioning;

import java.lang.reflect.InvocationTargetException;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.util.RestoreLayoutException;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;

/**
 * Deserializes strategies.
 * @author Lars Hamann
 *
 */
public class StrategyDeserializer {
	public static PositionStrategy restoreStrategy(PlaceableNode owner, PersistHelper helper, int version) throws RestoreLayoutException {
		PositionStrategy res = null;
		
		String strategyName = helper.getAttributeValue("type"); 
		strategyName = alignStrategyNames(strategyName);
		
		// Fixed strategy needs special treatment
		if (strategyName.equals("org.tzi.use.gui.views.diagrams.elements.positioning.StrategyFixed")) {
			res = StrategyFixed.instance;
			owner.setStrategy(res);
			res.restoreAdditionalInfo(owner, helper, version);
			return res;
		}
		
		Class<?> cls;
		Object newInstance;
		
		try {
			cls = Class.forName(strategyName);
		} catch (ClassNotFoundException e) {
			return null;
		}
		
		try {
			newInstance = cls.getDeclaredConstructor().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch(InvocationTargetException e) {
			e.printStackTrace();
			return null;
		} catch(NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		}
		
		res = (PositionStrategy)newInstance;
		res.restoreAdditionalInfo(owner, helper, version);
		return res;
	}

	/**
	 * Can be used to map old strategy names to new ones.
	 * @param strategyName
	 * @return
	 */
	private static String alignStrategyNames(String strategyName) {
		return strategyName;
	}
}
