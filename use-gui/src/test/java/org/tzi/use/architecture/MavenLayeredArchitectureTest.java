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

    @BeforeEach
    void setUp() {
        // Create reports directory if it doesn't exist
        File projectRoot = new File(System.getProperty("user.dir")).getParentFile();
        File reportsDir = new File(projectRoot,"docs/archunit-results");
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }

        // DO_NOT_INCLUDE_TESTS lässt folgende Tests aus: alle in GUI plus Integrationtest ShellIT
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("org.tzi.use");
        //System.out.println("Classes found: " + classes.size());
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
        System.out.println(violationCount);
        writeResultsToFile(violationCount, result);
    }

    private void writeResultsToFile(int violationCount, EvaluationResult result) {
        File projectRoot = new File(System.getProperty("user.dir")).getParentFile();
        File reportFile = new File(projectRoot, "docs/archunit-results/layers-current-failure-report.txt");

        try (FileWriter writer = new FileWriter(reportFile)) {
            if (violationCount > 0) {
                for (String detail : result.getFailureReport().getDetails()) {
                    writer.write(detail + "\n");
                }
                writer.write("\nLayer violations: " + violationCount + "\n");
            } else {
                writer.write("No violations - failure report does not exist.");
            }
        } catch (IOException e) {
            System.err.println("Error writing report to " + reportFile.getAbsolutePath() + ": " + e.getMessage());
        }
    }
}
