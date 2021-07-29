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

import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.behavior.shared.VisibleData;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.util.StringUtil;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Association links will be represented as LinkBoxNode
 * 
 * @author Quang Dung Nguyen
 * @author Carsten Schlobohm
 * 
 */
public class LinkBoxNode extends BaseNode {

	private final MLink link;
	private final String insertedStamp;

	public LinkBoxNode(MLink link, String sequenceNumber, CommunicationDiagramView parent, DiagramOptions opt) {
		super(parent, opt);
		this.link = link;
		this.insertedStamp = sequenceNumber;

		String assName = link.association().toString();
		label = ((link instanceof MLinkObject) ? ((MLinkObject)link).name() : "") +  ":" + assName;
		initLabel(label);
	}

	public MLink getLink() {
		return link;
	}

	@Override
	public String name() {
		return link.toString() + "." + insertedStamp;
	}
	
	@Override
	public String getTextForMenu() {
		return "link " + link.association() + " (" + StringUtil.fmtSeq(link.linkedObjects(), ", ") + ")";
	}

	@Override
	protected String getStoreType() {
		return "Link Node";
	}

	@Override
	public String toString() {
		return "LinkObjectNode[" + link.toString() + "]";
	}

	@Override
	public boolean willBeDrawn() {
		VisibleData visibleData = getComDiaView().getCommunicationDiagram().getSharedVisibleManager().getData();
		if (visibleData.areElementsAlwaysVisible(new HashSet<MObject>(), new HashSet<MLink>(Arrays.asList(link)))) {
			return true;
		}
		return super.willBeDrawn();
	}

	@Override
	public boolean isHidden() {
		if (getOriginalLifeState() != ObjectState.DELETED && getOriginalLifeState() != ObjectState.TRANSIENT) {
			VisibleData visibleData = getComDiaView().getCommunicationDiagram().getSharedVisibleManager().getData();
			return !visibleData.isLinkVisible(link);
		} else {
			return super.isHidden();
		}

	}

	@Override
	public void setHidden(boolean isHidden) {
		if (getOriginalLifeState() != ObjectState.DELETED && getOriginalLifeState() != ObjectState.TRANSIENT) {
			VisibleData visibleData = getComDiaView().getCommunicationDiagram().getSharedVisibleManager().getData();
			visibleData.changeLinkVisibility(link, !isHidden);
		} else {
			super.setHidden(isHidden);
		}
	}
}
