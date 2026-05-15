# Bug 6 plan — `parser.ocl ↔ parser.use / parser.soil` cycles

Status: **Planned**, not yet implemented.
Location: `org.tzi.use.parser.{ocl, use, soil.ast}` (use-core).

## What the ArchUnit report actually shows

Two cycles are reported by the `org.tzi.use.parser` slice rule:

1. `ocl → use → ocl`
2. `ocl → use → soil → ocl`

**Both cycles share the same single `ocl → use` edge** — it is the only
import from `parser.ocl` into `parser.use` in the codebase:

```
parser.ocl.ASTEnumTypeDefinition  extends  parser.use.ASTClassifier
```

The other directions (`use → ocl`, `use → soil`, `soil → ocl`) have many
edges and are the natural data flow of the parser AST (USE/SOIL AST
nodes reference OCL types and expressions). Trying to remove edges in
those directions would mean moving most of the parser around.

**Therefore: remove the single `ocl → use` edge and both cycles go to
zero in one move.**

## Fix: move `ASTEnumTypeDefinition` from `parser.ocl` to `parser.use`

Rationale:
- Enum type definitions are declared at the model level (in `.use`
  files via `enum { ... }`), which is the USE-language grammar — not
  the OCL expression grammar. The class is currently in `parser.ocl`
  for historical reasons only.
- The class already extends `parser.use.ASTClassifier`, so moving it
  *removes* a cross-package import rather than adding one.
- Blast radius is tiny:
  - 1 file moves (`ASTEnumTypeDefinition.java`).
  - 1 external Java import (`parser.use.ASTModel`) — same target
    package after the move, so the import line is *deleted*, not
    rewritten.
  - Generated `USEParser.java` references it via
    `import org.tzi.use.parser.ocl.*;` in the grammar `@header`. That
    import will still resolve to other ocl AST classes; the
    `ASTEnumTypeDefinition` reference will resolve via same-package
    lookup once the file is in `parser.use`. **No grammar change
    needed.**
  - No `module-info` change needed: both `parser.ocl` and
    `parser.use` are already exported.

### Concrete code-fix steps

1. `git mv use-core/src/main/java/org/tzi/use/parser/ocl/ASTEnumTypeDefinition.java use-core/src/main/java/org/tzi/use/parser/use/ASTEnumTypeDefinition.java`
2. In the moved file:
   - Change `package org.tzi.use.parser.ocl;` → `package org.tzi.use.parser.use;`
   - Drop `import org.tzi.use.parser.use.ASTClassifier;` (intra-package now).
3. In `parser/use/ASTModel.java`: drop the now-unused
   `import org.tzi.use.parser.ocl.ASTEnumTypeDefinition;`.
4. Sanity-grep for any straggler FQN references:
   `grep -rn 'parser\.ocl\.ASTEnumTypeDefinition' use-core/src use-gui/src`
   — expect zero matches outside the moved file.

### Expected ArchUnit outcome

After the move:
- `parser.ocl → parser.use` edges: **0** (down from 1).
- `parser` slice cycle count: **2 → 0**.
- All `use → ocl` and `soil → ocl` edges remain and are now part of a
  clean DAG: `use → {ocl, soil}`, `soil → ocl`.

## ⚙ Workflow for this PR — re-record after the fix

This is the workflow this PR has been using for every cycle bug
(matches what was done for Bugs 4, 5, 8). **Do not skip the archunit
snapshot regeneration steps — the in-repo `target_archunit_temp/`
directories are the PR's checked-in evidence of cycle state and the
reviewer reads them as part of the diff.**

1. **Capture the "before" state** (only if not already captured):
   ```bash
   cp use-core/target_archunit_temp/archunit-reports/failure_report_maven_cycles_parser.txt \
      docs/archunit-history/before-fix/bug-6_failure_report_maven_cycles_parser.txt
   ```
   This matches the existing
   `docs/archunit-history/before-fix/bug-4_…` and `bug-8_…` files.

2. **Apply the code fix** (the `git mv` + import edits above).

3. **Wipe stale build output and rebuild** so ArchUnit reports
   regenerate from the moved class:
   ```bash
   mvn -pl use-core,use-gui -am clean package -DskipTests=false
   ```
   (Or `mvn clean package` from the repo root if working on all
   modules. ArchUnit slice tests run during the `test` phase, so a
   `package` covers it.)

4. **Refresh the checked-in archunit snapshots** by copying the freshly
   generated outputs over:
   ```bash
   cp -r use-core/target/archunit-reports/.  use-core/target_archunit_temp/archunit-reports/
   cp -r use-core/target/archunit-results/.  use-core/target_archunit_temp/archunit-results/ 2>/dev/null || true
   cp -r use-gui/target/archunit-reports/.   use-gui/target_archunit_temp/archunit-reports/
   cp -r use-gui/target/archunit-results/.   use-gui/target_archunit_temp/archunit-results/
   ```
   Notes:
   - The `failure_report_maven_cycles_parser.txt` should now be
     **absent** under `target/archunit-reports/` (the test deletes
     stale reports when cycle count is 0, per the post-Copilot fix).
     If the file still exists in `target_archunit_temp/` after the
     copy, delete it explicitly:
     `git rm use-core/target_archunit_temp/archunit-reports/failure_report_maven_cycles_parser.txt`.
   - The `entire_project` failure report in
     `use-gui/target_archunit_temp/archunit-reports/` will change
     because the top-level slice cycle inventory shifts when
     intermediate edges move. Expect a large diff there; that's normal
     (see Bug 5 commit `4fe9153a` for the same pattern).

5. **Update PR notes**: edit
   `README_nghiabt_notes_on_this_pr/nghiabt_notes_on_this_pr.md` Bug 6
   section to:
   - Change heading to `## Bug 6: … — ✅ RESOLVED`.
   - Update severity line to
     `~~Low — 2 cycles~~ → **0 cycles**`.
   - Replace "Fix direction" bullet with the actual fix description.
   - Add Before/After mermaid blocks (same pattern as Bug 4/5).
   - Add a **⚠ Breaking API change — migration note** for the
     `parser.ocl.ASTEnumTypeDefinition` → `parser.use.ASTEnumTypeDefinition`
     class move (external callers that use the FQN will need an
     import update; suggested release-note tag: `[breaking] parser`).

6. **Commit** with the conventional message format used by previous
   bug fixes in this PR:
   ```
   fix: bug 6 resolved
   ```
   Stage: code changes, module-info (if any), notes file, archived
   before-fix report, refreshed `target_archunit_temp/` files.

7. **Verify the cycle count once more** before pushing, using the
   ad-hoc probe (because the use-core JUnit-5 surefire plugin isn't
   wired up — see Bug 5 notes):
   ```bash
   # From repo root, after the build in step 3:
   javac -cp "$(cat /tmp/cp.txt):use-core/target/classes" -d /tmp /tmp/CycleProbe.java
   java  -cp "/tmp:$(cat /tmp/cp.txt):use-core/target/classes" CycleProbe use-core/target/classes
   # Expect: "Cycles in org.tzi.use.parser: 0"
   ```
   (The probe class definition is in the Bug 5 session log; same
   shape as `CycleProbe.java` used for `org.tzi.use.gen`, just change
   the package constant to `org.tzi.use.parser`.)

## Risks / things to double-check

- `USEParser.java` is a generated file under `use-core/target/generated-sources/`.
  After `mvn clean`, it is regenerated from `USE.g` during the
  `generate-sources` phase. Confirm the regenerated file compiles
  cleanly with `ASTEnumTypeDefinition` resolved from `parser.use`
  rather than `parser.ocl`. If for some reason ANTLR resolves the
  `parser.ocl.*` wildcard import preferentially, the fix is to add
  `import org.tzi.use.parser.use.ASTEnumTypeDefinition;` to the
  `@header` block of `USE.g` — but I do not expect this to be needed
  because the generated parser class itself sits in `parser.use`, and
  same-package lookup wins over wildcard imports in Java.
- The `parser` slice cycle test is in `MavenCyclicDependenciesCoreTest`
  (JUnit 5). The convenience workflow above runs the test via `mvn
  package`; the GUI test (JUnit 4) does not have a `parser` slice, so
  watch for the report from the use-core build, not the use-gui one.
