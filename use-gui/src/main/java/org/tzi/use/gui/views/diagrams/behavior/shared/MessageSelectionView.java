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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Antje Werner
 * @author Thomas Schaefer
 * @author Carsten Schlobohm
 */
public class MessageSelectionView  extends JDialog implements ActionListener {
    public interface MessageSelectionDelegate {
        void selectMessageFromToAndDepth(int from, int to, int depth);
        java.util.List<String> messageLabels();
        int getMessageDepth();
    }
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * The container for this Window.
     */
    private Container contentContainer;

    private JComboBox<String> messageDepthComboBox;

    private MessageSelectionDelegate delegate;

    private boolean withDepth = false;

    /**
     * The from/tillComboBox
     */
    private JComboBox<String> tillComboBox, fromComboBox;

    public MessageSelectionView(MessageSelectionDelegate delegate, boolean withDepth) {
        setTitle("Choose the area of Messages");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.delegate = delegate;
        this.withDepth = withDepth;
        contentContainer = getContentPane();
        contentContainer.setLayout(new BorderLayout(20, 10));

        JLabel label1 = new JLabel("");
        JLabel label2 = new JLabel("");
        JLabel label3 = new JLabel("");

        JPanel fromTillPanel = new JPanel();
        int rows = (withDepth) ? 4 : 2;
        fromTillPanel.setLayout(new GridLayout(rows, 2));
        fromComboBox = new JComboBox<String>();
        fromComboBox.addActionListener(this);
        tillComboBox = new JComboBox<String>();
        JLabel fromLabel = new JLabel("From");
        JLabel tillLabel = new JLabel("Till");

        for (String text : delegate.messageLabels()) {
            fromComboBox.addItem(text);
        }
        fromTillPanel.add(fromLabel);
        fromTillPanel.add(tillLabel);
        fromTillPanel.add(fromComboBox);
        fromTillPanel.add(tillComboBox);


        messageDepthComboBox = new JComboBox<>();
        if (withDepth) {
            JLabel placeholder1 = new JLabel("");
            JLabel placeholder2 = new JLabel("");
            fromTillPanel.add(placeholder1);
            fromTillPanel.add(placeholder2);

            JLabel messageDepthLabel = new JLabel("Select message depth");

            String comboBoxItem = ": m";

            int messageDepth = delegate.getMessageDepth();

            for (int i = 1; i <= messageDepth; i++) {

                if(i == 1){
                    comboBoxItem = i + comboBoxItem;
                }else{
                    comboBoxItem = i + "." + comboBoxItem;
                }
                messageDepthComboBox.addItem(comboBoxItem);
                messageDepthComboBox.setSelectedItem(comboBoxItem);
            }

            fromTillPanel.add(messageDepthLabel);
            fromTillPanel.add(messageDepthComboBox);
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
        contentContainer.add("Center", fromTillPanel);
        contentContainer.add("South", buttonsPanel);
        pack();
    }


    public void showWindow() {
        this.setSize(350, 200);
        this.setLocation(300, 200);
        this.setVisible(true);
    }

    /**
     * Analyses the entries made in the window and make a treemap, who contains
     * all Messages, that shows up.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == fromComboBox){

            tillComboBox.removeAllItems();

            for (int i = fromComboBox.getSelectedIndex()+1; i < delegate.messageLabels().size(); i++) {
                tillComboBox.addItem(delegate.messageLabels().get(i));
            }
            // select the last item by default
            tillComboBox.setSelectedIndex(tillComboBox.getModel().getSize() - 1);
        }else{
            if ("Ok".equals(e.getActionCommand())) {
                delegate.selectMessageFromToAndDepth(
                        fromComboBox.getSelectedIndex(),
                        fromComboBox.getSelectedIndex() + tillComboBox.getSelectedIndex()+1,
                        (withDepth) ? messageDepthComboBox.getSelectedIndex()+1 : 0);
                dispose();
            }
            if ("Cancel".equals(e.getActionCommand())) {
                dispose();
            }
        }

    }

}
