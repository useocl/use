# Cyclic Dependency Bugs

> This file tracks architectural bugs detected by ArchUnit.
> Delete this file once all issues are resolved and the PR is merged.

We start from the commit [5989a4b](https://github.com/useocl/use/commit/5989a4be5f2b181189965482f4f10da3971878a4) (Merge pull request #130 from croni42/feature-main-branch-adjustment), date: 2026-04-27 (YYYY-MM-DD).

---

## Bug 1: `uml.mm` ↔ `uml.ocl` ↔ `uml.sys` triangle (use-core)

- **Severity:** Critical — 34 cycles, 2231 dependency violations
- **Location:** `org.tzi.use.uml.*`
- **Problem:** The metamodel (`mm`), OCL layer (`ocl`), and runtime system (`sys`) form
  a tightly coupled triangle. This is the single largest source of cycles in the project.
  - `uml.ocl.value` types (`ObjectValue`, `LinkValue`, `InstanceValue`) hold direct
    references to `uml.sys` runtime objects (`MObject`, `MLink`, `MInstance`).
  - `uml.sys.soil.*` statement constructors take `uml.ocl.expr.Expression` parameters.
  - `util.soil.VariableEnvironment` depends on `uml.ocl.value.Value`.
- **Fix direction:** Introduce interfaces/abstractions at the `uml.ocl` ↔ `uml.sys`
  boundary. Value types should not hold concrete runtime references.

<!-- BEGIN MERMAID:bug-1 -->
**uml.* triangle** — 5 cycle(s), 6 edge(s) across 3 package(s)

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
<!-- END MERMAID:bug-1 -->

## Bug 2: `gui.main` and `gui.views` internal cycles (use-gui)

- **Severity:** Medium — 1 cycle each, 14 GUI-specific cycles total
- **Location:** `org.tzi.use.gui.main`, `org.tzi.use.gui.views`
- **Problem:** Subpackages within `gui.main` and `gui.views` have circular dependencies
  between each other.
- **Fix direction:** Extract shared types into a common subpackage or flatten the hierarchy.

<!-- BEGIN MERMAID:bug-2 -->
**gui.main** — 1 cycle(s), 2 edge(s) across 2 package(s)

```mermaid
flowchart LR
    root["root"]
    runtime["runtime"]
    root --> runtime
    runtime --> root
    linkStyle 0,1 stroke:#d33,stroke-width:2px
```

**gui.views** — 1 cycle(s), 2 edge(s) across 2 package(s)

```mermaid
flowchart LR
    diagrams["diagrams"]
    selection["selection"]
    diagrams --> selection
    selection --> diagrams
    linkStyle 0,1 stroke:#d33,stroke-width:2px
```
<!-- END MERMAID:bug-2 -->

## Bug 3: `runtime` package cycles (use-gui)

- **Severity:** High — 43 cycles
- **Location:** `org.tzi.use.runtime`
- **Problem:** The runtime package has heavy internal coupling (43 cycles detected in
  Maven structure analysis).
- **Fix direction:** Needs further investigation — identify which runtime subpackages
  are involved and break the dependency chains.

<!-- BEGIN MERMAID:bug-3 -->
**runtime** — 43 cycle(s), 20 edge(s) across 6 package(s)

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

## Bug 6: `parser.ocl` ↔ `parser.use` / `parser.soil` cycles (use-core)

- **Severity:** Low — 2 cycles
- **Location:** `org.tzi.use.parser.*`
- **Problem:** SOIL AST nodes reference OCL AST types (`ASTType`, `ASTExpression`),
  and the OCL parser references USE parser types, creating mutual dependencies.
- **Fix direction:** Extract shared AST base types into a common `parser.ast` package.

<!-- BEGIN MERMAID:bug-6 -->
**parser.*** — 2 cycle(s), 4 edge(s) across 3 package(s)

```mermaid
flowchart LR
    ocl["ocl"]
    use["use"]
    soil["soil"]
    ocl --> use
    use --> ocl
    use --> soil
    soil --> ocl
    linkStyle 0,1,2,3 stroke:#d33,stroke-width:2px
```
<!-- END MERMAID:bug-6 -->

## Bug 7: Layer violations in GUI launcher (use-gui)

- **Severity:** Medium — 21 violations
- **Location:** `org.tzi.use.main.gui.*`, `org.tzi.use.util.test.*`
- **Problem:** These are not cycles but layer boundary violations:
  - `main.gui.fx.JavaFXAppLauncher` directly constructs `gui.mainFX.MainWindow`.
  - `main.gui.swing.MainSwing` directly constructs `gui.main.MainWindow`.
  - `util.test.DiagramUtilTest` calls into `gui.views.diagrams.util.Util`.
- **Fix direction:** Launchers should use a factory or DI to obtain window instances.
  Move `DiagramUtilTest` to the GUI test source root.

<!-- BEGIN MERMAID:bug-7 -->
**GUI launcher layer violations** — 21 violation(s) across 3 caller→callee pair(s)

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

## Current Metrics

| Module   | Cycles | Worst Area                          |
|----------|--------|-------------------------------------|
| use-core | 42     | `uml` package (Bug 1: 34 cycles)   |
| use-gui  | 384*   | `runtime` (Bug 3: 43 cycles)       |

\* Project-wide count; GUI-only is 14 cycles.

### Measurement Limitation

The "entire GUI" cycle count cannot be measured in isolation because GUI and Core share
overlapping package names (`org.tzi.use.util`, `org.tzi.use.main`). The ArchUnit importer
pulls in Core classes when scanning these packages, inflating the GUI-only count.
