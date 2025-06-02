package org.tzi.use.main.gui.swing;

import org.tzi.use.config.Options;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.main.Session;
import org.tzi.use.main.runtime.IRuntime;
import org.tzi.use.main.shell.Shell;
import org.tzi.use.parser.use.USECompiler;
import org.tzi.use.uml.mm.MMPrintVisitor;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.extension.ExtensionManager;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.util.Log;

import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.file.Path;

public class MainSwing {

    public void launch(String[] args) {

        Options.processArgs(args);

        if (Options.doGUI) {
            MetalLookAndFeel.setCurrentTheme(new MyTheme());
        }

        Session session = new Session();
        IRuntime pluginRuntime = null;
        MModel model = null;
        MSystem system = null;

        if (!Options.disableExtensions) {
            ExtensionManager.EXTENSIONS_FOLDER = Options.homeDir + Options.FILE_SEPARATOR + "oclextensions";
            ExtensionManager.getInstance().loadExtensions();
        }

        // Plugin Framework
        if (Options.doPLUGIN) {
            Path pluginDirURL = Options.pluginDir;
            Log.verbose("Plugin path: [" + pluginDirURL + "]");
            try {
                Class<?> mainPluginRuntimeClass = Class.forName("org.tzi.use.runtime.MainPluginRuntime");
                Method run = mainPluginRuntimeClass.getMethod("run", Path.class);
                pluginRuntime = (IRuntime) run.invoke(null, pluginDirURL);
                Log.debug("Starting plugin runtime, got class [" + pluginRuntime.getClass() + "]");
            } catch (Exception e) {
                e.printStackTrace();
                Log.error("FATAL ERROR.");
                System.exit(1);
            }
        }

        if (Options.specFilename != null) {
            Path file = Path.of(Options.specFilename);
            try (FileInputStream specStream = new FileInputStream(Options.specFilename)) {
                Log.verbose("compiling specification...");
                model = USECompiler.compileSpecification(specStream, file.getFileName().toString(),
                        new PrintWriter(System.err), new ModelFactory());
            } catch (FileNotFoundException e) {
                Log.error("File `" + Options.specFilename + "' not found.");
                if (Options.integrationTestMode) return;
                System.exit(1);
            } catch (IOException ignored) {}

            if (model == null) {
                if (Options.integrationTestMode) return;
                System.exit(1);
            }

            if (!Options.quiet)
                Options.setLastDirectory(new java.io.File(Options.specFilename).getAbsoluteFile().toPath().getParent());

            if (!Options.testMode)
                Options.getRecentFiles().push(Options.specFilename);

            if (Options.compileOnly) {
                Log.verbose("no errors.");
                if (Options.compileAndPrint) {
                    MMVisitor v = new MMPrintVisitor(new PrintWriter(System.out, true));
                    model.processWithVisitor(v);
                }
                System.exit(0);
            }

            Log.verbose(model.getStats());

            system = new MSystem(model);
        }

        session.setSystem(system);

        if (Options.doGUI) {
            if (pluginRuntime == null) {
                Log.debug("Starting gui without plugin runtime!");
                MainWindow.create(session);
            } else {
                Log.debug("Starting gui with plugin runtime.");
                MainWindow.create(session, pluginRuntime);
            }
        }

        Shell.createInstance(session, pluginRuntime);
        Shell sh = Shell.getInstance();
        Thread t = new Thread(sh);
        t.start();

        try {
            t.join();
        } catch (InterruptedException ignored) {}
    }

    private static class MyTheme extends DefaultMetalTheme {
        private final FontUIResource controlFont = new FontUIResource("Dialog", Font.PLAIN, 12);
        private final FontUIResource systemFont = new FontUIResource("Dialog", Font.PLAIN, 12);
        private final FontUIResource userFont = new FontUIResource("Dialog", Font.PLAIN, 12);
        private final FontUIResource smallFont = new FontUIResource("Dialog", Font.PLAIN, 10);

        public String getName() { return "USE"; }
        public FontUIResource getControlTextFont() { return controlFont; }
        public FontUIResource getSystemTextFont() { return systemFont; }
        public FontUIResource getUserTextFont() { return userFont; }
        public FontUIResource getMenuTextFont() { return controlFont; }
        public FontUIResource getWindowTitleFont() { return controlFont; }
        public FontUIResource getSubTextFont() { return smallFont; }

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
}
