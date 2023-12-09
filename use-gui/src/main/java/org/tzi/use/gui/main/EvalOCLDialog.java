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

package org.tzi.use.gui.main;

import org.tzi.use.config.Options;
import org.tzi.use.gui.util.CloseOnEscapeKeyListener;
import org.tzi.use.gui.util.TextComponentWriter;
import org.tzi.use.gui.views.evalbrowser.ExprEvalBrowser;
import org.tzi.use.main.ChangeEvent;
import org.tzi.use.main.ChangeListener;
import org.tzi.use.main.Session;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.autocompletion.AutoCompletion;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.TeeWriter;
import org.tzi.use.autocompletion.SuggestionResult;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A dialog for entering and evaluating OCL expressions.
 * 
 * @author Mark Richters
 */
@SuppressWarnings("serial")
class EvalOCLDialog extends JDialog {
    private MSystem fSystem;

    private AutoCompletion autocompletion;

    private final JTextArea fTextIn;

    private final JTextArea fTextOut;

    private ExprEvalBrowser fEvalBrowser;

    private Evaluator evaluator;

    private final JButton btnEvalBrowser;
    
    private final JButton btnEval;
    
    private JList<String> autocompletionResultList;

    private JPopupMenu autocompletionPopupMenu;

    private DefaultListModel<String> autocompletionListModel;

    private final ChangeListener sessionChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			Session session = (Session)e.getSource();
			fSystem = getSystem(session);
		}
	};
    
    EvalOCLDialog(final Session session, JFrame parent) {
        super(parent, "Evaluate OCL expression");
    	fSystem = getSystem(session);
        session.addChangeListener(sessionChangeListener);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // unregister from session on close
        addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		session.removeChangeListener(sessionChangeListener);
        	}
		});

        // Use font specified in the settings 
        Font evalFont = Font.getFont("use.gui.evalFont", getFont());
        
        // create text components and labels
        fTextIn = new JTextArea();
        fTextIn.setFont(evalFont);
        JLabel textInLabel = new JLabel("Enter OCL expression:");
        textInLabel.setDisplayedMnemonic('O');
        textInLabel.setLabelFor(fTextIn);
        
        fTextOut = new JTextArea();
        fTextOut.setEditable(false);
        fTextOut.setLineWrap(true);
        fTextOut.setFont(evalFont);
        
        JLabel textOutLabel = new JLabel("Result:");
        textOutLabel.setLabelFor(fTextOut);

        // create panel on the left and add text components
        JPanel textPane = new JPanel();
        textPane.setLayout(new BoxLayout(textPane, BoxLayout.Y_AXIS));

        fTextIn.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleDocumentUpdate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleDocumentUpdate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handleDocumentUpdate();
            }

            private void handleDocumentUpdate() {
                // Check if arrow up, arrow down, or enter keys are pressed
                boolean arrowOrEnterPressed = isArrowOrEnterPressed();

                // Call updateAutocompletionResults only if the specific conditions are met
                if (!arrowOrEnterPressed) {
                    updateAutocompletionResults(textPane);
                }
            }

            private boolean isArrowOrEnterPressed() {
                KeyStroke upKeyStroke = KeyStroke.getKeyStroke("pressed UP");
                KeyStroke downKeyStroke = KeyStroke.getKeyStroke("pressed DOWN");
                KeyStroke enterKeyStroke = KeyStroke.getKeyStroke("pressed ENTER");

                return fTextIn.getInputMap().get(upKeyStroke) == null
                        || fTextIn.getInputMap().get(downKeyStroke) == null
                        || fTextIn.getInputMap().get(enterKeyStroke) == null;
            }
        });


        InputMap inputMap = fTextIn.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap actionMap = fTextIn.getActionMap();

        KeyStroke enterKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        KeyStroke upKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
        KeyStroke downKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);

        inputMap.put(enterKeyStroke, "enterPressed");
        inputMap.put(upKeyStroke, "upPressed");
        inputMap.put(downKeyStroke, "downPressed");

        actionMap.put("enterPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autocompletionResultList.setFocusable(true);
                autocompletionResultList.requestFocus();
            }
        });

        actionMap.put("upPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autocompletionResultList.setFocusable(true);
                autocompletionResultList.requestFocus();
                int lastIndex = autocompletionResultList.getModel().getSize() - 1;
                int selectedIndex = autocompletionResultList.getSelectedIndex();
                autocompletionResultList.setSelectedIndex(selectedIndex < 0 ? lastIndex : selectedIndex - 1);
            }
        });

        actionMap.put("downPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autocompletionResultList.setFocusable(true);
                autocompletionResultList.requestFocus();
                int lastIndex = autocompletionResultList.getModel().getSize() - 1;
                int selectedIndex = autocompletionResultList.getSelectedIndex();
                autocompletionResultList.setSelectedIndex(selectedIndex == lastIndex ? 0 : selectedIndex + 1);
            }
        });

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
            @Override
			public void actionPerformed(ActionEvent e) {
            	if(fEvalBrowser != null && fEvalBrowser.getFrame().isVisible()){
            		// if evaluation browser is already open, update it as well
            		boolean evalSuccess = evaluate(fTextIn.getText(), true);
            		
            		if(evalSuccess){
            			fEvalBrowser.updateEvalBrowser(evaluator
                                .getEvalNodeRoot());
            		}
            	}
            	else {
            		evaluate(fTextIn.getText(), false);
            	}
            }
        });
        Dimension dim = btnEval.getMaximumSize();
        dim.width = Short.MAX_VALUE;
        btnEval.setMaximumSize(dim);
        btnEvalBrowser.setMnemonic('B');
        btnEvalBrowser.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	// Error message is printed by evaluate method
            	boolean evalSuccess = evaluate(fTextIn.getText(), true);
                
                if(evalSuccess){
                	if (fEvalBrowser != null && fEvalBrowser.getFrame().isVisible()) {
                		fEvalBrowser.updateEvalBrowser(evaluator
                                .getEvalNodeRoot());
                		fEvalBrowser.getFrame().requestFocus();
                	} else {
                		fEvalBrowser = ExprEvalBrowser.create(evaluator
                				.getEvalNodeRoot(), fSystem);
                	}
                }
            }
        });
        JButton btnClear = new JButton("Clear");
        btnClear.setMnemonic('C');
        btnClear.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                fTextOut.setText(null);
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
            @Override
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

    private void updateAutocompletionResults(JPanel textPane) {
        SwingWorker<SuggestionResult, Void> worker = new SwingWorker<>() {
            @Override
            protected SuggestionResult doInBackground() {
                return autocompletion.getSuggestions(fTextIn.getText(), true);
            }

            @Override
            protected void done() {
                try {
                    SuggestionResult suggestion = get();
                    if (suggestion != null && !suggestion.suggestions.isEmpty()) {
                        displayResultsWindow(suggestion, textPane);
                    } else {
                        autocompletionPopupMenu.setVisible(false);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    /**
     * Returns the session's system if available or an empty model.
     * 
	 * @param session current session
	 * @return a system to evaluate expressions in
	 */
	private MSystem getSystem(Session session) {
		if(session.hasSystem()){
			return session.system();
		}
		else {
			MModel model = new ModelFactory().createModel("empty model");
			return new MSystem(model);
		}
	}

	private void closeDialog() {
        setVisible(false);
        dispose();
    }

    private boolean evaluate(String in, boolean evalTree) {
        if (this.fSystem == null) {
        	fTextOut.setText("No system!");
        	return false;
        }
        
    	// clear previous results
        fTextOut.setText(null);

        // send error output to result window and msg stream
        StringWriter msgWriter = new StringWriter();
        PrintWriter out = new PrintWriter(new TeeWriter(
                new TextComponentWriter(fTextOut), msgWriter), true);

        
        // compile query
        Expression expr = OCLCompiler.compileExpression(
        		fSystem.model(),
        		fSystem.state(),
                in, 
                "Error", 
                out, 
                fSystem.varBindings());
        
        
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
            return false;
        }

        try {
            // evaluate it with current system state
            evaluator = new Evaluator(evalTree);
            Value val = evaluator.eval(expr, fSystem.state(), fSystem
                    .varBindings());
            // print result
            fTextOut.setText(val.toStringWithType());
            return true;
        } catch (MultiplicityViolationException e) {
            fTextOut.setText("Could not evaluate. " + e.getMessage());
        }
        return false;
    }

    /**
     * Displays a window with autocomplete suggestions.
     * <p>
     * This method takes a {@code SuggestionResult} object and a {@code JPanel} where the suggestions
     * will be displayed. The suggestions are formatted and displayed in a {@code JList} within a scrollable
     * {@code JScrollPane} within a popupmenu. The user can confirm a selection by pressing the Enter key.
     * <p>
     * The formatting includes highlighting the prefix in blue and, if the suggestion contains parentheses,
     * highlighting the content within parentheses in orange.
     *
     * @param suggestion The {@code SuggestionResult} object containing the list of suggestions and the prefix.
     * @param textPane   The {@code JPanel} where the autocomplete suggestions will be displayed.
     */
    private void displayResultsWindow(SuggestionResult suggestion, JPanel textPane) {
        List<String> suggestionList = suggestion.suggestions;
        List<String> displayedList = formatStrings(suggestion);

        if (autocompletionPopupMenu == null) {
            autocompletionListModel = new DefaultListModel<>();
            autocompletionResultList = new JList<>(autocompletionListModel);
            autocompletionResultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            autocompletionResultList.setCellRenderer(new TwoColorListCellRenderer());

            autocompletionResultList.addKeyListener(new KeyAdapter() {
                //Keys needing special treatment
                final Set<Integer> specialKeySet = Set.of(
                        KeyEvent.VK_BACK_SPACE,
                        KeyEvent.VK_LEFT,
                        KeyEvent.VK_RIGHT
                        // Add more special keys as needed
                );

                @Override
                public void keyPressed(KeyEvent e) {
                    int keyCode = e.getKeyCode();
                    if (specialKeySet.contains(keyCode)) {
                        handleSpecialKey(e, keyCode);// Handle special keys (backspace, arrow keys, etc.) separately
                    } else {
                        // If a normal key is pressed, give focus back to textarea
                        fTextIn.requestFocus();

                        // Simulate the keypress in the textarea
                        char keyChar = e.getKeyChar();

                        if(keyCode != KeyEvent.VK_UP && keyCode != KeyEvent.VK_DOWN && keyCode != KeyEvent.VK_ENTER && keyCode != KeyEvent.VK_ESCAPE){
                            fTextIn.append(String.valueOf(keyChar));
                        }
                    }
                }

                private void handleSpecialKey(KeyEvent e, int keyCode) {
                    fTextIn.dispatchEvent(new KeyEvent(fTextIn, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), e.getModifiersEx(), keyCode, KeyEvent.CHAR_UNDEFINED));
                    fTextIn.requestFocus();
                }
            });


            InputMap inputMap = autocompletionResultList.getInputMap(JComponent.WHEN_FOCUSED);
            ActionMap actionMap = autocompletionResultList.getActionMap();

            KeyStroke enterKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
            KeyStroke upKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
            KeyStroke downKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);

            inputMap.put(enterKeyStroke, "enterPressed");
            inputMap.put(upKeyStroke, "upPressed");
            inputMap.put(downKeyStroke, "downPressed");

            actionMap.put("upPressed", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int lastIndex = autocompletionResultList.getModel().getSize() - 1;
                    int selectedIndex = autocompletionResultList.getSelectedIndex();
                    autocompletionResultList.setSelectedIndex(selectedIndex <= 0 ? lastIndex : selectedIndex - 1);
                }
            });

            actionMap.put("downPressed", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int lastIndex = autocompletionResultList.getModel().getSize() - 1;
                    int selectedIndex = autocompletionResultList.getSelectedIndex();
                    autocompletionResultList.setSelectedIndex(selectedIndex == lastIndex ? 0 : selectedIndex + 1);
                }
            });

            autocompletionPopupMenu = new JPopupMenu();
            autocompletionPopupMenu.add(new JScrollPane(autocompletionResultList));
            textPane.add(autocompletionPopupMenu);
        }

        autocompletionResultList.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "confirmSelection");
        Action confirmSelectionAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = autocompletionResultList.getSelectedIndex();

                if (selectedIndex >= 0 && selectedIndex < autocompletionListModel.size()) {
                    String selectedValue = suggestionList.get(selectedIndex);
                    String currentText = fTextIn.getText();

                    String newText;
                    boolean hasParenthesis = false;
                    if (selectedValue.endsWith(")")) {
                        if (selectedValue.contains("(")) {
                            int firstIndex = selectedValue.indexOf("(");
                            selectedValue = selectedValue.substring(0, firstIndex + 1);
                        }
                        newText = currentText + selectedValue + ")";
                        hasParenthesis = true;
                    } else if (currentText.endsWith(")")) {
                        newText = currentText.substring(0, currentText.length() - 1) + selectedValue + ")";
                        hasParenthesis = true;
                    } else {
                        newText = currentText + selectedValue;
                    }

                    fTextIn.setText(newText);
                    if (hasParenthesis) {
                        fTextIn.setCaretPosition(newText.length() - 1);
                    } else {
                        fTextIn.setCaretPosition(newText.length());
                    }
                }

                autocompletionPopupMenu.setVisible(false);
                fTextIn.requestFocusInWindow();
            }
        };

        autocompletionResultList.getActionMap().put("confirmSelection", confirmSelectionAction);

        autocompletionListModel.clear();
        autocompletionListModel.addAll(displayedList);

        int caretPosition = fTextIn.getCaretPosition();
        Rectangle2D caretRectangle;
        try {
            caretRectangle = fTextIn.modelToView2D(caretPosition);
        } catch (BadLocationException e) {
            e.printStackTrace();
            return;
        }

        int xOffset = -2; //due to the boarder of the popupmenu
        int yOffset = 12;

        int prefixPixelOffset = SwingUtilities.computeStringWidth(fTextIn.getFontMetrics(fTextIn.getFont()), suggestion.prefix);

        fTextIn.requestFocusInWindow();

        autocompletionPopupMenu.show(fTextIn, (int) caretRectangle.getX() + xOffset - prefixPixelOffset, (int) caretRectangle.getY() + yOffset);
        autocompletionResultList.setFocusable(false);
        fTextIn.requestFocusInWindow();
        pack();
    }

    /**
     * Formats a list of suggestion strings for display, highlighting the prefix in blue
     * and content within parentheses in orange.
     *
     * @param suggestion      The SuggestionResult object containing the prefix and the list of suggestions.
     * @return                The formatted list of suggestions.
     */
    private List<String> formatStrings(SuggestionResult suggestion) {
        String prefix = suggestion.prefix;

        List<String> displayedList = new LinkedList<>();

        for (int i = 0; i < suggestion.suggestions.size(); i++) {
            String sugString = suggestion.suggestions.get(i);

            // Highlight content within parentheses in orange
            if (sugString.contains("(")) {
                sugString = sugString.substring(0, sugString.indexOf("(") + 1) +
                        "<font color='orange'>" +
                        sugString.substring(sugString.indexOf("(") + 1, sugString.indexOf(")")) +
                        "</font>)";
            }

            // Highlight the prefix in blue
            if (prefix != null) {
                displayedList.add(i, "<font color='blue'>" + prefix + "</font>");
                if(!sugString.equals(prefix)){
                    displayedList.set(i, displayedList.get(i) + sugString);
                }
            } else {
                displayedList.add(i, suggestion.suggestions.get(i));
            }
        }
        return displayedList;
    }

    public void setAutoCompletion(AutoCompletion autocompletion) {
        this.autocompletion = autocompletion;
    }
}

/**
 * Custom list cell renderer for displaying two-colored text in a {@code JList}.
 * <p>
 * This renderer extends {@code DefaultListCellRenderer} and overrides the
 * {@code getListCellRendererComponent} method to display HTML-formatted text in a JLabel.
 * The HTML formatting is used to apply two colors to the text, providing visual distinction.
 * The text is enclosed in &lt;html&gt; tags to enable rendering of HTML-formatted content.
 */
class TwoColorListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setText("<html>" + value + "</html>");
        return label;
    }
}
