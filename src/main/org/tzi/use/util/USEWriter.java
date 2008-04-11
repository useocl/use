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

$Id$

package org.tzi.use.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Replaces System.out and System.err by decorators that record all 
 * output to a protocol buffer. Access to the original out stream is
 * available for bypass writes (such as a command prompt).
 */
public class USEWriter {
    private static USEWriter INSTANCE;
    
    public static synchronized USEWriter getInstance() {
        if (INSTANCE==null)
            INSTANCE = new USEWriter();
        return INSTANCE;
    }
    
    private PrintStream out;
    private PrintStream noProtocolOut;
    private PrintStream err;
    private StringWriter log;
    private PrintWriter logWriter;
    
    private USEWriter() {
        noProtocolOut = System.out;
        out = new PrintStream(new LoggingOutputStreamDecorator(System.out));
        err = new PrintStream(new LoggingOutputStreamDecorator(System.err));
        log = new StringWriter();
        logWriter = new PrintWriter(log);
    }

    public PrintStream getOut() {
        return out;
    }
    
    public PrintStream getErr() {
        return err;
    }

    public PrintStream getNoProtocolOut() {
        return noProtocolOut;
    }

    public void protocol(String line) {
        logWriter.println(line);
        logWriter.flush();
    }
    
    public void writeProtocolFile(Writer writer) throws IOException  {
        log.flush();
        writer.write(log.getBuffer().toString());
    }

    
    public class LoggingOutputStreamDecorator extends OutputStream {
        
        private OutputStream os;

        public LoggingOutputStreamDecorator(OutputStream o) {
            os = o;
        }

        /* (non-Javadoc)
         * @see java.io.OutputStream#write(int)
         */
        public void write(int b) throws IOException {
            os.write(b);
            os.flush();
            log.write(b);
        }
    }
    
    
}
