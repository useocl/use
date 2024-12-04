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

    @BeforeEach
    void setUp() {
        Path corePath = Paths.get("..\\use-core\\target\\classes\\org\\tzi\\use");
        Path guiPath = Paths.get("..\\use-gui\\target\\classes\\org\\tzi\\use");

        coreClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPath(corePath);
        System.out.println("Core classes found: " + coreClasses.size());

        guiClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPath(guiPath);
        System.out.println("GUI classes found: " + guiClasses.size());
    }

    @Test
    @ArchTest
    public void core_should_not_depend_on_gui() {
        ArchRuleDefinition.noClasses()
                .that().belongToAnyOf(coreClasses.stream()
                        .filter(javaClass -> !javaClass.getName().contains("Op_number_pow")
                        && !javaClass.getName().contains("MDataTypeImpl"))
                        .map(JavaClass::reflect)
                        .toArray(Class<?>[]::new))
                .should().dependOnClassesThat().belongToAnyOf(guiClasses.stream()
                        .map(JavaClass::reflect)
                        .toArray(Class<?>[]::new))
                .because("Core module should not depend on GUI module")
                .check(coreClasses);
    }
}
