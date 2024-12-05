package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LayeredArchitectureTest {
    private JavaClasses coreClasses;
    private JavaClasses guiClasses;
    private JavaClasses classes;

    @BeforeEach
    void setUp() {
/*        Path corePath = Paths.get("..\\use-core\\target\\classes\\org\\tzi\\use");
        Path guiPath = Paths.get("..\\use-gui\\target\\classes\\org\\tzi\\use");

        coreClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPath(corePath);
        System.out.println("Core classes found: " + coreClasses.size());

        guiClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPath(guiPath);
        System.out.println("GUI classes found: " + guiClasses.size());*/

        Path corePath = Paths.get("use-core/src/main/java/org/tzi/use");
        Path guiPath = Paths.get("use-gui/src/main/java");

        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("org.tzi.use");
        System.out.println("Classes found: " + classes.size());

    }

    @Test
    @ArchTest
    //TODO Nicht sicher, ob das mit util so geht. Die Frage ist hier, ob es ok ist, wenn ShellReadline in util.input quasi
    //von sich selbst "abhängig" ist oder ob das zum Fehler führt...
/*    resideInAnyPackage("org.tzi.use.analysis..", "org.tzi.use.api..",
                               "org.tzi.use.config..", "org.tzi.use.gen..", "org.tzi.use.graph..", "org.tzi.use.parser..",
                               "org.tzi.use.uml..", "org.tzi.use.main.runtime..", "org.tzi.use.main.ChangeEvent",
                               "org.tzi.use.main.ChangeListener", "org.tzi.use.main.MonitorAspectGenerator", "org.tzi.use.main.Session",
                               "org.tzi.use.util.collections..", "org.tzi.use.util.rubyintegration..", "org.tzi.use.util.soil..",
                               "org.tzi.use.util.uml..")*/
    public void core_should_not_depend_on_gui() {
        ArchRuleDefinition.noClasses()
                .that().resideInAnyPackage("org.tzi.use.analysis..", "org.tzi.use.api..",
                        "org.tzi.use.config..", "org.tzi.use.gen..", "org.tzi.use.graph..", "org.tzi.use.parser..",
                        "org.tzi.use.uml..", "org.tzi.use.main.runtime..",
                        "org.tzi.use.util.collections..", "org.tzi.use.util.rubyintegration..", "org.tzi.use.util.soil..",
                        "org.tzi.use.util.uml..")
                .should().dependOnClassesThat().resideInAnyPackage("org.tzi.use.gui..",
                        "org.tzi.use.runtime..", "org.tzi.use.main.shell..")
                .because("Core packages should not depend on GUI packages")
                .check(classes);

/*        ArchRuleDefinition.noClasses()
                .that().belongToAnyOf(coreClasses.stream()
                        .filter(javaClass -> !javaClass.getName().contains("Op_number_pow")
                        && !javaClass.getName().contains("MDataTypeImpl"))
                        .map(JavaClass::reflect)
                        .toArray(Class<?>[]::new))
                .should().dependOnClassesThat().belongToAnyOf(guiClasses.stream()
                        .map(JavaClass::reflect)
                        .toArray(Class<?>[]::new))
                .because("Core module should not depend on GUI module")
                .check(coreClasses);*/

    }
}
