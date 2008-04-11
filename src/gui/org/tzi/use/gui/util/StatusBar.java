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

package org.tzi.use.gui.util;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


/** 
 * A StatusBar consisting of a single line of text.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class StatusBar extends JPanel {
    private JLabel fMsgLabel;
    private Timer fTimer;

    public StatusBar() {
        super(new BorderLayout());
        fMsgLabel = new JLabel("Ready.");
        add(fMsgLabel, BorderLayout.WEST);
    
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
     * Display a message. The message will be deleted only be a subsequent
     * call to showTmpMessage or showMessage.
     */
    public void showMessage(String msg) {
        // prevent status bar from disappearing completely
        if (msg == null || msg.length() == 0 )
            msg = " ";
        fMsgLabel.setText(msg);

        // stop timer if running
        if (fTimer.isRunning() )
            fTimer.stop();
    }

    /**
     * Clear status bar and display a default message.
     */
    public void clearMessage() {
        showMessage("Ready.");
    }

    class TimerListener implements ActionListener {
        // The actionPerformed method is called each time the Timer
        // fires
        public void actionPerformed(ActionEvent evt) {
            fTimer.stop();
            fMsgLabel.setText("Ready.");
        }
    }
}

