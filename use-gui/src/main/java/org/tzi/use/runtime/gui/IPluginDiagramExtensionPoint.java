package org.tzi.use.runtime.gui;

import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.main.runtime.IExtensionPoint;
import org.tzi.use.runtime.IPluginDescriptor;

public interface IPluginDiagramExtensionPoint extends IExtensionPoint {

    void registerPlugin(IPluginDescriptor pluginDescriptor);

    <T extends DiagramView> void registerView(T diagramView);

    void runPluginsOnInitialisation();
}
