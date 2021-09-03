package org.tzi.use.main;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.tzi.use.config.Options;

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
 * Shell integration tests.
 * Uses input files from it/resources/shell to
 * test input expressions against expected output.
 */
public class ShellIT {

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

    private Function<Path, DynamicTest> mapInFileToTest() {
        return path -> {
            final String modelFilename = path.getFileName().toString().replace(".in", ".use");
            final Path modelPath = path.resolveSibling(modelFilename);

            return DynamicTest.dynamicTest(path.getFileName().toString(), path.toUri(), () -> assertShellExpressions(path, modelPath));
        };
    }

    private void assertShellExpressions(Path testFile, Path useFile) {

        Path cmdFile = testFile.resolveSibling(testFile.getFileName() + ".cmd");
        List<String> expectedOutput = new LinkedList<>();

        createCommandFile(testFile, cmdFile, expectedOutput);

        List<String> actualOutput = runUSE(useFile, cmdFile);

        //compute the patch: this is the diffutils part
        Patch<String> patch = DiffUtils.diff(expectedOutput, actualOutput);

        if (!patch.getDeltas().isEmpty()) {
            StringBuilder diffMsg = new StringBuilder("USE output does not match expected output!").append(System.lineSeparator());

            diffMsg.append("Testfile: ").append(testFile).append(System.lineSeparator());

            diffMsg.append(System.lineSeparator()).append("Note: the position is not the position in the input file!");
            diffMsg.append(System.lineSeparator()).append(System.lineSeparator());

            //simple output the computed patch to console
            for (AbstractDelta<String> delta : patch.getDeltas()) {
                diffMsg.append(delta.toString()).append(System.lineSeparator());
            }

            writeToFile(expectedOutput, testFile.getParent().resolve(testFile.getFileName().toString() + ".expected" ));
            writeToFile(actualOutput, testFile.getParent().resolve(testFile.getFileName().toString() + ".actual" ));

            fail(diffMsg.toString());
        }
    }

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

    private void createCommandFile(Path inFile, Path cmdFile, List<String> expectedOutput) {

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
                        cmdWriter.write(Options.LINE_SEPARATOR);

                        expectedOutput.add((prompt + inputLine).trim());
                    } catch (IOException e1) {
                        fail("Could not write USE command file for test!", e1);
                    }
                }
            });
        } catch (IOException e) {
            fail("Could not write USE command file for test!", e);
        }
    }

    private List<String> runUSE(Path useFile, Path cmdFile) {
        // Find USE jar
        Optional<Path> useJar;

        try {
            Path targetDir = useFile.getParent().getParent().getParent().getParent();
            useJar = Files.walk(targetDir).filter(p -> p.getFileName().toString().matches("use-gui-.*\\.jar")).findFirst();
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
                "-H=" + useJar.get().getParent().getParent().getParent().toString(),
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
                actualOutput.add(line);
            }
        }

        return actualOutput;
    }
}
