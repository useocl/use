package org.tzi.use.gui.views.diagrams;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.gui.views.diagrams.elements.Rolename;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MOperation;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public final class StyleInfoClassNode extends StyleInfoPlaceableNode {

    private final Map<MAttribute, Color> attributeColors = new HashMap<>();
    private final Map<MOperation, Color> operationColors = new HashMap<>();
    private Color nodesColor;
    private Color backgroundColor;

    @Builder(setterPrefix = "with")
    private StyleInfoClassNode(Color namesColor, Map<Rolename, Color> roleNameColorMap, Color nodesColor, Color backgroundColor, Map<MAttribute, Color> attributeColor, Map<MOperation, Color> operationColor) {
        super(namesColor, roleNameColorMap);
        this.nodesColor = nodesColor;
        this.backgroundColor = backgroundColor;
        Optional.ofNullable(attributeColor).ifPresent(this.attributeColors::putAll);
        Optional.ofNullable(operationColor).ifPresent(this.operationColors::putAll);
    }

    /**
     * Creates a {@link StyleInfoClassNode} of an existing {@link ClassNode}.
     *
     * @param classNode the {@link ClassNode} to take the information from
     * @return {@link StyleInfoClassNode} with the information of the given {@link ClassNode}
     */
    public static StyleInfoClassNode createFromClassNode(@NonNull final ClassNode classNode) {
        final Map<MAttribute, Color> attributeColors = classNode.cls()
                .allAttributes()
                .stream()
                .filter(attribute -> classNode.getAttributeColor(attribute) != null)
                .collect(Collectors.toMap(Function.identity(), classNode::getAttributeColor));
        final Map<MOperation, Color> operationColors = classNode.cls()
                .allOperations()
                .stream()
                .filter(operation -> classNode.getOperationColor(operation) != null)
                .collect(Collectors.toMap(Function.identity(), classNode::getOperationColor));

        // TODO: extract roleNames
        return new StyleInfoClassNode(classNode.getTextColor(), new HashMap<>(), classNode.getColor(), classNode.getBackColor(), attributeColors, operationColors);
    }

    private void merge(@NonNull final StyleInfoClassNode other) {
        this.namesColor = Optional.ofNullable(this.namesColor).orElse(other.namesColor);
        this.nodesColor = Optional.ofNullable(this.nodesColor).orElse(other.nodesColor);
        this.backgroundColor = Optional.ofNullable(this.backgroundColor).orElse(other.backgroundColor);
        other.attributeColors.forEach(attributeColors::putIfAbsent);
        other.operationColors.forEach(operationColors::putIfAbsent);
    }

    /**
     * Merges the information of two {@link StyleInfoClassNode}
     * This method prioritizes the {@link StyleInfoClassNode} it has been called on.
     *
     * @param other the other {@link StyleInfoClassNode}
     */
    @Override
    public void merge(@NonNull StyleInfoBase other) {
        super.merge(other);
        if (other instanceof StyleInfoClassNode styleInfoClassNode) {
            merge(styleInfoClassNode);
        }
    }

}



