package org.tzi.use.gui.views.diagrams.objectdiagram;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;

/**
 * Default context menu provider used when no plugin provides a specialized menu.
 */
public class DefaultContextMenuProvider implements ContextMenuProvider {
    @Override
    public void enhanceMenu(JPopupMenu popupMenu,
                            ObjectDiagramInteractor diagram,
                            NewObjectDiagramPresenter presenter,
                            Set<MObject> selectedObjects,
                            Set<MLink> selectedLinks,
                            Set<MObject> selectedAssocObjects) {
        if (popupMenu == null || presenter == null) {
            return;
        }

        String summary = String.format("Selected objects: %d, selected links: %d",
                selectedObjects.size(),
                selectedLinks.size());
        JMenuItem summaryItem = new JMenuItem("Selection overview");
        summaryItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.onStatusMessage(summary);
            }
        });
        popupMenu.insert(summaryItem, 0);

        JMenuItem expandItem = new JMenuItem("Refresh selection");
        expandItem.addActionListener(ev -> {
            presenter.onStatusMessage("Refreshing selection overview");
            presenter.onRefreshRequested();
        });
        popupMenu.insert(expandItem, 1);

        // Hide selected items
        if (!selectedObjects.isEmpty() || !selectedLinks.isEmpty()) {
            JMenuItem hideItem = new JMenuItem("Hide selected");
            hideItem.addActionListener(ev -> {
                presenter.onHideLinks(selectedLinks);
                presenter.onHideObjects(selectedObjects);
                presenter.onStatusMessage("Selected elements hidden");
            });
            popupMenu.insert(hideItem, 2);
        }

        // Crop selection -> hide everything in selection (objects + link endpoints)
        if (!selectedObjects.isEmpty() || !selectedLinks.isEmpty()) {
            JMenuItem cropItem = new JMenuItem("Crop selection");
            cropItem.addActionListener(ev -> {
                Set<MObject> objectsToHide = new HashSet<>(selectedObjects);
                selectedLinks.forEach(link -> {
                    if (link instanceof MLinkObject) {
                        objectsToHide.add((MLinkObject) link);
                    } else {
                        objectsToHide.addAll(link.linkedObjects());
                    }
                });
                presenter.onCropSelection(objectsToHide);
                presenter.onStatusMessage("Cropped selection");
            });
            popupMenu.insert(cropItem, 3);
        }

        // Show hidden elements
        JMenuItem showHidden = new JMenuItem("Show hidden elements");
        showHidden.addActionListener(ev -> {
            presenter.onShowHiddenElements();
            presenter.onStatusMessage("Showing hidden elements");
        });
        popupMenu.insert(showHidden, 4);

        // Toggle show states
        JCheckBoxMenuItem showStates = new JCheckBoxMenuItem("Show states", diagram != null && diagram.isShowStates());
        showStates.addActionListener(ev -> presenter.onToggleShowStates(showStates.isSelected()));
        popupMenu.insert(showStates, 5);

        // Gray in/out
        if (!selectedObjects.isEmpty()) {
            Set<MObject> selection = selectedObjects;
            int greyed = 0;
            int visible = 0;
            for (MObject obj : selection) {
                if (diagram != null && diagram.isGreyed(obj)) {
                    greyed++;
                } else {
                    visible++;
                }
            }
            if (greyed > 0) {
                JMenuItem grayIn = new JMenuItem("Gray in selected" + (greyed > 1 ? " (" + greyed + ")" : ""));
                grayIn.addActionListener(ev -> presenter.onGrayIn(selection));
                popupMenu.insert(grayIn, 6);
            }
            if (visible > 0) {
                JMenuItem grayOut = new JMenuItem("Gray out selected" + (visible > 1 ? " (" + visible + ")" : ""));
                grayOut.addActionListener(ev -> presenter.onGrayOut(selection));
                popupMenu.insert(grayOut, 7);
            }
        }

        // Hide/show all links
        JMenuItem hideAllLinks = new JMenuItem("Hide all links");
        hideAllLinks.addActionListener(ev -> presenter.onHideAllLinks());
        popupMenu.insert(hideAllLinks, 8);

        JMenuItem showAllLinks = new JMenuItem("Show all links");
        showAllLinks.addActionListener(ev -> presenter.onShowAllLinks());
        popupMenu.insert(showAllLinks, 9);
    }
}
