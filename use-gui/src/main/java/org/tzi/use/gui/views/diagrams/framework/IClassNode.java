package org.tzi.use.gui.views.diagrams.framework;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassifier;

/** Foundation view of a class-diagram node, so layout/style code can discriminate
 *  and query class nodes without importing the concrete classdiagram.ClassNode. */
public interface IClassNode {
	MClass cls();
	MClassifier getClassifier();
}
