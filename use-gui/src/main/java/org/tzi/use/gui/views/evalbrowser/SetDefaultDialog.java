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

package org.tzi.use.gui.views.evalbrowser;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Set-Default-Configuration Dialog
 */
class SetDefaultDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 474482957709731454L;
	/**
	 * 
	 */
	private final ExprEvalBrowser exprEvalBrowser;

	public SetDefaultDialog(final ExprEvalBrowser exprEvalBrowser, JFrame mother) {
        super(mother, "Evaluation browser configuration", true);
		this.exprEvalBrowser = exprEvalBrowser;
        Container c = getContentPane();
        c.setLayout(new GridLayout(2, 1));
        JLabel titleLabel = new JLabel(
                "Set current evaluation browser configurations as default");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        c.add(titleLabel);
        JPanel p = new JPanel(new FlowLayout());
        JButton b1 = new JButton("For this session");
        // action for setting the defaults for only the current session
        b1.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent event) {
                if (SetDefaultDialog.this.exprEvalBrowser.fExtendedExists.isSelected())
                    System.setProperty("use.gui.view.evalbrowser.exists",
                            "true");
                else
                    System.setProperty("use.gui.view.evalbrowser.exists",
                            "false");
                if (SetDefaultDialog.this.exprEvalBrowser.fExtendedForAll.isSelected())
                    System.setProperty("use.gui.view.evalbrowser.forall",
                            "true");
                else
                    System.setProperty("use.gui.view.evalbrowser.forall",
                            "false");
                if (SetDefaultDialog.this.exprEvalBrowser.fExtendedOr.isSelected())
                    System.setProperty("use.gui.view.evalbrowser.or",
                            "true");
                else
                    System.setProperty("use.gui.view.evalbrowser.or",
                            "false");
                if (SetDefaultDialog.this.exprEvalBrowser.fExtendedAnd.isSelected())
                    System.setProperty("use.gui.view.evalbrowser.and",
                            "true");
                else
                    System.setProperty("use.gui.view.evalbrowser.and",
                            "false");
                if (SetDefaultDialog.this.exprEvalBrowser.fExtendedImplies.isSelected())
                    System.setProperty("use.gui.view.evalbrowser.implies",
                            "true");
                else
                    System.setProperty("use.gui.view.evalbrowser.implies",
                            "false");
                if (SetDefaultDialog.this.exprEvalBrowser.fVarAssListChk.isSelected())
                    System.setProperty(
                            "use.gui.view.evalbrowser.VarAssignmentWindow",
                            "true");
                else
                    System.setProperty(
                            "use.gui.view.evalbrowser.VarAssignmentWindow",
                            "false");
                if (SetDefaultDialog.this.exprEvalBrowser.fVarSubstituteWinChk.isSelected())
                    System
                            .setProperty(
                                    "use.gui.view.evalbrowser.SubExprSubstitutionWindow",
                                    "true");
                else
                    System
                            .setProperty(
                                    "use.gui.view.evalbrowser.SubExprSubstitutionWindow",
                                    "false");
                if (SetDefaultDialog.this.exprEvalBrowser.rbVariableAssignment[0].isSelected())
                    System.setProperty("use.gui.view.evalbrowser.treeview",
                            "lateVarAssignment");
                else if (SetDefaultDialog.this.exprEvalBrowser.rbVariableAssignment[1].isSelected())
                    System.setProperty("use.gui.view.evalbrowser.treeview",
                            "earlyVarAssignment");
                else if (SetDefaultDialog.this.exprEvalBrowser.rbVariableAssignment[2].isSelected())
                    System.setProperty("use.gui.view.evalbrowser.treeview",
                            "VarAssignment&Substitution");
                else if (SetDefaultDialog.this.exprEvalBrowser.rbVariableAssignment[3].isSelected())
                    System.setProperty("use.gui.view.evalbrowser.treeview",
                            "VarSubstitution");
                else if (SetDefaultDialog.this.exprEvalBrowser.rbVariableAssignment[4].isSelected())
                    System.setProperty("use.gui.view.evalbrowser.treeview",
                            "noVarAssignment");
                if (SetDefaultDialog.this.exprEvalBrowser.fTreeHighlightings[0].isSelected())
                    System.setProperty(
                            "use.gui.view.evalbrowser.highlighting", "no");
                else if (SetDefaultDialog.this.exprEvalBrowser.fTreeHighlightings[1].isSelected())
                    System.setProperty(
                            "use.gui.view.evalbrowser.highlighting", "term");
                else if (SetDefaultDialog.this.exprEvalBrowser.fTreeHighlightings[2].isSelected())
                    System.setProperty(
                            "use.gui.view.evalbrowser.highlighting",
                            "subtree");
                else if (SetDefaultDialog.this.exprEvalBrowser.fTreeHighlightings[3].isSelected())
                    System.setProperty(
                            "use.gui.view.evalbrowser.highlighting",
                            "complete");
                if (SetDefaultDialog.this.exprEvalBrowser.fNoColorHighlightingChk.isSelected())
                    System.setProperty(
                            "use.gui.view.evalbrowser.blackHighlighting",
                            "true");
                else
                    System.setProperty(
                            "use.gui.view.evalbrowser.blackHighlighting",
                            "false");
                setVisible(false);
                dispose();
            }
        });
        JButton b2 = new JButton("For all sessions");
        // action for setting the defaults and save them persistently in the
        // config file
        b2.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent event) {
                StringBuilder s = new StringBuilder();
                File f = new File(System.getProperty("user.dir", null),
                        ".userc");
                if (!f.exists())
                    f = new File(System.getProperty("user.home", null),
                            ".userc");
                try {
                    // create new config-file if there was no
                    if (!f.exists())
                        f.createNewFile();
                    // f = new File(System.getProperty("user.home", null),
                    // ".userc");
                    // read the config file int string s
                    try(FileReader reader = new FileReader(f)){
	                    for (int c; (c = reader.read()) != -1;) {
	                        s.append((char) c);
	                    }
                    }
                    if (s.length() != 0 && s.charAt(s.length() - 1) != '\n')
                        s.append("\n");
                    
                    String str = s.toString();
                    // reconfigure s
                    str = setConfigPoint(str,
                            "use.gui.view.evalbrowser.exists",
                            SetDefaultDialog.this.exprEvalBrowser.fExtendedExists.isSelected());
                    str = setConfigPoint(str,
                            "use.gui.view.evalbrowser.forall",
                            SetDefaultDialog.this.exprEvalBrowser.fExtendedForAll.isSelected());
                    str = setConfigPoint(str, "use.gui.view.evalbrowser.and",
                            SetDefaultDialog.this.exprEvalBrowser.fExtendedAnd.isSelected());
                    str = setConfigPoint(str, "use.gui.view.evalbrowser.or",
                            SetDefaultDialog.this.exprEvalBrowser.fExtendedOr.isSelected());
                    str = setConfigPoint(str,
                            "use.gui.view.evalbrowser.implies",
                            SetDefaultDialog.this.exprEvalBrowser.fExtendedImplies.isSelected());
                    str = setConfigPoint(str,
                            "use.gui.view.evalbrowser.VarAssignmentWindow",
                            SetDefaultDialog.this.exprEvalBrowser.fVarAssListChk.isSelected());
                    str = setConfigPoint(
                            str,
                            "use.gui.view.evalbrowser.SubExprSubstitutionWindow",
                            SetDefaultDialog.this.exprEvalBrowser.fVarSubstituteWinChk.isSelected());
                    if (SetDefaultDialog.this.exprEvalBrowser.rbVariableAssignment[0].isSelected())
                        str = setConfigPoint(str,
                                "use.gui.view.evalbrowser.treeview",
                                "lateVarAssignment");
                    else if (SetDefaultDialog.this.exprEvalBrowser.rbVariableAssignment[1].isSelected())
                        str = setConfigPoint(str,
                                "use.gui.view.evalbrowser.treeview",
                                "earlyVarAssignment");
                    else if (SetDefaultDialog.this.exprEvalBrowser.rbVariableAssignment[2].isSelected())
                        str = setConfigPoint(str,
                                "use.gui.view.evalbrowser.treeview",
                                "VarAssignment&Substitution");
                    else if (SetDefaultDialog.this.exprEvalBrowser.rbVariableAssignment[3].isSelected())
                        str = setConfigPoint(str,
                                "use.gui.view.evalbrowser.treeview",
                                "VarSubstitution");
                    else if (SetDefaultDialog.this.exprEvalBrowser.rbVariableAssignment[4].isSelected())
                        str = setConfigPoint(str,
                                "use.gui.view.evalbrowser.treeview",
                                "noVarAssignment");
                    if (SetDefaultDialog.this.exprEvalBrowser.fTreeHighlightings[0].isSelected())
                        str = setConfigPoint(str,
                                "use.gui.view.evalbrowser.highlighting", "no");
                    else if (SetDefaultDialog.this.exprEvalBrowser.fTreeHighlightings[1].isSelected())
                        str = setConfigPoint(str,
                                "use.gui.view.evalbrowser.highlighting",
                                "term");
                    else if (SetDefaultDialog.this.exprEvalBrowser.fTreeHighlightings[2].isSelected())
                        str = setConfigPoint(str,
                                "use.gui.view.evalbrowser.highlighting",
                                "subtree");
                    else if (SetDefaultDialog.this.exprEvalBrowser.fTreeHighlightings[3].isSelected())
                        str = setConfigPoint(str,
                                "use.gui.view.evalbrowser.highlighting",
                                "complete");
                    str = setConfigPoint(str,
                            "use.gui.view.evalbrowser.blackHighlighting",
                            SetDefaultDialog.this.exprEvalBrowser.fNoColorHighlightingChk.isSelected());

                    // rewrite the config file
                    try(FileWriter writer = new FileWriter(f)){
	                    for (int i = 0; i < str.length(); i++){
	                    	writer.write(str.charAt(i));
	                    }
                    }
                } catch (IOException e) {
					JOptionPane.showConfirmDialog(
							exprEvalBrowser,
							"Error",
							"Error while saving configuration!\n"
									+ e.getMessage(),
							JOptionPane.OK_OPTION,
							JOptionPane.ERROR_MESSAGE);
                }
                setVisible(false);
                dispose();

            }
        });
        JButton b3 = new JButton("Cancel");
        // cancel-Action
        b3.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent event) {
                setVisible(false);
                dispose();
            }
        });
        p.add(b1);
        p.add(b2);
        p.add(b3);
        c.add(p);
        pack();
        setVisible(true);
    }

    /**
     * sets the desired value to the attribute in the configure file. this
     * method is for the non-boolean attributes.
     * 
     * @return the changed content of the configure file
     */
    public String setConfigPoint(String confFileContent, String attr,
            String val) {
        confFileContent = "\n" + confFileContent;
        String escaptedAttr = "";
        for (int i = 0; i < attr.length(); i++) {
            if (attr.charAt(i) == '.')
                escaptedAttr += "\\.";
            else
                escaptedAttr += attr.charAt(i);
        }
        Pattern p = Pattern.compile("\n" + escaptedAttr);
        String parts[] = p.split(confFileContent);
        confFileContent = "";
        for (int i = 0; i < parts.length; i++) {
            if (i == parts.length - 1) {
                confFileContent += truncatePart(parts[i]);
            } else {
                System.setProperty(attr, val);
                confFileContent += truncatePart(parts[i]) + "\n" + attr
                        + "=" + val;
            }
        }
        if (parts.length == 1) {
            if (confFileContent.length() != 0
                    && confFileContent.charAt(confFileContent.length() - 1) != '\n')
                confFileContent += "\n";
            System.setProperty(attr, val);
            confFileContent += attr + "=" + val + "\n";
        }
        return confFileContent.substring(1);

    }

    /**
     * sets the desired value to the attribute in the configure file this
     * method is for the boolean attributes.
     * 
     * @return the changed content of the configure file
     */
    public String setConfigPoint(String confFileContent, String attr,
            boolean boolVal) {
        String val;
        if (boolVal)
            val = "true";
        else
            val = "false";
        confFileContent = "\n" + confFileContent;
        String escaptedAttr = "";
        for (int i = 0; i < attr.length(); i++) {
            if (attr.charAt(i) == '.')
                escaptedAttr += "\\.";
            else
                escaptedAttr += attr.charAt(i);
        }
        Pattern p = Pattern.compile("\n" + escaptedAttr);
        String parts[] = p.split(confFileContent);
        confFileContent = "";
        for (int i = 0; i < parts.length; i++) {
            if (i == parts.length - 1) {
                confFileContent += truncatePart(parts[i]);
            } else {
                System.setProperty(attr, val);
                confFileContent += truncatePart(parts[i]) + "\n" + attr
                        + "=" + val;
            }
        }
        if (parts.length == 1) {
            if (confFileContent.length() != 0
                    && confFileContent.charAt(confFileContent.length() - 1) != '\n')
                confFileContent += "\n";
            System.setProperty(attr, val);
            confFileContent += attr + "=" + val + "\n";
        }
        return confFileContent.substring(1);
    }

    /**
     * detaches the value from the rest of the part of the config file
     * 
     * @param part
     *            the part of the config file
     * @return the rest of the part of the config file
     */
    private String truncatePart(String part) {
        for (int i = 0; i < part.length(); i++)
            if (part.charAt(i) == '\n') {
                return part.substring(i);
            }
        return "";
    }
} // SetDefaultDialog