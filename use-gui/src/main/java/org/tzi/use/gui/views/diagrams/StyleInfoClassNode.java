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

    private final Map<MAttribute, Color> attributeColor = new HashMap<>();
    private final Map<MOperation, Color> operationColor = new HashMap<>();
    private Color nodesColor;
    private Color backgroundColor;

    @Builder(setterPrefix = "with")
    private StyleInfoClassNode(Color namesColor, Map<Rolename, Color> roleNameColorMap, Color nodesColor, Color backgroundColor, Map<MAttribute, Color> attributeColor, Map<MOperation, Color> operationColor) {
        // RoleNames are not supported as of yet
        super(namesColor, roleNameColorMap);
        this.nodesColor = nodesColor;
        this.backgroundColor = backgroundColor;
        Optional.ofNullable(attributeColor).ifPresent(this.attributeColor::putAll);
        Optional.ofNullable(operationColor).ifPresent(this.operationColor::putAll);
    }

    /**
     * Creates a {@link StyleInfoClassNode} of an existing {@link ClassNode}.
     *
     * @param classNode the {@link ClassNode} to take the information from
     * @return {@link StyleInfoClassNode} with the information of the given {@link ClassNode}
     */
    public static StyleInfoClassNode createFromClassNode(@NonNull final ClassNode classNode) {
        final Map<MAttribute, Color> attributeColor = classNode.cls()
                                                               .allAttributes()
                                                               .stream()
                                                               .collect(Collectors.toMap(Function.identity(), classNode::getAttributeColor));
        final Map<MOperation, Color> operationColor = classNode.cls()
                                                               .allOperations()
                                                               .stream()
                                                               .collect(Collectors.toMap(Function.identity(), classNode::getOperationColor));
        ;
        return new StyleInfoClassNode(classNode.getTextColor(), new HashMap<>(), classNode.getColor(), classNode.getBackColor(), attributeColor, operationColor);
    }

    /**
     * Merges the information of two {@link StyleInfoClassNode}
     * This method prioritizes the {@link StyleInfoClassNode} it has been called on.
     *
     * @param other the other {@link StyleInfoClassNode}
     */
    private void merge(@NonNull final StyleInfoClassNode other) {
        this.namesColor = Optional.ofNullable(this.namesColor).orElse(other.getNamesColor());
        this.nodesColor = Optional.ofNullable(this.nodesColor).orElse(other.getNodesColor());
        this.backgroundColor = Optional.ofNullable(this.backgroundColor).orElse(other.getBackgroundColor());
        other.getAttributeColor().forEach(attributeColor::putIfAbsent);
        other.getOperationColor().forEach(operationColor::putIfAbsent);
    }


    @Override
    public void merge(@NonNull StyleInfoBase other) {
        super.merge(other);
        if (other instanceof StyleInfoClassNode styleInfoClassNode) {
            merge(styleInfoClassNode);
        }
    }
}



