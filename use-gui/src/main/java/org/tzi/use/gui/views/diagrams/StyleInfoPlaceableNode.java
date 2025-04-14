package org.tzi.use.gui.views.diagrams;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.tzi.use.gui.views.diagrams.elements.Rolename;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Getter
public class StyleInfoPlaceableNode extends StyleInfoBase {

    protected Map<Rolename, Color> roleNameColorMap = new HashMap<>();

    @Builder(setterPrefix = "with", builderMethodName = "SIPlaceableNodeBuilder")
    protected StyleInfoPlaceableNode(Color namesColor, Map<Rolename, Color> roleNameColorMap) {
        super(namesColor);
        if (roleNameColorMap != null) {
            this.roleNameColorMap.putAll(roleNameColorMap);
        }
    }

    @Override
    public void merge(@NonNull StyleInfoBase other) {
        if (other instanceof StyleInfoPlaceableNode styleInfoPlaceableNode) {
            styleInfoPlaceableNode.getRoleNameColorMap().forEach(roleNameColorMap::putIfAbsent);
        }
    }


}



