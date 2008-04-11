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
import java.util.TreeSet;

import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.util.StringUtil;

/**
 * Set value. The set is read-only outside of this package. Changes always
 * result in a new Set. All methods operating on element values can throw an
 * IllegalArgumentException if a passed value's type does not conform to the
 * element type of the set.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Mark Richters
 */
public class SetValue extends CollectionValue {
    private TreeSet fElements; // (Value)

    /**
     * Constructs a new empty set.
     */
    public SetValue(Type elemType) {
        super(TypeFactory.mkSet(elemType), elemType);
        fElements = new TreeSet();
    }

    /**
     * Constructs a set and adds all values. Elements are type checked as they
     * get inserted into the set.
     * 
     * @exception IllegalArgumentException
     *                the type of at least one value does not match
     */
    public SetValue(Type elemType, Value[] values) {
        this(elemType);
        for (int i = 0; i < values.length; i++)
            add(values[i]);
    }

    /**
     * Constructs a set and adds all values. Elements are type checked as they
     * get inserted into the set.
     * 
     * @exception IllegalArgumentException
     *                the type of at least one value does not match
     */
    public SetValue(Type elemType, Collection values) {
        this(elemType);
        Iterator it = values.iterator();
        while (it.hasNext())
            add((Value) it.next());
    }

    /**
     * Constructs a set and fills it with ranges of integers.
     * 
     * @exception IllegalArgumentException
     *                the type of at least one value does not match
     */
    public SetValue(Type elemType, int[] ranges) {
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

    // /**
    // * Constructs a set from a collection of values. No type checking
    // * is done.
    // */
    // SetValue deepCopy() {
    // SetValue res = new SetValue(elemType());
    // res.fElements.addAll(fElements);
    // return res;
    // }

    /**
     * Adds an element to the set. The element's type is checked.
     * 
     */
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
        if (needToDeriveRuntimeType)
            deriveRuntimeType();
    }

    void addAll(Collection v) {
        // if (! v.type().isSubtypeOf(elemType()) )
        // throw new IllegalArgumentException("type mismatch: " + v.type() +
        // ", " + elemType());
        fElements.addAll(v);
        deriveRuntimeType();
    }

    /**
     * Adds an element to the set. The element's type is checked.
     * 
     */
    void remove(Value v) {
        // if (! v.type().isSubtypeOf(elemType()) )
        // throw new IllegalArgumentException("type mismatch: " + v.type() +
        // ", " + elemType());

        fElements.remove(v);
        deriveRuntimeType();
    }

    /**
     * Returns a new set which is the union of this and v.
     * 
     * @pre T2 <= T1, if this has type Set(T1) and v has type Set(T2).
     */
    public SetValue union(SetValue v) {
        if (v.isEmpty())
            return this;

        SetValue res = new SetValue(elemType());

        // add elements of this set to result
        res.addAll(fElements);

        // Iterator it = fElements.iterator();
        // while (it.hasNext() ) {
        // res.fElements.add(it.next());
        // }

        // add elements of v set to result
        res.addAll(v.fElements);

        // it = v.iterator();
        // while (it.hasNext() ) {
        // res.fElements.add(it.next());
        // }
        return res;
    }

    /**
     * Returns a new bag which is the union of this and v.
     * 
     * @pre T2 <= T1, if this has type Set(T1) and v has type Bag(T2).
     */
    public BagValue union(BagValue v) {
        BagValue res = new BagValue(elemType());

        // add elements of this set to result
        res.addAll(fElements);
        // Iterator it = fElements.iterator();
        // while (it.hasNext() ) {
        // res.addUnchecked((Value) it.next());
        // }

        // add elements of v set to result
        res.addAll(v.collection());
        // it = v.iterator();
        // while (it.hasNext() ) {
        // res.addUnchecked((Value) it.next());
        // }
        return res;
    }

    /**
     * Return a new set which is the intersection of this and v.
     * 
     * @pre T2 <= T1, if this has type Set(T1) and v has type Set(T2).
     */
    public SetValue intersection(SetValue v) {
        SetValue res = new SetValue(elemType());
        if (this.isEmpty() || v.isEmpty())
            return res;

        // use the smaller set for iteration
        SetValue v1, v2;
        if (size() < v.size()) {
            v1 = this;
            v2 = v;
        } else {
            v1 = v;
            v2 = this;
        }

        Iterator it = v1.fElements.iterator();
        while (it.hasNext()) {
            Value elem = (Value) it.next();
            if (v2.includes(elem))
                res.add(elem);
        }
        return res;
    }

    public SetValue intersection(BagValue v) {
        return intersection(v.asSet());
    }

    /**
     * Return a new set which is the difference of this and v.
     * 
     * @pre T2 <= T1, if this has type Set(T1) and v has type Set(T2).
     */
    public SetValue difference(SetValue v) {
        if (v.isEmpty())
            return this;

        SetValue res = new SetValue(elemType());

        // add elements of this set to result
        Iterator it = fElements.iterator();
        while (it.hasNext()) {
            Value elem = (Value) it.next();
            if (!v.includes(elem))
                res.add(elem);
        }
        return res;
    }

    /**
     * Return a copy of this set including v
     * 
     * @pre T2 <= T1, if this has type Set(T1) and v has type Set(T2).
     */
    public SetValue including(Value v) {
        // copy this set
        SetValue res = new SetValue(elemType(), fElements);
        res.add(v);
        return res;
    }

    /**
     * Return a copy of this set excluding v
     * 
     * @pre T2 <= T1, if this has type Set(T1) and v has type T2.
     */
    public SetValue excluding(Value v) {
        // copy this set
        SetValue res = new SetValue(elemType(), fElements);
        res.remove(v);
        return res;
    }

    /**
     * Return a new set which is the symmetric difference of this and v, ie. (A -
     * B) U (B - A).
     * 
     * @pre T2 <= T1, if this has type Set(T1) and v has type Set(T2).
     */
    public SetValue symmetricDifference(SetValue v) {
        return this.difference(v).union(v.difference(this));
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
        Iterator it = v.iterator();
        while (it.hasNext()) {
            Value elem = (Value) it.next();
            if (!fElements.contains(elem))
                return false;
        }
        return true;
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
        return fElements.contains(v) ? 1 : 0;
    }

    public SequenceValue asSequence() {
        return new SequenceValue(elemType(), fElements);
    }

    public BagValue asBag() {
        return new BagValue(elemType(), fElements);
    }

    /**
     * Returns a new "flattened" set. This set must have collection elements.
     */
    public SetValue flatten() {
        if (!elemType().isCollection())
            throw new RuntimeException("Set `" + type()
                    + "' cannot be flattened");

        CollectionType c2 = (CollectionType) elemType();
        SetValue res = new SetValue(c2.elemType());
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

    public String toString() {
        return "Set{" + StringUtil.fmtSeq(fElements.iterator(), ",") + "}";
    }

    public int hashCode() {
        return fElements.hashCode();
    }

    /**
     * Two sets are equal iff they contain the same elements. However, the
     * declared types may be different if the second is a subtype of the first.
     * 
     * @pre T2 <= T1, if this has type Set(T1) and obj has type Set(T2).
     */
    public boolean equals(Object obj) {
        // FIXME: this equals is not symmetric (ocl-equals != java-equals?)
        if (obj == null)
            return false;
        if (obj.getClass().equals(getClass())) {
            SetValue set2 = (SetValue) obj;
            return set2.type().isSubtypeOf(this.type())
                    && fElements.equals(set2.fElements);
        }
        return false;
    }

    // public int compareTo(Object o) {
    // if (o == this )
    // return 0;
    // if (o instanceof UndefinedValue )
    // return +1;
    // if (! (o instanceof SetValue) )
    // throw new ClassCastException();
    // SetValue set2 = (SetValue) o;
    // if (! set2.type().isSubtypeOf(this.type()) )
    // throw new ClassCastException("this.type() = " + this.type() +
    // ", set2.type() = " + set2.type());

    // return new CollectionComparator().compare(fElements, set2.fElements);
    // }
}
