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
     * This constructor is used to create a concrete type of {@link StyleInfoBase}
     *
     * @param selectable the {@link Selectable} to take the information from
     */
    protected StyleInfoBase(@NonNull final Selectable selectable) {
        createFrom(selectable);
    }

    /**
     * Creates a concrete type of {@link StyleInfoBase} from an existing {@link Selectable}.
     *
     * @param selectable the {@link Selectable} to take the information from
     */
    abstract protected void createFrom(@NonNull final Selectable selectable);

    /**
     * Merges the information of two of the same concrete types of {@link StyleInfoBase} into the former.
     * This method prioritizes the {@link StyleInfoBase} it has been called on.
     *
     * @param other the other {@link StyleInfoBase}
     */
    public abstract void merge(@NonNull final StyleInfoBase other);


}
