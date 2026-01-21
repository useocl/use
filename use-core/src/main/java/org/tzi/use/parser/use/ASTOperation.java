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

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.parser.ocl.ASTType;
import org.tzi.use.parser.ocl.ASTVariableDeclaration;
import org.tzi.use.parser.soil.ast.ASTStatement;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.soil.MEmptyStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;


/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @author  Mark Richters
 */
public class ASTOperation extends ASTAnnotatable {
    private Token fName;
    private List<ASTVariableDeclaration> fParamList;
    private ASTType fType;           // (optional)
    private ASTExpression fExpr;     // (optional)
    private ASTStatement fStatement; // optional
    private MOperation fOperation;   // the operation is generated in two passes
    private List<ASTPrePostClause> fPrePostClauses;
    private final boolean fIsConstructor;

    public ASTOperation(
    		Token name, 
    		List<ASTVariableDeclaration> paramList,
    		ASTType t,
            boolean isConstructor) {
    	
    	fName = name;
        fParamList = paramList;
        fType = t;
        fOperation = null;
        fPrePostClauses = new ArrayList<ASTPrePostClause>();
        fIsConstructor = isConstructor;
    }

    public boolean isConstructor() {
        return fIsConstructor;
    }

    public void setStatement(ASTStatement statement) {
    	fStatement = statement;
    }
    
    public void setExpression(ASTExpression exp) {
    	fExpr = exp;
    }
    
    public void addPrePostClause(ASTPrePostClause ppc) {
        fPrePostClauses.add(ppc);
    }

    public MOperation genSignature(Context ctx) 
        throws SemanticException
    {
        // map params to VarDeclList
        VarDeclList varDeclList = new VarDeclList(false);

        for (ASTVariableDeclaration astDecl : fParamList) {
            VarDecl decl = astDecl.gen(ctx);
            try {
                varDeclList.add(decl);
            } catch (IllegalArgumentException ex) {
                throw new SemanticException(astDecl.name(), "Redefinition of `" +
                                            decl.name() + "'.");
            }
        }
        Type resultType = null;
        if (fType == null) {
            if (this.fExpr != null) {
                // Wenn eine OCL-Query-Operation (mit Rumpf) keinen Rueckgabetyp deklariert,
                // soll eine eindeutige Fehlermeldung erzeugt werden. Diese Meldung wird
                // spaeter direkt mit Datei/Positionsangabe ausgegeben und darf nicht noch
                // einmal separat als allgemeine [REPORT]-Zeile erscheinen. Deshalb geben
                // wir hier nur den Klartext ohne zusaetzlichen Punkt am Ende zurueck,
                // die eigentliche Positionierung passiert in SemanticException.
                throw new SemanticException(fName, "Missing return type for OCL query operation " + StringUtil.inQuotes(fName.getText()) + ".");
            }
        } else {
            resultType = fType.gen(ctx);
        }

        fOperation = ctx.modelFactory().createOperation(fName.getText(), varDeclList, resultType, fIsConstructor);

        // sets the line position of the USE-Model in this attribute
        fOperation.setPositionInModel( fName.getLine() );
        
        // HACK: set a temporary expression here, otherwise generating
        // the body expression will fail if the body contains a
        // recursive call to this operation, or a forward reference is
        // made to another operation
        if (fExpr != null ) {
            fOperation.setTempExpression();
        } else if (fStatement != null) {
            fOperation.setStatement(MEmptyStatement.getInstance());
        }
        
        this.genAnnotations(fOperation);
        
        return fOperation;
    }

    public void genFinal(Context ctx) throws SemanticException
    {
        // fOperation is null if genSignature exited with an Exception
        if (fOperation == null)
            return;
        
        // For constructors, decide whether a result-type error or a body error
        if (fIsConstructor) {
            boolean resultTypeError = false;

            // Determine whether the AST contains an explicit result type declared on the same line
            boolean astDeclaredTypeExplicit = false;
            if (fType != null && fType.getStartToken() != null) {
                try {
                    astDeclaredTypeExplicit = (fType.getStartToken().getLine() == fName.getLine());
                } catch (Exception e) {
                    astDeclaredTypeExplicit = false;
                }
            }

            // If the AST explicitly declared a return type on the same line as the operation,
            // this is clearly an error for instance constructors.
            if (astDeclaredTypeExplicit) {
                resultTypeError = true;
            } else if (fOperation.hasResultType()) {
                // Vergleiche deklarierten Rueckgabetyp mit der besitzenden Klasse ueber den
                // Modell-Typnamen, ohne eine direkte Abhaengigkeit zu ocl.type.* zu verwenden.
                try {
                    String declaredName = fOperation.resultType().toString();
                    if (declaredName == null || !declaredName.equals(fOperation.cls().name())) {
                        resultTypeError = true;
                    }
                } catch (Exception e) {
                    resultTypeError = true;
                }
            }

            if (resultTypeError) {
                throw new SemanticException(fName, "Instance constructor " + StringUtil.inQuotes(fName.getText()) +
                        " must not have result type.");
            }

            // Otherwise, if there is a body (expression or SOIL statement), that's an error
            if (fExpr != null || fStatement != null) {
                throw new SemanticException(fName, "Instance constructor " + StringUtil.inQuotes(fName.getText()) +
                        " must not have body.");
            }
        }

        // enter parameters into scope of expression
        Symtable vars = ctx.varTable();
        vars.enterScope();

        for (ASTVariableDeclaration astDecl : fParamList) {
            VarDecl decl = astDecl.gen(ctx);
            vars.add(astDecl.name(), decl.type());
        }
        
        ///////////////////////
        // BEGIN SOIL EXTENSION
        if (fStatement != null) {
        	fOperation.setStatement(genStatement(ctx));
        }
        // END SOIL EXTENSION
        /////////////////////
        
        try {
            if (fExpr != null ) {
                Expression expr = fExpr.gen(ctx);
                fOperation.setExpression(expr);
            }

            for (ASTPrePostClause ppc : fPrePostClauses) {
                ppc.gen(ctx, ctx.currentClassifier(), fOperation);
            }
            
        } catch (MInvalidModelException ex) {
            throw new SemanticException(fName, ex);
        } finally {
            vars.exitScope(); 
        }
    }

	private MStatement genStatement(Context context) throws SemanticException {
		try {
			return fStatement.generateStatement(context, fOperation);
		} catch (CompilationFailedException e) {
            String message = "\nCould not compile soil defined operation " +
                    StringUtil.inQuotes(fOperation.signature()) +
                    " due to the following error:\n" +
                    e.getMessage(true);

			throw new SemanticException(fName, message);
		}
	}

	/**
	 * During compilation, this operation marks this AST-node
	 * as invalid, because the signature had errors.
	 * Any call to {@link #genFinal(Context)} afterward
	 * is ignored.
	 */
	public void setSignatureGenFailed() {
		this.fOperation = null;
	}
}
