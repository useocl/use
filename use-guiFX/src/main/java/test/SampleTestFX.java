package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.tzi.use.config.Options;
import org.tzi.use.util.USEWriter;

import java.util.Objects;


public class SampleTestFX extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {


//        if (!Options.disableExtensions) {
//            ExtensionManager.EXTENSIONS_FOLDER = Options.homeDir + "/oclextensions";
//            ExtensionManager.getInstance().loadExtensions();
//        }

        /*Session session = new Session();
        IRuntime pluginRuntime = null;
        MModel model = null;
        MSystem system = null;

        if (!Options.disableExtensions) {
            ExtensionManager.EXTENSIONS_FOLDER = Options.homeDir + "/oclextensions";
            ExtensionManager.getInstance().loadExtensions();
        }

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
                        new Class[] { Path.class });
                pluginRuntime = (IRuntime) run.invoke(null,
                        new Object[] { pluginDirURL });
                Log.debug("Starting plugin runtime, got class ["
                        + pluginRuntime.getClass() + "]");
            } catch (Exception e) {
                e.printStackTrace();
                Log.error("FATAL ERROR.");
                System.exit(1);
            }
        }

        // compile spec if filename given as argument
        if (Options.specFilename != null) {
            try (FileInputStream specStream = new FileInputStream(Options.specFilename)){
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

        // create thread for shell
        Shell.createInstance(session, pluginRuntime);
        Shell sh = Shell.getInstance();
        Thread t = new Thread(sh);
        t.start();

        // wait on exit from shell (this thread never returns)
        try {
            t.join();
        } catch (InterruptedException ex) {
            // ignored
        }

        // Generieren der CSS Datei
        CssEditor.editCssWithProperties(
                "C:\\Users\\akifn\\Desktop\\MyProjects\\java\\useTesting\\use-core\\src\\main\\resources\\etc\\use.properties",
                "C:\\Users\\akifn\\Desktop\\MyProjects\\java\\useTesting\\use-guiFX\\src\\main\\resources\\styles\\template.css",
                "C:\\Users\\akifn\\Desktop\\MyProjects\\java\\useTesting\\use-guiFX\\src\\main\\resources\\styles\\style.css"
        );*/

        // Load the main window FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Parent root = loader.load();

        // Set up the scene
        Scene scene = new Scene(root, 900.0, 620.0);

        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

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

    }


    public static void main(String[] args) {

        // set System.out to the OldUSEWriter to protocol the output.
        System.setOut(USEWriter.getInstance().getOut());
        // set System.err to the OldUSEWriter to protocol the output.
        System.setErr(USEWriter.getInstance().getErr());

        // read and set global options, setup application properties
        Options.processArgs(args);

        // Launch the JavaFX application
        launch(args);
    }
}
