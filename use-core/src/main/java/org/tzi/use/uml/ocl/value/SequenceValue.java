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

import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Sequence values.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class SequenceValue extends CollectionValue {
    private final ArrayList<Value> fElements;
    
    /**
     * Constructs a new empty sequence.
     */
    public SequenceValue(Type elemType) {
        super(TypeFactory.mkSequence(elemType), elemType);
        fElements = new ArrayList<Value>();
    }

    /**
     * Constructs a sequence and adds all values. Elements are type checked
     * as they get inserted into the sequence.
     *
     * @exception IllegalArgumentException the type of at least one
     *            value does not match 
     */
    public SequenceValue(Type elemType, Value[] values) {
        this(elemType);
        for (int i = 0; i < values.length; i++)
            add(values[i]);
    }

    /**
     * Constructs a sequence and adds all values. Elements are type checked
     * as they get inserted into the sequence.
     *
     * @exception IllegalArgumentException the type of at least one
     *            value does not match 
     */
    public SequenceValue(Type elemType, final Collection<? extends Value> values) {
        this(elemType);
        addAll(values);
    }

    /**
     * Constructs a sequence and fills it with ranges of integers.
     * The length of the array must be even.
     * @exception IllegalArgumentException the type of at least one
     *            value does not match 
     */
    public SequenceValue(Type elemType, int[] ranges) {
        this(elemType);
        int i = 0; 
        while (i < ranges.length ) {
            int lower = ranges[i]; 
            int upper = ranges[i+1];
            for (int j = lower; j <= upper; j++)
                fElements.add(IntegerValue.valueOf(j));
            i += 2;
        }
    }

    @Override
    public boolean isSequence() {
    	return true;
    }
    
    @Override
    public CollectionType getRuntimeType(Type elementType) {
        return TypeFactory.mkSequence(elementType);
    }

    /**
     * Returns the element at the specified position in this sequence.
     *
     * @param index index of element to return.
     * @return the element at the specified position in this list.
     * 
     * @throws IndexOutOfBoundsException if the index is out of range (index
     *        &lt; 1 || index &gt; size()).
     */
    public Value get(int index) {
        return fElements.get(index);
    }


    @Override
	public Iterator<Value> iterator() {
        return fElements.iterator();
    }

    @Override
	public int size() {
        return fElements.size();
    }

    @Override
	public boolean isEmpty() {
        return fElements.isEmpty();
    }

    @Override
	public boolean includes(Value v) {
        return fElements.contains(v);
    }

    @Override
	public boolean includesAll(CollectionValue v) {
        Iterator<Value> it = v.iterator(); 
        while (it.hasNext() ) {
            Value elem = it.next();
            if (! fElements.contains(elem) )
                return false;
        }
        return true;
    }

    @Override
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
     * Returns a copy of this sequence excluding all occurrences of v.
     *
     * @pre T2 <= T1, if this has type Sequence(T1) and v has type T2.
     */
    public SequenceValue excluding(Type resultType, Value v) {
        SequenceValue res = new SequenceValue(getResultElementType(resultType));
        Iterator<Value> it = fElements.iterator(); 
        while (it.hasNext() ) {
            Value elem = it.next();
            if (! v.equals(elem) )
                res.add(elem);
        }
        return res;
    }

    @Override
	public int count(Value v) {
        int res = 0;
        Iterator<Value> it = fElements.iterator();
        
        while (it.hasNext() ) {
            if (v.equals(it.next()) )
                res++;
        }
        
        return res;
    }

    public SequenceValue union(Type resultType, SequenceValue v) {
        SequenceValue res = new SequenceValue(getResultElementType(resultType));
        res.addAll(fElements);
        res.addAll(v.fElements);
        return res;
    }

    public SequenceValue append(Type resultType, Value v) {
        SequenceValue res = new SequenceValue(getResultElementType(resultType));
        res.addAll(fElements);
        res.add(v);
        return res;
    }

    public SequenceValue prepend(Type resultType, Value v) {
        SequenceValue res = new SequenceValue(getResultElementType(resultType));
        res.add(v);
        res.addAll(fElements);
        return res;
    }

    /**
     * Constructs a new {@link SequenceValue} with {@link Value} <code>v</code> inserted
     * at the position <code>index</code>.
     * @param resultType The {link Type} of the newly created {@link SequenceValue}
     * @param index The position to insert the element at
     * @param v The value that is inserted at position <code>index</code>
     * @return A new {@link SequenceValue} with <code>v</code> inserted at position <code>index</code> or
     *         <code>null</code> if <code>index</code> is not a valid index.
     */
    public SequenceValue insertAt(Type resultType, IntegerValue index, Value v) {
    	if (index.value() < 1 || index.value() > fElements.size() + 1)
    		return null;
    	
    	SequenceValue res = new SequenceValue(getResultElementType(resultType));
    	res.addAll(fElements);
    	res.fElements.add(index.value() - 1, v);
    	
    	return res;
    }
    
    public int indexOf(Value v) {
    	return fElements.indexOf(v);
    }
    
    /**
     * Returns a new sequence only containing the give range of
     * elements.
     *
     * @throws    IndexOutOfBoundsException if range is illegal
     */
    public SequenceValue subSequence(Type resultType, int lower, int upper) {
        SequenceValue res = new SequenceValue(getResultElementType(resultType));
        for (int i = lower; i < upper; i++) 
            res.add(fElements.get(i));
        return res;
    }

    /**
     * Returns a new "flattened" sequence. This sequence must have
     * sequence elements.  
     * Otherwise the result is nondeterministic, as in Set->asSequence
     */
    public SequenceValue flatten(Type resultType) {
        if (! elemType().isKindOfCollection(VoidHandling.EXCLUDE_VOID) ) 
            return this;
    
        SequenceValue res = new SequenceValue(getResultElementType(resultType));
        Iterator<Value> it = fElements.iterator();
        
        while (it.hasNext() ) {
        	Value v = it.next();
        	if (v.isUndefined())
        		continue;
        	
            CollectionValue elem = (CollectionValue)v;
            Iterator<Value> it2 = elem.iterator(); 
            while (it2.hasNext() ) {
                Value elem2 = it2.next();
                res.add(elem2);
            }
        }
        
        return res;
    }

    @Override
	public Collection<Value> collection() {
        return fElements;
    }

    // For list access
    public List<Value> list() {
    	return fElements;
    }
    
    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append("Sequence{");
        StringUtil.fmtSeqBuffered(sb, fElements.iterator(), ",");
        return sb.append("}");
    }

    @Override
	public int hashCode() {
        return fElements.hashCode();
    }

    @Override
	protected Integer getClassCompareNr()
    {
    	return Integer.valueOf(2);
    }
    
    /** 
     * Two sequences are equal iff they contain the same elements in
     * same order. However, the declared types may be different.
     *
     * @pre T2 and T1 have common supertype, if this has type Sequence(T1) and 
     *      obj has type Sequence(T2). 
     */
    @Override
	public boolean equals(Object obj) {
        
        if (obj == null) return false;
        if (obj.getClass().equals(getClass()) ) {
            SequenceValue seq2 = (SequenceValue) obj;
            return fElements.equals(seq2.fElements);
        }
        return false;
    }

    void add(Value v) {
        fElements.add(v);
    }

    void addAll(Collection<? extends Value> v) {
    	fElements.addAll(v);
    }
}
