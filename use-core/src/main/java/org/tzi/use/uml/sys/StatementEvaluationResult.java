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

package org.tzi.use.uml.sys;

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.uml.sys.events.Event;
import org.tzi.use.uml.sys.soil.MSequenceStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.soil.StateDifference;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;

/**
 * Captures additional information about the execution of a statement, 
 * for example to realize undo / redo and to feed state change listeners. 
 *
 * @author Fabian Buettner
 * @author Daniel Gent
 * @author Lars Hamann
 */
public class StatementEvaluationResult {
	private MStatement fEvaluatedStatement;
	private StateDifference fStateDifference = new StateDifference();
	private MSequenceStatement fInverseStatement = new MSequenceStatement();
	private EvaluationFailedException fException = null;
	private List<Event> fEvents = new ArrayList<Event>();
	

	/**
	 * Constructs a new <code>StatementEvaluationResult</code> for the given statement.
	 * @param statement The (top-level) statement that is / has been executed
	 */
	public StatementEvaluationResult(MStatement statement) {
		fEvaluatedStatement = statement;
	}
	

	/**
	 * <code>true</code>, if no exception was stored in this result.
	 * @return
	 */
	public boolean wasSuccessfull() {
		return fException == null;
	}
	
	
	/**
	 * Returns the executed statement which calculated this result.
	 * @return The executed <code>Statement</code>.
	 */
	public MStatement getEvaluatedStatement() {
		return fEvaluatedStatement;
	}
	
	
	/**
	 * Returns a {@link StateDifference} object that contains
	 * information about the changes made to calculate this statement result. 
	 * @return A <code>StateDifference</code> object including information about the state changes.
	 */
	public StateDifference getStateDifference() {
		return fStateDifference;
	}
	
	
	/**
	 * Returns the <code>Statement</code> to undo this result.
	 * @return The <code>Statement</code> to execute to undo this result.
	 */
	public MSequenceStatement getInverseStatement() {
		return fInverseStatement;
	}


	/**
	 * Adds the <code>statement</code> to the list of inverse statements.
	 * @param statement The <code>Statement</code> to append.
	 */
	public void prependToInverseStatement(MStatement statement) {
		fInverseStatement.prependStatement(statement);
	}
	
	
	/**
	 * Returns the exception which occurred during the calculation of this
	 * result, if any.
	 * @return The <code>Exception</code> that might have occurred.  
	 */
	public EvaluationFailedException getException() {
		return fException;
	}
	
	
	/**
	 * Stores an exception which was raised while calculating the result.
	 * @param exception The <code>Exception</code> to store.
	 */
	public void setException(EvaluationFailedException exception) {
		fException = exception;
	}
	
	
	/**
	 * The list of events raised for this result.
	 * @return The <code>List</code> of events.
	 */
	public List<Event> getEvents() {
		return fEvents;
	}
	
	
	/**
	 * Appends an event to the list of events raised for this result.
	 * @param event The <code>Event</code> to append. <code>null</code>-values are ignored.
	 */
	public void appendEvent(Event event) {
		if (event == null) return;
		
		fEvents.add(event);
	}
}
