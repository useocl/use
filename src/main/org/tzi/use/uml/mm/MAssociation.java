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

package org.tzi.use.uml.mm;

import java.util.List;
import java.util.Set;

/** 
 * An association connects two or more classes.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public interface MAssociation extends MModelElement {
    /** 
     * Adds an association end.
     *
     * @exception MInvalidModel trying to add another composition
     *            or aggregation end.
     */
    void addAssociationEnd(MAssociationEnd aend) throws MInvalidModelException;

    /**
     * Returns the list of association ends.
     *
     * @return List(MAssociationEnd)
     */
    List associationEnds();

    /**
     * Returns the list of reachable navigation ends from
     * this association.
     *
     * @return List(MAssociationEnd)
     */
    List reachableEnds();
    
    /**
     * Returns the set of association ends attached to <code>cls</code>.
     *
     * @return Set(MAssociationEnd)
     */
    Set associationEndsAt(MClass cls);

    /**
     * Returns the set of classes participating in this association.
     *
     * @return Set(MClass).
     */
    Set associatedClasses();

    /**
     * Returns kind of association. This operation returns aggregate
     * or composition if one of the association ends is aggregate or
     * composition.
     */
    int aggregationKind();

    /** 
     * Returns a list of association ends which can be reached by
     * navigation from the given class. Examples: 
     *
     * <ul> 
     * <li>For an association R(A,B), R.navigableEndsFrom(A)
     * results in (B).
     *
     * <li>For an association R(A,A), R.navigableEndsFrom(A) results
     * in (A,A).
     *
     * <li>For an association R(A,B,C), R.navigableEndsFrom(A) results
     * in (B,C).
     *
     * <li>For an association R(A,A,B), R.navigableEndsFrom(A) results
     * in (A,A,B).
     * </ul>
     *
     * This operation does not consider associations in which parents
     * of <code>cls</code> take part.
     *
     * @return List(MAssociationEnd)
     * @exception IllegalArgumentException cls is not part of this association.  
     */
    List navigableEndsFrom(MClass cls);

    /**
     * Returns the position in the defined USE-Model.
     */
    public int getPositionInModel();

    /**
     * Sets the position in the defined USE-Model.
     */
    public void setPositionInModel(int position);

    /**
     * Process this element with visitor.
     */
    void processWithVisitor(MMVisitor v);

    /** 
     * Returns true if a link consisting of objects of the given classes
     * can be assigned to this association.
     */
    boolean isAssignableFrom(MClass[] classes);
    
}
