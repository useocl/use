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

// $Id$

package org.tzi.use.uml.mm;

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.uml.ocl.expr.ExpUndefined;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.StringUtil;

/**
 * An operation is a parameterized expression. Evaluation of the
 * expression has no side-effects wrt the system state.
 * @author Mark Richters 
 * @author Lars Hamann
 */
public final class MOperation extends MModelElementImpl implements UseFileLocatable {
    /** A list of parameters */
	private VarDeclList fVarDeclList;
    
    /** The declared result type (optional) */
    private Type fResultType;
    
    /** The operation's body (optional) */
    private Expression fExpr;
    
    /** The statement, might be <code>null</code>.*/
    private MStatement fStatement;
    
    /** The owner */
    private MClass fClass;
    
    private List<MPrePostCondition> fPreConditions;
    private List<MPrePostCondition> fPostConditions;
    private int fPositionInModel;
    
    public MOperation(String name, VarDeclList varDeclList, Type resultType) {
	    super(name);
	    fVarDeclList = varDeclList;
	    fResultType = resultType;
	    fExpr = null;
	    fPreConditions = new ArrayList<MPrePostCondition>();
	    fPostConditions = new ArrayList<MPrePostCondition>();
	}

    /**
     * Returns <code>true</code> if a SOIL statement (a body with possible side effects)
     * is defined for this operation.
     * @return
     */
	public boolean hasStatement() {
    	return fStatement != null;
    }
    
	/**
	 * Returns the defined SOIL statement of the body or <code>null</code>. 
	 * @return The statement of the body if defined otherwise <code>null</code>.
	 */
    public MStatement getStatement() {
    	return fStatement;
    }
    
    
    /**
     * <p>Returns <code>true</code> if this operation has a body.</p>
     * 
     * <p>The body can be a side effect free OCL expression (see {@link #expression()})
     * or a SOIL statement possibly with side effects (see {@link #getStatement()}).</p> 
     * @return <code>true</code> if a body is defined, <code>false</code> otherwise.
     */
    public boolean hasBody() {
    	return (hasExpression() || hasStatement());
    }
    
    
    /**
     * Sets the given SOIL statement as the operation body. 
     * @param statement The operation body statement.
     */
    public void setStatement(MStatement statement) {
    	fStatement = statement;
    }
    
    		
    /**
     * Returns <code>true</code> if the operation
     * can be safely called from inside an OCL expression.
     * <p>An operation is allowed to be called from inside an OCL expression,
     * if it has a body that is defined as an OCL expression.</p>
     * @return <code>true</code> if the operation can be called from inside an OCL expression.
     */
    public boolean isCallableFromOCL() {
    	return (fExpr != null); 
    }

    /** 
     * Returns the owning class of this operation.
     */
    public MClass cls() {
        return fClass;
    }

    /** 
     * Sets the owning class of this operation to <code>cls</code>.
     */
    void setClass(MClass cls) {
        fClass = cls;
    }
    
    /** 
     * Returns the parameter list of the operation.
     */
    public VarDeclList paramList() {
        return fVarDeclList;
    }
    
    /**
     * Returns a list of all parameter names
     * @return
     */
    public List<String> paramNames() {
    	
    	ArrayList<String> parameterNames = 
    		new ArrayList<String>(fVarDeclList.size());
    	
    	int numVarDecls = fVarDeclList.size();
    	for (int i = 0; i < numVarDecls; ++i) {
    		parameterNames.add(fVarDeclList.varDecl(i).name());
    	}
    	
    	return parameterNames;
    }

    /** 
     * Returns true if the operation declares a return type.
     */
    public boolean hasResultType() {
        return fResultType != null;
    }

    /** 
     * Returns the declared result type of the operation.
     *
     * @return resultType or null, if no return type specified
     */
    public Type resultType() {
        return fResultType;
    }

    /** 
     * Returns true if the operation has an expression body.
     */
    public boolean hasExpression() {
        return fExpr != null;
    }

    /** 
     * Returns the expression of the operation.
     *
     * @return expression or null, if no expression was specified
     */
    public Expression expression() {
        return fExpr;
    }

    /** 
     * Sets the expression of the operation.
     *
     * @exception MInvalidModelException if the expression's type does
     *            not conform to the declared result type 
     */
    public void setExpression(Expression expr) throws MInvalidModelException {
        // If no result type is set, use type of the expression
    	if (fResultType == null) {
    		fResultType = expr.type();
    	} else if (expr.type() == null) {
    		throw new MInvalidModelException("The operation " + StringUtil.inQuotes(this.cls().name() + "." + this.name()) + 
    				" does not have a declared result type and the result type of the defined expression could not be calcuated. This" +
    				" can happen when an operation without a declared result type is calling itself recursively.");
    	} else if (! expr.type().conformsTo(fResultType) ) {
            throw new MInvalidModelException("Expression type `" +
                                             expr.type() + 
                                             "' does not match declared result type `" + fResultType + "'.");
    	}
        fExpr = expr;
    }

    /** 
     * Sets a temporary expression
     * 
     */
    public void setTempExpression() {
        // If no result type is set, use type of the expression
        fExpr = new ExpUndefined();
    }

    /**
     * Returns a string describing the signature of the operation, i. e.,<br/>
     * <code>opName( (param : Type)* ) : Type</code>
     */
    public String signature() {
        String res = name() + "(" + fVarDeclList + ")";
        if (fResultType != null )
            res += " : " + fResultType;
        return res;
    }

    void addPreCondition(MPrePostCondition ppc) {
        fPreConditions.add(ppc);
    }

    void addPostCondition(MPrePostCondition ppc) {
        fPostConditions.add(ppc);
    }

    /**
     * Returns a list of preconditions attached to this operation. Do
     * not modify this list.
     */
    public List<MPrePostCondition> preConditions() {
        return fPreConditions;
    }

    /**
     * Returns a list of postconditions attached to this operation. Do
     * not modify this list.
     */
    public List<MPrePostCondition> postConditions() {
        return fPostConditions;
    }
    
    /**
     * Returns true if any of the post conditions requires access to the pre state, e. g., by using @pre or oclIsNew
     * @return true if any of the post conditions requires access to the pre state, e. g., by using @pre or oclIsNew
     */
    public boolean postConditionsRequirePreState() {
    	for (MPrePostCondition postCondition : fPostConditions) {
    		if (postCondition.expression().requiresPreState()) {
    			return true;
    		}
    	}
    	return false;
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

    /**
     * Process this element with visitor.
     */
    public void processWithVisitor(MMVisitor v) {
        v.visitOperation(this);
    }

    /**
	 * @param op
	 * @return
	 */
	public boolean isValidOverrideOf(MOperation op) {
		if (!this.name().equals(op.name())) return false;
		
		if (this.fResultType == null) {
			if (op.fResultType != null) return false;
		} else {
			if (!this.fResultType.conformsTo(op.fResultType))
				return false;
		}
		
		if (this.fVarDeclList.size() != op.fVarDeclList.size())
			return false;
		
		int index = 0;
		for (VarDecl opVar : op.fVarDeclList) {
			if (!opVar.type().conformsTo(this.fVarDeclList.varDecl(index).type()))
				return false;
			++index;
		}
		
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		// Checks this and name
		if (!super.equals(obj))
			return false;
		
		if (!(obj instanceof MOperation))
			return false;
		
		MOperation op = (MOperation)obj;
		
		if (!fClass.equals(op.fClass))
			return false;

		if (fResultType == null) {
			if(op.fResultType != null) return false;
		} else {
			return fResultType.equals(op.fResultType);
		}
		
		if (!this.paramList().equals(op.paramList()))
			return false;
		
		return true;
	}

	@Override
	public int compareTo(MModelElement o) {
		if (o instanceof MOperation)
			return signature().compareTo(((MOperation)o).signature());
		else
			return super.compareTo(o);
	}
	
	public String qualifiedName() {
		return fClass.name() + "::" + name();
	}
	
	@Override
	public String toString() {
		return qualifiedName();
	}
}
