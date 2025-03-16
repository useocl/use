package org.tzi.use.gui.views.diagrams;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;

import java.awt.*;
import java.util.Optional;

@Getter
public class StyleInfoEdge extends StyleInfoBase {

    private Color egdeColor;

    private Color associationSourceRolenameColor;

    private Color associationTargetRolenameColor;

    @Builder(setterPrefix = "with")
    private StyleInfoEdge(Color namesColor, Color egdeColor, Color associationSourceRolenameColor, Color associationTargetRolenameColor) {
        super(namesColor);
        this.egdeColor = egdeColor;
        this.associationSourceRolenameColor = associationSourceRolenameColor;
        this.associationTargetRolenameColor = associationTargetRolenameColor;
    }

    /**
     * Creates a {@link StyleInfoEdge} from an existing {@link EdgeBase}.
     *
     * @param edgeBase the {@link EdgeBase} to take the information from
     * @return {@link StyleInfoEdge} with the information of the given {@link EdgeBase}
     */
    public static StyleInfoEdge createFromEdge(final EdgeBase edgeBase) {
      return StyleInfoEdge.builder().build();
    }

    /**
     * Merges the information of two {@link StyleInfoEdge}
     * This method prioritizes the {@link StyleInfoEdge} it has been called on.
     *
     * @param other the other {@link StyleInfoEdge}
     */
    private void merge(final StyleInfoEdge other) {
        this.namesColor = Optional.ofNullable(this.namesColor).orElse(other.getNamesColor());
        this.egdeColor = Optional.ofNullable(this.egdeColor).orElse(other.getEgdeColor());
        this.associationSourceRolenameColor = Optional.ofNullable(this.associationSourceRolenameColor).orElse(other.getAssociationSourceRolenameColor());
        this.associationTargetRolenameColor = Optional.ofNullable(this.associationTargetRolenameColor).orElse(other.getAssociationTargetRolenameColor());
    }

    @Override
    public void merge(@NonNull final StyleInfoBase other) {
        if (other instanceof StyleInfoEdge styleInfoEdge) {
            merge(styleInfoEdge);
        }
    }
}



