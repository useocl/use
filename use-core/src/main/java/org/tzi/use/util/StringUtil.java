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

import java.util.Collection;
import java.util.Iterator;

/**
 * Provides static methods for formating strings and sequences of
 * Objects.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters
 * @author 		Lars Hamann 
 */
public final class StringUtil {

    // no instances
    private StringUtil() {}

    public static String NEWLINE = System.getProperty("line.separator");
  
    
    /*
     * Buffered methods which use a StringBuilder and call BufferedString.toString(StringBuffer) 
     */
    
    /**
     * <p>
     * Appends all string representations of the elements of the <code>Array</code> 
     * starting at beginIndex to the <code>StringBuilder</code> by calling the method 
     * <code>{@link BufferedToString#toString(StringBuilder)}</code>.
     * </p>
     * <p>
     * The elements are separated by the String provided by the parameter divider.
     * </p>
     * 
     * @author Lars Hamann
     * @since 3.0.0
     * 
     * @param target The target StringBuffer
     * @param objarr The elements to append to the buffer
     * @param beginIndex Index to start appending from
     * @param divider String to be used as a separator between elements
     * 
     */
	public static void fmtSeqBuffered(StringBuilder target,
			final BufferedToString[] objarr, final int beginIndex,
			final String divider) {
		if (objarr != null) {
			boolean first = true;
			for (int i = beginIndex; i < objarr.length; i++) {
				if (first)
					first = false;
				else
					target.append(divider);

				objarr[i].toString(target);
			}
		}
	}

    /**
     * <p>
     * Appends all elements of the <code>Array</code> to the <code>StringBuilder</code> by
     * calling the method <code>{@link BufferedToString#toString(StringBuilder)}</code>.
     * </p>
     * <p>
     * The elements are separated by the String provided by the parameter divider.
     * </p>
     * 
     * @author Lars Hamann
     * @since 3.0.0
     * 
     * @param target The StringBuffer the elements are appended
     * @param objarr The elements to append to the buffer
     * @param beginIndex Index to start appending from
     * @param divider String to be used as a separator between elements
     * 
     */
	public static void fmtSeqBuffered(StringBuilder target,
			final BufferedToString[] objarr, final String divider) {
    	fmtSeqBuffered(target, objarr, 0, divider);
    }

    /**
     * <p>
     * Appends all elements of the <code>Collection</code> to the <code>StringBuilder</code> by
     * calling the method <code>{@link BufferedToString#toString(StringBuilder)}</code>.
     * </p>
     * <p>
     * The elements are separated by the String provided by the parameter divider.
     * </p>
     * 
     * @author Lars Hamann
     * @since 3.0.0
     * 
     * @param target The StringBuffer to append to
     * @param seq The Collection to get the elements from
     * @param divider The separator between elements
     */
	public static void fmtSeqBuffered(StringBuilder target,
			final Collection<? extends BufferedToString> seq, final String divider) {
		fmtSeqBuffered(target, seq.iterator(), divider);
	}

	/**
     * <p>
     * Appends all elements returned by the <code>Iterator</code> to the <code>StringBuilder</code> by
     * calling the method <code>{@link BufferedToString#toString(StringBuilder)}</code>.
     * </p>
     * <p>
     * The elements are separated by the String provided by the parameter divider.
     * </p>
     * 
     * @author Lars Hamann
     * @since 3.0.0
     * 
     * @param target The StringBuffer to append to
     * @param it The Iterator to get the elements from
     * @param divider The separator between elements
     */
	public static void fmtSeqBuffered(StringBuilder target,
			Iterator<? extends BufferedToString> it, final String divider) {
		while (it.hasNext()) {
			it.next().toString(target);

			if (it.hasNext())
				target.append(divider);
		}
	}
    
	/*
	 * Buffered methods, which use a StringBuilder and the regular toString Method
	 */
	
	/**
     * <p>
     * Appends all string representations of the elements of the <code>Array</code> 
     * starting at beginIndex to the <code>StringBuilder</code> by calling <code>toString</code>.
     * </p>
     * <p>
     * The elements are separated by the String provided by the parameter divider.
     * </p>
     * <p>
     * If possible the methods <code>fmtSeqBuffered</code> should be used to avoid
     * copying of characters. 
     * </p>
     * @author Lars Hamann
     * @since 3.0.0
     * 
     * @param target The target StringBuffer
     * @param objarr The elements to append to the buffer
     * @param beginIndex Index to start appending from
     * @param divider String to be used as a separator between elements
     * 
     * @see #fmtSeqBuffered(StringBuilder, BufferedToString[], int, String)
     */
    public static void fmtSeq(StringBuilder target, Object objarr[], int beginIndex, String divider) {
    	if (objarr != null ) {
            boolean first = true;
            for (int i = beginIndex; i < objarr.length; i++) {
                if (first)
                    first = false;
                else
                	target.append(divider);
                target.append(objarr[i]);
            }
        }
    }
    
    /**
     * <p>
     * Appends all string representations of the elements of the <code>Array</code> 
     * to the <code>StringBuilder</code> by calling <code>toString</code>.
     * </p>
     * <p>
     * The elements are separated by the String provided by the parameter divider.
     * </p>
     * <p>
     * If possible the methods <code>fmtSeqBuffered</code> should be used to avoid
     * copying of characters. 
     * </p>
     * @author Lars Hamann
     * @since 3.0.0
     * 
     * @param target The target StringBuffer
     * @param objarr The elements to append to the buffer
     * @param divider String to be used as a separator between elements
     * 
     * @see #fmtSeqBuffered(StringBuilder, BufferedToString[], String)
     */
    public static void fmtSeq(StringBuilder target, Object objarr[], String divider) {
        fmtSeq(target, objarr, 0, divider);
    }
    
    /**
     * <p>
     * Appends all elements returned by the <code>Iterator</code> to the <code>StringBuilder</code> by
     * calling <code>toString</code>.
     * </p>
     * <p>
     * The elements are separated by the String provided by the parameter divider.
     * </p>
     * <p>
     * If possible the methods <code>fmtSeqBuffered</code> should be used to avoid
     * copying of characters. 
     * </p>
     * 
     * @author Lars Hamann
     * @since 3.0.0
     * 
     * @param target The StringBuffer to append to
     * @param it The Iterator to get the elements from
     * @param divider The separator between elements
     * 
     * @see #fmtSeqBuffered(StringBuilder, Iterator, String)
     */
    public static void fmtSeq(StringBuilder target, Iterator<?> it, String divider) {
        while (it.hasNext() ) {            
        	target.append(it.next().toString());
            
            if (it.hasNext())
            	target.append(divider);
        }
    }
    
    
    public interface IElementFormatter<T> {
    	String format(T element);
    }
    
    public static <T> void fmtSeq(StringBuilder target, Collection<T> collection, String divider, IElementFormatter<T> formatter) {
    	fmtSeq(target, collection.iterator(), divider, formatter);
    }
    
    public static <T> String fmtSeq(Collection<T> collection, String divider, IElementFormatter<T> formatter) {
    	return fmtSeq(collection.iterator(), divider, formatter);
    }
    
    
    public static <T> String fmtSeq(Iterator<T> it, String divider, IElementFormatter<T> formatter) {
    	StringBuilder resString = new StringBuilder();
    	fmtSeq(resString, it, divider, formatter);
    	return resString.toString();
    }
    
    public static <T> void fmtSeq(StringBuilder target, Iterator<T> it, String divider, IElementFormatter<T> formatter) {
        while (it.hasNext() ) {            
        	target.append(formatter.format(it.next()));
            
            if (it.hasNext())
            	target.append(divider);
        }
    }
    
    /**
     * <p>
     * Appends all elements of the <code>Collection</code> to the <code>StringBuilder</code> by
     * calling <code>toString</code>.
     * </p>
     * <p>
     * The elements are separated by the string provided by the parameter <code>divider</code>.
     * </p>
     * <p>
     * If possible the methods <code>fmtSeqBuffered</code> should be used to avoid
     * copying of characters. 
     * </p>
     * 
     * @author Lars Hamann
     * @since 3.0.0
     * 
     * @param target The StringBuffer to append to
     * @param it The Iterator to get the elements from
     * @param divider The separator between elements
     * @see #fmtSeqBuffered(StringBuilder, Collection, String)
     */
    public static void fmtSeq(StringBuilder target, Collection<?> seq, String divider) {
    	fmtSeq(target, seq.iterator(), divider);
    }
    
    /**
     * <p>
     * Returns a <code>String</code> of the string representations of the elements of the <code>Array</code> 
     * starting at <code>beginIndex</code>.
     * </p>
     * <p>
     * The elements are separated by the string provided by the parameter <code>divider</code>.
     * </p>
     * <p>
     * If possible, the methods <code>fmtSeqBuffered</code> or <code>fmtSeq(StringBuilder, ...)</code> should be used to avoid
     * copying of characters. 
     * </p>
     * @param objarr The elements to append to the buffer
     * @param beginIndex Index to start appending from
     * @param divider String to be used as a separator between elements
     * @return A <code>String</code> with all elements separated by <code>divider</code> 
     * 
     * @see #fmtSeqBuffered(StringBuilder, BufferedToString[], int, String)
     * @see #fmtSeq(StringBuilder, Object[], int, String)
     */
    public static String fmtSeq(Object objarr[], 
                                int beginIndex, String divider) {
        StringBuilder resString = new StringBuilder();
        fmtSeq(resString, objarr, beginIndex, divider);        
        return resString.toString();
    }

    /**
     * <p>
     * Returns a <code>String</code> of all string representations of the elements of the <code>Array</code>.
     * </p>
     * <p>
     * The elements are separated by the string provided by the parameter <code>divider</code>.
     * </p>
     * <p>
     * If possible, the methods <code>fmtSeqBuffered</code> or <code>fmtSeq(StringBuilder, ...)</code> should be used to avoid
     * copying of characters. 
     * </p>
     * @param objarr The elements to print
     * @param beginIndex The index to start printing from
     * @param divider String that separates the elements
     * @return A <code>String</code> with all elements separated by <code>divider</code> 
     * 
     * @see #fmtSeqBuffered(StringBuilder, BufferedToString[], String)
     * @see #fmtSeq(StringBuilder, Object[], String)
     */
    public static String fmtSeq(Object objarr[], String divider) {
        return fmtSeq(objarr, 0, divider);
    }

    public static <T> StringBuilder fmtSeq(StringBuilder sb, T[] arr, String divider, IElementFormatter<T> formatter) {
    	if (arr != null ) {
            boolean first = true;
            for (int i = 0; i < arr.length; ++i) {
                if (first)
                    first = false;
                else
                	sb.append(divider);
                
                sb.append(formatter.format(arr[i]));
            }
        }
    	
    	return sb;
    }
    
    /**
     * <p>
     * Returns a <code>String</code> of all <code>toString</code> representations 
     * of the elements of the <code>Collection</code>.
     * </p>
     * <p>
     * The elements are separated by the string provided by the parameter <code>divider</code>.
     * </p>
     * <p>
     * If possible, the methods <code>fmtSeqBuffered</code> or <code>fmtSeq(StringBuilder, ...)</code>
     * should be used to avoid copying of characters. 
     * </p>
     * @param seq The <code>Collection</code> to be printed. 
     * @param divider The <code>String</code> to be used as a separator between elements
     * @return A <code>String</code> with all elements separated by <code>divider</code> 
     * 
     * @see #fmtSeqBuffered(StringBuilder, Collection, String)
     * @see #fmtSeq(StringBuilder, Collection, String)
     */
    public static String fmtSeq(Collection<?> seq, String divider) {
    	return fmtSeq(seq.iterator(), divider);
    }
    
    /**
     * <p>
     * Returns a <code>String</code> of all <code>toString</code> representations 
     * of the elements returned by <code>it</code>.
     * </p>
     * <p>
     * The elements are separated by the string provided by the parameter <code>divider</code>.
     * </p>
     * <p>
     * If possible, the methods <code>fmtSeqBuffered</code> or <code>fmtSeq(StringBuilder, ...)</code>
     * should be used to avoid copying of characters. 
     * </p>
     * @param it The <code>Iterator</code> over the elements to be printed. 
     * @param divider The <code>String</code> to be used as a separator between elements
     * @return A <code>String</code> with all elements separated by <code>divider</code> 
     * 
     * @see #fmtSeqBuffered(StringBuilder, Iterator, String)
     * @see #fmtSeq(StringBuilder, Iterator, String)
     */
    public static String fmtSeq(Iterator<?> it, String divider) {
    	StringBuilder resString = new StringBuilder();
    	fmtSeq(resString, it, divider);
    	return resString.toString();
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
     * @param quoteToEscape  the quote character that needs escaping (either " or ')
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
     * @param s
     * @param quoteToEscape  the quote character that needs escaping (either " or ')
     * @return
     */
    public static String escapeString(String s, char quoteToEscape) {
        StringBuffer res = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            res.append(escapeChar(s.charAt(i), quoteToEscape));
        }
        return res.toString();
    }
    
    
    /**
     * Returns <code>o</code> in the quoted format for USE messages, e.g., Unknown identifier `hello'.
     * @param o String to be quoted
     * @return The String <code>o</code> in quotes `{o}'
     */
    public static String inQuotes(Object o) {
    	return "`" + o.toString() + "'";
    }
    
    /**
     * Creates a <code>String</code> from <code>input</code> which is repeated <code>num</code> times.
     * @since 3.0.0
     * @param input String to be repeated
     * @param num Number of times <code>input</code> should be repeated.
     * @return String with <code>input</code> repeated <code>num</code> times.
     */
    public static String repeat(String input, int num) {
    	StringBuilder result = new StringBuilder(num * input.length());
    	for (int i = 0; i < num; ++i) {
    		result.append(input);
    	}
    	
    	return result.toString();
    }

    /**
	 * Prints the elements of the collection <code>mainSequence</code> separated by <code>delimiter</code> with
	 * the sub collection appended to each element, if present.
	 * E. g., ada{ibm}, bob
	 * 
     * @param buffer
     * @param mainSequence
     * @param delimiter
     * @param subSequences
     * @param subDelimiter
     * @param subStart
     * @param subEnd
     */
	public static <T> String fmtSeqWithSubSeq(final Collection<T> mainSequence, final String delimiter,
											  final Collection<? extends Collection<?>> subSequences, 
											  final String subDelimiter, final String subStart, final String subEnd) {
		StringBuilder res = new StringBuilder();
		StringUtil.fmtSeqWithSubSeq(res, mainSequence, delimiter, subSequences, subDelimiter, subStart, subEnd);
		return res.toString();
	}
	
	/**
	 * Prints the elements of the collection <code>mainSequence</code> separated by <code>delimiter</code> with
	 * the sub collection appended to each element, if present.
	 * E. g., ada{ibm}, bob
	 * 
     * @param buffer
     * @param mainSequence
     * @param delimiter
     * @param subSequences
     * @param subDelimiter
     * @param subStart
     * @param subEnd
     */
	public static <T> void fmtSeqWithSubSeq(
			StringBuilder buffer,
			final Collection<T> mainSequence, final String delimiter,
			final Collection<? extends Collection<?>> subSequences, 
			final String subDelimiter, final String subStart, final String subEnd) {
		
		final Iterator<? extends Collection<?>> subIter = subSequences.iterator();
		
		StringUtil.fmtSeq(buffer, mainSequence, delimiter, new StringUtil.IElementFormatter<T>() {
						
			@Override
			public String format(T element) {
				String qualifierValues = "";
				
				if (subIter.hasNext()) {
					Collection<?> subSeq = subIter.next();
					if (!subSeq.isEmpty()) {
						qualifierValues = subStart;
						qualifierValues += StringUtil.fmtSeq(subSeq, subDelimiter);
						qualifierValues += subEnd;
					}
				}
				
				return element.toString() + qualifierValues;
			}
		});
		
	}
}
