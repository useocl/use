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

package org.tzi.use.uml.ocl.expr;

import java.util.List;

import org.tzi.use.uml.ocl.type.BagType;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.OrderedSetType;
import org.tzi.use.uml.ocl.type.SequenceType;
import org.tzi.use.uml.ocl.type.SetType;
import org.tzi.use.uml.ocl.type.TupleType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.type.TupleType.Part;
import org.tzi.use.uml.ocl.value.BagValue;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.OrderedSetValue;
import org.tzi.use.uml.ocl.value.RealValue;
import org.tzi.use.uml.ocl.value.SequenceValue;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.util.HashMultiMap;
import org.tzi.use.util.MultiMap;
import org.tzi.use.util.StringUtil;

/**
 * General operation expressions. Each operation is implemented by its own
 * class. New Operations are easily introduced by writing a new operation class
 * and adding a single instance of the new class to the list of operations (see
 * below). Also, this way the new operation symbol is already known to the
 * specification compiler.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Mark Richters
 */
public final class ExpStdOp extends Expression {

    private static final OpGeneric oplist[] = {
    // operations on Integer and Real
            new Op_number_add(), new Op_number_sub(), new Op_number_mult(),
            new Op_number_div(), new Op_number_unaryminus(),
            new Op_number_unaryplus(), new Op_integer_abs(), new Op_real_abs(),
            new Op_real_floor(), new Op_real_round(), new Op_number_max(),
            new Op_number_min(), new Op_integer_mod(), new Op_integer_idiv(),
            new Op_number_less(), new Op_number_greater(),
            new Op_number_lessequal(), new Op_number_greaterequal(),

            // operations on String
            new Op_string_size(), new Op_string_concat(),
            new Op_string_toUpper(), new Op_string_toLower(),
            new Op_string_substring(), new Op_string_less(), // OCL
            // extension!
            new Op_string_greater(), // OCL extension!
            new Op_string_lessequal(), // OCL extension!
            new Op_string_greaterequal(), // OCL extension!

            // operations on Boolean
            new Op_boolean_or(), new Op_boolean_xor(), new Op_boolean_and(),
            new Op_boolean_not(), new Op_boolean_implies(),

            // operations on Date
            new Op_Date_Lower(), new Op_Date_LowerEqual(), new Op_Date_Equal(),
            new Op_Date_GreaterEqual(), new Op_Date_Greater(),
            new Op_Date_Day(),
            // operations on enumeration types

            // operations on Collection
            new Op_collection_size(), new Op_collection_includes(),
            new Op_collection_excludes(), new Op_collection_count(),
            new Op_collection_includesAll(), new Op_collection_excludesAll(),
            new Op_collection_isEmpty(), new Op_collection_notEmpty(),
            new Op_collection_sum(), new Op_collection_product(),
            // the following are special expressions:
            // exists
            // forall
            // isUnique
            // sortedBy
            // iterate
            new Op_collection_flatten(), // USE specific

            // operations on Set
            new Op_set_union(), new Op_set_union_bag(),
            new Op_set_intersection(), new Op_set_intersection_bag(),
            new Op_set_difference(), new Op_set_including(),
            new Op_set_excluding(), new Op_set_symmetricDifference(),
            // the following three are special expressions:
            // select
            // reject
            // collect
            // count: inherited from Collection
            new Op_set_asSequence(), // non-deterministic!
            new Op_set_asBag(),

            // operations on Bag
            new Op_bag_union(), new Op_bag_union_set(),
            new Op_bag_intersection(), new Op_bag_intersection_set(),
            new Op_bag_including(), new Op_bag_excluding(),
            // the following three are special expressions:
            // select
            // reject
            // collect
            // count: inherited from Collection
            new Op_bag_asSequence(), // non-deterministic!
            new Op_bag_asSet(),

            // operations on Sequence
            // count: inherited from Collection
            new Op_sequence_union(), new Op_sequence_append(),
            new Op_sequence_prepend(), new Op_sequence_subSequence(),
            new Op_sequence_at(), new Op_sequence_first(),
            new Op_sequence_last(), new Op_sequence_including(),
            new Op_sequence_excluding(), new Op_sequence_asBag(),
            new Op_sequence_asSet(), new Op_sequence_indexOf(),
            new Op_sequence_insertAt(),

            // operations on OrderedSet
            new Op_orderedSet_union(), new Op_orderedSet_append(),
            new Op_orderedSet_prepend(), new Op_orderedSet_subSequence(),
            new Op_orderedSet_at(), new Op_orderedSet_first(),
            new Op_orderedSet_last(), new Op_orderedSet_including(),
            new Op_orderedSet_excluding(), new Op_orderedSet_asBag(),
            new Op_orderedSet_asSet(), new Op_orderedSet_indexOf(),
            new Op_orderedSet_insertAt(),
            
            // creation operations for collections
            new Op_mkSet(), new Op_mkSetRange(), new Op_mkSequence(),
            new Op_mkSequenceRange(), new Op_mkBag(), new Op_mkBagRange(),
            new Op_mkOrderedSet(), new Op_mkOrderedSetRange(),

            // generic operations on all types
            new Op_equal(), new Op_notequal(), new Op_isDefined(),
            new Op_isUndefined(),

            // generic operations on all object types
            new Op_oclIsNew(), };

    // opname / possible (overloaded) operations
    public static MultiMap<String, OpGeneric> opmap;

    // initialize operation map
    static {
        opmap = new HashMultiMap<String, OpGeneric>();
        for (int i = 0; i < oplist.length; i++)
            opmap.put(oplist[i].name(), oplist[i]);
    }

    /***
     * Adds an operation to the standard operations
     * @param op
     */
    public static void addOperation(OpGeneric op) {
    	opmap.put(op.name(), op);
    }
    
    /***
     * Removes all given operations from list ops
     * @param ops
     */
    public static void removeAllOperations(List<OpGeneric> ops) {
    	for (OpGeneric op : ops) {
    		opmap.remove(op.name(), op);
    	}
    }
    
    /**
     * Returns true if a standard operation exists matching name and params.
     */
    public static boolean exists(String name, Type params[]) {
        if (params.length == 0)
            throw new IllegalArgumentException(
                    "ExpStdOp.exists called with empty params array");

        // search by operation symbol
        List<OpGeneric> ops = opmap.get(name);
        if (ops.isEmpty()) // unknown operation symbol
            return false;

        // search overloaded operations for a match
        for (OpGeneric op : ops) {
            Type t = op.matches(params);
            if (t != null)
                return true;
        }
        return false;
    }

    /**
     * Try to create a new instance of ExpOperation.
     * 
     * @exception ExpInvalidException
     *                cannot find a match for operation
     */
    public static ExpStdOp create(String name, Expression args[])
            throws ExpInvalidException {
        if (args.length == 0)
            throw new IllegalArgumentException(
                    "ExpOperation.create called with " + "empty args array");

        // search by operation symbol
        List<OpGeneric> ops = opmap.get(name);
        if (ops.isEmpty()) // unknown operation symbol
            throw new ExpInvalidException("Undefined operation named `" + name
                    + "' in expression `" + opCallSignature(name, args) + "'.");

        Type[] params = new Type[args.length];
        for (int i = 0; i < args.length; i++)
            params[i] = args[i].type();

        // search overloaded operations for a match
        for (OpGeneric op : ops) {
            Type t = op.matches(params);
            if (t != null)
                return new ExpStdOp(op, args, t);
        }

        // operation name matches but arguments don't
        throw new ExpInvalidException("Undefined operation `"
                + opCallSignature(name, args) + "'.");
    }

    private static String opCallSignature(String name, Expression args[]) {
        // build error message with type names of arguments
        Type srcType = args[0].type();
        StringBuffer s = new StringBuffer(srcType
                + (srcType.isCollection() ? "->" : ".") + name + "(");
        for (int i = 1; i < args.length; i++) {
            if (i > 1)
                s.append(", ");
            s.append(args[i].type());
        }
        s.append(")");
        return s.toString();
    }

    // instance variables
    private OpGeneric fOp;

    private Expression fArgs[];

    private ExpStdOp(OpGeneric op, Expression args[], Type t) {
        super(t);
        fOp = op;
        fArgs = args;
    }

    public String toString() {
        return fOp.stringRep(fArgs, atPre());
    }

    public String opname() {
        return fOp.name();
    }

    public String name() {
        return fOp.name();
    }

    public Expression[] args() {
        return fArgs;
    }

    /**
     * Evaluates the expression and returns a result value.
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = null;

        // Boolean operations need special treatment of undefined
        // arguments. Also, short-circuit evaluation may be used to
        // speed up the evaluation process.
        if (fOp instanceof BooleanOperation) {
            res = ((BooleanOperation) fOp).evalWithArgs(ctx, fArgs);
        } else {
            Value argValues[] = new Value[fArgs.length];
            int opKind = fOp.kind();
            for (int i = 0; i < fArgs.length && res == null; i++) {
                argValues[i] = fArgs[i].eval(ctx);
                // if any of the arguments is undefined, the result
                // depends on the kind of operation we are about to
                // call.
                if (argValues[i].isUndefined()) {
                    switch (opKind) {
                    case OpGeneric.OPERATION:
                        // strict evaluation, result is undefined, no
                        // need to call the operation's eval() method.
                        res = new UndefinedValue(type());
                        break;
                    case OpGeneric.PREDICATE:
                        // predicates are by default false when passed
                        // an undefined argument
                        res = BooleanValue.FALSE;
                        break;
                    case OpGeneric.SPECIAL:
                        // these operations handle undefined arguments
                        // themselves
                        break;
                    default:
                        throw new RuntimeException(
                                "Unexpected operation kind: " + opKind);
                    }
                }
            }
            if (res == null) {
                try {
                    res = fOp.eval(ctx, argValues, type());
                } catch (ArithmeticException ex) {
                    // catch e.g. division by zero
                    res = new UndefinedValue(type());
                }
            }
        }
        ctx.exit(this, res);
        return res;
    }
}

// --------------------------------------------------------
// 
// Integer and Real operations.
//
// --------------------------------------------------------

/*
 * This class is only used for +, *, -, max, min on Integers and Reals.
 */
abstract class ArithOperation extends OpGeneric {
    public int kind() {
        return OPERATION;
    }

    public Type matches(Type params[]) {
        if (params.length == 2) {
            if (params[0].isInteger() && params[1].isInteger())
                return TypeFactory.mkInteger();
            else if (params[00].isNumber() && params[1].isNumber())
                return TypeFactory.mkReal();
        }
        return null;
    }
}

// --------------------------------------------------------

/* + : Integer x Integer -> Integer */
/* + : Real x Integer -> Real */
/* + : Integer x Real -> Real */
/* + : Real x Real -> Real */
final class Op_number_add extends ArithOperation {
    public String name() {
        return "+";
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isInteger() && args[1].isInteger()) {
            int res = ((IntegerValue) args[0]).value()
                    + ((IntegerValue) args[1]).value();
            return new IntegerValue(res);
        } else {
            double d1;
            double d2;
            if (args[0].isInteger())
                d1 = ((IntegerValue) args[0]).value();
            else
                d1 = ((RealValue) args[0]).value();

            if (args[1].isInteger())
                d2 = ((IntegerValue) args[1]).value();
            else
                d2 = ((RealValue) args[1]).value();
            return new RealValue(d1 + d2);
        }
    }
}

// --------------------------------------------------------

/* - : Integer x Integer -> Integer */
/* - : Real x Integer -> Real */
/* - : Integer x Real -> Real */
/* - : Real x Real -> Real */
final class Op_number_sub extends ArithOperation {
    public String name() {
        return "-";
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isInteger() && args[1].isInteger()) {
            int res = ((IntegerValue) args[0]).value()
                    - ((IntegerValue) args[1]).value();
            return new IntegerValue(res);
        } else {
            double d1;
            double d2;
            if (args[0].isInteger())
                d1 = ((IntegerValue) args[0]).value();
            else
                d1 = ((RealValue) args[0]).value();

            if (args[1].isInteger())
                d2 = ((IntegerValue) args[1]).value();
            else
                d2 = ((RealValue) args[1]).value();
            return new RealValue(d1 - d2);
        }
    }
}

// --------------------------------------------------------

/* * : Integer x Integer -> Integer */
/* * : Real x Integer -> Real */
/* * : Integer x Real -> Real */
/* * : Real x Real -> Real */
final class Op_number_mult extends ArithOperation {
    public String name() {
        return "*";
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isInteger() && args[1].isInteger()) {
            int res = ((IntegerValue) args[0]).value()
                    * ((IntegerValue) args[1]).value();
            return new IntegerValue(res);
        } else {
            double d1;
            double d2;
            if (args[0].isInteger())
                d1 = ((IntegerValue) args[0]).value();
            else
                d1 = ((RealValue) args[0]).value();

            if (args[1].isInteger())
                d2 = ((IntegerValue) args[1]).value();
            else
                d2 = ((RealValue) args[1]).value();
            return new RealValue(d1 * d2);
        }
    }
}

// --------------------------------------------------------

/* / : Number x Number -> Real */
final class Op_number_div extends OpGeneric {
    public String name() {
        return "/";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 2 && params[0].isNumber() && params[1]
                .isNumber()) ? TypeFactory.mkReal() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        double d1;
        double d2;
        if (args[0].isInteger())
            d1 = ((IntegerValue) args[0]).value();
        else
            d1 = ((RealValue) args[0]).value();

        if (args[1].isInteger())
            d2 = ((IntegerValue) args[1]).value();
        else
            d2 = ((RealValue) args[1]).value();
        double res = d1 / d2;
        // make special values resulting in undefined
        if (Double.isNaN(res) || Double.isInfinite(res))
            throw new ArithmeticException();
        return new RealValue(res);
    }
}

// --------------------------------------------------------

/* abs : Real -> Real */
final class Op_real_abs extends OpGeneric {
    public String name() {
        return "abs";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length == 1 && params[0].isReal()) ? TypeFactory
                .mkReal() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        double d1 = ((RealValue) args[0]).value();
        return new RealValue(Math.abs(d1));
    }
}

// --------------------------------------------------------

/* abs : Integer -> Integer */
final class Op_integer_abs extends OpGeneric {
    public String name() {
        return "abs";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length == 1 && params[0].isInteger()) ? TypeFactory
                .mkInteger() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        int i1 = ((IntegerValue) args[0]).value();
        return new IntegerValue(Math.abs(i1));
    }
}

// --------------------------------------------------------

/* - : Number -> Number */
final class Op_number_unaryminus extends OpGeneric {
    public String name() {
        return "-";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 1 && params[0].isNumber()) ? params[0] : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        Value res;
        if (args[0].isInteger()) {
            int i = ((IntegerValue) args[0]).value();
            res = new IntegerValue(-i);
        } else {
            double d = ((RealValue) args[0]).value();
            res = new RealValue(-d);
        }
        return res;
    }
}

// --------------------------------------------------------

/* + : Number -> Number */
final class Op_number_unaryplus extends OpGeneric {
    public String name() {
        return "+";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 1 && params[0].isNumber()) ? params[0] : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        // nop
        return args[0];
    }
}

// --------------------------------------------------------

/* floor : Real -> Integer */
/* floor : Integer -> Integer */
final class Op_real_floor extends OpGeneric {
    public String name() {
        return "floor";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length == 1 && params[0].isNumber()) ? TypeFactory
                .mkInteger() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        double d1;
        if (args[0].isInteger())
            d1 = ((IntegerValue) args[0]).value();
        else
            d1 = ((RealValue) args[0]).value();

        return new IntegerValue((int) Math.floor(d1));
    }
}

// --------------------------------------------------------

/* round : Real -> Integer */
/* round : Integer -> Integer */
final class Op_real_round extends OpGeneric {
    public String name() {
        return "round";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length == 1 && params[0].isNumber()) ? TypeFactory
                .mkInteger() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        double d1;
        if (args[0].isInteger())
            d1 = ((IntegerValue) args[0]).value();
        else
            d1 = ((RealValue) args[0]).value();

        return new IntegerValue((int) Math.round(d1));
    }
}

// --------------------------------------------------------

/* max : Integer x Integer -> Integer */
/* max : Real x Integer -> Real */
/* max : Integer x Real -> Real */
/* max : Real x Real -> Real */
final class Op_number_max extends ArithOperation {
    public String name() {
        return "max";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isInteger() && args[1].isInteger()) {
            int res = Math.max(((IntegerValue) args[0]).value(),
                    ((IntegerValue) args[1]).value());
            return new IntegerValue(res);
        } else {
            double d1;
            double d2;
            if (args[0].isInteger())
                d1 = ((IntegerValue) args[0]).value();
            else
                d1 = ((RealValue) args[0]).value();

            if (args[1].isInteger())
                d2 = ((IntegerValue) args[1]).value();
            else
                d2 = ((RealValue) args[1]).value();
            return new RealValue(Math.max(d1, d2));
        }
    }
}

// --------------------------------------------------------

/* min : Integer x Integer -> Integer */
/* min : Real x Integer -> Real */
/* min : Integer x Real -> Real */
/* min : Real x Real -> Real */
final class Op_number_min extends ArithOperation {
    public String name() {
        return "min";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isInteger() && args[1].isInteger()) {
            int res = Math.min(((IntegerValue) args[0]).value(),
                    ((IntegerValue) args[1]).value());
            return new IntegerValue(res);
        } else {
            double d1;
            double d2;
            if (args[0].isInteger())
                d1 = ((IntegerValue) args[0]).value();
            else
                d1 = ((RealValue) args[0]).value();

            if (args[1].isInteger())
                d2 = ((IntegerValue) args[1]).value();
            else
                d2 = ((RealValue) args[1]).value();
            return new RealValue(Math.min(d1, d2));
        }
    }
}

// --------------------------------------------------------

/* mod : Integer x Integer -> Integer */
final class Op_integer_mod extends OpGeneric {
    public String name() {
        return "mod";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 2 && params[0].isInteger() && params[1]
                .isInteger()) ? TypeFactory.mkInteger() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        int i1 = ((IntegerValue) args[0]).value();
        int i2 = ((IntegerValue) args[1]).value();
        return new IntegerValue(i1 % i2);
    }
}

// --------------------------------------------------------

/* idiv : Integer x Integer -> Integer */
final class Op_integer_idiv extends OpGeneric {
    public String name() {
        return "div";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 2 && params[0].isInteger() && params[1]
                .isInteger()) ? TypeFactory.mkInteger() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        int i1 = ((IntegerValue) args[0]).value();
        int i2 = ((IntegerValue) args[1]).value();
        return new IntegerValue(i1 / i2);
    }
}

// --------------------------------------------------------

/* < : Number x Number -> Boolean */
final class Op_number_less extends OpGeneric {
    public String name() {
        return "<";
    }

    public int kind() {
        return PREDICATE;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 2 && params[0].isNumber() && params[1]
                .isNumber()) ? TypeFactory.mkBoolean() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        double d1;
        double d2;
        if (args[0].isInteger())
            d1 = ((IntegerValue) args[0]).value();
        else
            d1 = ((RealValue) args[0]).value();

        if (args[1].isInteger())
            d2 = ((IntegerValue) args[1]).value();
        else
            d2 = ((RealValue) args[1]).value();
        return BooleanValue.get(d1 < d2);
    }
}

// --------------------------------------------------------

/* > : Number x Number -> Boolean */
final class Op_number_greater extends OpGeneric {
    public String name() {
        return ">";
    }

    public int kind() {
        return PREDICATE;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 2 && params[0].isNumber() && params[1]
                .isNumber()) ? TypeFactory.mkBoolean() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        double d1;
        double d2;
        if (args[0].isInteger())
            d1 = ((IntegerValue) args[0]).value();
        else
            d1 = ((RealValue) args[0]).value();

        if (args[1].isInteger())
            d2 = ((IntegerValue) args[1]).value();
        else
            d2 = ((RealValue) args[1]).value();
        return BooleanValue.get(d1 > d2);
    }
}

// --------------------------------------------------------

/* <= : Number x Number -> Boolean */
final class Op_number_lessequal extends OpGeneric {
    public String name() {
        return "<=";
    }

    public int kind() {
        return PREDICATE;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 2 && params[0].isNumber() && params[1]
                .isNumber()) ? TypeFactory.mkBoolean() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        double d1;
        double d2;
        if (args[0].isInteger())
            d1 = ((IntegerValue) args[0]).value();
        else
            d1 = ((RealValue) args[0]).value();

        if (args[1].isInteger())
            d2 = ((IntegerValue) args[1]).value();
        else
            d2 = ((RealValue) args[1]).value();
        return BooleanValue.get(d1 <= d2);
    }
}

// --------------------------------------------------------

/* >= : Number x Number -> Boolean */
final class Op_number_greaterequal extends OpGeneric {
    public String name() {
        return ">=";
    }

    public int kind() {
        return PREDICATE;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 2 && params[0].isNumber() && params[1]
                .isNumber()) ? TypeFactory.mkBoolean() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        double d1;
        double d2;
        if (args[0].isInteger())
            d1 = ((IntegerValue) args[0]).value();
        else
            d1 = ((RealValue) args[0]).value();

        if (args[1].isInteger())
            d2 = ((IntegerValue) args[1]).value();
        else
            d2 = ((RealValue) args[1]).value();
        return BooleanValue.get(d1 >= d2);
    }
}

// --------------------------------------------------------
// 
// String operations.
//
// --------------------------------------------------------

/* size : String -> Integer */
final class Op_string_size extends OpGeneric {
    public String name() {
        return "size";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length == 1 && params[0].isString()) ? TypeFactory
                .mkInteger() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        int res = ((StringValue) args[0]).value().length();
        return new IntegerValue(res);
    }
}

// --------------------------------------------------------

/* concat : String x String -> String */
final class Op_string_concat extends OpGeneric {
    public String name() {
        return "concat";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length == 2 && params[0].isString() && params[1]
                .isString()) ? params[0] : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        String s1 = ((StringValue) args[0]).value();
        String s2 = ((StringValue) args[1]).value();
        return new StringValue(s1 + s2);
    }
}

// --------------------------------------------------------

/* toUpper : String -> String */
final class Op_string_toUpper extends OpGeneric {
    public String name() {
        return "toUpper";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length == 1 && params[0].isString()) ? params[0] : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        String s1 = ((StringValue) args[0]).value();
        return new StringValue(s1.toUpperCase());
    }
}

// --------------------------------------------------------

/* toLower : String -> String */
final class Op_string_toLower extends OpGeneric {
    public String name() {
        return "toLower";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length == 1 && params[0].isString()) ? params[0] : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        String s1 = ((StringValue) args[0]).value();
        return new StringValue(s1.toLowerCase());
    }
}

// --------------------------------------------------------

/* substring : String x Integer x Integer -> String */
final class Op_string_substring extends OpGeneric {
    public String name() {
        return "substring";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length == 3 && params[0].isString()
                && params[1].isInteger() && params[2].isInteger()) ? params[0]
                : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        String s1 = ((StringValue) args[0]).value();
        int lower = ((IntegerValue) args[1]).value();
        int upper = ((IntegerValue) args[2]).value();

        // return empty string in case of invalid range
        String s;
        try {
            s = s1.substring(lower - 1, upper);
        } catch (StringIndexOutOfBoundsException e) {
            s = "";
        }
        return new StringValue(s);
    }
}

// --------------------------------------------------------

/* < : String x String -> Boolean */
final class Op_string_less extends OpGeneric {
    public String name() {
        return "<";
    }

    public int kind() {
        return PREDICATE;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 2 && params[0].isString() && params[1]
                .isString()) ? TypeFactory.mkBoolean() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        int c = args[0].compareTo(args[1]);
        return (c < 0) ? BooleanValue.TRUE : BooleanValue.FALSE;
    }
}

// --------------------------------------------------------

/* > : String x String -> Boolean */
final class Op_string_greater extends OpGeneric {
    public String name() {
        return ">";
    }

    public int kind() {
        return PREDICATE;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 2 && params[0].isString() && params[1]
                .isString()) ? TypeFactory.mkBoolean() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        int c = args[0].compareTo(args[1]);
        return (c > 0) ? BooleanValue.TRUE : BooleanValue.FALSE;
    }
}

// --------------------------------------------------------

/* <= : String x String -> Boolean */
final class Op_string_lessequal extends OpGeneric {
    public String name() {
        return "<=";
    }

    public int kind() {
        return PREDICATE;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 2 && params[0].isString() && params[1]
                .isString()) ? TypeFactory.mkBoolean() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        int c = args[0].compareTo(args[1]);
        return (c <= 0) ? BooleanValue.TRUE : BooleanValue.FALSE;
    }
}

// --------------------------------------------------------

/* >= : String x String -> Boolean */
final class Op_string_greaterequal extends OpGeneric {
    public String name() {
        return ">=";
    }

    public int kind() {
        return PREDICATE;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 2 && params[0].isString() && params[1]
                .isString()) ? TypeFactory.mkBoolean() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        int c = args[0].compareTo(args[1]);
        return (c >= 0) ? BooleanValue.TRUE : BooleanValue.FALSE;
    }
}

// --------------------------------------------------------
//
// Boolean operations.
//
// --------------------------------------------------------

/**
 * This class is the base class for boolean operations. Boolean operations need
 * special treatment of undefined arguments. Also, short-circuit evaluation may
 * be used to speed up the evaluation process.
 */
abstract class BooleanOperation extends OpGeneric {
    public int kind() {
        return SPECIAL;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        throw new RuntimeException("Use evalWithArgs");
    }

    public abstract Value evalWithArgs(EvalContext ctx, Expression args[]);
}

// --------------------------------------------------------

/* or : Boolean x Boolean -> Boolean */
final class Op_boolean_or extends BooleanOperation {
    public String name() {
        return "or";
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 2 && params[0].isBoolean() && params[1]
                .isBoolean()) ? params[0] : null;
    }

    public Value evalWithArgs(EvalContext ctx, Expression args[]) {
        Value v1 = args[0].eval(ctx);
        Value v2 = args[1].eval(ctx);
        if (v1.isDefined()) {
            boolean b1 = ((BooleanValue) v1).value();
            if (b1)
                return BooleanValue.TRUE;
            else {
                // Value v2 = args[1].eval(ctx);
                return v2;
            }
        } else {
            // Value v2 = args[1].eval(ctx);
            if (v2.isDefined()) {
                boolean b2 = ((BooleanValue) v2).value();
                if (b2)
                    return BooleanValue.TRUE;
            }
            return new UndefinedValue(TypeFactory.mkBoolean());
        }
    }
}

// --------------------------------------------------------

/* xor : Boolean x Boolean -> Boolean */
final class Op_boolean_xor extends BooleanOperation {
    public String name() {
        return "xor";
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 2 && params[0].isBoolean() && params[1]
                .isBoolean()) ? params[0] : null;
    }

    public Value evalWithArgs(EvalContext ctx, Expression args[]) {
        Value v1 = args[0].eval(ctx);
        if (v1.isUndefined())
            return v1;

        boolean b1 = ((BooleanValue) v1).value();
        Value v2 = args[1].eval(ctx);
        if (!b1)
            return v2;
        else {
            if (v2.isUndefined())
                return v2;
            boolean b2 = ((BooleanValue) v2).value();
            return (b2) ? BooleanValue.FALSE : BooleanValue.TRUE;
        }
    }
}

// --------------------------------------------------------

/* and : Boolean x Boolean -> Boolean */
final class Op_boolean_and extends BooleanOperation {
    public String name() {
        return "and";
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 2 && params[0].isBoolean() && params[1]
                .isBoolean()) ? params[0] : null;
    }

    public Value evalWithArgs(EvalContext ctx, Expression args[]) {
        Value v1 = args[0].eval(ctx);
        Value v2 = args[1].eval(ctx);
        if (v1.isDefined()) {
            boolean b1 = ((BooleanValue) v1).value();
            if (!b1)
                return BooleanValue.FALSE;
            else {
                // Value v2 = args[1].eval(ctx);
                return v2;
            }
        } else {
            // Value v2 = args[1].eval(ctx);
            if (v2.isDefined()) {
                boolean b2 = ((BooleanValue) v2).value();
                if (!b2)
                    return BooleanValue.FALSE;
            }
            return new UndefinedValue(TypeFactory.mkBoolean());
        }
    }
}

// --------------------------------------------------------

/* not : Boolean -> Boolean */
final class Op_boolean_not extends BooleanOperation {
    public String name() {
        return "not";
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 1 && params[0].isBoolean()) ? params[0] : null;
    }

    public Value evalWithArgs(EvalContext ctx, Expression args[]) {
        Value v = args[0].eval(ctx);
        if (v.isUndefined())
            return v;
        boolean b = ((BooleanValue) v).value();
        return (b) ? BooleanValue.FALSE : BooleanValue.TRUE;
    }
}

// --------------------------------------------------------

/* implies : Boolean x Boolean -> Boolean */
final class Op_boolean_implies extends BooleanOperation {
    public String name() {
        return "implies";
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 2 && params[0].isBoolean() && params[1]
                .isBoolean()) ? params[0] : null;
    }

    public Value evalWithArgs(EvalContext ctx, Expression args[]) {
        Value v1 = args[0].eval(ctx);
        Value v2 = args[1].eval(ctx);
        if (v1.isDefined()) {
            boolean b1 = ((BooleanValue) v1).value();
            if (!b1)
                return BooleanValue.TRUE;
            else {
                // Value v2 = args[1].eval(ctx);
                return v2;
            }
        } else {
            // Value v2 = args[1].eval(ctx);
            if (v2.isDefined()) {
                boolean b2 = ((BooleanValue) v2).value();
                if (b2)
                    return BooleanValue.TRUE;
            }
            return new UndefinedValue(TypeFactory.mkBoolean());
        }
    }
}

// --------------------------------------------------------
// 
// Collection operations.
//
// --------------------------------------------------------

/* size : Collection(T) -> Integer */
final class Op_collection_size extends OpGeneric {
    public String name() {
        return "size";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length == 1 && params[0].isCollection()) ? TypeFactory
                .mkInteger() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        CollectionValue coll = (CollectionValue) args[0];
        return new IntegerValue(coll.size());
    }
}

// --------------------------------------------------------

/* includes : Collection(T2) x T1 -> Boolean, with T2 <= T1 */
final class Op_collection_includes extends OpGeneric {
    public String name() {
        return "includes";
    }

    // may test for undefined being an element of the collection
    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isCollection()) {
            CollectionType coll = (CollectionType) params[0];
            if (params[1].getLeastCommonSupertype(coll.elemType()) != null)
                return TypeFactory.mkBoolean();
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined())
            return BooleanValue.FALSE;
        CollectionValue coll = (CollectionValue) args[0];
        boolean res = coll.includes(args[1]);
        return BooleanValue.get(res);
    }
}

// --------------------------------------------------------

/* excludes : Collection(T2) x T1 -> Boolean, with T2 <= T1 */
final class Op_collection_excludes extends OpGeneric {
    public String name() {
        return "excludes";
    }

    // may test for undefined being an element of the collection
    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isCollection()) {
            CollectionType coll = (CollectionType) params[0];
            if (params[1].getLeastCommonSupertype(coll.elemType()) != null)
                return TypeFactory.mkBoolean();
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined())
            return BooleanValue.FALSE;
        CollectionValue coll = (CollectionValue) args[0];
        boolean res = !coll.includes(args[1]);
        return BooleanValue.get(res);
    }
}

// --------------------------------------------------------

/* count : Collection(T) x T -> Integer */
final class Op_collection_count extends OpGeneric {
    public String name() {
        return "count";
    }

    // may count occurrences of undefined in the collection
    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isCollection()) {
            CollectionType coll = (CollectionType) params[0];
            if (params[1].getLeastCommonSupertype(coll.elemType()) != null)
                return TypeFactory.mkInteger();
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined())
            return new IntegerValue(0);
        CollectionValue coll = (CollectionValue) args[0];
        int res = coll.count(args[1]);
        return new IntegerValue(res);
    }
}

// --------------------------------------------------------

/* includesAll : Collection(T) x Collection(T) -> Boolean */
final class Op_collection_includesAll extends OpGeneric {
    public String name() {
        return "includesAll";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isCollection()
                && params[1].isCollection()) {
            CollectionType coll1 = (CollectionType) params[0];
            CollectionType coll2 = (CollectionType) params[1];
            
            if (coll2.getLeastCommonSupertype(coll1) != null)
                return TypeFactory.mkBoolean();
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        CollectionValue coll1 = (CollectionValue) args[0];
        CollectionValue coll2 = (CollectionValue) args[1];
        boolean res = coll1.includesAll(coll2);
        return BooleanValue.get(res);
    }
}

// --------------------------------------------------------

/* excludesAll : Collection(T) x Collection(T) -> Boolean */
final class Op_collection_excludesAll extends OpGeneric {
    public String name() {
        return "excludesAll";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isCollection()
                && params[1].isCollection()) {
            CollectionType coll1 = (CollectionType) params[0];
            CollectionType coll2 = (CollectionType) params[1];
     
            if (coll2.getLeastCommonSupertype(coll1) != null)
                return TypeFactory.mkBoolean();
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        CollectionValue coll1 = (CollectionValue) args[0];
        CollectionValue coll2 = (CollectionValue) args[1];
        boolean res = coll1.excludesAll(coll2);
        return BooleanValue.get(res);
    }
}

// --------------------------------------------------------

/* isEmpty : Collection(T) -> Boolean */
final class Op_collection_isEmpty extends OpGeneric {
    public String name() {
        return "isEmpty";
    }

    public int kind() {
        return PREDICATE;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length == 1 && params[0].isCollection()) ? TypeFactory
                .mkBoolean() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        CollectionValue coll = (CollectionValue) args[0];
        return BooleanValue.get(coll.isEmpty());
    }
}

// --------------------------------------------------------

/* notEmpty : Collection(T) -> Boolean */
final class Op_collection_notEmpty extends OpGeneric {
    public String name() {
        return "notEmpty";
    }

    public int kind() {
        return PREDICATE;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length == 1 && params[0].isCollection()) ? TypeFactory
                .mkBoolean() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        CollectionValue coll = (CollectionValue) args[0];
        return BooleanValue.get(!coll.isEmpty());
    }
}

// --------------------------------------------------------

/* sum : Collection(T) -> T */
final class Op_collection_sum extends OpGeneric {
    public String name() {
        return "sum";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 1 && params[0].isCollection()) {
            CollectionType c = (CollectionType) params[0];
            if (c.elemType().isInteger())
                return TypeFactory.mkInteger();
            else if (c.elemType().isReal())
                return TypeFactory.mkReal();
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        CollectionValue coll = (CollectionValue) args[0];
        boolean isIntegerCollection = coll.elemType().isInteger();

        if (isIntegerCollection) {
            int isum = 0;
            for (Value v : coll) {
                if (v.isUndefined())
                    return new UndefinedValue(TypeFactory.mkInteger());
                isum += ((IntegerValue) v).value();
            }
            return new IntegerValue(isum);
        } else {
            double rsum = 0.0;

            for (Value v : coll) {
                if (v.isUndefined())
                    return new UndefinedValue(TypeFactory.mkReal());
                if (v.isInteger())
                    rsum += ((IntegerValue) v).value();
                else
                    rsum += ((RealValue) v).value();
            }
            return new RealValue(rsum);
        }
    }
}

/* product : Collection(T) x Collection(T2) -> Set(Tuple(first: T, second : T2)) */
final class Op_collection_product extends OpGeneric {
    public String name() {
        return "product";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isCollection() && params[1].isCollection()) {
            CollectionType c = (CollectionType) params[0];
            CollectionType c2 = (CollectionType) params[1];
            
            Part[] parts = new Part[2];
        	parts[0] = new Part("first", c.elemType());
        	parts[1] = new Part("second", c2.elemType());
        	
        	TupleType tupleType = TypeFactory.mkTuple(parts);
        	return TypeFactory.mkSet(tupleType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
    	if (args[0].isUndefined() || args[1].isUndefined())
    		return new UndefinedValue(resultType);
    	
        CollectionValue col1 = (CollectionValue) args[0];
        CollectionValue col2 = (CollectionValue) args[1];
        
        return col1.product(col2);        
    }
}

// --------------------------------------------------------

/* flatten : C1(C2(T)) -> C1(T) */
final class Op_collection_flatten extends OpGeneric {
    public String name() {
        return "flatten";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 1 && params[0].isCollection()) {
            CollectionType c1 = (CollectionType) params[0];
            if (c1.elemType().isCollection()) {
                CollectionType c2 = (CollectionType) c1.elemType();
                if (c1.isSet())
                    return TypeFactory.mkSet(c2.elemType());
                else if (c1.isBag())
                    return TypeFactory.mkBag(c2.elemType());
                else if (c1.isSequence() /* && c2.isSequence() */)
                    return TypeFactory.mkSequence(c2.elemType());
                else
                	return c1.elemType();
            }
            else
            {
            	return c1;
            }
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        CollectionValue coll = (CollectionValue) args[0];
        
        for (Value elem : coll) {
            if (elem.isUndefined())
                return new UndefinedValue(resultType);
        }
        if (coll.isBag())
            return ((BagValue) coll).flatten();
        else if (coll.isSet())
            return ((SetValue) coll).flatten();
        else if (coll.isSequence())
            return ((SequenceValue) coll).flatten();
        else if (coll.isOrderedSet())
        	return ((OrderedSetValue) coll).flatten();
        else
            throw new RuntimeException("Unexpected collection type `"
                    + coll.type() + "'.");
    }
}

// --------------------------------------------------------
// 
// Set operations.
//
// --------------------------------------------------------

/* union : Set(T1) x Set(T2) -> Set(T1), with T2 <= T1 */
final class Op_set_union extends OpGeneric {
    public String name() {
        return "union";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isSet() && params[1].isSet()) {
            SetType set1 = (SetType) params[0];
            SetType set2 = (SetType) params[1];
            
            return set1.getLeastCommonSupertype(set2);            
        }
        
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SetValue set1 = (SetValue) args[0];
        SetValue set2 = (SetValue) args[1];
        return set1.union(set2);
    }
}

// --------------------------------------------------------

/* union : Set(T1) x Bag(T2) -> Bag(T1), with T2 <= T1 */
final class Op_set_union_bag extends OpGeneric {
    public String name() {
        return "union";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isSet() && params[1].isBag()) {
            SetType set = (SetType) params[0];
            BagType bag = (BagType) params[1];
            Type newElementType = set.elemType().getLeastCommonSupertype(bag.elemType());
            
            if (newElementType != null)
                return TypeFactory.mkBag(newElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SetValue set = (SetValue) args[0];
        BagValue bag = (BagValue) args[1];
        return set.union(bag);
    }
}

// --------------------------------------------------------

/* intersection : Set(T1) x Set(T2) -> Set(T1), with T2 <= T1 */
final class Op_set_intersection extends OpGeneric {
    public String name() {
        return "intersection";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isSet() && params[1].isSet()) {
            SetType set1 = (SetType) params[0];
            SetType set2 = (SetType) params[1];
            Type commonElementType = set1.elemType().getLeastCommonSupertype(set2.elemType());
            
            if (commonElementType != null)
                return TypeFactory.mkSet(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SetValue set1 = (SetValue) args[0];
        SetValue set2 = (SetValue) args[1];
        return set1.intersection(set2);
    }
}

// --------------------------------------------------------

/* intersection : Set(T1) x Bag(T2) -> Set(T1), with T2 <= T1 */
final class Op_set_intersection_bag extends OpGeneric {
    public String name() {
        return "intersection";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isSet() && params[1].isBag()) {
            SetType set = (SetType) params[0];
            BagType bag = (BagType) params[1];

            Type commonElementType = set.elemType().getLeastCommonSupertype(bag.elemType());
            
            if (commonElementType != null)
                return TypeFactory.mkSet(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SetValue set = (SetValue) args[0];
        BagValue bag = (BagValue) args[1];
        return set.intersection(bag);
    }
}

// --------------------------------------------------------

/* - : Set(T1) x Set(T2) -> Set(T1), with T2 <= T1 */
final class Op_set_difference extends OpGeneric {
    public String name() {
        return "-";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isSet() && params[1].isSet()) {
            SetType set1 = (SetType) params[0];
            SetType set2 = (SetType) params[1];
            Type commonElementType = set1.elemType().getLeastCommonSupertype(set2.elemType());
            
            if (commonElementType != null)
                return TypeFactory.mkSet(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SetValue set1 = (SetValue) args[0];
        SetValue set2 = (SetValue) args[1];
        return set1.difference(set2);
    }
}

// --------------------------------------------------------

/* including : Set(T1) x T2 -> Set(T1), with T2 <= T1 */
final class Op_set_including extends OpGeneric {
    public String name() {
        return "including";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isSet()) {
            SetType set1 = (SetType) params[0];
            
            Type commonElementType = set1.elemType().getLeastCommonSupertype(params[1]);
            
            if (commonElementType != null)
            	return TypeFactory.mkSet(commonElementType);

        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined())
            return new UndefinedValue(resultType);
        SetValue set1 = (SetValue) args[0];
        return set1.including(args[1]);
    }
}

// --------------------------------------------------------

/* excluding : Set(T1) x T2 -> Set(T1), with T2 <= T1 */
final class Op_set_excluding extends OpGeneric {
    public String name() {
        return "excluding";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isSet()) {
            SetType set1 = (SetType) params[0];
            Type commonElementType = set1.elemType().getLeastCommonSupertype(params[1]);
            
            if (commonElementType != null)
                return TypeFactory.mkSet(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined())
            return new UndefinedValue(resultType);
        SetValue set1 = (SetValue) args[0];
        return set1.excluding(args[1]);
    }
}

// --------------------------------------------------------

/* symmetricDifference : Set(T1) x Set(T2) -> Set(T1) with T2 <= T1 */
final class Op_set_symmetricDifference extends OpGeneric {
    public String name() {
        return "symmetricDifference";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isSet() && params[1].isSet()) {
            SetType set1 = (SetType) params[0];
            SetType set2 = (SetType) params[1];
            
            Type commonElementType = set1.elemType().getLeastCommonSupertype(set2.elemType());
            
            if (commonElementType != null)
                return TypeFactory.mkSet(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SetValue set1 = (SetValue) args[0];
        SetValue set2 = (SetValue) args[1];
        return set1.symmetricDifference(set2);
    }
}

// --------------------------------------------------------

/* asSequence : Set(T) -> Sequence(T) */
final class Op_set_asSequence extends OpGeneric {
    public String name() {
        return "asSequence";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 1 && params[0].isSet()) {
            SetType set = (SetType) params[0];
            return TypeFactory.mkSequence(set.elemType());
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SetValue set = (SetValue) args[0];
        return set.asSequence();
    }
}

// --------------------------------------------------------

/* asBag : Set(T) -> Bag(T) */
final class Op_set_asBag extends OpGeneric {
    public String name() {
        return "asBag";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 1 && params[0].isSet()) {
            SetType set = (SetType) params[0];
            return TypeFactory.mkBag(set.elemType());
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SetValue set = (SetValue) args[0];
        return set.asBag();
    }
}

// --------------------------------------------------------
//
// Bag operations.
//
// --------------------------------------------------------

/* union : Bag(T1) x Bag(T2) -> Bag(T1), with T2 <= T1 */
final class Op_bag_union extends OpGeneric {
    public String name() {
        return "union";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isBag() && params[1].isBag()) {
            BagType bag1 = (BagType) params[0];
            BagType bag2 = (BagType) params[1];
            
            Type commonElementType = bag1.elemType().getLeastCommonSupertype(bag2.elemType());
            
            if (commonElementType != null)
                return TypeFactory.mkBag(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        BagValue bag1 = (BagValue) args[0];
        BagValue bag2 = (BagValue) args[1];
        return bag1.union(bag2);
    }
}

// --------------------------------------------------------

/* union : Bag(T1) x Set(T2) -> Bag(T1), with T2 <= T1 */
final class Op_bag_union_set extends OpGeneric {
    public String name() {
        return "union";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isBag() && params[1].isSet()) {
            BagType bag = (BagType) params[0];
            SetType set = (SetType) params[1];
            
            Type commonElementType = bag.elemType().getLeastCommonSupertype(set.elemType());
            
            if (commonElementType != null)
                return TypeFactory.mkBag(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        BagValue bag = (BagValue) args[0];
        SetValue set = (SetValue) args[1];
        return bag.union(set.asBag());
    }
}

// --------------------------------------------------------

/* intersection : Bag(T1) x Bag(T2) -> Bag(T1), with T2 <= T1 */
final class Op_bag_intersection extends OpGeneric {
    public String name() {
        return "intersection";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isBag() && params[1].isBag()) {
            BagType bag1 = (BagType) params[0];
            BagType bag2 = (BagType) params[1];
            
            Type commonElementType = bag1.elemType().getLeastCommonSupertype(bag2.elemType());
            
            if (commonElementType != null)
                return TypeFactory.mkBag(commonElementType);
            
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        BagValue bag1 = (BagValue) args[0];
        BagValue bag2 = (BagValue) args[1];
        return bag1.intersection(bag2);
    }
}

// --------------------------------------------------------

/* intersection : Bag(T1) x Set(T2) -> Set(T1), with T2 <= T1 */
final class Op_bag_intersection_set extends OpGeneric {
    public String name() {
        return "intersection";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isBag() && params[1].isSet()) {
            BagType bag = (BagType) params[0];
            SetType set = (SetType) params[1];
            
            Type commonElementType = bag.elemType().getLeastCommonSupertype(set.elemType());
            
            if (commonElementType != null)
                return TypeFactory.mkSet(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        BagValue bag = (BagValue) args[0];
        SetValue set = (SetValue) args[1];
        return bag.asSet().intersection(set);
    }
}

// --------------------------------------------------------

/* including : Bag(T1) x T2 -> Bag(T1), with T2 <= T1 */
final class Op_bag_including extends OpGeneric {
    public String name() {
        return "including";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isBag()) {
            BagType bag = (BagType) params[0];
            Type commonElementType = bag.elemType().getLeastCommonSupertype(params[1]);
            
            if (commonElementType != null)
                return TypeFactory.mkBag(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined())
            return new UndefinedValue(resultType);
        BagValue bag = (BagValue) args[0];
        return bag.including(args[1]);
    }
}

// --------------------------------------------------------

/* excluding : Bag(T1) x T2 -> Bag(T1), with T2 <= T1 */
final class Op_bag_excluding extends OpGeneric {
    public String name() {
        return "excluding";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isBag()) {
            BagType bag = (BagType) params[0];
            Type commonElementType = bag.elemType().getLeastCommonSupertype(params[1]);
            
            if (commonElementType != null)
                return TypeFactory.mkBag(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined())
            return new UndefinedValue(resultType);
        BagValue bag = (BagValue) args[0];
        return bag.excluding(args[1]);
    }
}

// --------------------------------------------------------

/* asSequence : Bag(T) -> Sequence(T) */
final class Op_bag_asSequence extends OpGeneric {
    public String name() {
        return "asSequence";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 1 && params[0].isBag()) {
            BagType bag = (BagType) params[0];
            return TypeFactory.mkSequence(bag.elemType());
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        BagValue bag = (BagValue) args[0];
        return bag.asSequence();
    }
}

// --------------------------------------------------------

/* asSet : Bag(T) -> Set(T) */
final class Op_bag_asSet extends OpGeneric {
    public String name() {
        return "asSet";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 1 && params[0].isBag()) {
            BagType bag = (BagType) params[0];
            return TypeFactory.mkSet(bag.elemType());
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        BagValue bag = (BagValue) args[0];
        return bag.asSet();
    }
}

// --------------------------------------------------------
//
// Sequence operations.
//
// --------------------------------------------------------

/* union : Sequence(T1) x Sequence(T2) -> Sequence(T1), with T2 <= T1 */
final class Op_sequence_union extends OpGeneric {
    public String name() {
        return "union";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isSequence()
                && params[1].isSequence()) {
            SequenceType sequence1 = (SequenceType) params[0];
            SequenceType sequence2 = (SequenceType) params[1];
            
            Type commonElementType = sequence1.elemType().getLeastCommonSupertype(sequence2.elemType());
            
            if (commonElementType != null)
                return TypeFactory.mkSequence(commonElementType);
            
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SequenceValue sequence1 = (SequenceValue) args[0];
        SequenceValue sequence2 = (SequenceValue) args[1];
        return sequence1.union(sequence2);
    }
}

// --------------------------------------------------------

/* append : Sequence(T) x T -> Sequence(T) */
final class Op_sequence_append extends OpGeneric {
    public String name() {
        return "append";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isSequence()) {
            SequenceType seqType = (SequenceType) params[0];
            
            Type commonElementType = seqType.elemType().getLeastCommonSupertype(params[1]);
            
            if (commonElementType != null)
                return TypeFactory.mkSequence(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SequenceValue seq = (SequenceValue) args[0];
        return seq.append(args[1]);
    }
}

// --------------------------------------------------------

/* prepend : Sequence(T) x T -> Sequence(T) */
final class Op_sequence_prepend extends OpGeneric {
    public String name() {
        return "prepend";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isSequence()) {
            SequenceType seqType = (SequenceType) params[0];
            
            Type commonElementType = seqType.elemType().getLeastCommonSupertype(params[1]);
            
            if (commonElementType != null)
                return TypeFactory.mkSequence(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SequenceValue seq = (SequenceValue) args[0];
        return seq.prepend(args[1]);
    }
}

//--------------------------------------------------------

/* insertAt : Sequence(T) x Integer x T -> Sequence(T) */
final class Op_sequence_insertAt extends OpGeneric {
    public String name() {
        return "insertAt";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 3 && params[0].isSequence() && params[1].isInteger()) {
        	SequenceType seqType = (SequenceType) params[0];
            
            Type commonElementType = seqType.elemType().getLeastCommonSupertype(params[2]);
            
            if (commonElementType != null)
                return TypeFactory.mkSequence(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
    	SequenceValue seq = (SequenceValue) args[0];
    	SequenceValue res = seq.insertAt((IntegerValue)args[1], (Value)args[2]);
    	
    	if (res == null)
    		return new UndefinedValue(resultType);
    	else
    		return res;
    }
}

// --------------------------------------------------------

/* subSequence : Sequence(T) x Integer x Integer -> Sequence(T) */
final class Op_sequence_subSequence extends OpGeneric {
    public String name() {
        return "subSequence";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 3 && params[0].isSequence()
                && params[1].isInteger() && params[2].isInteger()) ? params[0]
                : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SequenceValue seq = (SequenceValue) args[0];
        int lower = ((IntegerValue) args[1]).value();
        int upper = ((IntegerValue) args[2]).value();
        if (lower > upper)
            return new UndefinedValue(resultType);

        Value res = null;
        try {
            res = seq.subSequence(lower - 1, upper);
        } catch (IndexOutOfBoundsException e) {
            res = new UndefinedValue(resultType);
        }
        return res;
    }
}

// --------------------------------------------------------

/* at : Sequence(T) x Integer -> T */
final class Op_sequence_at extends OpGeneric {
    public String name() {
        return "at";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isSequence()
                && params[1].isInteger()) {
            SequenceType seq = (SequenceType) params[0];
            return seq.elemType();
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SequenceValue seq = (SequenceValue) args[0];
        IntegerValue n = (IntegerValue) args[1];
        Value res = null;
        try {
            res = seq.get(n.value() - 1);
        } catch (IndexOutOfBoundsException e) {
            res = new UndefinedValue(resultType);
        }
        return res;
    }
}

// --------------------------------------------------------

/* first : Sequence(T) -> T */
final class Op_sequence_first extends OpGeneric {
    public String name() {
        return "first";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 1 && params[0].isSequence()) {
            SequenceType seq = (SequenceType) params[0];
            return seq.elemType();
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SequenceValue seq = (SequenceValue) args[0];
        if (seq.isEmpty())
            return new UndefinedValue(resultType);
        return seq.get(0);
    }
}

// --------------------------------------------------------

/* last : Sequence(T) -> T */
final class Op_sequence_last extends OpGeneric {
    public String name() {
        return "last";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 1 && params[0].isSequence()) {
            SequenceType seq = (SequenceType) params[0];
            return seq.elemType();
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SequenceValue seq = (SequenceValue) args[0];
        if (seq.isEmpty())
            return new UndefinedValue(resultType);
        return seq.get(seq.size() - 1);
    }
}

// --------------------------------------------------------

/* including : Sequence(T) x T -> Sequence(T) */
final class Op_sequence_including extends OpGeneric {
    public String name() {
        return "including";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isSequence()) {
            SequenceType seqType = (SequenceType) params[0];
            
            Type commonElementType = seqType.elemType().getLeastCommonSupertype(params[1]);
            
            if (commonElementType != null)
                return TypeFactory.mkSequence(commonElementType);
            
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined())
            return new UndefinedValue(resultType);
        SequenceValue seq = (SequenceValue) args[0];
        return seq.append(args[1]);
    }
}

// --------------------------------------------------------

/* excluding : Sequence(T) x T -> Sequence(T) */
final class Op_sequence_excluding extends OpGeneric {
    public String name() {
        return "excluding";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isSequence()) {
            SequenceType seqType = (SequenceType) params[0];
            
            Type commonElementType = seqType.elemType().getLeastCommonSupertype(params[1]);
            
            if (commonElementType != null)
                return TypeFactory.mkSequence(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined())
            return new UndefinedValue(resultType);
        SequenceValue seq = (SequenceValue) args[0];
        return seq.excluding(args[1]);
    }
}

/* indexOf : Sequence(T) x T -> Integer */
final class Op_sequence_indexOf extends OpGeneric {
    public String name() {
        return "indexOf";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isSequence()) {
            SequenceType seqType = (SequenceType) params[0];
            
            Type commonElementType = seqType.elemType().getLeastCommonSupertype(params[1]);
            
            if (commonElementType != null)
                return TypeFactory.mkInteger();
            
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined())
            return new UndefinedValue(resultType);
        
        SequenceValue seq = (SequenceValue) args[0];
        
        int index = seq.indexOf(args[1]);
        if (index == -1)
        	return new UndefinedValue(resultType);
        else
        	return new IntegerValue(index + 1);
    }
}

// --------------------------------------------------------

/* asBag : Sequence(T) -> Bag(T) */
final class Op_sequence_asBag extends OpGeneric {
    public String name() {
        return "asBag";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 1 && params[0].isSequence()) {
            SequenceType seq = (SequenceType) params[0];
            return TypeFactory.mkBag(seq.elemType());
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SequenceValue seq = (SequenceValue) args[0];
        Value[] elems = new Value[seq.size()];
        int i = 0;
        
        for (Value v : seq) {
            elems[i++] = v;
        }

        return new BagValue(seq.elemType(), elems);
    }
}

// --------------------------------------------------------

/* asSet : Sequence(T) -> Set(T) */
final class Op_sequence_asSet extends OpGeneric {
    public String name() {
        return "asSet";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 1 && params[0].isSequence()) {
            SequenceType seq = (SequenceType) params[0];
            return TypeFactory.mkSet(seq.elemType());
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SequenceValue seq = (SequenceValue) args[0];
        Value[] elems = new Value[seq.size()];
        int i = 0;
        
        for (Value v : seq) {
            elems[i++] = v;
        }
        
        // the set constructor will remove duplicates
        return new SetValue(seq.elemType(), elems);
    }
}

//--------------------------------------------------------
//
// OrderedSet operations.
//
// --------------------------------------------------------

/* union : OrderedSet(T1) x OrderedSet(T2) -> OrderedSet(T1), with T2 <= T1 */
final class Op_orderedSet_union extends OpGeneric {
    public String name() {
        return "union";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isOrderedSet()
                && params[1].isOrderedSet()) {
            OrderedSetType oset1 = (OrderedSetType) params[0];
            OrderedSetType oset2 = (OrderedSetType) params[1];
            
            Type commonElementType = oset1.elemType().getLeastCommonSupertype(oset2.elemType());
            
            if (commonElementType != null)
                return TypeFactory.mkOrderedSet(commonElementType);
            
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        OrderedSetValue oset1 = (OrderedSetValue) args[0];
        OrderedSetValue oset2 = (OrderedSetValue) args[1];
        return oset1.union(oset2);
    }
}

// --------------------------------------------------------

/* append : OrderedSet(T) x T -> OrderedSet(T) */
final class Op_orderedSet_append extends OpGeneric {
    public String name() {
        return "append";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isOrderedSet()) {
            OrderedSetType osetType = (OrderedSetType) params[0];
            
            Type commonElementType = osetType.elemType().getLeastCommonSupertype(params[1]);
            
            if (commonElementType != null)
                return TypeFactory.mkOrderedSet(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        OrderedSetValue oset = (OrderedSetValue) args[0];
        return oset.append(args[1]);
    }
}

// --------------------------------------------------------

/* prepend : OrderedSet(T) x T -> OrderedSet(T) */
final class Op_orderedSet_prepend extends OpGeneric {
    public String name() {
        return "prepend";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isOrderedSet()) {
        	OrderedSetType osetType = (OrderedSetType) params[0];
            
            Type commonElementType = osetType.elemType().getLeastCommonSupertype(params[1]);
            
            if (commonElementType != null)
                return TypeFactory.mkOrderedSet(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
    	OrderedSetValue oset = (OrderedSetValue) args[0];
        return oset.prepend(args[1]);
    }
}

//--------------------------------------------------------

/* insertAt : OrderedSet(T) x Integer x T -> OrderedSet(T) */
final class Op_orderedSet_insertAt extends OpGeneric {
    public String name() {
        return "insertAt";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 3 && params[0].isOrderedSet() && params[1].isInteger()) {
        	OrderedSetType osetType = (OrderedSetType) params[0];
            
            Type commonElementType = osetType.elemType().getLeastCommonSupertype(params[2]);
            
            if (commonElementType != null)
                return TypeFactory.mkOrderedSet(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
    	OrderedSetValue oset = (OrderedSetValue) args[0];
    	OrderedSetValue res = oset.insertAt((IntegerValue)args[1], (Value)args[2]);
    	
    	if (res == null)
    		return new UndefinedValue(resultType);
    	else
    		return res;
    }
}

// --------------------------------------------------------

/* subOrderedSet : OrderedSet(T) x Integer x Integer -> OrderedSet(T) */
final class Op_orderedSet_subSequence extends OpGeneric {
    public String name() {
        return "subOrderedSet";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        return (params.length == 3 && params[0].isOrderedSet()
                && params[1].isInteger() && params[2].isInteger()) ? params[0]
                : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        OrderedSetValue seq = (OrderedSetValue) args[0];
        int lower = ((IntegerValue) args[1]).value();
        int upper = ((IntegerValue) args[2]).value();
        if (lower > upper)
            return new UndefinedValue(resultType);

        Value res = null;
        try {
            res = seq.subOrderedSet(lower - 1, upper);
        } catch (IndexOutOfBoundsException e) {
            res = new UndefinedValue(resultType);
        }
        return res;
    }
}

// --------------------------------------------------------

/* at : OrderedSet(T) x Integer -> T */
final class Op_orderedSet_at extends OpGeneric {
    public String name() {
        return "at";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isOrderedSet()
                && params[1].isInteger()) {
            OrderedSetType oset = (OrderedSetType) params[0];
            return oset.elemType();
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
    	OrderedSetValue oset = (OrderedSetValue) args[0];
        IntegerValue n = (IntegerValue) args[1];
        Value res = null;
        try {
            res = oset.get(n.value() - 1);
        } catch (IndexOutOfBoundsException e) {
            res = new UndefinedValue(resultType);
        }
        return res;
    }
}

// --------------------------------------------------------

/* first : OrderedSet(T) -> T */
final class Op_orderedSet_first extends OpGeneric {
    public String name() {
        return "first";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 1 && params[0].isOrderedSet()) {
        	OrderedSetType oset = (OrderedSetType) params[0];
            return oset.elemType();
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
    	OrderedSetValue oset = (OrderedSetValue) args[0];
        if (oset.isEmpty())
            return new UndefinedValue(resultType);
        return oset.get(0);
    }
}

// --------------------------------------------------------

/* last : OrderedSet(T) -> T */
final class Op_orderedSet_last extends OpGeneric {
    public String name() {
        return "last";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 1 && params[0].isOrderedSet()) {
        	OrderedSetType oset = (OrderedSetType) params[0];
            return oset.elemType();
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
    	OrderedSetValue oset = (OrderedSetValue) args[0];
        if (oset.isEmpty())
            return new UndefinedValue(resultType);
        return oset.get(oset.size() - 1);
    }
}

// --------------------------------------------------------

/* including : OrderedSet(T) x T -> OrderedSet(T) */
final class Op_orderedSet_including extends OpGeneric {
    public String name() {
        return "including";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isOrderedSet()) {
        	OrderedSetType osetType = (OrderedSetType) params[0];
            
            Type commonElementType = osetType.elemType().getLeastCommonSupertype(params[1]);
            
            if (commonElementType != null)
                return TypeFactory.mkOrderedSet(commonElementType);
            
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined())
            return new UndefinedValue(resultType);
        OrderedSetValue oset = (OrderedSetValue) args[0];
        return oset.append(args[1]);
    }
}

// --------------------------------------------------------

/* excluding : OrderedSet(T) x T -> OrderedSet(T) */
final class Op_orderedSet_excluding extends OpGeneric {
    public String name() {
        return "excluding";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isOrderedSet()) {
        	OrderedSetType osetType = (OrderedSetType) params[0];
            
            Type commonElementType = osetType.elemType().getLeastCommonSupertype(params[1]);
            
            if (commonElementType != null)
                return TypeFactory.mkOrderedSet(commonElementType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined())
            return new UndefinedValue(resultType);
        OrderedSetValue oset = (OrderedSetValue) args[0];
        return oset.excluding(args[1]);
    }
}

/* indexOf : OrderedSet(T) x T -> Integer */
final class Op_orderedSet_indexOf extends OpGeneric {
    public String name() {
        return "indexOf";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 2 && params[0].isOrderedSet()) {
            OrderedSetType osetType = (OrderedSetType) params[0];
            
            Type commonElementType = osetType.elemType().getLeastCommonSupertype(params[1]);
            
            if (commonElementType != null)
                return TypeFactory.mkInteger();
            
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined())
            return new UndefinedValue(resultType);
        
        OrderedSetValue oset = (OrderedSetValue) args[0];
        
        int index = oset.indexOf(args[1]);
        if (index == -1)
        	return new UndefinedValue(resultType);
        else
        	return new IntegerValue(index + 1);
    }
}

// --------------------------------------------------------

/* asBag : OrderedSet(T) -> Bag(T) */
final class Op_orderedSet_asBag extends OpGeneric {
    public String name() {
        return "asBag";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 1 && params[0].isOrderedSet()) {
        	OrderedSetType oset = (OrderedSetType) params[0];
            return TypeFactory.mkBag(oset.elemType());
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
    	OrderedSetValue oset = (OrderedSetValue) args[0];
        Value[] elems = new Value[oset.size()];
        int i = 0;
        
        for (Value v : oset) {
            elems[i++] = v;
        }
        
        return new BagValue(oset.elemType(), elems);
    }
}

// --------------------------------------------------------

/* asSet : OrderedSet(T) -> Set(T) */
final class Op_orderedSet_asSet extends OpGeneric {
    public String name() {
        return "asSet";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 1 && params[0].isOrderedSet()) {
        	OrderedSetType seq = (OrderedSetType) params[0];
            return TypeFactory.mkSet(seq.elemType());
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
    	OrderedSetValue oset = (OrderedSetValue) args[0];
        Value[] elems = new Value[oset.size()];
        int i = 0;
        
        for (Value v : oset) {
            elems[i++] = v;
        }
        
        return new SetValue(oset.elemType(), elems);
    }
}

// --------------------------------------------------------
//
// Construction of collection literals.
//
// --------------------------------------------------------

/* mkSet : T x T x ... x T -> Set(T) */
final class Op_mkSet extends OpGeneric {
    public String name() {
        return "mkSet";
    }

    // may include undefined elements
    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length > 0) {
            final Type elemType = params[0];

            // all arguments of set constructor must have equal type
            // FIXME: relax to common base type?
            for (int i = 1; i < params.length; i++)
                if (!params[i].equals(elemType))
                    return null;
            return TypeFactory.mkSet(elemType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        return new SetValue(args[0].type(), args);
    }

    public String stringRep(Expression args[], String atPre) {
        return "Set{" + StringUtil.fmtSeq(args, ",") + "}";
    }
}

// --------------------------------------------------------

/* mkSetRange : Integer x Integer, ... -> Set(Integer) */
final class Op_mkSetRange extends OpGeneric {
    public String name() {
        return "mkSetRange";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length >= 2 && params.length % 2 == 0
                && params[0].isInteger() && params[1].isInteger()) ? TypeFactory
                .mkSet(TypeFactory.mkInteger())
                : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        int[] ranges = new int[args.length];
        for (int i = 0; i < args.length; i++)
            ranges[i] = ((IntegerValue) args[i]).value();

        return new SetValue(TypeFactory.mkInteger(), ranges);
    }

    public String stringRep(Expression args[], String atPre) {
        if (args.length % 2 != 0)
            throw new IllegalArgumentException("length=" + args.length);
        String s = "Set{";
        for (int i = 0; i < args.length; i += 2) {
            if (i > 0)
                s += ",";
            s += args[i] + ".." + args[i + 1];
        }
        s += "}";
        return s;
    }
}

// --------------------------------------------------------

/* mkSequence : T x T x ... x T -> Sequence(T) */
final class Op_mkSequence extends OpGeneric {
    public String name() {
        return "mkSequence";
    }

    // may include undefined elements
    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length > 0) {
            final Type elemType = params[0];
            // all arguments of set constructor must have equal type
            // FIXME: relax to common base type?
            for (int i = 1; i < params.length; i++)
                if (!params[i].equals(elemType))
                    return null;
            return TypeFactory.mkSequence(elemType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        return new SequenceValue(args[0].type(), args);
    }

    public String stringRep(Expression args[], String atPre) {
        return "Sequence{" + StringUtil.fmtSeq(args, ",") + "}";
    }
}

// --------------------------------------------------------

/* mkSequenceRange : Integer x Integer, ... -> Sequence(Integer) */
final class Op_mkSequenceRange extends OpGeneric {
    public String name() {
        return "mkSequenceRange";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length >= 2 && params.length % 2 == 0
                && params[0].isInteger() && params[1].isInteger()) ? TypeFactory
                .mkSequence(TypeFactory.mkInteger())
                : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        int[] ranges = new int[args.length];
        for (int i = 0; i < args.length; i++)
            ranges[i] = ((IntegerValue) args[i]).value();

        return new SequenceValue(TypeFactory.mkInteger(), ranges);
    }

    public String stringRep(Expression args[], String atPre) {
        if (args.length % 2 != 0)
            throw new IllegalArgumentException("length=" + args.length);
        String s = "Sequence{";
        for (int i = 0; i < args.length; i += 2) {
            if (i > 0)
                s += ",";
            s += args[i] + ".." + args[i + 1];
        }
        s += "}";
        return s;
    }
}

// --------------------------------------------------------

/* mkBag : T x T x ... x T -> Bag(T) */
final class Op_mkBag extends OpGeneric {
    public String name() {
        return "mkBag";
    }

    // may include undefined elements
    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length > 0) {
            final Type elemType = params[0];
            // all arguments of set constructor must have equal type
            // FIXME: relax to common base type?
            for (int i = 1; i < params.length; i++)
                if (!params[i].equals(elemType))
                    return null;
            return TypeFactory.mkBag(elemType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        return new BagValue(args[0].type(), args);
    }

    public String stringRep(Expression args[], String atPre) {
        return "Bag{" + StringUtil.fmtSeq(args, ",") + "}";
    }
}

// --------------------------------------------------------

/* mkBagRange : Integer x Integer, ... -> Bag(Integer) */
final class Op_mkBagRange extends OpGeneric {
    public String name() {
        return "mkBagRange";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length >= 2 && params.length % 2 == 0
                && params[0].isInteger() && params[1].isInteger()) ? TypeFactory
                .mkBag(TypeFactory.mkInteger())
                : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        int[] ranges = new int[args.length];
        for (int i = 0; i < args.length; i++)
            ranges[i] = ((IntegerValue) args[i]).value();

        return new BagValue(TypeFactory.mkInteger(), ranges);
    }

    public String stringRep(Expression args[], String atPre) {
        if (args.length % 2 != 0)
            throw new IllegalArgumentException("length=" + args.length);
        String s = "Bag{";
        for (int i = 0; i < args.length; i += 2) {
            if (i > 0)
                s += ",";
            s += args[i] + ".." + args[i + 1];
        }
        s += "}";
        return s;
    }
}

//--------------------------------------------------------

/* mkOrderedSet : T x T x ... x T -> OrderedSet(T) */
final class Op_mkOrderedSet extends OpGeneric {
    public String name() {
        return "mkOrderedSet";
    }

    // may include undefined elements
    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length > 0) {
            final Type elemType = params[0];
            // all arguments of set constructor must have equal type
            // FIXME: relax to common base type?
            for (int i = 1; i < params.length; i++)
                if (!params[i].equals(elemType))
                    return null;
            return TypeFactory.mkOrderedSet(elemType);
        }
        return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        return new OrderedSetValue(args[0].type(), args);
    }

    public String stringRep(Expression args[], String atPre) {
        return "OrderedSet{" + StringUtil.fmtSeq(args, ",") + "}";
    }
}

// --------------------------------------------------------

/* mkSequenceRange : Integer x Integer, ... -> OrderedSet(Integer) */
final class Op_mkOrderedSetRange extends OpGeneric {
    public String name() {
        return "mkOrderedSetRange";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length >= 2 && params.length % 2 == 0
                && params[0].isInteger() && params[1].isInteger()) ? TypeFactory
                .mkOrderedSet(TypeFactory.mkInteger())
                : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        int[] ranges = new int[args.length];
        for (int i = 0; i < args.length; i++)
            ranges[i] = ((IntegerValue) args[i]).value();

        return new OrderedSetValue(TypeFactory.mkInteger(), ranges);
    }

    public String stringRep(Expression args[], String atPre) {
        if (args.length % 2 != 0)
            throw new IllegalArgumentException("length=" + args.length);
        String s = "OrderedSet{";
        for (int i = 0; i < args.length; i += 2) {
            if (i > 0)
                s += ",";
            s += args[i] + ".." + args[i + 1];
        }
        s += "}";
        return s;
    }
}
// --------------------------------------------------------
//
// Generic operations on enumeration types.
//
// --------------------------------------------------------

// --------------------------------------------------------
//
// Generic operations on all types.
//
// --------------------------------------------------------

/* = : T1 x T2 -> Boolean, with T2 <= T1 or T1 <= T2 */
final class Op_equal extends OpGeneric {
    public String name() {
        return "=";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        if (params.length == 2
                && params[0].getLeastCommonSupertype(params[1]) != null)
            return TypeFactory.mkBoolean();
        else
            return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        boolean res;
        
        if (args[1].type().isSubtypeOf(args[0].type()))
            res = args[0].equals(args[1]);
        else if (args[0].type().isSubtypeOf(args[1].type()))
            res = args[1].equals(args[0]);
        else
        	res = false;
        
        return BooleanValue.get(res);
    }
}

// --------------------------------------------------------

/* <> : T1 x T2 -> Boolean, with T2 <= T1 or T1 <= T2 */
final class Op_notequal extends OpGeneric {
    public String name() {
        return "<>";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return true;
    }

    public Type matches(Type params[]) {
        if (params.length == 2
                && (params[1].isSubtypeOf(params[0]) || params[0]
                        .isSubtypeOf(params[1])))
            return TypeFactory.mkBoolean();
        else
            return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        boolean res = !args[0].equals(args[1]);
        return BooleanValue.get(res);
    }
}

// --------------------------------------------------------

/* isDefined : T -> Boolean */
final class Op_isDefined extends OpGeneric {
    public String name() {
        return "isDefined";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length == 1) ? TypeFactory.mkBoolean() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        boolean res = !args[0].isUndefined();
        return BooleanValue.get(res);
    }
}

// --------------------------------------------------------

/* isUndefined : T -> Boolean */
final class Op_isUndefined extends OpGeneric {
    public String name() {
        return "isUndefined";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        return (params.length == 1) ? TypeFactory.mkBoolean() : null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        boolean res = args[0].isUndefined();
        return BooleanValue.get(res);
    }
}

// --------------------------------------------------------
//
// Generic operations on all object types.
//
// --------------------------------------------------------

/* oclIsNew : T -> Boolean */
final class Op_oclIsNew extends OpGeneric {
    public String name() {
        return "oclIsNew";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Type matches(Type params[]) {
        if (params.length == 1 && params[0].isObjectType())
            return TypeFactory.mkBoolean();
        else
            return null;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        Value res;
        if (args[0].isUndefined())
            res = new UndefinedValue(resultType);
        else {
            // get object
            ObjectValue objVal = (ObjectValue) args[0];
            MObject obj = objVal.value();
            MObjectState objPreState = obj.state(ctx.preState());
            res = BooleanValue.get(objPreState == null);
        }
        return res;
    }
}
