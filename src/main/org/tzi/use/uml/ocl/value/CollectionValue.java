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

import java.util.Collection;
import java.util.Iterator;

import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.util.CollectionComparator;

/**
 * Base class for collection values.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     SetValue
 * @see     SequenceValue
 * @see     BagValue
 */
public abstract class CollectionValue extends Value {
    private Type fElemType; // store frequently needed element type too

    CollectionValue(Type t, Type elemType) {
        super(t);
        fElemType = elemType;
    }

    public Type elemType() {
        return fElemType;
    }            

    public void setElemType( Type t ) {
        fElemType = t;
        if (this instanceof SetValue) {
            setType( TypeFactory.mkSet(fElemType));
        } else if (this instanceof BagValue) {
            setType( TypeFactory.mkBag(fElemType));
        } else if (this instanceof SequenceValue) {
            setType( TypeFactory.mkSequence(fElemType));
        } else {
            setType( TypeFactory.mkCollection(fElemType));
        } 
    }
    

    //    public abstract void add(Value v);
    public abstract Iterator iterator();
    public abstract int size();
    public abstract boolean isEmpty();
    public abstract boolean includes(Value v);
    public abstract boolean includesAll(CollectionValue v);
    public abstract boolean excludesAll(CollectionValue v);
    public abstract int count(Value v);

    protected abstract Integer getClassCompareNr();
    
    public abstract Collection collection();

    public int compareTo(Object o) {
        if (o == this )
            return 0;
        if (o instanceof UndefinedValue )
            return +1;
        if (o instanceof CollectionValue) {
            CollectionValue c = (CollectionValue)o;
            int res = new CollectionComparator().compare(collection(), c.collection());
            if (c.getClass().equals( getClass() ) ) {
                return res;
            } else {
            	// Need to make compares work in both directions a < b => b > a
            	return getClassCompareNr().compareTo(c.getClassCompareNr());
            }
        }
        throw new ClassCastException();
    }

    /**
     * Returns the value for the type parameter of this collection.
     */
    protected Type inferElementType() 
        throws ExpInvalidException
    {

        if (collection().size() == 0) {
            return fElemType;
        }
        
        Value[] values = new Value[collection().size()];
        collection().toArray(values);
        
        // One Value => Type is element type
        if (values.length == 1) {
        	return values[0].type();
        }
        
        // Two or more values
        Type commonSuperType = values[0].type();
    	Type t2;
    	
    	for (int i = 1; i < values.length; ++i) {
    		t2 = values[i].type();
    		commonSuperType = commonSuperType.getLeastCommonSupertype(t2);
    		
    		if (commonSuperType == null)
    			throw new ExpInvalidException("Type mismatch, " + this.getClass().toString() + " element " + 
                        (i + 1) +
                        " does not have a common supertype " + 
                        "with previous elements.");
    	}
    	
        // FIXME: deal with other cases: t1 < t, t2 < t, t1 and t2 unrelated.
        return commonSuperType;
    }


    protected void deriveRuntimeType() {
        try {
            setElemType(inferElementType()); 
        } catch( ExpInvalidException e) {
            throw new RuntimeException(e);
        }
    }
    
}

