package org.tzi.use.gui.views.diagrams;

import lombok.NonNull;


/**
 * @param exposedObject diagram maps UI element to exposed object for DiagramView it'd be MClassifier
 */
public record UIElementIntermediate<T>(@NonNull T exposedObject) {
}
