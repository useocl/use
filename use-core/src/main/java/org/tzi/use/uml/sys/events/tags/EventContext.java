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

package org.tzi.use.uml.sys.events.tags;

/**
 * An event can be raised inside these
 * three contexts:
 * <ul>
 *   <li>NORMAL_EXECUTION</li>
 *   <li>UNDO</li>
 *   <li>REDO</li>
 * </ul>
 * @author Lars Hamann
 *
 */
public enum EventContext {
	NORMAL_EXECUTION,
	UNDO,
	REDO
}
