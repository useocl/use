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

package org.tzi.use.gui.mainFX;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.tzi.use.config.Options;
import org.tzi.use.gui.utilFX.TextComponentWriter;
import org.tzi.use.gui.viewsFX.evalbrowser.ExprEvalBrowser;
import org.tzi.use.main.ChangeEvent;
import org.tzi.use.main.ChangeListener;
import org.tzi.use.main.Session;
import org.tzi.use.main.gui.Main;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.expr.EvalNode;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.TeeWriter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

/**
 * A dialog for entering and evaluating OCL expressions.
 * 
 * @author Akif Aydin
 */

public class EvalOCLDialog extends Stage {
    private Session fSession;
    private MSystem fSystem;
    private final TextArea fTextIn;
    private final TextArea fTextOut;
    private Stage fEvalStage;
    private Evaluator evaluator;

    private final ChangeListener sessionChangeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            Session session = (Session)e.getSource();
            fSystem = getSystem(session);
        }
    };
    
    public EvalOCLDialog(Session session, Stage owner) {
        super();

        initOwner(owner);
        initModality(Modality.WINDOW_MODAL);
        setTitle("Evaluate OCL expression");

        fSession = session;
        fSystem = getSystem(session);
        session.addChangeListener(sessionChangeListener);

        // Handle window close
        setOnCloseRequest(this::handleClose);

        // Create UI components
        String fontFamily = System.getProperty("use.gui.evalFont").toLowerCase();
        Font evalFont = new Font(fontFamily, 12);

        // Input component
        Label textInLabel = new Label("Enter _OCL expression:");
        textInLabel.setMnemonicParsing(true); // checks for _ and underlines the next letter
        fTextIn = new TextArea();
        fTextIn.setFont(evalFont);
        fTextIn.setWrapText(true);

        // Output component
        Label textOutLabel = new Label("Result:");
        fTextOut = new TextArea();
        fTextOut.setEditable(false);
        fTextOut.setWrapText(true);
        fTextOut.setFont(evalFont);

        // Buttons
        Button btnEval = initButton("Evaluate");
        btnEval.setOnAction(e -> handleEvaluate(false));

        Button btnEvalBrowser = initButton("Browser");
        btnEvalBrowser.setOnAction(e -> handleEvaluate(true));

        Button btnClear = initButton("Clear");
        btnClear.setOnAction(e -> {
            fTextOut.clear();
            if (fEvalStage != null) {
                fEvalStage.close();
                fEvalStage = null;
            }
        });

        Button btnClose = initButton("Close");
        btnClose.setOnAction(e -> closeDialog());


        // VBox Layout for textIn and TextOut (Left Side)
        VBox textPane = new VBox(5,
                new VBox(textInLabel, new ScrollPane(fTextIn)),
                new VBox(textOutLabel, new ScrollPane(fTextOut))
        );
        textPane.setPadding(new javafx.geometry.Insets(5));

        // VBox Layout for Buttons (Right Side)
        VBox btnPane = new VBox(5, btnEval, btnEvalBrowser, btnClear, btnClose);
        btnPane.setPadding(new Insets(5));
        btnPane.setMinWidth(90);
        btnPane.setPadding(new Insets(0,20,0,0));
        btnPane.setAlignment(Pos.TOP_CENTER);

        HBox root = new HBox(textPane, btnPane);
        //root.setSpacing(5);

        Scene scene = new Scene(root, 500, 200);
        setScene(scene);

        // Set default button
        btnEval.setDefaultButton(true);

        // Handle ESC key press
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                closeDialog();
            }
        });

        fTextIn.requestFocus();

        // setting Logo:
        getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/use_icon.png"))));

    }

    private Button initButton(String btnName) {
        Button btn = new Button(btnName);
        btn.setPrefWidth(150);
        btn.setMnemonicParsing(true);
        btn.setMaxWidth(Double.MAX_VALUE);
        return btn;
    }

    private void handleClose(WindowEvent event) {
        Session session = fSession;
        if (session != null) {
            session.removeChangeListener(sessionChangeListener);
        }
    }

    private MSystem getSystem(Session session) {
        if (session.hasSystem()) {
            return session.system();
        } else {
            MModel model = new ModelFactory().createModel("empty model");
            return new MSystem(model);
        }
    }

    private void closeDialog() {
        close();
        if (fEvalStage != null) {
            fEvalStage.close();
        }
    }

    private void handleEvaluate(boolean evalTree) {
        if (fSystem == null) {
            fTextOut.setText("No system!");
            return;
        }

        String input = fTextIn.getText();
        fTextOut.clear();

        // Evaluate in background thread if operation is long-running
        new Thread(() -> {
            boolean success = evaluate(input, evalTree);

            if (success) {
                EvalNode root = evaluator.getEvalNodeRoot();
                if (root == null) {
                    return;
                }
                Platform.runLater(() -> {
                    if (fEvalStage != null && fEvalStage.isShowing()) {
                        ExprEvalBrowser view = (ExprEvalBrowser) fEvalStage.getScene().getRoot();
                        view.updateEvalBrowser(root);
                        fEvalStage.requestFocus();
                    } else {
                        fEvalStage = ExprEvalBrowser.create(root, fSystem);
                    }
                });
            }
        }).start();
    }

    private boolean evaluate(String in, boolean evalTree) {
        // Similar to original evaluate method, adapted for JavaFX
        // Use Platform.runLater() for UI updates

        if (fSystem == null) {
            Platform.runLater(() -> fTextOut.setText("No system!"));
            return false;
        }

        Platform.runLater(fTextOut::clear);

        StringWriter msgWriter = new StringWriter();
        PrintWriter out = new PrintWriter(new TeeWriter(
                new TextComponentWriter(fTextOut), msgWriter), true);

        Expression expr = OCLCompiler.compileExpression(
                fSystem.model(),
                fSystem.state(),
                in,
                "Error",
                out,
                fSystem.varBindings());

        out.flush();
        Platform.runLater(fTextIn::requestFocus);

        if (expr == null) {
            String msg = msgWriter.toString();
            int colon1 = msg.indexOf(':');
            if (colon1 != -1) {
                int colon2 = msg.indexOf(':', colon1 + 1);
                int colon3 = msg.indexOf(':', colon2 + 1);

                try {
                    int line = Integer.parseInt(msg.substring(colon1 + 1, colon2));
                    int column = Integer.parseInt(msg.substring(colon2 + 1, colon3));
                    final int[] caret = {1 + StringUtil.nthIndexOf(in, line - 1, Options.LINE_SEPARATOR)};
                    caret[0] += column;

                    Platform.runLater(() -> {
                        caret[0] = Math.min(caret[0], fTextIn.getText().length());
                        fTextIn.positionCaret(caret[0]);
                    });
                } catch (NumberFormatException ex) { /* ignore */ }
            }
            return false;
        }

        try {
            evaluator = new Evaluator(evalTree);
            Value val = evaluator.eval(expr, fSystem.state(), fSystem.varBindings());

            Platform.runLater(() -> fTextOut.setText(val.toStringWithType()));
            return true;
        } catch (MultiplicityViolationException e) {
            Platform.runLater(() -> fTextOut.setText("Could not evaluate. " + e.getMessage()));
            return false;
        }
    }
}