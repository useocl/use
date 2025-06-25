package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.EvaluationResult;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AntLayeredArchitectureTest {
    private JavaClasses classes;

    @Before
    public void setUp() {
        // Create reports directory if it doesn't exist
        File reportsDir = new File("target/archunit-reports");
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }

        classes = new ClassFileImporter()
                .importPackages("org.tzi.use");
        // .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        System.out.println("Classes found: " + classes.size());
    }

    @Test
    @ArchTest
    public void core_should_not_depend_on_gui() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().resideInAnyPackage("org.tzi.use.analysis..", "org.tzi.use.api..",
                        "org.tzi.use.config..", "org.tzi.use.gen..", "org.tzi.use.graph..", "org.tzi.use.parser..",
                        "org.tzi.use.uml..", "org.tzi.use.main..", "org.tzi.use.util..")
                .should().dependOnClassesThat().resideInAnyPackage("org.tzi.use.gui..")
                .because("Core packages should not depend on GUI packages");

        EvaluationResult result = rule.evaluate(classes);

        int violationCount = result.getFailureReport().getDetails().size();

        System.out.println("Number of violations: " + violationCount);

        if (violationCount > 0) {
            // Write failure report to file
            String filename = String.format("target/archunit-reports/failure_report_maven_layers.txt");

            try (FileWriter writer = new FileWriter(filename)) {
                for (String detail : result.getFailureReport().getDetails()) {
                    writer.write(detail + "\n");
                }
                writer.write("\nLayer violations: " + violationCount + "\n");
            } catch (IOException e) {
                System.err.println("Error writing report to " + filename + ": " + e.getMessage());
            }
        }
    }
}