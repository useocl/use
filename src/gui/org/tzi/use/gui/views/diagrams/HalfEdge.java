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

import java.awt.FontMetrics;
import java.awt.Graphics;

import org.tzi.use.uml.mm.MAssociationEnd;

/**
 * An edge being part of a nary edge. This edge connects an node with a
 * DiamondNode in case of an nary edge, or an Object/ClassNode with a 
 * PseudoNode in case of an association class.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class HalfEdge extends EdgeBase {
    
    /**
     * Constructs a new edge. source is a pseude-node, target is a node.
     */
    public HalfEdge( NodeBase source, NodeBase target, MAssociationEnd assocEnd,
                     DiagramView diagram ) {
        super( source, target, assocEnd.association().name(),
               diagram, assocEnd.association() );
        
        fTargetRolename = new Rolename( assocEnd, target, source, 
                                        fX2, fY2, fX1, fY1,
                                        fOpt, Rolename.TARGET_SIDE, this );
        
        fTargetMultiplicity = new Multiplicity( assocEnd, target,
                                                source, this,
                                                fX2, fY2, fX1, fY1, fOpt,
                                                Multiplicity.TARGET_SIDE );
    }

    public void draw( Graphics g, FontMetrics fm ) {
        if ( isSelected() ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getEDGE_COLOR() );
        }
        drawEdge( g );
        
        if ( isSelected() ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getEDGE_LABEL_COLOR() );    
        }
        if ( fOpt.isShowRolenames() ) {
            fTargetRolename.draw( g, fm );
        }
        
        if ( fOpt.isShowMutliplicities() ) {
            fTargetMultiplicity.draw( g, fm );
        }
        g.setColor( fOpt.getEDGE_COLOR() );
    }
}

