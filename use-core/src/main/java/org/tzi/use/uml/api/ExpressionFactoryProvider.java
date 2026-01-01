package org.tzi.use.uml.api;

/** Simple provider for IExpressionFactory implementations set by sys/ocl at init time. */
public final class ExpressionFactoryProvider {
    private static IExpressionFactory instance;
    private ExpressionFactoryProvider() {}
    public static void set(IExpressionFactory factory) { instance = factory; }
    public static IExpressionFactory get() {
        if (instance == null) throw new IllegalStateException("IExpressionFactory not set. Please set via ExpressionFactoryProvider.set(...) in sys initialization.");
        return instance;
    }
}

