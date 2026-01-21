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

import org.tzi.use.uml.api.IType;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.SequenceValue;
import org.tzi.use.uml.ocl.value.Value;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * The OCL Sequence type.
 *
 * @author  Mark Richters
 * @see     SetType
 * @see     BagType
 */
public final class SequenceType extends CollectionType {

    public SequenceType(Type elemType) {
        super(elemType);
    }
    
    @Override
    public boolean isInstantiableCollection() {
    	return true;
    }

    @Override
    public boolean isTypeOfCollection() {
    	return false;
    }
    
    @Override
    public boolean isTypeOfSequence() {
    	return true;
    }
        
    @Override
	public boolean isKindOfSequence(VoidHandling h) {
		return true;
	}

	public String shortName() {
        if (elemType().isKindOfCollection(VoidHandling.EXCLUDE_VOID) )
            return "Sequence(...)";
        else 
            return "Sequence(" + elemType() + ")";
    }

    public Type getLeastCommonSupertype(Type type)
    {
        // Reine Void-Collections behandeln: der nicht-void Typ dominiert
        if (type == null || type.isTypeOfVoidType()) {
            return this;
        }

        // Wenn der andere Typ keine Collection ist, gibt es keinen gemeinsamen Collection-LCS
        if (!(type instanceof CollectionType)) {
            return null;
        }

        // Spezieller Pfad: beide sind Sequenzen -> LCS bleibt Sequence
        if (type instanceof SequenceType) {
            CollectionType cType = (CollectionType) type;
            Type t1 = this.elemType();
            Type t2 = cType.elemType();

            Type commonElementType = t1.getLeastCommonSupertype(t2);
            if (commonElementType == null) {
                // Kein gemeinsamer Elementtyp -> OclAny als Fallback
                commonElementType = TypeFactory.mkOclAny();
            }
            return TypeFactory.mkSequence(commonElementType);
        }

        // Gemischte Collection-Spezialisierungen oder abstrakte Collections:
        // Delegiere an CollectionType, damit dort entschieden wird, ob eine
        // abstrakte Collection(...) verwendet wird.
        return super.getLeastCommonSupertype(type);
    }
    
    /** 
     * Returns true if this type is a subtype of <code>t</code>. 
     */
    public boolean conformsTo(Type t) {
        // Spezialfall: Sequence(T) <: Sequence(U)
        if (t instanceof SequenceType) {
            SequenceType t2 = (SequenceType) t;
            Type elemType = elemType();
            Type otherElemType = t2.elemType();
            return elemType.conformsTo(otherElemType);
        }

        // Sequence(T) ist NICHT allgemeinem Collection(U) konform, da
        // Set{Sequence{42}, Bag{42}} als Set(Collection(Integer)) getypt
        // werden soll und nicht als Set(Sequence(Integer)).
        //
        // D.h. Sequence(T) hat nur Sequenzen als Collection-Supertypen,
        // keine abstrakten Collection-Typen.
        if (t instanceof CollectionType && !(t instanceof SequenceType)) {
            return false;
        }

        // Alle anderen Typen sind keine Supertypen von Sequence(T)
        return false;
    }

    /**
     * Returns the set of all supertypes (including this type).  If
     * this collection has type Sequence(T) the result is the set of
     * all types Sequence(T') and Collection(T') where T' &lt;= T.
     */
    public Set<Type> allSupertypes() {
        Set<Type> res = new HashSet<Type>();
        res.addAll(super.allSupertypes());
        Set<? extends Type> elemSuper = (Set<? extends Type>) elemType().allSupertypes();
        Iterator<? extends Type> typeIter = elemSuper.iterator();
        
        while (typeIter.hasNext() ) {
            Type t = typeIter.next();
            res.add(TypeFactory.mkSequence(t));
        }
        return res;
    }
    
    @Override
	public Type createCollectionType(Type t) {
	 	return TypeFactory.mkSequence(t);
	}

    @Override
    public CollectionValue createCollectionValue(List<Value> values) {
    	return new SequenceValue(elemType(), values);
    }
    
    @Override
    public CollectionValue createCollectionValue(Value[] values) {
    	return new SequenceValue(elemType(), values);
    }
    
    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append("Sequence(");
        elemType().toString(sb);
        return sb.append(")");
    }
}
