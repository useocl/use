package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclomaticComplexityTest {

    private final JavaClasses classes = new ClassFileImporter()
            .importPackages("org.tzi.use")
            .that(JavaClass.Predicates.resideInAPackage("org.tzi.use.."))
            .that(JavaClass.Predicates.resideOutsideOfPackage("org.tzi.use.it.."))
            .that(JavaClass.Predicates.resideOutsideOfPackage("..resources.."));

    private static final String RESULTS_FILE = "cyclomatic_complexity_results.csv";

    @Before
    public void setup() {
        // Delete the results file if it exists
        File file = new File(RESULTS_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void count_cycles_in_core() {
        int cycleCount = countCyclesForPackage("org.tzi.use");
        writeResult(cycleCount);
        assertTrue("Cycle count should be non-negative", cycleCount >= 0);
    }

/*    @Test
    void count_cycles_in_uml_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.uml");
        writeResult("Number of cycles in org.tzi.use.uml: " + cycleCount);
        Assertions.assertTrue(cycleCount >= 0, "Cycle count should be non-negative");
    }*/

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

    private void writeResult(int result) {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESULTS_FILE, true))){
            out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
