package org.tzi.use.runtime.gui;

import org.tzi.use.runtime.IPlugin;
import org.tzi.use.runtime.gui.impl.PluginDiagramManipulator;

public interface IDiagramPlugin extends IPlugin {

    /**
     * Method to the implementation of the plugins behaviour class
     * @return the plugins diagram-manipulator
     */
     PluginDiagramManipulator getPluginDiagramManipulator();
}
