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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.tzi.use.gui.views.diagrams.behavior.sequencediagram.CancelButton;
import org.tzi.use.gui.views.diagrams.behavior.sequencediagram.OKButton;

/**
 * @author Antje Werner
 * @author Thomas Schaefer
 * 
 *         A window for choosing the Messages to show in the communication diagram.
 */

public class MessageChooseWindow extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The container for this Window.
	 */
	private Container contentContainer;

	/**
	 * The {@link CommunicationDiagram} from which this window is called.
	 */
	private CommunicationDiagram fComDiag;

	/**
	 * The from/tillComboBox
	 */
	private JComboBox<String> tillComboBox, fromComboBox;

	public MessageChooseWindow(CommunicationDiagram comDiag) {
		setTitle("Choose the area of Messages");
		fComDiag = comDiag;
		//messageIds = fComDiag.getMessageIds();
		contentContainer = getContentPane();
		contentContainer.setLayout(new BorderLayout(20, 10));

		JLabel label1 = new JLabel("");
		JLabel label2 = new JLabel("");
		JLabel label3 = new JLabel("");

		JPanel fromTillPanel = new JPanel();
		fromTillPanel.setLayout(new GridLayout(2, 2));
		fromComboBox = new JComboBox<String>();
		fromComboBox.addActionListener(this);
		tillComboBox = new JComboBox<String>();
		JLabel fromLabel = new JLabel("From");
		JLabel tillLabel = new JLabel("Till");

		for (MMessage msg : fComDiag.getMessages()) {
			fromComboBox.addItem(msg.getSequenceNumberMessage());
		}
		fromTillPanel.add(fromLabel);
		fromTillPanel.add(tillLabel);
		fromTillPanel.add(fromComboBox);
		fromTillPanel.add(tillComboBox);

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
		contentContainer.add("Center", fromTillPanel);
		contentContainer.add("South", buttonsPanel);
		pack();
	}

	
	void showWindow() {
		MessageChooseWindow cmdChooseW = new MessageChooseWindow(fComDiag);
		cmdChooseW.setSize(350, 150);
		cmdChooseW.setLocation(300, 200);
		cmdChooseW.setVisible(true);
	}

	/**
	 * Analyses the entries made in the window and make a treemape, who contains 
	 * all Messages, that shows up.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == fromComboBox){

			tillComboBox.removeAllItems();

			for (int i = fromComboBox.getSelectedIndex()+1; i < fComDiag.getMessages().size(); i++) {
				tillComboBox.addItem(fComDiag.getMessages().get(i).getSequenceNumberMessage());
			}	
		}else{
			if ("Ok".equals(e.getActionCommand())) {
				fComDiag.showSomeMessages(fromComboBox.getSelectedIndex(),fromComboBox.getSelectedIndex() + tillComboBox.getSelectedIndex()+1);
				dispose();
			}
			if ("Cancel".equals(e.getActionCommand())) {
				dispose();
			}
		}

	}

}
