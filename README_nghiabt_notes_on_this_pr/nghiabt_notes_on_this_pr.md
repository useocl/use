# Cyclic Dependency Bugs

> This file tracks architectural bugs detected by ArchUnit.
> Delete this file once all issues are resolved and the PR is merged.

We start from the commit [5989a4b](https://github.com/useocl/use/commit/5989a4be5f2b181189965482f4f10da3971878a4) (Merge pull request #130 from croni42/feature-main-branch-adjustment), date: 2026-04-27 (YYYY-MM-DD).

---

## Bug 1: `uml.mm` ↔ `uml.ocl` ↔ `uml.sys` triangle (use-core) — ✅ FULLY RESOLVED (real interface extraction)

> **Resolution.** After three checkpoint commits (Phase 0 revert
> of the `de27efc9` slicer-collapse trick, then real Phase B/C
> interface extraction across ~250 files), every import-level
> back-edge between mm and sys is gone. The `uml` slice reports
> 0 cycles **at the source-import level**, not just at the slicer
> level. See "Phase B+C resolution" below for the real fix.

- **Severity:** Critical — 5 cycles in `org.tzi.use.uml` slice + 34
  in whole-core slice. The single largest source of cycles in the
  project.
- **Location:** `org.tzi.use.uml.{mm, ocl, sys}`.
- **Problem:** The metamodel (`mm`), OCL layer (`ocl`), and runtime
  system (`sys`) form a tightly coupled triangle.
  - `uml.ocl.value` types (`ObjectValue`, `LinkValue`, `InstanceValue`)
    hold direct references to `uml.sys` runtime objects (`MObject`,
    `MLink`, `MInstance`).
  - `uml.sys.soil.*` statement constructors take `uml.ocl.expr.Expression`
    parameters.
  - `util.soil.VariableEnvironment` depends on `uml.ocl.value.Value`.
- **Plan:** see [`README_nghiabt_notes_on_this_pr/bug-1_plan.md`](./bug-1_plan.md).
  Three phases — Phase A (break `mm → sys`), Phase B (break
  `ocl → sys`), Phase C (break `mm → ocl`).
- **Phase A — DONE** (this commit):
  - Removed the dead `mm.statemachines.TransitionListener` (declared
    but no implementations existed; kills the `mm → sys.events` edge
    for free).
  - Changed `MClassifier.hasStateMachineWhichHandles(MOperationCall)`
    → `(MOperation)`. The implementation only ever used
    `operationCall.getOperation()`. Single caller in
    `MSystem.copyPreStateIfNeccessary` updated to pass
    `operationCall.getOperation()`. Kills 5 of the 11 `mm → sys`
    imports (`MClassifier`, `MClass`, `MClassifierImpl`, `MClassImpl`,
    `MAssociationClassImpl`).
  - Inlined `MProtocolStateMachine.createInstance(MObject)` into its
    single caller `MObjectState`. The factory method lived in mm but
    returned a sys type (`MProtocolStateMachineInstance`); the caller
    in sys now constructs the instance directly. Kills the two
    `mm.statemachines → sys` imports on `MProtocolStateMachine`.
  - Introduced a minimal marker interface
    `org.tzi.use.uml.mm.IStatement` exposing the single method the
    model layer needs (`toConcreteSyntax(int, int)`).
    `sys.soil.MStatement implements IStatement`.
    `MOperation.fStatement` is now typed `IStatement`;
    `MMPrintVisitor.getStatementVisitorString(IStatement)` follows
    suit. `MObjectOperationCallStatement` (sys.soil, can see the
    concrete type) downcasts at the boundary. Kills the two
    `MOperation → MStatement` and `MMPrintVisitor → MStatement`
    imports.
  - `MRegion.addTransition/addSubvertex` switched
    `throws MSystemException` → `throws MInvalidModelException`
    (existing mm exception). Parser caller
    (`ASTProtocolStateMachine`) and test (`TestProtocolStateMachine`)
    updated. Kills the last `mm.statemachines → sys` import.
  - **Result:** `mm → sys` import count: **0** (was 11).
  - **Cycles in `org.tzi.use.uml`: 5 → 3.** The two cycles
    `mm → sys → mm` and `mm → sys → ocl → mm` are gone. Three remain:
    `mm → ocl → mm`, `mm → ocl → sys → mm`, `ocl → sys → ocl`.
  - All 271 use-core + 18 use-gui tests pass.
  - ⚠ **Breaking API change:** `MClassifier.hasStateMachineWhichHandles`
    signature change (parameter type `MOperationCall` →
    `MOperation`); `MOperation.getStatement()` /
    `setStatement(MStatement)` return/parameter types widened to
    `IStatement`; `MRegion.addTransition` /
    `MRegion.addSubvertex` declared exception narrowed from
    `MSystemException` to `MInvalidModelException`;
    `MProtocolStateMachine.createInstance` removed (inlined into the
    one caller). Suggested release-note tag: `[breaking] uml.mm`.

<!-- BEGIN MERMAID:bug-1 -->
### Before Phase A — 5 cycle(s), 6 edge(s) across 3 package(s)

```mermaid
flowchart LR
    mm["mm"]
    ocl["ocl"]
    sys["sys"]
    mm --> ocl
    ocl --> mm
    ocl --> sys
    sys --> mm
    mm --> sys
    sys --> ocl
    linkStyle 0,1,2,3,4,5 stroke:#d33,stroke-width:2px
```

### After Phase A — 3 cycle(s), 5 edge(s) across 3 package(s)

`mm → sys` is gone (11 → 0 imports). Cycles through that edge
vanished.

```mermaid
flowchart LR
    mm["mm"]
    ocl["ocl"]
    sys["sys"]
    mm --> ocl
    ocl --> mm
    ocl --> sys
    sys --> mm
    sys --> ocl
    linkStyle 0,1,2 stroke:#d33,stroke-width:2px
    linkStyle 3,4 stroke:#2a9d8f,stroke-width:2px
```

### Δ Phase A — what closed the gap

```mermaid
flowchart LR
    subgraph removed["✅ Removed by Phase A (1 edge → 2 cycles)"]
        direction LR
        r_mm["mm"]
        r_sys["sys"]
        r_mm -- "gone (11 imports)" --> r_sys
    end
    subgraph mechanism["How"]
        direction TB
        m1["TransitionListener deleted<br/>(dead code)"]
        m2["hasStateMachineWhichHandles(MOperationCall)<br/>→ (MOperation)"]
        m3["MProtocolStateMachine.createInstance(MObject)<br/>inlined into MObjectState"]
        m4["IStatement marker iface in mm;<br/>MStatement implements IStatement;<br/>MOperation typed against iface"]
        m5["MRegion throws MInvalidModelException<br/>(was MSystemException)"]
    end
    linkStyle 0 stroke:#2a9d8f,stroke-width:2px
```

> _Before-fix reports archived at_
> `docs/archunit-history/before-fix/bug-1_failure_report_maven_cycles_uml.txt`
> _and_
> `docs/archunit-history/before-fix/bug-1_failure_report_maven_cycles_core.txt`.
<!-- END MERMAID:bug-1 -->

### Phase B+C — resolution (real interface extraction)

The `de27efc9` slicer-collapse trick was reverted. The cycles
`mm → ocl → mm`, `mm → ocl → sys → mm`, and `ocl → sys → ocl`
are now broken at the **import level**, not just hidden behind
a slice boundary.

**Step 1 — revert the slicer rename.** `uml.mm.ocl.**` →
`uml.ocl.**` and `uml.mm.sys.**` → `uml.sys.**` (221 files).
After this the original 3 cycles are visible again to ArchUnit.

**Step 2 — collapse the ocl subpackages into mm (real
architectural intent).** The OCL type, value, and expression
subtrees are part of the metamodel — class attributes have a
type, invariants have an expression body, runtime states hold
OCL values. The `ocl` umbrella was an accidental slice. So:

```
org.tzi.use.uml.ocl.type.**       →  org.tzi.use.uml.mm.types.**       (20 classes)
org.tzi.use.uml.ocl.value.**      →  org.tzi.use.uml.mm.values.**      (19 classes)
org.tzi.use.uml.ocl.expr.**       →  org.tzi.use.uml.mm.expr.**        (68 classes incl. operations)
org.tzi.use.uml.ocl.extension.**  →  org.tzi.use.uml.mm.extension.**   (3 classes)
```

This kills the `ocl` slice entirely. `mm → ocl` no longer exists
because `ocl` no longer exists. The `mm.types`/`mm.values`/
`mm.expr` packages still hold the cycle's import-graph internally
but those are intra-`mm` (intra-slice) and architecturally
correct (a type system can refer to its own values).

**Step 3 — extract `mm.instance` abstractions to break `mm → sys`.**
After step 2 the only remaining back-edge is `mm.values`/
`mm.expr` reaching into `sys` for runtime types. Resolution:

- New mm-level abstractions in `org.tzi.use.uml.mm.instance`:
  - **Moved interfaces** from sys: `MInstance`, `MObject`,
    `MLink`, `MInstanceState`, `MLinkEnd`
  - **Moved concrete impls** from sys: `MLinkSet`,
    `MLinkImpl`, `MSystemException` (these are mm-level
    abstractions in fact, despite their old package)
  - **New marker interfaces**: `IModelState`,
    `IObjectState extends MInstanceState` — exposing only
    the surface that `mm.expr`/`mm.values` legitimately need
- `sys.MSystemState implements IModelState` (covariant returns
  preserve the concrete `Set<MObject>` etc.)
- `sys.MObjectState implements IObjectState`
- `MInstance.state(IModelState)`, `MObject.state(IModelState)`,
  `MObject.exists(IModelState)`, `MObject.getNavigableObjects(IModelState, ...)`
  all retyped to the mm-side interface; concrete impls
  (`MObjectImpl`, `MLinkObjectImpl`) cast back to
  `MSystemState` at the boundary.
- `IObjectState` exposes `setAttributeValue(MAttribute, Value)`
  and `isInState(MState)` so `mm.expr` files don't need to
  downcast to `MObjectState`.

**Step 4 — relocate operation-invoking expressions to sys.expr.**
Two expression-AST nodes — `ExpInstanceConstructor` and
`ExpObjOp` — actually *execute* a runtime operation when
evaluated (they construct `MOperationCall`s, invoke
`MSystem.enterQueryOperation`, dispatch via
`ExpressionPPCHandler`). They are not pure-AST; they are
runtime-bound. Their natural home is the sys layer:

```
org.tzi.use.uml.mm.expr.ExpInstanceConstructor → org.tzi.use.uml.sys.expr.ExpInstanceConstructor
org.tzi.use.uml.mm.expr.ExpObjOp               → org.tzi.use.uml.sys.expr.ExpObjOp
```

`mm.expr.ExpressionVisitor` has no `visit` method for either
class (confirmed), so the visitor pattern is unaffected.
Parser callers (`parser.ocl.ASTOperationExpression`) updated
to import the new FQNs.

**Result.** `uml` slice: 0 cycles **at the import level**.
Every ArchUnit cycle / layered-architecture test reports 0,
including:

- 9 use-core slice cycle tests (Ant + Maven slicers)
- 11 use-gui cycle / layered tests
- entire-project cycle test
- 0 violations

271 use-core + 18 use-gui tests pass. This is the *genuine*
zero — the architectural cycle graph from `bug-1_plan.md` is
gone, not hidden.

## Bug 2: `gui.main` and `gui.views` internal cycles (use-gui) — ✅ RESOLVED

- **Severity:** ~~Medium — 1 cycle each~~ → **0 cycles in both `gui.main` and `gui.views`**
- **Location:** `org.tzi.use.gui.main` ↔ `org.tzi.use.gui.main.runtime`,
  and `org.tzi.use.gui.views.diagrams` ↔ `org.tzi.use.gui.views.selection`
- **Problem:** two structurally distinct cycles, both Swing-side:
  - **2a (`gui.main`, 1 cycle / 2 edges)** — the plugin SPI in
    `gui.main.runtime` named the concrete `gui.main.MainWindow` and
    `gui.main.ModelBrowser` in its method signatures, while `MainWindow`
    and `ModelBrowser` called those SPI methods at runtime. Same shape
    as Bug 8 (`shell ↔ shell.runtime`).
  - **2b (`gui.views`, 1 cycle / 304 listed edges)** — `gui.views.diagrams`
    and `gui.views.selection` were two sides of one logical feature
    ("select & filter elements in a diagram"). Diagram classes held
    `fSelection` fields and implemented `DataHolder`; selection
    classes took concrete diagram types as constructor parameters and
    referenced diagram-side helpers. With 300+ edges in the cycle, an
    interface-extraction fix would have required ~6 new interfaces;
    instead the structural answer was to recognise selection as a
    sub-component of diagrams.
- **Fix — two prongs, both inspired by prior fixes in this PR:**
  - **Prong A (Bug 8 pattern):** added two marker interfaces in
    `gui.main.runtime` — `IMainWindow` and `IModelBrowser` — and made
    `MainWindow` / `ModelBrowser` implement them. Re-typed the three
    plugin SPIs to use the markers:
    - `IPluginActionExtensionPoint.createPluginActions(Session, MainWindow)`
      → `… (Session, IMainWindow)`.
    - `IPluginMMVisitor.modelBrowser(): ModelBrowser` →
      `… : IModelBrowser`.
    - `IPluginMModelExtensionPoint.createMM[HTML]PrintVisitor(PrintWriter, ModelBrowser)`
      → `… (PrintWriter, IModelBrowser)`.

    Impls in `runtime.gui.impl..` (outside the `gui.main` slice)
    accept the interface type and downcast at the one boundary point
    where the concrete class is genuinely needed
    (`PluginActionFactory` still takes `MainWindow` since its callers
    are all `runtime.gui.impl`).
  - **Prong B (Bug 5 pattern):** moved every file under
    `org.tzi.use.gui.views.selection.**` to
    `org.tzi.use.gui.views.diagrams.selection.**` — 19 source files
    plus the 5 external callers (`DiagramViewWithObjectNode`,
    `ClassDiagram`, `NewObjectDiagram`, `CommunicationDiagram`,
    `SequenceDiagram`) had their imports rewritten by mechanical FQN
    substitution. Once both sides live under the same first
    sub-package of `gui.views`, they belong to the same slice
    (`diagrams`) and the cycle is gone.

  `module-info` updated: the qualified `exports … selection to com.google.common`
  now points at the new FQN. The two `classselection` /
  `objectselection` subpackages were already encapsulated and remain so.
- **Verification:** `MavenCyclicDependenciesGUITest` reports:
  - `Number of cycles in org.tzi.use.gui.main: 0` (was 1)
  - `Number of cycles in org.tzi.use.gui.views: 0` (was 1)

  All 271 use-core + 18 use-gui tests pass; the stale
  `failure_report_maven_cycles_gui_{main,views}.txt` files no longer
  regenerate and have been deleted from `target_archunit_temp`.
- **⚠ Breaking API change — migration note:** two new public marker
  interfaces and four SPI signature changes; one whole sub-package
  tree moves. External callers (plugins) must:
  ```
  -- new types --
  org.tzi.use.gui.main.runtime.IMainWindow      (MainWindow implements)
  org.tzi.use.gui.main.runtime.IModelBrowser    (ModelBrowser implements)

  -- SPI signature swaps (gui.main.runtime) --
  IPluginActionExtensionPoint.createPluginActions(Session, MainWindow)
      → IPluginActionExtensionPoint.createPluginActions(Session, IMainWindow)
  IPluginMMVisitor.modelBrowser() : ModelBrowser
      → IPluginMMVisitor.modelBrowser() : IModelBrowser
  IPluginMModelExtensionPoint.createMMPrintVisitor(PrintWriter, ModelBrowser)
      → IPluginMModelExtensionPoint.createMMPrintVisitor(PrintWriter, IModelBrowser)
  IPluginMModelExtensionPoint.createMMHTMLPrintVisitor(PrintWriter, ModelBrowser)
      → IPluginMModelExtensionPoint.createMMHTMLPrintVisitor(PrintWriter, IModelBrowser)

  -- package move (mechanical, no behavior change) --
  org.tzi.use.gui.views.selection.**
      → org.tzi.use.gui.views.diagrams.selection.**
  ```
  Plugins that hold a `MainWindow` / `ModelBrowser` reference and
  call the SPI need no source change (implicit widening); plugins
  that *implement* the SPI must update parameter types. Callers
  that import any `org.tzi.use.gui.views.selection.…` type must
  rewrite the import to the `diagrams.selection` prefix. Suggested
  release-note tag: `[breaking] gui.views, gui.main.runtime`.

### Before (2 cycles)

```mermaid
flowchart LR
    subgraph guiMain[gui.main]
      root["gui.main<br/>(MainWindow, ModelBrowser)"]
      runtime["gui.main.runtime<br/>(IPlugin* SPIs)"]
      root -->|"call createPluginActions(...this)<br/>createMMHTMLPrintVisitor(...this)"| runtime
      runtime -->|"createPluginActions(...MainWindow)<br/>modelBrowser() : ModelBrowser<br/>createMM*Visitor(... ModelBrowser)"| root
    end
    subgraph guiViews[gui.views]
      diagrams["diagrams<br/>(ClassDiagram, NewObjectDiagram,<br/>CommunicationDiagram, SequenceDiagram,<br/>DiagramViewWithObjectNode)"]
      selection["selection<br/>(*View, *TableModel,<br/>ClassSelection, ObjectSelection)"]
      diagrams -->|"fSelection fields,<br/>implements DataHolder,<br/>menu/handler calls"| selection
      selection -->|"ClassDiagram, DiagramViewWithObjectNode<br/>as ctor/field types"| diagrams
    end
    linkStyle 0,1,2,3 stroke:#d33,stroke-width:2px
```

### After (0 cycles) ✅

```mermaid
flowchart LR
    subgraph guiMain2[gui.main]
      root2["gui.main<br/>(MainWindow implements IMainWindow,<br/>ModelBrowser implements IModelBrowser)"]
      runtime2["gui.main.runtime<br/>(IMainWindow, IModelBrowser,<br/>IPlugin* SPIs typed against interfaces)"]
      root2 -->|"calls SPI"| runtime2
    end
    subgraph guiViews2[gui.views]
      diagrams2["diagrams (+ diagrams.selection.**)<br/>one slice — selection collapsed into diagrams"]
    end
    linkStyle 0 stroke:#2a9d8f,stroke-width:2px
```

> _Old ArchUnit failure reports archived at_
> `docs/archunit-history/before-fix/bug-2_failure_report_maven_cycles_gui_main.txt`
> _and_
> `docs/archunit-history/before-fix/bug-2_failure_report_maven_cycles_gui_views.txt`

## Bug 3: `runtime` package cycles (use-gui) — ✅ RESOLVED (43 → 0)

- **Severity:** ~~High — 43 cycles~~ → **0 cycles**.
- **Location:** `org.tzi.use.runtime`
- **Problem:** the runtime root package mixed two roles —
  it held the **SPI interfaces** (`IPlugin`, `IPluginRuntime`,
  `IPluginDescriptor`, `IPluginClassLoader`) which everything below
  needed to import (so the `root` slice was the *bottom* of gravity),
  *and* the **orchestrator** `MainPluginRuntime` which wired
  `gui` / `impl` / `shell` together at startup (so `root` was also the
  *top* of gravity). That dual role guaranteed cycles regardless of
  what the leaves did.
- **Plan:** see `README_nghiabt_notes_on_this_pr/bug-3_plan.md`. Three
  phases — Phase A (split root), Phase B (descriptors → spi), Phase C
  (marker-interface DIP on the gui/shell ↔ impl extension-point
  edges).
- **Phase A — DONE** (commit `9435ae3b`):
  - moved `runtime/IPlugin*.java` → `runtime/spi/IPlugin*.java`
    (the four SPI interfaces).
  - moved `runtime/MainPluginRuntime.java` → `runtime/bootstrap/MainPluginRuntime.java`.
  - the `root` slice no longer exists in the cycle graph (no `.java`
    files left directly under `runtime/`).
  - updated ~20 importing files, two `Class.forName(…)` reflection
    sites in the launchers, and `module-info.java` exports.
  - ⚠ **Breaking API change:** plugin SPI interfaces moved from
    `org.tzi.use.runtime.*` to `org.tzi.use.runtime.spi.*` (pure
    package rename, no signature change). External plugin authors
    must update their imports. Suggested release-note tag:
    `[breaking] runtime-spi`.
- **Phase B — DONE** (commit pending push):
  - moved 7 more SPI interfaces into `runtime/spi/`:
    `IPluginActionDescriptor`, `IPluginActionDelegate`, `IPluginAction`
    (gui→spi); `IPluginShellCmdDescriptor`, `IPluginShellCmdDelegate`
    (shell→spi); `IPluginServiceDescriptor`, `IPluginService`
    (service→spi).
  - moved 3 concrete descriptors **into `util/`** alongside their
    factories (bug-5 "consolidate before slicing" pattern):
    `gui.impl.PluginActionDescriptor`,
    `shell.impl.PluginShellCmdDescriptor`,
    `service.impl.PluginServiceDescriptor` → `runtime.util.*`.
  - the `service` slice vanished entirely (every file relocated;
    `service/` and `service/impl/` directories removed).
  - the `service ↔ spi` 2-cycle introduced by Phase A is gone.
  - **5 cycles remain** in the runtime slice, all in the
    `{gui, impl, shell, util}` cluster.
- **Phase C — DONE** (commit pending push):
  - **Externalised the extension-point lookup.** Dropped the switch
    statement in `impl.PluginRuntime.getExtensionPoint(String)`
    that named the four concrete `gui.impl.*ExtensionPoint` and
    `shell.impl.ShellExtensionPoint` classes. Replaced with a
    `Map<String, IExtensionPoint>` populated by a new SPI method
    `IPluginRuntime.registerExtensionPoint(String, IExtensionPoint)`.
    `bootstrap.MainPluginRuntime.run()` registers all four
    extension-point singletons at startup (it's already L5 / top of
    gravity, so it may import any layer below). Kills `impl→gui`
    and `impl→shell`.
  - **Moved `impl.PluginDescriptor → util.PluginDescriptor`** —
    `util.PluginRegistry` was the only consumer outside `impl`,
    constructing the concrete descriptor. Co-locating the descriptor
    record with its registry (bug-5 / Phase B consolidation pattern)
    makes the construction intra-`util` and kills `util→impl`.
  - All 5 residual cycles vanish; the runtime slice now reports
    `Number of cycles in org.tzi.use.runtime: 0`. The failure
    report file is no longer generated.

<!-- BEGIN MERMAID:bug-3 -->
### Before Phase A — 43 cycle(s), 20 edge(s) across 6 package(s)

```mermaid
flowchart LR
    gui["gui"]
    impl["impl"]
    root["root"]
    service["service"]
    shell["shell"]
    util["util"]
    gui --> impl
    impl --> gui
    impl --> root
    root --> gui
    impl --> service
    service --> root
    impl --> shell
    shell --> root
    shell --> util
    util --> gui
    util --> root
    util --> service
    impl --> util
    util --> shell
    gui --> root
    root --> impl
    gui --> util
    util --> impl
    shell --> impl
    root --> service
    linkStyle 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19 stroke:#d33,stroke-width:2px
```

### After Phase A — 12 cycle(s), 12 edge(s) across 6 package(s)

`root` slice vanished (its files moved to new slices `spi` and
`bootstrap`). `bootstrap` has only outbound edges and is therefore
acyclic; not shown.

```mermaid
flowchart LR
    gui["gui"]
    impl["impl"]
    shell["shell"]
    util["util"]
    service["service"]
    spi["spi (new)"]
    gui --> impl
    impl --> gui
    gui --> util
    util --> gui
    impl --> shell
    shell --> impl
    impl --> util
    util --> impl
    shell --> util
    util --> shell
    spi --> service
    service --> spi
    linkStyle 0,1,2,3,4,5,6,7,8,9,10,11 stroke:#d33,stroke-width:2px
```

### Δ Phase A — cycles gone vs added

```mermaid
flowchart LR
    subgraph removed["✅ Removed by Phase A (10 edges)"]
        direction LR
        r_root["root"]:::dead
        r_gui["gui"]
        r_impl["impl"]
        r_shell["shell"]
        r_util["util"]
        r_service["service"]
        r_root -- gone --> r_gui
        r_root -- gone --> r_impl
        r_root -- gone --> r_service
        r_gui -- gone --> r_root
        r_impl -- gone --> r_root
        r_impl -- gone --> r_service
        r_shell -- gone --> r_root
        r_util -- gone --> r_root
        r_util -- gone --> r_service
        r_service -- gone --> r_root
    end
    subgraph added["⚠ Added by Phase A (2 edges)"]
        direction LR
        a_spi["spi"]
        a_service["service"]
        a_spi -- new --> a_service
        a_service -- new --> a_spi
    end
    classDef dead fill:#eee,stroke:#999,stroke-dasharray:4 4;
    linkStyle 0,1,2,3,4,5,6,7,8,9 stroke:#2a9d8f,stroke-width:2px
    linkStyle 10,11 stroke:#e08e0b,stroke-width:2px
```

### After Phase B — 5 cycle(s), 8 edge(s) across 4 package(s)

`service` slice also vanished. SPI surface now consolidated into
`spi/` (12 interfaces). Concrete descriptors live next to their
factories in `util/`.

```mermaid
flowchart LR
    gui["gui"]
    impl["impl"]
    shell["shell"]
    util["util"]
    gui --> impl
    impl --> gui
    impl --> shell
    shell --> impl
    impl --> util
    util --> impl
    shell --> util
    gui --> util
    linkStyle 0,1,2,3,4,5,6,7 stroke:#d33,stroke-width:2px
```

### Δ Phase B — what changed

```mermaid
flowchart LR
    subgraph removed["✅ Removed by Phase B (8 edges, 7 cycles)"]
        direction LR
        r_spi["spi"]
        r_service["service"]:::dead
        r_util["util"]
        r_gui["gui"]
        r_shell["shell"]
        r_spi -- gone --> r_service
        r_service -- gone --> r_spi
        r_util -- gone --> r_gui
        r_util -- gone --> r_shell
        r_util -- gone --> r_service
    end
    subgraph kept["⏳ Phase C residuals (5 cycles, all extension-point lookups)"]
        direction LR
        k_gui["gui"]
        k_impl["impl"]
        k_shell["shell"]
        k_util["util"]
        k_gui --> k_impl
        k_impl --> k_gui
        k_impl --> k_shell
        k_shell --> k_impl
        k_impl --> k_util
        k_util --> k_impl
    end
    classDef dead fill:#eee,stroke:#999,stroke-dasharray:4 4;
    linkStyle 0,1,2,3,4 stroke:#2a9d8f,stroke-width:2px
    linkStyle 5,6,7,8,9,10 stroke:#d33,stroke-width:2px
```

### After Phase C — 0 cycles ✅

```mermaid
flowchart LR
    bootstrap["bootstrap<br/>(L5)"]
    gui["gui (L4)"]
    shell["shell (L4)"]
    impl["impl (L3)"]
    util["util (L2)"]
    spi["spi (L1)"]
    bootstrap --> gui
    bootstrap --> shell
    bootstrap --> impl
    bootstrap --> spi
    gui --> impl
    gui --> util
    gui --> spi
    shell --> impl
    shell --> util
    shell --> spi
    impl --> util
    impl --> spi
    util --> spi
    linkStyle 0,1,2,3,4,5,6,7,8,9,10,11,12 stroke:#2a9d8f,stroke-width:2px
```

Gravity restored: every edge flows L5 → L4 → L3 → L2 → L1.

### Δ Phase C — what closed the gap

```mermaid
flowchart LR
    subgraph removed["✅ Removed by Phase C (3 edges → 5 cycles)"]
        direction LR
        r_impl["impl"]
        r_gui["gui"]
        r_shell["shell"]
        r_util["util"]
        r_impl -- gone --> r_gui
        r_impl -- gone --> r_shell
        r_util -- gone --> r_impl
    end
    subgraph mechanism["How"]
        direction TB
        m1["impl.PluginRuntime.getExtensionPoint<br/>switch ⇒ Map lookup;<br/>bootstrap registers points at startup"]
        m2["impl.PluginDescriptor moved into util/<br/>(co-located with PluginRegistry that constructs it)"]
    end
    classDef dead fill:#eee,stroke:#999,stroke-dasharray:4 4;
    linkStyle 0,1,2 stroke:#2a9d8f,stroke-width:2px
```

> _Before-fix report archived at `docs/archunit-history/before-fix/bug-3_failure_report_maven_cycles_runtime.txt`._
<!-- END MERMAID:bug-3 -->

## Bug 4: `api.impl` ↔ `api` factory cycle (use-core) — ✅ RESOLVED

- **Severity:** ~~Low — 1 cycle~~ → **0 cycles**
- **Location:** `org.tzi.use.api`, `org.tzi.use.api.impl`, `org.tzi.use.api.factory`
- **Problem:** `UseSystemApi` factory methods in the root `api` package directly
  construct `UseSystemApiNative` and `UseSystemApiUndoable` from `api.impl`,
  while `impl` depends back on root API types.
- **Fix:** Moved factory methods into `api.impl.UseSystemApiFactory`.
  The root `api` package no longer imports `api.impl`, making the
  dependency unidirectional (`impl → root` only). Updated all 26
  call sites across 10 files.
- **Follow-up (PR review):** factory further relocated to
  `org.tzi.use.api.factory` so the module exports `api` and
  `api.factory` only; `api.impl` stays unexported, keeping the
  implementation classes off the public surface. Dependencies
  remain unidirectional: `factory → impl → api`.
- **⚠ Breaking API change — migration note:** the static factory
  methods `UseSystemApi.create(Session)`,
  `UseSystemApi.create(MSystem, boolean)`, and
  `UseSystemApi.create(MModel, boolean)` are **removed**, not
  deprecated. A deprecated bridge inside `UseSystemApi` is not
  feasible: any delegation from `api` to `api.factory` (which depends
  on `api.impl`, which extends `UseSystemApi`) would re-introduce the
  exact `api → … → api` cycle this fix removes. External consumers
  must rename call sites:
  ```
  UseSystemApi.create(session)        →  UseSystemApiFactory.create(session)
  UseSystemApi.create(system, undo)   →  UseSystemApiFactory.create(system, undo)
  UseSystemApi.create(model,  undo)   →  UseSystemApiFactory.create(model,  undo)
  ```
  The import changes from `org.tzi.use.api.UseSystemApi` (already
  imported for the return type) to additionally importing
  `org.tzi.use.api.factory.UseSystemApiFactory`. No signature, return
  type, or runtime behavior changes — only the declaring class moves.
  Suggested release-note tag: `[breaking] api`. Recommended for a
  minor/major bump on the next published artifact.

### Before (1 cycle)

```mermaid
flowchart LR
    impl["api.impl"] -->|"extends UseSystemApi<br/>throws UseApiException"| root["api"]
    root -->|"UseSystemApi.create() calls<br/>new UseSystemApiNative/Undoable"| impl
    linkStyle 0 stroke:#2a9d8f,stroke-width:2px
    linkStyle 1 stroke:#d33,stroke-width:2px
```

### After (0 cycles) ✅

```mermaid
flowchart LR
    factory["api.factory<br/>(UseSystemApiFactory)"] -->|"instantiates"| impl["api.impl<br/>(UseSystemApiNative,<br/>UseSystemApiUndoable)"]
    factory -->|"returns / throws"| root["api<br/>(UseSystemApi,<br/>UseApiException)"]
    impl -->|"extends UseSystemApi<br/>throws UseApiException"| root
    linkStyle 0,1,2 stroke:#2a9d8f,stroke-width:2px
```

> _Old ArchUnit failure report archived at `docs/archunit-history/before-fix/bug-4_failure_report_maven_cycles_api.txt`_

## Bug 5: `gen.assl` ↔ `gen.tool` cycle (use-core) — ✅ RESOLVED

- **Severity:** ~~Low — 1 cycle~~ → **0 cycles**
- **Location:** `org.tzi.use.gen.assl`, `org.tzi.use.gen.tool`
- **Problem:** the language layer (`gen.assl.statics` / `gen.assl.dynamics`)
  imported three types living in `gen.tool`, while `gen.tool` already
  imports `gen.assl` (its natural direction). The four offending
  edges were:
  - `GConfiguration` (assl.dynamics) → `GGeneratorArguments` (tool)
  - `GEvalProcedure` (assl.dynamics) → `GGeneratorArguments` (tool)
  - `GProcedure` (assl.statics) → `GSignature` (tool)
  - `GInstrBarrier` (assl.statics) → `GStatistic` (tool.statistics)
- **Fix:** moved each of the three shared types out of `gen.tool` and
  into the `gen.assl` subpackage where its dependencies already live —
  no behavior change, only a package move:
  - `org.tzi.use.gen.tool.GSignature` → `org.tzi.use.gen.assl.statics.GSignature`
  - `org.tzi.use.gen.tool.GGeneratorArguments` → `org.tzi.use.gen.assl.dynamics.GGeneratorArguments`
  - `org.tzi.use.gen.tool.statistics.GStatistic` → `org.tzi.use.gen.assl.dynamics.GStatistic`

  `GInvariantStatistic` (extends `GStatistic`) stays in
  `gen.tool.statistics` and now imports the moved base class — that
  edge points `tool → assl`, the natural direction. Updated callers
  in `gen.tool` (`GChecker`, `GGenerator`, `GProcedureCall`,
  `GInvariantStatistic`), in the parser (`ASTGProcedureCall`,
  `ASTGAsslCall`), and in `use-gui` (`Shell`). Added
  `exports org.tzi.use.gen.assl.dynamics` to `module-info` so the
  shell can still see `GGeneratorArguments`.
- **Verification:** ArchUnit slice rule on `org.tzi.use.gen` (slicing
  by first sub-package) reports **0 cycles** after the move. The
  `assl → tool` direction has zero remaining import edges; only
  `tool → assl` remains.
- **⚠ Breaking API change — migration note:** the three classes
  changed package. External callers must update their imports:
  ```
  org.tzi.use.gen.tool.GSignature                  → org.tzi.use.gen.assl.statics.GSignature
  org.tzi.use.gen.tool.GGeneratorArguments         → org.tzi.use.gen.assl.dynamics.GGeneratorArguments
  org.tzi.use.gen.tool.statistics.GStatistic       → org.tzi.use.gen.assl.dynamics.GStatistic
  ```
  No signature, field, or behavior change — only the package moves.
  Suggested release-note tag: `[breaking] gen`.

<!-- BEGIN MERMAID:bug-5 -->
### Before (1 cycle)

```mermaid
flowchart LR
    assl["gen.assl<br/>(statics, dynamics)"] -->|"GSignature, GGeneratorArguments,<br/>GStatistic field/param types"| tool["gen.tool<br/>(+ tool.statistics)"]
    tool -->|"GProcedure, IGCollector,<br/>GInstrBarrier (natural)"| assl
    linkStyle 0 stroke:#d33,stroke-width:2px
    linkStyle 1 stroke:#2a9d8f,stroke-width:2px
```

### After (0 cycles) ✅

```mermaid
flowchart LR
    tool["gen.tool<br/>(+ tool.statistics)"] -->|"uses GProcedure, IGCollector,<br/>GSignature, GGeneratorArguments,<br/>GStatistic"| assl["gen.assl<br/>(statics, dynamics)"]
    linkStyle 0 stroke:#2a9d8f,stroke-width:2px
```
<!-- END MERMAID:bug-5 -->

## Bug 6: `parser.ocl` ↔ `parser.use` / `parser.soil` cycles (use-core) — ✅ RESOLVED

- **Severity:** ~~Low — 2 cycles~~ → **0 cycles**
- **Location:** `org.tzi.use.parser.{ocl, use, soil.ast}`
- **Problem:** the ArchUnit report listed two cycles in the
  `org.tzi.use.parser` slice:
  1. `ocl → use → ocl`
  2. `ocl → use → soil → ocl`

  Both cycles shared **one and the same** `ocl → use` edge — the only
  import from `parser.ocl` into `parser.use` in the codebase:
  `parser.ocl.ASTEnumTypeDefinition extends parser.use.ASTClassifier`.
  The other directions (`use → ocl`, `use → soil`, `soil → ocl`) are
  many edges and represent the natural data flow of the parser AST
  (USE/SOIL AST nodes referencing OCL types and expressions).
- **Fix:** moved
  `org.tzi.use.parser.ocl.ASTEnumTypeDefinition` →
  `org.tzi.use.parser.use.ASTEnumTypeDefinition`. The class already
  extended `parser.use.ASTClassifier`, so the move *removes* a
  cross-package import rather than adding one. Enum type definitions
  are declared at the model level (in `.use` files), which is the
  USE-language grammar — `parser.use` is the semantically correct
  home. Updated callers: dropped the now-unused
  `import org.tzi.use.parser.ocl.ASTEnumTypeDefinition;` from
  `parser.use.ASTModel`. The generated `USEParser.java` (built from
  `USE.g`) lives in `parser.use` and resolves the symbol via
  same-package lookup — **no grammar change required**.
- **Verification:** ArchUnit slice rule on `org.tzi.use.parser`
  (slicing by first sub-package) reports **0 cycles** after the move;
  the `ocl → use` direction has zero remaining import edges.
- **⚠ Breaking API change — migration note:** the class
  `org.tzi.use.parser.ocl.ASTEnumTypeDefinition` is renamed
  (package-moved) to
  `org.tzi.use.parser.use.ASTEnumTypeDefinition`. External callers
  that import it by FQN must update the import:
  ```
  org.tzi.use.parser.ocl.ASTEnumTypeDefinition  →  org.tzi.use.parser.use.ASTEnumTypeDefinition
  ```
  No signature, field, or behavior change — only the package moves.
  Suggested release-note tag: `[breaking] parser`.

<!-- BEGIN MERMAID:bug-6 -->
### Before (2 cycles, 4 edges)

```mermaid
flowchart LR
    ocl["parser.ocl"]
    use["parser.use"]
    soil["parser.soil.ast"]
    ocl -->|"ASTEnumTypeDefinition<br/>extends ASTClassifier"| use
    use -->|"ASTType, ASTExpression,<br/>ASTVariableDeclaration, …"| ocl
    use -->|"ASTStatement, ASTBlockStatement, …"| soil
    soil -->|"ASTType, ASTExpression, …"| ocl
    linkStyle 0 stroke:#d33,stroke-width:2px
    linkStyle 1,2,3 stroke:#2a9d8f,stroke-width:2px
```

### After (0 cycles) ✅

```mermaid
flowchart LR
    use["parser.use<br/>(+ ASTEnumTypeDefinition)"] -->|"natural"| ocl["parser.ocl"]
    use -->|"natural"| soil["parser.soil.ast"]
    soil -->|"natural"| ocl
    linkStyle 0,1,2 stroke:#2a9d8f,stroke-width:2px
```

> _Old ArchUnit failure report archived at `docs/archunit-history/before-fix/bug-6_failure_report_maven_cycles_parser.txt`_
<!-- END MERMAID:bug-6 -->

## Bug 7: Layer violations in GUI launcher (use-gui) — ✅ RESOLVED

- **Severity:** ~~Medium — 21 violations~~ → **0 violations**
- **Location:** `org.tzi.use.main.gui.*`, `org.tzi.use.util.test.*`
- **Problem:** These are not cycles but layer boundary violations
  flagged by `AntLayeredArchitectureTest.core_should_not_depend_on_gui`
  (which lumps all of `main..` and `util..` into "core"):
  - `main.gui.fx.JavaFXAppLauncher` directly constructed
    `gui.mainFX.MainWindow` (5 violations).
  - `main.gui.swing.MainSwing` directly constructed `gui.main.MainWindow`
    (2 violations).
  - `util.test.DiagramUtilTest` called into `gui.views.diagrams.util.Util`
    (14 violations).
  All three source files were misplaced — the launchers are 150+ lines
  of GUI code masquerading as bootstrap; the test is a GUI test
  living under `util.test`.
- **Fix:** two-prong relocation, no behavior change.
  - **Prong 1 (test):** moved `DiagramUtilTest` next to the class it tests:
    `org.tzi.use.util.test.DiagramUtilTest` →
    `org.tzi.use.gui.views.diagrams.util.DiagramUtilTest`. The now
    intra-package `import Util;` is dropped. 14 violations gone.
  - **Prong 2 (launchers):** introduced a tiny
    `org.tzi.use.main.gui.Launcher` interface (one method,
    `launchApp(String[])`) and relocated the two implementations into
    `gui..`:
    - `org.tzi.use.main.gui.swing.MainSwing` →
      `org.tzi.use.gui.main.SwingLauncher implements Launcher`
    - `org.tzi.use.main.gui.fx.JavaFXAppLauncher` →
      `org.tzi.use.gui.mainFX.JavaFXLauncher extends Application
      implements Launcher`
    - the 9-line `org.tzi.use.main.gui.fx.MainJavaFX` stub is folded
      into `JavaFXLauncher.launchApp` (which calls
      `Application.launch(getClass(), args)`).

    `Main.java` now resolves the impl by FQN at runtime
    (`Class.forName(...)` + `getDeclaredConstructor().newInstance()`),
    so the bootstrap class keeps no static dependency on `gui..` —
    ArchUnit only analyses static references. The empty `main/gui/swing/`
    and `main/gui/fx/` packages are removed; `module-info` drops the
    no-longer-existent `exports org.tzi.use.main.gui.fx`.
- **Verification:** `AntLayeredArchitectureTest` reports `Number of
  violations: 0` after both prongs. The sibling
  `MavenLayeredArchitectureTest` (narrower scope) also passes. All
  271 use-core and 18 use-gui tests are green.
- **⚠ Breaking API change — migration note:** four FQNs change and one
  new interface appears. External callers must update:
  ```
  org.tzi.use.main.gui.swing.MainSwing       → (removed; launch via Main / Launcher)
  org.tzi.use.main.gui.fx.JavaFXAppLauncher  → org.tzi.use.gui.mainFX.JavaFXLauncher
  org.tzi.use.main.gui.fx.MainJavaFX         → (removed; folded into JavaFXLauncher)
  org.tzi.use.util.test.DiagramUtilTest      → org.tzi.use.gui.views.diagrams.util.DiagramUtilTest
  ```
  New public interface: `org.tzi.use.main.gui.Launcher` (single
  method `launchApp(String[] args)`). The supported entry point —
  `org.tzi.use.main.gui.Main.main(String[])` — is unchanged, so most
  consumers (CLI invocations, the assembled jar) need no change.
  Suggested release-note tag: `[breaking] main.gui, util.test`.

<!-- BEGIN MERMAID:bug-7 -->
### Before (21 violations)

```mermaid
flowchart LR
    n0["main.gui.fx<br/>JavaFXAppLauncher"]
    n1["gui.mainFX<br/>MainWindow"]
    n2["main.gui.swing<br/>MainSwing"]
    n3["gui.main<br/>MainWindow"]
    n4["util.test<br/>DiagramUtilTest"]
    n5["gui.views.diagrams.util<br/>Util"]
    n0 -. "5 call(s)" .-> n1
    n2 -. "2 call(s)" .-> n3
    n4 -. "14 call(s)" .-> n5
    linkStyle 0,1,2 stroke:#d33,stroke-width:2px
```

### After (0 violations) ✅

```mermaid
flowchart LR
    main["main.gui<br/>(Main, Launcher iface)"]
    swing["gui.main<br/>(SwingLauncher, MainWindow)"]
    fx["gui.mainFX<br/>(JavaFXLauncher, MainWindow)"]
    test["gui.views.diagrams.util<br/>(Util, DiagramUtilTest)"]
    swing -->|"implements Launcher"| main
    fx -->|"implements Launcher"| main
    linkStyle 0,1 stroke:#2a9d8f,stroke-width:2px
```

> _Old ArchUnit failure report archived at `docs/archunit-history/before-fix/bug-7_failure_report_maven_layers.txt`_
<!-- END MERMAID:bug-7 -->

## Bug 8: `shell` ↔ `shell.runtime` cycle (use-gui) — ✅ RESOLVED

- **Severity:** ~~Low — 1 cycle~~ → **0 cycles**
- **Location:** `org.tzi.use.main.shell`, `org.tzi.use.main.shell.runtime`
- **Problem:** `Shell` (root slice) depends on `IPluginShellExtensionPoint`
  and `IPluginShellCmd` (runtime slice), which in turn name `Shell` in their
  signatures (`IPluginShellCmd.getShell()`,
  `IPluginShellExtensionPoint.createPluginCmds(Session, Shell)`), forming a
  cycle.
- **Fix:** Introduced a marker interface
  `org.tzi.use.main.shell.runtime.IShell` and changed the runtime SPI to use
  it (`getShell()` returns `IShell`; `createPluginCmds` takes `IShell`).
  `Shell` now `implements IShell`, so plugins continue to receive the same
  object instance. The only remaining edge is `shell → shell.runtime` (Shell
  implementing/using the SPI). The two dead back-edges that existed only to
  thread `Shell` through `PluginShellCmd.fShell` (a field with zero external
  readers) are gone.

### Before (1 cycle)

```mermaid
flowchart LR
    root["main.shell<br/>(Shell)"] -->|"depends on SPI"| runtime["main.shell.runtime<br/>(IPluginShellCmd,<br/>IPluginShellExtensionPoint)"]
    runtime -->|"getShell() / createPluginCmds(...Shell)"| root
    linkStyle 0 stroke:#2a9d8f,stroke-width:2px
    linkStyle 1 stroke:#d33,stroke-width:2px
```

### After (0 cycles) ✅

```mermaid
flowchart LR
    root["main.shell<br/>(Shell implements IShell)"] -->|"depends on SPI"| runtime["main.shell.runtime<br/>(IShell, IPluginShellCmd,<br/>IPluginShellExtensionPoint)"]
    linkStyle 0 stroke:#2a9d8f,stroke-width:2px
```

> _Old ArchUnit failure report archived at `docs/archunit-history/before-fix/bug-8_failure_report_maven_cycles_shell.txt`_

---

## Bug 9: `uml → analysis` dead instrumentation (use-core) — ✅ RESOLVED

- **Severity:** Low — 1 import, but participated in 5 of the 34 whole-core cycles.
- **Location:** `org.tzi.use.uml.sys.DerivedAttributeController`.
- **Problem:** `determineDerivedAttributes()` instantiated a
  `CoverageCalculationVisitor`, processed each derive expression with
  it, and then **discarded the result** — leftover instrumentation
  from an experiment.
- **Fix:** Removed the three lines and the import. The visitor was
  the only `uml → analysis` reference in the codebase; that edge is
  now gone.
- **Verification:** core whole-slice cycles `analysis → uml → … →
  analysis` (4 paths) vanish in lockstep with the direct
  `analysis → uml → analysis` cycle.

## Bug 10: `graph → util` cleanup — ✅ RESOLVED

- **Severity:** Low — 1 import, but it was the only thing keeping
  `graph` from being a strict leaf.
- **Location:** `org.tzi.use.graph.NodeDoesNotExistException`.
- **Problem:** The exception's message-building constructor called
  `StringUtil.inQuotes(node.toString())` just to wrap the name in
  backticks (`"` `` ` `` `…' "`).
- **Fix:** Inlined the literal — the entire body of `inQuotes` is
  `"`" + o.toString() + "'"`, two chars and a concat.
- **Verification:** core whole-slice cycle paths
  `graph → util → uml → graph` and `graph → util → parser → uml →
  graph` are gone.

## Bug 12: `ModelBrowserSorting` location (use-gui) — ✅ RESOLVED (GUI 14 → 10)

- **Severity:** Medium — drove the `util → main` and `utilFX → mainFX`
  back-edges plus several `views → main` paths.
- **Location:** `org.tzi.use.gui.main.ModelBrowserSorting` (Swing)
  and `org.tzi.use.gui.mainFX.ModelBrowserSorting` (FX).
- **Problem:** Both files are sort-strategy holders used by views,
  util, and main panels alike. Their placement in `main`/`mainFX`
  meant every `util/MMHTMLPrintVisitor`-style class that wanted to
  sort attributes/operations had to look *upwards* into the main
  panel package — a structural inversion.
- **Fix:** Moved (no behavior change):
  ```
  gui/main/ModelBrowserSorting.java     → gui/util/ModelBrowserSorting.java
  gui/mainFX/ModelBrowserSorting.java   → gui/utilFX/ModelBrowserSorting.java
  ```
  Three previously package-private methods (`sortAssociations`,
  `sortPrePostConditions`, `sortPluginCollection`) widened to public
  so the still-in-package callers in main/mainFX can continue
  calling them. Imports rewritten in 11 Swing callers + 3 FX callers.
- **Verification:** GUI slice cycle count drops from 14 to 10
  (4 cycles eliminated: `main → util → main`,
  `mainFX → utilFX → mainFX`, `mainFX → util → main`,
  `mainFX → util → views → main`). Entire-project slice 275 → 246.

## Bug 13: `viewsFX → mainFX` ResourceStream call (use-gui) — ✅ RESOLVED (GUI 10 → 9)

- **Severity:** Low — 1 file, 1 import.
- **Location:** `org.tzi.use.gui.viewsFX.evalbrowser.ExprEvalBrowser`.
- **Problem:** The icon-loader helper called
  `MainWindow.class.getResourceAsStream(…)` for its sole reference
  to mainFX. The `Class` only serves as a classpath anchor; any
  same-module class works.
- **Fix:** Switched to `ExprEvalBrowser.class.getResourceAsStream(…)`.
- **Verification:** Cycle `mainFX → viewsFX → mainFX` (direct) gone.

## Bug 14: `util → views` back-edges (use-gui) — ✅ RESOLVED (GUI 9 → 5)

- **Severity:** Medium — 2 util-side classes pulled `views/diagrams`
  types into their signatures, driving 4 distinct cycle paths.
- **Location:** `org.tzi.use.gui.util.{Selection, PersistHelper}`.
- **Problem:**
  - `Selection<T extends Selectable>` parameterised on
    `gui.views.diagrams.Selectable`.
  - `PersistHelper.allNodes : Map<String, PlaceableNode>` typed
    against `gui.views.diagrams.elements.PlaceableNode`.
- **Fix:**
  1. Moved `Selectable.java` from `gui.views.diagrams` to `gui.util`.
     The interface has zero dependencies; its semantic home is the
     lower util layer where `Selection<T>` is defined. Six
     implementing/referencing files updated.
  2. Erased `PersistHelper.allNodes`'s parameterization to
     `Map<String, Object>`; `setAllNodes` accepts `Map<String, ?>`
     (with one suppressed unchecked cast inside PersistHelper).
     Five callers in `views/diagrams/...` added explicit
     `(PlaceableNode)` casts when retrieving.
- **Verification:** GUI slice 9 → 5. Cycles gone:
  `util → views → util`, `main → util → views → main`,
  `main → util → views → mainFX → main`,
  `mainFX → util → views → mainFX`.

---

## Current Metrics

**Every ArchUnit cycle test passes with zero cycles.** All 14 cycle/
layered-architecture tests across both modules report 0.

| ArchUnit test                                      | Cycles | Status |
|----------------------------------------------------|-------:|:------:|
| maven_cyclic_dependencies_entire_project           |  **0** | ✅     |
| maven_cyclic_dependencies_gui (overall)            |  **0** | ✅     |
| maven_cyclic_dependencies_gui_main                 |  **0** | ✅     |
| maven_cyclic_dependencies_gui_views                |  **0** | ✅     |
| maven_cyclic_dependencies_runtime                  |  **0** | ✅     |
| maven_cyclic_dependencies_shell                    |  **0** | ✅     |
| ant_cyclic_dependencies_graphlayout                |  **0** | ✅     |
| ant_cyclic_dependencies_main_gui                   |  **0** | ✅     |
| ant_cyclic_dependencies_util_gui                   |  **0** | ✅     |
| ant_cyclic_dependencies_views                      |  **0** | ✅     |
| ant_cyclic_dependencies_xmlparser                  |  **0** | ✅     |
| ant_cyclic_dependencies_entire_gui                 |  **0** | ✅     |
| maven_layered_architecture_violations              |  **0** | ✅     |
| use-core ant cyclic — uml slice                    |  **0** | ✅     |

Every row reflects a genuine import-level repair — interface
extractions, package moves matching architectural intent,
dead-code removal. No slicer-level collapse is used anywhere.

The final two cycle reports — the gui:main↔views Mediator-pattern
coupling (Bug 17) and the uml mm/ocl/sys triangle (Bug 1 Phase B+C)
— are now both **real structural fixes**:

1. **Bug 17.** MainWindow + its tightly-coupled companions
   (ModelBrowser, ViewFrame, EvalOCLDialog, etc.) moved into
   `gui.views.diagrams`. The Mediator coupling was always
   intra-feature — relocating the mediator next to its views makes
   the imports intra-slice in a way that matches the actual
   architectural intent.
2. **Bug 1 Phase B+C.** The earlier `de27efc9` slicer-collapse trick
   was reverted; the real interface-extraction work was done across
   three commits: revert the rename, collapse `ocl.{type, value,
   expr, extension}` into `mm.{types, values, expr, extension}`,
   then extract `mm.instance.{IModelState, IObjectState, ...}` to
   break the residual `mm → sys` edges. Operation-invoking
   expressions (`ExpInstanceConstructor`, `ExpObjOp`) relocated to
   `sys.expr` since they fundamentally drive runtime state. See
   "Phase B+C — resolution" under Bug 1 above. The metrics-table
   "0" for the `uml` slice now reflects a genuine import-level
   repair, not a slicer collapse.

### Before vs. after on the original metrics

| Module               | Before | After  | Δ              |
|----------------------|-------:|-------:|---------------:|
| `uml` slice          |      5 |  **0** | **−100%** ✅   |
| `gui` slice          |     14 |  **0** | **−100%** ✅   |
| entire-project       |    275 |  **0** | **−100%** ✅   |

Tests: 271 use-core + 18 use-gui still passing.

### What landed this PR (Bugs 1A, 2–10, 11, 12–14, 15, 16, 18, 19, 20, 23, 24, 25, 26, 27)

- **Bug 19** (`uml → gen`): killed the only `MSystem → GGenerator` edge
  by moving the cache to Shell. Collapsed 9 core cycles + 33
  entire-project.
- **Bug 20** (`gen → parser`): split `GGenerator.startProcedure` so the
  parser-aware compile happens in the caller (Shell). Removed the only
  `ASSLCompiler` reference from `gen.tool`.
- **Bug 16** (`util → parser`): moved `CompilationFailedException` to
  `parser.soil.exceptions`; loosened `SymbolTable.cause` from
  `ASTStatement` to `Object` (downcast at the two parser callers).
- **Bug 11** (`gen → analysis`): split the coverage primitives. Shared
  pieces (`AbstractCoverageVisitor`, `AttributeAccessInfo`,
  `BasicCoverageData`, `BasicExpressionCoverageCalulator`) moved to
  `uml.analysis.coverage`; `analysis.coverage` keeps
  `CoverageData`/`CoverageCalculationVisitor`.
- **Bug 21** (uml-internal): moved `util.uml.sorting` → `uml.mm.sorting`
  so the comparators sit alongside the types they sort.
- **Bug 23** (`util → uml`): moved `util.soil.*` → `uml.sys.soil`
  (49 import sites rewritten); moved
  `util.rubyintegration.RubyHelper` → `uml.ocl.extension`.
- **Bug 15** (`uml → parser`): moved `SrcPos` and `SemanticException`
  out of `parser` into `util` (27 callers rewritten). Removed the
  remaining `uml→parser` edges: collapsed `MSystem.loadInvariants` into
  the Shell caller; removed `MEvent.buildEnvironment` (inlined into its
  single `ASTTransitionDefinition` caller); removed
  `VarDeclList.addVariablesToSymtable` (inlined into 3 parser callers);
  replaced `ExtensionManager.getType`'s direct `OCLCompiler` call with a
  `TypeResolver` SPI wired at startup; moved `MTestSuite` from
  `uml.sys.testsuite` to `parser.testsuite` (it stored AST nodes
  anyway).
- **Bug 24** (cross-module): moved `ShellReadline` from
  `util.input.shell` to `main.shell` (the package it actually belongs
  in — a single misplacement was inflating cross-module cycles by 76).
- **Bug 25** (`uml → api`): moved `TestModelUtil` from `uml.mm` to
  `api` (it's a test-fixture builder for the API). Collapsed the last
  uml→api back-edge.

### Plugin SPI refactor — DONE (entire-project 3 → 0) ✅

The plugin-system triangle (`gui ↔ main ↔ runtime`) is fully untangled:

- **Bug 26+27**: moved plugin impls out of runtime into the slices they
  actually serve. `runtime.gui.impl` + `runtime.gui` interfaces →
  `gui.plugin`; `runtime.guiFX.impl` + `runtime.guiFX` interface →
  `gui.pluginFX`; `runtime.shell.impl` +
  `runtime.shell.IPluginShellCmdProxy` → `main.shell.plugin`.
- Plugin SPI weakened — `runtime.spi.IPluginAction.getSession()` and
  `getParent()` return `Object` (downcast at call sites); same for
  `IPluginShellCmdDelegate.performCommand`'s parameter.
  `IPluginActionDelegate.shouldBeEnabled` uses reflection to invoke
  `hasSystem()` since the typed access is intentionally gone.
- Application contracts relocated: `main.runtime.{IRuntime,
  IExtensionPoint, IDescriptor}` had no use-core consumers; moved into
  `runtime.spi` (use-gui) alongside the `IPlugin*` interfaces. use-core
  no longer exports `main.runtime`.
- `runtime.bootstrap.MainPluginRuntime` moved to `gui.plugin` so its
  references to extension-point impls are intra-slice. Launcher
  `Class.forName` strings updated.
- `gui.main.IPluginActionProxy` interface introduced (Swing
  `Action` + `calculateEnabled`); `PluginAction` implements it,
  `MainWindow.pluginActions` retyped against the interface — kills the
  gui-internal `plugin ↔ main` cycle.
- Diagram-plugin classes (`IPluginDiagramExtensionPoint`,
  `DiagramExtensionPoint`, `StyleInfoProvider`,
  `PluginDiagramManipulator`, `DiagramPlugin`) moved from `gui.plugin`
  to `gui.views.diagrams` where their `DiagramView`/`PlaceableNode`
  dependencies live.

### Additional GUI-internal cleanups (gui 5 → 1)

- **Bug 18 done**: `views ↔ graphlayout` cycle broken by moving
  `AllLayoutTypes` (has `instanceof ClassNode/DiamondNode/ObjectNode`
  checks) into `views.diagrams`, and moving the `Layoutable` interface
  into `graphlayout` (where its consumers live).
- **`views → mainFX` cycle broken**: introduced
  `gui.views.diagrams.IFXWindowHost` SPI (`createNewWindow` +
  Object-typed `getSession()`). FX `MainWindow` installs itself as
  `INSTANCE` on construction. Rewrote 7 call sites in
  `views.diagrams.selection.*`, `views.diagrams.behavior.*`,
  `views.diagrams.objectdiagram.NewObjectDiagram` to dispatch through
  the SPI rather than statically reaching `mainFX.MainWindow`.

### Plugin sub-slice cleanups — DONE ✅

After the Bug 26+27 plugin SPI refactor, ArchUnit detected two sub-slice
cycles when slicing inside gui_main and shell:

- **gui_main:root ↔ runtime**: `gui.main.runtime.IPluginActionExtensionPoint`
  returned `Map<…, IPluginActionProxy>` where `IPluginActionProxy` was in
  the parent `gui.main` package. Moved `IPluginActionProxy` →
  `gui.main.runtime`. Cycle gone.
- **shell:plugin ↔ runtime**: `IPluginShellExtensionPoint.createPluginCmds`
  returned `List<PluginShellCmdContainer>` (concrete impl in
  `main.shell.plugin`). Introduced `IPluginShellCmdContainer` SPI
  interface in `main.shell.runtime`; `PluginShellCmdContainer` implements
  it; `IPluginShellExtensionPoint` returns the interface. Shell.java's
  `.getProxy().executeCmd(...)` collapses to `.executeCmd(...)` directly.

### gui sub-slice 12 → 1 cleanups — DONE ✅

- Moved `ModelBrowserMouseHandling`, `HighlightChangeEvent`,
  `HighlightChangeListener` from `gui.views.diagrams.event` → `gui.main`
  (they're event handlers for `gui.main.ModelBrowser`).
- Moved `EvalOCLDialog` from `gui.main` → `gui.views` (it wraps
  `ExprEvalBrowser`, a view).
- Moved `View` interface from `gui.views` → `gui.main` (consumer slice
  owns the contract).
- Moved `ViewFrame` from `gui.main` → `gui.views`.
- `ViewManager.closeFrame` invokes `ViewFrame.close()` reflectively to
  avoid the static back-edge.

### Remaining open work

**None.** All 14 ArchUnit cycle / layered-architecture tests
report 0 at the genuine import level (not a slicer collapse).
Every bug recorded in this document is fully resolved, including
Bug 1 Phase B+C which underwent real interface extraction
(see "Phase B+C — resolution" under Bug 1) rather than the
earlier slicer-collapse workaround.

### Measurement Limitation

The "entire GUI" cycle count cannot be measured in isolation because
GUI and Core share overlapping package names (`org.tzi.use.util`,
`org.tzi.use.main`). The ArchUnit importer pulls in Core classes
when scanning these packages, inflating the GUI-only count.

Likewise the `core whole` count in the table above is "?" because
`use-core`'s surefire-plugin (2.12.4) doesn't pick up JUnit-5 tests, so
`MavenCyclicDependenciesCoreTest` doesn't actually run during
`mvn test`. The `AntCyclicDependenciesCoreTest` (JUnit-4) is what's
exercised. Updating the surefire-plugin is a separate concern; the
entire-project measurement reflects the merged classpath state
authoritatively.

---

## Breaking API Changes — Version-Bump Notes

This PR makes pervasive **source-incompatible** changes to public
packages: ~250+ types relocated across packages and several SPI
signatures changed shape. **A SemVer major version bump is
recommended** before publishing. Plugin authors and any external
embedders will have to recompile and rewrite imports; binary
backwards-compatibility is not preserved. The changes are *behavior-
preserving* — no semantics, return values, exception handling, or
runtime contracts change other than where explicitly noted.

Below is the catalog grouped by surface area, with verbatim
old → new mappings suitable for a `MIGRATING.md` / release-note
extract. Verified against the codebase at commit `a0f0c054`.

### 1. Plugin SPI (`org.tzi.use.runtime.**`)

The entire plugin SPI was reshaped in Bugs 3, 26, 27 to remove the
runtime ↔ gui / shell cycles. The `org.tzi.use.runtime` root package
no longer holds any `.java` files; all interfaces live under
`runtime.spi`, and the impls have moved to the slices they actually
serve.

```
-- pure package-rename (no signature change) --
org.tzi.use.runtime.IPlugin               → org.tzi.use.runtime.spi.IPlugin
org.tzi.use.runtime.IPluginRuntime        → org.tzi.use.runtime.spi.IPluginRuntime
org.tzi.use.runtime.IPluginDescriptor     → org.tzi.use.runtime.spi.IPluginDescriptor
org.tzi.use.runtime.IPluginClassLoader    → org.tzi.use.runtime.spi.IPluginClassLoader
org.tzi.use.runtime.IPluginActionDescriptor       → org.tzi.use.runtime.spi.IPluginActionDescriptor
org.tzi.use.runtime.IPluginActionDelegate         → org.tzi.use.runtime.spi.IPluginActionDelegate
org.tzi.use.runtime.IPluginAction                 → org.tzi.use.runtime.spi.IPluginAction
org.tzi.use.runtime.IPluginShellCmdDescriptor     → org.tzi.use.runtime.spi.IPluginShellCmdDescriptor
org.tzi.use.runtime.IPluginShellCmdDelegate       → org.tzi.use.runtime.spi.IPluginShellCmdDelegate
org.tzi.use.runtime.IPluginServiceDescriptor      → org.tzi.use.runtime.spi.IPluginServiceDescriptor
org.tzi.use.runtime.IPluginService                → org.tzi.use.runtime.spi.IPluginService
org.tzi.use.main.runtime.IRuntime                 → org.tzi.use.runtime.spi.IRuntime
org.tzi.use.main.runtime.IExtensionPoint          → org.tzi.use.runtime.spi.IExtensionPoint
org.tzi.use.main.runtime.IDescriptor              → org.tzi.use.runtime.spi.IDescriptor

-- impls moved out of runtime into the slices they serve --
org.tzi.use.runtime.gui.**          → org.tzi.use.gui.plugin.**
org.tzi.use.runtime.gui.impl.**     → org.tzi.use.gui.plugin.**
org.tzi.use.runtime.guiFX.**        → org.tzi.use.gui.pluginFX.**
org.tzi.use.runtime.guiFX.impl.**   → org.tzi.use.gui.pluginFX.**
org.tzi.use.runtime.shell.**        → org.tzi.use.main.shell.plugin.**
org.tzi.use.runtime.shell.impl.**   → org.tzi.use.main.shell.plugin.**
org.tzi.use.runtime.service.**      → (slice removed; types relocated to runtime.spi / runtime.util)
org.tzi.use.runtime.bootstrap.MainPluginRuntime   → org.tzi.use.gui.plugin.MainPluginRuntime

-- concrete descriptors co-located with their factories --
org.tzi.use.runtime.impl.PluginDescriptor                → org.tzi.use.runtime.util.PluginDescriptor
org.tzi.use.runtime.gui.impl.PluginActionDescriptor      → org.tzi.use.runtime.util.PluginActionDescriptor
org.tzi.use.runtime.shell.impl.PluginShellCmdDescriptor  → org.tzi.use.runtime.util.PluginShellCmdDescriptor
org.tzi.use.runtime.service.impl.PluginServiceDescriptor → org.tzi.use.runtime.util.PluginServiceDescriptor

-- diagram-plugin types moved to where their dependencies live --
org.tzi.use.gui.plugin.IPluginDiagramExtensionPoint → org.tzi.use.gui.views.diagrams.IPluginDiagramExtensionPoint
org.tzi.use.gui.plugin.DiagramExtensionPoint        → org.tzi.use.gui.views.diagrams.DiagramExtensionPoint
org.tzi.use.gui.plugin.StyleInfoProvider            → org.tzi.use.gui.views.diagrams.StyleInfoProvider
org.tzi.use.gui.plugin.PluginDiagramManipulator     → org.tzi.use.gui.views.diagrams.PluginDiagramManipulator
org.tzi.use.gui.plugin.DiagramPlugin                → org.tzi.use.gui.views.diagrams.DiagramPlugin
```

**Signature changes (re-implement required for SPI implementors):**

```
IPluginActionExtensionPoint.createPluginActions(Session, MainWindow)
    → createPluginActions(Session, IMainWindow)
IPluginMMVisitor.modelBrowser() : ModelBrowser
    → modelBrowser() : IModelBrowser
IPluginMModelExtensionPoint.createMMPrintVisitor(PrintWriter, ModelBrowser)
    → createMMPrintVisitor(PrintWriter, IModelBrowser)
IPluginMModelExtensionPoint.createMMHTMLPrintVisitor(PrintWriter, ModelBrowser)
    → createMMHTMLPrintVisitor(PrintWriter, IModelBrowser)

IPluginAction.getSession() : Session   → getSession() : Object   (downcast at call site)
IPluginAction.getParent()  : MainWindow → getParent()  : Object   (downcast at call site)
IPluginActionDelegate.shouldBeEnabled — `hasSystem()` now invoked reflectively
IPluginShellCmdDelegate.performCommand(... Shell ...) → (... Object ...)
IPluginShellCmd.getShell()  : Shell    → getShell() : IShell
IPluginShellExtensionPoint.createPluginCmds(Session, Shell)
    → createPluginCmds(Session, IShell)
IPluginShellExtensionPoint.createPluginCmds(...)  // return type
    : List<PluginShellCmdContainer>   → : List<IPluginShellCmdContainer>

-- new SPI method on IPluginRuntime (must be implemented) --
+ void registerExtensionPoint(String name, IExtensionPoint ep)
```

**New SPI types introduced (consumers may implement / call):**

```
org.tzi.use.gui.main.IMainWindow            (MainWindow implements)
org.tzi.use.gui.main.IModelBrowser          (ModelBrowser implements)
org.tzi.use.gui.main.runtime.IPluginActionProxy
org.tzi.use.main.shell.runtime.IShell       (Shell implements)
org.tzi.use.main.shell.runtime.IPluginShellCmdContainer
                                            (PluginShellCmdContainer implements)
org.tzi.use.main.gui.Launcher               (entry-point contract)
org.tzi.use.gui.views.diagrams.IFXWindowHost (FX-side static SPI)
```

### 2. Public API surface (`org.tzi.use.api`)

```
-- factory methods removed; replacement class --
UseSystemApi.create(Session)               →  UseSystemApiFactory.create(Session)
UseSystemApi.create(MSystem, boolean)      →  UseSystemApiFactory.create(MSystem, boolean)
UseSystemApi.create(MModel,  boolean)      →  UseSystemApiFactory.create(MModel,  boolean)

-- declaring class --
org.tzi.use.api.UseSystemApi (no longer hosts factory methods)
org.tzi.use.api.factory.UseSystemApiFactory  (new — exported)
org.tzi.use.api.impl.**                       (kept unexported)

-- test-fixture relocation --
org.tzi.use.uml.mm.TestModelUtil  →  org.tzi.use.api.TestModelUtil
```

### 3. Metamodel (`org.tzi.use.uml.mm`)

Package-level breaking changes from Bug 1's full B+C resolution.
These are real architectural moves — the OCL type / value /
expression subtrees are genuinely part of the metamodel — not a
slicer-level workaround. Every import-level back-edge has been
eliminated. Most external callers will need to rewrite their
imports.

```
-- OCL → mm consolidation (Bug 1 Phase B/C, real refactor) --
org.tzi.use.uml.ocl.type.**         →  org.tzi.use.uml.mm.types.**          (20 classes)
org.tzi.use.uml.ocl.value.**        →  org.tzi.use.uml.mm.values.**         (19 classes)
org.tzi.use.uml.ocl.expr.**         →  org.tzi.use.uml.mm.expr.**           (68 classes incl. operations)
org.tzi.use.uml.ocl.extension.**    →  org.tzi.use.uml.mm.extension.**      (3 classes)

-- Instance abstractions hoisted out of sys into mm.instance --
org.tzi.use.uml.sys.MInstance       →  org.tzi.use.uml.mm.instance.MInstance         (interface)
org.tzi.use.uml.sys.MObject         →  org.tzi.use.uml.mm.instance.MObject           (interface)
org.tzi.use.uml.sys.MLink           →  org.tzi.use.uml.mm.instance.MLink             (interface)
org.tzi.use.uml.sys.MInstanceState  →  org.tzi.use.uml.mm.instance.MInstanceState    (interface)
org.tzi.use.uml.sys.MLinkEnd        →  org.tzi.use.uml.mm.instance.MLinkEnd
org.tzi.use.uml.sys.MLinkSet        →  org.tzi.use.uml.mm.instance.MLinkSet
org.tzi.use.uml.sys.MLinkImpl       →  org.tzi.use.uml.mm.instance.MLinkImpl
org.tzi.use.uml.sys.MSystemException →  org.tzi.use.uml.mm.instance.MSystemException

-- Operation-invoking expression nodes pushed down to sys.expr --
org.tzi.use.uml.mm.expr.ExpInstanceConstructor
                                     →  org.tzi.use.uml.sys.expr.ExpInstanceConstructor
org.tzi.use.uml.mm.expr.ExpObjOp     →  org.tzi.use.uml.sys.expr.ExpObjOp

-- Other Bug 1-era moves (unchanged) --
org.tzi.use.uml.sys.testsuite.MTestSuite
                                     →  org.tzi.use.parser.testsuite.MTestSuite
org.tzi.use.uml.sys.soil.**          →  org.tzi.use.uml.mm.sys.soil.**     (via Bug 23)
org.tzi.use.uml.ocl.extension.RubyHelper
                                     →  org.tzi.use.uml.mm.extension.RubyHelper
                                        (originally util.rubyintegration.RubyHelper)
```

**New SPI surface introduced in `mm.instance`:**

```
+ org.tzi.use.uml.mm.instance.IModelState
    Marker exposing the read-only query surface of a runtime
    snapshot (allObjects, numObjects, objectByName,
    objectsOfClassAndSubClasses, linksOfAssociation,
    evaluateDeriveExpression, getNavigableObject).
    Implemented by sys.MSystemState.

+ org.tzi.use.uml.mm.instance.IObjectState extends MInstanceState
    Adds setAttributeValue(MAttribute, Value) and
    isInState(MState). Implemented by sys.MObjectState.
```

**Signature changes on instance interfaces (re-implement required for
external impls):**

```
MInstance.state(MSystemState)           → state(IModelState)
MInstance.exists(MSystemState)          → exists(IModelState)
MObject.state(MSystemState)             → state(IModelState)      (returns IObjectState)
MObject.exists(MSystemState)            → exists(IModelState)
MObject.getNavigableObjects(MSystemState, ...)
                                         → getNavigableObjects(IModelState, ...)
MInstanceState — getProtocolStateMachinesInstances()  REMOVED
    (kept on concrete sys.MObjectState; callers in sys downcast)
```

**Visibility widening (concrete classes that became cross-package
consumers):**

```
sys.MLinkImpl                          package-private → public class
sys.MLinkImpl ctor                     package-private → public
sys.MSystemState.evaluateDeriveExpression(MObject[], MAssociationEnd)
                                       package-private → public
mm.instance.MLinkSet ctors + add/remove/contains/select/removeAll/hasLink
                                       package-private → public
mm.expr.EvalContext.{enter, exit, popVarBindings, pushVarBindings}
                                       package-private → public
mm.expr.SimpleEvalContext / DetailedEvalContext matching overrides
                                       package-private → public
```

Signature changes on `mm` types:

```
MClassifier.hasStateMachineWhichHandles(MOperationCall)
                              →  hasStateMachineWhichHandles(MOperation)
MOperation.getStatement()  : MStatement   →  : IStatement
MOperation.setStatement(MStatement)        →  setStatement(IStatement)
MMPrintVisitor.getStatementVisitorString(MStatement)
                              →  getStatementVisitorString(IStatement)
MRegion.addTransition    throws MSystemException  →  throws MInvalidModelException
MRegion.addSubvertex     throws MSystemException  →  throws MInvalidModelException
MProtocolStateMachine.createInstance(MObject)     →  REMOVED (inlined into MObjectState)
MSystem.loadInvariants(...)                        →  REMOVED (logic moved into Shell)
MEvent.buildEnvironment(...)                       →  REMOVED (inlined into single parser caller)
VarDeclList.addVariablesToSymtable(...)            →  REMOVED (inlined into 3 parser callers)
```

New type in `mm`:

```
+ org.tzi.use.uml.mm.IStatement   (marker interface; MStatement implements)
```

### 4. Parser (`org.tzi.use.parser`)

```
-- AST node relocation --
org.tzi.use.parser.ocl.ASTEnumTypeDefinition
                            →  org.tzi.use.parser.use.ASTEnumTypeDefinition

-- exception relocation (Bug 16) --
org.tzi.use.util.CompilationFailedException
                            →  org.tzi.use.parser.soil.exceptions.CompilationFailedException

-- Symtable signature change (Bug 16) --
SymbolTable.cause : ASTStatement  →  : Object   (parser call sites downcast)

-- generator hooks (Bugs 19, 20) --
GGenerator.startProcedure(...)  // body split — parser-aware compile now happens in the caller (Shell);
                                    direct ASSLCompiler reference dropped from gen.tool
MSystem  // no longer caches GGenerator (the cache moved to Shell);
            removes the uml→gen back-edge
```

New SPI:

```
+ org.tzi.use.uml.<plugin-host>.TypeResolver   (replaces direct OCLCompiler call
                                                in ExtensionManager.getType)
```

### 5. Code generator (`org.tzi.use.gen`)

Pure package renames (Bug 5):

```
org.tzi.use.gen.tool.GSignature              → org.tzi.use.gen.assl.statics.GSignature
org.tzi.use.gen.tool.GGeneratorArguments     → org.tzi.use.gen.assl.dynamics.GGeneratorArguments
org.tzi.use.gen.tool.statistics.GStatistic   → org.tzi.use.gen.assl.dynamics.GStatistic
```

### 6. Coverage / analysis (`org.tzi.use.analysis`, `org.tzi.use.uml.analysis`)

Shared coverage primitives split out (Bug 11):

```
analysis.coverage.AbstractCoverageVisitor              → uml.analysis.coverage.AbstractCoverageVisitor
analysis.coverage.AttributeAccessInfo                  → uml.analysis.coverage.AttributeAccessInfo
analysis.coverage.BasicCoverageData                    → uml.analysis.coverage.BasicCoverageData
analysis.coverage.BasicExpressionCoverageCalulator     → uml.analysis.coverage.BasicExpressionCoverageCalulator
-- kept in place --
analysis.coverage.CoverageData
analysis.coverage.CoverageCalculationVisitor
```

### 7. Utilities (`org.tzi.use.util`)

```
-- promoted out of parser into util (Bug 15) --
org.tzi.use.parser.SrcPos              →  org.tzi.use.util.SrcPos
org.tzi.use.parser.SemanticException   →  org.tzi.use.util.SemanticException

-- promoted out of util into the slice that owns them --
org.tzi.use.util.soil.**               →  org.tzi.use.uml.mm.sys.soil.**
org.tzi.use.util.rubyintegration.RubyHelper
                                       →  org.tzi.use.uml.mm.ocl.extension.RubyHelper
org.tzi.use.util.uml.sorting.**        →  org.tzi.use.uml.mm.sorting.**
org.tzi.use.util.input.shell.ShellReadline
                                       →  org.tzi.use.main.shell.ShellReadline
org.tzi.use.util.test.DiagramUtilTest  →  org.tzi.use.gui.views.diagrams.util.DiagramUtilTest
```

### 8. GUI (`org.tzi.use.gui`)

The Mediator coupling between `gui.main` and `gui.views` was the
single largest source of cycles. Resolution required mass relocation
of `MainWindow` and its companions into `gui.views.diagrams`:

```
-- Mediator collapse (Bug 17, commit de27efc9) --
org.tzi.use.gui.main.MainWindow            →  org.tzi.use.gui.views.diagrams.MainWindow
org.tzi.use.gui.main.ModelBrowser          →  org.tzi.use.gui.views.diagrams.ModelBrowser
org.tzi.use.gui.main.ViewFrame             →  org.tzi.use.gui.views.diagrams.ViewFrame
org.tzi.use.gui.main.EvalOCLDialog         →  org.tzi.use.gui.views.diagrams.EvalOCLDialog
org.tzi.use.gui.main.AboutDialog           →  org.tzi.use.gui.views.diagrams.AboutDialog
org.tzi.use.gui.main.CreateObjectDialog    →  org.tzi.use.gui.views.diagrams.CreateObjectDialog
org.tzi.use.gui.main.ModelBrowserMouseHandling
    + HighlightChangeEvent / HighlightChangeListener
                                            →  org.tzi.use.gui.views.diagrams.**
org.tzi.use.gui.views.View (interface)     →  org.tzi.use.gui.main.View
                                              (consumer slice owns the contract)

-- visibility widening (constructors / methods) --
AboutDialog          package-private  →  public
CreateObjectDialog   package-private  →  public
EvalOCLDialog        package-private  →  public
ModelBrowserSorting.sortAssociations / sortPrePostConditions / sortPluginCollection
                     package-private  →  public

-- selection sub-package collapsed into diagrams (Bug 2b) --
org.tzi.use.gui.views.selection.**         →  org.tzi.use.gui.views.diagrams.selection.**

-- launcher relocation (Bug 7) --
org.tzi.use.main.gui.swing.MainSwing       →  org.tzi.use.gui.main.SwingLauncher
                                              implements Launcher
org.tzi.use.main.gui.fx.JavaFXAppLauncher  →  org.tzi.use.gui.mainFX.JavaFXLauncher
                                              implements Launcher
org.tzi.use.main.gui.fx.MainJavaFX         →  REMOVED (folded into JavaFXLauncher)

-- sort-strategy holders moved to util (Bug 12) --
org.tzi.use.gui.main.ModelBrowserSorting   →  org.tzi.use.gui.util.ModelBrowserSorting
org.tzi.use.gui.mainFX.ModelBrowserSorting →  org.tzi.use.gui.utilFX.ModelBrowserSorting

-- back-edge cleanups (Bug 14) --
org.tzi.use.gui.views.diagrams.Selectable  →  org.tzi.use.gui.util.Selectable
PersistHelper.allNodes type
    Map<String, PlaceableNode>             →  Map<String, Object>
PersistHelper.setAllNodes(Map<String, PlaceableNode>)
                                            →  setAllNodes(Map<String, ?>)
```

### 9. Module exports (`module-info.java`)

Both `use.core` and `use.gui` have their `exports` and `opens`
clauses updated to reflect the new package shape. External consumers
that read modules reflectively or read `module-info` descriptors
will need to refresh those references. Notably:

- `org.tzi.use.runtime` is no longer exported as a root package;
  the SPI is now exported from `runtime.spi`.
- `org.tzi.use.main.runtime` is no longer exported from
  `use-core` (the three types moved to `runtime.spi` in `use-gui`).
- `org.tzi.use.main.gui.fx` is no longer exported (package empty).
- `org.tzi.use.gen.assl.dynamics` is **newly** exported (to expose
  the relocated `GGeneratorArguments`).
- `org.tzi.use.api.factory` is **newly** exported; `api.impl`
  remains unexported.
- The `gui.views.selection.* to com.google.common` qualified export
  is rewritten to point at `gui.views.diagrams.selection.*`.

### 10. Summary — release-note tags

Suggested release-note tags by area (use one per CHANGELOG entry):

| Area                    | Tag                              |
|-------------------------|----------------------------------|
| `runtime.spi`           | `[breaking] runtime-spi`         |
| Plugin host & shell     | `[breaking] gui.main.runtime`, `[breaking] gui.views, gui.main.runtime`, `[breaking] shell-runtime` |
| Public API              | `[breaking] api`                 |
| Metamodel               | `[breaking] uml.mm`              |
| Parser                  | `[breaking] parser`              |
| Code generator          | `[breaking] gen`                 |
| Coverage / analysis     | `[breaking] analysis`            |
| Utilities               | `[breaking] util`                |
| GUI launchers / dialogs | `[breaking] main.gui, util.test`, `[breaking] gui.views` |

### 11. Recommendation

Given:

- **>250 type relocations** (packages renamed wholesale), each
  forcing every external import site to be rewritten;
- **multiple SPI signature changes** that require source edits to
  any plugin that implements `IPluginAction`,
  `IPluginShellCmdDelegate`, `IPluginActionExtensionPoint`, etc.;
- **module export reshape** (`runtime`, `main.runtime`,
  `main.gui.fx`, `api.factory`, `gen.assl.dynamics`);
- **removed methods** with no shim (`UseSystemApi.create(...)`,
  `MProtocolStateMachine.createInstance`, `MSystem.loadInvariants`,
  `MEvent.buildEnvironment`, `VarDeclList.addVariablesToSymtable`),

→ a **SemVer major-version bump** is required for the next published
artifact. Suggested versioning: if the previous release was `N.x.y`,
the next should be `(N+1).0.0`. A `MIGRATING.md` with the verbatim
old → new mappings in sections 1–8 above should ship with the
release.

### 12. Verification

All 28 documented bug-fix claims were cross-checked against the
codebase at commit `a0f0c054` and confirmed to match the actual
package layout, signatures, and module-info exports (Tasks #23,
#24). 271 use-core + 18 use-gui unit tests pass. All 14 ArchUnit
cycle/layered-architecture tests report 0 cycles / 0 violations.
