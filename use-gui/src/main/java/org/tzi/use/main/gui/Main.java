package org.tzi.use.main.gui;

import org.tzi.use.main.gui.fx.MainJavaFX;
import org.tzi.use.main.gui.swing.MainSwing;
import org.tzi.use.util.USEWriter;
import org.tzi.use.uml.api.ExpressionFactoryProvider;
import org.tzi.use.uml.api.InvariantExpressionFactoryProvider;
import org.tzi.use.uml.api.VarDeclFactoryProvider;
import org.tzi.use.uml.ocl.OclExpressionFactoryAdapter;
import org.tzi.use.uml.ocl.expr.OclInvariantExpressionFactory;
import org.tzi.use.uml.ocl.expr.OclVarDeclFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialisierung der API-Expression-Factories, bevor irgendein mm-/parser-Code läuft
        ExpressionFactoryProvider.set(new OclExpressionFactoryAdapter());
        InvariantExpressionFactoryProvider.set(new OclInvariantExpressionFactory());
        VarDeclFactoryProvider.set(new OclVarDeclFactory());

        // set System.out to the USEWriter to protocol the output.
        System.setOut(USEWriter.getInstance().getOut());
        // set System.err to the USEWriter to protocol the output.
        System.setErr(USEWriter.getInstance().getErr());

        boolean useJavaFX = false;
        // cleanedArgs is needed to avoid giving -jfx into the MainJavaFX,
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

        if (useJavaFX) {
            MainJavaFX.launchApp(cleanedArgs.toArray(new String[0]));
        } else {
            new MainSwing().launch(cleanedArgs.toArray(new String[0]));
        }
    }
}
