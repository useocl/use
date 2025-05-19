package org.tzi.use.runtime.gui.impl;

import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.gui.IDiagramPlugin;
import org.tzi.use.runtime.gui.IPluginDiagramExtensionPoint;

import java.util.*;

public class DiagramExtensionPoint implements IPluginDiagramExtensionPoint {

    private static final DiagramExtensionPoint INSTANCE = new DiagramExtensionPoint();

    public static IPluginDiagramExtensionPoint getInstance() {
        return INSTANCE;
    }


    private final List<IDiagramPlugin> registeredPlugins = new ArrayList<>();

    private final Map<Class<? extends DiagramView>, List<StyleInfoProvider>> styleInfoProviders = new HashMap<>();

    @Override
    public <T extends DiagramView> void registerView(T diagramView) {
        PluginDiagramManipulator.ALL_REGISTERED_DIAGRAM_VIEWS.add(diagramView);
    }

    @Override
    public <T extends DiagramView> void removeView(T diagramView) {
        PluginDiagramManipulator.ALL_REGISTERED_DIAGRAM_VIEWS.remove(diagramView);
    }

    @Override
    public void runPluginsOnInitialisation() {
        registeredPlugins.stream().map(IDiagramPlugin::getPluginDiagramManipulator).filter(Objects::nonNull).forEach(PluginDiagramManipulator::onInitialisation);
    }

    @Override
    public void runPluginsOnClosure() {
        registeredPlugins.stream().map(IDiagramPlugin::getPluginDiagramManipulator).filter(Objects::nonNull).forEach(PluginDiagramManipulator::onClosure);
    }

    @Override
    public void addStyleInfoProvider(final StyleInfoProvider styleInfoProvider) {
        final Class<? extends DiagramView> targetClass = styleInfoProvider.getTargetClass();

        // if an entry for this target class already exists: add, else: skip
        styleInfoProviders.computeIfPresent(targetClass, (key, value) -> {
            value.add(styleInfoProvider);
            return value;
        });
        // if no entry exists: create, else: skip
        styleInfoProviders.putIfAbsent(targetClass, new ArrayList<>(List.of(styleInfoProvider)));
    }

    @Override
    public void removeStyleInfoProvider(final StyleInfoProvider styleInfoProvider) {
        styleInfoProviders.get(styleInfoProvider.getTargetClass()).remove(styleInfoProvider);
    }

    @Override
    public List<StyleInfoProvider> getStyleInfoProvider(final Class<? extends DiagramView> targetClass) {
        return styleInfoProviders.getOrDefault(targetClass, List.of());
    }

    @Override
    public void registerPlugin(final IPluginDescriptor pluginDescriptor) {
        if (pluginDescriptor.getPluginClass() instanceof IDiagramPlugin iDiagramPlugin) {
            registeredPlugins.add(iDiagramPlugin);
        }
    }

}
