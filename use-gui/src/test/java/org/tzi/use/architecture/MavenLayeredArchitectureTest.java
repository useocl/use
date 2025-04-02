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

public class MavenLayeredArchitectureTest {
    private JavaClasses classes;

    @BeforeEach
    void setUp() {
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

        if (violationCount > 0) {
            System.err.println("\nViolation details:");
            result.getFailureReport().getDetails().forEach(System.err::println);
        }
    }
}
