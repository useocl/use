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

package org.tzi.use.parser;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.antlr.runtime.Token;
import org.tzi.use.gen.assl.statics.GProcedure;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MSystemState;


/**
 * Context information available when walking the abstract syntax tree.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class Context {
    private Symtable fVarTable; // declared variable names
    private Symtable fTypeTable; // declared type names
    // implicit context for some expressions (self or element variable
    // in iterate-based expressions)
    private ExprContext fExprContext; 
    private int fErrorCount;
    private String fFilename;
    
    private PrintWriter fErr;
    private PrintWriter fOut;
    
    private MModel fModel;
    private MClass fCurrentClass;
    private ModelFactory fModelFactory;
    private MSystemState fSystemState;
    private boolean fInsidePostCondition;
    private List<String> fLoopVarNames;
    
    private List<GProcedure> generatorProcedures;
    
    // for test cases: in assert expressions invariant names 
    // are replaced by their expression
    private boolean fIsAssertExpression;
    
    private boolean fIsInsideTestCase;
    
    public Context(String filename, PrintWriter err, 
                   VarBindings globalBindings,
                   ModelFactory factory) {
        fFilename = filename;
        fErr = err;
        fOut = err;
        fVarTable = new Symtable(globalBindings);
        fTypeTable = new Symtable();
        fExprContext = new ExprContext();
        fModelFactory = factory;
        fLoopVarNames = new ArrayList<String>();
     }

    public void setOut(PrintWriter out) {
    	fOut = out;
    }

    public PrintWriter getOut() {
    	return fOut;
    }
    
    public List<String> loopVarNames() {
        return fLoopVarNames;
    }

    public String filename() {
        return fFilename;
    }

    public ModelFactory modelFactory() {
        return fModelFactory;
    }

    public void setVarTable(Symtable varTable) {
    	fVarTable = varTable;
    }
    
    public void buildVarTable(Map<String, Type> symTable)
    {
    	Symtable newSymtable = new Symtable();
    	try {
	    	for (Entry<String, Type> entry : symTable.entrySet()) {
	    		newSymtable.add(entry.getKey(), entry.getValue(), null);
	    	}
    	} catch (SemanticException e) {
			// since the exception gets thrown if we add something with
    		// the same name as an existing entry, and we're adding stuff
    		// from a map (in which keys are unique), we can safely assume
    		// that we won't end up here unless someone changes the behavior
    		// of the .add method
    		System.err.println("please check org.tzi.use.parser.Context:buildVarTable()");
		}	
    
    	fVarTable = newSymtable;
    }
    
    public Symtable varTable() {
        return fVarTable;
    }

    public Symtable typeTable() {
        return fTypeTable;
    }

    public ExprContext exprContext() {
        return fExprContext;
    }

    public void setModel(MModel model) {
        fModel = model;
    }

    public MModel model() {
        return fModel;
    }

    public void setSystemState(MSystemState systemState) {
        fSystemState = systemState;
    }

    public MSystemState systemState() {
        return fSystemState;
    }

    public void setCurrentClass(MClass cls) {
        fCurrentClass = cls;
    }

    public MClass currentClass() {
        return fCurrentClass;
    }
    
    public void setInsidePostCondition(boolean state) {
        fInsidePostCondition = state;
    }

    public boolean insidePostCondition() {
        return fInsidePostCondition;
    }
        
    public int errorCount() {
        return fErrorCount;
    }

    public void reportWarning(Token t, String msg) {
        fErr.println(fFilename + ":" + t.getLine() + ":" + 
                     t.getCharPositionInLine() + ": Warning: " + msg);
    }
    
    public void reportError(Token t, String msg) {
        fErrorCount++;
        fErr.println(fFilename + ":" + t.getLine() + ":" + 
                     t.getCharPositionInLine() + ": " + msg);
    }
    
    public void reportError(Token t, Exception ex) {
        reportError(t, ex.getMessage());
    }
    
    public void reportError(SemanticException ex) {
        fErrorCount++;
        fErr.println(ex.getMessage());
        fErr.flush();
    }

	public boolean isAssertExpression() {
		return fIsAssertExpression;
	}


	public void setIsAssertExpression(boolean fIsAssertExpression) {
		this.fIsAssertExpression = fIsAssertExpression;
	}

	public void setIsInsideTestCase(boolean newValue) {
		this.fIsInsideTestCase = newValue;
	}
	
	public boolean isInsideTestCase() {
		return this.fIsInsideTestCase;
	}

	/**
	 * Sets the available procedures in an ASSL file 
	 * @param procedures
	 */
	public void setProcedures(List<GProcedure> procedures) {
		generatorProcedures = procedures;
	}
	
	public List<GProcedure> getProcedures() {
		return generatorProcedures;
	}
}
