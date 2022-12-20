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

package org.tzi.use.uml.sys.events;

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.events.tags.EventContext;
import org.tzi.use.uml.sys.events.tags.SystemStateChangedEvent;


/**
 * Information about the event of an attribute assignment.
 * @author Daniel Gent
 * @author Lars Hamann
 *
 */
public class AttributeAssignedEvent extends Event implements SystemStateChangedEvent {
	/** The object that was assigned a new attribute value */
	private MObject fObject;
	/** The attribute which was assigned a new value */
	private MAttribute fAttribute;
	/** The assigned value */
	private Value fValue;
	
	
	/**
	 * Constructs a new <code>AttributeAssignedEvent</code> with
	 * all needed information.
	 * @param context The context in which the event is raised, e. g., UNDO
	 * @param object The <code>MObject</code> that was assigned a new attribute value
	 * @param attribute The <code>MAttribute</code> which was assigned a new value
	 * @param value The assigned <code>Value</code>
	 */
	public AttributeAssignedEvent(
			EventContext context,
			MObject object, 
			MAttribute attribute, 
			Value value) {
		super(context);
		fObject = object;
		fAttribute = attribute;
		fValue = value;
	}
	
	
	/**
	 * The object that was assigned a new attribute value
	 * @return The object that was assigned a new attribute value
	 */
	public MObject getObject() {
		return fObject;
	}
	
	
	/**
	 * The attribute which was assigned a new value
	 * @return The <code>MAttribute</code>
	 */
	public MAttribute getAttribute() {
		return fAttribute;
	}
	
	
	/**
	 *  The assigned value
	 * @return The <code>Value</code>
	 */
	public Value getValue() {
		return fValue;
	}
}
