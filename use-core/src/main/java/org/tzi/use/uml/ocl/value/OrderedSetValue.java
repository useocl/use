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

// $Id: SequenceValue.java 186 2009-03-27 13:30:35Z green $

package org.tzi.use.uml.ocl.value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.util.StringUtil;

/**
 * Ordered set values.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Lars Hamann
 */
public class OrderedSetValue extends CollectionValue {
    private ArrayList<Value> fElements;
    
    /**
     * Constructs a new empty OrderedSet.
     */
    public OrderedSetValue(Type elemType) {
        super(TypeFactory.mkOrderedSet(elemType), elemType);
        fElements = new ArrayList<Value>();
    }

    /**
     * Constructs an ordered set and adds all values. Elements are type checked
     * as they get inserted into the ordered set.
     * 
     * Duplicates are ignored after the first occurrence.
     */
    public OrderedSetValue(Type elemType, Value[] values) {
        this(elemType);
        for (int i = 0; i < values.length; i++)
            add(values[i]);
    }

    /**
     * Constructs an orderedset and adds all values. Elements are type checked
     * as they get inserted into the sequence.
     * 
     * Duplicates are ignored after the first occurrence.
     */
    public OrderedSetValue(Type elemType, Collection<Value> values) {
        this(elemType);
        addAll(values);
    }

    /**
     * Constructs an orderedset and fills it with ranges of integers.
     *
     */
    public OrderedSetValue(Type elemType, int[] ranges) {
        this(elemType);
        int i = 0; 
        while (i < ranges.length ) {
            int lower = ranges[i]; 
            int upper = ranges[i+1];
            for (int j = lower; j <= upper; j++)
                add(IntegerValue.valueOf(j));
            i += 2;
        }
    }

    @Override
    public boolean isOrderedSet() {
    	return true;
    }
    
    @Override
    public CollectionType getRuntimeType(Type elementType) {
        return TypeFactory.mkOrderedSet(elementType);
    }

    /**
     * Returns the element at the specified position in this orderedset.
     *
     * @param index index of element to return.
     * @return the element at the specified position in this orderedset.
     * 
     * @throws IndexOutOfBoundsException if the index is out of range (index
     *        &lt; 1 || index &gt; size()).
     */
    public Value get(int index) {
        return fElements.get(index);
    }


    public Iterator<Value> iterator() {
        return fElements.iterator();
    }

    public int size() {
        return fElements.size();
    }

    public boolean isEmpty() {
        return fElements.isEmpty();
    }

    public boolean includes(Value v) {
        return fElements.contains(v);
    }

    public boolean includesAll(CollectionValue v) {
        Iterator<Value> it = v.iterator(); 
        while (it.hasNext() ) {
            Value elem = it.next();
            if (! fElements.contains(elem) )
                return false;
        }
        return true;
    }

    public boolean excludesAll(CollectionValue v) {
        Iterator<Value> it = v.iterator(); 
        while (it.hasNext() ) {
            Value elem = it.next();
            if (fElements.contains(elem) )
                return false;
        }
        return true;
    }

    /** 
     * Returns a copy of this orderedset excluding all occurrences of v.
     *
     * @pre T2 <= T1, if this has type OrderedSet(T1) and v has type T2.
     */
    public OrderedSetValue excluding(Type resultType, Value v) {
        OrderedSetValue res = new OrderedSetValue(getResultElementType(resultType));
        Iterator<Value> it = fElements.iterator(); 
        while (it.hasNext() ) {
            Value elem = it.next();
            if (! v.equals(elem) )
                res.add(elem);
        }
        return res;
    }

    public OrderedSetValue insertAt(Type resultType, IntegerValue index, Value v) {
    	if (index.value() < 1 || index.value() > fElements.size() + 1)
    		return null;
    	
    	OrderedSetValue res = new OrderedSetValue(getResultElementType(resultType));
    	res.addAll(fElements);
    	res.fElements.add(index.value() - 1, v);
    	
    	return res;
    }
    
    public int indexOf(Value v) {
    	return fElements.indexOf(v);
    }
    
    public int count(Value v) {
        int res = 0;
        Iterator<Value> it = fElements.iterator(); 
        while (it.hasNext() )
            if (v.equals(it.next()) )
                res++;
        return res;
    }

    public OrderedSetValue union(Type resultType, OrderedSetValue v) {
        OrderedSetValue res = new OrderedSetValue(getResultElementType(resultType));
        res.addAll(fElements);
        res.addAll(v.fElements);
        return res;
    }

    public OrderedSetValue append(Type resultType, Value v) {
        OrderedSetValue res = new OrderedSetValue(getResultElementType(resultType));
        res.addAll(fElements);
        res.add(v);
        return res;
    }

    public OrderedSetValue prepend(Type resultType, Value v) {
        OrderedSetValue res = new OrderedSetValue(getResultElementType(resultType));
        if (!fElements.contains(v))
        	res.add(v);
        
        res.addAll(fElements);
        
        return res;
    }

    /**
     * Returns a new orderedset only containing the give range of
     * elements.
     *
     * @throws    IndexOutOfBoundsException if range is illegal
     */
    public OrderedSetValue subOrderedSet(Type resultType, int lower, int upper) {
        OrderedSetValue res = new OrderedSetValue(getResultElementType(resultType));
        for (int i = lower; i < upper; i++) 
            res.add(fElements.get(i));
        return res;
    }

    /**
     * Returns a new "flattened" ordered set. This ordered set must have
     * sequence elements.  
     * Otherwise the result is nondeterministic, as in Set->asSequence
     */
    public OrderedSetValue flatten(Type resultType) {
        if ( !elemType().isKindOfCollection(VoidHandling.EXCLUDE_VOID) ) 
            return this;

        OrderedSetValue res = new OrderedSetValue(getResultElementType(resultType));
        Iterator<Value> it = fElements.iterator(); 
        
        while (it.hasNext() ) {
            CollectionValue elem = (CollectionValue) it.next();
            Iterator<Value> it2 = elem.iterator(); 
            while (it2.hasNext() ) {
                Value elem2 = it2.next();
                res.add(elem2);
            }
        }
        return res;
    }

    public Collection<Value> collection() {
        return fElements;
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append("OrderedSet{");
        StringUtil.fmtSeqBuffered(sb, fElements.iterator(), ",");
        sb.append("}");
        
        return sb;
    }

    public int hashCode() {
        return fElements.hashCode();
    }

    protected Integer getClassCompareNr()
    {
    	return Integer.valueOf(4);
    }
    
    /** 
     * Two ordered sets are equal iff they contain the same elements in
     * same order. However, the declared types may be different.
     *
     * @pre T2 and T1 have common supertype, if this has type OrderedSet(T1) and 
     *      obj has type OrderedSet(T2). 
     */
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass().equals(getClass()) ) {
            OrderedSetValue os2 = (OrderedSetValue) obj;
            return fElements.equals(os2.fElements);
        }
        return false;
    }

    void add(Value v) {
    	if (!fElements.contains(v)) {
    		fElements.add(v);
    	}
    }


    void addAll(Collection<Value> v) {
    	Iterator<Value> iter = v.iterator();
    	while (iter.hasNext()) {
    		Value element = iter.next();
    		
    		if (!fElements.contains(element)) {
        		fElements.add(element);
        	}
    	}
    }
}
