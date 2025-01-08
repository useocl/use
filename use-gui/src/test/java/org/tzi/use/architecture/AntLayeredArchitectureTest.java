package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.Before;
import org.junit.Test;

public class AntLayeredArchitectureTest {
    private JavaClasses classes;

    @Before
    public void setUp() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("org.tzi.use");
        System.out.println("Classes found: " + classes.size());
    }

    @Test
    @ArchTest
    public void core_should_not_depend_on_gui() {
        ArchRuleDefinition.noClasses()
                .that().resideInAnyPackage("org.tzi.use.analysis..", "org.tzi.use.api..",
                        "org.tzi.use.config..", "org.tzi.use.gen..", "org.tzi.use.graph..", "org.tzi.use.parser..",
                        "org.tzi.use.uml..", "org.tzi.use.main..", "org.tzi.use.util..")
                .should().dependOnClassesThat().resideInAnyPackage("org.tzi.use.gui..")
                .because("Core packages should not depend on GUI packages")
                .check(classes);

    }

}
