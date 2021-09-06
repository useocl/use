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

package org.tzi.use.analysis.coverage;

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;

/**
 * A MAttribute can be defined in a base class.
 * To allow a more detailed coverage analysis this class
 * saves the source class of the object expression, e. g., 
 * for an access to the name of an professor
 * the class Professor is saved instead of the attribute owner Person 
 * @author Lars Hamann
 *
 */
public class AttributeAccessInfo implements Comparable<AttributeAccessInfo> {
	private MAttribute attribute;
	private MClass sourceClass;
	
	public AttributeAccessInfo(MClass cls, MAttribute att) {
		this.attribute = att;
		this.sourceClass = cls;
	}

	/**
	 * @return the attribute
	 */
	public MAttribute getAttribute() {
		return attribute;
	}

	/**
	 * @return the sourceClass
	 */
	public MClass getSourceClass() {
		return sourceClass;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		
		if (!(o instanceof AttributeAccessInfo))
			return false;
		
		AttributeAccessInfo info = (AttributeAccessInfo)o;
		return this.getSourceClass().equals(info.getSourceClass()) &&
			   this.getAttribute().equals(info.getAttribute());
	}
	
	@Override
	public int hashCode() {
		return (sourceClass.name() + attribute.name()).hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AttributeAccessInfo o) {
		if (this == o) return 0;
		
		int clsComp = this.getSourceClass().compareTo(o.getSourceClass()); 
		
		if (clsComp == 0)
			return this.getSourceClass().compareTo(o.getSourceClass());
		
		return clsComp;
	}

	/**
	 * @return
	 */
	public boolean isInherited() {
		return !getSourceClass().equals(getAttribute().owner());
	}
}