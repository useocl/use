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

/*
// $Id: USECompilerTest.java 5574 2015-03-09 15:07:18Z fhilken $
 */
package org.tzi.use.parser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.tzi.use.config.Options;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.parser.use.USECompiler;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.util.SuffixFileFilter;

/**
 *  Test USECompiler class. The TestDriver reads all files with extension ".use"
 *  and processes them with the parser. If a file <code>t.use</code> contains
 *  expected errors there should be a file <code>t.fail</code> with the expected
 *  output messages. There are four possible results:
 *  <ol>
 *    <li> file parses ok and no failure file exists: PASSED.</li>
 *    <li> file parses ok and a failure file exists: FAILURE.</li>
 *    <li> file does not parse and a failure file exists: PASSED if the actual
 *    output matches the expected output from the failure file, otherwise
 *    FAILURE.</li>
 *    <li> file does not parse and no failure file exists: FAILURE.</li>
 *  </ol>
 *
 *
 * @created    May 21, 2004
 * @author     Mark Richters
 */

public class USECompilerTest extends TestCase {
    // Set this to true to see more details about what is tested.
    private static final boolean VERBOSE = false;

    private static File TEST_PATH;
    private static File EXAMPLES_PATH;
    private static File TEST_EXPR_FILE;

    static {
        try {
            TEST_PATH = new File(ClassLoader.getSystemResource("org/tzi/use/parser").toURI());
            EXAMPLES_PATH = new File(ClassLoader.getSystemResource("examples").toURI());
            TEST_EXPR_FILE = new File(ClassLoader.getSystemResource("org/tzi/use/parser/test_expr.in").toURI());
        } catch (NullPointerException | URISyntaxException e) {
            TEST_PATH = null;
            EXAMPLES_PATH = null;
            TEST_EXPR_FILE = null;
            fail("Folders including tests are missing!");
        }
    }

    // java.io has a StringWriter but we need an OutputStream for
    // System.err
    class StringOutputStream extends OutputStream {
        private StringBuilder fBuffer = new StringBuilder();


        public void write(int b) {
            fBuffer.append((char) b);
        }


        public void reset() {
            fBuffer = new StringBuilder();
        }


        public String toString() {
            return fBuffer.toString();
        }
    }


    public void testSpecification() {
        Options.explicitVariableDeclarations = false;

        List<File> fileList = getFilesMatchingSuffix(".use", 32);
        // add all the example files which should have no errors
        File[] files = EXAMPLES_PATH.listFiles( new SuffixFileFilter(".use") );
        assertNotNull(files);
        fileList.addAll(Arrays.asList(files));

        // create a new stream for capturing output on stderr
        StringOutputStream errStr = new StringOutputStream();
        PrintWriter newErr = new PrintWriter(errStr);
        // compile each file and compare with expected result
        for (File specFile : fileList) {
            String specFileName = specFile.getName();
            try {
                MModel model = compileSpecification(specFile, newErr);
                File failFile = getFailFileFromUseFile(specFileName);

                if (failFile.exists()) {
                    if (model != null) {
                        failCompileSpecSucceededButErrorsExpected(specFileName, failFile);
                    } else {
                        if (!isErrorMessageAsExpected(failFile, errStr)) {
                            failCompileSpecFailedFailFileDiffers(specFileName, errStr, failFile);
                        }
                    }
                } else {
                    if (model == null) {
                        failCompileSpecFailedWithoutFailFile(specFileName, errStr, failFile);
                    }
                }
                if (VERBOSE) {
                    System.out.println(specFileName + ": PASSED.");
                }
                errStr.reset();
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void testExpression() throws IOException {
        MModel model = new ModelFactory().createModel("Test");
        // read expressions and expected results from file
        BufferedReader in = new BufferedReader(new FileReader(TEST_EXPR_FILE));
        int lineNr = 0;

        while (true) {
            String line = in.readLine();
            lineNr++;

            if (line == null) {
                break;
            }
            if (line.startsWith("##")) {
                if (VERBOSE) {
                    System.out.println("testing " + line.substring(2).trim());
                }
                continue;
            }
            if (line.length() == 0 || line.startsWith("#")) {
                continue;
            }

            String expStr = line;
            while (true) {
                line = in.readLine();
                lineNr++;

                if (line == null) {
                    in.close();
                    throw new RuntimeException("missing result line");
                }
                if (line.startsWith("-> ")) {
                    break;
                }
                expStr += " " + line.trim();
            }
            String resultStr = line.substring(3);

            if (VERBOSE) {
                System.out.println("expression: " + expStr);
            }

            InputStream stream = new ByteArrayInputStream(expStr.getBytes());

            Expression expr =
                    OCLCompiler.compileExpression(
                            model,
                            stream,
                            TEST_EXPR_FILE.toString(),
                            new PrintWriter(System.err),
                            new VarBindings());
            assertNotNull(expr + " compiles", expr);

            MSystemState systemState = new MSystem(model).state();

            Value val = new Evaluator().eval(expr, systemState);
            assertEquals(TEST_EXPR_FILE + ":" + lineNr + " evaluate: " + expStr, resultStr, val.toStringWithType());
        }

        in.close();
    }

    private File getFailFileFromUseFile(String specFileName) {
        // check for a failure file
        String failFileName =
                specFileName.substring(0, specFileName.length() - 4) + ".fail";
        File failFile = new File(TEST_PATH, failFileName);
        return failFile;
    }


    private void failCompileSpecFailedWithoutFailFile(String specFileName, StringOutputStream errStr, File failFile) {
        // unexpected failure
        System.err.println("#######################");
        System.err.print(errStr.toString());
        System.err.println("#######################");
        fail(
                "compilation of "
                        + specFileName
                        + " had errors, but there is "
                        + "no file `"
                        + failFile.getName()
                        + "'.");
    }


    private void failCompileSpecFailedFailFileDiffers(String specFileName, StringOutputStream errStr, File failFile) {
        System.err.println("Expected: #############");

        try (BufferedReader failReader = new BufferedReader(new FileReader(failFile))){
            while (true) {
                String line = failReader.readLine();
                if (line == null) {
                    break;
                }
                System.err.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        System.err.println("Got: ##################");
        System.err.print(errStr.toString());
        System.err.println("#######################");
        fail(
                "compilation of "
                        + specFileName
                        + " had errors, "
                        + "but the expected error output differs.");
    }


    private void failCompileSpecSucceededButErrorsExpected(String specFileName, File failFile) {
        fail(
                "compilation of "
                        + specFileName
                        + " succeeded, "
                        + "but errors were expected (see "
                        + failFile
                        + ").");
    }

    private boolean isErrorMessageAsExpected(File failFile, StringOutputStream errStr) throws FileNotFoundException {
        // check whether error output equals expected output
        String[] expect = errStr.toString().split("\n|(\r\n)");
        //                        for (int i = 0; i < expect.length; i++) {
        //                            System.out.println("[" + expect[i] + "]");
        //                        }
        int j = 0;
        boolean ok = true;
        try (BufferedReader failReader = new BufferedReader(new FileReader(failFile))){
            while (ok) {
                String line = failReader.readLine();
                if (line == null) {
                    ok = j == expect.length;
                    break;
                }
                ok = line.equals(expect[j]);
                j++;
            }
        } catch (IOException ex) {
            ok = false;
        } catch (IndexOutOfBoundsException ex) {
            ok = false;
        }
        return ok;
    }

    private List<File> getFilesMatchingSuffix(String suffix, int expected) {
        List<File> fileList = new ArrayList<File>();
        File dir = new File(TEST_PATH.toURI());
        File[] files = dir.listFiles( new SuffixFileFilter(suffix) );
        assertNotNull(files);
        fileList.addAll(Arrays.asList(files));

        // make sure we don't silently miss the input files
        assertEquals(
                "make sure that all test files can be found "
                        + " (or update expected number if you have added test files)",
                expected,
                fileList.size());

        return fileList;
    }


    private MModel compileSpecification(File specFile, PrintWriter newErr) throws FileNotFoundException {
        MModel result = null;

        try (FileInputStream specStream = new FileInputStream(specFile)){
            result = USECompiler.compileSpecification(specStream,
                    specFile.getName(), newErr, new ModelFactory());
            specStream.close();
        } catch (IOException e) {
            // This can be ignored
            e.printStackTrace();
        }

        return result;
    }

}
