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

import java.io.*;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.event.EventListenerList;

import org.tzi.use.gen.tool.GGenerator;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.util.Log;
import org.tzi.use.util.UniqueNameGenerator;
import org.tzi.use.util.cmd.*;

/**
 * A system maintains a system state and provides functionality for
 * doing state transitions.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class MSystem {
    private MModel fModel;  // The model of this system. 
    private MSystemState fCurrentState; // The current system state. 
    private Map fObjects;   // (String -> MObject) The set of all objects
    private UniqueNameGenerator fUniqueNameGenerator; // creation of object names
    private CommandProcessor fCommandProcessor; // executing state change commands
    protected EventListenerList fListenerList = new EventListenerList();
    private List fOperationCallStack; // stack of active operations
    private VarBindings fVarBindings; // top-level variable bindings
    private GGenerator fGenerator;    // snapshot generator

    public MSystem(MModel model) {
        fModel = model;
        init();
    }

    private void init() {
        fObjects = new HashMap();
        fUniqueNameGenerator = new UniqueNameGenerator();
        fCommandProcessor = new CommandProcessor();
        fCurrentState = new MSystemState(fUniqueNameGenerator.generate("state#"), this);
        fOperationCallStack = new ArrayList();
        fVarBindings = new VarBindings();
        fGenerator = new GGenerator(this);
    }

    /**
     * Returns the current system state.
     */
    public MSystemState state() {
        return fCurrentState;
    }

    /**
     * Returns the system's model.
     */
    public MModel model() {
        return fModel;
    }

    /**
     * Returns the system's instance generator.
     */
    public GGenerator generator() {
        return fGenerator;
    }

    public void addChangeListener(StateChangeListener l) {
        fListenerList.add(StateChangeListener.class, l);
    }
    
    public void removeChangeListener(StateChangeListener l) {
        fListenerList.remove(StateChangeListener.class, l);
    }

    /**
     * Creates and adds a new object to the system. The name of the
     * object may be null in which case a unique name is automatically
     * generated.
     *
     * @return the created object.  
     */
    MObject createObject(MClass cls, String name) throws MSystemException {
        if (cls.isAbstract() )
            throw new MSystemException("The abstract class `" + cls.name() + 
                                       "' cannot be instantiated.");
    
        // create new object and initial state
        if (name == null ) {
            name = uniqueObjectNameForClass(cls.name());
        } else if (fObjects.containsKey(name) )
            throw new MSystemException("An object with name `" + name + 
                                       "' already exists.");
        
        MObject obj = new MObjectImpl(cls, name);
        addObject(obj);
        return obj;
    }

    void addObject(MObject obj) {
        fObjects.put(obj.name(), obj);
    }

    void deleteObject(MObject obj) {
        fObjects.remove(obj.name());
    }

    /**
     * Returns a unique name that can be used for a new object of the
     * given class.  
     */
    public String uniqueObjectNameForClass(String clsName) {
        return fUniqueNameGenerator.generate(clsName);
    }

    /**
     * Executes a state manipulation command.
     */
    public void executeCmd(MCmd cmd) throws MSystemException {
        Log.trace(this, "executing command: " + cmd);
        try {
            fCommandProcessor.execute(cmd);
            fireStateChanged(cmd, false);
        } catch (CommandFailedException ex) {
            throw new MSystemException(ex.getMessage());
        }
    }

    /**
     * Undoes the last state manipulation command.
     */
    public void undoCmd() throws MSystemException {
        Log.trace(this, "undoing last command");
        try {
            MCmd cmd = (MCmd) fCommandProcessor.undoLast();
            fireStateChanged(cmd, true);
        } catch (CannotUndoException ex) {
            throw new MSystemException(ex.getMessage());
        }
    }

    /** 
     * Returns the name of the next command that can be undone.
     *
     * @return null, if there is no command for undo.
     */
    public String nextUndoableCmdName() {
        Command cmd = fCommandProcessor.nextUndoableCmd();
        String name = null;
        if (cmd != null )
            name = cmd.name();
        return name;
    }

    /**
     * Writes a sequence of commands that can be read and executed by
     * the USE shell to reproduce the same effects of all command
     * executed so far.  
     */
    public void writeUSEcmds(PrintWriter out) 
        throws IOException 
    {
        Iterator cmdIter = fCommandProcessor.commands().iterator();
        while (cmdIter.hasNext() ) {
            MCmd cmd = (MCmd) cmdIter.next();
            Log.trace(this, cmd.getUSEcmd());
            out.println(cmd.getUSEcmd());
        }
    }

    /**
     * Returns the number of commands executed so far.
     */
    public int numExecutedCmds() {
        return fCommandProcessor.commands().size();
    }

    /**
     * Returns the list of commands executed so far.
     *
     * @return List(MCmd)
     */
    public List commands() {
        return fCommandProcessor.commands();
    }

    /**
     * Resets the system to its initial state.
     */
    public void reset() {
        init();
    }

    /**
     * Simulate entry of an operation. The preconditions of the
     * operation are checked with the current state. If all
     * preconditions are satisfied, the current state is saved and the
     * operation is pushed on an internal call stack.  
     */
    public boolean enterOperation(MOperationCall opcall, PrintWriter out) {
        opcall.enter(fCurrentState);

        // check preconditions
        boolean preOk = true;
        MOperation op = opcall.operation();
        List preconds = op.preConditions();
        Iterator it = preconds.iterator();
        while (it.hasNext() ) {
            MPrePostCondition ppc = (MPrePostCondition) it.next();
            Expression expr = ppc.expression();
            Evaluator evaluator = new Evaluator();
            // evaluate in scope local to operation
            Value v = evaluator.eval(expr, fCurrentState, opcall.varBindings());
            boolean ok = v.isDefined() && ((BooleanValue) v).isTrue();
            out.println("precondition `" + ppc.name() + "' is " + ok);
            if (! ok )
                preOk = false;
        }

        if (preOk ) {
            pushOperation(opcall);
            fCurrentState = new MSystemState(fUniqueNameGenerator.generate("state#"), 
                                             fCurrentState);
        }
        out.flush();
        return preOk;
    }

    /**
     * Exits the least recently entered operation. If the operation
     * signature specifies a result type, a result value must be
     * given. This value is bound to the special OCL variable
     * result. Finally, all postconditions are checked with the system
     * state saved at operation entry time and the current system
     * state.  
     */
    public void exitOperation(Value optionalResult, PrintWriter out) 
        throws MSystemException 
    {
        MOperationCall opcall = activeOperation();
        if (opcall == null )
            throw new MSystemException("Call stack is empty.");

        MOperation op = opcall.operation();

        // bind result value to result variable
        VarBindings vb = opcall.varBindings();
        if (op.hasResultType() ) {
            if (optionalResult == null )
                throw new MSystemException("Result value required on exit of " + 
                                           "operation `" + op + "'.");
            if (! optionalResult.type().isSubtypeOf(op.resultType()) )
                throw new MSystemException("Result value type `" + 
                                           optionalResult.type() +
                                           "' does not match operation result type `" + 
                                           op.resultType() + "'.");
            vb.push("result", optionalResult);
        }

        // check postconditions
        boolean postOk = true;
        List postconds = op.postConditions();
        Iterator it = postconds.iterator();
        while (it.hasNext() ) {
            MPrePostCondition ppc = (MPrePostCondition) it.next();
            //          System.out.println("*** checking postcondition `" + ppc.name() + "'");
            //          System.out.println("*** prestate: " + opcall.preState());
            Expression expr = ppc.expression();
            Evaluator evaluator = new Evaluator();
            // evaluate in scope local to operation
            Value v = evaluator.eval(expr, opcall.preState(), 
                                     fCurrentState, vb,
                                     null);
            boolean ok = v.isDefined() && ((BooleanValue) v).isTrue();
            out.println("postcondition `" + ppc.name() + "' is " + ok);
            if (! ok ) {
                postOk = false;
                out.println("evaluation results:");
                evaluator.eval(expr, opcall.preState(), 
                               fCurrentState, vb, out);
            }
        }
        opcall.exit(optionalResult, postOk);
        out.flush();
        try {
            opcall = popOperation();
        } catch (EmptyStackException ex) {
            throw new MSystemException("Call stack is empty.");
        }
    }

    /**
     * Notify all listeners that have registered interest for
     * notification on this event type.
     */
    private void fireStateChanged(MCmd cmd, boolean cmdWasUndone) {
        // Guaranteed to return a non-null array
        Object[] listeners = fListenerList.getListenerList();
        StateChangeEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i >= 0; i -= 2) {
            if (listeners[i] == StateChangeListener.class ) {
                // Lazily create the event:
                if (e == null) {
                    e = new StateChangeEvent(this);
                    cmd.getChanges(e, cmdWasUndone);
                }
                ((StateChangeListener) listeners[i+1]).stateChanged(e);

                //System.out.println("Notifying: " + ((StateChangeListener) listeners[i+1]).getClass());
            }          
        }
    }

    /**
     * Returns the currently active operation or null if no operation
     * is active.
     */
    public MOperationCall activeOperation() {
        MOperationCall res = null;
        int activeOperations = fOperationCallStack.size();
        if (activeOperations > 0 )
            res = (MOperationCall) fOperationCallStack.get(activeOperations - 1);
        return res;
    }

    /**
     * Returns the operation call stack.
     *
     * @return List(MOperationCall)
     */
    public List callStack() {
        return fOperationCallStack;
    }

    /**
     * Returns the current top-level bindings. Changes to the
     * returned value do NOT affect the bindings of the system.
     */
    public VarBindings topLevelBindings() {
        VarBindings vb = new VarBindings(fVarBindings);
        // add bindings from the currently active operation
        int activeOperations = fOperationCallStack.size();
        if (activeOperations > 0 ) {
            MOperationCall opcall = 
                (MOperationCall) fOperationCallStack.get(activeOperations - 1);
            vb.add(opcall.varBindings());
        }
        return vb;
    }

    /**
     * Returns the top-level bindings of object names. Changes to the
     * returned value affect the bindings of the system.
     */
    public VarBindings varBindings() {
        return fVarBindings;
    }


    /**
     * Adds a variable binding to the current scope. If we are inside
     * of an operation call, the variable is bound in the operation's
     * scope and will be unbound on operation exit. Top-level
     * variables are persistent.
     */
    void addVarBindingToCurrentScope(String var, Value v) {
        int activeOperations = fOperationCallStack.size();
        if (activeOperations == 0 ) {
            // add to top-level
            fVarBindings.push(var, v);
        } else {
            // add to current scope
            MOperationCall opcall = 
                (MOperationCall) fOperationCallStack.get(activeOperations - 1);
            opcall.varBindings().push(var, v);
        }
    }

    /**
     * This is the inverse to addVarBindingToCurrentScope. It is only
     * here for purposes of undo functionality.
     */
    void removeLastVarBindingFromCurrentScope() {
        int activeOperations = fOperationCallStack.size();
        if (activeOperations == 0 ) {
            // remove from
            fVarBindings.pop();
        } else {
            // remove from current scope
            MOperationCall opcall = 
                (MOperationCall) fOperationCallStack.get(activeOperations - 1);
            opcall.varBindings().pop();
        }
    }


    void setCurrentState(MSystemState state) {
        fCurrentState = state;
    }

    /**
     * Pushes an operation onto the call stack.
     */
    void pushOperation(MOperationCall opcall) {
        fOperationCallStack.add(opcall);
    }

    /**
     * Pops the currently active operation from the call stack and
     * returns the operation call object. 
     *
     * @throws EmptyStackException
     */
    MOperationCall popOperation() {
        int stackSize = fOperationCallStack.size();
        if (stackSize == 0 )
            throw new EmptyStackException();

        MOperationCall opcall = (MOperationCall) fOperationCallStack.get(stackSize - 1);
        fOperationCallStack.remove(stackSize - 1);
        return opcall;
    }
}
