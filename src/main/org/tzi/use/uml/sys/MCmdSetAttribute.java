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

package org.tzi.use.uml.sys;

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.ExpAttrOp;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.util.cmd.CannotUndoException;
import org.tzi.use.util.cmd.CommandFailedException;

/**
 * A command for setting an attribute value of an object.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class MCmdSetAttribute extends MCmd {
    private MSystemState fSystemState;
    private ExpAttrOp fAttrExpr;
    private Expression fSetExpr;

    // undo information
    private Value fUndoAttrValue;
    private MObjectState fUndoObjState;
    private MAttribute fUndoAttr;
    
    private MObject fObject;

    /**
     * Creates a command for setting an attribute values.
     */
    public MCmdSetAttribute(MSystemState systemState, 
                            ExpAttrOp attrExpr,
                            Expression setExpr) {
        super(true);
        fSystemState = systemState;
        fAttrExpr = attrExpr;
        fSetExpr = setExpr;
    }

    /** 
     * Executes command and stores undo information.
     *
     * @exception CommandFailedException if the command failed.
     */
    public void doExecute() throws CommandFailedException {
        VarBindings varBindings = fSystemState.system().topLevelBindings();

        // get source expression and evaluate to object
        Expression objExp = fAttrExpr.objExp();
        Evaluator evaluator = new Evaluator();
        Value v = evaluator.eval(objExp, fSystemState, varBindings);
        // if we don't have an object we can't deliver an attribute value
        if (v.isUndefined() ) 
            throw new CommandFailedException("Target of assignment is undefined.");

        ObjectValue objVal = (ObjectValue) v;
        MObject obj = objVal.value();
        MObjectState objState = obj.state(fSystemState);
        // if the object is dead the result is undefined
        if (objState == null )
            throw new CommandFailedException("Target of assignment is undefined.");

        MAttribute attr = fAttrExpr.attr();
        fObject = obj;

        // evaluate expression for new attribute value
        Value newValue = new Evaluator().eval(fSetExpr, fSystemState, varBindings);
    
        // save state for undo
        fUndoAttrValue = objState.attributeValue(attr);
        fUndoObjState = objState;
        fUndoAttr = attr;

        try { 
            // setAttributeValue also checks conforming types of
            // attribute and expression
            objState.setAttributeValue(attr, newValue);
        } catch (IllegalArgumentException ex) {
            throw new CommandFailedException(ex.getMessage());
        }
    }

    /**
     * Undo effect of command.
     *
     * @exception CannotUndoException if the command cannot be undone or 
     *                                has not been executed before.
     */
    public void undo() throws CannotUndoException {
        // the CommandProcessor should prevent us from being called
        // without a successful prior execute, just be safe here
        if (fUndoAttrValue == null )
            throw new CannotUndoException("no undo information");
    
        fUndoObjState.setAttributeValue(fUndoAttr, fUndoAttrValue);
    }

    /**
     * Fill a StateChangeEvent with information about this command's
     * effect.  
     *
     * @param undoChanges get information about undo action of command.
     */
    public void getChanges(StateChangeEvent sce, boolean undoChanges) {
        if (fUndoObjState == null )
            throw new IllegalStateException("command not executed");
    
        sce.addModifiedObject(fUndoObjState.object());
    }

    /**
     * Returns a short name of command, e.g. 'Create class foo' for
     * display in an undo menu item.  
     */
    public String name() {
        return "Set attribute " + fAttrExpr;
    }

    /**
     * Returns a string that can be read and executed by the USE shell
     * achieving the same effect of this command.  
     */
    public String getUSEcmd() {
    	
    	String attExp = fAttrExpr.toString();
    	// If direct object access an extra @ is prepended.
    	// This cannot be read by the cmd interpreter. 
    	if (attExp.startsWith("@"))
    		attExp = attExp.substring(1);
    	
        // replaces('$', ' ') is used in case there was an implicit
        // collect than variables like `$e' are inserted and can not
        // be read by USE again.
        return ("!set " + attExp + " := " + fSetExpr).replace('$', ' ');
    }

    /**
     * Returns a general name of command, e.g. 'Create Class'.
     */
    public String toString() {
        return "Set attribute";
    }
    
    public MObject getObject() throws CommandFailedException{
        return fObject;
    }

    public String getAttribute(){
        MAttribute attr = fAttrExpr.attr();
        return attr.name();
    }

    public String getValue(){
        return fSetExpr.toString(); 
    }
}
