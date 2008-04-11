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

$Id$

package org.tzi.use.gui.xmlparser;

/**
 * This class provieds just the tags for categorysing movies in an xml
 * file.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author      Fabian Gutsche 
 */
public class LayoutTags {

    // XML tags
    public static final String AUTOLAYOUT_O = "<autolayout>";
    public static final String AUTOLAYOUT_C = "</autolayout>";
    public static final String ANTIALIASING_O = "<antialiasing>";
    public static final String ANTIALIASING_C = "</antialiasing>";
    public static final String SHOWASSOCNAMES_O = "<showassocnames>";
    public static final String SHOWASSOCNAMES_C = "</showassocnames>";
    public static final String SHOWROLENAMES_O = "<showrolenames>";
    public static final String SHOWROLENAMES_C = "</showrolenames>";
    public static final String SHOWMULTIPLICITIES_O = "<showmultiplicities>";
    public static final String SHOWMULTIPLICITIES_C = "</showmultiplicities>";
    public static final String SHOWATTRIBUTES_O = "<showattributes>";
    public static final String SHOWATTRIBUTES_C = "</showattributes>";
    public static final String SHOWOPERATIONS_O = "<showoperations>";
    public static final String SHOWOPERATIONS_C = "</showoperations>";
    
    public static final String NAME_O = "<name>";
    public static final String NAME_C = "</name>";
    public static final String X_COORD_O = "<x_coord>";
    public static final String X_COORD_C = "</x_coord>";
    public static final String Y_COORD_O = "<y_coord>";
    public static final String Y_COORD_C = "</y_coord>";
    public static final String HIDDEN_O = "<hidden>";
    public static final String HIDDEN_C = "</hidden>";
    public static final String SOURCE_O = "<source>";
    public static final String SOURCE_C = "</source>";
    public static final String TARGET_O = "<target>";
    public static final String TARGET_C = "</target>";
    public static final String CON_NODE_O = "<connectedNode>";
    public static final String CON_NODE_C = "</connectedNode>";
    public static final String ID_O = "<id>";
    public static final String ID_C = "</id>";
    public static final String SPECIALID_O = "<specialid>";
    public static final String SPECIALID_C = "</specialid>";
    

    public static final String NODE_O = "<node";
    public static final String NODE_C = "</node>";
    public static final String EDGE_O = "<edge";
    public static final String EDGE_C = "</edge>";
    public static final String EDGEPROPERTY_O = "<edgeproperty";
    public static final String EDGEPROPERTY_C = "</edgeproperty>";
    
    
    // diagram options tags
    public static final String AUTOLAYOUT = "autolayout";
    public static final String ANTIALIASING = "antialiasing";
    public static final String SHOWASSOCNAMES = "showassocnames";
    public static final String SHOWROLENAMES = "showrolenames";
    public static final String SHOWMULTIPLICITIES = "showmultiplicities";
    public static final String SHOWATTRIBUTES = "showattributes";
    public static final String SHOWOPERATIONS = "showoperations";
    
    // node and edge tags
    public static final String NAME = "name";
    public static final String X_COORD = "x_coord";
    public static final String Y_COORD = "y_coord";
    public static final String HIDDEN = "hidden";
    public static final String SOURCE = "source";
    public static final String TARGET = "target";
    public static final String CON_NODE = "connectedNode";
    public static final String ID = "id";
    public static final String SPECIALID = "specialid";
    
    
    // kind tag
    public static final String NODE= "node";
    public static final String EDGE = "edge";
    public static final String EDGEPROPERTY = "edgeproperty";
    
    // open closing tags
    public static final String OPEN_TAG = "<diagram_Layout> \n\n";
    public static final String CLOSE_TAG = "</diagram_Layout>";
    
    public static final String NL = "\n";
    public static final String INDENT = "   ";
    
    // type identifier
    public static final String CLASS = "Class";
    public static final String OBJECT = "Object";
    public static final String DIAMONDNODE = "DiamondNode";
    public static final String ENUMERATION = "Enumeration";
    
    public static final String EDGEBASE = "EdgeBase";
    public static final String BINARYEDGE = "BinaryEdge";
    public static final String HALFEDGE = "HalfEdge";
    public static final String EDGENODE = "NodeEdge";
    public static final String INHERITANCE = "Inheritance";
    
    public static final String ROLENAME = "rolename";
    public static final String MULTIPLICITY = "multiplicity";
    public static final String ASSOCNAME = "associationName";
    public static final String NODEONEDGE = "NodeOnEdge";
    
    public static final String LINK = "link";
    public static final String ASSOCIATION = "association";
    
    
    /**
     * This class should not be instanciable.
     */
    private LayoutTags(){}
    
}

