package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MavenCyclicDependenciesCoreTest {

    // hier sind keine Tests drin, überlegen, ob das so soll oder nicht
    // mit Tests: 293 Zyklen, ohne 55 ! und ohne uml sogar nur 5
    // hier sind nur core Klassen drin, weil core keinen Zugriff auf gui hat
    // also u.U. auch so einen Test f. gui
    private JavaClasses classes;

    // Single json for all results
    private static final String JSON_RESULTS_FILE = "cyclic_dependencies_results.json";

    // Map to store all cycle counts
    private Map<String, Integer> cycleCounts = new HashMap<>();

    @BeforeAll
    public void setup() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("org.tzi.use");

        // Initialize or reset the cycle counts map
        cycleCounts = new HashMap<>();

        // Delete the results file if it exists
        File file = new File(JSON_RESULTS_FILE);
        if (file.exists()) {
            file.delete();
        }
        //System.out.println("No. of imported classes : " + classes.size());
    }

    @Test
    @ArchTest
    public void count_cycles_in_core_without_tests() {
        int cycleCount = countCyclesForPackage("org.tzi.use");
        cycleCounts.put("all_modules", cycleCount);
        System.out.println("Cycles in core module : " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_analysis_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.analysis");
        cycleCounts.put("analysis", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.analysis: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_api_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.api");
        cycleCounts.put("api", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.api: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_config_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.config");
        cycleCounts.put("config", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.config: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_gen_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.gen");
        cycleCounts.put("gen", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.gen: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_graph_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.graph");
        cycleCounts.put("graph", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.graph: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_main_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.main");
        cycleCounts.put("main", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.main: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_parser_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.parser");
        cycleCounts.put("parser", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.parser: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_uml_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.uml");
        cycleCounts.put("uml", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.uml: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_util_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.util");
        cycleCounts.put("util", cycleCount);
        System.out.println("Number of cycles in org.tzi.use.util: " + cycleCount);
    }

    @AfterAll
    public void writeAllResults() {
        // Write the JSON file after all tests have completed
        writeJsonResults();
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

    private void writeJsonResults() {
        try (FileWriter writer = new FileWriter(JSON_RESULTS_FILE)) {
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
            System.out.println("Results written to " + JSON_RESULTS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
