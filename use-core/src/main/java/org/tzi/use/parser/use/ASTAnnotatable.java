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

package org.tzi.use.parser.use;

import java.util.Collections;
import java.util.Set;

import org.tzi.use.parser.AST;
import org.tzi.use.uml.mm.Annotatable;

/**
 * AST node with possible annotations
 * @author Lars Hamann
 *
 */
public abstract class ASTAnnotatable extends AST {
	
	private Set<ASTAnnotation> annotations = Collections.emptySet();
	
	public void setAnnotations(Set<ASTAnnotation> annotations) {
		this.annotations = annotations; 
	}
	
	public void genAnnotations(Annotatable element) {
		if (this.annotations == null || element == null) return;
		
		for (ASTAnnotation an : this.annotations) {
			element.addAnnotation(an.gen());
		}
	}
}
