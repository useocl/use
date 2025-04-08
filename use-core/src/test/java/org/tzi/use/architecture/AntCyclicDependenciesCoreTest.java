package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ViolationHandler;
import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AntCyclicDependenciesCoreTest {

    //private static final String PROJECT_ROOT = new File("").getAbsolutePath();
    //private static final String ALL_MODULES_RESULTS_FILE = new File(PROJECT_ROOT, "ant_cyclic_dependencies_results.csv").getAbsolutePath();
    // Single json for all results
    private JavaClasses classesWithTests;

    @Before
    public void setup() {
        classesWithTests = new ClassFileImporter()
                //.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("org.tzi.use")
                .that(JavaClass.Predicates.resideOutsideOfPackage("org.tzi.use.gui.."))
                .that(JavaClass.Predicates.resideOutsideOfPackage("org.tzi.use.runtime.."));
    }

    @Test
    @ArchTest
    public void count_cycles_in_core() {
/*        try {
            File file = new File(ALL_MODULES_RESULTS_FILE);
            System.out.println("Attempting to write to: " + file.getAbsolutePath());

            System.out.println("No. of classes analysed: " + classes.size());
            // classes.forEach(clazz -> System.out.println("Found class: " + clazz.getName()));

            int cycleCount = countCyclesForPackage("org.tzi.use");
            System.out.println("CYCLES COUNTED: " + cycleCount);

            writeResult(cycleCount, ALL_MODULES_RESULTS_FILE);
            System.out.println("Write complete");

            assertTrue("Cycle count should not be negative", cycleCount >= 0);
        } catch (Exception e) {
            System.err.println("ERROR IN ARCHUNIT TEST: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }*/
        int cycleCount = countCyclesForPackage("org.tzi.use");
        System.out.println("Cycles in core module: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_analysis_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.analysis");
        System.out.println("Number of cycles in org.tzi.use.analysis: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_api_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.api");
        System.out.println("Number of cycles in org.tzi.use.api: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_config_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.config");
        System.out.println("Number of cycles in org.tzi.use.config: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_gen_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.gen");
        System.out.println("Number of cycles in org.tzi.use.gen: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_graph_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.graph");
        System.out.println("Number of cycles in org.tzi.use.graph: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_main_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.main");
        System.out.println("Number of cycles in org.tzi.use.main: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_parser_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.parser");
        System.out.println("Number of cycles in org.tzi.use.parser: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_uml_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.uml");
        System.out.println("Number of cycles in org.tzi.use.uml: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_util_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.util");
        System.out.println("Number of cycles in org.tzi.use.util: " + cycleCount);
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
                .evaluate(classesWithTests)
                .handleViolations(new ViolationHandler<Object>() {
                    @Override
                    public void handle(Collection<Object> violatingObjects, String violationDescription) {
                        cycleCount.incrementAndGet();
                        String cycleInfo = "Cycle found: " + violatingObjects.iterator().next().toString();
                        cycleDetails.add(cycleInfo);
                    }
                });
                // not compatible with java 7
//                .handleViolations((violatingObjects, violationHandler) -> {
//                    cycleCount.incrementAndGet();
//                    String cycleInfo = "Cycle found: " + violatingObjects.iterator().next().toString();
//                    cycleDetails.add(cycleInfo);
//                });
        return cycleCount.get();
    }
}
