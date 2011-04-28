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
import org.tzi.use.gui.views.diagrams.AssociationOrLinkPartEdge;
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
@SuppressWarnings("serial")
public class ActionSaveLayout extends AbstractAction {
	private static String LAST_PATH = "";
	
	private JFileChooser fChooser;
    private String fTitle = "";
    private String fAppendix = "";
    private DirectedGraph<NodeBase, EdgeBase> fGraph;
    private DiagramOptions fOpt;
    private PrintWriter fLog;
    
    private LayoutInfos fLayoutInfos;
    
    public ActionSaveLayout( String title, String appendix, DirectedGraph<NodeBase, EdgeBase> graph,
                             DiagramOptions opt, Properties properties,
                             PrintWriter log ) {
        super("Save layout...");
        fTitle = title;
        fAppendix = appendix;
        
        fGraph = graph;
        fOpt = opt;
        fLog = log;
    }
    
    public ActionSaveLayout( String title, String appendix, DirectedGraph<NodeBase, EdgeBase> graph,
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
        StringBuilder xml = new StringBuilder();
        
        int option = JOptionPane.YES_OPTION;
        File f = null;
        do {
            // reuse chooser if possible
            if (fChooser == null) {
                fChooser = new JFileChooser(ActionSaveLayout.LAST_PATH);
                ExtFileFilter filter = 
                    new ExtFileFilter( fAppendix, fTitle );
                fChooser.addChoosableFileFilter(filter);
                fChooser.setDialogTitle("Save layout");
            }
            int returnVal = fChooser.showSaveDialog( new JPanel() );
            if (returnVal != JFileChooser.APPROVE_OPTION)
                return;

            ActionSaveLayout.LAST_PATH = fChooser.getCurrentDirectory().toString();
            String filename = fChooser.getSelectedFile().getName();

            // if file does not have the appendix .olt or .clt at the appendix
            int dot = filename.lastIndexOf(".");
            if (dot == -1
                || !filename.substring(dot, 
                                       filename.length()).trim()
                                       .equals( "." + fAppendix )) {
                filename += "." + fAppendix;
            }

            f = new File(ActionSaveLayout.LAST_PATH, filename);
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

        // TODO: Change to XmlDocument and create nodes, instead of writing a string
 
        // save diagram options
        xml.append("<diagramOptions>" + LayoutTags.NL);
        xml.append(LayoutTags.INDENT);
        xml.append(LayoutTags.AUTOLAYOUT_O);
        xml.append(fOpt.isDoAutoLayout());
        xml.append(LayoutTags.AUTOLAYOUT_C);
        xml.append(LayoutTags.NL);  
        xml.append(LayoutTags.INDENT);
        xml.append(LayoutTags.ANTIALIASING_O); 
        xml.append(fOpt.isDoAntiAliasing());
        xml.append(LayoutTags.ANTIALIASING_C);
        xml.append(LayoutTags.NL);  
        xml.append(LayoutTags.INDENT);
        xml.append(LayoutTags.SHOWASSOCNAMES_O);
        xml.append(fOpt.isShowAssocNames());
        xml.append(LayoutTags.SHOWASSOCNAMES_C);
        xml.append(LayoutTags.NL);
        xml.append(LayoutTags.INDENT);
        xml.append(LayoutTags.SHOWATTRIBUTES_O);
        xml.append(fOpt.isShowAttributes());
        xml.append(LayoutTags.SHOWATTRIBUTES_C);
        xml.append(LayoutTags.NL);  
        xml.append(LayoutTags.INDENT);
        xml.append(LayoutTags.SHOWMULTIPLICITIES_O); 
        xml.append(fOpt.isShowMutliplicities() );
        xml.append(LayoutTags.SHOWMULTIPLICITIES_C);
        xml.append(LayoutTags.NL);
        xml.append(LayoutTags.INDENT);
        xml.append(LayoutTags.SHOWOPERATIONS_O);
        xml.append(fOpt.isShowOperations());
        xml.append(LayoutTags.SHOWOPERATIONS_C);
        xml.append(LayoutTags.NL);  
        xml.append(LayoutTags.INDENT);
        xml.append(LayoutTags.SHOWROLENAMES_O);
        xml.append(fOpt.isShowRolenames());
        xml.append(LayoutTags.SHOWROLENAMES_C);
        xml.append(LayoutTags.NL);
        xml.append("</diagramOptions>");
        xml.append(LayoutTags.NL);
        xml.append(LayoutTags.NL);
        
        // store node positions in property object
        Iterator<NodeBase> nodeIterator = fGraph.iterator();
        while (nodeIterator.hasNext()) {
            NodeBase n = nodeIterator.next();
            xml.append(n.storePlacementInfo( false ));
            xml.append(LayoutTags.NL);
        }

        // store EdgePropertie positions in property object
        Iterator<EdgeBase> edgeIterator = fGraph.edgeIterator();
        while ( edgeIterator.hasNext() ) {
            EdgeBase edge = edgeIterator.next();
            if ( edge instanceof AssociationOrLinkPartEdge ) {
                continue;
            }
            xml.append(edge.storePlacementInfo( false ));
            xml.append(LayoutTags.NL);
        }
        
        xml.append(LayoutTags.NL);
        xml.append(fLayoutInfos.getHiddenElementsXML());
        
        XMLParserAccess xmlParser = new XMLParserAccessImpl();
        xmlParser.saveXMLFile( f, xml.toString() );
        fLog.println("Wrote layout file " + f);
    }

}