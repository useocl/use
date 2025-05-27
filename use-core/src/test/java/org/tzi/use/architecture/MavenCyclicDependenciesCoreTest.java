package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.EvaluationResult;
import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MavenCyclicDependenciesCoreTest {

    // hier sind keine Tests drin, überlegen, ob das so soll oder nicht
    // mit Tests: 293 Zyklen, ohne 55 ! und ohne uml sogar nur 5
    // hier sind nur core Klassen drin, weil core keinen Zugriff auf gui hat
    // also u.U. auch so einen Test f. gui
    private JavaClasses classesWithoutTests;
    private JavaClasses classesWithTests;

    @BeforeAll
    public void setup() {
        // Create reports directory if it doesn't exist
        File reportsDir = new File("target/archunit-reports");
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }

        // Import classes without tests
        classesWithoutTests = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("org.tzi.use");

        // Import classes with tests
        classesWithTests = new ClassFileImporter()
                //.withImportOption(ImportOption.Predefined.ONLY_INCLUDE_TESTS)
                .importPackages("org.tzi.use");
    }

    @Test
    @ArchTest
    public void count_cycles_in_core_without_tests() {
        int cycleCount = countCyclesForPackage("org.tzi.use", classesWithoutTests, false);
        System.out.println("Cycles in core module without tests : " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_core_with_tests() {
        int cycleCount = countCyclesForPackage("org.tzi.use", classesWithTests, true);
        System.out.println("Cycles in core module with tests : " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_analysis_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.analysis", classesWithoutTests, false);
        System.out.println("Number of cycles in org.tzi.use.analysis without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.analysis", classesWithTests, true);
        System.out.println("Number of cycles in org.tzi.use.analysis with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_api_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.api", classesWithoutTests, false);
        System.out.println("Number of cycles in org.tzi.use.api without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.api", classesWithTests, true);
        System.out.println("Number of cycles in org.tzi.use.api with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_config_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.config", classesWithoutTests, false);
        System.out.println("Number of cycles in org.tzi.use.config without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.config", classesWithTests, true);
        System.out.println("Number of cycles in org.tzi.use.config with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_gen_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.gen", classesWithoutTests, false);
        System.out.println("Number of cycles in org.tzi.use.gen without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.gen", classesWithTests, true);
        System.out.println("Number of cycles in org.tzi.use.gen with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_graph_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.graph", classesWithoutTests, false);
        System.out.println("Number of cycles in org.tzi.use.graph without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.graph", classesWithTests, true);
        System.out.println("Number of cycles in org.tzi.use.graph with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_main_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.main", classesWithoutTests, false);
        System.out.println("Number of cycles in org.tzi.use.main without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.main", classesWithTests, true);
        System.out.println("Number of cycles in org.tzi.use.main with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_parser_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.parser", classesWithoutTests, false);
        System.out.println("Number of cycles in org.tzi.use.parser without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.parser", classesWithTests, true);
        System.out.println("Number of cycles in org.tzi.use.parser with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_uml_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.uml", classesWithoutTests, false);
        System.out.println("Number of cycles in org.tzi.use.uml without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.uml", classesWithTests, true);
        System.out.println("Number of cycles in org.tzi.use.uml with tests: " + cycleCountWithTests);
    }

    @Test
    @ArchTest
    public void count_cycles_in_util_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.util", classesWithoutTests, false);
        System.out.println("Number of cycles in org.tzi.use.util without tests: " + cycleCount);

        int cycleCountWithTests = countCyclesForPackage("org.tzi.use.util", classesWithTests, true);
        System.out.println("Number of cycles in org.tzi.use.util with tests: " + cycleCountWithTests);
    }

    private int countCyclesForPackage(String packageName, JavaClasses classes, boolean withTests) {
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

        EvaluationResult result = SlicesRuleDefinition.slices()
                .assignedFrom(sliceAssignment)
                .should().beFreeOfCycles()
                .allowEmptyShould(true)
                .evaluate(classes);

        int cycleCount = result.getFailureReport().getDetails().size();

        if (cycleCount > 0) {
            // Extract package short name
            String packageShortName = packageName.equals("org.tzi.use") ? "core" : packageName.substring(packageName.lastIndexOf('.')+1);

            // Write failure report to file
            String testSuffix = withTests ? "with_tests" : "without_tests";
            String filename = String.format("target/archunit-reports/failure_report_maven_cycles_%s_%s.txt", packageShortName, testSuffix);

            try (FileWriter writer = new FileWriter(filename)) {
                for (String detail : result.getFailureReport().getDetails()) {
                    writer.write(detail + "\n");
                }
                writer.write("\nCycle count: " + cycleCount + "\n");
            } catch (IOException e) {
                System.err.println("Error writing report to " + filename + ": " + e.getMessage());
            }
        }
        return cycleCount;
    }
}
