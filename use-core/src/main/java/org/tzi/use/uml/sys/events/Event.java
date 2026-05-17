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

package org.tzi.use.uml.sys.events;

import org.tzi.use.uml.sys.events.tags.EventContext;


/**
 * Base class for all events, like {@link AttributeAssignedEvent}.
 * These events are for exaple used in the sequence diagram.
 * @author Daniel Gent
 * @author Lars Hamann
 *
 */
public abstract class Event {
	private final EventContext context;

	/**
	 * Constructs a new system event with
	 * information about its execution context. 
	 * @param context
	 */
	public Event(EventContext context) {
		super();
		this.context = context;
	}
	
	/**
	 * The context inside this event was raised.
	 * @return
	 */
	public EventContext getContext() {
		return this.context;
	}
}
