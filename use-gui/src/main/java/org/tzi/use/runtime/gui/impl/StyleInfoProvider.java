package org.tzi.use.runtime.gui.impl;

import lombok.Getter;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.StyleInfoBase;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;

public abstract class StyleInfoProvider {
    @Getter
    private final Class<? extends DiagramView> targetClass;

    protected StyleInfoProvider(final Class<? extends DiagramView> targetClass) {
        this.targetClass = targetClass;
    }

    public final StyleInfoBase getStyleInfoForDiagramElement(final PlaceableNode placeableNode, StyleInfoBase currentStyleInfo) {
        final StyleInfoBase styleInfoForMModelElement = getStyleInfoForDiagramElement(placeableNode);

        // add this provider changes to the current StyleInfo
        if (currentStyleInfo != null) {
            // unsupported
        }

        return styleInfoForMModelElement;
    }

    protected abstract StyleInfoBase getStyleInfoForDiagramElement(final PlaceableNode placeableNode);
}
