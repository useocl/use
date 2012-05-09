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

package org.tzi.use.uml.sys.events;

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class AttributeAssignedEvent extends Event {
	/** TODO */
	private MObject fObject;
	/** TODO */
	private MAttribute fAttribute;
	/** TODO */
	private Value fValue;
	
	
	/**
	 * TODO
	 * @param creator
	 * @param object
	 * @param attribute
	 * @param value
	 */
	public AttributeAssignedEvent(
			MObject object, 
			MAttribute attribute, 
			Value value) {
		
		fObject = object;
		fAttribute = attribute;
		fValue = value;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public MObject getObject() {
		return fObject;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public MAttribute getAttribute() {
		return fAttribute;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public Value getValue() {
		return fValue;
	}
}
