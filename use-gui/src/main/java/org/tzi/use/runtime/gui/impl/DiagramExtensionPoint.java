package org.tzi.use.runtime.gui.impl;

import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.gui.IDiagramPlugin;
import org.tzi.use.runtime.gui.IPluginDiagramExtensionPoint;

import java.util.ArrayList;
import java.util.List;

public final class DiagramExtensionPoint implements IPluginDiagramExtensionPoint {

    private static final DiagramExtensionPoint INSTANCE = new DiagramExtensionPoint();

    private final List<IDiagramPlugin> registeredPlugins = new ArrayList<>();

    public static IPluginDiagramExtensionPoint getInstance() {
        return INSTANCE;
    }

    private DiagramExtensionPoint() {
    }

    @Override
    public <T extends DiagramView> void registerView(T diagramView) {
        PluginDiagramManipulator.ALL_REGISTERED_DIAGRAM_VIEWS.add(diagramView);
    }

    @Override
    public void runPlugins() {
        for (final IDiagramPlugin diagramPlugin : registeredPlugins) {
            diagramPlugin.getPluginDiagramManipulator().doRun();
        }
    }
    @Override
    public void registerPlugin(final IPluginDescriptor pluginDescriptor) {
        if (pluginDescriptor.getPluginClass() instanceof IDiagramPlugin iDiagramPlugin) {
            registeredPlugins.add(iDiagramPlugin);
        }
    }

}
