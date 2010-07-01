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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.util.StringUtil;

/**
 * A link is an instance of an association.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */

final class MLinkImpl implements MLink {
    private MAssociation fAssociation; // The type of this link

    /**
     * For each association end we store the corresponding link end.
     */
    private Map<MAssociationEnd, MLinkEnd> fLinkEnds;

    // For performance reasons
    private int hashCode;

    /**
     * Creates a new link for the given association.
     *
     * @param objects List(MObject)
     * @exception MSystemException objects do not conform to the
     *            association ends.
     */
    MLinkImpl(MAssociation assoc, List<MObject> objects) throws MSystemException {
        fAssociation = assoc;
        if (assoc.associationEnds().size() != objects.size() )
            throw new IllegalArgumentException("Number of association ends (" +
                                               assoc.associationEnds().size() +
                                               ") does not match number of passed objects (" +
                                               objects.size() + ")");
        fLinkEnds = new HashMap<MAssociationEnd, MLinkEnd>();
        
        Iterator<MAssociationEnd> it1 = assoc.associationEnds().iterator();
        Iterator<MObject> it2 = objects.iterator();

        hashCode = fAssociation.hashCode();

        while (it1.hasNext() && it2.hasNext() ) {
            MAssociationEnd aend = it1.next();
            MObject obj = it2.next();
            MLinkEnd lend = new MLinkEnd(aend, obj);
            hashCode += lend.hashCode();
            fLinkEnds.put(aend, lend);
        }
    }

    /**
     * Returns the association describing this link.
     */
    public MAssociation association() {
        return fAssociation;
    }

    /** 
     * Returns all link ends of this link.
     *
     * @return Set(MLinkEnd)
     */
    public Set<MLinkEnd> linkEnds() {
        Set<MLinkEnd> s = new HashSet<MLinkEnd>(fLinkEnds.values());
        s.addAll(fLinkEnds.values());
        return s;
    }

    /**
     * Returns the set of objects participating in this link.
     *
     * @return Set(MObject).
     */
    public Set<MObject> linkedObjects() {
        Set<MObject> s = new HashSet<MObject>();
                
        for (MLinkEnd lend : fLinkEnds.values()) {
            s.add(lend.object());
        }
        
        return s;
    }

    /**
     * Returns the set of objects participating in this link.
     *
     */
    public MObject[] linkedObjectsAsArray() {
        MObject[] objs = new MObject[fLinkEnds.size()];
        int i=0;

        for (MAssociationEnd aend : fAssociation.associationEnds()) {
            MLinkEnd lend = linkEnd(aend);
            objs[i] = lend.object();
            ++i;
        }
        
        return objs;
    }

    /** 
     * Returns the link end for the given association end.
     */
    public MLinkEnd linkEnd(MAssociationEnd aend) {
        return (MLinkEnd) fLinkEnds.get(aend);
    }

    public int hashCode() { 
        return hashCode;
    }

    /**
     * Two links are equal iff they connect the same objects.
     */
    public boolean equals(Object obj) { 
        if (obj == this )
            return true;
        if (obj instanceof MLink )
            return association().equals(((MLink) obj).association())
                && linkEnds().equals(((MLink) obj).linkEnds());
        return false;
    }

    public String toString() {
        return "[" + fAssociation.name() + " : (" +
            StringUtil.fmtSeq(fLinkEnds.values().iterator(), ", ") +
            ")]";
    }

}
