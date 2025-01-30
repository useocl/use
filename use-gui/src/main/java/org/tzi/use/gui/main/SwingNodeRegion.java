package org.tzi.use.gui.main;

import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Region;

public class SwingNodeRegion extends Region {
    private final SwingNode swingNode;

    public SwingNodeRegion(SwingNode swingNode) {
        this.swingNode = swingNode;

        setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        getChildren().add(swingNode);
    }

    @Override
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        // Position the SwingNode at (0,0) and make it fill the entire region
        swingNode.resizeRelocate(0, 0, w, h);
    }

    @Override
    protected double computePrefWidth(double height) {
        return 300; // pick a default preference
    }

    @Override
    protected double computePrefHeight(double width) {
        return 200; // pick a default preference
    }
}
