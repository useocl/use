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

// $Id$

package org.tzi.use.gen.tool.statistics;

import org.tzi.use.uml.mm.MClassInvariant;

/**
 * Counts results of the evaluation of an invariant.
 * @author  Joern Bohling
 */
public class GInvariantStatistic extends GStatistic {
    
	private final MClassInvariant fInvariant;

	private boolean checkedByBarrier;
	
    public MClassInvariant getInvariant() {
        return fInvariant;
    }

    public GInvariantStatistic(MClassInvariant inv) {
    	super(inv.toString());
        fInvariant = inv;
    }

	/**
	 * If <code>true</code>, the encapsulated invariant is
	 * checked by a barrier instead of at the end of the search. 
	 * @return
	 */
	public boolean isCheckedByBarrier() {
		return checkedByBarrier;
	}
	
	public void setCheckedByBarrier(boolean newValue) {
		checkedByBarrier = newValue;
	}
}