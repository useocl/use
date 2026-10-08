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

package org.tzi.use.uml.sys;

import org.tzi.use.util.soil.StateDifference;

/**
 * Interface for controllers that calculate values
 * after the system state has been changed.
 * @author Lars Hamann
 *
 */
public interface DerivedValueController {

	/**
	 * Initializes the derived controller.
	 */
	public void initState();
	
	/**
	 * Invoked after a statement changed the system state,
	 * but before external instances are notified about the change.
	 */
	public void updateState();
	
	/**
	 * Invoked after a statement changed the system state,
	 * but before external instances are notified about the change.
	 * Implementors need to change the system state and add any
	 * changes into the state difference <code>diff</code>.
	 * @param diff
	 */
	public void updateState(StateDifference diff);
}
