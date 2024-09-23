package org.tzi.use.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.tzi.use.config.Options;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.MainWindowFX;
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
import org.tzi.use.util.USEWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.file.Path;


public class MainFX extends Application {

    private Session session;
    private IRuntime pluginRuntime;
    private MModel model;
    private MSystem system;

    public static void main(String[] args) {

        // set System.out to the OldUSEWriter to protocol the output.
        System.setOut(USEWriter.getInstance().getOut());
        // set System.err to the OldUSEWriter to protocol the output.
        System.setErr(USEWriter.getInstance().getErr());

        // read and set global options, setup application properties
        Options.processArgs(args);

        // Launch the JavaFX application runs start
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize the session
        session = new Session();
        pluginRuntime = null;
        model = null;
        system = null;

        // Initialize the application settings
        initExtensions();
        initPlugins();
        compileSpecification(primaryStage);

        // Start shell in a separate thread
        startShell();

        // Exit on shell termination
        primaryStage.setOnCloseRequest(event -> System.exit(0));

    }

    private void initExtensions() {
        if (!Options.disableExtensions) {
            ExtensionManager.EXTENSIONS_FOLDER = Options.homeDir + "/oclextensions";
            ExtensionManager.getInstance().loadExtensions();
        }
    }

    private void initPlugins() {
        // Plugin Framework
        if (Options.doPLUGIN) {
            // create URL from plugin directory
            Path pluginDirURL = Options.pluginDir;
            Log.verbose("Plugin path: [" + pluginDirURL + "]");
            Class<?> mainPluginRuntimeClass = null;
            try {
                mainPluginRuntimeClass = Class
                        .forName("org.tzi.use.runtime.MainPluginRuntime");
            } catch (ClassNotFoundException e) {
                Log
                        .error("Could not load PluginRuntime. Probably use-runtime-...jar is missing.\n"
                                + "Try starting use with -noplugins switch.\n"
                                + e.getMessage());
                System.exit(1);
            }
            try {
                Method run = mainPluginRuntimeClass.getMethod("run",
                        new Class[]{Path.class});
                pluginRuntime = (IRuntime) run.invoke(null,
                        new Object[]{pluginDirURL});
                Log.debug("Starting plugin runtime, got class ["
                        + pluginRuntime.getClass() + "]");
            } catch (Exception e) {
                e.printStackTrace();
                Log.error("FATAL ERROR.");
                System.exit(1);
            }
        }
    }

    private void compileSpecification(Stage primaryStage) throws IOException {
        // compile spec if filename given as argument
        if (Options.specFilename != null) {
            try (FileInputStream specStream = new FileInputStream(Options.specFilename)) {
                Log.verbose("compiling specification...");
                model = USECompiler.compileSpecification(specStream,
                        Options.specFilename, new PrintWriter(System.err),
                        new ModelFactory());
            } catch (FileNotFoundException e) {
                Log.error("File `" + Options.specFilename + "' not found.");
                System.exit(1);
            } catch (IOException e1) {
                // close failed
            }

            // Check for compilation errors
            if (model == null) {
                System.exit(1);
            }

            if (!Options.quiet) {
                Options.setLastDirectory(new java.io.File(Options.specFilename).getAbsoluteFile().toPath().getParent());
            }
            if (!Options.testMode)
                Options.getRecentFiles().push(Options.specFilename);

            if (Options.compileOnly) {
                Log.verbose("no errors.");
                if (Options.compileAndPrint) {
                    MMVisitor v = new MMPrintVisitor(new PrintWriter(
                            System.out, true));
                    model.processWithVisitor(v);
                }
                System.exit(0);
            }

            Log.verbose(model.getStats());

            // Create system from the model
            System.out.println("System: " + system);
            system = new MSystem(model);
            System.out.println("System: " + system);
        }
        session.setSystem(system);

        showMainWindow(primaryStage);

        startShell();
    }

    private void createPrimaryStage(Stage primaryStage) throws IOException {

        try {

            // Load the main window FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
            Parent root = loader.load();
            // Set up the scene
            Scene scene = new Scene(root, 900.0, 620.0);

            //scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

            // Set the stage properties
            primaryStage.setTitle("USE");
            primaryStage.getIcons().add(new Image("images/use1.gif"));
            primaryStage.setScene(scene);

            // Center the stage on the screen
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            double centerX = primaryScreenBounds.getMinX() + (primaryScreenBounds.getWidth() - scene.getWidth()) / 2;
            double centerY = primaryScreenBounds.getMinY() + (primaryScreenBounds.getHeight() - scene.getHeight()) / 2;
            primaryStage.setX(centerX);
            primaryStage.setY(centerY);

            // Show the stage
            primaryStage.show();

        } catch (IOException e) {

            Log.error("Failed to load FXML: " + e.getMessage());
            e.printStackTrace();

        }

    }

    private void showMainWindow(Stage primaryStage) throws IOException {
        if (Options.doGUI) {
            MainWindowFX mainWindowFX = new MainWindowFX();
            mainWindowFX.setSession(session);
            createPrimaryStage(primaryStage);

            if (pluginRuntime != null) {
                Log.debug("Starting gui with plugin runtime.");
                mainWindowFX.setPluginRuntime(pluginRuntime);
            } else {
                Log.debug("Starting gui without plugin runtime!");
            }
        }
    }

    private void startShell() {
        // create thread for shell
        Shell.createInstance(session, pluginRuntime);
        Shell sh = Shell.getInstance();
        Thread t = new Thread(sh);
        t.start(); // Start the shell thread without blocking the main thread

    }

}
