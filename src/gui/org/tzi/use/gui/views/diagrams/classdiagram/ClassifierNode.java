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

package org.tzi.use.gui.views.diagrams.classdiagram;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.tzi.use.gui.views.diagrams.DiagramOptionChangedListener;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.elements.CompartmentNode;
import org.tzi.use.gui.views.diagrams.util.Util;
import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.util.MathUtil;

/**
 * Base class for nodes representing a classifier (class or enumeration)
 * 
 * @author Lars Hamann
 *
 */
public abstract class ClassifierNode extends CompartmentNode implements DiagramOptionChangedListener {

	/**
     * The size of all three compartments (name, attributes, operations) is
     * calculated once.
     * The correct size is returned by checking the diagram options 
     * ({@link DiagramOptions#isShowAttributes(boolean)} and 
     * ({@link DiagramOptions#isShowOperations()}.
     *  
     */
    protected Rectangle2D.Double nameRect = new Rectangle2D.Double();
    protected Rectangle2D.Double attributesRect = new Rectangle2D.Double();
    protected Rectangle2D.Double operationsRect = new Rectangle2D.Double();
    
    protected DiagramOptions fOpt;
    
    protected String fLabel;
    
    protected MClassifier classifier;
    
    public ClassifierNode(MClassifier cls, DiagramOptions opt) {
    	this.classifier = cls;
    	this.fLabel = cls.name();
    	this.fOpt = opt;
    	this.fOpt.addOptionChangedListener(this);
    }
    
	public MClassifier getClassifier() {
		return classifier;
	}
	
	@Override
	public String name() {
		return getClassifier().name();
	}
	
	@Override
    public String getId() {
    	return name(); 
    }
	
	private double lineHeight;
	
	protected void calculateBounds() {
		double width = nameRect.width;
		double height = nameRect.height;
		
		double requiredHeight = nameRect.height;
		
		if (fOpt.isShowAttributes()) {
			width = Math.max(width, attributesRect.width);
			height += attributesRect.height;
			if (hasAttributes())
				requiredHeight += lineHeight + VERTICAL_INDENT;
			else
				requiredHeight += 2 * VERTICAL_INDENT;
		}
		
		if (fOpt.isShowOperations()) {
			width = Math.max(width, operationsRect.width);
			height += operationsRect.height;
			if (hasOperations())
				requiredHeight += lineHeight + VERTICAL_INDENT;
			else
				requiredHeight += 2 * VERTICAL_INDENT;
		}

		width += 10;
		
		setRequiredHeight("CLASSIFIERNODE", requiredHeight);
        height = MathUtil.max(height, getMinHeight(), getRequiredHeight());
        width = MathUtil.max(width, getMinWidth(), getRequiredWidth());

        setCalculatedBounds(width, height);
	}
	
	/**
     * Sets the correct size of the width and height of this class node.
     * This method needs to be called before actually drawing the node.
     * (Width and height are needed from other methods before the nodes are
     * drawn.)
     */
	@Override
    public final void doCalculateSize( Graphics2D g ) {
        calculateNameRectSize(g, nameRect);
        calculateAttributeRectSize(g, attributesRect);
        calculateOperationsRectSize(g, operationsRect);
        this.lineHeight = Util.getLineHeight(g.getFontMetrics());
        calculateBounds();
    }
    
	@Override
	public void optionChanged(String optionname) {
		if (optionname.equals("SHOWOPERATIONS") ||
			optionname.equals("SHOWATTRIBUTES")	)
		calculateBounds();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		fOpt.removeOptionChangedListener(this);
	}

    protected abstract void calculateNameRectSize(Graphics2D g, Rectangle2D.Double rect);
    protected abstract void calculateAttributeRectSize(Graphics2D g, Rectangle2D.Double rect);
    protected abstract void calculateOperationsRectSize(Graphics2D g, Rectangle2D.Double rect);
    
    protected abstract boolean hasAttributes();
	protected abstract boolean hasOperations();
}
