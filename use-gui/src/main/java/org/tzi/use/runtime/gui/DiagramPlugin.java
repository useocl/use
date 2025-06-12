package org.tzi.use.runtime.gui;

import lombok.NonNull;
import org.tzi.use.runtime.gui.impl.PluginDiagramManipulator;
import org.tzi.use.runtime.impl.Plugin;

import java.util.List;

/**
 * This class is extended by plugins, which want to modify the characteristics of diagrams.
 */
public abstract class DiagramPlugin extends Plugin {

    public abstract @NonNull List<PluginDiagramManipulator> getPluginDiagramManipulators();
}
