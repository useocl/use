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

package org.tzi.use.uml.sys;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.jdt.annotation.NonNull;
import org.tzi.use.config.Options;
import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.graph.DirectedGraphBase;
import org.tzi.use.uml.mm.MAggregationKind;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.ExpStdOp;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.expr.SimpleEvalContext;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MSystemState.DeleteObjectResult.ObjectStateModification;
import org.tzi.use.uml.sys.statemachines.MProtocolStateMachineInstance;
import org.tzi.use.util.Log;
import org.tzi.use.util.NullPrintWriter;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.StringUtil.IElementFormatter;
import org.tzi.use.util.collections.Bag;
import org.tzi.use.util.collections.CollectionUtil;
import org.tzi.use.util.collections.HashBag;
import org.tzi.use.util.collections.Queue;
import org.tzi.use.util.soil.StateDifference;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * A system state represents a valid instance of a model. It contains a set of
 * objects and links connecting objects. Methods allow manipulation and querying
 * of objects and links.
 * 
 * @author Mark Richters
 * @author Lars Hamann
 */

public final class MSystemState {

	/**
	 * The name of the system state
	 */
	private String fName;
	
	/**
	 * The system related to this state
	 */
	private MSystem fSystem;

	/**
	 * The set of all object states.
	 */
	private Map<MObject, MObjectState> fObjectStates;
	
	/**
	 * The set of objects partitioned by class. Must be kept in sync with
	 * fObjectStates.
	 */
	private Multimap<MClass, MObject> fClassObjects;

	/**
	 * Mapping of object names to objects to get
	 * objects by name faster 
	 */
	private Map<String, MObject> fObjectNames;
	
	/**
	 * The set of all links partitioned by association.
	 */
	private Map<MAssociation, MLinkSet> fLinkSets;

	/**
	 * Handles virtual links and attribute values
	 */
	private DerivedValueController[] derivedValuesController;
	
	private static final int DVC_DERIVED_ASSOCS = 0;
	private static final int DVC_UNION_ASSOCS = 1;
	private static final int DVC_DERIVED_ATTR = 2;
	/**
	 * Creates a new system state with no objects and links.
	 */
	MSystemState(String name, MSystem system) {
		fName = name;
		fSystem = system;
		fObjectStates = new HashMap<MObject, MObjectState>();
		fClassObjects = HashMultimap.create();
		fObjectNames = new HashMap<String, MObject>();
		fLinkSets = new HashMap<MAssociation, MLinkSet>();
		
		// create empty link sets
		for(MAssociation assoc : fSystem.model().associations()) {
			MLinkSet linkSet = new MLinkSet(assoc);
			fLinkSets.put(assoc, linkSet);
		}
		
		synchronized (dirtyLock) {
			derivedValuesController = new DerivedValueController[3];
			derivedValuesController[DVC_DERIVED_ASSOCS] = new DerivedLinkControllerDerivedEnd(this, fLinkSets);
			derivedValuesController[DVC_UNION_ASSOCS] = new DerivedLinkControllerUnion(this, fLinkSets);
			derivedValuesController[DVC_DERIVED_ATTR] = new DerivedAttributeController(this, fObjectStates);
	
			for (int i = 0; i < derivedValuesController.length; ++i) {
				derivedValuesController[i].initState();
			}
		}
	}

	/**
	 * Creates a copy of an existing system state.
	 */
	public MSystemState(String name, MSystemState x) {
		fName = name;
		fSystem = x.fSystem;

		// deep copy of object states
		fObjectStates = new HashMap<MObject, MObjectState>();
		fObjectNames = new HashMap<String, MObject>();
		
		for (Map.Entry<MObject, MObjectState> e : x.fObjectStates.entrySet()) {
			fObjectStates.put(e.getKey(), new MObjectState(e.getValue()));
			fObjectNames.put(e.getKey().name(), e.getKey());
		}

		fClassObjects = HashMultimap.create();
		fClassObjects.putAll(x.fClassObjects);

		fLinkSets = new HashMap<MAssociation, MLinkSet>();
		for(Map.Entry<MAssociation, MLinkSet> e : x.fLinkSets.entrySet()) {
			fLinkSets.put(e.getKey(), new MLinkSet(e.getValue()));
		}
		
		synchronized (dirtyLock) {
			derivedValuesController = new DerivedValueController[3];
			derivedValuesController[0] = new DerivedLinkControllerDerivedEnd(this, x.fLinkSets, (DerivedLinkController)x.derivedValuesController[0]);
			derivedValuesController[1] = new DerivedLinkControllerUnion(this, x.fLinkSets, (DerivedLinkController)x.derivedValuesController[1]);
			derivedValuesController[2] = new DerivedAttributeController(this, x.fObjectStates, (DerivedAttributeController)x.derivedValuesController[2]);
		}
	}

	
	private Object  dirtyLock = new Object();
	private boolean derivedIsDirty = true;
	
	/**
	 * Invokes updates on the controller for derived
	 * values, e. g., derived links or attributes.
	 */
	public void updateDerivedValues(boolean forceUpdate) {
		synchronized (dirtyLock) {
			if (!this.derivedIsDirty) return;
			
			if(forceUpdate || fSystem.isImmediatlyCalculateDerivedValues()) {
				for (int i = 0; i < derivedValuesController.length; ++i) { 
					derivedValuesController[i].updateState();
				}
				this.derivedIsDirty = false;
			}
		}
	}
	
	/**
	 * Invokes updates on the controller for derived
	 * values, e. g., derived links or attributes.
	 */
	public void updateDerivedValues() {
		updateDerivedValues(false);
	}
	
	/**
	 * Invokes updates on the controller for derived values, e. g., derived
	 * links or attributes and saves the changeset in {@code diff}.
	 * 
	 * @param diff
	 */
	public void updateDerivedValues(StateDifference diff) {
		synchronized (dirtyLock) {
			this.derivedIsDirty = true;
			
			if(fSystem.isImmediatlyCalculateDerivedValues()){
				for (int i = 0; i < derivedValuesController.length; ++i) {
					derivedValuesController[i].updateState(diff);
				}
				this.derivedIsDirty = false;
			}
		}
	}
	
	/**
	 * Returns the name of this state. The name is unique for different states.
	 */
	public String name() {
		return fName;
	}

	/**
	 * Returns the owning system of this state.
	 */
	public MSystem system() {
		return fSystem;
	}

	/**
	 * Returns the set of all objects in this state.
	 * 
	 * @return Set(MObject)
	 */
	public Set<MObject> allObjects() {
		return fObjectStates.keySet();
	}
	
	
	/**
	 * returns the names of all objects in this state
	 * 
	 * @return the set of names (empty set if there are no objects)
	 */
	public Set<String> allObjectNames() {
		return this.fObjectNames.keySet();
	}
	
	
	/**
	 * returns the number of objects in this state
	 * 
	 * @return the number of objects in this state
	 */
	public int numObjects() {
		return allObjects().size();
	}
	
	
	/**
	 * @return true if there are objects in this state, false else
	 */
	public boolean hasObjects() {
		return !allObjects().isEmpty();
	}
	
	
	/**
	 * returns true if there is an object with the supplied name
	 * 
	 * @param name the name to test
	 * @return true if there is an object with that name, false else
	 */
	public boolean hasObjectWithName(String name) {
		return this.fObjectNames.containsKey(name);
	}

	
	/**
	 * Returns the set of objects of class <code>cls</code> currently existing
	 * in this state.
	 * <p>To also get the objects of the sublcasses use {@link #objectsOfClassAndSubClasses(MClass)}.</p>
	 * @return Set(MObject)
	 */
	public Set<MObject> objectsOfClass(MClass cls) {
		return new HashSet<MObject>(fClassObjects.get(cls));
	}

	/**
	 * Returns the set of objects of class <code>cls</code> and all of its
	 * subclasses.
	 * 
	 * @return Set(MObject)
	 */
	public Set<MObject> objectsOfClassAndSubClasses(MClass cls) {
		Set<MObject> res = new HashSet<MObject>();
		Set<MClass> children = CollectionUtil.downCastUnsafe(cls.allChildren());
		
		res.addAll(fClassObjects.get(cls));
		
		for (MClass c : children) {
			res.addAll(fClassObjects.get(c));
		}
		
		return res;
	}

	/**
	 * Returns the object with the specified name.
	 * 
	 * @return null if no object with the specified name exists.
	 */
	public MObject objectByName(String name) {
		return fObjectNames.get(name);
	}

	/**
	 * Returns the set of all links in this state.
	 * 
	 * @return Set(MLink)
	 */
	public Set<MLink> allLinks() {
		Set<MLink> res = new HashSet<MLink>();

		for(MLinkSet ls : fLinkSets.values()) {
			res.addAll(ls.links());
		}
		
		return res;
	}

	/**
	 * Returns the set of links of the specified association in this state.
	 */
	public MLinkSet linksOfAssociation(MAssociation assoc) {
		if (assoc.isUnion() || assoc.isDerived()) {
			updateDerivedValues(true);
		}
		
		return fLinkSets.get(assoc);
	}

	/**
	 * Returns true if there is a link of the specified association connecting
	 * the given set of objects.
	 */
	public boolean hasLinkBetweenObjects(MAssociation assoc, MObject... objects) {
		MLinkSet linkSet = fLinkSets.get(assoc);
		return linkSet.hasLinkBetweenObjects(objects);
	}
	
	/**
	 * Returns true if there is a link of the specified association connecting
	 * the given set of objects with the provided qualifiers.
	 */
	public boolean hasLinkBetweenObjects(MAssociation assoc, List<MObject> objects, List<List<Value>> qualiferValues) {
		Set<MAssociation> toDo = new HashSet<MAssociation>();
		toDo.add(assoc);
		
		if (assoc instanceof MAssociationClass) {
			// Add parents and child
			toDo.addAll(assoc.allParents());
			toDo.addAll(assoc.allChildren());
		}
		
		for (MAssociation ass : toDo) {
			MLinkSet linkSet = fLinkSets.get(ass);
			if (linkSet.hasLinkBetweenObjects(objects, qualiferValues))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Returns all links between the given objects, ignoring possible
	 * qualifier values.
	 * @param assoc
	 * @param asList
	 * @return
	 */
	public Set<MLink> linkBetweenObjects(MAssociation assoc, List<MObject> objects) {
		MLinkSet linkSet = linksOfAssociation(assoc);
		return linkSet.linkBetweenObjects(objects);
	}
	
	/**
	 * Returns the link if there is a link connecting the given list of objects,
	 * otherwise null is returned.
	 */
	public MLink linkBetweenObjects(MAssociation assoc, List<MObject> objects, List<List<Value>> qualifierValues) {
		MLinkSet linkSet = linksOfAssociation(assoc);
		return linkSet.linkBetweenObjects(objects, qualifierValues);
	}

	/**
	 * Returns true if there is a link of the specified association connecting
	 * the objects in the given sequence.
	 * @throws MSystemException objects do not conform to the association ends.
	 */
	public boolean hasLink(MAssociation assoc, List<MObject> objects, List<List<Value>> qualifierValues)
			throws MSystemException {
		MLinkSet linkSet = linksOfAssociation(assoc);
		
		return linkSet.hasLink(objects, qualifierValues);
	}
	
	private static Pattern validObjectNamePattern = Pattern.compile("[$a-zA-Z_][a-zA-Z_0-9]*");
	
	/**
	 * Checks for a valid object name.
	 * Valid pattern: [$a-zA-Z_][a-zA-Z_0-9]*
	 * @param objectName
	 * @return
	 */
	private boolean isValidObjectName(String objectName) {
		return validObjectNamePattern.matcher(objectName).matches();
	}

	/**
	 * Creates and adds a new object to the state. The name of the object may be
	 * null in which case a unique name is automatically generated.
	 * 
	 * @return the created object.
	 */
	public MObject createObject(MClass cls, String name)
			throws MSystemException {
		
		if ((name != null) && !isValidObjectName(name)) {
			throw new MSystemException(
					StringUtil.inQuotes(name) + 
					" is not a valid object name");
		}
		
		// checks if cls is a association class, if yes then throw an exception,
		// because this should not be allowed.
		if (cls instanceof MAssociationClass) {
			throw new MSystemException(
					"Creation of a linkobject is not allowed with the command create. \n"
							+ "Use 'create ... between ...' or 'insert' instead.");
		}
		
		if (cls.isAbstract()) {
			throw new MSystemException(
					"Cannot create an object of an abtract class!");
		}
		
		// create new object and initial state
		MObject obj = fSystem.createObject(cls, name);
		
		MObjectState objState = new MObjectState(obj);
		
		fObjectStates.put(obj, objState);
		fClassObjects.put(cls, obj);
		fObjectNames.put(obj.name(), obj);
		
		objState.initialize(this);
		
		StringWriter err = new StringWriter();
		boolean valid = true;
		
		// Validate initial states of state machines
		for (MProtocolStateMachineInstance sm : objState.getProtocolStateMachinesInstances()) {
			valid &= sm.checkStateInvariant(this, new PrintWriter(err));
		}

		if (!valid) {
			fObjectStates.remove(obj);
			fClassObjects.remove(cls, obj);
			fObjectNames.remove(obj.name());
			fSystem.deleteObject(obj);
			
			throw new MSystemException("Object creation failed:\n" + err.toString());
		}
		
		return obj;
	}

   

	/**
	 * Restores a destroyed object.
	 */
	public void restoreObject(MObjectState objState) throws MSystemException {
		// create new object and initial state
		MObject obj = objState.object();
		fObjectStates.put(obj, objState);
		fClassObjects.put(obj.cls(), obj);
		fObjectNames.put(obj.name(), obj);
		
		fSystem.addObject(obj);
	}
	
	
	/**
	 * Returns a set of objects which are effected by the destruction of <code>object</code>.
	 * @param object The object to query effected objects for.
	 * @return A set of objects which are effected by the destruction of <code>object</code>.
	 */
	public Set<MObject> getObjectsAffectedByDestruction(MObject object) {
		Set<MObject> result = new HashSet<MObject>();
		
		getObjectsAffectedByDestruction(object, result);
		
		return result;
	}
	

	/**
	 * Recursively checks all association classes reachable from
	 * <code>object</code> for instances connected to <code>object</code>.
	 * These link objects are stored in <code>result</code>. 
	 * @param object The object to follow.
	 * @param result The overall result buffer.
	 */
	private void getObjectsAffectedByDestruction(MObject object, Set<MObject> result) {
		result.add(object);
		MClass objectClass = object.cls();
		
		for (MAssociation association : objectClass.allAssociations()) {
			if (association instanceof MAssociationClass) {			
				for (MLink link : fLinkSets.get(association).links()) {
					if ((link instanceof MLinkObject) && 
							link.linkedObjects().contains(object)) {
						
						MLinkObject linkObject = (MLinkObject)link;
						
						if (!result.contains(linkObject)) {
							getObjectsAffectedByDestruction(linkObject, result);
						}
					}
				}	
			}
		}
	}
	
	

	/**
	 * Deletes an object from the state. All links connected to the object are
	 * removed.
	 * 
	 * @return The <code>DeleteObjectResult</code> with information about the changes.
	 */
	public DeleteObjectResult deleteObject(MObject obj) {
		DeleteObjectResult result = auxDeleteObject(obj);

		// if obj is a link object it has to be deleted as a link too.
		if (obj instanceof MLinkObject) {
			MLinkObject linkObject = (MLinkObject) obj;
			auxDeleteLink(linkObject);
			result.getRemovedLinks().add(linkObject);
		}

		return result;
	}


	public static class DeleteObjectResult {
		public static class ObjectStateModification {

			private final MObjectState state;
			
			private final MAttribute attribute;
			
			private final MObject attributeValue;
			
			public ObjectStateModification(MObjectState state, MAttribute attr, MObject obj) {
				this.state = state;
				this.attribute = attr;
				this.attributeValue = obj;
			}
			
			/**
			 * The modified object state.
			 * @return The modified object state.
			 */
			public MObjectState getObjectState() {
				return state;
			}


			/**
			 * The object the attribute had as the value before.
			 * @return
			 */
			public MObject getObject() {
				return attributeValue;
			}


			/**
			 * The modified attribute.
			 * @return
			 */
			public MAttribute getAttribute() {
				return attribute;
			}
			
		}
		
		private Set<MLink> removedLinks = new HashSet<MLink>();
		private Set<MObject> removedObjects = new HashSet<MObject>();
		private Set<MObjectState> removedObjectStates = new HashSet<MObjectState>();
		private Set<ObjectStateModification> stateModifications = new HashSet<ObjectStateModification>();
		
		public Set<MLink> getRemovedLinks()
		{
			return removedLinks;
		}
		
		public Set<MObject> getRemovedObjects()
		{
			return removedObjects;
		}
		
		public Set<MObjectState> getRemovedObjectStates()
		{
			return removedObjectStates;
		}
		
		public void add(DeleteObjectResult other) {
			removedLinks.addAll(other.removedLinks);
			removedObjects.addAll(other.removedObjects);
			removedObjectStates.addAll(other.removedObjectStates);
			stateModifications.addAll(other.stateModifications);
		}

		/**
		 * @return
		 */
		public Set<ObjectStateModification> getModifiedStates() {
			return stateModifications;
		}
	}
	
	/**
	 * Deletes an object from the state. All links connected to the object are
	 * removed.
	 * 
	 * @return DeleteObjectResult with information about removed elements
	 */
	private DeleteObjectResult auxDeleteObject(MObject obj) {
		DeleteObjectResult res = new DeleteObjectResult();
		MClass objClass = obj.cls();

		// get associations this object might be participating in
		Set<MAssociation> assocSet = objClass.allAssociations();

		for (MAssociation assoc :assocSet) {
			MLinkSet linkSet = fLinkSets.get(assoc);

			// check all association ends the objects' class is
			// connected to
			for (MAssociationEnd aend : assoc.associationEnds()) {
				if (objClass.isSubClassOf(aend.cls())) {
					Set<MLink> removedLinks = linkSet.removeAll(aend, obj);
					
					for (MLink removed : removedLinks) {
						if (removed instanceof MLinkObject) {
							DeleteObjectResult resLinkObject = auxDeleteObject((MLinkObject) removed);
							
							res.add(resLinkObject);
						}
					}
					
					res.getRemovedLinks().addAll(removedLinks);
				}
			}
			
			linkSet.clearCache(obj);
		}
		
		// Find all classes which might have an attribute value containing the deleted object.
		Collection<MClass> allClasses = system().model().classes();
				
		for (MClass cls : allClasses) {
			for (MAttribute attr : cls.attributes()) {
				if (obj.cls().conformsTo(attr.type())) {
					// Check for all object values
					for (MObject relObject : this.objectsOfClassAndSubClasses(cls)) {
						MObjectState state = relObject.state(this); 
						if (state.attributeValue(attr).equals(obj.value())) {
							state.setAttributeValue(attr, UndefinedValue.instance);
							res.getModifiedStates().add(new ObjectStateModification(state, attr, obj));
						}
					}
				}
			}
		}
		
		res.getRemovedObjects().add(obj);
		res.getRemovedObjectStates().add(fObjectStates.get(obj));
		fObjectStates.remove(obj);
		fClassObjects.remove(objClass, obj);
		fObjectNames.remove(obj.name());
		fSystem.deleteObject(obj);
		return res;
	}

	private void auxDeleteLink(MLink link) {
		MLinkSet linkSet = fLinkSets.get(link.association());
		linkSet.remove(link);
		
		removeLinkFromWholePartGraph(link);
	}

	private void removeLinkFromWholePartGraph(MLink link) {
		if (   link.association().aggregationKind() == MAggregationKind.AGGREGATION 
			|| link.association().aggregationKind() == MAggregationKind.COMPOSITION ) {
			MWholePartLink wpLink = new MWholePartLinkImpl(link);
			fWholePartLinkGraph.removeEdge(wpLink);
			
			if (fWholePartLinkGraph.numIncomingEdges(wpLink.source()) == 0 &&
				fWholePartLinkGraph.numOutgoingEdges(wpLink.source()) == 0) {
					fWholePartLinkGraph.remove(wpLink.source());
			}
			
			if (fWholePartLinkGraph.numIncomingEdges(wpLink.target()) == 0 &&
				fWholePartLinkGraph.numOutgoingEdges(wpLink.target()) == 0) {
					fWholePartLinkGraph.remove(wpLink.target());
			}
		}
	}

	/**
	 * Inserts a link between objects to the current state-
	 * @exception MSystemException link invalid or already existing
	 * @return the newly created link.
	 */
	public MLink createLink(MAssociation assoc, List<MObject> objects, List<List<Value>> qualifierValues)
			throws MSystemException {
		
		MLink link = null;
		StringWriter sw = new StringWriter();

		if(assoc.isDerived()){
			throw new MSystemException("Cannot create link for association with derived end.");
		}
		
		validateLinkQualifiers(assoc, qualifierValues);
		
		if (!validateRedefinesForLink(assoc, objects, new PrintWriter(sw))) {
			throw new MSystemException(sw.toString());
		}
		
		// checks if assoc is an associationclass
		if (assoc instanceof MAssociationClass) {
			// specifies if there is already an existing linkobject of this
			// association class between the objects
			if (hasLinkBetweenObjects(assoc, objects, qualifierValues)) {
				throw new MSystemException(
						"Cannot insert two linkobjects of the same type"
								+ " between one set of objects!");
			}

			String name = fSystem.uniqueObjectNameForClass(assoc.name());
			
			// creates a linkobject with a generated name
			link = createLinkObject((MAssociationClass) assoc, name, objects, qualifierValues);
			
		} else if ((assoc.aggregationKind() == MAggregationKind.AGGREGATION)
				|| (assoc.aggregationKind() == MAggregationKind.COMPOSITION)) {
			
			MLinkSet linkSet = fLinkSets.get(assoc);
			link = new MLinkImpl(assoc, objects, qualifierValues);
			if (linkSet.contains(link))
				throw new MSystemException("Link `" + assoc.name()
						+ "' between ("
						+ StringUtil.fmtSeq(objects.iterator(), ",")
						+ ") already exist.");
			
			// The graph to store the information of the whole/part hierachy
			MWholePartLink wholePartLink = new MWholePartLinkImpl(link);
			MObject source = wholePartLink.source();
			MObject target = wholePartLink.target();
			 
			fWholePartLinkGraph.add(source);
			fWholePartLinkGraph.add(target);
			
			// the link is irreflexive
			if (wholePartLink.isReflexive())
				Log.warn("Warning: Object `" + source.name()
						+ "' cannot be a part of itself.");
			
			// check for SHARED OBJECT OF THE COMPOSION RELATIONSHIP
			if (assoc.aggregationKind() == MAggregationKind.COMPOSITION) {
				// finding all edges (links) whose target is the target object
				// of the new link
				Iterator<MObject> sourceEdgeIter = fWholePartLinkGraph.sourceNodeSet(target).iterator();
				
				while (sourceEdgeIter.hasNext()) {
					Iterator<MWholePartLink> iter = fWholePartLinkGraph.edgesBetween(target,
							sourceEdgeIter.next()).iterator();
					
					while (iter.hasNext()) {
						MLink l = iter.next();
						if (l.association().aggregationKind() == MAggregationKind.COMPOSITION && 
							!associationsHaveSubsetsRelation(l.association(), assoc) &&
							!associationsHaveRedefinitionRelation(l.association(), wholePartLink.association())) {
							Log.warn("Warning: Insert has resulted in two aggregates for object `"
									 + target.name()
									 + "'. Object `"
									 + target.name()
									 + "' is already component of another object.");
						}
					}
				}
			}
			// check for cycles that might be occurred when adding the new
			// whole/part link
			if (fWholePartLinkGraph.existsPath(target, source))
				Log.warn("Warning: Insert has resulted in a cycle in the part-whole hierarchy. Object `"
								+ source.name()
								+ "' is a direct or indirect part of `"
								+ target.name() + "'.");
			// silently ignore duplicates
			linkSet.add(link);
			fWholePartLinkGraph.addEdge(wholePartLink);
		} else {
			// create a normal link
			link = new MLinkImpl(assoc, objects, qualifierValues);
			// get link set for association
			MLinkSet linkSet = fLinkSets.get(assoc);
			if (linkSet.contains(link))
				throw new MSystemException("Link `" + assoc.name()
						+ "' between ("
						+ StringUtil.fmtSeq(objects.iterator(), ",")
						+ ") already exist.");
			linkSet.add(link);
		}
		return link;
	}

	/**
	 * Validates, if a redefinition constraint of a child association of <code>toCheck</code> exists,
	 * that is a more specific redefinition.
	 * The redefinition constraint must be suitable for the given list of <code>objects</code>.
	 **/
	private boolean validateRedefinesForLink(MAssociation assoc, List<MObject> objects, PrintWriter err) {
		final Set<MAssociation> redefinedBy = assoc.getRedefinedByClosure();
		
		// Number of ends equals (checked during model creation)
		int numEnds = assoc.associationEnds().size();
		
		// Go through the redefining associations
		for (MAssociation redefiningAssoc : redefinedBy) {
			// Check if at least one object at the other end is a subclass of the end type. 
			// If true, the redefinition must hold
			for (int i = 0; i < numEnds; ++i) {
				// If the redefinition start at the same end, we don't need
				// to check, because this end won't give us information
				MAssociationEnd checkAgainstEnd = assoc.associationEnds().get(i);
				MAssociationEnd childEnd =  redefiningAssoc.associationEnds().get(i);
				
				if (assoc.isUnion() || assoc.isDerived()) {
					// In a union association, no invalid links can exist
					// because, the redefined links are all subclasses and validated
					// downwards the inheritance tree
					
					// For derived associations, the type checker ensures
					// valid links.
					continue;
				} else if (!checkAgainstEnd.cls().equals(childEnd.cls()) && 
					objects.get(i).cls().isSubClassOf(redefiningAssoc.associationEnds().get(i).cls() ) ) {
					if (err != null) {
						err.print("The link of the association ");
						err.print(StringUtil.inQuotes(assoc));
						err.print(" with the participants (");
						err.print(StringUtil.fmtSeq(objects, ",", new IElementFormatter<MObject>() {
							@Override
							public String format(MObject element) {
								return element.name() + ":" + element.cls().name();
							}} ));
						err.print(") is invalid, because the association is redefined by ");
						err.print(StringUtil.inQuotes(redefiningAssoc.name()));
						err.print(" with ends (");
						err.print(StringUtil.fmtSeq(redefiningAssoc.associationEnds(), ",", new IElementFormatter<MAssociationEnd>() {
							@Override
							public String format(MAssociationEnd element) {
								return element.name() + ":" + element.cls().name();
							}} ));
						err.println(").");
					}
									
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * Validates all links of the association if they confirm to the redefinition constraints. 
	 * @param assoc The association to validate the redefines constraints for.
	 * @param out PrintWriter to print error messages to. Can be <code>null</code>.
	 * @param reportAllErrors If <code>true</code>, all errors are written to <code>out</code>. Otherwise the validation stops at the first error.
	 * @return
	 */
	private boolean validateRedefines(MAssociation assoc, PrintWriter out, boolean reportAllErrors) {
		final Set<MAssociation> redefinedBy = assoc.getRedefinedByClosure();

		if (redefinedBy.isEmpty()) {
			return true;
		}
		
		boolean isValid = true;
		
		for (MLink link : this.fLinkSets.get(assoc).links()) {
			isValid = validateRedefinesForLink(assoc, link.linkedObjects(), out) && isValid;
			
			if (!isValid && !reportAllErrors)
				return isValid;
		}
		
		return isValid;
	}
	
	
	/**
	 * Validates the correct usage of qualified associations, e.g.,
	 * The provided qualifier values are checked against the definition (defined?, Type?)
	 * and vice versa.
	 * @param assoc
	 * @param qualifiers
	 * @throws MSystemException
	 */
	private void validateLinkQualifiers(MAssociation assoc,
			List<List<Value>> qualifiers) throws MSystemException {
		if (assoc.hasQualifiedEnds()) {
			if (qualifiers == null || qualifiers.size() != assoc.associationEnds().size() )
				throw new MSystemException("Missing qualifier values for link creation.");
			
			for (int index = 0; index < assoc.associationEnds().size(); index++) {
				MAssociationEnd end = assoc.associationEnds().get(index);
				List<Value> qualifierValues = qualifiers.get(index);
				
				if (end.hasQualifiers()) {
					if (qualifierValues == null) {
						throw new MSystemException("Association end "
								+ StringUtil.inQuotes(end.toString())
								+ " requires qualifier values.");
					}
					
					if (qualifierValues.size() != end.getQualifiers().size()) {
						throw new MSystemException("Association end "
								+ StringUtil.inQuotes(end.toString())
								+ " requires " + end.getQualifiers().size()
								+ " qualifier values. Provided: "
								+ qualifierValues.size() + ".");
					}
					
					for (int valueIndex = 0; valueIndex < qualifierValues.size(); valueIndex++) {
						Value qualifierValue = qualifierValues.get(valueIndex);
						VarDecl qualifier = end.getQualifiers().get(valueIndex);
						
						if (!qualifierValue.type().conformsTo(qualifier.type())) {
							throw new MSystemException(
									"The type of the provided value ("
											+ StringUtil.inQuotes(qualifierValue
													.type().toString())
											+ ") for the qualifier "
											+ qualifier.name()
											+ " does not conform to the specified type ("
											+ StringUtil.inQuotes(qualifier
													.type().toString()) + ")");
						}
					}
				} else {
					if (qualifierValues != null && !qualifierValues.isEmpty()) {
						throw new MSystemException("The association end " + StringUtil.inQuotes(end.toString()) + " has no qualifier.");
					}
				}
			}
		} else if (qualifiers != null && !qualifiers.isEmpty()) {
			// All qualifier values must be null or empty
			for (int index = 0; index < assoc.associationEnds().size(); ++index) {
				if (!(qualifiers.get(index) == null || qualifiers.get(index).isEmpty()))
					throw new MSystemException("No association end of association " + StringUtil.inQuotes(assoc.toString()) + " has qualifier.");
			}
		}
	}
	
	private DirectedGraph<MObject, MWholePartLink> fWholePartLinkGraph = new DirectedGraphBase<MObject, MWholePartLink>();
	
	/**
	 * The graph to store the information of the whole/part hierachy.
	 */
	private DirectedGraph<MObject, MWholePartLink> getWholePartLinkGraph() {		
		return fWholePartLinkGraph;
	}

	/**
	 * Inserts a link into the state.
	 */
	public void insertLink(MLink link) {
		// get link set for association
		MLinkSet linkSet = fLinkSets.get(link.association());
		linkSet.add(link);
	}

	/**
	 * Deletes a link from the state. The link is indirectly specified by the
	 * association and objects.
	 * 
	 * @exception MSystemException
	 *                link does not exist
	 * @return the removed link.
	 */
	public DeleteObjectResult deleteLink(MAssociation assoc, List<MObject> objects, List<List<Value>> qualifierValues)
			throws MSystemException {
		MLink link = null;
		
		MLinkSet linkSet = linksOfAssociation(assoc);
		link = linkSet.linkBetweenObjects(objects, qualifierValues);
		
		if (link == null) {
			throw new MSystemException("Link `" + assoc.name() + "' between ("
					+ StringUtil.fmtSeqWithSubSeq(objects, ",", qualifierValues, ",", "{", "}")
					+ ") does not exist.");
		}
		
		return deleteLink(link);
	}
	
	public DeleteObjectResult deleteLink(MLink link) throws MSystemException {
		DeleteObjectResult result = new DeleteObjectResult();

		MLinkSet linkSet = linksOfAssociation(link.association());
		if (!linkSet.remove(link)) {
			throw new MSystemException("Invalid link to delete provided!");
		}
		
		removeLinkFromWholePartGraph(link);
		result.getRemovedLinks().add(link);

		if (link instanceof MLinkObject) {
			DeleteObjectResult res = auxDeleteObject((MLinkObject) link);
			result.getRemovedLinks().addAll(res.getRemovedLinks());
			result.getRemovedObjects().addAll(res.getRemovedObjects());
			result.getRemovedObjectStates().addAll(res.getRemovedObjectStates());
		}
		
		return result;
	}
	
	/**
	 * Creates and adds a new link to the state.
	 * 
	 * @exception MSystemException
	 *                link invalid or already existing
	 * @return the newly created link.
	 */
	public MLinkObject createLinkObject(MAssociationClass assocClass,
			String name, List<MObject> objects, List<List<Value>> qualifierValues) throws MSystemException {
		
		if ((name != null) && !isValidObjectName(name)) {
			throw new MSystemException(
					StringUtil.inQuotes(name) + 
					" is not a valid object name");
		}
		
		if (objectByName(name) != null) {
			throw new MSystemException("An object with name `" + name
					+ "' already exists.");
		}
		
		if (hasLinkBetweenObjects(assocClass, objects, qualifierValues)) {
			throw new MSystemException(
					"Cannot insert two linkobjects of the same type"
							+ " between one set of objects!");
		}
		
		if (assocClass.isAbstract()) {
			throw new MSystemException(
					"Cannot create a linkobject of an abtract association class!");
		}
		
		validateLinkQualifiers(assocClass, qualifierValues);
		StringWriter sw = new StringWriter();
		if (!validateRedefinesForLink(assocClass, objects, new PrintWriter(sw))) {
			throw new MSystemException(sw.toString());
		}
		
		MLinkObject linkobj = new MLinkObjectImpl(assocClass, name, objects, qualifierValues);

		//FIXME: Qualifier!
		// Part from createLink method
		MLinkSet linkSet = fLinkSets.get(assocClass);
		if (linkSet.contains(linkobj))
			throw new MSystemException("Link " + linkobj + " already exists.");
				
		// Part from createObject method
		MObjectState objState = new MObjectState(linkobj);
		fObjectStates.put(linkobj, objState);
		fClassObjects.put(assocClass, linkobj);
		fObjectNames.put(linkobj.name(), linkobj);

		objState.initialize(this);
		
		StringWriter err = new StringWriter();
		boolean valid = true;
		
		// Validate initial states of state machines
		for (MProtocolStateMachineInstance sm : objState.getProtocolStateMachinesInstances()) {
			valid &= sm.checkStateInvariant(this, new PrintWriter(err));
		}

		if (!valid) {
			fObjectStates.remove(linkobj);
			fClassObjects.remove(assocClass, linkobj);
			fObjectNames.remove(linkobj.name());
			
			throw new MSystemException("Object creation failed:\n" + err.toString());
		}

		linkSet.add(linkobj);
		return linkobj;
	}
	
	/**
	 * Returns the state of an object in a specific system state.
	 * 
	 * @return null if object does not exist in the state
	 */
	MObjectState getObjectState(MObject obj) {
		return fObjectStates.get(obj);
	}
    
	/**
	 * This operation returns all objects reachable from a source object <code>obj</code>
	 * navigating from association end <code>srcEnd</code> to association end
	 * <code>dst</code>.
	 * The difference to {@link #getNavigableObjects(MObject, MNavigableElement, MNavigableElement, List)}
	 * is, that no qualifier values are provided. Instead the reachable objects
	 * are partitioned by these values, to allow the validation of
	 * multiplicities on qualified ends (The meaning of multiplicities change when using qualifiers to
	 * how many objects are connected given concrete qualifier values).
	 * @param obj
	 * @param srcEnd
	 * @param dstEnd
	 * @return
	 */
	private Map<List<Value>,Set<MObject>> getLinkedObjects(MObject obj, MAssociationEnd srcEnd, MAssociationEnd dstEnd) {
		Map<List<Value>, Set<MObject>> res = new HashMap<List<Value>, Set<MObject>>();

		// get association
		MAssociation assoc = dstEnd.association();
		int srcIndex = srcEnd.association().associationEnds().indexOf(srcEnd);
		int dstIndex = dstEnd.association().associationEnds().indexOf(dstEnd);
		
		MLinkSet linkSet;
		
		if (dstEnd.isUnion()) {
			// add subsetting ends
			for (MAssociationEnd subsettingDestEnd : dstEnd.getSubsettingEnds()) {
				// TODO: n-ary!
				MAssociationEnd subsettingSrcEnd = subsettingDestEnd.getAllOtherAssociationEnds().get(0);
				
				// Add only if src end is a generalization relationship with cls of obj
				if (subsettingSrcEnd.cls().isSubClassOf(obj.cls()) || obj.cls().isSubClassOf(subsettingSrcEnd.cls())) {
					res.putAll(getLinkedObjects(obj, subsettingSrcEnd, subsettingDestEnd));
				}
			}
		} else if (!dstEnd.getRedefiningEnds().isEmpty()) {
			// add redefining ends
			for (MAssociationEnd redefiningDestEnd : dstEnd.getRedefiningEnds()) {
				MAssociationEnd redefiningSrcEnd = redefiningDestEnd.getAllOtherAssociationEnds().get(0);
				// Add only if src end is generalization relationship with cls of obj
				if (redefiningSrcEnd.cls().isSubClassOf(obj.cls()) || obj.cls().isSubClassOf(redefiningSrcEnd.cls())) {				
					res.putAll(getLinkedObjects(obj, redefiningSrcEnd, redefiningDestEnd));
				}
			}
			
		} else if (assoc.isDerived()) {
			// Derived cannot be qualified, otherwise for each qualifier value
			// combination like Integer x Integer the derive expression must be evaluated.
			List<MObject> derived = getNavigableObjectsFromDerivedAssociation(obj, srcEnd, dstEnd);
			res.put(Collections.<Value>emptyList(), new HashSet<MObject>(derived));
		} else {
			// get link set for association
			linkSet = fLinkSets.get(assoc);
			
			if (Log.isTracing())
				Log.trace(this, "linkSet size of association `" + assoc.name() + "' = " + linkSet.size());

			// if link set is empty return empty result list	
			if (linkSet.size() == 0)
				return res;
	
			// select links with srcEnd == obj
			Set<MLink> links = linkSet.select(srcEnd, obj);
			
			if (Log.isTracing())
				Log.trace(this, "linkSet.select for object `" + obj + "', size = " + links.size());
	
			// project tuples to destination end component
			for (MLink link : links) {
				MLinkEnd linkEndDst = link.getLinkEnd(dstIndex);
				MLinkEnd linkEndSrc = link.getLinkEnd(srcIndex);
				
				if (!res.containsKey(linkEndSrc.getQualifierValues()))
					res.put(linkEndSrc.getQualifierValues(), new HashSet<MObject>());
				
				res.get(linkEndSrc.getQualifierValues()).add(linkEndDst.object());
			}
		}
		
		return res;
	}
	
	public MObject getNavigableObject(MLink link, MNavigableElement dst) {
		int indexDst = link.association().associationEnds().indexOf(dst);
		return link.getLinkEnd(indexDst).object();
	}
	
	/**
	 * Returns a list of objects at <code>dst</code> which are connected to the
	 * object <code>obj</code> at <code>src</code>. This is needed for navigation.
	 * 
	 * @return List(MObject)
	 */
	public List<MObject> getNavigableObjects(MObject obj, MNavigableElement src, MNavigableElement dst, List<Value> qualifierValues) {
		return getNavigableObjects(obj, src, dst, qualifierValues, false, false);
	}

	protected List<MObject> getNavigableObjects(MObject obj, MNavigableElement src, MNavigableElement dst, List<Value> qualifierValues, boolean excludeDerivedLinks, boolean excludeRedefines) {
		MAssociation assoc = dst.association();
		int srcIndex = assoc.reachableEnds().indexOf(src);
		int dstIndex = assoc.reachableEnds().indexOf(dst);
		return getNavigableObjects(obj, assoc, srcIndex, dstIndex, qualifierValues, excludeDerivedLinks, excludeRedefines);
	}
	
	/**
	 * Returns a list of objects at <code>dst</code> which are connected to the
	 * object <code>obj</code> at <code>src</code>. This is needed for navigation.
	 * It recursively calls this method if there are child associations.
	 * @param obj The object the navigation starts
	 * @param src The source end the navigation starts 
	 * @param dst The end to navigate to
	 * @param qualifierValues The values which qualify the navigation
	 * @param excludeDerivedLinks If <code>true</code>, the concrete link sets are queried only for non virtual links. Derived association ends are still evaluated.
	 * @param excludeRedefines If <code>true</code>, redefining associations are not considered. Needed, because the first association queries all redefining associations.  
	 * @return
	 */
	protected List<MObject> getNavigableObjects(MObject obj, MAssociation assoc, int srcIndex, int dstIndex, List<Value> qualifierValues, boolean excludeDerivedLinks, boolean excludeRedefines) {
		ArrayList<MObject> res = null;
		Set<MAssociation> handledChilds = new HashSet<MAssociation>();
		
		MNavigableElement src = assoc.reachableEnds().get(srcIndex);
		MNavigableElement dst = assoc.reachableEnds().get(dstIndex);
		
		// Links are computed by the union of all subsets
		if (assoc.isUnion()) {
			if (Log.isDebug()) {
				Log.debug("getNavigableObjects for union [obj=" + obj + "; src=" + src + ";  dst=" + dst);
			}
			Set<MObject> tmpResult = new HashSet<MObject>();

			for (MAssociation subsettingAssoc : dst.association().children()) {
				MNavigableElement subsettingSrcEnd = subsettingAssoc.reachableEnds().get(srcIndex);
				MNavigableElement subsettingDstEnd = subsettingAssoc.reachableEnds().get(dstIndex);
				
				boolean subsets = false;
				// There are various number of child associations. Check for subsets
				if (subsettingSrcEnd instanceof MAssociationEnd) {
					subsets = ((MAssociationEnd)subsettingSrcEnd).getSubsettedEnds().contains(src);
				}

				if (!subsets && subsettingDstEnd instanceof MAssociationEnd) {
					subsets = ((MAssociationEnd)subsettingDstEnd).getSubsettedEnds().contains(dst);
				}
				
				if (subsets) {
					// Check only if source end has a generalization relationship with class of obj (inheritance tree can split up)
					if (subsettingSrcEnd.cls().isSubClassOf(obj.cls()) || obj.cls().isSubClassOf(subsettingSrcEnd.cls())) {
						tmpResult.addAll(getNavigableObjects(obj, subsettingAssoc, srcIndex, dstIndex, qualifierValues, excludeDerivedLinks, excludeRedefines));
					}
				}
			}
			
			res = new ArrayList<MObject>(tmpResult);
			return res;
		} 
		
		if (assoc.isDerived()) {
			if (Log.isDebug()) {
				Log.debug("getNavigableObjects for derived [obj=" + obj + "; src=" + src + ";  dst=" + dst + "]");
			}
			res = getNavigableObjectsFromDerivedAssociation(obj, src, dst);
		} else {
			if (Log.isDebug()) {
				Log.debug("getNavigableObjects normal [obj=" + obj + "; src=" + src + ";  dst=" + dst);
			}
			res = new ArrayList<MObject>();
			
			// get link set for association
			MLinkSet linkSet = linksOfAssociation(assoc);

			// if link set is empty return empty result list
			if (Log.isTracing()) {
				Log.trace(this, "linkSet size of association `" + assoc.name() + "' = " + linkSet.size());
			}

			if (linkSet.size() > 0) {

				// navigation from a linkobject
				if (src instanceof MAssociationClass) {
					if (dst instanceof MAssociationClass) {
						// Links for a link object are stored with a single additional end of type MAssociationClass 
						throw new RuntimeException("Internal error! Wrong internal navigation from accociation class end to association class end.");
					}
					MLinkEnd linkEnd = ((MLink)obj).getLinkEnd(dstIndex);
					res.add(linkEnd.object());
				} else {
					MAssociationEnd srcEnd = (MAssociationEnd) src;
					// select links with srcEnd == obj
					Set<MLink> links = linkSet.select(srcEnd, obj, qualifierValues, excludeDerivedLinks);
	
					if (Log.isTracing()) {
						Log.trace(this, "linkSet.select for object `" + obj + "', size = " + links.size());
					}
	
					// navigation to a linkobject
					if (dst instanceof MAssociationClass) {
						for (MLink link : links) {
							res.add((MObject) link);
						}
					} else {
						// project tuples to destination end component
						for (MLink link : links) {
							MLinkEnd linkEnd = link.getLinkEnd(dstIndex);
							res.add(linkEnd.object());
						}
					}
				}
			}
			// Check the children
			if (assoc instanceof MAssociationClass) {
				MAssociationClass assocCls = (MAssociationClass)assoc;
				for (MAssociationClass child : assocCls.children()) {
					// Redefines does not need to consider them
					handledChilds.add(child);
					// If an association class does not add new ends, the base association is used. 
					List<MObject> childs = getNavigableObjects(obj, child, srcIndex, dstIndex, qualifierValues, excludeDerivedLinks, excludeRedefines);
					
					res.addAll(childs);
				}
			}
		}

		if (!excludeRedefines) {
			// Collect all links of possible child associations.
			// Since we collect all children here, the child association does not need to calculate children
			for (MAssociation child : assoc.getRedefinedByClosure()) {
				if (handledChilds.contains(child)) continue;
				
				List<MObject> childs = getNavigableObjects(obj, child, srcIndex, dstIndex, qualifierValues, excludeDerivedLinks, true);
				res.addAll(childs);
			}
		}
		
		return res;
	}

	protected ArrayList<MObject> getNavigableObjectsFromDerivedAssociation(MObject obj, MNavigableElement src, MNavigableElement dst) {
		ArrayList<MObject> res = new ArrayList<MObject>();
		
		// Get all linked objects by evaluating the derive expression
		MAssociation association = dst.association(); 
		
		MAssociationEnd derivedEnd = null;
		
		for (MAssociationEnd end : association.associationEnds()) {
			if (end.isDerived()) {
				derivedEnd = end;
				break;
			}
		}
		
		assert(derivedEnd != null);
		
		int numEnds = association.associationEnds().size();
		List<MObject[]> toEvaluate = new ArrayList<MObject[]>();
		// Index of the destination object used for the input to
		// the evaluate derive expression.
		int dstIndex = -1;
		
		if (numEnds == 2) {
			if (src.isDerived()) {
				// if the source end is derived, check derive expression for all
				// possible instances of the destination end.
				Set<MObject> allObjects = objectsOfClassAndSubClasses(dst.cls());
				for (MObject o : allObjects) {
					toEvaluate.add( new MObject[] {o} );
				}
				dstIndex = 0;
			} else {
				// Simply evaluate the expression on source
				toEvaluate.add( new MObject[] {obj} );
			}
		} else {
			List<MAssociationEnd> noneDerivedEnds = derivedEnd.getAllOtherAssociationEnds();
			int srcPosition = noneDerivedEnds.indexOf(src);
			
			List<MClass> otherClasses = new ArrayList<MClass>();
			
			for (int index = 0; index < noneDerivedEnds.size(); ++index) {
				MAssociationEnd end = noneDerivedEnds.get(index);
				if (!end.equals(src)) {
					otherClasses.add(end.cls());
					if (end.equals(dst)) {
						dstIndex = index;
					}
				}
			}
			
			Bag<MObject[]> sourceObjects = getCrossProductOfInstanceSets(otherClasses);
			for (MObject[] toEval : sourceObjects) {
				MObject[] evalInput = new MObject[numEnds - 1];
				if (srcPosition >= 0)
					evalInput[srcPosition] = obj;
				
				int i = 0;
				for (int index = 0; index < srcPosition; ++index) {
					evalInput[index] = toEval[i];
					++i;
				}
				
				for (int index = srcPosition + 1; index < numEnds - 1; ++index) {
					evalInput[index] = toEval[i];
					++i;
				}
				
				toEvaluate.add(evalInput);
			}
		}

		for (MObject[] sourceObjects : toEvaluate) {
			List<MObject> linkedObjects;
			try {
				linkedObjects = evaluateDeriveExpression(sourceObjects, derivedEnd);
			} catch (StackOverflowError e) {
				Log.error("Derive expression of association end " + StringUtil.inQuotes(derivedEnd) + " let to a stack overflow!\nMaybe an infinite recursion is defined.");
				continue;
			} catch (RuntimeException e) {
				Log.error("Derive expression of association end " + StringUtil.inQuotes(derivedEnd) + " let to a runtime exception: " + e.getMessage());
				continue;
			} catch (MSystemException e) {
				Log.error("Derive expression of association end " + StringUtil.inQuotes(derivedEnd) + " let to a system exception: " + e.getMessage());
				continue;
			}
			
			if (src.isDerived()) {
				if (linkedObjects.contains(obj))
					res.add(sourceObjects[dstIndex]);
			} else if (dst.isDerived()) {
				res.addAll(linkedObjects);
			} else {
				if (linkedObjects.size() > 0)
					res.add(sourceObjects[dstIndex]);
			}
		}
		
		return res;
	}

	Value evaluateInitExpression(MObject self, Expression initExp) {
		// no variables in context
		VarBindings vars = new VarBindings();
		EvalContext ctx = new SimpleEvalContext(this, this, vars);
		ctx.pushVarBinding("self", self.value());
		
        Value res = null;
        try {
        	res = initExp.eval(ctx);
        } catch (MultiplicityViolationException e) {
        	return UndefinedValue.instance;
        }
        
        return res;
	}
	
	List<MObject> evaluateDeriveExpression(MObject[] source, MAssociationEnd dst) throws MSystemException {
		// add the object values to the context
		EvalContext ctx = new SimpleEvalContext(MSystemState.this, MSystemState.this, new VarBindings());
		List<MObject> result = new LinkedList<>();
		
		for (int i = 0; i < source.length; ++i) {
			ObjectValue objVal = new ObjectValue((MClass)dst.getDeriveParamter().varDecl(i).type(), source[i]);
			ctx.pushVarBinding(dst.getDeriveParamter().varDecl(i).name(), objVal);
		}
		
        Value res = null;
        try {
        	res = dst.getDeriveExpression().eval(ctx);
        } catch (MultiplicityViolationException e) {
        	return Collections.emptyList();
        }
    	
    	if (res.isUndefined()) {
    		return Collections.emptyList();
    	}

    	if (res.type().isTypeOfClass()) {
    		// Single object as result
    		ObjectValue singleObject = (ObjectValue)res;
    		result.add(singleObject.value());
    	} else if (res.type().isKindOfCollection(VoidHandling.EXCLUDE_VOID)) {
    		// Collection of objects as result
    		CollectionValue col = (CollectionValue)res;
    		
    		
    		for (Value v : col) {
    			if (!v.isUndefined()) {
    				ObjectValue singleObject = (ObjectValue)v;
    				result.add(singleObject.value());
    			}
    		}
    	} else {
    		// Ups....
    		throw new MSystemException("Invalid return type of derive expression");
    	}
    	
    	return result;
	}

	public Value evaluateDeriveExpression(final ObjectValue source, final MAttribute attribute) {
		final EvalContext ctx = new SimpleEvalContext(this, this, system().varBindings());
    	
        ctx.pushVarBinding("self", source);
		try {
			return attribute.getDeriveExpression().eval(ctx);
		} catch (StackOverflowError e) {
			Log.error("Derive expression of attribute " + StringUtil.inQuotes(attribute.qualifiedName()) + " let to a stack overflow!\nMaybe an infinite recursion is defined.");
		} catch (RuntimeException e) {
			Log.error("Derive expression of attribute " + StringUtil.inQuotes(attribute.qualifiedName()) + " let to a runtime exception: " + e.getMessage());
		}
		return UndefinedValue.instance;
	}
	
	public Value evaluateDeriveExpression(final MObject source, final MAttribute attribute) {
		final ObjectValue objVal = new ObjectValue(source.cls(), source);
		
    	return evaluateDeriveExpression(objVal, attribute);
	}
	
	/**
	 * Checks for a valid system state. Returns true if all constraints hold for
	 * all objects. Prints result of subexpressions for failing constraints to
	 * the log if traceEvaluation is true. - Checks the whole/part hierarchy.
	 */
	public boolean check(PrintWriter out, boolean traceEvaluation,
			boolean showDetails, boolean allInvariants, final List<String> invNames) {
		boolean valid = true;
		Evaluator evaluator = new Evaluator();

		// model inherent constraints: check whether cardinalities of
		// association links match their declaration of multiplicities
		if (!checkStructure(out)) {
			valid = false;
		}

		if (Options.EVAL_NUMTHREADS > 1)
			out.println("checking invariants (using " + Options.EVAL_NUMTHREADS
					+ " concurrent threads)...");
		else
			out.println("checking invariants...");
		
		out.flush();
		int numChecked = 0;
		int numFailed = 0;
		long tAll = System.currentTimeMillis();

		ArrayList<MClassInvariant> invList = new ArrayList<MClassInvariant>();
		ArrayList<Boolean> negatedList = new ArrayList<Boolean>();
		ArrayList<Expression> exprList = new ArrayList<Expression>();
		Collection<MClassInvariant> source;
		
		if (invNames.isEmpty()) {
			source = fSystem.model().classInvariants();
		} else {
			source = Collections2.filter(fSystem.model().classInvariants(), 
					new Predicate<MClassInvariant>() {
						@Override
						public boolean apply(MClassInvariant input) {
							return invNames.contains(input.name());
						}
					});
		}
		
		for (MClassInvariant inv : source) {
		
			// Ignore if deactivated and not all should be checked.
			if (!allInvariants && !inv.isActive()) continue;
			
			Expression expr = inv.expandedExpression();
			
			if (inv.isNegated()) {
				try {
					Expression[] args = { expr };
					Expression expr1 = ExpStdOp.create("not", args);
					expr = expr1;
				} catch (ExpInvalidException e) {
					// This cannot happen, since in invariant is a boolean expression 
					// (checked by MClassInvariant constructor)
				}
				negatedList.add(Boolean.TRUE);
			} else {
				negatedList.add(Boolean.FALSE);
			}
			invList.add(inv);
			exprList.add(expr);
		}

		// start (possibly concurrent) evaluation
		Queue resultValues = evaluator.evalList(Options.EVAL_NUMTHREADS,
				exprList, this);

		// receive results
		for (int i = 0; i < exprList.size(); i++) {
			MClassInvariant inv = invList.get(i);
			numChecked++;
			String msg = "checking invariant (" + numChecked + ") `"
					+ inv.cls().name() + "::" + inv.name() + "': ";
			out.print(msg); // + inv.bodyExpression());
			out.flush();
			try {
				Value v = (Value) resultValues.get();

				// if value 'v' is null, the invariant can not be evaluated,
				// therefore it is N/A (not available).
				if (v == null) {
					out.println("N/A");
					// if there is a value, the invariant can always be
					// evaluated and the
					// result can be printed.
				} else {
					boolean ok = v.isDefined() && ((BooleanValue) v).isTrue();
					if (ok)
						out.println("OK."); // (" + timeStr +").");
					else {
						out.println("FAILED."); // (" + timeStr +").");
						out.println("  -> " + v.toStringWithType());

						// repeat evaluation with output of all subexpression
						// results
						if (traceEvaluation) {
							out.println("Results of subexpressions:");
							Expression expr = exprList.get(i);
							evaluator.eval(expr, this, new VarBindings(), out);
						}

						// show instances violating the invariant by using
						// the OCL expression C.allInstances->reject(self |
						// <inv>)
						if (showDetails) {
							out.println("Instances of " + inv.cls().name()
									+ " violating the invariant:");
							Expression expr = inv
									.getExpressionForViolatingInstances();
							Value v1 = evaluator.eval(expr, this,
									new VarBindings());
							out.println("  -> " + v1.toStringWithType());
						}
						valid = false;
						numFailed++;
					}
				}
			} catch (InterruptedException ex) {
				Log.error("InterruptedException: " + ex.getMessage());
			}
		}

		long t = System.currentTimeMillis() - tAll;
		String timeStr = t % 1000 + "s";
		timeStr = (t / 1000) + "." + StringUtil.leftPad(timeStr, 4, '0');
		out.println("checked " + numChecked + " invariant"
				+ ((numChecked == 1) ? "" : "s") + (Options.testMode ? "" : " in " + timeStr) + ", "
				+ numFailed + " failure" + ((numFailed == 1) ? "" : "s") + '.');
		out.flush();
		return valid;
	}

	/**
	 * Checks the whole/part hierarchy.
	 */
	private boolean checkWholePartLink(PrintWriter out) {
		boolean valid = true;
		boolean isCyclic = false;
		DirectedGraph<MObject, MWholePartLink> fWholePartLinkGraph = getWholePartLinkGraph();
		List<MObject> sharedObjects = new ArrayList<MObject>();
		
		MWholePartLink wholePartLink = null;
		MObject source = null;
		MObject target = null;		
		Iterator<MWholePartLink> edgeIter = fWholePartLinkGraph.edgeIterator();
		while (edgeIter.hasNext()) {
			wholePartLink = edgeIter.next();
			source = wholePartLink.source();
			target = wholePartLink.target();
			// ****************************************************************
			// the link is irreflexive
			// ****************************************************************
			if (wholePartLink.isReflexive()) {
				out.println("Error: Object `" + source.name()
						+ "' cannot be a part of itself.");
				valid = false;
			}
			// ****************************************************************
			// check for SHARED OBJECTs OF THE COMPOSITION RELATIONSHIP
			// ****************************************************************
			if ((wholePartLink.association().aggregationKind() == MAggregationKind.COMPOSITION)
					&& !sharedObjects.contains(target)) {
				// finding all edges (links) whose target is the target object
				// of the new link
				for (MObject tmpSource : fWholePartLinkGraph.sourceNodeSet(target)) {
					for (MWholePartLink tmpWholePartLink : fWholePartLinkGraph.edgesBetween(tmpSource, target)) {
						MLink l = tmpWholePartLink;
						if ((l.association().aggregationKind() == MAggregationKind.COMPOSITION)
								&& (!wholePartLink.equals(l))
								&& !sharedObjects.contains(target)
								&& tmpWholePartLink.target().equals(target)) {
							
							if (!associationsHaveSubsetsRelation(l.association(), wholePartLink.association()) &&
								!associationsHaveRedefinitionRelation(l.association(), wholePartLink.association())) {
								out.println("Error: Object `" + target.name()
										+ "' is shared by object `" + source.name()
										+ "' and object `" + tmpSource.name()
										+ "'.");
								sharedObjects.add(target);
								valid = false;
							}
						}
					}
				}
			}
			// ****************************************************************
			// check for cycles that might be occurred when adding the new
			// whole/part link
			// ****************************************************************
			if (!isCyclic && fWholePartLinkGraph.existsPath(target, source)) {
				out
						.println("Error: There is a cycle in the part-whole hierarchy. Object `"
								+ source.name()
								+ "' is a direct or indirect part of `"
								+ target.name() + "'.");
				isCyclic = true;
				valid = false;
			}
		}
		return valid;
	}

	private boolean associationsHaveSubsetsRelation(MAssociation association, MAssociation association2) {
		
		Set<MAssociation> allSubsettingAssociations = association.getSubsettedByClosure();
		if (allSubsettingAssociations.contains(association2))
			return true;
		
		Set<MAssociation> allSubsettedAssociations = association.getSubsetsClosure();		
		if (allSubsettedAssociations.contains(association2))
			return true;
			
		return false;
	}

	private boolean associationsHaveRedefinitionRelation(MAssociation association, MAssociation association2) {
			
		Set<MAssociation> allRedefiningAssociations = association.getRedefinedByClosure();
		if (allRedefiningAssociations.contains(association2))
			return true;
		
		Set<MAssociation> allRedefinedAssociations = association.getRedefinesClosure();		
		if (allRedefinedAssociations.contains(association2))
			return true;
		
		return false;
	}

	/**
	 * Checks model inherent constraints, i.e., checks whether cardinalities of
	 * association links match their declaration of multiplicities.
	 */
	public boolean checkStructure(PrintWriter out) {
		return checkStructure(out, true);
	}
	
	/**
	 * Checks model inherent constraints, i.e., checks whether cardinalities of
	 * association links match their declaration of multiplicities.
	 */
	public boolean checkStructure(PrintWriter out, boolean reportAllErrors) {
		long start = System.currentTimeMillis();
		
		boolean res = true;
		out.println("checking structure...");
		out.flush();
		
		updateDerivedValues(true);
		
		// check the whole/part hierarchy
		if (!checkWholePartLink(out)) {
			if (!reportAllErrors) return false;
			res = false;
		}
		
		// check all associations
		for (MAssociation assoc : fSystem.model().associations()) {
			res = checkStructure(assoc, out, reportAllErrors) && res;
			if (!reportAllErrors && !res) return false;
		}

		out.flush();

		if (!Options.testMode) {
			long duration = System.currentTimeMillis() - start;
			out.println(String.format("checked structure in %,dms.", duration));
		}
		
		return res;
	}

	/**
	 * Checks model checks whether cardinalities of
	 * association links match their declaration of multiplicities.
	 * Further, subsetting and redefine constraints are validated.
	 */
	public boolean checkStructure(MAssociation assoc, PrintWriter out, boolean reportAllErrors) {
		boolean res = true;
		
		res = validateRedefines(assoc, out, reportAllErrors);
		
		if (assoc.associationEnds().size() != 2) {
			// check for n-ary links
			res = naryAssociationsAreValid(out, assoc, reportAllErrors) && res;
		} else {
			// check both association ends
			Iterator<MAssociationEnd> it2 = assoc.associationEnds().iterator();
			MAssociationEnd aend1 = it2.next();
			MAssociationEnd aend2 = it2.next();

			res = validateBinaryAssociations(out, assoc, aend1, aend2, reportAllErrors) && res;
			if (!res && !reportAllErrors) return res;
			
			res = validateBinaryAssociations(out, assoc, aend2, aend1, reportAllErrors) && res;
		}

		out.flush();
		return res;
	}

	private boolean naryAssociationsAreValid(PrintWriter out, MAssociation assoc, boolean reportAllErrors) {
		boolean valid = true;
		Set<MLink> links = linksOfAssociation(assoc).links();

		for (MAssociationEnd selEnd : assoc.associationEnds()) {
			List<MAssociationEnd> otherEnds = selEnd.getAllOtherAssociationEnds();
			List<MClass> classes = new ArrayList<MClass>();

			for (MAssociationEnd end : otherEnds) {
				classes.add(end.cls());
			}
			
			Bag<MObject[]> crossProduct = getCrossProductOfInstanceSets(classes);

			for (MObject[] tuple : crossProduct) {
				int count = 0;

				for (MLink link : links) {
					boolean ok = true;
					int index = 0;

					for (MAssociationEnd end : otherEnds) {
						if (link.linkEnd(end).object() != tuple[index]) {
							ok = false;
						}
						++index;
					}
					if (ok)
						++count;
				}
				if (!selEnd.multiplicity().contains(count)) {
					out.println("Multiplicity constraint violation in association `"
									+ assoc.name() + "':");
					out.println("  Objects `" + StringUtil.fmtSeq(tuple, ", ")
							+ "' are connected to " + count + " object"
							+ ((count == 1) ? "" : "s") + " of class `"
							+ selEnd.cls().name() + "'");
					out.println("  but the multiplicity is specified as `"
							+ selEnd.multiplicity() + "'.");
					valid = false;
				}
			}
			if (!reportAllErrors && !valid) return valid;
		}
		return valid;
	}

	/**
	 * Returns a bag containing the cross product of the instances of
	 * <code>classes</code>
	 * 
	 * @param classes
	 * @return a bag of object arrays (<code>Bag(MObject[])</code>)
	 */
	public Bag<MObject[]> getCrossProductOfInstanceSets(List<MClass> classes) {
		Bag<MObject[]> bag = new HashBag<MObject[]>();
		insertAllNMinusOneTuples(bag, classes.toArray(new MClass[classes.size()]), 0, new MObject[0]);
		return bag;
	}

	// Helper method for getCrossProductOfInstanceSets.
	private void insertAllNMinusOneTuples(Bag<MObject[]> allNMinusOneTuples,
			MClass[] classes, int index, MObject[] objects) {
		if (index < classes.length) {
			MClass next = classes[index];
			MObject[] objects1 = new MObject[objects.length + 1];
			for (int i = 0; i < objects.length; ++i)
				objects1[i] = objects[i];

			for (MObject obj : objectsOfClassAndSubClasses(next)) {
				objects1[objects.length] = obj;
				insertAllNMinusOneTuples(allNMinusOneTuples, classes,
						index + 1, objects1);
			}
		} else {
			allNMinusOneTuples.add(objects.clone());
		}
	}

	private boolean validateBinaryAssociations(PrintWriter out, MAssociation assoc, 
			MAssociationEnd aend1, MAssociationEnd aend2, boolean reportAllErrors) {
		boolean valid = true;

		// for each object of the association end's type get
		// the number of links in which the object participates
		MClass cls = aend1.cls();
		Set<MObject> objects = objectsOfClassAndSubClasses(cls);

		for (MObject obj : objects) {
			Map<List<Value>,Set<MObject>> linkedObjects = getLinkedObjects(obj, aend1, aend2);
			
			if (linkedObjects.size() == 0 && !aend2.multiplicity().contains(0)) {
				reportMultiplicityViolation(out, assoc, aend1, aend2, obj, null);
				if (!reportAllErrors) {
					return false;
				} else {
					valid = false;
					continue;
				}
			}
			
			for(Map.Entry<List<Value>, Set<MObject>> entry : linkedObjects.entrySet()) {
				if (!aend2.multiplicity().contains(entry.getValue().size())) {
					reportMultiplicityViolation(out, assoc, aend1, aend2, obj, entry);
					valid = false;
				}
				
				if (!aend1.getSubsettedEnds().isEmpty()) {
					if (!validateSubsets(out, obj, entry.getKey(), entry.getValue(), aend1))
						valid = false;
				}
			}
			
			if (!reportAllErrors && !valid) {
				return valid;
			}
		}
		
		return valid;
	}

	/**
	 * Writes informations about a multiplicity violation to <code>out</code>.
	 * @param out <code>PrintWriter</code> to write the message to.
	 * @param assoc The <code>MAssociation</code> the multiplicity is violated.
	 * @param aend1 The <code>MAssociationEnd</code> at the end <code>obj</code>.
	 * @param aend2 The <code>MAssociationEnd</code> at the end where the multiplicity is validated.
	 * @param obj The <code>MObject</code> which is validated.
	 * @param entry A <code>HashMap.Entry</code> with the linked objects. Can be null (no links)
	 */
	protected void reportMultiplicityViolation(PrintWriter out,
			MAssociation assoc, MAssociationEnd aend1, MAssociationEnd aend2,
			MObject obj, Map.Entry<List<Value>, Set<MObject>> entry) {
		
		if (out == NullPrintWriter.getInstance()) return;
		
		int n = (entry == null ? 0 : entry.getValue().size());
		
		out.println("Multiplicity constraint violation in association " + 
				StringUtil.inQuotes(assoc.name()) + ":");
		out.println("  Object " + StringUtil.inQuotes(obj.name()) + " of class "
				+ StringUtil.inQuotes(obj.cls().name()) + " is connected to " + n
				+ " object" + ((n == 1) ? "" : "s") + " of class "
				+ StringUtil.inQuotes(aend2.cls().name()));
		out.print("  at association end " + StringUtil.inQuotes(aend2.nameAsRolename()) + " ");
		if (aend1.hasQualifiers() && entry != null) {
			out.print(" with the qualifier value");
			if (entry.getKey().size() > 1) out.print("s");
			out.print(" [");
			out.print(StringUtil.fmtSeq(entry.getKey(), ","));
			out.println("]");
			out.print("  ");
		}
		out.println("but the multiplicity is specified as `"
				+ aend2.multiplicity() + "'.");
	}

	/**
	 * Checks if the list of connected objects is a subset of all parent navigations.
	 * @param out
	 * @param obj
	 * @param qualifierValues
	 * @param linkedObjects
	 * @param aend
	 * @return
	 */
	private boolean validateSubsets(PrintWriter out, MObject obj,
			List<Value> qualifierValues, Set<MObject> linkedObjects,
			MAssociationEnd aend) {
		boolean valid = true;
		
		for (MAssociationEnd subEnd1 : aend.getSubsettedEnds()) {
			List<MAssociationEnd> ends = subEnd1.getAllOtherAssociationEnds();
			
			// TODO: n-Ary
			assert ends.size() == 1;
			MAssociationEnd subEnd2 = ends.get(0);
			
			List<MObject> parentObjectList = getNavigableObjects(obj, subEnd1, subEnd2, qualifierValues);
			
			if (!parentObjectList.containsAll(linkedObjects)) {
				// Which objects are missing?
				linkedObjects.removeAll(parentObjectList);
				
				out.println("Constraint 'subsets " + subEnd1.association().name() + ":" + subEnd1.nameAsRolename() + "' on association end " + aend.nameAsRolename() + 
						    ":" + aend.association().name() + " is violated on object " + obj.toString() + ":" + obj.cls().name());
				
				out.println("Missing linked object" + (linkedObjects.size() > 1 ? "s" : "") + ": " + StringUtil.fmtSeq(linkedObjects.iterator(), ", "));
				
				valid = false;
			}
		}
		
		return valid;
	}
	
	/**
	 * Returns a unique name that can be used for a new object of the given
	 * class. Checks whether the name is used in this state. BugFix for USE
	 * 2.0.0. This operation is only used by the Snapshot Generator.
	 */
	public String uniqueObjectNameForClass(String clsName) {
		String name;
		do
			name = system().uniqueObjectNameForClass(clsName);
		while (objectByName(name) != null);
		return name;
	}
	
	/**
	 * wrapper for {@link #uniqueObjectNameForClass(String)}
	 * @param cls the class
	 * @return available unique object name for an object of the supplied class
	 */
	public String uniqueObjectNameForClass(MClass cls) {
		return uniqueObjectNameForClass(cls.name());
	}

	/**
	 * @param fLogWriter
	 */
	public void checkStateInvariants(@NonNull PrintWriter out) {
		boolean error = false;
		int checkedStateMachines = 0;
		long start = System.currentTimeMillis();
		
		for (MObject o : this.allObjects()) {
			if (o.cls().getAllOwnedProtocolStateMachines().isEmpty()) continue;
			
			for (MProtocolStateMachineInstance psmI : o.state(this).getProtocolStateMachinesInstances()) {
				error = error || !psmI.checkStateInvariant(this, out);
				++checkedStateMachines;
			}
		}
		
		long duration = System.currentTimeMillis() - start;
		if (!error) {
			out.println("All state invariants are valid.");
		}
		out.println(String.format("The evaluation of the state invariants of %,d state machines took %,dms.", checkedStateMachines, duration));
	}
}