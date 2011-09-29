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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.beans.PropertyVetoException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.tzi.use.config.Options;
import org.tzi.use.gui.main.runtime.IPluginActionExtensionPoint;
import org.tzi.use.gui.util.ExtFileFilter;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.util.StatusBar;
import org.tzi.use.gui.util.TextComponentWriter;
import org.tzi.use.gui.views.AssociationEndsInfo;
import org.tzi.use.gui.views.CallStackView;
import org.tzi.use.gui.views.ClassExtentView;
import org.tzi.use.gui.views.ClassInvariantView;
import org.tzi.use.gui.views.CommandView;
import org.tzi.use.gui.views.LinkCountView;
import org.tzi.use.gui.views.ObjectCountView;
import org.tzi.use.gui.views.ObjectPropertiesView;
import org.tzi.use.gui.views.StateEvolutionView;
import org.tzi.use.gui.views.View;
import org.tzi.use.gui.views.diagrams.AssociationName;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagramView;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.gui.views.diagrams.event.DiagramInputHandling;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagramView;
import org.tzi.use.gui.views.selection.classselection.SelectedAssociationPathView;
import org.tzi.use.gui.views.selection.classselection.SelectedClassPathView;
import org.tzi.use.gui.views.selection.classselection.SelectionClassView;
import org.tzi.use.gui.views.selection.objectselection.SelectedLinkPathView;
import org.tzi.use.gui.views.selection.objectselection.SelectedObjectPathView;
import org.tzi.use.gui.views.selection.objectselection.SelectionOCLView;
import org.tzi.use.gui.views.selection.objectselection.SelectionObjectView;
import org.tzi.use.gui.views.seqDiag.SDScrollPane;
import org.tzi.use.gui.views.seqDiag.SequenceDiagramView;
import org.tzi.use.main.Session;
import org.tzi.use.main.Session.EvaluatedStatement;
import org.tzi.use.main.runtime.IRuntime;
import org.tzi.use.main.shell.Shell;
import org.tzi.use.parser.use.USECompiler;
import org.tzi.use.runtime.gui.impl.PluginActionProxy;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.StateChangeEvent;
import org.tzi.use.uml.sys.StateChangeListener;
import org.tzi.use.uml.sys.soil.MEnterOperationStatement;
import org.tzi.use.uml.sys.soil.MExitOperationStatement;
import org.tzi.use.uml.sys.soil.MNewObjectStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.Log;
import org.tzi.use.util.USEWriter;

/**
 * The main application window of USE.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Mark Richters
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame implements StateChangeListener {
    private Session fSession;

    private StatusBar fStatusBar;

    private LogPanel fLogPanel;

    private PrintWriter fLogWriter;

    private JDesktopPane fDesk;

    private JSplitPane fTopSplitPane;

    private ModelBrowser fModelBrowser;

    private JMenuItem fMenuItemEditUndo;
    private JMenuItem fMenuItemEditRedo;

	private JToolBar fToolBar;
	private JMenuBar fMenuBar;
    private JButton fBtnEditUndo;
    private JButton fBtnEditRedo;

    private JCheckBoxMenuItem fCbMenuItemCheckStructure;

    private List<ClassDiagramView> classDiagrams = new ArrayList<ClassDiagramView>();
    private static final String DEFAULT_UNDO_TEXT = "Undo last statement";
    private List<NewObjectDiagramView> objectDiagrams = new ArrayList<NewObjectDiagramView>();
    private static final String DEFAULT_REDO_TEXT = "Redo last undone statement";

    private static final String STATE_EVAL_OCL = "Evaluate OCL expression";

    private PageFormat fPageFormat;

    private static MainWindow fInstance; // global instance of main window

    private JButton addToToolBar(JToolBar toolBar, AbstractAction action,
            String toolTip) {
        JButton tb = toolBar.add(action);
        tb.setToolTipText(toolTip);
        tb.setText("");
        return tb;
    }

	private static IRuntime fPluginRuntime;
 
	private Map<Map<String, String>, PluginActionProxy> pluginActions = 
		new HashMap<Map<String, String>, PluginActionProxy>();

	MainWindow(Session session, IRuntime pluginRuntime) {
        super("USE");
		if (pluginRuntime != null) {
			fPluginRuntime = pluginRuntime;
		}
        fInstance = this;
        fSession = session;
        // the session may be changed from the shell
        fSession.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                    	sessionChanged();
                    }
                });
            }
        }); 
        
        /**
         * for soil statements
         */
        fSession.addEvaluatedStatementListener(
        		new Session.EvaluatedStatementListener(){
        			@Override
        			public void evaluatedStatement(EvaluatedStatement event) {
        				SwingUtilities.invokeLater(new Runnable(){
        					@Override
        					public void run() {
        						setUndoRedoButtons();
        					}
        				});
        			}});

        // create toolbar
		fToolBar = new JToolBar();
		addToToolBar(fToolBar, fActionFileOpenSpec,  "Open specification");
		addToToolBar(fToolBar, fActionFilePrint,     "Print diagram");
		addToToolBar(fToolBar, fActionFilePrintView, "Print view");
		fToolBar.addSeparator();

		fBtnEditUndo = addToToolBar(fToolBar, fActionEditUndo, DEFAULT_UNDO_TEXT);
		fBtnEditRedo = addToToolBar(fToolBar, fActionEditRedo, DEFAULT_REDO_TEXT);
		
		fToolBar.addSeparator();
		
		addToToolBar(fToolBar, fActionStateEvalOCL, STATE_EVAL_OCL);
		
		fToolBar.addSeparator();

		addToToolBar(fToolBar, fActionViewCreateClassDiagram,
                "Create class diagram view");
		addToToolBar(fToolBar, fActionViewCreateObjectDiagram,
                "Create object diagram view");
		addToToolBar(fToolBar, fActionViewCreateClassInvariant,
                "Create class invariant view");
		addToToolBar(fToolBar, fActionViewCreateObjectCount,
                "Create object count view");
		addToToolBar(fToolBar, fActionViewCreateLinkCount,
                "Create link count view");
		addToToolBar(fToolBar, fActionViewCreateStateEvolution,
                "Create state evolution view");
        addToToolBar(
				fToolBar,
                fActionViewCreateObjectProperties,
                "Create object properties view <br>"
                        + "(double click on object to show properties for a specific object)");
		addToToolBar(fToolBar, fActionViewCreateClassExtent,
                "Create class extent view");
		addToToolBar(fToolBar, fActionViewCreateSequenceDiagram,
                "Create sequence diagram view");
		addToToolBar(fToolBar, fActionViewCreateCallStack,
                "Create call stack view");
		addToToolBar(fToolBar, fActionViewCreateCommandList,
                "Create command list view");
        // addToToolBar(toolBar, fActionViewCreateStateTree, "Create state tree
        // view");

        // create the menubar
		fMenuBar = new JMenuBar();
		getRootPane().setJMenuBar(fMenuBar);

        // `File' submenu
        JMenuItem mi;
        JMenu menu = new JMenu("File");
        menu.setMnemonic('F');
		fMenuBar.add(menu);

        mi = menu.add(fActionFileOpenSpec);
        mi.setAccelerator(KeyStroke
                .getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
        mi.setMnemonic('O');

        mi = menu.add(fActionFileSaveScript);
        mi.setMnemonic('S');

        mi = menu.add(fActionFileSaveProtocol);

        menu.addSeparator();
        mi = menu.add(fActionFilePrinterSetup);
        mi = menu.add(fActionFilePrint);
        mi = menu.add(fActionFilePrintView);

        menu.addSeparator();
        mi = menu.add(fActionFileExit);
        mi.setMnemonic('x');
        mi.setAccelerator(KeyStroke
                .getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));

        // `Edit' submenu
        menu = new JMenu("Edit");
        menu.setMnemonic('E');
		fMenuBar.add(menu);

        fMenuItemEditUndo = menu.add(fActionEditUndo);
        fMenuItemEditUndo.setMnemonic('U');
        fMenuItemEditUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                Event.CTRL_MASK));
        
        fMenuItemEditRedo = menu.add(fActionEditRedo);
        fMenuItemEditRedo.setMnemonic('R');
        fMenuItemEditRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                Event.CTRL_MASK + Event.SHIFT_MASK));
        
        // `State' submenu
        menu = new JMenu("State");
        menu.setMnemonic('S');
		fMenuBar.add(menu);

        mi = menu.add(fActionStateCreateObject);
        mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));
        mi.setMnemonic('C');

        mi = menu.add(fActionStateEvalOCL);
        mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0));
        mi.setMnemonic('E');

        mi = menu.add(fActionStateCheckStructure);
        mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));
        mi.setMnemonic('s');

        fCbMenuItemCheckStructure = new JCheckBoxMenuItem(
                "Check structure after every change");
        fCbMenuItemCheckStructure.setMnemonic('h');
        fCbMenuItemCheckStructure.setSelected(false);
        menu.add(fCbMenuItemCheckStructure);

        mi = menu.add(fActionStateReset);
        mi.setMnemonic('R');

        // `View' submenu
        menu = new JMenu("View");
        menu.setMnemonic('V');
		fMenuBar.add(menu);

        JMenu submenu = new JMenu("Create View");
        submenu.setMnemonic('C');
        menu.add(submenu);
        mi = submenu.add(fActionViewCreateObjectCount);
        mi.setMnemonic('O');
        mi = submenu.add(fActionViewCreateLinkCount);
        mi.setMnemonic('L');
        mi = submenu.add(fActionViewCreateClassDiagram);
        mi.setMnemonic('V');
        mi = submenu.add(fActionViewCreateObjectDiagram);
        mi.setMnemonic('d');
        mi = submenu.add(fActionViewCreateClassInvariant);
        mi.setMnemonic('C');
        mi = submenu.add(fActionViewCreateStateEvolution);
        mi.setMnemonic('S');
        mi = submenu.add(fActionViewCreateObjectProperties);
        mi.setMnemonic('p');
        mi = submenu.add(fActionViewCreateClassExtent);
        mi.setMnemonic('e');
        mi = submenu.add(fActionViewCreateSequenceDiagram);
        mi.setMnemonic('q');
        mi = submenu.add(fActionViewCreateCallStack);
        mi.setMnemonic('a');
        mi = submenu.add(fActionViewCreateCommandList);
        mi.setMnemonic('i');
        mi = submenu.add(fActionViewAssociationInfo);
        
        menu.addSeparator();
        mi = menu.add(fActionViewTile);
        mi.setMnemonic('T');
        mi = menu.add(fActionViewCloseAll);
        mi.setMnemonic('a');

        // create the browser panel
		fModelBrowser = new ModelBrowser(this, fPluginRuntime);

        // create the desktop
        fDesk = new JDesktopPane();
        fDesk.setDesktopManager(new ViewManager());

        // create the log panel
        fLogPanel = new LogPanel();
        fLogWriter = new PrintWriter(new TextComponentWriter(fLogPanel
                .getTextComponent()), true);

        // create status bar
        fStatusBar = new StatusBar();

        // put the three panels into split panes
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                fModelBrowser, fDesk);
        sp.setDividerLocation(200);
        fTopSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sp, fLogPanel);
        fTopSplitPane.setDividerLocation(400);
        fTopSplitPane.setOneTouchExpandable(true);

        // Layout and set the content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(800, 550));
		contentPane.add(fToolBar, BorderLayout.NORTH);
        contentPane.add(fTopSplitPane, BorderLayout.CENTER);
        contentPane.add(fStatusBar, BorderLayout.SOUTH);
        setContentPane(contentPane);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        setBounds(10, 20, 900, 700);

		// GUI Plugin integration
		if (Options.doPLUGIN) {
			MainWindow.instance().fToolBar.addSeparator();
			// `Plugins' submenu
			menu = new JMenu("Plugins");
			menu.setMnemonic('P');
			this.fMenuBar.add(menu);

			IPluginActionExtensionPoint actionExtensionPoint = (IPluginActionExtensionPoint) fPluginRuntime
					.getExtensionPoint("action");

			this.pluginActions = actionExtensionPoint.createPluginActions(session, this);

			Set<Map.Entry<Map<String, String>, PluginActionProxy>> pluginActionSet = this.pluginActions.entrySet();

			for (Map.Entry<Map<String, String>, PluginActionProxy> currentActionMapEntry : pluginActionSet) {
				Map<String, String> currentActionDescMap = currentActionMapEntry.getKey();
				AbstractAction currentAction = currentActionMapEntry.getValue();
				addToToolBar(this.fToolBar, currentAction, currentActionDescMap.get("tooltip"));

				// Creating submenu and menu entries
				if (currentActionDescMap.get("menu").toString() == null) {
					// No submenu needed
					Log.debug("Adding ["
							+ currentActionDescMap.get("menuitem").toString()
							+ "] to plugins menu");
					menu.add(currentAction);
				} else {
					// Check if submenu already exists
					Component[] menuItems = menu.getMenuComponents();
					boolean createNewMenu = true;
					Log
							.debug("Menu item length was [" + menuItems.length
									+ "]");
					for (int iterateMenuItems = 0; iterateMenuItems < menuItems.length;) {
						Log.debug("Menu item is of type ["
								+ menuItems[iterateMenuItems].getClass() + "]");
						if (menuItems[iterateMenuItems] instanceof JMenu) {
							JMenu currentMenu = (JMenu) menuItems[iterateMenuItems];
							Log.debug("Compairing menu ["
									+ currentMenu.getText()
									+ "] and ["
									+ currentActionDescMap.get("menu")
											.toString() + "]");
							if (currentMenu.getText()
									.equals(
											currentActionDescMap.get("menu")
													.toString())) {
								Log.debug("Adding ["
										+ currentActionDescMap.get("menuitem")
												.toString() + "] to submenu ["
										+ currentMenu.getText() + "]");
								currentMenu.add(currentAction);
								createNewMenu = false;
								break;
							}
						}
						iterateMenuItems++;
					}
					if (createNewMenu) {
						Log.debug("Creating new Menu ["
								+ currentActionDescMap.get("menuitem")
										.toString() + "]");
						JMenu pluginSubmenu = new JMenu(currentActionDescMap
								.get("menu").toString());
						Log.debug("Adding ["
								+ currentActionDescMap.get("menu").toString()
								+ "] to new submenu ["
								+ pluginSubmenu.getText() + "]");
						pluginSubmenu.add(currentAction);
						menu.add(pluginSubmenu);
					}
				}
			}
		}

		// -- GUI Plugin integration (end)

		// `Help' submenu
		menu = new JMenu("Help");
		menu.setMnemonic('H');
		fMenuBar.add(menu);
		// not yet implemented in swing: menuBar.setHelpMenu(menu);
		mi = menu.add(fActionHelpAbout);
		mi.setMnemonic('A');

        // initialize application state to current system
        sessionChanged();
    }

    /**
     * Returns the selected view of all internal views. If none is selected null
     * is returned.
     */
    public View getSelectedView() {
        if (fDesk.getSelectedFrame() != null) {
            return ((ViewFrame) fDesk.getSelectedFrame()).getView();
        }
        return null;
    }

    /**
     * Returns the model browser instance.
     */
    public ModelBrowser getModelBrowser() {
        return fModelBrowser;
    }

    /**
     * A list of all displayed class diagrams 
     * @return
     */
    public List<ClassDiagramView> getClassDiagrams() {
    	return this.classDiagrams;
    }
    
    /**
     * A list of all displayed object diagrams 
     * @return
     */
    public List<NewObjectDiagramView> getObjectDiagrams() {
    	return this.objectDiagrams;
    }
    
    private void close() {
        setVisible(false);
        dispose();
		Shell.getInstance().exit();
    }

    /**
     * Returns the instance of MainWindow.
     */
    public static MainWindow instance() {
        return fInstance;
    }

    /**
     * Returns the page format for printing.
     */
    public PageFormat pageFormat() {
        if (fPageFormat == null) {
            // initialize with defaults
            PrinterJob job = PrinterJob.getPrinterJob();
            fPageFormat = job.defaultPage();
            Paper p = fPageFormat.getPaper();
            p.setSize(Options.PRINT_PAGEFORMAT_WIDTH,
                    Options.PRINT_PAGEFORMAT_HEIGHT);
            fPageFormat.setPaper(p);
            if (Options.PRINT_PAGEFORMAT_ORIENTATION.equals("portrait"))
                fPageFormat.setOrientation(PageFormat.PORTRAIT);
            else if (Options.PRINT_PAGEFORMAT_ORIENTATION.equals("landscape"))
                fPageFormat.setOrientation(PageFormat.LANDSCAPE);
            else if (Options.PRINT_PAGEFORMAT_ORIENTATION.equals("seascape"))
                fPageFormat.setOrientation(PageFormat.REVERSE_LANDSCAPE);
        }
        return fPageFormat;
    }

    public StatusBar statusBar() {
        return fStatusBar;
    }

    LogPanel logPanel() {
        return fLogPanel;
    }

    public PrintWriter logWriter() {
        return fLogWriter;
    }

    private void checkStructure() {
        // fLogWriter.println("Checking structure...");
        boolean ok = fSession.system().state().checkStructure(fLogWriter);
        fLogWriter.println("checking structure, "
                + ((ok) ? "ok." : "found errors."));
        fLogWriter.flush();
    }

    public void stateChanged(StateChangeEvent e) {
    	setUndoRedoButtons();
    	
        if (e.structureHasChanged() && fCbMenuItemCheckStructure.isSelected()) {
        	 checkStructure();
        }
        
        fActionFileSaveScript.setEnabled(
        		fSession.system().numEvaluatedStatements() > 0);
    }

    private int fViewFrameX = 0;

    private int fViewFrameY = 0;

    /**
     * Adds a new view (internal frame) to the desktop.
     */
	public void addNewViewFrame(ViewFrame f) {
        f.setBounds(fViewFrameX, fViewFrameY, 300, 200);
        fDesk.add(f, JDesktopPane.DEFAULT_LAYER);
        fDesk.moveToFront(f);
        // position for next frame
        if (fViewFrameX < 200) {
            fViewFrameX += 20;
            fViewFrameY += 20;
        } else {
            fViewFrameX = 0;
            fViewFrameY = 0;
        }

        // enable print button when a printable view is activated
        final boolean isPrintable = f.isPrintable();
        final boolean isViewPrintable = (f.getView() instanceof SequenceDiagramView);
        f.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameActivated(InternalFrameEvent ev) {
                fActionFilePrint.setEnabled(isPrintable);
                fActionFilePrintView.setEnabled(isViewPrintable);
            }

            public void internalFrameDeactivated(InternalFrameEvent ev) {
                fActionFilePrint.setEnabled(false);
                fActionFilePrintView.setEnabled(false);
            }
        });

        f.setVisible(true);
    }

    /**
     * Set application state for new system. The system parameter may be null.
     */
    void sessionChanged() {
        boolean on = fSession.hasSystem();
        fActionStateCreateObject.setEnabled(on);
        fActionStateEvalOCL.setEnabled(on);
        fActionStateCheckStructure.setEnabled(on);
        fActionViewCreateObjectCount.setEnabled(on);
        fActionViewCreateObjectCount.setEnabled(on);
        fActionViewCreateLinkCount.setEnabled(on);
        fActionViewCreateClassDiagram.setEnabled(on);
        fActionViewCreateObjectDiagram.setEnabled(on);
        fActionViewCreateClassInvariant.setEnabled(on);
        fActionViewCreateStateEvolution.setEnabled(on);
        fActionViewCreateObjectProperties.setEnabled(on);
        fActionViewCreateClassExtent.setEnabled(on);
        fActionViewCreateSequenceDiagram.setEnabled(on);
        fActionViewCreateCallStack.setEnabled(on);
        fActionViewCreateCommandList.setEnabled(on);
        
		if (Options.doPLUGIN) {
			for (AbstractAction currentAction : pluginActions.values()) {
				currentAction.setEnabled(on);
			}
		}
        disableUndo();
        closeAllViews();

        if (on) {
            MSystem system = fSession.system();
            fModelBrowser.setModel(system.model());
            system.addChangeListener(this);
            setTitle("USE: " + system.model().filename());
        } else {
            fModelBrowser.setModel(null);
            // fSession.system().removeChangeListener(this);
            fActionFileSaveScript.setEnabled(false);
            setTitle("USE");
        }
    }

    /**
     * Closes all views.
     */
    void closeAllViews() {
        // How many frames do we have?
        JInternalFrame[] allframes = fDesk.getAllFrames();
        int count = allframes.length;
        for (int i = 0; i < count; i++) {
            ViewFrame f = (ViewFrame) allframes[i];
            fDesk.getDesktopManager().closeFrame(f);
        }
        // reset start position for new frames
        fViewFrameX = 0;
        fViewFrameY = 0;
    }
    
    private void setUndoRedoButtons() {
    	String nextToUndo = 
			fSession.system().getUndoDescription();
		
		if (nextToUndo != null) {
			enableUndo(nextToUndo);
		} else {
			disableUndo();
		}
		
		String nextToRedo =
			fSession.system().getRedoDescription();
		
		if (nextToRedo != null) {
			enableRedo(nextToRedo);
		} else {
			disableRedo();
		}
    }

    /**
     * Enables the undo command.
     */
    void enableUndo(String name) {
        fActionEditUndo.setEnabled(true);
        // change text of menu item, leave toolbar button untouched
        String s = "Undo: " + name;

        if (fMenuItemEditUndo != null) {
            fMenuItemEditUndo.setText(s);
        }
        if (fBtnEditUndo != null) {
            fBtnEditUndo.setToolTipText(s);
        }
    }

    /**
     * Disables the undo command.
     */
    void disableUndo() {
        fActionEditUndo.setEnabled(false);
        // change text of menu item, leave toolbar button untouched
        fMenuItemEditUndo.setText("Undo");
        fBtnEditUndo.setToolTipText(DEFAULT_UNDO_TEXT);
    }
    
    /**
     * Enables the redo command.
     */
    void enableRedo(String name) {
    	 fActionEditRedo.setEnabled(true);
         // change text of menu item, leave toolbar button untouched
         String s = "Redo: " + name;
         // FIXME: apparently enableUndo is sometimes called before GUI setup
         if (fMenuItemEditRedo != null) {
             fMenuItemEditRedo.setText(s);
         }
         if (fBtnEditRedo != null) {
             fBtnEditRedo.setToolTipText(s);
         }
    }
    
    /**
     * Disables the undo command.
     */
    void disableRedo() {
        fActionEditRedo.setEnabled(false);
        // change text of menu item, leave toolbar button untouched
        fMenuItemEditRedo.setText("Redo");
        fBtnEditRedo.setToolTipText(DEFAULT_REDO_TEXT);
    }

    /**
     * Shows the log panel.
     */
    void showLogPanel() {
        double loc = fTopSplitPane.getDividerLocation();
        // if divider is at bottom move it to top so that the log is visible
        if (loc / (fTopSplitPane.getHeight() - fTopSplitPane.getDividerSize()) > 0.95)
            fTopSplitPane.setDividerLocation(0.75);
    }

    public void createObject(String clsName) {
    	MClass objectClass = fSession.system().model().getClass(clsName);
    	
    	if (objectClass == null) {
            JOptionPane.showMessageDialog(
            		this, 
            		"No class named `" + clsName + "' defined in model.", 
            		"Error", 
            		JOptionPane.ERROR_MESSAGE);
            
            return;
        } 
    	
    	createObject(objectClass, null);
    }
    
    /**
     * Creates a new object. Keeps track of undo information and handles errors
     * on the GUI level.
     */
    public void createObject(MClass objectClass, String objectName) {
    	 
        try {
        	MNewObjectStatement statement = 
        		new MNewObjectStatement(objectClass, objectName);
        	
        	USEWriter.getInstance().protocol(
					"[GUI] " + statement.getShellCommand().substring(1));
        	
        	fSession.system().evaluateStatement(statement);
        			
        } catch (MSystemException e) {
        	JOptionPane.showMessageDialog(
					this, 
					e.getMessage(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // Actions

    private ActionFileOpenSpec fActionFileOpenSpec = new ActionFileOpenSpec();

    private ActionFileSaveScript fActionFileSaveScript = new ActionFileSaveScript();

    private ActionFileSaveProtocol fActionFileSaveProtocol = new ActionFileSaveProtocol();

    private ActionFilePrinterSetup fActionFilePrinterSetup = new ActionFilePrinterSetup();

    private ActionFilePrint fActionFilePrint = new ActionFilePrint();

    private ActionFilePrintView fActionFilePrintView = new ActionFilePrintView();

    private ActionFileExit fActionFileExit = new ActionFileExit();

    private ActionEditUndo fActionEditUndo = new ActionEditUndo();
    private ActionEditRedo fActionEditRedo = new ActionEditRedo();

    private ActionStateCreateObject fActionStateCreateObject = new ActionStateCreateObject();

    private ActionStateEvalOCL fActionStateEvalOCL = new ActionStateEvalOCL();

    private ActionStateCheckStructure fActionStateCheckStructure = new ActionStateCheckStructure();

    private ActionStateReset fActionStateReset = new ActionStateReset();

    private ActionViewCreateObjectCount fActionViewCreateObjectCount = new ActionViewCreateObjectCount();

    private ActionViewCreateLinkCount fActionViewCreateLinkCount = new ActionViewCreateLinkCount();

    private ActionViewCreateClassDiagram fActionViewCreateClassDiagram = new ActionViewCreateClassDiagram();

    private ActionViewCreateObjectDiagram fActionViewCreateObjectDiagram = new ActionViewCreateObjectDiagram();

    private ActionViewCreateClassInvariant fActionViewCreateClassInvariant = new ActionViewCreateClassInvariant();

    // private ActionViewCreateStateTreeView fActionViewCreateStateTree =
    // new ActionViewCreateStateTreeView();
    private ActionViewCreateStateEvolution fActionViewCreateStateEvolution = new ActionViewCreateStateEvolution();

    private ActionViewCreateObjectProperties fActionViewCreateObjectProperties = new ActionViewCreateObjectProperties();

    private ActionViewCreateClassExtent fActionViewCreateClassExtent = new ActionViewCreateClassExtent();

    private ActionViewCreateSequenceDiagram fActionViewCreateSequenceDiagram = new ActionViewCreateSequenceDiagram();

    private ActionViewCreateCallStack fActionViewCreateCallStack = new ActionViewCreateCallStack();

    private ActionViewCreateCommandList fActionViewCreateCommandList = new ActionViewCreateCommandList();

    private ActionViewAssociationInfo fActionViewAssociationInfo = new ActionViewAssociationInfo();
    
    private ActionViewTile fActionViewTile = new ActionViewTile();

    private ActionViewCloseAll fActionViewCloseAll = new ActionViewCloseAll();

    private ActionHelpAbout fActionHelpAbout = new ActionHelpAbout();

    /**
     * Opens and compiles a specification file.
     */
    private class ActionFileOpenSpec extends AbstractAction {
        private JFileChooser fChooser;

        ActionFileOpenSpec() {
            super("Open specification...", new ImageIcon(Options.iconDir
                    + "Open.gif"));
        }

        public void actionPerformed(ActionEvent e) {
            String path;
            // reuse chooser if possible
            if (fChooser == null) {
                path = Options.getLastDirectory();
                fChooser = new JFileChooser(path);
                ExtFileFilter filter = new ExtFileFilter("use",
                        "USE specifications");
                fChooser.addChoosableFileFilter(filter);
                fChooser.setDialogTitle("Open specification");
            }
            int returnVal = fChooser.showOpenDialog(MainWindow.this);
            if (returnVal != JFileChooser.APPROVE_OPTION)
                return;

            path = fChooser.getCurrentDirectory().toString();
            Options.setLastDirectory(path);
            File f = new File(path, fChooser.getSelectedFile().getName());

            fLogPanel.clear();
            showLogPanel();
            compile(f);
        }

        private void compile(final File f) {
            fLogWriter
                    .println("compiling specification " + f.getName() + "...");
            MModel model = null;
            MSystem system = null;
            FileInputStream iStream = null;
            try {
                iStream = new FileInputStream(f);
                model = USECompiler.compileSpecification(iStream, f.getName(),
                        fLogWriter, new ModelFactory());
                fLogWriter.println("done.");
                if (model != null) {
                    fLogWriter.println(model.getStats());
                    // create system
                    system = new MSystem(model);
                }
            } catch (FileNotFoundException ex) {
                Log.error("File `" + f.getName() + "' not found.");
            } finally {
                if (iStream != null)
                    try {
                        iStream.close();
                    } catch (IOException ex) {
                        // ignored
                    }
            }
            // set new system (may be null if compilation failed)
            final MSystem system2 = system; // need final variable for Runnable
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    fSession.setSystem(system2);
                }
            });
        }
    }

    /**
     * Saves commands executed so far to a script file.
     */
    private class ActionFileSaveScript extends AbstractAction {
        private JFileChooser fChooser;

        ActionFileSaveScript() {
			super("Save script (.soil)...", new ImageIcon(Options.iconDir
					+ "Save.gif"));
			
			setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            String path;
            // reuse chooser if possible
            if (fChooser == null) {
                path = System.getProperty("user.dir");
                fChooser = new JFileChooser(path);
                ExtFileFilter filter = new ExtFileFilter("soil", "soil scripts");
                fChooser.setFileFilter(filter);
                fChooser.setDialogTitle("Save script");
            }
            int returnVal = fChooser.showSaveDialog(MainWindow.this);
            if (returnVal != JFileChooser.APPROVE_OPTION)
                return;

            path = fChooser.getCurrentDirectory().toString();
            String filename = fChooser.getSelectedFile().getName();
			if (!filename.endsWith(".soil"))
				filename += ".soil";
            File f = new File(path, filename);
            Log.verbose("File " + f);

            if (f.exists()) {
                int n = JOptionPane.showConfirmDialog(MainWindow.this,
                        "Overwrite existing file " + f + "?", "Please confirm",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (n != JOptionPane.YES_OPTION)
                    return;
            }

            // write script file
            PrintWriter out = null;
            try {
                out = new PrintWriter(new BufferedWriter(new FileWriter(f)));
                out.println("-- Script generated by USE "
                        + Options.RELEASE_VERSION);
                out.println();
                //fSession.system().writeUSEcmds(out);
                fSession.system().writeSoilStatements(out);
                fLogWriter.println("Wrote script " + f);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(MainWindow.this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (out != null)
                    out.close();
            }
        }
    }

    private class ActionFileSaveProtocol extends AbstractAction {
        private JFileChooser fChooser;

        ActionFileSaveProtocol() {
            super("Save protocol...", new ImageIcon(Options.iconDir
                    + "Save.gif"));
        }

        public void actionPerformed(ActionEvent e) {
            String path;
            // reuse chooser if possible
            if (fChooser == null) {
                path = System.getProperty("user.dir");
                fChooser = new JFileChooser(path);
                ExtFileFilter filter = new ExtFileFilter("txt",
                        "Textfiles");
                fChooser.setFileFilter(filter);
                fChooser.setDialogTitle("Save protocol");
            }
            int returnVal = fChooser.showSaveDialog(MainWindow.this);
            if (returnVal != JFileChooser.APPROVE_OPTION)
                return;

            path = fChooser.getCurrentDirectory().toString();
            String filename = fChooser.getSelectedFile().getName();
            
			if (!(filename.lastIndexOf(".") > filename.lastIndexOf("\\")))
				filename += ".txt";
			
            File f = new File(path, filename);
            
            if (f.exists()) {
                int n = JOptionPane.showConfirmDialog(MainWindow.this,
                        "Overwrite existing file " + f + "?", "Please confirm",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (n != JOptionPane.YES_OPTION)
                    return;
            }

            try {
                FileOutputStream fOut = new FileOutputStream(f);
                USEWriter.getInstance().writeProtocolFile(fOut);
                fOut.flush();
                fOut.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

    }

    /**
     * Setup printer.
     */
    private class ActionFilePrinterSetup extends AbstractAction {
        ActionFilePrinterSetup() {
            super("Printer Setup...");
        }

        public void actionPerformed(ActionEvent e) {
            PrinterJob job = PrinterJob.getPrinterJob();
            // initialize page format if necessary
            pageFormat();
            // show dialog
            fPageFormat = job.pageDialog(fPageFormat);
        }
    }

    /**
     * Print view.
     */
    private class ActionFilePrint extends AbstractAction {
        ActionFilePrint() {
            super("Print diagram...", new ImageIcon(Options.iconDir
                    + "Print.gif"));
            this.setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            ViewFrame vf = null;

            // jdk1.3: vf = (ViewFrame) fDesk.getSelectedFrame();

            JInternalFrame[] frames = fDesk.getAllFrames();
            for (int i = 0; i < frames.length; i++)
                if (frames[i].isSelected())
                    vf = (ViewFrame) frames[i];

            if (vf != null && vf.isPrintable())
                vf.print(pageFormat());
        }
    }

    /**
     * Print visible view of diagram.
     */
    private class ActionFilePrintView extends AbstractAction {
        ActionFilePrintView() {
            super("Print View...", new ImageIcon(Options.iconDir + "Print.gif"));
            this.setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            ViewFrame vf = null;

            // jdk1.3: vf = (ViewFrame) fDesk.getSelectedFrame();

            JInternalFrame[] frames = fDesk.getAllFrames();
            for (int i = 0; i < frames.length; i++)
                if (frames[i].isSelected())
                    vf = (ViewFrame) frames[i];

            if (vf != null && vf.isPrintable()) {
                View view = vf.getView();
                ((SequenceDiagramView) view).printOnlyView(pageFormat());
            }
        }
    }

    /**
     * Exit application.
     */
    private class ActionFileExit extends AbstractAction {
        ActionFileExit() {
            super("Exit");
        }

        public void actionPerformed(ActionEvent e) {
            close();
        }
    }

    /**
     * Undoes the last state manipulation command.
     */
    class ActionEditUndo extends AbstractAction {
        ActionEditUndo() {
            super("Undo", new ImageIcon(Options.iconDir + "Undo.gif"));
            this.setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
        	
        	MSystem system = fSession.system();
        	
            try {
                /*fSession.system().undoCmd();
                String name = fSession.system().nextUndoableCmdName();
                if (name != null)
                    enableUndo(name);
                else
                    disableUndo();*/
            	
            	system.undoLastStatement();
            	
            	setUndoRedoButtons();
            	
            } catch (MSystemException ex) {
                JOptionPane.showMessageDialog(
                		MainWindow.this, 
                		ex.getMessage(),
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    class ActionEditRedo extends AbstractAction {
    	
    	ActionEditRedo() {
    		super("Redo", new ImageIcon(Options.iconDir + "Redo.gif"));
            this.setEnabled(false);
    	}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			MSystem system = fSession.system();
			
			MStatement nextToRedo = system.nextToRedo();
			if ((nextToRedo instanceof MEnterOperationStatement) ||
					(nextToRedo instanceof MExitOperationStatement)) {
				
				JOptionPane.showMessageDialog(
            			MainWindow.this, 
            			"openter/opexit can only be redone in the shell",
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
				
				return;
			}
			
			try {    	
            	system.redoStatement();
            	
            	setUndoRedoButtons();
            	
            } catch (MSystemException ex) {
            	JOptionPane.showMessageDialog(
            			MainWindow.this, 
            			ex.getMessage(),
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
			}
		}
    }

    /**
     * Opens a dialog for creating objects.
     */
    private class ActionStateCreateObject extends AbstractAction {
        ActionStateCreateObject() {
            super("Create object...");
        }

        public void actionPerformed(ActionEvent e) {
            CreateObjectDialog dlg = new CreateObjectDialog(fSession.system(),
                    MainWindow.this);
            dlg.setVisible(true);
        }
    }

    /**
     * Opens a dialog for evaluating OCL expressions.
     */
    private class ActionStateEvalOCL extends AbstractAction {
        ActionStateEvalOCL() {
            super("Evaluate OCL expression...", new ImageIcon(Options.iconDir
                    + "OCL.gif"));
        }

        public void actionPerformed(ActionEvent e) {
            EvalOCLDialog dlg = new EvalOCLDialog(fSession.system(),
                    MainWindow.this);
            dlg.setVisible(true);
        }
    }

    /**
     * Checks structure of system state.
     */
    private class ActionStateCheckStructure extends AbstractAction {
        ActionStateCheckStructure() {
            super("Check structure now");
        }

        public void actionPerformed(ActionEvent e) {
            checkStructure();
        }
    }

    /**
     * Resets the system to its initial empty state.
     */
    private class ActionStateReset extends AbstractAction {
        ActionStateReset() {
            super("Reset");
        }

        public void actionPerformed(ActionEvent e) {
            int n = JOptionPane
                    .showConfirmDialog(
                            MainWindow.this,
                            "Reset system to its initial state and delete all objects and links?",
                            "Please confirm", JOptionPane.YES_NO_CANCEL_OPTION);
            if (n == JOptionPane.YES_OPTION)
                fSession.reset();
        }
    }

    /**
     * Creates a new object count view.
     */
    private class ActionViewCreateObjectCount extends AbstractAction {
        ActionViewCreateObjectCount() {
            super("Object count", new ImageIcon(Options.iconDir
                    + "ObjectCountView.gif"));
        }

        public void actionPerformed(ActionEvent e) {
            ObjectCountView ov = new ObjectCountView(fSession.system());
            ViewFrame f = new ViewFrame("Object count", ov,
                    "ObjectCountView.gif");
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            c.add(new JScrollPane(ov), BorderLayout.CENTER);
            addNewViewFrame(f);
        }
    }

    /**
     * Creates a new link count view.
     */
    private class ActionViewCreateLinkCount extends AbstractAction {
        ActionViewCreateLinkCount() {
            super("Link count", new ImageIcon(Options.iconDir
                    + "LinkCountView.gif"));
        }

        public void actionPerformed(ActionEvent e) {
            LinkCountView lv = new LinkCountView(fSession.system());
            ViewFrame f = new ViewFrame("Link count", lv, "LinkCountView.gif");
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            c.add(new JScrollPane(lv), BorderLayout.CENTER);
            addNewViewFrame(f);
        }
    }

    /**
     * Creates a new class diagram view.
     */
    private class ActionViewCreateClassDiagram extends AbstractAction {
        ActionViewCreateClassDiagram() {
            super("Class diagram", new ImageIcon(Options.iconDir
                    + "ClassDiagram.gif"));
        }

        public void actionPerformed(ActionEvent e) {
            ClassDiagramView cdv = new ClassDiagramView(MainWindow.this,
                    fSession.system());
            ViewFrame f = new ViewFrame("Class diagram", cdv,
                    "ClassDiagram.gif");
            // give some help information
            f.addInternalFrameListener(new InternalFrameAdapter() {
                public void internalFrameActivated(InternalFrameEvent ev) {
                    fStatusBar.showTmpMessage("Use left mouse button to move "
                            + "classes, right button for popup menu.");
                }

                public void internalFrameDeactivated(InternalFrameEvent ev) {
                    fStatusBar.clearMessage();
                }

				@Override
				public void internalFrameClosed(InternalFrameEvent e) {
					super.internalFrameClosed(e);
					classDiagrams.remove((ClassDiagramView)((ViewFrame)e.getSource()).getView());
				}
            });
            
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            c.add(cdv, BorderLayout.CENTER);
            addNewViewFrame(f);
            classDiagrams.add(cdv);
        }
    }

    /**
     * Creates a new object diagram view.
     */
    private class ActionViewCreateObjectDiagram extends AbstractAction {
        ActionViewCreateObjectDiagram() {
            super("Object diagram", new ImageIcon(Options.iconDir
                    + "ObjectDiagram.gif"));
        }

        public void actionPerformed(ActionEvent e) {
            NewObjectDiagramView odv = new NewObjectDiagramView(MainWindow.this, fSession.system());
            ViewFrame f = new ViewFrame("Object diagram", 
            					odv, "ObjectDiagram.gif");
            
            // give some help information
            f.addInternalFrameListener(new InternalFrameAdapter() {
                public void internalFrameActivated(InternalFrameEvent ev) {
                    fStatusBar.showTmpMessage("Use left mouse button to move "
                            + "objects, right button for popup menu.");
                }

                public void internalFrameDeactivated(InternalFrameEvent ev) {
                    fStatusBar.clearMessage();
                }
                
                @Override
				public void internalFrameClosed(InternalFrameEvent e) {
					super.internalFrameClosed(e);
					objectDiagrams.remove((NewObjectDiagramView)((ViewFrame)e.getSource()).getView());
				}
            });
            
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            c.add(odv, BorderLayout.CENTER);
            addNewViewFrame(f);
            objectDiagrams.add(odv);
        }
    }

    /**
     * Creates a new class invariant view.
     */
    private class ActionViewCreateClassInvariant extends AbstractAction {
        ActionViewCreateClassInvariant() {
            super("Class invariants", new ImageIcon(Options.iconDir
                    + "InvariantView.gif"));
        }

        public void actionPerformed(ActionEvent e) {
            ClassInvariantView civ = new ClassInvariantView(MainWindow.this,
                    fSession.system());
            ViewFrame f = new ViewFrame("Class invariants", civ,
                    "InvariantView.gif");
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            c.add(civ, BorderLayout.CENTER);
            addNewViewFrame(f);
        }
    }

    // /**
    // * Creates a new state tree view.
    // */
    // private class ActionViewCreateStateTreeView extends AbstractAction {
    // ActionViewCreateStateTreeView() {
    // super("State tree",
    // new ImageIcon(Options.iconDir + "InvariantView.gif"));
    // }
    //
    // public void actionPerformed(ActionEvent e) {
    // StateTreeView stv = new StateTreeView(fSession.system());
    // ViewFrame f = new ViewFrame("State tree", stv, "InvariantView.gif");
    // JComponent c = (JComponent) f.getContentPane();
    // c.setLayout(new BorderLayout());
    // c.add(stv, BorderLayout.CENTER);
    // addNewViewFrame(f);
    // }
    // }

    /**
     * Creates a new state evolution view.
     */
    private class ActionViewCreateStateEvolution extends AbstractAction {
        ActionViewCreateStateEvolution() {
            super("State evolution", new ImageIcon(Options.iconDir
                    + "LineChartView.gif"));
        }

        public void actionPerformed(ActionEvent e) {
            StateEvolutionView sev = new StateEvolutionView(fSession.system());
            ViewFrame f = new ViewFrame("State evolution", sev,
                    "LineChartView.gif");
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            c.add(sev, BorderLayout.CENTER);
            addNewViewFrame(f);
        }
    }

    /**
     * Creates a new object properties view.
     */
    private class ActionViewCreateObjectProperties extends AbstractAction {
        ActionViewCreateObjectProperties() {
            super("Object properties", new ImageIcon(Options.iconDir
                    + "ObjectProperties.gif"));
        }

        public void actionPerformed(ActionEvent e) {
            showObjectPropertiesView();
        }
    }

    /**
     * Creates a new class extent view.
     */
    private class ActionViewCreateClassExtent extends AbstractAction {
        ActionViewCreateClassExtent() {
            super("Class extent", new ImageIcon(Options.iconDir
                    + "ClassExtentView.gif"));
        }

        public void actionPerformed(ActionEvent e) {
            ClassExtentView cev = new ClassExtentView(MainWindow.this, fSession
                    .system());
            ViewFrame f = new ViewFrame("Class extent", cev,
                    "ClassExtentView.gif");
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            c.add(cev, BorderLayout.CENTER);
            addNewViewFrame(f);
        }
    }

    /**
     * Creates a new sequence diagram view.
     */
    private class ActionViewCreateSequenceDiagram extends AbstractAction {
        ActionViewCreateSequenceDiagram() {
            super("Sequence diagram", new ImageIcon(Options.iconDir
                    + "SequenceDiagram.gif"));
        }

        public void actionPerformed(ActionEvent e) {
            SequenceDiagramView sv = createSeqDiagView();
            ViewFrame f = new ViewFrame("Sequence diagram", sv,
                    "SequenceDiagram.gif");
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            c.add(new SDScrollPane(sv), BorderLayout.CENTER);
            addNewViewFrame(f);
        }
    }

    public SequenceDiagramView createSeqDiagView() {
        return new SequenceDiagramView(fSession.system(), this);
    }

    /**
     * Creates a new call stack view.
     */
    private class ActionViewCreateCallStack extends AbstractAction {
        ActionViewCreateCallStack() {
            super("Call stack",
                    new ImageIcon(Options.iconDir + "CallStack.gif"));
        }

        public void actionPerformed(ActionEvent e) {
            CallStackView csv = new CallStackView(fSession.system());
            ViewFrame f = new ViewFrame("Call stack", csv, "CallStack.gif");
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            c.add(new JScrollPane(csv), BorderLayout.CENTER);
            addNewViewFrame(f);
        }
    }

    /**
     * Creates a new command list view.
     */
    private class ActionViewCreateCommandList extends AbstractAction {
        ActionViewCreateCommandList() {
            super("Command list",
                    new ImageIcon(Options.iconDir + "CmdList.gif"));
        }

        public void actionPerformed(ActionEvent e) {
            CommandView cv = new CommandView(fSession.system());
            ViewFrame f = new ViewFrame("Command list", cv, "CmdList.gif");
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            c.add(new JScrollPane(cv), BorderLayout.CENTER);
            addNewViewFrame(f);
        }
    }

    /**
     * Creates a new association info view.
     */
    private class ActionViewAssociationInfo extends AbstractAction {
    	ActionViewAssociationInfo() {
            super("Association ends informations", new ImageIcon(Options.iconDir
                    + "Association.gif"));
        }

        public void actionPerformed(ActionEvent e) {
        	AssociationEndsInfo v = new AssociationEndsInfo(MainWindow.this, fSession.system());
            ViewFrame f = new ViewFrame("Association ends info", v, "Association.gif");
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            c.add(new JScrollPane(v), BorderLayout.CENTER);
            addNewViewFrame(f);
        }
    }

    
    /**
     * Close all internal frames.
     */
    private class ActionViewCloseAll extends AbstractAction {
        ActionViewCloseAll() {
            super("Close all");
        }

        public void actionPerformed(ActionEvent ev) {
            closeAllViews();
        }
    }

    /**
     * Tile all internal frames.
     */
    private class ActionViewTile extends AbstractAction {
        ActionViewTile() {
            super("Tile");
        }

        public void actionPerformed(ActionEvent ev) {
            // How many frames do we have?
            JInternalFrame[] allframes = fDesk.getAllFrames();
            int count = allframes.length;
            if (count == 0)
                return;

            // Determine the necessary grid size
            int sqrt = (int) Math.sqrt(count);
            int rows = sqrt;
            int cols = sqrt;
            if (rows * cols < count) {
                cols++;
                if (rows * cols < count) {
                    rows++;
                }
            }

            // Define some initial values for size & location
            Dimension size = fDesk.getSize();

            int w = size.width / cols;
            int h = size.height / rows;
            int x = 0;
            int y = 0;

            // Iterate over the frames, deiconifying any iconified frames and
            // then
            // relocating & resizing each
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols && ((i * cols) + j < count); j++) {
                    JInternalFrame f = allframes[(i * cols) + j];

                    if (f.isIcon() && !f.isClosed()) {
                        try {
                            f.setIcon(false);
                        } catch (PropertyVetoException ex) {
                            // ignored
                        }
                    }
                    fDesk.getDesktopManager().resizeFrame(f, x, y, w, h);
                    x += w;
                }
                y += h; // start the next row
                x = 0;
            }
        }
    }

    /**
     * Help about.
     */
    private class ActionHelpAbout extends AbstractAction {
        ActionHelpAbout() {
            super("About...");
        }

        public void actionPerformed(ActionEvent e) {
            AboutDialog dlg = new AboutDialog(MainWindow.this);
            dlg.setVisible(true);
        }
    }

    public ObjectPropertiesView showObjectPropertiesView() {
        ObjectPropertiesView opv = new ObjectPropertiesView(MainWindow.this,
                fSession.system());
        ViewFrame f = new ViewFrame("Object properties", opv,
                "ObjectProperties.gif");
        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(opv, BorderLayout.CENTER);
        addNewViewFrame(f);
        return opv;
    }

    /**
     * Creates some initial views and tiles them.
     */
    public static MainWindow create(Session session) {

		return create(session, null);
	}

	public static MainWindow create(Session session, IRuntime pluginRuntime) {
		final MainWindow win = new MainWindow(session, pluginRuntime);

        win.pack();
        win.setVisible(true);
        return win;
    }
    
    //von hier jj
    public SelectionClassView showClassSelectionClassView (Set<MClass> selectedClasses, 
    								ClassDiagram classDiagram, DiagramInputHandling mouseHandling, 
    								Map<MClass, ClassNode> fClassToNodeMap, Selection fNodeSelection) { // jj object selection class 
    	
    	SelectionClassView opv = new SelectionClassView(MainWindow.this,
                fSession.system(), selectedClasses, classDiagram, mouseHandling, fClassToNodeMap, fNodeSelection);
    	mouseHandling.setSelectionClassView(opv);
        ViewFrame f = new ViewFrame("Selection classes", opv,
                "ObjectProperties.gif");
        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(opv, BorderLayout.CENTER);
        addNewViewFrame(f);
        f.setSize(580,230);
        return opv;
}
    
    /**
     * Creates a new assocation path length view.
     */
    public SelectedAssociationPathView showSelectedAssociationPathView(ClassDiagram diagram, Set<MClass> selectedClasses, Set<AssociationName> anames) { // jj object selection class 
    	SelectedAssociationPathView opv = new SelectedAssociationPathView(MainWindow.this, fSession.system(), diagram,
    			selectedClasses, anames);
        ViewFrame f = new ViewFrame("Selection association path length", opv,
                "ObjectProperties.gif");
        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(opv, BorderLayout.CENTER);
        addNewViewFrame(f);
        f.setSize(450,200);
        return opv;
    }
    
    //selection class path view
    public SelectedClassPathView showSelectedClassPathView(ClassDiagram diagram, Set<MClass> selectedClasses) { // jj object selection class 
    	SelectedClassPathView opv = new SelectedClassPathView(MainWindow.this, fSession.system(),
    									diagram, selectedClasses);
        ViewFrame f = new ViewFrame("Selection classes path length", opv,
                "ObjectProperties.gif");
        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(opv, BorderLayout.CENTER);
        addNewViewFrame(f);
        f.setSize(450,200);
        return opv;
    }
    
    //  selection class path view
    public SelectedLinkPathView showSelectedLinkPathView(NewObjectDiagram diagram, Set<MObject> selectedClasses, Set<AssociationName> anames) { // jj object selection class 
    	SelectedLinkPathView opv = 
    		new SelectedLinkPathView(MainWindow.this, fSession.system(), diagram, selectedClasses, anames);
        
    	ViewFrame f = new ViewFrame("Selection by path length", opv,
                "ObjectProperties.gif");
        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(opv, BorderLayout.CENTER);
        addNewViewFrame(f);
        f.setSize(450,200);
        return opv;
    }
    
    //selection object path view
    public SelectedObjectPathView showSelectedObjectPathView(NewObjectDiagram diagram, Set<MObject> selectedObjects) { // jj object selection class 
    	SelectedObjectPathView opv = new SelectedObjectPathView(MainWindow.this, fSession.system(), diagram, selectedObjects);
        ViewFrame f = new ViewFrame("Selection by path length", opv,
                "ObjectProperties.gif");
        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(opv, BorderLayout.CENTER);
        addNewViewFrame(f);
        f.setSize(450,200);
        return opv;
    }
    
    public SelectionObjectView showSelectionObjectView(NewObjectDiagram diagram) { // jj object selection class 
    	SelectionObjectView opv = new SelectionObjectView(MainWindow.this, fSession.system(), diagram);
        ViewFrame f = new ViewFrame("Selection objects", opv,
                "ObjectProperties.gif");
        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(opv, BorderLayout.CENTER);
        addNewViewFrame(f);
        f.setSize(530,230);
        return opv;
    }
    
    public SelectionOCLView showSelectionOCLView(NewObjectDiagram diagram) { // jj ocl selection class 
    	SelectionOCLView opv = new SelectionOCLView(MainWindow.this, fSession.system(), diagram);
    	ViewFrame f = new ViewFrame("Selection by OCL expression", opv,
                "ObjectProperties.gif");
        JComponent c = (JComponent) f.getContentPane();
       
        c.setLayout(new BorderLayout());
        c.add(opv, BorderLayout.CENTER);
        addNewViewFrame(f);
        f.setSize(370,250);
        return opv;
    }
}
