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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;

/** 
 * A Mouse listener for context menus.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class PopupListener extends MouseAdapter {
    private JPopupMenu fPopupMenu;

    public PopupListener(JPopupMenu m) {
        fPopupMenu = m;
    }

    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }
    
    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }
    
    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger() ) {
            fPopupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
