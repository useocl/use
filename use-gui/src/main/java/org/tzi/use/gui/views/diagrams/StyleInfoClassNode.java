package org.tzi.use.gui.views.diagrams;

import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.util.Optional;

@Getter
public class StyleInfoClassNode extends StyleInfoBase {
    private Color backgroundColor;
    private final Color[] attributeColor;
    private final Color[] operationColor;

    @Builder(setterPrefix = "with")
    private StyleInfoClassNode(Color namesColor, Color backgroundColor, Color[] attributeColor, Color[] operationColor) {
        super(namesColor);
        this.backgroundColor = backgroundColor;
        this.attributeColor = attributeColor;
        this.operationColor = operationColor;
    }

    public StyleInfoClassNode merge(final StyleInfoClassNode other) {
        // prioritizes changes of other
        this.namesColor = Optional.ofNullable(other.getNamesColor()).orElse(this.namesColor);
        this.backgroundColor = Optional.ofNullable(other.getBackgroundColor()).orElse(this.backgroundColor);
        mergeAttributeColors(other.getAttributeColor());
        mergeOperationColors(other.operationColor);
        return this;
    }

    public StyleInfoClassNode mergeAttributeColors(Color[] otherAttributeColors) {
        // prioritizes changes of other
        assert this.attributeColor.length == otherAttributeColors.length;
        for (int i = 0; i < this.attributeColor.length; i++) {
            this.attributeColor[i] = Optional.ofNullable(otherAttributeColors[i]).orElse(this.attributeColor[i]);
        }
        return this;
    }

    public StyleInfoClassNode mergeOperationColors(Color[] otherOperationColors) {
        // prioritizes changes of other
        assert this.operationColor.length == otherOperationColors.length;
        for (int i = 0; i < this.operationColor.length; i++) {
            this.operationColor[i] = Optional.ofNullable(otherOperationColors[i]).orElse(this.operationColor[i]);
        }
        return this;
    }
}



