# `decycle-2` — Maintainer Review & Change Proof

> **Audience:** maintainer reviewing the `decycle-2` PR before merge.
> **Purpose:** (1) *prove* the ArchUnit cyclic-dependency bugs are actually solved,
> (2) map every change for easy tracking, (3) list breaking changes for downstream
> plugin authors / embedders, (4) disclose follow-up fixes made on top of the PR.
>
> **Baseline:** `origin/main` @ `7e694be7` &nbsp;·&nbsp; **Head:** `decycle-2` @ `23bfeab1` &nbsp;·&nbsp; **86 commits.**
> Toolchain: OpenJDK 21.0.11, Maven 3.8.7. Self-contained — no other file required to review.

---

## 0. Verdict at a glance

| Check | Result |
|---|:--:|
| `use-core` + `use-gui` + `use-assembly` compile (Java 21) | ✅ `BUILD SUCCESS` (all 4 modules) |
| Full `mvn test` | ✅ **633 pass**, 0 failures / 0 errors / 0 skipped |
| Integration tests (`mvn install`/`verify`) | ✅ `OCLExpressionIT` (1) + `ShellIT` (129) pass¹ |
| ArchUnit cycle + layered tests **executing** | ✅ **6 / 6** classes, **37** tests |
| Cycle count on every measured slice + entire project | ✅ **0** |
| Layered-architecture violations (core → gui) | ✅ **0** |
| `cycles == 0` is **enforced** (a regression fails the build) | ✅ all 6 classes `assertEquals(0, …)` |
| `gui.views.diagrams` (the old "blind spot") | ✅ **now sliced & gated at 0** |
| Example plugins compile + run against the reshaped API | ✅ **5 / 5** |

<sub>¹ The IT pass requires the small build-infra fix in §4.1 (Failsafe). With the PR exactly as committed, `mvn test`/`mvn package` are green; `mvn install`/`verify` need §4.1.</sub>

**Headline:** before → after, on the exact same six ArchUnit tests:

| ArchUnit metric | `origin/main` | `decycle-2` |
|---|--:|--:|
| **entire-project cycles** | **384** | **0** |
| `runtime` | 43 | 0 |
| `core` module — Ant whole-slice / Maven no-tests / Maven with-tests | 34 / 55 / 210 | 0 / 0 / 0 |
| `gui` package (Ant) | 14 | 0 |
| `uml` slice | 5 | 0 |
| `parser` (production / with-tests) | 2 / 36 | 0 / 0 |
| `api` / `gen` | 1 / 1 | 0 / 0 |
| `gui.main` / `gui.views` / `main.shell` | 1 / 1 / 1 | 0 / 0 / 0 |
| **`gui.views.diagrams` sub-slices** (was unmeasured — see §1.3) | **11** (≈600 capped raw) | **0** |
| **layered-architecture violations** | **21** | **0** |

*Method: the `main` figures were obtained by building `origin/main` and running the same
`org.tzi.use.architecture.*` measurement logic; the `decycle-2` figures are **enforced** — the
tests `assertEquals(0, …)`, so the build cannot be green unless they are truly 0. Reproduce in §1.4.*

---

## 1. Proof the ArchUnit cycle bugs are solved

### 1.1 The cycles are gone *and the gate is real*

This is the crux: the original arch tests only **printed** the cycle count, so a regression
could not fail the build. On this branch **all six classes now assert zero**, so "0 cycles" is a
machine-checked invariant, not a claim.

| ArchUnit class | Module | Tests | Result | Asserts 0 via |
|---|:--:|:--:|:--:|---|
| `AntCyclicDependenciesCoreTest` | core | 11 | ✅ 0 fail | `assertEquals(…,0,cycleCount)` + `.should().beFreeOfCycles()` |
| `MavenCyclicDependenciesCoreTest` | core | 11 | ✅ 0 fail | `assertEquals(0,cycleCount,…)` + `beFreeOfCycles()` |
| `AntCyclicDependenciesGUITest` | gui | 6 | ✅ 0 fail | `assertEquals(…,0,cycleCount.get())` + `beFreeOfCycles()` |
| `MavenCyclicDependenciesGUITest` | gui | 7 | ✅ 0 fail | `assertEquals(…,0,cycleCount)` **and** sub-slice gate (§1.3) |
| `AntLayeredArchitectureTest` | gui | 1 | ✅ 0 fail | `assertEquals(…,0,violationCount)` |
| `MavenLayeredArchitectureTest` | gui | 1 | ✅ 0 fail | `assertEquals(0,violationCount,…)` |
| **Total** | | **37** | **✅ 0 fail** | |

### 1.2 The test harness used to under-report — fixed on this branch

A maintainer should know *why* the gate is trustworthy now. The POMs originally pinned **no**
Surefire version, so Maven 3.8.7 fell back to its default **2.12.4**, whose provider discovers
**JUnit 4 only**:

| | Before (default Surefire 2.12.4) | After (Surefire 3.5.2 + `junit-vintage-engine`) |
|---|---|---|
| Arch tests that actually execute | **4 / 6** — the two JUnit-5 classes were **silently skipped** | **6 / 6** |
| Arch tests that **assert** `cycles == 0` | **0** (all six only `println`) | **6** (`assertEquals(0, …)`) |
| `use-core` unit tests executed | **271** | **613** |
| Build outcome | green *even with hidden cycles* | green, and **a cycle now fails the build** |

Fix (commit `2ef85848`): pin `maven-surefire-plugin 3.5.2` in the parent `pluginManagement`,
add `org.junit.vintage:junit-vintage-engine` to both modules (so the JUnit-4 tests keep running
under the JUnit Platform), and add `assertEquals(0, …)` to all six arch tests.

### 1.3 The documented "blind spot" was closed and gated

The earlier `PR_decycle-2_STATUS.md` honestly disclosed a gap: no test rooted a slice at
`org.tzi.use.gui.views.diagrams`, so that subtree's internal cycles (≈600 at the capped raw count,
**11 cyclic sub-slices** by the slice-SCC metric) were *unmeasured*. **That gap is now closed.**
`MavenCyclicDependenciesGUITest` was extended with:

```java
@Test
public void count_cycles_in_gui_views_diagrams_package() {
    final String root = "org.tzi.use.gui.views.diagrams";
    …                                  // compute cyclic first-level sub-slices (SCCs)
    assertEquals("Cyclic dependencies detected among gui.views.diagrams sub-slices: "
            + new TreeSet<>(cyclic), 0, cyclic.size());   // ← 11 → 0, now GATED
}
```

The subtree was driven `11 → 0` by behaviour-preserving interface-extraction + relocation
(commits `d17335a9 … d45022f3`, §2.2 theme C), and the assertion now keeps it at 0.

### 1.4 Reproduce it yourself

```bash
# 1. compile everything (Java 21)
mvn -DskipTests clean install            # NB: build with `clean` (stale ANTLR target otherwise)

# 2. full suite — 633 tests; all six arch tests run and assert 0
mvn clean test

# 3. see a specific gate, e.g. the gui.views.diagrams sub-slice gate
mvn -pl use-gui -Dtest=MavenCyclicDependenciesGUITest test

# 4. (optional) show that origin/main is NOT free of cycles:
#    check out origin/main, copy these six tests in, run them — they fail with the §0 counts.
```

---

## 2. What changed — change map for tracking

### 2.1 Diff footprint

| Measure | Value |
|---|---|
| `.java` files changed (content; `--ignore-cr-at-eol`) | **710** |
| Line delta (content) | **+4 585 / −3 130** (≈11 lines/file — mostly `package` decls + imports) |
| Raw `.java` diff (`git diff --stat … -- '*.java'`, CRLF-inflated) | 782 files, +52 440 / −50 985 |
| Files differing **only** by line-ending (CRLF→LF) | ~72 *(cosmetic, no content change)* |

This is the signature of **decycling-by-relocation**: a *broad but shallow* change — moving a class
between packages touches every importer, but each touch is a one-line import edit. It is **not** a
large logic rewrite. (`origin/main` is mixed CRLF/LF; `decycle-2` is near-uniform LF, which accounts
for the ~72 EOL-only files and the inflated raw line counts — review with `git diff --ignore-cr-at-eol`.)

Most-touched packages (content diff): `uml/mm/expr` (64), `uml/sys/soil` (34), `parser/ocl` (33),
`gen/assl` (54), `uml/mm` (26), `gui/views/diagrams/framework` (22), `uml/mm/values|types` (42),
`runtime/spi` (14), `gui/views/diagrams/classdiagram` (14).

### 2.2 Cycle-bug → fix → gate (the tracking matrix)

| # | Cyclic area (the bug) | Before | Fix approach | Key commits | Now gated by |
|---|---|--:|---|---|---|
| A | `uml` `ocl ↔ mm ↔ sys` metamodel tangle | 5 (+210 with-tests) | Collapse `ocl.*` into `mm.*`; extract `IModelState`; relocate `MLink*`/`MSystemException`/operation-exprs into `mm.instance` / `sys.expr` | `de27efc9`, `42ab578c`, `57213380`, `aded938f` | `*CyclicDependenciesCoreTest` |
| B | `gui.main ↔ gui.views` Mediator cycle | 1 (entire-project 384) | Mediator-collapse via interfaces; cast `MObject.state(IModelState)` at the gui boundary | `de27efc9`, `5c10b6b5`, `04d1e12f` | `*CyclicDependenciesGUITest` |
| C | `gui.views.diagrams` internal tangle | 11 sub-slices | Extract a `framework` foundation slice + `base` slice; invert node/layout coupling via interfaces; merge `waypoints`→`elements`; dissolve `selection`; relocate `StyleInfo*`/`IFXWindowHost` | `a2d5095e`, `8fe4ee59`, `1c447ba3`, `4c375bd0`, `d198b1ff`, `334b536a`, `a373b640`, `b22c24bd`, `920418f3`, `d17335a9`, `d45022f3` | `MavenCyclicDependenciesGUITest` (§1.3) |
| D | cross-slice **test** placement inflating with-tests cycles | 36 / 210 | Relocate cross-slice + expression/system tests to `org.tzi.use.integration.*` / `…sys` | `3e0e2436`, `ed16b200` | the `*withTests` cases |
| E | test harness under-reporting (could hide cycles) | 4/6 run, 0 asserts | Surefire 3.5.2 + vintage + `assertEquals(0,…)` ×6 | `2ef85848` | the gate itself |
| F | defensive correctness at relocated boundaries | — | Guard `MObjectState` downcasts of `getSelf().state()`; cast `MObject.state` results in gui | `d0c684e2`, `5c10b6b5` | — |
| G | plugin compatibility with the reshaped API | — | Bundle the 5 example plugins rebuilt against the decycled 7.5.0 API; fix GUI-boot from obsolete plugins | `8a1d1935`, `96d283e9` | manual + §4.2 |
| H | docs / migration guide | — | `MIGRATING.md`, code-review & status notes, warning cleanup | `23bfeab1`, `93d26bba`, `0c83e9b7`, `54f16fb2` | — |

*(Full 86-commit list in the Appendix.)*

---

## 3. Breaking changes (downstream impact)

These are **source-incompatible** API changes: binary back-compat is **not** preserved — plugin
authors and embedders must recompile and rewrite imports. Behaviour is otherwise preserved
(no semantics/return/exception changes except the explicitly removed methods). A **SemVer major
bump** is appropriate. The authoritative, verbatim `old → new` mapping is in `MIGRATING.md`
(§§1–10); the summary:

| Area | Change | Migration |
|---|---|---|
| **Plugin SPI** `org.tzi.use.runtime.**` | SPI interfaces moved to `runtime.spi`; impls under `runtime.impl`, models in `runtime.model`, registries in `runtime.util`. New required `IPluginRuntime.registerExtensionPoint`. | recompile; re-implement changed SPI signatures (MIGRATING §1–2) |
| **Metamodel** `uml.mm` | `uml.ocl.{type,value,expr,extension}` → `uml.mm.{types,values,expr,extension}`; `MInstance/MObject/MLink*/MSystemException/MInstanceState` → `uml.mm.instance`; new `IModelState`. | prefix-rename (safe sed cheat-sheet in MIGRATING §0) + per-class moves §3 |
| **Soil / sorting** | `util.soil` → `uml.sys.soil`; `util.uml.sorting` → `uml.mm.sorting`. | prefix-rename |
| **Public API** `org.tzi.use.api` | signature adjustments. | re-check call sites (MIGRATING §4) |
| **GUI** `org.tzi.use.gui` | Mediator collapse; `views.selection` → `views.diagrams.selection`; diagram-editor split into `…diagrams.framework` / `…base` (§6, §6b). | plugin diagram-view host type `MainWindow` → `framework.IMainWindowServices`; `DiagramView` → `base`; `DiagramOptions`/`ViewFrame` → `framework` |
| **Module exports** | `module-info.java` exports updated. | §7 |
| **Removed methods** | no shims — callers must be rewritten. | §8 |
| **Visibility** | several types widened to `public` (informational). | §9 |

> The 5 bundled example plugins (OCLComplexity, ObjectToClass, AssociationExtend, ModelValidator,
> Filmstrip) have already been migrated against this exact API and recompile with **0 errors** —
> they are the worked reference for §3.

---

## 4. Follow-up fixes made on top of the PR (NOT yet committed)

> These are **uncommitted working-tree changes** I made while verifying the branch. They are
> *separate* from the 86-commit PR and listed so you can adopt or drop them independently.
> None touch decycling logic. Files: `use-core/pom.xml`, `use-gui/pom.xml`,
> `use-gui/src/main/resources/bin/use`, and the 5 jars under
> `use-assembly/src/main/resources/plugins/`.

### 4.1 Complete the test-enforcement fix so `mvn install`/`verify` is green
The PR pinned **Surefire** 3.5.2 but left **Failsafe** at the Maven-default **2.22.2**. Because the
documented baseline used `mvn clean test` (which never reaches the integration-test phase), this was
never exercised. `mvn install`/`verify` therefore **fails**: `OCLExpressionIT` dies with
`NoClassDefFoundError: org/junit/platform/commons/util/PreconditionViolationException`, and once
Failsafe is bumped, `ShellIT` hits a JPMS split-package crash (`java_cup.runtime` is in both module
`use.gui` and `vtd.xml`).
**Fix:** Failsafe `2.22.2 → 3.5.2` in `use-core` + `use-gui`, and `<useModulePath>false</useModulePath>`
on the use-gui Failsafe execution (the app launches via `java -jar` = classpath, so classpath ITs
match production). Result: `mvn clean install` fully green — 633 surefire + `OCLExpressionIT`(1) +
`ShellIT`(129), 0 failures.

### 4.2 Refresh the bundled plugin jars (fixes a runtime "obsolete jar" bug)
The committed `ModelValidatorPlugin-…jar` was **missing its `log4j/log4j.xml`** resource, so
`KodkodPlugin.doRun` threw `FileNotFoundException` at GUI startup. Rebuilding the 5 plugins from
`use_plugins@decycle-compat` (source unchanged — all 5 compile clean against the current API) with
the correct packaging restores the resource. **Verified at runtime:** clean GUI startup;
`modelvalidator -validate` → Kodkod→SAT4J → **SATISFIABLE**; `filmstrip` → model generated.

### 4.3 Silence the `use` launcher's readline error on Linux
`bin/use` printed `java.lang.UnsatisfiedLinkError: no natGNUReadline …` on every launch (the native
JNI lib is not shipped). The Windows launcher `start_use.bat` already passed `-nr`; the Linux `bin/use`
did not. **Fix:** add `-nr` to `bin/use` (one line) — silent fallback, consistent with Windows.

---

## 5. Scope, risk & non-goals

- **Risk profile:** low-per-change, high-count. 710 small relocation edits; behaviour-preserving
  (interface extraction + package moves). The 633-test suite + 6 enforced arch gates are the safety net.
- **Genuinely 0 at the suite's granularity.** Every root the suite slices is 0, *including* the
  formerly-unmeasured `gui.views.diagrams` subtree (§1.3). No remaining known cyclic blind spot.
- **EOL:** `decycle-2` is near-uniform LF vs `main`'s mixed CRLF/LF; review content with
  `git diff --ignore-cr-at-eol`. No gratuitous EOL flips were added by the follow-up fixes.
- **GUI:** verified to launch and load all 5 plugins (logs clean); deep per-menu GUI flows are
  manual smoke-test items (no headless GUI automation available).
- **Out of scope:** the headless `-nogui` + modelvalidator `log4j:WARN No appenders` (plugin
  configures log4j only on its GUI path) — benign, pre-existing, absent in normal GUI use.

---

## Appendix A — commit list (`origin/main..decycle-2`, newest first)

```
23bfeab1 docs(MIGRATING): document the gui.views.diagrams decycling relocations
8a1d1935 build(assembly): bundle the 5 plugins rebuilt against the decycled 7.5.0 API
d45022f3 refactor(gui): dissolve the selection slice + gate gui.views.diagrams at 0 cycles
920418f3 refactor(gui): move IFXWindowHost to framework + StyleInfo* into classdiagram
b22c24bd refactor(gui): break type/selection -> root via a framework main-window handle
a373b640 refactor(gui): untangle event<->types (relocate Hide actions, invert input handler)
334b536a refactor(gui): relocate DiagramView framework to a base slice
d198b1ff refactor(gui): invert AllLayoutTypes/StyleInfoBase node coupling via framework
4c375bd0 refactor(gui): route diagram views through framework.IMainWindowServices
1c447ba3 refactor(gui): peel elements off the diagram via framework interfaces
8fe4ee59 refactor(gui): merge waypoints into elements.waypoints
a2d5095e refactor(gui): extract diagrams.framework foundation slice
5db1e155 test(arch): measure the gui.views.diagrams cycle blind-spot (non-gating)
d17335a9 refactor(gui): relocate edges.GUI demo to break the edges<->util package cycle
93d26bba docs: add MIGRATING.md and correct two package-fact errors in the PR notes
d0c684e2 fix: guard MObjectState downcasts of getSelf().state() with a descriptive error
0c83e9b7 docs: add independent code review of the decycle-2 PR
0d6ea243 docs: add temporary reviewer-facing verification status (decycle-2)
2ef85848 test(arch): run all 6 ArchUnit tests under surefire 3.5.2 + vintage, assert 0 cycles
96d283e9 fix: GUI booting problems due to obsolete plugins
52ed1559 docs: remove temp files that aren't needed anymore
54f16fb2 fix: remove most of the warnings related to this PR
3056e389 docs: add diff analysis vs main with mermaid diagrams
94a857fb chore: move orphan package.html + correct doc's soil/RubyHelper paths
d5af9fcf docs: PR notes reflect genuine zero across every ArchUnit test
5c10b6b5 fix: cast MObject.state(IModelState) results in use-gui
3e0e2436 test: relocate all cross-slice tests to org.tzi.use.integration.*
ed16b200 test: relocate expression/system tests to sys to clear uml-with-tests cycle
fb49605a docs: honest verification of ArchUnit state — what runs, what doesn't, what's left
a77e6029 docs: refresh PR notes — Bug 1 Phase B+C now genuinely resolved
aded938f refactor: relocate operation-invoking expressions to sys.expr and MLinkImpl to mm.instance
1a403451 /8
57213380 refactor: extract IModelState + relocate MLinkSet/MSystemException to mm.instance
42ab578c refactor: revert slicer-collapse rename + collapse ocl→mm subpackages
52057e6d docs: finalization
a0f0c054 docs: refresh Current Metrics table — all 14 cycle tests at zero
de27efc9 fix: bug 17 + bug 1 — collapse gui.main↔views and uml mm/ocl/sys cycles (all 14 ArchUnit cycle tests pass ✅)
… (+ earlier docs/metrics commits; `git log --oneline 7e694be7..decycle-2` for the full 86)
```

## Appendix B — companion docs on the branch
- `MIGRATING.md` — authoritative verbatim `old → new` API map + sed cheat-sheets (breaking changes).
- `CODE_REVIEW_decycle-2.md` — independent code review.
- `PR_decycle-2_STATUS.md` — earlier verification status (note: its `gui.views.diagrams` "blind spot"
  caveat is **superseded** by §1.3 — that subtree is now gated at 0).
- `README_nghiabt_notes_on_this_pr/` — detailed per-bug notes.
