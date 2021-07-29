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

package org.tzi.use.util;

/**
 * Data structure for a mathematical pair.
 * @author Lars Hamann
 *
 */
public class Pair<T> {
	public T first;
	public T second;
	
	public String toString() {
		return "(" + first + "," + second + ")";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return first.hashCode() + 3 * second.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (this.hashCode() != obj.hashCode()) return false;
		
		boolean equals = false;
		
		if (obj instanceof Pair<?>) {
			Pair<?> o = (Pair<?>)obj;
			if (this.first == null) {
				equals = o.first == null;
			} else {
				equals = this.first.equals(o.first);
			}
			
			if (this.second == null) {
				equals = equals && o.second == null; 
			} else {
				equals = equals && this.second.equals(o.second);
			}
		}
		
		return equals;
	}
}
