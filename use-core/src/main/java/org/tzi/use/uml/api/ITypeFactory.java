package org.tzi.use.uml.api;

/**
 * API-level factory abstraction for creating IType instances and collection types.
 * Implemented by ocl.TypeFactory via an adapter (placed in sys or ocl).
 */
public interface ITypeFactory {
    IType mkSet(IType elementType);
    IType mkOrderedSet(IType elementType);
    IType mkSequence(IType elementType);
    IType mkBag(IType elementType);
    IType mkOclAny();
    IMessageType mkMessageType(IMessageReferent referent);
}

