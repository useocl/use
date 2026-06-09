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

package org.tzi.use.gui.views.diagrams;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.jupiter.api.Test;
import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagramOptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Tests that the diagram layout options are correctly persisted to and restored
 * from the layout file.
 *
 * <p>These tests focus on the option "Group multiplicities / role names"
 * (see {@link DiagramOptions#isGroupMR()}), which was reported to not be
 * persisted in the layout file (issue #16). The tests exercise the exact
 * save/load path used by
 * {@link DiagramView#saveLayoutAsString()} /
 * {@link DiagramView#loadLayoutFromString(String)}: the options are written
 * into a {@code diagramOptions} element of a versioned {@code diagram_Layout}
 * document and read back through the same navigation.</p>
 *
 * @author Claude
 */
public class DiagramOptionsPersistenceTest {

    /**
     * The XML element name expected for the "Group multiplicities / role names"
     * option in the persisted layout file.
     */
    private static final String GROUP_MR_TAG = "groupmultiplicityrolenames";

    /**
     * Serializes the options of {@code options} into a layout XML document,
     * exactly as {@link DiagramView} does when saving a layout, and returns
     * the resulting bytes.
     *
     * @param options the options to persist
     * @param version the layout version to write into the {@code diagram_Layout} root
     */
    private byte[] saveOptions(DiagramOptions options, int version) throws Exception {
        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = fact.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        PersistHelper writeHelper = new PersistHelper(new PrintWriter(new StringWriter()));

        Element rootElement = doc.createElement("diagram_Layout");
        rootElement.setAttribute("version", String.valueOf(version));
        doc.appendChild(rootElement);

        Element optionsElement = doc.createElement("diagramOptions");
        rootElement.appendChild(optionsElement);
        options.saveOptions(writeHelper, optionsElement);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(doc), new StreamResult(bos));
        return bos.toByteArray();
    }

    /**
     * Restores the diagram options from a layout XML document into
     * {@code options}, exactly as {@link DiagramView} does when loading a layout.
     */
    private void loadOptions(byte[] layoutXml, DiagramOptions options) throws Exception {
        PersistHelper readHelper = new PersistHelper(layoutXml, new PrintWriter(new StringWriter()));

        int version = 1;
        if (readHelper.hasAttribute("version")) {
            version = Integer.valueOf(readHelper.getAttributeValue("version"));
        }

        readHelper.toFirstChild("diagramOptions");
        options.loadOptions(readHelper, version);
        readHelper.toParent();
    }

    /**
     * Convenience overload that saves with the current layout version.
     */
    private byte[] saveOptions(DiagramOptions options) throws Exception {
        return saveOptions(options, DiagramOptions.XML_LAYOUT_VERSION);
    }

    /**
     * Issue #16: an enabled "Group multiplicities / role names" option must
     * survive a save/load round-trip.
     */
    @Test
    public void groupMultiplicityRolenameTrueSurvivesRoundTrip() throws Exception {
        ClassDiagramOptions saved = new ClassDiagramOptions();
        saved.setGroupMR(true);

        byte[] layoutXml = saveOptions(saved);

        ClassDiagramOptions loaded = new ClassDiagramOptions();
        // Force the opposite value first, so a passing assertion proves the
        // value was really restored from the file (not left at a default).
        loaded.setGroupMR(false);
        loadOptions(layoutXml, loaded);

        assertTrue(loaded.isGroupMR(),
                "Group multiplicities/role names = true must be persisted and restored");
    }

    /**
     * Issue #16: a disabled "Group multiplicities / role names" option must
     * survive a save/load round-trip as well (the user's explicit choice must
     * be honored, not silently reset to the default).
     */
    @Test
    public void groupMultiplicityRolenameFalseSurvivesRoundTrip() throws Exception {
        ClassDiagramOptions saved = new ClassDiagramOptions();
        saved.setGroupMR(false);

        byte[] layoutXml = saveOptions(saved);

        ClassDiagramOptions loaded = new ClassDiagramOptions();
        // Force the opposite value first.
        loaded.setGroupMR(true);
        loadOptions(layoutXml, loaded);

        assertFalse(loaded.isGroupMR(),
                "Group multiplicities/role names = false must be persisted and restored");
    }

    /**
     * The saved layout file must actually contain an element for the
     * "Group multiplicities / role names" option.
     */
    @Test
    public void savedLayoutContainsGroupMultiplicityRolenameElement() throws Exception {
        ClassDiagramOptions options = new ClassDiagramOptions();
        options.setGroupMR(true);

        String layoutXml = new String(saveOptions(options), StandardCharsets.UTF_8);

        assertTrue(layoutXml.contains("<" + GROUP_MR_TAG + ">true</" + GROUP_MR_TAG + ">"),
                "Saved layout must contain the group-multiplicities/role-names option element, but was:\n"
                        + layoutXml);
    }

    /**
     * Issue #16 (requested default): a brand-new class diagram should group
     * multiplicities and role names by default.
     */
    @Test
    public void groupMultiplicityRolenameDefaultsToTrue() {
        assertTrue(new ClassDiagramOptions().isGroupMR(),
                "New class diagrams should group multiplicities/role names by default");
    }

    /**
     * Adding the new option requires bumping the layout version so that the
     * loader can tell old layout files (without the option) from new ones.
     */
    @Test
    public void layoutVersionWasBumpedForNewOption() {
        assertTrue(DiagramOptions.XML_LAYOUT_VERSION >= 14,
                "The layout version must be increased to at least 14 for the new option");
    }

    /**
     * Backward compatibility: loading a pre-v14 layout file (which does not
     * contain the option) must not fail and must not silently turn the option
     * off. The default must be kept, guarded by the layout version.
     */
    @Test
    public void loadingPreVersion14LayoutKeepsDefault() throws Exception {
        String oldLayout =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<diagram_Layout version=\"13\">\n"
                + "    <diagramOptions>\n"
                + "        <autolayout>false</autolayout>\n"
                + "        <antialiasing>false</antialiasing>\n"
                + "        <showassocnames>false</showassocnames>\n"
                + "        <showattributes>true</showattributes>\n"
                + "        <showmultiplicities>false</showmultiplicities>\n"
                + "        <showoperations>false</showoperations>\n"
                + "        <showrolenames>true</showrolenames>\n"
                + "        <showgrid>false</showgrid>\n"
                + "    </diagramOptions>\n"
                + "</diagram_Layout>\n";

        ClassDiagramOptions loaded = new ClassDiagramOptions();
        // The default is "true"; loading an old file without the tag must not
        // overwrite it with "false".
        loadOptions(oldLayout.getBytes(StandardCharsets.UTF_8), loaded);

        assertTrue(loaded.isGroupMR(),
                "Loading a pre-v14 layout (without the option) must keep the default value");
    }

    /**
     * Regression guard: adding the new option must not break persistence of the
     * other (existing) diagram options.
     */
    @Test
    public void existingOptionsStillRoundTrip() throws Exception {
        ClassDiagramOptions saved = new ClassDiagramOptions();
        saved.setShowAttributes(false);
        saved.setShowOperations(true);
        saved.setShowAssocNames(true);
        saved.setGroupMR(true);

        byte[] layoutXml = saveOptions(saved);

        ClassDiagramOptions loaded = new ClassDiagramOptions();
        loadOptions(layoutXml, loaded);

        assertFalse(loaded.isShowAttributes(), "showattributes must round-trip");
        assertTrue(loaded.isShowOperations(), "showoperations must round-trip");
        assertTrue(loaded.isShowAssocNames(), "showassocnames must round-trip");
        assertTrue(loaded.isGroupMR(), "group multiplicities/role names must round-trip");
    }
}
