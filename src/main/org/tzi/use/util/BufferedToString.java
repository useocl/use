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
 * Interface for objects that can be translated
 * to string via a StringBuffer.
 * 
 * @author Lars Hamann
 * @since 3.0.0
 */
public interface BufferedToString {
	/**
	 * <p>
	 * Appends the string representation of this objects
	 * to the StringBuffer.
	 * </p>
	 * <p>
	 * The result should be the same as the operation toString()
	 * </P>
	 * @since 3.0.0
	 * @param sb The buffer to append
	 * @return The same StringBuilder that was provided which allows shorter implementations (cf. {@link StringBuilder#append(String))} 
	 */
	StringBuilder toString(StringBuilder sb);
}
