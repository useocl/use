/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2012 Mark Richters, University of Bremen
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
package org.tzi.use.gui.views.diagrams.statemachine;

import java.awt.Color;
import java.nio.file.Path;

import org.tzi.use.gui.views.diagrams.DiagramOptions;

/**
 * @author Lars Hamann
 *
 */
public class StateMachineDiagramOptions extends DiagramOptions {

	public StateMachineDiagramOptions() {
    }
    
	public StateMachineDiagramOptions(Path modelFile) {
    	this();
    	this.modelFileName = modelFile;
    }

	@Override
	protected void registerAdditionalColors() {
		// color Settings
        registerTypeColor(NODE_COLOR, new Color(0xe0, 0xe0, 0xe0), new Color(0xF0, 0xF0, 0xF0));
    	registerTypeColor(NODE_SELECTED_COLOR, Color.orange, new Color(0xD0, 0xD0, 0xD0));
    	registerTypeColor(NODE_FRAME_COLOR, Color.blue, Color.BLACK);
    	registerTypeColor(NODE_LABEL_COLOR, Color.black, Color.BLACK);
    	registerTypeColor(DIAMONDNODE_COLOR, Color.white, Color.WHITE);
    	registerTypeColor(DIAMONDNODE_FRAME_COLOR, Color.black, Color.BLACK);
    	registerTypeColor(EDGE_COLOR, Color.BLACK, Color.BLACK);
    	registerTypeColor(EDGE_LABEL_COLOR, Color.darkGray, Color.BLACK);
    	registerTypeColor(EDGE_SELECTED_COLOR, Color.ORANGE, new Color(0x50, 0x50, 0x50));
	}
	
	public StateMachineDiagramOptions(StateMachineDiagramOptions source) {
		super(source);
	}
	
	//TODO: Move down!
    public boolean isShowMutliplicities() {
        return false;
    }

    public void setShowMutliplicities( boolean showMutliplicities ) { }

}
