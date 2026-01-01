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

package org.tzi.use.uml.mm;

import com.google.common.collect.Iterators;
import org.tzi.use.uml.api.IType;
import org.tzi.use.uml.api.TypeFactoryProvider;
import org.tzi.use.uml.sys.MOperationCall;

import java.util.*;

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
	 * All defined attributes of this classifier excluding inherited ones.
	 */
	protected Map<String, MAttribute> fAttributes;

	protected Map<String, MOperation> fOperations;

	/**
	 * Maps all operations (including inherited)
	 */
	protected Map<String, MOperation> fVTableOperations;

	private boolean isQualifiedAccess = false;

	/**
	 * @param name
	 */
	protected MClassifierImpl(String name, boolean isAbstract) {
		super(name, "Classifier");
		this.isAbstract = isAbstract;
		fAttributes = new TreeMap<String, MAttribute>();
		fOperations = new TreeMap<String, MOperation>();
		fVTableOperations = new HashMap<String, MOperation>();
	}

	/**
	 * @param name
	 * @param prefix
	 */
	public MClassifierImpl(String name, String prefix) {
		super(name, prefix);
	}

	/**
     * Returns the model owning this classifier.
     */
    public MModel model() {
        return model;
    }

    /**
     * Sets the model owning this classifier. This method must be called by
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
	public boolean conformsTo(IType other) {
		if (other.equals(this)) return true;
		
		if (other.isKindOfClassifier(IType.VoidHandling.EXCLUDE_VOID)) {
			if (other instanceof MClassifier) {
				MClassifier clf = (MClassifier) other;
				return this.allSupertypes().contains(clf);
			}
			return false;
		}

		// other is not a classifier -> true only if it is OclAny
		return other.isTypeOfOclAny();
	}

	@Override
	public Set<? extends IType> allSupertypes() {
		Set<IType> res = new HashSet<IType>();
		// add all parent classifiers (they are MClassifier and thus IType)
		for (MClassifier p : allParents()) {
			res.add(p);
		}
		// add the universal OclAny type via the API factory
		res.add(TypeFactoryProvider.get().mkOclAny());
		res.add(this);
		return res;
	}

	@Override
	public IType getLeastCommonSupertype(IType other) {
		if (other.isTypeOfVoidType()) return this;

		// If other is not a classifier, handle collections vs OclAny
		if (!other.isKindOfClassifier(IType.VoidHandling.EXCLUDE_VOID)) {
			if (other.isKindOfCollection(IType.VoidHandling.EXCLUDE_VOID)) {
				return null;
			} else {
				return TypeFactoryProvider.get().mkOclAny();
			}
		}

		if (!(other instanceof MClassifier)) {
			// treat as most general classifier
			return TypeFactoryProvider.get().mkOclAny();
		}

		MClassifier oTypeOther = (MClassifier) other;

		Set<MClassifier> superClassesThis = new HashSet<MClassifier>();
		superClassesThis.add(this);

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
					nextIteration.addAll(cls.parents());
				}

				superClassesThis = nextIteration;
			} else {
				// NOTE: We use the first class common to both!
				return commonClasses.iterator().next();
			}
		}

		return TypeFactoryProvider.get().mkOclAny();
	}

	@Override
	public boolean isVoidOrElementTypeIsVoid() {
		return false;
	}

	@Override
	public boolean isKindOfNumber(IType.VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfInteger() {
		return false;
	}

	@Override
	public boolean isKindOfInteger(IType.VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfUnlimitedNatural() {
		return false;
	}

	@Override
	public boolean isKindOfUnlimitedNatural(IType.VoidHandling h) {
		return false;
	}

	@Override
	public boolean isKindOfReal(IType.VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfReal() {
		return false;
	}

	@Override
	public boolean isKindOfString(IType.VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfString() {
		return false;
	}

	@Override
	public boolean isKindOfBoolean(IType.VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfBoolean() {
		return false;
	}

	@Override
	public boolean isKindOfEnum(IType.VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfEnum() {
		return false;
	}

	@Override
	public boolean isKindOfCollection(IType.VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfCollection() {
		return false;
	}

	@Override
	public boolean isKindOfSet(IType.VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfSet() {
		return false;
	}

	@Override
	public boolean isKindOfSequence(IType.VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfSequence() {
		return false;
	}

	@Override
	public boolean isKindOfOrderedSet(IType.VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfOrderedSet() {
		return false;
	}

	@Override
	public boolean isKindOfBag(IType.VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfBag() {
		return false;
	}

	@Override
	public boolean isKindOfClassifier(IType.VoidHandling h) {
		return true;
	}

	@Override
	public boolean isTypeOfClassifier() {
		return true;
	}

	@Override
	public boolean isKindOfClass(IType.VoidHandling h) {
		return false;
	}

	@Override
	public boolean isKindOfDataType(IType.VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfDataType() {
		return false;
	}

	@Override
	public boolean isTypeOfClass() {
		return false;
	}
	
	@Override
	public boolean isKindOfAssociation(IType.VoidHandling h) {
		return false;
	}

	@Override
	public boolean isTypeOfAssociation() {
		return false;
	}

	@Override
	public boolean isKindOfOclAny(IType.VoidHandling h) {
		return true;
	}

	@Override
	public boolean isTypeOfOclAny() {
		return false;
	}

	@Override
	public boolean isKindOfTupleType(IType.VoidHandling h) {
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

	@Override
	public String qualifiedName() {
			return model != null ? model.name() + "#" + name() : null;
	}

	/**
	 * Indicates if classifier is imported using the model qualified syntax (modelName#classifierName).
	 * Can be used to determine if qualifiedName should be displayed instead of regular name.
	 */
	@Override
	public boolean isQualifiedAccess() {
		return isQualifiedAccess;
	}

	@Override
	public void setQualifiedAccess(boolean qualifiedAccess) {
		isQualifiedAccess = qualifiedAccess;
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
     * Returns the set of all parent classifiers (without this
     * classifier). This is the transitive closure of the generalization
     * relation.
     *
     * @return Set(MClass)
     */
	@Override
	public Set<? extends MClassifier> allParents() {
    	return model.generalizationGraph().targetNodeClosureSet(this);
    }

    /**
     * Returns the set of all child classifiers (without this classifier). This
     * is the transitive closure of the generalization relation.
     *
     * @return Set(MClass)
     */
	@Override
	public Set<? extends MClassifier> allChildren() {
    	return model.generalizationGraph().sourceNodeClosureSet(this);
    }

    /**
     * Returns the set of all direct child classifiers (without this
     * classifier).
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
    public boolean isSubClassifierOf(MClassifier otherClass) {
        return isSubClassifierOf(otherClass, false);
    }

    @Override
    public boolean isSubClassifierOf(MClassifier otherClassifier, boolean excludeThis) {
        return Iterators.contains(this.generalizationHierachie(!excludeThis).iterator(), otherClassifier);
    }

	/**
	 * Returns the specified attribute. Attributes are also looked up
	 * in super classifiers if <code>searchInherited</code> is true.
	 *
	 * @return null if attribute does not exist.
	 */
	public MAttribute attribute(String name, boolean searchInherited) {
		MAttribute a = fAttributes.get(name);
		if (a == null && searchInherited) {
			for (MClassifier cf : allParents()) {
				a = cf.attribute(name, false);
				if (a != null)
					break;
			}
		}
		return a;
	}

	@Override
	public List<MAttribute> attributes() {
		return new ArrayList<MAttribute>(fAttributes.values());
	}

	/**
	 * Returns the set of all attributes (including inherited ones)
	 * defined for this classifier.
	 *
	 * @return List(MAttribute)
	 */
	public List<MAttribute> allAttributes() {
		// start with local attributes
		List<MAttribute> result = new ArrayList<>(attributes());

		// add attributes from all super classes
		for (MClassifier cf : allParents() ) {
			result.addAll(cf.attributes());
		}

		return result;
	}

	/**
	 * Gets an operation by name. Operations are also looked up in
	 * super classifiers if <code>searchInherited</code> is true. This
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

			for (MClassifier cf : parents()) {
				op = cf.operation(name, false);
				if (op != null) {
					fVTableOperations.put(name, op);
					return op;
				}
			}
			// FIXME: The compiler has to check a unique binding in case of multiple inheritance
			for (MClassifier cf : parents()) {
				op = cf.operation(name, true);
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
	 * Returns all operations defined for this classifier. Inherited
	 * operations are not included.
	 */
	public List<MOperation> operations() {
		return new ArrayList<MOperation>(fOperations.values());
	}

	@Override
	public MNavigableElement navigableEnd(String rolename) {
		return null;
	}

	@Override
	public Map<String, ? extends MNavigableElement> navigableEnds() {
		return Collections.emptyMap();
	}

	@Override
	public boolean hasStateMachineWhichHandles(MOperationCall operationCall) {
		return false;
	}
}

