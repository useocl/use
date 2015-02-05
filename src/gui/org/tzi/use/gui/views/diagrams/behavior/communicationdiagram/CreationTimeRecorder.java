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

package org.tzi.use.gui.views.diagrams.behavior.communicationdiagram;

import java.util.Collection;
import java.util.Vector;

import org.tzi.use.uml.sys.events.LinkInsertedEvent;
import org.tzi.use.uml.sys.events.ObjectCreatedEvent;
import org.tzi.use.util.MathUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * Saves the time stamps of the creation events
 * of objects and links that are treaded as equal by USE.
 * This is for all events in the current use session.
 * For example:
 * 1 !create ada:Person  (ada:Person -> {1})
 * 2 !set ...            (ada:Person -> {1})
 * 3 !destroy ada        ()
 * 4 !create ada:Person  (ada:Person -> {1,4})
 * This allows to identify the correct object node
 * if both ada objects are shown in the communication diagram.
 * 
 * The creation time can be used by the raised events.
 * @author Lars Hamann
 */
public class CreationTimeRecorder {
	private Multimap<Object,Integer> elementCreationTime = ArrayListMultimap.create();
	
	private Vector<MMessage> messages = new Vector<>();
	
	private int time = 0;
	
	private void incrementTime() {
		++time;
	}
	
	/**
	 * @param mess
	 */
	public void addMessage(MMessage mess) {
		messages.add(mess);
		
		if (mess.getEvent() instanceof ObjectCreatedEvent) {
			ObjectCreatedEvent e = (ObjectCreatedEvent)mess.getEvent();
			elementCreated(e.getCreatedObject());
		} else if (mess.getEvent() instanceof LinkInsertedEvent) {
			LinkInsertedEvent e = (LinkInsertedEvent)mess.getEvent();
			elementCreated(e.getLink());
		}
		
		incrementTime();
	}
	
	public Vector<MMessage> getMessages() {
		return this.messages;
	}
	
	/**
	 * @return
	 */
	public int getTime() {
		return time;
	}
	
	/** Marks the birth of an element.
	 * The time is incremented.
	 * @param obj
	 * @return
	 */
	private void elementCreated(Object obj) {
		elementCreationTime.put(obj, time);
	}
	
	public int[] getCreationTimes(Object obj) {
		Collection<Integer> values = elementCreationTime.get(obj);
		int[] times = new int[values.size()];
		int i = 0;
		
		for(Integer value : values) {
			times[i++] = value.intValue();
		}
		
		return times;
	}
	
	/**
	 * Returns the last time an object with this value
	 * was created.
	 * @param obj
	 * @return
	 */
	public int getLastCreationTime(Object obj) {
		return MathUtil.max(true, getCreationTimes(obj));
	}
	
	/**
	 * Returns the last time an object with this value
	 * was created before time <code>before</code>.
	 * @param obj
	 * @param before
	 * @return
	 */
	public int getLastCreationTime(Object obj, int before) {
		int[] creationTimes = getCreationTimes(obj);
		for (int i = creationTimes.length - 1; i >= 0; --i) {
			if (creationTimes[i] <= before)
				return creationTimes[i];
		}
		
		throw new RuntimeException("Invalid creation times configured!");
	}
}
