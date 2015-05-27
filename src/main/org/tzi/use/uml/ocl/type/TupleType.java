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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.tzi.use.util.BufferedToString;
import org.tzi.use.util.StringUtil;

/**
 * OCL Tuple type.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class TupleType extends TypeImpl {
    private Map<String, Part> fParts = new TreeMap<String, Part>();

    public static class Part implements BufferedToString {
        private final int position;
    	private final String fName;
        private final Type fType;

        public Part(int position, String name, Type type) {
            this.fName = name;
            this.fType = type;
            this.position = position;
        }

        @Override
        public String toString() {
            return toString(new StringBuilder()).toString();
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

        public int getPosition() {
        	return position;
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
    public boolean isTypeOfTupleType() {
    	return true;
    }
    
    @Override
    public boolean isKindOfTupleType(VoidHandling h) {
    	return true;
    }
    
    @Override
    public boolean isKindOfOclAny(VoidHandling h) {
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
    public boolean conformsTo(Type t) {
    	if (t.isTypeOfOclAny())
    		return true;
    	
    	if(!t.isTypeOfTupleType()){
    		return false;
    	}

    	TupleType otherType = (TupleType)t;
    	
    	for(TupleType.Part part : fParts.values()){
    		if (!otherType.fParts.containsKey(part.name()))
    			return false;
    		
    		TupleType.Part otherPart = otherType.fParts.get(part.name());
    		
    		if (!part.type().conformsTo(otherPart.type()))
    			return false;
    	}
    	
    	return true;
    }

    @Override
	public Type getLeastCommonSupertype(Type type) {
    	if (type.isTypeOfVoidType())
    		return this;
    	
    	if(!type.isTypeOfTupleType()){
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
    		commonParts[index] = new Part(index, part.fName, part.fType.getLeastCommonSupertype(otherPart.fType));
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

        List<TupleType.Part> sortedParts = new ArrayList<TupleType.Part>(fParts.values());
        Collections.sort(sortedParts, new Comparator<TupleType.Part>() {
			@Override
			public int compare(Part p1, Part p2) {
				if (p1.position < p2.position) return -1;
			    if (p1.position > p2.position) return  1;
			    return 0;
			}});
        
        StringUtil.fmtSeqBuffered(sb, sortedParts, ",");        
        sb.append(")");
        
        return sb;
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
    	Set<Type> superTypes;
    	//FIXME: Inconsistent handling of OvlVoid. Maybe use parameter (noRecusrionOnVoid) for allSupertypes() 
    	if (p.type().isVoidOrElementTypeIsVoid()) {
    		superTypes = new HashSet<Type>();
    		superTypes.add(p.type());
    	} else {
    		superTypes = new HashSet<>(p.type().allSupertypes());
    	}
    	
    	for (Type t1 : superTypes) {
    		Part p1 = new Part(0, p.name(), t1);
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