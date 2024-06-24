package org.tzi.use.runtime.gui.impl;

import org.tzi.use.gui.main.runtime.IPluginGraphExtensionPoint;
import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.gui.IGraphManipulator;

import java.util.ArrayList;
import java.util.List;

public class GraphExtensionPoint implements IPluginGraphExtensionPoint {

    private static final GraphExtensionPoint INSTANCE = new GraphExtensionPoint();

    private final List<IGraphManipulator> registeredPlugins;

    private GraphExtensionPoint() {
        registeredPlugins = new ArrayList<>();
    }

    public static GraphExtensionPoint getInstance() {
        return INSTANCE;
    }

    public void registerPlugin(final IPluginDescriptor pluginDescriptor) {
        final IGraphManipulator graphManipulator = pluginDescriptor.getPluginClass().getGraphManipulator();
        if (graphManipulator != null) {
            registeredPlugins.add(graphManipulator);
        }
    }


    public List<IGraphManipulator> getRegisteredPlugins() {
        return registeredPlugins;
    }
}
