package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MavenCyclicDependenciesCoreTest {

    // hier sind keine Tests drin, überlegen, ob das so soll oder nicht
    // mit Tests: 293 Zyklen, ohne 55 ! und ohne uml sogar nur 5
    // hier sind nur core Klassen drin, weil core keinen Zugriff auf gui hat
    // also u.U. auch so einen Test f. gui
    private JavaClasses classesWithoutTests;
    private JavaClasses classesWithTests;

    // Single json for all results
    private static final String TESTS_RESULTS_JSON = "cycles-tests-results.json";

    // Maps to store all cycle counts
    private Map<String, Integer> cycleCountsWithoutTests = new HashMap<>();
    private Map<String, Integer> cycleCountsWithTests = new HashMap<>();

    @BeforeAll
    public void setup() {
        // Import classes without tests
        classesWithoutTests = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("org.tzi.use");

        // Import classes with tests
        classesWithTests = new ClassFileImporter()
                //.withImportOption(ImportOption.Predefined.ONLY_INCLUDE_TESTS)
                .importPackages("org.tzi.use");

        // Initialize or reset the cycle counts maps
        cycleCountsWithoutTests = new HashMap<>();
        cycleCountsWithTests = new HashMap<>();

        // Delete the results files if they exists
        deleteFileIfExists(TESTS_RESULTS_JSON);
        //System.out.println("No. of imported classes : " + classes.size());
    }

    private void deleteFileIfExists(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    @ArchTest
    public void count_cycles_in_core_without_tests() {
        int cycleCount = countCyclesForPackage("org.tzi.use", classesWithoutTests);
        cycleCountsWithoutTests.put("all_modules", cycleCount);
        System.out.println("Cycles in core module without tests : " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_core_with_tests() {
        int cycleCount = countCyclesForPackage("org.tzi.use", classesWithTests);
        cycleCountsWithTests.put("all_modules", cycleCount);
        System.out.println("Cycles in core module with tests : " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_analysis_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.analysis", classesWithoutTests);
        cycleCountsWithoutTests.put("analysis", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.analysis without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.analysis", classesWithTests);
        cycleCountsWithTests.put("analysis", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.analysis with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_api_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.api", classesWithoutTests);
        cycleCountsWithoutTests.put("api", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.api without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.api", classesWithTests);
        cycleCountsWithTests.put("api", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.api with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_config_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.config", classesWithoutTests);
        cycleCountsWithoutTests.put("config", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.config without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.config", classesWithTests);
        cycleCountsWithTests.put("config", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.config with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_gen_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.gen", classesWithoutTests);
        cycleCountsWithoutTests.put("gen", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.gen without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.gen", classesWithTests);
        cycleCountsWithTests.put("gen", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.gen with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_graph_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.graph", classesWithoutTests);
        cycleCountsWithoutTests.put("graph", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.graph without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.graph", classesWithTests);
        cycleCountsWithTests.put("graph", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.graph with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_main_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.main", classesWithoutTests);
        cycleCountsWithoutTests.put("main", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.main without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.main", classesWithTests);
        cycleCountsWithTests.put("main", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.main with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_parser_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.parser", classesWithoutTests);
        cycleCountsWithoutTests.put("parser", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.parser without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.parser", classesWithTests);
        cycleCountsWithTests.put("parser", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.parser with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_uml_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.uml", classesWithoutTests);
        cycleCountsWithoutTests.put("uml", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.uml without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.uml", classesWithTests);
        cycleCountsWithTests.put("uml", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.uml with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_util_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.util", classesWithoutTests);
        cycleCountsWithoutTests.put("util", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.util without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.util", classesWithTests);
        cycleCountsWithTests.put("util", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.util with tests: " + cycleCountWithTests);
    }

    @AfterAll
    public void writeAllResults() {
        // Write the JSON files after all tests have completed
        writeJsonResults(TESTS_RESULTS_JSON, cycleCountsWithoutTests, cycleCountsWithTests);
    }

    private int countCyclesForPackage(String packageName, JavaClasses classes) {
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

//        System.out.println("Cycle details for " + packageName + ":");
//        cycleDetails.forEach(System.out::println);
//        System.out.println("Total cycles found: " + cycleCount.get());

        return cycleCount.get();
    }

    private void writeJsonResults(String filename, Map<String, Integer> cycleCountsWithoutTests, Map<String, Integer> cycleCountsWithTests) {
        try (FileWriter writer = new FileWriter(filename)) {
            StringBuilder json = new StringBuilder();
            json.append("{\n");

            json.append("  \"without_tests\": {\n");
            int countWithout = 0;
            for (Map.Entry<String, Integer> entry : cycleCountsWithoutTests.entrySet()) {
                if (countWithout > 0) {
                    json.append(",\n");
                }
                json.append("  \"").append(entry.getKey()).append("\": ").append(entry.getValue());
                countWithout++;
            }
            json.append("\n  },\n");

            json.append("  \"with_tests\": {\n");
            int countWith = 0;
            for (Map.Entry<String, Integer> entry : cycleCountsWithTests.entrySet()) {
                if (countWith > 0) {
                    json.append(",\n");
                }
                json.append("    \"").append(entry.getKey()).append("\": ").append(entry.getValue());
                countWith++;
            }
            json.append("\n  }\n");

            json.append("}");
            writer.write(json.toString());
            System.out.println("Results written to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
