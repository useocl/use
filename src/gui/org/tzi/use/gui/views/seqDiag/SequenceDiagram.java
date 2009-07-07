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

package org.tzi.use.gui.views.seqDiag;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.CmdCreatesObjects;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MCmdCreateInsertObjects;
import org.tzi.use.uml.sys.MCmdCreateObjects;
import org.tzi.use.uml.sys.MCmdDeleteLink;
import org.tzi.use.uml.sys.MCmdDestroyObjects;
import org.tzi.use.uml.sys.MCmdInsertLink;
import org.tzi.use.uml.sys.MCmdOpEnter;
import org.tzi.use.uml.sys.MCmdOpExit;
import org.tzi.use.uml.sys.MCmdSetAttribute;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.cmd.CommandFailedException;

/**
 * A SequenceDiagram shows an UML sequence diagramm of events.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Mark Richters, Antje Werner
 */
@SuppressWarnings("serial")
public class SequenceDiagram extends JPanel implements Printable {

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
    private Lifeline fChoosedLl;

    /**
     * The last x-value in the diagram (here ends the diagram). Needed for
     * setting the preferred Size of the panel.
     */
    private int fLastXValue;

    /**
     * Needed for the Frames.
     */
    private static final float DASH1[] = { 5.0f };

    /**
     * The dashed Stroke, used for example for the lifelines or for the answers
     * of the activations.
     */
    private static final BasicStroke DASHEDSTROKE = new BasicStroke(1.0f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f, DASH1, 0.0f);

    /**
     * Specifies if only the actual fView should be repainted or the whole
     * diagram (for example for printing the whole diagram)
     */
    public boolean fOnlyView;

    /**
     * The actual position and dimension of the visible view.
     */
    public Rectangle fView;

    /**
     * The MainWindow from which this sequence diagram is called. Needed for the
     * properties windows.
     */
    private MainWindow fMainWindow;

    /**
     * Vector which contains all Lifelines that should be hidden in the diagram.
     */
    private Vector<Lifeline> fHiddenLifelines;

    /**
     * Constructs a new SequenceDiagram-Object.
     * 
     * @param system
     *            contains (direct or indirect) all needed informations mainW
     * @param mainW
     *            the MainWindow object from which the constructor is called
     */
    public SequenceDiagram(MSystem system, MainWindow mainW) {
        fSystem = system;
        fMainWindow = mainW;
        fProperties = new SDProperties();
        fObProperties = new OBProperties();
        fLifelines = new HashMap<Object, Lifeline>();
        fActivations = new ArrayList<Activation>();
        fHiddenLifelines = new Vector<Lifeline>();

        // at the beginning no Lifeline is chosen
        fChoosedLl = null;

        // normally only the visible view should be drawn
        fOnlyView = true;

        fView = new Rectangle();

        setBorder(BorderFactory.createEmptyBorder());
        setBackground(Color.white);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(50, 50));
        setPreferredSize(new Dimension(200, 100));
        fPopupMenu = new JPopupMenu();
        createPopupMenu();
        llMenu = new JPopupMenu();

        // Popup-Menu for hiding a maked lifeline
        final JMenuItem cbHideLl = new JMenuItem("Hide selected Lifeline");
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // mark choosed lifeline as hidden
                fChoosedLl.setHidden(true);
                // add to list of all hidden lifelines
                fHiddenLifelines.add(fChoosedLl);
                // unmark lifeline
                fChoosedLl = null;
                // update and repaint the whole diagram
                try {
                    update();
                } catch (CommandFailedException cfe) {
                    // ignored
                }
                repaint();
            }
        };
        cbHideLl.addActionListener(al);
        llMenu.add(cbHideLl);

        addMouseListener(new SDPopupListener(fPopupMenu, llMenu));
        addMouseListener(new MyMouseListener());
        addMouseMotionListener(new MyMouseMotionListener());
    }

    /*
     * PopupListener which differs between the two possible Popup-Menus. If a
     * lifeline is chosen by the user, the llMenu should be shown. Otherwise the
     * main fPopupMenu.
     */
    class SDPopupListener extends PopupListener {
        private JPopupMenu fLlMenu;

        public SDPopupListener(JPopupMenu SDMenu, JPopupMenu llMenu) {
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
                if (fChoosedLl == null)
                    fPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                else
                    fLlMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }

    }

    /**
     * Sets the bounds of the visible fView. Used by a SDSrcollPane-Object.
     * 
     * @param bounds
     *            the bounds of the actual visible fView
     */
    public void setViewBounds(Rectangle bounds) {
        fView = bounds;
        repaint();
    }

    /**
     * Creates the context menu, which is shown by a right mouse click.
     */
    void createPopupMenu() {
        fPopupMenu = new JPopupMenu();
        // menu item "Anti-aliasing"
        
        final JCheckBoxMenuItem cbAntiAliasing = new JCheckBoxMenuItem(
                "Anti-aliasing");
        cbAntiAliasing.setState(fProperties.getAntiAliasing());
        cbAntiAliasing.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                fProperties
                        .setAntiAliasing(ev.getStateChange() == ItemEvent.SELECTED);
                repaint();
            }
        });
        fPopupMenu.add(cbAntiAliasing);

        // menu item "Show values"
        final JCheckBoxMenuItem cbShowValues = new JCheckBoxMenuItem(
                "Show values");
        cbShowValues.setState(fProperties.showValues());
        cbShowValues.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                fProperties
                        .showValues(ev.getStateChange() == ItemEvent.SELECTED);
                repaint();
            }
        });
        fPopupMenu.add(cbShowValues);

        // menu item "Compact display"
        final JCheckBoxMenuItem cbCompactDisplay = new JCheckBoxMenuItem(
                "Compact display");
        cbCompactDisplay.setState(fProperties.compactDisplay());
        cbCompactDisplay.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                fProperties
                        .compactDisplay(ev.getStateChange() == ItemEvent.SELECTED);
                repaint();
            }
        });
        fPopupMenu.add(cbCompactDisplay);
        fPopupMenu.addSeparator();

        // new menu item "Show all Commands"
        final JCheckBoxMenuItem cbShowAllCommands = new JCheckBoxMenuItem(
                "Show all Commands");
        cbShowAllCommands.setState(fProperties.showCreate()
                && fProperties.showSet() && fProperties.showDestroy()
                && fProperties.showInsert() && fProperties.showDelete());
        cbShowAllCommands.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                fProperties
                        .showCreate(ev.getStateChange() == ItemEvent.SELECTED);
                fProperties.showSet(ev.getStateChange() == ItemEvent.SELECTED);
                fProperties
                        .showDestroy(ev.getStateChange() == ItemEvent.SELECTED);
                fProperties
                        .showInsert(ev.getStateChange() == ItemEvent.SELECTED);
                fProperties
                        .showDelete(ev.getStateChange() == ItemEvent.SELECTED);
                restoreAllValues();
                try {
                    update();
                } catch (CommandFailedException cfe) {
                    // ignored
                }
                repaint();
            }
        });
        fPopupMenu.add(cbShowAllCommands);

        // new menu item "Show somes Commands..."
        final JMenuItem cbShowSomeCommands = new JMenuItem(
                "Show some Command(s)...");
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // open CmdChooseWindow
                createCmdChooseWindow();
                // after closing update diagram
                try {
                    update();
                } catch (CommandFailedException cfe) {
                    // ignored
                }
                // repaint diagram
                repaint();
            }
        };
        cbShowSomeCommands.addActionListener(al);
        fPopupMenu.add(cbShowSomeCommands);

        fPopupMenu.addSeparator();

        // new menu item "Show hidden lifelines"
        final JMenuItem cbShowHidden = new JMenuItem("Show hidden lifelines");
        cbShowHidden.setEnabled(fHiddenLifelines.size() > 0);
        ActionListener al3 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < fHiddenLifelines.size(); i++) {
                    ((Lifeline) (fHiddenLifelines.get(i))).setHidden(false);
                }
                fHiddenLifelines = new Vector<Lifeline>();
                // update diagram
                try {
                    update();
                } catch (CommandFailedException cfe) {
                    // ignored
                }
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
                try {
                    update();
                } catch (CommandFailedException cfe) {
                    // ignored
                }
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
    public void createPropertiesWindow() {
        PropertiesWindow propWin = new PropertiesWindow(this);
        propWin.showWindow();
    }

    /**
     * Creates a new CmdChooseWindow object.
     */
    public void createCmdChooseWindow() {
        CmdChooseWindow chooseWin = new CmdChooseWindow(this);
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
     * @param g
     *            the Graphics-object which should be used by drawing.
     */
    public void paint(Graphics g) {
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
     * @param g
     *            the Graphics2D-object which should be used for drawing.
     */
    public void drawDiagram(Graphics2D g) {
        if (fProperties.getAntiAliasing())
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(fProperties.getFont());
        // respect borders
        Insets insets = getInsets();
        Rectangle r = getBounds();

        r.x += insets.left;
        r.y += insets.top;

        g.setColor(Color.black);

        int x = r.x + fProperties.getLeftMargin(); // + 20;
        int y = r.y + +fProperties.getTopMargin();// 20;

        /* draw actor */

        // head
        g.drawOval(x + 5, y, 10, 10);

        // body
        g.drawLine(x + 10, y + 10, x + 10, y + 30);

        // arms
        g.drawLine(x, y + 20, x + 20, y + 20);

        // legs
        g.drawLine(x + 10, y + 30, x, y + 40);
        g.drawLine(x + 10, y + 30, x + 20, y + 40);

        // the height of the actor-lifeline depends on the user-settings
        // for the distance between two messages
        int y_height = fNumSteps * fProperties.actStep();
        if (fProperties.getActManDist() != -1) {
            y_height = fNumSteps * fProperties.getActManDist();
        }

        // draw all Lifelines which are involved in at least one message
        for (Lifeline ll : fLifelines.values()) {
            // is lifeline involved in at least one message and is not marked as
            // hidden
            if (ll.drawLifeline() && !ll.isHidden()) {
                ll.draw(g, 0);
            }
        }
        
        // draw all activation-messages
        for (int i = 0; i < fActivations.size(); i++) {
            Activation a = fActivations.get(i);
            a.drawMessageSend(g, getFontMetrics(fProperties.getFont()));
        }

        // draw the lifeline of the actor
        g.setColor(Color.white);
        g.fillRect(x + 10 - fProperties.frWidth() / 2, fProperties.yStart(),
                fProperties.frWidth(), y_height);
        g.setColor(Color.black);
        g.drawRect(x + 10 - fProperties.frWidth() / 2, fProperties.yStart(),
                fProperties.frWidth(), y_height);

    }

    /**
     * Draws the view of the diagram, which is situated in the position denoted
     * by the fView attribute.
     * 
     * @param g
     *            the Graphics2D-object which should be used for drawing. values
     */
    public void drawView(Graphics2D g) {
        if (fProperties.getAntiAliasing())
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

        g.setFont(fProperties.getFont());
        
        Map<Object, Lifeline> lifelines = null;
        synchronized (this) {
            lifelines = new HashMap<Object, Lifeline>(fLifelines);
        }
        
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

        // the height of the actor-lifeline depends on the user-settings
        // for the distance between two messages
        int y_height = fNumSteps * fProperties.actStep();
        if (fProperties.getActManDist() != -1) {
            y_height = fNumSteps * fProperties.getActManDist();
        }

        /* draw actor */
        // head
        g.drawOval(x + 5, fY + y, 10, 10);

        // body
        g.drawLine(x + 10, fY + y + 10, x + 10, fY + y + 30);

        // arms
        g.drawLine(x, fY + y + 20, x + 20, fY + y + 20);

        // legs
        g.drawLine(x + 10, fY + y + 30, x, fY + y + 40);
        g.drawLine(x + 10, fY + y + 30, x + 20, fY + y + 40);

        // counter for the proceeding of scrolling
        int counter = 0;
        // has been scrolled by the user?
        if (fY > 0) {
            // start of the actors lifeline
            int y_start = fProperties.yScroll();
            // draw the first line of the zigzag pattern
            g.drawLine(x + 10 - fProperties.frWidth() / 2 + 2, fY
                    + fProperties.yScroll(), x + 10 - fProperties.frWidth() / 2
                    - 2, fY + fProperties.yScroll() + 4);
            g.drawLine(x + 10 + fProperties.frWidth() / 2 + 2, fY
                    + fProperties.yScroll(), x + 10 + fProperties.frWidth() / 2
                    - 2, fY + fProperties.yScroll() + 4);
            // lifeline should begin under the zigzag
            y_start += 4;
            // increase counter
            counter++;

            // if necessary draw the other lines of the zigzag pattern and
            // increase the start-point of the lifelines an the value of counter
            if (fY > 4) {
                // x+10 is the middle of the manicin
                g.drawLine(x + 10 - fProperties.frWidth() / 2 - 2, fY
                        + fProperties.yScroll() + 4, x + 10
                        - fProperties.frWidth() / 2 + 2, fY
                        + fProperties.yScroll() + 8);
                g.drawLine(x + 10 + fProperties.frWidth() / 2 - 2, fY
                        + fProperties.yScroll() + 4, x + 10
                        + fProperties.frWidth() / 2 + 2, fY
                        + fProperties.yScroll() + 8);
                y_start += 4;
                counter++;
            }
            if (fY > 8) {
                g.drawLine(x + 10 - fProperties.frWidth() / 2 + 2, fY
                        + fProperties.yScroll() + 8, x + 10
                        - fProperties.frWidth() / 2 - 2, fY
                        + fProperties.yScroll() + 12);
                g.drawLine(x + 10 + fProperties.frWidth() / 2 + 2, fY
                        + fProperties.yScroll() + 8, x + 10
                        + fProperties.frWidth() / 2 - 2, fY
                        + fProperties.yScroll() + 12);
                y_start += 4;
                counter++;
            }
            if (fY > 12) {
                g.drawLine(x + 10 - fProperties.frWidth() / 2 - 2, fY
                        + fProperties.yScroll() + 12, x + 10
                        - fProperties.frWidth() / 2 + 2, fY
                        + fProperties.yScroll() + 16);
                g.drawLine(x + 10 + fProperties.frWidth() / 2 - 2, fY
                        + fProperties.yScroll() + 12, x + 10
                        + fProperties.frWidth() / 2 + 2, fY
                        + fProperties.yScroll() + 16);
                y_start += 4;
                counter++;
            }

            // choose the drawing area -> drawing begins under the
            // zigzag-pattern
            g.clipRect((int) fView.getX(), (int) fView.getY() + y_start + 1,
                    (int) fView.getWidth(), (int) fView.getHeight() - y_start
                            - 1);
        }
        // draw the actors lifeline
        g.setColor(Color.white);
        g.fillRect(x + 10 - fProperties.frWidth() / 2, fProperties.yStart(),
                fProperties.frWidth(), y_height); // fNumSteps

        g.setColor(Color.black);
        g.drawRect(x + 10 - fProperties.frWidth() / 2, fProperties.yStart(),
                fProperties.frWidth(), y_height); // fNumSteps

        // set the drawing area on the whole visible view
        g.setClip((int) fView.getX(), (int) fView.getY(), (int) fView
                .getWidth(), (int) fView.getHeight());
        // draw all visible lifelines which are involved in at least one message
        for (Lifeline ll : lifelines.values()) {
            if (ll.fObjectBox.getEnd() > fX
                    && ll.fObjectBox.getStart() < fX + fWidth) {
                // is lifeline involved in at least one message and is not
                // marked as hidden
                if (ll.drawLifeline() && !ll.isHidden()) {
                    ll.draw(g, counter);
                }
            }

        }

        // choose the drawing area -> drawing begins under the
        // zigzag-patternS
        g.clipRect((int) fView.getX(), (int) fView.getY()
                + fProperties.yScroll() + 16, (int) fView.getWidth(),
                (int) fView.getHeight() - fProperties.yScroll() - 16);

        // draw all activation-messages
        for (int i = 0; i < fActivations.size(); i++) {
            Activation a = (Activation) fActivations.get(i);
            a.drawMessageSend(g, getFontMetrics(fProperties.getFont()));
        }
    }

    // *********************************************************************************************************

    /**
     * Represents a lifeline of the sequence diagram. It's abstract, because
     * there are two kind of lifelines which must be differentiated: one for
     * objects and one for associations/links.
     * 
     * @author Marc Richters, Antje Werner
     */
    public abstract class Lifeline {
        /**
         * The column of the lifeline.
         */
        int fColumn;

        /**
         * The x-value on which the lifeline is positioned in the diagram.
         */
        int fXValue;

        /**
         * The y-value on which the lifeline starts in the diagram.
         */
        int fYValue;

        /**
         * The length of the maximum message sent from a lifline which ist
         * positioned on the left size of this.
         */
        int fMaxMessLength;

        /**
         * The list of all activations sending a message to this lifeline.
         */
        List<Activation> fActivations;

        /**
         * The current activation nesting on this lifeline.
         */
        int fActivationNesting;

        /**
         * The object box of the lifeline.
         */
        ObjectBox fObjectBox;

        /**
         * The list of all frames on this lifeline.
         */
        List<Frame> fFrames;

        /**
         * The antecessor lifeline.
         */
        Lifeline fAntecessor;

        /**
         * The successor lifeline.
         */
        Lifeline fSuccessor;

        /**
         * Indicates if this lifeline should be drawn in the diagram.
         */
        boolean fDraw;

        /**
         * Indicates if this lifeline has been choosed to be hidden.
         */
        boolean fHidden;

        /**
         * Returns the x-value of the lifeline.
         * 
         * @return the x-value of the lifeline
         */
        int xValue() {
            return fXValue;
        }

        /**
         * Resets, if necessary, the x-Value, the Antecessor, the Successor of
         * the lifeline and the x-Value of the object box, if the x-Value of the
         * lifeline has been changed (for example the lifeline has been moved).
         * 
         * @param x
         *            the new x-value
         */
        void setNewValues(int x) {
            // lifeline has been moved in right direction
            if (x > fXValue) {
                // set new x-Value for the corresponding object box
                fObjectBox.setX(x);
                // set new x-values for the lifeline
                fXValue = x;

                /* calculate the new successor */
                // if old successor is now on the left side of this lifeline
                if (fSuccessor != null && fSuccessor.xValue() < x) {
                    // swap columns
                    int saveColumn = fSuccessor.column();
                    fSuccessor.setColumn(fColumn);
                    fColumn = saveColumn;

                    // update the successors and antecessors
                    Lifeline saveSuccessor = fSuccessor.getSuccessor();
                    if (fSuccessor.getSuccessor() != null)
                        fSuccessor.getSuccessor().setAntecessor(this);
                    if (fAntecessor != null)
                        fAntecessor.setSuccessor(fSuccessor);
                    fSuccessor.setAntecessor(fAntecessor);
                    fSuccessor.setSuccessor(this);
                    setAntecessor(fSuccessor);
                    setSuccessor(saveSuccessor);
                }
                // lifeline has been moved in left direction
            } else if (x < fXValue) {
                // set new x-Value for the corresponding object box
                fObjectBox.setX(x);
                // set new x-values for the lifeline
                fXValue = x;
                /* calculate the new antecessor */

                // if there was no antecessor
                if (fAntecessor == null) {
                    if (x - fObjectBox.getWidth() / 2 < fProperties
                            .getLeftMargin()
                            + 10 + fProperties.frWidth() / 2) {
                        fXValue = fProperties.getLeftMargin() + 10
                                + fProperties.frWidth() / 2
                                + fObjectBox.getWidth() / 2 + 1;
                        fObjectBox.setX(fXValue);
                    }
                    // otherwise if old antecessor is now on the right side of
                    // this lifeline
                } else if (fAntecessor.xValue() > x) {
                    // swap columns
                    int saveColumn = fAntecessor.column();
                    fAntecessor.setColumn(fColumn);
                    fColumn = saveColumn;
                    // update the successors and antecessors
                    Lifeline saveAntecessor = fAntecessor.getAntecessor();
                    if (fAntecessor.getAntecessor() != null)
                        fAntecessor.getAntecessor().setSuccessor(this);
                    if (fSuccessor != null)
                        fSuccessor.setAntecessor(fAntecessor);
                    fAntecessor.setAntecessor(this);
                    fAntecessor.setSuccessor(fSuccessor);
                    setSuccessor(fAntecessor);
                    setAntecessor(saveAntecessor);
                }
            }

        }

        /**
         * Returns the object box.
         * 
         * @return the object box of the lifeline
         */
        ObjectBox getObjectBox() {
            return fObjectBox;
        }

        /**
         * Return the antecessor lifeline.
         * 
         * @return the antecessor lifeline
         */
        Lifeline getAntecessor() {
            return fAntecessor;
        }

        /**
         * Returns the successor lifeline.
         * 
         * @return the successor lifeline
         */
        Lifeline getSuccessor() {
            return fSuccessor;
        }

        /**
         * Returns the maximum message length sent from a lifline which ist
         * positioned on the left size of this.
         * 
         * @return the maximum message length
         */
        int getMaxMessLength() {
            return fMaxMessLength;
        }

        /**
         * Returns the column of the lifeline which depends on teh visibility of
         * the antecessor lifelines.
         * 
         * @return the column
         */
        int column() {
            int column = fColumn;
            // regard all antecessor lifelines
            Lifeline ll = fAntecessor;
            while (ll != null) {
                // if one antecessor is not drawn in the diagram
                if (!ll.drawLifeline() || ll.isHidden()) {
                    // decrease the column of this lifeline
                    column--;
                }
                ll = ll.getAntecessor();
            }
            // returns the column on which this lifeline is drawn
            return column;
        }

        /**
         * Sets the nesting of the lifeline.
         * 
         * @param n
         *            the new nesting
         */
        void setNesting(int n) {
            fActivationNesting = n;
        }

        /**
         * Sets the maximum message length.
         * 
         * @param value
         *            the maximum message length
         */
        void setMaxMessLength(int value) {
            fMaxMessLength = value;
        }

        /**
         * Sets the successor lifeline.
         * 
         * @param successor
         *            the new successor lifeline
         */
        void setSuccessor(Lifeline successor) {
            fSuccessor = successor;
        }

        /**
         * Sets the antecessor lifeline.
         * 
         * @param antecessor
         *            the new antecessor lifeline
         */
        void setAntecessor(Lifeline antecessor) {
            fAntecessor = antecessor;
        }

        /**
         * Sets the column of the lifeline.
         * 
         * @param column
         *            the new column
         */
        void setColumn(int column) {
            fColumn = column;
        }

        /**
         * Adds an activation to the fActivations list and increases the nesting
         * of the Activation and this lifeline.
         * 
         * @param a
         *            the activation to add
         */
        synchronized void enterActivation(Activation a) {
            a.setNesting(++fActivationNesting);
            fActivations.add(a);
        }

        /**
         * Adds an activation to the fActivations list and sets the nesting of
         * the Activation. In contrast to enterActivation(Activation) the
         * nesting not increases.
         * 
         * @param a
         *            the activation to add
         */
        synchronized void addActivation(Activation a) {
            a.setNesting(fActivationNesting);
            fActivations.add(a);
        }

        /**
         * Decreases the activation nesting, when the last Activation has been
         * executes.
         * 
         */
        void exitActivation() {
            fActivationNesting--;
        }

        /**
         * Marks the lifeline to draw in the sequence diagram.
         * 
         * @param draw
         *            true if the lifeline should be drawn; false otherwise
         */
        void drawLifeline(boolean draw) {
            fDraw = draw;
        }

        /**
         * Indicates if this lifeline should be drawn.
         * 
         * @return True is the lifeline has marked to draw; else otherwise
         */
        boolean drawLifeline() {
            return fDraw;
        }

        /**
         * Marks the lifeline to be hidden in the sequence diagram.
         * 
         * @param hidden
         *            true if the lifeline should be hidden; false otherwise
         */
        void setHidden(boolean hidden) {
            fHidden = hidden;
        }

        /**
         * Indicates if this lifeline should be hidden.
         * 
         * @return True is the lifeline has marked to be hidden; false otherwise
         */
        boolean isHidden() {
            return fHidden;
        }

        /**
         * Draws the lifeline as a dashed line.
         * 
         * @param x
         *            the x-value of the line
         * @param y1
         *            the start-y-value of the line
         * @param y2
         *            the end-y-value of the line
         * @param g
         *            the graphics on which the dashed line should be drawn
         */
        public void drawDashedLine(int x, int y1, int y2, Graphics2D g) {
            Stroke oldStroke = g.getStroke();
            g.setStroke(DASHEDSTROKE);
            g.drawLine(x, y1, x, y2);
            g.setStroke(oldStroke);
        }

        /**
         * Draws the lifeline in the sequence diagram. Draws the object box, all
         * activations from the fActivations list, the dashed line and the
         * frames.
         * 
         * @param g
         *            the graphics on which the lifeline should be drawn
         * @param counter
         *            the level of scrolling which was calculated in the
         *            drawView-Method of the SequenceDiagram class.
         */
        public void draw(Graphics2D g, int counter) {
            FontMetrics fm = getFontMetrics(fProperties.getFont());
            // if only the visible view should be drawn
            if (fOnlyView) {
                // set drawing area on the counds of the visible view
                g.clipRect((int) fView.getX(), (int) fView.getY(), (int) fView
                        .getWidth(), (int) fView.getHeight());
            }

            g.setColor(Color.black);
            // the values for the start- and end-point and the height of the
            // area on
            // which the diagram should be drawn -> depends on the value of
            // fOnlyView
            int SB_yValue;
            if (fOnlyView) {
                // set values of the visible view
                SB_yValue = (int) fView.getY();
            } else {
                // diagram starts and ends at 0
                SB_yValue = 0;
            }

            // y-position where lifeline starts
            int y = fProperties.yScroll() - 20;
            // positions where objectbox starts and lifeline ends
            int y_start = 0, y_end = -1;

            // List of activation frames for this lifeline
            fFrames = new ArrayList<Frame>();
            // regard each actiovation of the lifeline
            Iterator<Activation> activationIter = fActivations.iterator();
            synchronized (this) {
                while (activationIter.hasNext()) {
                    y_start = fObjectBox.getY();
                    Activation a = (Activation) activationIter.next();
                    // y-value of the activation message
                    y = a.getY();

                    // if insert- or create-command-> calculate correct position
                    // for
                    // the object box
                    if (a.getCmd() instanceof MCmdInsertLink
                            || a.getCmd() instanceof MCmdCreateObjects) {
                        // if object box was not scrolled to the top so far
                        if (y - fObjectBox.getHeight() / 2 > y_start)
                            y_start = y + fObjectBox.getHeight() / 2;
                    }
                    // if position of object boxis on top of the front of the
                    // diagram
                    if (y_start + fObjectBox.getHeight() < fProperties
                            .yScroll()
                            + SB_yValue) {
                        // set y-Start at the front of the diagram
                        y_start = SB_yValue + fProperties.yScroll()
                                - fObjectBox.getHeight();
                    }
                    // is delete- or destroy-command
                    if (a.getCmd() instanceof MCmdDeleteLink
                            || a.getCmd() instanceof MCmdDestroyObjects) {
                        // lifeline ends at the position of the corresponding
                        // message
                        y_end = y;
                        // otherwise if not create-command
                    } else if (!(a.getCmd() instanceof CmdCreatesObjects)) {
                        // position of return arrow of the current activation
                        int fEnd = a.getEnd();
                        // if frame still active
                        if (fEnd == 0)
                            fEnd = fNumSteps;

                        // calculate position subject to the user settings for
                        // the
                        // message distances
                        if (fProperties.getActManDist() == -1) {
                            fEnd = (fEnd - a.getStart())
                                    * fProperties.actStep();
                        } else {
                            fEnd = (fEnd - a.getStart())
                                    * fProperties.getActManDist();
                        }
                        // add new Frame to fFrames; in case of insert consider
                        // the
                        // 'inserted as' messages
                        if (a.getCmd() instanceof MCmdInsertLink)
                            fFrames.add(new Frame(fXValue, y
                                    + fObjectBox.getHeight() / 2, fEnd - 10, a
                                    .getNesting()));
                        // in case of openter-command -> draw only frame if
                        // operation call was successful
                        else if (!(a.getCmd() instanceof MCmdOpEnter && !((MCmdOpEnter) a
                                .getCmd()).successful()))
                            fFrames.add(new Frame(fXValue, y, fEnd, a
                                    .getNesting()));
                    }
                }
            }// end of while

            // if only inserted as-Message is send to this Lifeline -> position
            // the object
            // box on the front of the diagram
            if (y_start == 0)
                y_start = SB_yValue + fProperties.yScroll()
                        - fObjectBox.getHeight();
            // if only visible view should be drawn and the user has scrolled in
            // vertical direction
            if (fOnlyView && fView.getY() > 0) {
                // draw the zigzag pattern depending on counter
                int boxPos = fObjectBox.getY() + fObjectBox.getHeight();
                if (counter > 0
                        && boxPos < ((int) fView.getY() + fProperties.yScroll())) {
                    g.drawLine(fXValue + 2, (int) fView.getY()
                            + fProperties.yScroll(), fXValue - 2, (int) fView
                            .getY()
                            + fProperties.yScroll() + 4);

                }
                if (counter > 1
                        && boxPos < ((int) fView.getY() + fProperties.yScroll() + 4)) {// scrolled

                    g.drawLine(fXValue - 2, (int) fView.getY()
                            + fProperties.yScroll() + 4, fXValue + 2,
                            (int) fView.getY() + fProperties.yScroll() + 8);

                }
                if (counter > 2
                        && boxPos < ((int) fView.getY() + fProperties.yScroll() + 8)) {

                    g.drawLine(fXValue + 2, (int) fView.getY()
                            + fProperties.yScroll() + 8, fXValue - 2,
                            (int) fView.getY() + fProperties.yScroll() + 12);

                }
                if (counter > 3
                        && boxPos < ((int) fView.getY() + fProperties.yScroll() + 12)) {

                    g.drawLine(fXValue - 2, (int) fView.getY()
                            + fProperties.yScroll() + 12, fXValue + 2,
                            (int) fView.getY() + fProperties.yScroll() + 16);
                }
            }

            // if there was no destroy- or delete- command for this lifeline
            if (y_end == -1) {
                // calculate end of lieline subject to the user settings
                if (fProperties.getActManDist() == -1) {
                    y_end = fProperties.yStart() + fNumSteps
                            * fProperties.actStep();// -
                } else {
                    y_end = fProperties.yStart() + fNumSteps
                            * fProperties.getActManDist();
                }
            }
            // draw objectBox in the right position (->y_start)
            fObjectBox.drawBox(g, fm, y_start);
            if (this == fChoosedLl) {
                g.fillRect(fObjectBox.getStart() - 4, y_start - 4, 4, 4);
                g.fillRect(fObjectBox.getStart() + fObjectBox.getWidth(),
                        y_start - 4, 4, 4);
                g.fillRect(fObjectBox.getStart() - 4, y_end, 4, 4);
                g.fillRect(fObjectBox.getStart() + fObjectBox.getWidth(),
                        y_end, 4, 4);
                Stroke oldStroke = g.getStroke();
                g.setStroke(new BasicStroke(0.3f));
                g.drawLine(fObjectBox.getStart() - 2, y_start - 2, fObjectBox
                        .getEnd() + 2, y_start - 2);
                g.drawLine(fObjectBox.getStart() - 2, y_start - 2, fObjectBox
                        .getStart() - 2, y_end + 2);
                g.drawLine(fObjectBox.getStart() + fObjectBox.getWidth() + 2,
                        y_start - 2, fObjectBox.getStart()
                                + fObjectBox.getWidth() + 2, y_end + 2);
                g.drawLine(fObjectBox.getStart() - 2, y_end + 2, fObjectBox
                        .getStart()
                        + fObjectBox.getWidth() + 2, y_end + 2);
                g.setStroke(oldStroke);
            }
            // if only the visible view should be drawn
            if (fOnlyView) {
                // set drawing area below the zigzag pattern
                g.clipRect((int) fView.getX(), (int) fView.getY()
                        + fProperties.yScroll() + counter * 4, (int) fView
                        .getWidth(), (int) fView.getHeight()
                        - fProperties.yScroll() - counter * 4);
            }
            // draw dashedLine in the right position and with the right height
            drawDashedLine(fXValue, y_start + fObjectBox.getHeight(), y_end, g);

            // draw frames for this lifeline
            for (int i = 0; i < fFrames.size(); i++) {
                Frame frame = (Frame) fFrames.get(i);
                frame.drawFrame(g);
            }

            // regard each activation of this lifeline
            activationIter = fActivations.iterator();
            synchronized (this) {
                while (activationIter.hasNext()) {
                    Activation a = (Activation) activationIter.next();
                    y = a.getY();

                    // if Object destroyed or Link deleted -> draw cross
                    if (a.getCmd() instanceof MCmdDeleteLink
                            || a.getCmd() instanceof MCmdDestroyObjects) {
                        y_end = y;

                        Stroke oldStroke = g.getStroke();
                        g.setStroke(new BasicStroke(2.0f));
                        g.drawLine(fXValue - 10, y - 10, fXValue + 10, y + 10);
                        g.drawLine(fXValue + 10, y - 10, fXValue - 10, y + 10);
                        g.setStroke(oldStroke);

                    }

                }
            }
            // reset drawing area to the whole visible view
            if (fOnlyView) {
                g.setClip((int) fView.getX(), (int) fView.getY(), (int) fView
                        .getWidth(), (int) fView.getHeight());
            }
        }

        /**
         * Abstract method for restoring some values.
         * 
         */
        public abstract void restoreValues();

        /**
         * Searches the longest Activation-Message for this Lifeline.
         * 
         * @param fm
         *            the FontMetrics object of the diagram
         */
        public synchronized int getLongestMessage(FontMetrics fm) {
            int returnValue = 0;
            // regard each activation of this lifeline
            for (int i = 0; i < fActivations.size(); i++) {
                Activation a = (Activation) fActivations.get(i);
                // length of the longest message of the regarded activation
                int messLength = a.getMessLength();
                // if bigger than fMaxMessLength -> new fMaxMessLength
                if (messLength > 0 && messLength > fMaxMessLength)
                    fMaxMessLength = messLength;
                // if messLength < 0 and longest message of successor is smaller
                // then -messLength
                // -> set fMaxMessLangth of successor
                else if (fSuccessor != null && messLength < 0
                        && -messLength > fSuccessor.getMaxMessLength())
                    fSuccessor.setMaxMessLength(-messLength);
                // if negative messLength is smaller than returnValue
                // -> set returnValue = messLength
                if (messLength < 0 && returnValue > messLength)
                    returnValue = messLength;
            }
            return returnValue;
        }

        /**
         * Calculates the difference between this and another lifeline.
         * 
         * @param src
         *            the lifeline to which the distance should be calculated
         * @return the difference between the column of the two lifelines
         */
        public int calculateDistance(Lifeline src) {
            // src is actors lifeline -> return column of this lifeline
            if (src == null)
                return column();
            // src is this lifeline -> return 0
            if (src == this)
                return 0;
            // return differende between column of src and this
            return (column() - src.column());
        }

        /**
         * Calculates the x position of this lifeline, subject to the values in
         * the fProperties-Object of the SequenceDiagram-Object.
         * 
         * @param fm
         *            the FontMetrics object of the diagram
         */
        public void calculatePosition(FontMetrics fm) {
            // column of this lifeline in the diagram
            int column = column();
            // antecessor of this lifeline -> must not be drawn in the diagram
            Lifeline ll = fAntecessor;
            // calculate drawn antecessor lifeline
            while (ll != null && (!ll.drawLifeline() || ll.isHidden())) {
                ll = ll.getAntecessor();
            }
            // calculate longest messagefor this lifeline
            int returnValue = getLongestMessage(fm);
            // beginning of the diagram
            int xValue = fProperties.getLeftMargin() + 10;
            // if there is no lifeline chosen by the user
            if (fChoosedLl == null) {
                // calculate xValue and in case of fDraw=true fXValue
                // subject to the user settings for the distance
                // of two lifelines
                if (!fProperties.llLikeLongMess()) {
                    if (!fProperties.individualLl()) {
                        xValue = fProperties.getLeftMargin() + 10 + column
                                * fProperties.llStep();
                    } else {
                        xValue = fXValue;
                    }
                    if (fDraw) {
                        fXValue = xValue;
                    }
                } else {
                    if (fProperties.individualLl()) {
                        if (ll != null) {
                            xValue = ll.xValue() + fMaxMessLength; // *
                        } else {
                            xValue += fMaxMessLength;
                            if (fMaxMessLength == 0) {
                                xValue += fProperties.frWidth() / 2
                                        + fObjectBox.getWidth() / 2;
                            }
                        }

                    } else {

                        xValue += fProperties.maxActMess() * column;
                    }

                    if (fDraw) {
                        fXValue = xValue;
                    }
                    if (fProperties.individualLl()
                            && ll != null
                            && (ll.xValue() + ll.getObjectBox().getWidth() / 2 > xValue
                                    - fObjectBox.getWidth() / 2 - 5)) {
                        fXValue = ll.xValue() + ll.getObjectBox().getWidth()
                                / 2 + fObjectBox.getWidth() / 2 + 5;
                    }
                }

            }
            // set x-value of the object box
            fObjectBox.setX(fXValue);
            // calculate start- and end-x-value of the object box
            fObjectBox.calculateStart(fm);
            fObjectBox.calculateEnd(fm);

            // calculate last x-value of this lifeline
            int lastX = fXValue + fObjectBox.getWidth() / 2;
            if (returnValue < 0
                    && fXValue + fObjectBox.getWidth() / 2 - returnValue > fLastXValue)
                lastX = fXValue + fObjectBox.getWidth() / 2 - returnValue;
            if (fLastXValue < lastX && fDraw) {
                fLastXValue = lastX;
            }

            // calculate width of object box
            int labelWidth = fm.stringWidth(fObjectBox.fName);
            // if there was no wider box so far -> set maximum width in
            // fObProperties
            if (fObProperties.maxWidth(labelWidth))
                fObProperties.setMaxWidth(labelWidth);
            // calculate height of object box
            int labelHeight = fProperties.getFont().getSize();
            // if there was no higher box so far -> set maximum height in
            // fObProperties
            if (fObProperties.maxHeight(labelHeight))
                fObProperties.setMaxHeight(labelHeight);

        }

    }

    // **************************************************************************************************************

    /**
     * Represents a lifeline for Objects.
     * 
     * @author Antje Werner
     */
    private class ObjLifeline extends Lifeline {
        /**
         * The object of this lifeline.
         */
        private MObject fObj;

        /**
         * Costructs a new ObjLifeline-Object.
         * 
         * @param col
         *            the column of the lifeline
         * @param obj
         *            the object of the lifeline
         * @param antecessor
         *            the antecessor lifeline
         */
        public ObjLifeline(int col, MObject obj, Lifeline antecessor) {
            fColumn = col;
            fObj = obj;
            fActivations = new ArrayList<Activation>();
            fObjectBox = new ObjectBox(-1, -1, fObj.name() + ":"
                    + fObj.cls().name());
            // calculate x-value subject to the column of the lifeline
            fXValue = fProperties.getLeftMargin() + fColumn
                    * fProperties.llStep();
            fAntecessor = antecessor;
            // normally a lifeline should not be drawn
            fDraw = false;
            // normally a lifeline should not be hidden
            fHidden = false;
        }

        /**
         * Restores some values to be sure, that all values will be calculated
         * correctly, when update is called.
         */
        public synchronized void restoreValues() {
            // resets the object box
            fObjectBox = new ObjectBox(-1, -1, fObj.name() + ":"
                    + fObj.cls().name());
            // deletes all activations for this lifeline
            fActivations = new ArrayList<Activation>();
            fActivationNesting = 0;
            // the lifeline should not be drawn
            fDraw = false;
            fMaxMessLength = 0;
        }

    }

    // **********************************************************************************************************

    /**
     * Represents a lifeline for Associations.
     * 
     * @author Antje Werner
     */
    private class AssLifeline extends Lifeline {
        /**
         * The association of this lifeline.
         */
        private MAssociation fAss;

        /**
         * Describes if the association has been deleted. A Link between two
         * Objects can be deleted and later inserted again.
         */
        private boolean fIsDeleted;

        /**
         * The list of the objects involved in the association of this lifeline.
         */
        private MObject[] fObjects;

        /**
         * Costructs a new AssLifeline-Object.
         * 
         * @param col
         *            the column of the lifeline
         * @param ass
         *            the association of the lifeline
         * @param antecessor
         *            the antecessor lifeline
         * @param objects
         *            list of the objects which are involved in the association
         *            ass
         */
        AssLifeline(int col, MAssociation ass, Lifeline antecessor,
                MObject[] objects) {
            fColumn = col;
            fAss = ass;
            fActivations = new ArrayList<Activation>();
            fObjectBox = new ObjectBox(-1, -1, " :" + fAss.name());
            fXValue = fProperties.getLeftMargin() + fColumn
                    * fProperties.llStep();
            fAntecessor = antecessor;
            fDraw = false;
            // normally a Link has not been deleted
            fIsDeleted = false;
            fObjects = objects;
            // normally a lifeline should not be hidden
            fHidden = false;
        }

        /**
         * Indicates whether or not the association of this lifeline has been
         * deleted.
         * 
         * @return true if delete should be shown; false otherwise.
         */
        public boolean isDeleted() {
            return fIsDeleted;
        }

        /**
         * Sets the value of the fDeleted-Variable
         * 
         * @param deleted
         *            true, if tha association has been deleted; false otherwise
         */
        public void setDeleted(boolean deleted) {
            fIsDeleted = deleted;
        }

        /**
         * Restores some values to be sure, that all values will be calculated
         * correctly, when update is called.
         */
        public void restoreValues() {
            fObjectBox = new ObjectBox(-1, -1, " :" + fAss.name());
            fActivations = new ArrayList<Activation>();
            fActivationNesting = 0;
            fDraw = false;
            fIsDeleted = false;
            fMaxMessLength = 0;
        }

        /**
         * Indicates if the given objects are the same as the objects of the
         * association of ths lifeline.
         * 
         * @param objects
         *            the objects to compare
         * @return true, if objects and fObjects contain a same number of
         *         objects and if each object in objects equals exactly one
         *         object of fObjects; false otherwise
         */
        public boolean sameObjects(MObject[] objects) {
            boolean answer = false;
            // if the size of the objects is the same
            if (fObjects.length == objects.length) {
                // regard all objects
                for (int i = 0; i < fObjects.length; i++) {
                    // if the objects are the same -> answer is true
                    if (fObjects[i] == objects[i])
                        answer = true;
                    // if only one object differs from the corresponding in
                    // objects -> answer is false
                    else {
                        answer = false;
                        break;
                    }
                }
            }
            return answer;
        }
    }

    // ************************************************************************************************************

    /**
     * Represents an object box of a lifeline.
     * 
     * @author Antje Werner
     */

    private class ObjectBox {
        /**
         * The x-value of the corresponding lifeline -> the center of the object
         * box.
         */
        int fXValue;

        /**
         * The x value where the object box starts.
         */
        int fXStart;

        /**
         * The x-value where the objct box ends.
         */
        int fXEnd;

        /**
         * The y-value where the object box starts.
         */
        int fYValue;

        

        /**
         * The name of the object which belongs to the object box.
         */
        String fName;

        /**
         * Constructs a new object box.
         * 
         * @param xValue
         *            the center x-value of the object box
         * @param yValue
         *            the y-value on which the object box starts
         * @param lifeline
         *            the corresponding lifeline
         * @param name
         *            the name of the corresponding object
         */
        ObjectBox(int xValue, int yValue, String name) {
            fXValue = xValue;
            fYValue = yValue;
            fXStart = 0;
            fXEnd = 0;
            fName = name;
        }

        /**
         * Returns the start x-value of the box.
         * 
         * @return the start x-value
         */
        int getStart() {
            return fXStart;
        }

        /**
         * Returns the end x-value of the box.
         * 
         * @return the end x-value
         */
        int getEnd() {
            return fXEnd;
        }

        /**
         * Returns the start y-value of the box.
         * 
         * @return the start y-value
         */
        int getY() {
            return fYValue;
        }

        /**
         * Calculates the height of the box for the given font.
         * 
         */
        int getHeight() {
            // Font used for the diagram
            Font font = fProperties.getFont();
            // manuel height chosen by the user
            if (fObProperties.manHeight()) {
                return fObProperties.getHeight();
                // same height for all boxes
            } else if (fObProperties.sameHeight()) {
                // calculate height of the box label
                int labelHeight = font.getSize();
                // if labelheight is bigger than the maximum height ind
                // fObProperties
                // -> labelheight is new maximum height
                if (fObProperties.maxHeight(labelHeight))
                    fObProperties.setMaxHeight(labelHeight);
                // otherwise height of this box is the maximum height from
                // fObProperties
                else
                    labelHeight = fObProperties.getMaxHeight();
                // return the calculated height + 8 as distance between label
                // and border
                return (labelHeight + 8);
                // individual heights
            } else {
                // see above
                int labelHeight = font.getSize();
                if (fObProperties.maxHeight(labelHeight))
                    fObProperties.setMaxHeight(labelHeight);
                return (labelHeight + 8);
            }
        }

        /**
         * Calculates the width of the box for the given FontMetrics.
         * 
         */
        int getWidth() {
            // the FontMetrics used for the diagram
            FontMetrics fm = getFontMetrics(fProperties.getFont());
            // see getHeight
            if (fObProperties.manWidth()) {
                return fObProperties.getWidth();
            } else if (fObProperties.sameWidth()) {
                int labelWidth = fm.stringWidth(fName);
                if (fObProperties.maxWidth(labelWidth))
                    fObProperties.setMaxWidth(labelWidth);
                else
                    labelWidth = fObProperties.getMaxWidth();
                return (labelWidth + 10);
            } else {
                int labelWidth = fm.stringWidth(fName);
                if (fObProperties.maxWidth(labelWidth))
                    fObProperties.setMaxWidth(labelWidth);
                return (labelWidth + 10);
            }
        }

        /**
         * Sets the center x-value of the box.
         * 
         * @param x
         *            the new center x-value
         */
        void setX(int x) {
            fXValue = x;
        }

        /**
         * Sets the start y-value of the box.
         * 
         * @param y
         *            the new start y-value
         */
        void setY(int y) {
            fYValue = y;
        }

        /**
         * Caculates and sets the start x-value of the object box in dependency
         * on the FontMetrics of the diagram.
         * 
         * @param fm
         *            the FontMetrics of the sequence diagram
         */
        void calculateStart(FontMetrics fm) {
            fXStart = fXValue - getWidth() / 2;
        }

        /**
         * Caculates and sets the end x-value of the object box in dependency on
         * the FontMetrics of the diagram.
         * 
         * @param fm
         *            the FontMetrics of the sequence diagram
         */
        void calculateEnd(FontMetrics fm) {
            fXEnd = fXValue + getWidth() / 2;
        }

        /**
         * Draws the box in the diagram.
         * 
         * @param graphic
         *            the graphic where the object box should be drawn in.
         * @param fm
         *            the FontMetrics of the sequence diagram
         * @param y
         *            the y-Value where the box begins.
         */
        public void drawBox(Graphics2D graphic, FontMetrics fm, int y) {
            // calculate width of the object box
            int boxWidth = getWidth();
            // calculate width of the box label
            int labelWidth = fm.stringWidth(fName);
            // calculate height of the box label
            int labelHeight = (fm.getFont()).getSize();
            // the y-value where the label should be drawn
            int yValue = y + labelHeight + 2;
            // calculate height of box
            int boxHeight = getHeight();

            // draw label
            graphic.drawString(fName, fXValue - labelWidth / 2, yValue);
            // draw underline
            graphic.drawLine(fXValue - labelWidth / 2, yValue + 2, fXValue
                    + labelWidth / 2, yValue + 2);
            // draw box
            graphic.drawRect(fXValue - boxWidth / 2, y, boxWidth, boxHeight);
        }
    }

    // *****************************************************************************************

    /**
     * 
     * Represent an activation frame, which is used to show the activity from a
     * lifeline.
     * 
     * @author Mark Richters, Antje Werner
     */
    private class Frame {
        /**
         * The x-value of the lifeline on which this frame is positioned
         */
        int fXValue;

        /**
         * The y-value on which the frame begins.
         */
        int fYValue;

        /**
         * The height of the frame.
         */
        int fHeight;

        /**
         * Constructs a new Frame.
         * 
         * @param x
         *            the x-value of the frame
         * @param y
         *            the y-value of the frame
         * @param height
         *            the height of the frame
         * @param nesting
         *            the nesting of this frame
         */
        Frame(int x, int y, int height, int nesting) {
            fXValue = x;
            fYValue = y;
            fHeight = height;
            if (nesting > 0) {
                fXValue += (nesting - 1) * fProperties.frOffset();
            }
        }

        /**
         * Draws the frame in the diagram.
         * 
         * @param graphic
         *            the Graphic on which is has to be drawn
         */
        public void drawFrame(Graphics2D graphic) {
            // draw background of the frame
            graphic.setColor(Color.white);
            graphic.fillRect(fXValue - fProperties.frWidth() / 2, fYValue,
                    fProperties.frWidth(), fHeight);
            // draw border of the frame
            graphic.setColor(Color.black);
            graphic.drawRect(fXValue - fProperties.frWidth() / 2, fYValue,
                    fProperties.frWidth(), fHeight);
        }

    }

    // ************************************************************************************************************
    /**
     * An activation in the sequence diagram. For each command (except for
     * opexit) shown in the diagram there is an Activation-object.
     * 
     * @author Marc Richters, Antje Werner
     */
    protected class Activation {

        /**
         * The lifeline which is receiver of the activation message.
         */
        protected Lifeline fOwner;

        /**
         * The command of this activation.
         */
        protected MCmd fCmd;

        /**
         * The position on the time axis on which the activation message is
         * positioned (number of antecessors + 1)
         */
        protected int fStart;

        /**
         * The position on the time axis on which the activations answer is
         * positioned.
         */
        protected int fEnd;

        /**
         * The source-activation from which the command fCmd has given. Null if
         * the command was given by the actors Lifeline.
         */
        protected Activation fSrc;

        /**
         * The nesting of the fOwner lifeline at the moment of the command call.
         */
        protected int fNesting;

        /**
         * The y-value on which the message arrow should be drawn.
         */
        protected int fYValue;

        /**
         * The y-value on which the answer should be drawn.
         */
        protected int fYEnd;

        /**
         * The length of the message which is sent by this activation.
         */
        protected int fMessLength;

        /**
         * Constructs a new Activation object. This constructor should be used
         * for non-parallel activations.
         * 
         * @param start
         *            the position on the time axis on which the activation
         *            message is positioned
         * @param owner
         *            the lifeline which is receiver of the activation message
         * @param cmd
         *            the command which should be represented
         * @param yValue
         *            the y-value on which the activation message should be
         *            drawn
         */
        public Activation(int start, Lifeline owner, MCmd cmd, Activation src,
                int yValue) {
            fStart = start;
            fOwner = owner;
            fCmd = cmd;
            fSrc = src;
            fYEnd = 0;
        }

        /**
         * Returns the command of this activation.
         * 
         * @return the command of this activation.
         */
        public MCmd getCmd() {
            return fCmd;
        }

        /**
         * Returns the position on the time axis on which the activation message
         * is positioned (number of antecessors + 1).
         * 
         * @return the position on the time axis
         */
        public int getStart() {
            return fStart;
        }

        /**
         * Returns the position on the time axis on which the activations answer
         * is positioned.
         * 
         * @return the position of the answer on the time axis
         */
        public int getEnd() {
            return fEnd;
        }

        /**
         * Returns the nesting of the fOwner lifeline at the moment of the
         * command call.
         * 
         * @return the nesting of the fOwner lifeline
         */
        public int getNesting() {
            return fNesting;
        }

        /**
         * Returns the y-value on which the activation message arrow should be
         * drawn.
         * 
         * @return the y value of the activation.
         */
        public int getY() {
            return fYValue;
        }

        /**
         * Returns the y-value on which the activation ends.
         * 
         * @return the y value on which the activation ends
         */
        public int getYEnd() {
            return fYEnd;
        }

        /**
         * Returns the length of the activation message.
         * 
         * @return the length of the activation message
         */
        public int getMessLength() {
            return fMessLength;
        }

        /**
         * Returns the Lifeline of the source activation.
         * 
         * @return the lifeline of the source activation if the source is not
         *         null; otherwise null
         */
        public Lifeline getSrc() {
            // is fSrc is not the actors lifeline -> return lifeline
            if (fSrc != null)
                return fSrc.owner();
            // else return null
            return null;
        }

        /**
         * Returns the lifeline which is receiver of the activation message.
         * 
         * @return the lifeline which is receiver of the message
         */
        public Lifeline owner() {
            return fOwner;
        }

        /**
         * Sets the position on the time axis on which the activations answer is
         * positioned.
         * 
         * @param end
         *            the position for the answer
         */
        public void setEnd(int end) {
            fEnd = end;
        }

        /**
         * Sets the nesting for this activation.
         * 
         * @param n
         *            the nesting
         */
        public void setNesting(int n) {
            fNesting = n;
        }

        /**
         * Sets the y-value of this activation.
         * 
         * @param y
         *            the new y-value of this activation
         */
        public void setY(int y) {
            fYValue = y;
        }

        /**
         * Calculates the length of the longest message sent by this activation.
         * 
         * @return the length of the message
         */
        private int calculateMessLength() {
            // message which will be sent by the message of this activation
            String message = createMessage();
            // the FontMetrics object used for drawing the diagram
            FontMetrics fm = getFontMetrics(fProperties.getFont());
            // width of Activation-Message + 8 as distance to the lifelines
            int messLength = fm.stringWidth(message) + 8;
            // owner-lifeline of the activation
            
            // distance from goal-lifeline to the source-lifeline
            // (-> difference of the column-numbers)
            int srcDistance = fOwner.column();
            if (fSrc != null && fSrc.owner() != null) {
                srcDistance = fOwner.calculateDistance(fSrc.owner());
            }
            // if recursive activation
            if (srcDistance == 0) {
                messLength = fm.stringWidth(message) + 15 + 14;
                // otherwise, if soruce-lifeline on the left side of
                // this lifeline
            } else {
                // if command of activation = create
            	// TODO: This is messy
                if (message.equals("create") || message.startsWith("create link")) {
                    // the create-arrow ends at the beginning of the
                    // ObjectBox
                    messLength += fOwner.getObjectBox().getWidth() / 2;
                    // if command of activation = insert
                }
                // if there is a return arrow
                if (fEnd > 0) {
                    // if openter-command
                    if (fCmd instanceof MCmdOpEnter) {
                        // calculate length of the result message
                        MOperationCall fOpCall = ((MCmdOpEnter) fCmd)
                                .operationCall();
                        if (fProperties.showValues()) {
                            Value result = fOpCall.resultValue();
                            if (result != null) {
                                String resultLabel = result.toString();
                                if (fm.stringWidth(resultLabel) + 14 > messLength)
                                    messLength = fm.stringWidth(resultLabel) + 14;
                            }
                        }
                    }
                }
                // consider the nestings of the corresponding lifelines
                if (fNesting > 0) {
                    messLength += fProperties.frWidth() / 2;
                }
                if (fNesting > 1)
                    messLength += (fSrc.fNesting - 1) * fProperties.frOffset();

                if (fSrc != null) {
                    if (fSrc.owner() != null && fSrc.fNesting > 1)
                        messLength += (fSrc.fNesting - 1)
                                * fProperties.frOffset();
                    if (fSrc.owner() == null || fSrc.fNesting > 0)
                        messLength += fProperties.frWidth() / 2;
                } else {
                    messLength += fProperties.frWidth() / 2;
                }

                if (!(fCmd instanceof MCmdDestroyObjects)
                        && !(fCmd instanceof MCmdDeleteLink)) {
                    if (fNesting > 0) {
                        messLength += fProperties.frWidth() / 2;
                    }
                }
            }
            // no recursive message
            if (srcDistance != 0) {
                // calculate distance between two lifelines subject to the
                // message length
                // and the distance between source- and goal-lifeline
                fMessLength = messLength / srcDistance;
                return messLength / srcDistance;
                // recursive message
            } else {
                // message is on the right side of the lifeline -> affect
                // position
                // of successor-lifelines -> retun negative value
                fMessLength = -messLength;
                return -messLength;
            }

        }

        /**
         * Calculates the y-value for the answer message of this Activation
         * depending on the fProperties which are set in the fProperties object
         * of the SequenceDiagram.
         * 
         * @return the y-position for the answer message
         */
        public int calculateEnd() {
            // y-value of the command-message
            int value = fYValue;
            // if there is a return arrow
            if (fEnd > 0) {
                // calculate position subject to the user settings for the
                // distance
                // between two messages
                if (fProperties.getActManDist() != -1) {
                    value += fEnd * fProperties.getActManDist() - fStart
                            * fProperties.getActManDist();
                } else {
                    value += fEnd * fProperties.actStep() - fStart
                            * fProperties.actStep();
                }
            }
            // set fYEnd-value and return result
            fYEnd = value;
            return value;
        }

        /**
         * Draws the message of the command of this activation in the diagram.
         * 
         * @param g
         *            the graphics on which tha message should be drawn
         * @param fm
         *            the FontMetrics-Object of the diagram
         */
        void drawMessageSend(Graphics2D g, FontMetrics fm) {
            g.setColor(Color.black);
            // activation is recursive if the owner of fSrc is the same as the
            // owner of
            // this activation
            boolean isRecursive = fSrc != null && fSrc.owner() == fOwner;

            // x-value of the source lifeline of the activation ->
            // left Margin + Center x-Coordinate of Actor-Lifeline (->10)
            int x_Src = fProperties.getLeftMargin() + 10;
            // x-value of the goal lifeline of the activation
            int x_Goal = owner().xValue();

            int xd = 0;

            // if fSrc is not the actors lifeline -> calculate x-value of the
            // source lifeline
            if (fSrc != null)
                x_Src = fSrc.owner().xValue();

            // consider netings of the source- and goal-lifeline
            if (x_Src < x_Goal) {
                if (fSrc != null) {
                    if (fSrc.fNesting > 1)
                        x_Src += (fSrc.fNesting - 1) * fProperties.frOffset();
                    if (fSrc.fNesting > 0)
                        x_Src += fProperties.frWidth() / 2;
                } else {
                    x_Src += fProperties.frWidth() / 2;
                }

                if (!(fCmd instanceof MCmdDestroyObjects)
                        && !(fCmd instanceof MCmdDeleteLink)) {

                    if (fNesting > 0) {
                        x_Goal -= fProperties.frWidth() / 2;
                    }
                    if (fNesting > 1) {
                        x_Goal += (fNesting - 1) * fProperties.frOffset();
                    }
                }
            } else if (x_Src > x_Goal) {
                if (!(fCmd instanceof MCmdDestroyObjects)
                        && !(fCmd instanceof MCmdDeleteLink)) {
                    if (fNesting > 0)
                        x_Goal += fProperties.frWidth() / 2;
                    if (fNesting > 1)
                        x_Goal += (fNesting - 1) * fProperties.frOffset();
                }
                if (fSrc.getEnd() != 0) {
                    if (fSrc.getNesting() > 0)
                        x_Src -= fProperties.frWidth() / 2;
                    if (fSrc.getNesting() > 1)
                        x_Src += (fSrc.getNesting() - 1)
                                * fProperties.frOffset();
                }
            } else {
                if (fSrc != null) {
                    if (fSrc.getNesting() > 0)
                        x_Src += fProperties.frWidth() / 2;
                    if (fSrc.getNesting() > 1)
                        x_Src += (fSrc.getNesting() - 1)
                                * fProperties.frOffset();
                } else {
                    x_Src += fProperties.frWidth() / 2;
                }
                if (fNesting > 0)
                    x_Goal += fProperties.frWidth() / 2;
                if (fNesting > 1)
                    x_Goal += (fNesting - 1) * fProperties.frOffset();

            }
            // if create-command
            if (fCmd instanceof CmdCreatesObjects) {
                // object box of the owner-lifeline
                ObjectBox objectBox = fOwner.getObjectBox();
                // x-value of the object box
                int x_ObjectBox = objectBox.getStart();
                x_Goal = x_ObjectBox;
                // draw message
                drawCreateMessage(g, fm, x_Src, x_ObjectBox, x_Src);
                // if insert-command
            } else if (fCmd instanceof MCmdInsertLink) {
                // draw message
                drawInsertMessages(g, fm, x_Src, x_Goal, x_Goal);
                // other command
            } else {
                if (fCmd instanceof MCmdOpEnter) {
                    // if operation result was not coorect -> change color to
                    // red
                    if (!((MCmdOpEnter) fCmd).successful())
                        g.setColor(Color.red);
                }
                // recursive message
                if (isRecursive) {
                    g.drawArc(x_Goal - 40, fYValue - fProperties.actStep() / 3,
                            80, fProperties.actStep() / 3, 90, -180);
                    g.drawLine(x_Goal, fYValue - fProperties.actStep() / 3,
                            x_Goal - fProperties.frOffset(), fYValue
                                    - fProperties.actStep() / 3);
                    xd = +10;
                    // otherwise
                } else {
                    // draw horizontal line
                    g.drawLine(x_Src, fYValue, x_Goal, fYValue);
                    xd = (x_Goal <= x_Src) ? +10 : -10;
                }
                // draw arrow
                int[] xp = { x_Goal, x_Goal + xd, x_Goal + xd };
                int[] yp = { fYValue, fYValue - 4, fYValue + 4 };
                g.fillPolygon(xp, yp, xp.length);

            }
            // message of the activation
            String msgLabel = createMessage();
            // draw message label into the diagram
            if (isRecursive) {
                g.drawString(msgLabel, x_Src + 15, fYValue
                        - fProperties.actStep() / 3 - 2);
            } else {
                int labelWidth = fm.stringWidth(msgLabel);
                int x_value = 0;
                // position of goal lifeline
                int x_Goal2 = x_Goal;
                // if insert-command
                if (fCmd instanceof MCmdInsertLink) {
                    // calculate position of goal lifeline
                    if (x_Goal > x_Src)
                        x_Goal2 = fOwner.getObjectBox().getStart();
                    else if (x_Goal < x_Src)
                        x_Goal2 = fOwner.getObjectBox().getStart()
                                + fOwner.getObjectBox().getWidth();
                }
                // calculate x-position fot the message label
                if (x_Src < x_Goal) {
                    x_value = x_Src + (x_Goal2 - x_Src - labelWidth) / 2;
                } else {
                    x_value = x_Goal2 + (x_Src - x_Goal2 - labelWidth) / 2;
                }

                g.drawString(msgLabel, x_value, fYValue - 2);

            }
            g.setColor(Color.black);
            // openter- or opexit-command
            if ((fCmd instanceof MCmdOpEnter)
                    && ((MCmdOpEnter) fCmd).successful()) {
                // draw answer message in black color
                drawAnswer(g, fm, x_Src, x_Goal, fYEnd);
            }
        }

        /**
         * Draws an insert message in the sequence diagram.
         * 
         * @param g
         *            the graphics on which tha message should be drawn
         * @param fm
         *            the FontMetrics-Object of the diagram
         * @param x1
         *            the start-x-value for the message
         * @param x2
         *            the end-x-value of the message
         * @param x
         *            the x-value of the goal lifeline (without consideration of
         *            frames)
         */
        public void drawInsertMessages(Graphics2D g, FontMetrics fm, int x1,
                int x2, int x) {
            // object box of the owner lifeline
            ObjectBox objectBox = fOwner.getObjectBox();
            // calculate start and end position of the box
            objectBox.calculateStart(fm);
            objectBox.calculateEnd(fm);

            // x-value where the arrow ends -> normally on the left end of the
            // box
            int x_ObjectBox = objectBox.getStart();
            // if owner-lifeline is on the left side of the source lifeline
            // -> arrow ends at the right side of the box
            if (x1 > x2)
                x_ObjectBox = objectBox.getEnd();

            int xd = 0;
            // draw line of arrow
            g.drawLine(x1, fYValue, x_ObjectBox, fYValue);

            xd = (x1 <= x2) ? -10 : +10;
            // draw arrowhead
            int[] xp = { x_ObjectBox, x_ObjectBox + xd, x_ObjectBox + xd };
            int[] yp = { fYValue, fYValue - 4, fYValue + 4 };
            g.fillPolygon(xp, yp, xp.length);

            // List of involved objects
            MObject[] objects = ((MCmdInsertLink) fCmd).getObjects();
            // rolenames of the roles of the link
            String[] roleNames = ((MCmdInsertLink) fCmd).getRoleNames();
            // regard each object
            for (int i = 0; i < objects.length; i++) {
                MObject object = objects[i];
                // lifeline of the object
                ObjLifeline oll = (ObjLifeline) fLifelines.get(object);
                // if the lifeline is not marked to be hidden
                if (!oll.isHidden()) {
                    // calculate y-position of the 'inserted as'-message
                    // depending
                    // on the user settings
                    int y3 = 0;
                    if (fProperties.getActManDist() == -1) {
                        y3 = fYValue + (i + 1) * fProperties.actStep();
                    } else {
                        y3 = fYValue + (i + 1) * fProperties.getActManDist();
                    }

                    // x-value of the object lifeline
                    int x3 = oll.xValue();
                    // consider nestings of the source and goal lifelines
                    if (x3 < x2) {
                        if (x2 > fOwner.xValue()) {
                            x2 -= fProperties.frWidth() / 2;
                            if (getNesting() > 0) {
                                x2 -= fProperties.frWidth() / 2;
                                x -= fProperties.frWidth() / 2;
                            }
                            if (getNesting() > 1) {
                                x2 += (getNesting() - 1)
                                        * fProperties.frOffset();
                                x += (getNesting() - 1)
                                        * fProperties.frOffset();
                            }
                        }
                        if (((InsertActivation) this).fNestings[i] > 0) {
                            x3 += fProperties.frWidth() / 2;// 5;
                        }
                        if (((InsertActivation) this).fNestings[i] > 1) {
                            x3 += (oll.fActivationNesting - 1)
                                    * fProperties.frOffset();
                        }
                    } else {
                        if (x2 < fOwner.xValue()) {
                            x2 += fProperties.frWidth() / 2;// 5;
                            if (getNesting() > 0) {
                                x2 += fProperties.frWidth() / 2;// 5;
                                x += fProperties.frWidth() / 2;
                            }
                            if (getNesting() > 1) {
                                x2 += (getNesting() - 1)
                                        * fProperties.frOffset();
                                x += (getNesting() - 1)
                                        * fProperties.frOffset();
                            }
                        }
                        if (((InsertActivation) this).fNestings[i] > 0) {
                            x3 -= fProperties.frWidth() / 2;// 5;
                        }
                        if (((InsertActivation) this).fNestings[i] > 1) {
                            x3 += (oll.fActivationNesting - 1)
                                    * fProperties.frOffset();
                        }

                    }
                    // draw arrow line
                    g.drawLine(x2, y3, x3, y3);
                    // draw arrowhead
                    xd = (x3 <= x2) ? -10 : +10;
                    g.drawLine(x3, y3, x3 - xd, y3 - 4);
                    g.drawLine(x3, y3, x3 - xd, y3 + 4);

                    // 'inserted as'-message
                    String message = "inserted as " + roleNames[i];
                    int labelWidth = fm.stringWidth(message);
                    // calculate position to draw the message
                    int x_value = 0;
                    if (x < x3) {
                        x_value = x + (x3 - x - labelWidth) / 2; // -
                        // fIsCoeval
                    } else {
                        x_value = x3 + (x - x3 - labelWidth) / 2; // -
                        // fIsCoeval
                    }
                    // draw message
                    g.drawString(message, x_value, y3 - 2);
                }
            }

        }

        /**
         * Draws an create message in the sequence diagram.
         * 
         * @param g
         *            the graphics on which tha message should be drawn
         * @param fm
         *            the FontMetrics-Object of the diagram
         * @param x1
         *            the start-x-value for the message
         * @param x2
         *            the end-x-value of the message
         * @param x
         *            the x-value of the goal lifeline (without consideration of
         *            frames)
         */
        public void drawCreateMessage(Graphics g, FontMetrics fm, int x1,
                int x2, int x) {
            // object box of goal lifeline
            ObjectBox objectBox = fOwner.getObjectBox();
            // calculate start and end position of the box
            objectBox.calculateStart(fm);
            objectBox.calculateEnd(fm);
            // x-value where the arrow ends -> normally on the left end of the
            // box
            int x_ObjectBox = objectBox.getStart();
            // if owner-lifeline is on the left side of the source lifeline
            // -> arrow ends at the right side of the box
            if (x1 > x2)
                x_ObjectBox = objectBox.getEnd();

            int xd = 0;
            // draw arrow line
            g.drawLine(x1, fYValue, x_ObjectBox, fYValue);

            xd = (x1 <= x2) ? -10 : +10;
            // draw arrowhead
            int[] xp = { x_ObjectBox, x_ObjectBox + xd, x_ObjectBox + xd };
            int[] yp = { fYValue, fYValue - 4, fYValue + 4 };
            g.fillPolygon(xp, yp, xp.length);

        }

        /**
         * Draws the answer message for the command in the sequence diagram.
         * 
         * @param g
         *            the graphics on which tha message should be drawn
         * @param fm
         *            the FontMetrics-Object of the diagram
         * @param x_Src
         *            the x-value for the source lifeline
         * @param x_Goal
         *            the x-value of the goal lifeline
         * @param y
         *            the y-value on which the answer should be drawn
         */
        public void drawAnswer(Graphics2D g, FontMetrics fm, int x_Src,
                int x_Goal, int y) {
            // message is recursive if the owner of fSrc is the same as the
            // owner of
            // this activation
            boolean isRecursive = fSrc != null && fSrc.owner() == fOwner;

            // result label
            String resultLabel = null;
            // result value
            Value result = null;

            // if openter-command
            if (fCmd instanceof MCmdOpEnter) {
                // OperationCall-object of the openter-command
                MOperationCall fOpCall = ((MCmdOpEnter) fCmd).operationCall();
                // one ore more postconditions of the activation is false
                // -> arrow in red color
                if (!fOpCall.postconditionsOkOnExit())
                    g.setColor(Color.red);
                // user has activated 'Show values'
                if (fProperties.showValues()) {
                    // result value of the operation call
                    result = fOpCall.resultValue();
                    // if there was a result label -> calculate textual
                    // description
                    if (result != null)
                        resultLabel = result.toString();
                }
                // all other commands -> no result label
            } else {
                resultLabel = "";
            }

            Stroke oldStroke = g.getStroke();
            g.setStroke(DASHEDSTROKE);

            int xd = (x_Src <= x_Goal) ? +10 : -10;
            // recurive answer
            if (isRecursive) {
                // draw arc
                g.drawArc(x_Goal - 40, y, 80, fProperties.actStep() / 3, -90,
                        180);
                g.drawLine((int) (x_Goal + 5f), y + fProperties.actStep() / 3,
                        x_Goal - fProperties.frOffset(), y
                                + fProperties.actStep() / 3);

                g.setStroke(oldStroke);

                // draw arrowhead
                int y0 = y + fProperties.actStep() / 3;
                g.drawLine(x_Src, y0, x_Src + xd, y0 - 3);
                g.drawLine(x_Src, y0, x_Src + xd, y0 + 3);

                // if there was a result -> draw result label
                if (resultLabel != null)
                    g.drawString(resultLabel, x_Goal + 15, y - 2);
                // answer is not recursive
            } else {
                // draw arrow line
                g.drawLine(x_Src, y, x_Goal, y);
                g.setStroke(oldStroke);

                // draw arrowhead
                g.drawLine(x_Src, y, x_Src + xd, y - 3);
                g.drawLine(x_Src, y, x_Src + xd, y + 3);

                // if there was a result label
                // -> draw it on the right position
                if (resultLabel != null) {
                    int labelWidth = fm.stringWidth(resultLabel);
                    int x_value = 0;
                    if (x_Src < x_Goal) {
                        x_value = x_Src + (x_Goal - x_Src - labelWidth) / 2;
                    } else {
                        x_value = x_Goal + (x_Src - x_Goal - labelWidth) / 2;
                    }

                    g.drawString(resultLabel, x_value, y - 2);
                }

            }

            g.setStroke(oldStroke);
            g.setColor(Color.black);
        }

        /**
         * Creates the message which should be sent to the goal lifeline.
         * 
         * @return the message
         */
        public String createMessage() {
            String msgLabel = "";
            if (fCmd instanceof MCmdOpEnter) {
                MOperationCall fOpCall = ((MCmdOpEnter) fCmd).operationCall();
                msgLabel = fOpCall.operation().name();
                if (fProperties.showValues())
                    msgLabel += "("
                            + StringUtil.fmtSeq(fOpCall.argValues(), ",") + ")";
            } else if (fCmd instanceof MCmdCreateObjects) {
                msgLabel = "create";
            } else if (fCmd instanceof MCmdCreateInsertObjects) {
                msgLabel = "create link";
                if (fProperties.showValues()) {
                    List<MObject> objects = ((MCmdCreateInsertObjects) fCmd).getObjects();
                    msgLabel = msgLabel + "(@";
                    Iterator<MObject> iter = objects.iterator();
                    
                    while(iter.hasNext()) {
                        MObject object = iter.next();
                        msgLabel = msgLabel + object.toString();
                        if (iter.hasNext())
                            msgLabel = msgLabel + ",@";
                        else
                            msgLabel = msgLabel + ")";
                    }
                }
            } else if (fCmd instanceof MCmdDestroyObjects) {
                msgLabel = "destroy";
            } else if (fCmd instanceof MCmdSetAttribute) {
                String attribute = ((MCmdSetAttribute) fCmd).getAttribute();
                String value = ((MCmdSetAttribute) fCmd).getValue();
                msgLabel = "set " + attribute;
                if (fProperties.showValues())
                    msgLabel = msgLabel + " := " + value;
            } else if (fCmd instanceof MCmdInsertLink) {
                msgLabel = "insert";
                if (fProperties.showValues()) {
                    MObject[] objects = ((MCmdInsertLink) fCmd).getObjects();
                    msgLabel = msgLabel + "(@";
                    for (int i = 0; i < objects.length; i++) {
                        MObject object = objects[i];
                        msgLabel = msgLabel + object.toString();
                        if (i < (objects.length - 1))
                            msgLabel = msgLabel + ",@";
                        else
                            msgLabel = msgLabel + ")";
                    }
                }
            } else if (fCmd instanceof MCmdDeleteLink) {
                msgLabel = "delete";
                if (fProperties.showValues()) {
                    MObject[] objects = ((MCmdDeleteLink) fCmd).getObjects();
                    msgLabel = msgLabel + "(@";
                    for (int i = 0; i < objects.length; i++) {
                        MObject object = objects[i];
                        msgLabel = msgLabel + object.toString();
                        if (i < (objects.length - 1))
                            msgLabel = msgLabel + ",@";
                        else
                            msgLabel = msgLabel + ")";
                    }
                }
            }
            return msgLabel;
        }
    }

    /**
     * An special kind of Activation for an insert command. This is necessery
     * because an insert command contains several objects which have to be
     * notified.
     * 
     * @author Antje Werner
     */
    private class InsertActivation extends Activation {
        /**
         * The nestings of the involved objects at the time of giving the
         * command.
         */
        public int[] fNestings;

        /**
         * Constructs a new InsertActivation by Calling the Construcor of the
         * super-class Activation and initialising nesting with the correct
         * values.
         * 
         * @param start
         *            the position on the time axis on which the activation
         *            message is positioned
         * @param owner
         *            the lifeline which is receiver of the activation message
         * @param cmd
         *            the command which should be represented
         * @param src
         *            the source-activation from which the command fCmd has
         *            given
         * @param yValue
         *            the y-value on which the activation message should be
         *            drawn
         */
        public InsertActivation(int start, Lifeline owner, MCmd cmd,
                Activation src, int yValue) {
            super(start, owner, cmd, src, yValue);
            MObject[] objects = ((MCmdInsertLink) fCmd).getObjects();
            fNestings = new int[objects.length];
            // calculate nestings of the object lifelines of the inserted
            // objects
            for (int i = 0; i < objects.length; i++) {
                MObject object = objects[i];
                ObjLifeline oll = (ObjLifeline) fLifelines.get(object);
                fNestings[i] = oll.fActivationNesting;
            }
        }

        /**
         * Calculates the y-value for the answer message of this Activation
         * depending on the properties which are set in the fProperties object
         * of the SequenceDiagram. Contrary to the same method in Activation the
         * messages to the object-lifelines musst also be regarded.
         * 
         * @return the y-position for the answer message
         */
        public int calculateEnd() {
            // objects which are involved in the insert-command
            MObject[] objects = ((MCmdInsertLink) fCmd).getObjects();
            // y-value of the insert-message
            int value = fYValue;
            // consider the 'inserted as'-messages an the user settings
            // for the distance between two messages
            if (fProperties.getActManDist() != -1) {
                value += (objects.length + 1) * fProperties.getActManDist();
            } else {
                value += (objects.length + 1) * fProperties.actStep();
            }
            // set y-value for the end
            fYEnd = value;
            return value;
        }

        /**
         * Calculates the length of the longest message sent by this activation.
         * 
         * @return the length of the longest message
         */
        private int calculateMessLength() {
            // see above
            String message = createMessage();
            FontMetrics fm = getFontMetrics(fProperties.getFont());
            int messLength = fm.stringWidth(message) + 10;
            Lifeline ll = null;
            int srcDistance = fOwner.column();
            if (fSrc != null && fSrc.owner() != null) {
                srcDistance = fOwner.calculateDistance(fSrc.owner());
                ll = fSrc.owner();
            }
            if (srcDistance == 0) {
                messLength = fm.stringWidth(message) + 15 + 14;
            } else {
                messLength += fOwner.getObjectBox().getWidth() / 2;
                if (fNesting > 0) {
                    messLength += fProperties.frWidth() / 2;
                }
                if (fNesting > 1)
                    messLength += (fSrc.fNesting - 1) * fProperties.frOffset();

                if (fSrc != null) {
                    if (fSrc.owner() != null && fSrc.fNesting > 1)
                        messLength += (fSrc.fNesting - 1)
                                * fProperties.frOffset();
                    if (fSrc.owner() == null || fSrc.fNesting > 0)
                        messLength += fProperties.frWidth() / 2; // +5
                } else {
                    messLength += fProperties.frWidth() / 2; // 5;
                }

                // List of all objects that take part in the new link
                MObject[] objects = ((MCmdInsertLink) fCmd).getObjects();
                // List of all role-names
                String[] roleNames = ((MCmdInsertLink) fCmd).getRoleNames();
                // view each role-name
                for (int i = 0; i < roleNames.length; i++) {
                    // the message from this lifline to the
                    // object-lifelines
                    String insertedMess = "inserted as " + roleNames[i];
                    // the regarded object
                    MObject object = objects[i];
                    // find out the according lifeline
                    ObjLifeline oll = (ObjLifeline) fLifelines.get(object);
                    if (!oll.isHidden()) {
                        // distance from this lifeline to the goal-lifeline
                        int dist = fOwner.calculateDistance(oll);
                        // length of message
                        int length = fm.stringWidth(insertedMess) + 10;
                        // consider nestings of activation
                        if (fNesting > 1) {
                            length += (fSrc.fNesting - 1)
                                    * fProperties.frOffset();
                        }
                        if (fNesting > 0) {
                            length += fProperties.frWidth() / 2;
                        }
                        // consider nestings of the goal-lifeline
                        if (fNestings[i] > 0) {
                            length += fProperties.frWidth() / 2;
                        }
                        if (fNestings[i] > 1) {
                            length += (oll.fActivationNesting - 1)
                                    * fProperties.frOffset();
                        }
                        // the object lifeline is on the right side of the link
                        // lifeline
                        if (dist > 0) {
                            // check out if the 'inserted as'-messsage causes a
                            // bigger distance
                            // between two lifelines than the 'insert'-message
                            if (srcDistance > 0) {
                                if (length / dist > messLength / srcDistance) {
                                    messLength = length;
                                    srcDistance = dist;
                                    ll = oll;
                                }
                            } else {
                                if (length / dist > messLength / -srcDistance) {
                                    messLength = length;
                                    srcDistance = dist;
                                    ll = oll;
                                }
                            }
                            // the object lifeline is on the left side of the
                            // link
                            // lifeline
                            // -> the inserted message affects the position of
                            // the
                            // objects lifeline
                        } else {
                            if (length / (-dist) > oll.getMaxMessLength())
                                oll.setMaxMessLength(length / (-dist));
                        }
                    }
                }
            }

            // see above
            if (srcDistance > 0) {
                fMessLength = messLength / srcDistance;
                return messLength / srcDistance;
            } else if (srcDistance < 0) {
                if (messLength / (-srcDistance) > ll.getMaxMessLength())
                    ll.setMaxMessLength(messLength / (-srcDistance));
                fMessLength = messLength / srcDistance;
                return messLength / srcDistance;
            } else {
                fMessLength = -messLength;
                return -messLength;
            }
        }
    }

    /**
     * Calculates the MObject-object of the given MCmd-Object cmd.
     * 
     * @param cmd
     *            the comand for which the object should be calculated
     * @return MObjectImpl the object to the given command cmd.
     */
    public MObject getObj(MCmd cmd) throws CommandFailedException {
        if (cmd instanceof MCmdOpEnter) {
            MCmdOpEnter openter = (MCmdOpEnter) cmd;
            MOperationCall opcall = openter.operationCall();
            return opcall.targetObject();
        } else if (cmd instanceof CmdCreatesObjects) {
            List<MObject> fObjectList = ((CmdCreatesObjects) cmd).getObjects();
            return fObjectList.get(0);
        } else if (cmd instanceof MCmdSetAttribute) {
            return ((MCmdSetAttribute) cmd).getObject();
        } else if (cmd instanceof MCmdDestroyObjects) {
            List<MObject> fObjectList = ((MCmdDestroyObjects) cmd).getObjects();
            return fObjectList.get(0);
        }
        return null;
    }

    /**
     * Creates all possible lifelines for the current system state and adds them
     * to the fLifelines-Map.
     */
    public synchronized void createLifelines() {
        // executed commands
        List<MCmd> cmds = fSystem.useCommands();
        // first lifeline has column 1
        int column = 1;
        // antecessor lifeline
        Lifeline antecessor = null;
        // last regarded object
        MObject lastObject = null;
        // counter for counting links
        // int counter = 1;
        // last link key
        String lastAssKey = null;
        // List for saving deleted links
        ArrayList<AssLifeline> deletedLls = new ArrayList<AssLifeline>();
        
        // view all commands
        for (MCmd cmd : cmds) {
            // i create command
            if (cmd instanceof CmdCreatesObjects) {
                // created objects
                List<MObject> fObjectList = ((CmdCreatesObjects) cmd).getObjects();
                // view each object
                for (int i = 0; i < fObjectList.size(); i++) {
                    MObject obj = fObjectList.get(i);
                    ObjLifeline ll = (ObjLifeline) fLifelines.get(obj);
                    // need new lifeline?
                    if (ll == null) {
                        // create new lifeline
                        ll = new ObjLifeline(column, obj, antecessor);
                        // set new lifeline as successor of last created
                        // lifeline
                        if (!(antecessor == null)) {
                            if (antecessor instanceof ObjLifeline) {
                                ((ObjLifeline) fLifelines.get(lastObject))
                                        .setSuccessor(ll);
                            } else {
                                ((AssLifeline) fLifelines.get(lastAssKey))
                                        .setSuccessor(ll);
                            }

                        }
                        // put new lifeline in fLifelines
                        fLifelines.put(obj, ll);
                        // lifeline already exists
                    } else {
                        // delete all saved activations for this lifeline
                        ll.fActivations = new ArrayList<Activation>();
                    }
                    // set ll as antecessor
                    antecessor = ll;
                    // set created object as lastObject
                    lastObject = obj;
                    // next new lifeline is positioned next to tis lifeline
                    column++;
                }
                // insert-command
            } else if (cmd instanceof MCmdInsertLink) {
                // association which is affected
                MAssociation ass = ((MCmdInsertLink) cmd).getAssociation();
                String key = "";
                // affected objects
                MObject[] objects = ((MCmdInsertLink) cmd).getObjects();
                // generate key for the link lifeline
                for (int i = 0; i < objects.length; i++) {
                    MObject obj = objects[i];
                    key = key + obj.name();
                }
                key += ass.name();
                // get Link-Lifeline
                AssLifeline ll = (AssLifeline) fLifelines.get(key);
                // AssLifeline ll = (AssLifeline) fLifelines.get(key
                // + String.valueOf(counter));
                // see above
                if (ll == null) {
                    ll = new AssLifeline(column, ass, antecessor, objects);
                    if (!(antecessor == null)) {
                        if (antecessor instanceof ObjLifeline) {
                            ((ObjLifeline) fLifelines.get(lastObject))
                                    .setSuccessor(ll);
                        } else {
                            ((AssLifeline) fLifelines.get(lastAssKey))
                                    .setSuccessor(ll);
                        }
                    }
                    fLifelines.put(key, ll);
                    // fLifelines.put(key + String.valueOf(counter), ll);
                } else {
                    ll.fActivations = new ArrayList<Activation>();
                }
                column++;
                antecessor = ll;
                // set lastAssKey
                lastAssKey = key;
                // lastAssKey = key + String.valueOf(counter);
                // increase counter
                // counter++;
                // delete-command?
            } else if (cmd instanceof MCmdDeleteLink) {
                // see above
                MAssociation ass = ((MCmdDeleteLink) cmd).getAssociation();
                String key = "";
                MObject[] objects = ((MCmdDeleteLink) cmd).getObjects();
                for (int i = 0; i < objects.length; i++) {
                    MObject obj = objects[i];
                    key = key + obj.name();
                }
                key += ass.name();
                // counter for counting equivalent links
                // int cnt = 1;

                AssLifeline ll = (AssLifeline) fLifelines.get(key);
                // AssLifeline ll = (AssLifeline) fLifelines.get(key
                // + String.valueOf(cnt));
                // how many links betwwen the two objects has been inerted and
                // deleted so far?
                while (true) {
                    if (ll != null && !(ll.isDeleted())
                            && ll.sameObjects(objects))
                        break;
                    // cnt++;
                    ll = (AssLifeline) fLifelines.get(key);
                    // ll = (AssLifeline) fLifelines
                    // .get(key + String.valueOf(cnt));
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
     * @param fm
     *            the used FontMetrics object, needed for calculating the width
     *            of the individual activation messages.
     */
    public void calculateLlPositions(FontMetrics fm) {
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
            while (ll2 != ll) {
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
     * 
     * @throws CommandFailedException
     */
    public void update() throws CommandFailedException {
        // refresh popup menu
        createPopupMenu();
        // restore all needed values to the default values
        restoreAllValues();
        // List of all executed commands
        List<MCmd> toDraw = fSystem.useCommands();

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
        int lastYValue = 0;
        // int counter = 1;
        // view each command
        for (int i = 0; i < toDraw.size(); i++) {
            MCmd cmd = toDraw.get(i);

            // create activations by calling other operations depending on
            // the
            // command
            if (cmd instanceof CmdCreatesObjects) {
                synchronized (this) {
                    lastYValue = drawCreate(cmd, activationStack, lastYValue,
                            lastAct);
                }
            } else if (cmd instanceof MCmdDestroyObjects) {
                synchronized (this) {
                    lastYValue = drawDestroy(cmd, activationStack, lastYValue,
                            lastAct);
                }
            } else if (cmd instanceof MCmdOpEnter) {
                synchronized (this) {
                    lastYValue = drawOpEnter(cmd, activationStack, lastYValue,
                            lastAct);
                }
            } else if (cmd instanceof MCmdSetAttribute) {
                synchronized (this) {
                    lastYValue = drawSet(cmd, activationStack, lastYValue,
                            lastAct);
                }

            } else if (cmd instanceof MCmdOpExit) {
                synchronized (this) {
                    if (!activationStack.empty()) {
                        // finish current activation
                        Activation a = activationStack.pop();
                        MCmdOpExit opexit = (MCmdOpExit) cmd;
                        MOperationCall opcall = opexit.operationCall();
                        MObject obj = opcall.targetObject();
                        // MObject obj = getObj(a.getCmd());
                        ObjLifeline ll = (ObjLifeline) fLifelines.get(obj);
                        // if the lifeline and the source-lifeline is not marked
                        // to be hidden
                        if (!ll.isHidden()
                                && (a.getSrc() == null || !a.getSrc()
                                        .isHidden())) {
                            a.setEnd(fNumSteps++);
                            // calculate position of answer-message
                            lastYValue = a.calculateEnd();
                            // only visible view should be drawn -> add only the
                            // visible
                            // activations
                            // to the fActivations-List
                            if (fOnlyView) {
                                if ((a.getY() > fView.getY() && a.getY() <= fView
                                        .getY()
                                        + fView.getHeight())
                                        || (a.getYEnd() > fView.getY() && a
                                                .getYEnd() <= fView.getY()
                                                + fView.getHeight())) {
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
                            if (messLength > 0
                                    && messLength > fProperties.maxActMess()) {
                                fProperties.setMaxActMess(messLength);
                            } else if (messLength < 0
                                    && -messLength > fProperties.maxActMess()) {
                                fProperties.setMaxActMess(-messLength);
                            }
                        }
                    }
                }
            } else if (cmd instanceof MCmdInsertLink) {
                synchronized (this) {
                    lastYValue = drawInsert(cmd, activationStack, lastYValue,
                            lastAct);
                }
            } else if (cmd instanceof MCmdDeleteLink) {
                synchronized (this) {
                    lastYValue = drawDelete(cmd, activationStack, lastYValue,
                            lastAct);
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
        // calculate last y-position on which something ist drawn in the diagram
        int lastYPos = fProperties.yStart() + fNumSteps * fProperties.actStep();
        if (fProperties.getActManDist() != -1) {
            lastYPos = fProperties.yStart() + fNumSteps
                    * fProperties.getActManDist();
        }
        // set preferred size of the sequence diagram window
        // -> needed for the right display of the scrollbars
        setPreferredSize(new Dimension(fLastXValue
                + fProperties.getRightMargin(), lastYPos
                + fProperties.getBottomMargin()));
        // repaint the diagram
        revalidate();
        repaint();
    }

    /**
     * Calculates the position of the next message depending on the given
     * parameters.
     * 
     * @param lastYValue
     *            the y value of the last drawn message
     * @param cmd
     *            the command to be drawn in the diagram
     * @param owner
     *            the owner lifeline of the command message
     * @return the position of the next message
     */
    public int calculateNextMessPosition(int lastYValue, MCmd cmd,
            Lifeline owner) {
        int value = lastYValue;
        // if there has not been drawn a message so far
        // -> caculate position of forst message in the sequence diagram
        if (lastYValue == 0) {
            if (cmd instanceof CmdCreatesObjects
                    || cmd instanceof MCmdInsertLink) {
                value = fProperties.yStart() + owner.getObjectBox().getHeight()
                        / 2 + fProperties.getFontSize();
            } else {
                value = fProperties.yStart();
                if (fProperties.getActManDist() != -1) {
                    value += fProperties.getActManDist();
                } else {
                    value += fProperties.actStep();

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
        // return position fr the next message
        return value;
    }

    /**
     * Creates the activations following from a create command and calculates
     * the y-position of this activation in the sequence diagram.
     * 
     * @param cmd
     *            the create command for which the activations should be created
     * @param activationStack
     *            the Stack of all activations created so far
     * @param lastyValue
     *            the y-Value of the last created activation, that is the
     *            activation drawn atop of the activation which should be
     *            created now
     * @param lastAct
     *            the antecessor of this activation
     * @return int the y-value in the sequence diagram of the created activation
     */
    public int drawCreate(MCmd cmd, Stack<Activation> activationStack, int lastyValue,
            Activation lastAct) {
        // created objects
        List<MObject> fObjectList = ((CmdCreatesObjects) cmd).getObjects();
        int yValue = lastyValue;
        // view each object
        for (int i = 0; i < fObjectList.size(); i++) {
            MObject obj = fObjectList.get(i);
            // Lifeline of the regarded object
            Lifeline ll = (ObjLifeline) fLifelines.get(obj);
            // if create should be drawn in the diagram
            if (fProperties.showCreate() && !ll.isHidden()) {
                // calculate position of the create-message
                yValue = calculateNextMessPosition(yValue, cmd, ll);
                // mark lifeline to draw
                ll.drawLifeline(true);
                // get last activation
                Activation srcAct = null;
                if (!activationStack.empty()) {
                    srcAct = (Activation) activationStack.peek();
                }
                // if the source-lifeline is not marked to be hidden
                if (srcAct == null || !srcAct.owner().isHidden()) {
                    // create new activation
                    Activation a = new Activation(fNumSteps, ll, cmd, srcAct,
                            yValue);
                    // object box of the lifeline
                    ObjectBox objBox = ll.getObjectBox();
                    // get x-value on which the lifeline is positiones
                    int xValue = ll.xValue();
                    // add new actiavtion to the lifeline
                    ll.addActivation(a);
                    // calculate length og activation message
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
                        if ((a.getY() > fView.getY() && a.getY() < fView.getY()
                                + fView.getHeight())
                                || (a.getEnd() > fView.getY() && a.getYEnd() < fView
                                        .getY()
                                        + fView.getHeight())) {
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
                    } else if (messLength < 0
                            && -messLength > fProperties.maxActMess()) {
                        fProperties.setMaxActMess(-messLength);
                    }
                    fNumSteps++;
                } else if (!fProperties.compactDisplay()) {
                    yValue = calculateNextMessPosition(yValue, cmd, ll);
                    fNumSteps++;
                }
            }
        }
        return yValue;
    }

    /**
     * Creates the activations following from a destroy command and calculates
     * the y-position of this activation in the sequence diagram.
     * 
     * @param cmd
     *            the destroy command for which the activations should be
     *            created
     * @param activationStack
     *            the Stack of all activations created so far
     * @param lastYValue
     *            the y-Value of the last created activation, that is the
     *            activation drawn atop of the activation which should be
     *            created now
     * @param lastAct
     *            the antecessor of this activation
     * @return the y-value in the sequence diagram of the created activation
     */
    public int drawDestroy(MCmd cmd, Stack<Activation> activationStack, int lastYValue,
            Activation lastAct) throws CommandFailedException {
        // see above
        List<MObject> fObjectList = ((MCmdDestroyObjects) cmd).getObjects();
        int yValue = lastYValue;
        for (int i = 0; i < fObjectList.size(); i++) {
            MObject obj = getObj(cmd);
            ObjLifeline ll = (ObjLifeline) fLifelines.get(obj);
            if (fProperties.showDestroy() && !ll.isHidden()) {
                yValue = calculateNextMessPosition(yValue, cmd, ll);
                ll.drawLifeline(true);
                Activation srcAct = null;
                if (!activationStack.empty()) {
                    srcAct = activationStack.peek();
                }
                if (srcAct == null || !srcAct.owner().isHidden()) {
                    ObjectBox objBox = ll.getObjectBox();
                    if (objBox.getY() == -1) {
                        int xValue = ll.xValue();
                        fNumSteps++;
                        objBox.setX(xValue);
                        objBox.setY(fProperties.yStart() - 10
                                - objBox.getHeight());
                    }

                    Activation a = new Activation(fNumSteps, ll, cmd, srcAct,
                            yValue);
                    ll.addActivation(a);
                    a.calculateMessLength();
                    activationStack.push(a);

                    a = activationStack.pop();
                    a.setEnd(0);

                    a.setY(yValue);
                    yValue = a.calculateEnd();
                    if (fOnlyView) {
                        if ((a.getY() > fView.getY() && a.getY() < fView.getY()
                                + fView.getHeight())
                                || (a.getEnd() > fView.getY() && a.getYEnd() < fView
                                        .getY()
                                        + fView.getHeight())) {
                            fActivations.add(a);
                        }
                    } else {
                        fActivations.add(a);
                    }
                    int messLength = a.getMessLength(); // a.calculateMessLength();
                    if (messLength > 0 && messLength > fProperties.maxActMess()) {
                        fProperties.setMaxActMess(messLength);
                    } else if (messLength < 0
                            && -messLength > fProperties.maxActMess()) {
                        fProperties.setMaxActMess(-messLength);
                    }
                    fNumSteps++;
                } else if (!fProperties.compactDisplay()) {
                    yValue = calculateNextMessPosition(yValue, cmd, ll);
                    fNumSteps++;
                }
            }
        }
        return yValue;
    }

    /**
     * Creates the activations following from a set command and calculates the
     * y-position of this activation in the sequence diagram.
     * 
     * @param cmd
     *            the set command for which the activations should be created
     * @param activationStack
     *            the Stack of all activations created so far
     * @param lastYValue
     *            the y-Value of the last created activation, that is the
     *            activation drawn atop of the activation which should be
     *            created now
     * @param lastAct
     *            the antecessor of this activation
     * @return the y-value in the sequence diagram of the created activation
     */
    public int drawSet(MCmd cmd, Stack<Activation> activationStack, int lastYValue,
            Activation lastAct) throws CommandFailedException {
        // see above
        MObject obj = getObj(cmd);
        ObjLifeline ll = (ObjLifeline) fLifelines.get(obj);
        int yValue = lastYValue;
        if ((fProperties.showSet() && !ll.isHidden())
                || !fProperties.compactDisplay()) {
            Activation srcAct = null;
            if (!activationStack.empty())
                srcAct = (Activation) activationStack.peek();

            if (srcAct == null || !srcAct.owner().isHidden()) {
                ObjectBox objBox = ll.getObjectBox();
                if (objBox.getY() == -1) {
                    int xValue = ll.xValue();
                    objBox.setX(xValue);
                    objBox.setY(fProperties.yStart() - 10 - objBox.getHeight());
                }

                Activation a = new Activation(fNumSteps, ll, cmd, srcAct,
                        yValue);

                if (fProperties.showSet()) {
                    ll.enterActivation(a);
                    a.calculateMessLength();
                    ll.exitActivation();
                    activationStack.push(a);
                    a = (Activation) activationStack.pop();
                }

                fNumSteps++;

                a.setEnd(fNumSteps);
                yValue = calculateNextMessPosition(lastYValue, cmd, ll);
                a.setY(yValue);
                int messLength = a.getMessLength();
                if (messLength > 0 && messLength > fProperties.maxActMess()) {
                    fProperties.setMaxActMess(messLength);
                } else if (messLength < 0
                        && -messLength > fProperties.maxActMess()) {
                    fProperties.setMaxActMess(-messLength);
                }
                fNumSteps++;
                yValue = a.calculateEnd();
                if (fProperties.showSet()) {
                    ll.drawLifeline(true);
                    if (fOnlyView) {
                        if ((a.getY() > fView.getY() && a.getY() < fView.getY()
                                + fView.getHeight())
                                || (a.getEnd() > fView.getY() && a.getYEnd() < fView
                                        .getY()
                                        + fView.getHeight())) {

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
     * @param cmd
     *            the openter command for which the activations should be
     *            created
     * @param activationStack
     *            the Stack of all activations created so far
     * @param lastYValue
     *            the y-Value of the last created activation, that is the
     *            activation drawn atop of the activation which should be
     *            created now
     * @param lastAct
     *            the antecessor of this activation
     * @return the y-value in the sequence diagram of the created activation
     */
    public int drawOpEnter(MCmd cmd, Stack<Activation> activationStack, int lastYValue,
            Activation lastAct) throws CommandFailedException {
        // see above
        MObject obj = getObj(cmd);
        ObjLifeline ll = (ObjLifeline) fLifelines.get(obj);
        int yValue = lastYValue;

        ll.drawLifeline(true);
        Activation srcAct = null;

        if (!activationStack.empty())
            srcAct = activationStack.peek();
        if (!ll.isHidden() && (srcAct == null || !srcAct.owner().isHidden()))
            yValue = calculateNextMessPosition(yValue, cmd, ll);

        ObjectBox objBox = ll.getObjectBox();

        if (objBox.getY() == -1) {
            int xValue = ll.xValue();
            objBox.setX(xValue);
            objBox.setY(fProperties.yStart() - 10 - objBox.getHeight());
        }

        Activation a = new Activation(fNumSteps, ll, cmd, srcAct, yValue);

        a.calculateMessLength();
        if (!ll.isHidden() && (srcAct == null || !srcAct.owner().isHidden())) {
            fNumSteps++;
        }
        a.setY(yValue);
        // operation call was successful -> increase activation nesting of
        // lifeline
        // -> call enterActivation
        if (((MCmdOpEnter) cmd).successful()) {
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
                if ((a.getY() > fView.getY() && a.getY() < fView.getY()
                        + fView.getHeight())
                        || (a.getEnd() > fView.getY() && a.getYEnd() < fView
                                .getY()
                                + fView.getHeight())) {
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
     * @param cmd
     *            the insert command for which the activations should be created
     * @param activationStack
     *            the Stack of all activations created so far
     * @param lastYValue
     *            the y-Value of the last created activation, that is the
     *            activation drawn atop of the activation which should be
     *            created now
     * @param lastAct
     *            the antecessor of this activation
     * @return the y-value in the sequence diagram of the created activation
     */
    public int drawInsert(MCmd cmd, Stack<Activation> activationStack, int lastYValue,
            Activation lastAct) throws CommandFailedException {
        MAssociation ass = ((MCmdInsertLink) cmd).getAssociation();
        String key = "";
        MObject[] objects = ((MCmdInsertLink) cmd).getObjects();
        for (int i = 0; i < objects.length; i++) {
            MObject obj = objects[i];
            key = key + obj.name();
        }
        key += ass.name();
        AssLifeline ll = (AssLifeline) fLifelines.get(key);
        
        int yValue = lastYValue;
        if ((fProperties.showInsert() && !ll.isHidden())
                || !fProperties.compactDisplay()) {
            Activation srcAct = null;

            if (!activationStack.empty()) {
                srcAct = (Activation) activationStack.peek();
            }
            if (srcAct == null || !srcAct.owner().isHidden()) {
                yValue = calculateNextMessPosition(yValue, cmd, ll);
                if (ll.getAntecessor() != null) {
                    ObjectBox antBox = ll.getAntecessor().getObjectBox();
                    int antEnd = antBox.getY() + antBox.getHeight();
                    if (yValue < antEnd)
                        yValue = antEnd;
                }

                InsertActivation a = new InsertActivation(fNumSteps, ll, cmd,
                        srcAct, yValue);
                ObjectBox objBox = ll.getObjectBox();
                int xValue = ll.xValue();
                objects = ((MCmdInsertLink) cmd).getObjects();
                if (fProperties.showInsert()) {
                    for (int i = 0; i < objects.length; i++) {
                        MObject object = objects[i];
                        ObjLifeline oll = (ObjLifeline) fLifelines.get(object);
                        if (!oll.isHidden()) {
                            oll.drawLifeline(true);
                            if (oll.getObjectBox().getY() == -1) {
                                oll.getObjectBox().setX(xValue);
                                oll.getObjectBox().setY(
                                        fProperties.yStart() - 10
                                                - objBox.getHeight());
                            }

                            if (fProperties.getActManDist() != -1) {
                                yValue += objects.length
                                        * fProperties.getActManDist();
                            } else {
                                yValue += objects.length
                                        * fProperties.actStep();
                            }
                            fNumSteps++;
                        }
                    }

                    ll.drawLifeline(true);
                    ll.enterActivation(a);
                    a.calculateMessLength();
                    ll.exitActivation();
                    activationStack.push(a);
                    a = (InsertActivation) activationStack.pop();
                }
                fNumSteps++;
                a.setEnd(fNumSteps);
                objBox.setX(xValue);
                yValue = calculateNextMessPosition(lastYValue, cmd, ll);
                a.setY(yValue);
                objBox.setY(yValue - objBox.getHeight() / 2);
                fNumSteps++;
                yValue = a.calculateEnd();
                if (fProperties.showInsert()) {
                    if (fOnlyView) {
                        if ((a.getY() > fView.getY() && a.getY() < fView.getY()
                                + fView.getHeight())
                                || (a.getEnd() > fView.getY() && a.getYEnd() < fView
                                        .getY()
                                        + fView.getHeight())) {

                            fActivations.add(a);
                        }
                    } else {
                        fActivations.add(a);
                    }

                }
                int messLength = a.getMessLength();
                if (messLength > 0 && messLength > fProperties.maxActMess()) {
                    fProperties.setMaxActMess(messLength);
                } else if (messLength < 0
                        && -messLength > fProperties.maxActMess()) {
                    fProperties.setMaxActMess(-messLength);
                }
            }
        }
        return yValue;
    }

    /**
     * Creates the activations following from a destroy command and calculates
     * the y-position of this activation in the sequence diagram.
     * 
     * @param cmd
     *            the destroy command for which the activations should be
     *            created
     * @param activationStack
     *            the Stack of all activations created so far
     * @param lastYValue
     *            the y-Value of the last created activation, that is the
     *            activation drawn atop of the activation which should be
     *            created now
     * @param lastAct
     *            the antecessor of this activation
     * @return the y-value in the sequence diagram of the created activation
     */
    public int drawDelete(MCmd cmd, Stack<Activation> activationStack, int lastYValue,
            Activation lastAct) throws CommandFailedException {
        // see above
        MAssociation ass = ((MCmdDeleteLink) cmd).getAssociation();
        String key = "";
        MObject[] objects = ((MCmdDeleteLink) cmd).getObjects();
        for (int i = 0; i < objects.length; i++) {
            MObject obj = objects[i];
            key = key + obj.name();
        }
        key += ass.name();
        // int counter = 1;
        
        int yValue = lastYValue;
        AssLifeline ll = (AssLifeline) fLifelines.get(key);
        if (fProperties.showDelete() && !ll.isHidden()) {
            Activation srcAct = null;
            if (!activationStack.empty()) {
                srcAct = (Activation) activationStack.peek();
            }
            if (srcAct == null || !srcAct.owner().isHidden()) {
                if (srcAct == null || !srcAct.owner().isHidden()) {
                    yValue = calculateNextMessPosition(yValue, cmd, ll);
                    
                    ll.drawLifeline(true);
                    ll.setDeleted(true);

                    ObjectBox objBox = ll.getObjectBox();
                    if (objBox.getY() == -1) {
                        int xValue = ll.xValue();
                        fNumSteps++;
                        objBox.setX(xValue);
                        objBox.setY(fProperties.yStart() - 10
                                - objBox.getHeight());
                    }

                    ll.setNesting(0);

                    Activation a = new Activation(fNumSteps, ll, cmd, srcAct,
                            yValue);
                    ll.addActivation(a);
                    a.calculateMessLength();
                    activationStack.push(a);
                    fNumSteps++;

                    a = (Activation) activationStack.pop();
                    a.setEnd(0);
                    yValue = calculateNextMessPosition(lastYValue, cmd, ll);

                    a.setY(yValue);
                    yValue = a.calculateEnd();
                    if (fOnlyView) {
                        if ((a.getY() > fView.getY() && a.getY() < fView.getY()
                                + fView.getHeight())
                                || (a.getEnd() > fView.getY() && a.getYEnd() < fView
                                        .getY()
                                        + fView.getHeight())) {
                            fActivations.add(a);
                        }
                    } else {
                        fActivations.add(a);
                    }
                    int messLength = a.getMessLength();
                    if (messLength > 0 && messLength > fProperties.maxActMess()) {
                        fProperties.setMaxActMess(messLength);
                    } else if (messLength < 0
                            && -messLength > fProperties.maxActMess()) {
                        fProperties.setMaxActMess(-messLength);
                    }
                } else if (!fProperties.compactDisplay()) {
                    yValue = calculateNextMessPosition(yValue, cmd, ll);
                    fNumSteps++;
                }
            }
        }
        return yValue;
    }

    /**
     * Calculates the lifeline which has as y-value the given value.
     * 
     * @param x
     *            the y-value of the lifeline which should be found
     * @return Lifeline the Lifeline which is located on the given y-value
     */
    public Lifeline getLifeline(int x) {
        // view each lifeline
        for (Lifeline ll : fLifelines.values()) {
            // object box of the lifeline
            ObjectBox ob = ll.getObjectBox();
            // if x-value is betwwen the start- and end-x-value of the object
            // box --> right lifeline is found
            if (ob.getStart() < x && ob.getEnd() > x)
                return ll;
        }
        return null;
    }

    /**
     * Restores the position values of all lifelines.
     * 
     */
    public synchronized void restoreAllValues() {
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
     * @param g
     *            the context into which the page is drawn
     * @param pf
     *            the size and orientation of the page being drawn
     * @param pi
     *            the zero based index of the page to be drawn (the page index)
     * @return PAGE_EXISTS if the page is rendered successfully or NO_SUCH_PAGE
     *         if pageIndex specifies a non-existent page.
     * @throws PrinterException
     *             thrown when the print job is terminated.
     */
    public int print(Graphics g, PageFormat pf, int pi) throws PrinterException {
        if (pi >= 1)
            return Printable.NO_SUCH_PAGE;

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        g2d.translate(pf.getImageableWidth() / 2, pf.getImageableHeight() / 2);

        Dimension d = getSize();
        double scale = Math.min(pf.getImageableWidth() / d.width, pf
                .getImageableHeight()
                / d.height);
        // fit to page
        if (scale < 1.0)
            g2d.scale(scale, scale);
        g2d.translate(-d.width / 2.0, -d.height / 2.0);

        Font f = Font.getFont("use.gui.print.diagramFont", getFont());
        g2d.setFont(f);

        // update the sequence diagram
        try {
            update();
        } catch (CommandFailedException cfe) {
            // ignored
        }
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
     * @param pf
     *            the size and orientation of the page being drawn
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
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Prints only the current visible fView.
     * 
     * @param pf
     *            the size and orientation of the page being drawn
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
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
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
    class MyMouseListener extends MouseAdapter {

        /**
         * Checks if the mouse button is pressed on a lifeline and if yes marks
         * this lifeline as fChoosedLl.
         * 
         * @param event
         *            the releasing MouseEvent
         */
        public void mousePressed(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON1) {
                // x-value of the mouse position
                int x = event.getX();
                // find choosed lifeline
                fChoosedLl = getLifeline(x);
            }
        }

        /**
         * Unmarks the fChoosedLl, when the mouse button has been released.
         * 
         * @param event
         *            the releasing MouseEvent
         */
        public void mouseReleased(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON1) {
                // there is no longer a lifeline choosed
                // fChoosedLl = null;
                // update the sequence diagram
                try {
                    update();
                } catch (CommandFailedException cfe) {
                    // ignored
                }
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
    class MyMouseMotionListener extends MouseMotionAdapter {
        /**
         * Resets the values of the choosed Lifeline, when the Mouse has been
         * moved while the mouse button is pressed.
         * 
         * @param event
         *            the releasing MouseEvent
         */
        public void mouseDragged(MouseEvent event) {
            // if there is a choosed lifeline
            if (fChoosedLl != null) {
                // set new x-value
                fChoosedLl.setNewValues(event.getX());
                fLastXValue = 0;
                // calculate all lifeline positions
                calculateLlPositions(getFontMetrics(fProperties.getFont()));
                // calculate last y position on which something is drawn in the
                // diagram
                int lastYPos = fProperties.yStart() + fNumSteps
                        * fProperties.actStep();
                if (fProperties.getActManDist() != -1) {
                    lastYPos = fProperties.yStart() + fNumSteps
                            * fProperties.getActManDist();
                }
                // calculate the preferred size
                setPreferredSize(new Dimension(fLastXValue
                        + fProperties.getRightMargin(), lastYPos
                        + fProperties.getBottomMargin()));
                // individual position should be hold
                fProperties.isIndividualLl(true);
                // repaint the diagram
                revalidate();
                repaint();
            }

        }
    }
}
