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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tzi.use.config.Options;
import org.tzi.use.gen.model.GFlaggedInvariant;
import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.graph.DirectedGraphBase;
import org.tzi.use.uml.mm.MAggregationKind;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.ExpStdOp;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.BooleanValue;
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
	MSystemState(String name, MSystemState x) {
		fName = name;
		fSystem = x.fSystem;

		// deep copy of object states
		fObjectStates = new HashMap<MObject, MObjectState>();
		
		for (Map.Entry<MObject, MObjectState> e : x.fObjectStates.entrySet()) {
			fObjectStates.put(e.getKey(), new MObjectState((MObjectState) e
					.getValue()));
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
	 * Returns the set of objects of class <code>cls</code> currently existing
	 * in this state.
	 * 
	 * @return Set(MObject)
	 */
	public Set<MObject> objectsOfClass(MClass cls) {
		Set<MObject> res = new HashSet<MObject>();
		res.addAll(fClassObjects.get(cls));
		return res;
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
		children.add(cls);

		for (MClass c : children) {
			res.addAll(objectsOfClass(c));
		}
		
		return res;
	}

	/**
	 * Returns the object with the specified name.
	 * 
	 * @return null if no object with the specified name exists.
	 */
	public MObject objectByName(String name) {
		//FIXME: Use Hashing for faster access!
		// this is a slow linear search over all objects
		for (MObject obj : allObjects()) {
			if (obj.name().equals(name))
				return obj;
		}
		
		return null;
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
	public boolean hasLinkBetweenObjects(MAssociation assoc, MObject[] objects) {
		MLinkSet linkSet = (MLinkSet) fLinkSets.get(assoc);
		return linkSet.hasLinkBetweenObjects(objects);
	}

	/**
	 * Returns the link if there is a link connecting the given set of objects,
	 * otherwise null is returend.
	 */
	public MLink linkBetweenObjects(MAssociation assoc, Set<MObject> objects) {
		MLinkSet linkSet = (MLinkSet) fLinkSets.get(assoc);
		return linkSet.linkBetweenObjects(objects);
	}

	/**
	 * Returns true if there is a link of the specified association connecting
	 * the objects in the given sequence.
	 */
	public boolean hasLink(MAssociation assoc, List<MObject> objects)
			throws MSystemException {
		MLinkSet linkSet = fLinkSets.get(assoc);
		
		return linkSet.hasLink(objects);
	}

	/**
	 * Creates and adds a new object to the state. The name of the object may be
	 * null in which case a unique name is automatically generated.
	 * 
	 * @return the created object.
	 */
	public MObject createObject(MClass cls, String name)
			throws MSystemException {
		// checks if cls is a association class, if yes then throw an exception,
		// because this should not be allowed.
		if (cls instanceof MAssociationClass) {
			throw new MSystemException(
					"Creation of a linkobject is not allowed with the command create. \n"
							+ "Use 'create ... between ...' or 'insert' instead.");
		}
		// create new object and initial state
		MObject obj = fSystem.createObject(cls, name);
		MObjectState objState = new MObjectState(obj);
		fObjectStates.put(obj, objState);
		fClassObjects.put(cls, obj);
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
		fSystem.addObject(obj);
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
		}
		
		res.getRemovedObjects().add(obj);
		res.getRemovedObjectStates().add(fObjectStates.get(obj));
		fObjectStates.remove(obj);
		fClassObjects.remove(objClass, obj);
		fSystem.deleteObject(obj);
		return res;
	}

	private void auxDeleteLink(MLink link) {
		MLinkSet linkSet = (MLinkSet) fLinkSets.get(link.association());
		linkSet.remove(link);
	}

	/**
	 * Creates and adds a new link to the state.
	 * 
	 * @exception MSystemException
	 *                link invalid or already existing
	 * @return the newly created link.
	 */
	public MLink createLink(MAssociation assoc, List<MObject> objects)
			throws MSystemException {
		MLink link = null;

		// checks if assoc is an associationclass
		if (assoc instanceof MAssociationClass) {
			// specifies if there is already an existing linkobject of this
			// association class between the objects
			if (hasLinkBetweenObjects(assoc, objects.toArray(new MObject[objects.size()]))) {
				throw new MSystemException(
						"Cannot insert two linkobjects of the same type"
								+ " between one set of objects!");
			}

			String name = fSystem.uniqueObjectNameForClass(assoc.name());
			// Log.println( "Information: The name '" + name +
			// "' was automatically generated for the "
			// + "linkobject." );
			// creates a linkobject with a generated name
			link = createLinkObject((MAssociationClass) assoc, name, objects);
		} else if ((assoc.aggregationKind() == MAggregationKind.AGGREGATION)
				|| (assoc.aggregationKind() == MAggregationKind.COMPOSITION)) {
			MLinkSet linkSet = (MLinkSet) fLinkSets.get(assoc);
			link = new MLinkImpl(assoc, objects);
			if (linkSet.contains(link))
				throw new MSystemException("Link `" + assoc.name()
						+ "' between ("
						+ StringUtil.fmtSeq(objects.iterator(), ",")
						+ ") already exist.");
			// The graph to store the information of the whole/part hierachy
			DirectedGraph<MObject, MWholePartLink> fWholePartLinkGraph = getWholePartLinkGraph();
			MWholePartLink wholePartLink = new MWholePartLinkImpl(link);
			MObject source = null;
			MObject target = null;
			source = (MObject) wholePartLink.source();
			target = (MObject) wholePartLink.target();
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
							Log
									.warn("Warning: Insert has resulted in two aggregates for object `"
											+ target.name()
											+ "'. Object `"
											+ target.name()
											+ "' is already component of another object.");
						}
					}
				}
			}
			// check for cycles that might be occured when adding the new
			// whole/part link
			if (fWholePartLinkGraph.existsPath(target, source))
				Log
						.warn("Warning: Insert has resulted in a cycle in the part-whole hierarchy. Object `"
								+ source.name()
								+ "' is a direct or indirect part of `"
								+ target.name() + "'.");
			// silently ignore duplicates
			linkSet.add(link);
		} else {
			// create a normal link
			link = new MLinkImpl(assoc, objects);
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
	 * The graph to store the information of the whole/part hierachy.
	 */
	private DirectedGraph<MObject, MWholePartLink> getWholePartLinkGraph() {
		DirectedGraph<MObject, MWholePartLink> ret = new DirectedGraphBase<MObject, MWholePartLink>();
		// Obtaining whole/part links from all links of the system state in
		// fLinkSets		

		MWholePartLink wholePartLink = null;
		MObject source = null;
		MObject target = null;
		
		for (MAssociation assocTemp : fLinkSets.keySet()) {
			if ((assocTemp.aggregationKind() == MAggregationKind.AGGREGATION)
					|| (assocTemp.aggregationKind() == MAggregationKind.COMPOSITION)) {
				MLinkSet linkSetTmp = fLinkSets.get(assocTemp);
				
				for (MLink link : linkSetTmp.links()) {
					try {
						wholePartLink = new MWholePartLinkImpl(link);
					} catch (MSystemException e) {
						Log.println(e.toString());
					}
					source = wholePartLink.source();
					target = wholePartLink.target();
					ret.add(source);
					ret.add(target);
					ret.addEdge(wholePartLink);
				}
			}
		}
		return ret;
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
	 * Deletes a link from the state.
	 */
	// TODO: Was geschieht mit Set von deleteObject
	public void deleteLink(MLink link) {
		auxDeleteLink(link);

		if (link instanceof MLinkObject) {
			auxDeleteObject((MLinkObject) link);
		}
	}

	/**
	 * Deletes a link from the state. The link is indirectly specified by the
	 * association and objects.
	 * 
	 * @exception MSystemException
	 *                link does not exist
	 * @return the removed link.
	 */
	public DeleteObjectResult deleteLink(MAssociation assoc, List<MObject> objects)
			throws MSystemException {
		
		DeleteObjectResult result = new DeleteObjectResult();
		MLink link = null;
		MLinkSet linkSet = linksOfAssociation(assoc);

		for (MLink l : linkSet.links()) {
			if (l.linkedObjects().equals(new HashSet<MObject>(objects))) {
				link = l;
				break;
			}
		}

		if (link == null) {
			throw new MSystemException("Link `" + assoc.name() + "' between ("
					+ StringUtil.fmtSeq(objects.iterator(), ",")
					+ ") does not exist.");
		}

		linkSet.remove(link);
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
			String name, List<MObject> objects) throws MSystemException {
		
		if (objectByName(name) != null) {
			throw new MSystemException("An object with name `" + name
					+ "' already exists.");
		}
		
		MLinkObject linkobj = new MLinkObjectImpl(assocClass, name, objects);

		// Part from createObject method
		MObjectState objState = new MObjectState(linkobj);
		fObjectStates.put(linkobj, objState);
		fClassObjects.put(assocClass, linkobj);

		// Part from createLink method
		MLinkSet linkSet = fLinkSets.get(assocClass);
		if (linkSet.contains(linkobj))
			throw new MSystemException("Link " + linkobj + " already exists.");
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
	 * Returns a list of objects at <code>dstEnd</code> which are linked to
	 * <code>obj</code> at srcEnd.
	 * 
	 * @return List(MObject)
	 */
	List<MObject> getLinkedObjects(MObject obj, MAssociationEnd srcEnd,
			MAssociationEnd dstEnd) {
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
				tmpResult.addAll(getLinkedObjects(obj, subsettingSrcEnd, subsettingDestEnd));
			}
			
			// add redefining ends
			for (MAssociationEnd redefiningDestEnd : dstEnd.getRedefiningEnds()) {
				MAssociationEnd redefiningSrcEnd = redefiningDestEnd.getAllOtherAssociationEnds().get(0);
				tmpResult.addAll(getLinkedObjects(obj, redefiningSrcEnd, redefiningDestEnd));
			}
			
			res.addAll(tmpResult);
			return res;
		} else {
			// get link set for association
			linkSet = fLinkSets.get(assoc);
		
		
			// if link set is empty return empty result list
			Log.trace(this, "linkSet size of association `" + assoc.name() + "' = "
					+ linkSet.size());
			if (linkSet.size() == 0)
				return res;
	
			// select links with srcEnd == obj
			Set<MLink> links = linkSet.select(srcEnd, obj);
			Log.trace(this, "linkSet.select for object `" + obj + "', size = "
					+ links.size());
	
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
	public List<MObject> getNavigableObjects(MObject obj, MNavigableElement src,
			MNavigableElement dst) {
		ArrayList<MObject> res = new ArrayList<MObject>();

		if (dst.isUnion()) {			
			Set<MObject> tmpResult = new HashSet<MObject>();
			
			// add subsetting ends
			for (MAssociationEnd subsettingDestEnd : dst.getSubsettingEnds()) {
				
				// TODO: n-ary!
				MAssociationEnd subsettingSrcEnd = subsettingDestEnd.getAllOtherAssociationEnds().get(0);
				tmpResult.addAll(getLinkedObjects(obj, subsettingSrcEnd, subsettingDestEnd));
			}
			
			// add redefining ends
			for (MAssociationEnd redefiningDestEnd : dst.getRedefiningEnds()) {
				MAssociationEnd redefiningSrcEnd = redefiningDestEnd.getAllOtherAssociationEnds().get(0);
				tmpResult.addAll(getLinkedObjects(obj, redefiningSrcEnd, redefiningDestEnd));
			}
			
			res.addAll(tmpResult);
		} else {
			// get association
			MAssociation assoc = dst.association();
	
			// get link set for association
			MLinkSet linkSet = fLinkSets.get(assoc);
	
			// if link set is empty return empty result list
			Log.trace(this, "linkSet size of association `" + assoc.name() + "' = "
					+ linkSet.size());
	
			if (linkSet.size() == 0)
				return res;
	
			// navigation from a linkobject
			if (src instanceof MAssociationClass) {
				// TODO: Why is navigation from AssociationClass to AssociationClass not allowed?
				if (dst instanceof MAssociationClass) {
					throw new RuntimeException("Wrong navigation expression.");
				}
				MLinkEnd linkEnd = ((MLinkObject)obj).linkEnd((MAssociationEnd) dst);
				res.add(linkEnd.object());
			} else {
				MAssociationEnd srcEnd = (MAssociationEnd) src;
				// select links with srcEnd == obj
				Set<MLink> links = linkSet.select(srcEnd, obj);
				Log.trace(this, "linkSet.select for object `" + obj + "', size = "
						+ links.size());
	
				// navigation to a linkobject
				if (dst instanceof MAssociationClass) {
					for (MLink link : links) {
						res.add((MObject)link);					
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
				System.out.println("GeneratorInvariants: " + inv);
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
				+ ((numChecked == 1) ? "" : "s") + " in " + timeStr + ", "
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

				if (!binaryAssociationsAreValid(out, assoc, aend1, aend2)
						|| !binaryAssociationsAreValid(out, assoc, aend2, aend1))
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

	private boolean binaryAssociationsAreValid(PrintWriter out,
			MAssociation assoc, MAssociationEnd aend1, MAssociationEnd aend2) {
		boolean valid = true;

		// for each object of the association end's type get
		// the number of links in which the object participates
		MClass cls = aend1.cls();
		Set<MObject> objects = objectsOfClassAndSubClasses(cls);

		for (MObject obj : objects) {
			List<MObject> objList = getLinkedObjects(obj, aend1, aend2);
			int n = objList.size();
			if (!aend2.multiplicity().contains(n)) {
				out.println("Multiplicity constraint violation in association `"
							+ assoc.name() + "':");
				out.println("  Object `" + obj.name() + "' of class `"
						+ obj.cls().name() + "' is connected to " + n
						+ " object" + ((n == 1) ? "" : "s") + " of class `"
						+ aend2.cls().name() + "'");
				out.println("  but the multiplicity is specified as `"
						+ aend2.multiplicity() + "'.");
				valid = false;
			}
			
			if (aend1.getSubsettedEnds().size() > 0) {
				if (!checkSubsets(out, obj, objList, aend1))
					valid = false;
			}

			if (aend1.getRedefiningEnds().size() > 0) {
				if (!checkRedefineBinary(out, obj, objList, aend1))
					valid = false;
			}
		}
		
		return valid;
	}

	
	private boolean checkRedefineBinary(PrintWriter out, MObject obj, List<MObject> linkedObjects, MAssociationEnd aend) {
		boolean valid = true;
		List<MAssociationEnd> endsToCheck = new ArrayList<MAssociationEnd>();
		
		// Find all redefining ends, which are of the same type or a subtype of obj 
		for (MAssociationEnd end : aend.getRedefiningEnds()) {
			if (aend.getType(obj.type(), aend.getAllOtherAssociationEnds().get(0)).isSubtypeOf(obj.type())) {
				endsToCheck.add(end);
			}
		}
		
		// All objects linked with obj must conform to the type at the redefining end
		for (MAssociationEnd end : endsToCheck) {
			// Get the type, the objects must conform to			
			Type destType = aend.getAllOtherAssociationEnds().get(0).getType(obj.type(), aend);
			
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
	
	private boolean checkSubsets(PrintWriter out, MObject obj, List<MObject> linkedObjects, MAssociationEnd aend) {
		boolean valid = true;
		
		for (MAssociationEnd subEnd1 : aend.getSubsettedEnds()) {
			List<MAssociationEnd> ends = subEnd1.getAllOtherAssociationEnds();
			
			// TODO: n-Ary
			assert ends.size() == 1;
			MAssociationEnd subEnd2 = ends.get(0);
			
			List<MObject> parentObjectList = getLinkedObjects(obj, subEnd1, subEnd2);
			
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

}