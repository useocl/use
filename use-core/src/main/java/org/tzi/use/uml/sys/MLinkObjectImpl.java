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
import java.util.Set;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.StringUtil;


/**
 * An link object is an instance of an association class. It usually has different
 * object states over time and is connected to several objects.
 *
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 * @author Lars Hamann
 */

public class MLinkObjectImpl implements MLinkObject {
    
	private final MLink delegatesLink;
    
    private final MObject delegatesObject;
    
    // Delegates all methodcalls to MLinkImpl or MObjectImpl

    /**
     * Constructs a new object for the given class.
     */
    MLinkObjectImpl( MAssociationClass assocClass, String name,
                     List<MObject> objects, List<List<Value>> qualifierValues ) throws MSystemException {
        delegatesLink = new MLinkImpl( assocClass, objects, qualifierValues );
        delegatesObject = new MObjectImpl( assocClass, name );
    }


    // Methods of MObject
    /**
     * Returns the class of this link object.
     */
    @Override
    public MClass cls() {
        return delegatesObject.cls();
    }

    /**
     * Returns a name for this link object.
     */
    @Override
    public String name() {
        return delegatesObject.name();
    }
    
    @Override
    public ObjectValue value() {
    	return new ObjectValue(cls(), this);
    }

    
    /**
     * Returns the state of an link object in a specific system state.
     *
     * @return null if object does not exist in the state
     */
    @Override
    public MObjectState state( MSystemState systemState ) {
        return systemState.getObjectState( this );
    }

    /**
     * Returns true if this link object exists in a specific system state.
     */
    @Override
    public boolean exists( MSystemState systemState ) {
        return systemState.getObjectState( this ) != null;
    }

    @Override
    public List<MObject> getNavigableObjects( MSystemState systemState,
                                     MNavigableElement src, MNavigableElement dst, List<Value> qualifierValues ) {
        // Here, delegation is not possible, because this instance is saved for link sets.
    	// Therefore, the delegation to this.delegateObject would result in empty data for navigation.
    	return systemState.getNavigableObjects( this, src, dst, qualifierValues );
    }

    @Override
    public int hashCode() {
        return delegatesObject.hashCode();
    }

    // Methods of MLink
    /**
     * Returns the association describing this linkobject.
     */
    public MAssociation association() {
        return delegatesLink.association();
    }

    /**
     * Returns all link ends of this linkobject.
     *
     * @return Set(MLinkEnd)
     */
    public Set<MLinkEnd> linkEnds() {
        return delegatesLink.linkEnds();
    }

    /**
     * Returns the list of objects participating in this link object.
     *
     * @return List(MObject).
     */
    public List<MObject> linkedObjects() {
        return delegatesLink.linkedObjects();
    }

    public MObject[] linkedObjectsAsArray() {
        return delegatesLink.linkedObjectsAsArray();
    }

    @Override
    public MLinkEnd linkEnd( MAssociationEnd aend ) {
        return delegatesLink.linkEnd( aend );
    }

    @Override
    public MLinkEnd getLinkEnd( int index ) {
        return delegatesLink.getLinkEnd( index );
    }

    /**
     * Two link objects are equal iff they have the same name.
     */
    public boolean equals( Object obj ) {
        if ( obj == this ) {
            return true;
        }
        if ( obj instanceof MLinkObject ) {
            return ( association().equals( ( ( MLink ) obj ).association() )
                    && linkEnds().equals( ( ( MLink ) obj ).linkEnds() ) );
        }
        return false;
    }

    public String toString() {
        return name() + " [" + association().name() + " : ("
                + StringUtil.fmtSeq( linkEnds().iterator(), ", " )
                + ")]";
    }

	@Override
	public boolean isVirtual() {
		return delegatesLink.isVirtual();
	}

	@Override
	public List<List<Value>> getQualifier() {
		return delegatesLink.getQualifier();
	}
	
    /**
	 * @return the delegatesLink
	 */
	public MLink getDelegatesLink() {
	    return delegatesLink;
	}
}
