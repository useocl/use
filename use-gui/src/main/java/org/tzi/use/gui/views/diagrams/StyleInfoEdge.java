package org.tzi.use.gui.views.diagrams;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.tzi.use.gui.views.diagrams.elements.AssociationName;
import org.tzi.use.gui.views.diagrams.elements.EdgeProperty;
import org.tzi.use.gui.views.diagrams.elements.Rolename;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.uml.mm.MAssociationEnd;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class StyleInfoEdge extends StyleInfoBase {

    private Color edgeColor;
    private final Map<MAssociationEnd, Color> roleNameColors = new HashMap<>();

    @Builder(setterPrefix = "with")
    private StyleInfoEdge(final Color namesColor, final Color edgeColor, final Map<MAssociationEnd, Color> roleNameColors) {
        super(namesColor);
        this.edgeColor = edgeColor;
        Optional.ofNullable(roleNameColors).ifPresent(this.roleNameColors::putAll);
    }

    public StyleInfoEdge(@NonNull final EdgeBase edgeBase) {
        super(Optional.ofNullable(edgeBase.getPropertiesGrouped().asMap().get(EdgeBase.PropertyOwner.EDGE))
                .flatMap(coll -> coll.stream().filter(AssociationName.class::isInstance).findFirst())
                .map(EdgeProperty::getColor)
                .orElse(null));
        edgeColor = edgeBase.getEdgeColor();
        roleNameColors.putAll(edgeBase.getProperties()
                .stream()
                .filter(Rolename.class::isInstance)
                .map(Rolename.class::cast)
                .filter(rolename -> rolename.getColor() != null)
                .collect(Collectors.toMap(Rolename::getEnd, Rolename::getColor)));
    }

    @Override
    public void merge(@NonNull final StyleInfoBase other) {
        if (other instanceof StyleInfoEdge styleInfoEdge) {
            this.namesColor = Optional.ofNullable(this.namesColor).orElse(styleInfoEdge.getNamesColor());
            this.edgeColor = Optional.ofNullable(this.edgeColor).orElse(styleInfoEdge.getEdgeColor());
            styleInfoEdge.roleNameColors.forEach(roleNameColors::putIfAbsent);
        }
    }
}



