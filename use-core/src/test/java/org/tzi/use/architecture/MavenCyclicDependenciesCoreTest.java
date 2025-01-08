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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MavenCyclicDependenciesCoreTest {

    // hier sind keine Tests drin, überlegen, ob das so soll oder nicht
    // mit Tests: 293 Zyklen, ohne 55 ! und ohne uml sogar nur 5
    // hier sind nur core Klassen drin, weil core keinen Zugriff auf gui hat
    // also u.U. auch so einen Test f. gui
    private JavaClasses classes;

    private static final String ALL_MODULES_RESULTS = "maven_cyclic_dependencies_results.csv";
    private static final String ANALYSIS_MODULE_RESULTS = "maven_cyclic_dependencies_analysis_results.csv";
    private static final String API_MODULE_RESULTS = "maven_cyclic_dependencies_api_results.csv";
    private static final String CONFIG_MODULE_RESULTS = "maven_cyclic_dependencies_config_results.csv";
    private static final String GEN_MODULE_RESULTS = "maven_cyclic_dependencies_gen_results.csv";
    private static final String GRAPH_MODULE_RESULTS = "maven_cyclic_dependencies_graph_results.csv";
    private static final String MAIN_MODULE_RESULTS = "maven_cyclic_dependencies_main_results.csv";
    private static final String PARSER_MODULE_RESULTS = "maven_cyclic_dependencies_parser_results.csv";
    private static final String UML_MODULE_RESULTS = "maven_cyclic_dependencies_uml_results.csv";
    private static final String UTIL_MODULE_RESULTS = "maven_cyclic_dependencies_util_results.csv";

    @BeforeEach
    public void setup() {
        // Delete the results file if it exists
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("org.tzi.use");

        File file = new File(ALL_MODULES_RESULTS);
        if (file.exists()) {
            file.delete();
        }
        //System.out.println("No. of imported classes : " + classes.size());
    }

    @Test
    @ArchTest
    public void count_cycles_in_core() {
        int cycleCount = countCyclesForPackage("org.tzi.use");
        writeResult(cycleCount, ALL_MODULES_RESULTS);
        System.out.println("Cycles in core module : " + cycleCount);
        //assertTrue("Cycle count should be non-negative", cycleCount >= 0);
    }

    @Test
    @ArchTest
    public void count_cycles_in_analysis_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.analysis");
        writeResult(cycleCount, ANALYSIS_MODULE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.analysis: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_api_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.api");
        writeResult(cycleCount, API_MODULE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.api: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_config_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.config");
        writeResult(cycleCount, CONFIG_MODULE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.config: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_gen_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.gen");
        writeResult(cycleCount, GEN_MODULE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.gen: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_graph_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.graph");
        writeResult(cycleCount, GRAPH_MODULE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.graph: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_main_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.main");
        writeResult(cycleCount, MAIN_MODULE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.main: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_parser_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.parser");
        writeResult(cycleCount, PARSER_MODULE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.parser: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_uml_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.uml");
        writeResult(cycleCount, UML_MODULE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.uml: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_util_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.util");
        writeResult(cycleCount, UTIL_MODULE_RESULTS);
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

    private void writeResult(int result, String filename) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename, true))){
            out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
