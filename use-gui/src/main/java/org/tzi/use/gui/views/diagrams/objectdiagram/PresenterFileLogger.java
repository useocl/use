package org.tzi.use.gui.views.diagrams.objectdiagram;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Simple file logger used for collecting presenter/view event traces per test case.
 *
 * <p>
 * <p>
 * Usage: set system property TEST_NAME to separate logs per test, e.g.
 * <p>
 * -DTEST_NAME=t01
 * <p>
 * or set environment variable TEST_NAME before starting the application.
 * <p>
 * Default test name is "default".
 */

public final class PresenterFileLogger {

    // store logs in a user-writable directory under the user's home so they are easy to find

    private static final Path LOG_DIR = Paths.get(System.getProperty("user.home"), "use_refactor_logs");

    // also write a copy to the current working directory to make logs visible in the project tree

    private static final Path WORK_DIR_LOG = Paths.get(System.getProperty("user.dir"), "use_refactor_logs");
    private static final DateTimeFormatter TS_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SSS").withZone(ZoneId.systemDefault());
    private static volatile String currentTest = System.getProperty("TEST_NAME", System.getenv().getOrDefault("TEST_NAME", "default"));
    private static volatile boolean enabled = readEnabledFlag();

    static {

        // Best-effort: create directories and write an initialization line into the test log.

        // Ensure directories exist (ignore failures)
        safeCreateDirs(LOG_DIR);
        safeCreateDirs(WORK_DIR_LOG);

        // write an init line into the current test log file (no stdout/stderr) using structured format
        String initTs = TS_FORMAT.format(Instant.now());
        String initLine = initTs + " [PresenterFileLogger#init] initialized test=" + currentTest + System.lineSeparator();

        Path p = LOG_DIR.resolve(currentTest + ".log");
        safeWriteString(p, initLine);

        Path pWork = WORK_DIR_LOG.resolve(currentTest + ".log");
        safeWriteString(pWork, initLine);

    }

    private PresenterFileLogger() {

        // utility

    }

    // Helper: create directories without nesting try-catch blocks at call sites
    private static void safeCreateDirs(Path dir) {
        try {
            Files.createDirectories(dir);
        } catch (IOException ignored) {
            // best-effort: ignore
        }
    }

    // Helper: write string to file in append mode, ignore failures
    private static void safeWriteString(Path file, String content) {
        try {
            Files.writeString(file, content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ignored) {
            // best-effort: ignore
        }
    }

    private static boolean readEnabledFlag() {

        String value = System.getProperty("PRESENTER_LOGGING_ENABLED");

        if (value == null) {

            value = System.getenv("PRESENTER_LOGGING_ENABLED");

        }

        return Boolean.parseBoolean(value);

    }

    public static boolean isEnabled() {

        return enabled;

    }

    public static void setEnabled(boolean enabled) {

        PresenterFileLogger.enabled = enabled;

    }

    public static synchronized void setCurrentTest(String testName) {

        if (testName == null || testName.isEmpty()) {

            testName = "default";

        }

        currentTest = testName;

        if (!enabled) {
            return;
        }

        // Ensure both directories exist when changing the test name so subsequent writes succeed
        safeCreateDirs(LOG_DIR);
        safeCreateDirs(WORK_DIR_LOG);

        // write an init line into the new current test log file (best-effort, no stdout/stderr)
        String initTs = TS_FORMAT.format(Instant.now());
        String initLine = initTs + " [PresenterFileLogger#setCurrentTest] switched test=" + currentTest + System.lineSeparator();

        Path p = LOG_DIR.resolve(currentTest + ".log");
        safeWriteString(p, initLine);

        Path pWork = WORK_DIR_LOG.resolve(currentTest + ".log");
        safeWriteString(pWork, initLine);

    }

    // Core write helper (best-effort)

    private static void writeLineToFiles(String line) {

        if (!enabled) {

            return;

        }

        String fileName = currentTest + ".log";

        Path p = LOG_DIR.resolve(fileName);

        Path pWork = WORK_DIR_LOG.resolve(fileName);

        // ensure directories exist (in case setCurrentTest wasn't called since class init)
        safeCreateDirs(LOG_DIR);
        safeCreateDirs(WORK_DIR_LOG);

        safeWriteString(p, line);
        safeWriteString(pWork, line);

    }

    /**
     * Structured log entry: className and methodName are required to identify the call site.
     */

    public static synchronized void log(String className, String methodName, String message) {

        if (!enabled) {

            return;

        }

        if (className == null) className = "<unknown>";

        if (methodName == null) methodName = "<unknown>";

        if (message == null) message = "";

        String ts = TS_FORMAT.format(Instant.now());

        String line = ts + " [" + className + "#" + methodName + "] " + message + System.lineSeparator();

        writeLineToFiles(line);

    }

    /**
     * Convenience overload accepting a Class object.
     */

    public static synchronized void log(Class<?> cls, String methodName, String message) {

        if (!enabled) {

            return;

        }

        String className = (cls == null) ? "<null>" : cls.getSimpleName();

        log(className, methodName, message);

    }

    /**
     * Backwards-compatible single-string log; maps to GLOBAL#log for traceability.
     */

    public static synchronized void log(String message) {

        if (!enabled) {

            return;

        }

        log("GLOBAL", "log", message);

    }

    public static synchronized void log(String testName, String message) {

        if (!enabled) {

            return;

        }

        setCurrentTest(testName);

        log(message);

    }

}