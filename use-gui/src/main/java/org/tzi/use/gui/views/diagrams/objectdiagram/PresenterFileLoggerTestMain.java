package org.tzi.use.gui.views.diagrams.objectdiagram;

/**
 * Small test runner to validate PresenterFileLogger behaviour at runtime.
 */
public class PresenterFileLoggerTestMain {
    public static void main(String[] args) {
        // write a few entries using different overloads
        PresenterFileLogger.log("Startup test: should appear in default.log");
        PresenterFileLogger.log(PresenterFileLoggerTestMain.class, "main", "class-based entry");

        // switch test name and write more
        PresenterFileLogger.setCurrentTest("t_test_manual");
        PresenterFileLogger.log("afterSwitch", "main", "entry after switching test name");
        PresenterFileLogger.log("t_test_manual", "explicit-message-after-switch");

        // Do not write to standard output for logging — use the file logger instead.
        PresenterFileLogger.log("PresenterFileLoggerTestMain finished — check use_refactor_logs in user home and working dir.");
    }
}

