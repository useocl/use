open module use.gui {
    requires org.eclipse.jdt.annotation;
    requires use.core;
    requires vtd.xml;
    requires com.google.common;
    requires itextpdf;
    requires java.desktop;
    exports org.tzi.use.gui.views;
    exports org.tzi.use.gui.main;
    exports org.tzi.use.gui.util;
    exports org.tzi.use.gui.views.diagrams.behavior.communicationdiagram;
    exports org.tzi.use.gui.views.diagrams.classdiagram;
    exports org.tzi.use.gui.views.diagrams.objectdiagram;
    exports org.tzi.use.runtime.gui.impl;
    exports org.tzi.use.runtime;
    exports org.tzi.use.main.shell;

}