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

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.List;

/**
 * Class providing information about a state change. Used to gather
 * change information from commands. A list of StateChangeInfo objects
 * fully documents the differences between two system states.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class StateChangeEvent extends EventObject {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6548177260846525532L;
	
	private List<MObject> fNewObjects;
    private List<MObject> fDeletedObjects;
    private List<MObject> fModifiedObjects;
    private List<MLink> fNewLinks;
    private List<MLink> fDeletedLinks;

    /**
     * Constructs a StateChangeEvent object.
     */
    public StateChangeEvent(Object source) {
        super(source);
        fNewObjects = new ArrayList<MObject>();
        fDeletedObjects = new ArrayList<MObject>();
        fModifiedObjects = new ArrayList<MObject>();
        fNewLinks = new ArrayList<MLink>();
        fDeletedLinks = new ArrayList<MLink>();
    }

    public List<MObject> getNewObjects() {
        return fNewObjects;
    }

    public List<MObject> getDeletedObjects() {
        return fDeletedObjects;
    }

    public List<MObject> getModifiedObjects() {
        return fModifiedObjects;
    }

    public List<MLink> getNewLinks() {
        return fNewLinks;
    }

    public List<MLink> getDeletedLinks() {
        return fDeletedLinks;
    }

    /**
     * Returns true if the number of objects or links has changed.
     */
    public boolean structureHasChanged() {
        return ! fNewObjects.isEmpty() 
            || ! fDeletedObjects.isEmpty() 
            || ! fNewLinks.isEmpty() 
            || ! fDeletedLinks.isEmpty();
    }

    public void addNewObject(MObject obj) {
        fNewObjects.add(obj);
    }
    
    public void addNewObjects(Collection<MObject> objects) {
    	fNewObjects.addAll(objects);
    }
    
    public void addDeletedObject(MObject obj) {
        fDeletedObjects.add(obj);
    }
    
    public void addDeletedObjects(Collection<MObject> objects) {
    	fDeletedObjects.addAll(objects);
    }

    public void addModifiedObject(MObject obj) {
        fModifiedObjects.add(obj);
    }
    
    
    public void addModifiedObjects(Collection<MObject> objects) {
    	fModifiedObjects.addAll(objects);
    }

    public void addNewLink(MLink link) {
        fNewLinks.add(link);
    }
    
    
    public void addNewLinks(Collection<MLink> links) {
    	fNewLinks.addAll(links);
    }

    public void addDeletedLink(MLink link) {
        fDeletedLinks.add(link);
    }
    
    public void addDeletedLinks(Collection<MLink> links) {
    	fDeletedLinks.addAll(links);
    }
    
    public String toString() {
        return "new objects: " + fNewObjects +
            ", deleted objects: " + fDeletedObjects +
            ", modified objects: " + fModifiedObjects +
            ", new links: " + fNewLinks +
            ", deleted links: " + fDeletedLinks;
    }
}