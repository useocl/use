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
 * Represents the frame in which you can select the message depth
 * @author Thomas Schaefer
 *
 */
@SuppressWarnings("serial")
public class MessageDepthWindow extends JDialog implements ActionListener {

	/**
	 * The container for this Window.
	 */
	private Container contentContainer;

	/**
	 * The {@link CommunicationDiagram} from which this window is called.
	 */
	private CommunicationDiagram fComDiag;
	
	private JComboBox<String> messageDepthComboBox;
	
	public MessageDepthWindow(CommunicationDiagram communicationDiagram){
		this.fComDiag = communicationDiagram;
		
		this.setTitle("Select Message Depth");
		
		contentContainer = getContentPane();
		contentContainer.setLayout(new BorderLayout(20, 10));
		
		JLabel label1 = new JLabel("");
		JLabel label2 = new JLabel("");
		JLabel label3 = new JLabel("");
		
		messageDepthComboBox = new JComboBox<String>();
		String comboBoxItem = ": m";
		
		for (int i = 1; i <= fComDiag.getMessageDepth(); i++) {
			
			if(i == 1){
				comboBoxItem = i + comboBoxItem;
			}else{
				comboBoxItem = i + "." + comboBoxItem;
			}
			
			messageDepthComboBox.addItem(comboBoxItem);
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
		contentContainer.add("Center", messageDepthComboBox);
		contentContainer.add("South", buttonsPanel);
		pack();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("Ok".equals(e.getActionCommand())) {
			fComDiag.filterGraphByMessageDepth(messageDepthComboBox.getSelectedIndex()+1);
			dispose();
		}
		if ("Cancel".equals(e.getActionCommand())) {
			dispose();
		}	
	}
}
