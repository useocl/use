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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Characterization (safety-net) tests for the {@link LineInput} factory.
 *
 * <p>These cover the stable {@code getStreamReadline} factory method whose
 * behaviour must be preserved across the migration to JLine.</p>
 */
class LineInputTest {

    @Test
    void getStreamReadline_returnsReadlineThatReadsTheStream() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("test line\n"));
        try (Readline rl = LineInput.getStreamReadline(reader, false, "")) {
            assertEquals("test line", rl.readline("> "));
        }
    }

    @Test
    void getStreamReadline_withoutEcho_doEchoIsFalse() {
        BufferedReader reader = new BufferedReader(new StringReader(""));
        Readline rl = LineInput.getStreamReadline(reader, false, "");
        assertFalse(rl.doEcho());
    }

    @Test
    void getStreamReadline_withEcho_doEchoIsTrue() {
        BufferedReader reader = new BufferedReader(new StringReader(""));
        Readline rl = LineInput.getStreamReadline(reader, true, "");
        assertTrue(rl.doEcho());
    }

    @Test
    void getUserInputReadline_returnsNonNullNonEchoingReadline() {
        // The interactive factory must always yield a usable, non-echoing
        // Readline. Whether that is a JLineReadline (interactive terminal)
        // or the StreamReadline fallback (headless/piped) depends on the
        // environment, but the behavioural contract is the same.
        Readline rl = LineInput.getUserInputReadline();
        assertNotNull(rl);
        assertFalse(rl.doEcho());
    }
}
