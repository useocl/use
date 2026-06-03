# Migrating to USE 7.5.0 (the `decycle-2` API reshape)

This release removes the long-standing cyclic dependencies in `use-core` and
`use-gui` (entire-project cycle count **384 → 0**, enforced by the ArchUnit
tests in `org.tzi.use.architecture`). Decoupling required relocating **250+
public types** across packages and changing the shape of several plugin SPI
signatures.

> **These are source-incompatible changes.** Binary backwards-compatibility is
> **not** preserved; plugin authors and embedders must recompile and rewrite
> imports. The changes are otherwise **behaviour-preserving** — no semantics,
> return values, exception handling, or runtime contracts change except where
> explicitly listed below (removed methods, signature changes, visibility).
>
> A **SemVer major bump** is therefore appropriate for the next published
> artifact.

All mappings below were verified against the source tree (re-checked at the
`decycle-2` head). Sections 1–8 are verbatim `old → new` package/type moves;
sections 9–11 cover module exports, removed methods, and visibility.

---

## 0. How to migrate a plugin / embedder

1. **Bulk-rewrite the wholesale package renames** with the sed cheat-sheet below
   (these moved *entire* sub-trees, so a prefix replace is safe).
2. **Hand-fix the class-specific moves** (sections 1, 3, 8) — these moved
   individual classes out of packages that still exist, so a prefix replace
   would be wrong.
3. **Re-implement the changed SPI signatures** (section 2) and add the new
   required `IPluginRuntime.registerExtensionPoint` method.
4. **Remove calls to deleted methods** (section 10) — there are no shims.
5. Recompile against `org.tzi.use:use-gui:7.5.0` (the GUI fat jar pulls in
   `use-core`).

### sed cheat-sheet — SAFE whole-subtree prefix renames

```bash
# Run from your plugin's source root. These sub-trees moved in their entirety,
# so replacing the package prefix in every .java file is safe.
find . -name '*.java' -print0 | xargs -0 sed -i \
  -e 's#org\.tzi\.use\.uml\.ocl\.type#org.tzi.use.uml.mm.types#g' \
  -e 's#org\.tzi\.use\.uml\.ocl\.value#org.tzi.use.uml.mm.values#g' \
  -e 's#org\.tzi\.use\.uml\.ocl\.expr#org.tzi.use.uml.mm.expr#g' \
  -e 's#org\.tzi\.use\.uml\.ocl\.extension#org.tzi.use.uml.mm.extension#g' \
  -e 's#org\.tzi\.use\.gui\.views\.selection#org.tzi.use.gui.views.diagrams.selection#g' \
  -e 's#org\.tzi\.use\.util\.soil#org.tzi.use.uml.sys.soil#g' \
  -e 's#org\.tzi\.use\.util\.uml\.sorting#org.tzi.use.uml.mm.sorting#g'
```

> ⚠️ **Do NOT** prefix-rename `org.tzi.use.uml.sys.*`,
> `org.tzi.use.runtime.*`, or `org.tzi.use.analysis.coverage.*` — those packages
> still exist and only *some* of their classes moved. Use the explicit per-class
> mappings in sections 1, 3, and 6 instead.

### sed cheat-sheet — class-specific moves (the common ones for plugins)

```bash
find . -name '*.java' -print0 | xargs -0 sed -i \
  -e 's#org\.tzi\.use\.uml\.sys\.MInstanceState#org.tzi.use.uml.mm.instance.MInstanceState#g' \
  -e 's#org\.tzi\.use\.uml\.sys\.MInstance#org.tzi.use.uml.mm.instance.MInstance#g' \
  -e 's#org\.tzi\.use\.uml\.sys\.MObject#org.tzi.use.uml.mm.instance.MObject#g' \
  -e 's#org\.tzi\.use\.uml\.sys\.MLinkEnd#org.tzi.use.uml.mm.instance.MLinkEnd#g' \
  -e 's#org\.tzi\.use\.uml\.sys\.MLinkSet#org.tzi.use.uml.mm.instance.MLinkSet#g' \
  -e 's#org\.tzi\.use\.uml\.sys\.MLinkImpl#org.tzi.use.uml.mm.instance.MLinkImpl#g' \
  -e 's#org\.tzi\.use\.uml\.sys\.MLink#org.tzi.use.uml.mm.instance.MLink#g' \
  -e 's#org\.tzi\.use\.uml\.sys\.MSystemException#org.tzi.use.uml.mm.instance.MSystemException#g'
```

> ⚠️ Order matters: longer names (`MInstanceState`, `MLinkEnd`, `MLinkSet`,
> `MLinkImpl`) must be substituted **before** their prefixes (`MInstance`,
> `MLink`) so the shorter rule doesn't corrupt the longer match. The block above
> is already ordered correctly.

---

## 1. Plugin SPI (`org.tzi.use.runtime.**`)

The `org.tzi.use.runtime` root package no longer holds `.java` files; the SPI
interfaces live under `runtime.spi`. (The core plugin runtime stays under
`runtime`: `Plugin`/`PluginRuntime` in `runtime.impl`, the `Plugin*Model`
classes in `runtime.model`, registries/descriptors in `runtime.util`.)

```
-- pure package-rename (no signature change) --
org.tzi.use.runtime.IPlugin                  → org.tzi.use.runtime.spi.IPlugin
org.tzi.use.runtime.IPluginRuntime           → org.tzi.use.runtime.spi.IPluginRuntime
org.tzi.use.runtime.IPluginDescriptor        → org.tzi.use.runtime.spi.IPluginDescriptor
org.tzi.use.runtime.IPluginClassLoader       → org.tzi.use.runtime.spi.IPluginClassLoader
org.tzi.use.runtime.IPluginActionDescriptor  → org.tzi.use.runtime.spi.IPluginActionDescriptor
org.tzi.use.runtime.IPluginActionDelegate    → org.tzi.use.runtime.spi.IPluginActionDelegate
org.tzi.use.runtime.IPluginAction            → org.tzi.use.runtime.spi.IPluginAction
org.tzi.use.runtime.IPluginShellCmdDescriptor→ org.tzi.use.runtime.spi.IPluginShellCmdDescriptor
org.tzi.use.runtime.IPluginShellCmdDelegate  → org.tzi.use.runtime.spi.IPluginShellCmdDelegate
org.tzi.use.runtime.IPluginServiceDescriptor → org.tzi.use.runtime.spi.IPluginServiceDescriptor
org.tzi.use.runtime.IPluginService           → org.tzi.use.runtime.spi.IPluginService
org.tzi.use.main.runtime.IRuntime            → org.tzi.use.runtime.spi.IRuntime
org.tzi.use.main.runtime.IExtensionPoint     → org.tzi.use.runtime.spi.IExtensionPoint
org.tzi.use.main.runtime.IDescriptor         → org.tzi.use.runtime.spi.IDescriptor

-- GUI/shell plugin impls moved OUT of runtime into the slices they serve --
org.tzi.use.runtime.gui.**         → org.tzi.use.gui.plugin.**
org.tzi.use.runtime.guiFX.**       → org.tzi.use.gui.pluginFX.**
org.tzi.use.runtime.shell.**       → org.tzi.use.main.shell.plugin.**
org.tzi.use.runtime.service.**     → (slice removed; types relocated to runtime.spi / runtime.util)
org.tzi.use.runtime.bootstrap.MainPluginRuntime → org.tzi.use.gui.plugin.MainPluginRuntime

-- concrete descriptors co-located with their factories (stay within runtime) --
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

### New SPI types (consumers may implement / call)

```
org.tzi.use.gui.main.runtime.IMainWindow      (MainWindow implements)
org.tzi.use.gui.main.runtime.IModelBrowser    (ModelBrowser implements)
org.tzi.use.gui.main.runtime.IPluginActionProxy
org.tzi.use.main.shell.runtime.IShell         (Shell implements)
org.tzi.use.main.shell.runtime.IPluginShellCmdContainer  (PluginShellCmdContainer implements)
org.tzi.use.main.gui.Launcher                 (entry-point contract)
org.tzi.use.gui.views.diagrams.IFXWindowHost  (FX-side static SPI)
```

## 2. SPI signature changes (re-implement required)

```
IPluginActionExtensionPoint.createPluginActions(Session, MainWindow)
    → createPluginActions(Session, IMainWindow)
IPluginMMVisitor.modelBrowser() : ModelBrowser
    → modelBrowser() : IModelBrowser
IPluginMModelExtensionPoint.createMMPrintVisitor(PrintWriter, ModelBrowser)
    → createMMPrintVisitor(PrintWriter, IModelBrowser)
IPluginMModelExtensionPoint.createMMHTMLPrintVisitor(PrintWriter, ModelBrowser)
    → createMMHTMLPrintVisitor(PrintWriter, IModelBrowser)

IPluginAction.getSession() : Session    → getSession() : Object   (downcast at call site)
IPluginAction.getParent()  : MainWindow → getParent()  : Object   (downcast at call site)
IPluginActionDelegate.shouldBeEnabled — hasSystem() now invoked reflectively
IPluginShellCmdDelegate.performCommand(... Shell ...) → (... Object ...)
IPluginShellCmd.getShell() : Shell      → getShell() : IShell
IPluginShellExtensionPoint.createPluginCmds(Session, Shell)
    → createPluginCmds(Session, IShell)
IPluginShellExtensionPoint.createPluginCmds(...) : List<PluginShellCmdContainer>
    → : List<IPluginShellCmdContainer>

-- new SPI method on IPluginRuntime (MUST be implemented) --
+ void registerExtensionPoint(String name, IExtensionPoint ep)
```

## 3. Metamodel (`org.tzi.use.uml.mm`)

```
-- OCL → mm consolidation --
org.tzi.use.uml.ocl.type.**       → org.tzi.use.uml.mm.types.**       (20 classes)
org.tzi.use.uml.ocl.value.**      → org.tzi.use.uml.mm.values.**      (19 classes)
org.tzi.use.uml.ocl.expr.**       → org.tzi.use.uml.mm.expr.**        (68 classes incl. operations)
org.tzi.use.uml.ocl.extension.**  → org.tzi.use.uml.mm.extension.**   (3 classes)

-- instance abstractions hoisted out of sys into mm.instance (class-specific!) --
org.tzi.use.uml.sys.MInstance       → org.tzi.use.uml.mm.instance.MInstance        (interface)
org.tzi.use.uml.sys.MObject         → org.tzi.use.uml.mm.instance.MObject          (interface)
org.tzi.use.uml.sys.MLink           → org.tzi.use.uml.mm.instance.MLink            (interface)
org.tzi.use.uml.sys.MInstanceState  → org.tzi.use.uml.mm.instance.MInstanceState   (interface)
org.tzi.use.uml.sys.MLinkEnd        → org.tzi.use.uml.mm.instance.MLinkEnd
org.tzi.use.uml.sys.MLinkSet        → org.tzi.use.uml.mm.instance.MLinkSet
org.tzi.use.uml.sys.MLinkImpl       → org.tzi.use.uml.mm.instance.MLinkImpl
org.tzi.use.uml.sys.MSystemException→ org.tzi.use.uml.mm.instance.MSystemException

-- operation-invoking expression nodes pushed down to sys.expr --
org.tzi.use.uml.mm.expr.ExpInstanceConstructor → org.tzi.use.uml.sys.expr.ExpInstanceConstructor
org.tzi.use.uml.mm.expr.ExpObjOp               → org.tzi.use.uml.sys.expr.ExpObjOp

-- other moves --
org.tzi.use.uml.sys.testsuite.MTestSuite       → org.tzi.use.parser.testsuite.MTestSuite
org.tzi.use.util.soil.**                       → org.tzi.use.uml.sys.soil.**
org.tzi.use.util.rubyintegration.RubyHelper    → org.tzi.use.uml.mm.extension.RubyHelper
```

### New types in `mm.instance` / `mm`

```
+ org.tzi.use.uml.mm.instance.IModelState   (read-only query surface of a runtime snapshot; sys.MSystemState implements)
+ org.tzi.use.uml.mm.instance.IObjectState extends MInstanceState
    (adds setAttributeValue / isInState; sys.MObjectState implements)
+ org.tzi.use.uml.mm.IStatement             (marker interface; MStatement implements)
```

### Instance / mm signature changes (re-implement required for external impls)

```
MInstance.state(MSystemState)   → state(IModelState)
MInstance.exists(MSystemState)  → exists(IModelState)
MObject.state(MSystemState)     → state(IModelState)   (returns IObjectState)
MObject.exists(MSystemState)    → exists(IModelState)
MObject.getNavigableObjects(MSystemState, ...) → getNavigableObjects(IModelState, ...)
MInstanceState.getProtocolStateMachinesInstances()  REMOVED from the interface
    (kept on concrete sys.MObjectState; sys callers downcast — see note below)

MClassifier.hasStateMachineWhichHandles(MOperationCall) → hasStateMachineWhichHandles(MOperation)
MOperation.getStatement() : MStatement → : IStatement
MOperation.setStatement(MStatement)    → setStatement(IStatement)
MMPrintVisitor.getStatementVisitorString(MStatement) → getStatementVisitorString(IStatement)
MRegion.addTransition  throws MSystemException → throws MInvalidModelException
MRegion.addSubvertex   throws MSystemException → throws MInvalidModelException
```

> **Note (downcast safety):** because `getProtocolStateMachinesInstances()` is no
> longer on `MInstanceState`, the three `sys` call sites that need it downcast
> `getSelf().state(...)` to `MObjectState`. As of this release those downcasts
> are **guarded** (`instanceof` + a descriptive `IllegalStateException`) rather
> than relying on a bare `ClassCastException`. Do **not** re-add the method to
> `MInstance`/`MInstanceState`: that would re-introduce a `uml.mm → uml.sys`
> dependency edge.

## 4. Public API (`org.tzi.use.api`)

```
UseSystemApi.create(Session)          → UseSystemApiFactory.create(Session)
UseSystemApi.create(MSystem, boolean) → UseSystemApiFactory.create(MSystem, boolean)
UseSystemApi.create(MModel,  boolean) → UseSystemApiFactory.create(MModel,  boolean)
org.tzi.use.api.factory.UseSystemApiFactory  (new — exported)
org.tzi.use.api.impl.**                      (kept unexported)
org.tzi.use.uml.mm.TestModelUtil → org.tzi.use.api.TestModelUtil
```

## 5. Parser, generator, coverage, utilities

```
-- parser --
org.tzi.use.parser.ocl.ASTEnumTypeDefinition → org.tzi.use.parser.use.ASTEnumTypeDefinition
org.tzi.use.util.CompilationFailedException  → org.tzi.use.parser.soil.exceptions.CompilationFailedException
SymbolTable.cause : ASTStatement → : Object   (parser call sites downcast)

-- code generator (pure renames) --
org.tzi.use.gen.tool.GSignature            → org.tzi.use.gen.assl.statics.GSignature
org.tzi.use.gen.tool.GGeneratorArguments   → org.tzi.use.gen.assl.dynamics.GGeneratorArguments
org.tzi.use.gen.tool.statistics.GStatistic → org.tzi.use.gen.assl.dynamics.GStatistic

-- coverage (PARTIAL — only these 4 moved; CoverageData & CoverageCalculationVisitor stay) --
org.tzi.use.analysis.coverage.AbstractCoverageVisitor          → org.tzi.use.uml.analysis.coverage.AbstractCoverageVisitor
org.tzi.use.analysis.coverage.AttributeAccessInfo             → org.tzi.use.uml.analysis.coverage.AttributeAccessInfo
org.tzi.use.analysis.coverage.BasicCoverageData              → org.tzi.use.uml.analysis.coverage.BasicCoverageData
org.tzi.use.analysis.coverage.BasicExpressionCoverageCalulator → org.tzi.use.uml.analysis.coverage.BasicExpressionCoverageCalulator

-- utilities --
org.tzi.use.parser.SrcPos            → org.tzi.use.util.SrcPos
org.tzi.use.parser.SemanticException → org.tzi.use.util.SemanticException
org.tzi.use.util.uml.sorting.**      → org.tzi.use.uml.mm.sorting.**
org.tzi.use.util.input.shell.ShellReadline → org.tzi.use.main.shell.ShellReadline
```

## 6. GUI (`org.tzi.use.gui`) — the Mediator collapse

```
-- MainWindow & companions relocated into gui.views.diagrams (Bug 17) --
org.tzi.use.gui.main.MainWindow         → org.tzi.use.gui.views.diagrams.MainWindow
org.tzi.use.gui.main.ModelBrowser       → org.tzi.use.gui.views.diagrams.ModelBrowser
org.tzi.use.gui.main.ViewFrame          → org.tzi.use.gui.views.diagrams.ViewFrame
org.tzi.use.gui.main.EvalOCLDialog      → org.tzi.use.gui.views.diagrams.EvalOCLDialog
org.tzi.use.gui.main.AboutDialog        → org.tzi.use.gui.views.diagrams.AboutDialog
org.tzi.use.gui.main.CreateObjectDialog → org.tzi.use.gui.views.diagrams.CreateObjectDialog
org.tzi.use.gui.main.ModelBrowserMouseHandling
    + HighlightChangeEvent / HighlightChangeListener
        (old package: org.tzi.use.gui.views.diagrams.event) → org.tzi.use.gui.views.diagrams.**
org.tzi.use.gui.views.View (interface)  → org.tzi.use.gui.main.View

-- selection sub-package collapsed into diagrams (Bug 2b) --
org.tzi.use.gui.views.selection.**      → org.tzi.use.gui.views.diagrams.selection.**

-- launcher relocation (Bug 7) --
org.tzi.use.main.gui.swing.MainSwing       → org.tzi.use.gui.main.SwingLauncher  (implements Launcher)
org.tzi.use.main.gui.fx.JavaFXAppLauncher  → org.tzi.use.gui.mainFX.JavaFXLauncher (implements Launcher)
org.tzi.use.main.gui.fx.MainJavaFX         → REMOVED (folded into JavaFXLauncher)

-- sort-strategy holders moved to util (Bug 12) --
org.tzi.use.gui.main.ModelBrowserSorting   → org.tzi.use.gui.util.ModelBrowserSorting
org.tzi.use.gui.mainFX.ModelBrowserSorting → org.tzi.use.gui.utilFX.ModelBrowserSorting

-- back-edge cleanups (Bug 14) --
org.tzi.use.gui.views.diagrams.Selectable  → org.tzi.use.gui.util.Selectable
PersistHelper.allNodes  : Map<String, PlaceableNode> → Map<String, Object>
PersistHelper.setAllNodes(Map<String, PlaceableNode>) → setAllNodes(Map<String, ?>)
```

## 7. Module exports (`module-info.java`)

`use.core` and `use.gui` updated their `exports`/`opens` clauses to the new
package shape. Consumers reading modules reflectively must refresh references.

```
-- use.core newly exported --
+ exports org.tzi.use.uml.mm.types
+ exports org.tzi.use.uml.mm.values
+ exports org.tzi.use.uml.mm.expr
+ exports org.tzi.use.uml.mm.expr.operations
+ exports org.tzi.use.uml.mm.extension
+ exports org.tzi.use.uml.mm.instance        (MInstance/MObject/MLink + IModelState/IObjectState)
+ exports org.tzi.use.uml.sys.expr           (ExpInstanceConstructor / ExpObjOp)

-- use.core renamed (slicer-rename revert: uml.mm.sys.* → uml.sys.*) --
  exports org.tzi.use.uml.sys[.events[.tags] | .soil[.exceptions] | .statemachines | .ppcHandling | .testsuite]

-- still in effect from earlier PRs --
- org.tzi.use.runtime          no longer exported as a root package (SPI exported from runtime.spi)
- org.tzi.use.main.runtime     no longer exported from use-core (moved to runtime.spi in use-gui)
- org.tzi.use.main.gui.fx      no longer exported (package empty)
+ org.tzi.use.gen.assl.dynamics newly exported (relocated GGeneratorArguments)
+ org.tzi.use.api.factory       newly exported (api.impl stays unexported)
  the `gui.views.selection → com.google.common` qualified export now targets gui.views.diagrams.selection
```

## 8. Removed methods (no shim — callers must be rewritten)

```
UseSystemApi.create(Session | MSystem,boolean | MModel,boolean)  → use UseSystemApiFactory.create(...)
MProtocolStateMachine.createInstance(MObject)                    → REMOVED (inlined into MObjectState)
MSystem.loadInvariants(...)                                      → REMOVED (logic moved into Shell)
MEvent.buildEnvironment(...)                                     → REMOVED (inlined into its single parser caller)
VarDeclList.addVariablesToSymtable(...)                          → REMOVED (inlined into 3 parser callers)
MInstanceState.getProtocolStateMachinesInstances()              → REMOVED from interface (use concrete MObjectState)
```

## 9. Visibility widening (now public — informational)

A number of constructors/methods were widened from package-private/protected to
`public` so relocated and cross-slice code (and the relocated integration tests)
can reach them. Most external callers are unaffected; notable ones:

```
sys.MLinkImpl (class + ctor)                                        → public
sys.MSystemState.evaluateDeriveExpression(MObject[], MAssociationEnd) → public
mm.instance.MLinkSet (ctors + add/remove/contains/select/removeAll/hasLink) → public
mm.MAssociationImpl (class + ctor)                                  → public
mm.types.{Boolean,Integer,Real,String}Type ctor                    → public
mm.types.{Enum,Collection,Set,Bag,Sequence,OrderedSet,Tuple}Type ctor → public
gui.{About,CreateObject,EvalOCL}Dialog                             → public
parser.shell.ShellCommandCompiler.constructAST                     → public
```

## 10. Suggested release-note tags

| Area | Tag |
|---|---|
| Plugin SPI | `[breaking] runtime-spi`, `[breaking] gui.main.runtime`, `[breaking] shell-runtime` |
| Public API | `[breaking] api` |
| Metamodel | `[breaking] uml.mm` |
| Parser / generator / analysis / util | `[breaking] parser`, `[breaking] gen`, `[breaking] analysis`, `[breaking] util` |
| GUI | `[breaking] gui.views`, `[breaking] main.gui` |

---

*Source of truth for these mappings: the per-bug breaking-change catalog in
`README_nghiabt_notes_on_this_pr/nghiabt_notes_on_this_pr.md` (§"Breaking API
Changes — Version-Bump Notes"), re-verified against the `decycle-2` tree.*
