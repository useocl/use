# Bug 3 plan — `org.tzi.use.runtime` internal cycles (use-gui)

Status: **Planned**, not yet implemented.
Severity: High — 43 cycles, 20 edges across 6 slices.
Location: `org.tzi.use.runtime.*` (sliced by first sub-package).

This bug is structurally different from Bugs 2 / 5 / 6 / 7 / 8 — those
were 1–2 cycles each that could be killed in a single change. Here we
have an **almost-complete graph** between six slices, so a one-shot
sweep is the wrong approach. The plan below is a multi-phase
refactor: each phase is independently buildable, independently
verifiable, and large enough to be reverted as a unit if it goes
sideways.

> 💡 **Working principle (per user direction):**
> *"Coarsen, then refine."* Don't split deeply-coupled packages into
> smaller ones yet — that multiplies edges, not reduces them. First
> establish a strict downward gravity, harden the boundary around the
> messy core, then break the highest-leverage bidirectional edge.
> Only after the slice rule is green do we slice anything else.

---

## 1. What the ArchUnit report actually shows

`use-gui/target_archunit_temp/archunit-reports/failure_report_maven_cycles_runtime.txt`
reports **43 cycles** in the `org.tzi.use.runtime` slice. The slice
rule slices by first sub-package of `runtime`, producing six nodes:

| Slice    | Contents (FQN under `org.tzi.use.runtime.…`)                                              |
|----------|-------------------------------------------------------------------------------------------|
| `root`   | `IPlugin`, `IPluginRuntime`, `IPluginDescriptor`, `IPluginClassLoader`, `MainPluginRuntime` |
| `gui`    | `gui/*` (5 SPI interfaces + `DiagramPlugin`) and `gui/impl/*` (extension-point impls)      |
| `impl`   | `impl/Plugin`, `impl/PluginDescriptor`, `impl/PluginRuntime`                              |
| `service`| `service/IPluginService`, `service/IPluginServiceDescriptor` (+ `service/impl/*`)         |
| `shell`  | `shell/*` (3 SPI interfaces) and `shell/impl/*`                                           |
| `util`   | `util/ActionRegistry`, `PluginRegistry`, `ServiceRegistry`, `ShellCmdRegistry`, `PluginClassLoader`, `PluginParser`, `IPluginParserSymbols` |
| `model`  | DTOs only. **Not** in any cycle — ignore for this bug.                                    |
| `guiFX`  | One file (`guiFX/IPluginMModelElement.java`). Not in any cycle.                           |

### 1.1 Edge frequency across the 43 cycles

(Edges sorted by how often they participate in a cycle path.)

```
 17  root  -> gui            14  root -> impl           14  gui  -> util
 11  gui   -> impl           11  service -> root        11  impl -> shell
 10  impl  -> util            9  util -> shell           8  shell -> util
  7  impl  -> gui             7  shell -> root           6  util -> root
  6  util  -> service         6  util -> impl            5  util -> gui
  5  shell -> impl            4  impl -> root            4  impl -> service
  4  gui   -> root            1  root -> service
```

20 directed edges, all but two (`impl → service`, `util → service`) participate in bidirectional pairs.

### 1.2 Bidirectional pairs (the egregious cycles)

| Pair                | Forward count | Reverse count | Net leverage |
|---------------------|---------------|---------------|--------------|
| **root ↔ gui**       | 17 (root→gui) | 4 (gui→root)  | **21** — biggest single break |
| **root ↔ impl**      | 14 (root→impl)| 4 (impl→root) | **18** |
| **gui ↔ util**       | 14 (gui→util) | 5 (util→gui)  | **19** |
| **gui ↔ impl**       | 11 (gui→impl) | 7 (impl→gui)  | 18 |
| **impl ↔ shell**     | 11 (impl→shell)| 5 (shell→impl)| 16 |
| **impl ↔ util**      | 10 (impl→util)| 6 (util→impl) | 16 |
| **shell ↔ util**     | 9, 8          |               | 17 |
| **root ↔ service**   | 1 (root→service)| 11 (service→root)| 12 |
| **shell ↔ root**     | 7, —          | (one-way)     | n/a |

The top three (root↔gui, gui↔util, root↔impl) account for the
majority of cycles. They share a single root cause:
**`org.tzi.use.runtime` (the root package) mixes two roles** —

1. It holds **SPI interfaces** (`IPlugin`, `IPluginRuntime`,
   `IPluginDescriptor`, `IPluginClassLoader`) which everything below
   needs to reference (so everything points *up* at root).
2. It holds the **orchestrator** (`MainPluginRuntime`) which wires
   `gui.*`, `impl.*`, `shell.*` together at startup (so root points
   *down* at everything).

That makes `root` both the top *and* the bottom of the gravity stack
simultaneously — a guaranteed cycle no matter what the leaves do.
Until that ambiguity is resolved, every other fix is local
firefighting.

---

## 2. Step 1 — Define the gravity (target layering)

This is documentation-only: no code change. It defines the **invariant
the slice rule will enforce after the refactor.**

### 2.1 Target layers (top → bottom, dependencies flow downward only)

```
┌─────────────────────────────────────────────────────────────────┐
│  L5  Bootstrap     ← MainPluginRuntime (orchestrator)            │
│                      *Newly extracted from `root` in Phase B.*   │
├─────────────────────────────────────────────────────────────────┤
│  L4  Host facades  ← gui/*, shell/*, service/*                   │
│                      (SPIs specific to one host: Swing UI, CLI,  │
│                      business-logic plugin)                      │
├─────────────────────────────────────────────────────────────────┤
│  L3  Infrastructure← impl/* (Plugin, PluginDescriptor,           │
│                      PluginRuntime)                              │
├─────────────────────────────────────────────────────────────────┤
│  L2  Registries    ← util/* (ActionRegistry, PluginRegistry,     │
│                      ServiceRegistry, ShellCmdRegistry,          │
│                      PluginClassLoader, PluginParser)            │
│                      *Misnamed: these are state singletons, not  │
│                      stateless utilities. Renaming is out of     │
│                      scope.*                                     │
├─────────────────────────────────────────────────────────────────┤
│  L1  SPI core      ← IPlugin, IPluginRuntime,                    │
│                      IPluginDescriptor, IPluginClassLoader       │
│                      (the four interfaces in `runtime/`)         │
│                      *Plus model/* DTOs — already clean.*        │
└─────────────────────────────────────────────────────────────────┘
```

### 2.2 Gravity rules (the invariants)

1. **L1 (SPI) imports nothing under `org.tzi.use.runtime` except
   `model/`.** All other layers may import L1.
2. **L5 (Bootstrap) may import any layer below.** Nothing below imports it.
3. **No sideways edges** between host facades (`gui ↛ shell`, `gui ↛
   service`, etc.). Host facades only see L1–L3.
4. **`util` registries may import L1 and L3.** They may *not* import
   any host facade (L4) — that's the current sin (`util → gui`,
   `util → shell`, `util → service`). Phase C will fix it via
   per-host registry interfaces in L1.

### 2.3 Why this gravity (and not e.g. "util at bottom"):

`util/*` in this codebase is **not** a leaf — every registry singleton
holds state that L4 reads back. Putting it at the bottom would
require pushing the SPI interfaces *into util*, which is worse:
util is currently 7 classes; bloating it with 4 more SPIs cements
the wrong abstraction. Instead, L1 (a tiny SPI-only package) sits
below util, and util is allowed to talk *down* to it. This matches
how Bug 8 resolved `shell ↔ shell.runtime` — a dedicated SPI
sub-package at the bottom.

---

## 3. Step 2 — Coarsen first (no slicing, no DIP yet)

Per the *coarsen-then-refine* directive: before introducing
interfaces, draw a thick line around the parts that genuinely belong
together and clean up only what crosses that line.

### 3.1 The "core chunk"

`root + impl + util` together implement the runtime kernel:
- `MainPluginRuntime` (root) wires the framework up at startup.
- `PluginRuntime` (impl) is the singleton state.
- `ActionRegistry`, `PluginRegistry`, `ServiceRegistry`,
  `ShellCmdRegistry`, `PluginClassLoader`, `PluginParser` (util) are
  the per-feature registries.

These three slices share singletons (`PluginRuntime.getInstance()`,
`ActionRegistry.getInstance()`, …) and have grown into one another
over years. Trying to make them acyclic *among themselves* without
first severing the host edges (gui/shell/service) is pure churn —
the host edges will keep re-injecting cycles through
`MainPluginRuntime` no matter what we do internally to impl ↔ util.

**Action in Phase B:** treat root + impl + util as a single logical
chunk. The boundary we will harden is **`{root, impl, util} ↔ host
facades`**, not the internal edges. We accept (for now) that
root→impl, impl→util, util→impl, etc. continue to exist; they don't
cross the boundary so they don't matter to the slice rule once the
chunk's seam to L4 is one-directional.

### 3.2 The "host-facade chunk"

`gui + shell + service` are three peer host SPIs. They currently have
zero direct edges between each other in the cycle report (verified:
no `gui → shell`, `gui → service`, `shell → service` lines appear).
They only cycle through the core chunk. So they're already
peer-clean — we just need to flip every `core → host` edge into a
`host → core` edge (host imports interfaces from core, never the
reverse).

---

## 4. Step 3 — Surgical DIP fixes in phased order

Each phase below is a self-contained PR-sized change. The phases are
ordered by **leverage per unit risk**: the earliest phase kills the
most cycles for the smallest blast radius.

### Phase A — Extract SPI from root (kills root ↔ gui, root ↔ impl, root ↔ service)

**Cycles killed:** ~31 of 43 (every cycle containing `root`).

**The move:** create `org.tzi.use.runtime.spi` and `git mv` the four
SPI interfaces into it. Move the orchestrator `MainPluginRuntime`
into a new `org.tzi.use.runtime.bootstrap` sub-package.

```
runtime/IPlugin.java               → runtime/spi/IPlugin.java
runtime/IPluginRuntime.java        → runtime/spi/IPluginRuntime.java
runtime/IPluginDescriptor.java     → runtime/spi/IPluginDescriptor.java
runtime/IPluginClassLoader.java    → runtime/spi/IPluginClassLoader.java
runtime/MainPluginRuntime.java     → runtime/bootstrap/MainPluginRuntime.java
```

After the move, `org.tzi.use.runtime.*` (the `root` slice) contains
**nothing** — the slice disappears from the cycle graph entirely
because there are no first-level `.java` files left.

Two new slices appear:
- `spi` (4 interfaces, zero outbound edges except into `model/` and
  `org.tzi.use.main.runtime` — both clean).
- `bootstrap` (1 class, all outbound; no inbound).

The slice rule re-runs with **no cycle involving the runtime root**.
Plus all `gui → root`, `impl → root`, `service → root`,
`shell → root`, `util → root` re-resolve to `→ spi` (downward
gravity, ✓), and `root → impl`, `root → gui` re-resolve to
`bootstrap → …` (top of stack, ✓).

**Files edited (import fixup, ~30 files):**
- Any file that imports `org.tzi.use.runtime.IPlugin*` updates its
  import to `org.tzi.use.runtime.spi.IPlugin*`.
- Any file that constructed or referenced `MainPluginRuntime` updates
  its import to `org.tzi.use.runtime.bootstrap.MainPluginRuntime`.
- The two known callers of `MainPluginRuntime.run(…)`:
  - `use-gui/.../main/gui/Main.java` (Swing entry)
  - `use-gui/.../main/shell/Shell.java` (CLI entry — verify with grep)

**Module-info:** if `module-info.java` exports
`org.tzi.use.runtime`, add `org.tzi.use.runtime.spi` and
`org.tzi.use.runtime.bootstrap` to the exports. Drop the old
`org.tzi.use.runtime` export if it becomes empty.

**Verification gate before moving on:**
```bash
mvn -pl use-core,use-gui -am clean package
```
The runtime slice rule should now report **≤ 12 cycles**
(everything that didn't involve `root`). If it still reports
anything involving `spi` or `bootstrap`, stop and reassess —
something didn't migrate.

---

### Phase B — Harden the {core chunk} ↔ {host facades} boundary

**Cycles killed (target):** the remaining `host → util → host` and
`host → impl → host` paths (~8 of the residual ~12).

The residual cycles after Phase A all flow through the same problem:
`util/*` registries reference *concrete* host-facade types in their
public method signatures. E.g.:

- `util/ActionRegistry.registerPluginAction(IPluginDescriptor, PluginActionModel)`
  returns `IPluginActionDescriptor` — that interface lives in `gui/`,
  pulling util → gui.
- `util/ServiceRegistry.registerService(...)` returns
  `IPluginServiceDescriptor` — pulls util → service.
- `util/ShellCmdRegistry.registerCmd(...)` returns
  `IPluginShellCmdDescriptor` — pulls util → shell.

These three types are *descriptor handles*, not really host-specific
behavior — they're just data records describing a registered plugin
contribution. The fix:

**Move the three `IPlugin*Descriptor` interfaces into `runtime.spi`**
alongside `IPluginDescriptor`. They're all variations on the same
concept and naturally belong together. After the move:

```
gui/IPluginActionDescriptor.java          → spi/IPluginActionDescriptor.java
shell/IPluginShellCmdDescriptor.java      → spi/IPluginShellCmdDescriptor.java
service/IPluginServiceDescriptor.java     → spi/IPluginServiceDescriptor.java
```

`util/*` now only ever names `spi/*` types in its public surface.
Edges become `util → spi` (downward, ✓) and the residual host facades
keep importing both `util` (downward, ✓) and `spi` (downward, ✓).

**Why not "interfaces in util"?** Two reasons:
(a) the user's gravity rule puts SPI at the bottom (L1), not L2;
(b) the existing Bug 8 / Bug 2 pattern is "all SPI under a dedicated
sub-package," so we stay consistent.

**Files edited:** every host-facade file that still imports its own
`IPlugin*Descriptor` (5–10 files) plus every util registry (~7
files). A mechanical FQN rewrite — no signature change beyond the
package move.

**Verification gate:** runtime cycle count should now be ≤ 4 (only
the host ↔ impl ↔ host triangles left).

---

### Phase C — Break the residual host ↔ impl cycles via marker interfaces

**Cycles killed:** the remaining ≤ 4.

By Phase C, what's left are concrete-class references between
`impl/PluginRuntime` and the three host facades — same shape as
Bug 8 / Bug 2 Prong A:

| Edge to break          | Fix pattern                                                  |
|------------------------|--------------------------------------------------------------|
| `gui → impl` and `impl → gui` | The cycle survives because `gui.impl.*` extension-point classes (`ActionExtensionPoint`, etc.) call `impl.PluginRuntime.getInstance()`, and `impl.PluginRuntime` calls `gui.impl.*ExtensionPoint.getInstance()`. Introduce `runtime.spi.IExtensionPoint` (or a per-host marker like Bug 8's `IShell`). `PluginRuntime` looks up extension points by interface type, never by concrete class. |
| `shell → impl` and `impl → shell` | Same shape, same fix. |
| `service → root` (residual via bootstrap) | After Phase A this is `service → spi`, already downward — drop it from the to-do. |

This phase is by far the most invasive — it touches the singleton
lookup pattern in `PluginRuntime`. Doing it last gives us a safety
net: if Phase A + B already brought the cycle count to single
digits, Phase C can be deferred or scoped to a follow-up PR with no
loss of consistency.

**Verification gate:** runtime cycle count = **0**.

---

## 5. What we explicitly do NOT do

- **Do not** rename `util/*` to `registry/*`. The user's strategy is
  *coarsen, then slice* — renaming during a cycle break adds
  cognitive churn for zero structural payoff. Track it as a
  follow-up cleanup PR after Bug 3 is closed.
- **Do not** introduce DI / Spring / Guice. The codebase is
  hand-wired singletons; one new framework dependency to break one
  cycle is a bad trade.
- **Do not** touch `runtime/guiFX/*`. The single FX file is not in
  any cycle and the FX-side host-facade story is its own bug
  (parallel to Bug 2's mainFX skip).
- **Do not** modify `runtime/model/*`. DTOs already point only at
  primitives; they're acyclic by construction.

---

## 6. Risks & things to double-check

- **Reflection.** `PluginRuntime.getExtensionPoint(String)` resolves
  extension points by string name. If any string referenced the FQN
  `org.tzi.use.runtime.gui.impl.*ExtensionPoint`, the move in
  Phase A is import-only and shouldn't break them — but the moved
  `IPlugin*Descriptor` interfaces (Phase B) may be addressed by FQN
  in plugin XML descriptors. Grep `**/*.xml`, `**/plugin.xml`-style
  configs, and `Class.forName(` call-sites for
  `"org.tzi.use.runtime"` literal substrings before Phase B.
- **Module-info exports.** Each of the four interface relocations
  may need a new `exports … to <consumer>;` clause if the original
  package was qualified-exported. Check `use-gui/src/main/java/module-info.java`
  and `use-core/src/main/java/module-info.java` for `runtime`
  exports before the moves.
- **Plugin authors (external).** Anyone implementing `IPlugin` or
  `IPluginRuntime` in a separate JAR will hit a compile error on the
  import after Phase A. This is a **breaking API change** of the
  same shape as Bug 5 (GSignature) — call it out in the PR notes:
  > ⚠ Breaking: plugin SPI interfaces moved from
  > `org.tzi.use.runtime.*` to `org.tzi.use.runtime.spi.*`. Pure
  > package rename; no signature change.
- **`MainPluginRuntime` is `public static`.** Anything reflecting on
  the FQN (e.g., a `Class.forName("org.tzi.use.runtime.MainPluginRuntime")`)
  will break in Phase A. Grep before the move.
- **Each phase must build cleanly on its own.** If Phase A leaves a
  compile error in Phase B's territory, abort and reassess — phases
  are meant to be revertable units. The verification gate after
  each phase is mandatory.

---

## 7. ⚙ Workflow — same shape as Bugs 5/6/7/8/2

For **each** of Phase A, Phase B, Phase C:

1. **Capture before-state** (first phase only):
   ```bash
   cp use-gui/target_archunit_temp/archunit-reports/failure_report_maven_cycles_runtime.txt \
      docs/archunit-history/before-fix/bug-3_failure_report_maven_cycles_runtime.txt
   ```
2. **Apply the phase's moves and edits.** Use `git mv` for all file
   renames so history is preserved.
3. **Build + test:**
   ```bash
   mvn -pl use-core,use-gui -am clean package
   ```
   Expect: 0 test failures. Cycle count strictly decreases from the
   previous phase's gate.
4. **Refresh in-repo snapshots:**
   ```bash
   cp -r use-gui/target/archunit-reports/. use-gui/target_archunit_temp/archunit-reports/
   cp -r use-gui/target/archunit-results/. use-gui/target_archunit_temp/archunit-results/
   ```
   Delete the now-empty `failure_report_maven_cycles_runtime.txt` if
   the cycle count hit 0:
   ```bash
   git rm use-gui/target_archunit_temp/archunit-reports/failure_report_maven_cycles_runtime.txt
   ```
5. **Update the PR notes**: the Bug 3 section gets a phase-by-phase
   "before / after" mermaid block under the existing
   `<!-- BEGIN MERMAID:bug-3 -->` marker. Once Phase C lands, mark
   the bug `✅ RESOLVED → 0 cycles`.
6. **Smoke-test the GUI manually** (or note inability to). Plugin
   loading is exercised by `org.tzi.use.main.gui.Main` (Swing): start
   it with the demo plugin directory, confirm the plugin loads and
   its actions appear in the menu. If no display available, say so
   explicitly in the PR — don't claim success.
7. **Commit the phase as its own commit.** Three small commits (one
   per phase) read better in history than one mega-commit and let a
   reviewer bisect if something breaks.

---

## 8. Acceptance criteria

- `MavenCyclicDependenciesGUITest.countCyclesForPackage("org.tzi.use.runtime")` returns **0**.
- No new cycle appears in `failure_report_maven_cycles_gui.txt` (whole-GUI slice).
- All existing tests (271 use-core + 18 use-gui) still pass.
- Plugin loading still works: a manual `Main`-app smoke test (or an
  explicit "couldn't test UI" note) confirms `MainPluginRuntime.run`
  still wires extension points correctly.
- PR notes' Bug 3 section is rewritten with before/after mermaid and
  the breaking-API note for the SPI relocation.
