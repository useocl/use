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

$Id$

package org.tzi.use.uml.ocl.value;

import java.util.Arrays;

import org.tzi.use.uml.ocl.type.TupleType;
import org.tzi.use.util.CollectionComparator;

/**
 * Tuple values.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public final class TupleValue extends Value implements Comparable {
    private Value[] fParts;

    /**
     * Constructs a tuple and sets all values. Elements are type checked
     * as they get inserted into the tuple.
     *
     * @exception IllegalArgumentException the type of at least one
     *            value does not match 
     */
    public TupleValue(TupleType t, Value[] parts) {
        super(t);
        fParts = new Value[parts.length];
        TupleType.Part[] typeParts = t.parts();
        for (int i = 0; i < parts.length; i++) {
            if (! parts[i].type().isSubtypeOf(typeParts[i].type()) )
                throw new IllegalArgumentException("type mismatch: " + 
                                                   parts[i].type() + ", " + typeParts[i].type());
            fParts[i] = parts[i];
        }
    }

    public String toString() {
        String s = "Tuple{";
        TupleType t = (TupleType) type();
        for (int i = 0; i < fParts.length; i++) {
            s += t.parts()[i].name() + "=" + fParts[i];
            if (i < fParts.length - 1 )
                s += ",";
        }
        return s + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this )
            return true;
        else if (obj instanceof TupleValue ) {
            TupleValue other = (TupleValue) obj;
            return Arrays.equals(fParts,other.fParts);
        }
        return false;
    }

    public int hashCode() {
        return fParts.hashCode();
    }

    public int compareTo(Object o) {
        if (o == this )
            return 0;
        if (o instanceof UndefinedValue )
            return +1;
        if (! (o instanceof TupleValue) )
            throw new ClassCastException();
        TupleValue other = (TupleValue) o;
        return new CollectionComparator().compare(Arrays.asList(fParts), 
                                                  Arrays.asList(other.fParts));
    }

    
    public Value getElementValue(String name) {
        TupleType t = (TupleType)type();
        for (int i=0;i<t.parts().length;++i) {
            TupleType.Part p = t.parts()[i];
            if (p.name().equals(name)) {
                return fParts[i];
            }
        }
        throw new IllegalArgumentException("No such element: " + name);
    }

}

