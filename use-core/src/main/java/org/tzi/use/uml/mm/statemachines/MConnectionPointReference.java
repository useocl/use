/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2012 Mark Richters, University of Bremen
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

package org.tzi.use.uml.mm.statemachines;


/**
 * A connection point reference represents a usage (as part of a 
 * submachine state) of an entry/exit point defined in the statemachine 
 * reference by the submachine state. 
 * [<a href="http://www.omg.org/spec/UML/">UML Superstructure 2.4.1</a>]
 * 
 * @author Lars Hamann
 */
public class MConnectionPointReference extends MVertex {

	/**
	 * @param name
	 */
	public MConnectionPointReference(String name) {
		super(name);
	}
	
}
