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

package org.tzi.use.main;

import java.io.*;

import org.tzi.use.util.Log;
import org.tzi.use.util.Queue;

/**
 * An interface for communicating with the daVinci graph visualization
 * tool.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class DaVinciProcess {
    /** 
     * Seconds to wait for successful ack after sending a command.
     */
    private static final int DAVINCI_WAIT_TIMEOUT = 20;

    /**
     * The path to the executable.
     */
    private String fPath;

    /**
     * Process and io streams.
     */
    private Process fProcess;
    private PrintStream fOut;
    private BufferedReader fIn;

    /**
     * This flag is true if daVinci is running and accepting commands.
     */
    private boolean fIsRunning = false;

    /**
     * A queue of incoming messages from daVinci.
     */
    private Queue fReceivedQueue;

    /**
     * The thread reading messages from daVinci.
     */
    private Thread fReceiverThread;

    /**
     * A receiver thread asynchronously reads messages from daVinci
     * and puts them into a synchronized queue for later
     * processing. 
     */
    class Receiver implements Runnable {

        public synchronized void run() {
            Log.trace(this, "Waiting for messages from daVinci...");

            boolean finished = false;
            while (! finished ) {
                try {
                    Thread.yield();
                    String line = fIn.readLine();
                    if (line == null ) {
                        Log.error("Connection terminated by daVinci.");
                        fIsRunning = false;
                        finished = true;
                    }
                    Log.trace(this, "received: " + line);
            
                    // append to event queue, this will wake up the
                    // thread waiting on the queue for processing the
                    // event
                    fReceivedQueue.append(line);
                } catch (InterruptedIOException ex) {
                    finished = true;
                } catch (IOException ex) {
                    Log.error(ex);
                }
            }
        }
    }


    /**
     * Constructs a new process.
     */
    public DaVinciProcess(String path) {
        fPath = path;
        fReceivedQueue = new Queue();
    }

    /**
     * Returns true if daVinci is running and accepting commands.
     */
    public boolean isRunning() {
        return fIsRunning;
    }

    /** 
     * Starts daVinci.
     */
    public void start() throws IOException {
        Log.verbose("Waiting for daVinci...");

        // fork daVinci
        fProcess = Runtime.getRuntime().exec(fPath + " -pipe");
    
        // connect input and output streams
        // output is flushed after each line
        fOut = new PrintStream(fProcess.getOutputStream(), true);
        fIn = new BufferedReader(new InputStreamReader(fProcess.getInputStream()));

        fReceiverThread = new Thread(new Receiver());
        fReceiverThread.start();

        if (! waitForOk() ) {
            // timeout
            Log.error("Can't connect to daVinci");
            fProcess.destroy();
        } else
            fIsRunning = true;
    }

    /** 
     * Sends a command to daVinci.
     */
    public void send(String cmd) throws IOException {
        fOut.println(cmd);
        waitForOk();
    }

    /** 
     * Terminates daVinci and closes connection.
     */
    public void close() {
        if (fIsRunning ) {
            if (fReceiverThread != null ) {
                fReceiverThread.interrupt();
            }
            fProcess.destroy();
        }
    }


    private synchronized boolean waitForOk() throws IOException {
        boolean ok = false;
        String line;

        while (! ok ) {
            Log.trace(this, "Waiting for a response from daVinci.");
            line = null;
            try { 
                line = (String) fReceivedQueue.get(DAVINCI_WAIT_TIMEOUT * 1000);
            } catch (InterruptedException ex) {
                Log.error(ex);
            }

            if (line == null )
                throw new IOException("Timeout, no response from daVinci.");

            ok = line.equals("ok");
        }
        return ok;
    }

}
