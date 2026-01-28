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

package org.tzi.use.gui.utilFX;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;


/** 
 * A StatusBar consisting of a single line of text.
 * 
 * @author  Akif Aydin
 */
public class StatusBar extends BorderPane {

	private final Label fMsgLabelWest;

	private final Label fMsgLabelEast;

    private Timeline fTimer;

    public StatusBar() {
        fMsgLabelWest = new Label("Ready.");
        fMsgLabelEast = new Label("");

        setLeft(fMsgLabelWest);
        setRight(fMsgLabelEast);

        setPadding(new Insets(2, 4, 2, 4));
        setStyle("-fx-border-color: lightgrey; -fx-border-width: 1 0 0 0;"); // top border
    }

    public void showTmpMessage(String msg) {
        if (msg == null || msg.isEmpty()) msg = " ";
        fMsgLabelWest.setText(msg);

        if (fTimer != null) fTimer.stop();
        fTimer = new Timeline(new KeyFrame(Duration.seconds(8), e -> fMsgLabelWest.setText("Ready.")));
        fTimer.setCycleCount(1);
        fTimer.play();
    }

    public void showMessage(String msg, Pos position) {
        if (msg == null || msg.isEmpty()) msg = " ";
        if (position == Pos.BASELINE_RIGHT) {
            fMsgLabelEast.setText(msg);
        } else if (position == Pos.BASELINE_LEFT) {
            fMsgLabelWest.setText(msg);
            if (fTimer != null) fTimer.stop();
        } else {
            throw new IllegalArgumentException("Only Pos.BASELINE_LEFT and BASELINE_RIGHT are supported.");
        }
    }

    public void clearMessage() {
        fMsgLabelWest.setText("Ready.");
        fMsgLabelEast.setText("");
        if (fTimer != null) fTimer.stop();
    }
}

