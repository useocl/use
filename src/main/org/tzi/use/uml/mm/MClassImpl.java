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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.util.collections.CollectionUtil;

/**
 * MClass instances represent classes in a model.
 *
 * @author  Mark Richters
 * @author  Lars Hamann
 */
public class MClassImpl extends MClassifierImpl implements MClass {
   
	/**
	 * All defined attributes of this class excluding inherited ones.
	 */
    private Map<String, MAttribute> fAttributes;
    
    private Map<String, MOperation> fOperations;
    
    /**
     * Maps all operations (including inherited)
     */
    private Map<String, MOperation> fVTableOperations;
        
    // other classes reachable by associations 
    private Map<String, MNavigableElement> fNavigableElements;

    /**
     * All owned PSM
     */
    private Set<MProtocolStateMachine> ownedProtocolStateMachines = Collections.emptySet();
    
    MClassImpl(String name, boolean isAbstract) {
        super(name, isAbstract);
        fAttributes = new TreeMap<String, MAttribute>();
        fOperations = new TreeMap<String, MOperation>();
        fVTableOperations = new HashMap<String, MOperation>();
        fNavigableElements = new HashMap<String, MNavigableElement>();
    }

    /**
     * Returns the name of this class with lowercase first letter.
     */
    public String nameAsRolename() {
        return Character.toLowerCase(name().charAt(0)) + name().substring(1);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" }) // Class only inherit from other classes
	public Iterable<MClass> generalizationHierachie(final boolean includeThis) {
    	return (Iterable)super.generalizationHierachie(includeThis);
	}
    
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" }) // Class only inherit from other classes
	public Iterable<MClass> specializationHierachie(final boolean includeThis) {
		return (Iterable)super.specializationHierachie(includeThis);
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
    public void addAttribute(MAttribute attr) throws MInvalidModelException {
        // check if there already exists an attribute with the
        // specified name in this class
        String attrName = attr.name();
        if (fAttributes.containsKey(attrName) )
            throw new MInvalidModelException("Class `" + name() + 
                                             "' already contains an attribute named `" + attrName + "'.");

        // check if attribute of same name is already defined in a
        // superclass
        if (attribute(attrName, true) != null )
            throw new MInvalidModelException("Attribute with name `" + 
                                             attrName + 
                                             "' already defined in a superclass.");
    
        // check if attribute of same name is defined in a subclass
        for (MClass cls : allChildren() ) {
            if (cls.attribute(attrName, false) != null )
                throw new MInvalidModelException("Attribute `" + 
                                                 attrName + "' conflicts with existing attribute " +
                                                 "in class `" + cls.name() + "'.");
        }
        
        // add attribute
        fAttributes.put(attr.name(), attr);
        attr.setOwner(this);
    }

    /**
     * Returns the set of attributes defined for this class. Inherited
     * attributes are not included.  
     *
     * @return List(MAttribute)
     */
    public List<MAttribute> attributes() {
        return new ArrayList<MAttribute>(fAttributes.values());
    }

    /**
     * Returns the set of all attributes (including inherited ones)
     * defined for this class.
     *
     * @return List(MAttribute) 
     */
    public List<MAttribute> allAttributes() {
        List<MAttribute> result = new ArrayList<MAttribute>();
        
        // start with local attributes
        result.addAll(attributes());

        // add attributes from all super classes
        for (MClass cls : allParents() ) {
            result.addAll(cls.attributes());
        }
        
        return result;
    }

    /**
     * Returns the specified attribute. Attributes are also looked up
     * in superclasses if <code>searchInherited</code> is true.
     *
     * @return null if attribute does not exist.  
     */
    public MAttribute attribute(String name, boolean searchInherited) {
        MAttribute a = fAttributes.get(name);
        if (a == null && searchInherited ) {
            for (MClass cls : allParents() ) {
                a = cls.attribute(name, false);
                if (a != null )
                    break;
            }
        }
        return a;
    }

    /** 
     * Adds an operation. The operation name must be unique among all
     * operations of this class. Operations in superclasses may be
     * redefined if the signature matches.
     *
     * @exception MInvalidModelException the operation is not unique
     *            or is not a valid redefinition.
     */
    public void addOperation(MOperation op) throws MInvalidModelException {
        // check if there already exists an operation with the same
        // name in this class
        String opname = op.name();
        if (fOperations.containsKey(opname) )
            throw new MInvalidModelException("Class `" + name() + 
                                             "' already contains an operation named `" + opname + "'.");

        // check if operation with same signature is already defined
        // in a superclass. A redefinition is only allowed with same
        // number and types of arguments.
        MOperation superOp = operation(opname, true);
        if (superOp != null ) {
            if (!op.isValidOverrideOf(superOp) )
                throw new MInvalidModelException("Redefinition of operation `" + superOp +
                                                 "' requires same number and type of arguments.");
        }
    
        // check if operation with same signature is already defined
        // in a subclass. A redefinition is only allowed with same
        // number and types of arguments.
        for (MClass cls : allChildren()) {
            MOperation subOp = cls.operation(opname, false);
            if (subOp != null )
                if (!subOp.isValidOverrideOf(op) )
                    throw new MInvalidModelException("Operation `" + op +
                                                     "' does not match its redefinition in class `" + 
                                                     cls + "'.");
        }
    
        fOperations.put(op.name(), op);
        fVTableOperations.put(op.name(), op);
        
        op.setClass(this);
    }

	/**
     * Returns all operations defined for this class. Inherited
     * operations are not included.
     */
    public List<MOperation> operations() {
        return new ArrayList<MOperation>( fOperations.values() );
    }


    /**
     * Returns the set of all operations (including inherited ones)
     * defined for this class.
     *
     * @return List(MOperation) 
     */
    public List<MOperation> allOperations() {
        List<MOperation> result = new ArrayList<MOperation>();
        
        // start with local operations
        result.addAll(operations());

        // add operations from all superclasses
        for (MClass cls : allParents()) {
            result.addAll(cls.operations());
        }
        
        return result;
    }

    /**
     * Gets an operation by name. Operations are also looked up in
     * superclasses if <code>searchInherited</code> is true. This
     * method walks up the generalization hierarchy and selects the
     * first matching operation. Thus, if an operation is redefined,
     * this method returns the most specific one.
     *
     * @return <code>null</code> if operation does not exist. 
     */
    public MOperation operation(String name, boolean searchInherited) {
    	MOperation op;
    	
    	if (searchInherited) {
    		op = fVTableOperations.get(name);
			
    		if (op != null)
    			return op;
    			
			for (MClass cls : parents()) {
				op = cls.operation(name, false);
				if (op != null) {
					fVTableOperations.put(name, op);
					return op;
				}
			}
			// FIXME: The compiler has to check a unique binding in case of multiple inheritance
			for (MClass cls : parents()) {
				op = cls.operation(name, true);
				if (op != null) {
					fVTableOperations.put(name, op);
					return op;
				}
			}
    	} else{
    		op = fOperations.get(name);
    	}
        
        return op;
    }


    /**
     * Returns the association end that can be reached by the
     * OCL expression <code>self.rolename</code>.
     *
     * @return <code>null</code> if no such association end exists.
     */
    public MNavigableElement navigableEnd(String rolename) {
        return navigableEnds().get( rolename );
    }

    /**
     * Returns a map of all association ends that can be reached from
     * this class by navigation.
     *
     * @return Map(String, MAssociationEnd) 
     */
    public Map<String, MNavigableElement> navigableEnds() {
        Map<String, MNavigableElement> res = new TreeMap<String, MNavigableElement>();
        res.putAll(fNavigableElements);
        
        // recursively add association ends in superclasses
        for (MClass superclass : parents() ) {
            res.putAll(superclass.navigableEnds());
        }
        return res;
    }

    /**
     * Registers all association ends as navigable from this
     * class. This should be called by a MModel implementation when an
     * association is added to the model.
     *
     * @param navigableElements List(MNavigableElement) 
     * @see MModel#addAssociation
     */
    public void registerNavigableEnds(List<MNavigableElement> navigableElements) {
        for (MNavigableElement nav : navigableElements) {
            fNavigableElements.put(nav.nameAsRolename(), nav);
        }
    }

    
    @Override
	public boolean isTypeOfClassifier() {
		return false;
	}

	@Override
	public boolean isKindOfClass(VoidHandling h) {
		return true;
	}

	@Override
	public boolean isTypeOfClass() {
		return true;
	}

	/**
     * Returns the set of all direct parent classes (without this
     * class).
     *
     * @return Set(MClass) 
     */
    @Override
    public Set<MClass> parents() {
    	return model.generalizationGraph().targetNodeSet(MClass.class, this);
    }

    /**
     * Returns the set of all parent classes (without this
     * class). This is the transitive closure of the generalization
     * relation.
     * The set is unmodifiable!
     *
     * @return Set(MClass) An unmodifiable set of all parent classes
     */
    public Set<MClass> allParents() {
        return Collections.unmodifiableSet(model.generalizationGraph().targetNodeClosureSet(MClass.class, this));
    }

    /**
     * Returns the set of all child classes (without this class). This
     * is the transitive closure of the generalization relation.
     * The set is unmodifiable!
     *
     * @return Set(MClass) An unmodifiable set of all child classes
     */
    public Set<MClass> allChildren() {
        return Collections.unmodifiableSet(model.generalizationGraph().sourceNodeClosureSet(MClass.class, this));
    }

    /**
     * Returns the set of all direct child classes (without this
     * class).
     *
     * @return Set(MClass) 
     */
    public Set<MClass> children() {
        return model.generalizationGraph().sourceNodeSet(MClass.class, this);
    }
    
    /**
     * Returns a new set of associations this class directly
     * participates in.
     *
     * @return Set(MAssociation).  
     */
    public Set<MAssociation> associations() {
        Set<MAssociation> res = new HashSet<MAssociation>();

        for(MNavigableElement aend : fNavigableElements.values()) {
            res.add(aend.association());
        }
        
        return res;
    }

    /**
     * Returns the set of associations this class and all of its
     * parents participate in.
     *
     * @return Set(MAssociation).  
     */
    public Set<MAssociation> allAssociations() {
        Set<MAssociation> res = new HashSet<MAssociation>();
        res.addAll(associations());
        
        for (MClass cls : allParents()) {
            res.addAll(cls.associations());
        }
        
        return res;
    }
    
    /**
     * Process this element with visitor.
     */
    public void processWithVisitor(MMVisitor v) {
        v.visitClass(this);
    }

	@Override
	public List<MAssociationEnd> getAssociationEnd(String roleName) {
		List<MAssociationEnd> result = new LinkedList<MAssociationEnd>();
		
		for (MClass cls : this.generalizationHierachie(true)) {
			for (MAssociation assoc : cls.associations()) {
				Set<MAssociationEnd> ends = assoc.associationEndsAt(cls);
				for (MAssociationEnd end : ends) {
					if (end.nameAsRolename().equals(roleName))
						result.add(end);
				}
			}
		}
		return result;
	}

	@Override
	public void addOwnedProtocolStateMachine(MProtocolStateMachine psm) {
		this.ownedProtocolStateMachines = CollectionUtil.initAsHashSet(this.ownedProtocolStateMachines);
		this.ownedProtocolStateMachines.add(psm);
	}

	@Override
	public Set<MProtocolStateMachine> getOwnedProtocolStateMachines() {
		return Collections.unmodifiableSet(this.ownedProtocolStateMachines);
	}

	@Override
	public boolean hasStateMachineWhichHandles(MOperationCall operationCall) {
		for (MClass general : generalizationHierachie(true)) {
			for (MProtocolStateMachine psm : general.getOwnedProtocolStateMachines()) {
				if (psm.handlesOperation(operationCall.getOperation()))
					return true;
			}
		}
				
		return false;
	}

	@Override
	public Set<MProtocolStateMachine> getAllOwnedProtocolStateMachines() {
		Set<MProtocolStateMachine> psms = new HashSet<MProtocolStateMachine>();
		for (MClass cls : generalizationHierachie(true)) {
			psms.addAll(cls.getOwnedProtocolStateMachines());
		}
		return psms;
	}
}