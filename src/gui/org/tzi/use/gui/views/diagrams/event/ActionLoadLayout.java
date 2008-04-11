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

package org.tzi.use.gui.views.diagrams.event;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.PrintWriter;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.gui.util.ExtFileFilter;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.LayoutInfos;
import org.tzi.use.gui.xmlparser.XMLParserAccess;
import org.tzi.use.gui.xmlparser.XMLParserAccessImpl;
import org.tzi.use.util.Log;

/**
 * Loads the current layout from a file.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class ActionLoadLayout extends AbstractAction {
    private JFileChooser fChooser;
    private String fTitle = "";
    private String fAppendix = "";
    private DiagramView fDiagram;
    private PrintWriter fLog;
    private HideAdministration fHideAdmin;
    private LayoutInfos fLayoutInfos;
    
    public ActionLoadLayout( String title, String appendix, DiagramView diagram,
                             PrintWriter log, DiagramOptions opt, 
                             HideAdministration hideAdmin, DirectedGraph graph ) {
        super("Load layout...");
        fTitle = title;
        fAppendix = appendix;
        
        fDiagram = diagram;
        fLog = log;
        fHideAdmin = hideAdmin;
    }

    public ActionLoadLayout( String title, String appendix,
                             DiagramView diagram, PrintWriter log,
                             HideAdministration hideAdmin, DirectedGraph graph,
                             LayoutInfos layoutInfos ) {
        super("Load layout...");
        fTitle = title;
        fAppendix = appendix;
        fLayoutInfos = layoutInfos;
        fDiagram = diagram;
        fLog = log;
        fHideAdmin = hideAdmin;

        //        fGraph = graph;
    }
    
    public void actionPerformed(ActionEvent e) {
        String path;
        // reuse chooser if possible
        if (fChooser == null) {
            path = System.getProperty("user.dir");
            fChooser = new JFileChooser(path);
            ExtFileFilter filter = new ExtFileFilter( fAppendix, fTitle );
            fChooser.addChoosableFileFilter(filter);
            fChooser.setDialogTitle("Load layout");
        }
        int returnVal = fChooser.showOpenDialog( new JPanel() );
        if (returnVal != JFileChooser.APPROVE_OPTION)
            return;

        path = fChooser.getCurrentDirectory().toString();
        File f = new File(path, fChooser.getSelectedFile().getName());
        Log.verbose("File " + f);

        //show all hidden nodes and edges. This is nesseccary, if 
        // nodes are hidden but a layout will be loaded which 
        // contains no hidden nodes or edges.
        if ( fHideAdmin != null ) {
            fHideAdmin.showAllHiddenElements();
        }
        
        fLayoutInfos.resetNodesOnEdges();
        fLog.println("Reading layout file " + f);
        XMLParserAccess xmlParser = new XMLParserAccessImpl( fLayoutInfos );
        xmlParser.loadXMLFile( f, true );
        fDiagram.deleteHiddenElementsFromDiagram( fLayoutInfos.getHiddenNodes(),
                                                  fLayoutInfos.getHiddenEdges() );
        
        fDiagram.repaint();
    }
 
}