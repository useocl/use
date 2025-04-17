package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.Location;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ViolationHandler;
import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AntCyclicDependenciesCoreTest {

    private JavaClasses classesWithTests;

    @Before
    public void setup() {
        classesWithTests = new ClassFileImporter()
                //.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .withImportOption(new CustomTestExclusionOption())
                .importPackages("org.tzi.use")
                .that(JavaClass.Predicates.resideOutsideOfPackage("org.tzi.use.gui.."))
                .that(JavaClass.Predicates.resideOutsideOfPackage("org.tzi.use.runtime.."))
                .that(JavaClass.Predicates.resideInAnyPackage("org.tzi.use.analysis..",
                        "org.tzi.use.api..", "org.tzi.use.config..", "org.tzi.use.gen..",
                        "org.tzi.use.graph..", "org.tzi.use.main..", "org.tzi.use.parser..",
                        "org.tzi.use.uml..", "org.tzi.use.util.."));
        System.out.println("--- setup method called ---");
        System.out.println("No of classes incl.: " + classesWithTests.size());
    }

    @Test
    @ArchTest
    public void count_cycles_in_core() {
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

    private int countCyclesForPackage(final String packageName) {
        final SliceAssignment sliceAssignment = new SliceAssignment() {
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

        final AtomicInteger cycleCount = new AtomicInteger(0);
        final List<String> cycleDetails = new ArrayList<>();

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

class CustomTestExclusionOption implements ImportOption {

    @Override
    public boolean includes(Location location) {

        String path = location.toString();

        // kann man auch weglassen, da die kompilierten pfade nie test enthalten
        if (path.toLowerCase().contains("/test/") || path.toLowerCase().contains("\\test\\")) {
            System.out.println("!!! location contains /test/ !!!");
            return false;
        }

//        String className = extractClassName(path);
//        System.out.println("!!! Extracted class name: " + className);

        // SystemManipulator does not exist in later project
        // TestModelUtil is moved to core in later project
        if (path.endsWith("Test") ||
                path.contains("Tests")
                ||
                path.contains("TestSystem")
                ||
                path.contains("ObjectCreation")
                ||
                path.contains("AllTests")
                ||
                path.contains(".fail")
                ||
                path.contains(".use")
                ||
                path.contains(".in")
        ) {
            System.out.println("+++ Excluding test file: " + path);
            System.out.println("+++ path ends with called +++");
            return false;
        }

        return true;
    }

    // Helper method to extract the class name without .class extension
/*    private String extractClassName(String path) {
        // Get the filename part (after the last slash or backslash)
        int lastSlashIndex = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
        if (lastSlashIndex == -1) {
            return path;
        }

        String fileName = path.substring(lastSlashIndex + 1);

        // Remove .class extension if present
        if (fileName.endsWith(".class")) {
            return fileName.substring(0, fileName.length() - 6); // 6 is the length of ".class"
        }

        return fileName;
    }*/
}