package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.Dependency;
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
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MavenCyclicDependenciesGUITest {

    // It is impossible to import only GUI packages here due to naming duplications
    // (org.tzi.use.util, org.tzi.use.util.input, org.tzi.use.main),
    // and because the GUI accesses these packages from `use-core`.
    // Therefore, the number of cycles in the GUI can never be accurately measured.
    // Only subpackages with unique names can be analyzed.
    private final JavaClasses classes = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
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
    private static final String GUI_VIEWS_DIAGRAMS_PACKAGE_RESULTS = new File(RESULTS_DIR,
            "maven_cyclic_dependencies_gui_views_diagrams_results.csv").getAbsolutePath();

    @Before
    public void setup() {
        File resultsDir = new File(RESULTS_DIR);
        if (!resultsDir.exists() && !resultsDir.mkdirs()) {
            System.err.println("Could not create results directory: " + RESULTS_DIR);
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

    /**
     * Blind-spot MEASUREMENT for the diagram-editor subtree (deliberately non-asserting).
     *
     * <p>No gated arch test roots a slice at {@code org.tzi.use.gui.views.diagrams} — the
     * deepest GUI root any of them uses is {@code org.tzi.use.gui.views} — so the large
     * pre-existing internal cycle web there (the {@code DiagramView} framework mutually
     * referencing {@code elements}/{@code event}/{@code waypoints}/...) is invisible to CI.
     *
     * <p>This records two STABLE structural metrics instead of the raw cycle count:
     * the number of first-level sub-slices that sit in a non-trivial strongly-connected
     * component, and the number of inter-slice dependency edges among those slices
     * ("feedback edges"). Raw cycle count is unusable here: it is combinatorial (removing
     * one edge can erase hundreds) AND ArchUnit caps detection at
     * {@code cycles.maxNumberToDetect} (=600 in this project) so it merely saturates — the
     * "~600 cycles" figure is that cap, not a true count. Cyclic-slice count and feedback
     * edges move monotonically as real back-edges are removed: e.g. relocating the
     * {@code edges.GUI} demo out of {@code util} took the cyclic-slice set from 11 to 9
     * (both {@code util} and {@code edges} left it).
     *
     * <p>The eventual decycling goal is 0 cyclic slices. Until the framework is untangled
     * (phases B2..B4) this stays non-asserting so it documents the debt without gating the
     * build on it.
     */
    @Test
    public void measure_cycles_in_gui_views_diagrams_package() {
        final String root = "org.tzi.use.gui.views.diagrams";
        Map<String, Set<String>> graph = buildSliceGraph(root);
        Set<String> cyclic = cyclicSlices(graph);

        int feedbackEdges = 0;
        for (Map.Entry<String, Set<String>> entry : graph.entrySet()) {
            if (!cyclic.contains(entry.getKey())) {
                continue;
            }
            for (String target : entry.getValue()) {
                if (cyclic.contains(target)) {
                    feedbackEdges++;
                }
            }
        }

        writeResult(cyclic.size(), GUI_VIEWS_DIAGRAMS_PACKAGE_RESULTS);
        System.out.println("gui.views.diagrams decycling metric (MEASURED, not gated): "
                + cyclic.size() + " slices in cycles " + new TreeSet<>(cyclic)
                + "; " + feedbackEdges + " inter-slice feedback edges");
    }

    /**
     * Builds the directed slice graph for {@code rootPackage}, where each node is the
     * first-level sub-package under it (or {@code root} for classes directly in it), and an
     * edge {@code a -> b} means some class in slice {@code a} depends on a class in slice
     * {@code b}. Uses ArchUnit's full dependency view (supertypes, fields, params, generics,
     * ...), not just {@code import} statements.
     */
    private Map<String, Set<String>> buildSliceGraph(String rootPackage) {
        Map<String, Set<String>> graph = new HashMap<>();
        for (JavaClass clazz : classes) {
            String from = sliceId(clazz, rootPackage);
            if (from == null) {
                continue;
            }
            graph.computeIfAbsent(from, k -> new HashSet<>());
            for (Dependency dependency : clazz.getDirectDependenciesFromSelf()) {
                String to = sliceId(dependency.getTargetClass(), rootPackage);
                if (to == null || to.equals(from)) {
                    continue;
                }
                graph.get(from).add(to);
                graph.computeIfAbsent(to, k -> new HashSet<>());
            }
        }
        return graph;
    }

    /** First-level sub-slice of {@code clazz} under {@code rootPackage}, or {@code null} if outside it. */
    private String sliceId(JavaClass clazz, String rootPackage) {
        String pkg = clazz.getPackageName();
        if (pkg.equals(rootPackage)) {
            return "root";
        }
        if (!pkg.startsWith(rootPackage + ".")) {
            return null;
        }
        return pkg.substring(rootPackage.length() + 1).split("\\.")[0];
    }

    /** Slices that belong to a non-trivial strongly-connected component (Tarjan's algorithm). */
    private Set<String> cyclicSlices(Map<String, Set<String>> graph) {
        Map<String, Integer> index = new HashMap<>();
        Map<String, Integer> low = new HashMap<>();
        Deque<String> stack = new ArrayDeque<>();
        Set<String> onStack = new HashSet<>();
        Set<String> cyclic = new HashSet<>();
        int[] counter = {0};
        for (String node : graph.keySet()) {
            if (!index.containsKey(node)) {
                strongConnect(node, graph, index, low, stack, onStack, counter, cyclic);
            }
        }
        return cyclic;
    }

    private void strongConnect(String v, Map<String, Set<String>> graph,
            Map<String, Integer> index, Map<String, Integer> low, Deque<String> stack,
            Set<String> onStack, int[] counter, Set<String> cyclic) {
        index.put(v, counter[0]);
        low.put(v, counter[0]);
        counter[0]++;
        stack.push(v);
        onStack.add(v);
        for (String w : graph.getOrDefault(v, Collections.emptySet())) {
            if (!index.containsKey(w)) {
                strongConnect(w, graph, index, low, stack, onStack, counter, cyclic);
                low.put(v, Math.min(low.get(v), low.get(w)));
            } else if (onStack.contains(w)) {
                low.put(v, Math.min(low.get(v), index.get(w)));
            }
        }
        if (low.get(v).equals(index.get(v))) {
            List<String> component = new ArrayList<>();
            String w;
            do {
                w = stack.pop();
                onStack.remove(w);
                component.add(w);
            } while (!w.equals(v));
            if (component.size() > 1) {
                cyclic.addAll(component);
            }
        }
    }

    private int countCyclesForPackage(String packageName, String shortName) {
        int cycleCount = evaluateCycles(packageName, shortName);
        assertEquals("Cyclic dependencies detected in " + packageName, 0, cycleCount);
        return cycleCount;
    }

    /**
     * Evaluates the cyclic-dependency slice rule for {@code packageName} (sliced by the
     * first sub-package under it), writes the failure report, and returns the cycle
     * count <strong>without</strong> asserting. Callers decide whether to gate on it.
     */
    private int evaluateCycles(String packageName, String shortName) {
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
        File reportsDir = new File(REPORTS_DIR);
        if (!reportsDir.exists() && !reportsDir.mkdirs()) {
            System.err.println("Could not create reports directory: " + REPORTS_DIR);
            return;
        }
        File reportFile = new File(reportsDir, "failure_report_maven_cycles_" + shortName + ".txt");
        if (cycleCount == 0) {
            // Drop any stale report from a previous run so the directory reflects the current state.
            if (reportFile.exists() && !reportFile.delete()) {
                System.err.println("Could not delete stale report: " + reportFile.getAbsolutePath());
            }
            return;
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(reportFile))) {
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
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
