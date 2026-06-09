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

import java.io.PrintStream;
import java.util.Objects;

/**
 * A {@link UserOutput} that writes to {@link PrintStream}s. Warnings and errors
 * are written to the error stream, everything else to the normal stream.
 *
 * @author Cansin Yildiz
 * @author Claude
 */
public class StreamUserOutput implements UserOutput {

    private final PrintStream normalStream;
    private final PrintStream errorStream;

    /**
     * @param normalStream stream for {@code NORMAL}, {@code INFO} and {@code TRACE} output
     * @param errorStream  stream for {@code WARN} and {@code ERROR} output
     */
    public StreamUserOutput(PrintStream normalStream, PrintStream errorStream) {
        this.normalStream = Objects.requireNonNull(normalStream);
        this.errorStream = Objects.requireNonNull(errorStream);
    }

    /** Writes all levels to the same stream. */
    public StreamUserOutput(PrintStream stream) {
        this(stream, stream);
    }

    @Override
    public void print(OutputLevel level, String message) {
        streamFor(level).print(message);
    }

    private PrintStream streamFor(OutputLevel level) {
        return (level == OutputLevel.WARN || level == OutputLevel.ERROR)
                ? errorStream : normalStream;
    }
}
