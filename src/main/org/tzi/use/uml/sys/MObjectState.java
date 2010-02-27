/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
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

package org.tzi.use.uml.sys;

import java.util.Map;
import java.util.TreeMap;

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;

/**
 * An object state is the state of an object in a given system state.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class MObjectState {
    /**
     * Slots holding a value for each attribute.
     */
    private TreeMap<MAttribute, Value> fAttrSlots;
    private MObject fObject;    // owner object

    /**
     * Constructs a new object state. 
     */
	public MObjectState(MObject obj) {
        fObject = obj;

        // initialize attribute slots with undefined values
        fAttrSlots = new TreeMap<MAttribute, Value>();
        
        for (MAttribute attr : obj.cls().allAttributes()) {
            fAttrSlots.put(attr, UndefinedValue.instance);
        }
    }

    /**
     * Copy constructor.
     */
    MObjectState(MObjectState x) {
        fObject = x.fObject;
        fAttrSlots = new TreeMap<MAttribute, Value>(x.fAttrSlots);
    }

    /**
     * Returns the object.
     */
    public MObject object() {
        return fObject;
    }

    /**
     * Returns the value of the specified attribute.
     *
	 * @exception IllegalArgumentException
	 *                attr is not part of this object.
     */
    public Value attributeValue(MAttribute attr) {
        Value val = (Value) fAttrSlots.get(attr);
        if (val == null )
			throw new IllegalArgumentException("Attribute `" + attr
					+ "' does not exist in object `" + fObject.name() + "'.");
        return val;
    }

    /**
     * Assigns a new value to the specified attribute.
     *
	 * @exception IllegalArgumentException
	 *                attr is not part of this object or types don't match.
     */
    public void setAttributeValue(MAttribute attr, Value newVal) {
        Value oldVal = (Value) fAttrSlots.get(attr);
        if (oldVal == null )
			throw new IllegalArgumentException("Attribute `" + attr
					+ "' does not exist in object `" + fObject.name() + "'.");
        if (! newVal.type().isSubtypeOf(attr.type()) )
			throw new IllegalArgumentException("Expected type `" + attr.type()
					+ "' for attribute `" + attr.name() + "', found type `"
					+ newVal.type() + "'.");
        fAttrSlots.put(attr, newVal);
    }

    /**
	 * Returns a map with attribute/value pairs. Do not modify this map!
     *
     * @return Map(MAttribute, Value) 
     */
    public Map<MAttribute, Value> attributeValueMap() {
        return fAttrSlots;
    }
}
