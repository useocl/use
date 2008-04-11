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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.TreeBag;

/**
 * Bag value. The bag is read-only outside of this package. Changes always
 * result in a new Bag. All methods operating on element values can throw an
 * IllegalArgumentException if a passed value's type does not conform to the
 * element type of the bag.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Mark Richters
 */
public class BagValue extends CollectionValue {
    private TreeBag fElements; // (Value)

    /**
     * Constructs a new empty bag.
     */
    public BagValue(Type elemType) {
        super(TypeFactory.mkBag(elemType), elemType);
        fElements = new TreeBag();
    }

    /**
     * Constructs a bag and adds all values. Elements are type checked as they
     * get inserted into the bag.
     * 
     * @exception IllegalArgumentException
     *                the type of at least one value does not match
     */
    public BagValue(Type elemType, Value[] values) {
        this(elemType);
        for (int i = 0; i < values.length; i++)
            add(values[i]);
    }

    /**
     * Constructs a bag and adds all values. Elements are type checked as they
     * get inserted into the bag.
     * 
     * @exception IllegalArgumentException
     *                the type of at least one value does not match
     */
    public BagValue(Type elemType, Collection values) {
        this(elemType);
        Iterator it = values.iterator();
        while (it.hasNext())
            add((Value) it.next());
    }

    /**
     * Constructs a bag and fills it with ranges of integers.
     * 
     * @exception IllegalArgumentException
     *                the type of at least one value does not match
     */
    public BagValue(Type elemType, int[] ranges) {
        this(elemType);
        int i = 0;
        while (i < ranges.length) {
            int lower = ranges[i];
            int upper = ranges[i + 1];
            for (int j = lower; j <= upper; j++)
                fElements.add(new IntegerValue(j));
            i += 2;
        }
    }

    /**
     * Adds an element to the bag. The element's type is not checked.
     */
    void addUnchecked(Value v) {
        fElements.add(v);
    }

    /**
     * Returns a new bag which is the union of this and v.
     * 
     * @pre T2 <= T1, if this has type Bag(T1) and v has type Bag(T2).
     */
    public BagValue union(BagValue v) {
        if (v.isEmpty())
            return this;

        BagValue res = new BagValue(elemType());

        // add elements of both bags to result
        res.addAll(fElements);
        res.addAll(v.fElements);
        return res;
    }

    /**
     * Returns a new bag which is the intersection of this bag and v.
     * 
     * @pre T2 <= T1, if this has type Bag(T1) and v has type Bag(T2).
     */
    public BagValue intersection(BagValue v) {
        BagValue res = new BagValue(elemType());
        if (this.isEmpty() || v.isEmpty())
            return res;

        // use the smaller bag for iteration
        BagValue v1, v2;
        if (size() < v.size()) {
            v1 = this;
            v2 = v;
        } else {
            v1 = v;
            v2 = this;
        }

        Iterator it = v1.fElements.uniqueIterator();
        while (it.hasNext()) {
            Value elem = (Value) it.next();
            if (v2.includes(elem)) {
                // result is the minimum number of objects
                // contained in both bags.
                res.add(elem, Math.min(v1.count(elem), v2.count(elem)));
            }
        }
        return res;
    }

    public Iterator iterator() {
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
        return fElements.containsAll(v.collection());
    }

    public boolean excludesAll(CollectionValue v) {
        Iterator it = v.iterator();
        while (it.hasNext()) {
            Value elem = (Value) it.next();
            if (fElements.contains(elem))
                return false;
        }
        return true;
    }

    public int count(Value v) {
        return fElements.occurrences(v);
    }

    /**
     * Returns a copy of this bag including v
     * 
     * @pre T2 <= T1, if this has type Bag(T1) and v has type T2.
     */
    public BagValue including(Value v) {
        // copy this bag
        BagValue res = new BagValue(elemType(), fElements);
        // check and add v
        res.add(v);
        return res;
    }

    /**
     * Return a copy of this bag excluding v
     * 
     * @pre T2 <= T1, if this has type Bag(T1) and v has type T2.
     */
    public BagValue excluding(Value v) {
        if (!v.type().isSubtypeOf(elemType()))
            throw new IllegalArgumentException("type mismatch");

        // copy this bag
        BagValue res = new BagValue(elemType(), fElements);
        res.removeAll(v);
        return res;
    }

    public SequenceValue asSequence() {
        return new SequenceValue(elemType(), fElements);
    }

    public SetValue asSet() {
        // the set constructor will remove duplicates
        return new SetValue(elemType(), fElements);
    }

    /**
     * Returns a new "flattened" bag. This bag must have collection elements.
     */
    public BagValue flatten() {
        if (!elemType().isCollection())
            throw new RuntimeException("Bag `" + type()
                    + "' cannot be flattened");

        CollectionType c2 = (CollectionType) elemType();
        BagValue res = new BagValue(c2.elemType());
        Iterator it = fElements.iterator();
        while (it.hasNext()) {
            CollectionValue elem = (CollectionValue) it.next();
            Iterator it2 = elem.iterator();
            while (it2.hasNext()) {
                Value elem2 = (Value) it2.next();
                res.add(elem2);
            }
        }
        return res;
    }

    public Collection collection() {
        return fElements;
    }

    /**
     * Returns a string representation of this bag. The elements are sorted.
     */
    public String toString() {
        Object[] valArray = fElements.toArray();
        Iterator it = fElements.iterator();
        // sorting needed in HashMaps but not anymore in TreeMaps
        // Arrays.sort(valArray);
        return "Bag{"
                + StringUtil.fmtSeq(Arrays.asList(valArray).iterator(), ",")
                + "}";
    }

    /**
     * Two bags are equal iff they contain the same elements. The declared types
     * may be different if the second is a subtype of the first.
     * 
     * @pre T2 <= T1, if this has type Bag(T1) and obj has type Bag(T2).
     */
    public boolean equals(Object obj) {
        // FIXME: this equals is not symmetric (ocl-equals != java-equals?)
        if (obj == null)
            return false;
        if (obj.getClass().equals(getClass())) {
            BagValue bag2 = (BagValue) obj;
            return bag2.type().isSubtypeOf(this.type())
                    && fElements.equals(bag2.fElements);
        }
        return false;
    }

    public int hashCode() {
        return fElements.hashCode();
    }

    void addAll(Collection v) {
        // if (! v.type().isSubtypeOf(elemType()) )
        // throw new IllegalArgumentException("type mismatch: " + v.type() +
        // ", " + elemType());
        fElements.addAll(v);
        try {
            setElemType(inferElementType());
        } catch (ExpInvalidException e) {
            throw new RuntimeException(e);
        }
    }

    void add(Value v) {
        boolean needToDeriveRuntimeType = true;
        // performance optimization
        for (Iterator it = fElements.iterator(); it.hasNext();) {
            Value val = (Value) it.next();
            if (val.type().equals(v.type())) {
                needToDeriveRuntimeType = false;
                break;
            }
        }
        fElements.add(v);
        if (needToDeriveRuntimeType) {
            try {
                setElemType(inferElementType());
            } catch (ExpInvalidException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void add(Value v, int i) {
        // if (! v.type().isSubtypeOf(elemType()) )
        // throw new IllegalArgumentException("type mismatch: " + v.type() +
        // ", " + elemType());
        fElements.add(v, i);
        try {
            setElemType(inferElementType());
        } catch (ExpInvalidException e) {
            throw new RuntimeException(e);
        }
    }

    void removeAll(Value v) {
        // if (! v.type().isSubtypeOf(elemType()) )
        // throw new IllegalArgumentException("type mismatch: " + v.type() +
        // ", " + elemType());
        fElements.removeAll(v);
        try {
            setElemType(inferElementType());
        } catch (ExpInvalidException e) {
            throw new RuntimeException(e);
        }
    }

    // public int compareTo(Object o) {
    // if (o == this )
    // return 0;
    // if (o instanceof UndefinedValue )
    // return +1;
    // if (! (o instanceof BagValue) )
    // throw new ClassCastException();
    // BagValue bag2 = (BagValue) o;
    // if (! bag2.type().isSubtypeOf(this.type()) )
    // throw new ClassCastException("this.type() = " + this.type() +
    // ", bag2.type() = " + bag2.type());

    // return new CollectionComparator().compare(fElements, bag2.fElements);
    // }
}
