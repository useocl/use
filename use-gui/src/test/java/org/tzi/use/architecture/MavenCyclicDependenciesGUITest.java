package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
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

public class MavenCyclicDependenciesGUITest {

    // hier ist es nicht möglich, nur GUI Packages zu importieren, wg. Namensdopplungen
    // (org.tzi.use.util, org.tzi.use.util.input, org.tzi.use.main) & da GUI Zugriff auf
    // diese Packages von Core hat. Dh. kann nie akkurat die Anzahl an Cycles in GUI gemessen
    // werden. Es können höchsten diejenigen Subpackages analysiert werden, die eindeutige
    // Namen besitzen.
    private final JavaClasses classes = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("org.tzi.use");

    private static final String ENTIRE_PROJECT_RESULTS = "maven_cyclic_dependencies_entire_project_results.csv";
    private static final String GUI_PACKAGE_RESULTS = "maven_cyclic_dependencies_gui_results.csv";
    private static final String RUNTIME_PACKAGE_RESULTS = "maven_cyclic_dependencies_runtime_results.csv";
    private static final String SHELL_PACKAGE_RESULTS = "maven_cyclic_dependencies_shell_results.csv";

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
        int cycleCount = countCyclesForPackage("org.tzi.use.gui");
        writeResult(cycleCount, GUI_PACKAGE_RESULTS);
        System.out.println("Cycles in gui package : " + cycleCount);
    }

    @Test
    public void count_cycles_in_runtime_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.runtime");
        writeResult(cycleCount, RUNTIME_PACKAGE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.runtime: " + cycleCount);
    }

    @Test
    public void count_cycles_in_shell_package() {
        int cycleCount = countCyclesForPackage("org.tzi.use.main.shell");
        writeResult(cycleCount, SHELL_PACKAGE_RESULTS);
        System.out.println("Number of cycles in org.tzi.use.main.shell: " + cycleCount);
    }

    @Test
    public void count_cycles_in_entire_project() {
        int cycleCount = countCyclesForPackage("org.tzi.use");
        writeResult(cycleCount, ENTIRE_PROJECT_RESULTS);
        System.out.println("Number of cycles in entire project: " + cycleCount);
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
