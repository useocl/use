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

package org.tzi.use.util;

/**
 * Container for a long value which can be changed.
 * @author Lars Hamann
 *
 */
public class MutableLong {
	long value;
	
	/**
	 * New MutableLong with initial value 0.
	 */
	public MutableLong() {
		this(0);
	}
	
	public MutableLong(long value) {
		this.value = value;
	}
	
	/**
	 * @return the value
	 */
	public long getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(long value) {
		this.value = value;
	}
	
	/**
	 * Adds one to the value.
	 */
	public void increment() {
		++value;
	}
}
