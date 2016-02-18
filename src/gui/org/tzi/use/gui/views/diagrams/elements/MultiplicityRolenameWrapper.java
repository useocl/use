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

package org.tzi.use.gui.views.diagrams.elements;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.tzi.use.gui.views.diagrams.DiagramOptionChangedListener;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.PositionChangedListener;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase.PropertyOwner;

/**
 * Provides logic for grouping multiplicity and role name nodes.
 * 
 * @author ms
 */
public final class MultiplicityRolenameWrapper implements
		DiagramOptionChangedListener {

	private Multiplicity multiplicity_client;
	private Rolename rolename_client;

	// Currently not needed, maybe of use later.
	// private PropertyOwner end;

	PositionChangedListener position_changed_listener = null;

	private boolean do_group = false;

	double offset = 0;

	public MultiplicityRolenameWrapper(Multiplicity multiplicity_client,
			Rolename rolename_client, PropertyOwner end, DiagramOptions options) {
		this.multiplicity_client = multiplicity_client;
		this.rolename_client = rolename_client;

		// this.end = end;

		// Let this wrapper be informed whenever options are changed.
		options.addOptionChangedListener(this);

		// Calculate sufficient distance between multiplicity and role name
		// node.
		determine_offset();

		// Let this wrapper listen to position changes of either multiplicity or
		// role name nodes.
		instantiatePositionChangedListener();
	}

	@Override
	public void optionChanged(String optionname) {
		if (optionname.equals("GROUPMR"))
			do_group = !do_group;

		if (do_group)
			attach_listener();
		else
			detach_listener();

	}

	protected void determine_offset() {
		int x = multiplicity_client.fAssocEnd.multiplicity().toString()
				.length();

		offset = (Math.pow(x, 2) - x) + 8;
	}

	protected void instantiatePositionChangedListener() {
		position_changed_listener = new PositionChangedListener() {
			@Override
			public void positionChanged(Object source, Point2D currentPosition,
					double deltaX, double deltaY) {

				detach_listener();

				if (source instanceof Multiplicity)
					rolename_client.moveToPosition(currentPosition.getX()
							+ offset, currentPosition.getY());

				else if (source instanceof Rolename)
					multiplicity_client.moveToPosition(currentPosition.getX()
							- offset, currentPosition.getY());

				else
					throw new RuntimeException(
							"Position source object not recognized.");

				attach_listener();
			}

			@Override
			public void boundsChanged(Object source, Rectangle2D oldBounds,
					Rectangle2D newBounds) {
			}
		};
	}

	protected void attach_listener() {
		multiplicity_client.fListenerList.add(PositionChangedListener.class,
				position_changed_listener);
		rolename_client.fListenerList.add(PositionChangedListener.class,
				position_changed_listener);
	}

	protected void detach_listener() {
		multiplicity_client.fListenerList.remove(PositionChangedListener.class,
				position_changed_listener);
		rolename_client.fListenerList.remove(PositionChangedListener.class,
				position_changed_listener);
	}
}