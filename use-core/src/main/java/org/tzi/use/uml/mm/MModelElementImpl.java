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

package org.tzi.use.uml.mm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.tzi.use.util.collections.CollectionUtil;


/**
 * Base class for all model elements.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */

public abstract class MModelElementImpl implements MModelElement {
	//TODO: Use delegation for Annotatable?
	
	private static Map<String, MutableInteger> fNameMap = new HashMap<String, MutableInteger>();
	
	private final String fName;
	
	/**
	 * The hash code of an model elements does not change after loading,
	 * therefore we don't need to calculate it every time.
	 * 
	 */
    private final int hashCode;
    
    /**
     * Possible annotations of this model element.
     */
    private Map<String, MElementAnnotation> annotations = Collections.emptyMap();
    
    // We don't want to allocate a new Integer object each time we
    // have to increment the value in a map.
    static class MutableInteger {
        int fInt = 1;
    }

    protected MModelElementImpl(String name) {
        if (name == null || name.length() == 0 )
            throw new IllegalArgumentException("Modelelement without name");
        fName = name;
        hashCode = fName.hashCode();
    }
    
    /**
     * Creates a new model element with optional name. If the name is
     * null or empty a new name starting with <code>prefix</code> will
     * be generated. Note that the generated names will be unique but
     * they may still clash with some user defined name.
     */
    protected MModelElementImpl(String name, String prefix) {
        if (name == null || name.trim().length() == 0 ) {
            MutableInteger i = fNameMap.get(prefix);
            if (i == null ) {
                i = new MutableInteger();
                fNameMap.put(prefix, i);
            } else
                i.fInt++;
            name = prefix + String.valueOf(i.fInt);
        }
        fName = name;
        hashCode = fName.hashCode();
    }
    
    /**
     * Returns the name of this model element.
     */
    public String name() {
        return fName;
    }

    /**
     * Process this element with visitor.
     */
    public abstract void processWithVisitor(MMVisitor v);

    @Override
    public Map<String, MElementAnnotation> getAllAnnotations() {
    	return this.annotations;
    }
    
    @Override
    public boolean isAnnotated() {
    	return !this.annotations.isEmpty();
    }
    
    @Override
    public MElementAnnotation getAnnotation(String name) {
    	if (this.annotations.containsKey(name)) {
    		return this.annotations.get(name);
    	} else {
    		return null;
    	}
    }
    
    @Override
    public String getAnnotationValue(String annotationName, String attributeName) {
    	MElementAnnotation ann = getAnnotation(annotationName);
    	
    	if (ann == null) return "";
    	
    	String value = ann.getAnnotationValue(attributeName); 
    	return (value == null ? "" : value);
    }
    
    @Override
    public void addAnnotation(MElementAnnotation annotation) {
    	this.annotations = CollectionUtil.initAsHashMap(this.annotations);
    	this.annotations.put(annotation.getName(), annotation);
    }
    
    @Override
    public int hashCode() { 
        return hashCode;
    }

    /**
     * The default method defines model elements to be equal if their
     * names are equal.
     */
    @Override
    public boolean equals(Object obj) {
    	if (obj == this )
            return true;
    	if (obj == null)
    		return false;
    	
        if (hashCode != obj.hashCode())
        	return false;
                
        if (obj instanceof MModelElement)
            return fName.equals(((MModelElement) obj).name());
        
        return false;
    }

    /**
     * Compares just the model element's name.
     */
    @Override
    public int compareTo(MModelElement o) {
        if (o == this )
            return 0;

        return fName.compareTo(o.name());
    }

    /**
     * Returns the name of this model element.
     */
    @Override
    public String toString() {
        return fName;
    }
}
