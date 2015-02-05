/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

package org.tzi.use.gui.views.diagrams.behavior.communicationdiagram;

/**
 * This thread makes the activated message on communication diagram blinking
 * @author Quang Dung Nguyen
 *
 */
public class BlinkingThread implements Runnable {
	private CommunicationDiagram communicationDiagram;
    private Thread thread;
    private long onTime = 2000;
    private long offTime = 700;
 
    public BlinkingThread(CommunicationDiagram comDia) {
        communicationDiagram = comDia;
    }
 
    public void run() {
    	Thread thisThread = Thread.currentThread();
        while(thread == thisThread) {
            communicationDiagram.showOrHideActivatedMessage(true);
            try {
                Thread.sleep(onTime);
            } catch(InterruptedException e) {
                stop();
            }
            communicationDiagram.showOrHideActivatedMessage(false);
            try {
                Thread.sleep(offTime);
            } catch(InterruptedException e) {
                stop();
            }
        }
    }
 
    /**
     * Start this thread
     */
	void start() {
		thread = new Thread(this);
		thread.start();
	}
 
	/**
	 * Stop this thread safety
	 */
    void stop() {
        thread = null;
    }
}
