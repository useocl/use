package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.EvaluationResult;
import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MavenCyclicDependenciesGUITest {

    // It is impossible to import only GUI packages here due to naming duplications
    // (org.tzi.use.util, org.tzi.use.util.input, org.tzi.use.main),
    // and because the GUI accesses these packages from `use-core`.
    // Therefore, the number of cycles in the GUI can never be accurately measured.
    // Only subpackages with unique names can be analyzed.
    private final JavaClasses classes = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOuse-gui/target/archunit-results use-gui/target/archunit-reportsT_INCLUDE_TESTS)
            .importPackages("org.tzi.use");

    private static final String RESULTS_DIR = new File("target/archunit-results").getAbsolutePath();
    private static final String REPORTS_DIR = new File("target/archunit-reports").getAbsolutePath();
    private static final String ENTIRE_PROJECT_RESULTS = new File(RESULTS_DIR,
            "maven_cyclic_dependencies_entire_project_results.csv").getAbsolutePath();
    private static final String GUI_PACKAGE_RESULTS = new File(RESULTS_DIR, "maven_cyclic_dependencies_gui_results.csv")
            .getAbsolutePath();
    private static final String RUNTIME_PACKAGE_RESULTS = new File(RESULTS_DIR,
            "maven_cyclic_dependencies_runtime_results.csv").getAbsolutePath();
    private static final String SHELL_PACKAGE_RESULTS = new File(RESULTS_DIR,
            "maven_cyclic_dependencies_shell_results.csv").getAbsolutePath();
    private static final String GUI_MAIN_PACKAGE_RESULTS = new File(RESULTS_DIR,
            "maven_cyclic_dependencies_gui_main_results.csv").getAbsolutePath();
    private static final String GUI_VIEWS_PACKAGE_RESULTS = new File(RESULTS_DIR,
            "maven_cyclic_dependencies_gui_views_results.csv").getAbsolutePath();

    @Before
    public void setup() {
        // Delete the results file if it exists
        File file = new File(GUI_PACKAGE_RESULTS);
        if (file.exists()) {
            file.delete();
        }
        System.out.println("No. of imported classes : " + classes.size());
    }

    @Test
    public void count_cycles_in_gui_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.gui", "gui");
        writeResult(cycleCount, GUI_PACKAGE_RESULTS);
        System.out.println("Cycles in gui package : " + cycleCount);
    }

    @Test
    public void count_cycles_in_runtime_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.runtime", "runtime");
        writeResult(cycleCount, RUNTIME_PACKAGE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.runtime: " + cycleCount);
    }

    @Test
    public void count_cycles_in_shell_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.main.shell", "shell");
        writeResult(cycleCount, SHELL_PACKAGE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.main.shell: " + cycleCount);
    }

    @Test
    public void count_cycles_in_gui_main_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.gui.main", "gui_main");
        writeResult(cycleCount, GUI_MAIN_PACKAGE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.gui.main: " + cycleCount);
    }

    @Test
    public void count_cycles_in_gui_views_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.gui.views", "gui_views");
        writeResult(cycleCount, GUI_VIEWS_PACKAGE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.gui.views: " + cycleCount);
    }

    @Test
    public void count_cycles_in_entire_project() {
        int cycleCount = countCyclesForPackage("org.tzi.use", "entire_project");
        writeResult(cycleCount, ENTIRE_PROJECT_RESULTS);
        System.out.println("Number of cycles in entire project: " + cycleCount);
    }

    private int countCyclesForPackage(String packageName, String shortName) {
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
        writeFailureReport(result, shortName, cycleCount);
        return cycleCount;
    }

    private void writeFailureReport(EvaluationResult result, String shortName, int cycleCount) {
        if (cycleCount == 0) {
            return;
        }
        File reportsDir = new File(REPORTS_DIR);
        if (!reportsDir.exists() && !reportsDir.mkdirs()) {
            System.err.println("Could not create reports directory: " + REPORTS_DIR);
            return;
        }
        String filename = new File(reportsDir,
                "failure_report_maven_cycles_" + shortName + ".txt").getAbsolutePath();
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            for (String detail : result.getFailureReport().getDetails()) {
                out.println(detail);
            }
            out.println();
            out.println("Cycle count: " + cycleCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeResult(int result, String filename) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename, true))) {
            out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
