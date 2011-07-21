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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;


/**
 * An associationclass is a class and a association at once.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 * @see MAssociationImpl
 * @see MClassImpl
 */

class MAssociationClassImpl extends MModelElementImpl implements MAssociationClass {

    private MAssociationImpl fAssociationImpl;
    private MClassImpl fClassImpl;
    private int fPositionInModel;

    /**
     * Creates a new associationclass.
     *
     * @param   name        Name of the associationclass.
     * @param   isAbstract  If this value is true, the associationclass
     *                      is defined abstract.
     */
    MAssociationClassImpl( String name, boolean isAbstract ) {
        super( name );
        fClassImpl = new MClassImpl( name, isAbstract );
        fAssociationImpl = new MAssociationImpl( name );
    }

    /**
     * Process this element with visitor.
     */
    public void processWithVisitor( MMVisitor v ) {
        v.visitAssociationClass( this );
    }
    

    //////////////////////////////////////////////////
    // IMPLEMENTATION OF MNavigableElement
    //////////////////////////////////////////////////
    
    public MClass cls() {
        return this;
    }

    @Override
    public Type getType( Type sourceObjectType, MNavigableElement src, boolean qualifiedAccess ) {
        MAssociation assoc = src.association();
        if (assoc.associationEnds().size() > 2) 
            return TypeFactory.mkSet( TypeFactory.mkObjectType( this ) );
        
        MAssociationEnd otherEnd;
        if (src == assoc.associationEnds().get(0))
            otherEnd = (MAssociationEnd) assoc.associationEnds().get(1);
        else
            otherEnd = (MAssociationEnd) assoc.associationEnds().get(0);
        
        if (otherEnd.multiplicity().isCollection()) {
            if (otherEnd.isOrdered())
                return TypeFactory.mkSequence(TypeFactory.mkObjectType( this ));
            else
                return TypeFactory.mkSet(TypeFactory.mkObjectType( this ));
        }
        return TypeFactory.mkObjectType( this );
    }

    public MAssociation association() {
        return this;
    }


    //////////////////////////////////////////////////
    // DELEGATION OF MClassImpl
    //////////////////////////////////////////////////

    /**
     * Returns true if the class is marked abstract.
     */
    public boolean isAbstract() {
        return fClassImpl.isAbstract();
    }


    /**
     * Returns the name of this class with lowercase first letter.
     */
    public String nameAsRolename() {
        return fClassImpl.nameAsRolename();
    }
    
    /**
     * returns the corresponding type
     * @return the corresponding type
     */
    public ObjectType type() {
    	return fClassImpl.type();
    }

    /**
     * Returns the model owning this class.
     */
    public MModel model() {
        return fClassImpl.model();
    }

    /**
     * Sets the model owning this class. This method must be called by
     * MModel.addClass().
     *
     * @see MModel#addClass
     */
    public void setModel( MModel model ) {
        fClassImpl.setModel( model );
    }

    /**
     * Adds an attribute. The attribute must have a unique name within
     * the class and must not conflict with any attribute with the
     * same name defined in a superclass or subclass or with a
     * rolename of an association this class participates in.
     *
     * @exception MInvalidModelException the class already contains an
     *            attribute with the same name or a name clash
     *            occured.
     */
    public void addAttribute( MAttribute attr ) throws MInvalidModelException {
        // Well-Formedness Rule No. 1 of AssociationClass of OMG 1.4
        for (MAssociationEnd ae : associationEnds()) {
            if ( ae.name().equals( attr.name() ) ) {
                throw new MInvalidModelException(
                        "Ambiguous attribute name `" + attr.name() + "'." );
            }
        }
        fClassImpl.addAttribute( attr );
    }

    /**
     * Returns the set of attributes defined for this class. Inherited
     * attributes are not included.
     *
     * @return List(MAttribute)
     */
    public List<MAttribute> attributes() {
        return fClassImpl.attributes();
    }

    /**
     * Returns the set of all attributes (including inherited ones)
     * defined for this class.
     *
     * @return List(MAttribute)
     */
    public List<MAttribute> allAttributes() {
        return fClassImpl.allAttributes();
    }


    /**
     * Returns the specified attribute. Attributes are also looked up
     * in superclasses if <code>searchInherited</code> is true.
     *
     * @return null if attribute does not exist.
     */
    public MAttribute attribute( String name, boolean searchInherited ) {
        return fClassImpl.attribute( name, searchInherited );
    }

    /**
     * Adds an operation. The operation name must be unique among all
     * operations of this class. Operations in superclasses may be
     * redefined if the signature matches.
     *
     * @exception MInvalidModelException the operation is not unique
     *            or is not a valid redefinition.
     */
    public void addOperation( MOperation op ) throws MInvalidModelException {
        fClassImpl.addOperation( op );
        op.setClass(this);
    }

    /**
     * Returns all operations defined for this class. Inherited
     * operations are not included.
     */
    public List<MOperation> operations() {
        return fClassImpl.operations();
    }

    /**
     * Returns the set of all operations (including inherited ones)
     * defined for this class.
     *
     * @return List(MOperation) 
     */
    public List<MOperation> allOperations() {
        return fClassImpl.allOperations();
    }

    /**
     * Gets an operation by name. Operations are also looked up in
     * superclasses if <code>searchInherited</code> is true. This
     * method walks up the generalization hierarchy and selects the
     * first matching operation. Thus, if an operation is redefined,
     * this method returns the most specific one.
     *
     * @return null if operation does not exist.
     */
    public MOperation operation( String name, boolean searchInherited ) {
        return fClassImpl.operation( name, searchInherited );
    }


    /**
     * Returns the association end that can be reached by the
     * OCL expression <code>self.rolename</code>.
     *
     * @return null if no such association end exists.
     */
    public MNavigableElement navigableEnd( String rolename ) {
        return navigableEnds().get( rolename );
    }

    /**
     * Returns a map of all association ends that can be reached from
     * this class by navigation.
     *
     * @return Map(String, MNavigableElement)
     */
    public Map<String, MNavigableElement> navigableEnds() {
        Map<String, MNavigableElement> nav = fClassImpl.navigableEnds();

        for (MAssociationEnd ae : associationEnds()) {
            nav.put( ae.name(), ae );
        }
        
        return nav;
    }

    /**
     * Registers all association ends as navigable from this
     * class. This should be called by a MModel implementation when an
     * association is added to the model.
     *
     * @param associationEnds List(MAssociationEnd)
     * @see MModel#addAssociation
     */
    public void registerNavigableEnds( List<MNavigableElement> associationEnds ) {
        fClassImpl.registerNavigableEnds( associationEnds );
    }

    public void deleteNavigableElements () {
        fClassImpl.deleteNavigableElements();
    }
    
    /**
     * Returns the set of all direct parent classes (without this
     * class).
     *
     * @return Set(MClass)
     */
    public Set<MClass> parents() {
        return fClassImpl.parents();
    }

    /**
     * Returns the set of all parent classes (without this
     * class). This is the transitive closure of the generalization
     * relation.
     *
     * @return Set(MClass)
     */
    public Set<MClass> allParents() {
        return fClassImpl.allParents();
    }

    /**
     * Returns the set of all child classes (without this class). This
     * is the transitive closure of the generalization relation.
     *
     * @return Set(MClass)
     */
    public Set<MClass> allChildren() {
        return fClassImpl.allChildren();
    }

    /**
     * Returns the set of all direct child classes (without this
     * class).
     *
     * @return Set(MClass) 
     */
    public Set<MClass> children() {
        return fClassImpl.children();
    }
    
    /**
     * Returns the set of associations this class directly
     * participates in.
     *
     * @return Set(MAssociation).
     */
    public Set<MAssociation> associations() {
        return fClassImpl.associations();
    }

    /**
     * Returns the set of associations this class and all of its
     * parents participate in.
     *
     * @return Set(MAssociation).
     */
    public Set<MAssociation> allAssociations() {
        return fClassImpl.allAssociations();
    }

    /**
     * Returns true if this class is a child of
     * <code>otherClass</code> or equal to it.
     */
    public boolean isSubClassOf( MClass otherClass ) {
        return fClassImpl.isSubClassOf( otherClass );
    }


    //////////////////////////////////////////////////
    // DELEGATION OF MAssociationImpl
    //////////////////////////////////////////////////

    /**
     * Adds an association end.
     *
     * @exception   MInvalidModelException trying to add another composition
     *              or aggregation end.
     * @see         MAssociation#addAssociationEnd( MAssociationEnd )
     */
    public void addAssociationEnd( MAssociationEnd aend ) throws MInvalidModelException {
        // Aggregation or composition is not allowed in combination
        // with a ternary associationclass
        if ( aggregationKind() == MAggregationKind.AGGREGATION
                || aggregationKind() == MAggregationKind.COMPOSITION ) {
            if ( associationEnds().size() >= 2 ) {
                throw new MInvalidModelException( "An aggregation or composition "
                                                  + "is not allowed in combination with "
                                                  + "a ternary associationclass" );
            }
        }

        // Well-Formedness Rule No. 1 of AssociationClass of OMG 1.4
        for (MAttribute attr : attributes()) {
            if ( aend.name().equals( attr.name() ) ) {
                throw new MInvalidModelException(
                        "Ambiguous rolename `" + aend.name() + "'." );
            }
        }

        // Well-Formedness Rule No. 2 of AssociationClass of OMG 1.4
        if ( aend.cls() == this ) {
            throw new MInvalidModelException(
                    "An associationclass cannot be defiened between itself and something else." );
        }

        fAssociationImpl.addAssociationEnd( aend );
        aend.setAssociation( this );
    }

    public List<MNavigableElement> reachableEnds() {
        List<MNavigableElement> ends = new ArrayList<MNavigableElement>( associationEnds() );
        ends.add( this );
        return ends;
    }

    /**
     * Returns the list of association ends.
     *
     * @return  List(MAssociationEnd)
     * @see     MAssociation#associationEnds()
     */
    public List<MAssociationEnd> associationEnds() {
        return fAssociationImpl.associationEnds();
    }
    
    
    /**
     * TODO
     */
    public List<String> roleNames() {
    	return fAssociationImpl.roleNames();
    }

    /**
     * Returns the set of association ends attached to <code>cls</code>.
     *
     * @return  Set(MAssociationEnd)
     * @see     MAssociation#associationEndsAt( MClass )
     */
    public Set<MAssociationEnd> associationEndsAt( MClass cls ) {
        return fAssociationImpl.associationEndsAt( cls );
    }

    /**
     * Returns the set of classes participating in this association.
     *
     * @return  Set(MClass).
     * @see     MAssociation#associatedClasses()
     */
    public Set<MClass> associatedClasses() {
        return fAssociationImpl.associatedClasses();
    }

    /**
     * Returns kind of association. This operation returns aggregate
     * or composition if one of the association ends is aggregate or
     * composition.
     *
     * @see     MAssociation#aggregationKind()
     */
    public int aggregationKind() {
        return fAssociationImpl.aggregationKind();
    }

    /**
     * Returns a list of association ends which can be reached by
     * navigation from the given class. Examples:
     *
     * <ul>
     * <li>For an association R(A,B), R.navigableEndsFrom(A)
     * results in (B).
     *
     * <li>For an association R(A,A), R.navigableEndsFrom(A) results
     * in (A,A).
     *
     * <li>For an association R(A,B,C), R.navigableEndsFrom(A) results
     * in (B,C).
     *
     * <li>For an association R(A,A,B), R.navigableEndsFrom(A) results
     * in (A,A,B).
     * </ul>
     *
     * This operation does not consider associations in which parents
     * of <code>cls</code> take part.
     *
     * @return      List(MNavigableElements)
     * @exception   IllegalArgumentException cls is not part of this association.
     * @see         MAssociation#navigableEndsFrom( MClass )
     */
    public List<MNavigableElement> navigableEndsFrom( MClass cls ) {
        List<MNavigableElement> nav = fAssociationImpl.navigableEndsFrom( cls );
        nav.add( this );
        return nav;
    }

    public Collection<MNavigableElement> navigableElements() {
        List<MNavigableElement> ne = new ArrayList<MNavigableElement>( navigableEnds().values() );
        ne.add( this );
        return ne;
    }

    /**
     * Returns the position in the defined USE-Model.
     */
    public int getPositionInModel() {
        return fPositionInModel;
    }

    /**
     * Sets the position in the defined USE-Model.
     */
    public void setPositionInModel(int position) {
        fPositionInModel = position;
    }

    public boolean isAssignableFrom(MClass[] classes) {
        return fAssociationImpl.isAssignableFrom(classes);
    }

	@Override
	public void addSubsets(MAssociation asso) {
		this.fAssociationImpl.addSubsets(asso);
	}

	@Override
	public Set<MAssociation> getSubsets() {
		return this.fAssociationImpl.getSubsets();
	}

	
	@Override
	public List<MAssociationEnd> getAssociationEnd(String subsetsRolename) {
		return this.fClassImpl.getAssociationEnd(subsetsRolename);
	}

	@Override
	public boolean isUnion() {
		return this.fAssociationImpl.isUnion();
	}

	@Override
	public void setUnion(boolean newValue) {
		this.fAssociationImpl.setUnion(newValue);
	}
	
	@Override
	public void addSubsettedBy(MAssociation asso) {
		this.fAssociationImpl.addSubsettedBy(asso);
	}

	@Override
	public Set<MAssociation> getSubsettedBy() {
		return this.fAssociationImpl.getSubsettedBy();
	}

	@Override
	public MAssociationEnd getAssociationEnd(MClass endCls, String rolename) {
		return this.fAssociationImpl.getAssociationEnd(endCls, rolename);
	}

	@Override
	public void addRedefinedBy(MAssociation association) {
		this.fAssociationImpl.addRedefinedBy(association);		
	}

	@Override
	public void addRedefines(MAssociation parentAssociation) {
		this.fAssociationImpl.addRedefines(parentAssociation);
	}

	@Override
	public Set<MAssociation> getSubsettedByClosure() {
		return this.fAssociationImpl.getSubsettedByClosure();
	}

	@Override
	public Set<MAssociation> getSubsetsClosure() {
		return this.fAssociationImpl.getSubsetsClosure();
	}

	@Override
	public Set<MAssociation> getRedefinedByClosure() {
		return this.fAssociationImpl.getRedefinedBy();
	}

	@Override
	public Set<MAssociation> getRedefinesClosure() {
		return this.fAssociationImpl.getRedefinesClosure();
	}

	@Override
	public Set<MAssociation> getRedefinedBy() {
		return this.fAssociationImpl.getRedefinedBy();
	}

	@Override
	public Set<MAssociation> getRedefines() {
		return this.fAssociationImpl.getRedefines();
	}

	@Override
	public Set<MAssociationEnd> getSubsettingEnds() {
		return Collections.emptySet();
	}

	@Override
	public Set<MAssociationEnd> getRedefiningEnds() {
		return Collections.emptySet();
	}
	
	@Override 
	public boolean isDerived() {
		return false;
	}
	
	@Override
	public Expression getDeriveExpression() {
		return null;
	}
	
	@Override
	public boolean isReadOnly() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.mm.MNavigableElement#getAllOtherAssociationEnds()
	 */
	@Override
	public List<MAssociationEnd> getAllOtherAssociationEnds() {
		return this.fAssociationImpl.associationEnds();
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.mm.MAssociation#hasQualifiedEnds()
	 */
	@Override
	public boolean hasQualifiedEnds() {
		return this.fAssociationImpl.hasQualifiedEnds();
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.mm.MNavigableElement#getQualifiers()
	 */
	@Override
	public List<VarDecl> getQualifiers() {
		return Collections.emptyList();
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.mm.MNavigableElement#hasQualifiers()
	 */
	@Override
	public boolean hasQualifiers() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.mm.MAssociation#getSourceEnd(org.tzi.use.uml.mm.MClass, org.tzi.use.uml.mm.MNavigableElement, java.lang.String)
	 */
	@Override
	public MNavigableElement getSourceEnd(MClass srcClass,
			MNavigableElement dst, String explicitRolename) {
		if (srcClass == this) {
			return this;
		}
		
		return this.fAssociationImpl.getSourceEnd(srcClass, dst, explicitRolename);
	}
	
	/* (non-Javadoc)
	 * @see org.tzi.use.uml.mm.MNavigableElement#isCollection()
	 */
	@Override
	public boolean isCollection() {
		return false;
	}
}
