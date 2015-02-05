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

import java.util.List;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;


/**
 * An object is an instance of a class. It usually has different
 * object states over time. This class allows references to objects
 * across different system states.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public interface MObject {

    /**
     * Returns the class of this object.
     */
    public MClass cls();

    /**
     * Returns a name for this object.
     */
    public String name();

    
    /**
     * returns the value for this object
     * @return the value for this object
     */
    public ObjectValue value();

    
    /**
     * Returns the state of an object in a specific system state.
     *
     * @return null if object does not exist in the state
     */
    public MObjectState state( MSystemState systemState );

    /**
     * Returns true if this object exists in a specific system state.
     */
    public boolean exists( MSystemState systemState );

    /**
     * Returns a list of objects at <code>dst</code> which are
     * connected to this object at <code>src</code> by the (possible) given qualifiers.
     * This is needed for navigation.
     *
     * @param systemState The <code>MSystemState</code> used to evaluate
     * @param src The navigable element to navigate from
     * @param dst The navigable element to navigate to
     * @param qualifierValues The <code>List</code> of qualifier values which determines the objects. May be <code>null</code>.
     * 
     * @return List(MObject)
     */
    public List<MObject> getNavigableObjects( MSystemState systemState,
                                     MNavigableElement src, MNavigableElement dst, List<Value> qualifierValues );


    public int hashCode();

    /**
     * Two objects are equal iff they have the same name.
     */
    public boolean equals( Object obj );

    /**
     * Returns the name of the object.
     * @return The name of the object
     */
    public String toString();
}
