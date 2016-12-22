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

package org.tzi.use.uml.ocl.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.uml.mm.MClassifierImpl;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.mm.MModel;

/**
 * An enumeration type.
 *
 * @author  Mark Richters
 */
public final class EnumType extends MClassifierImpl {
    /**
     * list of enumeration literals
     */
    private List<String> fLiterals;
    
    /**
     * for fast access
     */
    private Set<String> fLiteralSet;
    
    /**
     * Constructs an enumeration type with name and list of literals
     * (String objects). The list of literals is checked for
     * duplicates.
     */
    protected EnumType(MModel model, String name, List<String> literals) {
    	super(name, false);
        setModel(model);
        fLiterals = new ArrayList<String>(literals);
        fLiteralSet = new HashSet<String>(fLiterals.size());
        
        Iterator<String> it = fLiterals.iterator();
        while (it.hasNext() ) {
            String lit = it.next();
            if (! fLiteralSet.add(lit) )
                throw new IllegalArgumentException("duplicate literal `" +  lit + "'");
        }
    }
    
    @Override
    public boolean isTypeOfEnum() {
    	return true;
    }
    
    @Override
    public boolean isKindOfEnum(VoidHandling h) {
    	return true;
    }
    
    @Override
	public boolean isTypeOfClassifier() {
		return false;
	}

	/** 
     * Returns an iterator over the literals.
     */
    public Iterator<String> literals() {
        return fLiterals.iterator();
    }

    /**
     * Returns an unmodifiable list of literals for the enumeration
     * @return
     */
    public List<String> getLiterals() {
    	return Collections.unmodifiableList(fLiterals);
    }
    
    /** 
     * Returns true if this enumeration type contains the given literal.
     */
    public boolean contains(String lit) {
        return fLiteralSet.contains(lit);
    }

    /** 
     * Returns true if this type is a subtype of <code>t</code>. 
     */
    @Override
    public boolean conformsTo(Type t) {
        return equals(t) || t.isTypeOfOclAny();
    }

    /** 
     * Returns the set of all supertypes (including this type).
     */
    @Override
    public Set<Type> allSupertypes() {
        Set<Type> res = new HashSet<Type>(2);
        res.add(TypeFactory.mkOclAny());
        res.add(this);
        return res;
    }

    
	@Override
	public Set<? extends MClassifier> parents() {
		return Collections.emptySet();
	}

	@Override
	public Set<? extends MClassifier> allParents() {
		return Collections.emptySet();
	}

	@Override
	public Set<? extends MClassifier> allChildren() {
		return Collections.emptySet();
	}

	@Override
	public Set<? extends MClassifier> children() {
		return Collections.emptySet();
	}

	@Override
	public Iterable<? extends MClassifier> generalizationHierachie(boolean includeThis) {
		// We don't support generalization of enumerations, yet
		return new Iterable<MClassifier>() {
			@Override
			public Iterator<MClassifier> iterator() {
				return Collections.emptyIterator();
			}
		};
	}
	
	@Override
	public Iterable<? extends MClassifier> specializationHierachie(boolean includeThis) {
		// We don't support generalization of enumerations, yet
		return new Iterable<MClassifier>() {
			@Override
			public Iterator<MClassifier> iterator() {
				return Collections.emptyIterator();
			}
		};
	}

	@Override
	public void processWithVisitor(MMVisitor v) {
		v.visitEnum(this);
	}
}
