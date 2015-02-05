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

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.StringUtil;

/**
 * A link is an instance of an association.
 *
 * @author Mark Richters
 * @author Lars Hamann
 */
class MLinkImpl implements MLink {
	/** The type of this link */
    private final MAssociation fAssociation;

    /**
     * The set of all link ends to avoid copying.
     */
    private final MLinkEnd[] linkEnds;
    
    /**
     * Other representation of linked objects,
     * because of performance improvements
     */
    private final MObject[] linkedObjects;

    /**
     * Links are immutable. Therefore the hash code can
     * be stored.
     */
    private final int hashCode;

    
    private List<List<Value>> qualifierValues;
    
    /**
     * Creates a new link for the given association.
     *
     * @param assoc The association to create the link for.
     * @param objects The list of connected objects in the same order as the association ends.
     * @param qualifierValues The qualifier values for each end.
     * @exception MSystemException objects do not conform to the
     *            association ends.
     */
    protected MLinkImpl(MAssociation assoc, List<MObject> objects, List<List<Value>> qualifierValues) throws MSystemException {
        fAssociation = assoc;
        if (assoc.associationEnds().size() != objects.size() )
            throw new IllegalArgumentException("Number of association ends (" +
                                               assoc.associationEnds().size() +
                                               ") does not match number of passed objects (" +
                                               objects.size() + ")");

        linkEnds = new MLinkEnd[assoc.associationEnds().size()];
        this.qualifierValues = qualifierValues;
        Iterator<MAssociationEnd> it1 = assoc.associationEnds().iterator();
        Iterator<MObject> it2 = objects.iterator();
        boolean hasQualifiers = (qualifierValues != null && qualifierValues.size() > 0);
        Iterator<List<Value>> it3 = (hasQualifiers ? qualifierValues.iterator() : null);
        
        int newHashCode = fAssociation.hashCode();
        linkedObjects = new MObject[objects.size()];
        int i = 0;

        while (it1.hasNext() && it2.hasNext() ) {
            MAssociationEnd aend = it1.next();
            MObject obj = it2.next();
            List<Value> endQualifierValues;
            
            if (hasQualifiers)
            	endQualifierValues = it3.next();
            else
            	endQualifierValues = null;
            
            MLinkEnd lend = new MLinkEnd(aend, obj, endQualifierValues);
            newHashCode += lend.hashCode();
            linkEnds[i] = lend;
            linkedObjects[i] = obj;
            ++i;
        }
        
        this.hashCode = newHashCode;
    }

    /**
     * Returns the association describing this link.
     */
    public MAssociation association() {
        return fAssociation;
    }

    /** 
     * Returns a read only list of all link ends of this link.
     *
     * @return read only list of the link ends
     */
    public Set<MLinkEnd> linkEnds() {
    	return new HashSet<MLinkEnd>(Arrays.asList(linkEnds));
    }

    /**
     * Returns the list of objects participating in this link.
     *
     * @return Set(MObject).
     */
    public List<MObject> linkedObjects() {
    	List<MObject> s = Arrays.asList(linkedObjects);
        return s;
    }

    /**
     * Returns the set of objects participating in this link.
     *
     */
    public MObject[] linkedObjectsAsArray() {        
        return linkedObjects;
    }

    /** 
     * Returns the link end for the given association end.
     */
    public MLinkEnd linkEnd(MAssociationEnd aend) {
        // Should be fast enough for normal associations
    	int i = fAssociation.associationEnds().indexOf(aend);
    	return linkEnds[i];
    }

    /** 
     * Returns the link end for the given index.
     */
    public MLinkEnd getLinkEnd(int index) {
    	return linkEnds[index];
    }
    
    public int hashCode() { 
        return hashCode;
    }

    /**
     * Two links are equal iff they connect the same objects.
     */
    @Override
    public boolean equals(Object obj) { 
        if (obj == this)
            return true;
        
        if (obj == null) return false;
        
        // MLinkImpl implements hashCode() so
        // when the hash codes differ they are not equal
        if (this.hashCode() != obj.hashCode())
                return false;

        if (obj instanceof MLink ) {
            MLink other = (MLink)obj;
            
            return association().equals(other.association())
                   && linkEnds().equals(other.linkEnds());
        }

        return false;
    }

    @Override
    public String toString() {
    	// For the link identification in an object diagram we need to 
    	// build a deterministic string representation.
    	// Therefore, the link ends are ordered by the association end name
    	StringBuilder result = new StringBuilder();
    	if (isVirtual()) result.append("virtual");
    	result.append("[");
    	result.append(fAssociation.name()).append(" : (");

    	MLinkEnd[] sortedEnds = Arrays.copyOf(linkEnds, linkEnds.length); 
    	Arrays.sort(sortedEnds, new Comparator<MLinkEnd>() {
			@Override
			public int compare(MLinkEnd o1, MLinkEnd o2) {
				return o1.associationEnd().name().compareTo(o2.associationEnd().name());
			}
		});
    	
        StringUtil.fmtSeq(result, sortedEnds, ", ");
            
        result.append(")]");
        
        return result.toString();
    }

	@Override
	public boolean isVirtual() {
		return false;
	}

	@Override
	public List<List<Value>> getQualifier() {
		return this.qualifierValues;
	}
}
