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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.StringUtil;

/**
 * A link set contains instances of an association.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class MLinkSet {
    private final MAssociation fAssociation; // The type of all links of this set.
    private Set<MLink> fLinks;

    private static class CacheEntry {
        final MAssociationEnd end;
        final MObject         object;
        final int 			  hashCode;
        final List<Value>	  qualifiers;
        
        public CacheEntry(MAssociationEnd end, MObject object, List<Value> qualifiers) {
            this.end = end;
            this.object = object;
            // We set qualifier to null, if no elements are given
            // This makes comparison easier.
            this.qualifiers = (qualifiers != null && qualifiers.size() == 0) ? null : qualifiers;
            hashCode = end.hashCode() + 19 * object.hashCode() + (this.qualifiers == null ? 0 : 23 * this.qualifiers.hashCode());
        }
        
        public final int hashCode() {
            return hashCode;
        }
        
        public final boolean equals(Object o) {
            if (this == o) {
            	return true;
            } else if (o instanceof CacheEntry) {
            	CacheEntry e = (CacheEntry)o;
            
	            return e.end == end && 
	            	   e.object.equals( object ) && 
	            	   (qualifiers == null ? e.qualifiers == null : qualifiers.equals(e.qualifiers));
            } else {
            	return false;
            }
        }
        
        @Override
        public String toString() {
			return end.nameAsRolename()
					+ ":"
					+ object.name()
					+ (qualifiers != null ? "["
							+ StringUtil.fmtSeq(qualifiers, ",") + "]" : "");
        }
    }
    
    private Map<CacheEntry, Set<MLink>> selectCache; 
    
    MLinkSet(MAssociation assoc) {
        fAssociation = assoc;
        fLinks = new HashSet<MLink>();
        selectCache = new HashMap<CacheEntry, Set<MLink>>();
    }

    /**
     * Copy constructor.
     */
    MLinkSet(MLinkSet x) {
        fAssociation = x.fAssociation;
        fLinks = new HashSet<MLink>();
        fLinks.addAll(x.fLinks);
        selectCache = new HashMap<CacheEntry, Set<MLink>>();
        
        for (MLink link : x.fLinks) {
            add(link);
        }
    }

    /**
     * Selects all links whose link ends at <code>aend</code> connect
     * <code>obj</code> with the given qualifier values.
     * The Set is immutable.
     *
     * @return Set(MLink) An immutable <code>Set</code> of the corresponding links. 
     */
    Set<MLink> select(MAssociationEnd aend, MObject obj, List<Value> qualifierValues) {
        Set<MLink> res;
        if (selectCache != null) {
            CacheEntry e = new CacheEntry(aend, obj, qualifierValues);
            res = selectCache.get(e);
            if (res==null) res = Collections.emptySet();
            
            return res;
        }
        
        res = new HashSet<MLink>();

        for(MLink link : fLinks) {
            MLinkEnd linkEnd = link.linkEnd(aend);
            if (linkEnd.object().equals(obj) && linkEnd.qualifierValuesEqual(qualifierValues))
                res.add(link);
        }
        
        return res;
    }

    /**
     * Selects all links whose link ends at <code>aend</code> connect
     * <code>obj</code>.
     *
     * @return Set(MLink) A <code>Set</code> of the corresponding links. 
     */
    Set<MLink> select(MAssociationEnd aend, MObject obj) {
        Set<MLink> res;
        res = new HashSet<MLink>();

        for(MLink link : fLinks) {
            MLinkEnd linkEnd = link.linkEnd(aend);
            if (linkEnd.object().equals(obj))
                res.add(link);
        }
        
        return res;
    }
    
    /**
     * Removes all links whose link ends at <code>aend</code> connect
     * <code>obj</code>. 
     * <strong>Note:</strong> The <code>selectCache</code> is not changed. Use {@link MLinkSet#clearCache} afterwards.
     * @return Set(MLink) the set of removed links
     */
    Set<MLink> removeAll(MAssociationEnd aend, MObject obj) {
        Set<MLink> res = new HashSet<MLink>();
        Iterator<MLink> it = fLinks.iterator();
        
        while (it.hasNext() ) {
            MLink link = it.next();
            MLinkEnd linkEnd = link.linkEnd(aend);
            
            if (linkEnd.object().equals(obj) ) {
                res.add(link);
                it.remove();
            }
        }
        
        return res;
    }

    /**
     * Removes all links to object <code>obj</code> from cache.
     * @param obj
     */
	public void clearCache(MObject obj) {
		
        for (Iterator<Map.Entry<CacheEntry, Set<MLink>>> entryIter = selectCache.entrySet().iterator(); entryIter.hasNext(); ) {
        	MLink link;
        	Map.Entry<CacheEntry, Set<MLink>> entry = entryIter.next();
        	if (entry.getKey().object.equals(obj)) {
        		entryIter.remove();
        	} else {
	        	for (Iterator<MLink> linkIter = entry.getValue().iterator(); linkIter.hasNext();) {
	        		link = linkIter.next();
	        		if (link.linkedObjects().contains(obj)) {
	        			linkIter.remove();
	        		}
	        	}
        	}
        }
	}


    /**
     * Returns the association describing this link set.
     */
    public MAssociation association() {
        return fAssociation;
    }

    /**
     * Returns the number of links in this set.
     */
    public int size() {
        return fLinks.size();
    }

    /**
     * Returns the set of links in this set.
     *
     * @return Set(MLink)
     */
    public Set<MLink> links() {
        return fLinks;
    }

    /**
     * Checks whether a link is in this set.
     *
     * @return true if the link is in this set.
     */
    boolean contains(MLink link) {
        return fLinks.contains(link);
    }

    /**
     * Adds a link to this link set.
     *
     * @return true if the link set did not already contain the link.
     */
    boolean add(MLink link) {    	
        for (MLinkEnd end : link.linkEnds()) {
            CacheEntry e = new CacheEntry(end.associationEnd(), end.object(), end.getQualifierValues());
            Set<MLink> links = selectCache.get(e);
            
            if (links == null) {
                links = new HashSet<MLink>();
                selectCache.put(e, links);
            }
            
            links.add(link);
            
            // we add two entries for ends with qualifiers
            if (end.hasQualifiers()) {
            	e = new CacheEntry(end.associationEnd(), end.object(), null);
                links = selectCache.get(e);
                
                if (links == null) {
                    links = new HashSet<MLink>();
                    selectCache.put(e, links);
                }
                
                links.add(link);
            }
        }
        
        return fLinks.add(link);
    }

    void addAll(MLinkSet linkSet) {
    	if (linkSet == null)
    		return;
    	
    	for (MLink newLink : linkSet.links()) {
    		add(newLink);
    	}
    }
    
    /**
     * Returns true if there is a link connecting the given set of
     * objects with the provided qualifier values.  
     */
    public boolean hasLinkBetweenObjects(List<MObject> objects, List<List<Value>> qualifierValues) {
        return linkBetweenObjects(objects, qualifierValues) != null;
    }

    /**
     * Returns true if there is a link connecting the given set of
     * objects without taking qualifier values into account
     */
    public boolean hasLinkBetweenObjects(MObject[] objects) {
        return !linkBetweenObjects(Arrays.asList(objects)).isEmpty();
    }
    
    /**
     * Returns all links connecting the given set of
     * objects. If no link exists an empty set is returned.  
     */
	public Set<MLink> linkBetweenObjects(List<MObject> objects) {
		Set<MLink> result = new HashSet<MLink>();
		
		for (MLink link : fLinks) {
            if (link.linkedObjects().equals(objects)) {
            	result.add(link);
            }
        }
		
		return result;
	}
	
    /**
     * Returns the link if there is a link connecting the given set of
     * objects, otherwise null is returned.  
     */
    public MLink linkBetweenObjects(List<MObject> objects, List<List<Value>> qualifierValues) {
        boolean equal = true;
        
    	for (MLink link : fLinks) {
            if (link.linkedObjects().equals(objects)) {
            	if (link.association().hasQualifiedEnds()) {
	            	equal = true; // true, because if no qualifiers exists it is the correct link 
	            	for (int index = 0; index < link.association().associationEnds().size(); ++index) {
	            		MAssociationEnd end = link.association().associationEnds().get(index);
	            		if (!link.linkEnd(end).qualifierValuesEqual(qualifierValues.get(index)))
	            			equal = false;
	            	}
	            	
	            	if (equal)
	            		return link;
            	} else {
            		return link;
            	}
            }
        }
        
        return null;
    }
    
    /**
     * Returns true if there is a link connecting the objects
     * in the given sequence with the given qualifier values.
     * @throws MSystemException objects do not conform to the association ends.
     */
    public boolean hasLink(List<MObject> objects, List<List<Value>> qualifierValues) throws MSystemException {
        return contains(new MLinkImpl(fAssociation, objects, qualifierValues));
    }

    /**
     * Removes a link from this link set.
     *
     * @return true if the link set did contain the link.
     */
    boolean remove(MLink link) {
        for (MLinkEnd end : link.linkEnds()) {
            CacheEntry e = new CacheEntry(end.associationEnd(), end.object(), end.getQualifierValues());
            Set<MLink> links = selectCache.get(e);
            
            if (links != null) {
                links.remove(link);
                if (links.isEmpty()) {
                    selectCache.remove(e);
                }
            }
            
            if (link.association().hasQualifiedEnds()) {
            	e = new CacheEntry(end.associationEnd(), end.object(), null);
                links = selectCache.get(e);
                
                if (links != null) {
                    links.remove(link);
                    if (links.isEmpty()) {
                        selectCache.remove(e);
                    }
                }
            }
        }
        return fLinks.remove(link);
    }
}