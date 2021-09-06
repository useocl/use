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

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.gen.assl.statics.GInstruction;
import org.tzi.use.gen.assl.statics.GInstructionCreator;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.util.StringUtil;

public class ASTGAtomicInstruction extends ASTGValueInstruction {
	Token fName;
    List<Object> fParameter;  // ASTGInstructionParameterInterface;

    public ASTGAtomicInstruction(Token name) {
        fName = name;
        fParameter = new ArrayList<Object>();
    }

    public void addParameter( Object parameter ) {
        fParameter.add(parameter);
    }

    public GInstruction gen(Context ctx) throws SemanticException {

        List<Object> params = new ArrayList<Object>();
        List<Object> errParams = new ArrayList<Object>();

        for (Object param : fParameter) {
            if (param instanceof Token ) {
                String name = ((Token) param).getText();
                boolean isClass = ctx.model().getClass(name) != null;
                boolean isAssociation = ctx.model().getAssociation(name) != null;
                
                if (isClass && isAssociation )
                    errParams.add("Class/Association");
                else if (isClass)
                    errParams.add("Class");
                else if (isAssociation)
                    errParams.add("Association");
                else {
                	boolean isAttribute = false;
                	// Attribute?
                	for (MClass cls : ctx.model().classes()) {
                		if (cls.attribute(name, false) != null) {
                			isAttribute = true;
                			errParams.add("Attribute");
                			params.add( name );
                			break;
                		}
                	}
                	
                	if (isAttribute)
            			continue;
                	
                    String err = "`" + name +"' is not a class or " +
                        "association of the current model. If `" + name +
                        "' is a variable, use squared " +
                        "brackets.";
                    throw new SemanticException(fName, err);
                }
                params.add( name );
            } else {
                GValueInstruction instr =
                    (GValueInstruction) ((ASTGocl) param).gen(ctx);
                params.add( instr );
                errParams.add( instr.type().toString() );
            }
        }

        // params is a list over Strings or GValueInstructions.
        // A containing string is a classname or associationname.

        GInstruction instr;
        instr = GInstructionCreator.instance().create( fName.getText(), 
                                                       params, 
                                                       ctx.model() );
        if (instr == null) {
            String err = "Instruction `" + fName.getText() + "(" + 
                	     StringUtil.fmtSeq(errParams.iterator(), ",") +
                         ")' not found.";
            throw new SemanticException(fName, err);
        }
        return instr;
    }
}
