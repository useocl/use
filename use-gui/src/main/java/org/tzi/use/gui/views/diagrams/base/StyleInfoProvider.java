package org.tzi.use.gui.views.diagrams.base;

import lombok.Getter;

@Getter
public abstract class StyleInfoProvider {

    private final Class<? extends DiagramView> targetClass;

    protected StyleInfoProvider(final Class<? extends DiagramView> targetClass) {
        this.targetClass = targetClass;
    }

    public abstract StyleInfoBase getStyleInfoForDiagramElement(final Object identifier);
}
