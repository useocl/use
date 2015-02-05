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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.tzi.use.gui.util.GridBagHelper;
import org.tzi.use.uml.sys.events.Event;

import com.google.common.eventbus.Subscribe;

/**
 * Dialog for navigating on messages of communication diagrams
 * 
 * @author Quang Dung Nguyen
 * 
 */
@SuppressWarnings("serial")
public class MessagesNavigationDialog extends JDialog {
	private CommunicationDiagram communicationDiagram;
	private BlinkingThread blinker;

	private JButton closeButton;
	private JButton nextButton;
	private JButton previousButton;
	private JLabel sequenceNumberLabel;
	private JComboBox<MMessage> messagesBox;
	private DefaultComboBoxModel<MMessage> model;
	private JComponent contentPanel;

	public MessagesNavigationDialog(CommunicationDiagram diagram) {
		this.communicationDiagram = diagram;
		blinker = new BlinkingThread(communicationDiagram);
		blinker.start();
		setTitle("Messages navigation");
		setAlwaysOnTop(true);
		setSize(250, 120);
		setResizable(true);
		setMaximumSize(new Dimension(800, 120));
		setMinimumSize(new Dimension(250, 120));

		sequenceNumberLabel = new JLabel("Message : ");
		sequenceNumberLabel.setLabelFor(messagesBox);
		sequenceNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);

		initMessages();

		nextButton = new JButton("Next");
		nextButton.setPreferredSize(new Dimension(100, 30));
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (messagesBox.getSelectedIndex() < messagesBox.getItemCount() - 1) {
					messagesBox.setSelectedIndex((messagesBox.getSelectedIndex() + 1));
				}
			}
		});

		previousButton = new JButton("Previous");
		previousButton.setPreferredSize(new Dimension(100, 30));
		previousButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (messagesBox.getSelectedIndex() > 1) {
					messagesBox.setSelectedIndex((messagesBox.getSelectedIndex() - 1));
				} else if (messagesBox.getSelectedIndex() == 1) {
					messagesBox.setSelectedIndex((messagesBox.getSelectedIndex() - 1));
					communicationDiagram.getActivatedEdge().setActivate(false);
					communicationDiagram.getActivatedMessage().removeColoredMessage();
					communicationDiagram.repaint();
				}
			}
		});

		closeButton = new JButton("Close");
		closeButton.setPreferredSize(new Dimension(100, 30));
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MessagesNavigationDialog.this.dispatchEvent(new WindowEvent(MessagesNavigationDialog.this, WindowEvent.WINDOW_CLOSING));
			}
		});

		contentPanel = (JComponent) getContentPane();
		contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		GridBagHelper gh = new GridBagHelper(contentPanel);

		gh.add(sequenceNumberLabel, 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);

		gh.add(messagesBox, 1, 0, GridBagConstraints.REMAINDER, 1, 1.0, 0.0, GridBagConstraints.HORIZONTAL);

		gh.add(new JLabel(), 1, 1, 3, 1, 10.0, 0.0, GridBagConstraints.HORIZONTAL);

		gh.add(nextButton, 4, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST);

		gh.add(previousButton, 5, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST);

		gh.add(closeButton, 5, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST);

		getRootPane().setDefaultButton(nextButton);
		pack();
		setLocationRelativeTo(communicationDiagram);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				blinker.stop();
				if (communicationDiagram.getActivatedEdge() != null) {
					communicationDiagram.getActivatedEdge().setActivate(false);
				}
				if (communicationDiagram.getActivatedMessage() != null) {
					communicationDiagram.getActivatedMessage().removeColoredMessage();
				}
				communicationDiagram.repaint();
			}
		});
		communicationDiagram.getParentDiagram().system().getEventBus().register(this);
	}

	private void initMessages() {
		model = new DefaultComboBoxModel<MMessage>(communicationDiagram.getMessages());
		messagesBox = new JComboBox<MMessage>(model);
		
		messagesBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MMessage message = (MMessage) messagesBox.getSelectedItem();
				CommunicationDiagramEdge cde = communicationDiagram.getEdge(message);
				if (cde != null) {
					if (communicationDiagram.getActivatedEdge() != null) {
						communicationDiagram.getActivatedEdge().setActivate(false);
					}
					cde.setActivate(true);
					communicationDiagram.setActivatedEdge(cde);
					if (communicationDiagram.getActivatedMessage() != null) {
						communicationDiagram.getActivatedMessage().removeColoredMessage();
					}
					communicationDiagram.setActivatedMessage(cde.getMessagesGroup());
					if (communicationDiagram.getActivatedMessage() != null) {
						communicationDiagram.getActivatedMessage().setColoredMessage(message);
					}
					communicationDiagram.repaint();
				} else {
					if (communicationDiagram.getActivatedEdge() != null) {
						communicationDiagram.getActivatedEdge().setActivate(false);
						communicationDiagram.getActivatedMessage().removeColoredMessage();
						communicationDiagram.repaint();
					}
				}
			}
		});
	}

	@Subscribe
	public void stateChanged(Event e) {		
		DefaultComboBoxModel<MMessage> newmodel = new DefaultComboBoxModel<MMessage>(communicationDiagram.getMessages());
		messagesBox.setModel(newmodel);
		if (communicationDiagram.getActivatedEdge() != null) {
			communicationDiagram.getActivatedEdge().setActivate(false);
		}
		if (communicationDiagram.getActivatedMessage() != null) {
			communicationDiagram.getActivatedMessage().removeColoredMessage();
		}
		communicationDiagram.invalidateContent(true);
	}

}
