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
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.util.StringUtil;


/**
 * An linkobject is an instance of an associationclass. It usually has different
 * object states over time and is connected to several objects.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */

public class MLinkObjectImpl implements MLinkObject {
    private MLink delegatesLink;
    private MObject delegatesObject;
    // Delegates all methodcalls to MLinkImpl or MObjectImpl

    /**
     * Constructs a new object for the given class.
     */
    MLinkObjectImpl( MAssociationClass assocClass, String name,
                     List<MObject> objects ) throws MSystemException {
        delegatesLink = new MLinkImpl( assocClass, objects );
        delegatesObject = new MObjectImpl( assocClass, name );
    }


    // Methods of MObject
    /**
     * Returns the class of this linkobject.
     */
    public MClass cls() {
        return delegatesObject.cls();
    }

    /**
     * Returns the type of this linkobject.
     */
    public ObjectType type() {
        return delegatesObject.type();
    }

    /**
     * Returns a name for this linkobject.
     */
    public String name() {
        return delegatesObject.name();
    }

    /**
     * Returns the state of an linkobject in a specific system state.
     *
     * @return null if object does not exist in the state
     */
    public MObjectState state( MSystemState systemState ) {
        return systemState.getObjectState( this );
    }

    /**
     * Returns true if this linkobject exists in a specific system state.
     */
    public boolean exists( MSystemState systemState ) {
        return systemState.getObjectState( this ) != null;
    }

    /**
     * Returns a list of objects at <code>dstEnd</code> which are
     * linked to this linkobject at <code>srcEnd</code>.
     *
     * @return List(MObject)
     */
    public List<MObject> getLinkedObjects( MSystemState systemState,
                                  MAssociationEnd srcEnd, MAssociationEnd dstEnd ) {
        return systemState.getLinkedObjects( this, srcEnd, dstEnd );
    }

    /**
     * Returns a list of objects at <code>dst</code> which are
     * connected to this object at <code>src</code>. This is needed for navigation.
     *
     * @return List(MObject)
     */
    public List<MObject> getNavigableObjects( MSystemState systemState,
                                     MNavigableElement src, MNavigableElement dst ) {
        return systemState.getNavigableObjects( this, src, dst );
    }


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
     * Returns the set of objects participating in this linkobject.
     *
     * @return Set(MObject).
     */
    public Set<MObject> linkedObjects() {
        return delegatesLink.linkedObjects();
    }

    public MObject[] linkedObjectsAsArray() {
        return delegatesLink.linkedObjectsAsArray();
    }

    /**
     * Returns the link end for the given association end.
     */
    public MLinkEnd linkEnd( MAssociationEnd aend ) {
        return delegatesLink.linkEnd( aend );
    }


    /**
     * Two linkobjects are equal iff they have the same name.
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
}
