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

package org.tzi.use.uml.mm;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;

import com.google.common.collect.Iterators;

/**
 * 
 * @author Lars Hamann
 *
 */
public abstract class MClassifierImpl extends MModelElementImpl implements MClassifier {

	/**
     * Owner of this classifier
     */
    protected MModel model;
    
	/**
	 * To be able to sort by the USE file defined order.
	 */
    private int positionInModel = 0;
	
	/**
	 * abstract classifier?
	 */
	private boolean isAbstract;
	
	/**
	 * @param name
	 */
	protected MClassifierImpl(String name, boolean isAbstract) {
		super(name, "Classifier");
		this.isAbstract = isAbstract;
	}

	/**
	 * @param name
	 * @param prefix
	 */
	public MClassifierImpl(String name, String prefix) {
		super(name, prefix);
	}

	/**
     * Returns the model owning this class.
     */
    public MModel model() {
        return model;
    }

    /**
     * Sets the model owning this class. This method must be called by
     * MModel.addClass().  
     *
     * @see MModel#addClass
     */
    public void setModel(MModel model) {
        this.model = model;
    }
    
	@Override
    public boolean isAbstract() {
        return isAbstract;
    }

	/**
     * Returns the position in the defined USE-Model.
     */
    public int getPositionInModel() {
        return positionInModel;
    }

    /**
     * Sets the position in the defined USE-Model.
     */
    public void setPositionInModel(int position) {
        positionInModel = position;
    }
    
	@Override
	public boolean conformsTo(Type other) {
		if (other.equals(this)) return true;
		
		if (other.isKindOfClassifier(VoidHandling.EXCLUDE_VOID)) {
            MClassifier clf = (MClassifier)other;
            return this.allSupertypes().contains(clf);
        }
		
        return other.isTypeOfOclAny();
	}

	@Override
	public Set<Type> allSupertypes() {
		Set<Type> res = new HashSet<Type>(this.allParents());
		res.add(TypeFactory.mkOclAny());
		res.add(this);
		return res;
	}

	@Override
	public Type getLeastCommonSupertype(Type other) {
		if (other.isTypeOfVoidType()) return this;
		
		Type cType = TypeFactory.mkOclAny();
		
		// Object type and build in type have OclAny
    	if (!other.isKindOfClassifier(VoidHandling.EXCLUDE_VOID)) {
    		// Collections are no subtypes of OclAny
    		if (other.isKindOfCollection(VoidHandling.EXCLUDE_VOID)) {
    			return null;
    		} else {
    			return TypeFactory.mkOclAny();
    		}
    	}
    	
    	MClassifier oTypeThis = this;
    	MClassifier oTypeOther = (MClassifier)other;
    	        	
    	Set<MClassifier> superClassesThis = new HashSet<MClassifier>();
    	superClassesThis.add(oTypeThis);
    	
    	Set<? extends MClassifier> allP = oTypeOther.allParents();
    	Set<MClassifier> allSuperClassesOther = new HashSet<MClassifier>(allP);
    	allSuperClassesOther.add(oTypeOther);
    	
    	Set<MClassifier> commonClasses;
    	
    	while (!superClassesThis.isEmpty()) {
    		commonClasses = new HashSet<MClassifier>(superClassesThis);
    		commonClasses.retainAll(allSuperClassesOther);
    		
    		if (commonClasses.isEmpty()) {
    			Set<MClassifier> nextIteration = new HashSet<MClassifier>();
    			for (MClassifier cls : superClassesThis) {
    				for (MClassifier cf : cls.parents()) {
    					nextIteration.add(cf);
    				}
    			}
    			
    			superClassesThis = nextIteration;
    		} else {
    			// NOTE: We use the first class common to both!
    			cType = commonClasses.iterator().next();
    			break;
    		}
    	}
    	
    	return cType;
	}

	@Override
	public boolean isVoidOrElementTypeIsVoid() {
		return false;
	}

	@Override
	public boolean isKindOfNumber(VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfInteger() {
		return false;
	}

	@Override
	public boolean isKindOfInteger(VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfUnlimitedNatural() {
		return false;
	}

	@Override
	public boolean isKindOfUnlimitedNatural(VoidHandling h) {
		return false;
	}

	@Override
	public boolean isKindOfReal(VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfReal() {
		return false;
	}

	@Override
	public boolean isKindOfString(VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfString() {
		return false;
	}

	@Override
	public boolean isKindOfBoolean(VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfBoolean() {
		return false;
	}

	@Override
	public boolean isKindOfEnum(VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfEnum() {
		return false;
	}

	@Override
	public boolean isKindOfCollection(VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfCollection() {
		return false;
	}

	@Override
	public boolean isKindOfSet(VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfSet() {
		return false;
	}

	@Override
	public boolean isKindOfSequence(VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfSequence() {
		return false;
	}

	@Override
	public boolean isKindOfOrderedSet(VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfOrderedSet() {
		return false;
	}

	@Override
	public boolean isKindOfBag(VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfBag() {
		return false;
	}

	@Override
	public boolean isKindOfClassifier(VoidHandling h) {
		return true;
	}

	@Override
	public boolean isTypeOfClassifier() {
		return true;
	}

	@Override
	public boolean isKindOfClass(VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfClass() {
		return false;
	}
	
	@Override
	public boolean isKindOfAssociation(VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfAssociation() {
		return false;
	}

	@Override
	public boolean isKindOfOclAny(VoidHandling h) {
		return true;
	}

	@Override
	public boolean isTypeOfOclAny() {
		return false;
	}

	@Override
	public boolean isKindOfTupleType(VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfTupleType() {
		return false;
	}

	@Override
	public boolean isTypeOfVoidType() {
		return false;
	}

	@Override
	public boolean isInstantiableCollection() {
		return false;
	}

	@Override
	public StringBuilder toString(StringBuilder sb) {
		return sb.append(toString());
	}
	
	@Override
	public String shortName() {
		return name();
	}

	/**
     * Returns the set of all direct parent classifiers (without this classifier).
     *
     * @return Set(MClassifier) 
     */
	@Override
    public Set<? extends MClassifier> parents() {
        return model.generalizationGraph().targetNodeSet(this);
    }

    /**
     * Returns the set of all parent classes (without this
     * class). This is the transitive closure of the generalization
     * relation.
     *
     * @return Set(MClass) 
     */
	@Override
	public Set<? extends MClassifier> allParents() {
    	return model.generalizationGraph().targetNodeClosureSet(this);
    }

    /**
     * Returns the set of all child classes (without this class). This
     * is the transitive closure of the generalization relation.
     *
     * @return Set(MClass) 
     */
	@Override
	public Set<? extends MClassifier> allChildren() {
    	return model.generalizationGraph().sourceNodeClosureSet(this);
    }

    /**
     * Returns the set of all direct child classes (without this
     * class).
     *
     * @return Set(MClass) 
     */
	public Set<? extends MClassifier> children() {
    	return model.generalizationGraph().sourceNodeSet(this);
    }

	@Override
	public Iterable<? extends MClassifier> generalizationHierachie(final boolean includeThis) {
		return new Iterable<MClassifier>() {
			@Override
			public Iterator<MClassifier> iterator() {
				return model.generalizationGraph().targetNodeClosureSetIterator(MClassifierImpl.this, includeThis);
			}
		};
	}

	@Override
	public Iterable<? extends MClassifier> specializationHierachie(final boolean includeThis) {
		return new Iterable<MClassifier>() {
			@Override
			public Iterator<MClassifier> iterator() {
				return model.generalizationGraph().sourceNodeClosureSetIterator(MClassifierImpl.this, includeThis);
			}
		};
	}

	/**
     * Checks if <code>otherClassifier</code> is equal to this classifier 
     * or if it is a parent of this classifier. 
     */
    @Override
    public boolean isSubClassOf(MClassifier otherClass) {
        return isSubClassOf(otherClass, false);
    }

    @Override
    public boolean isSubClassOf(MClassifier otherClassifier, boolean excludeThis) {
        return Iterators.contains(this.generalizationHierachie(!excludeThis).iterator(), otherClassifier);
    }

    @Override
    public MAttribute attribute( String name, boolean searchInherited ) {
    	return null;
    }
    
	@Override
	public MNavigableElement navigableEnd(String rolename) {
		return null;
	}

	@Override
	public Map<String, ? extends MNavigableElement> navigableEnds() {
		return Collections.emptyMap();
	}
}
