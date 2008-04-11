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

$Id$

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
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MAggregationKind;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.graph.DirectedGraphBase;
import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.ExpStdOp;
import org.tzi.use.uml.ocl.expr.Expression;
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
 * A system state represents a valid instance of a model. It contains
 * a set of objects and links connecting objects. Methods allow
 * manipulation and querying of objects and links.
 *
 * @version     $ProjectVersion: 2-3-1-release.3 $
 * @author      Mark Richters 
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
    private Map fObjectStates;  // (MObject -> MObjectState)
    
    /**
     * The set of objects partitioned by class. Must be kept in sync
     * with fObjectStates.
     */
    private MultiMap fClassObjects; // (MClass -> list of MObject)

    /**
     * The set of all links partitioned by association.
     */
    private Map fLinkSets;  // (MAssociation -> MLinkSet)


    /**
     * Creates a new system state with no objects and links.
     */
    MSystemState(String name, MSystem system) {
        fName = name;
        fSystem = system;
        fObjectStates = new HashMap();
        fClassObjects = new HashMultiMap();
        fLinkSets = new HashMap();

        // create empty link sets
        Iterator it = fSystem.model().associations().iterator();
        while (it.hasNext()) {
            MAssociation assoc = (MAssociation) it.next();
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
        fObjectStates = new HashMap();
        Iterator it = x.fObjectStates.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            fObjectStates.put(e.getKey(), new MObjectState((MObjectState) e.getValue()));
        }

        fClassObjects = new HashMultiMap();
        fClassObjects.putAll(x.fClassObjects);

        fLinkSets = new HashMap();
        it = x.fLinkSets.entrySet().iterator();
        while (it.hasNext() ) {
            Map.Entry e = (Map.Entry) it.next();
            fLinkSets.put(e.getKey(), new MLinkSet((MLinkSet) e.getValue()));
        }
    }

    /**
     * Returns the name of this state. The name is unique for
     * different states.
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
    public Set allObjects() {
        return fObjectStates.keySet();
    }

    /**
     * Returns the set of objects of class <code>cls</code> currently
     * existing in this state.
     *
     * @return Set(MObject) 
     */
    public Set objectsOfClass(MClass cls) {
        Set res = new HashSet();
        res.addAll(fClassObjects.get(cls));
        return res;
    }

    /*
     * Returns the set of objects of class <code>cls</code> and all of
     * its subclasses.
     *
     * @return Set(MObject) 
     */
    public Set objectsOfClassAndSubClasses(MClass cls) {
        Set res = new HashSet();
        Set children = cls.allChildren();
        children.add(cls);

        Iterator it = children.iterator();
        while (it.hasNext() ) {
            MClass c = (MClass) it.next();
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
        // this is a slow linear search over all objects
        Set allObjects = allObjects();
        Iterator it = allObjects.iterator();
        while (it.hasNext() ) {
            MObject obj = (MObject) it.next();
            if (obj.name().equals(name) )
                return obj;
        }
        return null;
    }

    /**
     * Returns the set of all links in this state.
     *
     * @return Set(MLink)
     */
    public Set allLinks() {
        Set res = new HashSet();
        Iterator it = fLinkSets.values().iterator();
        while (it.hasNext() ) {
            MLinkSet ls = (MLinkSet) it.next();
            res.addAll(ls.links());
        }
        return res;
    }

    /**
     * Returns the set of links of the specified association in this
     * state.  
     */
    public MLinkSet linksOfAssociation(MAssociation assoc) {
        return (MLinkSet) fLinkSets.get(assoc);
    }

    /**
     * Returns true if there is a link of the specified association
     * connecting the given set of objects.
     */
    public boolean hasLinkBetweenObjects(MAssociation assoc, MObject[] objects) {
        MLinkSet linkSet = (MLinkSet) fLinkSets.get(assoc);
        return linkSet.hasLinkBetweenObjects(objects);
    }
    
    /**
     * Returns the link if there is a link connecting the given set of
     * objects, otherwise null is returend.  
     */
    public MLink linkBetweenObjects(MAssociation assoc, Set objects) {
        MLinkSet linkSet = (MLinkSet) fLinkSets.get(assoc);
        return linkSet.linkBetweenObjects(objects);
    }
    
    /**
     * Returns true if there is a link of the specified association
     * connecting the objects in the given sequence.
     */
    public boolean hasLink(MAssociation assoc, List objects)
        throws MSystemException {
        MLinkSet linkSet = (MLinkSet) fLinkSets.get(assoc);
        return linkSet.hasLink(objects);
    }

    /**
     * Creates and adds a new object to the state. The name of the
     * object may be null in which case a unique name is automatically
     * generated.
     *
     * @return the created object.
     */
    public MObject createObject( MClass cls, String name ) throws MSystemException {
        // checks if cls is a associationclass, if yes then throw an exception,
        // because this should not be allowed.
        if ( cls instanceof MAssociationClass ) {
            throw new MSystemException(
                    "Creation of a linkobject is not allowed with the command create. \n"
                    + "Use 'create ... between ...' or 'insert' instead." );
        }
        // create new object and initial state
        MObject obj = fSystem.createObject( cls, name );
        MObjectState objState = new MObjectState( obj );
        fObjectStates.put( obj, objState );
        fClassObjects.put( cls, obj );
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
     * Deletes an object from the state. All links connected to the
     * object are removed.
     *
     * @return Set(MLink) the set of removed links
     */
    public Set[] deleteObject( MObject obj ) {
        Set[] result = auxDeleteObject( obj );

        // if obj is an linkobject it has to be deleted as a link too.
        if ( obj instanceof MLinkObject ) {
            MLinkObject linkObject = ( MLinkObject ) obj;
            auxDeleteLink( linkObject );
            result[REMOVED_LINKS].add( linkObject );
        }

        return result;
    }

    /**
     * Deletes an object from the state. All links connected to the
     * object are removed.
     *
     * @return Set(MLink) the set of removed links
     */
    private Set[] auxDeleteObject( MObject obj ) {
        Set[] res = new Set[3];
        res[REMOVED_LINKS] = new HashSet();
        res[REMOVED_OBJECTS] = new HashSet();
        res[REMOVED_OBJECTSTATES] = new HashSet();
        MClass objClass = obj.cls();

        // get associations this object might be participating in
        Set assocSet = objClass.allAssociations();
      
        Iterator assocIter = assocSet.iterator();
        while ( assocIter.hasNext() ) {
            MAssociation assoc = ( MAssociation ) assocIter.next();
         
            MLinkSet linkSet = ( MLinkSet ) fLinkSets.get( assoc );

            // check all association ends the objects' class is
            // connected to
            Iterator aendIter = assoc.associationEnds().iterator();
            while ( aendIter.hasNext() ) {
                MAssociationEnd aend = ( MAssociationEnd ) aendIter.next();
                if ( objClass.isSubClassOf( aend.cls() ) ) {
                    Set removedLinks = linkSet.removeAll( aend, obj );
                    Iterator it = removedLinks.iterator();
                    while ( it.hasNext() ) {
                        Object o = it.next();
                        if ( o instanceof MLinkObject ) {
                            Set[] resLinkObject = auxDeleteObject( ( MLinkObject ) o );
                            res[REMOVED_LINKS].addAll( resLinkObject[REMOVED_LINKS] );
                            res[REMOVED_OBJECTS].addAll( resLinkObject[REMOVED_OBJECTS] );
                            res[REMOVED_OBJECTSTATES].addAll( resLinkObject[REMOVED_OBJECTSTATES] );
                        }
                    }
                    res[REMOVED_LINKS].addAll( removedLinks );
                }
            }
        }
        res[REMOVED_OBJECTS].add( obj );
        res[REMOVED_OBJECTSTATES].add( fObjectStates.get( obj ) );
        fObjectStates.remove( obj );
        fClassObjects.remove( objClass, obj );
        fSystem.deleteObject( obj );
        return res;
    }

    private void auxDeleteLink( MLink link ) {
        MLinkSet linkSet = ( MLinkSet ) fLinkSets.get( link.association() );
        linkSet.remove( link );
    }

    /**
     * Creates and adds a new link to the state.
     *
     * @exception MSystemException link invalid or already existing
     * @return the newly created link.
     */
    public MLink createLink( MAssociation assoc, List objects )
            throws MSystemException {
        MLink link = null;

        // checks if assoc is an associationclass
        if ( assoc instanceof MAssociationClass ) {
            // specifies if there is already an existing linkobject of this
            // association class between the objects
            if ( hasLinkBetweenObjects( assoc, (MObject[])objects.toArray(new MObject[0]) ) ) {
                throw new MSystemException( "Cannot insert two linkobjects of the same type"
                                            + " between one set of objects!" );
            }

            String name = fSystem.uniqueObjectNameForClass( assoc.name() );
//            Log.println( "Information: The name '" + name + "' was automatically generated for the "
//                         + "linkobject." );
            // creates a linkobject with a generated name
            link = createLinkObject( ( MAssociationClass ) assoc,
                                     name, objects );
        }else if ( ( assoc.aggregationKind() == MAggregationKind.AGGREGATION) || 
		   ( assoc.aggregationKind() == MAggregationKind.COMPOSITION) ){                  
	    MLinkSet linkSet = ( MLinkSet ) fLinkSets.get( assoc );
	    link = new MLinkImpl( assoc, objects );
	    if ( linkSet.contains( link ) )
		throw new MSystemException( "Link `" + assoc.name() 
					    + "' between (" 
					    + StringUtil.fmtSeq( objects.iterator(), "," ) 
					    + ") already exist." );
	    //The graph to store the information of the whole/part hierachy            
            DirectedGraph fWholePartLinkGraph;
	    fWholePartLinkGraph = new DirectedGraphBase();		   
            //Obtaining whole/part links from all links of the system state in fLinkSets
	    Iterator assocIter = fLinkSets.keySet().iterator();
	    MAssociation assocTemp = null;
	    MWholePartLink wholePartLink = null;
	    MObject source = null;
	    MObject target = null;
	    while(assocIter.hasNext()){
	      assocTemp = (MAssociation) assocIter.next();	    
	      if( (assocTemp.aggregationKind() == MAggregationKind.AGGREGATION) || 
		   ( assocTemp.aggregationKind() == MAggregationKind.COMPOSITION) ) {	    
                  MLinkSet linkSetTmp = (MLinkSet) fLinkSets.get(assocTemp);
		  Iterator it = linkSetTmp.links().iterator();
                  while (it.hasNext() ) {                    
                    wholePartLink = new MWholePartLinkImpl( (MLink) it.next() );
		    source = (MObject) wholePartLink.source();
	            target = (MObject) wholePartLink.target();
	            fWholePartLinkGraph.add(source);
	            fWholePartLinkGraph.add(target);
		    fWholePartLinkGraph.addEdge(wholePartLink);	  
                  }	   		  
	      }	    
	    }	    
	    wholePartLink = new MWholePartLinkImpl( link );
	    // get link set for fAssociation    	    
	    source = (MObject) wholePartLink.source();
	    target = (MObject) wholePartLink.target();
	    fWholePartLinkGraph.add(source);
	    fWholePartLinkGraph.add(target);
	    // the link is irreflexive    
	    if (wholePartLink.isReflexive())
		throw new MSystemException("Object `" + source.name()
                   + "' cannot be a part of itself.");
	    //check for SHARED OBJECT OF THE COMPOSION RELATIONSHIP
	    if( assoc.aggregationKind() == MAggregationKind.COMPOSITION) { 
		//finding all edges (links) whose target is the target object of the new link
		Iterator sourceEdgeIter = fWholePartLinkGraph.sourceNodeSet(target).iterator();
		while (sourceEdgeIter.hasNext() ) {
		    Iterator iter = fWholePartLinkGraph.edgesBetween(target,sourceEdgeIter.next()).iterator();
		    while (iter.hasNext() ) {
			MLink l = (MLink) iter.next();
			if(l.association().aggregationKind() == MAggregationKind.COMPOSITION){
			    throw new MSystemException("Object `" + target.name()
						       + "' is already a part of other composition.");
			}
		    }
		}			
	    }
	    // check for cycles that might be occured when adding the new
	    // whole/part link    
	    if (fWholePartLinkGraph.existsPath(target, source) )
		throw new MSystemException(
					   "Detected a cycle in aggregation hierarchy. Object `"
					   + source.name()
					   + "' is already a part of `"
					   + target.name() + "'.");
	    // silently ignore duplicates	    
	    linkSet.add( link );			   
        } else {
            // create a normal link
            link = new MLinkImpl( assoc, objects );
            // get link set for association
            MLinkSet linkSet = ( MLinkSet ) fLinkSets.get( assoc );
            if ( linkSet.contains( link ) )
                throw new MSystemException( "Link `" + assoc.name() 
                                            + "' between (" 
                                            + StringUtil.fmtSeq( objects.iterator(), "," ) 
                                            + ") already exist." );
            linkSet.add( link );
        }
        return link;
    }

    /**
     * Inserts a link into the state.
     */
    public void insertLink(MLink link) {
        // get link set for association
        MLinkSet linkSet = (MLinkSet) fLinkSets.get(link.association());
        linkSet.add(link);
    }

    /**
     * Deletes a link from the state.
     */
    // TODO: Was geschieht mit Set von deleteObject
    public void deleteLink( MLink link ) {
        auxDeleteLink( link );

        if ( link instanceof MLinkObject ) {
            auxDeleteObject( ( MLinkObject ) link );
        }
    }

    /**
     * Deletes a link from the state. The link is indirectly specified
     * by the association and objects.
     *
     * @exception MSystemException link does not exist
     * @return the removed link.
     */
    public Set[] deleteLink( MAssociation assoc, List objects ) throws MSystemException {
        Set[] result = new Set[3];
        result[REMOVED_LINKS] = new HashSet();
        result[REMOVED_OBJECTS] = new HashSet();
        result[REMOVED_OBJECTSTATES] = new HashSet();
        MLink link = null;
        MLinkSet linkSet = linksOfAssociation( assoc );
        
        Iterator linkIter = linkSet.links().iterator();
        while (linkIter.hasNext() ) {
            MLink l = (MLink) linkIter.next();
            if (l.linkedObjects().equals( new HashSet(objects) ) ) {
                link = l;
                break;
            }
        }
        
        if ( link == null ) {
            throw new MSystemException( "Link `" + assoc.name() 
                                        + "' between (" 
                                        + StringUtil.fmtSeq( objects.iterator(), "," ) 
                                        + ") does not exist." );
        }
        
        linkSet.remove( link );
        result[REMOVED_LINKS].add( link );
        
        if ( link instanceof MLinkObject ) {
            Set[] res = auxDeleteObject( ( MLinkObject ) link );
            result[REMOVED_LINKS].addAll( res[REMOVED_LINKS] );
            result[REMOVED_OBJECTS].addAll( res[REMOVED_OBJECTS] );
            result[REMOVED_OBJECTSTATES].addAll( res[REMOVED_OBJECTSTATES] );
        }
        return result;
        
//        if ( assoc instanceof MAssociationClass ) {
//            MAssociationClass assocClass = ( MAssociationClass ) assoc;
//            String assocClassName = getNameOfLinkObject( assocClass, objects );
//            link = new MLinkObjectImpl( assocClass, assocClassName, objects );
//        } else {
//            link = new MLinkImpl( assoc, objects );
//        }
        
//        // get link set for association
//        MLinkSet linkSet = ( MLinkSet ) fLinkSets.get( assoc );
//        if ( !linkSet.contains( link ) )
//            throw new MSystemException( "Link " + link + " does not exist." );
//        linkSet.remove( link );
//        if ( link instanceof MLinkObject ) {
//            auxDeleteObject( ( MLinkObject ) link );
//        }
//        return link;
    }

//    private String getNameOfLinkObject( MAssociationClass assocClass, List objects ) {
//        HashSet set = new HashSet( objects );
//        Iterator it = linksOfAssociation( assocClass ).links().iterator();
//        while ( it.hasNext() ) {
//            MLinkObject linkObject = ( MLinkObject ) it.next();
//            if ( linkObject.linkedObjects().equals( set ) ) {
//                return linkObject.name();
//            }
//        }
//        return "";
//    }

    /**
     * Creates and adds a new link to the state.
     *
     * @exception MSystemException link invalid or already existing
     * @return the newly created link.
     */
    public MLinkObject createLinkObject( MAssociationClass assocClass,
                                         String name, List objects )
            throws MSystemException {
        if ( objectByName( name ) != null ) {
            throw new MSystemException("An object with name `" + name +
                                        "' already exists.");
        }
        MLinkObject linkobj = new MLinkObjectImpl( assocClass, name, objects );

        // Part from createObject method
        MObjectState objState = new MObjectState( linkobj );
        fObjectStates.put( linkobj, objState );
        fClassObjects.put( assocClass, linkobj );

        // Part from createLink method
        MLinkSet linkSet = ( MLinkSet ) fLinkSets.get( assocClass );
        if ( linkSet.contains( linkobj ) )
            throw new MSystemException( "Link " + linkobj + " already exists." );
        linkSet.add( linkobj );

        return linkobj;
    }


    /**
     * Returns the state of an object in a specific system state.
     *
     * @return null if object does not exist in the state
     */
    MObjectState getObjectState(MObject obj) {
        return (MObjectState) fObjectStates.get(obj);
    }

    /**
     * Returns a list of objects at <code>dstEnd</code> which are
     * linked to <code>obj</code> at srcEnd.
     *
     * @return List(MObject) 
     */
    List getLinkedObjects(MObject obj, 
                          MAssociationEnd srcEnd, 
                          MAssociationEnd dstEnd) {
        ArrayList res = new ArrayList();

        // get association
        MAssociation assoc = dstEnd.association();
    
        // get link set for association
        MLinkSet linkSet = (MLinkSet) fLinkSets.get(assoc);

        // if link set is empty return empty result list
        Log.trace(this, "linkSet size of association `" + assoc.name() + 
                  "' = " + linkSet.size());
        if (linkSet.size() == 0 )
            return res;

        // select links with srcEnd == obj
        Set links = linkSet.select(srcEnd, obj);
        Log.trace(this, "linkSet.select for object `" + obj + 
                  "', size = " + links.size());

        // project tuples to destination end component 
        Iterator it = links.iterator();
        while (it.hasNext() ) {
            MLink link = (MLink) it.next();
            MLinkEnd linkEnd = link.linkEnd(dstEnd);
            res.add(linkEnd.object());
        }
        return res;
    }

    /**
     * Returns a list of objects at <code>dst</code> which are
     * connected to this object at <code>src</code>. This is needed for navigation.
     *
     * @return List(MObject)
     */
    public List getNavigableObjects( MObject obj, MNavigableElement src, 
                                     MNavigableElement dst ) {
        ArrayList res = new ArrayList();

        // get association
        MAssociation assoc = dst.association();

        // get link set for association
        MLinkSet linkSet = ( MLinkSet ) fLinkSets.get( assoc );

        // if link set is empty return empty result list
        Log.trace( this, "linkSet size of association `" + assoc.name() +
                         "' = " + linkSet.size() );
        if ( linkSet.size() == 0 )
            return res;

        // navigation from a linkobject
        if ( src instanceof MAssociationClass ) {
            if ( dst instanceof MAssociationClass ) {
                throw new RuntimeException( "Wrong navigation expression." );
            }
            MLinkEnd linkEnd = ( ( MLinkObject ) obj ).linkEnd( ( MAssociationEnd ) dst );
            res.add( linkEnd.object() );
        } else {
            if ( src instanceof MAssociationClass ) {
                throw new RuntimeException( "Wrong navigation expression." );
            }
            MAssociationEnd srcEnd = ( MAssociationEnd ) src;
            // select links with srcEnd == obj
            Set links = linkSet.select( srcEnd, obj );
            Log.trace( this, "linkSet.select for object `" + obj +
                             "', size = " + links.size() );

            // navigation to a linkobject
            if ( dst instanceof MAssociationClass ) {
                return new ArrayList( links );
            } else {
                MAssociationEnd dstEnd = ( MAssociationEnd ) dst;
                // project tuples to destination end component
                Iterator it = links.iterator();
                while ( it.hasNext() ) {
                    MLink link = ( MLink ) it.next();
                    MLinkEnd linkEnd = link.linkEnd( dstEnd );
                    res.add( linkEnd.object() );
                }
            }
        }
        return res;
    }

    /**
     * Checks for a valid system state. Returns true if all
     * constraints hold for all objects. Prints result of
     * subexpressions for failing constraints to the log if
     * traceEvaluation is true.  
     */
    public boolean check(PrintWriter out, 
                         boolean traceEvaluation,
                         boolean showDetails,
                         boolean allInvariants,
                         List invNames) {
        boolean valid = true;
        Evaluator evaluator = new Evaluator();
        MModel model = fSystem.model();

        // model inherent constraints: check whether cardinalities of
        // association links match their declaration of multiplicities
        if (! checkStructure(out) ) {
            //return false;
            valid = false;
        }
        
        if (Options.EVAL_NUMTHREADS > 1 )
            out.println("checking invariants (using " + Options.EVAL_NUMTHREADS + 
                        " concurrent threads)...");
        else
            out.println("checking invariants...");
        out.flush();
        int numChecked = 0;
        int numFailed = 0;
        long tAll = System.currentTimeMillis();

        ArrayList invList = new ArrayList();
        ArrayList negatedList = new ArrayList();
        ArrayList exprList = new ArrayList();
        if (allInvariants ) {
            // get all invariants (loaded and normal classinvariants)
            // it doesn't matter if specific invariants are given or not 

            // get all loaded Invariants (generator)
            Iterator it = fSystem.generator().flaggedInvariants().iterator();
            while (it.hasNext() ) {
                GFlaggedInvariant flaggedInv = (GFlaggedInvariant) it.next();
                MClassInvariant inv = flaggedInv.classInvariant();
                Expression expr = inv.expandedExpression();
                //sanity check
                if (! expr.type().isBoolean() )
                    throw new RuntimeException("Expression " + expr + 
                                               "has type " + expr.type() + 
                                               ", expected boolean type");
                if (flaggedInv.negated() ){
                    try {
                        Expression [] args = { expr };
                        Expression expr1 = ExpStdOp.create("not", args);
                        expr = expr1;
                    } catch (ExpInvalidException e) { 
                        // TODO: Should this be ignored or is this a fatal error?
                    }
                    negatedList.add(Boolean.TRUE );
                } else {
                    negatedList.add(Boolean.FALSE );
                }
                invList.add(inv);
                exprList.add(expr);
                System.out.println("GeneratorInvariants: "+inv);
            }
        } else if (invNames.isEmpty() ) {
            // get all invariants
            Iterator it = model.classInvariants().iterator();
            while (it.hasNext() ) {
                MClassInvariant inv = (MClassInvariant) it.next();
                Expression expr = inv.expandedExpression();
                // sanity check
                if (! expr.type().isBoolean() )
                    throw new RuntimeException("Expression " + expr +
                                               "has type " + expr.type() +
                                               ", expected boolean type");
                invList.add(inv);
                negatedList.add(Boolean.FALSE );
                exprList.add(expr);
            }
        } else {
            // get only specified invariants
            Iterator it = invNames.iterator();
            while (it.hasNext() ) {
                String invName = (String) it.next();
                MClassInvariant inv = model.getClassInvariant(invName);
                if (inv != null ) {
                    Expression expr = inv.expandedExpression();
                    // sanity check
                    if (! expr.type().isBoolean() )
                        throw new RuntimeException("Expression " + expr +
                                                   "has type " + expr.type() +
                                                   ", expected boolean type");
                    invList.add(inv);
                    negatedList.add(Boolean.FALSE );
                    exprList.add(expr);
                } else {
                    GFlaggedInvariant flaggedInv = fSystem.generator().flaggedInvariant(invName);
                    inv = flaggedInv.classInvariant();
                    if (inv != null ) {
                        Expression expr = inv.expandedExpression();
                        //sanity check
                        if (! expr.type().isBoolean() )
                            throw new RuntimeException("Expression " + expr + 
                                                       "has type " + expr.type() + 
                                                       ", expected boolean type");
                        if (flaggedInv.negated() ){
                            try {
                                Expression [] args = { expr };
                                Expression expr1 = ExpStdOp.create("not", args);
                                expr = expr1;
                            } catch (ExpInvalidException e) {
                                // TODO: Should this be ignored or is this a fatal error?
                            }
                            negatedList.add(Boolean.TRUE );
                        } else {
                            negatedList.add(Boolean.FALSE );
                        }
                        invList.add(inv);
                        exprList.add(expr);
                    }  
                }
            }
        
            ///////////////////////////////////////////
            // it's the same as above
            ///////////////////////////////////////////            
            //         } else {
            //      // get only specified invariants
            //      Iterator it = invNames.iterator();
            //      while ( it.hasNext() ) {
            //      String invName = (String) it.next();
            //      MClassInvariant inv = model.getClassInvariant(invName);
            //      if ( inv != null ) {
            //          Expression expr = inv.expandedExpression();
            //          // sanity check
            //          if ( ! expr.type().isBoolean() )
            //          throw new RuntimeException("Expression " + expr +
            //                         "has type " + expr.type() +
            //                         ", expected boolean type");
            //          invList.add(inv);
            //          exprList.add(expr);
            //      }
            //      }
        }
        
        // start (possibly concurrent) evaluation
        Queue resultValues = 
            evaluator.evalList(Options.EVAL_NUMTHREADS, exprList, this);
        
        // receive results
        for (int i = 0; i < exprList.size(); i++) {
            MClassInvariant inv = (MClassInvariant) invList.get(i);
            numChecked++;
            String msg = "checking invariant (" + numChecked + ") `" + 
                inv.cls().name() + "::" + inv.name() + "': ";
            out.print(msg); // + inv.bodyExpression());
            out.flush();
            try {
                Value v = (Value) resultValues.get();

                // if value 'v' is null, the invariant can not be evaluated, therefor
                // it is N/A (not available).
                if (v == null ){
                    out.println("N/A");
                    // if there is a value, the invariant can allways be evaluated and the
                    // result can be printed.
                } else {
                    boolean ok = v.isDefined() && ((BooleanValue) v).isTrue();
                    if (ok ) 
                        out.println("OK."); // (" + timeStr +").");
                    else {
                        out.println("FAILED."); //  (" + timeStr +").");
                        out.println("  -> " + v.toStringWithType());
                        
                        // repeat evaluation with output of all subexpression results
                        if (traceEvaluation ) {
                            out.println("Results of subexpressions:");
                            Expression expr = (Expression) exprList.get(i);
                            evaluator.eval(expr, this, new VarBindings(), out);
                        }
                        
                        // show instances violating the invariant by using
                        // the OCL expression C.allInstances->reject(self | <inv>)
                        if (showDetails ) {
                            out.println("Instances of " + inv.cls().name() + 
                                        " violating the invariant:");
                            Expression expr = inv.getExpressionForViolatingInstances();
                            Value v1 = evaluator.eval(expr, this, new VarBindings());
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
        out.println("checked " + 
                    numChecked + " invariant" +
                    ((numChecked == 1 ) ? "" : "s") + 
                    " in " + timeStr + ", " + 
                    numFailed + " failure" +
                    ((numFailed == 1 ) ? "" : "s") + '.');
        out.flush();
        return valid;
    }

    /**
     * Checks model inherent constraints, i.e., checks whether
     * cardinalities of association links match their declaration of
     * multiplicities. 
     */
    public boolean checkStructure(PrintWriter out) {
        boolean res = true;
        out.println("checking structure...");
        out.flush();
        // check all associations
        Iterator it = fSystem.model().associations().iterator();
        while (it.hasNext() ) {
            MAssociation assoc = (MAssociation) it.next();
            if (assoc.associationEnds().size() != 2 ) {
                // check for n-ary links
                if (! naryAssociationsAreValid(out, assoc) )
                    res = false;
            } else {
                // check both association ends
                Iterator it2 = assoc.associationEnds().iterator();
                MAssociationEnd aend1 = (MAssociationEnd) it2.next();
                MAssociationEnd aend2 = (MAssociationEnd) it2.next();

                if (! binaryAssociationsAreValid(out, assoc, aend1, aend2)
                     || ! binaryAssociationsAreValid(out, assoc, aend2, aend1) )
                    res = false;
            }
        }
        //out.println("checking link cardinalities, done.");
        out.flush();
        return res;
    }

    private boolean naryAssociationsAreValid(PrintWriter out, 
                                             MAssociation assoc) {
        boolean valid = true;
        Set links = linksOfAssociation(assoc).links();

        Iterator aendIter1 = assoc.associationEnds().iterator();
        while (aendIter1.hasNext() ) {
            MAssociationEnd selEnd = (MAssociationEnd) aendIter1.next();
            List otherEnds = selEnd.getAllOtherAssociationEnds();
            List classes = new ArrayList();
            for (Iterator it = otherEnds.iterator(); it.hasNext();) {
                MAssociationEnd end = (MAssociationEnd) it.next();
                classes.add(end.cls());
            }
            Bag crossProduct = getCrossProductOfInstanceSets(classes);
            
            for (Iterator itTuples=crossProduct.iterator(); itTuples.hasNext();) {
                MObject[] tuple = (MObject[])itTuples.next();
                
                int count = 0;
                for( Iterator itLinks=links.iterator();itLinks.hasNext();) {
                    MLink link = (MLink)itLinks.next();
                    boolean ok=true;
                    int index = 0;
                    for (Iterator itEnds=otherEnds.iterator();itEnds.hasNext();) {
                        MAssociationEnd end = (MAssociationEnd)itEnds.next();
                        if (link.linkEnd(end).object()!=tuple[index]) {
                            ok=false;
                        }
                        ++index;
                    }
                    if (ok)
                        ++count;
                }
                if (!selEnd.multiplicity().contains(count)) {
                    out.println("Multiplicity constraint violation in association `" + 
                                assoc.name() + "':");
                    out.println("  Objects `" + 
                                StringUtil.fmtSeq(tuple, ", ") + 
                                "' are connected to " + count + 
                                " object" + 
                                ((count == 1 ) ? "" : "s") +
                                " of class `" + 
                                selEnd.cls().name() + "'");
                    out.println("  but the multiplicity is specified as `" +
                                selEnd.multiplicity() + "'.");
                    valid = false;
                }
            }
        }
        return valid;
    }

    /**
     * Returns a bag containing the cross product of the instances of 
     * <code>classes</code>
     * @param classes
     * @return a bag of object arrays (<code>Bag(MObject[])</code>)
     */
    Bag getCrossProductOfInstanceSets(List classes) {
       Bag bag = new HashBag();
       insertAllNMinusOneTuples(bag, (MClass[])classes.toArray(new MClass[classes.size()]), 0, new MObject[0]);
       return bag;
   }
    
    // Helper method for getCrossProductOfInstanceSets.
    private void insertAllNMinusOneTuples(Bag allNMinusOneTuples, MClass[] classes, int index, MObject[] objects) {
        if (index < classes.length) {
            MClass next = classes[index];
            MObject[] objects1 = new MObject[objects.length + 1];
            for (int i=0;i<objects.length;++i)
                objects1[i] = objects[i];
            
            for (Iterator it=objectsOfClassAndSubClasses(next).iterator();it.hasNext();) {
                MObject obj = (MObject)it.next();
                objects1[objects.length] = obj;
                insertAllNMinusOneTuples(allNMinusOneTuples, classes, index+1, objects1);
            }
        } else {
            allNMinusOneTuples.add(objects.clone());
        }
    }

    private boolean binaryAssociationsAreValid(PrintWriter out, 
                                               MAssociation assoc,
                                               MAssociationEnd aend1,
                                               MAssociationEnd aend2) {
        boolean valid = true;

        // for each object of the association end's type get
        // the number of links in which the object participates
        MClass cls = aend1.cls();
        Set objects = objectsOfClassAndSubClasses(cls);
        Iterator it = objects.iterator();
        while (it.hasNext() ) {
            MObject obj = (MObject) it.next();
            List objList = getLinkedObjects(obj, aend1, aend2);
            int n = objList.size();
            if (! aend2.multiplicity().contains(n) ) {
                out.println("Multiplicity constraint violation in association `" + 
                            assoc.name() + "':");
                out.println("  Object `" + obj.name() + "' of class `" + 
                            obj.cls().name() + 
                            "' is connected to " + n +
                            " object" + 
                            ((n == 1 ) ? "" : "s") +
                            " of class `" + 
                            aend2.cls().name() + "'");
                out.println("  but the multiplicity is specified as `" +
                            aend2.multiplicity() + "'.");
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Returns a unique name that can be used for a new object of the
     * given class. Checks whether the name is used in this state.
     * BigFix for USE 2.0.0.
     * This operation is only used by the Snapshot Generator.
     */
    public String uniqueObjectNameForClass(String clsName) {
        String name;
        do
            name = system().uniqueObjectNameForClass(clsName);
        while (objectByName(name)!=null );
        return name;
    }

}
