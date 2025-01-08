package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.ArchTest;
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

public class AntCyclesTest {

    private final JavaClasses classes = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("org.tzi.use")
            .that(JavaClass.Predicates.resideInAPackage("org.tzi.use.."))
            .that(JavaClass.Predicates.resideOutsideOfPackage("org.tzi.use.gui.."))
            .that(JavaClass.Predicates.resideOutsideOfPackage("org.tzi.use.runtime.."));

    private static final String PROJECT_ROOT = new File("").getAbsolutePath();
    private static final String ALL_MODULES_RESULTS_FILE = new File(PROJECT_ROOT, "ant_cyclic_dependencies_results.csv").getAbsolutePath();


    @Before
    public void setup() {
        try {
            File file = new File(ALL_MODULES_RESULTS_FILE);
            System.out.println("CSV FILE CREATED IN: " + file.getAbsolutePath());
            if (file.exists()) {
                file.delete();
            }
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @ArchTest
    public void count_cycles_in_core() {
        System.out.println("Counting cycles in core...");
        try {
            File file = new File(ALL_MODULES_RESULTS_FILE);
            System.out.println("Attempting to write to: " + file.getAbsolutePath());

            System.out.println("No. of classes analysed: " + classes.size());
            // classes.forEach(clazz -> System.out.println("Found class: " + clazz.getName()));

            int cycleCount = countCyclesForPackage("org.tzi.use");
            System.out.println("CYCLES COUNTED: " + cycleCount);

            writeResult(cycleCount);
            System.out.println("Write complete");

            assertTrue("Cycle count should not be negative", cycleCount >= 0);
        } catch (Exception e) {
            System.err.println("ERROR IN ARCHUNIT TEST: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
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
            public String getDescription() { return "Slices for " + packageName; }
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
                });
        return cycleCount.get();
    }

    private void writeResult(int result) {
        try (PrintWriter out = new PrintWriter(new FileWriter(ALL_MODULES_RESULTS_FILE, true))) {
            out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
