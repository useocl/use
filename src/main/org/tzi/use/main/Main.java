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

package org.tzi.use.main;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Method;

import javax.swing.ImageIcon;
import javax.swing.UIDefaults;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.tzi.use.config.Options;
import org.tzi.use.main.shell.Shell;
import org.tzi.use.parser.use.USECompiler;
import org.tzi.use.uml.mm.MMPrintVisitor;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.util.Log;
import org.tzi.use.util.USEWriter;

/**
 * Main class.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Mark Richters
 */
public final class Main {

    // utility class
    private Main() {
    }

    private static void initGUIdefaults() {
        MetalLookAndFeel.setCurrentTheme(new MyTheme());
    }

    public static void main(String args[]) {
        // set System.out to the OldUSEWriter to protocol the output.
        System.setOut(USEWriter.getInstance().getOut());
        // set System.err to the OldUSEWriter to protocol the output.
        System.setErr(USEWriter.getInstance().getErr());

        // read and set global options, setup application properties
        Options.processArgs(args);
        if (Options.doGUI) {
            initGUIdefaults();
        }

        Session session = new Session();
        MModel model = null;
        MSystem system = null;

        // compile spec if filename given as argument
        if (Options.specFilename != null) {
            Reader r = null;
            try {
                Log.verbose("compiling specification...");
                r = new BufferedReader(new FileReader(Options.specFilename));
                model = USECompiler.compileSpecification(r,
                        Options.specFilename, new PrintWriter(System.err),
                        new ModelFactory());
            } catch (FileNotFoundException e) {
                Log.error("File `" + Options.specFilename + "' not found.");
                System.exit(1);
            } finally {
                if (r != null)
                    try {
                        r.close();
                    } catch (IOException ex) {
                        // ignored
                    }
            }

            // compile errors?
            if (model == null) {
                System.exit(1);
            }

            if (Options.compileOnly) {
                Log.verbose("no errors.");
                if (Options.compileAndPrint) {
                    MMVisitor v = new MMPrintVisitor(new PrintWriter(
                            System.out, true));
                    model.processWithVisitor(v);
                }
                System.exit(0);
            }

            // print some info about model
            Log.verbose(model.getStats());

            // create system
            system = new MSystem(model);
            session.setSystem(system);
        } else {
	  model = new ModelFactory().createModel("empty model");
	  system = new MSystem(model);
	  Log.verbose("using empty model.");
	  session.setSystem(system);
	} 

        if (Options.doGUI) {
            Class mainWindowClass = null;
            try {
                mainWindowClass = Class
                        .forName("org.tzi.use.gui.main.MainWindow");
            } catch (ClassNotFoundException e) {
                Log.error("Could not load GUI. Probably use-gui-...jar is missing.\n" + 
                          "Try starting use with -nogui switch.\n" +
                          e.getMessage());
                System.exit(1);
            }
            try {
                Method create = mainWindowClass.getMethod("create",
                        new Class[] { Session.class });
                create.invoke(null, new Object[] { session });
            } catch (Exception e) {
                Log.error("FATAL ERROR.", e);
                System.exit(1);
            }
        }

        //      create thread for shell
        // Shell sh = new Shell(session);
        Shell sh = Shell.getInstance(session);
        Thread t = new Thread(sh);
        t.start();

        // wait on exit from shell (this thread never returns)
        try {
            t.join();
        } catch (InterruptedException ex) {
            // ignored
        }
    }
}

/**
 * A theme with full control over fonts and customized tree display.
 */
class MyTheme extends DefaultMetalTheme {
    private FontUIResource controlFont;

    private FontUIResource systemFont;

    private FontUIResource userFont;

    private FontUIResource smallFont;

    MyTheme() {
        // System.out.println("font: " + Font.getFont("use.gui.controlFont"));
        controlFont = new FontUIResource(Font.getFont("use.gui.controlFont",
                super.getControlTextFont()));
        systemFont = new FontUIResource(Font.getFont("use.gui.systemFont",
                super.getSystemTextFont()));
        userFont = new FontUIResource(Font.getFont("use.gui.userFont", super
                .getUserTextFont()));
        smallFont = new FontUIResource(Font.getFont("use.gui.smallFont", super
                .getSubTextFont()));
    }

    public String getName() {
        return "USE";
    }

    public FontUIResource getControlTextFont() {
        return controlFont;
    }

    public FontUIResource getSystemTextFont() {
        return systemFont;
    }

    public FontUIResource getUserTextFont() {
        return userFont;
    }

    public FontUIResource getMenuTextFont() {
        return controlFont;
    }

    public FontUIResource getWindowTitleFont() {
        return controlFont;
    }

    public FontUIResource getSubTextFont() {
        return smallFont;
    }

    public void addCustomEntriesToTable(UIDefaults table) {
        initIcon(table, "Tree.expandedIcon", "TreeExpanded.gif");
        initIcon(table, "Tree.collapsedIcon", "TreeCollapsed.gif");
        initIcon(table, "Tree.leafIcon", "TreeLeaf.gif");
        initIcon(table, "Tree.openIcon", "TreeOpen.gif");
        initIcon(table, "Tree.closedIcon", "TreeClosed.gif");
        table.put("Desktop.background", table.get("Menu.background"));
    }

    private void initIcon(UIDefaults table, String property, String iconFilename) {
        table.put(property, new ImageIcon(Options.iconDir + iconFilename));
    }
}
