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

import java.util.List;

import org.tzi.use.uml.mm.MClass;

/**
 * Interface for type creation.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     Type
 */
public final class TypeFactory {

    /**
     * Keep shared instances for frequently used types which only need
     * to be created once.
     */
    private static IntegerType integerType = new IntegerType();
    private static RealType realType = new RealType();
    private static StringType stringType = new StringType();
    private static BooleanType booleanType = new BooleanType();
    private static OclAnyType oclAnyType = new OclAnyType();
    private static VoidType voidType = new VoidType();
    
    /**
     * No instances.
     */
    private TypeFactory() {
    }

    public static IntegerType mkInteger() {
        return integerType;
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

    public static EnumType mkEnum(String name, List literals) {
        return new EnumType(name, literals);
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

    public static ObjectType mkObjectType(MClass cls) {
        return new ObjectType(cls);
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
}
