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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClassifierImpl;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.ocl.type.MessageType;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.collections.CollectionUtil;

/**
 * Meta class for signals
 * UML-SS 2.4.1 p. 465 
 * @author Lars Hamann
 */
public class MSignalImpl extends MClassifierImpl implements MSignal {

	/**
	 * UML 2.4.1 p. 466
	 * The attributes owned by the signal. (Subsets Classifier::attribute, Namespace::ownedMember). 
	 * This association end is ordered.
	 */
	private Map<String,MAttribute> ownedAttribute = new HashMap<>();
	
	/**
	 * Constructs a new signal with the given <code>name</code>.
	 * @param name
	 */
	public MSignalImpl(String name, boolean isAbstract) {
		super(name, isAbstract);
	}

	public void addAttribute(MAttribute attr) throws MInvalidModelException {		
		for (MSignal signal : this.generalizationHierachie(true)) {
			if (signal.getAttribute(attr.name()) != null) {
				throw new MInvalidModelException("An attribute with name "
						+ StringUtil.inQuotes(attr.name())
						+ " is already defined in signal "
						+ StringUtil.inQuotes(signal.name()) + ".");
			}
		}
		
		this.ownedAttribute.put(attr.name(), attr);
	}
	
	@Override
	public Set<MAttribute> getAttributes() {
		return new HashSet<>(this.ownedAttribute.values());
	}
	
	@Override
	public MAttribute getAttribute(String name) {
		return this.ownedAttribute.get(name);
	}
	
	@Override
	public Set<MSignal> parents() {
		return CollectionUtil.downCastUnsafe(super.parents());
	}

	@Override
	public Set<MSignal> allParents() {
		return CollectionUtil.downCastUnsafe(super.allParents());
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" }) // Signals only inherit from other signals
	public Iterable<MSignal> generalizationHierachie(final boolean includeThis) {
		return (Iterable)super.generalizationHierachie(includeThis);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" }) // Signals only inherit from other signals
	public Iterable<MSignal> specializationHierachie(final boolean includeThis) {
		return (Iterable)super.specializationHierachie(includeThis);
	}

	@Override
	public Set<MSignal> allChildren() {
		return CollectionUtil.downCastUnsafe(super.allChildren());
	}

	@Override
	public Set<MSignal> children() {
		return CollectionUtil.downCastUnsafe(super.children());
	}

	@Override
	public void processWithVisitor(MMVisitor v) {
		v.visitSignal(this);
	}

	@Override
	public MessageType getType() {
		return new MessageType(this);
	}

	@Override
	public Set<MAttribute> getAllAttributes() {
		Set<MAttribute> attrs = new HashSet<MAttribute>(getAttributes());
		for (MSignal parent : generalizationHierachie(false)) {
			attrs.addAll(parent.getAttributes());
		}
		
		return attrs;
	}

	@Override
	public MNavigableElement navigableEnd(String rolename) {
		return null;
	}

	@Override
	public Map<String, MNavigableElement> navigableEnds() {
		return Collections.emptyMap();
	}
}
