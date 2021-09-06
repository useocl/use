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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.tzi.use.gui.util.GridBagHelper;
import org.tzi.use.gui.views.diagrams.behavior.shared.CancelButton;
import org.tzi.use.gui.views.diagrams.behavior.shared.OKButton;

/**
 * @author Carsten Schlobohm
 */
public class PropertiesWindow extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The container for this Window.
	 */
	private Container fC;

	/**
	 * The tabbed pane for an Diagram, After select, actor settings-Tabs
	 */
	private JTabbedPane fTabbedPane;

	/**
	 * Some panels.
	 */
	private JPanel fDiag, actorSettings, selectionSettings;

	private ShowRelatedObjectsPanel panelContainer;

	private JLabel nameLabel;
	private JTextField nameField;

	/**
	 * The communication diagram from which this window is called.
	 */
	private CommunicationDiagramView comDia;

	private JRadioButton actorMovableNoBtn, actorMovableYesBtn, showIncompleteLinksYesBtn,
			showIncompleteLinksNoBtn, bindDiagramToSelectionYesBtn, bindDiagramToSelectionNoBtn,
			showActorAlwaysYesBtn, showActorAlwaysNoBtn;

	/**
	 * Constructs a new Properties-Window.
	 */
	public PropertiesWindow(CommunicationDiagramView communicationDiagram) {
		super(communicationDiagram.getMainWindow(), true);
		setMinimumSize(new Dimension(450, 400));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setTitle("Properties...");
		comDia = communicationDiagram;
		fC = getContentPane();
		fC.setLayout(new BorderLayout(20, 10));

		fTabbedPane = new JTabbedPane();


		CommunicationDiagramOptions settings = comDia.getCommunicationDiagram().getSettings();

		String helpText = "The following options allow you to " +
				"automatically add objects to your selection. A search cycle of 1 starts " +
				"with the currently visible objects. A higher search cycle also " +
				"takes into account the newly found objects of the previous search cycle.";

		panelContainer = new ShowRelatedObjectsPanel(settings.isShowMissingLinks(),
				settings.isShowMissingObjectLinks(), settings.isShowHiddenObjectsOfLinkObject(),
				settings.isShowHiddenFunctionParameter(),
				settings.getSearchCycles(),
				helpText);


		// ==================== Diagram-Tab!!   ============================
		fDiag = new JPanel();

		//  Show incomplete links
		JPanel labelShowIncompleteLinks = WindowHelper.customJPanel(100);
		labelShowIncompleteLinks.setBorder(BorderFactory.createTitledBorder("Hide links with (a) hidden partner(s)"));

		ButtonGroup showLinkButtonGroup = new ButtonGroup();
		showIncompleteLinksYesBtn = new JRadioButton("Yes");
		showIncompleteLinksNoBtn = new JRadioButton("No");
		showLinkButtonGroup.add(showIncompleteLinksYesBtn);
		showLinkButtonGroup.add(showIncompleteLinksNoBtn);

		labelShowIncompleteLinks.add(showIncompleteLinksYesBtn);
		labelShowIncompleteLinks.add(showIncompleteLinksNoBtn);

		//  message selection
		JPanel bindDiagramToSelection = WindowHelper.customJPanel(100);
		bindDiagramToSelection.setBorder(BorderFactory.createTitledBorder("Bind visible messages to message selection"));
		bindDiagramToSelection.setMinimumSize((new Dimension(400,300)));

		ButtonGroup bindSelectionButtonGroup = new ButtonGroup();
		bindDiagramToSelectionYesBtn = new JRadioButton("Yes");
		bindDiagramToSelectionNoBtn = new JRadioButton("No");
		bindSelectionButtonGroup.add(bindDiagramToSelectionYesBtn);
		bindSelectionButtonGroup.add(bindDiagramToSelectionNoBtn);

		bindDiagramToSelection.add(bindDiagramToSelectionYesBtn);
		bindDiagramToSelection.add(bindDiagramToSelectionNoBtn);

		JTextArea helpTextPage1 = WindowHelper.customTextArea("A message selection has the disadvantage " +
						"that some objects are no longer available. Therefore, the following " +
						"option allows you to change a message selection to a selection of objects " +
						"in which all messages are visible.\n" +
				"Note: Changes over this menu are temporary. To change the default settings permanently, " +
				"you must change them in the \"use.properties\" file. \n");

		// ==== layout

		GridBagHelper diagramLayoutHelper = new GridBagHelper(fDiag);

		diagramLayoutHelper.add(helpTextPage1, 0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);
		diagramLayoutHelper.add(labelShowIncompleteLinks, 0, 2, 2, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);
		diagramLayoutHelper.add(bindDiagramToSelection, 0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);

		fDiag.setMinimumSize(new Dimension(500,300));

		// ====================== end Diagram-Tab ================

		// Lifeline-Tab
		selectionSettings = panelContainer.getPanel();

		// ====  Tab Actor Settings   ==========================
		actorSettings = WindowHelper.customJPanel(0);

		JPanel actorNamePanel = WindowHelper.customJPanel(100);
		actorNamePanel.setBorder(BorderFactory.createTitledBorder("Actor name"));

		nameLabel = new JLabel("Name : ");
		nameLabel.setLabelFor(nameField);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(100,30));

		GridBagHelper actorNameHelper = new GridBagHelper(actorNamePanel);
		actorNameHelper.add(nameLabel, 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);
		actorNameHelper.add(nameField, 1, 0, GridBagConstraints.REMAINDER, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);

		//  Actor movable
		JPanel actorPanel = WindowHelper.customJPanel(100);
		actorPanel.setBorder(BorderFactory.createTitledBorder("Actor movable"));

		ButtonGroup actorButtonGroup = new ButtonGroup();
		actorMovableYesBtn = new JRadioButton("Yes");
		actorMovableNoBtn = new JRadioButton("No");
		actorButtonGroup.add(actorMovableYesBtn);
		actorButtonGroup.add(actorMovableNoBtn);

		actorPanel.add(actorMovableYesBtn);
		actorPanel.add(actorMovableNoBtn);

		//  ====== Show Actor always
		JPanel showActorAlways = WindowHelper.customJPanel(100);
		showActorAlways.setBorder(BorderFactory.createTitledBorder("Always show actor"));

		ButtonGroup showActorButtonGroup = new ButtonGroup();
		showActorAlwaysYesBtn = new JRadioButton("Yes");
		showActorAlwaysNoBtn = new JRadioButton("No");
		showActorButtonGroup.add(showActorAlwaysYesBtn);
		showActorButtonGroup.add(showActorAlwaysNoBtn);

		showActorAlways.add(showActorAlwaysYesBtn);
		showActorAlways.add(showActorAlwaysNoBtn);

		GridBagHelper obLayoutHelper = new GridBagHelper(actorSettings);


		// layout
		obLayoutHelper.add(actorNamePanel, 0, 0, GridBagConstraints.REMAINDER, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);
		obLayoutHelper.add(actorPanel, 0,1, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL);
		obLayoutHelper.add(showActorAlways, 0,2, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL);


		// ===== end Actor Settings  ===================


		//fTabbedPane.add(fDiag, "Diagram", 0); FIXME currently not used because it led to a bug (ticket 192)
		//fTabbedPane.addTab("After selection", selectionSettings);
		fTabbedPane.addTab("Actor settings", actorSettings);

		fC.add("Center", fTabbedPane);

		// OK-/Cancel-Buttons
		JPanel buttons = WindowHelper.customJPanel(0);
		JButton ok = new OKButton("Ok");
		ok.addActionListener(this);
		buttons.add(ok);
		JButton cancel = new CancelButton("Cancel");
		cancel.addActionListener(this);
		buttons.add(cancel);
		fC.add("South", buttons);
		initCurrentState();
		pack();
	}

	/**
	 * Set the states of all options
	 */
	private void initCurrentState() {
		// Actor
		nameField.setText(comDia.getCommunicationDiagram().getActorNode().getActorData().getUserName());

		if (comDia.getCommunicationDiagram().getActorNode().isAlwaysVisible()) {
			showActorAlwaysYesBtn.setSelected(true);
		} else {
			showActorAlwaysNoBtn.setSelected(true);
		}

		if (comDia.getCommunicationDiagram().getActorNode().isUnmovable()) {
			actorMovableNoBtn.setSelected(true);
		} else {
			actorMovableYesBtn.setSelected(true);
		}

		CommunicationDiagramOptions settings = comDia.getCommunicationDiagram().getSettings();

		showIncompleteLinksYesBtn.setSelected(settings.isHideAssociationsWithAHiddenPartner());
		showIncompleteLinksNoBtn.setSelected(!settings.isHideAssociationsWithAHiddenPartner());
		bindDiagramToSelectionYesBtn.setSelected(settings.isBindVisibleMessagesToMessageSelection());
		bindDiagramToSelectionNoBtn.setSelected(!settings.isBindVisibleMessagesToMessageSelection());
	}

	/**
	 * Shows the Window.
	 */
	public void showWindow() {
		this.setLocation(300, 200);
		this.setVisible(true);
	}

	/**
	 * Analyses the Entries and adjusts the values in the properties objects of
	 * the communication diagram.
	 */
	public void actionPerformed(ActionEvent e) {

		if ("Ok".equals(e.getActionCommand())) {

			comDia.getCommunicationDiagram().getActorNode().getActorData().setUserName(nameField.getText());
			comDia.getCommunicationDiagram().getActorNode().setUnmovable(actorMovableNoBtn.isSelected());
			comDia.getCommunicationDiagram().getActorNode().setIsAlwaysVisible(showActorAlwaysYesBtn.isSelected());

			CommunicationDiagramOptions settings = comDia.getCommunicationDiagram().getSettings();

			settings.setHideAssociationsWithAHiddenPartner(showIncompleteLinksYesBtn.isSelected());
			settings.setBindVisibleMessagesToMessageSelection(bindDiagramToSelectionYesBtn.isSelected());
			settings.setShowMissingLinks(panelContainer.isShowAssociations());
			settings.setShowMissingObjectLinks(panelContainer.isShowAssociationClasses());
			settings.setShowHiddenObjectsOfLinkObject(panelContainer.isShowObjectsFromAssociation());
			settings.setShowHiddenFunctionParameter(panelContainer.isShowObjectsAsParam());
			settings.setSearchCycles(panelContainer.searchCycles());


			dispose();
		}
		if ("Cancel".equals(e.getActionCommand())) {
			dispose();
		}
	}

}