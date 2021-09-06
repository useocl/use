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

package org.tzi.use.uml.ocl.value;

import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.util.StringUtil;

/**
 * A value referring to a link
 * @author Lars Hamann
 *
 */
public class LinkValue extends Value {

	private final MLink link;
	
	/**
	 * @param t
	 * @param link
	 */
	public LinkValue(Type t, MLink link) {
		super(t);
		this.link = link;
	}

	public MLink getLink() {
		return this.link;
	}
	
	@Override
	public boolean isLink() {
		return true;
	}

	@Override
	public int compareTo(Value o) {
		if (!o.isLink()) {
			return -1;
		}
		
		LinkValue lv = (LinkValue)o;
		
		return this.toString().compareTo(lv.toString());
	}

	@Override
	public StringBuilder toString(StringBuilder sb) {
		//FIXME: Use name of real association if derived
		StringUtil.fmtSeq(sb, link.linkedObjects(), "_");
		sb.append("_").append(link.association());
		return sb;
	}

	@Override
	public int hashCode() {
		return 3 + link.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null || !(obj instanceof LinkValue)) {
			return false;
		}
		
		return this.link.equals(((LinkValue)obj).getLink());
	}

}
