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

package org.tzi.use.uml.mm;

import java.util.Collections;
import java.util.Map;

/**
 * Saves information about annotations of
 * model elements as attribute value pairs.
 * @author Lars Hamann
 *
 */
public class MElementAnnotation {
	
	private String name;
	
	private Map<String, String> annotationValues;

	/**
	 * Creates an element annotation with the given annotation values. 
	 * @param name The name of the annotation
	 * @param annotationValues Attribute and value pairs with additional informations
	 */
	public MElementAnnotation(String name, Map<String, String> annotationValues) {
		this.name = name;
		this.annotationValues = Collections.unmodifiableMap(annotationValues);
	}
	
	/**
	 * Creates an element annotation without any annotation values.
	 * @param name
	 */
	public MElementAnnotation(String name) {
		this(name, Collections.<String,String>emptyMap());
	}
	
	/**
	 * The name of the annotation
	 * @return The name of the annotation.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * All annotation values defined for this annotation.
	 * The returned <code>Map</code> is immutable.
	 * @return An <UnmodifiableMap> which contains the informations defined for this annotation.
	 */
	public Map<String, String> getValues() {
		return this.annotationValues;
	}
	
	public boolean hasAnnotationValue(String name) {
		return this.getValues().containsKey(name);
	}
	
	/**
	 * Returns the value for the annotation value <code>name</code> or
	 * <code>null</code> if absend.
	 * @param name
	 * @return
	 */
	public String getAnnotationValue(String name) {
		if (this.hasAnnotationValue(name)) {
			return this.getValues().get(name);
		} else {
			return null;
		}
	}

	/**
	 * @param mmPrintVisitor
	 */
	public void processWithVisitor(MMVisitor v) {
		v.visitAnnotation(this);
	}
}
