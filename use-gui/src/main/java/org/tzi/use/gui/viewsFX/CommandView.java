/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package org.tzi.use.gui.viewsFX;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import org.tzi.use.gui.views.View;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.events.StatementExecutedEvent;
import org.tzi.use.uml.sys.soil.MEnterOperationStatement;
import org.tzi.use.uml.sys.soil.MExitOperationStatement;
import org.tzi.use.uml.sys.soil.MStatement;

import java.util.List;
import java.util.Stack;

/**
 * A JavaFX version of the CommandView showing executed state manipulation commands.
 *
 * @author Akif Aydin
 */
public class CommandView extends BorderPane implements View, PrintableView{
    private final MSystem system;
    private final ListView<String> listView;
    private final ObservableList<String> listItems;

    public CommandView(MSystem system) {
        this.system = system;
        this.listItems = FXCollections.observableArrayList();
        this.listView = new ListView<>(listItems);

        ScrollPane scrollPane = new ScrollPane(listView);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPadding(new Insets(4));

        // custom scroll speed
        scrollPane.addEventFilter(ScrollEvent.SCROLL, getCustomScrollHandler(scrollPane));

        setCenter(scrollPane);

        // update and Register for system events
        update();
        system.getEventBus().register(this);
    }

    /**
     * Updates the list of executed commands in the view.
     */
    private void update() {
        listItems.clear();

        List<MStatement> evaluatedStatements = system.getEvaluatedStatements();
        if (evaluatedStatements.isEmpty()) {
            listItems.add("<empty>");
        } else {
            Stack<Integer> numbering = new Stack<>();
            Stack<String> prefixes = new Stack<>();

            numbering.push(0);
            prefixes.push("");

            for (MStatement statement : evaluatedStatements) {
                int number = numbering.pop() + 1;
                numbering.push(number);

                String prefix = prefixes.peek() + number + ".";
                String entry = prefix + " " + statement.getShellCommand();

                if (statement instanceof MEnterOperationStatement) {
                    numbering.push(0);
                    prefixes.push(prefix);
                } else if (statement instanceof MExitOperationStatement) {
                    numbering.pop();
                    prefixes.pop();
                }

                listItems.add(entry);
            }
        }

        // Scroll to last entry
        Platform.runLater(() -> listView.scrollTo(listItems.size() - 1)); //scroll to bottom
    }

    /**
     * Reacts to executed statements by updating the command list.
     */
    @Subscribe
    public void onStatementExecuted(StatementExecutedEvent e) {
        update();
    }

    /**
     * Detaches the view from the system event bus.
     */
    public void detachModel() {
        system.getEventBus().unregister(this);
    }

    /**
     * Returns an event handler that customizes the scroll speed
     * of the given ScrollPane to simulate unit increment scrolling.
     */
    private EventHandler<ScrollEvent> getCustomScrollHandler(ScrollPane scrollPane) {
        return event -> {
            double deltaY = event.getDeltaY();
            double contentHeight = scrollPane.getContent().getBoundsInLocal().getHeight();
            double viewportHeight = scrollPane.getViewportBounds().getHeight();
            double scrollStep = 16 / (contentHeight - viewportHeight);

            double newValue = scrollPane.getVvalue() - deltaY * scrollStep;
            scrollPane.setVvalue(clamp(newValue, 0, 1));

            event.consume();
        };
    }

    /**
     * Clamps a given value between a minimum and maximum bound.
     */
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    public void printView(PageLayout layout, PrinterJob job) {
        // Print the view node (this BorderPane or inner list)
        job.printPage(layout, this);  // or this.listView
    }

    @Override
    public double getContentWidth() {
        return listView.getWidth();
    }

    @Override
    public double getContentHeight() {
        return listView.getHeight();
    }
}