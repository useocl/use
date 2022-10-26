package org.tzi.use.main;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * This class implements the shell integration tests.
 *
 * These tests represent nearly the exact behavior of the
 * USE shell. Only some whitespace differences and time outputs are ignored.
 *
 * Each test consists of the following files:
 * <ol>
 *     <li>a model file (suffix <code>.use</code>) - this file provides the (possible empty) model used
 *     for the tests</li>
 *     <li>an input file (suffix: <code>.in</code>) - this file can contain any commands USE supports.
 *     The expected output must be specified by starting a line with a star <code>*</code></li>
 *     <li>any other used file from the command line, e.g., ASSL- or command-files.</li>
 * </ol>
 *
 * All <code>.use</code> and </code><code>.in</code> files must share the same name, e.g., t555.use and t555.in for test
 * case 555. These files must be placed in the folder <code>it/resources/testfiles/shell</code>.
 *
 * If an integration test fails, two additional files are created:
 * <ol>
 *     <li><code>{testcasename}.expected</code> - The expected output calculated from the <code>{testcase}.in</code>-file</li>
 *     <li><code>{testcasename}.actual</code> - The ouptut captured while running the test.</li>
 * </ol>
 * These files can be used to easily diff expected and current output.
 */
public class ShellIT {

    /**
     * This TestFactory enumerates the <code>.in</code>-files in th folder <code>testfiles/shell</code>.
     * For each file a <Code>DynamicTest</Code> is created with the name of the file.
     *
     * @return A <code>Stream</code> with one <code>DynamicTest</code> for each <code>*.in</code>-file.
     */
    @TestFactory
    public Stream<DynamicTest> evaluateExpressionFiles() {
        URL testDirURL = getClass().getClassLoader().getResource("testfiles/shell");
        Path testDirPath = null;

        if (testDirURL == null) {
            fail("Directory for shell integration tests not found!");
        }

        try {
            testDirPath = Path.of(testDirURL.toURI());
        } catch (URISyntaxException e) {
            fail("Directory for shell integration tests not found!");
        }

        try {
            return Files.walk(testDirPath).filter(
                    path -> path.getFileName().toString().endsWith(".in")
                        ).map(mapInFileToTest());
        } catch (IOException e) {
            fail("Error iterating shell integration test input files!");
        }

        return Stream.empty();
    }

    /**
     * This <code>Function</code> is used to map
     * a given testinput-file given as a <code>Path</code>
     * to a <code>DynamicTest</code>.
     *
     * @return A <code>DynamicTest</code> that uses the function <code>assertShellExpression</code>
     *         to test the given testinput file.
     */
    private Function<Path, DynamicTest> mapInFileToTest() {
        return path -> {
            final String modelFilename = path.getFileName().toString().replace(".in", ".use");
            final Path modelPath = path.resolveSibling(modelFilename);

            return DynamicTest.dynamicTest(path.getFileName().toString(), path.toUri(), () -> assertShellExpressions(path, modelPath));
        };
    }

    /**
     * This function controls the overall process for test for a single testfile.
     *
     * The process is as follow:
     * <ol>
     *     <li>a command file and the expected output are created by examining the input file (via <code>createCommandFile</code>.</li>
     *     <li>USE is executed using the <code>useFile</code> and the created command file (<code>runUSE</code>).</li>
     *     <li>The output of USE is compared to the expected output created in 1. (<code>validateOutput</code>).</li>
     * </ol>
     *
     * @param testFile <code>Path</code> to the test input file to execute.
     * @param useFile <code>Path</code> to the USE file containing the model to load for the test.
     */
    private void assertShellExpressions(Path testFile, Path useFile) {

        Path cmdFile = testFile.resolveSibling(testFile.getFileName() + ".cmd");

        List<String> expectedOutput = createCommandFile(testFile, cmdFile);

        List<String> actualOutput = runUSE(useFile, cmdFile);

        validateOutput(testFile, expectedOutput, actualOutput);
    }

    /**
     * Compares the two lists of strings <code>expectedOutput</code>
     * and <code>actualOutput</code>.
     * If they differ, two files are written at the location of the
     * <code>testFile</code>. One with the expected output (<code>.expected</code>)
     * and one with the actual output (<code>.actual</code>).
     *
     * @param testFile The <code>Path</code> to the <code>testFile</code>
     * @param expectedOutput List of strings with the expected output (one String per line)
     * @param actualOutput List of strings with the actual output (one String per line)
     */
    private void validateOutput(Path testFile, List<String> expectedOutput, List<String> actualOutput) {
        Patch<String> patch = DiffUtils.diff(expectedOutput, actualOutput);
        boolean nonWhitespaceChange = false;

        // Check if non whitespace diffs are present
        nonWhitespaceChange = patch.getDeltas().stream().anyMatch(
                (delta) -> delta.getSource().getLines().stream().anyMatch(line -> !line.isBlank()) ||
                           delta.getTarget().getLines().stream().anyMatch(line -> !line.isBlank())
        );

        if (nonWhitespaceChange) {
            StringBuilder diffMsg = new StringBuilder("USE output does not match expected output!").append(System.lineSeparator());

            diffMsg.append("Testfile: ").append(testFile).append(System.lineSeparator());

            diffMsg.append(System.lineSeparator()).append("Note: the position is not the position in the input file!");
            diffMsg.append(System.lineSeparator()).append(System.lineSeparator());

            //simple output the computed patch to console
            for (AbstractDelta<String> delta : patch.getDeltas()) {
                diffMsg.append("Diff [")
                        .append(delta.getType())
                        .append("] Source: '")
                        .append(delta.getSource().toString())
                        .append("' Target: '" )
                        .append(delta.getTarget().toString())
                        .append("'")
                        .append(System.lineSeparator());
            }

            writeToFile(expectedOutput, testFile.getParent().resolve(testFile.getFileName().toString() + ".expected" ));
            writeToFile(actualOutput, testFile.getParent().resolve(testFile.getFileName().toString() + ".actual" ));

            fail(diffMsg.toString());
        }
    }

    /**
     * Helper method that writes the list of strings <code>data</code>
     * to the file loctaed by the <code>Path</code> <code>file</code>.
     *
     * If the file is not accessible, i.e., an IOException is thrown,
     * the exceptions is cathed and the test case fails.
     * @param data The <code>List</code> of string (lines) to write.
     * @param file The path to the file to write (file is overwritten).
     */
    private void writeToFile(List<String> data, Path file) {
        try (FileWriter writer = new FileWriter(file.toFile())) {

            for (String line : data) {
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            fail("Testoutput could not be written!", e);
        }
    }

    /**
     * Creates a USE-commandfile at the position located by the path <code>cmdFile</code>.
     * The file contains all commands that are specified in the <code>inFile</code>.
     * The expected output, i.e., lines starting with a <code>*</code> are added to the list <code>expectedOutput</code>.
     *
     * @param inFile The <code>Path</code> to the test input file.
     * @param cmdFile The <code>Path</code> where to create the command file.
     * @return  A <code>List</code> which is filled with the expected output of USE.
     */
    private List<String> createCommandFile(Path inFile, Path cmdFile) {
        List<String> expectedOutput = new LinkedList<>();

        // Build USE command file and build expected output
        try (
                Stream<String> linesStream = Files.lines(inFile, StandardCharsets.UTF_8);
                FileWriter cmdWriter = new FileWriter(cmdFile.toFile(), StandardCharsets.UTF_8, false)
        ) {
            // USE writes a prompt including the filename
            String prompt = cmdFile.getFileName().toString() + "> ";

            linesStream.forEach(inputLine -> {
                if (inputLine.startsWith("*")) {
                    // Input line minus prefix(*) is expected output
                    expectedOutput.add(inputLine.substring(1).trim());
                } else if (!inputLine.startsWith("#")) { // Not a comment
                    inputLine = inputLine.trim();

                    if (inputLine.isEmpty()) {
                        return;
                    }

                    try {
                        cmdWriter.write(inputLine);
                        cmdWriter.write(System.lineSeparator());

                        expectedOutput.add((prompt + inputLine).trim());
                    } catch (IOException e1) {
                        fail("Could not write USE command file for test!", e1);
                    }
                }
            });
        } catch (IOException e) {
            fail("Could not write USE command file for test!", e);
        }

        return expectedOutput;
    }

    /**
     * Executes USE with the given <code>useFile</code> as the model
     * and the <code>cmdFile</code> to execute commands.
     * The output is captured from the output and error streams.
     *
     * @param useFile Path to the USE model to load on startup
     * @param cmdFile Path to the commands file to execute
     *
     * @return A <code>List</code> of strings. Each string is one line of output.
     */
    private List<String> runUSE(Path useFile, Path cmdFile) {
        // Find USE jar
        Optional<Path> useJar;

        try {
            Path targetDir = useFile.getParent().getParent().getParent().getParent();
            useJar = Files.walk(targetDir).filter(p -> p.getFileName().toString().matches("use-gui.jar")).findFirst();
        } catch (IOException e) {
            fail("Could not find USE jar!", e);
            return Collections.emptyList();
        }

        if (useJar.isEmpty()) {
            fail("Could not find USE jar!");
        }

        Path javaHome = Path.of(System.getProperty("java.home"));
        Path javaBinary = javaHome.resolve("bin/java.exe");

        if (!javaBinary.toFile().exists()) {
            javaBinary = javaHome.resolve("bin/java");

            if (!javaBinary.toFile().exists()) {
                fail("Java binary could not be found");
            }
        }

        ProcessBuilder pb = new ProcessBuilder(
                javaBinary.toString(),
                "-Duser.country=US",
                "-Duser.language=en",
                "-jar",
                useJar.get().toString(),
                "-nogui",
                "-nr",
                "-t",
                "-oclAnyCollectionsChecks:E",
                "-extendedTypeSystemChecks:E",
                /* This is currently an unstable workaround
                   USE determines the plugin and the extensions to OCL by fixed paths.
                   For now, the use-core module contains the directories including the extensions
                   and an empty plugins folder.
                   The folder is located: use/use-core/target/classes
                   Therefore, this is used as the USE home
                 */
                "-H=" + useJar.get().getParent().resolve("../../use-core/target/classes").toString(),
                useFile.getFileName().toString(),
                cmdFile.getFileName().toString());

        pb.redirectErrorStream(true);
        pb.directory(useFile.getParent().toFile());

        // Run a java app in a separate system process
        Process proc = null;
        try {
            proc = pb.start();
        } catch (IOException e) {
            fail("USE could not be started!", e);
        }

        // Then retrieve the process output
        InputStream in = proc.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line;
        List<String> actualOutput = new LinkedList<>();
        boolean firstLine = true;

        while(proc.isAlive()) {
            try {
                line = reader.readLine();

                if (firstLine) {
                    // USE writes some information at the beginning
                    // We ignore this.
                    firstLine = false;
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            if (proc.isAlive()) {
                line = line == null ? "" : line.trim();
                if (!line.equals("")) {
                    actualOutput.add(line);
                }
            }
        }

        return actualOutput;
    }
}
