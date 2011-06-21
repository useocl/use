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
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.gui.util.ExtFileFilter;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.LayoutInfos;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Loads the current layout from a file.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
@SuppressWarnings("serial")
public class ActionLoadLayout extends AbstractAction {
    
	private static String LAST_PATH = "";
	private JFileChooser fChooser;
    private String fTitle = "";
    private String fAppendix = "";
    private DiagramView fDiagram;
    private HideAdministration fHideAdmin;
    private LayoutInfos fLayoutInfos;
    
    public ActionLoadLayout( String title, String appendix, DiagramView diagram,
                             PrintWriter log, DiagramOptions opt, 
                             HideAdministration hideAdmin, DirectedGraph<NodeBase, EdgeBase> graph ) {
        super("Load layout...");
        fTitle = title;
        fAppendix = appendix;
        
        fDiagram = diagram;
        fHideAdmin = hideAdmin;
    }

    public ActionLoadLayout( String title, String appendix,
                             DiagramView diagram, PrintWriter log,
                             HideAdministration hideAdmin, DirectedGraph<NodeBase, EdgeBase> graph,
                             LayoutInfos layoutInfos ) {
        super("Load layout...");
        fTitle = title;
        fAppendix = appendix;
        fLayoutInfos = layoutInfos;
        fDiagram = diagram;
        fHideAdmin = hideAdmin;
    }
    
    public void actionPerformed(ActionEvent e) {
        // reuse chooser if possible
        if (fChooser == null) {
            fChooser = new JFileChooser(ActionLoadLayout.LAST_PATH);
            ExtFileFilter filter = new ExtFileFilter( fAppendix, fTitle );
            fChooser.addChoosableFileFilter(filter);
            fChooser.setDialogTitle("Load layout");
        }
        int returnVal = fChooser.showOpenDialog( new JPanel() );
        if (returnVal != JFileChooser.APPROVE_OPTION)
            return;

        ActionLoadLayout.LAST_PATH = fChooser.getCurrentDirectory().toString();
        File f = fChooser.getSelectedFile();
        
        // show all hidden nodes and edges. This is necessary, if 
        // nodes are hidden but a layout will be loaded which 
        // contains no hidden nodes or edges.
        if ( fHideAdmin != null ) {
            fHideAdmin.showAllHiddenElements();
        }
        
        fLayoutInfos.resetNodesOnEdges();
                
        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document doc;
        
        try {
        	docBuilder = fact.newDocumentBuilder();
			doc = docBuilder.parse(f);
		} catch (ParserConfigurationException e1) {
			JOptionPane.showMessageDialog(fChooser, e1.getMessage());
			return;
		} catch (SAXException e1) {
			JOptionPane.showMessageDialog(fChooser, e1.getMessage());
			return;
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(fChooser, e1.getMessage());
			return;
		}
		
		Element rootElement = (Element)doc.getDocumentElement();
		Element layoutElement = (Element)rootElement.getElementsByTagName("diagramOptions").item(0);
		fLayoutInfos.getOpt().loadOptions(layoutElement);
		
		fDiagram.restorePositionData(rootElement);
        fDiagram.invalidateContent();
    }
 
}