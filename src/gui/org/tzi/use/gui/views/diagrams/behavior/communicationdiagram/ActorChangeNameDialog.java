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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.tzi.use.gui.util.GridBagHelper;

/**
 * A dialog for changing the actors name
 * 
 * @author Quang Dung Nguyen
 * 
 */
@SuppressWarnings("serial")
public class ActorChangeNameDialog extends JDialog {
	private CommunicationDiagram communicationDiagram;
	private JComponent contentPanel;

	private JLabel nameLabel;
	private JTextField nameField;
	private JButton okButton;
	private JButton closeButton;

	public ActorChangeNameDialog(CommunicationDiagram diagram) {
		this.communicationDiagram = diagram;
		setTitle("Change actor name");
		setModal(true);
		setSize(250, 100);
		setResizable(true);
		setMaximumSize(new Dimension(500, 100));
		setMinimumSize(new Dimension(250, 100));

		nameLabel = new JLabel("Name : ");
		nameLabel.setLabelFor(nameField);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

		nameField = new JTextField();
		nameField.setText(communicationDiagram.getActorName());

		okButton = new JButton("OK");
		okButton.setPreferredSize(new Dimension(100, 30));
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				communicationDiagram.changeActorName(nameField.getText());
				communicationDiagram.invalidateContent(true);
				dispose();
			}
		});

		closeButton = new JButton("Close");
		closeButton.setPreferredSize(new Dimension(100, 30));
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ActorChangeNameDialog.this.dispatchEvent(new WindowEvent(ActorChangeNameDialog.this, WindowEvent.WINDOW_CLOSING));
			}
		});

		contentPanel = (JComponent) getContentPane();
		contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		GridBagHelper gh = new GridBagHelper(contentPanel);

		gh.add(nameLabel, 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);
		gh.add(nameField, 1, 0, GridBagConstraints.REMAINDER, 1, 1.0, 0.0, GridBagConstraints.HORIZONTAL);
		gh.add(new JLabel(), 1, 1, 1, 1, 10.0, 0.0, GridBagConstraints.HORIZONTAL);
		gh.add(okButton, 2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST);
		gh.add(closeButton, 3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST);

		getRootPane().setDefaultButton(okButton);
		pack();
		setLocationRelativeTo(communicationDiagram);
	}

}
