package org.tzi.use.runtime.gui.impl;

import lombok.Getter;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.Selectable;
import org.tzi.use.gui.views.diagrams.StyleInfoBase;

@Getter
public abstract class StyleInfoProviderOld<T extends Selectable, S extends StyleInfoBase> {

    private final Class<T> targetElement;

    private final Class<? extends DiagramView> targetClass;

    protected StyleInfoProviderOld(final Class<T> targetDiagramElement, final Class<? extends DiagramView> targetClass) {
        this.targetElement = targetDiagramElement;
        this.targetClass = targetClass;
    }

    public final S getStyleInfoForDiagramElement(final T diagramElement, S othersStyleInfo) {
        final S styleInfoForMModelElement = getStyleInfoForDiagramElement(diagramElement);

        // add this provider changes to the current StyleInfo
        if (othersStyleInfo != null) {
//            othersStyleInfo.merge
        }

        return styleInfoForMModelElement;
    }

    public abstract S getStyleInfoForDiagramElement(final T diagramElement);
}
