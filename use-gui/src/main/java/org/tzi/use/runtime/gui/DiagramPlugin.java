package org.tzi.use.runtime.gui;

import org.tzi.use.runtime.gui.impl.PluginDiagramManipulator;
import org.tzi.use.runtime.impl.Plugin;

/**
 * This class is extended by plugins, which want to modify the characteristics of Diagrams.
 */
public abstract class DiagramPlugin extends Plugin {

    /**
     * Method to the implementation of the plugins behaviour class
     *
     * @return the plugins diagram-manipulator
     */
    public abstract PluginDiagramManipulator getPluginDiagramManipulator();
}
