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

import org.tzi.use.config.Options;

import com.google.common.math.DoubleMath;

/**
 * Utility class for equality checks on floating-point numbers.
 *  
 * @author Frank Hilken
 */
public class FloatUtil {
	
	private static final double EPSILON = Math.pow(10, -Options.DEFAULT_FLOAT_PRECISION);
	
	private FloatUtil(){
	}
	
	/**
	 * Compares two floats for equality using the default precision defined by
	 * {@link Options#DEFAULT_FLOAT_PRECISION}.
	 */
	public static boolean equals(float a, float b){
		return DoubleMath.fuzzyEquals(a, b, EPSILON);
	}
	
	/**
	 * Compares two floats for equality using a given precision.
	 * 
	 * @param precision number of decimals where the first difference may occur
	 */
	public static boolean equals(float a, float b, int precision){
		return DoubleMath.fuzzyEquals(a, b, Math.pow(10, -precision));
	}
	
	/**
	 * Compares two doubles for equality using the default precision defined by
	 * {@link Options#DEFAULT_FLOAT_PRECISION}.
	 */
	public static boolean equals(double a, double b){
		return DoubleMath.fuzzyEquals(a, b, EPSILON);
	}
	
	/**
	 * Compares two doubles for equality using a given precision.
	 * 
	 * @param precision number of decimals where the first difference may occur
	 */
	public static boolean equals(double a, double b, int precision){
		return DoubleMath.fuzzyEquals(a, b, Math.pow(10, -precision));
	}
	
}
