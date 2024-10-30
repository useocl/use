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

package org.tzi.use.parser.use;

import org.antlr.runtime.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @author Stefan Schoon
 */
public abstract class ASTClassifier extends ASTAnnotatable {
    protected Token fName;
    protected boolean fIsAbstract;
    protected List<ASTAttribute> fAttributes, fSuperAttributes;
    protected List<ASTOperation> fOperations;
    protected List<Token> fSuperClassifiers;
    protected List<ASTConstraintDefinition> fConstraints;
    protected ArrayList<ASTInvariantClause> fInvariantClauses;

    public ASTClassifier(Token name, boolean isAbstract) {
        this.fName = name;
        this.fIsAbstract = isAbstract;
        fAttributes = new ArrayList<ASTAttribute>();
        fSuperAttributes = new ArrayList<ASTAttribute>();
        fOperations = new ArrayList<ASTOperation>();
        fConstraints = new ArrayList<ASTConstraintDefinition>();
        fInvariantClauses = new ArrayList<ASTInvariantClause>();
    }

    /**
     * @return the isAbstract
     */
    public boolean isAbstract() {
        return fIsAbstract;
    }

    public void addAttribute(ASTAttribute a) {
        fAttributes.add(a);
    }

    public void addSuperAttribute(ASTAttribute a) {
        fSuperAttributes.add(a);
    }

    public void addSuperClassifiers(List<Token> idList) {
        fSuperClassifiers = idList;
    }

    public void addOperation(ASTOperation op) {
        fOperations.add(op);
    }

    public void addInvariantClause(ASTInvariantClause inv) {
        fInvariantClauses.add(inv);
    }

    /**
     * @return the name
     */
    public Token getName() {
        return fName;
    }

    public String toString() {
        return "(" + fName + ")";
    }
}
