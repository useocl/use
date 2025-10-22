package org.tzi.use.main.gui;

import org.tzi.use.main.gui.fx.MainJavaFX;
import org.tzi.use.main.gui.swing.MainSwing;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        boolean useJavaFX = false;
        // cleanedArgs is needed to avoid giving -jfx into the MainJavaFX,
        // because Application.launch() tries to parse all arguments as JavaFX-compatible
        // and throws an error if it encounters an unknown one (like -jfx).
        List<String> cleanedArgs = new ArrayList<>();
        for (String arg : args) {
            if (arg.equalsIgnoreCase("-jfx")) {
                useJavaFX = true;
            } else {
                cleanedArgs.add(arg);
            }
        }

        if (useJavaFX) {
            MainJavaFX.launchApp(cleanedArgs.toArray(new String[0]));
        } else {
            new MainSwing().launch(cleanedArgs.toArray(new String[0]));
        }
    }
}
