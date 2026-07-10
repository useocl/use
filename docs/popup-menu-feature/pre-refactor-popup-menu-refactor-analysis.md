> **Pre-refactor documentation** — describes the popup menu code as it was *before* the PopupMenuBuilder refactor.

# Popup Menu – Refactor Analysis

This document covers the three topics identified as prerequisites for planning a popup menu refactor: the submenu factory classes, the positional coupling mechanism that ties subclasses to the base menu's internal structure, and a cross-diagram item inventory.

---

## 1. Submenu Factory Classes: `ClassSelection` and `ObjectSelection`

Each diagram that calls `super.unionOfPopUpMenu()` also holds a private `fSelection` field that delegates the construction of all show/hide/crop submenus to a dedicated factory class. There is **no shared interface** between the two factories.

```
ClassDiagram          →  ClassSelection        (package: classselection)
NewObjectDiagram      →  ObjectSelection       (package: objectselection)
CommunicationDiagram  →  ObjectSelection       (package: objectselection)
```

### 1.1 Coupling model

Both classes hold a direct back-reference to the diagram instance. The coupling is tighter than it first appears:

| Factory | Declared diagram type | Actual constraint |
|---|---|---|
| `ClassSelection` | `ClassDiagram` | Hard-coded; cannot be used with any other diagram |
| `ObjectSelection` | `DiagramViewWithObjectNode` | Declared type is the abstract parent, but three methods (`getSubMenuHideLinks`, `getSubMenuShowLinks`, `getSubMenuLinksByKind`) **cast the field to `NewObjectDiagram` at runtime**. These methods fail at runtime if called from `CommunicationDiagram`. |

`ObjectSelection` also receives a `MSystem` reference at construction time, which it uses to query the model for all objects and links independently of the diagram's current visible/hidden state.

---

### 1.2 `ClassSelection` — factory methods

**File:** `use-gui/src/main/java/org/tzi/use/gui/views/selection/classselection/ClassSelection.java`

All submenus use `MenuScroller` (max 20 items visible, 125 ms scroll interval) to handle long lists.

---

#### `getSubMenuHideClass()` → `JMenu "Hide classes"`
- **Reads from:** `diagram.getGraph()` (visible nodes only)
- **Items:**
  - `"Hide all classes"` — present only if `nodesize > 1`; calls `getActionHideNodes("Hide all classes", allVisibleClasses)` on the diagram. Uses `diagram.getActionHideNodes(...)` (not `hideClass` directly) so that the hide is tracked as an undoable action.
  - *(Separator — only when "Hide all" is present)*
  - One `"Hide <ClassName>"` per visible `ClassNode`, sorted alphabetically by `SelectionComparator`. Each calls `diagram.getActionHideNodes("Hide <name>", singletonSet)`.

---

#### `getSubMenuShowClass()` → `JMenu "Show classes"`
- **Reads from:** `diagram.getHiddenNodes()`
- **Items:**
  - `"Show all classes"` — present only if `hiddenNodes.size() > 1`; calls `diagram.showAll()` then `diagram.invalidateContent(true)`.
  - *(Separator — only when "Show all" is present)*
  - One `"Show <ClassName>"` per hidden `ClassNode`, sorted alphabetically. Each calls `diagram.showClass(cls)` then `diagram.repaint()`.

---

#### `getSubMenuHideAssoc()` → `JMenu "Hide associations"`
- **Reads from:** `diagram.getSystem().model().associations()` (all model associations, not just visible ones)
- **Items:**
  - `"Hide all associations"` — present only if `diagram.getVisibleData().getEdges()` is non-empty; calls `hideAssociation(assoc)` for every model association then `repaint()`.
  - *(Separator — only when "Hide all" is present)*
  - One `"Hide <AssocName>"` per association **not** currently hidden (`!diagram.isHidden(association)`), sorted alphabetically. Each calls `diagram.hideAssociation(assoc)` then `diagram.repaint()`.

---

#### `getSubMenuShowAssociation()` → `JMenu "Show associations"`
- **Reads from:** `diagram.getSystem().model().associations()`
- **Items:**
  - `"Show all associations"` — present only if `diagram.getHiddenData().getEdges()` is non-empty; calls `showAssociation(assoc)` for every model association then `repaint()`.
  - *(Separator — only when "Show all" is present)*
  - One `"Show <AssocName>"` per association **currently hidden** (`diagram.isHidden(association)`), sorted alphabetically. Each calls `diagram.showAssociation(assoc)` then `repaint()`.

---

#### `getSubMenuHideGeneralizations(Set<MGeneralization> gens)` → `JMenu "Hide generalizations"`
- **Reads from:** `gens` parameter (passed in by `ClassDiagram` from `visibleData.fGenToGeneralizationEdge.keySet()`)
- **Items:**
  - `"Hide all generalizations"` — always present; calls `diagram.hideGeneralization(gen)` for each gen then `repaint()`.
  - *(Separator)*
  - One `"Hide <GenName>"` per generalization. Label strips the leading `"GEN_"` prefix if present. Each calls `diagram.hideGeneralization(gen)` then `repaint()`.

---

#### `getSubMenuShowGeneralizations(HashSet<MGeneralization> gens)` → `JMenu "Show generalizations"`
- **Reads from:** `gens` parameter (passed in by `ClassDiagram` from `hiddenData.fGenToGeneralizationEdge.keySet()`)
- **Items:**
  - `"Show all generalizations"` — always present; for each gen calls `diagram.showClass(gen.source())`, `diagram.showClass(gen.target())`, `diagram.showGeneralization(gen)`, then `repaint()`. Note: restoring a generalization also forces its source and target classes visible.
  - *(Separator)*
  - One `"Show <GenName>"` per generalization. Label strips the `"GEN_"` prefix. Before showing the edge, checks visibility of source/target and calls `showClass` for each if hidden. Then calls `diagram.showGeneralization(gen)` and `repaint()`.

---

#### `getSelectionClassView(String text)` → `Action` (opens `SelectionClassView`)
- Opens a "Selection classes" window (`SelectionClassView`) embedded in a `ViewFrame`. This is a full panel view (580 × 230) that lets the user choose which classes to show or hide using a table or filter UI.
- In JavaFX mode creates a `SwingNode` window with `DiagramType.SELECTED_CLASS_VIEW`.

---

#### `getSelectedClassPathView(String text, Set<MClass> selectedClasses)` → `Action` (opens `SelectedClassPathView`)
- Opens a "Selection by path length" window (`SelectedClassPathView`) scoped to the given `selectedClasses`. The view lets the user enter a path-length integer and shows/hides/crops all classes within that graph distance from the selection.
- In JavaFX mode creates a `SwingNode` window with `DiagramType.SELECTED_CLASS_PATH_VIEW`.

---

### 1.3 `ObjectSelection` — factory methods

**File:** `use-gui/src/main/java/org/tzi/use/gui/views/selection/objectselection/ObjectSelection.java`

All submenus use `MenuScroller` (same settings as `ClassSelection`). Objects are sorted alphanumerically by name via `AlphanumComparator`.

> **Runtime coupling warning:** `getSubMenuHideLinks`, `getSubMenuShowLinks`, and `getSubMenuLinksByKind` cast `diagram` to `(NewObjectDiagram)` unconditionally. Calling these from `CommunicationDiagram` would throw `ClassCastException`. In practice, `CommunicationDiagram` never calls these three methods.

---

#### `getSubMenuHideObject()` → `JMenu "Hide objects"`
- **Reads from:** `diagram.getGraph().getVisibleNodesIterator()`
- **Items:**
  - `"Hide all objects"` — always present; enabled only if `diagram.getVisibleData().hasNodes()`; calls `diagram.hideAll()` then `repaint()`.
  - *(Separator)*
  - One `JMenu "Class <ClassName>"` per class that has at least one visible object node. Each class submenu contains:
    - `"Hide all"` — calls `diagram.hideObjects(getVisibleObjects(cls))` then `repaint()`.
    - *(Separator)*
    - One `"Hide <objectName>"` per visible object of that class, sorted by `AlphanumComparator`. Each calls `diagram.hideObject(obj)` then `repaint()`.
  - Class submenus are sorted alphabetically by class name.

---

#### `getSubMenuShowObject()` → `JMenu "Show objects"`
- **Reads from:** `diagram.getHiddenNodes()`
- **Items:**
  - `"Show all hidden objects"` — always present; enabled only if `hiddenObjectNodes` is non-empty; calls `diagram.showAll()` then `repaint()`.
  - *(Separator)*
  - One `JMenu "Class <ClassName>"` per class that has at least one hidden object. Each class submenu contains:
    - `"Show all"` — calls `diagram.showObjects(getHiddenObjects(cls))` then `repaint()`.
    - *(Separator)*
    - One `"Show <objectName>"` per hidden object of that class, sorted by `AlphanumComparator`. Each calls `diagram.showObject(obj)` then `repaint()`.
  - Class submenus are sorted alphabetically by class name.

---

#### `getSubMenuHideLinks()` → `JMenu "Hide links"` *(NewObjectDiagram only)*
- **Reads from:** `diagram.getVisibleData().getEdges()`
- **Casts:** `(NewObjectDiagram) diagram`
- **Items:**
  - `"Hide all links"` — always present; enabled only if visible edges exist; calls `((NewObjectDiagram) diagram).hideAllLinks()` then `repaint()`.
  - *(Separator)*
  - One `JMenu "<AssociationName>"` per association that has visible links. Each association submenu contains:
    - `"Hide all"` — calls `((NewObjectDiagram) diagram).hideLink(assocName, links)` then `repaint()`.
    - *(Separator)*
    - One `"Hide <formatted-link-name>"` per visible link, sorted by `linkComparator` (alphanumeric on first two linked objects). Each calls `((NewObjectDiagram) diagram).hideLink(link)` then `repaint()`.
  - Link format: `(Object1, Object2)` for non-reflexive; `(role1:Object1, role2:Object2)` for reflexive; qualifiers appended if present; link-object name prepended if applicable.

---

#### `getSubMenuShowLinks()` → `JMenu "Show links"` *(NewObjectDiagram only)*
- **Reads from:** `diagram.getHiddenData().getEdges()`
- **Casts:** `(NewObjectDiagram) diagram`
- **Structure:** Mirror of `getSubMenuHideLinks()` with show actions:
  - `"Show all links"` — calls `((NewObjectDiagram) diagram).showAllLinks()` then `repaint()`.
  - Per-association submenus each contain `"Show all"` and per-link `"Show <formatted-link-name>"` items.

---

#### `getSubMenuLinksByKind()` → `JMenu "Show/hide links-by-kind"` *(NewObjectDiagram only)*
- **Reads from:** `((NewObjectDiagram) diagram).mapLinksToKindOfAssociation()`
- **Casts:** `(NewObjectDiagram) diagram`
- **Top-level items (conditional):**
  - `"Hide all links"` — present if any visible edges exist.
  - `"Show all links"` — present if any hidden edges exist.
  - *(Separator)*
- **Per-association submenus:** One `JMenu "<AssociationName>"` per association. Each submenu contains:
  - `"Hide all <AssocName>"` — present only if `isHidden(links)` returns 1 (all visible) or 2 (mixed). Hides all links of this association.
  - `"Show all <AssocName>"` — present only if `isHidden(links)` returns 0 (all hidden) or 2 (mixed). Shows all links of this association.
  - *(Separator)*
  - One item per link: `"Show <formatted-link-name>"` if that link is hidden; `"Hide <formatted-link-name>"` if it is visible.

---

#### `getSelectedObjectPathView(String text, Set<MObject> selectedObjects)` → `Action` (opens `SelectedObjectPathView`)
- Opens a "Selection by path length" window scoped to the given selected objects. Behavior mirrors the class-diagram equivalent.

---

#### `getSelectionObjectView()` → `Action` (opens `SelectionObjectView`)
- Opens a "Select objects" window (530 × 230) allowing the user to choose which objects to show or hide via a table UI.

---

#### `getSelectionWithOCLViewAction()` → `Action` (opens `SelectionOCLView`)
- Opens a "Selection by OCL expression" window (370 × 250). The user types an OCL expression; the result set determines which objects are shown or hidden.

---

---

## 2. Architecture Risk: The `PopupMenuInfo` Positional Coupling

### 2.1 What it is

`PopupMenuInfo` is an inner class of `DiagramView` with three fields:

```java
public JPopupMenu popupMenu;
public int generalShowHideStart = 0;
public int generalShowHideLength = 0;
public Point position;
```

The base `DiagramView.unionOfPopUpMenu()` sets the two integer fields as follows:

```java
// immediately before adding the first show/hide checkbox:
info.generalShowHideStart = popupMenu.getComponentCount();
popupMenu.add(cbAttrValues);       // "Show attributes"
popupMenu.add(cbAssocNames);       // "Show association names"
popupMenu.add(cbRolenames);        // "Show role names"
info.generalShowHideLength = popupMenu.getComponentCount() - info.generalShowHideStart;
// generalShowHideLength is always 3 with the current base implementation
```

`generalShowHideStart` is therefore an **absolute component index** — the position of the first checkbox in the assembled `JPopupMenu` at the time the base class finishes running. It encodes the base class's internal item count as a number that the subclass must consume.

### 2.2 How `ClassDiagram` uses it

`ClassDiagram` reads both fields to intercalate its own checkboxes between the base class's three items:

```java
pos += info.generalShowHideStart;   // jump to where "Show attributes" sits
int extraShowHideLength = info.generalShowHideLength;  // = 3

popupMenu.insert(cbGroupMR, pos - 1);    // just before "Show attributes"
pos++;                                    // skip "Show attributes"
extraShowHideLength--;
popupMenu.insert(cbOperations, ++pos);   // after "Show attributes"
pos++;                                    // skip "Show association names"
extraShowHideLength--;
popupMenu.insert(cbMultiplicities, ++pos); // after "Show association names"
pos++;                                    // skip "Show role names"
extraShowHideLength--;
popupMenu.insert(cbUnion, ++pos);
popupMenu.insert(cbSubsets, ++pos);
popupMenu.insert(cbRedefines, ++pos);

pos += extraShowHideLength + 1;          // skip past the separator
popupMenu.insert(menuCoverage, pos++);
```

The final menu order in the show/hide section is therefore:

```
[Group multiplicities / role names]   ← ClassDiagram inserts before slot 0
Show attributes                        ← base slot 0
[Show operations]                      ← ClassDiagram inserts after slot 0
Show association names                 ← base slot 1
[Show multiplicities]                  ← ClassDiagram inserts after slot 1
Show role names                        ← base slot 2
[Show union constraints]               ← ClassDiagram inserts after slot 2
[Show subset constraints]              ← ClassDiagram inserts
[Show redefines constraints]           ← ClassDiagram inserts
```

### 2.3 Why this is the primary fragility

The mechanism works only under a set of implicit invariants that are enforced nowhere:

| Invariant | What breaks if violated |
|---|---|
| The base class adds exactly 3 items in the show/hide block | `generalShowHideLength` returns the wrong count; `ClassDiagram`'s position arithmetic over-shoots or under-shoots |
| No item is added to the base class popup before the show/hide block without updating `generalShowHideStart` | `pos += info.generalShowHideStart` resolves to the wrong index; every subsequent `insert` lands in the wrong slot |
| The separator immediately follows the three base checkboxes | `pos += extraShowHideLength + 1` skips past the separator only if `extraShowHideLength` reaches 0 correctly |
| No item is ever removed from the base class popup before the show/hide block | Same as the second invariant |

None of these invariants are expressed in code. There is no assertion, no comment on the base class side warning that the item count matters, and no compile-time check. A developer adding a new global option to `DiagramView.unionOfPopUpMenu()` before the show/hide block (a reasonable thing to do) would silently mis-position all of `ClassDiagram`'s injected items.

### 2.4 `NewObjectDiagram`'s simpler but related coupling

`NewObjectDiagram` does not use `generalShowHideStart`. Instead it uses a hardcoded offset:

```java
popupMenu.insert(showStates, pos + 3);
```

`pos` at that point is the index of the first base item (which `NewObjectDiagram` tracks as it inserts its own items before calling the base). The `+ 3` assumes that "Show attributes", "Show association names", and "Show role names" are always items 0, 1, and 2 of the base section, and that "Show states" should appear after them. This is the same fragility with less indirection: the literal `3` is a hardcoded assumption about the base class's item count.

### 2.5 `CommunicationDiagram` and `StateMachineDiagram` avoid this entirely

By building their own `JPopupMenu` from scratch and not calling `super`, they have no coupling to the base class's item order. The trade-off is that shared items (comment node, align, anti-aliasing, show grid, layout) must be wired in manually via the inherited helper methods.

---

---

## 3. Cross-Diagram Item Matrix

The table below maps every functional item group to the diagrams that include it. "via base" means the item is provided by `DiagramView.unionOfPopUpMenu()` and inherited. "direct" means the diagram builds the item itself without calling super.

Legend:  ✓ = present   –  = absent

| Functional group | ClassDiagram | NewObjectDiagram | CommunicationDiagram | StateMachineDiagram |
|---|:---:|:---:|:---:|:---:|
| **Selection-scoped hide** | ✓ | ✓ | ✓ | – |
| **Selection-scoped crop** | ✓ | ✓ | ✓ | – |
| **Show all hidden elements** | ✓ | ✓ | – | – |
| **Show neighborhood of selection** | – | – | ✓ | – |
| **Show/hide by path length** | ✓ | ✓ | – | – |
| **Show/hide by OCL expression** | – | ✓ | ✓ | – |
| **Show/hide with view** | ✓ | ✓ | ✓ | – |
| **Named-element show/hide submenus** | ✓ class, assoc, gen | ✓ object, link | ✓ object | – |
| **Hide/show links by kind** | – | ✓ | – | – |
| **PSM launcher** | ✓ | ✓ | – | – |
| **Create/delete link** | – | ✓ | – | – |
| **Destroy object** | – | ✓ | – | – |
| **Edit object properties** | – | ✓ | – | – |
| **Greying (grey out / grey in)** | – | ✓ | – | – |
| **Always-visible pinning** | – | – | ✓ | – |
| **Reset always-visible** | – | – | ✓ | – |
| **Sub/superclass navigation** | ✓ | – | – | – |
| **Hide selected edge properties** | via base | via base | – | – |
| **Show/hide per-edge properties** | via base | via base | – | – |
| **Show attributes** | via base | via base | – | – |
| **Show association names** | via base | via base | – | – |
| **Show role names** | via base | via base | – | – |
| **Show operations** | ✓ | – | – | – |
| **Show multiplicities** | ✓ | – | – | – |
| **Show union/subset/redefines constraints** | ✓ | – | – | – |
| **Group multiplicities / role names** | ✓ | – | – | – |
| **Show states (object state label)** | – | ✓ | – | – |
| **Show all life states** | – | – | ✓ | – |
| **Show state invariant** | – | – | – | ✓ |
| **Hide state invariant** | – | – | – | ✓ |
| **Merge / demerge transitions** | – | – | – | ✓ |
| **Show communication messages** | – | – | ✓ | – |
| **Messages navigation** | – | – | ✓ | – |
| **Show all commands** | – | – | ✓ | – |
| **Commands to show…** | – | – | ✓ | – |
| **Show all messages** | – | – | ✓ | – |
| **Message selection…** | – | – | ✓ | – |
| **Relative numbering** | – | – | ✓ | – |
| **Show coverage** | ✓ | – | – | – |
| **Export as new model** | ✓ | – | – | – |
| **Properties…** | – | – | ✓ | – |
| **Create synchronized sequence diagram** | – | – | ✓ | – |
| **Add comment node** | via base | via base | direct | direct |
| **Delete comment node** | – | – | ✓ (conditional) | – |
| **Align elements** | via base | via base | direct | direct |
| **Anti-aliasing** | via base | via base | direct | direct |
| **Show grid** | via base | via base | direct | direct |
| **Grayscale view** | via base | via base | – | – |
| **Auto-Layout** | via base | via base | via helper | via helper |
| **Layouts submenu** | via base | via base | via helper | via helper |
| **Reset / Load / Save layout** | via base | via base | via helper | via helper |
| **Select all nodes / edges** | via base | via base | via helper | via helper |
| **Edit text… (Rolename)** | via base | via base | – | – |

### 3.1 Extraction candidates (present in 2+ diagrams)

The following groups are shared across at least two diagrams and are therefore candidates for extraction into reusable components during a refactor:

| Group | Diagrams | Current duplication |
|---|---|---|
| Selection-scoped hide + crop | CD, OD, CommD | Three independent implementations with identical label-building logic |
| Show all hidden elements | CD, OD | Two near-identical implementations |
| Show/hide with view | CD, OD, CommD | Both OD and CommD delegate to `ObjectSelection`; CD delegates to `ClassSelection` |
| Show/hide by path length | CD, OD | Two separate view classes with parallel structures |
| Show/hide by OCL | OD, CommD | Both delegate to `ObjectSelection.getSelectionWithOCLViewAction()` — already shared |
| Named-element show/hide submenus | CD, OD, CommD | CD uses `ClassSelection`; OD/CommD use `ObjectSelection` — no common interface |
| PSM launcher | CD, OD | Two independent implementations with duplicated loop logic |
| Add comment node | all 4 | Already factored into `getMenuItemCommentNode()` in the base class |
| Align elements | all 4 | Already factored into `getMenuAlign()` in the base class |
| Anti-aliasing | all 4 | Already factored into `getMenuItemAntiAliasing()` in the base class |
| Show grid | all 4 | Already factored into `getMenuItemShowGrid()` in the base class |
| Layout items | all 4 | Already factored into `addLayoutMenuItems()` in the base class |

### 3.2 Items unique to one diagram (no extraction value)

The following groups exist in exactly one diagram. They are candidates for staying in their respective diagram class or being moved to a dedicated builder for that diagram:

- `ClassDiagram` only: sub/superclass navigation, show operations, show multiplicities, show union/subset/redefines, group MR, show coverage, export as new model
- `NewObjectDiagram` only: create/delete link, destroy object, edit properties, greying, hide/show links by kind, show states
- `CommunicationDiagram` only: always-visible pinning, reset always-visible, show neighborhood, all message/command items, properties, create sequence diagram, delete comment node
- `StateMachineDiagram` only: show/hide state invariant, merge/demerge transitions