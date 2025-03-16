package org.tzi.use.gui.views.diagrams;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.awt.*;

/**
 * Base properties of Style for all drawable objects (such as Associations, Nodes, etc.)
 */
@Getter
@AllArgsConstructor
public abstract class StyleInfoBase {

    protected Color namesColor;
    //protected Color namesFont;

    public abstract void merge(@NonNull final StyleInfoBase other);

}
