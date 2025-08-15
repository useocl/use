package org.tzi.use.gui.views.diagrams;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.tzi.use.gui.views.diagrams.classdiagram.EnumNode;

import java.awt.Color;
import java.util.Optional;

@Getter
public class StyleInfoEnumNode extends StyleInfoBase {

    private Color frameColor;
    private Color nodesColor;

    @Builder(setterPrefix = "with")
    private StyleInfoEnumNode(final Color namesColor, final Color frameColor, final Color nodesColor) {
        super(namesColor);
        this.frameColor = frameColor;
        this.nodesColor = nodesColor;
    }

    public StyleInfoEnumNode(@NonNull final EnumNode enumNode) {
        super(enumNode.getTextColor());
        this.frameColor = enumNode.getFrameColor();
        this.nodesColor = enumNode.getColor();
    }

    @Override
    public void merge(@NonNull final StyleInfoBase other) {
        if (other instanceof StyleInfoEnumNode styleInfoEnumNode) {
            this.namesColor = Optional.ofNullable(this.namesColor).orElse(styleInfoEnumNode.namesColor);
            this.frameColor = Optional.ofNullable(this.frameColor).orElse(styleInfoEnumNode.frameColor);
            this.nodesColor = Optional.ofNullable(this.nodesColor).orElse(styleInfoEnumNode.nodesColor);
        }
    }
}
