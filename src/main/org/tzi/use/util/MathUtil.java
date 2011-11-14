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
 * Some math utility functions
 * @author Lars Hamann
 *
 */
public class MathUtil {
	private MathUtil(){}
	
	/**
	 * Calculates the maximum of the given parameters
	 * @param value List of values to retrieve the maximum from
	 * @return The maximum value of all given values
	 */
	public static double max(double... value) {
		double max = Double.MIN_VALUE;
		
		for (double d : value) {
			max = Math.max(max, d);
		}
		
		return max;
	}
	
	/**
	 * Calculates the maximum of the given parameters
	 * @param useInt This parameter can be removed when Sun/Oracle fixes a <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6199075">bug</a> with parameter arrays.</br>
	 * 				 The compiler wrongly determines max(double...) and max(int...) as ambiguous.
	 * @param value List of values to retrieve the maximum from
	 * @return The maximum value of all given values
	 */
	public static int max(boolean useInt, int... value) {
		int max = Integer.MIN_VALUE;
		
		for (int d : value) {
			max = Math.max(max, d);
		}
		
		return max;
	}
	
	/**
	 * Calculates the minimum of the given parameters
	 * @param value List of values to retrieve the minimum from
	 * @return The minimum value of all given values
	 */
	public static double min(double... value) {
		double min = Double.MAX_VALUE;
		
		for (double d : value) {
			min = Math.min(min, d);
		}
		
		return min;
	}
	
	/**
	 * Calculates the minimum of the given parameters
	 * @param useInt This parameter can be removed when Sun/Oracle fixes a <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6199075">bug</a> with parameter arrays.</br>
	 * 				 The compiler wrongly determines max(double...) and max(int...) as ambiguous.
	 * @param value List of values to retrieve the minimum from
	 * @return The minimum value of all given values
	 */
	public static int min(boolean useInt, int... value) {
		int min = Integer.MAX_VALUE;
		
		for (int d : value) {
			min = Math.min(min, d);
		}
		
		return min;
	}
}
