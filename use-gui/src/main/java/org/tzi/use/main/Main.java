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

package org.tzi.use.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tzi.use.config.Options;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.main.runtime.IRuntime;
import org.tzi.use.main.shell.Shell;
import org.tzi.use.output.UserOutput;
import org.tzi.use.output.DefaultUserOutput;
import org.tzi.use.parser.use.USECompiler;
import org.tzi.use.uml.mm.MMPrintVisitor;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.extension.ExtensionManager;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.util.USEWriter;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.file.Path;

/**
 * Main class.
 *
 * @author Mark Richters
 */
public final class Main {

	// utility class
	private Main() {
	}

	private static void initGUIdefaults() {
		MetalLookAndFeel.setCurrentTheme(new MyTheme());
	}

	private static final Logger log = LogManager.getLogger(Main.class.getName());

	public static void main(String[] args) {
		// set System.out to the OldUSEWriter to protocol the output.
		System.setOut(USEWriter.getInstance().getOut());
		// set System.err to the OldUSEWriter to protocol the output.
		System.setErr(USEWriter.getInstance().getErr());

		UserOutput out = DefaultUserOutput.createSystemOutOutput();

		// read and set global options, setup application properties
		Options.processArgs(args, out);
		if (Options.doGUI) {
			initGUIdefaults();
		}

		Options.configureOutput(out);

		Session session = new Session();
		IRuntime pluginRuntime = null;
		MModel model = null;
		MSystem system = null;

		if (!Options.disableExtensions) {
			ExtensionManager.EXTENSIONS_FOLDER = Options.homeDir + "/oclextensions";
			ExtensionManager.getInstance().loadExtensions(out);
		}
		
		// Plugin Framework
		if (Options.doPLUGIN) {
			// create URL from plugin directory
			Path pluginDirURL = Options.pluginDir;
			out.printlnVerbose("Plugin path: [" + pluginDirURL + "]");
			Class<?> mainPluginRuntimeClass = null;
			try {
				mainPluginRuntimeClass = Class.forName("org.tzi.use.runtime.MainPluginRuntime");
			} catch (ClassNotFoundException e) {
				out.printlnError("Could not load PluginRuntime. Probably use-runtime-...jar is missing.\n"
								+ "Try starting use with -noplugins switch.\n"
								+ e.getMessage());
				System.exit(1);
			}
			try {
				Method run = mainPluginRuntimeClass.getMethod("run", Path.class, UserOutput.class);
				pluginRuntime = (IRuntime) run.invoke(null,
						new Object[] { pluginDirURL, out });
				log.debug("Starting plugin runtime, got class [" + pluginRuntime.getClass() + "]");
			} catch (Exception e) {
				log.fatal("Error running plugin", e);
				out.printlnError("Fatal error while loading plugin runtime. Exiting now. See logs for details.");
				System.exit(1);
			}
		}

		// compile spec if filename given as argument
		if (Options.specFilename != null) {
			try (FileInputStream specStream = new FileInputStream(Options.specFilename)){
				out.printlnVerbose("compiling specification...");
				model = USECompiler.compileSpecification(specStream,
						Options.specFilename, out,
						new ModelFactory());
			} catch (FileNotFoundException e) {
				out.printlnError("File `" + Options.specFilename + "' not found.");
				System.exit(1);
			} catch (IOException e1) {
				// close failed
			}

			// compile errors?
			if (model == null) {
				System.exit(1);
			}

			if(!Options.quiet){
				Options.setLastDirectory(new java.io.File(Options.specFilename).getAbsoluteFile().toPath().getParent());
			}
			if (!Options.testMode)
				Options.getRecentFiles().push(Options.specFilename);
			
			if (Options.compileOnly) {
				out.printlnVerbose("no errors.");
				if (Options.compileAndPrint) {
					//TODO: Replace System.out with new output
					MMVisitor v = new MMPrintVisitor(new PrintWriter(
							System.out, true));
					model.processWithVisitor(v);
				}
				System.exit(0);
			}

			// print some info about model
			out.printlnVerbose(model.getStats());

			// create system
			system = new MSystem(model);
		}
		session.setSystem(system);

		if (Options.doGUI) {
			if (pluginRuntime == null) {
				log.debug("Starting gui without plugin runtime!");
				MainWindow.create(session);
			} else {
				log.debug("Starting gui with plugin runtime.");
				MainWindow.create(session, pluginRuntime);
			}
		}

		// create thread for shell
		Shell.createInstance(session, pluginRuntime, out);
		Shell sh = Shell.getInstance();
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
		table.put(property, new ImageIcon(getClass().getResource("/images/" + iconFilename)));
	}

}
