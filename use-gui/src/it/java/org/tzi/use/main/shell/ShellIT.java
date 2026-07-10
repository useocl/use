package org.tzi.use.main.shell;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.io.TempDir;
import org.tzi.use.config.Options;
import org.tzi.use.main.gui.Main;
import org.tzi.use.util.USEWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * This class implements the shell integration tests.
 *
 * <p>These tests represent nearly the exact behavior of the
 * USE shell. Only some whitespace differences and time outputs are ignored.</p>
 *
 * <p>Each test consists of the following files:
 * <ol>
 *     <li>A model file (suffix {@code .use}) - this file provides the (possible empty) model used
 *     for the tests</li>
 *     <li>An input file (suffix: {@code .in}) - this file can contain any commands USE supports.
 *     The expected output must be specified by starting a line with a star {@code *}</li>
 *     <li>Any other used file from the command line, e.g., ASSL- or command-files.</li>
 * </ol>
 * </p>
 *
 * <p>All {@code .use</code> and </code><code>.in} files must share the same name, e.g., t555.use and t555.in for test
 * case 555. These files must be placed in the folder {@code it/resources/testfiles/shell}.</p>
 *
 * <p>If an integration test fails, two additional files are created:
 * <ol>
 *     <li>{@code {testcasename}.expected</code> - The expected output calculated from the <code>{testcase}.in}-file</li>
 *     <li>{@code {testcasename}.actual} - The output captured while running the test.</li>
 * </ol>
 * These files can be used to easily diff expected and current output.</p>
 */
public class ShellIT {

    /**
     * This TestFactory enumerates the {@code .in</code>-files in th folder <code>testfiles/shell}.
     * For each file a {@code DynamicTest} is created with the name of the file.
     *
     * @return A {@code Stream</code> with one <code>DynamicTest</code> for each <code>*.in}-file.
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

        try  {
            return Files.walk(testDirPath).filter(
                    path -> path.getFileName().toString().endsWith(".in")
            ).map(mapInFileToTest());
        } catch (IOException e) {
            fail("Error iterating shell integration test input files!");
        }

        return Stream.empty();
    }

    /**
     * This {@code Function} is used to map
     * a given testinput-file given as a {@code Path}
     * to a {@code DynamicTest}.
     *
     * @return A {@code DynamicTest</code> that uses the function <code>assertShellExpression}
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
     * <p>This function controls the overall process for test for a single testfile.</p>
     *
     * <p>The process is as follows:
     * <ol>
     *     <li>A command file and the expected output are created by examining the input file (via {@code createCommandFile}).</li>
     *     <li>USE is executed using the {@code useFile</code> and the created command file (<code>runUSE}).</li>
     *     <li>The output of USE is compared to the expected output created in 1. ({@code validateOutput}).</li>
     * </ol>
     * </p>
     *
     * @param testFile {@code Path} to the test input file to execute.
     * @param useFile {@code Path} to the USE file containing the model to load for the test.
     */
    private void assertShellExpressions(Path testFile, Path useFile) {

        Path cmdFile = testFile.resolveSibling(testFile.getFileName() + ".cmd");

        List<String> expectedOutput = createCommandFile(testFile, cmdFile);

        List<String> actualOutput = runUSE(useFile, cmdFile).collect(Collectors.toList());

        validateOutput(testFile, expectedOutput, actualOutput);
    }

    /**
     * Compares the two lists of strings {@code expectedOutput}
     * and {@code actualOutput}.
     * If they differ, two files are written at the location of the
     * {@code testFile</code>. One with the expected output (<code>.expected})
     * and one with the actual output ({@code .actual}).
     *
     * @param testFile The {@code Path</code> to the <code>testFile}
     * @param expectedOutput List of strings with the expected output (one String per line)
     * @param actualOutput List of strings with the actual output (one String per line)
     */
    private void validateOutput(Path testFile, List<String> expectedOutput, List<String> actualOutput) {
        //create a configured DiffRowGenerator
        DiffRowGenerator generator = DiffRowGenerator.create()
                .showInlineDiffs(true)
                .mergeOriginalRevised(true)
                .inlineDiffByWord(true)
                .ignoreWhiteSpaces(true)
                .lineNormalizer( (s) -> s ) // No normalization required
                .oldTag((f, start) -> start ? "-\033[9m" : "\033[m-")      //introduce style for strikethrough
                .newTag((f, start) -> start ? "+\033[97;42m" : "\033[m+")  //introduce style for bold
                .build();

        //compute the differences for two test texts.
        List<DiffRow> rows = generator.generateDiffRows(expectedOutput, actualOutput);
        Predicate<DiffRow> filter = d -> d.getTag() != DiffRow.Tag.EQUAL;

        if (rows.stream().anyMatch(filter)) {
            StringBuilder diffMsg = new StringBuilder("USE output does not match expected output!").append(System.lineSeparator());

            diffMsg.append("Test file: ").append(testFile).append(System.lineSeparator());

            diffMsg.append(System.lineSeparator()).append("Note: the position is not the position in the input file!");
            diffMsg.append(System.lineSeparator()).append(System.lineSeparator());

            rows.stream().filter(filter).forEach(
                    row ->diffMsg.append(System.lineSeparator()).append(row.getOldLine())
            );

            writeToFile(expectedOutput, testFile.getParent().resolve(testFile.getFileName().toString() + ".expected"));
            writeToFile(actualOutput, testFile.getParent().resolve(testFile.getFileName().toString() + ".actual"));

            fail(diffMsg.toString());
        }
    }

    /**
     * <p>Helper method that writes the list of strings {@code data}
     * to the file located by the {@code Path} {@code file}.</p>
     *
     * <p>If the file is not accessible, i.e., an IOException is thrown,
     * the exception is caught and the test case fails.</p>
     * @param data The {@code List} of string (lines) to write.
     * @param file The path to the file to write (the file is overwritten).
     */
    private void writeToFile(List<String> data, Path file) {
        try (FileWriter writer = new FileWriter(file.toFile())) {

            for (String line : data) {
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            fail("Test output could not be written!", e);
        }
    }

    /**
     * Issue #32: the generated command file must always end with an
     * {@code exit} command so USE terminates even if the {@code .in} file does
     * not contain an explicit {@code exit}.
     */
    @Test
    public void generatedCommandFileAlwaysEndsWithExit(@TempDir Path dir) throws IOException {
        Path inFile = dir.resolve("noexit.in");
        Files.writeString(inFile, "?1 + 1" + System.lineSeparator() + "*-> 2 : Integer" + System.lineSeparator());
        Path cmdFile = dir.resolve("noexit.in.cmd");

        createCommandFile(inFile, cmdFile);

        List<String> cmdLines = readNonBlankLines(cmdFile);
        assertEquals("exit", cmdLines.get(cmdLines.size() - 1),
                "The generated command file must always end with an exit command.");
    }

    /**
     * The appended {@code exit} is echoed by USE, so it must be part of the
     * expected output for an {@code .in} file that did not terminate itself.
     */
    @Test
    public void appendedExitIsAddedToExpectedOutput(@TempDir Path dir) throws IOException {
        Path inFile = dir.resolve("noexit.in");
        Files.writeString(inFile, "?1 + 1" + System.lineSeparator() + "*-> 2 : Integer" + System.lineSeparator());
        Path cmdFile = dir.resolve("noexit.in.cmd");

        List<String> expected = createCommandFile(inFile, cmdFile);

        assertEquals("exit", expected.get(expected.size() - 1),
                "The appended exit must be part of the expected output.");
    }

    /**
     * Tests that load an invalid model make USE exit by itself before the
     * command file is read. Such tests are marked with {@code #expected exit}
     * and must not expect the (never executed) appended exit.
     */
    @Test
    public void selfExitingTestsDoNotExpectTheAppendedExit(@TempDir Path dir) throws IOException {
        Path inFile = dir.resolve("selfexit.in");
        Files.writeString(inFile, "#expected exit" + System.lineSeparator()
                + "?1 + 1" + System.lineSeparator() + "*-> 2 : Integer" + System.lineSeparator());
        Path cmdFile = dir.resolve("selfexit.in.cmd");

        List<String> expected = createCommandFile(inFile, cmdFile);

        assertFalse(expected.contains("exit"),
                "A test marked with '#expected exit' must not expect the appended exit.");
    }

    /**
     * If the {@code .in} file already ends with an explicit {@code exit}, the
     * appended exit is never executed, so {@code exit} must appear only once in
     * the expected output.
     */
    @Test
    public void exitIsNotDuplicatedWhenInFileAlreadyEndsWithExit(@TempDir Path dir) throws IOException {
        Path inFile = dir.resolve("withexit.in");
        Files.writeString(inFile, "?1 + 1" + System.lineSeparator() + "*-> 2 : Integer"
                + System.lineSeparator() + "exit" + System.lineSeparator());
        Path cmdFile = dir.resolve("withexit.in.cmd");

        List<String> expected = createCommandFile(inFile, cmdFile);

        assertEquals(1, expected.stream().filter("exit"::equals).count(),
                "exit must appear exactly once in the expected output.");
    }

    /**
     * The {@code quit} (and {@code q}) alias also terminates USE, so an .in file
     * ending with it must not expect the (never executed) appended exit.
     */
    @Test
    public void quitTerminatedTestsDoNotExpectTheAppendedExit(@TempDir Path dir) throws IOException {
        Path inFile = dir.resolve("withquit.in");
        Files.writeString(inFile, "?1 + 1" + System.lineSeparator() + "*-> 2 : Integer"
                + System.lineSeparator() + "quit" + System.lineSeparator());
        Path cmdFile = dir.resolve("withquit.in.cmd");

        List<String> expected = createCommandFile(inFile, cmdFile);

        assertFalse(expected.contains("exit"),
                "A test that terminates with quit must not expect the appended exit.");
    }

    private List<String> readNonBlankLines(Path file) throws IOException {
        return Files.readAllLines(file, StandardCharsets.UTF_8).stream()
                .filter(line -> !line.isBlank())
                .collect(Collectors.toList());
    }

    /**
     * Creates a USE-command file at the position located by the path {@code cmdFile}.
     * The file contains all commands that are specified in the {@code inFile}.
     * The expected output, i.e., lines starting with a {@code *} are added to the list {@code expectedOutput}.
     *
     * @param inFile The {@code Path} to the test input file.
     * @param cmdFile The {@code Path} where to create the command file.
     * @return  A {@code List} which is filled with the expected output of USE.
     */
    private List<String> createCommandFile(Path inFile, Path cmdFile) {
        List<String> expectedOutput = new LinkedList<>();

        // Whether USE is expected to terminate by itself before the command
        // file is read (e.g. because the test loads an invalid model). Such
        // tests are marked with a '#expected exit' comment.
        boolean selfExits = false;
        // The last command written to the command file. Used to avoid adding a
        // duplicate exit to the expected output if the .in file already ends
        // with an explicit exit.
        String lastCommand = null;

        // Build USE command file and build expected output
        try (
                FileWriter cmdWriter = new FileWriter(cmdFile.toFile(), StandardCharsets.UTF_8, false)
        ) {
            for (String inputLine : Files.readAllLines(inFile, StandardCharsets.UTF_8)) {

                // Ignore empty lines in expected, since they are also suppressed in the actual output
                if (inputLine.isBlank())
                    continue;

                if ((inputLine.startsWith("*") || inputLine.startsWith("#"))
                        && inputLine.substring(1).isBlank()) {
                    continue;
                }

                if (inputLine.startsWith("*")) {
                    // Input line minus prefix(*) is expected output
                    expectedOutput.add(inputLine.substring(1).trim());
                } else if (inputLine.startsWith("#")) {
                    // Comment line. The '#expected exit' marker denotes tests in
                    // which USE exits by itself (e.g. on an invalid model).
                    if (inputLine.substring(1).trim().equals("expected exit")) {
                        selfExits = true;
                    }
                } else { // A command
                    cmdWriter.write(inputLine);
                    cmdWriter.write(System.lineSeparator());

                    // Multi-line commands (backslash and dot) are ignored
                    if (!inputLine.matches("^[\\\\.]$")) {
                        expectedOutput.add(inputLine);
                    }
                    lastCommand = inputLine.trim();
                }
            }

            // Issue #32: always terminate the command file with an exit so USE
            // does not keep running when an .in file forgot to add one.
            cmdWriter.write("exit");
            cmdWriter.write(System.lineSeparator());
        } catch (IOException e) {
            fail("Could not write USE command file for test!", e);
        }

        // The appended exit is echoed by USE and therefore part of the output,
        // but only when it is actually executed: not when USE exits by itself
        // before reading the command file (#expected exit), and not when the
        // .in file already terminated with its own exit/quit (which runs first).
        if (!selfExits && !isExitCommand(lastCommand)) {
            expectedOutput.add("exit");
        }

        return expectedOutput;
    }

    /**
     * Whether the given command terminates USE, i.e., is one of {@code q},
     * {@code quit} or {@code exit} (see {@code Shell.processLine}).
     */
    private static boolean isExitCommand(String command) {
        return "exit".equals(command) || "quit".equals(command) || "q".equals(command);
    }

    /**
     * Executes USE with the given {@code useFile} as the model
     * and the {@code cmdFile} to execute commands.
     * The output is captured from the output and error streams.
     *
     * @param useFile Path to the USE model to load on startup
     * @param cmdFile Path to the commands file to execute
     * @return A {@code List} of strings. Each string is one line of output.
     */
    private Stream<String> runUSE(Path useFile, Path cmdFile) {

        // We need to specify a concrete locale to always get the same formatted result
        Locale.setDefault(Locale.forLanguageTag("en-US"));

        Options.resetOptions();
        USEWriter.getInstance().clearLog();

        String homeDir;
        try {
            homeDir = useFile.getParent().resolve("../../../../../use-core/target/classes").toFile().getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] args = new String[] {
                "-nogui",
                "-nr",
                "-t",
                "-it",
                "-q",
                "-oclAnyCollectionsChecks:E",
                "-extendedTypeSystemChecks:E",
                /* This is currently an unstable workaround
                   USE determines the plugin and the extensions to OCL by fixed paths.
                   For now, the use-core module contains the directories including the extensions
                   and an empty plugins folder.
                   The folder is located: use/use-core/target/classes
                   Therefore, this is used as the USE home
                 */
                "-H=" + homeDir,
                useFile.toString(),
                cmdFile.toString()};

        Main.main(args);

        try (ByteArrayOutputStream protocol = new ByteArrayOutputStream()) {
            USEWriter.getInstance().writeProtocolFile(protocol);
            String output = protocol.toString();
            return  output.lines().filter(l -> !l.isBlank());
        } catch (IOException e) {
            fail(e);
        }

        return Stream.empty();
    }
}
