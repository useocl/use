package org.tzi.use.gui.main.runtime;

import org.tzi.use.main.runtime.IExtensionPoint;
import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.gui.IGraphManipulator;

import java.util.List;

public interface IPluginGraphExtensionPoint extends IExtensionPoint {

    /**
     * Method to register Plugins GraphManipulator from the given Plugin Descriptor
     *
     * @param pluginDescriptor - The Plugin Descriptor object
     */
    void registerPlugin(IPluginDescriptor pluginDescriptor);

    List<IGraphManipulator> getRegisteredPlugins();
}
