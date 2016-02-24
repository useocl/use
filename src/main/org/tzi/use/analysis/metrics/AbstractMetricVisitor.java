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
package org.tzi.use.analysis.metrics;

import java.util.Stack;

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
import org.tzi.use.uml.ocl.expr.ExpConstEnum;
import org.tzi.use.uml.ocl.expr.ExpConstInteger;
import org.tzi.use.uml.ocl.expr.ExpConstReal;
import org.tzi.use.uml.ocl.expr.ExpConstString;
import org.tzi.use.uml.ocl.expr.ExpConstUnlimitedNatural;
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
import org.tzi.use.uml.ocl.expr.ExpNavigationClassifierSource;
import org.tzi.use.uml.ocl.expr.ExpObjAsSet;
import org.tzi.use.uml.ocl.expr.ExpObjOp;
import org.tzi.use.uml.ocl.expr.ExpObjRef;
import org.tzi.use.uml.ocl.expr.ExpObjectByUseId;
import org.tzi.use.uml.ocl.expr.ExpOclInState;
import org.tzi.use.uml.ocl.expr.ExpOne;
import org.tzi.use.uml.ocl.expr.ExpOrderedSetLiteral;
import org.tzi.use.uml.ocl.expr.ExpQuery;
import org.tzi.use.uml.ocl.expr.ExpRange;
import org.tzi.use.uml.ocl.expr.ExpReject;
import org.tzi.use.uml.ocl.expr.ExpSelect;
import org.tzi.use.uml.ocl.expr.ExpSelectByKind;
import org.tzi.use.uml.ocl.expr.ExpSelectByType;
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
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;

/**
 * Abstract metrics visitor implementation.
 * 
 * @author ms
 *
 */
public abstract class AbstractMetricVisitor implements ExpressionVisitor {

	protected final MeasurementStrategy measurement;
	protected final boolean expandOperations;

	public AbstractMetricVisitor(MeasurementStrategy measurement, boolean expandOperations) {
		this.measurement = measurement;
		this.expandOperations = expandOperations;
	}

	@Override
	public void visitAllInstances(ExpAllInstances exp) {
	}

	@Override
	public void visitAny(ExpAny exp) {
		visitQuery(exp);
	}

	@Override
	public void visitAsType(ExpAsType exp) {
	}

	@Override
	public void visitAttrOp(ExpAttrOp exp) {
		exp.objExp().processWithVisitor(this);
	}

	@Override
	public void visitBagLiteral(ExpBagLiteral exp) {
		visitCollectionLiteral(exp);
	}

	@Override
	public void visitCollect(ExpCollect exp) {
		visitQuery(exp);
	}

	@Override
	public void visitCollectNested(ExpCollectNested exp) {
		visitQuery(exp);
	}

	@Override
	public void visitConstBoolean(ExpConstBoolean exp) {
	}

	@Override
	public void visitConstEnum(ExpConstEnum exp) {
	}

	@Override
	public void visitConstInteger(ExpConstInteger exp) {
	}

	@Override
	public void visitConstReal(ExpConstReal exp) {
	}

	@Override
	public void visitConstString(ExpConstString exp) {
	}

	@Override
	public void visitEmptyCollection(ExpEmptyCollection exp) {
	}

	@Override
	public void visitExists(ExpExists exp) {
		visitQuery(exp);
	}

	@Override
	public void visitForAll(ExpForAll exp) {
		visitQuery(exp);
	}

	@Override
	public void visitIf(ExpIf exp) {
		exp.getCondition().processWithVisitor(this);
		exp.getThenExpression().processWithVisitor(this);
		exp.getElseExpression().processWithVisitor(this);
	}

	@Override
	public void visitIsKindOf(ExpIsKindOf exp) {
		exp.getSourceExpr().processWithVisitor(this);
	}

	@Override
	public void visitIsTypeOf(ExpIsTypeOf exp) {
		exp.getSourceExpr().processWithVisitor(this);
	}

	@Override
	public void visitIsUnique(ExpIsUnique exp) {
		visitQuery(exp);
	}

	@Override
	public void visitIterate(ExpIterate exp) {
		visitQuery(exp);
	}

	@Override
	public void visitLet(ExpLet exp) {
		exp.getVarExpression().processWithVisitor(this);
		exp.getInExpression().processWithVisitor(this);
	}

	@Override
	public void visitNavigation(ExpNavigation exp) {
		exp.getObjectExpression().processWithVisitor(this);
	}

	@Override
	public void visitNavigationClassifierSource(
			ExpNavigationClassifierSource exp) {
		exp.getObjectExpression().processWithVisitor(this);
	}

	@Override
	public void visitObjAsSet(ExpObjAsSet exp) {
		exp.getObjectExpression().processWithVisitor(this);
	}

	private Stack<MOperation> operationStack = new Stack<MOperation>();

	@Override
	public void visitObjOp(ExpObjOp exp) {
		for (Expression ex : exp.getArguments()) {
			ex.processWithVisitor(this);
		}

		if (expandOperations && exp.getOperation().hasExpression()
				&& !operationStack.contains(exp.getOperation())) {
			operationStack.push(exp.getOperation());
			exp.getOperation().expression().processWithVisitor(this);
			operationStack.pop();
		}
	}

	@Override
	public void visitObjRef(ExpObjRef exp) {
		exp.processWithVisitor(this);
	}

	@Override
	public void visitOne(ExpOne exp) {
		visitQuery(exp);
	}

	@Override
	public void visitOrderedSetLiteral(ExpOrderedSetLiteral exp) {
		visitCollectionLiteral(exp);
	}

	@Override
	public void visitQuery(ExpQuery exp) {
		exp.getRangeExpression().processWithVisitor(this);
		exp.getQueryExpression().processWithVisitor(this);
	}

	@Override
	public void visitReject(ExpReject exp) {
		visitQuery(exp);
	}

	@Override
	public void visitWithValue(ExpressionWithValue exp) {
	}

	@Override
	public void visitSelect(ExpSelect exp) {
		visitQuery(exp);
	}

	@Override
	public void visitSequenceLiteral(ExpSequenceLiteral exp) {
		visitCollectionLiteral(exp);
	}

	@Override
	public void visitSetLiteral(ExpSetLiteral exp) {
		visitCollectionLiteral(exp);
	}

	@Override
	public void visitSortedBy(ExpSortedBy exp) {
		visitQuery(exp);
	}

	@Override
	public void visitStdOp(ExpStdOp exp) {
		for (Expression expArg : exp.args()) {
			expArg.processWithVisitor(this);
		}
	}

	@Override
	public void visitTupleLiteral(ExpTupleLiteral exp) {
		for (ExpTupleLiteral.Part part : exp.getParts()) {
			part.getExpression().processWithVisitor(this);
		}
	}

	@Override
	public void visitTupleSelectOp(ExpTupleSelectOp exp) {
	}

	@Override
	public void visitUndefined(ExpUndefined exp) {
	}

	@Override
	public void visitVariable(ExpVariable exp) {
	}

	protected void visitCollectionLiteral(ExpCollectionLiteral exp) {
		for (Expression ex : exp.getElemExpr()) {
			ex.processWithVisitor(this);
		}
	}

	@Override
	public void visitClosure(ExpClosure expClosure) {
		visitQuery(expClosure);
	}

	@Override
	public void visitOclInState(ExpOclInState expOclInState) {
		expOclInState.getSourceExpr().processWithVisitor(this);
	}

	@Override
	public void visitVarDeclList(VarDeclList varDeclList) {
		for (int i = 0; i < varDeclList.size(); ++i) {
			varDeclList.varDecl(i).processWithVisitor(this);
		}
	}

	@Override
	public void visitVarDecl(VarDecl varDecl) {
	}

	@Override
	public void visitObjectByUseId(ExpObjectByUseId expObjectByUseId) {
		expObjectByUseId.getIdExpression().processWithVisitor(this);
	}

	@Override
	public void visitConstUnlimitedNatural(
			ExpConstUnlimitedNatural expressionConstUnlimitedNatural) {
	}

	@Override
	public void visitSelectByKind(ExpSelectByKind expSelectByKind) {
		expSelectByKind.getSourceExpression().processWithVisitor(this);
	}

	@Override
	public void visitExpSelectByType(ExpSelectByType expSelectByType) {
		expSelectByType.getSourceExpression().processWithVisitor(this);
	}

	@Override
	public void visitRange(ExpRange exp) {
		exp.getStart().processWithVisitor(this);
		exp.getEnd().processWithVisitor(this);
	}
}
