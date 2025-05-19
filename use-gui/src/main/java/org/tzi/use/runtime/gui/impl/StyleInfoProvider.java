package org.tzi.use.runtime.gui.impl;

import lombok.Getter;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.StyleInfoBase;
import org.tzi.use.gui.views.diagrams.UIElementIntermediate;

@Getter
public abstract class StyleInfoProvider {

    private final Class<? extends DiagramView> targetClass;

    protected StyleInfoProvider(final Class<? extends DiagramView> targetClass) {
        this.targetClass = targetClass;
    }

    public abstract StyleInfoBase getStyleInfoForDiagramElement(final UIElementIntermediate<?> uiElementIntermediate);
}
