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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tzi.use.uml.ocl.type.TupleType;
import org.tzi.use.uml.ocl.type.TupleType.Part;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.type.UniqueLeastCommonSupertypeDeterminator;
import org.tzi.use.util.collections.CollectionComparator;

/**
 * Base class for collection values.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     SetValue
 * @see     SequenceValue
 * @see     BagValue
 */
public abstract class CollectionValue extends Value implements Iterable<Value> {
    protected Type fElemType; // store frequently needed element type too
	private boolean fTypeIsDirty; // true: fType and fElementType have to be recomputed

    CollectionValue(Type t, Type elemType) {
        super(t);
        fTypeIsDirty = true;
        fElemType = elemType;
    }

    public final Type elemType() {
        recalculateTypeIfRequired();
    	return fElemType;
    }
    
    public final Type type() {
        recalculateTypeIfRequired();
        return super.type();
    }

	private void recalculateTypeIfRequired() {
		if (fTypeIsDirty) {
        	Set<Type> types = new HashSet<Type>();
        	for (Value v : collection()) {
        		types.add(v.type());
        	}
        	fElemType = new UniqueLeastCommonSupertypeDeterminator().calculateFor(types);
        	if (fElemType == null) {
        		throw new RuntimeException("Could not determine unique common supertype for " + types);
        	}
        	doSetElemType();
        	fTypeIsDirty = false;
        }
	}            

    protected void markTypeAsDirty() {
    	fTypeIsDirty = true;
    }
    
    /**
     * This method must set the type of the value.
     * It is called by the template method <code>setElemType()</code>, which
     * sets the element type.
     * So implementors are guaranteed to be able to use <code>fElemtType</code>.   
     */
    protected abstract void doSetElemType();
    
    public abstract Iterator<Value> iterator();
    public abstract int size();
    public abstract boolean isEmpty();
    public abstract boolean includes(Value v);
    public abstract boolean includesAll(CollectionValue v);
    public abstract boolean excludesAll(CollectionValue v);
    public abstract int count(Value v);

    protected abstract Integer getClassCompareNr();
    
    public abstract Collection<Value> collection();

    @Override
    public boolean isCollection() {
    	return true;
    }
    
    @Override
    public int compareTo(Value o) {
        if (o == this )
            return 0;
        if (o instanceof UndefinedValue )
            return +1;
        if (o instanceof CollectionValue) {
            CollectionValue c = (CollectionValue)o;
            int res = new CollectionComparator<Value>().compare(collection(), c.collection());
            if (c.getClass().equals( getClass() ) ) {
                return res;
            } else {
            	// Need to make compares work in both directions a < b => b > a
            	return getClassCompareNr().compareTo(c.getClassCompareNr());
            }
        }
        throw new ClassCastException();
    }

  
    public SetValue product(CollectionValue col)
    {
    	Part[] parts = new Part[2];
    	parts[0] = new Part("first", this.elemType());
    	parts[1] = new Part("second", col.elemType());
    	
    	TupleType tupleType = TypeFactory.mkTuple(parts);
    	SetValue res = new SetValue(tupleType);
    	
    	Iterator<Value> iter1 = this.iterator();
    	Iterator<Value> iter2;
    	Value elem1, elem2;
    	TupleValue tuple;
    	Map<String, Value> valueParts;
    	
    	while (iter1.hasNext())
    	{
    		elem1 = iter1.next();
    		iter2 = col.iterator();
    		
    		while(iter2.hasNext())
    		{
    			elem2 = iter2.next();
    			
    			valueParts = new HashMap<String, Value>(2);
    			valueParts.put("first", elem1);
    			valueParts.put("second", elem2);
    			
    			tuple = new TupleValue(tupleType, valueParts);
    			res.add(tuple);
    		}
    	}
    	
    	return res;
    }
    
    /**
     * Creates a sorted list of the elements included in the collection.
     * This is especially used for messages to the user and for tests to
     * get a natural output.
     * @return Sorted <code>List</code> of the elements of the set.
     */
    public List<Value> getSortedElements() {
    	List<Value> result = new ArrayList<Value>(collection());
    	Collections.sort(result);
    	return result;
    }
    
    public BagValue asBag() {
    	return new BagValue(elemType(), collection());
    }
    
    public SetValue asSet() {
    	return new SetValue(elemType(), collection());
    }
    
    public OrderedSetValue asOrderedSet() {
    	if (this.isOrderedSet() || this.isSequence())
    		return new OrderedSetValue(elemType(), this.collection());
    	else
    		return new OrderedSetValue(elemType(), this.getSortedElements());
    }
    
    public SequenceValue asSequence() {
    	if (this.isOrderedSet() || this.isSequence())
    		return new SequenceValue(elemType(), this.collection());
    	else
    		return new SequenceValue(elemType(), this.getSortedElements());
    }
}

