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
package org.tzi.use.uml.sys;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.tzi.use.config.Options;
import org.tzi.use.gen.model.GFlaggedInvariant;
import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.graph.DirectedGraphBase;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MAggregationKind;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.ExpStdOp;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.util.Bag;
import org.tzi.use.util.HashBag;
import org.tzi.use.util.HashMultiMap;
import org.tzi.use.util.Log;
import org.tzi.use.util.MultiMap;
import org.tzi.use.util.Queue;
import org.tzi.use.util.StringUtil;

/**
 * A system state represents a valid instance of a model. It contains a set of
 * objects and links connecting objects. Methods allow manipulation and querying
 * of objects and links.
 * 
 * @version $ProjectVersion: 2-3-1-release.3 $
 * @author Mark Richters
 */

public final class MSystemState {
	public static final int REMOVED_LINKS = 0;
	public static final int REMOVED_OBJECTS = 1;
	public static final int REMOVED_OBJECTSTATES = 2;

	private String fName;
	private MSystem fSystem;

	/**
	 * The set of all object states.
	 */
	private Map<MObject, MObjectState> fObjectStates;

	/**
	 * The set of objects partitioned by class. Must be kept in sync with
	 * fObjectStates.
	 */
	private MultiMap<MClass, MObject> fClassObjects;

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
	 * Creates a new system state with no objects and links.
	 */
	MSystemState(String name, MSystem system) {
		fName = name;
		fSystem = system;
		fObjectStates = new HashMap<MObject, MObjectState>();
		fClassObjects = new HashMultiMap<MClass, MObject>();
		fObjectNames = new HashMap<String, MObject>();
		fLinkSets = new HashMap<MAssociation, MLinkSet>();

		// create empty link sets
		for(MAssociation assoc : fSystem.model().associations()) {
			MLinkSet linkSet = new MLinkSet(assoc);
			fLinkSets.put(assoc, linkSet);
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

		fClassObjects = new HashMultiMap<MClass, MObject>();
		fClassObjects.putAll(x.fClassObjects);

		fLinkSets = new HashMap<MAssociation, MLinkSet>();
		for(Map.Entry<MAssociation, MLinkSet> e : x.fLinkSets.entrySet()) {
			fLinkSets.put(e.getKey(), new MLinkSet((MLinkSet) e.getValue()));
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
	 * 
	 * @return Set(MObject)
	 */
	public Set<MObject> objectsOfClass(MClass cls) {
		return new HashSet<MObject>(fClassObjects.get(cls));
	}

	/*
	 * Returns the set of objects of class <code>cls</code> and all of its
	 * subclasses.
	 * 
	 * @return Set(MObject)
	 */
	public Set<MObject> objectsOfClassAndSubClasses(MClass cls) {
		Set<MObject> res = new HashSet<MObject>();
		Set<MClass> children = cls.allChildren();
		
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
		return fLinkSets.get(assoc);
	}

	/**
	 * Returns true if there is a link of the specified association connecting
	 * the given set of objects.
	 */
	public boolean hasLinkBetweenObjects(MAssociation assoc, MObject... objects) {
		MLinkSet linkSet = (MLinkSet) fLinkSets.get(assoc);
		return linkSet.hasLinkBetweenObjects(objects);
	}
	
	/**
	 * Returns true if there is a link of the specified association connecting
	 * the given set of objects with the provided qualifiers.
	 */
	public boolean hasLinkBetweenObjects(MAssociation assoc, List<MObject> objects, List<List<Value>> qualiferValues) {
		MLinkSet linkSet = (MLinkSet) fLinkSets.get(assoc);
		return linkSet.hasLinkBetweenObjects(objects, qualiferValues);
	}
	
	/**
	 * Returns true if there is a link of the specified association connecting
	 * the given set of objects.
	 */
	public boolean hasLinkBetweenObjects(
			MAssociation assoc, 
			Collection<MObject> objects) {
		
		return hasLinkBetweenObjects(assoc, new LinkedList<MObject>(objects));
	}

	/**
	 * Returns all links between the given objects, ignoring possible
	 * qualifier values.
	 * @param assoc
	 * @param asList
	 * @return
	 */
	public Set<MLink> linkBetweenObjects(MAssociation assoc, List<MObject> objects) {
		MLinkSet linkSet = (MLinkSet) fLinkSets.get(assoc);
		return linkSet.linkBetweenObjects(objects);
	}
	
	/**
	 * Returns the link if there is a link connecting the given list of objects,
	 * otherwise null is returned.
	 */
	public MLink linkBetweenObjects(MAssociation assoc, List<MObject> objects, List<List<Value>> qualifierValues) {
		MLinkSet linkSet = (MLinkSet) fLinkSets.get(assoc);
		return linkSet.linkBetweenObjects(objects, qualifierValues);
	}

	/**
	 * Returns true if there is a link of the specified association connecting
	 * the objects in the given sequence.
	 * @throws MSystemException objects do not conform to the association ends.
	 */
	public boolean hasLink(MAssociation assoc, List<MObject> objects, List<List<Value>> qualifierValues)
			throws MSystemException {
		MLinkSet linkSet = fLinkSets.get(assoc);
		
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
	 * TODO
	 * @param object
	 * @return
	 */
	public Set<MObject> getObjectsAffectedByDestruction(MObject object) {
		Set<MObject> result = new HashSet<MObject>();
		
		getObjectsAffectedByDestruction(object, result);
		
		return result;
	}
	

	/**
	 * TODO
	 * @param object
	 * @param result
	 */
	private void getObjectsAffectedByDestruction(
			MObject object, 
			Set<MObject> result) {
		
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
	 * @return Set(MLink) the set of removed links
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

	public class DeleteObjectResult {
		private Set<MLink> removedLinks = new HashSet<MLink>();
		private Set<MObject> removedObjects = new HashSet<MObject>();
		private Set<MObjectState> removedObjectStates = new HashSet<MObjectState>();
		
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
							
							res.getRemovedLinks().addAll(resLinkObject.getRemovedLinks());
							res.getRemovedObjects().addAll(resLinkObject.getRemovedObjects());
							res.getRemovedObjectStates().addAll(resLinkObject.getRemovedObjectStates());
						}
					}
					
					res.getRemovedLinks().addAll(removedLinks);
				}
			}
			
			linkSet.clearCache(obj);
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
		Set<MAssociation> allAssocs = new HashSet<MAssociation>(link.association().getAllParentAssociations());
		allAssocs.add(link.association());
		
		for (MAssociation ass : allAssocs) {
			MLinkSet linkSet = (MLinkSet) fLinkSets.get(ass);
			linkSet.remove(link);
		
			removeLinkFromWholePartGraph(link);
		}
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
	 * Creates and adds a new link to the state.
	 * 
	 * @exception MSystemException
	 *                link invalid or already existing
	 * @return the newly created link.
	 */
	public MLink createLink(MAssociation assoc, List<MObject> objects, 
							List<List<Value>> qualifierValues)
			throws MSystemException {
		MLink link = null;

		validateLinkQualifiers(assoc, qualifierValues);
		
		validateRedefinesForLink(assoc, objects);
		
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
			
			MLinkSet linkSet = (MLinkSet) fLinkSets.get(assoc);
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
						MLink l = (MLink) iter.next();
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
			MLinkSet linkSet = (MLinkSet) fLinkSets.get(assoc);
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
	 * Validates if the given objects can
	 * be linked wrt possible defined redefinitions
	 * @param assoc
	 * @param objects
	 */
	private void validateRedefinesForLink(MAssociation assoc,
			List<MObject> objects) {
		
		if (!assoc.getRedefinedBy().isEmpty()) {
			return;
		}
		
		for (MAssociation redefiningAssoc : assoc.getRedefinedByClosure()) {
			// List<MAssociationEnd> redefiningEnds = redefiningAssoc.getParentAlignedEnds(assoc);
			
		}
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
						
						if (!qualifierValue.type().isSubtypeOf(qualifier.type())) {
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
		
		DeleteObjectResult result = new DeleteObjectResult();
		MLink link = null;
		
		MLinkSet linkSet = linksOfAssociation(assoc);
		link = linkSet.linkBetweenObjects(objects, qualifierValues);

		if (link == null) {
			throw new MSystemException("Link `" + assoc.name() + "' between ("
					+ StringUtil.fmtSeqWithSubSeq(objects, ",", qualifierValues, ",", "{", "}")
					+ ") does not exist.");
		}

		linkSet.remove(link);
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
		
		MLinkObject linkobj = new MLinkObjectImpl(assocClass, name, objects, qualifierValues);

		// Part from createObject method
		MObjectState objState = new MObjectState(linkobj);
		fObjectStates.put(linkobj, objState);
		fClassObjects.put(assocClass, linkobj);
		fObjectNames.put(linkobj.name(), linkobj);
		
		//FIXME: Qualifier!
		// Part from createLink method
		MLinkSet linkSet = fLinkSets.get(assocClass);
		if (linkSet.contains(linkobj))
			throw new MSystemException("Link " + linkobj + " already exists.");
		linkSet.add(linkobj);

		for (MAssociation parent : assocClass.getAllParentAssociations()) {
			linkSet = fLinkSets.get(parent);
			if (linkSet.contains(linkobj))
				throw new MSystemException("Link " + linkobj + " already exists.");
			
			linkSet.add(linkobj);
		}
		
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
	 * 
	 * @param obj
	 * @param srcEnd
	 * @param dstEnd
	 * @return
	 */
	Map<List<Value>,Set<MObject>> getLinkedObjects(MObject obj, MAssociationEnd srcEnd, MAssociationEnd dstEnd) {
		Map<List<Value>, Set<MObject>> res = new HashMap<List<Value>, Set<MObject>>();

		// get association
		MAssociation assoc = dstEnd.association();
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
			
			// add redefining ends
			for (MAssociationEnd redefiningDestEnd : dstEnd.getRedefiningEnds()) {
				MAssociationEnd redefiningSrcEnd = redefiningDestEnd.getAllOtherAssociationEnds().get(0);
				// Add only if src end is generalization relationship with cls of obj
				if (redefiningSrcEnd.cls().isSubClassOf(obj.cls()) || obj.cls().isSubClassOf(redefiningSrcEnd.cls())) {				
					res.putAll(getLinkedObjects(obj, redefiningSrcEnd, redefiningDestEnd));
				}
			}

			return res;
		/*
		// FIXME: Is derived possible with qualifiers?
		} else if (dstEnd.isDerived()) {
			EvalContext ctx = new EvalContext(this, this, this.fSystem.varBindings(), null);
			
			ctx.pushVarBinding("self", obj.value());
        	Value evalRes = dstEnd.getDeriveExpression().eval(ctx);
        	
        	if (evalRes.isUndefined()) {
        		return new ArrayList<MObject>();
        	} else {
        		CollectionValue col = (CollectionValue)evalRes;
        		res = new ArrayList<MObject>(col.size());
        		for (Value v : col) {
        			ObjectValue oVal = (ObjectValue)v;
        			res.add(oVal.value());
        		}
        		
        		return res;
        	}
        } else if (dstEnd.getAllOtherAssociationEnds().size() == 1 && 
        		   dstEnd.getAllOtherAssociationEnds().get(0).isDerived()) {
	       / The opposite side of a derived end of a binary association can be calculated:
	     	   T = 
	     	   T.allInstances()->select(t | t.deriveExpression->includes(self))
	     	   
	     	/
        	EvalContext ctx = new EvalContext(this, this, this.fSystem.varBindings(), null);
						
	     	MClass endClass = dstEnd.cls();
	     	MNavigableElement otherEnd = dstEnd.getAllOtherAssociationEnds().get(0);
	     	StringBuilder query = new StringBuilder();
	     	query.append(endClass.name()).append(".allInstances()->select(self | ");
	     	otherEnd.getDeriveExpression().toString(query);
	     	query.append("->includes(sourceObject)");
	     	query.append(")");
	     	
	     	ctx.pushVarBinding("sourceObject", obj.value());
	     	
	     	Expression linkExpression = OCLCompiler.compileExpression(
	     			ctx.postState().system().model(), 
	     			query.toString(), 
	     			"opposite derived end", 
	     			new PrintWriter(Log.out()),
	     			ctx.varBindings());
	     	
	     	if (linkExpression == null) {
	     		Log.error("Calculated opposite derive expression had compile errors!");
	     		return new ArrayList<MObject>();
	     	}
	     	
	     	Value expResult; 
	     	expResult = linkExpression.eval(ctx);
	     	
	     	if (expResult.isUndefined()) {
	     		return new ArrayList<MObject>();
	     	} else {
        		CollectionValue col = (CollectionValue)expResult;
        		res = new ArrayList<MObject>(col.size());
        		for (Value v : col) {
        			ObjectValue oVal = (ObjectValue)v;
        			res.add(oVal.value());
        		}
        		
        		return res;
        	}
	    */
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
				MLinkEnd linkEndDst = link.linkEnd(dstEnd);
				MLinkEnd linkEndSrc = link.linkEnd(srcEnd);
				
				if (!res.containsKey(linkEndSrc.getQualifierValues()))
					res.put(linkEndSrc.getQualifierValues(), new HashSet<MObject>());
				
				res.get(linkEndSrc.getQualifierValues()).add(linkEndDst.object());
			}
			
			return res;
		}
	}
	
	/**
	 * Returns a list of objects at <code>dstEnd</code> which are linked to
	 * <code>obj</code> at srcEnd with the given qualifier values.
	 * 
	 * @param obj The source object
	 * @param srcEnd The association end to navigate from
	 * @param dstEnd The association end to navigate to
	 * @param qualifierValues Possible qualifier values. Can be <code>null</code>.
	 * 
	 * @return List(MObject)
	 */
	List<MObject> getLinkedObjects(MObject obj, MAssociationEnd srcEnd, MAssociationEnd dstEnd, List<Value> qualifierValues) {
		List<MObject> res = new ArrayList<MObject>();

		// get association
		MAssociation assoc = dstEnd.association();
		MLinkSet linkSet;
		
		if (dstEnd.isUnion()) {			
			Set<MObject> tmpResult = new HashSet<MObject>();
			
			// add subsetting ends
			for (MAssociationEnd subsettingDestEnd : dstEnd.getSubsettingEnds()) {
				// TODO: n-ary!
				MAssociationEnd subsettingSrcEnd = subsettingDestEnd.getAllOtherAssociationEnds().get(0);
				// Add only if src end is generalization relationship with cls of obj
				if (subsettingSrcEnd.cls().isSubClassOf(obj.cls()) || obj.cls().isSubClassOf(subsettingSrcEnd.cls())) {
					tmpResult.addAll(getLinkedObjects(obj, subsettingSrcEnd, subsettingDestEnd, qualifierValues));
				}
			}
			
			// add redefining ends
			for (MAssociationEnd redefiningDestEnd : dstEnd.getRedefiningEnds()) {
				MAssociationEnd redefiningSrcEnd = redefiningDestEnd.getAllOtherAssociationEnds().get(0);
				// Add only if src end is generalization relationship with cls of obj
				if (redefiningSrcEnd.cls().isSubClassOf(obj.cls()) || obj.cls().isSubClassOf(redefiningSrcEnd.cls())) {				
					tmpResult.addAll(getLinkedObjects(obj, redefiningSrcEnd, redefiningDestEnd, qualifierValues));
				}
			}
			
			res.addAll(tmpResult);
			return res;
		} else if (dstEnd.isDerived()) {
			EvalContext ctx = new EvalContext(this, this, this.fSystem.varBindings(), null);
			
			ctx.pushVarBinding("self", obj.value());
        	Value evalRes = dstEnd.getDeriveExpression().eval(ctx);
        	
        	if (evalRes.isUndefined()) {
        		return new ArrayList<MObject>();
        	} else {
        		CollectionValue col = (CollectionValue)evalRes;
        		res = new ArrayList<MObject>(col.size());
        		for (Value v : col) {
        			ObjectValue oVal = (ObjectValue)v;
        			res.add(oVal.value());
        		}
        		
        		return res;
        	}
        } else if (dstEnd.getAllOtherAssociationEnds().size() == 1 && 
        		   dstEnd.getAllOtherAssociationEnds().get(0).isDerived()) {
	       /* The opposite side of a derived end of a binary association can be calculated:
	     	   T = 
	     	   T.allInstances()->select(t | t.deriveExpression->includes(self))
	     	   
	     	*/
        	EvalContext ctx = new EvalContext(this, this, this.fSystem.varBindings(), null);
						
	     	MClass endClass = dstEnd.cls();
	     	MNavigableElement otherEnd = dstEnd.getAllOtherAssociationEnds().get(0);
	     	StringBuilder query = new StringBuilder();
	     	query.append(endClass.name()).append(".allInstances()->select(self | ");
	     	otherEnd.getDeriveExpression().toString(query);
	     	query.append("->includes(sourceObject)");
	     	query.append(")");
	     	
	     	ctx.pushVarBinding("sourceObject", obj.value());
	     	
	     	Expression linkExpression = OCLCompiler.compileExpression(
	     			ctx.postState().system().model(), 
	     			query.toString(), 
	     			"opposite derived end", 
	     			new PrintWriter(Log.out()),
	     			ctx.varBindings());
	     	
	     	if (linkExpression == null) {
	     		Log.error("Calculated opposite derive expression had compile errors!");
	     		return new ArrayList<MObject>();
	     	}
	     	
	     	Value expResult; 
	     	expResult = linkExpression.eval(ctx);
	     	
	     	if (expResult.isUndefined()) {
	     		return new ArrayList<MObject>();
	     	} else {
        		CollectionValue col = (CollectionValue)expResult;
        		res = new ArrayList<MObject>(col.size());
        		for (Value v : col) {
        			ObjectValue oVal = (ObjectValue)v;
        			res.add(oVal.value());
        		}
        		
        		return res;
        	}
	     	
	     	
		} else {
			// get link set for association
			linkSet = fLinkSets.get(assoc);
		
		
			// if link set is empty return empty result list
			if (Log.isTracing())
				Log.trace(this, "linkSet size of association `" + assoc.name() + "' = "
						  + linkSet.size());
			
			if (linkSet.size() == 0)
				return res;
	
			// select links with srcEnd == obj
			Set<MLink> links = linkSet.select(srcEnd, obj, qualifierValues);
			
			if (Log.isTracing())
				Log.trace(this, "linkSet.select for object `" + obj + "', size = " + links.size());
	
			// project tuples to destination end component
			for (MLink link : links) {
				MLinkEnd linkEnd = link.linkEnd(dstEnd);
				res.add(linkEnd.object());
			}
			
			return res;
		}
	}

	/**
	 * Returns a list of objects at <code>dst</code> which are connected to this
	 * object at <code>src</code>. This is needed for navigation.
	 * 
	 * @return List(MObject)
	 */
	public List<MObject> getNavigableObjects(MObject obj, MNavigableElement src, MNavigableElement dst, List<Value> qualifierValues) {
		ArrayList<MObject> res = new ArrayList<MObject>();

		if (dst.isUnion()) {
			Set<MObject> tmpResult = new HashSet<MObject>();
			// TODO: n-ary!

			// add subsetting ends
			for (MAssociationEnd subsettingDestEnd : dst.getSubsettingEnds()) {
				MAssociationEnd subsettingSrcEnd = subsettingDestEnd
						.getAllOtherAssociationEnds().get(0);
				// Check only if src end is generalization relationship with cls
				// of obj
				if (subsettingSrcEnd.cls().isSubClassOf(obj.cls())
						|| obj.cls().isSubClassOf(subsettingSrcEnd.cls())) {
					tmpResult.addAll(getLinkedObjects(obj, subsettingSrcEnd,
							subsettingDestEnd, qualifierValues));
				}
			}

			// add redefining ends
			for (MAssociationEnd redefiningDestEnd : dst.getRedefiningEnds()) {
				MAssociationEnd redefiningSrcEnd = redefiningDestEnd
						.getAllOtherAssociationEnds().get(0);
				// Check only if src end is generalization relationship with cls
				// of obj
				if (redefiningSrcEnd.cls().isSubClassOf(obj.cls())
						|| obj.cls().isSubClassOf(redefiningSrcEnd.cls())) {
					tmpResult.addAll(getLinkedObjects(obj, redefiningSrcEnd,
							redefiningDestEnd, qualifierValues));
				}
			}

			res.addAll(tmpResult);
		} else {
			// get association
			MAssociation assoc = dst.association();

			// get link set for association
			MLinkSet linkSet = fLinkSets.get(assoc);

			// if link set is empty return empty result list
			if (Log.isTracing()) {
				Log.trace(this, "linkSet size of association `" + assoc.name()
						+ "' = " + linkSet.size());
			}

			if (linkSet.size() == 0)
				return res;

			// navigation from a linkobject
			if (src instanceof MAssociationClass) {
				// TODO: Why is navigation from AssociationClass to
				// AssociationClass not allowed?
				if (dst instanceof MAssociationClass) {
					throw new RuntimeException("Wrong navigation expression.");
				}
				MLinkEnd linkEnd = ((MLinkObject) obj)
						.linkEnd((MAssociationEnd) dst);
				res.add(linkEnd.object());
			} else {
				MAssociationEnd srcEnd = (MAssociationEnd) src;
				// select links with srcEnd == obj
				Set<MLink> links = linkSet.select(srcEnd, obj, qualifierValues);

				if (Log.isTracing()) {
					Log.trace(this, "linkSet.select for object `" + obj
							+ "', size = " + links.size());
				}

				// navigation to a linkobject
				if (dst instanceof MAssociationClass) {
					for (MLink link : links) {
						res.add((MObject) link);
					}
				} else {
					MAssociationEnd dstEnd = (MAssociationEnd) dst;
					// project tuples to destination end component
					for (MLink link : links) {
						MLinkEnd linkEnd = link.linkEnd(dstEnd);
						res.add(linkEnd.object());
					}
				}
			}
		}

		return res;
	}

	/**
	 * Checks for a valid system state. Returns true if all constraints hold for
	 * all objects. Prints result of subexpressions for failing constraints to
	 * the log if traceEvaluation is true. - Checks the whole/part hierarchy.
	 */
	public boolean check(PrintWriter out, boolean traceEvaluation,
			boolean showDetails, boolean allInvariants, List<String> invNames) {
		boolean valid = true;
		Evaluator evaluator = new Evaluator();
		MModel model = fSystem.model();

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
		
		if (allInvariants) {
			// get all invariants (loaded and normal classinvariants)
			// it doesn't matter if specific invariants are given or not

			// get all loaded Invariants (generator)
			for (GFlaggedInvariant flaggedInv : fSystem.generator().flaggedInvariants()) {
				MClassInvariant inv = flaggedInv.classInvariant();
				Expression expr = inv.expandedExpression();
				
				// sanity check
				if (!expr.type().isBoolean())
					throw new RuntimeException("Expression " + expr
							+ "has type " + expr.type()
							+ ", expected boolean type");
				if (flaggedInv.negated()) {
					try {
						Expression[] args = { expr };
						Expression expr1 = ExpStdOp.create("not", args);
						expr = expr1;
					} catch (ExpInvalidException e) {
						// TODO: Should this be ignored or is this a fatal
						// error?
					}
					negatedList.add(Boolean.TRUE);
				} else {
					negatedList.add(Boolean.FALSE);
				}
				invList.add(inv);
				exprList.add(expr);
				out.println("GeneratorInvariants: " + inv);
			}
		} else if (invNames.isEmpty()) {
			// get all invariants
			for(MClassInvariant inv : model.classInvariants()) {
				Expression expr = inv.expandedExpression();
				// sanity check
				if (!expr.type().isBoolean())
					throw new RuntimeException("Expression " + expr
							+ "has type " + expr.type()
							+ ", expected boolean type");
				invList.add(inv);
				negatedList.add(Boolean.FALSE);
				exprList.add(expr);
			}
		} else {
			// get only specified invariants
			for (String invName : invNames) {
				MClassInvariant inv = model.getClassInvariant(invName);
				if (inv != null) {
					Expression expr = inv.expandedExpression();
					// sanity check
					if (!expr.type().isBoolean())
						throw new RuntimeException("Expression " + expr
								+ "has type " + expr.type()
								+ ", expected boolean type");
					invList.add(inv);
					negatedList.add(Boolean.FALSE);
					exprList.add(expr);
				} else {
					GFlaggedInvariant flaggedInv = fSystem.generator()
							.flaggedInvariant(invName);
					inv = flaggedInv.classInvariant();
					if (inv != null) {
						Expression expr = inv.expandedExpression();
						// sanity check
						if (!expr.type().isBoolean())
							throw new RuntimeException("Expression " + expr
									+ "has type " + expr.type()
									+ ", expected boolean type");
						if (flaggedInv.negated()) {
							try {
								Expression[] args = { expr };
								Expression expr1 = ExpStdOp.create("not", args);
								expr = expr1;
							} catch (ExpInvalidException e) {
								// TODO: Should this be ignored or is this a
								// fatal error?
							}
							negatedList.add(Boolean.TRUE);
						} else {
							negatedList.add(Boolean.FALSE);
						}
						invList.add(inv);
						exprList.add(expr);
					}
				}
			}
		}

		// start (possibly concurrent) evaluation
		Queue resultValues = evaluator.evalList(Options.EVAL_NUMTHREADS,
				exprList, this);

		// receive results
		for (int i = 0; i < exprList.size(); i++) {
			MClassInvariant inv = (MClassInvariant) invList.get(i);
			numChecked++;
			String msg = "checking invariant (" + numChecked + ") `"
					+ inv.cls().name() + "::" + inv.name() + "': ";
			out.print(msg); // + inv.bodyExpression());
			out.flush();
			try {
				Value v = (Value) resultValues.get();

				// if value 'v' is null, the invariant can not be evaluated,
				// therefor
				// it is N/A (not available).
				if (v == null) {
					out.println("N/A");
					// if there is a value, the invariant can allways be
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
							Expression expr = (Expression) exprList.get(i);
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
		boolean res = true;
		out.println("checking structure...");
		out.flush();
		// check the whole/part hierarchy
		if (!checkWholePartLink(out)) {
			res = false;
		}
		// check all associations
		for (MAssociation assoc : fSystem.model().associations()) {
			if (assoc.associationEnds().size() != 2) {
				// check for n-ary links
				if (!naryAssociationsAreValid(out, assoc))
					res = false;
			} else {
				// check both association ends
				Iterator<MAssociationEnd> it2 = assoc.associationEnds().iterator();
				MAssociationEnd aend1 = it2.next();
				MAssociationEnd aend2 = it2.next();

				if (!validateBinaryAssociations(out, assoc, aend1, aend2)
						|| !validateBinaryAssociations(out, assoc, aend2, aend1))
					res = false;
			}
		}
		// out.println("checking link cardinalities, done.");
		out.flush();
		return res;
	}

	private boolean naryAssociationsAreValid(PrintWriter out, MAssociation assoc) {
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
					out
							.println("Multiplicity constraint violation in association `"
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
	Bag<MObject[]> getCrossProductOfInstanceSets(List<MClass> classes) {
		Bag<MObject[]> bag = new HashBag<MObject[]>();
		insertAllNMinusOneTuples(bag, (MClass[]) classes
				.toArray(new MClass[classes.size()]), 0, new MObject[0]);
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
			MAssociationEnd aend1, MAssociationEnd aend2) {
		boolean valid = true;

		// for each object of the association end's type get
		// the number of links in which the object participates
		MClass cls = aend1.cls();
		Set<MObject> objects = objectsOfClassAndSubClasses(cls);

		for (MObject obj : objects) {
			Map<List<Value>,Set<MObject>> linkedObjects = getLinkedObjects(obj, aend1, aend2);
			
			if (linkedObjects.size() == 0 && !aend2.multiplicity().contains(0)) {
				reportMultiplicityViolation(out, assoc, aend1, aend2, obj, null);
				valid = false;
				continue;
			}
			
			for(Map.Entry<List<Value>, Set<MObject>> entry : linkedObjects.entrySet()) {
				if (!aend2.multiplicity().contains(entry.getValue().size())) {
					reportMultiplicityViolation(out, assoc, aend1, aend2, obj, entry);
					valid = false;
				}
				
				if (aend1.getSubsettedEnds().size() > 0) {
					if (!validateSubsets(out, obj, entry.getKey(), entry.getValue(), aend1))
						valid = false;
				}
	
				if (aend1.getRedefiningEnds().size() > 0) {
					if (!validateRedefineBinary(out, obj, entry.getKey(), entry.getValue(), aend1))
						valid = false;
				}
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

	
	private boolean validateRedefineBinary(PrintWriter out, MObject obj,
			List<Value> qualifierValues, Set<MObject> linkedObjects,
			MAssociationEnd aend) {
		boolean valid = true;
		List<MAssociationEnd> endsToCheck = new ArrayList<MAssociationEnd>();
		
		// Find all redefining ends, which are of the same type or a subtype of obj 
		for (MAssociationEnd end : aend.getRedefiningEnds()) {
			if (aend.cls().type().isSubtypeOf(obj.type())) {
				endsToCheck.add(end);
			}
		}
		
		// All objects linked with obj must conform to the type at the redefining end
		for (MAssociationEnd end : endsToCheck) {
			// Get the type, the objects must conform to			
			Type destType = aend.getAllOtherAssociationEnds().get(0).cls().type();
			
			for (MObject dstObj : linkedObjects) {
				if (!dstObj.type().isSubtypeOf(destType)) {
					out.println("constraint {redefines " + aend.nameAsRolename() + "} on association end " 
							+ end.nameAsRolename() + ":" + end.association().name() + " is violated!");
					out.println("Object " + obj.name() + " is linked with object " + dstObj.name() + 
							    " whichs type does not conform to the type " + destType.shortName());
					valid = false;
				}
			}
		}
		
		return valid;
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
			
			List<MObject> parentObjectList = getLinkedObjects(obj, subEnd1, subEnd2, qualifierValues);
			
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
	 * class. Checks whether the name is used in this state. BigFix for USE
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
}