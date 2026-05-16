package org.tzi.use.main.gui;

import org.tzi.use.util.USEWriter;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // set System.out to the USEWriter to protocol the output.
        System.setOut(USEWriter.getInstance().getOut());
        // set System.err to the USEWriter to protocol the output.
        System.setErr(USEWriter.getInstance().getErr());

        boolean useJavaFX = false;
        // cleanedArgs is needed to avoid passing -jfx to the JavaFX launcher,
        // because Application.launch() tries to parse all arguments as JavaFX compatible
        // and throws an error if it encounters an unknown one (like -jfx).
        List<String> cleanedArgs = new ArrayList<>();
        for (String arg : args) {
            if (arg.equalsIgnoreCase("-jfx")) {
                useJavaFX = true;
            } else {
                cleanedArgs.add(arg);
            }
        }

        // Resolve the launcher impl by FQN at runtime so this bootstrap
        // class keeps no static dependency on gui.. — ArchUnit only sees
        // the Launcher interface (which lives in main.gui).
        String fqcn = useJavaFX
                ? "org.tzi.use.gui.mainFX.JavaFXLauncher"
                : "org.tzi.use.gui.main.SwingLauncher";

        try {
            Launcher launcher = (Launcher) Class.forName(fqcn)
                    .getDeclaredConstructor()
                    .newInstance();
            launcher.launchApp(cleanedArgs.toArray(new String[0]));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to load launcher: " + fqcn, e);
        }
    }
}
