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

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.event.ListSelectionEvent;

import org.tzi.use.util.BufferedToString;
import org.tzi.use.util.StringUtil;

/**
 * OCL Tuple type.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class TupleType extends Type {
    private Map<String, Part> fParts = new TreeMap<String, Part>();

    public static class Part implements BufferedToString {
        private String fName;
        private Type fType;

        public Part(String name, Type type) {
            fName = name;
            fType = type;
        }

        @Override
        public String toString() {
            return this.toString(new StringBuilder()).toString();
        }

        @Override
        public StringBuilder toString(StringBuilder sb) {
        	return sb.append(fName).append(":").append(fType);
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
    	for(int index = 0; index < parts.length; index++)
        {
    		fParts.put(parts[index].name(), parts[index]);
        }
    }

    @Override
    public boolean isTupleType(boolean excludeVoid) {
    	return true;
    }
    
    /**
     * Returns the defined tuple parts
     * @return A map of the type Map&lt;String, Part&gt;
     */
    public Map<String, Part> getParts() {
        return fParts;
    }

    /** 
     * Returns true if this type is a subtype of <code>t</code>. 
     */
    public boolean isSubtypeOf(Type t) {
    	if (t.isTrueOclAny())
    		return true;
    	
    	if(!t.isTupleType(true)){
    		return false;
    	}

    	TupleType otherType = (TupleType)t;
    	
    	for(TupleType.Part part : fParts.values()){
    		if (!otherType.fParts.containsKey(part.name()))
    			return false;
    		
    		TupleType.Part otherPart = otherType.fParts.get(part.name());
    		
    		if (!part.type().isSubtypeOf(otherPart.type()))
    			return false;
    	}
    	
    	return true;
    }

    @Override
	public Type getLeastCommonSupertype(Type type) {
    	if (type.isVoidType())
    		return this;
    	
    	if(!type.isTupleType(true)){
    		return TypeFactory.mkOclAny();
    	}

    	TupleType otherType = (TupleType)type;
    	if (otherType.fParts.size() != this.fParts.size()) {
    		return TypeFactory.mkOclAny();
    	}
    	
    	Part[] commonParts = new Part[this.fParts.size()];
    	int index = 0;
    	
    	for(TupleType.Part part : fParts.values()){
    		if (!otherType.fParts.containsKey(part.name()))
    			return TypeFactory.mkOclAny();
    		
    		TupleType.Part otherPart = otherType.fParts.get(part.name());
    		commonParts[index] = new Part(part.fName, part.fType.getLeastCommonSupertype(otherPart.fType));
    		index++;
    	}
    	
    	TupleType commonType = TypeFactory.mkTuple(commonParts);
    	return commonType;
	}

    /** 
     * Returns a complete printable type name, e.g. 'Set(Bag(Integer))'. 
     */
    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append("Tuple(");
        StringUtil.fmtSeqBuffered(sb, fParts.values().iterator(), ",");
        return sb.append(")");
    }

    /** 
     * Overwrite to determine equality of types.
     */
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this ) return true;
        if (obj.getClass().equals(getClass()))
            return fParts.equals(((TupleType) obj).fParts);
        return false;
    }

    public int hashCode() {
        int hashCode = 23;
        Iterator<Part> iter = fParts.values().iterator();
        
        while(iter.hasNext()) { 
        	hashCode += iter.next().hashCode();
        }
        
        return hashCode;
    }
    

    /** 
     * Returns the set of all supertypes (including this type).
     */
    public Set<Type> allSupertypes() {
        Set<Type> res = new HashSet<Type>(1);
        res.add(this);
        res.add(TypeFactory.mkOclAny());
        List<Part> selectedSupertypes = new LinkedList<Part>();
        List<Part> remainingTypes = new LinkedList<Part>();
        remainingTypes.addAll(fParts.values());
        genAllSuperTypes(selectedSupertypes, remainingTypes, res);
        return res;
    }
    
    private void genAllSuperTypes(List<Part> selectedSupertypes, List<Part> remainingTypes, Set<Type> result) {
    	if (remainingTypes.isEmpty()) {
    		TupleType tt = new TupleType( selectedSupertypes.toArray(new Part[0]));
    		result.add(tt);
    		return;
    	}
    	
    	Part p = remainingTypes.remove(0);
    	for (Type t1 : p.type().allSupertypes()) {
    		Part p1 = new Part(p.name(), t1);
    		selectedSupertypes.add(0, p1);
    		genAllSuperTypes(selectedSupertypes, remainingTypes, result);
    		selectedSupertypes.remove(0);
    	}
    	remainingTypes.add(0,p);
    }

    /**
     * Returns the tuple part with the given <code>name</code> or <code>null</code> if no
     * such part exists. 
     * @param name
     * @return
     */
    public Part getPart(String name) {
        if (fParts.containsKey(name))
        	return fParts.get(name);
        else
        	return null;
    }
}