package org.tzi.use.main.gui.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.tzi.use.config.Options;
import org.tzi.use.gui.mainFX.MainWindow;
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
import org.tzi.use.util.USEWriter;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Objects;

public class JavaFXAppLauncher extends Application {

    private Session session;
    private IRuntime pluginRuntime;
    private MModel model;
    private MSystem system;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // set up output redirection
        System.setOut(USEWriter.getInstance().getOut());
        System.setErr(USEWriter.getInstance().getErr());

        // read global options
        Options.processArgs(getParameters().getRaw().toArray(new String[0]));

        session = new Session();
        pluginRuntime = null;
        model = null;
        system = null;

        initExtensions();
        initPlugins();
        compileSpecification(primaryStage);

        // Start shell in separate thread
        startShell();

        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }

    private void initExtensions() {
        if (!Options.disableExtensions) {
            ExtensionManager.EXTENSIONS_FOLDER = Options.homeDir + Options.FILE_SEPARATOR + "oclextensions";
            ExtensionManager.getInstance().loadExtensions();
        }
    }

    private void initPlugins() {
        if (Options.doPLUGIN) {
            Path pluginDirURL = Options.pluginDir;
            Log.verbose("Plugin path: [" + pluginDirURL + "]");
            try {
                Class<?> clazz = Class.forName("org.tzi.use.runtime.MainPluginRuntime");
                Method run = clazz.getMethod("run", Path.class);
                pluginRuntime = (IRuntime) run.invoke(null, pluginDirURL);
                Log.debug("Starting plugin runtime, got class [" + pluginRuntime.getClass() + "]");
            } catch (Exception e) {
                e.printStackTrace();
                Log.error("FATAL ERROR.");
                System.exit(1);
            }
        }
    }

    private void compileSpecification(Stage primaryStage) throws IOException {
        if (Options.specFilename != null) {
            Path file = Path.of(Options.specFilename);
            try (FileInputStream specStream = new FileInputStream(file.toFile())) {
                Log.verbose("compiling specification...");
                model = USECompiler.compileSpecification(specStream, file.getFileName().toString(),
                        new PrintWriter(System.err), new ModelFactory());
            } catch (FileNotFoundException e) {
                Log.error("File `" + Options.specFilename + "' not found.");
                if (!Options.integrationTestMode) System.exit(1);
                return;
            }

            if (model == null) {
                if (!Options.integrationTestMode) System.exit(1);
                return;
            }

            if (!Options.quiet) {
                Options.setLastDirectory(file.getParent());
            }
            if (!Options.testMode) {
                Options.getRecentFiles().push(Options.specFilename);
            }

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
        showMainWindow(primaryStage);
    }

    private void showMainWindow(Stage primaryStage) throws IOException {
        if (Options.doGUI) {
            MainWindow mainWindowFX = new MainWindow();
            mainWindowFX.setSession(session);
            mainWindowFX.setPluginRuntime(pluginRuntime);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 900, 620);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/style.css")).toExternalForm());

            primaryStage.setTitle("USE");
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/use_icon.gif"))));
            primaryStage.setScene(scene);

            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            primaryStage.setX(bounds.getMinX() + (bounds.getWidth() - scene.getWidth()) / 2);
            primaryStage.setY(bounds.getMinY() + (bounds.getHeight() - scene.getHeight()) / 2);
            primaryStage.show();

            mainWindowFX.setPrimaryStage(primaryStage);

            if (pluginRuntime != null) {
                Log.debug("Starting gui with plugin runtime.");
                mainWindowFX.setPluginRuntime(pluginRuntime);
            } else {
                Log.debug("Starting gui without plugin runtime!");
            }
        }
    }

    private void startShell() {
        Shell.createInstance(session, pluginRuntime);
        Thread t = new Thread(Shell.getInstance());
        t.start();
    }
}
