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

package org.tzi.use.gui.views.diagrams.behavior.communicationdiagram;

import java.awt.Color;

import org.tzi.use.gui.views.diagrams.DiagramOptions;

/**
 * Class for saving attribute values of communication diagrams.
 * 
 * @author Quang Dung Nguyen
 * 
 */
public class CommunicationDiagramOptions extends DiagramOptions {

	public static final String LINK_NODE_COLOR = "LINK NODE COLOR";
	public static final String LINK_NODE_SELECTED_COLOR = "LINK NODE SELECTED COLOR";
	public static final String LINK_NODE_FRAME_COLOR = "LINK NODE FRAME COLOR";
	public static final String LINK_NODE_LABEL_COLOR = "LINK NODE LABEL COLOR";

	private boolean showLifeStates;
	private boolean showCommunicationMessages;
	
	/**
	 * Copy constructor
	 */
	public CommunicationDiagramOptions(CommunicationDiagramOptions opt) {
		super(opt);
	}

	public CommunicationDiagramOptions() {
		showLifeStates = true;
		showCommunicationMessages = true;
	}

	@Override
	protected void registerAdditionalColors() {
		// color settings
		registerTypeColor(NODE_COLOR, new Color(0xe0, 0xe0, 0xe0), new Color(0xF0, 0xF0, 0xF0));
		registerTypeColor(NODE_FRAME_COLOR, Color.black, Color.black);
		registerTypeColor(NODE_LABEL_COLOR, Color.black, Color.black);

		registerTypeColor(LINK_NODE_COLOR, new Color(0xca, 0xe4, 0xa5), new Color(0xF0, 0xF0, 0xF0));
		registerTypeColor(LINK_NODE_SELECTED_COLOR, Color.orange, new Color(0xD0, 0xD0, 0xD0));
		registerTypeColor(LINK_NODE_FRAME_COLOR, Color.blue, Color.black);
		registerTypeColor(LINK_NODE_LABEL_COLOR, Color.black, Color.black);

		registerTypeColor(DIAMONDNODE_COLOR, Color.white, Color.white);
		registerTypeColor(DIAMONDNODE_FRAME_COLOR, Color.red, Color.black);
		registerTypeColor(EDGE_COLOR, Color.black, Color.black);
		registerTypeColor(EDGE_LABEL_COLOR, Color.darkGray, Color.darkGray);
		registerTypeColor(EDGE_SELECTED_COLOR, Color.orange, new Color(0xD0, 0xD0, 0xD0));
		
	}

	public Color getLinkNodeColor() {
		return getColor(LINK_NODE_COLOR);
	}

	public Color getLinkNodeSelectedColor() {
		return getColor(LINK_NODE_SELECTED_COLOR);
	}

	public Color getLinkNodeFrameColor() {
		return getColor(LINK_NODE_FRAME_COLOR);
	}

	public Color getLinkNodeLabelColor() {
		return getColor(LINK_NODE_LABEL_COLOR);
	}

	public boolean isShowCommunicationMessages() {
		return showCommunicationMessages;
	}

	public void setShowCommunicationMessages(boolean withMessage) {
		showCommunicationMessages = withMessage;
	}

	public boolean isShowLifeStates() {
		return showLifeStates;
	}

	public void setShowLifeStates(boolean lifeStates) {
		showLifeStates = lifeStates;
	}
	
	@Override
	public boolean isShowMutliplicities() {
		return false;
	}

	@Override
	public void setShowMutliplicities(boolean showMutliplicities) {

	}

}
