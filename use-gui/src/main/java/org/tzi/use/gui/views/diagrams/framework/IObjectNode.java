package org.tzi.use.gui.views.diagrams.framework;

/** Foundation marker for an object-DIAGRAM node (only objectdiagram.ObjectNode
 *  implements it — NOT the communication-diagram object boxes), so layout/style
 *  code can discriminate object-diagram nodes precisely without importing
 *  objectdiagram.ObjectNode. Query cls()/object() via the inherited interface. */
public interface IObjectNode extends ObjectNodeActivity {
}
