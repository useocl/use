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

package org.tzi.use.uml.mm;

import java.util.Set;

/**
 * MAssociationClass instances represent associationclasses in a model.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public interface MAssociationClass extends MClass, MAssociation, MNavigableElement {

	@Override
    public Set<MAssociationClass> parents();
    
    
	@Override
    public Set<MAssociationClass> allParents();
    
	@Override
    public Set<MAssociationClass> allChildren();

	@Override
    public Set<MAssociationClass> children();
	
	@Override
	public Iterable<MAssociationClass> generalizationHierachie(boolean includeThis);
	
	@Override
	public Iterable<MAssociationClass> specializationHierachie(boolean includeThis);
}
