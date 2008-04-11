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

import org.tzi.use.uml.mm.MAssociationEnd;

/**
 * A link end keeps a reference to an object as defined by an
 * association end.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class MLinkEnd {
    private MAssociationEnd fAssociationEnd; // The type of the link end
    private MObject fObject;    // The linked object

    /**
     * Constructs a new link end. 
     *
     * @exception MSystemException object does not conform to the
     *            association end.
     */
    public MLinkEnd(MAssociationEnd aend, MObject obj) 
        throws MSystemException 
    {
        // make sure objects match the expected type of the
        // association end
        if (! obj.cls().isSubClassOf(aend.cls()) )
            throw new MSystemException("Object `" + obj.name() + 
                                       "' is of class `" + obj.cls() +
                                       "', but association end `" + aend + 
                                       "' can only hold objects of class `" +
                                       aend.cls() + 
                                       "' or its subclasses.");
        fAssociationEnd = aend;
        fObject = obj;
    }

    /**
     * Returns the association end describing this link end.
     */
    public MAssociationEnd associationEnd() {
        return fAssociationEnd;
    }

    /**
     * Returns the connected object.
     */
    public MObject object() {
        return fObject;
    }

    public int hashCode() { 
        return fAssociationEnd.hashCode() + fObject.hashCode();
    }

    /**
     * Two link ends are equal iff they connect the same object and
     * have the same type.
     */
    public boolean equals(Object obj) { 
        if (obj == this )
            return true;
        if (obj instanceof MLinkEnd )
            return fAssociationEnd.equals(((MLinkEnd) obj).fAssociationEnd)
                && fObject.equals(((MLinkEnd) obj).fObject);
        return false;
    }

    public String toString() {
        return fAssociationEnd + ":" + fObject;
    }
}
