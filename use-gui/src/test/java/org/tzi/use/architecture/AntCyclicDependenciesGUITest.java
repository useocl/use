package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AntCyclicDependenciesGUITest {

    private final JavaClasses classes = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("org.tzi.use.gui");

    private static final String PROJECT_ROOT = new File("").getAbsolutePath();
    private static final String ALL_PACKAGES_RESULTS_FILE = new File(PROJECT_ROOT, "ant_cyclic_dependencies_entire_gui_results.csv").getAbsolutePath();
    private static final String GRAPHLAYOUT_PACKAGE_RESULTS = new File(PROJECT_ROOT, "ant_cyclic_dependencies_graphlayout_results.csv").getAbsolutePath();
    private static final String MAIN_PACKAGE_RESULTS = new File(PROJECT_ROOT, "ant_cyclic_dependencies_main_gui_results.csv").getAbsolutePath();
    private static final String UTIL_PACKAGE_RESULTS = new File(PROJECT_ROOT, "ant_cyclic_dependencies_util_gui_results.csv").getAbsolutePath();
    private static final String VIEWS_PACKAGE_RESULTS = new File(PROJECT_ROOT, "ant_cyclic_dependencies_views_results.csv").getAbsolutePath();
    private static final String XMLPARSER_PACKAGE_RESULTS = new File(PROJECT_ROOT, "ant_cyclic_dependencies_xmlparser_results.csv").getAbsolutePath();

    @Before
    public void setup() {
        try {
            File file = new File(ALL_PACKAGES_RESULTS_FILE);
            System.out.println("CSV FILE CREATED IN: " + file.getAbsolutePath());
            if (file.exists()) {
                file.delete();
            }
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @ArchTest
    public void count_cycles_in_gui() {
        System.out.println("Counting cycles in gui...");
        System.out.println("Attempting to write to " + ALL_PACKAGES_RESULTS_FILE);
        int cycleCount = countCyclesForPackage("org.tzi.use.gui");
        writeResult(cycleCount, ALL_PACKAGES_RESULTS_FILE);
        System.out.println("Number of cycles in core: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_graphlayout_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.gui.graphlayout");
        writeResult(cycleCount, GRAPHLAYOUT_PACKAGE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.gui.graphlayout: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_main_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.gui.main");
        writeResult(cycleCount, MAIN_PACKAGE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.gui.main: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_util_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.gui.util");
        writeResult(cycleCount, UTIL_PACKAGE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.gui.util: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_views_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.gui.views");
        writeResult(cycleCount, VIEWS_PACKAGE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.gui.views: " + cycleCount);
    }

    @Test
    @ArchTest
    public void count_cycles_in_xmlparser_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.gui.xmlparser");
        writeResult(cycleCount, XMLPARSER_PACKAGE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.gui.xmlparser: " + cycleCount);
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

    private void writeResult(int result, String filename) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename, true))) {
            out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
