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

package org.tzi.use.analysis.coverage;

import java.util.Stack;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.ExpAllInstances;
import org.tzi.use.uml.ocl.expr.ExpAny;
import org.tzi.use.uml.ocl.expr.ExpAsType;
import org.tzi.use.uml.ocl.expr.ExpAttrOp;
import org.tzi.use.uml.ocl.expr.ExpBagLiteral;
import org.tzi.use.uml.ocl.expr.ExpClosure;
import org.tzi.use.uml.ocl.expr.ExpCollect;
import org.tzi.use.uml.ocl.expr.ExpCollectNested;
import org.tzi.use.uml.ocl.expr.ExpCollectionLiteral;
import org.tzi.use.uml.ocl.expr.ExpConstBoolean;
import org.tzi.use.uml.ocl.expr.ExpConstDate;
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
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpressionVisitor;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.type.ObjectType;

/**
 * TODO
 * @author lhamann
 *
 */
public class CoverageCalculationVisitor implements ExpressionVisitor {

	private CoverageData coverageData = new CoverageData();
	
	/**
	 * @param cls
	 */
	private void addClassCoverage(MClass cls) {
		if (!coverageData.getClassCoverage().containsKey(cls)) {
			coverageData.getClassCoverage().put(cls, 1);
		} else {
			coverageData.getClassCoverage().put(cls, coverageData.getClassCoverage().get(cls) + 1);
		}
		addCompleteClassCoverage(cls);
	}
	
	/**
	 * @param cls
	 */
	private void addCompleteClassCoverage(MClass cls) {
		if (!coverageData.getCompleteClassCoverage().containsKey(cls)) {
			coverageData.getCompleteClassCoverage().put(cls, 1);
		} else {
			coverageData.getCompleteClassCoverage().put(cls, coverageData.getCompleteClassCoverage().get(cls) + 1);
		}
	}
	
	/**
	 * @param sourceType
	 */
	private void addAttributeCoverage(MClass sourceClass, MAttribute att) {
		AttributeAccessInfo info = new AttributeAccessInfo(sourceClass, att);
		if (!coverageData.getAttributeAccessCoverage().containsKey(info)) {
			coverageData.getAttributeAccessCoverage().put(info, 1);
		} else {
			coverageData.getAttributeAccessCoverage().put(info, coverageData.getAttributeAccessCoverage().get(info) + 1);
		}
		if (!coverageData.getAttributeCoverage().containsKey(att)) {
			coverageData.getAttributeCoverage().put(att, 1);
		} else {
			coverageData.getAttributeCoverage().put(att, coverageData.getAttributeCoverage().get(att) + 1);
		}
		addCompleteClassCoverage(sourceClass);
	}
	
	/**
	 * @param sourceType
	 */
	private void addAssociationCoverage(MAssociation assoc) {
		if (!coverageData.getAssociationCoverage().containsKey(assoc)) {
			coverageData.getAssociationCoverage().put(assoc, 1);
		} else {
			coverageData.getAssociationCoverage().put(assoc, coverageData.getAssociationCoverage().get(assoc) + 1);
		}
	}
	
	/**
	 * @param sourceType
	 */
	private void addAssociationEndCoverage(MNavigableElement dst) {
		//FIXME: How to handle association class?
		if (!(dst instanceof MAssociationEnd)) return;
		MAssociationEnd end = (MAssociationEnd)dst;
		
		if (!coverageData.getAssociationEndCoverage().containsKey(end)) {
			coverageData.getAssociationEndCoverage().put(end, 1);
		} else {
			coverageData.getAssociationEndCoverage().put(end, coverageData.getAssociationEndCoverage().get(end) + 1);
		}
		
		addCompleteClassCoverage(end.cls());
	}
	
	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitAllInstances(org.tzi.use.uml.ocl.expr.ExpAllInstances)
	 */
	@Override
	public void visitAllInstances(ExpAllInstances exp) {
		addClassCoverage(exp.getSourceType().cls());
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitAny(org.tzi.use.uml.ocl.expr.ExpAny)
	 */
	@Override
	public void visitAny(ExpAny exp) {
		visitQuery(exp);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitAsType(org.tzi.use.uml.ocl.expr.ExpAsType)
	 */
	@Override
	public void visitAsType(ExpAsType exp) {
		// Needed?
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitAttrOp(org.tzi.use.uml.ocl.expr.ExpAttrOp)
	 */
	@Override
	public void visitAttrOp(ExpAttrOp exp) {
		exp.objExp().processWithVisitor(this);
		addAttributeCoverage(((ObjectType)exp.objExp().type()).cls(), exp.attr());
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitBagLiteral(org.tzi.use.uml.ocl.expr.ExpBagLiteral)
	 */
	@Override
	public void visitBagLiteral(ExpBagLiteral exp) {
		visitCollectionLiteral(exp);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitCollect(org.tzi.use.uml.ocl.expr.ExpCollect)
	 */
	@Override
	public void visitCollect(ExpCollect exp) {
		visitQuery(exp);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitCollectNested(org.tzi.use.uml.ocl.expr.ExpCollectNested)
	 */
	@Override
	public void visitCollectNested(ExpCollectNested exp) {
		visitQuery(exp);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitConstBoolean(org.tzi.use.uml.ocl.expr.ExpConstBoolean)
	 */
	@Override
	public void visitConstBoolean(ExpConstBoolean exp) {}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitConstDate(org.tzi.use.uml.ocl.expr.ExpConstDate)
	 */
	@Override
	public void visitConstDate(ExpConstDate exp) {}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitConstEnum(org.tzi.use.uml.ocl.expr.ExpConstEnum)
	 */
	@Override
	public void visitConstEnum(ExpConstEnum exp) {
		// TODO: Coverage?
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitConstInteger(org.tzi.use.uml.ocl.expr.ExpConstInteger)
	 */
	@Override
	public void visitConstInteger(ExpConstInteger exp) {}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitConstReal(org.tzi.use.uml.ocl.expr.ExpConstReal)
	 */
	@Override
	public void visitConstReal(ExpConstReal exp) {}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitConstString(org.tzi.use.uml.ocl.expr.ExpConstString)
	 */
	@Override
	public void visitConstString(ExpConstString exp) {}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitEmptyCollection(org.tzi.use.uml.ocl.expr.ExpEmptyCollection)
	 */
	@Override
	public void visitEmptyCollection(ExpEmptyCollection exp) {
		
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitExists(org.tzi.use.uml.ocl.expr.ExpExists)
	 */
	@Override
	public void visitExists(ExpExists exp) {
		visitQuery(exp);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitForAll(org.tzi.use.uml.ocl.expr.ExpForAll)
	 */
	@Override
	public void visitForAll(ExpForAll exp) {
		visitQuery(exp);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitIf(org.tzi.use.uml.ocl.expr.ExpIf)
	 */
	@Override
	public void visitIf(ExpIf exp) {
		exp.getCondition().processWithVisitor(this);
		exp.getThenExpression().processWithVisitor(this);
		exp.getElseExpression().processWithVisitor(this);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitIsKindOf(org.tzi.use.uml.ocl.expr.ExpIsKindOf)
	 */
	@Override
	public void visitIsKindOf(ExpIsKindOf exp) {
		exp.getSourceExpr().processWithVisitor(this);
		//TODO: Check type for coverage?
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitIsTypeOf(org.tzi.use.uml.ocl.expr.ExpIsTypeOf)
	 */
	@Override
	public void visitIsTypeOf(ExpIsTypeOf exp) {
		exp.getSourceExpr().processWithVisitor(this);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitIsUnique(org.tzi.use.uml.ocl.expr.ExpIsUnique)
	 */
	@Override
	public void visitIsUnique(ExpIsUnique exp) {
		visitQuery(exp);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitIterate(org.tzi.use.uml.ocl.expr.ExpIterate)
	 */
	@Override
	public void visitIterate(ExpIterate exp) {
		visitQuery(exp);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitLet(org.tzi.use.uml.ocl.expr.ExpLet)
	 */
	@Override
	public void visitLet(ExpLet exp) {
		exp.getVarExpression().processWithVisitor(this);
		exp.getInExpression().processWithVisitor(this);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitNavigation(org.tzi.use.uml.ocl.expr.ExpNavigation)
	 */
	@Override
	public void visitNavigation(ExpNavigation exp) {
		exp.getObjectExpression().processWithVisitor(this);
		addAssociationCoverage(exp.getDestination().association());
		addAssociationEndCoverage(exp.getDestination());
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitObjAsSet(org.tzi.use.uml.ocl.expr.ExpObjAsSet)
	 */
	@Override
	public void visitObjAsSet(ExpObjAsSet exp) {
		exp.getObjectExpression().processWithVisitor(this);
	}

	private Stack<MOperation> operationStack = new Stack<MOperation>();
	
	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitObjOp(org.tzi.use.uml.ocl.expr.ExpObjOp)
	 */
	@Override
	public void visitObjOp(ExpObjOp exp) {
		for (Expression ex : exp.getArguments()) {
			ex.processWithVisitor(this);
		}
		if (exp.getOperation().hasExpression() && !operationStack.contains(exp.getOperation())) {
			operationStack.push(exp.getOperation());
			exp.getOperation().expression().processWithVisitor(this);
			operationStack.pop();
		}
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitObjRef(org.tzi.use.uml.ocl.expr.ExpObjRef)
	 */
	@Override
	public void visitObjRef(ExpObjRef exp) {
		// TODO: Needed?
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitOne(org.tzi.use.uml.ocl.expr.ExpOne)
	 */
	@Override
	public void visitOne(ExpOne exp) {
		visitQuery(exp);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitOrderedSetLiteral(org.tzi.use.uml.ocl.expr.ExpOrderedSetLiteral)
	 */
	@Override
	public void visitOrderedSetLiteral(ExpOrderedSetLiteral exp) {
		visitCollectionLiteral(exp);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitQuery(org.tzi.use.uml.ocl.expr.ExpQuery)
	 */
	@Override
	public void visitQuery(ExpQuery exp) {
		exp.getRangeExpression().processWithVisitor(this);
		exp.getQueryExpression().processWithVisitor(this);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitReject(org.tzi.use.uml.ocl.expr.ExpReject)
	 */
	@Override
	public void visitReject(ExpReject exp) {
		visitQuery(exp);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitWithValue(org.tzi.use.uml.ocl.expr.ExpressionWithValue)
	 */
	@Override
	public void visitWithValue(ExpressionWithValue exp) {
		
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitSelect(org.tzi.use.uml.ocl.expr.ExpSelect)
	 */
	@Override
	public void visitSelect(ExpSelect exp) {
		visitQuery(exp);

	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitSequenceLiteral(org.tzi.use.uml.ocl.expr.ExpSequenceLiteral)
	 */
	@Override
	public void visitSequenceLiteral(ExpSequenceLiteral exp) {
		visitCollectionLiteral(exp);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitSetLiteral(org.tzi.use.uml.ocl.expr.ExpSetLiteral)
	 */
	@Override
	public void visitSetLiteral(ExpSetLiteral exp) {
		visitCollectionLiteral(exp);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitSortedBy(org.tzi.use.uml.ocl.expr.ExpSortedBy)
	 */
	@Override
	public void visitSortedBy(ExpSortedBy exp) {
		visitQuery(exp);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitStdOp(org.tzi.use.uml.ocl.expr.ExpStdOp)
	 */
	@Override
	public void visitStdOp(ExpStdOp exp) {
		for (Expression expArg : exp.args()) {
			expArg.processWithVisitor(this);
		}
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitTupleLiteral(org.tzi.use.uml.ocl.expr.ExpTupleLiteral)
	 */
	@Override
	public void visitTupleLiteral(ExpTupleLiteral exp) {
		for (ExpTupleLiteral.Part part : exp.getParts()) {
			part.getExpression().processWithVisitor(this);
		}
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitTupleSelectOp(org.tzi.use.uml.ocl.expr.ExpTupleSelectOp)
	 */
	@Override
	public void visitTupleSelectOp(ExpTupleSelectOp exp) {}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitUndefined(org.tzi.use.uml.ocl.expr.ExpUndefined)
	 */
	@Override
	public void visitUndefined(ExpUndefined exp) {}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitVariable(org.tzi.use.uml.ocl.expr.ExpVariable)
	 */
	@Override
	public void visitVariable(ExpVariable exp) {
		if (exp.type().isTrueObjectType()) {
			addClassCoverage(((ObjectType)exp.type()).cls());
		}
	}
	
	protected void visitCollectionLiteral(ExpCollectionLiteral exp) {
		for (Expression ex : exp.getElemExpr()) {
			ex.processWithVisitor(this);
		}
	}

	/**
	 * Returns the collected information about the coverage
	 * @return The collected coverage data
	 */
	public CoverageData getCoverageData() {
		return coverageData;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.ExpressionVisitor#visitClosure(org.tzi.use.uml.ocl.expr.ExpClosure)
	 */
	@Override
	public void visitClosure(ExpClosure expClosure) {
		visitQuery(expClosure);
	}
}
