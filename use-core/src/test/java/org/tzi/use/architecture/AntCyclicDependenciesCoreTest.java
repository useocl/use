package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.Location;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.EvaluationResult;
import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AntCyclicDependenciesCoreTest {

    private JavaClasses classes;

    @Before
    public void setup() {
        // Create reports directory if it doesn't exist
        File reportsDir = new File("target/archunit-reports");
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }

        classes = new ClassFileImporter()
                //.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .withImportOption(new CustomTestExclusionOption())
                .importPackages("org.tzi.use")
                .that(JavaClass.Predicates.resideOutsideOfPackage("org.tzi.use.gui.."))
                .that(JavaClass.Predicates.resideOutsideOfPackage("org.tzi.use.runtime.."))
                .that(JavaClass.Predicates.resideInAnyPackage("org.tzi.use.analysis..",
                        "org.tzi.use.api..", "org.tzi.use.config..", "org.tzi.use.gen..",
                        "org.tzi.use.graph..", "org.tzi.use.main..", "org.tzi.use.parser..",
                        "org.tzi.use.uml..", "org.tzi.use.util.."));
        System.out.println("No of classes incl.: " + classes.size());
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

        EvaluationResult result = SlicesRuleDefinition.slices()
                .assignedFrom(sliceAssignment)
                .should().beFreeOfCycles()
                .allowEmptyShould(true)
                .evaluate(classes);

        int cycleCount = result.getFailureReport().getDetails().size();

        if (cycleCount > 0) {
            //Extract package short name
            String packageShortName = packageName.equals("org.tzi.use") ? "core" : packageName.substring(packageName.lastIndexOf('.')+1);
            String filename = String.format("target/archunit-reports/failure_report_maven_cycles_%s.txt", packageShortName);

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

    class CustomTestExclusionOption implements ImportOption {

        @Override
        public boolean includes(Location location) {

            String path = location.toString();

            // SystemManipulator does not exist in later project
            // TestModelUtil is moved to core in later project
            if (path.contains("Test") ||
                    path.contains("ObjectCreation")
            ) {
                return false;
            }

            return true;
        }
    }
}