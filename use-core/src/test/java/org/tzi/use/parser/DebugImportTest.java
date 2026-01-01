package org.tzi.use.parser;

import junit.framework.TestCase;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;

public class DebugImportTest extends TestCase {

    public void testDebugImports() throws Exception {
        File testPath = new File(ClassLoader.getSystemResource("org/tzi/use/parser").toURI());
        String[] files = {"t31_imports.use", "t37_imports.use"};

        for (String name : files) {
            File specFile = new File(testPath, name);
            System.err.println("--- Compiling: " + specFile + " ---");
            try (InputStream in = new FileInputStream(specFile)) {
                PrintWriter err = new PrintWriter(System.err, true);
                MModel model = org.tzi.use.parser.use.USECompiler.compileSpecification(in, specFile.getName(), specFile.toPath().toUri(), err, new ModelFactory());
                System.err.println("Result model: " + (model == null ? "<null>" : model.name()));
                System.err.println();
            }
        }
    }
}
