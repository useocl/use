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

package org.tzi.use.gui.util;

import java.util.Collection;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.event.EventListenerList;

import org.tzi.use.gui.views.diagrams.Selectable;

/** 
 * A selection maintains a collection of objects that implement 
 * the <code>Selectable</code>  interface. Clients may register for 
 * event notification when the selection changes.
 *
 * Note: All graphical presentation and updates have to be done by
 * client classes.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class Selection<T extends Selectable> implements Iterable<T> { 
 
    /**
     * The selected components
     */
	private Set<T> fSelection;
    
	/**
	 * The listeners
	 */
    protected EventListenerList fListenerList = new EventListenerList();

    /**
     * A ChangeEvent is used to notify interested listeners that the
     * state of the selection has changed.  
     */
    @SuppressWarnings("serial")
	public static class ChangeEvent extends EventObject {
        public ChangeEvent(Object source) {
            super(source);
        }
    }

    public interface ChangeListener extends EventListener {
        void stateChanged(Selection.ChangeEvent e);
    }

    public Selection() {
        fSelection = new LinkedHashSet<T>();
    }

    /**
     * Allow resize if only a single element is selected.
     */
    private void setResizeable() {
    	boolean allowed = (fSelection.size() == 1); 
		for (T s : fSelection) {
        	s.setResizeAllowed(allowed);
        }
    }
    
    public void add(T sel) {  	
    	fSelection.add(sel);
    	sel.setSelected(true);
    	setResizeable();
    	fireStateChanged();
    }

    
    public void addAll(Collection<? extends T> sel) {
        for (T s : sel) {
        	fSelection.add(s);
        	s.setSelected(true);
        }

    	setResizeable();
        fireStateChanged();
    }
    
    public void remove(T sel) {
        fSelection.remove(sel);
        sel.setSelected(false);
        setResizeable();
        fireStateChanged();
    }

    /**
     * Returns true if the specified object is currently selected.
     */
    public boolean isSelected(T sel) {
        return fSelection.contains(sel);
    }

    /**
     * Remove all selections.
     *
     * @return true if the selection was non-empty before.
     */
    public boolean clear() {
        boolean res = ! fSelection.isEmpty();
        
        for (Selectable sel : fSelection) {
            sel.setSelected(false);
        }
        
        fSelection.clear();
        
        if (res)
            fireStateChanged();
        
        return res;
    }

    /**
     * Returns the number of selected elements.
     */
    public int size() {
        return fSelection.size();
    }

    /**
     * Returns true if no element is selected.
     */
    public boolean isEmpty() {
        return fSelection.isEmpty();
    }

    /**
     * Returns an iterator over all selected components.
     *
     * @return iterator over <code>Selectable</code> objects.
     */
    public Iterator<T> iterator() {
        return fSelection.iterator();
    }

    public void addChangeListener(ChangeListener l) {
        fListenerList.add(ChangeListener.class, l);
    }
    
    public void removeChangeListener(ChangeListener l) {
        fListenerList.remove(ChangeListener.class, l);
    }

    /*
     * Notify all listeners that have registered interest for
     * notification on this event type.  The event instance 
     * is lazily created using the parameters passed into 
     * the fire method.
     * @see EventListenerList
     */
    protected void fireStateChanged() {
        // Guaranteed to return a non-null array
        Object[] listeners = fListenerList.getListenerList();
        ChangeEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i >= 0; i -= 2) {
            if (listeners[i] == ChangeListener.class ) {
                // Lazily create the event:
                if (e == null) {
                    e = new ChangeEvent(this);
                }
                ((ChangeListener) listeners[i+1]).stateChanged(e);
            }          
        }
    }
}

