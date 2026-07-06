/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2026 University of Bremen & University of Applied Sciences Hamburg
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package org.tzi.use.util;

/**
 * Abstraction for output that is meant for the USE <em>user</em>, as opposed to
 * developer-facing logging.
 *
 * <p>This is the foundation for unifying USE's output handling (issue #60).
 * Instead of statically writing to {@code System.out}/{@code System.err} or the
 * static {@link Log}, code that produces user output should be given a
 * {@code UserOutput} instance. This separates user output from developer
 * logging, allows multiple output targets (e.g. shell and log window) and
 * allows decorated output (e.g. warnings in a different color).</p>
 *
 * <p>Implementations only have to provide {@link #print(OutputLevel, String)};
 * the {@code println} and per-level convenience methods are derived from it.</p>
 *
 * @author Cansin Yildiz
 * @author Claude
 */
public interface UserOutput {

    /**
     * Outputs {@code message} at the given {@code level}. This is the single
     * method implementations must provide.
     */
    void print(OutputLevel level, String message);

    /** Outputs {@code message} followed by a line separator at {@code level}. */
    default void println(OutputLevel level, String message) {
        print(level, message + System.lineSeparator());
    }

    /** Outputs an empty line at {@code level}. */
    default void println(OutputLevel level) {
        print(level, System.lineSeparator());
    }

    default void printNormal(String message)   { print(OutputLevel.NORMAL, message); }
    default void printlnNormal(String message) { println(OutputLevel.NORMAL, message); }
    default void printlnNormal()               { println(OutputLevel.NORMAL); }

    default void printInfo(String message)     { print(OutputLevel.INFO, message); }
    default void printlnInfo(String message)   { println(OutputLevel.INFO, message); }

    default void printWarn(String message)     { print(OutputLevel.WARN, message); }
    default void printlnWarn(String message)   { println(OutputLevel.WARN, message); }

    default void printError(String message)    { print(OutputLevel.ERROR, message); }
    default void printlnError(String message)  { println(OutputLevel.ERROR, message); }

    default void printTrace(String message)    { print(OutputLevel.TRACE, message); }
    default void printlnTrace(String message)  { println(OutputLevel.TRACE, message); }

    /**
     * A {@code UserOutput} that discards everything. Useful where no user output
     * is desired.
     */
    static UserOutput getSilentOutput() {
        return new NullUserOutput();
    }

    /**
     * The default {@code UserOutput}, writing normal output to {@code System.out}
     * and warnings/errors to {@code System.err}.
     */
    static UserOutput getDefaultOutput() {
        return new StreamUserOutput(System.out, System.err);
    }

    /**
     * A {@code UserOutput} that collects everything in memory. Useful for tests
     * and for capturing output.
     */
    static CollectingUserOutput createCollectingOutput() {
        return new CollectingUserOutput();
    }
}
