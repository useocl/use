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

package org.tzi.use.gui.views.diagrams;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.w3c.dom.Element;

/**
 * Contains Options for the class and object diagrams.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public abstract class DiagramOptions {

    protected boolean fDoAutoLayout = false;
    protected boolean fShowRolenames = false;
    protected boolean fShowAssocNames = true;
    protected boolean fDoAntiAliasing = false;
    protected boolean fShowMutliplicities = false;
    protected boolean fShowAttributes = false;
    protected boolean fShowOperations = false;
    protected boolean fShowGrid = false;
    protected boolean fShowCoverage = false;
    
    // color settings
    protected Color NODE_COLOR;
    protected Color NODE_SELECTED_COLOR;
    protected Color NODE_FRAME_COLOR;
    protected Color NODE_LABEL_COLOR;
    protected Color DIAMONDNODE_COLOR;
    protected Color DIAMONDNODE_FRAME_COLOR;
    protected Color EDGE_COLOR;
    protected Color EDGE_LABEL_COLOR;
    protected Color EDGE_SELECTED_COLOR;
    
    public abstract boolean isShowMutliplicities();
    public abstract void setShowMutliplicities( boolean showMutliplicities );
    
    public boolean isDoAutoLayout() {
        return fDoAutoLayout;
    }
    public void setDoAutoLayout( boolean doAutoLayout ) {
        fDoAutoLayout = doAutoLayout;
        onOptionChanged("DOAUTOLAYOUT");
    }

    public boolean isShowAssocNames() {
        return fShowAssocNames;
    }
    public void setShowAssocNames( boolean showAssocNames ) {
        fShowAssocNames = showAssocNames;
        onOptionChanged("SHOWASSOCNAMES");
    }

    public boolean isShowRolenames() {
        return fShowRolenames;
    }
    public void setShowRolenames( boolean showRolenames ) {
        fShowRolenames = showRolenames;
        onOptionChanged("SHOWROLENAMES");
    }

    public boolean isDoAntiAliasing() {
        return fDoAntiAliasing;
    }
    public void setDoAntiAliasing( boolean doAntiAliasing ) {
        fDoAntiAliasing = doAntiAliasing;
        onOptionChanged("DOANTIALIASING");
    }
    
    public boolean isShowAttributes() {
        return fShowAttributes;
    }
    public void setShowAttributes( boolean showAttributes ) {
        fShowAttributes = showAttributes;
        onOptionChanged("SHOWATTRIBUTES");
    }

    public boolean isShowOperations() {
        return fShowOperations;
    }
    public void setShowOperations( boolean showOperations ) {
        fShowOperations = showOperations;
        onOptionChanged("SHOWOPERATIONS");
    }
    
    /**
	 * @param b
	 */
	public void setShowCoverage(boolean showCoverage) {
		fShowCoverage = showCoverage;
		onOptionChanged("SHOWCOVERAGE");
	}

	public boolean isShowCoverage() {
		return fShowCoverage;
	}

	
	public boolean showGrid() {
		return fShowGrid;
	}
	public void setShowGrid(boolean showGrid) {
		fShowGrid = showGrid;
		onOptionChanged("SHOWGRID");
	}
	
    public Color getDIAMONDNODE_COLOR() {
        return DIAMONDNODE_COLOR;
    }
    public Color getDIAMONDNODE_FRAME_COLOR() {
        return DIAMONDNODE_FRAME_COLOR;
    }
    public Color getEDGE_COLOR() {
        return EDGE_COLOR;
    }
    public Color getEDGE_LABEL_COLOR() {
        return EDGE_LABEL_COLOR;
    }
    public Color getEDGE_SELECTED_COLOR() {
        return EDGE_SELECTED_COLOR;
    }
    public Color getNODE_COLOR() {
        return NODE_COLOR;
    }
    public Color getNODE_FRAME_COLOR() {
        return NODE_FRAME_COLOR;
    }
    public Color getNODE_LABEL_COLOR() {
        return NODE_LABEL_COLOR;
    }
    public Color getNODE_SELECTED_COLOR() {
        return NODE_SELECTED_COLOR;
    }
    
    protected boolean fIsLoadingLayout = false;
    
	/**
	 * @return
	 */
	public boolean isLoadingLayout() {
		return fIsLoadingLayout;
	}
    
	public void setIsLoadingLayout(boolean isLoading) {
		fIsLoadingLayout = isLoading;
		onOptionChanged("ISLOADINGLAYOUT");
	}
	
	/**
	 * @param optionsElement
	 */
	public void saveOptions(PersistHelper helper, Element parent) {
		// save diagram options
		helper.appendChild(parent, LayoutTags.AUTOLAYOUT, String.valueOf(isDoAutoLayout()));
		helper.appendChild(parent, LayoutTags.ANTIALIASING, String.valueOf(isDoAntiAliasing()));
		helper.appendChild(parent, LayoutTags.SHOWASSOCNAMES, String.valueOf(isShowAssocNames()));
		helper.appendChild(parent, LayoutTags.SHOWATTRIBUTES, String.valueOf(isShowAttributes()));        
		helper.appendChild(parent, LayoutTags.SHOWMULTIPLICITIES, String.valueOf(isShowMutliplicities()));
		helper.appendChild(parent, LayoutTags.SHOWOPERATIONS, String.valueOf(isShowOperations()));
		helper.appendChild(parent, LayoutTags.SHOWROLENAMES, String.valueOf(isShowRolenames()));
		helper.appendChild(parent, LayoutTags.SHOWGRID, String.valueOf(showGrid()));
	}
	/**
	 * @param rootElement
	 */
	public void loadOptions(PersistHelper helper, Element parent, String version) {
		setDoAutoLayout(helper.getElementBooleanValue(parent, LayoutTags.AUTOLAYOUT));
		setDoAntiAliasing(helper.getElementBooleanValue(parent, LayoutTags.ANTIALIASING));
		setShowAssocNames(helper.getElementBooleanValue(parent, LayoutTags.SHOWASSOCNAMES));
		setShowAttributes(helper.getElementBooleanValue(parent, LayoutTags.SHOWATTRIBUTES));
		setShowMutliplicities(helper.getElementBooleanValue(parent, LayoutTags.SHOWMULTIPLICITIES));
		setShowOperations(helper.getElementBooleanValue(parent, LayoutTags.SHOWOPERATIONS));
		setShowRolenames(helper.getElementBooleanValue(parent, LayoutTags.SHOWROLENAMES));
		if (!version.equals("1")) {
			setShowGrid(helper.getElementBooleanValue(parent, LayoutTags.SHOWGRID));
		}
	}
	
	private List<DiagramOptionChangedListener> optionChangedListener = new ArrayList<DiagramOptionChangedListener>();
	
	public void addOptionChangedListener(DiagramOptionChangedListener listener) {
		optionChangedListener.add(listener);
	}
	
	public void removeOptionChangedListener(DiagramOptionChangedListener listener) {
		optionChangedListener.remove(listener);
	}
	
	protected void onOptionChanged(String optionname) {
		for (DiagramOptionChangedListener listener : optionChangedListener) {
			listener.optionChanged(optionname);
		}
	}
}
