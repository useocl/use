package org.tzi.use.gui.views.diagrams.base;
import org.tzi.use.gui.views.diagrams.base.DiagramView;
import org.tzi.use.gui.views.diagrams.base.StyleInfoProvider;

import org.tzi.use.runtime.spi.IExtensionPoint;
import org.tzi.use.runtime.spi.IPluginDescriptor;

import java.util.List;

public interface IPluginDiagramExtensionPoint extends IExtensionPoint {

    void registerDiagramManipulators(IPluginDescriptor pluginDescriptor);

    <T extends DiagramView> void registerView(T diagramView);

    <T extends DiagramView> void removeView(T diagramView);

    void runPluginsOnStart();

    void runPluginsOnClose();

    List<StyleInfoProvider> getStyleInfoProvider(final Class<? extends DiagramView> targetClass);
}
