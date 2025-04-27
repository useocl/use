package org.tzi.use.gui.views.diagrams;

public enum DiagramType {
    CLASS_DIAGRAM(true, false, true),
    STATE_MACHINE_DIAGRAM(true, false, true),
    OBJECT_DIAGRAM(true, false, true),
    CLASS_INVARIANT_VIEW(false, false, false),
    OBJECT_COUNT_VIEW(false, false, false),
    LINK_COUNT_VIEW(false, false, false),
    STATE_EVOLUTION_VIEW(false, false, false),
    OBJECT_PROPERTIES_VIEW(false, false, false),
    CLASS_EXTENT_VIEW(false, false, false),
    SEQUENCE_DIAGRAM(true, true, true),
    COMMUNICATION_DIAGRAM(true, false, true),
    CALL_STACK_VIEW(false, false, false),
    COMMAND_LIST_VIEW(false, false, false),
    ASSOCIATION_INFO_VIEW(false, false, false),
    OCL_EVALUATION(false, false, false),
    OTHER(false, false, false);

    private final boolean isDiagram;
    private final boolean isView;
    private final boolean isExportable;

    DiagramType(boolean isDiagram, boolean isView, boolean isExportable) {
        this.isDiagram = isDiagram;
        this.isView = isView;
        this.isExportable = isExportable;
    }

    public boolean isDiagram() { return isDiagram; }
    public boolean isView() { return isView; }
    public boolean isExportable() { return isExportable; }
}