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

package org.tzi.use.main;

import java.util.EventListener;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.soil.MStatement;

/**
 * A session manages a system and its model. System and model may
 * change during a session. Interested listeners are then notified.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class Session {
    private MSystem fSystem;
    private List<ChangeListener> fListenerStateChange;
    private List<EvaluatedStatementListener> fListenerEvaluatedStatement;

    public class EvaluatedStatement extends EventObject {
		private static final long serialVersionUID = 1L;

		public EvaluatedStatement(Object source) {
			super(source);

		}
    }
    
    public interface EvaluatedStatementListener extends EventListener {
    	void evaluatedStatement(EvaluatedStatement event);
    }
    
    
    
    public Session() {
        fListenerEvaluatedStatement = new LinkedList<EvaluatedStatementListener>();
        fListenerStateChange = new LinkedList<ChangeListener>();
    }

    /** 
     * Set application state for new system. The system parameter may
     * be null.
     */
    public void setSystem(MSystem system) {
        fSystem = system;
        fireStateChanged();
    }

    /** 
     * Returns true if we have a system.
     */
    public boolean hasSystem() {
        return fSystem != null;
    }

    public void evaluatedStatement(MStatement statement) {
    	fireEvaluatedStatement(statement);
    }

    /**
     * Resets the system to its initial state.
     */
    public void reset() {
        if (fSystem != null ) {
            fSystem.reset();
            fireStateChanged();
        }
    }

    /** 
     * Returns the current system. 
     *
     * @throws IllegalStateException if no system available
     */
    public MSystem system() {
        if (fSystem == null )
            throw new IllegalStateException("no system");
        return fSystem;
    }

    public void addChangeListener(ChangeListener l) {
        fListenerStateChange.add(l);
    }
    
    public void removeChangeListener(ChangeListener l) {
    	fListenerStateChange.remove(l);
    }

    public void addEvaluatedStatementListener(EvaluatedStatementListener l) {
    	fListenerEvaluatedStatement.add(l);
    }
    
    public void removeEvaluatedStatementListener(EvaluatedStatementListener l) {
    	fListenerEvaluatedStatement.remove(l);
    }
    
    /**
     * Notify all listeners that have registered interest for
     * notification on this event type.  
     */
    private void fireStateChanged() {
        // Process the listeners and notifying them
        ChangeEvent event = new ChangeEvent(this);
        
        for (ChangeListener l : this.fListenerStateChange) {
            l.stateChanged(event);
        }
    }
    
    /**
     * Notifies listeners, that a statement has been evaluated.
     * @param statement
     */
    private void fireEvaluatedStatement(MStatement statement) {
    	EvaluatedStatement event = new EvaluatedStatement(this);

    	for (EvaluatedStatementListener l : this.fListenerEvaluatedStatement) {
        	l.evaluatedStatement(event);
    	}
    }
}
