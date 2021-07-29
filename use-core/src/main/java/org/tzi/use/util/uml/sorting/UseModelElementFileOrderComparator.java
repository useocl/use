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

package org.tzi.use.util.uml.sorting;

import java.util.Comparator;

import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.mm.UseFileLocatable;

/**
 * A wrapper for {@link UseFileOrderComparator} using {@link MModelElement}s.
 * <p>
 * <b>Note:</b> All accessed members must be a subtype of {@link UseFileLocatable}
 * 
 * @author Frank Hilken
 */
public class UseModelElementFileOrderComparator implements Comparator<MModelElement> {
	private final UseFileOrderComparator comparator = new UseFileOrderComparator();

	/**
	 * {@inheritDoc}
	 * <p>
	 * <b>Note:</b> All accessed members must be a subtype of {@link UseFileLocatable}
	 * 
	 * @throws ClassCastException when elements are not subtype of {@link UseFileLocatable}
	 */
	@Override
	public int compare(MModelElement o1, MModelElement o2) {
		return comparator.compare((UseFileLocatable) o1, (UseFileLocatable) o2);
	}
}
