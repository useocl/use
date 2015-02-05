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

package org.tzi.use.gui.util;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


/** 
 * A StatusBar consisting of a single line of text.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
@SuppressWarnings("serial")
public class StatusBar extends JPanel {
    
	private final JLabel fMsgLabel;
    
	private final JLabel fMsgLabelEast;
	
    private final Timer fTimer;

    public StatusBar() {
        super(new BorderLayout());
        fMsgLabel = new JLabel("Ready.");
        fMsgLabelEast = new JLabel("");
        
        add(fMsgLabel, BorderLayout.WEST);
        add(fMsgLabelEast, BorderLayout.EAST);
        
        // create timer for automatic clearing of messages after a
        // specified delay.
        fTimer = new Timer(8000, new TimerListener());
    }

    /**
     * Display a temporary message. The message will be deleted after a delay.
     */
    public void showTmpMessage(String msg) {
        // prevent status bar from disappearing completely
        if (msg == null || msg.length() == 0 )
            msg = " ";
        fMsgLabel.setText(msg);

        // restart a timer
        if (fTimer.isRunning() )
            fTimer.stop();
        fTimer.start();
    }

    /**
     * Displays a message in the status bar.
     * @param msg The message to show
     * @param position Either {@link BorderLayout#WEST} or {@link BorderLayout#EAST}. 
     */
    public void showMessage(String msg, String position) {
    	// prevent status bar from disappearing completely
        if (msg == null || msg.length() == 0 )
            msg = " ";
        
        if (position.equals(BorderLayout.WEST))
        	fMsgLabel.setText(msg);
        else if (position.equals(BorderLayout.EAST))
        	fMsgLabelEast.setText(msg);
        else 
        	throw new IllegalArgumentException("Only WEST or EAST are allowed in status bar.");
        
        // stop timer if running
        if (position.equals(BorderLayout.WEST) && fTimer.isRunning() )
            fTimer.stop();
    }
    
    /**
     * Display a message. The message will be deleted only be a subsequent
     * call to showTmpMessage or showMessage.
     */
    public void showMessage(String msg) {
        showMessage(msg, BorderLayout.WEST);
    }

    /**
     * Clear status bar and display a default message.
     */
    public void clearMessage() {
        showMessage("Ready.");
        showMessage("", BorderLayout.EAST);
    }

    class TimerListener implements ActionListener {
        // The actionPerformed method is called each time the Timer
        // fires
        @Override
		public void actionPerformed(ActionEvent evt) {
            fTimer.stop();
            fMsgLabel.setText("Ready.");
        }
    }
}

