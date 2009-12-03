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

// $Id$

package org.tzi.use.util.input;

import java.io.*;
import java.net.Socket;

/**
 * A Readline implementation using a socket as source.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class SocketReadline implements Readline {
    private Socket fSocket;
    private String fStaticPrompt;
    private boolean fEchoInput;
    private BufferedReader fIn;
    private BufferedWriter fOut;

    /**
     * Constructs a new SocketReadline object. If echoInput is true,
     * each line read will be echoed back to the socket. staticPrompt
     * may be used to overwrite the prompt passed to the readline
     * method.
     */
    public SocketReadline(Socket socket,
                          boolean echoInput,
                          String staticPrompt) {
        fSocket = socket;
        fStaticPrompt = staticPrompt;
        fEchoInput = echoInput;
    }

    /**
     * Reads a line of text from the underlying socket.
     *
     * @return     A String containing the contents of the line, not including
     *             any line-termination characters, or null if the end of the
     *             stream has been reached.
     *
     * @exception  IOException  If an I/O error occurs.
     */
    public String readline(String prompt) throws IOException {
        if (fIn == null )
            fIn = new BufferedReader(new InputStreamReader(fSocket.getInputStream()));
        if (fOut == null)
            fOut = new BufferedWriter(new OutputStreamWriter(fSocket.getOutputStream()));

        if (fStaticPrompt != null )
            fOut.write(fStaticPrompt);
        else
            fOut.write(prompt);
        String line = fIn.readLine();
        if (fEchoInput )
            if (line != null )
                System.out.println(line);
            else 
                System.out.println();
        return line;
    }   

    /**
     * Closes the Readline stream.
     */
    public void close() throws IOException {
        if (fOut != null )
            fOut.close();
        if (fIn != null )
            fIn.close();
        fSocket.close();
    }

    // The following operations are just noops.

    public void usingHistory() {}

    public void readHistory(String filename) {}

    public void writeHistory(String filename) {}
 
    public boolean doEcho() {
        return true;
    }
}
