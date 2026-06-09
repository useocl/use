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

import jline.console.ConsoleReader;
import jline.console.history.FileHistory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A {@link Readline} implementation backed by the pure-Java
 * <a href="https://github.com/jline/jline2">JLine</a> library. It provides
 * line editing and a persistent command history on all platforms (Linux,
 * macOS and Windows) without requiring a platform-dependent native library.
 *
 * <p>This replaces the former JNI binding to the native GNU readline library
 * and removes the corresponding native build steps.</p>
 *
 * @author Mark Richters
 */
public class JLineReadline implements Readline {

    private final ConsoleReader reader;
    private FileHistory history;

    public JLineReadline() throws IOException {
        this(null, null);
    }

    /**
     * Package-private constructor used by tests to supply explicit streams
     * instead of attaching to the real terminal.
     */
    JLineReadline(InputStream in, OutputStream out) throws IOException {
        reader = (in != null && out != null)
                ? new ConsoleReader(in, out)
                : new ConsoleReader();
        // USE uses '!' and '!!' to start SOIL statements. Disable JLine's
        // history expansion so these characters are passed through verbatim.
        reader.setExpandEvents(false);
    }

    @Override
    public String readline(String prompt) throws IOException {
        return reader.readLine(prompt);
    }

    @Override
    public void usingHistory() {
        reader.setHistoryEnabled(true);
    }

    @Override
    public void readHistory(String filename) throws IOException {
        history = new FileHistory(new File(filename));
        reader.setHistory(history);
    }

    @Override
    public void writeHistory(String filename) throws IOException {
        if (history != null) {
            history.flush();
        }
    }

    @Override
    public void close() throws IOException {
        reader.shutdown();
    }

    @Override
    public boolean doEcho() {
        return false;
    }
}
