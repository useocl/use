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

import org.eclipse.jdt.annotation.NonNull;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.ocl.value.Value;

/**
 * A link is an instance of an fAssociation.
 *
 * @author      Duc-Hanh
 */

final class MWholePartLinkImpl implements MWholePartLink {
    private final MLink delegatesLink; 

    /**
     * Constructor.     
     */
    MWholePartLinkImpl(MLink link) {
        delegatesLink = link;
    }
    
    /**
     * Returns the fAssociation describing this link.
     */
    public MAssociation association() {
        return delegatesLink.association();
    }

    /** 
     * Returns all link ends of this link.
     *
     * @return Set(MLinkEnd)
     */
    public Set<MLinkEnd> linkEnds() {        
        return delegatesLink.linkEnds();
    }

    /**
     * Returns the set of objects participating in this link.
     *
     * @return List(MObject).
     */
    public List<MObject> linkedObjects() {        
        return delegatesLink.linkedObjects();
    }

    /**
     * Returns the set of objects participating in this link.
     *
     */
    public MObject[] linkedObjectsAsArray() {        
        return delegatesLink.linkedObjectsAsArray();
    }

    /** 
     * Returns the link end for the given fAssociation end.
     */
    public MLinkEnd linkEnd(MAssociationEnd aend) {
        return delegatesLink.linkEnd(aend);
    }
    
    public int hashCode() {
        return delegatesLink.hashCode();
    }
    /**
     * Two links are equal iff they connect the same objects.
     */
    public boolean equals(Object obj) {        
        if (obj == this )
            return true;         
        return delegatesLink.equals(obj);
    }
    
    public String toString() {
        return delegatesLink.toString();
    }
    
    /////////////////////////////////////////////////////////////
    /////////////GRAPH FOR AGGREGATION & COMPOSITION/////////////
    /////////////////////////////////////////////////////////////
    
    
    /**
     * Returns the source node of this edge //parent or whole
     */
    @Override
    @NonNull
    public MObject source(){
        MObject[] temp = linkedObjectsAsArray();
        return temp[0];
    }
    
    /**
     * Returns the target node of this edge. //child or part
     */
    @Override
    @NonNull
    public MObject target(){
        MObject[] temp = linkedObjectsAsArray();             
        return temp[1];            
    }

    /**
     * Returns true if source and target of this edge connect the same Node.
     */
    public boolean isReflexive(){
        MObject[] temp = linkedObjectsAsArray();
        return temp[0] == temp[1];
    }

	@Override
	public boolean isVirtual() {
		return delegatesLink.isVirtual();
	}

	@Override
	public List<List<Value>> getQualifier() {
		return delegatesLink.getQualifier();
	}

	@Override
	public MLinkEnd getLinkEnd(int index) {
		return delegatesLink.getLinkEnd(index);
	}
}
