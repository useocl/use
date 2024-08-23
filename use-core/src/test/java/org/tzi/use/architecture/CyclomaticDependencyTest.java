package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import static com.tngtech.archunit.library.metrics.MetricsComponents.fromPackages;

public class CyclomaticDependencyTest {

    private final JavaClasses classes = new ClassFileImporter().importPackages("org.tzi.use");

    @Test
    public void check_cycles_in_core() {
        // Violated 95 times
         SlicesRuleDefinition.slices().matching("org.tzi.use.(*)..").should().beFreeOfCycles().check(classes);
    }

    @Test
    void ensure_no_cycles_in_uml_package() {
        // Violated 5 times, dependencies in logs
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
