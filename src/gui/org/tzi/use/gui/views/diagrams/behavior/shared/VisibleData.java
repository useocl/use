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

package org.tzi.use.gui.views.diagrams.behavior.shared;

import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.events.Event;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Carsten Schlobohm
 *
 */
public class VisibleData {
    private Map<Event, Boolean> eventManagement = new ConcurrentHashMap<>();
    private Map<MObject, Boolean> objectManagement = new ConcurrentHashMap<>();
    private Map<MLink, Boolean> linkManagement = new ConcurrentHashMap<>();
    private Map<Class<? extends Event>, Boolean> eventTypeManagement = new ConcurrentHashMap<>();
    private Set<MObject> alwaysVisibleObjects = new HashSet<>();
    private Set<MLink> alwaysVisibleLinks = new HashSet<>();


    /**
     * Set a specific event visible
     * @param element to show
     */
    public void setEventVisible(Event element) {
        synchronized (this) {
            if (eventManagement.containsKey(element)) {
                eventManagement.put(element, true);
            }
        }
    }

    /**
     * Set a specific element hidden
     * @param element to hide
     */
    public void setEventHidden(Event element) {
        synchronized (this) {
            if (eventManagement.containsKey(element)) {
                eventManagement.put(element, false);
            }
        }
    }

    /**
     * Is event visible?
     * @param element to check
     * @return true if visible
     */
    public Boolean isEventVisible(Event element) {
        synchronized (this) {
            if (eventManagement.containsKey(element)) {
                return eventManagement.get(element);
            }
        }
        return true;
    }

    /**
     * Set all events visible
     */
    public void setAllEventsVisible() {
        synchronized (this) {
            for (Map.Entry<Event, Boolean> entry : eventManagement.entrySet()) {
                entry.setValue(true);
            }
        }
    }

    /**
     * Are all events visible
     * @return true if all events are visible
     */
    public Boolean areAllEventsVisible() {
        return !eventManagement.values().contains(false);
    }

    /**
     * Set a specific MObject hidden or hidden
     * @param element to change visibility
     * @param visible true to show or false to hide
     */
    public void changeObjectVisibility(MObject element, boolean visible) {
        synchronized (this) {
            if (objectManagement.containsKey(element) && !alwaysVisibleObjects.contains(element)) {
                objectManagement.put(element, visible);
            }
        }
    }

    /**
     * Check is specific element visible
     * @param element to check
     * @return true if visible
     */
    public Boolean isObjectVisible(MObject element) {
        synchronized (this) {
            if (objectManagement.containsKey(element)) {
                return objectManagement.get(element);
            }
        }
        return true;
    }

    /**
     * Change the visibility of an element
     * @param element to hide or show
     * @param visible true to show the element
     */
    public void changeLinkVisibility(MLink element, boolean visible) {
        synchronized (this) {
            if (linkManagement.containsKey(element) && !alwaysVisibleLinks.contains(element)) {
                linkManagement.put(element, visible);
            }
        }
    }

    /**
     * Is link visible?
     * @param element to check
     * @return true if visible
     */
    public Boolean isLinkVisible(MLink element) {
        synchronized (this) {
            if (linkManagement.containsKey(element)) {
                return linkManagement.get(element);
            }
        }
        return true;
    }

    /**
     * Set all MLinks visible
     */
    public void setAllLinksVisible() {
        synchronized (this) {
            for (Map.Entry<MLink, Boolean> entry : linkManagement.entrySet()) {
                entry.setValue(true);
            }
        }
    }

    /**
     * Set all MLink hidden
     */
    public void setAllLinksHidden() {
        synchronized (this) {
            for (Map.Entry<MLink, Boolean> entry : linkManagement.entrySet()) {
                entry.setValue(false);
            }
        }
    }

    /**
     * Set all MObject visible
     */
    public void setAllObjectsVisible() {
        synchronized (this) {
            for (Map.Entry<MObject, Boolean> entry : objectManagement.entrySet()) {
                entry.setValue(true);
            }
        }
    }

    /**
     * Set all MObject hidden
     */
    public void setAllObjectsHidden() {
        synchronized (this) {
            for (Map.Entry<MObject, Boolean> entry : objectManagement.entrySet()) {
                entry.setValue(false);
            }
        }
    }

    /**
     * @param mObjects which should be always visible
     * @param mLinks which should be always visible
     */
    public void addAlwaysVisibleElements(Collection<MObject> mObjects,
                                         Collection<MLink> mLinks) {
        synchronized (this) {
            alwaysVisibleObjects.addAll(mObjects);
            alwaysVisibleLinks.addAll(mLinks);
        }
    }

    /**
     * @param mObjects which should not always visible
     * @param mLinks which should not always visible
     */
    public void removeAlwaysVisibleElements(Collection<MObject> mObjects,
                                            Collection<MLink> mLinks) {
        synchronized (this) {
            alwaysVisibleObjects.removeAll(mObjects);
            alwaysVisibleLinks.removeAll(mLinks);
        }
    }

    /**
     * Cleared the selection of elements which are always visible
     */
    public void clearAlwaysVisibleElements() {
        synchronized (this) {
            alwaysVisibleObjects.clear();
            alwaysVisibleLinks.clear();
        }
    }

    /**
     * @param mObjects to check
     * @param mLinks to check
     * @return true if all elements are in the list
     */
    public boolean areElementsAlwaysVisible(Collection<MObject> mObjects,
                                            Collection<MLink> mLinks) {
        return alwaysVisibleLinks.containsAll(mLinks) &&
                alwaysVisibleObjects.containsAll(mObjects);
    }

    /**
     * @return true if a element is always visible
     */
    public boolean existAlwaysVisibleElements() {
        return !(alwaysVisibleLinks.isEmpty() && alwaysVisibleObjects.isEmpty());
    }

    /**
     * Returns all visible or all hidden MLinks
     * @param isVisible only visible or hidden elements?
     * @return  all visible or all hidden MLinks
     */
    public Collection<MLinkObject> getAllMLinkObjects(boolean isVisible) {
        Collection<MLinkObject> loList = new ArrayList<>();
        synchronized (this) {
            for (Map.Entry<MLink, Boolean> entry : linkManagement.entrySet()) {
                if ((entry.getValue() && isVisible) || !entry.getValue() && !isVisible) {
                    if (entry.getKey() instanceof MLinkObject) {
                        loList.add((MLinkObject) entry.getKey());
                    }
                }
            }
        }
        return loList;
    }

    /**
     * Returns all visible or all hidden MObjects
     * @param isVisible only visible or hidden elements?
     * @return  all visible or all hidden MObjects
     */
    public Collection<MObject> getAllMObjects(boolean isVisible) {
        Collection<MObject> loList = new ArrayList<>();
        synchronized (this) {
            for (Map.Entry<MObject, Boolean> entry : objectManagement.entrySet()) {
                if ((entry.getValue() && isVisible) || !entry.getValue() && !isVisible) {
                    loList.add(entry.getKey());
                }
            }
        }
        return loList;
    }

    /**
     * Are all MObjects visible?
     * @return true, if they are
     */
    public Boolean areAllObjectsVisible() {
        return !objectManagement.values().contains(false);
    }

    /**
     * Are all links visible?
     * @return true, if all links are visible
     */
    public Boolean areAllLinksVisible() {
        return !linkManagement.values().contains(false);
    }

    /**
     * Set a specific type visible
     * @param eventType type of event
     * @param visible to set visible pass the param true
     */
    public void setEventTypeVisible(Class<? extends Event> eventType, boolean visible) {
        eventTypeManagement.put(eventType, visible);
    }

    /**
     * Is a specific type of event visible
     * @param eventType event type to check
     * @return true, if visible
     */
    public boolean isEventTypeVisible(Class<? extends Event> eventType) {
        synchronized (this) {
            if (eventTypeManagement.containsKey(eventType)) {
                return eventTypeManagement.get(eventType);
            }
        }
        return true;
    }

    /**
     * Check are all types of events visible
     * @return true, if all types are visible
     */
    public boolean allEventTypesVisible() {
        boolean allClassesVisible = true;
        synchronized (this) {
            for (Map.Entry<Class<? extends Event>, Boolean> entry: eventTypeManagement.entrySet()) {
                if (!entry.getValue()) {
                    allClassesVisible = false;
                }
            }
        }
        return allClassesVisible;
    }

    /**
     * Set all types of events visible or hidden (types: create, set, ...)
     * @param visible hidden or visible?
     */
    public void setAllEventTypesVisible(boolean visible) {
        synchronized (this) {
            for (Map.Entry<Class<? extends Event>, Boolean> entry: eventTypeManagement.entrySet()) {
                entry.setValue(visible);
            }
        }
    }

    /**
     * Initialize the state
     * @param events events which can be set hidden or visible
     * @param eventTypes types of events which can be set hidden (set, create,...)
     * @param objects MObjects which can be set hidden
     * @param links links which can be set hidden
     */
    void initData(Collection<Event> events,
                         Collection<Class<? extends Event>> eventTypes,
                         Collection<MObject> objects,
                         Collection<MLink> links) {
        eventManagement.clear();
        for (Event element : events) {
            eventManagement.put(element, true);
        }

        for (Class<? extends Event> clazz : eventTypes) {
            eventTypeManagement.put(clazz, true);
        }

        for (MLink element : links) {
            linkManagement.put(element, true);
        }

        for (MObject element : objects) {
            if (!(element instanceof MLink)) {
                objectManagement.put(element, true);
            }
        }
    }

    /**
     * Checks if the link is known to VisibleData
     * @param link to check
     * @return true if already known
     */
    public boolean isLinkKnown(MLink link) {
        return linkManagement.keySet().contains(link);
    }

    /**
     * Checks if the object is known to VisibleData
     * @param object to check
     * @return true if already known
     */
    public boolean isObjectKnown(MObject object) {
        return objectManagement.keySet().contains(object);
    }

    /**
     * Update the local state of the mSystem with
     * the global state
     * All new elements will be set visible by default
     * @param events all current events in the mSystem
     * @param objects all current objects in the mSystem
     * @param links all current links in the mSystem
     */
    protected void update(Collection<Event> events,
                          Collection<MObject> objects,
                          Collection<MLink> links) {
        Collection<MObject> onlyMObject = new ArrayList<>();
        for (MObject object : objects)  {
            if (!(object instanceof MLink)) {
                onlyMObject.add(object);
            }
        }
        synchronized (this) {
            // Event update
            Collection<Event> eventsToAdd = new HashSet<>(events);
            eventsToAdd.removeAll(eventManagement.keySet());
            for (Event add : eventsToAdd) {
                eventManagement.put(add, true);
            }

            Collection<MObject> toAdd = new HashSet<>(onlyMObject);
            toAdd.removeAll(objectManagement.keySet());
            for (MObject add : toAdd) {
                objectManagement.put(add, true);
            }

            // Link update
            Set<MLink> keysLinks = new HashSet<>(linkManagement.keySet());
            keysLinks.removeAll(links);
            linkManagement.keySet().removeAll(keysLinks);

            Collection<MLink> linksToAdd = new HashSet<>(links);
            linksToAdd.removeAll(linkManagement.keySet());
            for (MLink add : linksToAdd) {
                linkManagement.put(add, true);
            }
        }
    }
}
