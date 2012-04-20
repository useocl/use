/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
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

package org.tzi.use.gui.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.tzi.use.config.Options;
import org.tzi.use.config.Options.SoilPermissionLevel;
import org.tzi.use.gui.util.CloseOnEscapeKeyListener;
import org.tzi.use.gui.util.TextComponentWriter;
import org.tzi.use.gui.views.ExprEvalBrowser;
import org.tzi.use.main.ChangeEvent;
import org.tzi.use.main.ChangeListener;
import org.tzi.use.main.Session;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.TeeWriter;

/**
 * A dialog for entering and evaluating OCL expressions.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Mark Richters
 */
@SuppressWarnings("serial")
class EvalOCLDialog extends JDialog {
    private MSystem fSystem;

    private JTextArea fTextIn;

    private JTextArea fTextOut;

    private ExprEvalBrowser fEvalBrowser;

    private Evaluator evaluator;

    private JButton btnEvalBrowser;
    
    private JButton btnEval;
    
    EvalOCLDialog(Session session, JFrame parent) {
        super(parent, "Evaluate OCL expression");
        fSystem = session.system();
        session.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Session session = (Session)e.getSource();
				
				fSystem = session.hasSystem() ? session.system() : null;
				btnEvalBrowser.setEnabled(session.hasSystem());
				btnEval.setEnabled(session.hasSystem());
			}
		});
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // create text components and labels
        fTextIn = new JTextArea();
        fTextIn.setFont(new Font("Monospaced", Font.PLAIN, getFont().getSize()));
        JLabel textInLabel = new JLabel("Enter OCL expression:");
        textInLabel.setDisplayedMnemonic('O');
        textInLabel.setLabelFor(fTextIn);
        fTextOut = new JTextArea();
        fTextOut.setEditable(false);
        JLabel textOutLabel = new JLabel("Result:");
        textOutLabel.setLabelFor(fTextOut);

        // create panel on the left and add text components
        JPanel textPane = new JPanel();
        textPane.setLayout(new BoxLayout(textPane, BoxLayout.Y_AXIS));

        JPanel p = new JPanel(new BorderLayout());
        p.add(textInLabel, BorderLayout.NORTH);
        p.add(new JScrollPane(fTextIn), BorderLayout.CENTER);
        textPane.add(p);
        textPane.add(Box.createRigidArea(new Dimension(0, 5)));

        p = new JPanel(new BorderLayout());
        p.add(textOutLabel, BorderLayout.NORTH);
        p.add(new JScrollPane(fTextOut), BorderLayout.CENTER);
        textPane.add(p);
        textPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // create panel on the right containing buttons
        JPanel btnPane = new JPanel();
        btnEvalBrowser = new JButton("Browser");
        
        btnEval = new JButton("Evaluate");
        btnEval.setMnemonic('E');
        
        btnEval.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                evaluate(fTextIn.getText());
                String out = fTextOut.getText();
                if (out != null && out.length() > 4
                        && !out.substring(0, 4).equals("Error")) {
                    btnEvalBrowser.setEnabled(true);
                    if (fEvalBrowser != null
                            && fEvalBrowser.getFrame().isVisible()) {
                        fEvalBrowser.updateEvalBrowser(evaluator
                                .getEvalNodeRoot());
                    }
                } else {
                    btnEvalBrowser.setEnabled(false);
                    if (fEvalBrowser != null) {
                        fEvalBrowser.getFrame().setVisible(false);
                        fEvalBrowser.getFrame().dispose();
                    }
                }

            }
        });
        Dimension dim = btnEval.getMaximumSize();
        dim.width = Short.MAX_VALUE;
        btnEval.setMaximumSize(dim);
        btnEvalBrowser.setMnemonic('B');
        btnEvalBrowser.setEnabled(false);
        btnEvalBrowser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fEvalBrowser != null && fEvalBrowser.getFrame().isVisible()) {
                    fEvalBrowser.getFrame().setVisible(true);
                } else {
                    fEvalBrowser = ExprEvalBrowser.create(evaluator
                            .getEvalNodeRoot(), fSystem);
                }
            }
        });
        JButton btnClear = new JButton("Clear");
        btnClear.setMnemonic('C');
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fTextOut.setText(null);
                btnEvalBrowser.setEnabled(false);
                if (fEvalBrowser != null) {
                    fEvalBrowser.getFrame().setVisible(false);
                    fEvalBrowser.getFrame().dispose();
                }
            }
        });
        dim = btnClear.getMaximumSize();
        dim.width = Short.MAX_VALUE;
        btnClear.setMaximumSize(dim);
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeDialog();
                if (fEvalBrowser != null) {
                    fEvalBrowser.getFrame().setVisible(false);
                    fEvalBrowser.getFrame().dispose();
                }
            }
        });
        dim = btnClose.getMaximumSize();
        dim.width = Short.MAX_VALUE;
        btnClose.setMaximumSize(dim);

        btnPane.setLayout(new BoxLayout(btnPane, BoxLayout.Y_AXIS));
        btnPane.add(Box.createVerticalGlue());
        btnPane.add(btnEval);
        btnPane.add(Box.createRigidArea(new Dimension(0, 5)));
        btnPane.add(btnEvalBrowser);
        btnPane.add(Box.createRigidArea(new Dimension(0, 5)));
        btnPane.add(btnClear);
        btnPane.add(Box.createRigidArea(new Dimension(0, 5)));
        btnPane.add(btnClose);
        btnPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        JComponent contentPane = (JComponent) getContentPane();
        contentPane.add(textPane, BorderLayout.CENTER);
        contentPane.add(btnPane, BorderLayout.EAST);
        getRootPane().setDefaultButton(btnEval);

        pack();
        setSize(new Dimension(500, 200));
        setLocationRelativeTo(parent);
        fTextIn.requestFocus();

        // allow dialog close on escape key
        CloseOnEscapeKeyListener ekl = new CloseOnEscapeKeyListener(this);
        addKeyListener(ekl);
        fTextIn.addKeyListener(ekl);
        fTextOut.addKeyListener(ekl);
    }

    private void closeDialog() {
        setVisible(false);
        dispose();
    }

    private void evaluate(String in) {
        if (this.fSystem == null) {
        	fTextOut.setText("No system!");
        	return;
        }
        
    	// clear previous results
        fTextOut.setText(null);

        // send error output to result window and msg stream
        StringWriter msgWriter = new StringWriter();
        PrintWriter out = new PrintWriter(new TeeWriter(
                new TextComponentWriter(fTextOut), msgWriter), true);

        SoilPermissionLevel permissionLevel = Options.soilFromOCL;
        if (permissionLevel == SoilPermissionLevel.ALL) {
        	Options.soilFromOCL = SoilPermissionLevel.SIDEEFFECT_FREE_ONLY;
        }
        
        // compile query
        Expression expr = OCLCompiler.compileExpression(
        		fSystem.model(),
        		fSystem.state(),
                in, 
                "Error", 
                out, 
                fSystem.varBindings());
        
        Options.soilFromOCL = permissionLevel;
        
        out.flush();
        fTextIn.requestFocus();

        // compile errors?
        if (expr == null) {
            // try to parse error message and set caret to error position
            String msg = msgWriter.toString();
            int colon1 = msg.indexOf(':');
            if (colon1 != -1) {
                int colon2 = msg.indexOf(':', colon1 + 1);
                int colon3 = msg.indexOf(':', colon2 + 1);
                
                try {
                    int line = Integer.parseInt(msg.substring(colon1 + 1,
                            colon2));
                    int column = Integer.parseInt(msg.substring(colon2 + 1,
                            colon3));
                    int caret = 1 + StringUtil.nthIndexOf(in, line - 1,
                            Options.LINE_SEPARATOR);
                    caret += column;

                    // sanity check
                    caret = Math.min(caret, fTextIn.getText().length());
                    fTextIn.setCaretPosition(caret);
                } catch (NumberFormatException ex) { }
            }
            return;
        }

        try {
            // evaluate it with current system state
            evaluator = new Evaluator(true);
            Value val = evaluator.eval(expr, fSystem.state(), fSystem
                    .varBindings());
            // print result
            fTextOut.setText(val.toStringWithType());

        } catch (MultiplicityViolationException e) {
            fTextOut.setText("Could not evaluate. " + e.getMessage());
        }
    }
}
