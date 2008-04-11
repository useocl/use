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

$Id$

package org.tzi.use.uml.sys;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.type.ObjectType;

import java.util.List;


/**
 * An object is an instance of a class. It usually has different
 * object states over time. This class allows references to objects
 * across different system states.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Marc Richters
 */
public final class MObjectImpl implements MObject {
    private MClass fClass;  // class of object
    private ObjectType fType;   // type of object
    private String fName;   // unique object name

    /**
     * Constructs a new object for the given class.
     */
    MObjectImpl( MClass cls, String name ) {
        fClass = cls;
        fType = TypeFactory.mkObjectType( fClass );
        fName = name;
    }

    /**
     * Returns the class of this object.
     */
    public MClass cls() {
        return fClass;
    }

    /**
     * Returns the type of this object.
     */
    public ObjectType type() {
        return fType;
    }

    /**
     * Returns a name for this object.
     */
    public String name() {
        return fName;
    }

    /**
     * Returns the state of an object in a specific system state.
     *
     * @return null if object does not exist in the state
     */
    public MObjectState state( MSystemState systemState ) {
        return systemState.getObjectState( this );
    }

    /**
     * Returns true if this object exists in a specific system state.
     */
    public boolean exists( MSystemState systemState ) {
        return systemState.getObjectState( this ) != null;
    }

    /**
     * Returns a list of objects at <code>dstEnd</code> which are
     * linked to this object at <code>srcEnd</code>.
     *
     * @return List(MObject)
     */
    public List getLinkedObjects( MSystemState systemState,
                                  MAssociationEnd srcEnd, MAssociationEnd dstEnd ) {
        return systemState.getLinkedObjects( this, srcEnd, dstEnd );
    }

    /**
     * Returns a list of objects at <code>dst</code> which are
     * connected to this object at <code>src</code>. This is needed for navigation.
     *
     * @return List(MObject)
     */
    public List getNavigableObjects( MSystemState systemState, MNavigableElement src, 
                                     MNavigableElement dst ) {
        return systemState.getNavigableObjects( this, src, dst );
    }

    public int hashCode() {
        return fName.hashCode();
    }

    /**
     * Two objects are equal iff they have the same name.
     */
    public boolean equals( Object obj ) {
        if ( obj == this )
            return true;
        if ( obj instanceof MObject )
            return fName.equals( ( ( MObject ) obj ).name() );
        return false;
    }

    public String toString() {
        return name();
    }

}
