package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.EvaluationResult;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MavenLayeredArchitectureTest {
    private JavaClasses classes;
    private static final String TEST_RESULT_CSV = "layer-violations.csv";

    @BeforeEach
    void setUp() {
        // DO_NOT_INCLUDE_TESTS lässt folgende Tests aus: alle in GUI plus Integrationtest ShellIT
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("org.tzi.use");
        System.out.println("Classes found: " + classes.size());

        deleteFileIfExists(TEST_RESULT_CSV);
    }

    private void deleteFileIfExists(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    @ArchTest
    //ist leider nicht optimal & vollständig, da Namensdopplung in:
    // org.tzi.use.util, org.tzi.use.util.input, org.tzi.use.main,
    public void countCoreDependenciesOnGui() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().resideInAnyPackage("org.tzi.use.analysis..", "org.tzi.use.api..",
                        "org.tzi.use.config..", "org.tzi.use.gen..", "org.tzi.use.graph..", "org.tzi.use.parser..",
                        "org.tzi.use.uml..", "org.tzi.use.main.runtime..",
                        "org.tzi.use.util.collections..", "org.tzi.use.util.rubyintegration..", "org.tzi.use.util.soil..",
                        "org.tzi.use.util.uml..")
                .should().dependOnClassesThat().resideInAnyPackage("org.tzi.use.gui..",
                        "org.tzi.use.runtime..", "org.tzi.use.main.shell..")
                .because("Core packages should not depend on GUI packages");

        EvaluationResult result = rule.evaluate(classes);

        int violationCount = result.getFailureReport().getDetails().size();

        System.out.println("Number of core-to-gui dependency violations: " + violationCount);

        writeResultToCSV(violationCount);

        if (violationCount > 0) {
            System.out.println("\nViolation details:");
            result.getFailureReport().getDetails().forEach(System.out::println);
        }
    }

    private void writeResultToCSV(int violationCount) {
        try (FileWriter writer = new FileWriter(TEST_RESULT_CSV)) {
            writer.append("rule,violations\n");

            writer.append("core_to_gui,").append(String.valueOf(violationCount)).append("\n");

            System.out.println("CSV file written successfully: " + TEST_RESULT_CSV);
        } catch (IOException e) {
            System.err.println("Error writing to CSB file: " + e.getMessage());
        }
    }
}
