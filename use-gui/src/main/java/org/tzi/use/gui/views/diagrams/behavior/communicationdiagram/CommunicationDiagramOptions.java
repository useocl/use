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

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.w3c.dom.Element;

/**
 * Class for saving attribute values of communication diagrams.
 * 
 * @author Quang Dung Nguyen
 * @author Carsten Schlobohm
 * 
 */
public class CommunicationDiagramOptions extends DiagramOptions {

	private static final String LINK_NODE_COLOR = "LINK NODE COLOR";
	private static final String LINK_NODE_SELECTED_COLOR = "LINK NODE SELECTED COLOR";
	private static final String LINK_NODE_FRAME_COLOR = "LINK NODE FRAME COLOR";
	private static final String LINK_NODE_LABEL_COLOR = "LINK NODE LABEL COLOR";

	private static final String CONFIG_PROPERTY_BASE_PATH = "use.gui.view.communicationdiagram.";
	private static final String BIND_DIA_TO_MES_SELECTION = "bindVisibleMessagesToMessageSelection";
	private static final String HIDE_INCOMPLETE_LINK = "hideAssociationsWithAHiddenPartner";
	private static final String ACTOR_NAME = "actorName";
	private static final String IS_ACTOR_MOVABLE = "isActorMovable";
	private static final String IS_ACTOR_ALWAYS_VISIBLE = "isActorAlwaysVisible";
	private static final String SHOW_HIDDEN_LINKS = "showHiddenAssociations";
	private static final String SHOW_HIDDEN_OBJECT_LINKS = "showHiddenAssociationClasses";
	private static final String SHOW_HIDDEN_OBJECTS_OF_LINK = "showHiddenPartnersOfVisibleAssociation";
	private static final String SHOW_FUNCTION_PARAM = "showObjectsUsedAsFunctionParameter";
	private static final String NUMBER_OF_SEARCH_CYCLES = "numberOfSearchCycles";


	private boolean showLifeStates = false;
	private boolean showCommunicationMessages = false;

	// Indicates if a link is shown when one end of the link is hidden
	private boolean hideAssociationsWithAHiddenPartner = true;
	// if the value is set to true, only
	// nodes can be make visible which are connected to the selected messages
	private boolean bindVisibleMessagesToMessageSelection = true;

	// If a hidden link exist between objects in a result set (ocl selection, ...),
	// then the link will be automatically be made visible
	private boolean showMissingLinks = false;

	// If an hidden link-object exist between objects in a result set,
	// they will be made visible
	private boolean showMissingObjectLinks = false;
	// If a link-object has hidden connected nodes
	// they will be shown if this value is true
	private boolean showHiddenObjectsOfLinkObject = false;

	// Makes hidden nodes visible which are present as function parameter
	private boolean showHiddenFunctionParameter = false;

	// how often the iteration will run to add new hidden elements
	// its important because a new visible node can contain information about nodes
	// which are not visible yet and if these nodes are made visible,
	// they can contain more not visible data and so on
	private int searchCycles = 1;

	private String actorDefaultName = "Actor";

	// Allows to change the position of the actor
	private boolean actorMovableDefault = true;

	// Prevents the actor from hiding
	private boolean isActorAlwaysVisible = false;
	
	/**
	 * Copy constructor
	 */
	public CommunicationDiagramOptions(CommunicationDiagramOptions opt) {
		super(opt);
		showLifeStates = opt.showLifeStates;
		showCommunicationMessages = opt.showCommunicationMessages;
		hideAssociationsWithAHiddenPartner = opt.hideAssociationsWithAHiddenPartner;
		bindVisibleMessagesToMessageSelection = opt.bindVisibleMessagesToMessageSelection;
		showMissingLinks = opt.showMissingLinks;
		showMissingObjectLinks = opt.showMissingObjectLinks;
		showHiddenObjectsOfLinkObject = opt.showHiddenObjectsOfLinkObject;
		showHiddenFunctionParameter = opt.showHiddenFunctionParameter;
		searchCycles = opt.searchCycles;
		actorDefaultName = opt.actorDefaultName;
		actorMovableDefault = opt.actorMovableDefault;
		isActorAlwaysVisible = opt.isActorAlwaysVisible;
	}

	public CommunicationDiagramOptions() {
		showLifeStates = true;
		showCommunicationMessages = true;
		try {
			searchCycles = Integer.parseInt(System.getProperty(CONFIG_PROPERTY_BASE_PATH + NUMBER_OF_SEARCH_CYCLES));
		} catch (Exception e) {
			searchCycles = 1;
		}
		String actorName = System.getProperty(CONFIG_PROPERTY_BASE_PATH + ACTOR_NAME);
		if (actorName == null) {
			actorDefaultName = "Actor";
		}
		bindVisibleMessagesToMessageSelection = Boolean.getBoolean(CONFIG_PROPERTY_BASE_PATH + BIND_DIA_TO_MES_SELECTION);
		hideAssociationsWithAHiddenPartner = Boolean.getBoolean(CONFIG_PROPERTY_BASE_PATH + HIDE_INCOMPLETE_LINK);
		actorMovableDefault = Boolean.getBoolean(CONFIG_PROPERTY_BASE_PATH + IS_ACTOR_MOVABLE);
		isActorAlwaysVisible = Boolean.getBoolean(CONFIG_PROPERTY_BASE_PATH + IS_ACTOR_ALWAYS_VISIBLE);
		showMissingLinks = Boolean.getBoolean(CONFIG_PROPERTY_BASE_PATH + SHOW_HIDDEN_LINKS);
		showMissingObjectLinks = Boolean.getBoolean(CONFIG_PROPERTY_BASE_PATH + SHOW_HIDDEN_OBJECT_LINKS);
		showHiddenObjectsOfLinkObject = Boolean.getBoolean(CONFIG_PROPERTY_BASE_PATH + SHOW_HIDDEN_OBJECTS_OF_LINK);
		showHiddenFunctionParameter = Boolean.getBoolean(CONFIG_PROPERTY_BASE_PATH + SHOW_FUNCTION_PARAM);
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

	Color getLinkNodeColor() {
		return getColor(LINK_NODE_COLOR);
	}

	Color getLinkNodeSelectedColor() {
		return getColor(LINK_NODE_SELECTED_COLOR);
	}

	Color getLinkNodeFrameColor() {
		return getColor(LINK_NODE_FRAME_COLOR);
	}

	Color getLinkNodeLabelColor() {
		return getColor(LINK_NODE_LABEL_COLOR);
	}

	boolean isShowCommunicationMessages() {
		return showCommunicationMessages;
	}

	void setShowCommunicationMessages(boolean withMessage) {
		showCommunicationMessages = withMessage;
	}

	boolean isShowLifeStates() {
		return showLifeStates;
	}

	void setShowLifeStates(boolean lifeStates) {
		showLifeStates = lifeStates;
	}
	
	@Override
	public boolean isShowMutliplicities() {
		return false;
	}

	@Override
	public void setShowMutliplicities(boolean showMutliplicities) {

	}

	boolean isHideAssociationsWithAHiddenPartner() {
		return hideAssociationsWithAHiddenPartner;
	}

	void setHideAssociationsWithAHiddenPartner(boolean hideAssociationsWithAHiddenPartner) {
		this.hideAssociationsWithAHiddenPartner = hideAssociationsWithAHiddenPartner;
	}

	boolean isShowMissingLinks() {
		return showMissingLinks;
	}

	void setShowMissingLinks(boolean showMissingLinks) {
		this.showMissingLinks = showMissingLinks;
	}

	boolean isBindVisibleMessagesToMessageSelection() {
		return bindVisibleMessagesToMessageSelection;
	}

	void setBindVisibleMessagesToMessageSelection(boolean bindVisibleMessagesToMessageSelection) {
		this.bindVisibleMessagesToMessageSelection = bindVisibleMessagesToMessageSelection;
	}

	boolean isShowMissingObjectLinks() {
		return showMissingObjectLinks;
	}

	void setShowMissingObjectLinks(boolean showMissingObjectLinks) {
		this.showMissingObjectLinks = showMissingObjectLinks;
	}

	boolean isShowHiddenObjectsOfLinkObject() {
		return showHiddenObjectsOfLinkObject;
	}

	void setShowHiddenObjectsOfLinkObject(boolean showHiddenObjectsOfLinkObject) {
		this.showHiddenObjectsOfLinkObject = showHiddenObjectsOfLinkObject;
	}

	boolean isShowHiddenFunctionParameter() {
		return showHiddenFunctionParameter;
	}

	void setShowHiddenFunctionParameter(boolean showHiddenFunctionParameter) {
		this.showHiddenFunctionParameter = showHiddenFunctionParameter;
	}

	int getSearchCycles() {
		return searchCycles;
	}

	void setSearchCycles(int searchCycles) {
		this.searchCycles = searchCycles;
	}

	String getActorDefaultName() {
		return actorDefaultName;
	}

	boolean isActorAlwaysVisible() {
		return isActorAlwaysVisible;
	}

	boolean isActorMovableDefault() {
		return actorMovableDefault;
	}

	@Override
	public void saveOptions(PersistHelper helper, Element parent) {
		super.saveOptions(helper, parent);
		helper.appendChild(parent, BIND_DIA_TO_MES_SELECTION, String.valueOf(bindVisibleMessagesToMessageSelection));
		helper.appendChild(parent, HIDE_INCOMPLETE_LINK, String.valueOf(hideAssociationsWithAHiddenPartner));
		helper.appendChild(parent, SHOW_HIDDEN_LINKS, String.valueOf(showMissingLinks));
		helper.appendChild(parent, SHOW_HIDDEN_OBJECT_LINKS, String.valueOf(showMissingObjectLinks));
		helper.appendChild(parent, SHOW_HIDDEN_OBJECTS_OF_LINK, String.valueOf(showHiddenObjectsOfLinkObject));
		helper.appendChild(parent, SHOW_FUNCTION_PARAM, String.valueOf(showHiddenFunctionParameter));
	}

	@Override
	public void loadOptions(PersistHelper helper, int version) {
		super.loadOptions(helper, version);
		bindVisibleMessagesToMessageSelection = helper.getElementBooleanValue(BIND_DIA_TO_MES_SELECTION);
		hideAssociationsWithAHiddenPartner = helper.getElementBooleanValue(HIDE_INCOMPLETE_LINK);
		showMissingLinks = helper.getElementBooleanValue(SHOW_HIDDEN_LINKS);
		showMissingObjectLinks = helper.getElementBooleanValue(SHOW_HIDDEN_OBJECT_LINKS);
		showHiddenObjectsOfLinkObject = helper.getElementBooleanValue(SHOW_HIDDEN_OBJECTS_OF_LINK);
		showHiddenFunctionParameter = helper.getElementBooleanValue(SHOW_FUNCTION_PARAM);
	}
}
