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

package org.tzi.use.uml.mm;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.tzi.use.config.Options;
import org.tzi.use.uml.al.ALActionList;
import org.tzi.use.uml.al.ALCreateObject;
import org.tzi.use.uml.al.ALCreateVar;
import org.tzi.use.uml.al.ALDelete;
import org.tzi.use.uml.al.ALDestroyObject;
import org.tzi.use.uml.al.ALExecute;
import org.tzi.use.uml.al.ALFor;
import org.tzi.use.uml.al.ALIf;
import org.tzi.use.uml.al.ALInsert;
import org.tzi.use.uml.al.ALSet;
import org.tzi.use.uml.al.ALSetCreate;
import org.tzi.use.uml.al.ALWhile;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.util.StringUtil;

/**
 * A visitor for producing a sequence of commands for generating the
 * current model as an instance of the UML metamodel. The output can
 * be used as input commands for USE in combination with the
 * specification of the UML metamodel.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class MMInstanceGenerator implements MMVisitor {
    protected PrintWriter fOut;
    private Set fDataTypes;
    private boolean fPass1;
    private String fModelId;

    public MMInstanceGenerator(PrintWriter out) {
        fOut = out;
        fDataTypes = new HashSet();
    }

    /**
     * Generates output for creating an instance of a model
     * element. This is common for all model elements.
     *
     * @return the name of the new instance 
     */
    private String genInstance(MModelElement e, String metaClass, String prefix) {
        String eName = e.name();
        String qualifiedName = (( prefix != null ) ? prefix + "_" : "") + eName;
        fOut.println("-- " + metaClass + " " + qualifiedName);
        String id = qualifiedName + metaClass;
        fOut.println("!create " + id + " : " + metaClass);
        fOut.println("!set " + id + ".name := '" + eName + "'");
        return id;
    }

    private String genInstance(MModelElement e, String metaClass) {
        return genInstance(e, metaClass, null);
    }

    public void visitAssociation(MAssociation e) {
        String id = genInstance(e, "Association");
        // add to model namespace
        fOut.println("!insert (" + fModelId + ", " + id +
                     ") into Namespace_ModelElement");
        fOut.println();

        // visit association ends
        Iterator it = e.associationEnds().iterator();
        while (it.hasNext() ) {
            MAssociationEnd assocEnd = (MAssociationEnd) it.next();
            assocEnd.processWithVisitor(this);
        }
    }

    public void visitAssociationClass( MAssociationClass e ) {

        // prints information about associationclass
        if ( !fPass1 ) {
            String id = genInstance( e, "AssociationClass" );
            fOut.println( "!set " + id + ".isAbstract = " + e.isAbstract() );
            fOut.println( "!set " + id + ".isRoot = false" );
            fOut.println( "!set " + id + ".isLeaf = false" );

            // add to model namespace
            fOut.println( "!insert (" + fModelId + ", " + id +
                          ") into Namespace_ModelElement" );
            fOut.println();
        }

        // visit attributes
        Iterator it = e.attributes().iterator();
        while ( it.hasNext() ) {
            MAttribute attr = ( MAttribute ) it.next();
            attr.processWithVisitor( this );
        }

        // visit association ends
        it = e.associationEnds().iterator();
        while ( it.hasNext() ) {
            MAssociationEnd assocEnd = ( MAssociationEnd ) it.next();
            assocEnd.processWithVisitor( this );
        }

    }

    public void visitAssociationEnd(MAssociationEnd e) {
        String id = genInstance(e, "AssociationEnd", e.association().name());
        fOut.print("!set " + id + ".aggregation_ := #");
        switch ( e.aggregationKind() ) {
        case MAggregationKind.NONE:
            fOut.println("none");
            break;
        case MAggregationKind.AGGREGATION:
            fOut.println("aggregate");
            break;
        case MAggregationKind.COMPOSITION:
            fOut.println("composite");
            break;
        default: 
            throw new Error("Fatal error. Invalid multiplicity kind");            
        }
        // connect to Association
        fOut.println("!insert (" + 
                     e.association().name() + "Association, " + 
                     id + ") into Association_AssociationEnd");
        // connect to Classifier
        fOut.println("!insert (" + id + ", " + 
                     e.cls().name() + 
                     "Class) into AssociationEnd_Classifier1");
        fOut.println();
    }

    public void visitAttribute(MAttribute e) {
        if (fPass1 ) {
            fDataTypes.add(e.type());
            return;
        }
        String id = genInstance(e, "Attribute", e.owner().name());
        fOut.println("!insert (" + e.owner().name() + "Class, " + 
                     id + ") into Classifier_Feature");

        // add StructuralFeature_Classifier link for type of attribute
        String s;
        if (e.owner().model().getClass(e.type().toString()) == null )
            s = "DataType";
        else 
            s = "Class";
        fOut.println("!insert (" + id + ", " + e.type() + s +
                     ") into StructuralFeature_Classifier");
        fOut.println();
    }

    public void visitClass(MClass e) {
        if (! fPass1 ) {
            String id = genInstance(e, "Class");
            fOut.println("!set " + id + ".isAbstract := " + e.isAbstract());
            fOut.println("!set " + id + ".isRoot := false");
            fOut.println("!set " + id + ".isLeaf := false");

            // add to model namespace
            fOut.println("!insert (" + fModelId + ", " + id +
                         ") into Namespace_ModelElement");
            fOut.println();
        }

        // visit attributes
        Iterator it = e.attributes().iterator();
        while (it.hasNext() ) {
            MAttribute attr = (MAttribute) it.next();
            attr.processWithVisitor(this);
        }

        //      // visit operations
        //      if (e.operations().size() > 0 ) {
        //          indent();
        //          println(keyword("operations"));
        //          incIndent();
        //          Iterator it = e.operations().iterator();
        //          while (it.hasNext() ) {
        //          MOperation op = (MOperation) it.next();
        //          op.processWithVisitor(this);
        //          }
        //          decIndent();
        //      }
    }

    public void visitClassInvariant(MClassInvariant e) {
        String id = genInstance(e, "Constraint", e.cls().name());
        fOut.println("!set " + id + ".body := '" + 
                     StringUtil.escapeString(e.bodyExpression().toString(), '\'')
                     + "'");
        // connect to ModelElement
        fOut.println("!insert (" + id + ", " +
                     e.cls().name() + 
                     "Class) into Constraint_ModelElement");
        // add to model namespace
        fOut.println("!insert (" + fModelId + ", " + id +
                     ") into Namespace_ModelElement");
        fOut.println();
    }

    public void visitGeneralization(MGeneralization e) {
        String id = genInstance(e, "Generalization");
        fOut.println("!set " + id + ".discriminator := ''");
        // connect to child
        fOut.println("!insert (" + id + ", " +
                     e.child().name() + 
                     "Class) into Generalization_GeneralizableElement1");
        // connect to parent
        fOut.println("!insert (" + id + ", " +
                     e.parent().name() + 
                     "Class) into Generalization_GeneralizableElement2");
        // add to model namespace
        fOut.println("!insert (" + fModelId + ", " + id +
                     ") into Namespace_ModelElement");
        fOut.println();
    }

    public void visitModel(MModel e) {
        // create Model
        fOut.println("-- UML metamodel instance generated by USE " + 
                     Options.RELEASE_VERSION);
        fOut.println();
        fModelId = genInstance(e, "Model");
        fOut.println();

        fPass1 = true;

        // visit classes in first pass only to gather required
        // DataTypes
        Iterator it = e.classes().iterator();
        while (it.hasNext() ) {
            MClass cls = (MClass) it.next();
            cls.processWithVisitor(this);
        }

        // create all DataTypes that are required later
        fOut.println("-- DataTypes");
        it = fDataTypes.iterator();
        while (it.hasNext() ) {
            Type t = (Type) it.next();
            if (e.getClass(t.toString()) == null ) {
                String id = t.toString() + "DataType";
                fOut.println("!create " + id + " : DataType");
                fOut.println("!set " + id + ".name := '" + t.toString() + "'");
                // add to model namespace
                fOut.println("!insert (" + fModelId + ", " + id +
                             ") into Namespace_ModelElement");
            }
        }
        fOut.println();

        fPass1 = false;

        // visit classes
        it = e.classes().iterator();
        while (it.hasNext() ) {
            MClass cls = (MClass) it.next();
            cls.processWithVisitor(this);
        }

        // visit associations
        it = e.associations().iterator();
        while (it.hasNext() ) {
            MAssociation assoc = (MAssociation) it.next();
            assoc.processWithVisitor(this);
        }

        // visit generalizations
        it = e.generalizationGraph().edgeIterator();
        while (it.hasNext() ) {
            MGeneralization gen = (MGeneralization) it.next();
            gen.processWithVisitor(this);
        }

        // visit constraints
        it = e.classInvariants().iterator();
        while (it.hasNext() ) {
            MClassInvariant inv = (MClassInvariant) it.next();
            inv.processWithVisitor(this);
        }
    }

    public void visitOperation(MOperation e) {
        //FIXME: implement
    }

    public void visitPrePostCondition(MPrePostCondition e) {
        //FIXME: implement
    }

    public void visitALActionList(ALActionList e) {
    }

    public void visitALFor(ALFor e) {
    }

    public void visitALDestroyObject(ALDestroyObject e) {
    }

    public void visitALIf(ALIf e) {
    }

    public void visitALSet(ALSet e) {
    }

    public void visitALSetCreate(ALSetCreate e) {
    }

    public void visitALInsert(ALInsert e) {
    }

    public void visitALDelete(ALDelete e) {
    }

    public void visitALExecute(ALExecute e) {
    }

    public void visitALCreateVar(ALCreateVar e) {
    }

    public void visitALCreateObject(ALCreateObject e) {
    }

    public void visitALWhile(ALWhile e) {
    }

}
