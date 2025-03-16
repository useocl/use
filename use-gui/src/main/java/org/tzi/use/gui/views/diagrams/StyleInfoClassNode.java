package org.tzi.use.gui.views.diagrams;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.gui.views.diagrams.elements.Rolename;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public final class StyleInfoClassNode extends StyleInfoPlaceableNode {

    private Color backgroundColor;

    //TODO: instead of implicit mapping via index, use a map with direct mapping
    private final Color[] attributeColor;

    private final Color[] operationColor;

    @Builder(setterPrefix = "with")
    private StyleInfoClassNode(Color namesColor, Map<Rolename, Color> roleNameColorMap, Color backgroundColor, Color[] attributeColor, Color[] operationColor) {
        // RoleNames are not supported as of yet
        super(namesColor, roleNameColorMap);
        this.backgroundColor = backgroundColor;
        this.attributeColor = attributeColor;
        this.operationColor = operationColor;
    }

    /**
     * Creates a {@link StyleInfoClassNode} from an existing {@link ClassNode}.
     *
     * @param classNode the {@link ClassNode} to take the information from
     * @return {@link StyleInfoClassNode} with the information of the given {@link ClassNode}
     */
    public static StyleInfoClassNode createFromClassNode(final ClassNode classNode) {
        final Color[] attributeColor = classNode.cls().allAttributes().stream().map(classNode::getAttributeColor).toList().toArray(new Color[0]);
        final Color[] operationColor = classNode.cls().allOperations().stream().map(classNode::getOperationColor).toList().toArray(new Color[0]);
        return new StyleInfoClassNode(classNode.getTextColor(), new HashMap<>(), classNode.getBackColor(), attributeColor, operationColor);
    }

    /**
     * Merges the information of two {@link StyleInfoClassNode}
     * This method prioritizes the {@link StyleInfoClassNode} it has been called on.
     *
     * @param other the other {@link StyleInfoClassNode}
     */
    private void merge(final StyleInfoClassNode other) {
        this.namesColor = Optional.ofNullable(this.namesColor).orElse(other.getNamesColor());
//        this.roleNameColorMap = Optional.ofNullable(this.roleNameColorMap).orElse(other.getRoleNameColorMap());
        this.backgroundColor = Optional.ofNullable(this.backgroundColor).orElse(other.getBackgroundColor());
        mergeAttributeColors(other.getAttributeColor());
        mergeOperationColors(other.getOperationColor());
    }

    /**
     * Merges the attribute colors fo two {@link StyleInfoClassNode}s.
     * This method prioritizes the {@link StyleInfoClassNode} it has been called on.
     *
     * @param otherAttributeColors the other attribute colors
     */
    private void mergeAttributeColors(final Color[] otherAttributeColors) {
        assert this.attributeColor.length == otherAttributeColors.length;
        for (int i = 0; i < this.attributeColor.length; i++) {
            this.attributeColor[i] = Optional.ofNullable(this.attributeColor[i]).orElse(otherAttributeColors[i]);
        }
    }

    /**
     * Merges the operation colors fo two {@link StyleInfoClassNode}s.
     * This method prioritizes the {@link StyleInfoClassNode} it has been called on.
     *
     * @param otherOperationColors the other operation colors
     */
    private void mergeOperationColors(final Color[] otherOperationColors) {
        assert this.operationColor.length == otherOperationColors.length;
        for (int i = 0; i < this.operationColor.length; i++) {
            this.operationColor[i] = Optional.ofNullable(this.operationColor[i]).orElse(otherOperationColors[i]);
        }
    }

    @Override
    public void merge(@NonNull StyleInfoBase other) {
        super.merge(other);
        if (other instanceof StyleInfoClassNode styleInfoClassNode) {
            merge(styleInfoClassNode);
        }
    }
}



