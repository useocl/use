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

package org.tzi.use.uml.ocl.expr;

import java.io.PrintWriter;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;

/**
 * Default visitor to print OCL expressions.
 * Subclasses can override the format operations.
 * 
 * @author Frank Hilken
 * @author Lars Hamann
 */
public class ExpressionPrintVisitor implements ExpressionVisitor {

	protected final PrintWriter writer;
	
	public ExpressionPrintVisitor(PrintWriter writer) {
		this.writer = writer;
	}
	
	@Override
	public String toString(){
		return writer.toString();
	}

	protected String ws(){
		return " ";
	}
	
	protected String quoteContent(String s) {
		return s;
	}
	
	protected final String keyword(String s, Expression expr){
		return formatKeyword(quoteContent(s), expr);
	}
	
	protected String formatKeyword(String s, Expression expr) {
		return s;
	}

	protected final String variable(String s, Expression expr){
		return formatVariable(quoteContent(s), expr);
	}
	
	protected String formatVariable(String s, Expression expr) {
		return s;
	}
	
	protected final String type(String s, Expression expr){
		return formatType(quoteContent(s), expr);
	}

	protected String formatType(String s, Expression expr) {
		return s;
	}

	protected String operation(String s, Expression expr){
		return formatOperation(quoteContent(s), expr);
	}
	
	protected String formatOperation(String s, Expression expr) {
		return s;
	}
	
	protected String operator(String s, Expression expr){
		return formatOperator(quoteContent(s), expr);
	}

	protected String formatOperator(String s, Expression expr) {
		return s;
	}

	protected String literal(String s, Expression expr){
		return formatLiteral(quoteContent(s), expr);
	}
	
	protected String formatLiteral(String s, Expression expr) {
		return s;
	}

	protected void atPre(Expression exp) {
		if(exp.isPre()){
			writer.write(operator("@pre", exp));
		}
	}
	
	private void visitCollectionLiteral(ExpCollectionLiteral exp) {
		writer.write(keyword(exp.getKind(), exp));
		writer.write(operator("{", exp));
		
		boolean first = true;
		for (Expression elemExp : exp.getElemExpr()) {
			if (!first) {
				writer.write(",");
				writer.write(ws());
			}
			elemExp.processWithVisitor(this);
			first = false;
		}
		
		writer.write(operator("}", exp));
	}
	
	@Override
	public void visitAllInstances(ExpAllInstances exp) {
		writer.write(type(exp.getSourceType().toString(), exp));
		writer.write(".");
		writer.write(operation(exp.name(), exp));
		atPre(exp);
		writer.write(operator("()", exp));
	}

	@Override
	public void visitAny(ExpAny exp) {
		visitQuery(exp);
	}

	@Override
	public void visitAsType(ExpAsType exp) {
		exp.getSourceExpr().processWithVisitor(this);
		writer.write('.');
		writer.write(operation(exp.name(), exp));
		writer.write(operator("(", exp));
		writer.write(type(exp.getTargetType().toString(), exp));
		writer.write(operator(")", exp));
	}

	@Override
	public void visitAttrOp(ExpAttrOp exp) {
		exp.objExp().processWithVisitor(this);
		writer.write('.');
		writer.write(exp.attr().name());
		atPre(exp);
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
		writer.write(literal(String.valueOf(exp.value()), exp));
	}

	@Override
	public void visitConstEnum(ExpConstEnum exp) {
		writer.write(type(exp.type().toString(), exp));
		writer.write("::");
		writer.write(literal(exp.value(), exp));
	}

	@Override
	public void visitConstInteger(ExpConstInteger exp) {
		writer.write(literal(String.valueOf(exp.value()), exp));
	}

	@Override
	public void visitConstReal(ExpConstReal exp) {
		writer.write(literal(String.valueOf(exp.value()), exp));
	}

	@Override
	public void visitConstString(ExpConstString exp) {
		writer.write("'");
		writer.write(literal(exp.value(), exp));
		writer.write("'");
	}

	@Override
	public void visitEmptyCollection(ExpEmptyCollection exp) {
		writer.write(operation(exp.name(), exp));
		writer.write(operator("(", exp));
		writer.write(type(exp.type().toString(), exp));
		writer.write(operator(")", exp));
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
		writer.write(keyword("if", exp));
		writer.write(ws());
		exp.getCondition().processWithVisitor(this);
		writer.write(ws());
		writer.write(keyword("then", exp));
		writer.write(ws());
		exp.getThenExpression().processWithVisitor(this);
		writer.write(ws());
		writer.write(keyword("else", exp));
		writer.write(ws());
		exp.getElseExpression().processWithVisitor(this);
		writer.write(ws());
		writer.write(keyword("endif", exp));
	}

	@Override
	public void visitIsKindOf(ExpIsKindOf exp) {
		exp.getSourceExpr().processWithVisitor(this);
		writer.write(".");
		writer.write(operation(exp.name(), exp));
		writer.write(operator("(", exp));
		writer.write(type(exp.getTargetType().toString(), exp));
		writer.write(operator(")", exp));
	}

	@Override
	public void visitIsTypeOf(ExpIsTypeOf exp) {
		exp.getSourceExpr().processWithVisitor(this);

		if (exp.getSourceExpr().type().isKindOfCollection(VoidHandling.EXCLUDE_VOID))
        	writer.write("->");
        else
        	writer.write(".");
		
		writer.write(operation(exp.name(), exp));
		writer.write(operator("(", exp));
		writer.write(type(exp.getTargetType().toString(), exp));
		writer.write(operator(")", exp));
	}
	
	@Override
	public void visitIsUnique(ExpIsUnique exp) {
		visitQuery(exp);
	}

	@Override
	public void visitIterate(ExpIterate exp) {
		visitQuery(exp, exp.getAccuInitializer());
	}

	@Override
	public void visitLet(ExpLet exp) {
		writer.write(operator("(", exp));
		writer.write(keyword("let", exp));
		writer.write(ws());
		writer.write(variable(exp.getVarname(), exp));
		writer.write(operator(":", exp));
		writer.write(type(exp.getVarType().toString(), exp));
		writer.write(ws());
		writer.write(operator("=", exp));
		writer.write(ws());
		exp.getVarExpression().processWithVisitor(this);
		writer.write(ws());
		writer.write(keyword("in", exp));
		writer.write(ws());
		exp.getInExpression().processWithVisitor(this);
		writer.write(operator(")", exp));
	}

	@Override
	public void visitNavigation(ExpNavigation exp) {
		exp.getObjectExpression().processWithVisitor(this);
		writer.write('.');
		writer.write(exp.getDestination().nameAsRolename());
		
		MAssociation assoc = exp.getDestination().association();
		MClass src = exp.getSource().cls();
		MClass dest = exp.getDestination().cls();
		int endsRequired = src.equals(dest) ? 2 : 1;
		
		if(assoc.associationEndsAt(src).size() > endsRequired){
			// check necessity for specifying the source role
			writer.write('[');
			writer.write(exp.getSource().nameAsRolename());
			writer.write(']');
		}
		else if(exp.getQualifierExpression().length > 0){
			// qualifier values
			writer.write('[');
			boolean first = true;
			for(Expression e : exp.getQualifierExpression()){
				if(!first){
					writer.write(',');
					writer.write(ws());
				}
				e.processWithVisitor(this);
				first = false;
			}
			writer.write(']');
		}
		atPre(exp);
	}

	@Override
	public void visitNavigationClassifierSource(ExpNavigationClassifierSource exp) {
		exp.getObjectExpression().processWithVisitor(this);
		writer.write('.');
		writer.write(exp.getDestination().nameAsRolename());
		atPre(exp);
	}
	
	@Override
	public void visitObjAsSet(ExpObjAsSet exp) {
		exp.getObjectExpression().processWithVisitor(this);
	}

	@Override
	public void visitObjOp(ExpObjOp exp) {
		exp.getArguments()[0].processWithVisitor(this);
		writer.write('.');
		writer.write(operation(exp.getOperation().name(), exp));
		atPre(exp);
		writer.write(operator("(", exp));
		for (int i = 1; i < exp.getArguments().length; ++i) {
			if (i > 1) {
				writer.write(",");
				writer.write(ws());
			}
			
			exp.getArguments()[i].processWithVisitor(this);
		}
		writer.write(operator(")", exp));
	}

	@Override
	public void visitObjRef(ExpObjRef exp) {
		writer.write(literal(exp.toString(), exp));
	}

	@Override
	public void visitOne(ExpOne exp) {
		visitQuery(exp);
	}

	@Override
	public void visitOrderedSetLiteral(ExpOrderedSetLiteral exp) {
		visitCollectionLiteral(exp);
	}

	private void visitQuery(ExpQuery exp, VarInitializer accuInitializer) {
		exp.getRangeExpression().processWithVisitor(this);
		writer.write(operator("->", exp));
		writer.write(operation(exp.name(), exp));
		writer.write(operator("(", exp));
		writer.write(ws());
		exp.getVariableDeclarations().processWithVisitor(this);
		if (accuInitializer != null) {
			writer.write(operator(";", exp));
			writer.write(ws());
			accuInitializer.getVarDecl().processWithVisitor(this);
			writer.write(operator("=", exp));
			accuInitializer.initExpr().processWithVisitor(this);
		}
		writer.write(ws());
		writer.write(operator("|", exp));
		writer.write(ws());
		exp.getQueryExpression().processWithVisitor(this);
		writer.write(ws());
		writer.write(operator(")", exp));
	}
	
	@Override
	public void visitQuery(ExpQuery exp) {
		visitQuery(exp, null);
	}
	
	@Override
	public void visitReject(ExpReject exp) {
		visitQuery(exp);
	}

	@Override
	public void visitWithValue(ExpressionWithValue exp) {
		writer.write(exp.getValue().toString());
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
		Expression[] args = exp.args();
		String operationName;
		
		if(exp.getOperation().isInfixOrPrefix()){
			operationName = keyword(exp.opname(), exp);
			if(args.length == 1){
				writer.write(operationName);
				writer.write(ws());
				args[0].processWithVisitor(this);
			} else { // Infix has two arguments
				writer.write(operator("(", exp));
				args[0].processWithVisitor(this);
				writer.write(ws());
				writer.write(operationName);
				writer.write(ws());
				args[1].processWithVisitor(this);
				writer.write(operator(")", exp));
			}
		} else {
			operationName = operation(exp.opname(), exp);
			if(exp.isPre()){
				operationName += operator("@pre", exp);
			}
			
			if(args.length == 0){
				writer.write(operationName);
			} else {
				args[0].processWithVisitor(this);
				writer.write(operator( args[0].type().isKindOfCollection(VoidHandling.EXCLUDE_VOID) ? "->" : ".", exp ));
				writer.write(operationName);
				writer.write(operator("(", exp));
				if(args.length > 1){
					for(int i = 1; i < args.length; i++){
						if(i > 1){
							writer.write(",");
							writer.write(ws());
						}
						args[i].processWithVisitor(this);
					}
				}
				writer.write(operator(")", exp));
			}
		}
	}

	@Override
	public void visitTupleLiteral(ExpTupleLiteral exp) {
		writer.write(keyword("Tuple", exp));
		writer.write(operator("{", exp));
		boolean first = true;
		for(ExpTupleLiteral.Part p : exp.getParts()){
			if(!first){
				writer.write(operator(",", exp));
				writer.write(ws());
			}
			writer.write(p.getName());
			writer.write(operator("=", exp));
			p.getExpression().processWithVisitor(this);
			first = false;
		}
		writer.write(operator("}", exp));
	}

	@Override
	public void visitTupleSelectOp(ExpTupleSelectOp exp) {
		exp.getTupleExp().processWithVisitor(this);
		writer.write(".");
		writer.write(exp.getPart().name());
	}

	@Override
	public void visitUndefined(ExpUndefined exp) {
		writer.write(literal("null", exp));
	}

	@Override
	public void visitVariable(ExpVariable exp) {
		writer.write(variable(exp.getVarname(), exp));
		atPre(exp);
	}

	@Override
	public void visitClosure(ExpClosure exp) {
		visitQuery(exp);
	}

	@Override
	public void visitOclInState(ExpOclInState exp) {
		exp.getSourceExpr().processWithVisitor(this);
		writer.write(".");
		writer.write(operation(exp.name(), exp));
		writer.write(operator("(", exp));
		writer.write(exp.getState().name());
		writer.write(operator(")", exp));
	}

	@Override
	public void visitVarDeclList(VarDeclList varDeclList) {
		for (int i = 0; i < varDeclList.size(); ++i) {
			if (i > 0) {
				writer.write(",");
				writer.write(ws());
			}
			
			varDeclList.varDecl(i).processWithVisitor(this);
		}
	}
	
	@Override
	public void visitVarDecl(VarDecl varDecl) {
		writer.write(variable(varDecl.name(), null));
		writer.write(":");
		writer.write(type(varDecl.type().toString(), null));
	}

	@Override
	public void visitObjectByUseId(ExpObjectByUseId expObjectByUseId) {
		writer.write(expObjectByUseId.getSourceType().name());
		writer.write(".");
		writer.write(operation(expObjectByUseId.name(), expObjectByUseId));
		writer.write(operator("(", expObjectByUseId));
		expObjectByUseId.processWithVisitor(this);
		writer.write(operator(")", expObjectByUseId));
		atPre(expObjectByUseId);
	}

	@Override
	public void visitConstUnlimitedNatural(
			ExpConstUnlimitedNatural expressionConstUnlimitedNatural) {
		writer.write("*");
	}

	@Override
	public void visitSelectByKind(ExpSelectByKind expSelectByKind) {
		expSelectByKind.getSourceExpression().processWithVisitor(this);
		writer.write(operator("->", expSelectByKind));
		writer.write(operation(expSelectByKind.name(), expSelectByKind));
		writer.write(operator("(", expSelectByKind));
		writer.write(type(expSelectByKind.type().elemType().toString(), expSelectByKind));
		writer.write(operator(")", expSelectByKind));
	}

	@Override
	public void visitExpSelectByType(ExpSelectByType expSelectByType) {
		visitSelectByKind(expSelectByType);
	}

	@Override
	public void visitRange(ExpRange exp) {
		exp.getStart().processWithVisitor(this);
		writer.write("..");
		exp.getEnd().processWithVisitor(this);
	}

}
