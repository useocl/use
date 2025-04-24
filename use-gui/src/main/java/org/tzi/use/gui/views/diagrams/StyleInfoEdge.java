package org.tzi.use.gui.views.diagrams;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.tzi.use.gui.views.diagrams.elements.AssociationName;
import org.tzi.use.gui.views.diagrams.elements.EdgeProperty;
import org.tzi.use.gui.views.diagrams.elements.Rolename;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class StyleInfoEdge extends StyleInfoBase {

    private Color egdeColor;
    private Map<Rolename, Color> roleNameColors;

    @Builder(setterPrefix = "with")
    private StyleInfoEdge(final Color namesColor, final Color egdeColor, final Map<Rolename, Color> roleNameColors) {
        super(namesColor);
        this.egdeColor = egdeColor;
        this.roleNameColors = Optional.ofNullable(roleNameColors).orElseGet(HashMap::new);
    }

    public StyleInfoEdge(@NonNull final EdgeBase selectable) {
        super(selectable);
    }

    @Override
    protected void createFrom(@NonNull final Selectable selectable) {
        final EdgeBase edgeBase = (EdgeBase) selectable;
        this.namesColor = Optional.ofNullable(edgeBase.getPropertiesGrouped().asMap().get(EdgeBase.PropertyOwner.EDGE))
                .flatMap(coll -> coll.stream().filter(AssociationName.class::isInstance).findFirst())
                .map(EdgeProperty::getColor)
                .orElse(null);
        this.egdeColor = edgeBase.getEdgeColor();
        this.roleNameColors = edgeBase.getProperties()
                .stream()
                .filter(Rolename.class::isInstance)
                .map(Rolename.class::cast)
                .filter(rolename -> rolename.getColor() != null)
                .collect(Collectors.toMap(Function.identity(), Rolename::getColor));
    }

    @Override
    public void merge(@NonNull final StyleInfoBase other) {
        if (other instanceof StyleInfoEdge styleInfoEdge) {
            this.namesColor = Optional.ofNullable(this.namesColor).orElse(styleInfoEdge.getNamesColor());
            this.egdeColor = Optional.ofNullable(this.egdeColor).orElse(styleInfoEdge.getEgdeColor());
            styleInfoEdge.roleNameColors.forEach(roleNameColors::putIfAbsent);
        }
    }
}



