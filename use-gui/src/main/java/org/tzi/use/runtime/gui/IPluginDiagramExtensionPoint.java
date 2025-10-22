package org.tzi.use.runtime.gui;

import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.main.runtime.IExtensionPoint;
import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.gui.impl.StyleInfoProvider;

import java.util.List;

public interface IPluginDiagramExtensionPoint extends IExtensionPoint {

    void registerDiagramManipulators(IPluginDescriptor pluginDescriptor);

    <T extends DiagramView> void registerView(T diagramView);

    <T extends DiagramView> void removeView(T diagramView);

    void runPluginsOnStart();

    void runPluginsOnClose();

    List<StyleInfoProvider> getStyleInfoProvider(final Class<? extends DiagramView> targetClass);
}
