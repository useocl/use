open module use.guiFX {

    requires javafx.graphics;
    requires javafx.fxml;
    requires use.core;
    requires javafx.controls;
    requires javafx.swing;
    requires javafx.web;
    requires org.kordamp.desktoppanefx.core;
    requires use.gui;

    exports org.tzi.use.mainFX;
    exports org.tzi.use.gui.mainFX;
}