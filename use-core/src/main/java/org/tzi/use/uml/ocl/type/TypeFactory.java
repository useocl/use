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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tzi.use.uml.mm.commonbehavior.communications.MSignal;

/**
 * Interface for type creation.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     Type
 */
public final class TypeFactory {

    private static final Map<String, Type> buildInTypesMap = new HashMap<String, Type>();
    
    /**
     * Keep shared instances for frequently used types which only need
     * to be created once.
     */
    private static final IntegerType integerType = new IntegerType();
    private static final UnlimitedNaturalType unlimitedNaturalType = new UnlimitedNaturalType();
    private static final RealType realType = new RealType();
    private static final StringType stringType = new StringType();
    private static final BooleanType booleanType = new BooleanType();
    private static final OclAnyType oclAnyType = new OclAnyType();
    private static final VoidType voidType = new VoidType();
    
    static {
    	buildInTypesMap.put("Integer", integerType);
    	buildInTypesMap.put("UnlimitedNatural", unlimitedNaturalType);
    	buildInTypesMap.put("String", stringType);
    	buildInTypesMap.put("Boolean", booleanType);
    	buildInTypesMap.put("Real", realType);
    	buildInTypesMap.put("OclAny", oclAnyType);
    	buildInTypesMap.put("OclVoid", voidType);
    }
    
    /**
     * No instances.
     */
    private TypeFactory() {
    }

    public static IntegerType mkInteger() {
        return integerType;
    }

    public static UnlimitedNaturalType mkUnlimitedNatural() {
		return unlimitedNaturalType;
	}
    
    public static RealType mkReal() {
        return realType;
    }

    public static StringType mkString() {
        return stringType;
    }

    public static BooleanType mkBoolean() {
        return booleanType;
    }

    public static EnumType mkEnum(String name, List<String> literals) {
        return new EnumType(null, name, literals);
    }

    public static CollectionType mkCollection(Type elemType) {
        return new CollectionType(elemType);
    }

    public static SetType mkSet(Type elemType) {
        return new SetType(elemType);
    }

    public static SequenceType mkSequence(Type elemType) {
        return new SequenceType(elemType);
    }

    public static BagType mkBag(Type elemType) {
        return new BagType(elemType);
    }

    public static OrderedSetType mkOrderedSet(Type elemType) {
    	return new OrderedSetType(elemType);
    }

    public static MessageType mkMessageType(MSignal signal) {
		return signal.getType();
	}
    
    public static OclAnyType mkOclAny() {
        return oclAnyType;
    }

    public static VoidType mkVoidType() {
    	return voidType;
    }
    
    public static TupleType mkTuple(TupleType.Part[] parts) {
        return new TupleType(parts);
    }
    
    /**
     * Returns the type instance of the build in simple type with the name <code>typeName</code>
     * or <code>null</code> if the name does not map to a build in type.
     * @param typeName
     * @return
     */
    public static Type mkSimpleType(String typeName) {
    	if (buildInTypesMap.containsKey(typeName)) {
    		return buildInTypesMap.get(typeName);
    	}
    	else {
    		return null;
    	}
    }
}
