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

    // Map to store all cycle counts
    private Map<String, Integer> cycleCountsMap = new HashMap<>();

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

        // Initialize or reset the cycle counts map
        cycleCountsMap = new HashMap<>();

        // Delete the results files if they exists
        deleteFileIfExists(TESTS_RESULTS_JSON);
        //System.out.println("No. of imported classes : " + classes.size());
    }

    private void deleteFileIfExists(String filename) {
        File file = new File(TESTS_RESULTS_JSON);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    @ArchTest
    public void count_cycles_in_core_without_tests() {
        int cycleCount = countCyclesForPackage("org.tzi.use", classesWithoutTests);
        cycleCountsMap.put("all_modules_no_tests", cycleCount);
        System.out.println("Cycles in core module without tests : " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_core_with_tests() {
        int cycleCount = countCyclesForPackage("org.tzi.use", classesWithTests);
        cycleCountsMap.put("all_modules_with_tests", cycleCount);
        System.out.println("Cycles in core module with tests : " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_analysis_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.analysis", classesWithoutTests);
        cycleCountsMap.put("analysis_no_tests", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.analysis without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.analysis", classesWithTests);
        cycleCountsMap.put("analysis_with_tests", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.analysis with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_api_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.api", classesWithoutTests);
        cycleCountsMap.put("api_no_tests", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.api without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.api", classesWithTests);
        cycleCountsMap.put("api_with_tests", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.api with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_config_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.config", classesWithoutTests);
        cycleCountsMap.put("config_no_tests", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.config without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.config", classesWithTests);
        cycleCountsMap.put("config_with_tests", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.config with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_gen_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.gen", classesWithoutTests);
        cycleCountsMap.put("gen_no_tests", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.gen without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.gen", classesWithTests);
        cycleCountsMap.put("gen_with_tests", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.gen with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_graph_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.graph", classesWithoutTests);
        cycleCountsMap.put("graph_no_tests", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.graph without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.graph", classesWithTests);
        cycleCountsMap.put("graph_with_tests", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.graph with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_main_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.main", classesWithoutTests);
        cycleCountsMap.put("main_no_tests", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.main without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.main", classesWithTests);
        cycleCountsMap.put("main_with_tests", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.main with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_parser_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.parser", classesWithoutTests);
        cycleCountsMap.put("parser_no_tests", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.parser without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.parser", classesWithTests);
        cycleCountsMap.put("parser_with_tests", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.parser with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_uml_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.uml", classesWithoutTests);
        cycleCountsMap.put("uml_no_tests", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.uml without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.uml", classesWithTests);
        cycleCountsMap.put("uml_with_tests", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.uml with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_util_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.util", classesWithoutTests);
        cycleCountsMap.put("util_no_tests", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.util without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.util", classesWithTests);
        cycleCountsMap.put("util_with_tests", cycleCountWithTests);
        System.out.println("Number of cycles in org.tzi.use.util with tests: " + cycleCountWithTests);
    }

    @AfterAll
    public void writeAllResults() {
        // Write the JSON files after all tests have completed
        writeJsonResults(TESTS_RESULTS_JSON, cycleCountsMap);
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

    private void writeJsonResults(String filename, Map<String, Integer> cycleCounts) {
        try (FileWriter writer = new FileWriter(filename)) {
            StringBuilder json = new StringBuilder();
            json.append("{\n");

            int count = 0;
            for (Map.Entry<String, Integer> entry : cycleCounts.entrySet()) {
                if (count > 0) {
                    json.append(",\n");
                }
                json.append("  \"").append(entry.getKey()).append("\": ").append(entry.getValue());
                count++;
            }

            json.append("\n}");
            writer.write(json.toString());
            System.out.println("Results written to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
