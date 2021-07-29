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

package org.tzi.use.gui.views.diagrams.behavior.sequencediagram;

import java.util.List;

import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.events.LinkInsertedEvent;

/**
 * An special kind of Activation for an insert command. This is necessery
 * because an insert command contains several objects which have to be notified.
 * 
 * @author Antje Werner
 */
public class InsertActivation extends Activation {
	/**
	 * The nestings of the involved objects at the time of giving the command.
	 */
	protected int[] fNestings;

	/**
	 * Constructs a new InsertActivation by Calling the Construcor of the
	 * super-class Activation and initialising nesting with the correct values.
	 * 
	 * @param start the position on the time axis on which the activation
	 *            message is positioned
	 * @param owner the lifeline which is receiver of the activation message
	 * @param cmd the command which should be represented
	 * @param src the source-activation from which the command fCmd has given
	 * @param yValue the y-value on which the activation message should be drawn
	 */
	public InsertActivation(int start, Lifeline owner, LinkInsertedEvent event, Activation src, int yValue, SequenceDiagram sequenceDiagram) {
		super(start, owner, event, src, yValue, sequenceDiagram);
		List<MObject> objects = event.getParticipants();
		// MObject[] objects = ((MCmdInsertLink) fEvent).getObjects();
		fNestings = new int[objects.size()];
		// calculate nestings of the object lifelines of the inserted
		// objects
		synchronized (sequenceDiagram) {
			for (int i = 0; i < fNestings.length; i++) {
				MObject object = objects.get(i);
				Lifeline ll = getSequenceDiagram().getAllLifelines().get(object);
				fNestings[i] = ll.activationNesting;
			}
		}
	}

	/**
	 * Calculates the y-value for the answer message of this Activation
	 * depending on the properties which are set in the fProperties object of
	 * the SequenceDiagram. Contrary to the same method in Activation the
	 * messages to the object-lifelines musst also be regarded.
	 * 
	 * @return the y-position for the answer message
	 */
	public int calculateEnd() {
		// objects which are involved in the insert-command
		List<MObject> objects = ((LinkInsertedEvent) eventOfActivation).getParticipants();
		int numObjects = objects.size();
		// y-value of the insert-message
		int value = yPositionOfMessageArrow;
		// consider the 'inserted as'-messages an the user settings
		// for the distance between two messages
		if (getSequenceDiagramProperties().getActManDist() != -1) {
			value += (numObjects + 1) * getSequenceDiagramProperties().getActManDist();
		} else {
			value += (numObjects + 1) * getSequenceDiagramProperties().actStep();
		}
		// set y-value for the end
		yPositionOfAnswerLine = value;
		return value;
	}
}
