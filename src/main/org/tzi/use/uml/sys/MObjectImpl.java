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

import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;


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

    private ObjectValue objectValue;

    // For performance reasons
    private int hashCode;

    /**
     * Constructs a new object for the given class.
     */
    MObjectImpl( MClass cls, String name ) {
        fClass = cls;
        fType = TypeFactory.mkObjectType( fClass );
        fName = name;
        hashCode = fName.hashCode();
        objectValue = new ObjectValue(TypeFactory.mkObjectType(fClass), this);
    }

    @Override
    public MClass cls() {
        return fClass;
    }

    @Override
    public ObjectType type() {
        return fType;
    }
    
    @Override
    public String name() {
        return fName;
    }

    @Override
    public ObjectValue value() {
    	return objectValue;
    }

    @Override
    public MObjectState state( MSystemState systemState ) {
        return systemState.getObjectState( this );
    }

    @Override
    public boolean exists( MSystemState systemState ) {
        return systemState.getObjectState( this ) != null;
    }

    @Override
    public List<MObject> getLinkedObjects( MSystemState systemState,
                                  MAssociationEnd srcEnd, MAssociationEnd dstEnd, List<Value> qualifierValues ) {
        return systemState.getLinkedObjects( this, srcEnd, dstEnd, qualifierValues );
    }

    @Override
    public List<MObject> getNavigableObjects( MSystemState systemState, MNavigableElement src, 
                                     MNavigableElement dst, List<Value> qualifierValues ) {
        return systemState.getNavigableObjects( this, src, dst, qualifierValues );
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == this )
            return true;
        if (hashCode != obj.hashCode())
            return false;
        if ( obj instanceof MObject )
            return fName.equals( ( ( MObject ) obj ).name() );
        return false;
    }

    @Override
    public String toString() {
        return name();
    }

}
