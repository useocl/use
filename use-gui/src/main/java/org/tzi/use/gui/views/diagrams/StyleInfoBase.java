package org.tzi.use.gui.views.diagrams;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;

import java.awt.*;

/**
 * Base properties of Style for all {@link Selectable} objects such as {@link ClassNode}, {@link EdgeBase} and more.
 */
@Getter
@AllArgsConstructor
public abstract class StyleInfoBase {

    protected Color namesColor;
    //protected Color namesFont;

    /**
     * Merges the information of two of the same concrete types of {@link StyleInfoBase} into the former.
     * This method prioritizes the {@link StyleInfoBase} it has been called on.
     *
     * @param other the other {@link StyleInfoBase}
     */
    public abstract void merge(@NonNull final StyleInfoBase other);


}
