package org.tzi.use.gui.views.diagrams;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.tzi.use.gui.views.diagrams.elements.Rolename;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public class StyleInfoPlaceableNode extends StyleInfoBase {

    private final Map<Rolename, Color> roleNameColorMap = new HashMap<>();

    @Builder(setterPrefix = "with", builderMethodName = "SIPlaceableNodeBuilder")
    protected StyleInfoPlaceableNode(Color namesColor, Map<Rolename, Color> roleNameColorMap) {
        super(namesColor);
        Optional.ofNullable(roleNameColorMap).ifPresent(this.roleNameColorMap::putAll);
    }

    @Override
    public void merge(@NonNull StyleInfoBase other) {
        if (other instanceof StyleInfoPlaceableNode styleInfoPlaceableNode) {
            styleInfoPlaceableNode.getRoleNameColorMap().forEach(roleNameColorMap::putIfAbsent);
        }
    }


}



