/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.tzi.use.gui.views.diagrams;

import java.awt.geom.Point2D;

/**
 * The layouts of USE can automatically
 * position objects of this type.
 * @author Lars Hamann
 *
 */
public interface Layoutable {
	/**
	 * Center position of the object to layout
	 * @return The center <code>Point2D</code> of the object to layout
	 */
	Point2D getCenter();
	
	double getHeight();
	
	double getWidth();
	
	/**
	 * Sets the calculated center of the object.
	 * @param x
	 * @param y
	 */
	void setCenter(double x, double y);
}
