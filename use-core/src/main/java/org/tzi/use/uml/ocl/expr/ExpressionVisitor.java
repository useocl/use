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

// $Id$

package org.tzi.use.uml.ocl.expr;

/**
 * Visitor interface for expressions
 * @author Lars Hamann
 *
 */
public interface ExpressionVisitor {
	void visitAllInstances (ExpAllInstances exp);
	void visitAny (ExpAny exp);
	void visitAsType (ExpAsType exp);
	void visitAttrOp (ExpAttrOp exp);
	void visitBagLiteral (ExpBagLiteral exp);
	void visitCollect (ExpCollect exp);
	void visitCollectNested (ExpCollectNested exp);
	void visitConstBoolean (ExpConstBoolean exp);
	void visitConstEnum (ExpConstEnum exp);
	void visitConstInteger (ExpConstInteger exp);
	void visitConstReal (ExpConstReal exp);
	void visitConstString (ExpConstString exp);
	void visitEmptyCollection (ExpEmptyCollection exp);
	void visitExists (ExpExists exp);
	void visitForAll (ExpForAll exp);
	void visitIf (ExpIf exp);
	void visitIsKindOf (ExpIsKindOf exp);
	void visitIsTypeOf (ExpIsTypeOf exp);
	void visitIsUnique (ExpIsUnique exp);
	void visitIterate (ExpIterate exp);
	void visitLet (ExpLet exp);
	void visitNavigation (ExpNavigation exp);
	void visitObjAsSet (ExpObjAsSet exp);
	void visitObjOp (ExpObjOp exp);
	void visitObjRef (ExpObjRef exp);
	void visitOne (ExpOne exp);
	void visitOrderedSetLiteral (ExpOrderedSetLiteral exp);
	void visitQuery (ExpQuery exp);
	void visitReject (ExpReject exp);
	void visitWithValue (ExpressionWithValue exp);
	void visitSelect (ExpSelect exp);
	void visitSequenceLiteral (ExpSequenceLiteral exp);
	void visitSetLiteral (ExpSetLiteral exp);
	void visitSortedBy (ExpSortedBy exp);
	void visitStdOp (ExpStdOp exp);
	void visitTupleLiteral (ExpTupleLiteral exp);
	void visitTupleSelectOp (ExpTupleSelectOp exp);
	void visitUndefined (ExpUndefined exp);
	void visitVariable (ExpVariable exp);
	void visitClosure(ExpClosure exp);
	void visitOclInState(ExpOclInState exp);
	void visitVarDeclList(VarDeclList varDeclList);
	void visitVarDecl(VarDecl varDecl);
	void visitObjectByUseId(ExpObjectByUseId exp);
	void visitConstUnlimitedNatural(ExpConstUnlimitedNatural exp);
	void visitSelectByKind(ExpSelectByKind exp);
	void visitExpSelectByType(ExpSelectByType exp);
	void visitRange(ExpRange exp);
	void visitNavigationClassifierSource(ExpNavigationClassifierSource exp);
}
