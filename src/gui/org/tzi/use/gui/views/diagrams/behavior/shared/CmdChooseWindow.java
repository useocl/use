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

package org.tzi.use.gui.views.diagrams.behavior.shared;

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

/**
 * @author Antje Werner
 * @author Thomas Schaefer
 * @author Carsten Schlobohm
 * 
 *         A window for choosing the commands to show in the communication diagram.
 *         By default only operation calls are diagramed,but it is also possible
 *         to diagram create-, destroy-, insert-, delete- and/or set-commands.
 */
public class CmdChooseWindow extends JDialog implements ActionListener {
	public interface CmdChooseWindowDelegate {
		boolean isCreateSelected();
		boolean isDestroySelected();
		boolean isInsertSelected();
		boolean isDeleteSelected();
		boolean isAssignSelected();
		void setCreateVisible(boolean selected);
		void setDestroyVisible(boolean selected);
		void setInsertVisible(boolean selected);
		void setDeleteVisible(boolean selected);
		void setAssignVisible(boolean selected);
		void filterGraphByEvent();
	}
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

	private CmdChooseWindowDelegate delegate;

	/**
	 * Constructs an new CmdChooseWindow.
	 * 
	 */
	public CmdChooseWindow(CmdChooseWindowDelegate delegate) {
		setTitle("Choose Commands...");
		this.delegate = delegate;
		contentContainer = getContentPane();
		contentContainer.setLayout(new BorderLayout(20, 10));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JLabel label1 = new JLabel("");
		JLabel label2 = new JLabel("");
		JLabel label3 = new JLabel("");
		JPanel checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new GridLayout(3, 2, 20, 0));

		checkBoxes[0] = new JCheckBox("Create", delegate.isCreateSelected());
		checkBoxes[1] = new JCheckBox("Destroy",delegate.isDestroySelected());
		checkBoxes[2] = new JCheckBox("Insert",delegate.isInsertSelected());
		checkBoxes[3] = new JCheckBox("Delete",delegate.isDeleteSelected());
		checkBoxes[4] = new JCheckBox("Set", delegate.isAssignSelected());

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
	public void showWindow() {
		this.setSize(200, 150);
		this.setLocation(300, 200);
		this.setVisible(true);
	}

	/**
	 * Analyses the entries made in the window.
	 */
	public void actionPerformed(ActionEvent e) {
		if ("Ok".equals(e.getActionCommand())) {
			delegate.setCreateVisible(checkBoxes[0].isSelected());
			delegate.setDestroyVisible(checkBoxes[1].isSelected());
			delegate.setInsertVisible(checkBoxes[2].isSelected());
			delegate.setDeleteVisible(checkBoxes[3].isSelected());
			delegate.setAssignVisible(checkBoxes[4].isSelected());
			delegate.filterGraphByEvent();
			dispose();
		}
		if ("Cancel".equals(e.getActionCommand())) {
			dispose();
		}

	}

}
