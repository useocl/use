/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package org.tzi.use.uml.ocl.type;

import org.tzi.use.util.BufferedToString;

import java.util.Set;

/**
 * 
 * @author Lars Hamann
 *
 */
public interface Type extends BufferedToString {
	
	public enum VoidHandling {
		INCLUDE_VOID,
		EXCLUDE_VOID
	}
	
	/** 
     * Overwrite this method to return a short printable name of
     * a type (e.g. 'Set(...)') rather than a full type name.
     */
	String shortName();

  /**
   * Overwrite this method to return a qualified name (modelName#typeName) for
   * user-generated types that are part of a model.
   */
  String qualifiedName();

	/**
	 * The query conformsTo() gives true for a type that conforms to another. By
	 * default, two types do not conform to each other. This query is intended
	 * to be redefined for specific conformance situations. UML 2.4.1 p. 138
	 * 
	 * @param other
	 * @return
	 */
	boolean conformsTo(Type other);

	/** 
     * Returns the set of all super types (including this type).
     */
	Set<? extends Type> allSupertypes();

	/**
     * Returns the least common super type of this type and the the given type.
     * @param other
     * @return
     */
	Type getLeastCommonSupertype(Type other);
	
	/**
	 * @return Whether this type is OclVoid or an collection type 
	 * having a void element type (recursively)
	 */
	boolean isVoidOrElementTypeIsVoid();
	
	// The following set of functions is used to 
    // avoids numerous instanceof tests in user code.
    // Corresponding sub types override these methods and return true.
	
	boolean isKindOfNumber(VoidHandling h);

    boolean isTypeOfInteger();
    
    boolean isKindOfInteger(VoidHandling h);
    
    boolean isTypeOfUnlimitedNatural();
    
    boolean isKindOfUnlimitedNatural(VoidHandling h);
    
    boolean isKindOfReal(VoidHandling h);

    boolean isTypeOfReal();
    
    boolean isKindOfString(VoidHandling h);

    boolean isTypeOfString();
    
    boolean isKindOfBoolean(VoidHandling h);

    boolean isTypeOfBoolean();
    
    boolean isKindOfEnum(VoidHandling h);
    
    boolean isTypeOfEnum();
    
    boolean isKindOfCollection(VoidHandling h);
    
    boolean isTypeOfCollection();

    boolean isKindOfSet(VoidHandling h);

    boolean isTypeOfSet();
    
    boolean isKindOfSequence(VoidHandling h);
    
    boolean isTypeOfSequence();

    boolean isKindOfOrderedSet(VoidHandling h);
    
    boolean isTypeOfOrderedSet();
    
    boolean isKindOfBag(VoidHandling h);
    
    boolean isTypeOfBag();

    boolean isKindOfClassifier(VoidHandling h);
    
    boolean isTypeOfClassifier();
    
    boolean isKindOfClass(VoidHandling h);
    
    boolean isTypeOfClass();

    boolean isKindOfDataType(VoidHandling h);

    boolean isTypeOfDataType();

    boolean isKindOfAssociation(VoidHandling h);
    
    boolean isTypeOfAssociation();
    
    boolean isKindOfOclAny(VoidHandling h);
    
    boolean isTypeOfOclAny();

    boolean isKindOfTupleType(VoidHandling h);
    
    boolean isTypeOfTupleType();
    
    boolean isTypeOfVoidType();
    
    boolean isInstantiableCollection();
}
