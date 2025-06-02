package org.tzi.use.gui.viewsFX.evalbrowser;

import javafx.scene.control.*;

import java.io.*;
import java.util.Optional;
import java.util.Properties;

public class SetDefaultDialog {

    private final ExprEvalBrowser exprEvalBrowser;

    public SetDefaultDialog(ExprEvalBrowser browser) {
        this.exprEvalBrowser = browser;
        showDialog();
    }

    private void showDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Evaluation Browser Configuration");
        alert.setHeaderText("Set current configuration as default");
        alert.setContentText("Choose whether to apply settings only for this session or save them permanently:");

        ButtonType btnSession = new ButtonType("For this session");
        ButtonType btnPermanent = new ButtonType("For all sessions");
        ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(btnSession, btnPermanent, btnCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isEmpty()) return;

        if (result.get() == btnSession) {
            applySessionSettings();
        } else if (result.get() == btnPermanent) {
            savePermanentSettings();
        }
    }

    private void applySessionSettings() {
        // Highlighting mode
        String highlighting = "no";
        if (exprEvalBrowser.highlightTerm.isSelected()) highlighting = "term";
        else if (exprEvalBrowser.highlightSubtree.isSelected()) highlighting = "subtree";
        else if (exprEvalBrowser.highlightComplete.isSelected()) highlighting = "complete";
        System.setProperty("use.gui.view.evalbrowser.highlighting", highlighting); // same in savePermanentSettings

        // Variable assignment list + substitution view checkboxes
        System.setProperty("use.gui.view.evalbrowser.VarAssignmentWindow", String.valueOf(exprEvalBrowser.cbVarAssList.isSelected()));
        System.setProperty("use.gui.view.evalbrowser.SubExprSubstitutionWindow", String.valueOf(exprEvalBrowser.cbSubstitutionView.isSelected()));

        System.setProperty("use.gui.view.evalbrowser.exists", String.valueOf(exprEvalBrowser.fExtendedExists.isSelected()));
        System.setProperty("use.gui.view.evalbrowser.forall", String.valueOf(exprEvalBrowser.fExtendedForAll.isSelected()));
        System.setProperty("use.gui.view.evalbrowser.and", String.valueOf(exprEvalBrowser.fExtendedAnd.isSelected()));
        System.setProperty("use.gui.view.evalbrowser.or", String.valueOf(exprEvalBrowser.fExtendedOr.isSelected()));
        System.setProperty("use.gui.view.evalbrowser.implies", String.valueOf(exprEvalBrowser.fExtendedImplies.isSelected()));
        System.setProperty("use.gui.view.evalbrowser.substitution", String.valueOf(exprEvalBrowser.cbSubstituteVariables.isSelected()));
        System.setProperty("use.gui.view.evalbrowser.blackHighlighting", String.valueOf(exprEvalBrowser.fNoColorHighlightingChk.isSelected()));

        String mode = switch (exprEvalBrowser.getVarAssignmentMode()) {
            case EARLY -> "early";
            case NEVER -> "never";
            default -> "late";
        };
        System.setProperty("use.gui.view.evalbrowser.treeview", mode);
    }

    private void savePermanentSettings() {
        try {
            File configFile = new File(System.getProperty("user.home"), ".use_evalbrowser.properties");
            Properties props = new Properties();

            if (configFile.exists()) {
                try (FileReader reader = new FileReader(configFile)) {
                    props.load(reader);
                }
            }

            // Highlighting
            String hl = "no";
            if (exprEvalBrowser.highlightTerm.isSelected()) hl = "term";
            else if (exprEvalBrowser.highlightSubtree.isSelected()) hl = "subtree";
            else if (exprEvalBrowser.highlightComplete.isSelected()) hl = "complete";
            props.setProperty("use.gui.view.evalbrowser.highlighting", hl);

            // Variable assignment list + substitution view checkboxes
            System.setProperty("use.gui.view.evalbrowser.VarAssignmentWindow", String.valueOf(exprEvalBrowser.cbVarAssList.isSelected()));
            System.setProperty("use.gui.view.evalbrowser.SubExprSubstitutionWindow", String.valueOf(exprEvalBrowser.cbSubstitutionView.isSelected()));

            props.setProperty("use.gui.view.evalbrowser.exists", String.valueOf(exprEvalBrowser.fExtendedExists.isSelected()));
            props.setProperty("use.gui.view.evalbrowser.forall", String.valueOf(exprEvalBrowser.fExtendedForAll.isSelected()));
            props.setProperty("use.gui.view.evalbrowser.and", String.valueOf(exprEvalBrowser.fExtendedAnd.isSelected()));
            props.setProperty("use.gui.view.evalbrowser.or", String.valueOf(exprEvalBrowser.fExtendedOr.isSelected()));
            props.setProperty("use.gui.view.evalbrowser.implies", String.valueOf(exprEvalBrowser.fExtendedImplies.isSelected()));
            props.setProperty("use.gui.view.evalbrowser.substitution", String.valueOf(exprEvalBrowser.cbSubstituteVariables.isSelected()));
            props.setProperty("use.gui.view.evalbrowser.blackHighlighting", String.valueOf(exprEvalBrowser.fNoColorHighlightingChk.isSelected()));

            String mode = switch (exprEvalBrowser.getVarAssignmentMode()) {
                case EARLY -> "early";
                case NEVER -> "never";
                default -> "late";
            };
            props.setProperty("use.gui.view.evalbrowser.treeview", mode);

            try (FileWriter writer = new FileWriter(configFile)) {
                props.store(writer, "USE EvalBrowser Settings");
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Could not save configuration:\n" + e.getMessage()).showAndWait();
        }
    }
}
