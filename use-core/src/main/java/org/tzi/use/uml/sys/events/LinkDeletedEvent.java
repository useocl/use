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

package org.tzi.use.uml.sys.events;

import java.util.List;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.events.tags.EventContext;
import org.tzi.use.uml.sys.events.tags.SystemStructureChangedEvent;


/**
 * An event including all event information.
 * @author Daniel Gent
 * @author Lars Hamann
 *
 */
public class LinkDeletedEvent extends Event implements SystemStructureChangedEvent {
	
	/** The deleted link */
	private MLink fLink;

	/**
	 * Constructs a new LinkDeleteEvent. 
	 * @param ctx
	 * @param link
	 */
	public LinkDeletedEvent(EventContext ctx, MLink link) {
		super(ctx);
		fLink = link;
	}
	
	/**
	 * Returns the deleted link.
	 */
	public MLink getLink() {
		return fLink;
	}
	
	/**
	 * Returns the association the link is deleted from
	 * @return The <code>association</code>
	 */
	public MAssociation getAssociation() {
		return fLink.association();
	}
	
	
	/**
	 * Access to the participating objects in the order of the association ends
	 * @return A <code>List</code> of the participating objects.
	 */
	public List<MObject> getParticipants() {
		return fLink.linkedObjects();
	}
}
