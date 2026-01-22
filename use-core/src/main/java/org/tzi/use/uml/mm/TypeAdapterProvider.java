package org.tzi.use.uml.mm;

/**
 * Provider for an mm-owned ITypeAdapter to avoid cyclical references between api and mm.
 * Implementations (e.g., in the OCL layer) should register an adapter at startup.
 */
public final class TypeAdapterProvider {
    private static ITypeAdapter instance;
    private TypeAdapterProvider() {}

    public static void set(ITypeAdapter adapter) {
        instance = adapter;
    }

    public static ITypeAdapter get() {
        if (instance == null) {
            throw new IllegalStateException("ITypeAdapter not set. Please set via TypeAdapterProvider.set(...) in sys/ocl initialization.");
        }
        return instance;
    }
}
