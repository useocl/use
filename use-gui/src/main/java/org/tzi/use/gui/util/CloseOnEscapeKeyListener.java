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

import java.awt.KeyEventDispatcher;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/** 
 * A KeyAdapter listening on escape key events. The specified window
 * will be closed and disposed when the escape key was pressed.
 * 
 * @author Mark Richters
 * @author Frank Hilken 
 */
public class CloseOnEscapeKeyListener extends KeyAdapter implements KeyEventDispatcher {
    private Window fComp;

    public CloseOnEscapeKeyListener(Window comp) {
        fComp = comp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        dispatchKeyEvent(e);
    }

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ESCAPE ) {
			fComp.setVisible(false);
			fComp.dispose();
			return true;
		}
		return false;
	}
}
