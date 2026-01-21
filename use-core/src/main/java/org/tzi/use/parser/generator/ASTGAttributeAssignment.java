/*
 * This is source code of the Snapshot Generator, an extension for USE
 * to generate (valid) system states of UML models.
 * Copyright (C) 2001 Joern Bohling, University of Bremen
 *
 * About USE:
 *   USE - UML based specification environment
 *   Copyright (C) 1999,2000,2001 Mark Richters, University of Bremen
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

/**
 * March 22th 2001 
 * @author  Joern Bohling
 */

package org.tzi.use.parser.generator;

import org.antlr.runtime.Token;
import org.tzi.use.gen.assl.statics.GAttributeAssignment;
import org.tzi.use.gen.assl.statics.GInstruction;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeAdapters;

public class ASTGAttributeAssignment extends ASTGInstruction {
    ASTGocl fTargetObject;
    private Token fAttributeName;
    private ASTGValueInstruction fSource;

    public ASTGAttributeAssignment( ASTGocl targetObject,
    				Token attributeName,
                                    ASTGValueInstruction source ) {
        fTargetObject = targetObject;
        fAttributeName = attributeName;
        fSource = source;
    }

    public GInstruction gen(Context ctx) throws SemanticException {
        GValueInstruction targetinstr
            = (GValueInstruction) fTargetObject.gen(ctx);
        if (! targetinstr.type().isTypeOfClass()) {
            String err = "The type of `" + targetinstr
                + "' must be an object type.";
            throw new SemanticException( fAttributeName, err );
        }

        // Ermittle die zugrunde liegende UML-Klasse des Zielobjekts über den OCL-Typ
        Type targetType = TypeAdapters.asOclType(targetinstr.type());
        MModel model = ctx.model();
        MClass targetcls = resolveTargetClassFromType(targetType, model);

        if (targetcls == null) {
            String err = "Attribute target is not a UML class (type=" + targetType + ").";
            throw new SemanticException(fAttributeName, err);
        }

        MAttribute targetAttribute
            = targetcls.attribute(fAttributeName.getText(), true);
        if (targetAttribute == null) {
            String err = "Attribute `" + fAttributeName.getText()
                + "' is not known in class `" +
                targetcls + "'.";
            throw new SemanticException( fAttributeName, err );
        }

        // Attributtyp immer in einen OCL-Type adaptieren
        Type type = TypeAdapters.asOclType(targetAttribute.type());

        // fSource must be a GValueInstruction
        GInstruction source = fSource.gen(ctx);
        if (! (source instanceof GValueInstruction) ) {
            String err = "`" + source + "' can't be the source of an " +
                "assignment, because it has no return value.";
            throw new SemanticException( fAttributeName, err );
        }

        GValueInstruction valuesource = (GValueInstruction) source;
        // The type of the source must be the same type or
        // a subtype of the target.
        if (! valuesource.type().conformsTo(type)) {
            String err = "Invalid assignment: " +
                "`" + targetinstr + "." +targetAttribute.name()+ "'" +
                " is of type " + type + "." +
                "`" + valuesource + "' is of type " + valuesource.type() + ".";
            throw new SemanticException( fAttributeName, err );
        }

        GAttributeAssignment assignment =
            new GAttributeAssignment(targetinstr,targetAttribute,valuesource);

        return assignment;
    }

    /**
     * Versucht aus einem OCL-Type die zugrunde liegende UML-Klasse zu ermitteln.
     *
     * Behandelt sowohl einfache Objekt-Typen (ClassifierType) als auch
     * Collection-Typen, bei denen die Elementtypen Klassen repräsentieren.
     */
    private MClass resolveTargetClassFromType(Type targetType, MModel model) {
        if (targetType == null) {
            return null;
        }

        Type baseType = targetType;
        if (baseType instanceof CollectionType) {
            baseType = ((CollectionType) baseType).elemType();
        }

        // Erst versuchen, direkt über den Adapter einen MClassifier zu bekommen
        MClassifier classifier = TypeAdapters.asMClassifier(baseType);
        if (classifier instanceof MClass) {
            return (MClass) classifier;
        }

        // Fallback: versuche über den Namen im Modell eine Klasse zu finden
        String typeName = baseType.toString();
        MClass fromModel = model.getClass(typeName);
        if (fromModel != null) {
            return fromModel;
        }

        return null;
    }
}
