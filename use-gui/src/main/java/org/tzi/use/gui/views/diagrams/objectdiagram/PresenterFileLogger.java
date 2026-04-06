package org.tzi.use.gui.views.diagrams.objectdiagram;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.nio.file.StandardOpenOption;

/**
 * Simple file logger used for collecting presenter/view event traces per test case.
 * <p>
 * Usage: set system property TEST_NAME to separate logs per test, e.g.
 * -DTEST_NAME=t01
 * or set environment variable TEST_NAME before starting the application.
 * Default test name is "default".
 */
public final class PresenterFileLogger {
    // store logs in a user-writable directory under the user's home so they are easy to find
    private static final Path LOG_DIR = Paths.get(System.getProperty("user.home"), "use_refactor_logs");
    // also write a copy to the current working directory to make logs visible in the project tree
    private static final Path WORK_DIR_LOG = Paths.get(System.getProperty("user.dir"), "use_refactor_logs");

    private static volatile String currentTest = System.getProperty("TEST_NAME", System.getenv().getOrDefault("TEST_NAME", "default"));
    private static final DateTimeFormatter TS_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SSS").withZone(ZoneId.systemDefault());

    static {
        // Best-effort: create directories and write an initialization line into the test log.
        try {
            Files.createDirectories(LOG_DIR);
            Files.createDirectories(WORK_DIR_LOG);
            // write an init line into the current test log file (no stdout/stderr) using structured format
            String initTs = TS_FORMAT.format(Instant.now());
            String initLine = initTs + " [PresenterFileLogger#init] initialized test=" + currentTest + System.lineSeparator();
            try {
                Path p = LOG_DIR.resolve(currentTest + ".log");
                Files.writeString(p, initLine, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException ignored) {
                // ignore — best-effort
            }
            try {
                Path pWork = WORK_DIR_LOG.resolve(currentTest + ".log");
                Files.writeString(pWork, initLine, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException ignored) {
                // ignore
            }
        } catch (IOException ignored) {
            // ignore directory creation failures — logger is best-effort and must not print to stdout/stderr
        }
    }

    private PresenterFileLogger() {
        // utility
    }

    public static synchronized void setCurrentTest(String testName) {
        if (testName == null || testName.isEmpty()) {
            testName = "default";
        }
        currentTest = testName;
        try {
            // Ensure both directories exist when changing the test name so subsequent writes succeed
            Files.createDirectories(LOG_DIR);
            Files.createDirectories(WORK_DIR_LOG);
            // write an init line into the new current test log file (best-effort, no stdout/stderr)
            String initTs = TS_FORMAT.format(Instant.now());
            String initLine = initTs + " [PresenterFileLogger#setCurrentTest] switched test=" + currentTest + System.lineSeparator();
            try {
                Path p = LOG_DIR.resolve(currentTest + ".log");
                Files.writeString(p, initLine, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException ignored) {
                // ignore — best-effort
            }
            try {
                Path pWork = WORK_DIR_LOG.resolve(currentTest + ".log");
                Files.writeString(pWork, initLine, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException ignored) {
                // ignore
            }
        } catch (IOException ignored) {
            // ignore directory creation failures — logger is best-effort and must not print to stdout/stderr
        }
    }

    // Core write helper (best-effort)
    private static void writeLineToFiles(String line) {
        String fileName = currentTest + ".log";
        Path p = LOG_DIR.resolve(fileName);
        Path pWork = WORK_DIR_LOG.resolve(fileName);
        try {
            // ensure directories exist (in case setCurrentTest wasn't called since class init)
            Files.createDirectories(LOG_DIR);
        } catch (IOException ignored) {
            // ignore
        }
        try {
            Files.createDirectories(WORK_DIR_LOG);
        } catch (IOException ignored) {
            // ignore
        }
        try {
            Files.writeString(p, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ignored) {
            // ignore
        }
        try {
            Files.writeString(pWork, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ignored) {
            // ignore
        }
    }

    /**
     * Structured log entry: className and methodName are required to identify the call site.
     */
    public static synchronized void log(String className, String methodName, String message) {
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
        String className = (cls == null) ? "<null>" : cls.getSimpleName();
        log(className, methodName, message);
    }

    /**
     * Backwards-compatible single-string log; maps to GLOBAL#log for traceability.
     */
    public static synchronized void log(String message) {
        log("GLOBAL", "log", message);
    }

    public static synchronized void log(String testName, String message) {
        setCurrentTest(testName);
        log(message);
    }
}
