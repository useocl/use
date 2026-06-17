/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
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

package org.tzi.use.util.input;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the new JLine-backed {@link JLineReadline}.
 *
 * <p>A package-private constructor accepting explicit streams is used so the
 * tests never need to attach to a real terminal.</p>
 */
class JLineReadlineTest {

    private static JLineReadline readingFrom(String input) throws IOException {
        return new JLineReadline(new ByteArrayInputStream(input.getBytes()), new ByteArrayOutputStream());
    }

    @Test
    void readline_returnsInputLine() throws IOException {
        try (var rl = readingFrom("hello\n")) {
            assertEquals("hello", rl.readline("prompt> "));
        }
    }

    @Test
    void readline_returnsNullAtEndOfStream() throws IOException {
        try (var rl = readingFrom("")) {
            assertNull(rl.readline("prompt> "));
        }
    }

    @Test
    void doEcho_returnsFalse() throws IOException {
        try (var rl = readingFrom("")) {
            assertFalse(rl.doEcho());
        }
    }

    @Test
    void usingHistory_doesNotThrow() throws IOException {
        try (var rl = readingFrom("")) {
            assertDoesNotThrow(rl::usingHistory);
        }
    }

    @Test
    void writeHistory_persistsEnteredCommandsToFile(@TempDir Path tmp) throws IOException {
        Path histFile = tmp.resolve(".use_history_test");

        try (var rl = new JLineReadline(
                new ByteArrayInputStream("cmd1\ncmd2\n".getBytes()), new ByteArrayOutputStream())) {
            rl.usingHistory();
            rl.readHistory(histFile.toString());
            rl.readline("> ");
            rl.readline("> ");
            rl.writeHistory(histFile.toString());
        }

        assertTrue(histFile.toFile().exists(), "history file should be created");
        assertTrue(histFile.toFile().length() > 0, "history file should not be empty");
    }

    @Test
    void close_doesNotThrow() throws IOException {
        var rl = readingFrom("");
        assertDoesNotThrow(rl::close);
    }
}
