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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.uml.mm;

import org.tzi.use.uml.ocl.type.Type;

/**
 * An Attribute is a model element that is part of a Class.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public final class MAttribute extends MModelElementImpl {
    private MClass fOwner;
    private Type fType;
    private int fPositionInModel;

    /**
     * Creates an attribute with given name and type.
     */
    MAttribute(String name, Type type) {
        super(name);
        fType = type;
    }

    void setOwner(MClass owner) {
        fOwner = owner;
    }

    /**
     * Returns the owner class of this attribute.
     */
    public MClass owner() {
        return fOwner;
    }

    /**
     * Returns the type of this attribute.
     */
    public Type type() {
        return fType;
    }

    /**
     * Returns the position in the defined USE-Model.
     */
    public int getPositionInModel() {
        return fPositionInModel;
    }

    /**
     * Sets the position in the defined USE-Model.
     */
    public void setPositionInModel(int position) {
        fPositionInModel = position;
    }
    
    public String toString() {
        return name() + " : " + fType;
    }

    /**
     * Process this element with visitor.
     */
    public void processWithVisitor(MMVisitor v) {
        v.visitAttribute(this);
    }
}
