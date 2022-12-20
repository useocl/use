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

package org.tzi.use.gui.views.diagrams.objectdiagram;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;
import java.util.List;

import org.tzi.use.gui.main.ModelBrowserSorting;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeEvent;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeListener;
import org.tzi.use.gui.views.diagrams.DiagramOptionChangedListener;
import org.tzi.use.gui.views.diagrams.ObjectNodeActivity;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.util.Util;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.statemachines.MState;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.uml.ocl.value.EnumValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;

/**
 * A node representing an object.
 * 
 * @author Fabian Gutsche
 * @author Lars Hamann
 */
public class ObjectNode extends PlaceableNode implements SortChangeListener, ObjectNodeActivity {

	private final ObjDiagramOptions fOpt;
	private final NewObjectDiagramView fParent;
	private final MObject fObject;
	private final String fLabel;
	protected AttributedString fLabelA;

	private List<MAttribute> fAttributes;
	private final String[] fValues;

	private List<MStateMachine> fStateMachines;
	private final String[] fStateValues;

	private boolean fIsGreyed;

	protected Rectangle2D.Double nameRect = new Rectangle2D.Double();
	protected Rectangle2D.Double attributesRect = new Rectangle2D.Double();
	protected Rectangle2D.Double statesRect = new Rectangle2D.Double();

	protected DiagramOptionChangedListener fOptChaneListener;

	public ObjectNode(MObject obj, NewObjectDiagramView parent, ObjDiagramOptions opt) {
		fObject = obj;
		fParent = parent;
		fOpt = opt;
		fIsGreyed = false;
		fOptChaneListener = new DiagramOptionChangedListener() {
			@Override
			public void optionChanged(String optionname) {
				if (optionname.equals("SHOWOPERATIONS") || optionname.equals("SHOWATTRIBUTES")
						|| optionname.equals("SHOWSTATES"))
					calculateBounds();
			}
		};

		this.fOpt.addOptionChangedListener(fOptChaneListener);

		MClass cls = obj.cls();
		fLabel = obj.name() + ":" + cls.name();
		fLabelA = new AttributedString(fLabel);
		fLabelA.addAttribute(TextAttribute.FONT, parent.getFont());
		fLabelA.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

		List<MAttribute> allAttributes = cls.allAttributes();
		final int N = allAttributes.size();
		fValues = new String[N];

		fAttributes = ModelBrowserSorting.getInstance().sortAttributes(allAttributes);

		fStateMachines = ModelBrowserSorting.getInstance()
				.sortStateMachines(fObject.cls().getAllOwnedProtocolStateMachines());

		fStateValues = new String[fStateMachines.size()];

	}

	public MObject object() {
		return fObject;
	}

	public MClass cls() {
		return fObject.cls();
	}

	@Override
	public String getId() {
		return name();
	}

	@Override
	public String name() {
		return fObject.name();
	}

	/**
	 * After the occurence of an event the attribute list is updated.
	 */
	@Override
	public void stateChanged(SortChangeEvent e) {
		fAttributes = ModelBrowserSorting.getInstance().sortAttributes(fAttributes);

		fStateMachines = ModelBrowserSorting.getInstance().sortStateMachines(fStateMachines);
	}

	/**
	 * Sets the correct size of the width and height of this object node. This
	 * method needs to be called before actually drawing the node. (Width and
	 * height are needed from other methods before the nodes are drawn.)
	 */
	@Override
	public void doCalculateSize(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();

		nameRect.width = fm.stringWidth(fLabel);
		nameRect.height = fm.getHeight();

		updateContent();
		attributesRect.width = 0;
		for (int i = 0; i < fValues.length; ++i) {
			attributesRect.width = Math.max(attributesRect.width, fm.stringWidth(fValues[i]));
		}

		attributesRect.height = fm.getHeight() * fAttributes.size() + 3;

		statesRect.width = 0;
		for (int i = 0; i < fStateValues.length; ++i) {
			statesRect.width = Math.max(statesRect.width, fm.stringWidth(fStateValues[i]));
		}
		statesRect.height = fm.getHeight() * fStateMachines.size() + 3;

		calculateBounds();
	}

	protected void calculateBounds() {
		double width = nameRect.width;
		double height = nameRect.height;

		if (fOpt.isShowAttributes()) {
			width = Math.max(width, attributesRect.width);
			height += attributesRect.height;
		}

		if (fOpt.isShowStates()) {
			width = Math.max(width, statesRect.width);
			height += statesRect.height;
		}

		height += 4;
		width += 10;

		height = Math.max(height, getMinHeight());
		width = Math.max(width, getMinWidth());

		setCalculatedBounds(width, height);
	}

	/**
	 * 
	 */
	public void updateContent() {
		String value;
		MObjectState objState = fObject.state(fParent.system().state());

		if (objState == null)
			return;

		for (int i = 0; i < fAttributes.size(); i++) {
			MAttribute attr = fAttributes.get(i);
			Value val = objState.attributeValue(attr);

			if (val instanceof EnumValue) {
				value = "#" + ((EnumValue) val).value();
			} else {
				value = val.toString();
			}

			fValues[i] = (attr.isDerived() ? "/" : "") + attr.name() + "=" + value;
		}

		for (int i = 0; i < fStateMachines.size(); i++) {
			MStateMachine sm = fStateMachines.get(i);
			MState val = objState.getProtocolStateMachineInstance(sm).getCurrentState(sm.getDefaultRegion());

			fStateValues[i] = sm.name() + "=" + val.name();
		}
	}

	/**
	 * Draws a box with an underlined label.
	 */
	@Override
	protected void onDraw(Graphics2D g) {
		Rectangle2D.Double currentBounds = getRoundedBounds();

		if (!Util.enlargeRectangle(currentBounds, 10).intersects(g.getClipBounds())) {
			return;
		}

		double x = currentBounds.getX();
		int y = (int) currentBounds.getY();

		int labelWidth = g.getFontMetrics().stringWidth(fLabel);

		// set default colors
		Color fillColor = fOpt.getNODE_COLOR();
		Color lineColor = fOpt.getNODE_FRAME_COLOR();
		// change colors if needed
		if(isSelected() && isGreyed()) {
			//fillColor = fOpt.getGREYED_SELECTED_FILL_COLOR();
			fillColor = fOpt.getNODE_SELECTED_COLOR();
			//lineColor = fOpt.getGREYED_LINE_COLOR();
			lineColor = fOpt.getGREYED_SELECTED_LINE_COLOR();
		} else if (isSelected()) {
			fillColor = fOpt.getNODE_SELECTED_COLOR();
			lineColor = fOpt.getNODE_FRAME_COLOR();
		} else if (isGreyed()) {
			fillColor = fOpt.getGREYED_FILL_COLOR();
			lineColor = fOpt.getGREYED_LINE_COLOR();
		}
		g.setColor(fillColor);
		g.fill(currentBounds);
		g.setColor(lineColor);
		g.draw(currentBounds);

		x = (currentBounds.getCenterX() - labelWidth / 2);
		y = (int) currentBounds.getY() + g.getFontMetrics().getAscent() + 2;

		//g.setColor(fOpt.getColor(DiagramOptions.NODE_LABEL_COLOR));
		g.drawString(fLabelA.getIterator(), Math.round(x), y);

		if (fOpt.isShowAttributes()) {
			// compartment divider
			Line2D.Double lineAttrDivider = new Line2D.Double(currentBounds.getX(), y + 3, currentBounds.getMaxX(),
					y + 3);
			g.draw(lineAttrDivider);
			x = currentBounds.getX() + 5;
			y += 3;
			for (int i = 0; i < fAttributes.size(); i++) {
				y += g.getFontMetrics().getHeight();
				g.drawString(fValues[i], Math.round(x), y);
			}
		}

		if (fOpt.isShowStates()) {
			// compartment divider
			Line2D.Double lineAttrDivider = new Line2D.Double(currentBounds.getX(), y + 3, currentBounds.getMaxX(),
					y + 3);
			g.draw(lineAttrDivider);
			x = currentBounds.getX() + 5;
			y += 3;
			for (int i = 0; i < fStateValues.length; i++) {
				y += g.getFontMetrics().getHeight();
				g.drawString(fStateValues[i], Math.round(x), y);
			}
		}
	}

	public String ident() {
		return "Object." + fObject.name();
	}

	public String identNodeEdge() {
		return "OjectLink." + fObject.name();
	}

	@Override
	protected String getStoreType() {
		return "Object";
	}

	@Override
	public String toString() {
		return "ObjectNode[" + object().name() + "]";
	}

	@Override
	public void dispose() {
		super.dispose();
		this.fOpt.removeOptionChangedListener(fOptChaneListener);
	}

	/**
	 * Check if one object is greyed
	 * 
	 * @return true, if object is greyed; else return false
	 */
	public boolean isGreyed() {
		return fIsGreyed;
	}

	/**
	 * Change status to realize grey in and grey out
	 * 
	 * @param b
	 */
	public void setGreyed(boolean b) {
		fIsGreyed = b;
	}
}