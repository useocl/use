# Bug 1 plan — `uml.mm` ↔ `uml.ocl` ↔ `uml.sys` triangle

Status: **Planned**, partially executed.
Severity: Critical — 5 cycles in `org.tzi.use.uml` slice + 34 cycles
in the whole-core slice (most of which propagate *through* this
triangle).
Location: `org.tzi.use.uml.{mm, ocl, sys}` (use-core).

This bug dwarfs every other resolved bug in this PR (8, 7, 6, 5, 4,
3, 2). The runtime-package fix (Bug 3) took three phases to break
**20 edges across 6 slices, 43 cycles**. The `uml` triangle is
**~1100 raw edges across 3 slices, 5 cycles** — so the cycle count
is small but the bidirectional edge density is enormous and every
edge is on the hot path of the model, OCL evaluator, and runtime.

Per the working principle proven by Bug 3 — *"coarsen, then
refine"* — this plan does **not** try to slice mm/ocl/sys into
smaller pieces. Instead it identifies the natural gravity (sys → ocl
→ mm), then surgically breaks the smallest set of back-edges that
make that gravity true.

> 💡 **Working principle:** *break the cheapest back-edge first.*
> Each phase below kills the back-edge whose volume × design risk is
> minimal, and gates with `mvn test` before moving on. If a phase
> ever introduces a build break it can't fix in one sitting, revert
> the phase as a unit and re-plan — phases are sized to be revertible.

---

## 1. What the ArchUnit report actually shows

`use-core/target_archunit_temp/archunit-reports/failure_report_maven_cycles_uml.txt`
slices `org.tzi.use.uml` by first sub-package and reports
**5 cycles**:

```
1. mm  → ocl → mm
2. mm  → ocl → sys → mm
3. mm  → sys → mm
4. mm  → sys → ocl → mm
5. ocl → sys → ocl
```

### 1.1 Directed import counts (measured by `grep import`)

| Edge          | Imports | Notes                                                |
|---------------|--------:|-------------------------------------------------------|
| `mm → ocl`    |      47 | model classes reference OCL `Type` / `Expression`     |
| `ocl → mm`    |      26 | ocl expressions reference `MClass`, `MAttribute`, …   |
| `mm → sys`    |      11 | small — `MOperationCall`, `MStatement`, statemachines |
| `sys → mm`    |      81 | natural — runtime instances point at model classes    |
| `ocl → sys`   |      35 | the offender — value & eval types name `M*` runtime   |
| `sys → ocl`   |     155 | natural — runtime evaluates OCL expressions           |

Three of these six are **natural** in any sane layering — `sys → mm`,
`sys → ocl`, `ocl → mm`. The other three (`mm → ocl`, `mm → sys`,
`ocl → sys`) are the **back-edges** to break.

### 1.2 Target gravity

```
┌───────────────────────────────────────────────────────────────┐
│  L3  sys  (runtime)  ← MObject, MLink, MSystem, MSystemState   │
├───────────────────────────────────────────────────────────────┤
│  L2  ocl  (language) ← Type, Expression, Value, eval contexts  │
├───────────────────────────────────────────────────────────────┤
│  L1  mm   (model)    ← MClass, MAssociation, MOperation, …     │
└───────────────────────────────────────────────────────────────┘
```

All edges must flow downward: L3 → L2, L3 → L1, L2 → L1. No upward
edges. The README's "Fix direction" called out the exact same thing:

> Value types should not hold concrete runtime references.

The three back-edges that violate gravity:

| Back-edge   | Imports | Strategy                                              |
|-------------|--------:|-------------------------------------------------------|
| `mm → sys`  |  **11** | smallest — start here (Phase A)                       |
| `ocl → sys` |  **35** | targeted — mostly value/eval (Phase B)                |
| `mm → ocl`  |  **47** | last & largest — Type/Expression are foundational     |

---

## 2. The 11 `mm → sys` imports (Phase A)

```
mm/MClassifier.java                                  → sys.MOperationCall  [param: hasStateMachineWhichHandles]
mm/MClass.java                                       → sys.MOperationCall
mm/MClassifierImpl.java                              → sys.MOperationCall
mm/MClassImpl.java                                   → sys.MOperationCall
mm/MAssociationClassImpl.java                        → sys.MOperationCall
mm/statemachines/MProtocolStateMachine.java          → sys.MObject
mm/statemachines/MProtocolStateMachine.java          → sys.statemachines.MProtocolStateMachineInstance
mm/statemachines/MRegion.java                        → sys.MSystemException
mm/statemachines/TransitionListener.java             → sys.events.TransitionEvent
mm/MMPrintVisitor.java                               → sys.soil.MStatement   [for MOperation.getStatement()]
mm/MOperation.java                                   → sys.soil.MStatement   [field + getter + setter]
```

### 2.1 Pattern: `sys.MOperationCall` parameter in `MClassifier.hasStateMachineWhichHandles`

`MClassifier`, `MClass`, `MClassImpl`, `MClassifierImpl`,
`MAssociationClassImpl` all declare or implement:

```java
boolean hasStateMachineWhichHandles(MOperationCall operationCall);
```

`MOperationCall` is a sys-level runtime object (an actual call
in-flight on `MSystem`). It carries an `MOperation` reference. The
method only needs the `MOperation` (and possibly the runtime args)
to determine which protocol state machines match.

**Fix:** extract a minimal mm-level interface
`org.tzi.use.uml.mm.IOperationCallInfo` (or similar) that exposes
just the data the model layer needs from a call (the operation, the
parameter values). `MOperationCall` `implements IOperationCallInfo`.
The method signature changes to
`hasStateMachineWhichHandles(IOperationCallInfo)` and the runtime
caller passes its `MOperationCall` directly (it implements the
interface, so no cast needed).

Alternative: relocate `MOperationCall` to `mm` if its dependencies
allow (almost certainly not — `MOperationCall` references
`MSystemState`, which would force a cascade).

### 2.2 Pattern: `MStatement` field on `MOperation`

`MOperation` holds a `private MStatement fStatement` (the SOIL body
of the operation). `MStatement` lives in `sys.soil`.

**Fix:** extract `IStatement` marker interface in `mm` (or in a new
`uml.shared`). `MStatement` implements it. `MOperation.fStatement`
becomes `IStatement`. Callers either cast (where they need SOIL
specifics) or interact through the interface.

This is the same pattern as Bug 8 (`shell` introduced `IShell` as a
marker) and Bug 2 Prong A (`gui.main.runtime` introduced
`IMainWindow` / `IModelBrowser`).

### 2.3 Pattern: statemachine transition listener references `sys.events.TransitionEvent`

`mm.statemachines.TransitionListener` is a 1-method interface used
by the runtime to publish transition events. It currently lives in
`mm` but takes a `sys.events.TransitionEvent` parameter. Two
options:

- **Option 2.3a (smaller):** move `TransitionListener` into
  `sys.events` (where `TransitionEvent` lives) — the model is not
  using it; the runtime is.
- **Option 2.3b (cleaner long-term):** extract a `mm`-level
  `ITransitionInfo` interface; `TransitionEvent` implements it.
  Listener method takes the interface.

Pick 2.3a (move the listener); it's a clean one-file relocation and
no caller cares which package the interface lives in.

### 2.4 Pattern: `MRegion` throws `sys.MSystemException`

`MRegion.applyTrigger` declares `throws MSystemException`.
`MSystemException` is a generic runtime exception type.

**Fix:** introduce `mm.MModelException` (or reuse existing
`MInvalidModelException`) and have `MSystemException` extend it.
`MRegion.applyTrigger` declares the mm-level exception; runtime
callers continue to throw `MSystemException` (a subtype) without
change.

### 2.5 Pattern: `MProtocolStateMachine` references runtime instance + `MObject`

`MProtocolStateMachine` has methods that look up a runtime instance:
`getStateMachineInstance(MObject)` returns
`MProtocolStateMachineInstance`.

**Fix:** these methods live on the wrong side of the boundary.
Move them out of `MProtocolStateMachine` into a runtime-side
service class (e.g.,
`sys.statemachines.MProtocolStateMachineRuntime`) that maintains the
instance map and looks up by `(MProtocolStateMachine, MObject)`.

### 2.6 Phase A verification gate

After Phase A:

- `mm → sys` import count: **0**.
- Cycles involving `mm → sys` edge gone: cycles **3** and **4**
  (`mm → sys → mm`, `mm → sys → ocl → mm`) should vanish.
- Expected remaining cycles in `uml`: **3** (`mm → ocl → mm`,
  `mm → ocl → sys → mm`, `ocl → sys → ocl`).

---

## 3. The 35 `ocl → sys` imports (Phase B)

These cluster in three subpackages of ocl:

### 3.1 `ocl.value` — value types holding runtime references

```
ocl/value/ObjectValue.java        → sys.MObject       [fInstance field]
ocl/value/LinkValue.java          → sys.MLink         [link field]
ocl/value/InstanceValue.java      → sys.MInstance     [fInstance field]
ocl/value/DataTypeValueValue.java → sys.MInstance     [param]
ocl/value/VarBindings.java        → sys.MSystemState  [param]
```

This is the **canonical violation the README calls out**:
> `uml.ocl.value` types … hold direct references to `uml.sys`
> runtime objects (`MObject`, `MLink`, `MInstance`).

**Fix:** introduce abstractions in `mm` (or `ocl.value`) that
runtime types implement:

- `mm.IObject` ← `sys.MObject implements IObject`.
- `mm.ILink`   ← `sys.MLink   implements ILink`.
- `mm.IInstance` ← `sys.MInstance implements IInstance`.

`ObjectValue`, `LinkValue`, `InstanceValue` hold the interface type.
The runtime continues to pass its concrete `MObject` to the value
constructor; values that need runtime-specific behavior either go
through the interface API or downcast at the few boundary points
where they genuinely need the runtime type.

`MSystemState` reference in `VarBindings` is the trickier one — it's
passed in for path expression evaluation. Extract
`ocl.value.IEvalState` interface; `MSystemState implements
IEvalState`. The `VarBindings` constructor takes the interface.

### 3.2 `ocl.expr` eval-context types

```
ocl/expr/EvalContext.java          → sys.MSystemState
ocl/expr/SimpleEvalContext.java    → sys.MSystemState
ocl/expr/DetailedEvalContext.java  → sys.MSystemState
ocl/expr/EvaluatorCallable.java    → sys.MSystemState
ocl/expr/ThreadedEvaluator.java    → sys.MSystemState
ocl/expr/ExpObjRef.java            → sys.MObject
```

Same fix as 3.1 — these all use `IEvalState` (for system state) or
`IObject` (for object refs).

### 3.3 `ocl.type` — `MessageType` references operation/signal

```
ocl/type/MessageType.java → uml.mm.MOperation,
                            uml.mm.commonbehavior.communications.MSignal
```

(Wait — this is `ocl → mm`, not `ocl → sys`. It belongs in §4.
Listed here because the original grep mis-attributed it; corrected.)

### 3.4 Phase B verification gate

After Phase B:

- `ocl → sys` import count: **0**.
- Cycles involving `ocl → sys`: cycle **5** (`ocl → sys → ocl`) and
  cycle **2** (`mm → ocl → sys → mm` — its `ocl → sys` segment) gone.
- Expected remaining: **1 cycle** (`mm → ocl → mm`).

---

## 4. The 47 `mm → ocl` imports (Phase C)

This is the structural heart of the bug. mm needs `Type` and
`Expression` because every model element carries semantic info:
- `MAttribute(name, Type, initExpression)`
- `MOperation(name, VarDeclList, returnType, body)`
- `MClassInvariant(body : Expression)`
- `MPrePostCondition(condition : Expression)`
- `MAssociationEnd(deriveExpression : Expression, qualifier : VarDecl)`

Two viable strategies:

### 4.1 Strategy 4a — Move `Type` and `Expression` core types into `mm` (or a shared sublayer)

The simplest decoupling is: **promote `Type` to L1 (mm)**. `Type` is
a value-type hierarchy that the model genuinely owns (a class's
attribute *has a type* — that's a model fact, not an OCL fact).
Once `Type` is in mm:

- `mm → ocl.type.Type` import goes away (Type is now in mm).
- `ocl.expr.Expression` still lives in ocl. mm needs to reference
  Expression in invariants/operations. Two sub-options:
  - **4a-i:** also move `Expression` to mm — but `Expression` is
    the OCL AST, so this drags the entire `ocl.expr` package into
    mm. Bad.
  - **4a-ii:** extract a `mm.IExpression` marker interface.
    `Expression implements IExpression`. mm holds `IExpression`.

This is invasive — 50+ source files import `org.tzi.use.uml.ocl.type.Type`.

### 4.2 Strategy 4b — Extract `mm.ITypedElement` / `mm.IConstraint` interfaces

Keep `Type` and `Expression` where they are; introduce mm-level
interfaces that capture only what the model layer needs:

- `mm.ITyped` — has-a-type marker. mm fields are `ITyped`; concrete
  types from ocl implement it.
- `mm.IConstraint` — body marker. Invariant/precondition bodies are
  `IConstraint`; `Expression` implements it.

This is much less code movement (~10 new interfaces) but pushes the
"casts at boundary" pattern further. Risk: every consumer of
`MAttribute.getType()` now gets `ITyped` and has to cast to `Type`
to use type-system operations.

### 4.3 Recommendation for Phase C

Pick **4a** (move Type to mm). Reasons:

1. `Type` is conceptually mm-owned. The fix matches the design.
2. The reverse direction (ocl → mm) is *already natural* — ocl
   imports `MClass`, `MAttribute`, etc. So putting Type in mm
   doesn't create new cycles.
3. Most "type system" logic on `Type` is operating on a Type
   instance; the file location doesn't change behavior. The
   package rename is mechanical.

Move plan:
```
ocl/type/Type.java               → mm/types/Type.java
ocl/type/TypeFactory.java        → mm/types/TypeFactory.java
ocl/type/Type$VoidHandling.java  → mm/types/Type$VoidHandling.java
ocl/type/CollectionType.java     → mm/types/CollectionType.java
... (~15 type classes total)
```

`ocl.type.EnumType` extends `mm.MClassifierImpl` — this back-edge
disappears when `Type` moves up.

For `Expression`: leave it in `ocl.expr` for now and use the marker
interface `mm.IConstraint` (the much smaller subset of mm's
imports). This costs ~10 new interfaces and ~50 callsite casts but
avoids moving the entire OCL evaluator into mm.

### 4.4 Phase C verification gate

After Phase C:

- `mm → ocl` import count: **0**.
- Cycles in `uml` slice: **0**.
- Cycles in `core` slice (whole, sliced by first sub-package): drops
  by all the cycles that flow through the uml triangle (target:
  reduce 34 → ~10).

---

## 5. What we explicitly do NOT do

- **Do not** touch `uml.mm.statemachines` deeply. It's a sub-slice
  of mm and its current state-machine-vs-runtime-instance split is
  reasonable. Only the listener-relocation (§2.3) and the
  protocol-state-machine instance-lookup move (§2.5) are in scope.
- **Do not** rename packages. The slice rule keys on first
  sub-package of `uml`; renaming `ocl.type` → `mm.types` is a
  *move*, not a rename of the parent slice.
- **Do not** introduce DI / Guice / factory frameworks. The Bug 3
  fix kept everything hand-wired; same principle here.
- **Do not** fix the `core` whole-slice cycles (analysis ↔ uml,
  config ↔ util, gen ↔ parser, …) in this bug. Those are separate
  Bugs 9–17 (see [[bug-1-followups]] / the new entries in PR notes).
  Many will collapse for free when Bug 1 lands.

---

## 6. Risks & things to double-check

- **`Type` is widely used.** Moving `Type` and its subclasses
  invalidates ~50+ external file imports. Mechanical FQN rewrite;
  no signature change. Plugin authors will hit compile errors:
  document as breaking API change.
- **`module-info.java`** currently exports `org.tzi.use.uml.ocl.type`.
  When `Type` moves to `mm.types`, add `exports org.tzi.use.uml.mm.types`
  and drop the type re-export (or keep both for one release).
- **Generated grammar code.** `parser.use.USEParser` (generated from
  `USE.g`) imports types. Re-run grammar generation or rewrite the
  generated file's imports — check `USE.g` `header { … }` block for
  hard-coded imports.
- **`MOperation.fStatement` is used by the SOIL evaluator on the
  hot path.** Phase A §2.2 introduces an interface around it.
  Profile if there's any sign of slowdown after the change.
- **Tests that grep for FQNs.** The codebase has a few tests that
  match against full type names in error messages. Search for
  `"org.tzi.use.uml.ocl.type"` literal substrings before moving.

---

## 7. ⚙ Workflow — same shape as Bugs 3 / 5 / 6 / 7 / 8 / 2

For **each** of Phase A, Phase B, Phase C:

1. **Capture before-state** (Phase A only):
   ```bash
   cp use-core/target_archunit_temp/archunit-reports/failure_report_maven_cycles_uml.txt \
      docs/archunit-history/before-fix/bug-1_failure_report_maven_cycles_uml.txt
   ```
2. **Apply the phase's moves and edits.** Use `git mv` for all file
   renames so history is preserved.
3. **Build + test:**
   ```bash
   mvn -pl use-core,use-gui -am clean test
   ```
   Expect: 0 test failures. Cycle count strictly decreases from the
   previous phase's gate.
4. **Refresh in-repo snapshots:**
   ```bash
   cp -r use-core/target/archunit-reports/. use-core/target_archunit_temp/archunit-reports/
   cp -r use-core/target/archunit-results/. use-core/target_archunit_temp/archunit-results/
   ```
5. **Update PR notes**: append `### After Phase X` mermaid block to
   the existing `Bug 1` section in `nghiabt_notes_on_this_pr.md`.
6. **Commit the phase as its own commit** with a tag in the message:
   `fix: bug 1 Phase A — extract mm → sys edges` etc.

---

## 8. Acceptance criteria

- `MavenCyclicDependenciesCoreTest.count_cycles_in_uml_package`
  reports **0** cycles in `org.tzi.use.uml`.
- The core whole-slice cycle count drops by all 4–5 cycles that pass
  through the uml triangle (`analysis → uml → analysis`,
  `analysis → uml → … → analysis`, `gen → uml → gen`,
  `parser → uml → parser`, `config → util → uml → config`, …).
- All existing tests (271 use-core + 18 use-gui) still pass.
- Before-fix report archived at
  `docs/archunit-history/before-fix/bug-1_failure_report_maven_cycles_uml.txt`.
- PR notes' Bug 1 section is rewritten with before/after mermaid and
  the breaking-API note for the SPI relocation.
