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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.gui.views.diagrams.event;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.gui.util.ExtFileFilter;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.HalfEdge;
import org.tzi.use.gui.views.diagrams.LayoutInfos;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.gui.xmlparser.XMLParserAccess;
import org.tzi.use.gui.xmlparser.XMLParserAccessImpl;
import org.tzi.use.util.Log;

/**
 * Saves the current layout to a file.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class ActionSaveLayout extends AbstractAction {
    private JFileChooser fChooser;
    private String fTitle = "";
    private String fAppendix = "";
    private DirectedGraph fGraph;
    private DiagramOptions fOpt;
    private PrintWriter fLog;
    
    private LayoutInfos fLayoutInfos;
    
    public ActionSaveLayout( String title, String appendix, DirectedGraph graph,
                             DiagramOptions opt, Properties properties,
                             PrintWriter log ) {
        super("Save layout...");
        fTitle = title;
        fAppendix = appendix;
        
        fGraph = graph;
        fOpt = opt;
        fLog = log;
    }
    
    public ActionSaveLayout( String title, String appendix, DirectedGraph graph,
                             PrintWriter log, LayoutInfos layoutInfos ) {
        super("Save layout...");
        fTitle = title;
        fAppendix = appendix;
        
        fGraph = graph;
        fOpt = layoutInfos.getOpt();
        fLog = log;
        fLayoutInfos = layoutInfos;
    }
    
    
    public void actionPerformed(ActionEvent e) {
        String path;
        String xml = "";
        
        int option = JOptionPane.YES_OPTION;
        File f = null;
        do {
            // reuse chooser if possible
            if (fChooser == null) {
                path = System.getProperty("user.dir");
                fChooser = new JFileChooser(path);
                ExtFileFilter filter = 
                    new ExtFileFilter( fAppendix, fTitle );
                fChooser.addChoosableFileFilter(filter);
                fChooser.setDialogTitle("Save layout");
            }
            int returnVal = fChooser.showSaveDialog( new JPanel() );
            if (returnVal != JFileChooser.APPROVE_OPTION)
                return;

            path = fChooser.getCurrentDirectory().toString();
            String filename = fChooser.getSelectedFile().getName();

            // if file does not have the appendix .olt or .clt at the appendix
            int dot = filename.lastIndexOf(".");
            if (dot == -1
                || !filename.substring(dot, 
                                       filename.length()).trim()
                                       .equals( "." + fAppendix )) {
                filename += "." + fAppendix;
            }

            f = new File(path, filename);
            Log.verbose("File " + f);

            if (f.exists()) {
                option = JOptionPane.showConfirmDialog(new JPanel(),
                        "Overwrite existing file " + f + "?",
                        "Please confirm", JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                }

            }
            // display the saving dialog, as long as the file
            // will be overwritten or cancel is pressed.
        } while (option != JOptionPane.YES_OPTION);

        // save diagram options
        xml += "<diagramOptions>" + LayoutTags.NL;
        xml += LayoutTags.INDENT + LayoutTags.AUTOLAYOUT_O 
               + Boolean.toString( fOpt.isDoAutoLayout() ) + LayoutTags.AUTOLAYOUT_C 
               + LayoutTags.NL;  
        xml += LayoutTags.INDENT + LayoutTags.ANTIALIASING_O 
               + Boolean.toString( fOpt.isDoAntiAliasing() ) + LayoutTags.ANTIALIASING_C
               + LayoutTags.NL;  
        xml += LayoutTags.INDENT + LayoutTags.SHOWASSOCNAMES_O
               + Boolean.toString( fOpt.isShowAssocNames() ) + LayoutTags.SHOWASSOCNAMES_C
               + LayoutTags.NL;  
        xml += LayoutTags.INDENT + LayoutTags.SHOWATTRIBUTES_O 
               + Boolean.toString( fOpt.isShowAttributes() ) + LayoutTags.SHOWATTRIBUTES_C 
               + LayoutTags.NL;  
        xml += LayoutTags.INDENT + LayoutTags.SHOWMULTIPLICITIES_O 
               + Boolean.toString( fOpt.isShowMutliplicities() ) + LayoutTags.SHOWMULTIPLICITIES_C 
               + LayoutTags.NL;  
        xml += LayoutTags.INDENT + LayoutTags.SHOWOPERATIONS_O
               + Boolean.toString( fOpt.isShowOperations() ) + LayoutTags.SHOWOPERATIONS_C 
               + LayoutTags.NL;  
        xml += LayoutTags.INDENT + LayoutTags.SHOWROLENAMES_O
               + Boolean.toString( fOpt.isShowRolenames() ) + LayoutTags.SHOWROLENAMES_C
               + LayoutTags.NL;  
        xml += "</diagramOptions>" + LayoutTags.NL + LayoutTags.NL;
        
        // store node positions in property object
        Iterator nodeIterator = fGraph.iterator();
        while (nodeIterator.hasNext()) {
            NodeBase n = (NodeBase) nodeIterator.next();
            xml += n.storePlacementInfo( false );
            xml += LayoutTags.NL;
        }

        // store EdgePropertie positions in property object
        Iterator edgeIterator = fGraph.edgeIterator();
        while ( edgeIterator.hasNext() ) {
            EdgeBase edge = (EdgeBase) edgeIterator.next();
            if ( edge instanceof HalfEdge ) {
                continue;
            }
            xml += edge.storePlacementInfo( false );
            xml += LayoutTags.NL;
        }
        
        xml += LayoutTags.NL + fLayoutInfos.getHiddenElementsXML();
        
        XMLParserAccess xmlParser = new XMLParserAccessImpl();
        xmlParser.saveXMLFile( f, xml );
        fLog.println("Wrote layout file " + f);
    }

}