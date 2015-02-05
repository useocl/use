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

// $Id$

package org.tzi.use.gui.views.diagrams.behavior.sequencediagram;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.KeyStroke;

/**
 * A special kind of JButton which is activated by "Escape".
 * 
 * @author Antje Werner
 */

public class CancelButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CancelButton(String title) {
		super(title);
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String cmd = event.getActionCommand();
				if (cmd.equals("Cancel")) {
					doClick();
				}
			}
		};
		registerKeyboardAction(al, "Cancel", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JButton.WHEN_IN_FOCUSED_WINDOW);
	}
}