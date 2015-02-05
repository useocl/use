/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

package org.tzi.use.gui.views.diagrams.behavior.communicationdiagram;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.tzi.use.gui.views.diagrams.behavior.sequencediagram.CancelButton;
import org.tzi.use.gui.views.diagrams.behavior.sequencediagram.OKButton;

/**
 * @author Antje Werner
 * @author Thomas Schaefer
 * 
 *         A window for choosing the commands to show in the communication diagram.
 *         By default only operation calls are diagramed,but it is also possible
 *         to diagram create-, destroy-, insert-, delete- and/or set-commands.
 */

public class CmdChooseWindowComDiag extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A checkbox which contains the possible constructions (create, destroy,
	 * set, insert, delete).
	 */
	private JCheckBox checkBoxes[] = new JCheckBox[5];

	/**
	 * The container for this Window.
	 */
	private Container contentContainer;

	/**
	 * The {@link CommunicationDiagram} from which this window is called.
	 */
	private CommunicationDiagram fComDiag;

	/**
	 * Constructs an new CmdChooseWindow.
	 * 
	 */
	
	private boolean[] filter = new boolean[5];
	
	public CmdChooseWindowComDiag(CommunicationDiagram comDiag) {
		setTitle("Choose Commands...");
		fComDiag = comDiag;
		contentContainer = getContentPane();
		contentContainer.setLayout(new BorderLayout(20, 10));

		JLabel label1 = new JLabel("");
		JLabel label2 = new JLabel("");
		JLabel label3 = new JLabel("");
		JPanel checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new GridLayout(3, 2, 20, 0));

		checkBoxes[0] = new JCheckBox("Create", fComDiag.isCreateSelected());
		checkBoxes[1] = new JCheckBox("Destroy",fComDiag.isDestroySelected());
		checkBoxes[2] = new JCheckBox("Insert",fComDiag.isInsertSelected());
		checkBoxes[3] = new JCheckBox("Delete",fComDiag.isDeleteSelected());
		checkBoxes[4] = new JCheckBox("Set", fComDiag.isSetSelected());

		for (int i = 0; i < checkBoxes.length; i++) {
			checkBoxPanel.add(checkBoxes[i]);
		}

		JPanel buttonsPanel = new JPanel();
		JButton okButton = new OKButton("Ok");
		okButton.addActionListener(this);
		buttonsPanel.add(okButton);
		JButton cancelButton = new CancelButton("Cancel");
		cancelButton.addActionListener(this);
		buttonsPanel.add(cancelButton);
		contentContainer.add("East", label1);
		contentContainer.add("West", label2);
		contentContainer.add("North", label3);
		contentContainer.add("Center", checkBoxPanel);
		contentContainer.add("South", buttonsPanel);
		pack();
	}

	/**
	 * Shows the setup dialog for sequence diagram.
	 * 
	 */
	void showWindow() {
		CmdChooseWindowComDiag cmdChooseW = new CmdChooseWindowComDiag(fComDiag);
		cmdChooseW.setSize(200, 150);
		cmdChooseW.setLocation(300, 200);
		cmdChooseW.setVisible(true);
	}

	/**
	 * Analyses the entries made in the window.
	 */
	public void actionPerformed(ActionEvent e) {
		if ("Ok".equals(e.getActionCommand())) {
			filter[0] = !checkBoxes[0].isSelected();
			filter[1] = !checkBoxes[1].isSelected();
			filter[2] = !checkBoxes[2].isSelected();
			filter[3] = !checkBoxes[3].isSelected();
			filter[4] = !checkBoxes[4].isSelected();
			fComDiag.filterGraphByEvent(filter);
			dispose();
		}
		if ("Cancel".equals(e.getActionCommand())) {
			dispose();
		}

	}

}
