package org.tzi.use.main;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.tzi.use.config.Options;
import org.tzi.use.main.shell.Shell;
import org.tzi.use.parser.use.USECompiler;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.util.USEWriter;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
            return java.nio.file.Files.walk(testDirPath).filter(
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

    private void assertShellExpressions(Path inputFile, Path useFile) {
        final MModel model;
        final Session session = new Session();
        final MSystem system;

        try (FileInputStream specStream = new FileInputStream(useFile.toFile())) {

            model = USECompiler.compileSpecification(specStream,
                                    useFile.toString(), new PrintWriter(System.err),
                                    new ModelFactory());

        } catch (FileNotFoundException e) {
            fail(String.format("File `%s' not found.", useFile));
            return;
        } catch (IOException e1) {
            fail(String.format("File `%s' not accessible.", useFile));
            return;
        }

        system = new MSystem(model);
        session.setSystem(system);

        Options.doPLUGIN = false;
        Options.quiet = true;

        Shell.createInstance(session, null);
        Shell sh = Shell.getInstance();

        // set System.out to the OldUSEWriter to protocol the output.
        System.setOut(USEWriter.getInstance().getOut());
        // set System.err to the OldUSEWriter to protocol the output.
        System.setErr(USEWriter.getInstance().getErr());
        USEWriter.getInstance().setQuietMode(true);

        List<String> expectedOutput = new LinkedList<>();
        List<String> actualOutput;
        List<String> testInput;

        try {
            testInput = Files.readAllLines(inputFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            fail(String.format("File `%s' not accessible.", inputFile));
            return;
        }

        boolean multiLine = false;
        StringBuilder multiLineString = new StringBuilder();
        String line;

        // Run test USE commands and build expected output
        for (String inputLine : testInput) {
            inputLine = inputLine.trim();

            // Ignore comments
            if (inputLine.startsWith("#") || inputLine.length() == 0) {
                continue;
            }

            if (inputLine.startsWith("exit")) {
                break;
            }

            if (inputLine.startsWith("\\")) {
                multiLine = true;
                continue;
            }

            if (inputLine.startsWith(".")) {
                multiLine = false;
                line = multiLineString.toString();
                multiLineString.setLength(0);
            } else {
                if (multiLine) {
                    multiLineString.append(inputLine).append(Options.LINE_SEPARATOR);
                    continue;
                } else {
                    line = inputLine;
                }
            }

            if (line.startsWith("*")) {
                // Input line minus prefix(*) is expected output
                expectedOutput.add(line.substring(1).trim());
            } else {
                sh.processLineSafely(line);
            }
        }

        ByteArrayOutputStream useOutputStream = new ByteArrayOutputStream();
        try {
            USEWriter.getInstance().writeProtocolFile(useOutputStream);
        } catch (IOException e) {
            fail(e);
        }

        String useOutputRaw = useOutputStream.toString();
        actualOutput = new ArrayList<>();
        for (String s : useOutputRaw.split(Options.LINE_SEPARATOR)) {
            String trim = s.trim();
            actualOutput.add(trim);
        }

        //compute the patch: this is the diffutils part
        Patch<String> patch = DiffUtils.diff(expectedOutput, actualOutput);

        if (!patch.getDeltas().isEmpty()) {
            StringBuilder diffMsg = new StringBuilder("USE output does not match expected output!");
            diffMsg.append(Options.LINE_SEPARATOR).append("Note: the position is not the position in the input file!");
            diffMsg.append(Options.LINE_SEPARATOR).append(Options.LINE_SEPARATOR);

            //simple output the computed patch to console
            for (AbstractDelta<String> delta : patch.getDeltas()) {
                diffMsg.append(delta.toString()).append(Options.LINE_SEPARATOR);
            }

            fail(diffMsg.toString());
        }
    }
}
