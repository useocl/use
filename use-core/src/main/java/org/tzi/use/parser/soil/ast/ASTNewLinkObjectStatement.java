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

package org.tzi.use.parser.soil.ast;

import org.antlr.runtime.Token;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.parser.ocl.ASTSimpleType;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.sys.soil.MNewLinkObjectStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * AST class for a new link object statement
 * @author Daniel Gent
 * @author Lars Hamann
 *
 */
public class ASTNewLinkObjectStatement extends ASTStatement {

    private ASTSimpleType fAssociationClassName;

    private List<ASTRValue> fParticipants;

    /** Expression to retrieve the new object name from can be <code>null</code> */
    private ASTExpression fLinkObjectName;

    /**
     * The List of the provided qualifiers
     */
    private List<List<ASTRValue>> qualifierValues;


    /**
     * Constructs a new ASTNewLinkObjectStatement node using the provided information.
     * @param associationClassName
     * @param participants
     * @param qualifierValues
     * @param linkObjectName
     */
    public ASTNewLinkObjectStatement(
            Token start,
            ASTSimpleType associationClassName,
            List<ASTRValue> participants,
            List<List<ASTRValue>> qualifierValues,
            ASTExpression linkObjectName) {
        super(start);
        fAssociationClassName = associationClassName;
        fParticipants = participants;
        fLinkObjectName = linkObjectName;
        this.qualifierValues = qualifierValues;
    }


    /**
     * Constructs a new ASTNewLinkObjectStatement node using the
     * provided information.
     * <p>The object name is set by the system.</p>
     * @param associationClassName
     * @param participants
     * @param qualifierValues
     */
    public ASTNewLinkObjectStatement(
            Token start,
            ASTSimpleType associationClassName,
            List<ASTRValue> participants,
            List<List<ASTRValue>> qualifierValues) {

        this(start, associationClassName, participants, qualifierValues, null);
    }

    @Override
    protected MStatement generateStatement() throws CompilationFailedException {

        // Use the token text of the ASTSimpleType for lookup instead of
        // relying on its toString() (which defaults to Object.toString()).
        String associationClassNameText = fAssociationClassName.getName().getText();

        MAssociationClass associationClass =
                fContext.model().getAssociationClass(associationClassNameText);

        if (associationClass == null) {
            throw new CompilationFailedException(this, "Association class "
                    + StringUtil.inQuotes(associationClassNameText) + " does not exist.");
        }

        List<MRValue> participants =
            generateAssociationParticipants(
                    associationClass,
                    fParticipants);

        List<List<MRValue>> qualifierRValues;
        if (this.qualifierValues == null || this.qualifierValues.isEmpty()) {
            qualifierRValues = Collections.emptyList();
        } else {
            qualifierRValues = new ArrayList<List<MRValue>>();

            for (List<ASTRValue> endQualifierValues : this.qualifierValues ) {
                List<MRValue> endQualifierRValues;

                if (endQualifierValues == null || endQualifierValues.isEmpty()) {
                    endQualifierRValues = Collections.emptyList();
                } else {
                    endQualifierRValues = new ArrayList<MRValue>();

                    for (ASTRValue value : endQualifierValues) {
                        endQualifierRValues.add(value.generate(this));
                    }
                }
                qualifierRValues.add(endQualifierRValues);
            }
        }

        return new MNewLinkObjectStatement(
                associationClass,
                participants,
                qualifierRValues,
                (fLinkObjectName == null ?
                        null : generateStringExpression(fLinkObjectName)));
    }


    @Override
    protected void printTree(StringBuilder prelude, PrintWriter target) {
        target.println(prelude + "[LINK OBJECT CREATION]");
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Use the actual text of the simple type token for a readable
        // string representation (avoid Object.toString result).
        sb.append(fAssociationClassName.getName().getText());
        if (fLinkObjectName != null) {
            sb.append(" ");
            sb.append(fLinkObjectName);
        }
        sb.append(" between (");

        StringUtil.fmtSeqWithSubSeq(sb, fParticipants, ",", qualifierValues, ",", "{", "}");

        sb.append(")");

        return sb.toString();
    }
}
