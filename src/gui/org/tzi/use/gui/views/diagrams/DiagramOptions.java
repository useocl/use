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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
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
	/**
	 * The current version number of the layout informations
	 */
	public static int XML_LAYOUT_VERSION = 13;
	
    protected boolean fDoAutoLayout = false;
    protected boolean fShowRolenames = true;
    protected boolean fShowAssocNames = false;
    protected boolean fDoAntiAliasing = false;
    protected boolean fShowMutliplicities = false;
    protected boolean fShowAttributes = true;
    protected boolean fShowOperations = false;
    protected boolean fGroupMR = false;
    protected boolean fShowGrid = false;
    protected boolean fGrayscale = false;

    protected boolean showUnionConstraints = true;
    protected boolean showSubsetsConstraints = true;
    protected boolean showRedefinesConstraints = true;
    
    protected Path modelFileName = null;
    
    // color settings
    public static final String NODE_COLOR = "NODE_COLOR";
    public static final String NODE_SELECTED_COLOR = "NODE_SELECTED_COLOR";
    public static final String NODE_FRAME_COLOR = "NODE_FRAME_COLOR";
    public static final String NODE_LABEL_COLOR = "NODE_LABEL_COLOR";
    public static final String DIAMONDNODE_COLOR = "DIAMONDNODE_COLOR";
    public static final String DIAMONDNODE_FRAME_COLOR = "DIAMONDNODE_FRAME_COLOR";
    public static final String EDGE_COLOR = "EDGE_COLOR";
    public static final String EDGE_LABEL_COLOR = "EDGE_LABEL_COLOR";
    public static final String EDGE_SELECTED_COLOR = "EDGE_SELECTED_COLOR";
    public static final String GREYED_FILL_COLOR = "GREYED_FILL_COLOR";
	public static final String GREYED_LINE_COLOR = "GREYED_LINE_COLOR";
	public static final String GREYED_SELECTED_LINE_COLOR = "GREYED_SELECTED_LINE_COLOR";
    
    public abstract boolean isShowMutliplicities();
    
    public abstract void setShowMutliplicities( boolean showMutliplicities );

    private List<DiagramOptionChangedListener> optionChangedListener = new ArrayList<DiagramOptionChangedListener>();
    
    public DiagramOptions() {
    	registerColors();
    	registerAdditionalColors();
    }
    
    /**
     * Copy constructor. Copies only the settings, no listeners.
     * @param source
     */
    public DiagramOptions(DiagramOptions source) {
    	
    	this.colorSafe = new HashMap<String, DiagramOptions.ColorContainer>(source.colorSafe);
        
        this.fDoAutoLayout = source.fDoAutoLayout;
        this.fShowRolenames = source.fShowRolenames;
        this.fShowAssocNames = source.fShowAssocNames;
        this.fDoAntiAliasing = source.fDoAntiAliasing;
        this.fShowMutliplicities = source.fShowMutliplicities;
        this.fShowAttributes = source.fShowAttributes;
        this.fShowOperations = source.fShowOperations;
        this.fShowGrid = source.fShowGrid;
        this.fGrayscale = source.fGrayscale;

        this.modelFileName = source.modelFileName;
    }
    
    private void registerColors() {
    	// default colors
        registerTypeColor(NODE_COLOR, new Color( 0xff, 0xf8, 0xb4 ), new Color(0xF0, 0xF0, 0xF0));
    	registerTypeColor(NODE_SELECTED_COLOR, Color.orange, new Color(0xD0, 0xD0, 0xD0));
    	registerTypeColor(NODE_FRAME_COLOR, Color.blue, Color.BLACK);
    	registerTypeColor(NODE_LABEL_COLOR, Color.black, Color.BLACK);
    	registerTypeColor(DIAMONDNODE_COLOR, Color.white, Color.WHITE);
    	registerTypeColor(DIAMONDNODE_FRAME_COLOR, Color.black, Color.BLACK);
    	registerTypeColor(EDGE_COLOR, Color.BLACK, Color.BLACK);
    	registerTypeColor(EDGE_LABEL_COLOR, Color.black, Color.BLACK);
    	registerTypeColor(EDGE_SELECTED_COLOR, Color.ORANGE, new Color(0x50, 0x50, 0x50));
    	registerTypeColor(GREYED_FILL_COLOR, new Color(249, 249, 249), Color.WHITE);
		registerTypeColor(GREYED_LINE_COLOR, new Color(189, 189, 191), Color.WHITE);
		registerTypeColor(GREYED_SELECTED_LINE_COLOR, new Color(120, 120, 120), Color.WHITE);
    }
    
    protected abstract void registerAdditionalColors();
    
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
    
    public boolean isGroupMR() {
        return fGroupMR;
    }
    public void setGroupMR( boolean groupMR ) {
        fGroupMR = groupMR;
        onOptionChanged("GROUPMR");
    }
	
	public boolean showGrid() {
		return fShowGrid;
	}
	public void setShowGrid(boolean showGrid) {
		fShowGrid = showGrid;
		onOptionChanged("SHOWGRID");
	}
	
	private static class ColorContainer {
		public final Color inColor;
		public final Color inGray;
		
		public ColorContainer(Color inColor, Color inGray) {
			this.inColor = inColor;
			this.inGray = inGray;
		}
	}
	
	private HashMap<String, ColorContainer> colorSafe = new HashMap<String, ColorContainer>(10);
	
	public boolean grayscale() {
		return fGrayscale;
	}
	
	public void registerTypeColor(String key, Color inColor, Color inGray) {
		this.colorSafe.put(key, new ColorContainer(inColor, inGray));
	}
	
	public void setGrayscale(boolean grayscale) {
		fGrayscale = grayscale;
		onOptionChanged("GRAYSCALE");
	}
	
	public Color getColor(String key) {
		if (!this.colorSafe.containsKey(key))
			throw new IllegalArgumentException("unknown color key!");
		
		ColorContainer c = this.colorSafe.get(key);
		
		return (this.grayscale() ? c.inGray : c.inColor);
	}
	
    public Color getDIAMONDNODE_COLOR() {
        return getColor("DIAMONDNODE_COLOR");
    }
    public Color getDIAMONDNODE_FRAME_COLOR() {
    	return getColor("DIAMONDNODE_FRAME_COLOR");
    }
    public Color getEDGE_COLOR() {
    	return getColor("EDGE_COLOR");
    }
    public Color getEDGE_LABEL_COLOR() {
    	return getColor("EDGE_LABEL_COLOR");
    }
    public Color getEDGE_SELECTED_COLOR() {
    	return getColor("EDGE_SELECTED_COLOR");
    }
    public Color getNODE_COLOR() {
    	return getColor("NODE_COLOR");
    }
    public Color getNODE_FRAME_COLOR() {
    	return getColor("NODE_FRAME_COLOR");
    }
    public Color getNODE_LABEL_COLOR() {
    	return getColor("NODE_LABEL_COLOR");
    }
    public Color getNODE_SELECTED_COLOR() {
    	return getColor("NODE_SELECTED_COLOR");
    }
	public Color getGREYED_FILL_COLOR() {
		return getColor(GREYED_FILL_COLOR);
	}
	public Color getGREYED_LINE_COLOR() {
		return getColor(GREYED_LINE_COLOR);
	}
	public Color getGREYED_SELECTED_LINE_COLOR() {
		return getColor(GREYED_SELECTED_LINE_COLOR);
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
	public void loadOptions(PersistHelper helper, int version) {
		setDoAutoLayout(helper.getElementBooleanValue(LayoutTags.AUTOLAYOUT));
		setDoAntiAliasing(helper.getElementBooleanValue(LayoutTags.ANTIALIASING));
		setShowAssocNames(helper.getElementBooleanValue(LayoutTags.SHOWASSOCNAMES));
		setShowAttributes(helper.getElementBooleanValue(LayoutTags.SHOWATTRIBUTES));
		setShowMutliplicities(helper.getElementBooleanValue(LayoutTags.SHOWMULTIPLICITIES));
		setShowOperations(helper.getElementBooleanValue(LayoutTags.SHOWOPERATIONS));
		setShowRolenames(helper.getElementBooleanValue(LayoutTags.SHOWROLENAMES));
		if (version > 1) {
			setShowGrid(helper.getElementBooleanValue(LayoutTags.SHOWGRID));
		}
	}
	
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
	/**
	 * @return the directory
	 */
	public Path getDirectory() {
		return modelFileName == null ? null : modelFileName.toAbsolutePath().getParent();
	}
		
	/**
	 * @return the modelFileName
	 */
	public Path getModelFileName() {
		return modelFileName;
	}
	
	/**
	 * @param modelFileName the modelFileName to set
	 */
	public void setModelFileName(Path modelFileName) {
		this.modelFileName = modelFileName;
	}
	/**
	 * @return
	 */
	public boolean isShowUnionConstraints() {
		return showUnionConstraints;
	}
	
	public void setShowUnionConstraints(boolean show) {
		this.showUnionConstraints = show;
		onOptionChanged("SHOW_UNION_CONSTRAINTS");
	}
	
	/**
	 * @return
	 */
	public boolean isShowSubsetsConstraints() {
		return showSubsetsConstraints;
	}
	
	public void setShowSubsetsConstraints(boolean show) {
		this.showSubsetsConstraints = show;
		onOptionChanged("SHOW_SUBSETS_CONSTRAINTS");
	}
	
	/**
	 * @return
	 */
	public boolean isShowRedefinesConstraints() {
		return showRedefinesConstraints;
	}
	
	public void setShowRedefinesConstraints(boolean show) {
		this.showRedefinesConstraints = show;
		onOptionChanged("SHOW_REDEFINES_CONSTRAINTS");
	}
}
