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

/**
 * Visitor interface for model elements.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public interface MMVisitor {
    void visitAssociation(MAssociation e);
    void visitAssociationClass( MAssociationClass e );
    void visitAssociationEnd(MAssociationEnd e);
    void visitAttribute(MAttribute e);
    void visitClass(MClass e);
    void visitClassInvariant(MClassInvariant e);
    void visitGeneralization(MGeneralization e);
    void visitModel(MModel e);
    void visitOperation(MOperation e);
    void visitPrePostCondition(MPrePostCondition e);
    
    // AL
    void visitALActionList(ALActionList e);
    void visitALFor(ALFor e);
    void visitALDestroyObject(ALDestroyObject e);
    void visitALIf(ALIf e);
    void visitALSet(ALSet e);
    void visitALSetCreate(ALSetCreate e);
    void visitALInsert(ALInsert e);
    void visitALDelete(ALDelete e);
    void visitALExecute(ALExecute e);
    void visitALCreateVar(ALCreateVar e);
    void visitALCreateObject(ALCreateObject e);
    void visitALWhile(ALWhile e);
}
