package org.tzi.use.gui.views.diagrams;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.gui.views.diagrams.classdiagram.EnumNode;
import org.tzi.use.gui.views.diagrams.elements.Rolename;

import java.awt.*;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Getter
public class StyleInfoEnumNode extends StyleInfoPlaceableNode {

    private Color frameColor;
    private Color nodesColor;

    @Builder(setterPrefix = "with")
    private StyleInfoEnumNode(Color namesColor, Map<Rolename, Color> roleNameColorMap, Color frameColor, Color nodesColor) {
        super(namesColor, roleNameColorMap);
        this.frameColor = frameColor;
        this.nodesColor = nodesColor;
    }

    /**
     * Creates a {@link StyleInfoEnumNode} of an existing {@link EnumNode}.
     *
     * @param enumNode the {@link ClassNode} to take the information from
     * @return {@link StyleInfoEnumNode} with the information of the given {@link ClassNode}
     */
    public static StyleInfoEnumNode createFromEnumNode(@NonNull final EnumNode enumNode) {
        return new StyleInfoEnumNode(enumNode.getTextColor(), Collections.emptyMap(), enumNode.getFrameColor(), enumNode.getColor());
    }

    private void merge(@NonNull final StyleInfoEnumNode other) {
        this.namesColor = Optional.ofNullable(this.namesColor).orElse(other.namesColor);
        this.frameColor = Optional.ofNullable(this.frameColor).orElse(other.frameColor);
        this.nodesColor = Optional.ofNullable(this.nodesColor).orElse(other.nodesColor);
    }

    /**
     * Merges the information of two {@link StyleInfoEnumNode}
     * This method prioritizes the {@link StyleInfoEnumNode} it has been called on.
     *
     * @param other the other {@link StyleInfoEnumNode}
     */
    @Override
    public void merge(@NonNull StyleInfoBase other) {
        super.merge(other);
        if (other instanceof StyleInfoEnumNode styleInfoEnumNode) {
            merge(styleInfoEnumNode);
        }
    }
}
