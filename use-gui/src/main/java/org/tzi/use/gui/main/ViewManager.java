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

package org.tzi.use.gui.main;

import javax.swing.DefaultDesktopManager;
import javax.swing.JInternalFrame;

/** 
 * A DesktopManager that knows about ViewFrames.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
@SuppressWarnings("serial")
public class ViewManager extends DefaultDesktopManager {

    /**
     * Closes the frame and notifies the view about it.
     */
    public void closeFrame(JInternalFrame f) {
        super.closeFrame(f);
        ((ViewFrame) f).close();
    }
}
