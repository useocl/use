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

package org.tzi.use.gui.views.diagrams.behavior.sequencediagram;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * @author Antje Werner
 * 
 *         A window for choosing some properties, e.g. font, distance of
 *         lifelines, size of object boxes and so on.
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
	 * The tabbed pane for an Diagram, Objectbox, Activation and an
	 * Lifeline-tab.
	 */
	private JTabbedPane fTabbedPane;

	/**
	 * The text fields for making some size-entries.
	 */
	private JTextField fLeftMargin, fTopMargin, fRightMargin, fBottomMargin, fManW, fManH, fAct_ManDist, fLl_Width, fLl_ManDist, stateNodeManualWidthField, stateNodeManualHeightField;

	/**
	 * ComboBoxes for creating some menu items.
	 */
	private JComboBox<String> fFontBox;
	private JComboBox<Integer> fSizeBox;

	/**
	 * RadioButtons for creating some menu items.
	 */
	private JRadioButton fWidthB1, fWidthB2, fHeightB1, fHeightB2, fLlB1, fLlB2, fLl_manDist, fLl_textLength, fLl_manLabel, fLl_individual, fObWidth_B1, fObWidth_B2,
			fObHeight_B1, fObHeight_B2, lineBreakLabelNoBtn, lineBreakLabelYesBtn, stateNodeIndivitualBtn, stateNodeManualBtn;

	/**
	 * Some caption labels.
	 */
	private JLabel fActLabel, stateNodeWidthLabel, stateNodeHeightLabel;

	/**
	 * Some panels.
	 */
	private JPanel fDiag, fObjectBox, fLifeline;

	/**
	 * The sequence fDiagram from which this window is called.
	 */
	private SequenceDiagram fSeqDiag;

	/**
	 * Constructs a new Properties-Window.
	 */
	public PropertiesWindow(SequenceDiagram seqDiag) {
		super(seqDiag.getMainWindow(), true);
		setTitle("Properties...");
		fSeqDiag = seqDiag;
		fC = getContentPane();
		fC.setLayout(new BorderLayout(20, 10));

		fTabbedPane = new JTabbedPane();

		// Diagram-Tab!!
		fDiag = new JPanel();
		GridBagLayout fDiagLayout = new GridBagLayout();
		fDiag.setLayout(fDiagLayout);
		JPanel margins = new JPanel();
		margins.setBorder(BorderFactory.createTitledBorder("Margins"));
		GridBagLayout margLayout = new GridBagLayout();
		margins.setLayout(margLayout);

		fLeftMargin = new JTextField(new NumberDocument(), "", 4);
		fLeftMargin.setText(Integer.toString((fSeqDiag.getProperties().getLeftMargin())));

		fRightMargin = new JTextField(new NumberDocument(), "", 4);
		fRightMargin.setText(Integer.toString((fSeqDiag.getProperties().getRightMargin())));

		fTopMargin = new JTextField(new NumberDocument(), "", 4);
		fTopMargin.setText(Integer.toString((fSeqDiag.getProperties().getTopMargin())));

		fBottomMargin = new JTextField(new NumberDocument(), "", 4);
		fBottomMargin.setText(Integer.toString((fSeqDiag.getProperties().getBottomMargin())));

		JPanel font = new JPanel();
		font.setBorder(BorderFactory.createTitledBorder("Font"));
		GridBagLayout fontLayout = new GridBagLayout();
		font.setLayout(fontLayout);
		fFontBox = new JComboBox<String>();
		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		String actFont = fSeqDiag.getProperties().getFont().getFamily();
		for (int i = 0; i < fonts.length; i++) {
			fFontBox.addItem(fonts[i]);
			if (actFont.equalsIgnoreCase(fonts[i])) {
				actFont = fonts[i];
			}
		}

		fFontBox.setSelectedItem(actFont);

		fSizeBox = new JComboBox<Integer>();
		int sizes[] = { 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28 };
		for (int i = 0; i < sizes.length; i++) {
			fSizeBox.addItem(Integer.valueOf(sizes[i]));
		}
		int actFontSize = fSeqDiag.getProperties().getFontSize();
		fSizeBox.setSelectedItem(Integer.valueOf(actFontSize));

		// Activation-Tab
		JPanel dist = new JPanel();
		dist.setBorder(BorderFactory.createTitledBorder("Activations"));

		GridBagLayout distLayout = new GridBagLayout();
		dist.setLayout(distLayout);

		JPanel act_Distance = new JPanel();
		fActLabel = new JLabel("Distance: ");
		act_Distance.add(fActLabel);

		fAct_ManDist = new JTextField(new NumberDocument(), "", 3);
		act_Distance.add(fAct_ManDist);

		fActLabel.setEnabled(true);
		fAct_ManDist.setEditable(true);
		if (fSeqDiag.getProperties().getActManDist() != -1)
			fAct_ManDist.setText(Integer.toString(fSeqDiag.getProperties().getActManDist()));
		else
			fAct_ManDist.setText(Integer.toString(fSeqDiag.getProperties().actStep()));

		// x y w h wx wy
		JLabel left = new JLabel("Left:");
		margLayout.setConstraints(left, addComponent(margins, margLayout, left, 0, 1, 1, 1, 0, 0, new Insets(10, 5, 0, 0)));
		margins.add(left);
		margLayout.setConstraints(fLeftMargin, addComponent(margins, margLayout, fLeftMargin, 1, 1, 1, 1, 0, 0, new Insets(10, 5, 0, 0)));
		margins.add(fLeftMargin);
		JLabel right = new JLabel("Right:");
		margLayout.setConstraints(right, addComponent(margins, margLayout, right, 2, 1, 1, 1, 0, 0, new Insets(10, 20, 0, 0)));
		margins.add(right);
		margLayout.setConstraints(fRightMargin, addComponent(margins, margLayout, fRightMargin, 3, 1, 1, 1, 0, 0, new Insets(10, 5, 0, 5)));
		margins.add(fRightMargin);
		JLabel top = new JLabel("Top:");
		margLayout.setConstraints(top, addComponent(margins, margLayout, top, 0, 3, 1, 1, 0, 0, new Insets(5, 5, 5, 0)));
		margins.add(top);
		margLayout.setConstraints(fTopMargin, addComponent(margins, margLayout, fTopMargin, 1, 3, 1, 1, 0, 0, new Insets(5, 5, 5, 0)));
		margins.add(fTopMargin);
		JLabel bottom = new JLabel("Bottom:");
		margLayout.setConstraints(bottom, addComponent(margins, margLayout, bottom, 2, 3, 1, 1, 0, 0, new Insets(5, 20, 5, 0)));
		margins.add(bottom);
		margLayout.setConstraints(fBottomMargin, addComponent(margins, margLayout, fBottomMargin, 3, 3, 1, 1, 0, 0, new Insets(5, 5, 5, 5)));
		margins.add(fBottomMargin);

		fontLayout.setConstraints(fFontBox, addComponent(font, fontLayout, fFontBox, 0, 0, 1, 1, 0, 0, new Insets(0, 0, 0, 0)));
		font.add(fFontBox);
		fontLayout.setConstraints(fSizeBox, addComponent(font, fontLayout, fSizeBox, 1, 0, 1, 1, 0, 0, new Insets(0, 0, 0, 0)));
		font.add(fSizeBox);

		fDiagLayout.setConstraints(font, addComponent(fDiag, fDiagLayout, font, 0, 0, 2, 1, 0, 0, new Insets(10, 0, 0, 87)));
		fDiag.add(font);
		fDiagLayout.setConstraints(margins, addComponent(fDiag, fDiagLayout, margins, 0, 1, 2, 1, 0, 0, new Insets(15, 0, 60, 87)));
		fDiag.add(margins);

		// Lifeline-Tab
		fLifeline = new JPanel();
		GridBagLayout llLayout = new GridBagLayout();
		fLifeline.setLayout(llLayout);

		JPanel width = new JPanel();
		width.add(new JLabel("Width of active Lifelines:"));
		fLl_Width = new JTextField(new NumberDocument(), "", 3);
		fLl_Width.setText(Integer.toString(fSeqDiag.getProperties().frWidth()));
		width.add(fLl_Width);

		JPanel ll_distance = new JPanel();
		ll_distance.setBorder(BorderFactory.createTitledBorder("Distance between Lifelines"));
		GridBagLayout ll_distLayout = new GridBagLayout();
		ll_distance.setLayout(ll_distLayout);

		ButtonGroup llGroup = new ButtonGroup();
		fLl_textLength = new JRadioButton("Fit to Text-Length");
		fLl_manDist = new JRadioButton("Manual distance");
		llGroup.add(fLl_textLength);
		llGroup.add(fLl_manDist);

		ButtonGroup llGroup2 = new ButtonGroup();
		fLlB1 = new JRadioButton("Uniform distance"); // depending on maximum
		fLlB2 = new JRadioButton("Individual distances");
		llGroup2.add(fLlB1);
		llGroup2.add(fLlB2);

		ButtonGroup llGroup3 = new ButtonGroup();
		fLl_manLabel = new JRadioButton("Uniform distance: ");
		fLl_individual = new JRadioButton("Individual distances");
		llGroup3.add(fLl_manLabel);
		llGroup3.add(fLl_individual);		

		// JPanel ll_Distance = new JPanel();
		fLl_ManDist = new JTextField(new NumberDocument(), "", 3);

		ll_distLayout.setConstraints(fLl_textLength, addComponent(ll_distance, ll_distLayout, fLl_textLength, 0, 0, 3, 1, 0, 0, new Insets(0, 0, 0, 0)));
		ll_distance.add(fLl_textLength);
		ll_distLayout.setConstraints(fLl_manDist, addComponent(ll_distance, ll_distLayout, fLl_manDist, 0, 3, 3, 1, 0, 0, new Insets(0, 0, 0, 0)));
		ll_distance.add(fLl_manDist);
		ll_distLayout.setConstraints(fLlB1, addComponent(ll_distance, ll_distLayout, fLlB1, 1, 1, 2, 1, 0, 0, new Insets(0, 15, 0, 0)));
		ll_distance.add(fLlB1);
		ll_distLayout.setConstraints(fLlB2, addComponent(ll_distance, ll_distLayout, fLlB2, 3, 1, 2, 1, 0, 0, new Insets(0, 15, 0, 0)));
		ll_distance.add(fLlB2);
		ll_distLayout.setConstraints(fLl_manLabel, addComponent(ll_distance, ll_distLayout, fLl_manLabel, 1, 5, 2, 1, 0, 0, new Insets(0, 15, 0, 0)));
		ll_distance.add(fLl_manLabel);
		ll_distLayout.setConstraints(fLl_ManDist, addComponent(ll_distance, ll_distLayout, fLl_ManDist, 3, 5, 2, 1, 0, 0, new Insets(0, 0, 0, 0)));
		ll_distance.add(fLl_ManDist);
		ll_distLayout.setConstraints(fLl_individual, addComponent(ll_distance, ll_distLayout, fLl_individual, 1, 6, 2, 1, 0, 0, new Insets(0, 15, 0, 0)));
		ll_distance.add(fLl_individual);

		// Tab ObjectBox
		fObjectBox = new JPanel();
		fObjectBox.setBorder(BorderFactory.createTitledBorder("Size of object box"));

		// Label line break properties
		JPanel labelLineBreakPanel = new JPanel();
		labelLineBreakPanel.setBorder(BorderFactory.createTitledBorder("Break Label Line"));
		GridLayout obLabelLayout = new GridLayout();
		labelLineBreakPanel.setLayout(obLabelLayout);

		ButtonGroup labelLineBreakGroup = new ButtonGroup();
		lineBreakLabelNoBtn = new JRadioButton("No");
		lineBreakLabelYesBtn = new JRadioButton("Yes");
		labelLineBreakGroup.add(lineBreakLabelNoBtn);
		labelLineBreakGroup.add(lineBreakLabelYesBtn);

		labelLineBreakPanel.add(lineBreakLabelNoBtn);
		labelLineBreakPanel.add(lineBreakLabelYesBtn);

		// Width-Properties
		JPanel obWidth = new JPanel();
		obWidth.setBorder(BorderFactory.createTitledBorder("Width"));
		GridBagLayout obWidthLayout = new GridBagLayout();
		obWidth.setLayout(obWidthLayout);

		ButtonGroup adjustWGroup = new ButtonGroup();
		fObWidth_B1 = new JRadioButton("Fit to Text-Size");
		fObWidth_B2 = new JRadioButton("Manual Width: ");
		adjustWGroup.add(fObWidth_B1);
		adjustWGroup.add(fObWidth_B2);

		ButtonGroup adjustWGroup2 = new ButtonGroup();
		fWidthB1 = new JRadioButton("Fit to maximum Width");
		fWidthB2 = new JRadioButton("Individual Widthes");
		adjustWGroup2.add(fWidthB1);
		adjustWGroup2.add(fWidthB2);

		fManW = new JTextField(new NumberDocument(), "", 3);
		fManW.setEditable(false);

		obWidthLayout.setConstraints(fObWidth_B1, addComponent(obWidth, obWidthLayout, fObWidth_B1, 0, 0, 3, 1, 0, 0, new Insets(0, 0, 0, 0)));
		obWidth.add(fObWidth_B1);
		obWidthLayout.setConstraints(fWidthB1, addComponent(obWidth, obWidthLayout, fWidthB1, 1, 1, 2, 1, 0, 0, new Insets(0, 15, 0, 0)));
		obWidth.add(fWidthB1);
		obWidthLayout.setConstraints(fWidthB2, addComponent(obWidth, obWidthLayout, fWidthB2, 3, 1, 2, 1, 0, 0, new Insets(0, 15, 0, 0)));
		obWidth.add(fWidthB2);

		obWidthLayout.setConstraints(fObWidth_B2, addComponent(obWidth, obWidthLayout, fObWidth_B2, 0, 3, 3, 1, 0, 0, new Insets(0, 0, 0, 0)));
		obWidth.add(fObWidth_B2);
		obWidthLayout.setConstraints(fManW, addComponent(obWidth, obWidthLayout, fManW, 3, 3, 3, 1, 0, 0, new Insets(0, 0, 0, 0)));
		obWidth.add(fManW);

		// Height-Properties
		JPanel obHeight = new JPanel();
		obHeight.setBorder(BorderFactory.createTitledBorder("Height"));
		GridBagLayout obHeightLayout = new GridBagLayout();
		obHeight.setLayout(obHeightLayout);

		ButtonGroup adjustHGroup = new ButtonGroup();
		fObHeight_B1 = new JRadioButton("Fit to Text-Height");
		fObHeight_B2 = new JRadioButton("Manual Height: ");
		adjustHGroup.add(fObHeight_B1);
		adjustHGroup.add(fObHeight_B2);

		ButtonGroup adjustHGroup2 = new ButtonGroup();
		fHeightB1 = new JRadioButton("Fit to maximum Height");
		fHeightB2 = new JRadioButton("Individual Height");
		adjustHGroup2.add(fHeightB1);
		adjustHGroup2.add(fHeightB2);

		fManH = new JTextField(new NumberDocument(), "", 3);
		fManH.setEditable(false);

		obHeightLayout.setConstraints(fObHeight_B1, addComponent(obHeight, obHeightLayout, fObWidth_B1, 0, 0, 3, 1, 0, 0, new Insets(0, 0, 0, 0)));
		obHeight.add(fObHeight_B1);
		obHeightLayout.setConstraints(fHeightB1, addComponent(obHeight, obHeightLayout, fHeightB1, 1, 1, 2, 1, 0, 0, new Insets(0, 15, 0, 0)));
		obHeight.add(fHeightB1);
		obHeightLayout.setConstraints(fHeightB2, addComponent(obHeight, obHeightLayout, fHeightB2, 3, 1, 2, 1, 0, 0, new Insets(0, 15, 0, 0)));
		obHeight.add(fHeightB2);

		obHeightLayout.setConstraints(fObHeight_B2, addComponent(obHeight, obHeightLayout, fObHeight_B2, 0, 3, 3, 1, 0, 0, new Insets(0, 0, 0, 0)));
		obHeight.add(fObHeight_B2);
		obHeightLayout.setConstraints(fManH, addComponent(obHeight, obHeightLayout, fManH, 3, 3, 3, 1, 0, 0, new Insets(0, 0, 0, 0)));
		obHeight.add(fManH);

		GridBagLayout objectboxLayout = new GridBagLayout();
		fObjectBox.setLayout(objectboxLayout);

		objectboxLayout.setConstraints(obWidth, addComponent(fObjectBox, objectboxLayout, obWidth, 0, 0, 1, 1, 0, 0, new Insets(0, 0, 0, 0)));
		fObjectBox.add(obWidth);
		objectboxLayout.setConstraints(obHeight, addComponent(fObjectBox, objectboxLayout, obHeight, 0, 1, 1, 1, 0, 0, new Insets(0, 0, 0, 0)));
		fObjectBox.add(obHeight);

		objectboxLayout.setConstraints(labelLineBreakPanel,
				addComponent(fObjectBox, objectboxLayout, labelLineBreakPanel, 0, 2, 1, 1, 0, 0, new Insets(0, 0, 0, 0)));
		fObjectBox.add(labelLineBreakPanel);

		llLayout.setConstraints(width, addComponent(fLifeline, llLayout, width, 0, 0, 1, 1, 0, 0, new Insets(0, 0, 0, 0)));
		fLifeline.add(width);
		llLayout.setConstraints(ll_distance, addComponent(fLifeline, llLayout, ll_distance, 0, 1, 3, 6, 0, 0, new Insets(0, 0, 0, 0)));
		fLifeline.add(ll_distance);
		llLayout.setConstraints(fActLabel, addComponent(dist, distLayout, fActLabel, 0, 0, 1, 1, 0, 0, new Insets(0, 0, 0, 0)));
		dist.add(fActLabel);
		llLayout.setConstraints(act_Distance, addComponent(dist, distLayout, act_Distance, 1, 0, 1, 1, 0, 0, new Insets(0, 0, 0, 0)));
		dist.add(act_Distance);
		llLayout.setConstraints(dist, addComponent(fLifeline, llLayout, dist, 1, 8, 1, 1, 0, 0, new Insets(0, 0, 0, 0)));
		fLifeline.add(dist);
		
		// Size of state nodes
		JPanel stateNodeSizePanel = new JPanel();
		stateNodeSizePanel.setBorder(BorderFactory.createTitledBorder("Size of state nodes"));
		GridBagLayout stateNodeLayout = new GridBagLayout();
		stateNodeSizePanel.setLayout(stateNodeLayout);
				
		stateNodeWidthLabel = new JLabel("Width: ");
		stateNodeHeightLabel = new JLabel("Height: ");

		ButtonGroup stateNodesWidthButtonGroup = new ButtonGroup();
		stateNodeIndivitualBtn = new JRadioButton("Fit to name");
		stateNodeManualBtn = new JRadioButton("Manual");
		stateNodesWidthButtonGroup.add(stateNodeIndivitualBtn);
		stateNodesWidthButtonGroup.add(stateNodeManualBtn);

		stateNodeManualWidthField = new JTextField(new NumberDocument(), "", 3);
		stateNodeManualWidthField.setText(Integer.toString(fSeqDiag.getProperties().getStateNodeWidth()));
		stateNodeManualHeightField = new JTextField(new NumberDocument(), "", 3);
		stateNodeManualHeightField.setText(Integer.toString(fSeqDiag.getProperties().getStateNodeHeight()));
				
		stateNodeLayout.setConstraints(stateNodeIndivitualBtn, addComponent(stateNodeSizePanel, stateNodeLayout, stateNodeIndivitualBtn, 0, 0, 4, 1, 0, 0, new Insets(0, 0, 0, 0)));
		stateNodeSizePanel.add(stateNodeIndivitualBtn);
				
		stateNodeLayout.setConstraints(stateNodeManualBtn, addComponent(stateNodeSizePanel, stateNodeLayout, stateNodeManualBtn, 0, 1, 4, 1, 0, 0, new Insets(0, 0, 0, 0)));
		stateNodeSizePanel.add(stateNodeManualBtn);
						
		stateNodeLayout.setConstraints(stateNodeWidthLabel, addComponent(stateNodeSizePanel, stateNodeLayout, stateNodeWidthLabel, 1, 2, 1, 1, 0, 0, new Insets(0, 30, 0, 0)));
		stateNodeSizePanel.add(stateNodeWidthLabel);
						
		stateNodeLayout.setConstraints(stateNodeManualWidthField, addComponent(stateNodeSizePanel, stateNodeLayout, stateNodeManualWidthField, 2, 2, 1, 1, 0, 0, new Insets(0, 0, 0, 0)));
		stateNodeSizePanel.add(stateNodeManualWidthField);
						
		stateNodeLayout.setConstraints(stateNodeHeightLabel, addComponent(stateNodeSizePanel, stateNodeLayout, stateNodeHeightLabel, 1, 3, 1, 1, 0, 0, new Insets(0, 30, 0, 0)));
		stateNodeSizePanel.add(stateNodeHeightLabel);
						
		stateNodeLayout.setConstraints(stateNodeManualHeightField, addComponent(stateNodeSizePanel, stateNodeLayout, stateNodeManualHeightField, 2, 3, 1, 1, 0, 0, new Insets(0, 0, 0, 0)));
		stateNodeSizePanel.add(stateNodeManualHeightField);
						
		llLayout.setConstraints(stateNodeSizePanel, addComponent(fLifeline, llLayout, stateNodeSizePanel, 0, 8, 1, 1, 0, 0, new Insets(0, 0, 0, 0)));
		fLifeline.add(stateNodeSizePanel);
		
		fTabbedPane.add(fDiag, "Diagram", 0);
		fTabbedPane.addTab("Lifelines", fLifeline);
		fTabbedPane.addTab("ObjectBox", fObjectBox);

		fC.add("Center", fTabbedPane);

		// OK-/Cancel-Buttons
		JPanel buttons = new JPanel();
		JButton ok = new OKButton("Ok");
		ok.addActionListener(this);
		buttons.add(ok);
		JButton cancel = new CancelButton("Cancel");
		cancel.addActionListener(this);
		buttons.add(cancel);
		fC.add("South", buttons);

		if (fSeqDiag.getObProperties().isLineBreakLabel()) {
			lineBreakLabelYesBtn.setSelected(true);
		} else {
			lineBreakLabelNoBtn.setSelected(true);
		}

		if (fSeqDiag.getProperties().isStateNodeManualSize()) {
			stateNodeManualBtn.setEnabled(true);
			stateNodeManualBtn.setSelected(true);
			stateNodeWidthLabel.setEnabled(true);
			stateNodeManualWidthField.setEnabled(true);
			stateNodeManualWidthField.setEditable(true);
			stateNodeHeightLabel.setEnabled(true);
			stateNodeManualHeightField.setEnabled(true);
			stateNodeManualHeightField.setEditable(true);
		} else {
			stateNodeIndivitualBtn.setEnabled(true);
			stateNodeIndivitualBtn.setSelected(true);
			stateNodeWidthLabel.setEnabled(false);
			stateNodeManualWidthField.setEnabled(false);
			stateNodeManualWidthField.setEditable(false);
			stateNodeHeightLabel.setEnabled(false);
			stateNodeManualHeightField.setEnabled(false);
			stateNodeManualHeightField.setEditable(false);
		}

		if (!fSeqDiag.getProperties().llLikeLongMess()) {
			// manual distance is selected
			fLl_manDist.setSelected(true);
			fLlB1.setEnabled(false);
			fLlB2.setEnabled(false);
			fLl_manLabel.setEnabled(true);
			fLl_manLabel.setSelected(true);
			fLl_individual.setEnabled(true);
			if (!fSeqDiag.getProperties().individualLl()) {
				fLl_ManDist.setEnabled(true);
				fLl_ManDist.setEditable(true);
				fLl_ManDist.setText(Integer.toString(fSeqDiag.getProperties().llStep()));
			} else {
				fLl_ManDist.setEnabled(false);
				fLl_ManDist.setEditable(false);
				fLl_ManDist.setText(Integer.toString(fSeqDiag.getProperties().llStep()));
			}
		} else {
			// manual distance is selected
			fLl_textLength.setSelected(true);
			fLlB1.setEnabled(true);
			fLlB2.setEnabled(true);
			fLl_manLabel.setEnabled(false);
			fLl_individual.setEnabled(false);
			fLl_ManDist.setEditable(false);
			fLl_ManDist.setText(Integer.toString(fSeqDiag.getProperties().llStep()));
			if (!fSeqDiag.getProperties().individualLl()) {
				fLlB1.setSelected(true);
			} else {
				fLlB2.setSelected(true);
			}
		}

		if (fSeqDiag.getObProperties().manWidth()) {
			fObWidth_B2.setSelected(true);
			fManW.setEditable(true);
			fManW.setText(Integer.toString(fSeqDiag.getObProperties().getWidth()));
			fWidthB1.setEnabled(false);
			fWidthB2.setEnabled(false);
		} else {
			fObWidth_B1.setSelected(true);
			fManW.setText(Integer.toString(fSeqDiag.getObProperties().getWidth()));
			fManW.setEditable(false);
			fManW.setEnabled(false);
			fWidthB1.setEnabled(true);
			fWidthB2.setEnabled(true);
			fWidthB1.setSelected(fSeqDiag.getObProperties().sameWidth());
			fWidthB2.setSelected(!fSeqDiag.getObProperties().sameWidth());
		}

		if (fSeqDiag.getObProperties().manHeight()) {
			fObHeight_B2.setSelected(true);
			fManH.setEditable(true);
			fManH.setText(Integer.toString(fSeqDiag.getObProperties().getHeight()));
			fHeightB1.setEnabled(false);
			fHeightB2.setEnabled(false);
		} else {
			fObHeight_B1.setSelected(true);
			fManH.setText(Integer.toString(fSeqDiag.getObProperties().getHeight()));
			fManH.setEditable(false);
			fManH.setEnabled(false);
			fHeightB1.setEnabled(true);
			fHeightB2.setEnabled(true);
			fHeightB1.setSelected(fSeqDiag.getObProperties().sameHeight());
			fHeightB2.setSelected(!fSeqDiag.getObProperties().sameHeight());
		}

		lineBreakLabelYesBtn.addActionListener(this);
		lineBreakLabelNoBtn.addActionListener(this);

		ItemListener stateNodeWidthRadioButtonListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (stateNodeIndivitualBtn.isSelected()) {
					stateNodeWidthLabel.setEnabled(false);
					stateNodeManualWidthField.setEnabled(false);
					stateNodeManualWidthField.setEditable(false);
					stateNodeHeightLabel.setEnabled(false);
					stateNodeManualHeightField.setEnabled(false);
					stateNodeManualHeightField.setEditable(false);
				} else if (stateNodeManualBtn.isSelected()) {
					stateNodeWidthLabel.setEnabled(true);
					stateNodeManualWidthField.setEnabled(true);
					stateNodeManualWidthField.setEditable(true);
					stateNodeHeightLabel.setEnabled(true);
					stateNodeManualHeightField.setEnabled(true);
					stateNodeManualHeightField.setEditable(true);
				}
			}
		};
		stateNodeIndivitualBtn.addItemListener(stateNodeWidthRadioButtonListener);
		stateNodeManualBtn.addItemListener(stateNodeWidthRadioButtonListener);

		ItemListener radioButtonListener1 = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (fLl_textLength.isSelected()) {
					fLlB1.setEnabled(true);
					fLlB2.setEnabled(true);
					fLl_manLabel.setEnabled(false);
					fLl_individual.setEnabled(false);
					fLl_ManDist.setEditable(false);
				} else if (fLl_manDist.isSelected()) {
					fLl_manDist.setSelected(true);
					fLlB1.setEnabled(false);
					fLlB2.setEnabled(false);
					fLl_manLabel.setEnabled(true);
					fLl_individual.setEnabled(true);

					if (fLl_manLabel.isSelected()) {
						// textfield for manual distance is activated
						fLl_ManDist.setEnabled(true);
						fLl_ManDist.setEditable(true);
						fLl_ManDist.setText(Integer.toString(fSeqDiag.getProperties().llStep()));
					} else if (fLl_individual.isSelected()) {
						fLl_ManDist.setEnabled(false);
						fLl_ManDist.setEditable(false);
						fLl_ManDist.setText(Integer.toString(fSeqDiag.getProperties().llStep()));
					}

				}

			}

		};
		fLl_textLength.addItemListener(radioButtonListener1);
		fLl_manDist.addItemListener(radioButtonListener1);

		ItemListener radioButtonListener2 = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (fLl_manLabel.isSelected()) {
					// textfield for manual distance is activated
					fLl_ManDist.setEnabled(true);
					fLl_ManDist.setEditable(true);
					fLl_ManDist.setText(Integer.toString(fSeqDiag.getProperties().llStep()));
				} else if (fLl_individual.isSelected()) {
					fLl_ManDist.setEnabled(false);
					fLl_ManDist.setEditable(false);
					fLl_ManDist.setText(Integer.toString(fSeqDiag.getProperties().llStep()));
				}

			}

		};
		fLl_manLabel.addItemListener(radioButtonListener2);
		fLl_individual.addItemListener(radioButtonListener2);

		ItemListener radioButtonListener3 = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (fObWidth_B1.isSelected()) {
					fWidthB1.setEnabled(true);
					fWidthB2.setEnabled(true);
					fWidthB1.setSelected(fSeqDiag.getObProperties().sameWidth());
					fWidthB2.setSelected(!fSeqDiag.getObProperties().sameWidth());
					fManW.setEditable(false);
					fManW.setEnabled(false);
				} else if (fObWidth_B2.isSelected()) {
					fWidthB1.setEnabled(false);
					fWidthB2.setEnabled(false);
					fManW.setEditable(true);
					fManW.setEnabled(true);
				}
			}
		};
		fObWidth_B1.addItemListener(radioButtonListener3);
		fObWidth_B2.addItemListener(radioButtonListener3);

		ItemListener radioButtonListener4 = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (fObHeight_B1.isSelected()) {
					fHeightB1.setEnabled(true);
					fHeightB2.setEnabled(true);
					fHeightB1.setSelected(fSeqDiag.getObProperties().sameHeight());
					fHeightB2.setSelected(!fSeqDiag.getObProperties().sameHeight());
					fManH.setEditable(false);
					fManH.setEnabled(false);
				} else if (fObHeight_B2.isSelected()) {
					fHeightB1.setEnabled(false);
					fHeightB2.setEnabled(false);
					fManH.setEditable(true);
					fManH.setEnabled(true);
				}
			}
		};
		fObHeight_B1.addItemListener(radioButtonListener4);
		fObHeight_B2.addItemListener(radioButtonListener4);
		pack();
	}

	/**
	 * Method for adding Components in a GridBagLayout.
	 * 
	 * @param panel the panel in which the component should be added
	 * @param gbl the layout of the panel
	 * @param c the component to be added
	 * @param x the x-value for the layout
	 * @param y the y-value for the layout
	 * @param width the width-value
	 * @param height the height-value
	 * @param weightx the weighty-value
	 * @param weighty the weighty-value
	 */
	public GridBagConstraints addComponent(JPanel panel, GridBagLayout gbl, Component c, int x, int y, int width, int height, double weightx, double weighty,
			Insets insets) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.insets = insets;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		return gbc;

	}

	/**
	 * Shows the Window.
	 */
	public void showWindow() {
		PropertiesWindow propW = new PropertiesWindow(fSeqDiag);
		propW.setLocation(300, 200);
		propW.setVisible(true);
	}

	/**
	 * Analyses the Entries and adjusts the values in the properties objects of
	 * sthe seuqeunce fDiagram.
	 */
	public void actionPerformed(ActionEvent e) {

		if ("Ok".equals(e.getActionCommand())) {
			boolean dispose = true;

			if (lineBreakLabelYesBtn.isSelected()) {
				fSeqDiag.getObProperties().setMaxHeight(0);
				fSeqDiag.getObProperties().setLineBreak(true);
			} else {
				fSeqDiag.getObProperties().setMaxHeight(0);
				fSeqDiag.getObProperties().setLineBreak(false);
			}

			if (stateNodeIndivitualBtn.isSelected()) {
				fSeqDiag.getProperties().setStateNodeManualSize(false);
			} else {
				fSeqDiag.getProperties().setStateNodeWidth(Integer.valueOf(stateNodeManualWidthField.getText()).intValue());
				fSeqDiag.getProperties().setStateNodeHeight(Integer.valueOf(stateNodeManualHeightField.getText()).intValue());
				fSeqDiag.getProperties().setStateNodeManualSize(true);
			}

			if (fLeftMargin.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Enter a value for the left margin!", "No entry", JOptionPane.ERROR_MESSAGE);
				dispose = false;
				fTabbedPane.setSelectedComponent(fDiag);
			} else {
				fSeqDiag.getProperties().setLeftMargin(Integer.valueOf(fLeftMargin.getText()).intValue());
			}
			if (fRightMargin.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Enter a value for the right margin!", "No entry", JOptionPane.ERROR_MESSAGE);
				dispose = false;
				fTabbedPane.setSelectedComponent(fDiag);
			} else {
				fSeqDiag.getProperties().setRightMargin(Integer.valueOf(fRightMargin.getText()).intValue());
			}
			if (fTopMargin.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Enter a value for the top margin!", "No entry", JOptionPane.ERROR_MESSAGE);
				dispose = false;
				fTabbedPane.setSelectedComponent(fDiag);
			} else {
				fSeqDiag.getProperties().setTopMargin(Integer.valueOf(fTopMargin.getText()).intValue());
			}
			if (fBottomMargin.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Enter a value for the bottom margin!", "No entry", JOptionPane.ERROR_MESSAGE);
				dispose = false;
				fTabbedPane.setSelectedComponent(fDiag);
			} else {
				fSeqDiag.getProperties().setBottomMargin(Integer.valueOf(fBottomMargin.getText()).intValue());
			}

			if (fAct_ManDist.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Enter a value for the Activation distance!", "No entry", JOptionPane.ERROR_MESSAGE);
				dispose = false;
				fTabbedPane.setSelectedComponent(fLifeline);
			} else {
				fSeqDiag.getProperties().setAct_manDist(Integer.valueOf(fAct_ManDist.getText()).intValue());
			}

			if (fLl_Width.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Enter a value for the frame-width!", "No entry", JOptionPane.ERROR_MESSAGE);
				dispose = false;
				fTabbedPane.setSelectedComponent(fLifeline);
			} else {
				fSeqDiag.getProperties().setFrameWidth((Integer.valueOf(fLl_Width.getText())).intValue());
			}

			fSeqDiag.getProperties().setFont((String) fFontBox.getSelectedItem());
			fSeqDiag.getProperties().setFontSize(((Integer) fSizeBox.getSelectedItem()).intValue());

			if (fObWidth_B2.isSelected()) {
				if (fManW.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Enter a width for the ObjectBox!", "No entry", JOptionPane.ERROR_MESSAGE);
					dispose = false;
					fTabbedPane.setSelectedComponent(fObjectBox);
				} else {
					fSeqDiag.getObProperties().setWidth((Integer.valueOf(fManW.getText())).intValue());
					fSeqDiag.getObProperties().setSameWidth(true);
					fSeqDiag.getObProperties().setManWidth(true);
				}
			} else {
				fSeqDiag.getObProperties().setManWidth(false);
				fSeqDiag.getObProperties().setSameWidth(fWidthB1.isSelected());
			}

			if (fObHeight_B2.isSelected()) {
				if (fManH.getText().length() == 0) {
					dispose = false;
					JOptionPane.showMessageDialog(null, "Enter a height for the ObjectBox!", "No entry", JOptionPane.ERROR_MESSAGE);
					fTabbedPane.setSelectedComponent(fObjectBox);
				} else {
					fSeqDiag.getObProperties().setHeight((Integer.valueOf(fManH.getText())).intValue());
					fSeqDiag.getObProperties().setSameHeight(true);
					fSeqDiag.getObProperties().setManHeight(true);
				}
			} else {
				fSeqDiag.getObProperties().setManHeight(false);
				fSeqDiag.getObProperties().setSameHeight(fHeightB1.isSelected());
			}

			if (fLl_manDist.isSelected()) {
				fSeqDiag.getProperties().llLikeLongMess(false);
				fSeqDiag.getProperties().setIndividualLl(fLl_individual.isSelected());
				if (fLl_manLabel.isSelected()) {
					if (fLl_ManDist.getText().length() == 0) {
						dispose = false;
						JOptionPane.showMessageDialog(null, "Enter a distance for the Lifelines!", "No entry", JOptionPane.ERROR_MESSAGE);
						fTabbedPane.setSelectedComponent(fLifeline);
					} else {
						fSeqDiag.getProperties().setLlStep((Integer.valueOf(fLl_ManDist.getText())).intValue());
					}
				}
			} else if (fLl_textLength.isSelected()) {
				fSeqDiag.getProperties().llLikeLongMess(true);
				fSeqDiag.getProperties().setIndividualLl(fLlB2.isSelected());
			}
			fSeqDiag.restoreAllValues();
			if (dispose)
				dispose();
		}

		if ("Cancel".equals(e.getActionCommand())) {
			dispose();
		}

	}

	/**
	 * A Document on which only numbers are allowed.
	 * 
	 * @author Antje Werner
	 */
	public static class NumberDocument extends PlainDocument {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Inserts a String in the Document.
		 * 
		 * @param offset position on which the string should be added
		 * @param text the Text taht should be added
		 * @param attributeSet the formatting of the text
		 * @exception BadLocationException if the text should be added on a
		 *                position that not exists
		 */

		public void insertString(int offset, String text, AttributeSet attributeSet) throws BadLocationException {
			if (this.isNumber(text)) {
				super.insertString(offset, text, attributeSet);
			}
		}

		/**
		 * Verifies, if the entry is a number.
		 * 
		 * @param toTest the string taht should be verified
		 * @return true, is toTest is a number; false otherwise
		 */
		protected boolean isNumber(String toTest) {
			try {
				if (toTest.length() > 0)
					Integer.valueOf(toTest);
				return true;
			} catch (final NumberFormatException ignored) {
				// ignored
			}
			return false;
		}

	}

}