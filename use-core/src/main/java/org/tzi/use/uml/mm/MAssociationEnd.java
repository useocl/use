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

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.util.collections.CollectionUtil;

/** 
 * An AssociationEnd stores information about the role a class plays
 * in an association.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public final class MAssociationEnd extends MModelElementImpl implements MNavigableElement {

    private MAssociation fAssociation; // Owner of this association end
    private MClass fClass;  // associated class
    private MMultiplicity fMultiplicity; // multiplicity spec
    private int fKind; // none, aggregation, or composition
    private boolean fIsOrdered; // use as Set or OrderedSet
    private boolean fIsNavigable = true; 
    private boolean fIsExplicitNavigable = false;
    
    /**
     *  This end is a derived union
     */
    private boolean isUnion;
    
    /**
     * <code>true</code> if this end is defined as derived.
     */
    private boolean isDerived;
    
    /**
     *  All ends this end subsets
     */
    private Set<MAssociationEnd> subsettedEnds = Collections.emptySet();
    /**
     *  All ends this end is subsetted by
     */
    private Set<MAssociationEnd> subsettingEnds = Collections.emptySet();
    /**
     *  All ends this end redefines
     */
    private Set<MAssociationEnd> redefinedEnds = Collections.emptySet();
    /**
     *  All ends this end is redefined by
     */
    private Set<MAssociationEnd> redefiningEnds = Collections.emptySet();
    /**
     *  Parameter For calculating derived values
     */
    private VarDeclList deriveParameter = null;
    /**
     *  For calculating derived values
     */
    private Expression deriveExpression = null;
    /**
     * List of defined qualifiers for this end.
     */
    private List<VarDecl> qualifier;
    /**
     *  For performance reasons
     */
    private int hashCode;
    

    /** 
     * Creates a new association end. 
     *
     * @param cls       the class to be connected.
     * @param rolename  role that the class plays in this association.
     * @param mult      multiplicity of end.
     * @param kind      MAggregationKind
     * @param isOrdered use as Set or Sequence
     * @param qualifiers The qualifiers at this end, if any. If param is <code>null</code> an empty list is used.
     */
    public MAssociationEnd(MClass cls, 
                           String rolename, 
                           MMultiplicity mult, 
                           int kind,
                           boolean isOrdered,
                           List<VarDecl> qualifiers) {
        super(rolename);
        fClass = cls;
        fMultiplicity = mult;
        setAggregationKind(kind);
        fIsOrdered = isOrdered;
        
        if (qualifiers == null)
        	this.qualifier = Collections.emptyList();
        else
        	this.qualifier = qualifiers;
    }
    
    /** 
     * Creates a new association end. 
     *
     * @param cls       the class to be connected.
     * @param rolename  role that the class plays in this association.
     * @param mult      multiplicity of end.
     * @param kind      MAggregationKind
     * @param isOrdered use as Set or OrderedSet
     * @param isExplicitNavigable needs to be true if this end is explicit
     *                            navigable
     **/
    public MAssociationEnd(MClass cls, 
                           String rolename, 
                           MMultiplicity mult, 
                           int kind,
                           boolean isOrdered,
                           boolean isExplicitNavigable) {
        super(rolename);
        fClass = cls;
        fMultiplicity = mult;
        setAggregationKind(kind);
        fIsOrdered = isOrdered;
        fIsExplicitNavigable = isExplicitNavigable;
    }

    private void setAggregationKind(int kind) {
        if (kind != MAggregationKind.NONE 
            && kind != MAggregationKind.AGGREGATION
            && kind != MAggregationKind.COMPOSITION )
            throw new IllegalArgumentException("Invalid kind");
        fKind = kind;
    }

    /**
     * Sets the owner association. This operation should be called by
     * an implementation of MAssociation.addAssociationEnd.
     *
     * @see MAssociation#addAssociationEnd
     */
    public void setAssociation(MAssociation assoc) {
        fAssociation = assoc;
        setHashCode();
    }

    /**
     * Returns the owner association.
     */
    public MAssociation association() {
        return fAssociation;
    }

    /**
     * Returns the associated class.
     */
    public MClass cls() {
        return fClass;
    }

    /**
     * Returns the multiplicity of this association end.
     */
    public MMultiplicity multiplicity() {
        return fMultiplicity;
    }

    /**
     * Returns the aggregation kind of this association end.
     */
    public int aggregationKind() {
        return fKind;
    }

    /**
     * Returns true if this association end is ordered.
     */
    public boolean isOrdered() {
        return fIsOrdered;
    }

    public boolean isExplicitNavigable() {
        return fIsExplicitNavigable;
    }
    public void setNavigable( boolean isNavigable ) {
        fIsNavigable = isNavigable;
    }
    public boolean isNavigable() {
        return fIsNavigable;
    }
    
    /**
     * Process this element with visitor.
     */
    public void processWithVisitor(MMVisitor v) {
        v.visitAssociationEnd(this);
    }

    private void setHashCode() {
        this.hashCode = name().hashCode() + fAssociation.hashCode() + fClass.hashCode();
    }

    public int hashCode() { 
        return hashCode;
    }

    /**
     * Two association end are equal iff they connect the same class
     * in the same association with the same role name.  
     */
    public boolean equals(Object obj) { 
        if (obj == this )
            return true;
        if (obj instanceof MAssociationEnd ) {
            MAssociationEnd aend = (MAssociationEnd) obj;
            return name().equals(aend.name()) 
                && fAssociation.equals(aend.association())
                && fClass.equals(aend.cls());
        }
        return false;
    }

    //////////////////////////////////////////////////
    // IMPLEMENTATION OF MNavigableElement
    //////////////////////////////////////////////////

    @Override
    public Type getType( Type sourceObjectType, MNavigableElement src, boolean qualifiedAccess ) {
    	Type t;
    	
    	if (this.getRedefiningEnds().size() > 0) {
    		t = getRedefinedType((MClass)sourceObjectType);
    	} else {
    		t = cls();
    	}
    	
        if ( src.equals( src.association() ) ) {
            return t;
        }
        
        return getType(t, src.hasQualifiers(), qualifiedAccess);
    }

    /**
     * Used internally to create a the correct collection 
     * type (OrderedSet, Set) if multiplicity is greater then 1. 
     * @param t The Type at the association end (maybe different from {@link MAssociationEnd#cls()})</code>
     * 			because of redefinition. 
     * @return The Type t or a collection with t as element type 
     */
    private Type getType(Type t, boolean sourceHasQualifiers, boolean qualifiedAccess) {
    	if (sourceHasQualifiers) {
    		if (qualifiedAccess) {
    			if (multiplicity().isCollection()) {
    				if ( isOrdered() )
    	                t = TypeFactory.mkOrderedSet( t );
    	            else
    	                t = TypeFactory.mkSet( t );
    			}
    		} else {
    			if ( isOrdered() )
	                t = TypeFactory.mkSequence( t );
	            else
	                t = TypeFactory.mkBag( t );
    		}
    	} else {
	    	if ( multiplicity().isCollection() ) {
	    		if (this.hasQualifiers() && !this.isOrdered()) {
	    			t = TypeFactory.mkBag( t );
	    		} else if (this.hasQualifiers() && this.isOrdered()) {
	    			t = TypeFactory.mkSequence( t );
	    		} else if ( !this.hasQualifiers() && this.isOrdered() ) {
	                t = TypeFactory.mkOrderedSet( t );
	    		} else {
	                t = TypeFactory.mkSet( t );
	    		}
	        } else if ( association().associationEnds().size() > 2 ) {
	            t = TypeFactory.mkSet(t);
	        }
    	}
    	
    	return t;
    }

    /**
     * Returns the type of this association end without taking
     * redefinition into account.
     * @return The type at this association end.
     */
    public Type getType() {
    	return this.getType(cls(), false, false);
    }
    
	private Type getRedefinedType(MClass sourceObjectType) {
		Type resultType = null;
		
		// If another association end redefines this end with 
		// source from the type of sourceObjectExp or a subtype of sourceObjectExp
		boolean foundDirectEnd = false;

		// No redefinition possible, because source type is the opposite of this end
		if (this.getAllOtherAssociationEnds().get(0).cls().equals(sourceObjectType) )
			return this.cls();
			
		for (MAssociationEnd redefiningEnd : this.getRedefiningEnds()) {
			// TODO: n-ary
			MAssociationEnd redefiningEndSrc = redefiningEnd.getAllOtherAssociationEnds().get(0);
			Type redefiningEndSrcType = redefiningEndSrc.cls();
			
			if (redefiningEndSrcType.equals(sourceObjectType)) {
				resultType = redefiningEnd.cls();
				foundDirectEnd = true;
				break;
			}
		}

		// No redefinitions with equal type found.
		// We need to check inheritance
		if (!foundDirectEnd) {
			for (MClass parent : sourceObjectType.parents()) {
				resultType = getRedefinedType(parent);
				
				if (resultType != null) {
					break;
				}
			}
		}
		
		return resultType;
	}

	@Override
    public String nameAsRolename() {
        return name();
    }

    private List<MAssociationEnd> allOtherEnds = null;
    /**
     * Returns the list of all other association ends participating
     * in the same association.
     * @return List(MAssociatioEnd)
     */
    public List<MAssociationEnd> getAllOtherAssociationEnds() {
        if (allOtherEnds == null) {
        	allOtherEnds = new LinkedList<MAssociationEnd>(fAssociation.associationEnds());
        	allOtherEnds.remove(this);
        }
        
        return allOtherEnds;
    }

	public void addSubsettedEnd(MAssociationEnd subsetsEnd) {
		this.subsettedEnds = CollectionUtil.initAsHashSet(this.subsettedEnds);
		this.subsettedEnds.add(subsetsEnd);
	}

	public Set<MAssociationEnd> getSubsettedEnds() {
		return this.subsettedEnds;
	}
	
	@Override
	public boolean isUnion() {
		return isUnion;
	}
	
	@Override
	public void setUnion(boolean newValue) {
		isUnion = newValue;
	}

	public void addSubsettingEnd(MAssociationEnd nSubsettingEnd) {
		this.subsettingEnds = CollectionUtil.initAsHashSet(this.subsettingEnds);
		this.subsettingEnds.add(nSubsettingEnd);
	}
	
	public Set<MAssociationEnd> getSubsettingEnds() {
		return this.subsettingEnds;
	}

	public Set<MAssociationEnd> getSubsettingEndsClosure() {
		Set<MAssociationEnd> closure = new HashSet<MAssociationEnd>();
		for (MAssociationEnd end : this.subsettingEnds) {
			closure.add(end);
			closure.addAll(end.getSubsettingEndsClosure());
		}
		return closure;
	}
	
	public void addRedefinedEnd(MAssociationEnd redefinedEnd) {
		this.redefinedEnds = CollectionUtil.initAsHashSet(this.redefinedEnds);
		this.redefinedEnds.add(redefinedEnd);
	}

	public Set<MAssociationEnd> getRedefinedEnds() {
		return this.redefinedEnds;
	}
	
	public void addRedefiningEnd(MAssociationEnd redefiningEnd) {
		this.redefiningEnds = CollectionUtil.initAsHashSet(this.redefiningEnds);
		this.redefiningEnds.add(redefiningEnd);	
	}
	
	public Set<MAssociationEnd> getRedefiningEnds() {
		return this.redefiningEnds;
	}

	/**
	 * Returns the transitive closure of all
	 * redefining association ends of this end without this.
	 * @return
	 */
	public Set<MAssociationEnd> getRedefiningEndsClosure() {
		Set<MAssociationEnd> closure = new HashSet<MAssociationEnd>();
		for (MAssociationEnd end : this.redefiningEnds) {
			closure.add(end);
			closure.addAll(end.getRedefiningEndsClosure());
		}
		return closure;
	}
	
	/**
	 * Sets the expression to calculate the derived value
	 * of this association end.
	 * Type has to be checked before!
	 * @param exp
	 */
	public void setDeriveExpression(VarDeclList parameter, Expression exp) {
		this.deriveExpression = exp;
		this.deriveParameter = parameter;
	}
	
	@Override
	public Expression getDeriveExpression() {
		return this.deriveExpression;
	}
	
	/**
	 * @return
	 */
	public VarDeclList getDeriveParamter() {
		return deriveParameter;
	}
	
	@Override
	public boolean isDerived() {
		return isDerived;
	}

	/**
	 * @param b
	 */
	public void setDerived(boolean b) {
		this.isDerived = b;
	}
	
	@Override
	public List<VarDecl> getQualifiers() {
		return qualifier;
	}

	@Override
	public boolean hasQualifiers() {
		return getQualifiers().size() > 0;
	}
	
	/* (non-Javadoc)
	 * @see org.tzi.use.uml.mm.MNavigableElement#isCollection()
	 */
	@Override
	public boolean isCollection() {
		return multiplicity().isCollection();
	}
}
