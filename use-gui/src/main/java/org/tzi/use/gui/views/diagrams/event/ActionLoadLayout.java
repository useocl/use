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
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import org.tzi.use.config.Options;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.util.ExtFileFilter;
import org.tzi.use.gui.views.diagrams.DiagramView;

/**
 * Loads the current layout from a file.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
@SuppressWarnings("serial")
public class ActionLoadLayout extends AbstractAction {
	
    private String fTitle = "";
    private String fAppendix = "";
    private DiagramView fDiagram;
    
    private Path lastFile = null;
    
    public ActionLoadLayout(String title, String appendix, DiagramView diagram) {
        super("Load layout...");
        fTitle = title;
        fAppendix = appendix;        
        fDiagram = diagram;
    }

    public void actionPerformed(ActionEvent e) {
        // reuse chooser if possible
    	JFileChooser fileChooser;
    	
        fileChooser = new JFileChooser(Options.getLastDirectory().toFile());
        
        ExtFileFilter filter = new ExtFileFilter( fAppendix, fTitle );
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Load layout");
        
		if (   lastFile != null 
			&& Files.isReadable(lastFile)
			&& lastFile.getParent().equals(Options.getLastDirectory())) {
			fileChooser.setSelectedFile(lastFile.toFile());
		}
        
        int returnVal = fileChooser.showOpenDialog( MainWindow.instance() );
        if (returnVal != JFileChooser.APPROVE_OPTION)
            return;

        Options.setLastDirectory(fileChooser.getCurrentDirectory().toPath());
        lastFile = fileChooser.getSelectedFile().toPath();

        fDiagram.loadLayout(lastFile);
    }
 
}