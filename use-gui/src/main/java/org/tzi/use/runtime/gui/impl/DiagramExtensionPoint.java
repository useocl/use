package org.tzi.use.runtime.gui.impl;

import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.gui.DiagramPlugin;
import org.tzi.use.runtime.gui.IPluginDiagramExtensionPoint;
import org.tzi.use.util.Log;

import java.util.*;

public class DiagramExtensionPoint implements IPluginDiagramExtensionPoint {

    private static final DiagramExtensionPoint INSTANCE = new DiagramExtensionPoint();

    public static IPluginDiagramExtensionPoint getInstance() {
        return INSTANCE;
    }


    private final List<DiagramPlugin> registeredPlugins = new ArrayList<>();

    @Override
    public <T extends DiagramView> void registerView(T diagramView) {
        PluginDiagramManipulator.ALL_REGISTERED_DIAGRAM_VIEWS.add(diagramView);
    }

    @Override
    public <T extends DiagramView> void removeView(T diagramView) {
        PluginDiagramManipulator.ALL_REGISTERED_DIAGRAM_VIEWS.remove(diagramView);
    }

    @Override
    public void runPluginsOnStart() {
        registeredPlugins.stream().map(DiagramPlugin::getPluginDiagramManipulators).flatMap(Collection::stream).forEach(PluginDiagramManipulator::onStart);
    }

    @Override
    public void runPluginsOnClose() {
        registeredPlugins.stream().map(DiagramPlugin::getPluginDiagramManipulators).flatMap(Collection::stream).forEach(PluginDiagramManipulator::onClose);
    }

    @Override
    public List<StyleInfoProvider> getStyleInfoProvider(final Class<? extends DiagramView> targetClass) {
        return registeredPlugins.stream()
                                .map(DiagramPlugin::getPluginDiagramManipulators)
                                .flatMap(Collection::stream)
                                .filter(pluginDiagramManipulator -> pluginDiagramManipulator.getTargetClass().equals(targetClass))
                                .map(PluginDiagramManipulator::getStyleInfoProvider)
                                .filter(Objects::nonNull)
                                .toList();
    }

    @Override
    public void registerDiagramManipulators(final IPluginDescriptor pluginDescriptor) {
        if (pluginDescriptor.getPluginClass() instanceof DiagramPlugin diagramPlugin) {
            registeredPlugins.add(diagramPlugin);
            Log.debug(String.format("Registered plugin '%s' to %s", diagramPlugin.getName(), this.getClass().getSimpleName()));
        }
    }

}
