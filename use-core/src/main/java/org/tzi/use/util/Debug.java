package org.tzi.use.util;

/**
 * Small debug helper to enable conditional debug output without polluting
 * the normal test/golden outputs. Activate by setting system property
 * -Duse.debug=true or environment variable USE_DEBUG=true.
 */
public final class Debug {
    private Debug() {}

    public static boolean enabled() {
        try {
            String p = System.getProperty("use.debug");
            if (p != null) return "true".equalsIgnoreCase(p);
            String e = System.getenv("USE_DEBUG");
            return "true".equalsIgnoreCase(e);
        } catch (Throwable t) {
            return false;
        }
    }

    public static void log(String s) {
        if (enabled()) {
            try {
                // Try to write to USE protocol (preferred for integration tests)
                org.tzi.use.util.USEWriter.getInstance().protocol("[USE-DEBUG] " + s);
            } catch (Throwable ignore) {
                // fallback to stderr
                System.err.println("[USE-DEBUG] " + s);
            }
        }
    }
}
