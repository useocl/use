# Bug 2 plan — `gui.main` and `gui.views` internal cycles

Status: **Planned**, not yet implemented.
Location: `org.tzi.use.gui.main` ↔ `org.tzi.use.gui.main.runtime`
and `org.tzi.use.gui.views.diagrams` ↔ `org.tzi.use.gui.views.selection`
(both in `use-gui`).

## What the ArchUnit report actually shows

Two cycles, both Swing-side, reported by the slice rules
`MavenCyclicDependenciesGuiTest` for the `gui.main` and `gui.views`
slices.

### 2a — `gui.main`: `root → runtime → root` (1 cycle, 2 edges)

`use-gui/target_archunit_temp/archunit-reports/failure_report_maven_cycles_gui_main.txt`
shows:

- **root → runtime** (callers in `gui.main` reaching into `gui.main.runtime`):
  - `ModelBrowser.displayInfo(...)` calls
    `IPluginMModelExtensionPoint.createMMHTMLPrintVisitor(PrintWriter, ModelBrowser)`
    (`ModelBrowser.java:322`).
  - `MainWindow.<init>(Session, IRuntime)` calls
    `IPluginActionExtensionPoint.createPluginActions(Session, MainWindow)`
    (`MainWindow.java:462`).
- **runtime → root** (the runtime SPI signatures naming concrete root types):
  - `IPluginActionExtensionPoint.createPluginActions(...)` takes a
    `MainWindow`.
  - `IPluginMMVisitor.modelBrowser()` returns a `ModelBrowser`.
  - `IPluginMModelExtensionPoint.createMMHTMLPrintVisitor(PrintWriter, ModelBrowser)`
    takes a `ModelBrowser`.
  - `IPluginMModelExtensionPoint.createMMPrintVisitor(PrintWriter, ModelBrowser)`
    takes a `ModelBrowser`.

This is structurally **identical** to Bug 8 (`shell ↔ shell.runtime`):
a runtime/SPI subpackage refers to a concrete class in its parent
package, and the parent package implements those SPIs against the
concretes. Bug 8's fix — introduce marker interfaces in the runtime
SPI package — applies cleanly here.

### 2b — `gui.views`: `diagrams ↔ selection` (1 cycle, many edges)

`use-gui/target_archunit_temp/archunit-reports/failure_report_maven_cycles_gui_views.txt`
shows a *much* denser cycle than 2a:

- **diagrams → selection** (25 listed + 15 omitted): `ClassDiagram`,
  `NewObjectDiagram`, `CommunicationDiagram`, and `SequenceDiagram`
  hold `selection.*` fields (`fSelection`), invoke `ObjectSelection` /
  `ClassSelection` methods to build pop-up menus, and `DiagramView`
  classes implement the `DataHolder` interface from
  `selection.objectselection`.
- **selection → diagrams** (25 listed + 279 omitted): nearly every
  selection view/model class (`ClassSelectionView`, `ObjectSelectionView`,
  `SelectionClassTableModel`, `ObjectSelection`, `ClassSelection`,
  `ActionSelectionOCLView`, `SelectionOCLView`, `SelectedClassPathView`,
  `SelectedObjectPathView`, …) takes a concrete diagram type
  (`ClassDiagram`, `DiagramViewWithObjectNode`, etc.) as a
  constructor parameter or field, plus references to
  `gui.views.diagrams.event.DiagramInputHandling`,
  `gui.views.diagrams.ObjectNodeActivity`.

Trying to "break" this cycle by abstracting one direction would mean
introducing a dozen+ interfaces — every selection view that takes a
`ClassDiagram` or a `DiagramViewWithObjectNode` would need a
corresponding `IClassDiagram` / `IDiagramViewWithObjectNode` interface
extracted, and the selection-side back-references (`fSelection` field
in `ClassDiagram`) would still need an interface. With 304 listed
edges, this is the wrong shape of fix.

**Observation:** the selection package is structurally a sub-component
of the diagrams package. Every selection class is parameterised by a
specific diagram type — `ClassSelection` is *for* `ClassDiagram`,
`ObjectSelection` is *for* `DiagramViewWithObjectNode`. Nothing
outside the diagrams stack instantiates a selection class. The only
reason this is a "cycle" is the package boundary: both sides are part
of one logical feature ("select & filter elements in a diagram") that
was split across two top-level packages.

The clean move is therefore: **collapse the selection package into
the diagrams package** (`gui.views.selection.*` →
`gui.views.diagrams.selection.*`). Once both sides live under the
same first sub-package of `gui.views`, they belong to the same
ArchUnit slice (`diagrams`) and the cycle disappears in one move —
same shape of fix used by Bug 5 (`gen.tool.GSignature` → `gen.assl`).

## Fix plan

### Prong A — `gui.main` cycle via marker interfaces (1 → 0)

Mirror Bug 8 exactly:

1. **Introduce two marker interfaces** in
   `use-gui/src/main/java/org/tzi/use/gui/main/runtime/`:

   ```java
   // IMainWindow.java
   package org.tzi.use.gui.main.runtime;

   /**
    * SPI-facing handle to the application's main window.
    * MainWindow implements this so runtime SPIs can refer to it
    * without depending on the concrete root-package class.
    * Intentionally a marker interface; downcast if needed.
    */
   public interface IMainWindow {}
   ```

   ```java
   // IModelBrowser.java
   package org.tzi.use.gui.main.runtime;

   /**
    * SPI-facing handle to the model-browser tree.
    * ModelBrowser implements this. Marker; downcast if needed.
    */
   public interface IModelBrowser {}
   ```

2. **Have the concrete classes implement them** (no behavior change,
   just a marker `implements` clause):
   - `gui.main.MainWindow` → `implements IMainWindow`
   - `gui.main.ModelBrowser` → `implements IModelBrowser`

3. **Re-type the SPI signatures** in the three SPI files so they
   reference only `gui.main.runtime`-local types:

   | File | Change |
   |---   |---     |
   | `runtime/IPluginActionExtensionPoint.java` | `createPluginActions(Session, MainWindow)` → `createPluginActions(Session, IMainWindow)`. Drop `import org.tzi.use.gui.main.MainWindow;`. |
   | `runtime/IPluginMMVisitor.java`            | `ModelBrowser modelBrowser()` → `IModelBrowser modelBrowser()`. Drop `import org.tzi.use.gui.main.ModelBrowser;`. |
   | `runtime/IPluginMModelExtensionPoint.java` | Both `createMM*Visitor(PrintWriter, ModelBrowser)` parameters → `IModelBrowser`. Drop the `ModelBrowser` import. |

4. **Adjust the impl classes in `runtime.gui.impl..`** (these are
   already outside the cycle — they live in the `runtime` slice — but
   their method signatures must continue to match the SPIs):
   - `runtime/gui/impl/ActionExtensionPoint.java` —
     `createPluginActions(Session, IMainWindow mainWindow)`. The body
     currently passes `mainWindow` along; if any internal code needs
     `MainWindow`-specific methods, downcast at that point (Bug 8
     uses the same approach for `IShell` → `Shell`).
   - `runtime/gui/impl/MModelExtensionPoint.java` — same for
     `ModelBrowser` ↔ `IModelBrowser`.
   - `runtime/gui/impl/PluginMMPrintVisitor.java` and
     `PluginMMHTMLPrintVisitor.java` — their constructors and the
     `modelBrowser()` getter must return/accept `IModelBrowser`.
     The field type can stay `ModelBrowser` (these impls are inside
     `runtime.gui.impl`, so importing `gui.main.ModelBrowser` is
     allowed by the slice rule), but the return type of the SPI method
     must be `IModelBrowser`.

5. **Call sites that pass `this`** from `MainWindow` /
   `ModelBrowser` to an SPI method don't need any change — Java
   widens to the interface implicitly. So
   `MainWindow.<init>` line 462 (`createPluginActions(session, this)`)
   and `ModelBrowser.displayInfo` line 322
   (`createMMHTMLPrintVisitor(out, this)`) compile as-is.

**JavaFX twin:** `gui.mainFX.runtime` has parallel SPIs
(`IPluginMMVisitor`, `IPluginMModelExtensionPoint`) that *probably*
have the same problem. Check the
`failure_report_maven_cycles_gui.txt` (the whole-gui cycle report)
for an analogous mainFX cycle; if present, apply the same fix in
mainFX. If absent, leave mainFX alone — out of scope for this bug.

**Module-info:** `org.tzi.use.gui.main.runtime` is implicitly available
to the `runtime.gui.impl` classes because they're inside `use.gui`;
no `exports` change is needed.

### Prong B — `gui.views` cycle by collapsing selection into diagrams (1 → 0)

Move every file under `org.tzi.use.gui.views.selection.**` into
`org.tzi.use.gui.views.diagrams.selection.**`. Both sides of the
cycle become one ArchUnit slice (`diagrams`), and the cycle vanishes.

#### Files moving (19 source files)

```
gui/views/selection/ClassSelectionView.java          → gui/views/diagrams/selection/ClassSelectionView.java
gui/views/selection/ObjectSelectionView.java         → gui/views/diagrams/selection/ObjectSelectionView.java
gui/views/selection/TableModel.java                  → gui/views/diagrams/selection/TableModel.java
gui/views/selection/SelectionComparator.java         → gui/views/diagrams/selection/SelectionComparator.java
gui/views/selection/classselection/ClassPathTableModel.java       → gui/views/diagrams/selection/classselection/…
gui/views/selection/classselection/ClassSelection.java            → …
gui/views/selection/classselection/SelectedClassPathView.java     → …
gui/views/selection/classselection/SelectionClassTableModel.java  → …
gui/views/selection/classselection/SelectionClassView.java        → …
gui/views/selection/objectselection/ActionSelectionOCLView.java       → gui/views/diagrams/selection/objectselection/…
gui/views/selection/objectselection/ActionSelectionObjectView.java    → …
gui/views/selection/objectselection/DataHolder.java                   → …
gui/views/selection/objectselection/ObjectPathTableModel.java         → …
gui/views/selection/objectselection/ObjectSelection.java              → …
gui/views/selection/objectselection/ObjectSelectionHelper.java        → …
gui/views/selection/objectselection/SelectedObjectPathView.java       → …
gui/views/selection/objectselection/SelectionOCLView.java             → …
gui/views/selection/objectselection/SelectionObjectTableModel.java    → …
gui/views/selection/objectselection/SelectionObjectView.java          → …
```

Do this with `git mv` to preserve history.

#### Edits required

1. **Package declarations**: every moved file gets
   `package org.tzi.use.gui.views.selection.* ;` rewritten to
   `package org.tzi.use.gui.views.diagrams.selection.* ;`.
2. **Internal imports between moved files**: any
   `import org.tzi.use.gui.views.selection.…` reference between the
   moved files becomes `import org.tzi.use.gui.views.diagrams.selection.…`.
3. **External callers** (5 files outside the selection tree):
   - `gui/views/diagrams/DiagramViewWithObjectNode.java`
   - `gui/views/diagrams/objectdiagram/NewObjectDiagram.java`
   - `gui/views/diagrams/classdiagram/ClassDiagram.java`
   - `gui/views/diagrams/behavior/communicationdiagram/CommunicationDiagram.java`
   - `gui/views/diagrams/behavior/sequencediagram/SequenceDiagram.java`

   Each imports types from `org.tzi.use.gui.views.selection.*`.
   Rewrite to `org.tzi.use.gui.views.diagrams.selection.*`. A
   sed-pass works because the FQN prefix change is mechanical.

4. **`module-info.java`**:
   - Replace `exports org.tzi.use.gui.views.selection to com.google.common;`
     with `exports org.tzi.use.gui.views.diagrams.selection to com.google.common;`.
   - The two subpackages
     (`gui.views.diagrams.selection.classselection`,
     `gui.views.diagrams.selection.objectselection`) were not
     previously exported, so they remain encapsulated.

5. **Verify no stragglers**:
   ```bash
   grep -rn 'org\.tzi\.use\.gui\.views\.selection' use-gui/src use-core/src
   ```
   Should be zero matches after the move + import sweep.

6. **Delete the now-empty source directories** under
   `use-gui/src/main/java/org/tzi/use/gui/views/selection/` (Git won't
   track empty dirs; `find … -empty -delete` is fine if they linger).

#### Expected slice outcome

After both Prongs the slice rule on `org.tzi.use.gui.views` reports
**0 cycles** (selection collapsed into the `diagrams` slice; the
`diagrams → diagrams` self-loop isn't a slice cycle). The
`gui.main` slice rule also reports **0 cycles** (no edges left from
`runtime` back to `root`).

## Risks / things to double-check

- **Reflection / config strings.** Plugins might address the old FQNs
  through XML configs (`/plugin.xml`-style metadata) or `Class.forName`.
  Grep both `use-gui/src` and any plugin examples for the literal
  string `gui.views.selection` (no wildcards) before declaring done.
  Currently `grep -rn '"org\.tzi\.use\.gui\.views\.selection"'` should
  return zero — verify.
- **`com.google.common` directive.** The existing
  `exports … to com.google.common;` line is the Guava module name.
  After the move, several inner classes that exposed
  `com.google.common.collect.Comparator`-style generics shift to a new
  package — the qualified export keeps Guava's reflective access
  working, but the JPMS check fails fast if a still-needed class is
  missed. If the build prints
  `module … does not export … to com.google.common`, the fix is to add
  the affected subpackage (likely `…diagrams.selection.objectselection`)
  to the qualified export.
- **`DataHolder` is implemented by `DiagramViewWithObjectNode` and
  `SequenceDiagram`** (intra-cycle). After the move, both
  implementing classes import `DataHolder` from the new FQN. Their
  `implements` clauses don't need re-typing — only the import line.
- **MainFX twin SPIs.** If the JavaFX-side runtime SPIs have the same
  `MainWindow`/`ModelBrowser` problem, fixing Prong A on Swing only
  may leave a `mainFX` slice still failing. Verify with
  `failure_report_maven_cycles_gui.txt` (full-gui slice) after Prong
  A — if a residual mainFX cycle appears, repeat Prong A on
  `gui.mainFX.runtime`. If no such report regenerates, no action
  needed.
- **Bug 7 already touched `Util` in `gui.views.diagrams.util`**;
  Prong B should not collide because the new directory is
  `gui.views.diagrams.selection`, not `…util`.
- **JavaFX FXML loaders are NOT in scope.** FXML files (`.fxml`)
  reference controllers by FQN. Selection classes are pure Swing —
  none are referenced from FXML resources. Confirm with
  `grep -rln 'gui\.views\.selection' use-gui/src/main/resources/`
  → expect zero.

## ⚙ Workflow for this PR — same shape as Bugs 5/6/7/8

1. **Capture before-state reports**:
   ```bash
   cp use-gui/target_archunit_temp/archunit-reports/failure_report_maven_cycles_gui_main.txt \
      docs/archunit-history/before-fix/bug-2_failure_report_maven_cycles_gui_main.txt
   cp use-gui/target_archunit_temp/archunit-reports/failure_report_maven_cycles_gui_views.txt \
      docs/archunit-history/before-fix/bug-2_failure_report_maven_cycles_gui_views.txt
   ```
2. **Apply Prong A** (interfaces + signature swap + impl updates).
   Compile.
3. **Apply Prong B** (`git mv` selection → diagrams/selection, sed
   the FQN, fix module-info). Compile.
4. **`mvn -pl use-core,use-gui -am clean package`** — confirm 0 test
   failures, both slice tests print `Cycle count: 0` (or stop emitting
   their report files when below threshold).
5. **Refresh `target_archunit_temp/`** for use-gui:
   ```bash
   cp -r use-gui/target/archunit-reports/. use-gui/target_archunit_temp/archunit-reports/
   cp -r use-gui/target/archunit-results/. use-gui/target_archunit_temp/archunit-results/
   ```
   The fresh build should NOT regenerate
   `failure_report_maven_cycles_gui_main.txt` or
   `failure_report_maven_cycles_gui_views.txt` (cycle test deletes
   stale reports at 0 — see post-Copilot fix in
   `MavenCyclicDependenciesGuiTest`). Delete any stale tracked copies:
   ```bash
   git rm use-gui/target_archunit_temp/archunit-reports/failure_report_maven_cycles_gui_main.txt
   git rm use-gui/target_archunit_temp/archunit-reports/failure_report_maven_cycles_gui_views.txt
   ```
   The `entire_project` failure report shifts as well — expect a
   large diff there; that's the same noise we accepted on Bugs 5/6/7.
6. **Smoke-test the GUI manually** (or note inability to). The
   selection menus are accessed via right-click in the class /
   object / communication / sequence diagrams. The bootstrap is
   `org.tzi.use.main.gui.Main` (Swing) — start it, open any model,
   right-click a class to confirm the selection submenu still works.
   The Prong A change only touched static types, no runtime behavior,
   so the smoke test is mainly for Prong B.
7. **Update PR notes**: mark Bug 2 RESOLVED, replace "Fix direction"
   with the actual fix description, refresh the before/after mermaid
   blocks (template from Bugs 5/6/7/8), and add a **⚠ Breaking API
   change — migration note** listing the 19 FQN changes (compressed
   into a sub-package mapping) plus the two new marker interfaces:
   ```
   org.tzi.use.gui.views.selection.**       →  org.tzi.use.gui.views.diagrams.selection.**
   org.tzi.use.gui.main.runtime.IMainWindow      (new — MainWindow implements)
   org.tzi.use.gui.main.runtime.IModelBrowser    (new — ModelBrowser implements)
   IPluginActionExtensionPoint.createPluginActions(Session, MainWindow)
       → IPluginActionExtensionPoint.createPluginActions(Session, IMainWindow)
   IPluginMMVisitor.modelBrowser(): ModelBrowser
       → IPluginMMVisitor.modelBrowser(): IModelBrowser
   IPluginMModelExtensionPoint.createMMPrintVisitor(PrintWriter, ModelBrowser)
       → IPluginMModelExtensionPoint.createMMPrintVisitor(PrintWriter, IModelBrowser)
   IPluginMModelExtensionPoint.createMMHTMLPrintVisitor(PrintWriter, ModelBrowser)
       → IPluginMModelExtensionPoint.createMMHTMLPrintVisitor(PrintWriter, IModelBrowser)
   ```
   Tag: `[breaking] gui.views, gui.main.runtime`.
8. **Commit**: `fix: bug 2 resolved` — single commit with code,
   module-info, archived before-fix reports, refreshed
   `target_archunit_temp/`, and PR notes.

## Alternative considered — and rejected

For Prong B we could instead introduce a `diagrams.api` package
exposing read-only interfaces (`IClassDiagram`,
`IDiagramViewWithObjectNode`, `IClassDiagramView`, `IObjectNodeActivity`,
…) and have selection depend only on those. That would keep
selection as a sibling of diagrams. **Reject**: it requires
~6 new interfaces, each duplicating the publicly-called surface of
a concrete diagram class; the next selection feature would add
another. The collapse-into-subpackage move is one `git mv`-pass with
no new types and matches the actual coupling (selection is a
sub-feature of diagrams, not a peer).
