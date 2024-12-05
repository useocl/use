/*
package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArchUnitTest {
    private final JavaClasses classes = new ClassFileImporter()
            .importPackages("org.tzi.use")
            .that(JavaClass.Predicates.resideInAPackage("org.tzi.use.."))
            .that(JavaClass.Predicates.resideOutsideOfPackage("org.tzi.use.it.."))
            .that(JavaClass.Predicates.resideOutsideOfPackage("..resources.."));

    @Test
    public void ensure_only_main_packages_are_analyzed() {
        for (JavaClass clazz : classes) {
            assertTrue(clazz.getPackageName().startsWith("org.tzi.use"));
            assertFalse(clazz.getPackageName().contains("resources"));
            assertFalse(clazz.getPackageName().startsWith("org.tzi.use.it"));
        }
    }

    @Test
    public void ensure_no_cycles_in_core() {
        checkPackageForCycles("org.tzi.use");
    }

    @Test
    void ensure_no_cycles_in_uml_package() {
        checkPackageForCycles("org.tzi.use.uml");
    }

    private void checkPackageForCycles(String packageName) {
        SliceAssignment sliceAssignment = new SliceAssignment() {
            @Override
            public SliceIdentifier getIdentifierOf(JavaClass javaClass) {
                if (javaClass.getPackageName().startsWith(packageName)) {
                    String subPackage = javaClass.getPackageName().substring(packageName.length());
                    if (subPackage.isEmpty()) {
                        return SliceIdentifier.of("root");
                    }

                    String[] parts = subPackage.substring(1).split("\\.");
                    return SliceIdentifier.of(parts.length > 0 ? parts[0] : "root");
                }
                return SliceIdentifier.ignore();
            }

            @Override
            public String getDescription() {
                return "Slices for " + packageName;
            }
        };

        SlicesRuleDefinition.slices().assignedFrom(sliceAssignment).should().beFreeOfCycles().check(classes);
    }


}
*/
