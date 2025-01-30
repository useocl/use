package org.tzi.use.runtime.gui.impl;

import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.gui.IDiagramPlugin;
import org.tzi.use.runtime.gui.IPluginDiagramExtensionPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DiagramExtensionPoint implements IPluginDiagramExtensionPoint {

    private static final DiagramExtensionPoint INSTANCE = new DiagramExtensionPoint();

    private final List<IDiagramPlugin> registeredPlugins = new ArrayList<>();

    private final Map<Class<? extends DiagramView>, List<StyleInfoProvider>> styleInfoProviders = new HashMap<>();

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
    public void runPluginsOnInitialisation() {
        for (final IDiagramPlugin diagramPlugin : registeredPlugins) {
            diagramPlugin.getPluginDiagramManipulator().onInitialisation();
        }
    }

    @Override
    public void runPluginsOnClosure() {
        for (final IDiagramPlugin diagramPlugin : registeredPlugins) {
            diagramPlugin.getPluginDiagramManipulator().onClosure();
        }
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
        styleInfoProviders.putIfAbsent(targetClass, List.of(styleInfoProvider));
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
