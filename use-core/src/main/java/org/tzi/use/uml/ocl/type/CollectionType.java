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
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.StringUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Collection is the abstract base class for set, sequence, and bag.
 *
 * @author  Mark Richters
 * @see     SetType
 * @see     SequenceType
 * @see     BagType
 */
public class CollectionType extends TypeImpl {

    private final Type fElemType;

    protected CollectionType(Type elemType) {
        fElemType = elemType;
    }

    public Type elemType() {
        return fElemType;
    }

    @Override
    public boolean isKindOfCollection(VoidHandling h) {
    	return true;
    }
    
    @Override
    public boolean isTypeOfCollection() {
    	return true;
    }
    
    @Override
	public boolean conformsTo(IType other) {
        if (other == null) {
            return false;
        }

        // Wenn other ein internes OCL-Type-Objekt ist, machen wir eine präzise Prüfung
        if (other instanceof Type) {
            Type o = (Type) other;

            // VoidType: als Zieltyp für Collections nicht sinnvoll – keine Konformität
            if (o.isTypeOfVoidType()) {
                return false;
            }

            // Muss ein Collection-Typ sein, sonst nicht kompatibel
            if (!o.isKindOfCollection(VoidHandling.INCLUDE_VOID)) {
                return false;
            }

            // Jetzt ist garantiert, dass o tatsächlich eine CollectionType-Unterklasse ist
            if (!(o instanceof CollectionType)) {
                // Defensive fallback: unbekannter Collection-artiger Typ
                return false;
            }
            CollectionType t2 = (CollectionType) o;

            // Element-Typen müssen konform sein (covariant)
            return fElemType.conformsTo(t2.elemType());
        }

        // API-level IType ohne interne Typinformation: konservativer Fallback
        return other.isTypeOfCollection();
    }

    /**
     * Returns the set of all supertypes (including this type).  If
     * this collection has type Collection(T) the result is the set of
     * all types Collection(T') where T' <= T.
     */
    @Override
    public Set<Type> allSupertypes() {
        Set<Type> res = new HashSet<>();
        for (org.tzi.use.uml.api.IType it : fElemType.allSupertypes()) {
            if (it instanceof Type) {
                Type t = (Type) it;
                res.add(TypeFactory.mkCollection(t));
            }
        }
        return res;
    }

    @Override
    public Type getLeastCommonSupertype(Type type) {
        if (!(type instanceof CollectionType)) {
            return null;
        }

        CollectionType other = (CollectionType) type;
        Type t1 = this.elemType();
        Type t2 = other.elemType();

        // Void-Elementtypen behandeln: der nicht-void Elementtyp dominiert
        if (t1.isTypeOfVoidType() && !t2.isTypeOfVoidType()) {
            Type u = t2;
            return decideCollectionHullForLcs(other, u);
        }
        if (t2.isTypeOfVoidType() && !t1.isTypeOfVoidType()) {
            Type u = t1;
            return decideCollectionHullForLcs(other, u);
        }

        Type u = t1.getLeastCommonSupertype(t2);
        if (u == null) {
            // Kein gemeinsamer Element-Supertyp -> OclAny als sicherer Fallback
            u = TypeFactory.mkOclAny();
        }

        return decideCollectionHullForLcs(other, u);
    }

    /**
     * Entscheidet, welche Collection-Huelle fuer den LCS benutzt wird.
     * Wenn beide Collections dieselbe konkrete Klasse haben (z.B. beide SetType),
     * bleibt diese Spezialisierung erhalten. Ansonsten wird immer der
     * abstrakte Collection-Typ verwendet.
     */
    private Type decideCollectionHullForLcs(CollectionType other, Type elementLcs) {
        if (this.getClass().equals(other.getClass())) {
            // gleiche konkrete Collection-Spezialisierung beibehalten
            return createCollectionType(elementLcs);
        }
        // gemischte Spezialisierungen oder abstrakte Collections -> Collection(U)
        return TypeFactory.mkCollection(elementLcs);
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append("Collection(");
        elemType().toString(sb);
        return sb.append(")");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() == getClass() )
            return ((CollectionType) obj).elemType().equals(elemType());
        return false;
    }

    @Override
    public int hashCode() {
        return fElemType.hashCode();
    }
    
    @Override
    public boolean isVoidOrElementTypeIsVoid() {
		return elemType().isVoidOrElementTypeIsVoid();
	}

	/**
	 * Creates a collection type with the same
	 * kind of collection and the given element type.
	 * <p>
	 * <code>
	 * Collection(OclAny).createCollectionType(String) => Collection(String)<br/>
	 * Set(OclAny).createCollectionType(String) => Set(String)
	 * </code>
	 * </p>
	 * @param t The new type of the elements
	 * @return
	 */
	public Type createCollectionType(Type t) {
		return TypeFactory.mkCollection(t);
	}

	/**
	 * Creates a new collection value of this type, if possible.
	 * Note: The type <code>Collection</code> cannot be instantiated. 
	 * @param values The values of the elements of the collection. 
	 * @return
	 * @throws UnsupportedOperationException If called on CollectionType.
	 */
	public CollectionValue createCollectionValue(List<Value> values) {
		throw new UnsupportedOperationException("The abstract type " + StringUtil.inQuotes("Collection") + " cannot be instantiated.");
	}
	
	public CollectionValue createCollectionValue(Value[] values) {
		throw new UnsupportedOperationException("The abstract type " + StringUtil.inQuotes("Collection") + " cannot be instantiated.");
	}
}
