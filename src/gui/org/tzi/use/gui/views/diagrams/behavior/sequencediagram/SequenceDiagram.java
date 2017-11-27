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
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.util.PopupListener;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.behavior.DrawingUtil;
import org.tzi.use.gui.views.diagrams.behavior.sequencediagram.Lifeline.ObjectBox;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObjectImpl;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectImpl;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.events.AttributeAssignedEvent;
import org.tzi.use.uml.sys.events.Event;
import org.tzi.use.uml.sys.events.LinkDeletedEvent;
import org.tzi.use.uml.sys.events.LinkInsertedEvent;
import org.tzi.use.uml.sys.events.ObjectCreatedEvent;
import org.tzi.use.uml.sys.events.ObjectDestroyedEvent;
import org.tzi.use.uml.sys.events.OperationEnteredEvent;
import org.tzi.use.uml.sys.events.OperationExitedEvent;

/**
 * A SequenceDiagram shows an UML sequence diagram of events.
 * 
 * @author Mark Richters 
 * @author Antje Werner
 */
@SuppressWarnings("serial")
public class SequenceDiagram extends JPanel implements Printable {
	
	public static final int OFFSET_LEFT_MARGIN_ACTOR_CENTER = 10;

	/**
	 * The actual system status.
	 */
	private MSystem fSystem;

	/**
	 * The (Hash)Map of all Lifelines. The respective MObject-Variable is used
	 * as the key for finding the right Object-Lifeline. (MObject -> Lifeline)
	 * Each Associaiton-Lifeline can be found by a key which contains the names
	 * of the involved objects, the association name and a specific number of
	 * the Link.
	 */
	private Map<Object, Lifeline> fLifelines;

	/**
	 * The list of all activations that should actually be drawn.
	 */
	private ArrayList<Activation> fActivations;

	/**
	 * The context menu on right mouse click. Provides several illustration and
	 * setting facilities.
	 */
	private JPopupMenu fPopupMenu;

	/**
	 * The context menu on right mouse click when a lifeline is chosen. Provides
	 * the possibility to hide the chosen lifeline.
	 */
	private JPopupMenu llMenu;

	/**
	 * Contains several properties for this SequenceDiagram
	 */
	private SDProperties fProperties;

	/**
	 * Contains several properties for the construction of object boxes.
	 */
	private OBProperties fObProperties;

	/**
	 * The actual number of time steps.
	 */
	private int fNumSteps;

	/**
	 * The lifeline which is choosed by ths user by pressing the left mouse
	 * button on it. Is null, if no Lifeline ist choosed and the left Mouse
	 * button is released respecitvly.??
	 */
	private Selection<Lifeline> choosedLifelines;

	/**
	 * The last x-value in the diagram (here ends the diagram). Needed for
	 * setting the preferred Size of the panel.
	 */
	private int fLastXValue;
	
	/**
	 * The last y-value in the diagram
	 */
	private int lastYValue = 0;

	/**
	 * The MainWindow from which this sequence diagram is called. Needed for the
	 * properties windows.
	 */
	private MainWindow fMainWindow;


	/**
	 * Vector which contains all Lifelines that should be hidden in the diagram.
	 */
	private Vector<Lifeline> fHiddenLifelines;

	private int scrollCounter;

	// needed for mouse handling
	private static final int DRAG_NONE = 0;

	private static final int DRAG_ITEMS = 1; // drag selected items

	private Point fDragStart;

	private int fDragMode;

	private boolean fIsDragging = false;

	private Cursor fCursor;

	public Selection<Lifeline> getChoosedLinelines() {
		return choosedLifelines;
	}

	public int getLastYPos() {
		return lastYValue;
	}

	public int getNumSteps() {
		return fNumSteps;
	}

	public Map<Object, Lifeline> getAllLifelines() {
		return fLifelines;
	}

	public MSystem getSystem() {
		return fSystem;
	}

	public int getLastXValue() {
		return fLastXValue;
	}

	public void setLastXValue(int fLastXValue) {
		this.fLastXValue = fLastXValue;
	}

	/**
	 * Specifies if only the actual fView should be repainted or the whole
	 * diagram (for example for printing the whole diagram)
	 */
	private boolean fOnlyView;

	public boolean isOnlyView() {
		return fOnlyView;
	}

	/**
	 * The actual position and dimension of the visible view.
	 */
	private Rectangle fView;

	public Rectangle getView() {
		return fView;
	}

	/**
	 * Constructs a new SequenceDiagram-Object.
	 * 
	 * @param system contains (direct or indirect) all needed informations mainW
	 * @param mainW the MainWindow object from which the constructor is called
	 */
	public SequenceDiagram(MSystem system, MainWindow mainW) {
		fSystem = system;
		fMainWindow = mainW;
		fProperties = new SDProperties(this);
		fObProperties = new OBProperties();
		fLifelines = new HashMap<Object, Lifeline>();
		fActivations = new ArrayList<Activation>();
		fHiddenLifelines = new Vector<Lifeline>();

		// at the beginning no Lifeline is chosen
		choosedLifelines = new Selection<Lifeline>();

		// normally only the visible view should be drawn
		fOnlyView = true;
		fView = new Rectangle();

		setBorder(BorderFactory.createEmptyBorder());
		setBackground(Color.white);
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(50, 50));
		setPreferredSize(new Dimension(200, 100));
		createPopupMenu();
		llMenu = new JPopupMenu();

		// Popup-Menu for hiding a maked lifeline
		final JMenuItem cbHideLl = new JMenuItem("Hide selected Lifelines");
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Lifeline ll : choosedLifelines) {
					ll.setHidden(true);
					fHiddenLifelines.add(ll);
				}
				choosedLifelines.clear();
				// update and repaint the whole diagram
				update();
				repaint();
			}
		};
		cbHideLl.addActionListener(al);
		llMenu.add(cbHideLl);

		final JMenuItem hideOtherMI = new JMenuItem("Hide Others");
		ActionListener hideOtherMIActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Lifeline ll : getAllLifelines().values()) {
					if (!choosedLifelines.isSelected(ll)) {
						ll.setHidden(true);
						fHiddenLifelines.add(ll);
					}
				}
				update();
				repaint();
			}
		};
		hideOtherMI.addActionListener(hideOtherMIActionListener);
		llMenu.add(hideOtherMI);

		final JMenuItem hideStateInvariante = new JMenuItem("Hide/Show States of selected lifelines");
		ActionListener hideStateInvarianteActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Lifeline ll : choosedLifelines) {
					if (ll.isShowStates()) {
						ll.setShowStates(false);
					} else {
						ll.setShowStates(true);
					}
				}
				update();
				repaint();
			}
		};
		hideStateInvariante.addActionListener(hideStateInvarianteActionListener);
		llMenu.add(hideStateInvariante);

		addMouseListener(new SDPopupListener(fPopupMenu, llMenu));
		addMouseListener(new MyMouseListener());
		addMouseMotionListener(new MyMouseMotionListener());
	}

	/*
	 * PopupListener which differs between the two possible Popup-Menus. If a
	 * lifeline is chosen by the user, the llMenu should be shown. Otherwise the
	 * main fPopupMenu.
	 */
	private class SDPopupListener extends PopupListener {
		private JPopupMenu fLlMenu;

		private SDPopupListener(JPopupMenu SDMenu, JPopupMenu llMenu) {
			super(SDMenu);
			fLlMenu = llMenu;
		}

		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				if (choosedLifelines.isEmpty())
					fPopupMenu.show(e.getComponent(), e.getX(), e.getY());
				else
					fLlMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}

	}

	/**
	 * Sets the bounds of the visible fView. Used by a SDSrcollPane-Object.
	 * 
	 * @param bounds the bounds of the actual visible fView
	 */
	public void setViewBounds(Rectangle bounds) {
		fView = bounds;
		repaint();
	}

	/**
	 * Creates the context menu, which is shown by a right mouse click.
	 */

	private void createPopupMenu() {
		fPopupMenu = new JPopupMenu();
		// menu item "Anti-aliasing"

		final JCheckBoxMenuItem cbAntiAliasing = new JCheckBoxMenuItem("Anti-aliasing");
		cbAntiAliasing.setState(fProperties.getAntiAliasing());
		cbAntiAliasing.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				fProperties.setAntiAliasing(ev.getStateChange() == ItemEvent.SELECTED);
				repaint();
			}
		});
		fPopupMenu.add(cbAntiAliasing);

		// menu item "Show values"
		final JCheckBoxMenuItem cbShowValues = new JCheckBoxMenuItem("Show values");
		cbShowValues.setState(fProperties.showValues());
		cbShowValues.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				fProperties.showValues(ev.getStateChange() == ItemEvent.SELECTED);
				repaint();
			}
		});
		fPopupMenu.add(cbShowValues);

		// menu item "Compact display"
		final JCheckBoxMenuItem cbCompactDisplay = new JCheckBoxMenuItem("Compact display");
		cbCompactDisplay.setState(fProperties.compactDisplay());
		cbCompactDisplay.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				fProperties.compactDisplay(ev.getStateChange() == ItemEvent.SELECTED);
				repaint();
			}
		});
		fPopupMenu.add(cbCompactDisplay);
		fPopupMenu.addSeparator();

		// new menu item "Show all Commands"
		final JCheckBoxMenuItem cbShowAllCommands = new JCheckBoxMenuItem("Show all Commands");
		cbShowAllCommands.setState(fProperties.showCreate() && fProperties.showSet() && fProperties.showDestroy() && fProperties.showInsert()
				&& fProperties.showDelete());
		cbShowAllCommands.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				fProperties.showCreate(ev.getStateChange() == ItemEvent.SELECTED);
				fProperties.showSet(ev.getStateChange() == ItemEvent.SELECTED);
				fProperties.showDestroy(ev.getStateChange() == ItemEvent.SELECTED);
				fProperties.showInsert(ev.getStateChange() == ItemEvent.SELECTED);
				fProperties.showDelete(ev.getStateChange() == ItemEvent.SELECTED);
				update();
				repaint();
			}
		});
		fPopupMenu.add(cbShowAllCommands);

		// new menu item "Show somes Commands..."
		final JMenuItem cbShowSomeCommands = new JMenuItem("Commands to show...");
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// open CmdChooseWindow
				createCmdChooseWindow();
				// after closing update diagram
				update();
				// repaint diagram
				repaint();
			}
		};
		cbShowSomeCommands.addActionListener(al);
		fPopupMenu.add(cbShowSomeCommands);

		fPopupMenu.addSeparator();

		// new menu item "Show Lifeline States..."
		final JCheckBoxMenuItem cbState = new JCheckBoxMenuItem("Show all Lifelines States");
		cbState.setEnabled(false);
		cbState.setState(fProperties.isStatesShown());
		for (MClass cls : fSystem.model().classes()) {
			if (cls.getAllOwnedProtocolStateMachines().size() > 0) {
				cbState.setEnabled(true);
				break;
			}
		}
		cbState.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				fProperties.setLifelineStates(ev.getStateChange() == ItemEvent.SELECTED);
				update();
				repaint();
			}
		});
		fPopupMenu.add(cbState);

		fPopupMenu.addSeparator();

		// new menu item "Show hidden lifelines"
		final JMenuItem cbShowHidden = new JMenuItem("Show hidden lifelines");
		cbShowHidden.setEnabled(!fHiddenLifelines.isEmpty());
		ActionListener al3 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < fHiddenLifelines.size(); i++) {
					fHiddenLifelines.get(i).setHidden(false);
				}
				fHiddenLifelines.clear();
				// update diagram
				update();
				// repaint diagram
				repaint();
			}
		};
		cbShowHidden.addActionListener(al3);
		fPopupMenu.add(cbShowHidden);

		fPopupMenu.addSeparator();
		// new menu item "Properties..."
		final JMenuItem cbProperties = new JMenuItem("Properties...");
		ActionListener al2 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// open PropertiesWindow
				createPropertiesWindow();
				// after closing update diagram
				update();
				// repaint diagram
				repaint();
			}
		};
		cbProperties.addActionListener(al2);
		fPopupMenu.add(cbProperties);
	}

	/**
	 * Creates a new PropertiesWindow object.
	 */
	private void createPropertiesWindow() {
		PropertiesWindow propWin = new PropertiesWindow(this);
		propWin.showWindow();
	}

	/**
	 * Creates a new CmdChooseWindow object.
	 */
	private void createCmdChooseWindow() {
		CmdChooseWindowSeqDiag chooseWin = new CmdChooseWindowSeqDiag(this);
		chooseWin.showWindow();
	}

	/**
	 * Returns the SDProperties object which contains all needed datas for
	 * drawing the diagram.
	 * 
	 * @return current Properties object
	 */
	public SDProperties getProperties() {
		return fProperties;
	}

	/**
	 * Returns the OBfProperties object which contains all needed datas for
	 * drawing an object box.
	 * 
	 * @return current objectbox fProperties object
	 */
	public OBProperties getObProperties() {
		return fObProperties;
	}

	/**
	 * Returns the MainWindow from which this sequence diagram is called.
	 * 
	 * @return the MainWindow object
	 */
	public MainWindow getMainWindow() {
		return fMainWindow;
	}

	// ******************************************************************************

	/**
	 * Draws the panel.
	 * 
	 * @param g the Graphics-object which should be used by drawing.
	 */
	public synchronized void paint(Graphics g) {
		Font oldFont = g.getFont();
		g.setFont(fProperties.getFont());
		super.paint(g);

		// Depending on the value of fView the whole
		// diagram (->drawDiagram) or only the visible view (->drawView)
		// should be drawn.
		if (fOnlyView) {
			drawView((Graphics2D) g);// , fView);
		} else {
			drawDiagram((Graphics2D) g);
		}
		g.setFont(oldFont);
	}

	/**
	 * Draws the whole diagram.
	 * 
	 * @param g the Graphics2D-object which should be used for drawing.
	 */
	private synchronized void drawDiagram(Graphics2D g) {
		if (fProperties.getAntiAliasing())
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(fProperties.getFont());
		// respect borders
		Insets insets = getInsets();
		Rectangle r = getBounds();

		// r is the visible Rectangle where sequence diagram is painted.
		r.x += insets.left;
		r.y += insets.top;

		g.setColor(Color.black);

		int x = r.x + fProperties.getLeftMargin(); // + 20;
		int y = r.y + fProperties.getTopMargin(); // + 20;

		/* draw actor */

		DrawingUtil.drawActor(x + OFFSET_LEFT_MARGIN_ACTOR_CENTER, y, g);

		int y_height = lastYValue;

		if (y_height == 0) {
			y_height = 20;
		}

		// draw all Lifelines which are involved in at least one message
		for (Lifeline ll : fLifelines.values()) {
			// is lifeline involved in at least one message and is not marked as
			// hidden
			if (ll.isDraw() && !ll.isHidden()) {
				ll.draw(g);
			}
		}

		// draw all activation-messages
		for (int i = 0; i < fActivations.size(); i++) {
			Activation a = fActivations.get(i);
			a.drawMessageSend(g, getFontMetrics(fProperties.getFont()));
		}

		drawActorLifeline(g, x, y_height);

	}

	public int getScrollCounter() {
		return scrollCounter;
	}

	/**
	 * Draws the view of the diagram, which is situated in the position denoted
	 * by the fView attribute.
	 * 
	 * @param g the Graphics2D-object which should be used for drawing. values
	 */
	private synchronized void drawView(Graphics2D g) {
		if (fProperties.getAntiAliasing())
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setFont(fProperties.getFont());

		// the postition and dimension values of the visible view
		int fX = (int) fView.getX();
		int fY = (int) fView.getY();
		int fWidth = (int) fView.getWidth();
		int fHeight = (int) fView.getHeight();

		// respect borders
		Insets insets = getInsets();
		Rectangle r = getBounds();
		r.x += insets.left;
		r.y += insets.top;

		// choose the drawing area
		g.clipRect(fX, fY, fWidth, fHeight);

		g.setColor(Color.black);

		// left Border
		int x = r.x + fProperties.getLeftMargin();
		// top Border
		int y = r.y + fProperties.getTopMargin();

		int y_height = lastYValue;

		if (y_height == 0) {
			y_height = 20;
		}

		// counter for the proceeding of scrolling
		scrollCounter = 0;

		if (fY == 0) {
			/* draw actor */
			DrawingUtil.drawActor(x + OFFSET_LEFT_MARGIN_ACTOR_CENTER, y, g);
			drawActorLifeline(g, x, y_height);
		} else if (fY > 0) {
			// has been scrolled by the user?
			// start of the actors lifeline
			int y_start = fProperties.yScroll();
			// draw the first line of the zigzag pattern
			g.drawLine(x + 10 - fProperties.frWidth() / 2 + 2, fY + fProperties.yScroll(), x + 10 - fProperties.frWidth() / 2 - 2, fY + fProperties.yScroll()
					+ 4);
			g.drawLine(x + 10 + fProperties.frWidth() / 2 + 2, fY + fProperties.yScroll(), x + 10 + fProperties.frWidth() / 2 - 2, fY + fProperties.yScroll()
					+ 4);
			// lifeline should begin under the zigzag
			y_start += 4;
			// increase counter
			scrollCounter++;

			// if necessary draw the other lines of the zigzag pattern and
			// increase the start-point of the lifelines an the value of counter
			if (fY > 4) {
				// x+10 is the middle of the manicin
				g.drawLine(x + 10 - fProperties.frWidth() / 2 - 2, fY + fProperties.yScroll() + 4, x + 10 - fProperties.frWidth() / 2 + 2,
						fY + fProperties.yScroll() + 8);
				g.drawLine(x + 10 + fProperties.frWidth() / 2 - 2, fY + fProperties.yScroll() + 4, x + 10 + fProperties.frWidth() / 2 + 2,
						fY + fProperties.yScroll() + 8);
				y_start += 4;
				scrollCounter++;
			}
			if (fY > 8) {
				g.drawLine(x + 10 - fProperties.frWidth() / 2 + 2, fY + fProperties.yScroll() + 8, x + 10 - fProperties.frWidth() / 2 - 2,
						fY + fProperties.yScroll() + 12);
				g.drawLine(x + 10 + fProperties.frWidth() / 2 + 2, fY + fProperties.yScroll() + 8, x + 10 + fProperties.frWidth() / 2 - 2,
						fY + fProperties.yScroll() + 12);
				y_start += 4;
				scrollCounter++;
			}
			if (fY > 12) {
				g.drawLine(x + 10 - fProperties.frWidth() / 2 - 2, fY + fProperties.yScroll() + 12, x + 10 - fProperties.frWidth() / 2 + 2,
						fY + fProperties.yScroll() + 16);
				g.drawLine(x + 10 + fProperties.frWidth() / 2 - 2, fY + fProperties.yScroll() + 12, x + 10 + fProperties.frWidth() / 2 + 2,
						fY + fProperties.yScroll() + 16);
				y_start += 4;
				scrollCounter++;
			}

			/* draw actor */
			DrawingUtil.drawActor(x + OFFSET_LEFT_MARGIN_ACTOR_CENTER, y + fY, g);

			// choose the drawing area -> drawing begins under the
			// zigzag-pattern
			g.clipRect((int) fView.getX(), (int) fView.getY() + y_start + 1, (int) fView.getWidth(), (int) fView.getHeight() - y_start - 1);

			drawActorLifeline(g, x, y_height);
		}

		// set the drawing area on the whole visible view
		g.setClip((int) fView.getX(), (int) fView.getY(), (int) fView.getWidth(), (int) fView.getHeight());
		// draw all visible lifelines which are involved in at least one message

		for (Lifeline ll : fLifelines.values()) {
			if (ll.objectBox.getEnd() > fX && ll.objectBox.getStart() < fX + fWidth) {
				// is lifeline involved in at least one message and is not
				// marked as hidden
				if (ll.isDraw() && !ll.isHidden()) {
					ll.draw(g);
				}
			}
		}

		// choose the drawing area -> drawing begins under the
		// zigzag-patternS
		g.clipRect((int) fView.getX(), (int) fView.getY() + fProperties.yScroll() + 16, (int) fView.getWidth(), (int) fView.getHeight() - fProperties.yScroll()
				- 16);

		for (Activation a : fActivations) {
			a.drawMessageSend(g, getFontMetrics(fProperties.getFont()));
		}

	}

	/**
	 * Draw the actor's lifeline
	 * 
	 * @param g
	 * @param x
	 * @param y_height
	 */
	private void drawActorLifeline(Graphics2D g, int x, int y_height) {
		// draw the actors lifeline
		g.setColor(Color.white);
		g.fillRect(x + OFFSET_LEFT_MARGIN_ACTOR_CENTER - fProperties.frWidth() / 2, fProperties.yStart(), fProperties.frWidth(), y_height); // fNumSteps

		g.setColor(Color.black);
		g.drawRect(x + OFFSET_LEFT_MARGIN_ACTOR_CENTER - fProperties.frWidth() / 2, fProperties.yStart(), fProperties.frWidth(), y_height); // fNumSteps
	}

	/**
	 * Creates all possible lifelines for the current system state and adds them
	 * to the fLifelines-Map.
	 */
	private synchronized void createLifelines() {
		// executed commands
		List<Event> events = fSystem.getAllEvents();
		// first lifeline has column 1
		int column = 1;
		// antecessor lifeline
		Lifeline antecessor = null;
		// List for saving deleted links
		ArrayList<AssLifeline> deletedLls = new ArrayList<AssLifeline>();

		// view all commands
		for (Event event : events) {
			// i create command
			if (event instanceof ObjectCreatedEvent) {
				MObject obj = ((ObjectCreatedEvent) event).getCreatedObject();
								
				ObjLifeline ll = (ObjLifeline) fLifelines.get(obj);
				// need new lifeline?
				if (ll == null) {
					// create new lifeline
					ll = new ObjLifeline(column, obj, antecessor, this);
					// set new lifeline as successor of last created
					// lifeline
					if (antecessor != null) {
						antecessor.setSuccessor(ll);
					}
					// put new lifeline in fLifelines
					fLifelines.put(obj, ll);
					// lifeline already exists
				} else {
					// delete all saved activations for this lifeline
					ll.activationsList = new ArrayList<Activation>();
				}
				// set ll as antecessor
				antecessor = ll;
				// next new lifeline is positioned next to this lifeline
				column++;
				// insert-command
			} else if (event instanceof LinkInsertedEvent) {
				MLink link = ((LinkInsertedEvent) event).getLink();
				
				// association which is affected
				MAssociation ass = ((LinkInsertedEvent) event).getAssociation();
				// affected objects
				List<MObject> objects = ((LinkInsertedEvent) event).getParticipants();
				// get Link-Lifeline
				AssLifeline ll = (AssLifeline) fLifelines.get(link);
				if (ll == null) {
					ll = new AssLifeline(column, ass, antecessor, objects, this);
					if (!(antecessor == null)) {
						antecessor.setSuccessor(ll);
					}
					fLifelines.put(link, ll);
				} else {
					ll.activationsList = new ArrayList<Activation>();
				}
				column++;
				antecessor = ll;
			} else if (event instanceof LinkDeletedEvent) {
				MLink link = ((LinkDeletedEvent) event).getLink();
				List<MObject> objects = ((LinkDeletedEvent) event).getParticipants();

				AssLifeline ll = (AssLifeline) fLifelines.get(link);
				while (true) {
					if (ll != null && !(ll.isDeleted()) && ll.sameObjects(objects))
						break;
					ll = (AssLifeline) fLifelines.get(link);
				}
				// mark lifeline as deleted
				ll.setDeleted(true);
				// add Lifeline to deletedLls
				deletedLls.add(ll);
			}

			for (AssLifeline ll : deletedLls) {
				ll.setDeleted(false);
			}
		}

	}

	/**
	 * Calculates the x-Positions within the sequence diagram for all lifelines
	 * contained in the fLifelines-Map
	 * 
	 * @param fm the used FontMetrics object, needed for calculating the width
	 *            of the individual activation messages.
	 */
	private synchronized void calculateLlPositions(FontMetrics fm) {
		// Map of all existing lifelines
		Map<Object, Lifeline> lifelines = fLifelines;
		Iterator<Lifeline> lifelineIter = lifelines.values().iterator();

		// view each lifeline
		if (lifelineIter.hasNext()) {
			// first lifeline in Map
			Lifeline ll = lifelineIter.next();
			// search first lifeline in the sequencediagram
			Lifeline ll2 = ll;
			while (ll2.getAntecessor() != null) {
				ll2 = ll2.getAntecessor();
			}
			// calculate position of each lifeline and its successor lifeline
			while (ll2 != null && ll2 != ll) {
				ll2.calculatePosition(fm);
				ll2 = ll2.getSuccessor();
			}
			// calculate position of first lifeline in Map
			ll.calculatePosition(fm);
			// calculate position of each successor lifeline
			ll2 = ll.getSuccessor();
			while (ll2 != null) {
				ll2.calculatePosition(fm);
				ll2 = ll2.getSuccessor();
			}
		}
	}

	/**
	 * Updates all values of the seuquence diagram: gives the command for
	 * creating all (new) lifelines, creates all activations and repaints the
	 * sequence diagram.
	 */
	void update() {
		// refresh popup menu
		createPopupMenu();
		// restore all needed values to the default values
		restoreAllValues();
		// List of all executed commands
		List<Event> toDraw = fSystem.getAllEvents();

		// while reading a command file dont show
		// the following commands
		// create all lifelines
		createLifelines();

		// number of steps
		fNumSteps = 1;
		// Stack for new created activations
		Stack<Activation> activationStack = new Stack<Activation>();
		// remove all activations created so far
		fActivations = new ArrayList<Activation>();
		// last created activation
		Activation lastAct = null;
		// last y-value on which an activation message should be drawn
		lastYValue = 0;
		// int counter = 1;
		// view each command
		for (int i = 0; i < toDraw.size(); i++) {
			Event event = toDraw.get(i);

			// create activations by calling other operations depending on
			// the
			// command
			if (event instanceof ObjectCreatedEvent) {
				synchronized (this) {
					lastYValue = drawCreate((ObjectCreatedEvent) event, activationStack, lastYValue, lastAct);
				}
			} else if (event instanceof ObjectDestroyedEvent) {
				// Call drawDestroy() only if the object to destroy is instance of a class not a association class
				if (((ObjectDestroyedEvent) event).getDestroyedObject() instanceof MObjectImpl) {
					synchronized (this) {
						lastYValue = drawDestroy((ObjectDestroyedEvent) event, activationStack, lastYValue, lastAct);
					}
				}
			} else if (event instanceof OperationEnteredEvent) {
				synchronized (this) {
					lastYValue = drawOpEnter((OperationEnteredEvent) event, activationStack, lastYValue, lastAct);
				}
			} else if (event instanceof AttributeAssignedEvent) {
				synchronized (this) {
					lastYValue = drawSet((AttributeAssignedEvent) event, activationStack, lastYValue, lastAct);
				}

			} else if (event instanceof OperationExitedEvent) {
				synchronized (this) {
					if (!activationStack.empty()) {
						// finish current activation
						Activation a = activationStack.pop();
						MOperationCall opcall = ((OperationExitedEvent) event).getOperationCall();
						MObject obj = opcall.getSelf();
						// MObject obj = getObj(a.getCmd());
						Lifeline ll = fLifelines.get(obj);
						// if the lifeline and the source-lifeline is not marked
						// to be hidden
						if (!ll.isHidden() && (a.getSrc() == null || !a.getSrc().isHidden())) {
							a.setEnd(fNumSteps++);
							// calculate position of answer-message
							lastYValue = a.calculateEnd();
							// only visible view should be drawn -> add only the
							// visible
							// activations
							// to the fActivations-List
							if (fOnlyView) {
								if ((a.getYOfActivationMessArrow() > fView.getY() && a.getYOfActivationMessArrow() <= fView.getY() + fView.getHeight())
										|| (a.getYEndOfActivation() > fView.getY() && a.getYEndOfActivation() <= fView.getY() + fView.getHeight())) {
									fActivations.add(a);
								}
							} else {
								fActivations.add(a);
							}

							// exit activation of the lifeline
							ll.exitActivation();
							// calculate message length of the answer
							int messLength = a.getMessLength();
							// if necessary set fMaxMessLength of properties
							if (messLength > 0 && messLength > fProperties.maxActMess()) {
								fProperties.setMaxActMess(messLength);
							} else if (messLength < 0 && -messLength > fProperties.maxActMess()) {
								fProperties.setMaxActMess(-messLength);
							}
						}
						if (opcall.hasExecutedTransitions() && !ll.isHidden() && ll.isShowStates()) {
							if (fProperties.isStatesShown()) {
								lastYValue += fProperties.getStateNodeExtension();
							}
						}
					}
				}
			} else if (event instanceof LinkInsertedEvent) {
				synchronized (this) {
					lastYValue = drawInsert((LinkInsertedEvent) event, activationStack, lastYValue, lastAct);
				}
			} else if (event instanceof LinkDeletedEvent) {
				synchronized (this) {
					lastYValue = drawDelete((LinkDeletedEvent) event, activationStack, lastYValue, lastAct);
				}
				// if user disabled 'compact display'
			} else if (!fProperties.compactDisplay()) {
				// increase number of steps
				synchronized (this) {
					fNumSteps++;
					// calculate new value for lastYValue
					int actStep = fProperties.actStep();
					if (fProperties.getActManDist() != -1) {
						actStep = fProperties.getActManDist();
					}
					lastYValue += actStep;
				}
			}
		}

		// calculate positions of all lifelines
		calculateLlPositions(getFontMetrics(fProperties.getFont()));

		// set preferred size of the sequence diagram window
		// -> needed for the right display of the scrollbars
		setPreferredSize(new Dimension(fLastXValue + fProperties.getRightMargin(), lastYValue + fProperties.yStart() + fProperties.getBottomMargin()));

		// repaint the diagram
		revalidate();
		repaint();
	}

	/**
	 * Calculates the position of the next message depending on the given
	 * parameters.
	 * 
	 * @param lastYValue the y value of the last drawn message
	 * @param cmd the command to be drawn in the diagram
	 * @param owner the owner lifeline of the command message
	 * @return the position of the next message
	 */
	private int calculateNextMessPosition(int lastYValue, Event event, Lifeline owner) {
		int value = lastYValue;
		// if there has not been drawn a message so far
		// -> calculate position of first message in the sequence diagram
		if (lastYValue == 0) {
			FontMetrics fm = getFontMetrics(fProperties.getFont());
			// Offset between message and edge = 2
			if (event instanceof ObjectCreatedEvent || event instanceof LinkInsertedEvent) {
				value = fProperties.yStart() + owner.getObjectBox().getHeight() / 2 + fm.getHeight() + 2;
			} else {
				value = fProperties.yStart();
				if (fProperties.getActManDist() != -1) {
					value += fProperties.getActManDist() + fm.getHeight() + 2;
				} else {
					value += fProperties.actStep();
				}
				if (owner.isShowStates() && owner.hasStatesMachine() && fProperties.isStatesShown()) {
					value += fProperties.getStateNodeHeight();
				}
			}
			// otherwise
			// -> calculate next position subject to the user settings
		} else {
			if (fProperties.getActManDist() != -1) {
				value += fProperties.getActManDist();
			} else {
				value += fProperties.actStep();

			}
		}
		// return position for the next message
		return value;
	}

	/**
	 * Creates the activations following from a create command and calculates
	 * the y-position of this activation in the sequence diagram.
	 * 
	 * @param cmd the create command for which the activations should be created
	 * @param activationStack the Stack of all activations created so far
	 * @param lastyValue the y-Value of the last created activation, that is the
	 *            activation drawn atop of the activation which should be
	 *            created now
	 * @param lastAct the antecessor of this activation
	 * @return int the y-value in the sequence diagram of the created activation
	 */
	private synchronized int drawCreate(ObjectCreatedEvent event, Stack<Activation> activationStack, int lastyValue, Activation lastAct) {
		int yValue = lastyValue;
		MObject obj = event.getCreatedObject();
		// Lifeline of the regarded object
		Lifeline ll = fLifelines.get(obj);
		// if create should be drawn in the diagram
		if (fProperties.showCreate() && !ll.isHidden()) {
			// calculate position of the create-message
			yValue = calculateNextMessPosition(yValue, event, ll);
			// mark lifeline to draw
			ll.setDraw(true);
			// get last activation
			Activation srcAct = null;
			if (!activationStack.empty()) {
				srcAct = activationStack.peek();
			}
			// if the source-lifeline is not marked to be hidden
			if (srcAct == null || !srcAct.owner().isHidden()) {
				// create new activation
				Activation a = new Activation(fNumSteps, ll, event, srcAct, yValue, this);
				// object box of the lifeline
				ObjectBox objBox = ll.getObjectBox();
				// get x-value on which the lifeline is positiones
				int xValue = ll.xValue();
				// add new actiavtion to the lifeline
				ll.addActivation(a);
				// calculate length of activation message
				a.calculateMessLength();

				// there is no answer message for create messages
				a.setEnd(0);
				// set x-value of object box
				objBox.setX(xValue);

				// set y-position of the new activation
				a.setY(yValue);
				// only visible view should be drawn -> add only the visible
				// activations
				// to the fActivations-List
				if (fOnlyView) {
					if ((a.getYOfActivationMessArrow() > fView.getY() && a.getYOfActivationMessArrow() < fView.getY() + fView.getHeight())
							|| (a.getEnd() > fView.getY() && a.getYEndOfActivation() < fView.getY() + fView.getHeight())) {
						fActivations.add(a);
					}
				} else {
					fActivations.add(a);
				}
				// set y value of the object box
				objBox.setY(yValue - objBox.getHeight() / 2);
				// see above
				int messLength = a.getMessLength();
				if (messLength > 0 && messLength > fProperties.maxActMess()) {
					fProperties.setMaxActMess(messLength);
				} else if (messLength < 0 && -messLength > fProperties.maxActMess()) {
					fProperties.setMaxActMess(-messLength);
				}
				fNumSteps++;
			} else if (!fProperties.compactDisplay()) {
				yValue = calculateNextMessPosition(yValue, event, ll);
				fNumSteps++;
			}
			// If a object was created and has state machines
			if (!obj.cls().getOwnedProtocolStateMachines().isEmpty() && ll.isShowStates()) {
				if (fProperties.isStatesShown()) {
					yValue += fProperties.getStateNodeExtension();
				}
			}
		}

		return yValue;
	}

	/**
	 * Creates the activations following from a destroy command and calculates
	 * the y-position of this activation in the sequence diagram.
	 * 
	 * @param cmd the destroy command for which the activations should be
	 *            created
	 * @param activationStack the Stack of all activations created so far
	 * @param lastYValue the y-Value of the last created activation, that is the
	 *            activation drawn atop of the activation which should be
	 *            created now
	 * @param lastAct the antecessor of this activation
	 * @return the y-value in the sequence diagram of the created activation
	 */
	private synchronized int drawDestroy(ObjectDestroyedEvent event, Stack<Activation> activationStack, int lastYValue, Activation lastAct) {

		int yValue = lastYValue;

		MObject obj = event.getDestroyedObject();
		Lifeline ll = fLifelines.get(obj);
		if (fProperties.showDestroy() && !ll.isHidden()) {
			yValue = calculateNextMessPosition(yValue, event, ll);
			ll.setDraw(true);
			Activation srcAct = null;
			if (!activationStack.empty()) {
				srcAct = activationStack.peek();
			}
			if (srcAct == null || !srcAct.owner().isHidden()) {
				ObjectBox objBox = ll.getObjectBox();
				if (objBox.getYPosOfBoxStart() == -1) {
					int xValue = ll.xValue();
					fNumSteps++;
					objBox.setX(xValue);
					objBox.setY(fProperties.yStart() - 10 - objBox.getHeight());
				}

				Activation a = new Activation(fNumSteps, ll, event, srcAct, yValue, this);
				ll.addActivation(a);
				a.calculateMessLength();
				activationStack.push(a);

				a = activationStack.pop();
				a.setEnd(0);

				a.setY(yValue);
				yValue = a.calculateEnd();
				if (fOnlyView) {
					if ((a.getYOfActivationMessArrow() > fView.getY() && a.getYOfActivationMessArrow() < fView.getY() + fView.getHeight())
							|| (a.getEnd() > fView.getY() && a.getYEndOfActivation() < fView.getY() + fView.getHeight())) {
						fActivations.add(a);
					}
				} else {
					fActivations.add(a);
				}
				int messLength = a.getMessLength(); // a.calculateMessLength();
				if (messLength > 0 && messLength > fProperties.maxActMess()) {
					fProperties.setMaxActMess(messLength);
				} else if (messLength < 0 && -messLength > fProperties.maxActMess()) {
					fProperties.setMaxActMess(-messLength);
				}
				fNumSteps++;
			} else if (!fProperties.compactDisplay()) {
				yValue = calculateNextMessPosition(yValue, event, ll);
				fNumSteps++;
			}
		}

		return yValue;
	}

	/**
	 * Creates the activations following from a set command and calculates the
	 * y-position of this activation in the sequence diagram.
	 * 
	 * @param cmd the set command for which the activations should be created
	 * @param activationStack the Stack of all activations created so far
	 * @param lastYValue the y-Value of the last created activation, that is the
	 *            activation drawn atop of the activation which should be
	 *            created now
	 * @param lastAct the antecessor of this activation
	 * @return the y-value in the sequence diagram of the created activation
	 */
	private synchronized int drawSet(AttributeAssignedEvent event, Stack<Activation> activationStack, int lastYValue, Activation lastAct) {

		MObject obj = event.getObject();
		Lifeline ll = fLifelines.get(obj);

		int yValue = lastYValue;
		if ((fProperties.showSet() && !ll.isHidden()) || !fProperties.compactDisplay()) {
			Activation srcAct = null;
			if (!activationStack.empty())
				srcAct = activationStack.peek();

			if (srcAct == null || !srcAct.owner().isHidden()) {
				ObjectBox objBox = ll.getObjectBox();
				if (objBox.getYPosOfBoxStart() == -1) {
					int xValue = ll.xValue();
					objBox.setX(xValue);
					objBox.setY(fProperties.yStart() - 10 - objBox.getHeight());
				}

				Activation a = new Activation(fNumSteps, ll, event, srcAct, yValue, this);

				if (fProperties.showSet()) {
					ll.enterActivation(a);
					a.calculateMessLength();
					ll.exitActivation();
					activationStack.push(a);
					a = activationStack.pop();
				}

				fNumSteps++;

				a.setEnd(fNumSteps);
				yValue = calculateNextMessPosition(lastYValue, event, ll);
				a.setY(yValue);
				int messLength = a.getMessLength();
				if (messLength > 0 && messLength > fProperties.maxActMess()) {
					fProperties.setMaxActMess(messLength);
				} else if (messLength < 0 && -messLength > fProperties.maxActMess()) {
					fProperties.setMaxActMess(-messLength);
				}
				fNumSteps++;
				yValue = a.calculateEnd();
				if (fProperties.showSet()) {
					ll.setDraw(true);
					if (fOnlyView) {
						if ((a.getYOfActivationMessArrow() > fView.getY() && a.getYOfActivationMessArrow() < fView.getY() + fView.getHeight())
								|| (a.getEnd() > fView.getY() && a.getYEndOfActivation() < fView.getY() + fView.getHeight())) {

							fActivations.add(a);
						}
					} else {
						fActivations.add(a);
					}
				}
			}
		}
		return yValue;
	}

	/**
	 * Creates the activations following from a openter command and calculates
	 * the y-position of this activation in the sequence diagram.
	 * 
	 * @param cmd the openter command for which the activations should be
	 *            created
	 * @param activationStack the Stack of all activations created so far
	 * @param lastYValue the y-Value of the last created activation, that is the
	 *            activation drawn atop of the activation which should be
	 *            created now
	 * @param lastAct the antecessor of this activation
	 * @return the y-value in the sequence diagram of the created activation
	 */
	private synchronized int drawOpEnter(OperationEnteredEvent event, Stack<Activation> activationStack, int lastYValue, Activation lastAct) {

		// see above
		MOperationCall operationCall = event.getOperationCall();

		MObject obj = operationCall.getSelf();
		Lifeline ll = fLifelines.get(obj);
		int yValue = lastYValue;

		ll.setDraw(true);
		Activation srcAct = null;

		if (!activationStack.empty())
			srcAct = activationStack.peek();
		if (!ll.isHidden() && (srcAct == null || !srcAct.owner().isHidden()))
			yValue = calculateNextMessPosition(yValue, event, ll);

		ObjectBox objBox = ll.getObjectBox();

		if (objBox.getYPosOfBoxStart() == -1) {
			int xValue = ll.xValue();
			objBox.setX(xValue);
			objBox.setY(fProperties.yStart() - 10 - objBox.getHeight());
		}

		Activation a = new Activation(fNumSteps, ll, event, srcAct, yValue, this);

		a.calculateMessLength();
		if (!ll.isHidden() && (srcAct == null || !srcAct.owner().isHidden())) {
			fNumSteps++;
		}
		a.setY(yValue);
		// operation call was successful -> increase activation nesting of
		// lifeline
		// -> call enterActivation
		if (event.getOperationCall().enteredSuccessfully()) {
			activationStack.push(a);
			ll.enterActivation(a);
			// otherwise do not increase activationNesting -> call
			// addActivation and setEnd
		} else {
			a.setEnd(fNumSteps);
			yValue = a.calculateEnd();
			ll.addActivation(a);
		}

		int messLength = a.getMessLength();
		if (messLength > 0 && messLength > fProperties.maxActMess()) {
			fProperties.setMaxActMess(messLength);
		} else if (messLength < 0 && -messLength > fProperties.maxActMess()) {
			fProperties.setMaxActMess(-messLength);
		}
		if (!ll.isHidden() && (srcAct == null || !srcAct.owner().isHidden())) {
			if (fOnlyView) {
				if ((a.getYOfActivationMessArrow() > fView.getY() && a.getYOfActivationMessArrow() < fView.getY() + fView.getHeight())
						|| (a.getEnd() > fView.getY() && a.getYEndOfActivation() < fView.getY() + fView.getHeight())) {
					fActivations.add(a);
				}
			} else {
				fActivations.add(a);
			}
		}
		// }
		return yValue;
	}

	/**
	 * Creates the activations following from a insert command and calculates
	 * the y-position of this activation in the sequence diagram.
	 * 
	 * @param event the insert command for which the activations should be
	 *            created
	 * @param activationStack the Stack of all activations created so far
	 * @param lastYValue the y-Value of the last created activation, that is the
	 *            activation drawn atop of the activation which should be
	 *            created now
	 * @param lastAct the antecessor of this activation
	 * @return the y-value in the sequence diagram of the created activation
	 */
	private synchronized int drawInsert(LinkInsertedEvent event, Stack<Activation> activationStack, int lastYValue, Activation lastAct) {

		MLink link = event.getLink();

		Lifeline ll = fLifelines.get(link);

		int yValue = lastYValue;
		if ((fProperties.showInsert() && !ll.isHidden()) || !fProperties.compactDisplay()) {
			Activation srcAct = null;

			if (!activationStack.empty()) {
				srcAct = activationStack.peek();
			}
			if (srcAct == null || !srcAct.owner().isHidden()) {
				yValue = calculateNextMessPosition(yValue, event, ll);
				if (ll.getAntecessor() != null) {
					ObjectBox antBox = ll.getAntecessor().getObjectBox();
					int antEnd = antBox.getYPosOfBoxStart() + antBox.getHeight();
					if (yValue < antEnd)
						yValue = antEnd;
				}

				InsertActivation a = new InsertActivation(fNumSteps, ll, event, srcAct, yValue, this);
				ObjectBox objBox = ll.getObjectBox();
				int xValue = ll.xValue();

				if (fProperties.showInsert()) {
					int numObjects = event.getNumParticipants();
					for (MObject object : event.getParticipants()) {
						Lifeline oll = fLifelines.get(object);
						if (!oll.isHidden()) {
							oll.setDraw(true);
							if (oll.getObjectBox().getYPosOfBoxStart() == -1) {
								oll.getObjectBox().setX(xValue);
								oll.getObjectBox().setY(fProperties.yStart() - 10 - objBox.getHeight());
							}

							if (fProperties.getActManDist() != -1) {
								yValue += numObjects * fProperties.getActManDist();
							} else {
								yValue += numObjects * fProperties.actStep();
							}
							fNumSteps++;
						}
					}

					ll.setDraw(true);
					ll.enterActivation(a);
					a.calculateMessLength();
					ll.exitActivation();
					activationStack.push(a);
					a = (InsertActivation) activationStack.pop();
				}
				fNumSteps++;
				a.setEnd(fNumSteps);
				objBox.setX(xValue);
				yValue = calculateNextMessPosition(lastYValue, event, ll);
				a.setY(yValue);
				objBox.setY(yValue - objBox.getHeight() / 2);
				fNumSteps++;
				yValue = a.calculateEnd();
				if (fProperties.showInsert()) {
					if (fOnlyView) {
						if ((a.getYOfActivationMessArrow() > fView.getY() && a.getYOfActivationMessArrow() < fView.getY() + fView.getHeight())
								|| (a.getEnd() > fView.getY() && a.getYEndOfActivation() < fView.getY() + fView.getHeight())) {

							fActivations.add(a);
						}
					} else {
						fActivations.add(a);
					}

				}
				int messLength = a.getMessLength();
				if (messLength > 0 && messLength > fProperties.maxActMess()) {
					fProperties.setMaxActMess(messLength);
				} else if (messLength < 0 && -messLength > fProperties.maxActMess()) {
					fProperties.setMaxActMess(-messLength);
				}
			}

			// If a object was created and has state machines
			if (link instanceof MLinkObjectImpl) {
				if (!((MLinkObjectImpl) link).cls().getOwnedProtocolStateMachines().isEmpty() && ll.isShowStates()) {
					if (fProperties.isStatesShown()) {
						yValue += fProperties.getStateNodeExtension();
					}
				}
			}
		}
		return yValue;
	}

	/**
	 * Creates the activations following from a destroy command and calculates
	 * the y-position of this activation in the sequence diagram.
	 * 
	 * @param event the destroy command for which the activations should be
	 *            created
	 * @param activationStack the Stack of all activations created so far
	 * @param lastYValue the y-Value of the last created activation, that is the
	 *            activation drawn atop of the activation which should be
	 *            created now
	 * @param lastAct the antecessor of this activation
	 * @return the y-value in the sequence diagram of the created activation
	 */
	private synchronized int drawDelete(LinkDeletedEvent event, Stack<Activation> activationStack, int lastYValue, Activation lastAct) {

		MLink key = event.getLink();

		int yValue = lastYValue;
		Lifeline ll = fLifelines.get(key);
		if (fProperties.showDelete() && !ll.isHidden()) {
			Activation srcAct = null;
			if (!activationStack.empty()) {
				srcAct = activationStack.peek();
			}
			if (srcAct == null || !srcAct.owner().isHidden()) {
				if (srcAct == null || !srcAct.owner().isHidden()) {
					yValue = calculateNextMessPosition(yValue, event, ll);

					ll.setDraw(true);

					ObjectBox objBox = ll.getObjectBox();
					if (objBox.getYPosOfBoxStart() == -1) {
						int xValue = ll.xValue();
						fNumSteps++;
						objBox.setX(xValue);
						objBox.setY(fProperties.yStart() - 10 - objBox.getHeight());
					}

					ll.setNesting(0);

					Activation a = new Activation(fNumSteps, ll, event, srcAct, yValue, this);
					ll.addActivation(a);
					a.calculateMessLength();
					activationStack.push(a);
					fNumSteps++;

					a = activationStack.pop();
					a.setEnd(0);
					yValue = calculateNextMessPosition(lastYValue, event, ll);

					a.setY(yValue);
					yValue = a.calculateEnd();
					if (fOnlyView) {
						if ((a.getYOfActivationMessArrow() > fView.getY() && a.getYOfActivationMessArrow() < fView.getY() + fView.getHeight())
								|| (a.getEnd() > fView.getY() && a.getYEndOfActivation() < fView.getY() + fView.getHeight())) {
							fActivations.add(a);
						}
					} else {
						fActivations.add(a);
					}
					int messLength = a.getMessLength();
					if (messLength > 0 && messLength > fProperties.maxActMess()) {
						fProperties.setMaxActMess(messLength);
					} else if (messLength < 0 && -messLength > fProperties.maxActMess()) {
						fProperties.setMaxActMess(-messLength);
					}
				} else if (!fProperties.compactDisplay()) {
					yValue = calculateNextMessPosition(yValue, event, ll);
					fNumSteps++;
				}
			}
		}
		return yValue;
	}

	/**
	 * Calculates the lifeline which has as y-value the given value.
	 * 
	 * @param x the y-value of the lifeline which should be found
	 * @return Lifeline the Lifeline which is located on the given y-value
	 */
	private synchronized Lifeline getLifeline(int x, int y) {
		// view each lifeline
		for (Lifeline ll : fLifelines.values()) {
			// object box of the lifeline
			ObjectBox ob = ll.getObjectBox();
			// if x-value is betwwen the start- and end-x-value of the object
			// box --> right lifeline is found
			if (ob.getStart() < x && ob.getEnd() > x && ob.getYPosOfBoxStart() < y && y < ll.getEndPosY() && !ll.isHidden())
				return ll;
		}
		return null;
	}

	/**
	 * Restores the position values of all lifelines.
	 * 
	 */
	synchronized void restoreAllValues() {
		// view each lifeline and restore values
		for (Lifeline ll : fLifelines.values()) {
			ll.restoreValues();
		}
		// reset value of fMaxActMess to 0
		fProperties.setMaxActMess(0);
		// reset fLastXValue to 0
		fLastXValue = 0;
	}

	/**
	 * Prints the diagram. Implementation of Printable interface.
	 * 
	 * @param g the context into which the page is drawn
	 * @param pf the size and orientation of the page being drawn
	 * @param pi the zero based index of the page to be drawn (the page index)
	 * @return PAGE_EXISTS if the page is rendered successfully or NO_SUCH_PAGE
	 *         if pageIndex specifies a non-existent page.
	 * @throws PrinterException thrown when the print job is terminated.
	 */
	public int print(Graphics g, PageFormat pf, int pi) throws PrinterException {
		if (pi >= 1)
			return Printable.NO_SUCH_PAGE;

		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		g2d.translate(pf.getImageableWidth() / 2, pf.getImageableHeight() / 2);

		Dimension d = getSize();
		double scale = Math.min(pf.getImageableWidth() / d.width, pf.getImageableHeight() / d.height);
		// fit to page
		if (scale < 1.0)
			g2d.scale(scale, scale);
		g2d.translate(-d.width / 2.0, -d.height / 2.0);

		Font f = Font.getFont("use.gui.print.diagramFont", getFont());
		g2d.setFont(f);

		// update the sequence diagram
		update();
		// if only the visible view should be printed -> call drawView
		// otherwise -> call method drawDiagram
		if (fOnlyView)
			drawView(g2d); // Diagram(g2d, fView);
		else
			drawDiagram(g2d);

		return Printable.PAGE_EXISTS;
	}

	/**
	 * Prints the whole diagram.
	 * 
	 * @param pf the size and orientation of the page being drawn
	 */
	void printDiagram(PageFormat pf) {
		fOnlyView = false;
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setJobName("Sequence diagram");
		job.setPrintable(this, pf);

		if (job.printDialog()) {
			// Print the job if the user didn't cancel printing
			try {
				job.print();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Prints only the current visible fView.
	 * 
	 * @param pf the size and orientation of the page being drawn
	 */
	void printView(PageFormat pf) {
		fOnlyView = true;
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setJobName("Sequence diagram");
		job.setPrintable(this, pf);

		if (job.printDialog()) {
			// Print the job if the user didn't cancel printing
			try {
				job.print();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * MouseListener needed for the manual moving of a lifeline. If the left
	 * mouse button is pressed and the mouse icon is located on a lifeline, the
	 * lifeline is marked as chooedLifeline while the mouse button is pressed.
	 * If the mouse button is released, chooeslifeline is set as null.
	 * 
	 * @author Antje Werner
	 */
	private class MyMouseListener extends MouseAdapter {

		/**
		 * Checks if the mouse button is pressed on a lifeline and if yes marks
		 * this lifeline as fChoosedLl.
		 * 
		 * @param event the releasing MouseEvent
		 */

		public void mousePressed(MouseEvent event) {

			int modifiers = event.getModifiersEx();
			// The last selected lifeline
			Lifeline pickedLifeline = getLifeline(event.getX(), event.getY());

			switch (modifiers) {
			// If only left key of mouse is clicked
			case InputEvent.BUTTON1_DOWN_MASK:
				if (pickedLifeline != null) {
					if (!choosedLifelines.isSelected(pickedLifeline)) {
						choosedLifelines.clear();
						choosedLifelines.add(pickedLifeline);
					}
					fDragMode = DRAG_ITEMS;
					fDragStart = event.getPoint();
					repaint();
				} else {
					choosedLifelines.clear();
					fDragMode = DRAG_NONE;
					repaint();
				}
				break;
			// If shift key with left key of mouse are clicked
			case InputEvent.SHIFT_DOWN_MASK | InputEvent.BUTTON1_DOWN_MASK:
				if (pickedLifeline != null) {
					// add or remove this component to the selection
					if (choosedLifelines.isSelected(pickedLifeline))
						choosedLifelines.remove(pickedLifeline);
					else
						choosedLifelines.add(pickedLifeline);
					if (choosedLifelines.isEmpty())
						fDragMode = DRAG_NONE;
					repaint();
				}
				break;
			default:
			}
		}

		/**
		 * Unmarks the fChoosedLl, when the mouse button has been released.
		 * 
		 * @param event the releasing MouseEvent
		 */
		public void mouseReleased(MouseEvent event) {
			if (event.getButton() == MouseEvent.BUTTON1) {
				if (fIsDragging) {
					event.getComponent().setCursor(fCursor);
					fIsDragging = false;
					fDragMode = DRAG_NONE;
				}
				// update the sequence diagram
				update();
			}
		}

	}

	/**
	 * MouseMotionListener needed for drawing the movement of the chooed
	 * lifeline. While the left mouse button is pressed the choosed lifeline can
	 * be moved and each movement is shown in the sequence diagram.
	 * 
	 * motion events
	 * 
	 * @author Antje Werner
	 */
	private class MyMouseMotionListener extends MouseMotionAdapter {

		/**
		 * Resets the values of the choosed Lifeline, when the Mouse has been
		 * moved while the mouse button is pressed.
		 * 
		 * @param event the releasing MouseEvent
		 */
		public void mouseDragged(MouseEvent event) {
			if (fDragMode == DRAG_NONE)
				return;

			// switch cursor at start of dragging
			if (!fIsDragging) {
				fCursor = getCursor();
				setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				fIsDragging = true;
			}

			if (fDragMode == DRAG_ITEMS) {
				double posX = event.getX();
				int movedX = (int) (posX - fDragStart.getX());

				for (Lifeline ll : choosedLifelines) {
					int oldX = ll.xValue();
					int minX = getBounds().x + fProperties.getLeftMargin() + OFFSET_LEFT_MARGIN_ACTOR_CENTER + fProperties.frWidth() / 2 + ll.getObjectBox().getWidth() / 2;
					if (oldX + movedX < minX) {
						ll.setNewValues(minX);
					} else {
						ll.setNewValues(oldX + movedX);
					}
					fDragStart = event.getPoint();
				}

				// calculate all lifeline positions
                calculateLlPositions(getFontMetrics(fProperties.getFont()));
				// individual position should be hold
				fProperties.setIndividualLl(true);
			}
			// repaint the diagram
			revalidate();
			repaint();
		}

	}

}