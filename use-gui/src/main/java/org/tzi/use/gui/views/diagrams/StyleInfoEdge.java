package org.tzi.use.gui.views.diagrams;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;

import java.awt.*;
import java.util.Optional;

@Getter
public class StyleInfoEdge extends StyleInfoBase {

    private Color egdeColor;
    private Color associationSourceRolenameColor;
    private Color associationTargetRolenameColor;

    @Builder(setterPrefix = "with")
    private StyleInfoEdge(final Color namesColor, final Color egdeColor, final Color associationSourceRolenameColor, final Color associationTargetRolenameColor) {
        super(namesColor);
        this.egdeColor = egdeColor;
        this.associationSourceRolenameColor = associationSourceRolenameColor;
        this.associationTargetRolenameColor = associationTargetRolenameColor;
    }

    public StyleInfoEdge(@NonNull final EdgeBase selectable) {
        super(selectable);
    }

    @Override
    protected void createFrom(@NonNull final Selectable selectable) {
        //TODO
    }

    @Override
    public void merge(@NonNull final StyleInfoBase other) {
        if (other instanceof StyleInfoEdge styleInfoEdge) {
            this.namesColor = Optional.ofNullable(this.namesColor).orElse(styleInfoEdge.getNamesColor());
            this.egdeColor = Optional.ofNullable(this.egdeColor).orElse(styleInfoEdge.getEgdeColor());
            this.associationSourceRolenameColor = Optional.ofNullable(this.associationSourceRolenameColor).orElse(styleInfoEdge.getAssociationSourceRolenameColor());
            this.associationTargetRolenameColor = Optional.ofNullable(this.associationTargetRolenameColor).orElse(styleInfoEdge.getAssociationTargetRolenameColor());
        }
    }
}



