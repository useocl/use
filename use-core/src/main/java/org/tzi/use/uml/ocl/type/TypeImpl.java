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

package org.tzi.use.uml.ocl.type;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.tzi.use.uml.api.IType;

/**
 * Abstract base class of all types. Types should be created only by
 * the TypeFactory class.
 *  
 * @author      Mark Richters 
 */
public abstract class TypeImpl implements Type {

    /** 
     * Overwrite this method to return a short printable name of
     * a type (e.g. 'Set(...)') rather than a full type name.
     */
    @Override
    public String shortName() {
        return toString();
    }

        @Override
        public String qualifiedName() {
            return toString();
        }

    /** 
     * Returns a complete printable type name, e.g. 'Set(Bag(Integer))'.
     * For complex string operations see {@link #toString(StringBuilder)}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        this.toString(sb);
        return sb.toString();
    }

    /** 
     * Adds a complete printable type name, e.g. 'Set(Bag(Integer))' to
     * the StringBuilder 
     */
    @Override
    public abstract StringBuilder toString(StringBuilder sb);

    /** 
     * Overwrite to determine equality of types.
     */
    @Override
    public abstract boolean equals(Object obj);

    /** 
     * Always define hashCode when defining equals().
     */
    @Override
    public abstract int hashCode();

    @Override
    public boolean conformsTo(IType other) {
        if (other == null) {
            return false;
        }

        // If other is an OCL low-level Type we can compare directly
        if (other instanceof Type) {
            Type otherType = (Type) other;
            if (this.equals(otherType)) {
                return true;
            }
            // default: if other is OclAny then it conforms
            if (otherType.isTypeOfOclAny()) {
                return true;
            }
            return false;
        }

        // other is only an API-level IType (adapter or mm-level). Fall back to a conservative check:
        if (other.isTypeOfOclAny()) {
            return true;
        }

        // We cannot determine more precisely at this level; default to false
        return false;
    }

    @Override
    public Type getLeastCommonSupertype(Type type) {
        if (type == null )
            return this;

        // easy case: equal type
        if (this.equals(type))
            return type;

        // one of the types (this or type) is oclVoid
        if (this.isTypeOfVoidType())
            return type;

        if (type.isTypeOfVoidType())
            return this;

        // Collection types have their own implementation of
        // getLeastCommonSupertype and have no common type to
        // simple types
        if (type.isKindOfCollection(IType.VoidHandling.EXCLUDE_VOID)) {
            return null;
        }
        
        // determine common supertypes = intersection of all
        // supertypes of all elements
        Set<Type> cs = new HashSet<>();
        // collect only OCL Type instances from this.allSupertypes()
        Set<Type> supsThis = new HashSet<>();
        for (IType it : this.allSupertypes()) {
            if (it instanceof Type) {
                supsThis.add((Type) it);
            }
        }
        Set<Type> supsOther = new HashSet<>();
        for (IType it : type.allSupertypes()) {
            if (it instanceof Type) {
                supsOther.add((Type) it);
            }
        }
        cs.addAll(supsThis);
        cs.retainAll(supsOther);

        // return immediately if intersection is empty
        if (cs.isEmpty() )
            return null;

        // determine the least common supertype
        // if there is only one common supertype return it
        if (cs.size() == 1 ) 
            return cs.iterator().next();
        
        // For performance reasons: if oclAny and another type then the other type 
        // is the least common supertype 
        if (cs.size() == 2 && cs.contains(TypeFactory.mkOclAny())) {
            cs.remove(TypeFactory.mkOclAny());
            // Returns the only element
            return cs.iterator().next();
        }
        
        // search for a type that is less than or equal to all other types
        Type cType = null;
        
        Iterator<Type> it1 = cs.iterator();
        outerLoop: 
        while (it1.hasNext() ) {
            Type t1 = it1.next();
            Iterator<Type> it2 = cs.iterator();
            
            while (it2.hasNext() ) {
                Type t2 = it2.next();
                if (! t1.conformsTo(t2) )
                    continue outerLoop;
            }
            
            cType = t1;
            break;
        }
            
        return cType;
    }

    /**
     * @return Whether this type is OclVoid or an collection type having a void element type (recursively)
     */
    public boolean isVoidOrElementTypeIsVoid() {
        return false;
    }

    // The following set of functions is used to
    // avoids numerous instanceof tests in user code.
    // Corresponding sub types override these methods and return true.

    @Override
    public boolean isKindOfNumber(IType.VoidHandling h) {
        return false;
    }

    @Override
    public boolean isTypeOfInteger() {
        return false;
    }

    @Override
    public boolean isKindOfInteger(IType.VoidHandling h) {
        return false;
    }

    @Override
    public boolean isTypeOfUnlimitedNatural() {
        return false;
    }

    @Override
    public boolean isKindOfUnlimitedNatural(IType.VoidHandling h) {
        return false;
    }

    @Override
    public boolean isKindOfReal(IType.VoidHandling h) {
        return false;
    }

    @Override
    public boolean isTypeOfReal() {
        return false;
    }

    @Override
    public boolean isKindOfString(IType.VoidHandling h) {
        return false;
    }

    @Override
    public boolean isTypeOfString() {
        return false;
    }

    @Override
    public boolean isKindOfBoolean(IType.VoidHandling h) {
        return false;
    }

    @Override
    public boolean isTypeOfBoolean() {
        return false;
    }

    @Override
    public boolean isKindOfEnum(IType.VoidHandling h) {
        return false;
    }

    @Override
    public boolean isTypeOfEnum() {
        return false;
    }

    @Override
    public boolean isKindOfCollection(VoidHandling h) {
        return false;
    }

    @Override
    public boolean isTypeOfCollection() {
        return false;
    }

    @Override
    public boolean isKindOfSet(VoidHandling h) {
        return false;
    }

    @Override
    public boolean isTypeOfSet() {
        return false;
    }

    @Override
    public boolean isKindOfSequence(VoidHandling h) {
        return false;
    }

    @Override
    public boolean isTypeOfSequence() {
        return false;
    }

    @Override
    public boolean isKindOfOrderedSet(VoidHandling h) {
        return false;
    }

    @Override
    public boolean isTypeOfOrderedSet() {
        return false;
    }

    @Override
    public boolean isKindOfBag(VoidHandling h) {
        return false;
    }

    @Override
    public boolean isTypeOfBag() {
        return false;
    }

    @Override
    public boolean isKindOfClass(VoidHandling h) {
        return false;
    }

    @Override
    public boolean isTypeOfClass() {
        return false;
    }

    @Override
    public boolean isKindOfDataType(VoidHandling h) {
        return false;
    }

    @Override
    public boolean isTypeOfDataType() {
        return false;
    }

    @Override
    public boolean isKindOfAssociation(VoidHandling h) {
        return false;
    }

    @Override
    public boolean isTypeOfAssociation() {
        return false;
    }

    @Override
    public boolean isKindOfOclAny(VoidHandling h) {
        return false;
    }

    @Override
    public boolean isTypeOfOclAny() {
        return false;
    }

    @Override
    public boolean isKindOfTupleType(VoidHandling h) {
        return false;
    }

    @Override
    public boolean isTypeOfTupleType() {
        return false;
    }

    @Override
    public boolean isTypeOfVoidType() {
        return false;
    }

    @Override
    public boolean isInstantiableCollection() {
        return false;
    }

    @Override
    public boolean isKindOfClassifier(VoidHandling h) {
        return false;
    }

    @Override
    public boolean isTypeOfClassifier() {
        return false;
    }
}
