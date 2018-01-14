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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.beans.PropertyVetoException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Icon;
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
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.tzi.use.config.Options;
import org.tzi.use.config.RecentItems;
import org.tzi.use.config.RecentItems.RecentItemsObserver;
import org.tzi.use.gui.main.runtime.IPluginActionExtensionPoint;
import org.tzi.use.gui.util.ExtFileFilter;
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
import org.tzi.use.gui.views.PrintableView;
import org.tzi.use.gui.views.StateEvolutionView;
import org.tzi.use.gui.views.View;
import org.tzi.use.gui.views.diagrams.behavior.communicationdiagram.CommunicationDiagramView;
import org.tzi.use.gui.views.diagrams.behavior.sequencediagram.SDScrollPane;
import org.tzi.use.gui.views.diagrams.behavior.sequencediagram.SequenceDiagramView;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagramView;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagramView;
import org.tzi.use.gui.views.diagrams.statemachine.StateMachineDiagramView;
import org.tzi.use.main.ChangeEvent;
import org.tzi.use.main.ChangeListener;
import org.tzi.use.main.Session;
import org.tzi.use.main.Session.EvaluatedStatement;
import org.tzi.use.main.runtime.IRuntime;
import org.tzi.use.main.shell.Shell;
import org.tzi.use.parser.use.USECompiler;
import org.tzi.use.runtime.gui.impl.PluginActionProxy;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.events.StatementExecutedEvent;
import org.tzi.use.uml.sys.events.tags.SystemStateChangedEvent;
import org.tzi.use.uml.sys.events.tags.SystemStructureChangedEvent;
import org.tzi.use.uml.sys.soil.MEnterOperationStatement;
import org.tzi.use.uml.sys.soil.MExitOperationStatement;
import org.tzi.use.uml.sys.soil.MNewObjectStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.Log;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.USEWriter;

import com.google.common.eventbus.Subscribe;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * The main application window of USE.
 * 
 *  
 * @author Mark Richters
 * @author Lars Hamann
 * @author Frank Hilken
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {
    private final Session fSession;

    private final StatusBar fStatusBar;

    private final LogPanel fLogPanel;

    private final PrintWriter fLogWriter;

    private final JDesktopPane fDesk;

    private final JSplitPane fTopSplitPane;

    private final ModelBrowser fModelBrowser;

    private final JMenuItem fMenuItemEditUndo;
    private final JMenuItem fMenuItemEditRedo;

	private final JToolBar fToolBar;
	private final JMenuBar fMenuBar;
	private final JMenu statemachineMenu;
	
    private final JButton fBtnEditUndo;
    private final JButton fBtnEditRedo;

    private final JCheckBoxMenuItem fCbMenuItemCheckStructure;

    private final List<ClassDiagramView> classDiagrams = new ArrayList<ClassDiagramView>();
    private final List<NewObjectDiagramView> objectDiagrams = new ArrayList<NewObjectDiagramView>();
    private final List<CommunicationDiagramView> communicationDiagrams = new ArrayList<CommunicationDiagramView>();
    
    private static final String DEFAULT_UNDO_TEXT = "Undo last statement";
    private static final String DEFAULT_REDO_TEXT = "Redo last undone statement";

    private static final String STATE_EVAL_OCL = "Evaluate OCL expression";

    private PageFormat fPageFormat;

    private static MainWindow fInstance; // global instance of main window

    private JMenu recentFilesMenu;
    
    private JButton addToToolBar(JToolBar toolBar, AbstractAction action, String toolTip) {
        JButton tb = new JButton(action);
        addToToolBar(toolBar, tb, toolTip);
        return tb;
    }
    
    private AbstractButton addToToolBar(JToolBar toolBar, AbstractButton button, String toolTip) {
    	toolBar.add(button);
    	button.setMaximumSize(new Dimension(30, 30));
    	button.setToolTipText(toolTip);
    	button.setText("");
    	return button;
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

        // create toolbar
		fToolBar = new JToolBar();
		fToolBar.setFloatable(false);
		
		fToolBar.addSeparator();
		
		addToToolBar(fToolBar, fActionFileOpenSpec,  "Open specification");
		addToToolBar(fToolBar, fActionFileReload,  "Reload current specification");
		
		fActionFileReload.setEnabled(!Options.getRecentFiles().isEmpty());
		
		addToToolBar(fToolBar, fActionFilePrint,     "Print diagram");
		addToToolBar(fToolBar, fActionFilePrintView, "Print view");
		addToToolBar(fToolBar, fActionFileExportView, "Export content of view as PDF");
		
		fToolBar.addSeparator();

		fBtnEditUndo = addToToolBar(fToolBar, fActionEditUndo, DEFAULT_UNDO_TEXT);
		fBtnEditRedo = addToToolBar(fToolBar, fActionEditRedo, DEFAULT_REDO_TEXT);
		
		fToolBar.addSeparator();
		
		addToToolBar(fToolBar, fActionStateEvalOCL, STATE_EVAL_OCL);
		
		fToolBar.addSeparator();

		addToToolBar(fToolBar, fActionViewCreateClassDiagram,
                "Create class diagram view");
		addToToolBar(fToolBar, fStateMachineDropdown,
				"Create statemachine diagram view");
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
		addToToolBar(fToolBar, fActionViewCreateCommunicationDiagram,
	                "Create communication diagram view");
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
                .getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        mi.setMnemonic('O');

        {
			recentFilesMenu = new JMenu("Open recent specification");
			recentFilesMenu.setIcon(getIcon("document-open.png"));
			
        	menu.add(recentFilesMenu);
        	
        	setRecentfiles();
        	
        	Options.getRecentFiles().addObserver(new RecentItemsObserver() {
        		@Override
				public void onRecentItemChange(RecentItems src) {
					setRecentfiles();
					fActionFileReload.setEnabled(!Options.getRecentFiles().isEmpty());
				}
			});
        }
        
        mi = menu.add(fActionFileSaveScript);
        mi.setMnemonic('S');

        mi = menu.add(fActionFileSaveProtocol);

        menu.addSeparator();
        mi = menu.add(fActionFilePrinterSetup);
        mi = menu.add(fActionFilePrint);
        mi = menu.add(fActionFilePrintView);
        mi = menu.add(fActionFileExportViewAll);
        
        menu.addSeparator();
        mi = menu.add(fActionFileExit);
        mi.setMnemonic('x');
        mi.setAccelerator(KeyStroke
                .getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));

        // `Edit' submenu
        menu = new JMenu("Edit");
        menu.setMnemonic('E');
		fMenuBar.add(menu);

        fMenuItemEditUndo = menu.add(fActionEditUndo);
        fMenuItemEditUndo.setMnemonic('U');
        fMenuItemEditUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
        		InputEvent.CTRL_DOWN_MASK));
        
        fMenuItemEditRedo = menu.add(fActionEditRedo);
        fMenuItemEditRedo.setMnemonic('R');
        fMenuItemEditRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
        		InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        
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

        menu.add(new JSeparator());
        JCheckBoxMenuItem fCbMenuItemCheckValidTransitions = new JCheckBoxMenuItem(
                "Check state machine transitions");
        fCbMenuItemCheckValidTransitions.setMnemonic('t');
        fCbMenuItemCheckValidTransitions.setSelected(Options.getCheckTransitions());
        fCbMenuItemCheckValidTransitions.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				Options.setCheckTransitions(((JCheckBoxMenuItem)e.getSource()).isSelected());
			}
		});
        menu.add(fCbMenuItemCheckValidTransitions);
        
        
        JCheckBoxMenuItem fCbMenuItemCheckStateInvariants = new JCheckBoxMenuItem(
                "Check state invariants after every change");
        fCbMenuItemCheckStateInvariants.setMnemonic('i');
        fCbMenuItemCheckStateInvariants.setSelected(Options.getCheckStateInvariants());
        fCbMenuItemCheckStateInvariants.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				Options.setCheckStateInvariants(((JCheckBoxMenuItem)e.getSource()).isSelected());
			}
		});
        menu.add(fCbMenuItemCheckStateInvariants);
        
        menu.add(fActionDetermineStates);
        menu.add(fActionCheckStateInvariants);
        
        menu.add(new JSeparator());
        mi = menu.add(fActionStateReset);
        mi.setMnemonic('R');

        // `View' submenu
        menu = new JMenu("View");
        menu.setMnemonic('V');
		fMenuBar.add(menu);

        JMenu submenu = new JMenu("Create View");
        submenu.setMnemonic('C');
        menu.add(submenu);
        mi = submenu.add(fActionViewCreateClassDiagram);
        mi.setMnemonic('V');
        
        statemachineMenu = new JMenu("State machine diagram");
        statemachineMenu.setIcon(getIcon("Diagram.gif"));
        createStateMachineMenuEntries(statemachineMenu);
        submenu.add(statemachineMenu);
        
        mi = submenu.add(fActionViewCreateObjectDiagram);
        mi.setMnemonic('d');
        mi = submenu.add(fActionViewCreateClassInvariant);
        mi.setMnemonic('C');
        mi = submenu.add(fActionViewCreateObjectCount);
        mi.setMnemonic('O');
        mi = submenu.add(fActionViewCreateLinkCount);
        mi.setMnemonic('L');
        mi = submenu.add(fActionViewCreateStateEvolution);
        mi.setMnemonic('S');
        mi = submenu.add(fActionViewCreateObjectProperties);
        mi.setMnemonic('p');
        mi = submenu.add(fActionViewCreateClassExtent);
        mi.setMnemonic('e');
        mi = submenu.add(fActionViewCreateSequenceDiagram);
        mi.setMnemonic('q');
        mi = submenu.add(fActionViewCreateCommunicationDiagram);
        mi.setMnemonic('m');
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
        fTopSplitPane.setResizeWeight(.8d);

        // Layout and set the content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(800, 550));
		contentPane.add(fToolBar, BorderLayout.NORTH);
        contentPane.add(fTopSplitPane, BorderLayout.CENTER);
        contentPane.add(fStatusBar, BorderLayout.SOUTH);
        setContentPane(contentPane);

        addWindowListener(new WindowAdapter() {
            @Override
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
        
     // the session may be changed from the shell
        fSession.addChangeListener(new ChangeListener() {
            @Override
			public void stateChanged(ChangeEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
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
    }

	private void setRecentfiles() {
		recentFilesMenu.removeAll();
		
		for (Path recent : Options.getRecentFiles("use")) {
			recentFilesMenu.add(new ActionFileOpenSpecRecent(recent));
		}
	}

    /**
	 * @return the fPluginRuntime
	 */
	public static IRuntime getfPluginRuntime() {
		return fPluginRuntime;
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
    
    /**
     * A list of all displayed communication diagrams 
     * @return
     */
    public List<CommunicationDiagramView> getCommunicationDiagrams() {
    	return this.communicationDiagrams;
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
        boolean ok = fSession.system().state().checkStructure(fLogWriter);
        
        fLogWriter.println("checking structure, "
                + ((ok) ? "ok." : "found errors."));
        fLogWriter.flush();
    }

    private void createStateMachineMenuEntries(Container menu){
    	int elems = 0;
    	if(fSession.hasSystem()){
    		for (final MClass cls : fSession.system().model().classes()) {
    			for (final MStateMachine sm : cls.getOwnedProtocolStateMachines()) {
    				JMenuItem item = new JMenuItem(cls.name() + "::" + sm.name());
    				item.addActionListener(new ActionListener() {
    					@Override
    					public void actionPerformed(ActionEvent e) {
    						showStateMachineView(sm);
    					}
    				});
    				menu.add(item);
    				++elems;
    			}
    		}
    	}
    	
    	if (elems == 0) {
    		JMenuItem item = new JMenuItem("<html><i>No statemachines available.</i></html>");
    		item.setEnabled(false);
    		menu.add(item);
    	}
    }
    
    @Subscribe
	public void onSystemChanged(SystemStateChangedEvent e) {
    	if (Options.getCheckStateInvariants()) {
        	fLogWriter.println("Checking state invariants.");
        	fSession.system().state().checkStateInvariants(fLogWriter);
        }
    }
    
    @Subscribe
	public void onStructureChanged(SystemStructureChangedEvent e) {
		if (fCbMenuItemCheckStructure.isSelected()) {
			checkStructure();
		}
	}
    
    @Subscribe
	public void onStatementExecuted(StatementExecutedEvent e) {
    	setUndoRedoButtons();

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
            @Override
			public void internalFrameActivated(InternalFrameEvent ev) {
                fActionFilePrint.setEnabled(isPrintable);
                fActionFileExportView.setEnabled(isPrintable);
                fActionFileExportViewAll.setEnabled(isPrintable);
                fActionFilePrintView.setEnabled(isViewPrintable);
            }

            @Override
			public void internalFrameDeactivated(InternalFrameEvent ev) {
                fActionFilePrint.setEnabled(false);
                fActionFileExportView.setEnabled(false);
                fActionFileExportViewAll.setEnabled(false);
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
        fActionStateCheckStructure.setEnabled(on);
        fActionDetermineStates.setEnabled(on);
        fActionCheckStateInvariants.setEnabled(on);
        fActionStateReset.setEnabled(on);
        fActionViewCreateObjectCount.setEnabled(on);
        fActionViewCreateObjectCount.setEnabled(on);
        fActionViewCreateLinkCount.setEnabled(on);
        fActionViewCreateClassDiagram.setEnabled(on);
        fStateMachineDropdown.setEnabled(on);
        statemachineMenu.setEnabled(on);
        fActionViewCreateObjectDiagram.setEnabled(on);
        fActionViewCreateClassInvariant.setEnabled(on);
        fActionViewCreateStateEvolution.setEnabled(on);
        fActionViewCreateObjectProperties.setEnabled(on);
        fActionViewCreateClassExtent.setEnabled(on);
        fActionViewCreateSequenceDiagram.setEnabled(on);
        fActionViewCreateCommunicationDiagram.setEnabled(on);
        fActionViewCreateCallStack.setEnabled(on);
        fActionViewCreateCommandList.setEnabled(on);
        fActionViewAssociationInfo.setEnabled(on);
        
		if (Options.doPLUGIN) {
			for (AbstractAction currentAction : pluginActions.values()) {
				Object actionName = currentAction.getValue(AbstractAction.NAME);
				if(actionName != null && actionName.equals("Object to Class")) {
					currentAction.setEnabled(true);
				} else {
					currentAction.setEnabled(on);
				}
			}
		}
		setUndoRedoButtons();
        closeAllViews();
        statemachineMenu.removeAll();
        createStateMachineMenuEntries(statemachineMenu);

        if (on) {
            MSystem system = fSession.system();
            fModelBrowser.setModel(system.model());
            system.getEventBus().register(this);
            setTitle("USE: " + new File(system.model().filename()).getName());
        } else {
            fModelBrowser.setModel(null);
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
    	if(!fSession.hasSystem()){
    		disableUndo();
    		disableRedo();
    		return;
    	}
    	
    	String nextToUndo = fSession.system().getUndoDescription();
		
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
        fMenuItemEditUndo.setText(s);
        fBtnEditUndo.setToolTipText(s);
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
         fMenuItemEditRedo.setText(s);
         fBtnEditRedo.setToolTipText(s);
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
        	
        	fSession.system().execute(statement);
        			
        } catch (MSystemException e) {
        	JOptionPane.showMessageDialog(
					this, 
					e.getMessage(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // Actions

    private final ActionFileOpenSpec fActionFileOpenSpec = new ActionFileOpenSpec();

    private final ActionFileRefreshSpec fActionFileReload = new ActionFileRefreshSpec();
    
    private final ActionFileSaveScript fActionFileSaveScript = new ActionFileSaveScript();

    private final ActionFileSaveProtocol fActionFileSaveProtocol = new ActionFileSaveProtocol();

    private final ActionFilePrinterSetup fActionFilePrinterSetup = new ActionFilePrinterSetup();

    private final ActionFilePrint fActionFilePrint = new ActionFilePrint();

    private final ActionFilePrintView fActionFilePrintView = new ActionFilePrintView();

    private final ActionFileExportView fActionFileExportView = new ActionFileExportView(false);
    
    private final ActionFileExportView fActionFileExportViewAll = new ActionFileExportView(true);
    
    private final ActionFileExit fActionFileExit = new ActionFileExit();

    private final ActionEditUndo fActionEditUndo = new ActionEditUndo();
    private final ActionEditRedo fActionEditRedo = new ActionEditRedo();

    private final ActionStateCreateObject fActionStateCreateObject = new ActionStateCreateObject();

    private final ActionStateEvalOCL fActionStateEvalOCL = new ActionStateEvalOCL();

    private final ActionStateCheckStructure fActionStateCheckStructure = new ActionStateCheckStructure();

    private final ActionDetermineStates fActionDetermineStates = new ActionDetermineStates();
    
    private final ActionCheckStateInvariants fActionCheckStateInvariants = new ActionCheckStateInvariants();
    
    private final ActionStateReset fActionStateReset = new ActionStateReset();

    private final ActionViewCreateObjectCount fActionViewCreateObjectCount = new ActionViewCreateObjectCount();

    private final ActionViewCreateLinkCount fActionViewCreateLinkCount = new ActionViewCreateLinkCount();

    private final ActionViewCreateClassDiagram fActionViewCreateClassDiagram = new ActionViewCreateClassDiagram();

    private final StateMachineDropdown fStateMachineDropdown = new StateMachineDropdown();
    
    private final ActionViewCreateObjectDiagram fActionViewCreateObjectDiagram = new ActionViewCreateObjectDiagram();

    private final ActionViewCreateClassInvariant fActionViewCreateClassInvariant = new ActionViewCreateClassInvariant();

    private final ActionViewCreateStateEvolution fActionViewCreateStateEvolution = new ActionViewCreateStateEvolution();

    private final ActionViewCreateObjectProperties fActionViewCreateObjectProperties = new ActionViewCreateObjectProperties();

    private final ActionViewCreateClassExtent fActionViewCreateClassExtent = new ActionViewCreateClassExtent();

    private final ActionViewCreateSequenceDiagram fActionViewCreateSequenceDiagram = new ActionViewCreateSequenceDiagram();

    private final ActionViewCreateCommunicationDiagram fActionViewCreateCommunicationDiagram = new ActionViewCreateCommunicationDiagram();
    
    private final ActionViewCreateCallStack fActionViewCreateCallStack = new ActionViewCreateCallStack();

    private final ActionViewCreateCommandList fActionViewCreateCommandList = new ActionViewCreateCommandList();

    private final ActionViewAssociationInfo fActionViewAssociationInfo = new ActionViewAssociationInfo();
    
    private final ActionViewTile fActionViewTile = new ActionViewTile();

    private final ActionViewCloseAll fActionViewCloseAll = new ActionViewCloseAll();

    private final ActionHelpAbout fActionHelpAbout = new ActionHelpAbout();

    /**
     * Opens and compiles a specification file.
     */
    private class ActionFileOpenSpec extends AbstractAction {
        private boolean wasUsed;

        ActionFileOpenSpec() {
            super("Open specification...", getIcon("document-open.png"));
        }

        protected ActionFileOpenSpec(String title) {
            super(title);
        }
        
        protected ActionFileOpenSpec(String title, Icon icon) {
            super(title, icon);
        }
        
        @Override
		public void actionPerformed(ActionEvent e) {
        	if (!validateOpenPossible()) return;

            JFileChooser fChooser = new JFileChooser(Options.getLastDirectory().toFile());
            ExtFileFilter filter = new ExtFileFilter("use", "USE specifications");
            fChooser.setFileFilter(filter);
            fChooser.setDialogTitle("Open specification");
            
            if (wasUsed)
            	fChooser.setSelectedFile(Options.getRecentFile("use").toFile());

            int returnVal = fChooser.showOpenDialog(MainWindow.this);
            if (returnVal != JFileChooser.APPROVE_OPTION)
                return;

            Path path = fChooser.getCurrentDirectory().toPath();
            Options.setLastDirectory(path);
            Path f = fChooser.getSelectedFile().toPath();

            compile(f);
            
            Options.getRecentFiles().push(f.toAbsolutePath().toString());
            wasUsed = true;
        }

        protected boolean validateOpenPossible() {
        	if (fSession.hasSystem() && fSession.system().isExecutingStatement()) {
				JOptionPane
						.showMessageDialog(
								MainWindow.this,
								"The system is currently executing a statement.\nPlease end the execution before opening a new model.",
								"USE is executing",
								JOptionPane.ERROR_MESSAGE);
				return false;
        	} else {
        		return true;
        	}
        }
                    
        protected boolean compile(final Path f) {
        	fLogPanel.clear();
            showLogPanel();
            
        	fLogWriter.println("compiling specification " + f.toString() + "...");
        	
            MModel model = null;
            try (InputStream iStream = Files.newInputStream(f)) {
                model = USECompiler.compileSpecification(iStream, f.toAbsolutePath().toString(),
                        fLogWriter, new ModelFactory());
                fLogWriter.println("done.");
            } catch (IOException ex) {
                fLogWriter.println("File `" + f.toAbsolutePath().toString() + "' not found.");
            }
            
            final MSystem system;
            if (model != null) {
            	fLogWriter.println(model.getStats());
            	// create system
            	system = new MSystem(model);
            } else {
            	system = null;
            }
            
            // set new system (may be null if compilation failed)
            SwingUtilities.invokeLater(new Runnable() {
                @Override
				public void run() {
                    fSession.setSystem(system);
                }
            });
            
            if (system != null) {
            	Options.getRecentFiles().push(f.toString());
            	Options.setLastDirectory(f.getParent());
            	return true;
            } else {
            	return false;
            }
        }
    }

    private class ActionFileOpenSpecRecent extends ActionFileOpenSpec {
    	
    	private final Path fileName;
    	
    	public ActionFileOpenSpecRecent(Path filename) {
    		super(filename.toString());
    		this.fileName = filename;
    	}
    	
    	@Override
		public void actionPerformed(ActionEvent e) {
    		if (!validateOpenPossible()) return;
            compile(fileName);
        }
    }
    
    private class ActionFileRefreshSpec extends ActionFileOpenSpec {
    	public ActionFileRefreshSpec() {
    		super("Reload specification", getIcon("refresh.png"));
    	}
    	
    	@Override
		public void actionPerformed(ActionEvent e) {
    		if (!validateOpenPossible()) return;
    		
    		Path file = Options.getRecentFile("use");
            
    		if (file != null) {
            	compile(file);
            }
        }
    }

    /**
     * Saves commands executed so far to a script file.
     */
    private class ActionFileSaveScript extends AbstractAction {
        private JFileChooser fChooser;

        ActionFileSaveScript() {
			super("Save script (.soil)...", getIcon("save.png"));
			
			setEnabled(false);
        }

        @Override
		public void actionPerformed(ActionEvent e) {
            String path;
            // reuse chooser if possible
            if (fChooser == null) {
                fChooser = new JFileChooser(Options.getLastDirectory().toFile());
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
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f)))){
                out.println("-- Script generated by USE "
                        + Options.RELEASE_VERSION);
                out.println();
                fSession.system().writeSoilStatements(out);
                fLogWriter.println("Wrote script " + f);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(MainWindow.this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ActionFileSaveProtocol extends AbstractAction {
        private JFileChooser fChooser;

        ActionFileSaveProtocol() {
            super("Save protocol...", getIcon("save.png"));
        }

        @Override
		public void actionPerformed(ActionEvent e) {
            String path;
            // reuse chooser if possible
            if (fChooser == null) {
                fChooser = new JFileChooser(Options.getLastDirectory().toFile());
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

            try (FileOutputStream fOut = new FileOutputStream(f)){
                USEWriter.getInstance().writeProtocolFile(fOut);
                fOut.flush();
                fOut.close();
            } catch (IOException e1) {
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

        @Override
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
            super("Print diagram...", getIcon("document-print.png"));
            this.setEnabled(false);
        }

        @Override
		public void actionPerformed(ActionEvent e) {
            ViewFrame vf = (ViewFrame) fDesk.getSelectedFrame();

            if (vf != null && vf.isPrintable())
                vf.print(pageFormat());
        }
    }

    /**
     * Print visible view of diagram.
     */
    private class ActionFilePrintView extends AbstractAction {
        ActionFilePrintView() {
            super("Print View...", getIcon("document-print.png"));
            this.setEnabled(false);
        }

        @Override
		public void actionPerformed(ActionEvent e) {
            ViewFrame vf = (ViewFrame) fDesk.getSelectedFrame();

            if (vf != null && vf.isPrintable()) {
                View view = vf.getView();
                ((SequenceDiagramView) view).printOnlyView(pageFormat());
            }
        }
    }

    /**
     * Print visible view of diagram.
     */
    private class ActionFileExportView extends AbstractAction {
    	
    	private JFileChooser chooser;
    	
    	private final boolean exportAll;
    	
    	/**
    	 * @param exportAll whether to export the view border or not
    	 */
    	ActionFileExportView(boolean exportAll) {
            super("Export view as PDF...", getIcon("export_pdf.png"));
            this.setEnabled(false);
            this.exportAll = exportAll;
        }

        @Override
		public void actionPerformed(ActionEvent e) {
            ViewFrame vf = (ViewFrame) fDesk.getSelectedFrame();
            File f;

            if (vf != null && vf.isPrintable()) {
            	// Ask for filename
            	int option = JOptionPane.YES_OPTION;

            	if (chooser == null) {
            		chooser = new JFileChooser(Options.getLastDirectory().toFile());
            		ExtFileFilter filter = new ExtFileFilter("pdf", "PDF");
            		chooser.setFileFilter(filter);
            		chooser.setDialogTitle("Export to PDF");
            	}
            	
                do {
                    int returnVal = chooser.showSaveDialog( MainWindow.this );
                    if (returnVal != JFileChooser.APPROVE_OPTION)
                        return;

                    Options.setLastDirectory(chooser.getCurrentDirectory().toPath());
                    String filename = chooser.getSelectedFile().getName();

                    f = chooser.getSelectedFile();
                    String filePath = f.getPath();
                    if(!filePath.toLowerCase().endsWith(".pdf"))
                    {
                        f = new File(filePath + ".pdf");
                    }

                    if (f.exists()) {
                        option = JOptionPane.showConfirmDialog(MainWindow.this,
                                "Overwrite existing file " + filename + "?",
                                "Please confirm", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (option == JOptionPane.CANCEL_OPTION) {
                            return;
                        }

                    }
                    // display the saving dialog, as long as the file
                    // will be overwritten or cancel is pressed.
                } while (option != JOptionPane.YES_OPTION);

                PrintableView pv = (PrintableView)vf.getView();
                
                Rectangle size;
                if (exportAll) {
                	size = new Rectangle(vf.getWidth(), vf.getHeight());
                } else {
                	size = new Rectangle(pv.getContentWidth(), pv.getContentHeight());
                }
                
                // step 1
                Document document = new Document(size);
                // step 2
                PdfWriter writer;
				try {
					writer = PdfWriter.getInstance(document, new FileOutputStream(f));
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(MainWindow.this, e1.getMessage(), "Error accessing file!", JOptionPane.ERROR_MESSAGE);
					return;
				} catch (DocumentException e1) {
					e1.printStackTrace();
					return;
				}
				
                // step 3
                document.open();
                // step 4
                PdfContentByte canvas = writer.getDirectContent();
                Graphics2D g2 = new PdfGraphics2D(canvas, size.getWidth(), size.getHeight());
                vf.export(g2, exportAll);
                                
                g2.dispose();
                // step 5
                document.close();
                
				JOptionPane.showMessageDialog(MainWindow.this,
						"Export finished.", "USE " + Options.getUSEVersion(),
						JOptionPane.INFORMATION_MESSAGE);
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

        @Override
		public void actionPerformed(ActionEvent e) {
            close();
        }
    }

    /**
     * Undoes the last state manipulation command.
     */
    class ActionEditUndo extends AbstractAction {
        ActionEditUndo() {
            super("Undo", getIcon("undo.png"));
            this.setEnabled(false);
        }

        @Override
		public void actionPerformed(ActionEvent e) {
        	if (fSession.hasSystem() && fSession.system().isExecutingStatement()) {
				JOptionPane
						.showMessageDialog(
								MainWindow.this,
								"The system is currently executing a statement.\nPlease end the execution before undoing.",
								"USE is executing",
								JOptionPane.ERROR_MESSAGE);
				return;
        	}
        	
            try {
            	fSession.system().undoLastStatement();
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
    		super("Redo", getIcon("redo.png"));
            this.setEnabled(false);
    	}

		@Override
		public void actionPerformed(ActionEvent e) {
        	if (fSession.hasSystem() && fSession.system().isExecutingStatement()) {
				JOptionPane
						.showMessageDialog(
								MainWindow.this,
								"The system is currently executing a statement.\nPlease end the execution before redoing.",
								"USE is executing",
								JOptionPane.ERROR_MESSAGE);
				return;
        	}
        				
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

        @Override
		public void actionPerformed(ActionEvent e) {
            CreateObjectDialog dlg = new CreateObjectDialog(fSession, MainWindow.this);
            dlg.setVisible(true);
        }
    }

    /**
     * Opens a dialog for evaluating OCL expressions.
     */
    private class ActionStateEvalOCL extends AbstractAction {
        ActionStateEvalOCL() {
            super("Evaluate OCL expression...", getIcon("OCL.gif"));
        }

        @Override
		public void actionPerformed(ActionEvent e) {
            EvalOCLDialog dlg = new EvalOCLDialog(fSession, MainWindow.this);
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

        @Override
		public void actionPerformed(ActionEvent e) {
            checkStructure();
        }
    }

    private class ActionCheckStateInvariants extends AbstractAction {
    	ActionCheckStateInvariants() {
			super("Check state invariants");
		}
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			fSession.system().state().checkStateInvariants(fLogWriter);
		}
	}
    
    private class ActionDetermineStates extends AbstractAction {
    	ActionDetermineStates() {
			super("Determine states");
		}
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			fSession.system().determineStates(fLogWriter);
		}
	}
    
    /**
     * Resets the system to its initial empty state.
     */
    private class ActionStateReset extends AbstractAction {
        ActionStateReset() {
            super("Reset");
        }

        @Override
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
            super("Object count", getIcon("blue-chart-icon.png"));
        }

        @Override
		public void actionPerformed(ActionEvent e) {
            ObjectCountView ov = new ObjectCountView(fSession.system());
            ViewFrame f = new ViewFrame("Object count", ov,
                    "blue-chart-icon.png");
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            JScrollPane jsp = new JScrollPane(ov);
            jsp.getVerticalScrollBar().setUnitIncrement(16);
            c.add(jsp, BorderLayout.CENTER);
            addNewViewFrame(f);
        }
    }

    /**
     * Creates a new link count view.
     */
    private class ActionViewCreateLinkCount extends AbstractAction {
        ActionViewCreateLinkCount() {
            super("Link count", getIcon("red-chart-icon.png"));
        }

        @Override
		public void actionPerformed(ActionEvent e) {
            LinkCountView lv = new LinkCountView(fSession.system());
            ViewFrame f = new ViewFrame("Link count", lv, "red-chart-icon.png");
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            JScrollPane jsp = new JScrollPane(lv);
            jsp.getVerticalScrollBar().setUnitIncrement(16);
            c.add(jsp, BorderLayout.CENTER);
            addNewViewFrame(f);
        }
    }

    /**
     * Creates a new class diagram view.
     */
    private class ActionViewCreateClassDiagram extends AbstractAction {
        ActionViewCreateClassDiagram() {
            super("Class diagram", getIcon("ClassDiagram.gif"));
        }

        @Override
		public void actionPerformed(ActionEvent e) {
        	// Don' load layout if shift key is pressed
        	boolean loadLayout = (e.getModifiers() & ActionEvent.SHIFT_MASK) == 0;
        		
            ClassDiagramView cdv = new ClassDiagramView(MainWindow.this, fSession.system(), loadLayout);
            ViewFrame f = new ViewFrame("Class diagram", cdv, "ClassDiagram.gif");
            // give some help information
            f.addInternalFrameListener(new InternalFrameAdapter() {
                @Override
				public void internalFrameActivated(InternalFrameEvent ev) {
                    fStatusBar.showTmpMessage("Use left mouse button to move "
                            + "classes, right button for popup menu.");
                }

                @Override
				public void internalFrameDeactivated(InternalFrameEvent ev) {
                    fStatusBar.clearMessage();
                }

				@Override
				public void internalFrameClosed(InternalFrameEvent e) {
					classDiagrams.remove(((ViewFrame)e.getSource()).getView());
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
     * Button for statemachine selection from the toolbar.
     */
    private class StateMachineDropdown extends JToggleButton {

		private final JPopupMenu menu = new JPopupMenu();

		public StateMachineDropdown() {
			setIcon(MainWindow.this.getIcon("Diagram.gif"));
			menu.addPopupMenuListener(new PopupListener());
			addItemListener(new DropdownItemListener());
			
			// reserve space for arrow (there must be a nicer solution, also: magic numbers)
			Insets in = getMargin();
			Insets in2 = new Insets(in.top +1, 2, in.bottom, 12);
			setMargin(in2);
		}

		@Override
		public Dimension getMaximumSize() {
			// hacky?
			return super.getPreferredSize();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Color oldColor = g.getColor();
			if(isEnabled()){
				g.setColor(Color.BLACK);
			}
			else {
				g.setColor(Color.GRAY);
			}
			
			Polygon triangle = new Polygon(new int[]{ 0, 9, 5 }, new int[]{ 0, 0, 4 }, 3);
			triangle.translate(23, 13);
			g.fillPolygon(triangle);
			
			g.setColor(oldColor);
		}
		
		private class DropdownItemListener implements ItemListener {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					menu.removeAll();
					createStateMachineMenuEntries(menu);
					menu.show(StateMachineDropdown.this, 0, getHeight());
				}
			}
		}

		private class PopupListener implements PopupMenuListener {
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				StateMachineDropdown.this.setSelected(false);
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		}

	}
    
    /**
     * Creates a new object diagram view.
     */
    private class ActionViewCreateObjectDiagram extends AbstractAction {
        ActionViewCreateObjectDiagram() {
            super("Object diagram", getIcon("ObjectDiagram.gif"));
        }

        @Override
		public void actionPerformed(ActionEvent e) {
            NewObjectDiagramView odv = new NewObjectDiagramView(MainWindow.this, fSession.system());
            ViewFrame f = new ViewFrame("Object diagram", odv, "ObjectDiagram.gif");
            
            // give some help information
            f.addInternalFrameListener(new InternalFrameAdapter() {
                @Override
				public void internalFrameActivated(InternalFrameEvent ev) {
                    fStatusBar.showTmpMessage("Use left mouse button to move "
                            + "objects, right button for popup menu.");
                }

                @Override
				public void internalFrameDeactivated(InternalFrameEvent ev) {
                    fStatusBar.clearMessage();
                }
                
                @Override
				public void internalFrameClosed(InternalFrameEvent e) {
					objectDiagrams.remove(((ViewFrame)e.getSource()).getView());
				}
            });
            
            int OBJECTS_LARGE_SYSTEM = 100;
            
            // Many objects. Ask user if all objects should be hidden
            if (fSession.system().state().allObjects().size() > OBJECTS_LARGE_SYSTEM) {
            	
            	int option = JOptionPane.showConfirmDialog(new JPanel(),
                        "The current system state contains more then " + OBJECTS_LARGE_SYSTEM + " instances." +
            	"This can slow down the object diagram.\r\nDo you want to start with an empty object diagram?",
                        "Large system state", JOptionPane.YES_NO_OPTION);
                
            	if (option == JOptionPane.YES_OPTION) {
                    odv.getDiagram().hideAll();
                }
            }
            
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            c.add(odv, BorderLayout.CENTER);
            addNewViewFrame(f);
            objectDiagrams.add(odv);
        }
    }
    
    /**
     * Creates a new communication diagram view.
     */
    private class ActionViewCreateCommunicationDiagram extends AbstractAction {
	ActionViewCreateCommunicationDiagram() {
	    super("Communication diagram", getIcon("CommunicationDiagram.gif"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	    CommunicationDiagramView cdv = new CommunicationDiagramView(MainWindow.this, fSession.system());
	    ViewFrame f = new ViewFrame("Communication diagram", cdv, "CommunicationDiagram.gif");
	    // give some help information
	    f.addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameActivated(InternalFrameEvent ev) {
				fStatusBar.showTmpMessage("Use left mouse button to move "
								+ "actor, object and link boxes, right button for popup menu.");
			}

			@Override
			public void internalFrameDeactivated(InternalFrameEvent ev) {
				fStatusBar.clearMessage();
			}

			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				communicationDiagrams.remove(((ViewFrame) e.getSource()).getView());
			}
	    });

	    JComponent c = (JComponent) f.getContentPane();
	    c.setLayout(new BorderLayout());
	    c.add(cdv, BorderLayout.CENTER);
	    addNewViewFrame(f);
	    communicationDiagrams.add(cdv);
	}
    }

    /**
     * Creates a new class invariant view.
     */
    private class ActionViewCreateClassInvariant extends AbstractAction {
        ActionViewCreateClassInvariant() {
            super("Class invariants", getIcon("invariant-view.png"));
        }

        @Override
		public void actionPerformed(ActionEvent e) {
            ClassInvariantView civ = new ClassInvariantView(MainWindow.this,
                    fSession.system());
            ViewFrame f = new ViewFrame("Class invariants", civ,
                    "InvariantView.gif");
            
			f.addInternalFrameListener(new InternalFrameAdapter() {
				@Override
				public void internalFrameActivated(InternalFrameEvent ev) {
					fStatusBar.showTmpMessage("Use right mouse button for popup menu.");
				}

				@Override
				public void internalFrameDeactivated(InternalFrameEvent ev) {
					fStatusBar.clearMessage();
				}
			});
            
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            c.add(civ, BorderLayout.CENTER);
            addNewViewFrame(f);
        }
    }

    /**
     * Creates a new state evolution view.
     */
    private class ActionViewCreateStateEvolution extends AbstractAction {
        ActionViewCreateStateEvolution() {
            super("State evolution", getIcon("line-chart.png"));
        }

        @Override
		public void actionPerformed(ActionEvent e) {
            StateEvolutionView sev = new StateEvolutionView(fSession.system());
            ViewFrame f = new ViewFrame("State evolution", sev,
                    "line-chart.png");
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
            super("Object properties", getIcon("ObjectProperties.gif"));
        }

        @Override
		public void actionPerformed(ActionEvent e) {
            showObjectPropertiesView();
        }
    }

    /**
     * Creates a new class extent view.
     */
    private class ActionViewCreateClassExtent extends AbstractAction {
        ActionViewCreateClassExtent() {
            super("Class extent", getIcon("ClassExtentView.gif"));
        }

        @Override
		public void actionPerformed(ActionEvent e) {
            ClassExtentView cev = new ClassExtentView(MainWindow.this, fSession.system());
            ViewFrame f = new ViewFrame("Class extent", cev, "ClassExtentView.gif");
            
            f.addInternalFrameListener(new InternalFrameAdapter() {
            	@Override
				public void internalFrameActivated(InternalFrameEvent ev) {
					fStatusBar.showTmpMessage("Use right mouse button for popup menu.");
				}

				@Override
				public void internalFrameDeactivated(InternalFrameEvent ev) {
					fStatusBar.clearMessage();
				}
			});
            
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
			super("Sequence diagram", getIcon("SequenceDiagram.gif"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			SequenceDiagramView sv = new SequenceDiagramView(fSession.system(), MainWindow.this);
			ViewFrame f = new ViewFrame("Sequence diagram", sv,
					"SequenceDiagram.gif");
			JComponent c = (JComponent) f.getContentPane();
			c.setLayout(new BorderLayout());
			c.add(new SDScrollPane(sv), BorderLayout.CENTER);
			addNewViewFrame(f);
		}
	}

    /**
     * Creates a new call stack view.
     */
    private class ActionViewCreateCallStack extends AbstractAction {
        ActionViewCreateCallStack() {
            super("Call stack", getIcon("CallStack.gif"));
        }

        @Override
		public void actionPerformed(ActionEvent e) {
            CallStackView csv = new CallStackView(fSession.system());
            ViewFrame f = new ViewFrame("Call stack", csv, "CallStack.gif");
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            JScrollPane jsp = new JScrollPane(csv);
            jsp.getVerticalScrollBar().setUnitIncrement(16);
            c.add(jsp, BorderLayout.CENTER);
            addNewViewFrame(f);
        }
    }

    /**
     * Creates a new command list view.
     */
    private class ActionViewCreateCommandList extends AbstractAction {
        ActionViewCreateCommandList() {
            super("Command list", getIcon("CmdList.gif"));
        }

        @Override
		public void actionPerformed(ActionEvent e) {
            CommandView cv = new CommandView(fSession.system());
            ViewFrame f = new ViewFrame("Command list", cv, "CmdList.gif");
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            JScrollPane jsp = new JScrollPane(cv);
            jsp.getVerticalScrollBar().setUnitIncrement(16);
            c.add(jsp, BorderLayout.CENTER);
            addNewViewFrame(f);
        }
    }

    /**
     * Creates a new association info view.
     */
    private class ActionViewAssociationInfo extends AbstractAction {
    	ActionViewAssociationInfo() {
            super("Association ends informations", getIcon("Association.gif"));
        }

        @Override
		public void actionPerformed(ActionEvent e) {
        	AssociationEndsInfo v = new AssociationEndsInfo(MainWindow.this, fSession.system());
            ViewFrame f = new ViewFrame("Association ends info", v, "Association.gif");
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            JScrollPane jsp = new JScrollPane(v);
            jsp.getVerticalScrollBar().setUnitIncrement(16);
            c.add(jsp, BorderLayout.CENTER);
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

        @Override
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

        @Override
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
            // then relocating & resizing each
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

        @Override
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
        win.setLocationRelativeTo(null);
        win.setVisible(true);
        return win;
    }
    
    public void showStateMachineView(MProtocolStateMachine sm, MObject instance) {
    	StateMachineDiagramView dv = showStateMachineView(sm);
    	dv.setMonitoredInstance(instance);
    }
    
	/**
	 * @param psm
	 */
	public StateMachineDiagramView showStateMachineView(MStateMachine sm) {
		StateMachineDiagramView dv = new StateMachineDiagramView(MainWindow.this, fSession.system(), sm);
        
		ViewFrame f = new ViewFrame("State machine " + StringUtil.inQuotes(sm.name()), dv, "ClassDiagram.gif");
        
		// give some help information
        f.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
			public void internalFrameActivated(InternalFrameEvent ev) {
                fStatusBar.showTmpMessage("Use left mouse button to move "
                        + "objects, right button for popup menu.");
            }

            @Override
			public void internalFrameDeactivated(InternalFrameEvent ev) {
                fStatusBar.clearMessage();
            }
        });
        
        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(dv, BorderLayout.CENTER);
        addNewViewFrame(f);
        
        return dv;
	}
	
	private Icon getIcon(String name) {
		return new ImageIcon(Options.getIconPath(name).toString());
	}
}
