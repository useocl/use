package org.tzi.use.gui.views.diagrams;


import lombok.Getter;

import java.awt.*;

/**
 * Base properties of Style for all drawable objects (such as Associations, Nodes, etc.)
 */
@Getter
public abstract class StyleInfoBase {


    protected Color namesColor;
    //protected Color namesFont;

    protected StyleInfoBase(Color namesColor) {
        this.namesColor = namesColor;
    }

    //<T extends StyleInfoBase> T merge(T other);
    //StyleInfoClassNode merge(StyleInfoClassNode other);
}
