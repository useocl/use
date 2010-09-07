/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

package org.tzi.use.util.soil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.StateChangeEvent;
import org.tzi.use.uml.sys.MSystemState.DeleteObjectResult;
import org.tzi.use.uml.sys.soil.MStatement;


/**
 * Holds information about what has changed in a {@link MSystemState system}
 * during the evaluation of a {@link MStatement Soil statement}.
 * <p>
 * The {@code add...} methods check for conflicting situations, e.g. if the same
 * object is added to both the new objects and the deleted objects, it is 
 * removed from this altogether.
 * <p>
 * For {@link MLinkObject link objects} it doesn't matter whether you call
 * {@code add...Object}, {@code add...Link} or {@code add...LinkObject}, since
 * in either case the link object is treated as both an object and a link. To
 * save yourself some headache, it is recommended to use the 
 * {@code add...LinkObject} versions, if you know you deal with a link object.
 *   
 * @author Daniel Gent
 */
public class StateDifference {
	/** objects created during evaluation */
	private Set<MObject> fNewObjects = new HashSet<MObject>();
	/** objects deleted during evaluation */
	private Set<MObject> fDeletedObjects = new HashSet<MObject>();
	/** objects modified during evaluation */
	private Set<MObject> fModifiedObjects = new HashSet<MObject>();
	/** links created during evaluation */
    private Set<MLink> fNewLinks = new HashSet<MLink>();
    /** links deleted during evaluation */
    private Set<MLink> fDeletedLinks = new HashSet<MLink>();
    

    /**
     * restores the initial state of this containing nothing
     */
    public void clear() {
    	fNewObjects.clear();
    	fDeletedObjects.clear();
    	fModifiedObjects.clear();
    	fNewLinks.clear();
    	fDeletedLinks.clear();
    }
    
    
    /**
     * returns true if there are no state changes
     * 
     * @return true if there are no state changes, false else
     */
    public boolean isEmpty() {
    	return fNewObjects.isEmpty() 
    	  && fDeletedObjects.isEmpty()
    	  && fModifiedObjects.isEmpty()
    	  && fNewLinks.isEmpty()
    	  && fDeletedLinks.isEmpty();
    }
    
    
    /**
     * handles the object strictly as an object, i.e. no check whether object
     * is a link object
     * 
     * @param object a new object
     */
    private void addNewObjectOnly(MObject object) {
    	boolean wasDeleted = fDeletedObjects.contains(object);
    	
    	remove(object);
    	
    	if (wasDeleted) {
    		// might have been modified before deleting
    		fModifiedObjects.add(object);
    	} else {
    		fNewObjects.add(object);
    	}
    }
   
    
    /**
     * adds a new object
     * <p>
     * if {@code object} is among the deleted objects,
     * it is deleted from there and nothing else is done. if it is 
     * among the modified objects, it is deleted from there and added to the
     * new objects.<br>
     * in every other case it is added to the new objects.
     * <p>
     * if {@code object} is a link object, it is also added to the new links
     * following the semantics of {@link #addNewLink(MLink) addNewLink}
     * 
     * @param object a new object
     */
    public void addNewObject(MObject object) {
    	addNewObjectOnly(object);
    	
    	if (object instanceof MLinkObject) {
    		addNewLinkOnly((MLinkObject)object);
    	}
    }
    
   
    /**
     * adds multiple new objects
     * <p>
     * calling this is equivalent to repeatedly calling 
     * {@link #addNewObject(MObject) addNewObject}
     * 
     * @param objects some new objects
     */
    public void addNewObjects(Collection<MObject> objects) {
    	for (MObject object : objects) {
    		addNewObject(object);
    	}
    }
    
    
    /**
     * adds multiple new objects
     * <p>
     * calling this is equivalent to repeatedly calling 
     * {@link #addNewObject(MObject) addNewObject}
     * 
     * @param objects some new objects
     */
    public void addNewObjects(MObject... objects) {
    	for (MObject object : objects) {
    		addNewObject(object);
    	}
    }
    
    
    /**
     * adds a new link object
     * <p>
     * the object is added to the new objects as if 
     * {@link #addNewObject(MObject) addNewObject} had been called, and to the 
     * new links as if {@link #addNewLink(MLink) addNewLink} had been called
     * 
     * @param linkObject a new link object
     */
    public void addNewLinkObject(MLinkObject linkObject) {
    	addNewLinkOnly(linkObject);
    	addNewObjectOnly(linkObject);
    }
    
    
    /**
     * adds multiple new link objects
     * <p>
     * calling this is equivalent to repeatedly calling 
     * {@link #addNewLinkObject(MLinkObject) addNewLinkObject}
     * 
     * @param linkObjects some new objects
     */
    public void addNewLinkObjects(Collection<MLinkObject> linkObjects) {
    	for (MLinkObject linkObject : linkObjects) {
    		addNewLinkObject(linkObject);
    	}
    }
    
    
    /**
     * adds multiple new link objects
     * <p>
     * calling this is equivalent to repeatedly calling 
     * {@link #addNewLinkObject(MLinkObject) addNewLinkObject}
     * 
     * @param linkObjects some new objects
     */
    public void addNewLinkObjects(MLinkObject... linkObjects) {
    	for (MLinkObject linkObject : linkObjects) {
    		addNewLinkObject(linkObject);
    	}
    }
    
    
    /**
     * handles the object strictly as an object, i.e. no check whether object
     * is a link object
     * 
     * @param object a deleted object
     */
    private void addDeletedObjectOnly(MObject object) {
    	boolean wasNew = fNewObjects.contains(object);
    	
    	remove(object);
    	
    	if (!wasNew) {
    		fDeletedObjects.add(object);
    	}
    }
    
    
    /**
     * adds a deleted object
     * <p>
     * if {@code object} is among the new objects,
     * it removed there, and nothing else is done. if it is among the modified
     * objects it gets removed from there and added to the deleted objects.<br>
     * in every other case it gets added to the deleted objects. 
     * <p>
     * if {@code object} is a link object, it is also added to the new links
     * following the semantics of {@link #addDeletedLink(MLink) addDeletedLink}
     * 
     * @param object a deleted object
     */
    public void addDeletedObject(MObject object) {
    	addDeletedObjectOnly(object);
    	
    	if (object instanceof MLinkObject) {
    		addDeletedLinkOnly((MLinkObject)object);
    	}
    }
    
    
    /**
     * adds multiple deleted objects
     * <p>
     * calling this is equivalent to repeatedly calling 
     * {@link #addDeletedObject(MObject) addDeletedObject}
     * 
     * @param objects some deleted objects
     */
    public void addDeletedObjects(Collection<MObject> objects) {
    	for (MObject object : objects) {
    		addDeletedObject(object);
    	}
    }

    
    /**
     * adds multiple deleted objects
     * <p>
     * calling this is equivalent to repeatedly calling 
     * {@link #addDeletedObject(MObject) addDeletedObject}
     * 
     * @param objects some deleted objects
     */
    public void addDeletedObjects(MObject... objects) {
    	for (MObject object : objects) {
    		addDeletedObject(object);
    	}
    }
    
    
    /**
     * adds a deleted link object
     * <p>
     * the object is added to the deleted objects as if 
     * {@link #addDeletedObject(MObject) addDeletedObject} had been called, and 
     * to the deleted links as if {@link #addDeletedLink(MLink) addDeletedLink} 
     * had been called
     * 
     * @param linkObject a deleted link object
     */
    public void addDeletedLinkObject(MLinkObject linkObject) {
    	addDeletedLinkOnly(linkObject);
    	addDeletedObjectOnly(linkObject);
    }
    
    
    /**
     * adds multiple deleted link objects
     * <p>
     * calling this is equivalent to repeatedly calling 
     * {@link #addDeletedLinkObject(MLinkObject) addDeletedLinkObject}
     * 
     * @param linkObjects some deleted objects
     */
    public void addDeletedLinkObjects(Collection<MLinkObject> linkObjects) {
    	for (MLinkObject linkObject : linkObjects) {
    		addDeletedLinkObject(linkObject);
    	}
    }
    
    
    /**
     * adds multiple deleted link objects
     * <p>
     * calling this is equivalent to repeatedly calling 
     * {@link #addDeletedLinkObject(MLinkObject) addDeletedLinkObject}
     * 
     * @param linkObjects some deleted objects
     */
    public void addDeletedLinkObjects(MLinkObject... linkObjects) {
    	for (MLinkObject linkObject : linkObjects) {
    		addDeletedLinkObject(linkObject);
    	}
    }
    
    
    /**
     * adds a modified object
     * <p>
     * if {@code object} is among the new objects,
     * nothing is done. if it is among the deleted objects, it is removed
     * from there and added to the modified objects.<br>
     * in every other case it is added to the modified objects.
     * 
     * @param object a modified object
     */
    public void addModifiedObject(MObject object) {
    	boolean wasNew = fNewObjects.contains(object);
    	
    	remove(object);
    	
    	if (wasNew) {
    		fNewObjects.add(object);
    	} else {
    		fModifiedObjects.add(object);
    	}
    }
    
    
    /**
     * adds multiple modified objects
     * <p>
     * calling this is equivalent to repeatedly calling 
     * {@link #addModifiedObject(MObject) addModifiedObject}
     * 
     * @param objects some modified objects
     */
    public void addModifiedObjects(Collection<MObject> objects) {
    	for (MObject object : objects) {
    		addModifiedObject(object);
    	}
    }
    
    
    /**
     * adds multiple modified objects
     * <p>
     * calling this is equivalent to repeatedly calling 
     * {@link #addModifiedObject(MObject) addModifiedObject}
     * 
     * @param objects some modified objects
     */
    public void addModifiedObjects(MObject... objects) {
    	for (MObject object : objects) {
    		addModifiedObject(object);
    	}
    }
    
    
    /**
     * handles the link strictly as a link, i.e. no check whether object
     * is a link object
     * 
     * @param link a new link
     */
    private void addNewLinkOnly(MLink link) {
    	boolean wasDeleted = fDeletedLinks.remove(link);
    	
    	remove(link);
    	
    	if (!wasDeleted) {
    		fNewLinks.add(link);
    	}
    }
    
    
    /**
     * adds a new link
     * <p>
     * if {@code link} is among the deleted links, it is deleted from
     * there and nothing else happens. if not, it is added to the new links.
     * <p>
     * if {@code link} is a link object, it is also added to the new objects
     * following the semantics of {@link #addNewObject(MLink) addNewObject}
     * 
     * @param link a new link
     */
    public void addNewLink(MLink link) {
    	addNewLinkOnly(link);
    	
    	if (link instanceof MLinkObject) {
    		addNewObjectOnly((MLinkObject)link);
    	}
    }
    
    
    /**
     * adds multiple new links
     * <p>
     * calling this is equivalent to repeatedly calling 
     * {@link #addNewLink(MObject) addNewLink}
     * 
     * @param links some new links
     */
    public void addNewLinks(Collection<MLink> links) {
    	for (MLink link : links) {
    		addNewLink(link);
    	}
    }
    
    
    /**
     * adds multiple new links
     * <p>
     * calling this is equivalent to repeatedly calling 
     * {@link #addNewLink(MObject) addNewLink}
     * 
     * @param links some new links
     */
    public void addNewLinks(MLink... links) {
    	for (MLink link : links) {
    		addNewLink(link);
    	}
    }
    
    
    /**
     * handles the link strictly as a link, i.e. no check whether object
     * is a link object
     * 
     * @param link a new link
     */
    private void addDeletedLinkOnly(MLink link) {
    	boolean wasNew = fNewLinks.remove(link);
    	
    	remove(link);
    	
    	if (!wasNew) {
    		fDeletedLinks.add(link);
    	}
    }
    
    
    /**
     * adds a deleted link
     * <p>
     * if {@code link} is among the new links, it is deleted from
     * there and nothing else happens. if not, it is added to the deleted links.
     * <p>
     * if {@code link} is a link object, it is also added to the deleted objects
     * following the semantics of 
     * {@link #addDeletedObject(MLink) addDeletedObject}
     * 
     * @param link a deleted link
     */
    public void addDeletedLink(MLink link) {
    	addDeletedLinkOnly(link);
    	
    	if (link instanceof MLinkObject) {
    		addDeletedObjectOnly((MLinkObject)link);
    	}
    }
    
    
    /**
     * adds multiple deleted links
     * <p>
     * calling this is equivalent to repeatedly calling 
     * {@link #addDeletedLink(MObject) addDeletedLink}
     * 
     * @param links some deleted links
     */
    public void addDeletedLinks(Collection<MLink> links) {
    	for (MLink link : links) {
    		addDeletedLink(link);
    	}
    }
    
    
    /**
     * adds multiple deleted links
     * <p>
     * calling this is equivalent to repeatedly calling 
     * {@link #addDeletedLink(MObject) addDeletedLink}
     * 
     * @param links some deleted links
     */
    public void addDeletedLinks(MLink... links) {
    	for (MLink link : links) {
    		addDeletedLink(link);
    	}
    }
    
    
    /**
     * extracts information about deleted objects and links from the
     * delete result, and adds them to this via the
     * {@link #addDeletedObject(MObject) addDeletedObject} and 
     * {@link #addDeletedLink(MLink) addDeletedLink} methods.
     * 
     * @param dor result of a delete operation on a MSystemState
     */
    public void addDeleteResult(DeleteObjectResult dor) {
    	addDeletedObjects(dor.getRemovedObjects());
    	addDeletedLinks(dor.getRemovedLinks());
    }
    
    
    /**
     * TODO
     * @param stateDiff
     */
    public void addStateDifference(StateDifference stateDiff) {
    	addNewObjects(stateDiff.fNewObjects);
    	addModifiedObjects(stateDiff.fModifiedObjects);
    	addNewLinks(stateDiff.fNewLinks);
    	addDeletedObjects(stateDiff.fDeletedObjects);
    	addDeletedLinks(stateDiff.fDeletedLinks);
    }
    
    
    /**
     * removes an object from each set of objects
     * 
     * @param object the object to remove
     */
    private void remove(MObject object) {
    	fNewObjects.remove(object);
    	fModifiedObjects.remove(object);
    	fDeletedObjects.remove(object);
    }
    
    
    /**
     * removes a link from each set of links
     * 
     * @param link the link to remove
     */
    private void remove(MLink link) {
    	fNewLinks.remove(link);
    	fDeletedLinks.remove(link);
    }
    
    
    /**
     * returns the set of new objects
     * 
     * @return the set of new objects
     */
    public Set<MObject> getNewObjects() {
		return fNewObjects;
	}


    /**
     * returns the set of deleted objects
     * 
     * @return the set of deleted objects
     */
	public Set<MObject> getDeletedObjects() {
		return fDeletedObjects;
	}


	/**
	 * returns the set of modified objects
	 * 
	 * @return the set of modified objects
	 */
	public Set<MObject> getModifiedObjects() {
		return fModifiedObjects;
	}


	/**
	 * returns the set of new links
	 * 
	 * @return the set of new links
	 */
	public Set<MLink> getNewLinks() {
		return fNewLinks;
	}


	/**
	 * returns the set of deleted links
	 * 
	 * @return the set of deleted links
	 */
	public Set<MLink> getDeletedLinks() {
		return fDeletedLinks;
	}


	/**
	 * add the information stored in this object to the
	 * {@code StateChangeEvent}.
	 * 
	 * @param sce the event to add information to
	 */
	public void fillStateChangeEvent(StateChangeEvent sce) {
    	sce.addNewObjects(fNewObjects);
    	sce.addDeletedObjects(fDeletedObjects);
    	sce.addModifiedObjects(fModifiedObjects);
    	sce.addNewLinks(fNewLinks);
    	sce.addDeletedLinks(fDeletedLinks);
    }
    
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("nO: ");
    	sb.append(fNewObjects);
    	sb.append("\ndO: ");
    	sb.append(fDeletedObjects);
    	sb.append("\nmO: ");
    	sb.append(fModifiedObjects);
    	sb.append("\nnL: ");
    	sb.append(fNewLinks);
    	sb.append("\ndL: ");
    	sb.append(fDeletedLinks);
    	
    	return sb.toString();
    }
}
