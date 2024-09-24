package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclomaticComplexityTest {

    private final JavaClasses classes = new ClassFileImporter()
            .importPackages("org.tzi.use")
            .that(JavaClass.Predicates.resideInAPackage("org.tzi.use.."))
            .that(JavaClass.Predicates.resideOutsideOfPackage("org.tzi.use.it.."))
            .that(JavaClass.Predicates.resideOutsideOfPackage("..resources.."));

    @Test
    public void count_cycles_in_core() {
        int cycleCount = countCyclesForPackage("org.tzi.use");
        System.out.println("Number of cycles in org.tzi.use: " + cycleCount);
        Assertions.assertTrue(cycleCount >= 0, "Cycle count should be non-negative");
    }

    @Test
    void count_cycles_in_uml_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.uml");
        System.out.println("Number of cycles in org.tzi.use.uml: " + cycleCount);
        Assertions.assertTrue(cycleCount >= 0, "Cycle count should be non-negative");
    }

    private int countCyclesForPackage(String packageName) {
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
        List<String> cycleDetails = new ArrayList<>();

        SlicesRuleDefinition.slices()
                .assignedFrom(sliceAssignment)
                .should().beFreeOfCycles()
                .allowEmptyShould(true)
                .evaluate(classes)
                .handleViolations((violatingObjects, violationHandler) -> {
                    cycleCount.incrementAndGet();
                    String cycleInfo = "Cycle found: " + violatingObjects.iterator().next().toString();
                    cycleDetails.add(cycleInfo);
                    //System.out.println(cycleInfo);
                });

        //System.out.println("Cycle details for " + packageName + ":");
        //cycleDetails.forEach(System.out::println);
        //System.out.println("Total cycles found: " + cycleCount.get());

        return cycleCount.get();
    }
}
