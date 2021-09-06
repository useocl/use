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

package org.tzi.use.gui.views.diagrams.objectdiagram;

import java.awt.Color;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.w3c.dom.Element;

/**
 * Contains all optional settings for the object diagram.
 *
 * @author Fabian Gutsche
 */
public final class ObjDiagramOptions extends DiagramOptions {

	private boolean isShowStates = false;

	public ObjDiagramOptions() {
	}

	/**
	 * Copy constructor
	 */
	public ObjDiagramOptions(ObjDiagramOptions opt) {
		super(opt);
	}

	@Override
	protected void registerAdditionalColors() {
		// color settings
		registerTypeColor(NODE_COLOR, new Color(0xe0, 0xe0, 0xe0), new Color(0xF0, 0xF0, 0xF0));
		registerTypeColor(NODE_SELECTED_COLOR, Color.ORANGE, new Color(0xD0, 0xD0, 0xD0));
		registerTypeColor(NODE_FRAME_COLOR, Color.BLACK, Color.BLACK);
		registerTypeColor(NODE_LABEL_COLOR, Color.BLACK, Color.BLACK);
		registerTypeColor(DIAMONDNODE_COLOR, Color.WHITE, Color.WHITE);
		registerTypeColor(DIAMONDNODE_FRAME_COLOR, Color.RED, Color.BLACK);
		registerTypeColor(EDGE_COLOR, Color.RED, Color.BLACK);
		registerTypeColor(EDGE_LABEL_COLOR, Color.DARK_GRAY, Color.BLACK);
		registerTypeColor(EDGE_SELECTED_COLOR, Color.ORANGE, new Color(0x50, 0x50, 0x50));
	}

	public boolean isShowMutliplicities() {
		return false;
	}

	public void setShowMutliplicities(boolean showMutliplicities) {
	}

	/**
	 * @return the isShowStates
	 */
	public boolean isShowStates() {
		return isShowStates;
	}

	/**
	 * @param isShowStates
	 *            the isShowStates to set
	 */
	public void setShowStates(boolean isShowStates) {
		this.isShowStates = isShowStates;
		this.onOptionChanged("SHOWSTATES");
	}

	@Override
	public void saveOptions(PersistHelper helper, Element parent) {
		super.saveOptions(helper, parent);
		helper.appendChild(parent, "ShowStates", String.valueOf(this.isShowStates));
	}

	@Override
	public void loadOptions(PersistHelper helper, int version) {
		super.loadOptions(helper, version);
		this.isShowStates = helper.getElementBooleanValue("ShowStates");
	}
}