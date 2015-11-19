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
 * Represents a role name node in a diagram.
 * 
 * @author ms
 */
public final class MultiplicityRolenameWrapper implements DiagramOptionChangedListener {

	Multiplicity multiplicity_client;
	Rolename rolename_client;
	PropertyOwner end;

	public MultiplicityRolenameWrapper(Multiplicity multiplicity_client, Rolename rolename_client, PropertyOwner end, DiagramOptions options) {
		this.multiplicity_client = multiplicity_client;
		this.rolename_client = rolename_client;
		this.end = end;
		
		//options.addOptionChangedListener(this);
		addPositionChangedListener();
	}

	protected void addPositionChangedListener() {
		PositionChangedListener listener = new PositionChangedListener() {
			@Override
			public void positionChanged(Object source, Point2D currentPosition, double deltaX, double deltaY) {
				
				if (source instanceof Multiplicity) {
					//if(end.equals(PropertyOwner.SOURCE)); new_x = currentPosition.getX()+20;
					//if(end.equals(PropertyOwner.TARGET)); new_x = currentPosition.getX()-20;
					
					//System.out.println( ((Multiplicity) source).fAssocEnd.multiplicity().toString().length() );
					
					double distance = 0;
					
					int ln = ((Multiplicity) source).fAssocEnd.multiplicity().toString().length();
					
					if(ln > 1) {
						distance = 20;
					}
					else {
						distance = 8;
					}
					
					rolename_client.moveToPosition(currentPosition.getX()+distance, currentPosition.getY());

				} else if (source instanceof Rolename) {
					//if(end.equals(PropertyOwner.SOURCE)); new_x = currentPosition.getX()+20;
					//if(end.equals(PropertyOwner.TARGET)); new_x = currentPosition.getX()-20;
					
					double distance = 10;
					
					//multiplicity_client.moveToPosition(currentPosition.getX(), currentPosition.getY());
				
				} else {
					throw new RuntimeException("Position source object not recognized!");
				}
			}

			@Override
			public void boundsChanged(Object source, Rectangle2D oldBounds,	Rectangle2D newBounds) {}
		};

		multiplicity_client.fListenerList.add(PositionChangedListener.class, listener);
		rolename_client.fListenerList.add(PositionChangedListener.class, listener);
	}


	@Override
	public void optionChanged(String optionname) {
		System.out.println(optionname);
	}
}
