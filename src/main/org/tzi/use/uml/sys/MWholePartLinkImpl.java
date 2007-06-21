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

/* $ProjectHeader: use 2-3-1-release.3 Wed, 02 Aug 2006 17:53:29 +0200 green $ */

package org.tzi.use.uml.sys;

import java.util.List;
import java.util.Set;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MAggregationKind;

/**
 * A link is an instance of an fAssociation.
 *
 * @version     $ProjectVersion: 2-3-1-release.3 $
 * @author      Duc-Hanh
 */

final class MWholePartLinkImpl implements MWholePartLink {
    private MLink delegatesLink; 

    /**
     * Constructor.     
     */
    MWholePartLinkImpl(MAssociation assoc, List objects) throws MSystemException {
        delegatesLink = new MLinkImpl(assoc,objects);
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
    public Set linkEnds() {        
        return delegatesLink.linkEnds();
    }

    /**
     * Returns the set of objects participating in this link.
     *
     * @return Set(MObject).
     */
    public Set linkedObjects() {        
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
    public Object source(){
        int aggregationKind = association().aggregationKind();
        if ( (aggregationKind == MAggregationKind.AGGREGATION) || 
	     (aggregationKind == MAggregationKind.COMPOSITION) ){            
            MObject[] temp = linkedObjectsAsArray();                 
            return temp[0];        
        }
        return null;
    }
    
    /**
     * Returns the target node of this edge. //child or part
     */
    public Object target(){
        int aggregationKind = association().aggregationKind();
        if ( (aggregationKind == MAggregationKind.AGGREGATION) || 
	     (aggregationKind == MAggregationKind.COMPOSITION) ){	    
            MObject[] temp = linkedObjectsAsArray();             
            return temp[1];            
        }
        return null;        
    }

    /**
     * Returns true if source and target of this edge connect the same Node.
     */
    public boolean isReflexive(){
        MObject[] temp = linkedObjectsAsArray();
        return temp[0] == temp[1];
    }    
    
}
