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

/* $ProjectHeader: use 2-3-1-release.3 Wed, 02 Aug 2006 17:53:29 +0200 green $ */

package org.tzi.use.gui.views.selection.objectselection;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.tzi.use.config.Options;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.util.TextComponentWriter;
import org.tzi.use.gui.views.View;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.StateChangeEvent;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.TeeWriter;

/**
 * a view of OCL Selection
 * 
 * @author   Jun Zhang 
 * @author   Jie Xu
 */
@SuppressWarnings("serial")
public class SelectionOCLView extends JPanel implements View, ActionListener {

	public static MSystem fSystem;

	private JButton fBtnShowAll;

	private JButton fBtnHideAll;

	private JButton fBtnEval;

	private JButton fBtnClear;

	private JTextArea fTextIn;

	private JTextArea fTextOut;

	private JRadioButton fRadioCropButton = new JRadioButton("crop", true);

	private JRadioButton fRadioShowButton = new JRadioButton("show", false);

	private JRadioButton fRadioHideButton = new JRadioButton("hide", false);

	static String cropString = "crop";

	static String showString = "show";

	static String hideString = "hide";

	String showart = cropString;

	private ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Constructor for SelectionOCLView.
	 */
	public SelectionOCLView(MainWindow parent, MSystem system) {
		super(new BorderLayout());
		fSystem = system;
		// create text components and labels
		fTextIn = new JTextArea();
		fTextIn.setSize(new Dimension(300, 100));
		JLabel textInLabel = new JLabel("Enter OCL query expression:");
		textInLabel.setDisplayedMnemonic('O');
		textInLabel.setLabelFor(fTextIn);
		fTextOut = new JTextArea();
		fTextOut.setSize(new Dimension(300, 150));
		fTextOut.setEditable(false);
		JLabel textOutLabel = new JLabel("Result:");
		textOutLabel.setLabelFor(fTextOut);
		JPanel radioButtonPane = new JPanel();
		radioButtonPane.setLayout(new BoxLayout(radioButtonPane,
				BoxLayout.Y_AXIS));
		fRadioCropButton.setMnemonic(KeyEvent.VK_C);
		fRadioCropButton.setActionCommand(cropString);
		fRadioCropButton.addActionListener(this);

		fRadioShowButton.setMnemonic(KeyEvent.VK_S);
		fRadioShowButton.setActionCommand(showString);
		fRadioShowButton.addActionListener(this);

		fRadioHideButton.setMnemonic(KeyEvent.VK_H);
		fRadioHideButton.setActionCommand(hideString);
		fRadioHideButton.addActionListener(this);

		buttonGroup.add(fRadioCropButton);
		buttonGroup.add(fRadioShowButton);
		buttonGroup.add(fRadioHideButton);

		radioButtonPane.add(Box.createVerticalGlue());
		radioButtonPane.add(fRadioCropButton);
		radioButtonPane.add(fRadioShowButton);
		radioButtonPane.add(fRadioHideButton);
		radioButtonPane.add(Box.createVerticalGlue());
		add(radioButtonPane, BorderLayout.WEST);

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
		fBtnEval = new JButton("Evalute");
		fBtnEval.setMnemonic('E');
		fBtnEval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evaluate(fTextIn.getText(), e);
			}
		});

		fBtnShowAll = new JButton("Show All");
		fBtnShowAll.setMnemonic('S');
		fBtnShowAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyShowAllObjects();
			}
		});

		fBtnHideAll = new JButton("Hide All");
		fBtnHideAll.setMnemonic('H');
		fBtnHideAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyHideAllObjects(e);
			}
		});

		fBtnClear = new JButton("Clear Result");
		fBtnClear.setMnemonic('C');
		fBtnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fTextOut.setText(null);
			}
		});

		btnPane.setLayout(new BoxLayout(btnPane, BoxLayout.X_AXIS));
		btnPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		btnPane.add(Box.createHorizontalGlue());
		btnPane.add(fBtnShowAll);
		btnPane.add(fBtnHideAll);
		btnPane.add(Box.createRigidArea(new Dimension(10, 0)));
		btnPane.add(fBtnEval);

		btnPane.add(fBtnClear);
		btnPane.add(Box.createHorizontalGlue());

		add(textPane, BorderLayout.CENTER);
		add(btnPane, BorderLayout.SOUTH);

		fTextIn.requestFocus();
	}

	protected void applyHideAllObjects(ActionEvent ev) {
		Iterator it = NewObjectDiagram.ffGraph.iterator();
		HashSet hideObjects = new HashSet();
		while (it.hasNext()) {
			Object node = it.next();
			if (node instanceof ObjectNode) {
				MObject mo = ((ObjectNode) node).object();
				hideObjects.add(mo);
			}
		}
		NewObjectDiagram.ffHideAdmin.setValues("Hide all classes", hideObjects)
				.actionPerformed(ev);
	}

	protected void applyShowAllObjects() {
		NewObjectDiagram.ffHideAdmin.showAllHiddenElements();
		MainWindow.instance().repaint();
	}

	/**
	 * Method evaluate evaluate ocl-expression.
	 * 
	 * @param in
	 *            String
	 * @param ev
	 *            ActionEvent
	 */
	private void evaluate(String in, ActionEvent ev) {
		// clear previous results
		fTextOut.setText(null);

		// send error output to result window and msg stream
		StringWriter msgWriter = new StringWriter();
		PrintWriter out = new PrintWriter(new TeeWriter(
				new TextComponentWriter(fTextOut), msgWriter), true);

		// compile query
		Expression expr = OCLCompiler.compileExpression(fSystem.model(), in, 
														"Error", out, fSystem.topLevelBindings());
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
				} catch (NumberFormatException ex) {
				}
			}
			return;
		}

		try {
			// evaluate it with current system state
			Evaluator evaluator = new Evaluator();
			Value val = evaluator.eval(expr, fSystem.state(), fSystem
					.topLevelBindings());
			String[] str = getNames(val.toString());

			fTextOut.setText(val.toStringWithType());
			if (showart.equalsIgnoreCase("crop")) {
				NewObjectDiagram.ffHideAdmin.setValues("Hide",
						getCropHideObjects(str)).actionPerformed(ev);
				NewObjectDiagram.ffHideAdmin
						.showHiddenElements(getShowObjects(str));
			} else if (showart.equalsIgnoreCase("show")) {
				NewObjectDiagram.ffHideAdmin
						.showHiddenElements(getShowObjects(str));
			} else if (showart.equalsIgnoreCase("hide")) {
				NewObjectDiagram.ffHideAdmin.setValues("Hide",
						getHideObjects(str)).actionPerformed(ev);
			}
		} catch (MultiplicityViolationException e) {
			fTextOut.setText("Could not evaluate. " + e.getMessage());
		}
	}

	HashSet getShowObjects(String[] str) {
		HashSet objects = new HashSet();
		for (int i = 0; i < str.length; i++) {
			String oname = str[i];
			Iterator ithide = NewObjectDiagram.ffHiddenNodes.iterator();

			while (ithide.hasNext()) {
				Object node = ithide.next();
				if (node instanceof MObject) {
					MObject mobj = (MObject) (node);
					if (mobj.name().equals(oname)) {
						objects.add(mobj);
						break;
					}
				}
			}
		}
		return objects;
	}

	HashSet getCropHideObjects(String[] str) {
		HashSet objects = new HashSet();
		for (int i = 0; i < str.length; i++) {
			String oname = str[i];
			Iterator itobject = NewObjectDiagram.ffGraph.iterator(); 

			while (itobject.hasNext()) {
				Object node = itobject.next();
				if (node instanceof ObjectNode) {
					MObject mobj = ((ObjectNode) node).object();
					if (!mobj.name().equals(oname)) {
						objects.add(mobj);
					}
				}
			}
		}
		return objects;
	}

	HashSet getHideObjects(String[] str) {
		HashSet objects = new HashSet();
		for (int i = 0; i < str.length; i++) {
			String oname = str[i];
			Iterator itobject = NewObjectDiagram.ffGraph.iterator();

			while (itobject.hasNext()) {
				Object node = itobject.next();
				if (node instanceof ObjectNode) {
					MObject mobj = ((ObjectNode) node).object();
					if (mobj.name().equals(oname)) {
						objects.add(mobj);
					}
				}
			}
		}
		return objects;
	}

	/**
	 * Method getNames give all names as array
	 */
	private String[] getNames(String ss) {
		if (ss.contains("{")) {
			ss = ss.substring(ss.indexOf("@") + 1, ss.indexOf("}"));
		}
		String[] st = ss.split(",");
		String[] str = new String[st.length];
		for (int j = 0; j < st.length; j++) {
			if (st[j].contains("@")) {
				str[j] = st[j].substring(1);
			} else {
				str[j] = st[j];
			}
		}
		return str;
	}

	public void actionPerformed(ActionEvent ev) {
		showart = ev.getActionCommand();
	}

	/**
	 * Method stateChanged call due to an external change of state.
	 * 
	 * @see org.tzi.use.uml.sys.StateChangeListener#stateChanged(StateChangeEvent)
	 */
	public void stateChanged(StateChangeEvent e) {
	}

	/**
	 * Method detachModel detaches the view from its model.
	 * 
	 * @see org.tzi.use.gui.views.View#detachModel()
	 */
	public void detachModel() {
		fSystem.removeChangeListener(this);
	}

}
