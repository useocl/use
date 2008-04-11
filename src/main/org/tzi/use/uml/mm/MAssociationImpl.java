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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/** 
 * An association connects two or more classes.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
class MAssociationImpl extends MModelElementImpl implements MAssociation {
    private List fAssociationEnds; // (MAssociationEnd)
    private int fPositionInModel;
    /**
     * The association is called reflexive if any participating class
     * occurs more than once, e.g. R(C,C) is reflexive but also
     * R(C,D,C).  
     */
    private boolean fIsReflexive = false;

    /** 
     * Creates a new association. Connections to classes are
     * established by adding association ends. The kind of association
     * will be automatically determined by the kind of association
     * ends.
     */
    MAssociationImpl(String name) {
        super(name);
        fAssociationEnds = new ArrayList(2);
    }

    /** 
     * Adds an association end.
     *
     * @exception MInvalidModel trying to add another composition
     *            or aggregation end.
     */
    public void addAssociationEnd(MAssociationEnd aend) throws MInvalidModelException {
        if (aend.aggregationKind() != MAggregationKind.NONE )
            if (this.aggregationKind() != MAggregationKind.NONE ) 
                throw new MInvalidModelException(
                                                 "Trying to add another composition " +
                                                 "or aggregation end (`" + aend.name() +
                                                 "') to association `" + name() + "'.");

        // duplicate role names are ambiguos if they (1) refer to the
        // same class, or (2) are used in n-ary associations with n > 2
        String rolename = aend.name();
        Iterator it = fAssociationEnds.iterator();
        while (it.hasNext() ) {
            MAssociationEnd aend2 = (MAssociationEnd) it.next();
            if (rolename.equals(aend2.name()) )
                if (fAssociationEnds.size() >= 2 || aend.cls().equals(aend2.cls()) )
                    throw new MInvalidModelException(
                                                     "Ambiguous role name `" + rolename + "'.");

            // check for reflexivity
            if (aend.cls().equals(aend2.cls()) )
                fIsReflexive = true;
        }
        fAssociationEnds.add(aend);
        aend.setAssociation(this);
    }

    /**
     * Returns the list of association ends.
     *
     * @return List(MAssociationEnd)
     */
    public List associationEnds() {
        return fAssociationEnds;
    }

    /**
     * Returns the list of reachable navigation ends from
     * this association.
     *
     * @return List(MAssociationEnd)
     */
    public List reachableEnds() {
        return associationEnds();
    }

    /**
     * Returns the set of association ends attached to <code>cls</code>.
     *
     * @return Set(MAssociationEnd)
     */
    public Set associationEndsAt(MClass cls) {
        Set res = new HashSet();
        Iterator aendIter = fAssociationEnds.iterator();
        while (aendIter.hasNext() ) {
            MAssociationEnd aend = (MAssociationEnd) aendIter.next();
            if (aend.cls().equals(cls) )
                res.add(aend);
        }
        return res;
    }

    /**
     * Returns the set of classes participating in this association.
     *
     * @return Set(MClass).
     */
    public Set associatedClasses() {
        HashSet res = new HashSet();
        Iterator aendIter = fAssociationEnds.iterator();
        while (aendIter.hasNext() ) {
            MAssociationEnd aend = (MAssociationEnd) aendIter.next();
            res.add(aend.cls());
        }
        return res;
    }

    /**
     * Returns kind of association. This operation returns aggregate
     * or composition if one of the association ends is aggregate or
     * composition.
     */
    public int aggregationKind() {
        Iterator it = fAssociationEnds.iterator();
        while (it.hasNext() ) {
            MAssociationEnd aend = (MAssociationEnd) it.next();
            int k = aend.aggregationKind();
            if (k != MAggregationKind.NONE )
                return k;
        }
        return MAggregationKind.NONE;
    }

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
    public List navigableEndsFrom(MClass cls) {
        List res = new ArrayList();
        boolean partOfAssoc = false;
        Iterator it = fAssociationEnds.iterator();
        while (it.hasNext() ) {
            MAssociationEnd aend = (MAssociationEnd) it.next();
            if (! aend.cls().equals(cls) )
                res.add(aend);
            else {
                partOfAssoc = true;
                if (fIsReflexive )
                    res.add(aend);
            }
        }
        if (! partOfAssoc ) 
            throw new IllegalArgumentException("class `" + cls.name() + 
                                               "' is not part of this association.");
        return res;
    }
    
    /**
     * Returns the position in the defined USE-Model.
     */
    public int getPositionInModel() {
        return fPositionInModel;
    }

    /**
     * Sets the position in the defined USE-Model.
     */
    public void setPositionInModel(int position) {
        fPositionInModel = position;
    }


    /**
     * Process this element with visitor.
     */
    public void processWithVisitor(MMVisitor v) {
        v.visitAssociation(this);
    }

    public boolean isAssignableFrom(MClass[] classes) {
        int i=0;
        for (Iterator it=associationEnds().iterator(); it.hasNext();) {
            MAssociationEnd end = (MAssociationEnd)it.next();
            if (!classes[i].isSubClassOf(end.cls())) return false;
            ++i;
        }
        return true;
    }
}
