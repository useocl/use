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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Characterization (safety-net) tests for {@link StreamReadline} — the
 * fallback {@link Readline} implementation used when no interactive terminal
 * is available.
 *
 * <p>These tests pin down the behaviour of the existing implementation so we
 * can be confident the migration from native GNU readline to JLine does not
 * change the contract every {@link Readline} must satisfy.</p>
 */
class StreamReadlineTest {

    private static StreamReadline from(String input) {
        return new StreamReadline(new BufferedReader(new StringReader(input)), false);
    }

    @Test
    void readline_returnsInputLineWithoutLineTerminator() throws IOException {
        try (var rl = from("hello\n")) {
            assertEquals("hello", rl.readline("prompt> "));
        }
    }

    @Test
    void readline_returnsNullAtEndOfStream() throws IOException {
        try (var rl = from("")) {
            assertNull(rl.readline("prompt> "));
        }
    }

    @Test
    void readline_handlesMultipleLinesThenEof() throws IOException {
        try (var rl = from("first\nsecond\n")) {
            assertEquals("first",  rl.readline("> "));
            assertEquals("second", rl.readline("> "));
            assertNull(rl.readline("> "));
        }
    }

    @Test
    void doEcho_isFalseByDefault() {
        assertFalse(from("").doEcho());
    }

    @Test
    void doEcho_isTrueWhenConstructedWithEcho() {
        var rl = new StreamReadline(new BufferedReader(new StringReader("")), true);
        assertTrue(rl.doEcho());
    }

    @Test
    void usingHistory_isANoOp() {
        assertDoesNotThrow(() -> from("").usingHistory());
    }

    @Test
    void readHistory_isANoOp(@TempDir Path tmp) {
        assertDoesNotThrow(() -> from("").readHistory(tmp.resolve("history").toString()));
    }

    @Test
    void writeHistory_isANoOp(@TempDir Path tmp) {
        assertDoesNotThrow(() -> from("").writeHistory(tmp.resolve("history").toString()));
    }

    @Test
    void close_doesNotThrow() {
        assertDoesNotThrow(() -> from("").close());
    }
}
