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

package org.tzi.use.analysis.coverage;

import org.tzi.use.uml.mm.*;
import org.tzi.use.uml.ocl.expr.*;

import java.util.Stack;

/**
 * Abstract visitor implementation.
 * 
 * @author Lars Hamann
 *
 */
public abstract class AbstractCoverageVisitor implements ExpressionVisitor {

	protected final boolean expandOperations;

	public AbstractCoverageVisitor(boolean expandOperations) {
		this.expandOperations = expandOperations;
	}

	protected abstract void addDataTypeCoverage(MDataType cls);

	protected abstract void addClassCoverage(MClass cls);

	protected abstract void addAssociationEndCoverage(MNavigableElement dst);

	protected abstract void addAssociationCoverage(MAssociation assoc);

	protected abstract void addAttributeCoverage(MClassifier sourceClassifier,
			MAttribute att);

	protected abstract void addOperationCoverage(MClassifier sourceClassifier,
			MOperation att);

	@Override
	public void visitAllInstances(ExpAllInstances exp) {
		if (exp.getSourceType() instanceof MDataType) {
			addDataTypeCoverage((MDataType) exp.getSourceType());
		} else if (exp.getSourceType() instanceof MClass) {
			addClassCoverage((MClass) exp.getSourceType());
		} else if (exp.getSourceType() instanceof MAssociation) {
			addAssociationCoverage((MAssociation) exp.getSourceType());
		} else {
			// handle case
		}
	}

	@Override
	public void visitAny(ExpAny exp) {
		visitQuery(exp);
	}

	@Override
	public void visitAsType(ExpAsType exp) {
		// Needed?
	}

	@Override
	public void visitAttrOp(ExpAttrOp exp) {
		exp.objExp().processWithVisitor(this);
		addAttributeCoverage((MClassifier) exp.objExp().type(), exp.attr());
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
		// TODO: Coverage?
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
		if (exp.getTargetType().isTypeOfClass()) {
			addClassCoverage((MClass) exp.getTargetType());
		}
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
		addAssociationCoverage(exp.getDestination().association());
		addAssociationEndCoverage(exp.getDestination());
	}

	@Override
	public void visitNavigationClassifierSource(
			ExpNavigationClassifierSource exp) {
		exp.getObjectExpression().processWithVisitor(this);
		addAssociationCoverage(exp.getDestination().association());
		addAssociationEndCoverage(exp.getDestination());
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

		addOperationCoverage((MClassifier) exp.getArguments()[0].type(),
				exp.getOperation());

		if (expandOperations && exp.getOperation().hasExpression()
				&& !operationStack.contains(exp.getOperation())) {
			operationStack.push(exp.getOperation());
			exp.getOperation().expression().processWithVisitor(this);
			operationStack.pop();
		}
	}

	@Override
	public void visitInstanceConstructor(ExpInstanceConstructor exp) {
		for (Expression ex : exp.getArguments()) {
			ex.processWithVisitor(this);
		}

		addOperationCoverage((MClassifier) exp.getArguments()[0].type(),
				exp.getOperation());

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
		if (exp.type().isTypeOfClass()) {
			addClassCoverage((MClass) exp.type());
		}
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
		addClassCoverage(expObjectByUseId.getSourceType());
		expObjectByUseId.getIdExpression().processWithVisitor(this);
	}

	@Override
	public void visitConstUnlimitedNatural(
			ExpConstUnlimitedNatural expressionConstUnlimitedNatural) {

	}

	@Override
	public void visitSelectByKind(ExpSelectByKind expSelectByKind) {
		if (expSelectByKind.type().elemType().isTypeOfClass()) {
			addClassCoverage((MClass) expSelectByKind.type().elemType());
		}
		expSelectByKind.getSourceExpression().processWithVisitor(this);
	}

	@Override
	public void visitExpSelectByType(ExpSelectByType expSelectByType) {
		if (expSelectByType.type().elemType().isTypeOfClass()) {
			addClassCoverage((MClass) expSelectByType.type().elemType());
		}
		expSelectByType.getSourceExpression().processWithVisitor(this);
	}

	@Override
	public void visitRange(ExpRange exp) {
		exp.getStart().processWithVisitor(this);
		exp.getEnd().processWithVisitor(this);
	}
}
