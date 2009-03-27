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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

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
import org.tzi.use.gui.util.ExtFileFilter;
import org.tzi.use.gui.util.StatusBar;
import org.tzi.use.gui.util.TextComponentWriter;
import org.tzi.use.gui.views.CallStackView;
import org.tzi.use.gui.views.ClassExtentView;
import org.tzi.use.gui.views.ClassInvariantView;
import org.tzi.use.gui.views.CommandView;
import org.tzi.use.gui.views.LinkCountView;
import org.tzi.use.gui.views.ObjectCountView;
import org.tzi.use.gui.views.ObjectPropertiesView;
import org.tzi.use.gui.views.StateEvolutionView;
import org.tzi.use.gui.views.View;
import org.tzi.use.gui.views.diagrams.classdiagram.NewClassDiagramView;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagramView;
import org.tzi.use.gui.views.seqDiag.SDScrollPane;
import org.tzi.use.gui.views.seqDiag.SequenceDiagramView;
import org.tzi.use.main.Session;
import org.tzi.use.main.shell.Shell;
import org.tzi.use.parser.cmd.CMDCompiler;
import org.tzi.use.parser.use.USECompiler;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MCmdCreateObjects;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.StateChangeEvent;
import org.tzi.use.uml.sys.StateChangeListener;
import org.tzi.use.util.Log;
import org.tzi.use.util.USEWriter;

/**
 * The main application window of USE.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Mark Richters
 */
public class MainWindow extends JFrame implements StateChangeListener {
    private Session fSession;

    private StatusBar fStatusBar;

    private LogPanel fLogPanel;

    private PrintWriter fLogWriter;

    private JDesktopPane fDesk;

    private JSplitPane fTopSplitPane;

    private ModelBrowser fModelBrowser;

    private JMenuItem fMenuItemEditUndo;

    private JButton fBtnEditUndo;

    private JCheckBoxMenuItem fCbMenuItemCheckStructure;

    private static final String DEFAULT_UNDO_TEXT = "Undo last state command";

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

    MainWindow(Session session) {
        super("USE");

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

        fSession.addExecutedCmdListener(new Session.ExecutedCmdListener() {
            public void executedCmd(Session.ExecutedCmd e) {
                final MCmd cmd = e.cmd();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (cmd.canUndo())
                            enableUndo(cmd.name());
                    }
                });
            }
        });

        // create toolbar
        JToolBar toolBar = new JToolBar();
        addToToolBar(toolBar, fActionFileOpenSpec, "Open specification");
        addToToolBar(toolBar, fActionFilePrint, "Print diagram");
        addToToolBar(toolBar, fActionFilePrintView, "Print view");
        toolBar.addSeparator();

        fBtnEditUndo = addToToolBar(toolBar, fActionEditUndo, DEFAULT_UNDO_TEXT);
        addToToolBar(toolBar, fActionStateEvalOCL, STATE_EVAL_OCL);
        toolBar.addSeparator();

        addToToolBar(toolBar, fActionViewCreateClassDiagram,
                "Create class diagram view");
        addToToolBar(toolBar, fActionViewCreateObjectDiagram,
                "Create object diagram view");
        addToToolBar(toolBar, fActionViewCreateClassInvariant,
                "Create class invariant view");
        addToToolBar(toolBar, fActionViewCreateObjectCount,
                "Create object count view");
        addToToolBar(toolBar, fActionViewCreateLinkCount,
                "Create link count view");
        addToToolBar(toolBar, fActionViewCreateStateEvolution,
                "Create state evolution view");
        addToToolBar(
                toolBar,
                fActionViewCreateObjectProperties,
                "Create object properties view <br>"
                        + "(double click on object to show properties for a specific object)");
        addToToolBar(toolBar, fActionViewCreateClassExtent,
                "Create class extent view");
        addToToolBar(toolBar, fActionViewCreateSequenceDiagram,
                "Create sequence diagram view");
        addToToolBar(toolBar, fActionViewCreateCallStack,
                "Create call stack view");
        addToToolBar(toolBar, fActionViewCreateCommandList,
                "Create command list view");
        // addToToolBar(toolBar, fActionViewCreateStateTree, "Create state tree
        // view");

        // create the menubar

        JMenuBar menuBar = new JMenuBar();
        getRootPane().setJMenuBar(menuBar);

        // `File' submenu
        JMenuItem mi;
        JMenu menu = new JMenu("File");
        menu.setMnemonic('F');
        menuBar.add(menu);

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
        menuBar.add(menu);

        fMenuItemEditUndo = menu.add(fActionEditUndo);
        fMenuItemEditUndo.setMnemonic('U');
        fMenuItemEditUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                Event.CTRL_MASK));

        // `State' submenu
        menu = new JMenu("State");
        menu.setMnemonic('S');
        menuBar.add(menu);

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
        menuBar.add(menu);

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

        menu.addSeparator();
        mi = menu.add(fActionViewTile);
        mi.setMnemonic('T');
        mi = menu.add(fActionViewCloseAll);
        mi.setMnemonic('a');

        // `Help' submenu
        menu = new JMenu("Help");
        menu.setMnemonic('H');
        menuBar.add(menu);
        // not yet implemented in swing: menuBar.setHelpMenu(menu);
        mi = menu.add(fActionHelpAbout);
        mi.setMnemonic('A');

        // create the browser panel
        fModelBrowser = new ModelBrowser();

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
        contentPane.add(toolBar, BorderLayout.NORTH);
        contentPane.add(fTopSplitPane, BorderLayout.CENTER);
        contentPane.add(fStatusBar, BorderLayout.SOUTH);
        setContentPane(contentPane);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        setBounds(10, 20, 900, 700);

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

    private void close() {
        setVisible(false);
        dispose();
        Shell.getInstance(fSession).exit();
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
        if (e.structureHasChanged() && fCbMenuItemCheckStructure.isSelected())
            checkStructure();
        fActionFileSaveScript
                .setEnabled(fSession.system().numExecutedCmds() > 0);
    }

    private int fViewFrameX = 0;

    private int fViewFrameY = 0;

    /**
     * Adds a new view (internal frame) to the desktop.
     */
    private void addNewViewFrame(ViewFrame f) {
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
                // jdk1.3: JInternalFrame jif = ev.getInternalFrame();
                JInternalFrame jif = (JInternalFrame) ev.getSource();
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
        fActionViewClassDiagram.setEnabled(on);
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

    /**
     * Enables the undo command.
     */
    void enableUndo(String name) {
        fActionEditUndo.setEnabled(true);
        // change text of menu item, leave toolbar button untouched
        String s = "Undo: " + name;
        // FIXME: apparently enableUndo is sometimes called before GUI setup
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
     * Shows the log panel.
     */
    void showLogPanel() {
        double loc = fTopSplitPane.getDividerLocation();
        // if divider is at bottom move it to top so that the log is visible
        if (loc / (fTopSplitPane.getHeight() - fTopSplitPane.getDividerSize()) > 0.95)
            fTopSplitPane.setDividerLocation(0.75);
    }

    /**
     * Creates a new object. Keeps track of undo information and handles errors
     * on the GUI level.
     */
    public void createObject(String clsName, java.util.List names) {
        // System.err.println("Creating " + name + " : " + cls.name());

        // setup command for object creation
        MClass cls = fSession.system().model().getClass(clsName);
        if (cls == null) {
            JOptionPane
                    .showMessageDialog(this, "No class named `" + clsName
                            + "' defined in model.", "Error",
                            JOptionPane.ERROR_MESSAGE);
        } else {
            MSystem system = fSession.system();
            MCmdCreateObjects cmd = new MCmdCreateObjects(system.state(),
                    names, TypeFactory.mkObjectType(cls));
            try {
                system.executeCmd(cmd);
                if (cmd.canUndo())
                    enableUndo(cmd.name());
                StringBuffer msg = new StringBuffer();
                for (Iterator it = names.iterator(); it.hasNext();) {
                    String name = (String) it.next();
                    msg.append(name);
                    if (it.hasNext())
                        msg.append(", ");
                }
                USEWriter.getInstance().protocol(
                        "[GUI] create " + msg + ":" + clsName);
            } catch (MSystemException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                // Log.error(ex.getMessage());
            }
        }
    }

    /**
     * Applies changes by setting new attribute values. Entries may be arbitrary
     * OCL expressions.
     */
    public void execCmd(String line) {
        Log.trace(this, "line = " + line);

        // exit if no changes
        if (line == null)
            return;

        MSystem system = fSession.system();
        java.util.List cmdList = CMDCompiler.compileCmdList(system.model(),
                system.state(), line, "<input>", fLogWriter);

        // compile errors?
        if (cmdList == null) {
            JOptionPane.showMessageDialog(this,
                    "One of the values you entered contains an error. "
                            + Options.LINE_SEPARATOR
                            + "See the Log for more information.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Iterator it = cmdList.iterator();
        while (it.hasNext()) {
            MCmd cmd = (MCmd) it.next();
            Log.trace(this, "--- Executing command: " + cmd);
            try {
                system.executeCmd(cmd);
                USEWriter.getInstance().protocol("[GUI] " + line);
                if (cmd.canUndo())
                    enableUndo(cmd.name());
            } catch (MSystemException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
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

    private ActionViewClassDiagram fActionViewClassDiagram = new ActionViewClassDiagram();

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
                path = System.getProperty("user.dir");
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
            File f = new File(path, fChooser.getSelectedFile().getName());
            Log.verbose("File " + f);

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
            super("Save script (.cmd)...", new ImageIcon(Options.iconDir + "Save.gif"));
        }

        public void actionPerformed(ActionEvent e) {
            String path;
            // reuse chooser if possible
            if (fChooser == null) {
                path = System.getProperty("user.dir");
                fChooser = new JFileChooser(path);
                ExtFileFilter filter = new ExtFileFilter("cmd", "USE scripts");
                fChooser.setFileFilter(filter);
                fChooser.setDialogTitle("Save script");
            }
            int returnVal = fChooser.showSaveDialog(MainWindow.this);
            if (returnVal != JFileChooser.APPROVE_OPTION)
                return;

            path = fChooser.getCurrentDirectory().toString();
            String filename = fChooser.getSelectedFile().getName();
            if (!filename.endsWith(".cmd")) filename += ".cmd";
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
                fSession.system().writeUSEcmds(out);
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
                ExtFileFilter filter = new ExtFileFilter("protocol",
                        "USE protocols");
                fChooser.setFileFilter(filter);
                fChooser.setDialogTitle("Save protocol");
            }
            int returnVal = fChooser.showSaveDialog(MainWindow.this);
            if (returnVal != JFileChooser.APPROVE_OPTION)
                return;

            path = fChooser.getCurrentDirectory().toString();
            String filename = fChooser.getSelectedFile().getName();
            if (!filename.endsWith(".protocol")) filename += ".protocol";
            File f = new File(path, filename);
            Log.verbose("File " + f);

            if (f.exists()) {
                int n = JOptionPane.showConfirmDialog(MainWindow.this,
                        "Overwrite existing file " + f + "?", "Please confirm",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (n != JOptionPane.YES_OPTION)
                    return;
            }

            try {
                // OldUSEWriter.getInstance().writeProtocolFile( f );
                FileWriter fw = new FileWriter(f);
                USEWriter.getInstance().writeProtocolFile(fw);
                fw.flush();
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
            try {
                fSession.system().undoCmd();
                String name = fSession.system().nextUndoableCmdName();
                if (name != null)
                    enableUndo(name);
                else
                    disableUndo();
            } catch (MSystemException ex) {
                JOptionPane.showMessageDialog(MainWindow.this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
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
            NewClassDiagramView cdv = new NewClassDiagramView(MainWindow.this,
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
            });
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            // c.add(new JScrollPane(cdv), BorderLayout.CENTER);
            c.add(cdv, BorderLayout.CENTER);
            addNewViewFrame(f);
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
            NewObjectDiagramView odv = new NewObjectDiagramView(
                    MainWindow.this, fSession.system());
            ViewFrame f = new ViewFrame("Object diagram", odv,
                    "ObjectDiagram.gif");
            // give some help information
            f.addInternalFrameListener(new InternalFrameAdapter() {
                public void internalFrameActivated(InternalFrameEvent ev) {
                    fStatusBar.showTmpMessage("Use left mouse button to move "
                            + "objects, right button for popup menu.");
                }

                public void internalFrameDeactivated(InternalFrameEvent ev) {
                    fStatusBar.clearMessage();
                }
            });
            JComponent c = (JComponent) f.getContentPane();
            c.setLayout(new BorderLayout());
            // c.add(new JScrollPane(odv), BorderLayout.CENTER);
            c.add(odv, BorderLayout.CENTER);
            addNewViewFrame(f);
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
     * Show class diagram.
     */
    private class ActionViewClassDiagram extends AbstractAction {
        ActionViewClassDiagram() {
            super("Class diagram");
        }

        public void actionPerformed(ActionEvent ev) {
            // FIXME: ClassDiagramView cdv = new
            // ClassDiagramView(fSession.system().model());
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
    // private void createInitialViews() {
    // ActionEvent ev =
    // new ActionEvent(this, 0, null);
    // fActionViewCreateObjectCount.actionPerformed(ev);
    // fActionViewCreateLinkCount.actionPerformed(ev);
    // fActionViewCreateObjectDiagram.actionPerformed(ev);
    // fActionViewCreateClassInvariant.actionPerformed(ev);
    // fActionViewTile.actionPerformed(ev);
    // fDesk.revalidate();
    // }
    public static MainWindow create(Session session) {
        // Object[] arr =
        // GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        // //Object[] arr =
        // GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        // for (int i = 0; i < arr.length; i++) {
        // System.out.println(arr[i]);
        // }

        final MainWindow win = new MainWindow(session);
        win.pack();
        // win.createInitialViews();
        win.setVisible(true);
        return win;
    }
}
