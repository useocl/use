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

// $Id$

package org.tzi.use.gui.views.diagrams.elements;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.tzi.use.gui.views.diagrams.DiagramOptionChangedListener;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToAttached;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToAttached.Placement;
import org.tzi.use.gui.views.diagrams.waypoints.AttachedWayPoint;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.util.StringUtil;

/**
 * Represents a role name node in a diagram. 
 * 
 * @author Fabian Gutsche
 * @author Lars Hamann
 */
public final class Rolename extends EdgeProperty implements DiagramOptionChangedListener {

	MAssociationEnd fAssocEnd;
    
	/**
     * 
     * @param assocEnd The <code>MAassociationEnd</code> this role name belongs to.
     * @param source The <code>NodeBase</code> which represents the class or object node this role name is attached to. 
     * @param target The <code>NodeBase</code> which represents the class, object or diamond node of the opposite end.
     * @param sourceWayPoint
     * @param targetWayPoint
     * @param opt
     * @param side
     * @param edge
     */
    public Rolename( String id, MAssociationEnd assocEnd, AttachedWayPoint attached, DiagramOptions opt, EdgeBase edgeBase ) {
    	super(id, new PlaceableNode[] {attached}, false, opt, edgeBase);
    	
        fAssocEnd = assocEnd;
        fAssoc = fAssocEnd.association();
        
        setName();
        
        fOpt.addOptionChangedListener(this);
        setStrategy(new StrategyRelativeToAttached(this, attached, Placement.TOP, 8, 8)); 
    }
    
    private void setName() {
    	List<String> constraints = new ArrayList<String>();
    		
    	if (fAssocEnd.isOrdered()) {
    		constraints.add("ordered");
    	}
    	
    	if (fOpt.isShowUnionConstraints() &&  fAssocEnd.isUnion()) {
    		constraints.add("union");
    	}
    	
    	if (fOpt.isShowSubsetsConstraints()) {
			for (MAssociationEnd subsettedEnd : fAssocEnd.getSubsettedEnds()) {
				constraints.add("subsets " + subsettedEnd.nameAsRolename());
			}
    	}
    	
    	if (fOpt.isShowRedefinesConstraints()) {
			for (MAssociationEnd redefinedEnd : fAssocEnd.getRedefinedEnds()) {
				constraints.add("redefines " + redefinedEnd.nameAsRolename());    			
			}
    	}
    	
		if (fAssocEnd.isDerived())
			fName = "/" + fAssocEnd.nameAsRolename();
		else
			fName = fAssocEnd.nameAsRolename();
    	
    	if (constraints.size() > 0) {
    		fName = fName + " {" + StringUtil.fmtSeq(constraints.iterator(), ", ") + "}";
    	}
    }
    
    public MAssociationEnd getEnd() {
    	return fAssocEnd;
    }
    
    /**
     * Draws a role name on a binary edge.
     */
    @Override
    protected void onDraw( Graphics2D g ) {
    	if ( fAssocEnd == null || !fAssocEnd.isNavigable() )
    		return;
    	
    	if (forceRecalc) {
    		calculateSize(g);
    		forceRecalc = false;
    	}
    	
    	super.onDraw(g);
    }

	@Override
    public String getStoreType() {
    	return "rolename";
    }
	
	@Override
	public String toString() {
		return "Rolename: " + fName;
	}

	/**
	 * @param newText
	 */
	public void setText(String newText) {
		setName(newText);
	}

	private boolean forceRecalc = false;
	
	@Override
	public void optionChanged(String optionname) {
		if (optionname.equals("SHOW_UNION_CONSTRAINTS")   ||
			optionname.equals("SHOW_SUBSETS_CONSTRAINTS") ||
			optionname.equals("SHOW_REDEFINES_CONSTRAINTS")    ) {
			setName();
			//FIXME: CHANGE!!!
			forceRecalc = true;
		}
	}
}
