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
            .importPaths("src/main")
            .that(JavaClass.Predicates.resideInAPackage("org.tzi.use.."))
            .that(JavaClass.Predicates.resideOutsideOfPackage("..test.."));

    private static final String PROJECT_ROOT = new File("").getAbsolutePath();
    private static final String RESULTS_FILE = new File(PROJECT_ROOT, "cycles_ant_results.csv").getAbsolutePath();


    @Before
    public void setup() {
        try {
            File file = new File(RESULTS_FILE);
            System.out.println("CSV FILE CREATED IN: " + file.getAbsolutePath());
            if (file.exists()) {
                file.delete();
            }
            file.getParentFile().mkdirs();
            file.createNewFile();

            try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
                out.println("Test setup complete");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @ArchTest
    public void count_cycles_in_main() {
        System.out.println("------HELLO FROM ARCHUNIT------");
        System.err.println("------HELLO FROM ARCHUNIT (ERR) ------");
        try {
            File file = new File(RESULTS_FILE);
            System.err.println("Attempting to write to: " + file.getAbsolutePath());

            int cycleCount = countCyclesForPackage("org.tzi.use");
            System.out.println("CYCLES COUNTED: " + cycleCount);
            System.err.println("CYCLES COUNTED: " + cycleCount);

            writeResult(cycleCount);
            System.err.println("Write complete");

            assertTrue("Cycle count should not be negative", cycleCount >= 0);
        } catch (Exception e) {
            System.out.println("ERROR IN ARCHUNIT TEST!");
            System.err.println("ERROR IN ARCHUNIT TEST: " + e.getMessage());
            // Log any exceptions that occur during test execution
            try (PrintWriter out = new PrintWriter(new FileWriter(RESULTS_FILE, true))) {
                out.println("Error during test: " + e.getMessage());
                e.printStackTrace(out);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
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
        try (PrintWriter out = new PrintWriter(new FileWriter(RESULTS_FILE, true))) {
            out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
