> **Pre-refactor documentation** — describes the popup menu code as it was *before* the PopupMenuBuilder refactor.

# Popup Menu – Subclass Overrides Documentation

This document covers the `unionOfPopUpMenu()` overrides in each `DiagramView` subclass. For the base class implementation, see [popup-menu-documentation.md](popup-menu-documentation.md).

**Overview of strategies:**

| Subclass               | Calls `super`? | Strategy                                          |
|------------------------|----------------|---------------------------------------------------|
| `ClassDiagram`         | Yes            | Inserts class-specific items before and within base menu |
| `NewObjectDiagram`     | Yes            | Inserts object/link-specific items before base menu |
| `CommunicationDiagram` | No             | Builds a fully independent menu from scratch      |
| `StateMachineDiagram`  | No             | Builds a fully independent menu from scratch      |

---

## 1. ClassDiagram

**File:** `use-gui/src/main/java/org/tzi/use/gui/views/diagrams/classdiagram/ClassDiagram.java`

`ClassDiagram` calls `super.unionOfPopUpMenu()` and inserts class-specific items at the top (`pos = 0`) and also intercalates additional checkbox items within the base show/hide section using the `generalShowHideStart` / `generalShowHideLength` offsets returned by the base method. Two further items are appended after all base items.

### Menu items in order of appearance

---

#### 1. Show hidden elements *(conditional)*
- **Type:** `JMenuItem`
- **Condition:** `hiddenData.hasNodes() || hiddenData.hasEdges()` — there is at least one hidden class, data type, enum, association, or generalization.
- **Functionality:** Calls `showAll()` to move all hidden nodes and edges back into the visible data set, then calls `invalidateContent(true)` to relayout and repaint.

---

#### *(Separator — same condition as #1)*

---

#### 2. Hide [label] *(conditional)*
- **Type:** `JMenuItem` (anonymous `AbstractAction`)
- **Condition:** At least one class node, association name node, association edge, or generalization edge is selected (`!selectedAssociations.isEmpty() || !selectedClasses.isEmpty() || !selectedGeneralization.isEmpty()`).
- **Label:**
  - Single association: `"Hide <assoc-name>"`
  - Single class: `"Hide <class-name>"`
  - Single generalization: `"Hide <gen-name>"` (strips leading `"gen "` prefix)
  - Multiple: `"Hide N elements"`
- **Functionality:** Calls `hideAssociation(assoc)` for each selected association, `hideClass(cls)` for each selected class, and `hideGeneralization(gen)` for each selected generalization. Calls `repaint()`.

---

#### 3. Crop [label] *(conditional)*
- **Type:** `JMenuItem` (anonymous `AbstractAction`)
- **Condition:** Same as item 2.
- **Label:** Same pattern as item 2 but with `"Crop"` prefix.
- **Functionality:** Computes the union of all classes directly or transitively involved in the selection (associated classes of selected associations, parent/child classes of selected generalizations). Hides every visible element NOT in that union by calling `getActionHideNodes(label, getNoneSelectedElementsByElements(...))`.

---

#### 4. Sub/Superclasses *(conditional submenu)*
- **Type:** `JMenu`
- **Condition:** At least one selected class has parents (`!cls.parents().isEmpty()`) or children (`!cls.children().isEmpty()`).
- **Sub-items:**

  ##### 4a. Show superclasses of [label] *(conditional)*
  - **Condition:** At least one parent of the selected class(es) is currently hidden.
  - **Functionality:** Calls `showClass(cls)` for every parent in `allParents()`, then `repaint()`.

  ##### 4b. Hide superclasses of [label] *(conditional)*
  - **Condition:** At least one parent of the selected class(es) is currently visible.
  - **Functionality:** Calls `getActionHideNodes("Hide superclasses of ...", parents)` to hide all parent classes.

  ##### 4c. Crop superclasses of [label]
  - **Condition:** None (always present when the submenu is shown).
  - **Functionality:** First calls `showClass(cls)` for every parent to make them all visible, then hides everything NOT in the parent set via `getActionHideNodes(...)`.

  ##### 4d. Show subclasses of [label] *(conditional)*
  - **Condition:** At least one child of the selected class(es) is currently hidden.
  - **Functionality:** Calls `showClass(cls)` for every child in `allChildren()`, then `repaint()`.

  ##### 4e. Hide subclasses of [label] *(conditional)*
  - **Condition:** At least one child of the selected class(es) is currently visible.
  - **Functionality:** Calls `getActionHideNodes("Hide subclasses of ...", children)` to hide all child classes.

  ##### 4f. Crop subclasses of [label]
  - **Condition:** None (always present when the submenu is shown).
  - **Functionality:** First calls `showClass(cls)` for every child to make them all visible, then hides everything NOT in the child set via `getActionHideNodes(...)`.

---

#### *(Separator — same condition as item 2)*

---

#### 5. Show class... *(conditional submenu)*
- **Type:** `JMenu` (from `fSelection.getSubMenuShowClass()`)
- **Condition:** `hiddenData.hasNodes()` — at least one classifier node is hidden.
- **Functionality:** Lists all hidden classifiers by name. Clicking a name calls `showClass(cls)` to restore it.

---

#### 6. Hide class... *(conditional submenu)*
- **Type:** `JMenu` (from `fSelection.getSubMenuHideClass()`)
- **Condition:** `fGraph.size() > 0` — the diagram graph contains at least one visible node.
- **Functionality:** Lists all visible classifiers by name. Clicking a name calls `hideClass(cls)` to hide it.

---

#### 7. Show/hide/crop classes
- **Type:** `JMenu`
- **Condition:** Always shown.
- **Sub-items:**

  ##### 7a. By path length...
  - **Condition:** `!selectedClasses.isEmpty()` — at least one class node is selected.
  - **Functionality:** Opens a dialog to select classes by their graph-distance from the currently selected class(es). The result is applied as a show/hide/crop operation.

  ##### 7b. With view...
  - **Condition:** Always present.
  - **Functionality:** Opens a view-based class selection dialog (`fSelection.getSelectionClassView(...)`) where the user can choose classes to show or hide using a filter view.

---

#### *(Separator)*

---

#### 8. Hide association... *(conditional submenu)*
- **Type:** `JMenu` (from `fSelection.getSubMenuHideAssoc()`)
- **Condition:** `fGraph.size() > 0` AND at least one visible binary association, association class, or n-ary association exists in `visibleData`.
- **Functionality:** Lists all visible associations by name. Clicking a name hides that association edge.

---

#### 9. Show association... *(conditional submenu)*
- **Type:** `JMenu` (from `fSelection.getSubMenuShowAssociation()`)
- **Condition:** At least one hidden binary association, association class, or n-ary association exists in `hiddenData`.
- **Functionality:** Lists all hidden associations by name. Clicking a name restores that association edge.

---

#### *(Separator)*

---

#### 10. Hide generalizations... *(conditional submenu)*
- **Type:** `JMenu` (from `fSelection.getSubMenuHideGeneralizations(gens)`)
- **Condition:** `!visibleData.fGenToGeneralizationEdge.isEmpty()` — at least one generalization edge is currently visible.
- **Functionality:** Lists all visible generalization edges. Clicking one hides that generalization from the diagram.

---

#### 11. Show generalizations... *(conditional submenu)*
- **Type:** `JMenu` (from `fSelection.getSubMenuShowGeneralizations(gens)`)
- **Condition:** `!hiddenData.fGenToGeneralizationEdge.isEmpty()` — at least one generalization edge is currently hidden.
- **Functionality:** Lists all hidden generalization edges. Clicking one restores it to the diagram.

---

#### *(Separator — shown when any generalization exists in either visibleData or hiddenData)*

---

#### 12. Show protocol state machine...
- **Type:** `JMenu`
- **Condition:** Always inserted. Enabled only when exactly one classifier node is selected, that classifier is an `MClass`, and it owns at least one `MProtocolStateMachine`.
- **Sub-items (one per PSM, sorted by name):**
  - Label: the PSM's name.
  - **Functionality:** Calls `MainWindow.instance().showStateMachineView(sm)` to open a `StateMachineView` for that protocol state machine.

---

#### *(Separator)*

---

#### 13. Add comment node *(from base)*
- **Type:** `JMenuItem`
- **Condition:** Always shown.
- **Functionality:** Creates a new `CommentNode` at the position where the user right-clicked. See base class documentation.

---

#### 14. Hide selected properties *(conditional, from base)*
- **Type:** `JMenuItem`
- **Condition:** Node selection is non-empty and every selected node is an `EdgeProperty`.
- **Functionality:** Hides every selected edge property label. See base class documentation.

---

#### 15. Show or hide edge properties *(conditional submenu, from base)*
- **Type:** `JMenu`
- **Condition:** At least one edge is selected (`fEdgeSelection` is non-empty).
- **Functionality:** Controls visibility of role names, multiplicities, and association names on selected edges. See base class documentation.

---

#### *(Separator)*

---

#### 16. Group multiplicities / role names *(inserted by ClassDiagram into base section)*
- **Type:** `JCheckBoxMenuItem`
- **Condition:** Always shown (inserted just before the base "Show attributes" item).
- **State reflects:** `fOpt.isGroupMR()`
- **Functionality:** Toggles whether multiplicity and role name labels are grouped together at each association end. Calls `fOpt.setGroupMR(...)` and `repaint()`.

---

#### 17. Show attributes *(from base)*
- **Type:** `JCheckBoxMenuItem`
- **Condition:** Always shown.
- **State reflects:** `DiagramOptions.isShowAttributes()`
- **Functionality:** Toggles the attributes compartment on all class nodes. See base class documentation.

---

#### 18. Show operations *(inserted by ClassDiagram into base section)*
- **Type:** `JCheckBoxMenuItem`
- **Condition:** Always shown (inserted after "Show attributes").
- **State reflects:** `fOpt.isShowOperations()`
- **Functionality:** Toggles display of the operations compartment on class nodes. Calls `fOpt.setShowOperations(...)` and `repaint()`.

---

#### 19. Show association names *(from base)*
- **Type:** `JCheckBoxMenuItem`
- **Condition:** Always shown.
- **State reflects:** `DiagramOptions.isShowAssocNames()`
- **Functionality:** Toggles center association-name labels. See base class documentation.

---

#### 20. Show multiplicities *(inserted by ClassDiagram into base section)*
- **Type:** `JCheckBoxMenuItem`
- **Condition:** Always shown (inserted after "Show association names").
- **State reflects:** `fOpt.isShowMutliplicities()`
- **Functionality:** Toggles display of multiplicity labels at association ends. Calls `fOpt.setShowMutliplicities(...)` and `repaint()`.

---

#### 21. Show role names *(from base)*
- **Type:** `JCheckBoxMenuItem`
- **Condition:** Always shown.
- **State reflects:** `DiagramOptions.isShowRolenames()`
- **Functionality:** Toggles role name labels at association ends. See base class documentation.

---

#### 22. Show union constraints *(inserted by ClassDiagram into base section)*
- **Type:** `JCheckBoxMenuItem`
- **Condition:** Always shown (inserted after "Show role names").
- **State reflects:** `fOpt.isShowUnionConstraints()`
- **Functionality:** Toggles display of `{union}` constraint annotations on association ends. Calls `fOpt.setShowUnionConstraints(...)` and `repaint()`.

---

#### 23. Show subset constraints *(inserted by ClassDiagram into base section)*
- **Type:** `JCheckBoxMenuItem`
- **Condition:** Always shown.
- **State reflects:** `fOpt.isShowSubsetsConstraints()`
- **Functionality:** Toggles display of `{subsets ...}` constraint annotations on association ends. Calls `fOpt.setShowSubsetsConstraints(...)` and `repaint()`.

---

#### 24. Show redefines constraints *(inserted by ClassDiagram into base section)*
- **Type:** `JCheckBoxMenuItem`
- **Condition:** Always shown.
- **State reflects:** `fOpt.isShowRedefinesConstraints()`
- **Functionality:** Toggles display of `{redefines ...}` constraint annotations on association ends. Calls `fOpt.setShowRedefinesConstraints(...)` and `repaint()`.

---

#### *(Separator)*

---

#### 25. Align elements *(from base)*
- **Type:** `JMenu`
- See base class documentation.

---

#### 26. Anti-aliasing *(from base)*
- **Type:** `JCheckBoxMenuItem`
- See base class documentation.

---

#### 27. Show grid *(from base)*
- **Type:** `JCheckBoxMenuItem`
- See base class documentation.

---

#### 28. Grayscale view *(from base)*
- **Type:** `JCheckBoxMenuItem`
- See base class documentation.

---

#### *(Layout items from base — Auto-Layout, Layouts, Reset layout, Load layout, Save layout, Select all nodes, Select all edges, Edit text…)*

---

#### 29. Show coverage *(appended by ClassDiagram after all base items)*
- **Type:** `JMenu`
- **Condition:** Always shown.
- **Sub-items (radio buttons, mutually exclusive via `ButtonGroup`):**

  ##### 29a. Don't show
  - **Functionality:** Sets `getOptions().setShowCoverage(ShowCoverage.DONT_SHOW)`. Coverage coloring is disabled.

  ##### 29b. Show
  - **Functionality:** Sets `getOptions().setShowCoverage(ShowCoverage.SHOW)`. Each class node is colored based on test-coverage data via `setCoverageColor()`.

  ##### 29c. Show (expand operations)
  - **Functionality:** Sets `getOptions().setShowCoverage(ShowCoverage.SHOW_EXPAND_OPERATIONS)`. Same as "Show" but also expands the operations compartment to show per-operation coverage.

---

#### *(Separator)*

---

#### 30. Export visible elements as new model... *(appended by ClassDiagram)*
- **Type:** `JMenuItem`
- **Condition:** Always shown.
- **Functionality:** Opens a `JFileChooser` to select a save path for a `.use` file. Exports all currently visible classes, data types, enums, and associations from `visibleData` to a new USE model file via `ModelExporter.export(...)`. Prompts for overwrite confirmation if the file already exists. Shows a success or error dialog when done.

---

---

## 2. NewObjectDiagram

**File:** `use-gui/src/main/java/org/tzi/use/gui/views/diagrams/objectdiagram/NewObjectDiagram.java`

`NewObjectDiagram` calls `super.unionOfPopUpMenu()` and inserts object/link-specific items at the top (`pos = 0`). One additional item (`Show states`) is inserted within the base section at a fixed offset of `+3` relative to the start of the base items.

### Menu items in order of appearance

---

#### 1. Edit properties of '[name]' *(conditional)*
- **Type:** `ActionShowProperties`
- **Condition:** Exactly one object node is selected (`selectedObjects.size() == 1`).
- **Functionality:** Opens the `ObjectPropertiesView` panel for the selected object so the user can inspect and edit its attribute values.

---

#### 2. Destroy '[name]' / Destroy N objects *(conditional)*
- **Type:** `ActionDelete`
- **Condition:** At least one object node is selected (`!selectedObjects.isEmpty()`).
- **Label:** `"Destroy '<name>'"` for a single object, `"Destroy N objects"` for multiple.
- **Functionality:** Removes the selected objects from the running system state, which also removes all their links.

---

#### *(Separator — same condition as item 2)*

---

#### 3. Create [AssociationName]... *(conditional, one item per valid combination)*
- **Type:** `ActionInsertLink`
- **Condition:** At least one object is selected AND a non-read-only association exists whose end types are compatible with the selected objects AND either no link yet exists between those specific objects for that association, or the association has qualified ends.
- **Label:** The association name (formatted by `ActionInsertLink`).
- **Functionality:** Opens a dialog to insert a new link for the given association between the selected objects.

---

#### 4. Delete [LinkName] *(conditional, one item per existing link)*
- **Type:** `ActionDeleteLink`
- **Condition:** At least one object is selected AND an existing link between the selected objects for a given association was found.
- **Label:** The formatted link name.
- **Functionality:** Deletes the specified link from the system state.

---

#### *(Separator — shown only if at least one Create or Delete link item was added)*

---

#### 5. Show hidden elements *(conditional)*
- **Type:** `JMenuItem`
- **Condition:** At least one hidden object, binary link, n-ary link, half-link, or link object exists in `hiddenData`.
- **Functionality:** Calls `showAll()` to restore all hidden objects and `showAllLinks()` to restore all hidden links. Calls `invalidateContent(true)`.

---

#### *(Separator — same condition as item 5)*

---

#### 6. Hide [label] *(conditional)*
- **Type:** `JMenuItem` (anonymous `AbstractAction`)
- **Condition:** At least one object or link is selected.
- **Label:**
  - Single link: `"Hide <formatted-link-name>"`
  - Single object: `"Hide <object-name>"`
  - Multiple: `"Hide N elements"`
- **Functionality:** Calls `hideLink(link)` for each selected link and `hideObject(obj)` for each selected object. Calls `repaint()`.

---

#### 7. Crop [label] *(conditional)*
- **Type:** `JMenuItem` (anonymous `AbstractAction`)
- **Condition:** Same as item 6.
- **Label:** Same pattern as item 6 but with `"Crop"` prefix.
- **Functionality:** Builds the set of objects to hide: starts from selected objects, then adds all objects that are endpoints of selected links (or the link object itself if it is an `MLinkObject`). Hides all objects NOT in that union via `getAction(label, getNoneSelectedNodes(objectsToHide))`.

---

#### 8. Grey out/in '[name]' *(conditional — single object)*
- **Type:** `JMenuItem` (anonymous `AbstractAction`)
- **Condition:** Exactly one object is selected AND that object's node is present in `visibleData`.
- **Label:** `"Grey out <name>"` if the node is not greyed; `"Grey in <name>"` if it is already greyed.
- **Functionality:** Toggles the `greyed` property of the `ObjectNode`. Calls `repaint()`.

---

#### 9. Grey in N elements *(conditional — multiple objects)*
- **Type:** `JMenuItem` (anonymous `AbstractAction`)
- **Condition:** More than one object is selected AND at least one of the selected objects' nodes is currently greyed.
- **Label:** `"Grey in N elements"` where N is the count of currently-greyed nodes in the selection.
- **Functionality:** Sets `greyed = false` on all currently-greyed nodes in the selection. Calls `repaint()`.

---

#### 10. Grey out N elements *(conditional — multiple objects)*
- **Type:** `JMenuItem` (anonymous `AbstractAction`)
- **Condition:** More than one object is selected AND at least one of the selected objects' nodes is currently not greyed.
- **Label:** `"Grey out N elements"` where N is the count of non-greyed nodes in the selection.
- **Functionality:** Sets `greyed = true` on all currently-non-greyed nodes in the selection. Calls `repaint()`.

---

#### *(Separator — same condition as item 6)*

---

#### 11. Show/hide/crop objects
- **Type:** `JMenu`
- **Condition:** Always shown.
- **Sub-items:**

  ##### 11a. By path length... *(conditional)*
  - **Condition:** `!selectedObjects.isEmpty()` — at least one object is selected.
  - **Functionality:** Opens a dialog to show/hide objects by their graph-distance from the selected object(s). Calls `fSelection.getSelectedObjectPathView(...)`.

  ##### 11b. With OCL view
  - **Condition:** Always present.
  - **Functionality:** Opens a view where the user can write an OCL expression to filter which objects are visible. Calls `fSelection.getSelectionWithOCLViewAction()`.

  ##### 11c. With view...
  - **Condition:** Always present.
  - **Functionality:** Opens a generic object selection view. Calls `fSelection.getSelectionObjectView()`.

---

#### 12. Hide object... *(conditional submenu)*
- **Type:** `JMenu` (from `fSelection.getSubMenuHideObject()`)
- **Condition:** `fGraph.size() > 0` — at least one object node is visible in the graph.
- **Functionality:** Lists all visible object nodes by name. Clicking a name hides that object.

---

#### 13. Show object... *(conditional submenu)*
- **Type:** `JMenu` (from `fSelection.getSubMenuShowObject()`)
- **Condition:** `!hiddenData.fObjectToNodeMap.isEmpty()` — at least one object node is hidden.
- **Functionality:** Lists all hidden object nodes by name. Clicking a name restores that object.

---

#### 14. Hide link... *(conditional submenu)*
- **Type:** `JMenu` (from `fSelection.getSubMenuHideLinks()`)
- **Condition:** At least one visible link edge exists (binary link, n-ary link, half-link, or link object) in `visibleData`.
- **Functionality:** Lists all visible links by name. Clicking a name hides that link.

---

#### 15. Show link... *(conditional submenu)*
- **Type:** `JMenu` (from `fSelection.getSubMenuShowLinks()`)
- **Condition:** At least one hidden link exists (binary link, n-ary link, half-link, or link object) in `hiddenData`.
- **Functionality:** Lists all hidden links by name. Clicking a name restores that link.

---

#### *(Separator)*

---

#### 16. Hide/show links by kind *(conditional submenu)*
- **Type:** `JMenu` (from `fSelection.getSubMenuLinksByKind()`)
- **Condition:** At least one visible or hidden link exists.
- **Functionality:** Groups all links by their association type. Each group entry allows hiding or showing all links of that association kind at once.

---

#### *(Separator — same condition as item 16)*

---

#### 17. Show protocol state machine... *(conditional)*
- **Type:** `JMenu`
- **Condition:** `!selectedObjects.isEmpty()` — at least one object is selected. The menu is always inserted when this condition is met, but is disabled unless exactly one object is selected and that object's class hierarchy owns at least one `MProtocolStateMachine`.
- **Sub-items (one per PSM, sorted by name):**
  - Label: the PSM's name.
  - **Functionality:** Calls `MainWindow.instance().showStateMachineView(sm, obj)` to open a `StateMachineView` for that protocol state machine bound to the specific object instance.

---

#### *(Separator — same condition as item 17)*

---

#### 18. Add comment node *(from base)*
- **Type:** `JMenuItem`
- **Condition:** Always shown.
- **Functionality:** Creates a new `CommentNode` at the right-click position. See base class documentation.

---

#### 19. Hide selected properties *(conditional, from base)*
- **Type:** `JMenuItem`
- **Condition:** Node selection is non-empty and every selected node is an `EdgeProperty`.
- **Functionality:** Hides every selected edge property. See base class documentation.

---

#### 20. Show or hide edge properties *(conditional submenu, from base)*
- **Type:** `JMenu`
- **Condition:** At least one edge is selected.
- **Functionality:** See base class documentation.

---

#### *(Separator)*

---

#### 21. Show attributes *(from base)*
- **Type:** `JCheckBoxMenuItem`
- See base class documentation.

---

#### 22. Show association names *(from base)*
- **Type:** `JCheckBoxMenuItem`
- See base class documentation.

---

#### 23. Show role names *(from base)*
- **Type:** `JCheckBoxMenuItem`
- See base class documentation.

---

#### 24. Show states *(inserted by NewObjectDiagram into base section)*
- **Type:** `JCheckBoxMenuItem`
- **Condition:** Always shown (inserted at `pos + 3` relative to start of the base section, placing it just after "Show role names").
- **State reflects:** `getOptions().isShowStates()`
- **Functionality:** Toggles display of the current active state label inside each object node. Calls `getOptions().setShowStates(...)` and `invalidateContent(true)`.

---

#### *(Separator)*

---

#### 25. Align elements *(from base)*
- **Type:** `JMenu`
- See base class documentation.

---

#### 26. Anti-aliasing *(from base)*
- **Type:** `JCheckBoxMenuItem`
- See base class documentation.

---

#### 27. Show grid *(from base)*
- **Type:** `JCheckBoxMenuItem`
- See base class documentation.

---

#### 28. Grayscale view *(from base)*
- **Type:** `JCheckBoxMenuItem`
- See base class documentation.

---

#### *(Layout items from base — Auto-Layout, Layouts, Reset layout, Load layout, Save layout, Select all nodes, Select all edges, Edit text…)*

---

---

## 3. CommunicationDiagram

**File:** `use-gui/src/main/java/org/tzi/use/gui/views/diagrams/behavior/communicationdiagram/CommunicationDiagram.java`

`CommunicationDiagram` does **not** call `super.unionOfPopUpMenu()`. It creates a brand-new `JPopupMenu` and `PopupMenuInfo`, building the entire menu from scratch. It reuses helper methods `getMenuItemCommentNode()`, `getMenuAlign()`, `getMenuItemAntiAliasing()`, `getMenuItemShowGrid()`, and `addLayoutMenuItems()` that are inherited from `DiagramView`.

### Menu items in order of appearance

---

#### 1. Show neighborhood of selection *(conditional)*
- **Type:** `JMenuItem`
- **Condition:** At least one `ObjectNodeActivity` or `LinkBoxNode` is selected.
- **Functionality:** Calls `showReachableObjectsViaOneStep(selectedNodesSet)` to reveal all objects and links reachable in one step from the selected nodes. Calls `afterSelectionAction()` and `invalidateContent(true)`.

---

#### *(Separator — same condition as item 1)*

---

#### 2. Hide [label] *(conditional)*
- **Type:** `ActionHideCommunicationDiagram`
- **Condition:** `!selectedNodesSet.isEmpty()`.
- **Label:** `"Hide <name>"` for a single node, `"Hide N Nodes"` for multiple.
- **Functionality:** Hides the selected nodes via `ActionHideCommunicationDiagram`.

---

#### 3. Crop [label] *(conditional)*
- **Type:** `ActionHideCommunicationDiagram`
- **Condition:** Same as item 2.
- **Label:** `"Crop <name>"` for a single node, `"Crop N Nodes"` for multiple.
- **Functionality:** Hides all nodes NOT in the current selection via `ActionHideCommunicationDiagram(label, getNoneSelectedNodes(selectedNodesSet), ...)`.

---

#### *(Separator — same condition as item 2)*

---

#### 4. Show/hide/crop objects
- **Type:** `JMenu`
- **Condition:** Always shown.
- **Sub-items:**

  ##### 4a. With OCL view
  - **Condition:** Always present.
  - **Functionality:** Opens a view where the user can write an OCL expression to filter object visibility. Calls `fSelection.getSelectionWithOCLViewAction()`.

  ##### 4b. With view...
  - **Condition:** Always present.
  - **Functionality:** Opens a generic object selection view. Calls `fSelection.getSelectionObjectView()`.

---

#### 5. Hide object... *(conditional submenu)*
- **Type:** `JMenu` (from `fSelection.getSubMenuHideObject()`)
- **Condition:** `fGraph.size() > 0` — graph has at least one visible node.
- **Functionality:** Lists all visible object nodes by name. Clicking a name hides that object.

---

#### 6. Show object... *(conditional submenu)*
- **Type:** `JMenu` (from `fSelection.getSubMenuShowObject()`)
- **Condition:** At least one node in the graph has `isHidden() == true`.
- **Functionality:** Lists all hidden object nodes by name. Clicking a name restores that object.

---

#### 7. Show related objects...
- **Type:** `JMenuItem`
- **Condition:** Always shown.
- **Functionality:** Opens a `ShowRelatedObjectsWindow` to display all objects related to the current diagram state (objects not yet shown but reachable from visible objects). Calls `createShowRelatedObjectsWindow()` and `invalidateContent(true)`.

---

#### *(Separator)*

---

#### 8. Keep [element] always visible *(conditional)*
- **Type:** `JMenuItem`
- **Condition:** `!selectedNodesSet.isEmpty()` — at least one compatible node is selected.
- **Label:**
  - Single `LinkBoxNode`: `"Keep link always visible"`
  - Single `ObjectNode`: `"Keep object always visible"`
  - Single other node / multiple: `"Keep <name-or-N-Nodes> always visible"`
- **Functionality:** Separates selected nodes into `MLink` and `MObject` sets, then calls `getSharedVisibleManager().getData().addAlwaysVisibleElements(objects, links)` to pin them as always-visible. Calls `invalidateContent(true)`.

---

#### *(Separator — same condition as item 8)*

*Note: Items 8–9 are in the `if (selectedNodesSet.size() > 0)` branch. Items 10–11 are in the `else` branch and are therefore mutually exclusive with items 8–9.*

---

#### 9. Reset selection of always visible *(conditional)*
- **Type:** `JMenuItem`
- **Condition:** `selectedNodesSet.isEmpty()` AND `getSharedVisibleManager().getData().existAlwaysVisibleElements()`.
- **Functionality:** Calls `getSharedVisibleManager().getData().clearAlwaysVisibleElements()` to remove all pinned elements, then calls `filterEdges()` and `invalidateContent(true)`.

---

#### *(Separator — same condition as item 9)*

---

#### 10. Show all life states
- **Type:** `JCheckBoxMenuItem`
- **Condition:** Always shown.
- **State reflects:** `((CommunicationDiagramOptions) fOpt).isShowLifeStates()`
- **Functionality:** Toggles display of life-state labels inside object boxes. Calls `invalidateContent(true)`.

---

#### *(Separator)*

---

#### 11. Show communication messages
- **Type:** `JCheckBoxMenuItem`
- **Condition:** Always shown.
- **State reflects:** `((CommunicationDiagramOptions) fOpt).isShowCommunicationMessages()`
- **Functionality:** Toggles whether message labels are displayed on link edges. Calls `invalidateContent(true)`.

---

#### 12. Messages navigation
- **Type:** `JMenuItem`
- **Condition:** Always shown.
- **Functionality:** Opens a `MessagesNavigationDialog` for the parent `CommunicationDiagramView`, allowing the user to step forward and backward through messages in the execution trace.

---

#### *(Separator)*

---

#### 13. Show all Commands
- **Type:** `JCheckBoxMenuItem`
- **Condition:** Always shown.
- **State reflects:** `sharedVisibleManager.getData().allEventTypesVisible()`
- **Functionality:** When selected, calls `getSharedVisibleManager().getData().setAllEventTypesVisible(true)`. When deselected, sets it to `false`. Calls `filterGraphByEvent()` in both cases to re-filter visible nodes by event type.

---

#### 14. Commands to show...
- **Type:** `JMenuItem`
- **Condition:** Always shown.
- **Functionality:** Opens a `CmdChooseWindow` where the user can select which command types (event types) to display on the diagram. Calls `createCmdChooseWindow()`.

---

#### *(Separator)*

---

#### 15. Show all messages
- **Type:** `JCheckBoxMenuItem`
- **Condition:** Always shown.
- **State reflects:** `showAllMessages` field on the diagram.
- **Functionality:** When activated, calls `getSharedVisibleManager().getData().setAllEventsVisible()` to mark all events visible, recalculates states via `dataSource.calculateStates()`, updates numbering via `setRelativSequenceNumbers()`, and calls `invalidateContent(true)`. Sets the `showAllMessages` flag to `true`.

---

#### 16. Message selection...
- **Type:** `JMenuItem`
- **Condition:** Always shown.
- **Functionality:** Opens a `MessageSelectionView` window (modal) where the user can select a specific range or subset of messages to display. Calls `createMessageSelectionWindow()`.

---

#### 17. Relative numbering
- **Type:** `JCheckBoxMenuItem`
- **Condition:** Always shown.
- **State reflects:** `resetEnum` field on the diagram.
- **Functionality:** When toggled, calls `resetEnum(state)` to switch between absolute sequence numbering (global index from the start of the trace) and relative numbering (renumbered starting from 1 within the visible window).

---

#### *(Separator)*

---

#### 18. Properties...
- **Type:** `JMenuItem`
- **Condition:** Always shown (appended via `popupMenu.add(...)`).
- **Functionality:** Opens the `PropertiesWindow` for this communication diagram (timing, filters, display options). After the dialog is closed, calls `afterPropertiesClosedAction()` and `invalidateContent(true)`.

---

#### 19. Create synchronized sequence diagram
- **Type:** `JMenuItem`
- **Condition:** Always shown (appended via `popupMenu.add(...)`).
- **Functionality:** Creates a new `SequenceDiagramView` synchronized with this communication diagram via the shared `sharedVisibleManager`. In JavaFX mode, creates and displays a new JavaFX window containing the sequence diagram in a `SwingNode`. In Swing mode, calls `fParent.getMainWindow().createSequenceDiagram(sharedVisibleManager)`.

---

#### *(Separator)*

---

#### 20. Add comment node
- **Type:** `JMenuItem` (from `getMenuItemCommentNode(popupInfo)`)
- **Condition:** Always shown.
- **Functionality:** Creates a new `CommentNode` at the right-click position and adds it to the diagram graph.

---

#### 21. Delete comment node *(conditional)*
- **Type:** `JMenuItem`
- **Condition:** Exactly one node is selected (`fNodeSelection.size() == 1`) AND that node is a `CommentNode`.
- **Functionality:** Calls `getGraph().remove(selected)` to remove the comment node, clears `fNodeSelection`, and calls `repaint()`.

---

#### *(Separator)*

---

#### 22. Align elements
- **Type:** `JMenu` (from `getMenuAlign()`)
- **Condition:** Enabled only when two or more nodes are selected.
- **Functionality:** Same as base class. Six sub-items: Left, Center, Right, Top, Middle, Bottom.

---

#### 23. Anti-aliasing
- **Type:** `JCheckBoxMenuItem` (from `getMenuItemAntiAliasing()`)
- **Condition:** Always shown.
- **Functionality:** Same as base class.

---

#### 24. Show grid
- **Type:** `JCheckBoxMenuItem` (from `getMenuItemShowGrid()`)
- **Condition:** Always shown.
- **Functionality:** Same as base class.

---

#### *(Layout items from `addLayoutMenuItems(popupMenu)` — Auto-Layout, Layouts, Reset layout, Load layout, Save layout, Select all nodes, Select all edges)*

---

---

## 4. StateMachineDiagram

**File:** `use-gui/src/main/java/org/tzi/use/gui/views/diagrams/statemachine/StateMachineDiagram.java`

`StateMachineDiagram` does **not** call `super.unionOfPopUpMenu()`. It creates a brand-new `JPopupMenu` and `PopupMenuInfo`. It reuses `getMenuItemCommentNode()`, `getMenuAlign()`, `getMenuItemAntiAliasing()`, `getMenuItemShowGrid()`, and `addLayoutMenuItems()`.

### Menu items in order of appearance

---

#### 1. Show state invariant *(conditional)*
- **Type:** `JMenuItem`
- **Condition:** `!fNodeSelection.isEmpty()` (at least one node selected). Item is always added when condition holds, but is **enabled** only when `somethingHidden == true` — i.e., at least one selected `StateNode` has `showInvariant == false`.
- **Functionality:** Iterates over `fNodeSelection`; for every `StateNode` found, sets `showInvariant = true` and calls `invalidateNode(sn)`. Calls `repaint()`.

---

#### 2. Hide state invariant *(conditional)*
- **Type:** `JMenuItem`
- **Condition:** `!fNodeSelection.isEmpty()` (at least one node selected). Item is always added when condition holds, but is **enabled** only when `somethingShown == true` — i.e., at least one selected `StateNode` has `showInvariant == true`.
- **Functionality:** Iterates over `fNodeSelection`; for every `StateNode` found, sets `showInvariant = false` and calls `invalidateNode(sn)`. Calls `repaint()`.

---

#### 3. Merge transitions *(submenu)*
- **Type:** `JMenu`
- **Condition:** Always shown.
- **Sub-items:**

  ##### 3a. Merge all possible transitions
  - **Condition:** Always enabled.
  - **Functionality:** Calls `mergeTransitions(transitionMappings.values())` to merge every pair of `TransitionEdge` instances that share the same source and target node. Calls `invalidateContent(true)`.

  ##### 3b. Demerge all possible transitions
  - **Condition:** Always enabled.
  - **Functionality:** Calls `demergeTransitions(transitionMappings.values())` to split every previously merged transition edge back into individual edges. Calls `invalidateContent(true)`.

  ##### *(Separator)*

  ##### 3c. Merge selected transitions
  - **Condition:** Enabled only when `fEdgeSelection.size() > 1` (two or more edges are selected).
  - **Functionality:** Collects all selected `TransitionEdge` instances, then calls `mergeTransitions(edges)` to merge those that share the same source and target. Calls `invalidateContent(true)`.

  ##### 3d. Demerge selected transitions
  - **Condition:** Enabled only when `fEdgeSelection.size() > 0` (at least one edge is selected).
  - **Functionality:** Collects all selected `TransitionEdge` instances, then calls `demergeTransitions(edges)` to split those merged edges back into individual edges. Calls `invalidateContent(true)`.

---

#### *(Separator)*

---

#### 4. Add comment node
- **Type:** `JMenuItem` (from `getMenuItemCommentNode(popupInfo)`)
- **Condition:** Always shown.
- **Functionality:** Creates a new `CommentNode` at the right-click position and adds it to the diagram graph.

---

#### *(Separator)*

---

#### 5. Align elements
- **Type:** `JMenu` (from `getMenuAlign()`)
- **Condition:** Enabled only when two or more nodes are selected.
- **Functionality:** Same as base class. Six sub-items: Left, Center, Right, Top, Middle, Bottom.

---

#### 6. Anti-aliasing
- **Type:** `JCheckBoxMenuItem` (from `getMenuItemAntiAliasing()`)
- **Condition:** Always shown.
- **Functionality:** Same as base class.

---

#### 7. Show grid
- **Type:** `JCheckBoxMenuItem` (from `getMenuItemShowGrid()`)
- **Condition:** Always shown.
- **Functionality:** Same as base class.

---

#### *(Layout items from `addLayoutMenuItems(popupMenu)` — Auto-Layout, Layouts, Reset layout, Load layout, Save layout, Select all nodes, Select all edges)*