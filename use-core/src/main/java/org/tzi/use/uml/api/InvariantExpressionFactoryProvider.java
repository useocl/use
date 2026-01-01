package org.tzi.use.uml.api;

public final class InvariantExpressionFactoryProvider {
    private static IInvariantExpressionFactory instance;
    private InvariantExpressionFactoryProvider() {}
    public static void set(IInvariantExpressionFactory factory) { instance = factory; }
    public static IInvariantExpressionFactory get() {
        if (instance == null) throw new IllegalStateException("IInvariantExpressionFactory not set. Please set via InvariantExpressionFactoryProvider.set(...) in sys initialization.");
        return instance;
    }
}

