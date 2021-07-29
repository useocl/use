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

// $Id$

package org.tzi.use.util;

import java.util.Properties;
import java.util.Arrays;

/**
 * Extension of Properties class allowing type checking of values.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class TypedProperties extends Properties {

	/**
	 * To get rid of the warning...
	 */
	private static final long serialVersionUID = 1L;

	public TypedProperties() {
        super();
    }

    public TypedProperties(Properties p) {
        super(p);
    }

    /**
     * Returns an integer value assigned to the given key. If the
     * property does not exist the defaultValue is returned. If the
     * property exists but the value is not an integer, an error is
     * printed to System.err and the default value is returned. 
     */
    public int getIntProperty(String key, int defaultValue) {
        return getRangeIntProperty(key, defaultValue, 
                                   Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Returns an integer value assigned to the given key. If the
     * property does not exist the defaultValue is returned. If the
     * property exists but the value is not an integer, a warning is
     * printed to System.err and the default value is returned. If the
     * value is not in the specified range a warning is printed and
     * the return value is adjusted to the minimum or maximum
     * value.
     */
    public int getRangeIntProperty(String key, int defaultValue,
                                   int minValue, int maxValue) {
        int res;
        String value = getProperty(key);
        if (value == null )
            res = defaultValue;
        else {
            try {
                res = Integer.parseInt(value);
                if (minValue != Integer.MIN_VALUE && res < minValue ) {
                    System.err.println("Warning: value " + value +
                                       " for property `" + key +
                                       "' is less than minimum value " + minValue +
                                       ", using value " + minValue + ".");
                    res = minValue;
                }
                if (maxValue != Integer.MAX_VALUE && res > maxValue ) {
                    System.err.println("Warning: value " + value +
                                       " for property `" + key +
                                       "' is greater than maximum value " + maxValue +
                                       ", using value " + maxValue + ".");
                    res = maxValue;
                }
            
            } catch (NumberFormatException e) {
                System.err.println("Warning: illegal value `" + value +
                                   "' for property `" + key +
                                   "', using default value " + defaultValue + ".");
                res = defaultValue;
            }
        }
        return res;
    }

    /**
     * Returns an integer value assigned to the given key. See
     * getRangeIntProperty for details. 
     */
    public double getRangeDoubleProperty(String key, double defaultValue,
                                         double minValue, double maxValue) {
        double res;
        String value = getProperty(key);
        if (value == null )
            res = defaultValue;
        else {
            try {
                res = Double.parseDouble(value);
                if (minValue != Double.MIN_VALUE && res < minValue ) {
                    System.err.println("Warning: value " + value +
                                       " for property `" + key +
                                       "' is less than minimum value " + minValue +
                                       ", using value " + minValue + ".");
                    res = minValue;
                }
                if (maxValue != Double.MAX_VALUE && res > maxValue ) {
                    System.err.println("Warning: value " + value +
                                       " for property `" + key +
                                       "' is greater than maximum value " + maxValue +
                                       ", using value " + maxValue + ".");
                    res = maxValue;
                }
            
            } catch (NumberFormatException e) {
                System.err.println("Warning: illegal value `" + value +
                                   "' for property `" + key +
                                   "', using default value " + defaultValue + ".");
                res = defaultValue;
            }
        }
        return res;
    }

    /**
     * Returns a string value assigned to the given key. The string
     * must be one of the allowed strings. If the property does not
     * exist, the default value is returned. Note: The allowed strings
     * array MUST BE SORTED.  
     */
    public String getStringEnumProperty(String key, String defaultValue,
                                        String[] allowed) {
        if (allowed == null || allowed.length < 1 )
            throw new IllegalArgumentException();
        String res;
        String value = getProperty(key);
        if (value == null )
            res = defaultValue;
        else {
            if (Arrays.binarySearch(allowed, value) < 0 ) {
                System.err.println("Warning: illegal value `" + value +
                                   "' for property `" + key +
                                   "', using default value `" + defaultValue + "'.");
                res = defaultValue;
            } else
                res = value;
        }
        return res;
    }
}
