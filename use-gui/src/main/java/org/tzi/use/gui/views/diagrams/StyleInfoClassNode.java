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
    //TODO: revert changes of nullable maps
    private Map<MAttribute, Color> attributeColors;
    private Map<MOperation, Color> operationColors;

    @Builder(setterPrefix = "with")
    private StyleInfoClassNode(final Color namesColor,
                               final Color nodesColor,
                               final Color backgroundColor,
                               final Map<MAttribute, Color> attributeColor,
                               final Map<MOperation, Color> operationColor) {
        super(namesColor);
        this.nodesColor = nodesColor;
        this.backgroundColor = backgroundColor;
        this.attributeColors = Optional.ofNullable(attributeColor).orElseGet(HashMap::new);
        this.operationColors = Optional.ofNullable(operationColor).orElseGet(HashMap::new);
    }


    public StyleInfoClassNode(@NonNull final ClassNode classNode) {
        super(classNode.getColor());
        this.nodesColor = classNode.getColor();
        this.backgroundColor = classNode.getBackColor();
        this.attributeColors = classNode.cls()
                .attributes()
                .stream()
                .filter(attribute -> classNode.getAttributeColor(attribute) != null)
                .collect(Collectors.toMap(Function.identity(), classNode::getAttributeColor));
        this.operationColors = classNode.cls()
                .operations()
                .stream()
                .filter(operation -> classNode.getOperationColor(operation) != null)
                .collect(Collectors.toMap(Function.identity(), classNode::getOperationColor));
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



