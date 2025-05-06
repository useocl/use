module use.gui {
    requires org.eclipse.jdt.annotation;
    requires use.core;
    requires vtd.xml;
    requires com.google.common;
    requires itextpdf;
    requires javafx.fxml;
    requires javafx.web;
    requires org.kordamp.desktoppanefx.core;
    requires javafx.swing;
    requires annotations;
    requires jline;
    opens org.tzi.use.gui.main to javafx.fxml;
    exports org.tzi.use.main.gui;
    exports org.tzi.use.gui.views;
    exports org.tzi.use.gui.main;
    exports org.tzi.use.gui.util;
    exports org.tzi.use.gui.views.diagrams.behavior.communicationdiagram;
    exports org.tzi.use.gui.views.diagrams.classdiagram;
    exports org.tzi.use.gui.views.diagrams.objectdiagram;
    exports org.tzi.use.runtime.gui.impl;
    exports org.tzi.use.runtime;
    exports org.tzi.use.main.shell;
    exports org.tzi.use.main.gui.fx;
    exports org.tzi.use.gui.mainFX;
    exports org.tzi.use.gui.mainFX.runtime;
    opens org.tzi.use.gui.mainFX to javafx.fxml;
    exports org.tzi.use.runtime.guiFX.impl;
    exports org.tzi.use.gui.utilFX;

}