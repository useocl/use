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
 * 
 */
public class MMessage implements MNamedElement {

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
	private boolean isAbsentFromGraphByDepth = false;
	private boolean isAbsentFromGraphByEvent = false;
	
	/**
	 * 
	 * @param number the new Sequencenumber
	 */
	
	public void setResetedSequenceNumber(String sequenceNumber){
		resetedSequenceNumber = sequenceNumber;
	}
	
	/**
	 * @return the isVisible
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * @param isVisible the isVisible to set
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	private final Event event;
	
	public MMessage(Event e, String sequenceNumber, String message) {
		this.sequenceNumber = sequenceNumber;
		this.name = message;
		this.failedReturn = false;
		this.event = e;
	}

	public MMessage(Event e) {
		this.sequenceNumber = "";
		this.name = "";
		this.failedReturn = false;
		this.event = e;
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
//		String [] strings = sequenceNumber.split("\\.");
//		String newSequenceNumber = "";
//		
//		if(strings.length == 1)
//		{
//			strings[0] = resetedSequenceNumber;
//		}else if(strings.length > 1){
//			strings[0] = resetedSequenceNumber + ".";
//		}
//		
//		for (int i = 0; i < strings.length; i++) {
//			newSequenceNumber = newSequenceNumber + strings[i];
//		}
		
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
	 * @param owner the owner to set
	 */
	public void setOwner(CommunicationDiagramEdge owner) {
		this.owner = owner;
	}

	/**
	 * @return the event
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

	/**
	 * @return the isAbsentFromGraph
	 */
	public boolean isAbsentFromGraph() {
		return isAbsentFromGraphByDepth || isAbsentFromGraphByEvent;
	}

	/**
	 * @param isAbsentFromGraph the isAbsentFromGraph to set
	 */
	public void setAbsentFromGraphByDepth(boolean isAbsentFromGraph) {
		this.isAbsentFromGraphByDepth = isAbsentFromGraph;
	}
	
	public void setAbsentFromGraphByEvent(boolean isAbsentFromGraph) {
		this.isAbsentFromGraphByEvent = isAbsentFromGraph;
	}
}
