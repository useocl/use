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

package org.tzi.use.uml.mm.commonbehavior.communications;

import java.util.Set;

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.ocl.type.MessageType;

/**
 * Interface for the meta class <code>Signal</code>.
 * @author Lars Hamann
 *
 */
public interface MSignal extends MClassifier {
	
	/**
	 * UML 2.4.1 p. 466
	 * The attributes owned by the signal. (Subsets Classifier::attribute, Namespace::ownedMember).
	 */
	public Set<MAttribute> getAttributes();

	/**
	 * Looks up the attribute of the signal with
	 * the given <code>name</code>.
	 * @param name
	 * @return The attribute with the given name or <code>null</code>.
	 */
	public MAttribute getAttribute(String name);

	/**
	 * Adds the attribute <code>attr</code> to the signal.
	 * @param attr The attribute to add.
	 * @throws MInvalidModelException If the attribute is not valid, e.g., name collision.
	 */
	public void addAttribute(MAttribute attr) throws MInvalidModelException;
	
	/**
	 * Returns all attributes including inherited ones.
	 * @return
	 */
	public Set<MAttribute> getAllAttributes();
	
	/**
	 * Sets a reference to the model the signal is contained in.
	 * @param model
	 */
	public void setModel(MModel model);

	/**
	 * @param line
	 */
	public void setPositionInModel(int line);
	
	@Override
	public Iterable<MSignal> generalizationHierachie(boolean includeThis);

	@Override
	public Set<MSignal> parents();
	
	/**
	 * @return
	 */
	public MessageType getType();
}
