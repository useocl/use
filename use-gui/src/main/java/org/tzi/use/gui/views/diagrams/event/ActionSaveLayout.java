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
import javax.swing.JOptionPane;

import org.tzi.use.config.Options;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.util.ExtFileFilter;
import org.tzi.use.gui.views.diagrams.DiagramView;

/**
 * Saves the current layout to a file.
 * 
 * @author Fabian Gutsche
 */
@SuppressWarnings("serial")
public class ActionSaveLayout extends AbstractAction {

    private String fTitle = "";
    private String fAppendix = "";
    private DiagramView fDiagram;

    private Path lastFile = null;
    
    public ActionSaveLayout( String title, String appendix, DiagramView diagram ) {
        super("Save layout...");
        fTitle = title;
        fAppendix = appendix;
        fDiagram = diagram;
    }
    
    public void actionPerformed(ActionEvent e) {        
        int option = JOptionPane.YES_OPTION;

		JFileChooser fChooser = new JFileChooser(Options.getLastDirectory().toFile());
		ExtFileFilter filter = new ExtFileFilter(fAppendix, fTitle);
		fChooser.setFileFilter(filter);
		fChooser.setDialogTitle("Save layout");
        
        if (   lastFile != null 
        	&& Files.exists(lastFile)
			&& lastFile.getParent().equals(Options.getLastDirectory())) {
			
        	fChooser.setSelectedFile(lastFile.toFile());
		}
        
        do {
            int returnVal = fChooser.showSaveDialog( MainWindow.instance() );
            if (returnVal != JFileChooser.APPROVE_OPTION)
                return;

            Options.setLastDirectory(fChooser.getCurrentDirectory().toPath());
            String filename = fChooser.getSelectedFile().getName();

            // if file does not have the appendix .olt or .clt at the appendix
            if (!filename.endsWith("." + fAppendix)) {
                filename += "." + fAppendix;
            }

            lastFile = Options.getLastDirectory().resolve(filename);
            
            if (Files.exists(lastFile)) {
                option = JOptionPane.showConfirmDialog(MainWindow.instance(),
                        "Overwrite existing file " + lastFile + "?",
                        "Please confirm", JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                }

            }
            // display the saving dialog, as long as the file
            // will be overwritten or cancel is pressed.
        } while (option != JOptionPane.YES_OPTION);

        fDiagram.saveLayout(lastFile);
    }

}