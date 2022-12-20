/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

package org.tzi.use.util.soil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.tzi.use.uml.ocl.type.Type;


/**
 * A {@code VariableSet} is a set of {@link Type typed} variables and
 * provides operations to construct the {@link #add(VariableSet) union}, 
 * {@link #remove(VariableSet) difference} and two polymorphic differences
 * ( {@link #removePolymorphic1(VariableSet) 1}, 
 * {@link #removePolymorphic2(VariableSet) 2} ) between {@code VariableSet}s.
 * 
 * @author Daniel Gent
 */
public class VariableSet {
	/** maps variable names to the set of their types */
	private Map<String, Set<Type>> fEntries = 
		new HashMap<String, Set<Type>>();
	
	
	/**
	 * constructs an empty {@code VariableSet}
	 */
	public VariableSet() {
		
	}
	
	
	/**
	 * copy constructor
	 * 
	 * @param other {@code VariableSet} whose entries are copied
	 */
	public VariableSet(VariableSet other) {
		add(other);
	}
	
	
	/**
	 * returns the names of all variables in this
	 * 
	 * @return the names of all variables in this
	 */
	public Set<String> getNames() {
		return fEntries.keySet();
	}
	
	
	/**
	 * returns the names of variables, which exists in this and the supplied
	 * {@code VariableSet}
	 * 
	 * @param other another {@code VariableSet}
	 * @return the set of all common variable names (empty set if there are none)
	 */
	public Set<String> getCommonNames(VariableSet other) {
		Set<String> result = new HashSet<String>(fEntries.keySet());
		result.retainAll(other.fEntries.keySet());
		
		return result;
	}
	
	
	/**
	 * returns all types of the variable specified by the supplied name
	 * 
	 * @param name the name of the variable whose types are requested
	 * @return a set containing the types of the variable with that name, or 
	 * {@code null} if there is no variable with that name
	 */
	public Set<Type> getTypes(String name) {
		return fEntries.get(name);		
	}
	
	
	/**
	 * returns the (first) type of the variable specified by the supplied
	 * name.
	 * 
	 * @param name the name of the variable whose (first) type is requested
	 * @return the (first) type of the variable with that name, or {@code null}
	 *         if there is no variable with that name
	 */
	public Type getType(String name) {
		if (contains(name)) {
			return fEntries.get(name).iterator().next();
		} else {
			return null;
		}
	}
	
	
	/**
	 * returns true if this contains a variable with the specified name
	 * 
	 * @param name the name of the variable whose presence is to be tested
	 * @return true if this contains a variable with the specified name
	 */
	public boolean contains(String name) {
		return fEntries.containsKey(name);
	}
	
	
	/**
	 * returns true if this contains a variable with the specified name and type
	 * 
	 * @param name name of the variable whose presence is to be tested
	 * @param type type of the variable whose presence is to be tested
	 * @return true if this contains a variable with the specified name and type
	 */
	public boolean contains(String name, Type type) {
		return fEntries.containsKey(name) && fEntries.get(name).contains(type);
	}
	
	
	/**
	 * returns true if this contains everything which exists in the other 
	 * {@code VariableSet}
	 * 
	 * @param other another {@code VariableSet}
	 * @return true if this contains everything which exists in the other 
	 *         {@code VariableSet}
	 */
	public boolean containsAll(VariableSet other) {
		for (String name : other.getNames()) {
			for (Type type : other.getTypes(name)) {
				if (!contains(name, type)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	/**
	 * returns true if this contains no variables
	 * 
	 * @return true if this contains no variables
	 */
	public boolean isEmpty() {
		return fEntries.isEmpty();
	}


	/**
	 * adds a new typed variable to this
	 * 
	 * @param name the variable's name
	 * @param type the variable's type
	 */
	public void add(String name, Type type) {
		if (fEntries.containsKey(name)) {
			fEntries.get(name).add(type);
		} else {
			Set<Type> newEntry = new LinkedHashSet<Type>();
			newEntry.add(type);
			
			fEntries.put(name, newEntry);
		}
	}
	
	
	/**
	 * adds the contents of another {@code VariableSet} to this
	 * <p>
	 * this method is essentially a set union operation
	 * 
	 * @see #union(VariableSet, VariableSet)
	 * @param other the {@code VariableSet} whose contents are added to this
	 */
	public void add(VariableSet other) {
		for (Entry<String, Set<Type>> entry : other.fEntries.entrySet()) {
			String name = entry.getKey();
			Set<Type> types = entry.getValue();
			
			if (fEntries.containsKey(name)) {
				fEntries.get(name).addAll(types);
			} else {
				fEntries.put(name, new LinkedHashSet<Type>(types));
			}
		}
	}
	
	
	/**
	 * removes each element, which exists in both this and the other 
	 * {@code VariableSet} from this
	 * <p>
	 * this method is essentially a set difference operation
	 * 
	 * @see #difference(VariableSet, VariableSet)
	 * @param other the {@code VariableSet} whose contents are removed from this
	 */
	public void remove(VariableSet other) {
		if (fEntries.isEmpty() || other.fEntries.isEmpty()) {
			return;
		}
		
		for (String name : getCommonNames(other)) {
			Set<Type> theseTypes = fEntries.get(name);
			Set<Type> thoseTypes = other.fEntries.get(name);
			
			theseTypes.removeAll(thoseTypes);
			if (theseTypes.isEmpty()) {
				fEntries.remove(name);
			}
		}
	}
	
	
	/**
	 * removes all variables in this, which also exist in the other
	 * {@code VariableSet}, disregarding types
	 * 
	 * @param other the {@code VariableSet} whose contents are removed from this
	 */
	public void removePolymorphic1(VariableSet other) {
		if (fEntries.isEmpty() || other.fEntries.isEmpty()) {
			return;
		}
		
		for (String name : getCommonNames(other)) {
			fEntries.remove(name);
		}
	}
	
	
	/**
	 * removes all variables in this, which exist with a more specific type in 
	 * the other {@code VariableSet}
	 * 
	 * @param other the {@code VariableSet} whose contents are removed from this
	 */
	public void removePolymorphic2(VariableSet other) {
		if (fEntries.isEmpty() || other.fEntries.isEmpty()) {
			return;
		}
	
		Set<String> commonNames = getCommonNames(other);
		
		if (commonNames.isEmpty()) {
			return;
		}
			
		List<Type> toDelete = new ArrayList<Type>();
		for (String name : commonNames) {
			Set<Type> theseTypes = fEntries.get(name);
			Set<Type> thoseTypes = other.fEntries.get(name);
			
			for (Type thisType : theseTypes) {
				for (Type thatType : thoseTypes) {
					if (thatType.conformsTo(thisType)) {
						toDelete.add(thisType);
						break;
					}
				}
			}
			
			theseTypes.removeAll(toDelete);
			if (theseTypes.isEmpty()) {
				fEntries.remove(name);
			}
			
			toDelete.clear();
		}
	}
	
	
	/**
	 * constructs {@code A U B} (union of sets)
	 * 
	 * @param vA a {@code VariableSet}
	 * @param vB another {@code VariableSet}
	 * @return a new {@code VariableSet}
	 */
	public static VariableSet union(VariableSet vA, VariableSet vB) {
		VariableSet result = new VariableSet(vA);
		result.add(vB);
		
		return result;
	}
	
	
	/**
	 * constructs {@code A \ B} (difference of sets)
	 * 
	 * @param vA a {@code VariableSet}
	 * @param vB another {@code VariableSet}
	 * @return a new {@code VariableSet}
	 */
	public static VariableSet difference(VariableSet vA, VariableSet vB) {
		VariableSet result = new VariableSet(vA);
		result.remove(vB);
		
		return result;
	}
	
	
	/**
	 * constructs the first 
	 * {@link #removePolymorphic1(VariableSet) polymorphic difference} between
	 * vA and vB
	 * 
	 * @param vA a {@code VariableSet}
	 * @param vB another {@code VariableSet}
	 * @return a new {@code VariableSet}
	 */
	public static VariableSet polymorphicDifference1(
			VariableSet vA, 
			VariableSet vB) {
		
		VariableSet result = new VariableSet(vA);
		result.removePolymorphic1(vB);
		
		return result;
	}
	
	
	/**
	 * constructs the second 
	 * {@link #removePolymorphic2(VariableSet) polymorphic difference} between
	 * vA and vB
	 * 
	 * @param vA a {@code VariableSet}
	 * @param vB another {@code VariableSet}
	 * @return a new {@code VariableSet}
	 */
	public static VariableSet polymorphicDifference2(
			VariableSet vA, 
			VariableSet vB) {
		
		VariableSet result = new VariableSet(vA);
		result.removePolymorphic2(vB);
		
		return result;
	}
	
	
	@Override
	public String toString() {
		if (fEntries.isEmpty()) {
			return "{ }";
		}
		
		final String LBRAC = "{";
		final String RBRAC = "}";
		final String COLON = ":";
		final String COMMA = ",";
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(LBRAC);
		for (String name : fEntries.keySet()) {
			for (Type type : fEntries.get(name)) {
				sb.append(name);
				sb.append(COLON);
				sb.append(type);
				sb.append(COMMA);
			}
		}
		
		sb.delete(
				(sb.length() - COMMA.length()), 
				sb.length()); 
		
		sb.append(RBRAC);
		
		return sb.toString();
	}
}
