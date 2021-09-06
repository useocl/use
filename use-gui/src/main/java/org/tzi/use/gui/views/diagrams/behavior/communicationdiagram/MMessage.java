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

import org.tzi.use.uml.mm.MNamedElement;
import org.tzi.use.uml.sys.events.Event;

/**
 * This class represent a single message in communication diagrams
 * 
 * @author Quang Dung Nguyen
 * @author Thomas Schaefer
 * @author Carsten Schlobohm
 * 
 */
public class MMessage implements MNamedElement {

	/** Original event from system */
	private final Event event;

	/** Indicates in which direction the arrow will point */
	public static final int FORWARD = 1;
	public static final int BACKWARD = 2;
	public static final int RETURN = 3;

	private String resetedSequenceNumber;
	private String sequenceNumber;
	private String name;
	private int direction = FORWARD;
	private boolean failedReturn;

	private CommunicationDiagramEdge owner;

	private boolean isVisible = true;
	private boolean isAbsentFromGraphByEvent = false;

	/**
	 * Custom constructor
	 * @param e	contains the event from the system which spawns this message
	 * @param sequenceNumber number for ordering the messages (look communication diagram)
	 * @param message as string which will be shown
	 */
	public MMessage(Event e, String sequenceNumber, String message) {
		this.sequenceNumber = sequenceNumber;
		this.name = message;
		this.failedReturn = false;
		this.event = e;
	}

	/**
	 * Custom constructor for test cases
	 * @param e is the event which spawns this message
	 */
	public MMessage(Event e) {
		this.sequenceNumber = "";
		this.name = "";
		this.failedReturn = false;
		this.event = e;
	}

	/**
	 * Allows to set a new sequence number after a message selection
	 * @param sequenceNumber new sequence number
	 */
	public void setResetedSequenceNumber(String sequenceNumber){
		resetedSequenceNumber = sequenceNumber;
	}

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public String getMessageName() {
		return name;
	}

	public String getSequenceNumberMessage() {
		return sequenceNumber + " : " + name;
	}
	
	public String getResetedNumberMessage(){
		return resetedSequenceNumber + " : " + name;
	}

	/**
	 * @return the fDirection
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * @param direction the fDirection to set
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * @return the failedReturn
	 */
	public boolean isFailedReturn() {
		return failedReturn;
	}

	/**
	 * @param failedReturn the failedReturn to set
	 */
	public void setFailedReturn(boolean failedReturn) {
		this.failedReturn = failedReturn;
	}

	/**
	 * @return the owner
	 */
	public CommunicationDiagramEdge getOwner() {
		return owner;
	}

	/**
	 * @param owner of the message
	 */
	public void setOwner(CommunicationDiagramEdge owner) {
		this.owner = owner;
	}

	/**
	 * @return the stored event
	 */
	public Event getEvent() {
		return event;
	}

	@Override
	public String name() {
		if (sequenceNumber.equals("") && name.equals("")) {
			return "-";
		}
		return sequenceNumber + " : " + name;
	}

	@Override
	public int hashCode() {
		return sequenceNumber.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MMessage) {
			if (name().equals(((MMessage) obj).name())) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return name();
	}
}
