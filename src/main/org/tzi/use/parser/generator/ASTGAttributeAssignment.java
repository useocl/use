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
import org.tzi.use.uml.ocl.type.Type;

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
        MClass targetcls = (MClass)targetinstr.type();
        MAttribute targetAttribute
            = targetcls.attribute(fAttributeName.getText(), true);
        if (targetAttribute == null) {
            String err = "Attribute `" + fAttributeName.getText() 
                + "' is not known in class `" +
                targetcls + "'.";
            throw new SemanticException( fAttributeName, err );
        }
        Type type = targetAttribute.type();

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
}
