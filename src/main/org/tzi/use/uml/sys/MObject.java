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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.uml.sys;

import java.util.List;

import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.ocl.type.ObjectType;


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
     * Returns the type of this object.
     */
    public ObjectType type();


    /**
     * Returns a name for this object.
     */
    public String name();


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
     * Returns a list of objects at <code>dstEnd</code> which are
     * linked to this object at <code>srcEnd</code>.
     *
     * @return List(MObject)
     */
    public List getLinkedObjects( MSystemState systemState,
                                  MAssociationEnd srcEnd, MAssociationEnd dstEnd );

    /**
     * Returns a list of objects at <code>dst</code> which are
     * connected to this object at <code>src</code>. This is needed for navigation.
     *
     * @return List(MObject)
     */
    public List getNavigableObjects( MSystemState systemState,
                                     MNavigableElement src, MNavigableElement dst );


    public int hashCode();

    /**
     * Two objects are equal iff they have the same name.
     */
    public boolean equals( Object obj );

    public String toString();

}
