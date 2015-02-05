/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

package org.tzi.use.gui.views.evalbrowser;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.tzi.use.uml.ocl.expr.EvalNode;
import org.tzi.use.uml.ocl.value.VarBindings.Entry;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.StringUtil.IElementFormatter;

/**
 * This is a virtual node for the evaluation tree,
 * to represent (multiple) variable assignments in a node.
 * @author Lars Hamann
 *
 */
public class EvalNodeVarAssignment extends EvalNode {

	/**
	 * Variable assignments used to dense multiple
	 * assignments into one tree node.
	 */
	private List<Entry> fVarAss = new ArrayList<>();

	/**
	 * @param vars
	 */
	public EvalNodeVarAssignment(Vector<Entry> vars) {
		super(vars);
	}

	/**
	 * adds the next Variable-assignment for Variable-Assignment-EvalNodes
	 */
	public void addVarAssignment(Entry assignment) {
	    this.fVarAss.add(assignment);
	}

	public boolean isEarlyVarNode() {
        return true;
    }
	
	@Override
	public String getExprAndValue(boolean substituteVariables) {
		return getExpressionString(substituteVariables);
	}

	@Override
	public String getExpressionString(boolean substituteVariables) {
		return StringUtil.fmtSeq(fVarAss.iterator(), ", ", new IElementFormatter<Entry>() {
			@Override
			public String format(Entry element) {
				return element.getVarName() + " = " + (element.getValue().isObject()?"@":"") + element.getValue().toString();
			}});
	}
}
