# Bug 7 plan — GUI launcher & test layer violations

Status: **Planned**, not yet implemented.
Location: `org.tzi.use.main.gui.{fx,swing}`, `org.tzi.use.util.test`,
`org.tzi.use.gui.{main, mainFX, views.diagrams.util}` (use-gui).

## Which ArchUnit rule is firing

The 21 violations in `failure_report_maven_layers.txt` come from
`AntLayeredArchitectureTest.core_should_not_depend_on_gui`. Its rule:

```java
noClasses().that().resideInAnyPackage(
        "org.tzi.use.analysis..", "org.tzi.use.api..", "org.tzi.use.config..",
        "org.tzi.use.gen..",      "org.tzi.use.graph..", "org.tzi.use.parser..",
        "org.tzi.use.uml..",      "org.tzi.use.main..",  "org.tzi.use.util..")
    .should().dependOnClassesThat().resideInAnyPackage("org.tzi.use.gui..")
```

The rule lumps **all of `main..` and `util..`** into "core" and forbids
any reach into `gui..`. The sibling **`MavenLayeredArchitectureTest`**
has a stricter, more correct scope (it only treats `main.runtime..`
and selected `util.*` subpackages as core, not all of `main..`/`util..`)
— under that rule none of these 21 lines would be flagged. We keep
the Ant rule as the contract this PR commits to: the violations are
real architectural smells worth fixing rather than rule-noise to be
relaxed away.

## What the 21 violations break down into

| Source class                                    | Calls into                                          | Count | Real problem? |
|---                                              |---                                                  |---    |---            |
| `main.gui.fx.JavaFXAppLauncher` (165 LOC)       | `gui.mainFX.MainWindow` (ctor + 4 setters)          | 5     | Yes — class is mis-located |
| `main.gui.swing.MainSwing` (152 LOC)            | `gui.main.MainWindow.create(…)`                     | 2     | Yes — class is mis-located |
| `util.test.DiagramUtilTest` (JUnit test)        | `gui.views.diagrams.util.Util.intersectionPoint(…)` | 14    | Yes — test mis-located     |

All three source files are misplaced in the package tree. The
launcher classes are 150+ lines of *GUI code* (calling Swing/JavaFX
APIs, manipulating `MainWindow`); they only sit under `main.gui.*`
for historical reasons. `DiagramUtilTest` tests a GUI utility but
lives under `util.test`. Moving each file to its semantically correct
package eliminates the violation.

## Fix plan

### Prong 1 — Move `DiagramUtilTest` (14/21 violations, trivial)

The test class tests `org.tzi.use.gui.views.diagrams.util.Util`. The
test should live next to the class it tests, in the same package on
the test source root.

```
git mv use-gui/src/test/java/org/tzi/use/util/test/DiagramUtilTest.java \
       use-gui/src/test/java/org/tzi/use/gui/views/diagrams/util/DiagramUtilTest.java
```

Edits:
- Change `package org.tzi.use.util.test;` → `package org.tzi.use.gui.views.diagrams.util;`
- Drop the now-intra-package import
  `import org.tzi.use.gui.views.diagrams.util.Util;`

Verification: after the move, the test class resides under
`org.tzi.use.gui..` (specifically `gui.views.diagrams.util`), which is
NOT in the rule's "that" clause — so it can freely depend on other
`gui..` classes. 14 violations gone.

This is the same shape of fix the rest of this PR has been doing
(Bugs 4/5/6/8 = move a class to fix its package).

### Prong 2 — Relocate launchers + invert the bootstrap (7/21 violations, moderate)

Both `JavaFXAppLauncher` and `MainSwing` are predominantly GUI code,
not bootstrap code. They belong under `gui..`. The catch: their
current callers (`MainJavaFX.java` and `Main.java`, both in
`main.gui..`) reference them by class literal — if we just move them,
those callers gain new `main → gui` static dependencies, swapping the
violations rather than removing them.

**The clean solution: introduce a tiny `Launcher` interface in `main`
and have the bootstrap discover impls without a compile-time class
reference.**

Concrete steps:

1. **Define the interface** at `use-gui/src/main/java/org/tzi/use/main/gui/Launcher.java`:
   ```java
   package org.tzi.use.main.gui;

   /** Bootstrap-side contract for a GUI launcher. */
   public interface Launcher {
       void launch(String[] args);
   }
   ```
   This lives in `main..` (which is allowed to be depended on by
   `gui..` — that's the natural direction).

2. **Move `MainSwing`** to `gui..`:
   - `git mv` → `use-gui/src/main/java/org/tzi/use/gui/main/SwingLauncher.java`
     (renamed to reflect the new role).
   - Change `package org.tzi.use.main.gui.swing;` → `package org.tzi.use.gui.main;`
   - `class SwingLauncher implements Launcher` (import the new
     interface from `org.tzi.use.main.gui`).
   - The intra-package import of `gui.main.MainWindow` becomes
     same-package; drop it.

3. **Move `JavaFXAppLauncher`** to `gui..`:
   - `git mv` → `use-gui/src/main/java/org/tzi/use/gui/mainFX/JavaFXLauncher.java`
   - Change package; have it extend
     `javafx.application.Application` and `implements Launcher`. The
     `launch(String[])` method delegates to
     `Application.launch(getClass(), args)` (no static reference to
     itself by class literal).
   - The intra-package import of `gui.mainFX.MainWindow` becomes
     same-package; drop it.

4. **Rewrite `Main.java`** to dispatch via the interface, using a
   string-literal class name (no compile-time dep on the impl):
   ```java
   String fqcn = useJavaFX
       ? "org.tzi.use.gui.mainFX.JavaFXLauncher"
       : "org.tzi.use.gui.main.SwingLauncher";
   Launcher launcher = (Launcher) Class.forName(fqcn)
           .getDeclaredConstructor()
           .newInstance();
   launcher.launch(cleanedArgs.toArray(new String[0]));
   ```
   `Main.java` keeps its imports of `Launcher` (in `main.gui`) and
   `USEWriter` (in `util`) — no `gui..` import, no static dep on the
   launcher classes. ArchUnit only analyses static references; the
   `Class.forName` string is opaque to it.

5. **Delete `main.gui.fx.MainJavaFX.java`** (9-line stub that just
   calls `Application.launch(JavaFXAppLauncher.class, args)`). Its
   role is absorbed into `JavaFXLauncher.launch`. Confirm no other
   caller references it (current grep showed none outside
   `JavaFXAppLauncher` itself).

6. **Empty packages**: after moves, the directories
   `main/gui/swing/` and `main/gui/fx/` will be empty. Delete them
   (`git clean` will not touch them since they're not tracked once
   empty, but rmdir is safe to confirm).

Verification after Prong 2: zero remaining edges from `main..` into
`gui..`. The `Launcher` interface introduces a `gui → main` edge from
each launcher impl, but that direction is allowed.

### Cumulative effect

| Prong | Files touched | Violations removed | Cumulative count |
|---    |---            |---                 |---               |
| Start |               |                    | 21               |
| Prong 1 | 1 test moved + 1 import drop | 14 | 7 |
| Prong 2 | 2 classes moved, 1 interface added, 1 stub deleted, `Main.java` rewritten to reflective dispatch | 7 | **0** |

## Risks / things to double-check

- **JavaFX `Application.launch` requires a `Class<? extends Application>`.**
  Solution above uses `Application.launch(getClass(), args)` from
  inside the impl — works because `JavaFXLauncher extends Application`
  itself.
- **`Class.forName` failure modes.** Wrap the lookup in a clear
  exception path that prints which launcher couldn't be loaded.
  Don't silently fall back to Swing if JavaFX is missing — the user
  explicitly asked for `-jfx`.
- **Reflection vs. AOT/GraalVM-native:** USE doesn't ship a native
  image build, so reflective construction is fine. If that changes,
  switch to `ServiceLoader` (which keeps native-image-friendly
  metadata).
- **The `MavenLayeredArchitectureTest`** (narrower rule) will already
  pass with 0 violations after Prong 1, before we even touch the
  launchers — because `main.gui..` is not in its "that" clause. Worth
  running both tests to confirm.
- **Other callers**: current grep results show only `MainJavaFX`
  references `JavaFXAppLauncher`, and only `Main` references
  `MainSwing`. Re-grep after the moves to be sure no plugin or
  reflection-string references them by the old FQN.

## ⚙ Workflow for this PR — same as Bug 6

1. **Capture before state** (the layer report from
   `use-gui/target_archunit_temp/archunit-reports/failure_report_maven_layers.txt`)
   to `docs/archunit-history/before-fix/bug-7_failure_report_maven_layers.txt`.
2. **Apply Prong 1** (move `DiagramUtilTest`). Compile + run tests.
3. **Apply Prong 2** (introduce `Launcher`, move launchers, rewrite
   `Main`, delete `MainJavaFX`). Compile + run.
4. **`mvn -pl use-core,use-gui -am clean package`** — confirm 0 test
   failures and that the layer test prints `Number of violations: 0`.
5. **Refresh `target_archunit_temp/`** for use-gui (and use-core for
   completeness):
   ```bash
   cp -r use-gui/target/archunit-reports/. use-gui/target_archunit_temp/archunit-reports/
   cp -r use-gui/target/archunit-results/. use-gui/target_archunit_temp/archunit-results/
   ```
   The fresh build should NOT regenerate
   `failure_report_maven_layers.txt` (the AntLayeredArchitectureTest
   only writes the file when `violationCount > 0` — see
   `AntLayeredArchitectureTest.java:48-60`). Delete the stale tracked
   file:
   `git rm use-gui/target_archunit_temp/archunit-reports/failure_report_maven_layers.txt`.
6. **Smoke-test the launchers manually** (the GUI is the production
   feature here — type checkers won't catch a broken reflective
   dispatch):
   ```bash
   # From repo root after a successful package:
   java -cp use-assembly/target/use-7.5.0.jar org.tzi.use.main.gui.Main
   java -cp use-assembly/target/use-7.5.0.jar org.tzi.use.main.gui.Main -jfx
   ```
   Both should open the respective window. If you can't run a UI
   from this environment, document that explicitly in the PR
   (per CLAUDE.md's "test the golden path" guidance for UI changes).
7. **Update PR notes**: mark Bug 7 RESOLVED, replace the "Fix
   direction" bullet with the actual fix description, add
   before/after mermaid blocks (same template as Bugs 4/5/6), and
   add a **⚠ Breaking API change — migration note** listing the FQN
   changes:
   ```
   org.tzi.use.main.gui.swing.MainSwing            → (removed; launch via Main / Launcher)
   org.tzi.use.main.gui.fx.JavaFXAppLauncher       → org.tzi.use.gui.mainFX.JavaFXLauncher
   org.tzi.use.main.gui.fx.MainJavaFX              → (removed; folded into JavaFXLauncher)
   org.tzi.use.util.test.DiagramUtilTest           → org.tzi.use.gui.views.diagrams.util.DiagramUtilTest
   ```
   Plus a new public interface `org.tzi.use.main.gui.Launcher`. Tag:
   `[breaking] main.gui, util.test`.
8. **Commit**: `fix: bug 7 resolved`.

## Alternative (NOT recommended) — relax the rule

The `MavenLayeredArchitectureTest` rule already excludes `main.gui..`
from "that" and would report 0 violations once Prong 1 (test move) is
done. If the team wanted to stop here, we could narrow the
`AntLayeredArchitectureTest` rule's "that" clause to match. **Reject
this path** — the launcher relocation in Prong 2 is a real
architectural improvement (correctly classifying 300 lines of GUI
code as GUI code, not bootstrap), and dropping the rule's coverage
would let future drift re-introduce the violations silently.
