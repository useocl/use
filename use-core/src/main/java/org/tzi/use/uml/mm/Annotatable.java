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

package org.tzi.use.uml.mm;

import org.tzi.use.uml.api.IElementAnnotation;

import java.util.HashMap;
import java.util.Map;

/**
 * Interface for annotatable elements
 * @author Lars Hamann
 *
 */
public interface Annotatable {

    /**
     * True if the model element was defined with annotations, e. g.,
     * <code>@Monitor(host="localhost")</code>.
     * @return true if annotated
     */
    boolean isAnnotated();
    
    /**
     * All defined annotations for this model element.
     * The key is the name of the annotation, e. g., <code>Monitor</code> when
     * annotated <code>@Monitor(...)</code>.
     * @return A <code>Map</code> that relates annotation names to their annotation information.
     */
    Map<String, MElementAnnotation> getAllAnnotations();
    
    /**
     * Returns the {@link MElementAnnotation} object of this model element
     * with the given <code>name</code>.
     * If no annotation with the name was defined <code>null</code> is
     * returned. 
     * @param name Name of the annotation
     * @return MElementAnnotation or null
     */
    MElementAnnotation getAnnotation(String name);
    
    /**
     * Returns the value of the annotation attribute of the annotation given by <code>name</code>
     * if it exists otherwise the empty string ""
     * @param annotationName Name of the annotation
     * @param attributeName Name of the attribute
     * @return Attribute value or empty string
     */
    String getAnnotationValue(String annotationName, String attributeName);
    
    /**
     * Adds an annotation
     * @param an Annotation to add
     */
    void addAnnotation(MElementAnnotation an);

    /**
     * Adapter to the API-level annotations representation (IElementAnnotation)
     */
    default Map<String, IElementAnnotation> asApiAnnotations() {
        Map<String, IElementAnnotation> map = new HashMap<>();
        Map<String, MElementAnnotation> all = getAllAnnotations();
        if (all != null) {
            for (Map.Entry<String, MElementAnnotation> e : all.entrySet()) {
                map.put(e.getKey(), e.getValue());
            }
        }
        return map;
    }
}
