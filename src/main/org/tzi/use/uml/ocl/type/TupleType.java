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

package org.tzi.use.uml.ocl.type;

import java.util.HashSet;
import java.util.Set;

import org.tzi.use.util.StringUtil;
import java.util.Arrays;

/**
 * OCL Tuple type.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class TupleType extends Type {
    private Part[] fParts;

    public static class Part {
        private String fName;
        private Type fType;

        public Part(String name, Type type) {
            fName = name;
            fType = type;
        }

        public String toString() {
            return fName + ":" + fType;
        }

        public String name() {
            return fName;
        }

        public Type type() {
            return fType;
        }

        public boolean equals(Object obj) {
            if (obj == null) 
                return false;
            if (obj == this )
                return true;
            if (obj.getClass().equals(getClass())) {
                Part other = (Part) obj;
                return fName.equals(other.fName) && fType.equals(other.fType);
            }
            return false;
        }

        public int hashCode() {
            return fName.hashCode() + fType.hashCode() * 113;
        }
        
    }
        
    TupleType(Part[] parts) {
        fParts = parts;
    }

    public Part[] parts() {
        return fParts;
    }

    /** 
     * Returns true if this type is a subtype of <code>t</code>. 
     */
    public boolean isSubtypeOf(Type t) {
        return equals(t);
    }

    /** 
     * Returns a complete printable type name, e.g. 'Set(Bag(Integer))'. 
     */
    public String toString() {
        return "Tuple(" + StringUtil.fmtSeq(fParts, ",") + ")";
    }

    /** 
     * Overwrite to determine equality of types.
     */
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this ) return true;
        if (obj.getClass().equals(getClass()))
            return Arrays.equals(((TupleType) obj).fParts, fParts);
        return false;
    }

    public int hashCode() {
        int hashCode = 23;
        for (int i=0; i<fParts.length;++i) hashCode += fParts[i].hashCode();
        return hashCode;
    }

    public boolean isTupleType() {
    	return true;
    }

    /** 
     * Returns the set of all supertypes (including this type).
     */
    public Set allSupertypes() {
        Set res = new HashSet(1);
        res.add(this);
        return res;
    }

    public Part getPart(String name) {
        for (int i=0;i<parts().length;++i) {
            Part p = parts()[i];
            if (p.name().equals(name)) {
                return parts()[i];
            }
        }
        return null;
    }
}

