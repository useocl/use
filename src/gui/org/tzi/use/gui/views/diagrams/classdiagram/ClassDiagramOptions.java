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

// $Id: ClassDiagramOptions.java 1050 2009-07-07 16:25:22Z lars $

package org.tzi.use.gui.views.diagrams.classdiagram;

import java.awt.Color;

import org.tzi.use.gui.views.diagrams.DiagramOptions;

/**
 * Contains all optional settings for the class diagram.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public final class ClassDiagramOptions extends DiagramOptions {

    public ClassDiagramOptions() {
        // color Settings
        NODE_COLOR = new Color( 0xff, 0xf8, 0xb4 ); //new Color(0xe0, 0xe0, 0xe0);
        NODE_SELECTED_COLOR = Color.orange;
        NODE_FRAME_COLOR = Color.blue; //Color.black;
        NODE_LABEL_COLOR = Color.black;
        DIAMONDNODE_COLOR = Color.white;
        DIAMONDNODE_FRAME_COLOR = Color.black;
        EDGE_COLOR = Color.BLACK; //Color.red;
        EDGE_LABEL_COLOR = Color.darkGray;
        EDGE_SELECTED_COLOR = Color.orange;
    }
    
    public boolean isShowMutliplicities() {
        return fShowMutliplicities;
    }

    public void setShowMutliplicities( boolean showMutliplicities ) {
        fShowMutliplicities = showMutliplicities;
    }

}
