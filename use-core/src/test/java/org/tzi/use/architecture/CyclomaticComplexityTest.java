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

import java.util.concurrent.atomic.AtomicInteger;

public class CyclomaticComplexityTest {

    private final JavaClasses classes = new ClassFileImporter().importPackages("org.tzi.use");

    @Test
    public void check_cycles_in_core() {
        // Violated 100 times
        checkPackageForCycles("org.tzi.use");
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

    @Test
    public void count_cycles_in_core() {
        countCyclesForPackage("org.tzi.use");
    }

    @Test
    void count_cycles_in_uml_package() {
        countCyclesForPackage("org.tzi.use.uml");
    }

    // maybe it's better to have this function actually return the number, not be void
    private void countCyclesForPackage(String packageName) {
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

        AtomicInteger cycleCount = new AtomicInteger(0);

        SlicesRuleDefinition.slices()
            .assignedFrom(sliceAssignment)
            .should().beFreeOfCycles()
            .allowEmptyShould(true)
            .evaluate(classes)
            .handleViolations((violatingObjects, violationHandler) -> {
                cycleCount.incrementAndGet();
                System.out.println("Cycle found: " + violatingObjects.iterator().next().toString());
            });
        System.out.println("Number of cycles in " + packageName + ": " + cycleCount);
    }
}
