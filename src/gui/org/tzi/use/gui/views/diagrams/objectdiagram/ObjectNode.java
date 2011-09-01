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

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.util.List;

import org.tzi.use.gui.main.ModelBrowserSorting;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeEvent;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeListener;
import org.tzi.use.gui.views.diagrams.DiagramOptionChangedListener;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.value.EnumValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;

/**
 * A node representing an object.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class ObjectNode extends NodeBase implements SortChangeListener {

    private DiagramOptions fOpt;
    private NewObjectDiagramView fParent;
    private MObject fObject;
    private String fLabel;
    private List<MAttribute> fAttributes;
    private String[] fValues;
    
    protected Rectangle2D.Double nameRect = new Rectangle2D.Double();
    protected Rectangle2D.Double attributesRect = new Rectangle2D.Double();
    
    public ObjectNode( MObject obj, NewObjectDiagramView parent, 
                       DiagramOptions opt ) {
        fObject = obj;
        fParent = parent;
        fOpt = opt;
        this.fOpt.addOptionChangedListener(new DiagramOptionChangedListener() {
			@Override
			public void optionChanged(String optionname) {
				if (optionname.equals("SHOWROLENAMES") ||
					optionname.equals("SHOWATTRIBUTES")	)
				calculateBounds();
			}
		});
        
        MClass cls = obj.cls();
        fLabel = obj.name() + ":" + cls.name();
        List<MAttribute> allAttributes = cls.allAttributes();
        final int N = allAttributes.size();
        fValues = new String[N];

        fAttributes = ModelBrowserSorting.getInstance()
                                            .sortAttributes( allAttributes );
        ModelBrowserSorting.getInstance().addSortChangeListener( this );
    }

    public MObject object() {
        return fObject;
    }
    
    public MClass cls() {
        return fObject.cls();
    }
    
    public String name() {
        return fObject.name();
    }


    /**
     * After the occurence of an event the attribute list is updated.
     */
    public void stateChanged( SortChangeEvent e ) {
        fAttributes = 
            ModelBrowserSorting.getInstance().sortAttributes( fAttributes );
    }

    /**
     * Sets the correct size of the width and height of this object node.
     * This method needs to be called before actually drawing the node.
     * (Width and height are needed from other methods before the nodes are
     * drawn.)
     */
    public void setRectangleSize( Graphics2D g ) {
        FontMetrics fm = g.getFontMetrics();
        
        nameRect.width = fm.stringWidth( fLabel );
        nameRect.height =  fm.getHeight();

        String value;
        
        MObjectState objState = fObject.state( fParent.system().state() );
        for ( int i = 0; i < fAttributes.size(); i++ ) {
            MAttribute attr = (MAttribute) fAttributes.get( i );
            Value val = (Value) objState.attributeValue( attr );
            
            if (val instanceof EnumValue) {
            	value = "#" + ((EnumValue)val).value();
            } else {
            	value = val.toString();
            }
            
            fValues[i] = attr.name() + "=" + value; 
            
            attributesRect.width = Math.max( attributesRect.width, fm.stringWidth( fValues[i] ) );
        }
        
        attributesRect.height = fm.getHeight() * fAttributes.size() + 3;
        calculateBounds();
    }

	protected void calculateBounds() {
		double width = nameRect.width;
		double height = nameRect.height;
		
		if (fOpt.isShowAttributes()) {
			width = Math.max(width, attributesRect.width);
			height += attributesRect.height;
		}
		
		height += 4;
		width += 10;
		
        height = Math.max(height, getMinHeight());
        width = Math.max(width, getMinWidth());
        
		bounds.width = width;
		bounds.height = height;
	}


    public boolean isDeletable() {
        return true;
    }

    /**
     * Draws a box with an underlined label.
     */
    @Override
    protected void onDraw( Graphics2D g ) {
        int x = (int) getX();
        int y = (int) getY();

        Polygon dimension = dimension();
        Rectangle2D bounds = getBounds();
        
        int labelWidth = g.getFontMetrics().stringWidth( fLabel );

        if ( isSelected() ) {
            g.setColor( fOpt.getNODE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getNODE_COLOR() );
        }
        g.fillPolygon( dimension );
        g.setColor( fOpt.getNODE_FRAME_COLOR() );
        g.drawPolygon( dimension );

        x = (int)bounds.getCenterX() - labelWidth / 2;
        y = (int)bounds.getY() + g.getFontMetrics().getAscent() + 2;
        g.setColor( fOpt.getNODE_LABEL_COLOR() );
        g.drawString( fLabel, x, y );
        // underline label
        g.drawLine( x, y + 1, x + labelWidth, y + 1 );

        if ( fOpt.isShowAttributes() ) {
            // compartment divider
            x = (int) getBounds().getCenterX();
            g.drawLine( (int)bounds.getX(), y + 3, (int)bounds.getMaxX() - 1, y + 3 );
            x -= ( getWidth() - 10 ) / 2;
            y += 3;
            for ( int i = 0; i < fAttributes.size(); i++ ) {
                y += g.getFontMetrics().getHeight();
                g.drawString( fValues[i], x, y );
            }
        }
    }

    public String ident() {
        return "Object." + fObject.name();
    }
    public String identNodeEdge() {
        return "OjectLink." + fObject.name();
    }

    @Override
    protected String getStoreType() {
    	return "Object";
    }
}
