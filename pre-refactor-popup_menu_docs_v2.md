> **Pre-refactor documentation** — describes the popup menu code as it was *before* the PopupMenuBuilder refactor.

# DiagramView Popup Menu Control Flow Analysis

## Updated UML Class Diagram

```plantuml
@startuml
interface MouseListener {
  +mousePressed(e: MouseEvent)
  +mouseReleased(e: MouseEvent)
  +mouseClicked(e: MouseEvent)
}

class DiagramInputHandling {
  -fDiagram: DiagramView
  -fNodeSelection: Selection<PlaceableNode>
  -fEdgeSelection: Selection<EdgeBase>
  +mousePressed(e: MouseEvent)
}

abstract class DiagramView {
  +maybeShowPopup(e: MouseEvent): boolean
  #unionOfPopUpMenu(): PopupMenuInfo
  #addLayoutMenuItems(popupMenu: JPopupMenu)
}

class PopupMenuInfo {
  +popupMenu: JPopupMenu
  +position: Point
}

MouseListener <|.. DiagramInputHandling
DiagramInputHandling --> "1" DiagramView : "delegates popup logic to"
DiagramView ..> PopupMenuInfo : "creates"
@enduml
```

## Updated Sequence Diagram

```plantuml
@startuml
actor User
participant "e: MouseEvent" as Event
participant "input: DiagramInputHandling" as Input
participant "diagram: DiagramView" as DV
participant "info: PopupMenuInfo" as PMI
participant "JPopupMenu" as Menu

User -> Event : right-click (trigger popup)
Event -> Input : mousePressed(e)
activate Input

Input -> DV : maybeShowPopup(e)
activate DV

alt e.isPopupTrigger() == true
    DV -> DV : unionOfPopUpMenu()
    activate DV

    create Menu
    DV -> Menu : new JPopupMenu()

    create PMI
    DV -> PMI : new PopupMenuInfo(Menu)
    
    DV -> DV : addLayoutMenuItems(Menu)

    return info : PopupMenuInfo

    DV -> Menu : show(e.getComponent(), e.getX(), e.getY())
    activate Menu
    Menu --> User : Render Popup Menu
    deactivate Menu
    
    DV --> Input : return true
else e.isPopupTrigger() == false
    DV --> Input : return false
end
deactivate DV

alt returned true
    Input --> User : return (short-circuit rest of logic)
else returned false
    Input -> Input : handle selection, dragging, etc.
end
deactivate Input
@enduml
```

## Control Flow Explanation

1. **User Interaction**: The user right-clicks (or performs an OS-specific popup trigger action) on the diagram. This generates a `MouseEvent`.
2. **Input Handling Intercept (DiagramInputHandling)**: The `MouseEvent` is received by `DiagramInputHandling.mousePressed(MouseEvent e)`. The `DiagramInputHandling` class acts as the central controller for all mouse interactions on the diagram surface.
3. **Popup Delegation**: As the very first action in `mousePressed`, `DiagramInputHandling` delegates checking for a popup menu to the UI layer by calling `fDiagram.maybeShowPopup(e)`.
4. **Popup Menu Verification (DiagramView)**: Inside `DiagramView.maybeShowPopup`, the event is checked using the Java generic `e.isPopupTrigger()` method.
    - If `false`, `maybeShowPopup` returns `false`, and `DiagramInputHandling` continues to process the mouse event as a normal click (handling node selection, dragging, double-clicks for resizing, etc.).
5. **Popup Menu Construction**: If `e.isPopupTrigger()` is `true`:
    - `DiagramView` calls `#unionOfPopUpMenu()`, which constructs the base `JPopupMenu`.
    - It wraps it in a `PopupMenuInfo` object.
    - It appends layout-related menu items using `#addLayoutMenuItems(popupMenu)`.
    - It configures the menu with context-aware items based on what elements are currently selected (stored in the diagram's selection state).
6. **Popup Rendering**: `DiagramView` then positions and displays the `JPopupMenu` by invoking `popupMenu.show(...)` directly on the screen.
7. **Execution Short-Circuit**: Back in `DiagramInputHandling`, receiving `true` from `maybeShowPopup` causes `mousePressed` to immediately `return`. This effectively drops the mouse event and explicitly prevents the popup click from triggering node selections, drag operations, or other background logic, beautifully separating the UI popup rendering from the diagram's canvas interaction logic.

