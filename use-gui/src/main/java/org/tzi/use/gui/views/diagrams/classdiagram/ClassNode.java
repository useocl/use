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
package org.tzi.use.gui.views.diagrams.classdiagram;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.tzi.use.gui.main.ModelBrowserSorting;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeEvent;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeListener;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.util.Util;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MOperation;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 * A node representing a class.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class ClassNode extends ClassifierNode implements SortChangeListener {
		
    private List<MAttribute> fAttributes;
    private List<MOperation> fOperations;
    
    private final String[] fAttrValues;
    private final Color[] fAttrColors;
    
    private final String[] fOprSignatures;
    private final Color[] fOperationColors;
    
    private Color color = null;

    protected ClassNode( MClass cls, DiagramOptions opt ) {
    	super(cls, opt);
        
        fAttributes = cls.attributes(); 
        fAttrValues = new String[fAttributes.size()];
        fAttrColors = new Color[fAttributes.size()];

        fOperations = new ArrayList<>(Collections2.filter(cls.operations(), new Predicate<MOperation>() {
			@Override
			public boolean apply(MOperation input) {
				return !input.getAnnotationValue("View", "hideInDiagram").equals("true");
			}
		}));
        
        fOprSignatures = new String[fOperations.size()];
        fOperationColors = new Color[fOperations.size()];
                        
        copyDisplayedValues();
    }
    
    @Override
	public boolean isResizable() {
		return true;
	}
    
    /**
     * Gets the {@link MClass} represented by this class node.
     * @return The represented <code>MClass</code>
     */
    public MClass cls() {
        return (MClass)getClassifier();
    }
    
    /**
     * Gets the currently set color of the class node.
     * May be <code>null</code>.
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color of the class node to <code>color</code>.
	 * If color is <code>null</code> the default specified in the properties file color is used.
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Sets the color of the attribute <code>attr</code> to <code>color</code>.
	 * @param att
	 * @param color
	 */
	public void setAttributeColor(MAttribute att, Color color) {
		fAttrColors[fAttributes.indexOf(att)] = color;
	}
	
	/**
	 * Sets the color of the operation <code>op</code> to <code>color</code>.
	 * @param op
	 * @param color
	 */
	public void setOperationColor(MOperation op, Color color) {
		fOperationColors[fOperations.indexOf(op)] = color;
	}
	
	/**
	 * Resets the attribute colors to the color of the class node
	 */
	public void resetAttributeColor() {
		for (int i = 0; i < fAttrColors.length; i++) {
			fAttrColors[i] = null;
		}
	}
	
	/**
	 * Resets the operation colors to the color of the class node
	 */
	public void resetOperationColor() {
		for (int i = 0; i < fOperationColors.length; i++) {
			fOperationColors[i] = null;
		}
	}
	
	/**
     * After the occurrence of an event the attribute list is updated.
     */
    @Override
	public void stateChanged( SortChangeEvent e ) {
        copyDisplayedValues();
    }
    
    private void copyDisplayedValues() {
    	fAttributes = ModelBrowserSorting.getInstance().sortAttributes( fAttributes );
    	fOperations = ModelBrowserSorting.getInstance().sortOperations( fOperations );
    	
    	for ( int i = 0; i < fAttributes.size(); i++ ) {
            MAttribute attr = fAttributes.get( i );
            fAttrValues[i] = attr.toString();
        }
    	
    	for ( int i = 0; i < fOperations.size(); i++ ) {
            MOperation opr = fOperations.get( i );
            fOprSignatures[i] = opr.signature();
        }
    }
    @Override
    protected void calculateNameRectSize(Graphics2D g, Rectangle2D.Double rect) {
        Font classNameFont;
        
        if ( cls().isAbstract() ) {
            classNameFont = g.getFont().deriveFont( Font.ITALIC );
        } else {
        	classNameFont = g.getFont();
        }
        
        FontMetrics classNameFontMetrics = g.getFontMetrics( classNameFont );
        
        rect.width = classNameFontMetrics.stringWidth( fLabel );
		rect.height = classNameFontMetrics.getDescent()
				+ classNameFontMetrics.getAscent() + (2 * VERTICAL_INDENT);
		
		// At least the class name should be visible
		this.setRequiredHeight("CLASSNODE", rect.height);
		this.setRequiredWidth("CLASSNODE", rect.width + (2 * HORIZONTAL_INDENT));
    }
    
    @Override
    protected void calculateAttributeRectSize(Graphics2D g, Rectangle2D.Double rect) {
    	calculateCompartmentRectSize(g, rect, fAttrValues);
    }
    
    @Override
    protected void calculateOperationsRectSize(Graphics2D g, Rectangle2D.Double rect) {
    	calculateCompartmentRectSize(g, rect, fOprSignatures);
    }
    
    public String ident() {
        return "Class." + cls().name();
    }
    public String identNodeEdge() {
        return "AssociationClass." + cls().name();
    }
    
    /**
     * Draws a box with a label.
     */
    @Override
    protected void onDraw( Graphics2D g ) {
    	Rectangle2D.Double currentBounds = getRoundedBounds();
    	
    	if (g.getClipBounds() != null && 
    	    !Util.enlargeRectangle(currentBounds, 10).intersects(g.getClipBounds())) {
    		return;
    	}
                
        int y;
                
        FontMetrics fm; 
        
        Font oldFont = g.getFont();
        if ( cls().isAbstract() ) {
            g.setFont( oldFont.deriveFont( Font.ITALIC ) );
        } 
        
        fm = g.getFontMetrics();
        
        int labelWidth = fm.stringWidth( fLabel );
        
        if ( isSelected() ) {
            g.setColor( fOpt.getNODE_SELECTED_COLOR() );
        } else {
        	if (getColor() != null)
        		g.setColor( getColor() );
        	else
        		g.setColor( fOpt.getNODE_COLOR() );
        }
        
        g.fill( currentBounds );
                        
        double x = getCenter().getX();
        x -= labelWidth / 2;
        y = (int)currentBounds.getY() + fm.getAscent() + VERTICAL_INDENT;
        g.setColor( fOpt.getNODE_LABEL_COLOR() );
        // We know that the name fits, because we require this size
        g.drawString( fLabel, Math.round(x), y );
        
        y += VERTICAL_INDENT + fm.getDescent();
        
        // resetting font and fontMetrics if the class was abstract
        g.setFont( oldFont );
        fm = g.getFontMetrics();
        
        Line2D.Double lineAttrDivider = new Line2D.Double(currentBounds.getX(), y, currentBounds.getMaxX(), y);
        Line2D.Double lineOpDivider = new Line2D.Double(currentBounds.getX(), y, currentBounds.getMaxX(), y);
        
        if ( fOpt.isShowAttributes() ) {
            // compartment divider
        	lineAttrDivider.y1 = y;
        	lineAttrDivider.y2 = y;
        	
        	if (fAttributes.isEmpty()) {
        		y += 2 * VERTICAL_INDENT;
        	} else {
	        	Rectangle2D.Double attributeBounds = new Rectangle2D.Double(currentBounds.x, y, currentBounds.width, currentBounds.height);
	        	
	        	if (fOpt.isShowOperations()){
	        		if(!cls().operations().isEmpty()){
	        			attributeBounds.height = currentBounds.getMaxY() - y - VERTICAL_INDENT - Util.getLineHeight(fm);
	        		} else {
	        			attributeBounds.height = currentBounds.getMaxY() - y - VERTICAL_INDENT - 2*Util.getLeading(fm);
	        		}
	        	}
	        	
	            y = drawCompartment(g, y, fAttrValues, fAttrColors, attributeBounds);
        	}
        }
        
        if ( fOpt.isShowOperations() ) {
        	// compartment divider
        	lineOpDivider.y1 = y;
        	lineOpDivider.y2 = y;

            y = drawCompartment(g, y, fOprSignatures, fOperationColors, currentBounds);
        }
        
        g.setColor( fOpt.getNODE_FRAME_COLOR() );
        g.draw( currentBounds );
        
        if ( fOpt.isShowAttributes() ) {
        	g.draw(lineAttrDivider);
        }
        if ( fOpt.isShowOperations() ) {
        	g.draw(lineOpDivider);
        }
    }
    
    @Override
    public boolean hasAttributes() {
    	return !fAttributes.isEmpty();
    }
    
    @Override
    public boolean hasOperations() {
    	return !fOperations.isEmpty();
    }
    
    @Override
    public String toString() {
    	return cls().name() + "(ClassNode)";
    }
    
    @Override
    protected String getStoreType() {
    	return "Class";
    }
}
