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

import java.util.Iterator;

/**
 * Provides static methods for formating strings and sequences of
 * Objects.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class StringUtil {

    // no instances
    private StringUtil() {}

    public static String fmtSeq(Object objarr[], 
                                int beginIndex, String divider) {
        String resString = new String();
        if (objarr != null ) {
            boolean first = true;
            for (int i = beginIndex; i < objarr.length; i++) {
                if (first )
                    first = false;
                else
                    resString += divider;
                resString += objarr[i];
            }
        }
        return resString;
    }

    public static String fmtSeq(Object objarr[], String divider) {
        return fmtSeq(objarr, 0, divider);
    }

    public static String fmtSeq(Iterator it, String divider) {
        String resString = new String();
        boolean first = true;
        while (it.hasNext() ) {
            if (first )
                first = false;
            else
                resString += divider;
            resString += it.next();
        }
        return resString;
    }

    /**
     * Returns a string of length len filled with characters c.  
     */
    public static String fill(int len, char c) {
        char[] chars = new char[len];
        for (int i = 0; i < len; i++)
            chars[i] = c;
        return new String(chars);
    }

    /**
     * Returns a string of length len left-padded with characters
     * c. If the length of the passed string is greater or equal len
     * the string is returned unmodified.  
     */
    public static String leftPad(String s, int len, char c) {
        int slen = s.length();
        if (slen >= len )
            return s;
        else {
            char[] pad = new char[len - slen];
            for (int i = len - slen - 1; i >= 0; i--)
                pad[i] = c;
            return new String(pad) + s;
        }
    }

    /**
     * Returns a string of length len left-padded with blanks. If the
     * length of the passed string is greater or equal len the string
     * is returned unmodified.  
     */
    public static String leftPad(String s, int len) {
        return leftPad(s, len, ' ');
    }


    /**
     * Returns a string of length len right-padded with characters
     * c. If the length of the passed string is greater or equal len
     * the string is returned unmodified.  
     */
    public static String pad(String s, int len, char c) {
        int slen = s.length();
        if (slen >= len )
            return s;
        else {
            char[] pad = new char[len - slen];
            for (int i = len - slen - 1; i >= 0; i--)
                pad[i] = c;
            return s + new String(pad);
        }
    }

    /**
     * Returns a string of length len right-padded with blanks. If the
     * length of the passed string is greater or equal len the string
     * is returned unmodified.  
     */
    public static String pad(String s, int len) {
        return pad(s, len, ' ');
    }

    /**
     * Returns a string of length len left- and right-padded with
     * blanks so that s is centered. If the length of the passed
     * string is greater or equal len the string is returned
     * unmodified.  
     */
    public static String center(String s, int len) {
        int slen = s.length();
        if (slen >= len )
            return s;
        else {
            int n = len - slen;
            int n1 = n / 2;
            StringBuffer res = new StringBuffer(len);
            for (int i = 0; i < n1; i++)
                res.append(' ');
            res.append(s);
            n1 = n - n1;
            for (int i = 0; i < n1; i++)
                res.append(' ');
            return res.toString();
        }
    }


    /**
     * Returns the index within a string of the nth occurrence of the
     * specified character. If no such occurrence is found, then
     * <code>-1</code> is returned.  
     */
    public static int nthIndexOf(String s, int n, int ch) {
        int p1 = 0;
        int p2 = -1;
        while (n > 0 ) {
            p2 = s.indexOf(ch, p1);
            if (p2 == -1 )
                return -1;
            p1 = p2 + 1;
            n--;
        }
        return p2;
    }

    /**
     * Returns the index within a string of the nth occurrence of the
     * specified string. If no such occurrence is found, then
     * <code>-1</code> is returned.  
     */
    public static int nthIndexOf(String s, int n, String t) {
        int p1 = 0;
        int p2 = -1;
        while (n > 0 ) {
            p2 = s.indexOf(t, p1);
            if (p2 == -1 )
                return -1;
            p1 = p2 + t.length();
            n--;
        }
        return p2;
    }


    /**
     * Returns a string representation of the given character that
     * takes care of special characters by escaping them.
     *
     * @param quoteToEscape  the quote character needing escaping
     */
    public static String escapeChar(char c, char quoteToEscape) {
        switch ( c ) {
        case '\n' : return "\\n";
        case '\t' : return "\\t";
        case '\r' : return "\\r";
        case '\\' : return "\\\\";
        case '\'' : return ( quoteToEscape == '\'' ) ? "\\'" : "'";
        case '"' :  return ( quoteToEscape == '"' ) ? "\"" : "\\\"";
        default :
            if (c < ' ' || c > 126 ) {
                if (c > 255) {
                    return "\\u" + Integer.toString(c,16);
                }
                else {
                    return "\\" + Integer.toString(c,8);
                }
            }
            else {
                return String.valueOf(c);
            }
        }
    }

    /** 
     * Converts a String into a representation that can be use as a
     * literal when surrounded by quotes.  
     */
    public static String escapeString(String s, char quoteToEscape) {
        StringBuffer res = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            res.append(escapeChar(s.charAt(i), quoteToEscape));
        }
        return res.toString();
    }
}
