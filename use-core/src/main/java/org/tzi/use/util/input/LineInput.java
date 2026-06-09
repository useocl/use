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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Factory for obtaining a suitable {@link Readline} implementation.
 *
 * <p>For interactive use the pure-Java {@link JLineReadline} is returned,
 * providing line editing and command history on all platforms. When no
 * interactive terminal is available (e.g. the input is piped or USE runs in
 * a headless environment) a plain {@link StreamReadline} reading from
 * {@code System.in} is used instead.</p>
 *
 * @author      Mark Richters
 */
public class LineInput {

    // utility class
    private LineInput() {}

    /**
     * Returns a {@link Readline} implementation for reading interactive user
     * input. A JLine-backed implementation is used when a terminal is
     * available; otherwise a simple stream-based implementation reading from
     * {@code System.in} is returned.
     */
    public static Readline getUserInputReadline() {
        if (System.console() != null) {
            try {
                return new JLineReadline();
            } catch (IOException ex) {
                // JLine could not attach to the terminal; fall back below.
            }
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // no echo, do protocol
        return new StreamReadline(reader, false);
    }

    public static Readline getStreamReadline(BufferedReader reader, boolean doEcho, String prompt) {
        return new StreamReadline(reader, doEcho, prompt);
    }
}
