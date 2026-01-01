package org.tzi.use.uml.api;

/** Simple provider singleton for an IVarDeclFactory implementation. Sys/ocl should set an implementation at startup. */
public final class VarDeclFactoryProvider {
    private static IVarDeclFactory instance;

    private VarDeclFactoryProvider() { }

    public static void set(IVarDeclFactory factory) {
        instance = factory;
    }

    public static IVarDeclFactory get() {
        if (instance == null) {
            throw new IllegalStateException("IVarDeclFactory not set. Please set via VarDeclFactoryProvider.set(...) in sys initialization.");
        }
        return instance;
    }
}

