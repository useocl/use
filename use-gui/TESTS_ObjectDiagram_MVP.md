# Testfälle: NewObjectDiagram / Presenter (MVP)

Diese Datei enthält 10 fokussierte Testfälle, die ausschließlich `NewObjectDiagram`, `NewObjectDiagramPresenterImpl` und die direkt beteiligten Komponenten (`NewObjectDiagramView`, `NewObjectDiagramModel`, `PlacementRepository`, `ContextMenuProvider`, `ApplicationController`) betreffen.

Hinweis: Für die Log-Verifizierung werden Einträge im Verzeichnis `use_refactor_logs` erwartet (z. B. `%USERPROFILE%\use_refactor_logs\t01.log`).

| Test-ID | Scope | Vorbedingung | Schritte | Erwartete Log-Einträge (Beispiele) | Verifikation |
|---|---|---|---|---|---|
| Test-01 | Presenter-Initialisierung | `NewObjectDiagramView.initDiagram()` wird aufgerufen | Öffne die Objektdiagram-View (View init) | `NewObjectDiagramPresenterImpl - constructed`<br>`NewObjectDiagram.setPresenter - presenter attached for test=...`<br>`LOGGER TEST` (ctor) | Alle drei Einträge erscheinen in `<TEST>.log` |
| Test-02 | Create Object → Presenter → ApplicationController | Diagramm geöffnet | Erzeuge Objekt via View (z. B. Create A) | `onCreateObject called` (presenter) <br>Controller-spezifische Logs (z. B. create invocation) | Presenter-Log vorhanden und Controller-Aufruf sichtbar oder Objekt im Model vorhanden |
| Test-03 | Show Object Properties flow | Mind. ein Objekt vorhanden | Rechtsklick auf Objekt → Properties (oder Doppelklick) | `NewObjectDiagram - invoking presenter.onShowObjectProperties for object=...`<br>`onShowObjectProperties object=...`<br>`DefaultApplicationController.showObjectProperties - object=...` | Reihenfolge: Presenter → Controller; ObjectPropertiesView zeigt Objekt |
| Test-04 | Insert Link | Zwei Objekte (A,B) vorhanden | Insert Link via Context/Toolbar zwischen A und B | `onInsertLink association=... participants=2` | Link erscheint; Log zeigt participants=2 |
| Test-05 | Delete Link | Ein Link zwischen A und B existiert | Delete Link via Context/Toolbar | `onDeleteLink link=...` | Link ist entfernt; Log enthält `onDeleteLink` |
| Test-06 | Delete Objects (Multi) | Mehrere Objekte auswählbar | Wähle A,B → Delete objects | `onDeleteObjects count=2` | Objekte sind entfernt; Log zeigt count |
| Test-07 | Store / Restore Layout (PlacementRepository) | Nodes sind manuell positioniert | 1) Save layout → 2) Reset positions → 3) Restore layout | `onStoreLayout called`<br>`onRestoreLayout called version=...` | Nach Restore sind Positionen wiederhergestellt; Logs enthalten Store+Restore |
| Test-08 | Hide / Show Links & Objects | Links/Objects vorhanden | Hide links → Show all links → Hide objects (crop) | `onHideLinks count=...`<br>`onShowAllLinks called`<br>`onCropSelection count=...` | Visueller Status entspricht Aktionen; Logs enthalten counts |
| Test-09 | Highlight / Transition Events (EventBus) | System kann Events senden | 1) Trigger TransitionEvent (z. B. Attributänderung) <br>2) Trigger HighlightChangeEvent | `onTransition event source=...`<br>`onHighlightChange called event=...` | Presenter empfängt Events; Diagramm wird refreshed; Logs vorhanden |
| Test-10 | Full Reset / Refresh Flow | Diagramm in beliebigem Zustand | 1) Reset Diagram → 2) Refresh (z. B. SortChange) | `onResetDiagram called options=...`<br>`onRefreshRequested called`<br>`onStatusMessage: Diagram reset` | Diagramm ist zurückgesetzt; Status-Logs vorhanden |

---

Kurze Ausführungs- und Prüf-Hinweise

- Setze vor dem Start die Umgebungsvariable `TEST_NAME` oder übergebe sie beim Start, z. B. mit dem bereitgestellten Script:

```powershell
Push-Location "C:\Users\ahmed\IdeaProjects\use\use-gui\src\main\resources\bin"
$env:TEST_NAME = 't01'
.\start_with_testlog.bat t01 > C:\temp\use_start_t01.txt 2>&1
Pop-Location
```

- Live-Tail des Logs während Interaktion:

```powershell
Get-Content -Path "$env:USERPROFILE\use_refactor_logs\t01.log" -Wait -Tail 20
```

- Prüfe die erwarteten Log-Muster in der Datei `%USERPROFILE%\use_refactor_logs\<TEST>.log` oder in der Working-Copy `...\bin\use_refactor_logs\<TEST>.log`.

Wenn du möchtest, kann ich jetzt:
- A) Für jeden Test ein kurzes PowerShell-Checkskript erzeugen, das die erwarteten Log-Patterns prüft (einfaches text-suchen), oder
- B) Die 10 Tests als separate `.md`-Files unter `use-gui/tests/` ablegen, oder
- C) Sofort Test-2..Test-10 Einträge in die Logs wie bei Test-1 schreiben.

Antworte bitte mit `A`, `B` oder `C` (oder `Stop`).

