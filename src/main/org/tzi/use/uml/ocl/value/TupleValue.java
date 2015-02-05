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

package org.tzi.use.uml.ocl.value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.tzi.use.uml.ocl.type.TupleType;
import org.tzi.use.util.BufferedToString;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.collections.CollectionComparator;

/**
 * Tuple values.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public final class TupleValue extends Value {
	
	public static class Part implements BufferedToString, Comparable<Part> {
        private final int position;
    	private final String fName;
        private final Value fValue;

        public Part(int position, String name, Value value) {
            this.fName = name;
            this.fValue = value;
            this.position = position;
        }

        @Override
        public String toString() {
            return this.toString(new StringBuilder()).toString();
        }

        @Override
        public StringBuilder toString(StringBuilder sb) {
        	sb.append(fName).append("=");
        	fValue.toString(sb);
        	return sb;
        }
        
        public String getName() {
            return fName;
        }

        public Value getValue() {
            return fValue;
        }

        public int getPosition() {
        	return position;
        }
        
        public boolean equals(Object obj) {
            if (obj == null) 
                return false;
            if (obj == this )
                return true;
            if (obj.getClass().equals(getClass())) {
                Part other = (Part) obj;
                return fName.equals(other.fName) && fValue.equals(other.fValue);
            }
            return false;
        }

        public int hashCode() {
            return fName.hashCode() + fValue.hashCode() * 113;
        }

		@Override
		public int compareTo(Part o) {
			return this.fValue.compareTo(o.fValue);
		}
        
    }
	
	/**
     * Map&lt;String, Value&gt;
     */
	private Map<String, TupleValue.Part> fParts = new TreeMap<String, TupleValue.Part>();

    /**
     * Constructs a tuple and sets all values. Elements are type checked
     * as they get inserted into the tuple.
     *
     * @param t The type of the tuple
     * @param parts Map&lt;String, Value&gt; containing the tuple parts
     * 
     * @exception IllegalArgumentException the type of at least one
     *            value does not match 
     */
    public TupleValue(TupleType t, List<TupleValue.Part> parts) {
        super(t);
        Iterator<TupleValue.Part> iter = parts.iterator();
        
        while(iter.hasNext())
        {
        	TupleValue.Part part = iter.next();
            fParts.put(part.getName(), part);
        }
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append("Tuple{");

        List<TupleValue.Part> sortedParts = new ArrayList<TupleValue.Part>(fParts.values());
        Collections.sort(sortedParts, new Comparator<TupleValue.Part>() {
			@Override
			public int compare(Part p1, Part p2) {
				if (p1.position < p2.position) return -1;
			    if (p1.position > p2.position) return  1;
			    return 0;
			}});
        
        StringUtil.fmtSeqBuffered(sb, sortedParts, ",");
        sb.append("}");
        
        return sb;
    }

    public boolean equals(Object obj) {
        if (obj == this )
            return true;
        else if (obj instanceof TupleValue ) {
            TupleValue other = (TupleValue) obj;
            return fParts.equals(other.fParts);
        }
        return false;
    }

    public int hashCode() {
        return fParts.hashCode();
    }

    public int compareTo(Value o) {
        if (o == this )
            return 0;
        if (o instanceof UndefinedValue)
            return +1;
        if (! (o instanceof TupleValue) )
        	// Use textual representation to compare
            return this.toStringWithType().compareTo(o.toStringWithType());
        
        TupleValue other = (TupleValue) o;
        return new CollectionComparator<TupleValue.Part>().compare(fParts.values(), 
                                                                   other.fParts.values());
    }

    
    public Value getElementValue(String name) {
        if (fParts.containsKey(name))
        	return fParts.get(name).getValue();
        else
        	throw new IllegalArgumentException("No such element: " + name);
    }

}