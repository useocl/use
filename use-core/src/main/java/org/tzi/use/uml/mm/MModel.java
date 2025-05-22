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

package org.tzi.use.uml.mm;

import com.google.common.collect.Maps;
import org.eclipse.jdt.annotation.Nullable;
import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.graph.DirectedGraphBase;
import org.tzi.use.uml.mm.commonbehavior.communications.MSignal;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.collections.CollectionUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A Model is a top-level package containing all other model elements.
 * 
 * @author Mark Richters
 * @author Lars Hamann
 */
public class MModel extends MModelElementImpl {

	/**
	 * This map keeps track of the numbering of "unnamed" named model elements,
	 * i.e., if an invariant was defined without a name, a name like
	 * <code>inv::1</code> is generated.
	 */
	private final Map<String, MutableInteger> fNameMap = new HashMap<>();

	/**
	 * We don't want to allocate a new Integer object each time we have to
	 * increment the value in a map.
	 */
	static class MutableInteger {
		int fInt = 1;
	}

	private final Map<String, EnumType> fEnumTypes;

	private final Map<String, MDataType> fDataTypes;

	private final Map<String, MClass> fClasses;

	protected Map<String, MAssociation> fAssociations;

	private final DirectedGraph<MClassifier, MGeneralization> fGenGraph;

	protected Map<String, MClassInvariant> fClassInvariants;

	private final Map<String, MPrePostCondition> fPrePostConditions;

	private String fFilename; // name of .use file

	private final Map<String, MSignal> signals;

	private final Map<String, MImportedModel> importedModels;

	protected MModel(String name) {
		super(name);
		fEnumTypes = new TreeMap<>();
		fDataTypes = new TreeMap<>();
		fClasses = new TreeMap<>();
		fAssociations = new TreeMap<>();
		fGenGraph = new DirectedGraphBase<>();
		fClassInvariants = new TreeMap<>();
		fPrePostConditions = new TreeMap<>();
		signals = new TreeMap<>();
		importedModels = new TreeMap<>();

		fFilename = "";
	}

	public void setFilename(String filename) {
		fFilename = filename;
	}

	/**
	 * Returns the filename of the specification from which this model was read.
	 * May be empty if model is not constructed from a file.
	 */
	public String filename() {
		return fFilename;
	}

	/**
	 * Adds a class. The class must have a unique name within the model.
	 * 
	 * @exception MInvalidModelException
	 *                model already contains a class with the same name.
	 */
	public void addClass(MClass cls) throws MInvalidModelException {
		if (fClasses.containsKey(cls.name()))
			throw new MInvalidModelException("Model already contains a class `"
					+ cls.name() + "'.");

		if (!(cls instanceof MAssociationClass)
				&& fAssociations.containsKey(cls.name()))
			throw new MInvalidModelException(
					"Model already contains an association `" + cls.name()
							+ "'.");

		fClasses.put(cls.name(), cls);
		fGenGraph.add(cls);
		cls.setModel(this);
	}

	/**
	 * Returns the specified class.
	 * 
	 * @return <code>null</code> if class <code>name</code> does not exist.
	 */
	public MClass getClass(String name) {
		MClass cls = fClasses.get(name);
		if (cls != null) {
			return cls;
		}

		// Check direct imports
		for (MImportedModel imp : importedModels.values()) {
			cls = imp.getClass(name);
			if (cls != null) {
				return cls;
			}
		}

		// Check transitive imports only via qualified name resolution
		if (name.contains("::")) {
			for (MImportedModel imp : importedModels.values()) {
				cls = imp.resolveQualifiedClass(name);
				if (cls != null) {
					return cls;
				}
			}
		}
		return null;
	}

	/**
	 * Adds a data type. The data type must have a unique name within the model.
	 *
	 * @exception MInvalidModelException
	 *                model already contains a data type with the same name.
	 */
	public void addDataType(MDataType dtp) throws MInvalidModelException {
		if (fDataTypes.containsKey(dtp.name()))
			throw new MInvalidModelException("Model already contains a data type `"
					+ dtp.name() + "'.");

		fDataTypes.put(dtp.name(), dtp);
		fGenGraph.add(dtp);
		dtp.setModel(this);
	}

	/**
	 * Returns the specified data type.
	 * 
	 * @return <code>null</code> if data type <code>name</code> does not exist.
	 */
	public MDataType getDataType(String name) {
		MDataType dataType = fDataTypes.get(name);
		if (dataType != null) {
			return dataType;
		}

		// Check direct imports
		for (MImportedModel imp : importedModels.values()) {
			dataType = imp.getDataType(name);
			if (dataType != null) {
				return dataType;
			}
		}

		// Check transitive imports only via qualified name resolution
		if (name.contains("::")) {
			for (MImportedModel imp : importedModels.values()) {
				dataType = imp.resolveQualifiedDatatype(name);
				if (dataType != null) {
					return dataType;
				}
			}
		}
		return null;
	}

	/**
	 * Returns the classifier (currently MClass, MDataType, MAssociation) with the given name or <code>null</code>,
	 * if no classifier with the given name exists in the model.
	 */
	public MClassifier getClassifier(String name) {
		MClassifier classifier = getClass(name);
		if (classifier != null) {
			return classifier;
		}

		classifier = getDataType(name);
		if (classifier != null) {
			return classifier;
		}

		classifier = getAssociation(name);
		return classifier;
	}

	/**
	 * Returns the specified association class.
	 * 
	 * @return null if class <code>name</code> does not exist.
	 */
	public MAssociationClass getAssociationClass(String name) {
		MClass cls = fClasses.get(name);
		if (cls instanceof MAssociationClass) {
			return (MAssociationClass) cls;
		}

		for (MImportedModel imp : importedModels.values()) {
			MAssociationClass assocCls = imp.getAssociationClass(name);
			if (assocCls != null) {
				return assocCls;
			}
		}

		return null;
	}

	/**
	 * Returns a collection containing all data types in this model.
	 *
	 * @return collection of MDataType objects.
	 */
	public Collection<MDataType> dataTypes() {
		return fDataTypes.values();
	}

	/**
	 * Returns a collection containing all data types in this model including the data types it imports.
	 *
	 * @return collection of MDataType objects.
	 */
	public Collection<MDataType> getDataTypesIncludingImports() {
		Set<MDataType> datatypes = new HashSet<>(fDataTypes.values());
		for (MImportedModel importedModel : importedModels.values()) {
			datatypes.addAll(importedModel.getDataTypes());
		}
		return datatypes;
	}

	/**
	 * Returns a collection containing all classes in this model.
	 * 
	 * @return collection of MClass objects.
	 */
	public Collection<MClass> classes() {
		return fClasses.values();
	}

	/**
	 * Returns a collection containing all classes in this model including the classes it imports.
	 *
	 * @return collection of MClass objects.
	 */
	public Collection<MClass> getClassesIncludingImports() {
		Set<MClass> classes = new HashSet<>(fClasses.values());
		for (MImportedModel importedModel : importedModels.values()) {
			classes.addAll(importedModel.getClasses());
		}
		return classes;
	}

	public Collection<MClassifier> classifiers() {
		Set<MClassifier> col = new HashSet<>(dataTypes());
		col.addAll(classes());
		return col;
	}

	public Collection<MClassifier> getClassifiersIncludingImports() {
		return Stream.concat(getDataTypesIncludingImports().stream(), getClassesIncludingImports().stream())
				.collect(Collectors.toSet());
	}

	/**
	 * Adds an association. The association must have a unique name within the
	 * model.
	 * 
	 * @exception MInvalidModelException
	 *                model already contains an association with the same name.
	 */
	public void addAssociation(MAssociation assoc)
			throws MInvalidModelException {
		if (assoc.associationEnds().size() < 2) {
			if (!(assoc instanceof MAssociationClass)
					|| ((MAssociationClass) assoc).parents().isEmpty())
				throw new IllegalArgumentException("Illformed association `"
						+ assoc.name() + "': number of associationEnds == "
						+ assoc.associationEnds().size());
		}

		if (fAssociations.containsKey(assoc.name()))
			throw new MInvalidModelException(
					"Model already contains an association named `"
							+ assoc.name() + "'.");

		if (!(assoc instanceof MAssociationClass)
				&& fClasses.containsKey(assoc.name()))
			throw new MInvalidModelException("Model already contains a class `"
					+ assoc.name() + "'.");

		// check for role name conflicts: for each class the set of
		// navigable classes must have unique role names
		for (MClass cls : assoc.associatedClasses()) {
			Map<String, ? extends MNavigableElement> aends = cls
					.navigableEnds();
			List<String> newRolenames = new ArrayList<>();

			for (MNavigableElement elem : assoc.navigableEndsFrom(cls)) {
				String newRolename = elem.nameAsRolename();

				newRolenames.add(newRolename);

				if (aends.containsKey(newRolename)) {
					// Inherited?
					boolean inherited = false;
					MNavigableElement otherEnd = aends.get(newRolename);

					if (otherEnd.association() instanceof MAssociationClass
							&& assoc instanceof MAssociationClass) {
						MAssociationClass otherCls = (MAssociationClass) otherEnd
								.association();
						MAssociationClass ourCls = (MAssociationClass) assoc;

						if (ourCls.allParents().contains(otherCls)) {
							inherited = true;
						}
					}

					if (!inherited) {
						throw new MInvalidModelException(
								"Association end `"
										+ newRolename
										+ "' navigable from class `"
										+ cls.name()
										+ "' conflicts with same rolename in association `"
										+ aends
												.get(newRolename)
												.association().name() + "'.");
					}
				}
			}

			// tests if the rolenames are already used in one of the subclasses
			for (MClass subCls : CollectionUtil
					.<MClassifier, MClass> downCastUnsafe(cls.allChildren())) {
                for (String newRolename : newRolenames) {
                    if (subCls.navigableEnds().containsKey(newRolename)) {
                        throw new MInvalidModelException(
                                "Association end `"
                                        + newRolename
                                        + "' navigable from class `"
                                        + subCls.name()
                                        + "' conflicts with same rolename in association `"
                                        + subCls
                                        .navigableEnds().get(
                                                newRolename)
                                        .association().name() + "'.");
                    }
                }
			}
		}

		// for each class register the association and the
		// reachable association ends
		for (MAssociationEnd aend : assoc.associationEnds()) {
			MClass cls = aend.cls();
			cls.registerNavigableEnds(assoc.navigableEndsFrom(cls));
		}

		assoc.setModel(this);
		fGenGraph.add(assoc);

		fAssociations.put(assoc.name(), assoc);
	}

	/**
	 * Returns a collection containing all associations in this model.
	 * 
	 * @return collection of MAssociation objects.
	 */
	public Collection<MAssociation> associations() {
		return fAssociations.values();
	}

	public Collection<MAssociation> getAssociationsIncludingImports() {
		Set<MAssociation> associations = new HashSet<>(fAssociations.values());
		for (MImportedModel importedModel : importedModels.values()) {
			associations.addAll(importedModel.getAssociations());
		}
		return associations;
	}

	/**
	 * Returns the specified association.
	 * 
	 * @return null if association does not exist.
	 */
	@Nullable
	public MAssociation getAssociation(String name) {
		MAssociation association = fAssociations.get(name);

		if (association == null) {
			// Check direct imports
			for (MImportedModel imp : importedModels.values()) {
				association = imp.getAssociation(name);
			}
		}
		return association;
	}

	/**
	 * Returns the set of all associations that exist between the specified
	 * classes (inherited ones are not included). The arity of the returned
	 * associations is equal to <code>classes.size()</code>.
	 * 
	 * @return Set(MAssociation)
	 */
	public Set<MAssociation> getAssociationsBetweenClasses(Set<MClass> classes) {
		Set<MAssociation> res = new HashSet<>();

		// search associations
        for (MAssociation assoc : fAssociations.values()) {
            if (assoc.associatedClasses().equals(classes))
                res.add(assoc);
        }

		return res;
	}

	/**
	 * Returns the set of all associations that exist between the specified
	 * classes (including inherited ones). The arity of the returned
	 * associations is equal to <code>classes.size()</code>.
	 * 
	 * @return Set(MAssociation)
	 */
	public Set<MAssociation> getAllAssociationsBetweenClasses(
			Set<MClass> classes) {
		// NOT IMPLEMENTED YET
		return getAssociationsBetweenClasses(classes);
	}

	/**
	 * Adds a generalization from <code>child</code> to <code>parent</code>
	 * class.
	 * 
	 * @exception MInvalidModelException
	 *                generalization is not well-formed, e.g., a cycle is
	 *                introduced into the generalization hierarchy.
	 */
	public void addGeneralization(MGeneralization gen)
			throws MInvalidModelException {
		// generalization is irreflexive
		MClassifier child = gen.child();
		if (gen.isReflexive()) {
			throw new MInvalidModelException("Class `" + child
					+ "' cannot be a superclass of itself.");
		}

		// check for cycles that might be introduced by adding the new
		// generalization
		MClassifier parent = gen.parent();
		if (fGenGraph.existsPath(parent, child)) {
			throw new MInvalidModelException(
					"Detected cycle in generalization hierarchy. Class `"
							+ parent.name() + "' is already a subclass of `"
							+ child.name() + "'.");
		}

		if (!parent.getClass().isAssignableFrom(child.getClass())) {
			throw new MInvalidModelException(
					"Invalid inheritance relation between meta elements "
							+ StringUtil.inQuotes(child.getClass()
									.getSimpleName() + "::" + child.name())
							+ " < "
							+ StringUtil.inQuotes(parent.getClass()
									.getSimpleName() + "::" + parent.name()));
		}

		final boolean childIsAssocClass = child instanceof MAssociationClass;
		final boolean parentIsAssocClass = parent instanceof MAssociationClass;

		/*
		 * If one element is an association class, both elements must be
		 * association classes. (childIsAssocClass || parentIsAssocClass)
		 * implies (childIsAssocClass && parentIsAssocClass) This is negated to
		 * raise an error. !((childIsAssocClass || parentIsAssocClass) implies
		 * (childIsAssocClass && parentIsAssocClass)) Extracted this expression
		 * leads to:
		 */
		if ((childIsAssocClass || parentIsAssocClass)
				&& (!childIsAssocClass || !parentIsAssocClass)) {
			throw new MInvalidModelException(
					"Association classes can only inherit from association classes.");
		}

		/*
		 * FIXME: check for any conflicts that might be introduced by the
		 * generalization: (1) attributes with same name, (2) inherited
		 * associations?? For usage as a library, one cannot guarantee that the
		 * inheritance Relations are set-up before attribute definition.
		 */
		// child.validateInheritance();

		// silently ignore duplicates
		fGenGraph.addEdge(gen);
	}

	/**
	 * Returns the generalization graph of this model.
	 * 
	 * @return a DirectedGraph with MClass nodes and MGeneralization edges
	 */
	public DirectedGraph<MClassifier, MGeneralization> generalizationGraph() {
		return fGenGraph;
	}

	/**
	 * Adds an enumeration type.
	 * 
	 * @exception MInvalidModelException
	 *                model already contains an element with same name.
	 */
	public void addEnumType(EnumType e) throws MInvalidModelException {
		if (fEnumTypes.containsKey(e.name()))
			throw new MInvalidModelException("Model already contains a type `"
					+ e.name() + "'.");
		fEnumTypes.put(e.name(), e);
	}

	/**
	 * Returns an enumeration type by name.
	 * 
	 * @return null if enumeration type does not exist.
	 */
	public EnumType enumType(String name) {
		EnumType enumType = fEnumTypes.get(name);
		if (enumType != null) {
			return enumType;
		}

		// Check direct imports
		for (MImportedModel importedModel: importedModels.values()) {
			enumType = importedModel.getEnumType(name);
			if (enumType != null) {
				return enumType;
			}
		}
		return null;
	}

	/**
	 * Returns an enumeration type for a given literal.
	 * 
	 * @return null if enumeration type does not exist.
	 */
	public EnumType enumTypeForLiteral(String literal) {

        for (EnumType t : fEnumTypes.values()) {
            if (t.contains(literal))
                return t;
        }

		return null;
	}

	/**
	 * Returns a set of all enumeration types.
	 */
	public Set<EnumType> enumTypes() {
        return new HashSet<>(fEnumTypes.values());
	}

	public Set<EnumType> getEnumTypesIncludingImports() {
		Set<EnumType> enumTypes = new HashSet<>(fEnumTypes.values());
		for (MImportedModel importedModel : importedModels.values()) {
			enumTypes.addAll(importedModel.getEnumTypes());
		}
		return enumTypes;
	}

	/**
	 * Adds a class invariant. The class + invariant name must have a unique
	 * name within the model.
	 * 
	 * @exception MInvalidModelException
	 *                model already contains an invariant with same name.
	 */
	public void addClassInvariant(MClassInvariant inv)
			throws MInvalidModelException {

		String name = inv.cls().name() + "::" + inv.name();

		if (fClassInvariants.containsKey(name))
			throw new MInvalidModelException(
					"Duplicate definition of invariant `" + inv.name()
							+ "' in class `" + inv.cls().name() + "'.");

		fClassInvariants.put(name, inv);
	}

	/**
	 * Returns a collection containing all class invariants (including loaded
	 * invariants).
	 * 
	 * @return collection of MClassInvariant objects.
	 */
	public Collection<MClassInvariant> classInvariants() {
		return fClassInvariants.values();
	}

	public Collection<MClassInvariant> getClassInvariantsIncludingImports() {
		Set<MClassInvariant> classInvariants = new HashSet<>(fClassInvariants.values());
		for (MImportedModel importedModel : importedModels.values()) {
			classInvariants.addAll(importedModel.getClassInvariants());
		}
		return classInvariants;
	}

	public Collection<MClassInvariant> classInvariants(boolean onlyActive) {
		if (onlyActive) {
			return Maps.filterValues(fClassInvariants,
                    MClassInvariant::isActive).values();
		} else {
			return fClassInvariants.values();
		}
	}

	/**
	 * @return collection of class invariants from the use file loaded
	 */
	public Collection<MClassInvariant> modelClassInvariants() {
		return Maps.filterValues(fClassInvariants,
                inv -> !inv.isLoaded()).values();
	}

	/**
	 * Returns a collection containing all invariants for a given class.
	 * 
	 * @return collection of MClassInvariant objects.
	 */
	public Set<MClassInvariant> classInvariants(MClass cls) {
		Set<MClassInvariant> res = new HashSet<>();
		for (MClassInvariant inv : fClassInvariants.values()) {
				if (inv.cls().equals(cls))
						res.add(inv);
		}

		return res;
	}

	/**
	 * Returns a collection containing all invariants for a given class and its
	 * parents.
	 * 
	 * @return collection of MClassInvariant objects.
	 */
	public Set<MClassInvariant> allClassInvariants(MClass cls) {
		Set<MClassInvariant> res = new HashSet<>();
		Set<MClass> allP = CollectionUtil.downCastUnsafe(cls.allParents());
		Set<MClass> parents = new HashSet<>(allP);
		parents.add(cls);

		for (MClassInvariant inv : fClassInvariants.values()) {
				if (parents.contains(inv.cls()))
						res.add(inv);
		}

		// Check direct imports
		for (MImportedModel importedModel: importedModels.values()) {
			res.addAll(importedModel.getAllClassInvariants(parents));
		}
		return res;
	}

	/**
	 * Returns the specified invariant. The name must be given as "class::inv".
	 * 
	 * @return <code>null</code> if invariant <code>name</code> does not exist.
	 */
	public MClassInvariant getClassInvariant(String name) {
		MClassInvariant inv = fClassInvariants.get(name);
		if (inv != null) {
			return inv;
		}

		// Check direct imports
		for (MImportedModel importedModel: importedModels.values()) {
			inv = importedModel.getClassInvariant(name);
			if (inv != null) {
				return inv;
			}
		}
		return null;
	}

	/**
	 * Returns all loaded invariants.
	 */
	public Collection<MClassInvariant> getLoadedClassInvariants() {
		return Maps.filterValues(fClassInvariants,
                MClassInvariant::isLoaded).values();
	}

	public MClassInvariant removeClassInvariant(String name) {
		MClassInvariant inv = fClassInvariants.get(name);

		if (inv != null && inv.isLoaded()) {
			fClassInvariants.remove(name);
			return inv;
		}

		return null;
	}

	/**
	 * Adds a pre-/postcondition.
	 */
	public void addPrePostCondition(MPrePostCondition ppc)
			throws MInvalidModelException {

		String name = ppc.cls().name() + "::" + ppc.operation().name()
				+ ppc.name();
		if (fPrePostConditions.containsKey(name))
			throw new MInvalidModelException(
					"Duplicate definition of pre-/postcondition `" + ppc.name()
							+ "' in class `" + ppc.cls().name() + "'.");
		fPrePostConditions.put(name, ppc);
		if (ppc.isPre())
			ppc.operation().addPreCondition(ppc);
		else {
			if (ppc.operation().isConstructor()) {
				throw new MInvalidModelException(
						"Declaring a postcondition for constructor `" + ppc.operation()
								+ "' is not allowed.");
			}
			ppc.operation().addPostCondition(ppc);
		}
	}

	/**
	 * Returns a collection containing all pre-/postconditions.
	 * 
	 * @return collection of MPrePostCondition objects.
	 */
	public Collection<MPrePostCondition> prePostConditions() {
		return fPrePostConditions.values();
	}

	/**
	 * Returns a collection containing all preconditions.
	 * 
	 * @return collection of MPrePostCondition objects.
	 */
	public Collection<MPrePostCondition> preConditions() {
		Collection<MPrePostCondition> conditions = fPrePostConditions.values();
		Collection<MPrePostCondition> preConditions = new ArrayList<>();
		
		for(MPrePostCondition ppc : conditions) {
			if(ppc.isPre()) {
				preConditions.add(ppc);
			}
		}

		return preConditions;
	}
	
	/**
	 * Returns a collection containing all postconditions.
	 * 
	 * @return collection of MPrePostCondition objects.
	 */
	public Collection<MPrePostCondition> postConditions() {
		Collection<MPrePostCondition> conditions = fPrePostConditions.values();
		Collection<MPrePostCondition> postConditions = new ArrayList<>();
		
		for(MPrePostCondition ppc : conditions) {
			if(!ppc.isPre()) {
				postConditions.add(ppc);
			}
		}

		return postConditions;
	}

	/**
	 * Adds the <code>signal</code> to the model.
	 * 
	 * @param signal
	 *            The signal to add.
	 * @throws MInvalidModelException
	 *             If a classifier with the same name already exists.
	 */
	public void addSignal(MSignal signal) throws MInvalidModelException {
		if (this.signals.containsKey(signal.name())
				|| this.fAssociations.containsKey(signal.name())
				|| this.fClasses.containsKey(signal.name())
				|| this.fEnumTypes.containsKey(signal.name())) {
			throw new MInvalidModelException(
					"Model already contains a classifier named "
							+ StringUtil.inQuotes(signal.name()));
		}

		this.signals.put(signal.name(), signal);
		this.fGenGraph.add(signal);

		signal.setModel(this);
	}

	/**
	 * Returns a copied set of all defined signals.
	 * 
	 * @return
	 */
	public Set<MSignal> getSignals() {
		return new HashSet<>(this.signals.values());
	}

	public Set<MSignal> getSignalsIncludingImports() {
		HashSet<MSignal> signals = new HashSet<>(this.signals.values());
		for (MImportedModel importedModel : importedModels.values()) {
			signals.addAll(importedModel.getSignals());
		}
		return signals;
	}

	/**
	 * Returns the signal with the given <code>name</code> or <code>null</code>,
	 * if no such signal exists.
	 * 
	 * @param name
	 *            The name of the signal to lookup.
	 * @return The signal with the given name or <code>null</code>.
	 */
	public MSignal getSignal(String name) {
		MSignal signal = this.signals.get(name);
		if (signal != null) {
			return signal;
		}

		// Check direct imports
		for (MImportedModel importedModel : importedModels.values()) {
			signal = importedModel.getSignal(name);
			if (signal != null) {
				return signal;
			}
		}

		return null;
	}

	/**
	 * Adds the <code>imported model</code> to the model.
	 *
	 * @param model
	 *            The model to add.
	 * @throws MInvalidModelException
	 *             If a model with the same name is already imported.
	 */
	public void addImportedModel(MImportedModel model) throws MInvalidModelException {
		if (this.importedModels.containsKey(model.name())) {
			throw new MInvalidModelException("Model already contains an imported model names " +
					StringUtil.inQuotes(model.name()));
		}

		this.importedModels.put(model.name(), model);
	}

	/**
	 * Returns a copied set of all imported models.
	 *
	 * @return Set of imported model objects.
	 */
	public Set<MImportedModel> getImportedModels() {
		return new HashSet<>(this.importedModels.values());
	}

	/**
	 * Returns the imported model with the given <code>name</code> or <code>null</code>,
	 * if no such model exists.
	 *
	 * @param name
	 *            The name of the imported model to lookup.
	 * @return The model with the given name or <code>null</code>.
	 */
	public MImportedModel getImportedModel(String name) {
		return this.importedModels.get(name);
	}

	/**
	 * Returns a string with some statistics about the model: Number of classes,
	 * associations, data types, invariants, and operations.
	 */
	public String getStats() {
		String stats = " (";
		int n = dataTypes().size();
		stats += n + " data type";
		if (n != 1)
			stats += "s";
		n = classes().size();
		stats += ", " + n + " class";
		if (n != 1)
			stats += "es";
		n = associations().size();
		stats += ", " + n + " association";
		if (n != 1)
			stats += "s";
		n = classInvariants().size();
		stats += ", " + n + " invariant";
		if (n != 1)
			stats += "s";

		n = 0;
		int nPSMs = 0;

        for (MClass cls : classes()) {
            n += cls.operations().size();
            nPSMs += cls.getOwnedProtocolStateMachines().size();
        }

		stats += ", " + n + " operation";
		if (n != 1)
			stats += "s";

		n = fPrePostConditions.size();
		stats += ", " + n + " pre-/postcondition";
		if (n != 1) {
			stats += "s";
		}

		stats += ", " + nPSMs + " state machine";
		if (nPSMs != 1) {
			stats += "s";
		}

		return "Model " + name() + stats + ")";
	}

	/**
	 * Process this element with visitor.
	 */
	public void processWithVisitor(MMVisitor v) {
		v.visitModel(this);
	}

	/**
	 * Sets the name of model element to a generated one, if the element has no
	 * name. If the name is <code>null</code> or empty a new name starting with
	 * <code>prefix</code> will be generated. Note that the generated names will
	 * be unique, but they may still clash with some user defined name.
	 */
	public String createModelElementName(String prefix) {

		MutableInteger i = fNameMap.get(prefix);
		if (i == null) {
			i = new MutableInteger();
			fNameMap.put(prefix, i);
		} else {
			i.fInt++;
		}

		return prefix + i.fInt;
	}
}
