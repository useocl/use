package org.tzi.use.uml.api;

import java.util.ServiceLoader;

/**
 * Simple provider singleton for an ITypeFactory implementation. Sys/ocl should set an implementation at startup.
 */
public final class TypeFactoryProvider {
    private static ITypeFactory instance;

    private TypeFactoryProvider() { }

    public static void set(ITypeFactory factory) {
        instance = factory;
    }

    public static ITypeFactory get() {
        if (instance == null) {
            synchronized (TypeFactoryProvider.class) {
                if (instance == null) {
                    // Try to locate an implementation via ServiceLoader (preferred, DIP-friendly)
                    try {
                        ServiceLoader<ITypeFactory> loader = ServiceLoader.load(ITypeFactory.class);
                        for (ITypeFactory f : loader) {
                            instance = f;
                            break;
                        }
                    } catch (Throwable ignored) {
                        // ignore and try reflection fallback
                    }

                    // Fallback: try to instantiate known OCL adapter via reflection
                    if (instance == null) {
                        try {
                            Class<?> cls = Class.forName("org.tzi.use.uml.ocl.OclTypeFactoryAdapter");
                            Object o = cls.getDeclaredConstructor().newInstance();
                            if (o instanceof ITypeFactory) {
                                instance = (ITypeFactory) o;
                            }
                        } catch (Throwable ignored) {
                            // leave instance null -> will throw below
                        }
                    }

                    if (instance == null) {
                        throw new IllegalStateException("ITypeFactory not set. Please set via TypeFactoryProvider.set(...) in sys initialization.");
                    }
                }
            }
        }
        return instance;
    }
}
