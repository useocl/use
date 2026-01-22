package org.tzi.use.uml.ocl;

import org.tzi.use.uml.api.ITypeFactory;
import org.tzi.use.uml.api.IType;
import org.tzi.use.uml.api.IMessageType;
import org.tzi.use.uml.api.IMessageReferent;
import org.tzi.use.uml.ocl.type.TypeFactory;

import org.tzi.use.uml.ocl.type.TypeAdapters;
import org.tzi.use.uml.mm.commonbehavior.communications.MSignal;
import org.tzi.use.uml.ocl.type.OclTypeAdapter;

/**
 * Adapter implementing API ITypeFactory using ocl TypeFactory.
 */
public class OclTypeFactoryAdapter implements ITypeFactory {

    static {
        try {
            // register for mm provider so mm-layer can access type adapter without
            // depending on ocl at compile time
            org.tzi.use.uml.mm.TypeAdapterProvider.set(new OclTypeAdapter());
        } catch (Throwable t) {
            // ignore
        }
    }

    @Override
    public IType mkSet(IType elementType) {
        // adapt API-level IType to low-level OCL Type before delegating
        return TypeFactory.mkSet(TypeAdapters.asOclType(elementType));
    }

    @Override
    public IType mkOrderedSet(IType elementType) {
        return TypeFactory.mkOrderedSet(TypeAdapters.asOclType(elementType));
    }

    @Override
    public IType mkSequence(IType elementType) {
        return TypeFactory.mkSequence(TypeAdapters.asOclType(elementType));
    }

    @Override
    public IType mkBag(IType elementType) {
        return TypeFactory.mkBag(TypeAdapters.asOclType(elementType));
    }

    @Override
    public IType mkOclAny() {
        return TypeFactory.mkOclAny();
    }

    @Override
    public IMessageType mkMessageType(IMessageReferent referent) {
        // allowed to cast here because this code lives in ocl/sys
        if (referent instanceof MSignal) {
            return TypeFactory.mkMessageType((MSignal) referent);
        }
        // not supported (e.g., operations) -> return null as fallback
        return null;
    }
}
