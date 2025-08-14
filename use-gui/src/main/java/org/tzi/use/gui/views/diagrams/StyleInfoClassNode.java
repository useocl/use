package org.tzi.use.gui.views.diagrams;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MOperation;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public final class StyleInfoClassNode extends StyleInfoBase {

    private Color nodesColor;
    private Color backgroundColor;
    private final Map<MAttribute, Color> attributeColors = new HashMap<>();
    private final Map<MOperation, Color> operationColors = new HashMap<>();

    @Builder(setterPrefix = "with")
    private StyleInfoClassNode(final Color namesColor,
                               final Color nodesColor,
                               final Color backgroundColor,
                               final Map<MAttribute, Color> attributeColor,
                               final Map<MOperation, Color> operationColor) {
        super(namesColor);
        this.nodesColor = nodesColor;
        this.backgroundColor = backgroundColor;
        Optional.ofNullable(attributeColor).ifPresent(this.attributeColors::putAll);
        Optional.ofNullable(operationColor).ifPresent(this.operationColors::putAll);
    }

    public StyleInfoClassNode(@NonNull final ClassNode classNode) {
        super(classNode.getColor());
        nodesColor = classNode.getColor();
        backgroundColor = classNode.getBackColor();
        attributeColors.putAll(classNode.cls()
                .attributes()
                .stream()
                .filter(attribute -> classNode.getAttributeColor(attribute) != null)
                .collect(Collectors.toMap(Function.identity(), classNode::getAttributeColor)));
        operationColors.putAll(classNode.cls()
                .operations()
                .stream()
                .filter(operation -> classNode.getOperationColor(operation) != null)
                .collect(Collectors.toMap(Function.identity(), classNode::getOperationColor)));
    }

    @Override
    public void merge(@NonNull final StyleInfoBase other) {
        if (other instanceof StyleInfoClassNode styleInfoClassNode) {
            this.namesColor = Optional.ofNullable(this.namesColor).orElse(styleInfoClassNode.namesColor);
            this.nodesColor = Optional.ofNullable(this.nodesColor).orElse(styleInfoClassNode.nodesColor);
            this.backgroundColor = Optional.ofNullable(this.backgroundColor).orElse(styleInfoClassNode.backgroundColor);
            styleInfoClassNode.attributeColors.forEach(attributeColors::putIfAbsent);
            styleInfoClassNode.operationColors.forEach(operationColors::putIfAbsent);
        }
    }
}



