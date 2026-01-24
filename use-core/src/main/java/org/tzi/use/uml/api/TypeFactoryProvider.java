package org.tzi.use.uml.api;

import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * Simple provider singleton for an ITypeFactory implementation. Sys/ocl should set an implementation at startup.
 */
public final class TypeFactoryProvider {
    // volatile to make access across threads visible and to satisfy static analyzers
    private static volatile ITypeFactory instance;

    private TypeFactoryProvider() { }

    public static synchronized void set(ITypeFactory factory) {
        instance = factory;
    }

    public static synchronized ITypeFactory get() {
        if (instance == null) {
            // Try to locate an implementation via ServiceLoader (preferred, DIP-friendly)
            try {
                ServiceLoader<ITypeFactory> loader = ServiceLoader.load(ITypeFactory.class);
                for (ITypeFactory f : loader) {
                    instance = f;
                    break;
                }
            } catch (ServiceConfigurationError e) {
                // ServiceLoader can throw ServiceConfigurationError for misconfigured provider files
                if (Boolean.getBoolean("use.debug")) {
                    System.err.println("ServiceLoader configuration error while looking up ITypeFactory: " + e);
                    e.printStackTrace(System.err);
                }
            } catch (Exception e) {
                // Log and try reflection fallback (debug only to avoid noisy output)
                if (Boolean.getBoolean("use.debug")) {
                    System.err.println("ServiceLoader lookup for ITypeFactory failed: " + e);
                    e.printStackTrace(System.err);
                }
            }

            // Fallback: try to instantiate known OCL adapter via reflection
            if (instance == null) {
                try {
                    Class<?> cls = Class.forName("org.tzi.use.uml.ocl.OclTypeFactoryAdapter");
                    Object o = cls.getDeclaredConstructor().newInstance();
                    if (o instanceof ITypeFactory) {
                        instance = (ITypeFactory) o;
                    }
                } catch (ReflectiveOperationException e) {
                    if (Boolean.getBoolean("use.debug")) {
                        System.err.println("Reflection fallback for OclTypeFactoryAdapter failed: " + e);
                        e.printStackTrace(System.err);
                    }
                } catch (LinkageError e) {
                    // LinkageErrors may occur if the optional module is present but incompatible; debug output only
                    if (Boolean.getBoolean("use.debug")) {
                        System.err.println("LinkageError while loading OclTypeFactoryAdapter: " + e);
                        e.printStackTrace(System.err);
                    }
                }
            }

            if (instance == null) {
                throw new IllegalStateException("ITypeFactory not set. Please set via TypeFactoryProvider.set(...) in sys initialization.");
            }
        }
        return instance;
    }
}
