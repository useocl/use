/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2014 Mark Richters, University of Bremen
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

package org.tzi.use.uml.sys.events;

import org.tzi.use.uml.sys.events.tags.EventContext;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.soil.StateDifference;

/**
 * Payload for the event raised after a
 * statement has been executed.
 * It contains the complete changes made 
 * by the statement. 
 * @author Lars Hamann
 * @author Frank Hilken
 *
 */
public class StatementExecutedEvent extends Event {

	private final MStatement statement;
	
	private final StateDifference changes;

	/**
	 * Constructs a new StatementExecutedEvent with all required
	 * information.
	 * @param statement
	 * @param changes
	 */
	public StatementExecutedEvent(EventContext ctx, MStatement statement, StateDifference changes) {
		super(ctx);
		this.statement = statement;
		this.changes = changes;
	}

	/**
	 * The executed statement.
	 * @return the statement
	 */
	public MStatement getStatement() {
		return statement;
	}

	/**
	 * The changes made by the statement.
	 * @return the changes
	 */
	public StateDifference getChanges() {
		return changes;
	}
}
