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

package org.tzi.use.uml.ocl.expr.visitor;

import org.tzi.use.uml.ocl.expr.ExpAllInstances;
import org.tzi.use.uml.ocl.expr.ExpAny;
import org.tzi.use.uml.ocl.expr.ExpAsType;
import org.tzi.use.uml.ocl.expr.ExpAttrOp;
import org.tzi.use.uml.ocl.expr.ExpBagLiteral;
import org.tzi.use.uml.ocl.expr.ExpClosure;
import org.tzi.use.uml.ocl.expr.ExpCollect;
import org.tzi.use.uml.ocl.expr.ExpCollectNested;
import org.tzi.use.uml.ocl.expr.ExpConstBoolean;
import org.tzi.use.uml.ocl.expr.ExpConstEnum;
import org.tzi.use.uml.ocl.expr.ExpConstInteger;
import org.tzi.use.uml.ocl.expr.ExpConstReal;
import org.tzi.use.uml.ocl.expr.ExpConstString;
import org.tzi.use.uml.ocl.expr.ExpEmptyCollection;
import org.tzi.use.uml.ocl.expr.ExpExists;
import org.tzi.use.uml.ocl.expr.ExpForAll;
import org.tzi.use.uml.ocl.expr.ExpIf;
import org.tzi.use.uml.ocl.expr.ExpIsKindOf;
import org.tzi.use.uml.ocl.expr.ExpIsTypeOf;
import org.tzi.use.uml.ocl.expr.ExpIsUnique;
import org.tzi.use.uml.ocl.expr.ExpIterate;
import org.tzi.use.uml.ocl.expr.ExpLet;
import org.tzi.use.uml.ocl.expr.ExpNavigation;
import org.tzi.use.uml.ocl.expr.ExpObjAsSet;
import org.tzi.use.uml.ocl.expr.ExpObjOp;
import org.tzi.use.uml.ocl.expr.ExpObjRef;
import org.tzi.use.uml.ocl.expr.ExpOne;
import org.tzi.use.uml.ocl.expr.ExpOrderedSetLiteral;
import org.tzi.use.uml.ocl.expr.ExpQuery;
import org.tzi.use.uml.ocl.expr.ExpReject;
import org.tzi.use.uml.ocl.expr.ExpSelect;
import org.tzi.use.uml.ocl.expr.ExpSequenceLiteral;
import org.tzi.use.uml.ocl.expr.ExpSetLiteral;
import org.tzi.use.uml.ocl.expr.ExpSortedBy;
import org.tzi.use.uml.ocl.expr.ExpStdOp;
import org.tzi.use.uml.ocl.expr.ExpTupleLiteral;
import org.tzi.use.uml.ocl.expr.ExpTupleSelectOp;
import org.tzi.use.uml.ocl.expr.ExpUndefined;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.ExpressionVisitor;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;

/**
 * TODO
 * @author lhamann
 *
 */
public class TypeVisitor implements ExpressionVisitor {

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitAllInstances(org.tzi.use.uml.ocl.expr.ExpAllInstances)
	 */
	@Override
	public void visitAllInstances(ExpAllInstances exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitAny(org.tzi.use.uml.ocl.expr.ExpAny)
	 */
	@Override
	public void visitAny(ExpAny exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitAsType(org.tzi.use.uml.ocl.expr.ExpAsType)
	 */
	@Override
	public void visitAsType(ExpAsType exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitAttrOp(org.tzi.use.uml.ocl.expr.ExpAttrOp)
	 */
	@Override
	public void visitAttrOp(ExpAttrOp exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitBagLiteral(org.tzi.use.uml.ocl.expr.ExpBagLiteral)
	 */
	@Override
	public void visitBagLiteral(ExpBagLiteral exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitCollect(org.tzi.use.uml.ocl.expr.ExpCollect)
	 */
	@Override
	public void visitCollect(ExpCollect exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitCollectNested(org.tzi.use.uml.ocl.expr.ExpCollectNested)
	 */
	@Override
	public void visitCollectNested(ExpCollectNested exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitConstBoolean(org.tzi.use.uml.ocl.expr.ExpConstBoolean)
	 */
	@Override
	public void visitConstBoolean(ExpConstBoolean exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitConstEnum(org.tzi.use.uml.ocl.expr.ExpConstEnum)
	 */
	@Override
	public void visitConstEnum(ExpConstEnum exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitConstInteger(org.tzi.use.uml.ocl.expr.ExpConstInteger)
	 */
	@Override
	public void visitConstInteger(ExpConstInteger exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitConstReal(org.tzi.use.uml.ocl.expr.ExpConstReal)
	 */
	@Override
	public void visitConstReal(ExpConstReal exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitConstString(org.tzi.use.uml.ocl.expr.ExpConstString)
	 */
	@Override
	public void visitConstString(ExpConstString exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitEmptyCollection(org.tzi.use.uml.ocl.expr.ExpEmptyCollection)
	 */
	@Override
	public void visitEmptyCollection(ExpEmptyCollection exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitExists(org.tzi.use.uml.ocl.expr.ExpExists)
	 */
	@Override
	public void visitExists(ExpExists exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitForAll(org.tzi.use.uml.ocl.expr.ExpForAll)
	 */
	@Override
	public void visitForAll(ExpForAll exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitIf(org.tzi.use.uml.ocl.expr.ExpIf)
	 */
	@Override
	public void visitIf(ExpIf exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitIsKindOf(org.tzi.use.uml.ocl.expr.ExpIsKindOf)
	 */
	@Override
	public void visitIsKindOf(ExpIsKindOf exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitIsTypeOf(org.tzi.use.uml.ocl.expr.ExpIsTypeOf)
	 */
	@Override
	public void visitIsTypeOf(ExpIsTypeOf exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitIsUnique(org.tzi.use.uml.ocl.expr.ExpIsUnique)
	 */
	@Override
	public void visitIsUnique(ExpIsUnique exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitIterate(org.tzi.use.uml.ocl.expr.ExpIterate)
	 */
	@Override
	public void visitIterate(ExpIterate exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitLet(org.tzi.use.uml.ocl.expr.ExpLet)
	 */
	@Override
	public void visitLet(ExpLet exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitNavigation(org.tzi.use.uml.ocl.expr.ExpNavigation)
	 */
	@Override
	public void visitNavigation(ExpNavigation exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitObjAsSet(org.tzi.use.uml.ocl.expr.ExpObjAsSet)
	 */
	@Override
	public void visitObjAsSet(ExpObjAsSet exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitObjOp(org.tzi.use.uml.ocl.expr.ExpObjOp)
	 */
	@Override
	public void visitObjOp(ExpObjOp exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitObjRef(org.tzi.use.uml.ocl.expr.ExpObjRef)
	 */
	@Override
	public void visitObjRef(ExpObjRef exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitOne(org.tzi.use.uml.ocl.expr.ExpOne)
	 */
	@Override
	public void visitOne(ExpOne exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitOrderedSetLiteral(org.tzi.use.uml.ocl.expr.ExpOrderedSetLiteral)
	 */
	@Override
	public void visitOrderedSetLiteral(ExpOrderedSetLiteral exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitQuery(org.tzi.use.uml.ocl.expr.ExpQuery)
	 */
	@Override
	public void visitQuery(ExpQuery exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitReject(org.tzi.use.uml.ocl.expr.ExpReject)
	 */
	@Override
	public void visitReject(ExpReject exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitWithValue(org.tzi.use.uml.ocl.expr.ExpressionWithValue)
	 */
	@Override
	public void visitWithValue(ExpressionWithValue exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitSelect(org.tzi.use.uml.ocl.expr.ExpSelect)
	 */
	@Override
	public void visitSelect(ExpSelect exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitSequenceLiteral(org.tzi.use.uml.ocl.expr.ExpSequenceLiteral)
	 */
	@Override
	public void visitSequenceLiteral(ExpSequenceLiteral exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitSetLiteral(org.tzi.use.uml.ocl.expr.ExpSetLiteral)
	 */
	@Override
	public void visitSetLiteral(ExpSetLiteral exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitSortedBy(org.tzi.use.uml.ocl.expr.ExpSortedBy)
	 */
	@Override
	public void visitSortedBy(ExpSortedBy exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitStdOp(org.tzi.use.uml.ocl.expr.ExpStdOp)
	 */
	@Override
	public void visitStdOp(ExpStdOp exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitTupleLiteral(org.tzi.use.uml.ocl.expr.ExpTupleLiteral)
	 */
	@Override
	public void visitTupleLiteral(ExpTupleLiteral exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitTupleSelectOp(org.tzi.use.uml.ocl.expr.ExpTupleSelectOp)
	 */
	@Override
	public void visitTupleSelectOp(ExpTupleSelectOp exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitUndefined(org.tzi.use.uml.ocl.expr.ExpUndefined)
	 */
	@Override
	public void visitUndefined(ExpUndefined exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitVariable(org.tzi.use.uml.ocl.expr.ExpVariable)
	 */
	@Override
	public void visitVariable(ExpVariable exp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitClosure(org.tzi.use.uml.ocl.expr.ExpClosure)
	 */
	@Override
	public void visitClosure(ExpClosure expClosure) {
		// TODO Auto-generated method stub

	}

}
