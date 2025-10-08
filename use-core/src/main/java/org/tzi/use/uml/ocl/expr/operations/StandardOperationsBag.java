package org.tzi.use.uml.ocl.expr.operations;

import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ParamInfo;
import org.tzi.use.uml.ocl.type.BagType;
import org.tzi.use.uml.ocl.type.SetType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BagValue;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.StringUtil;

import com.google.common.collect.Multimap;

import java.util.List;

public class StandardOperationsBag {
    public static void registerTypeOperations(Multimap<String, OpGeneric> opmap) {
        // operations on Bag
        OpGeneric.registerOperation(new Op_bag_union(), opmap);
        OpGeneric.registerOperation(new Op_bag_union_set(), opmap);
        OpGeneric.registerOperation(new Op_bag_intersection(), opmap);
        OpGeneric.registerOperation(new Op_bag_intersection_set(), opmap);
        OpGeneric.registerOperation(new Op_bag_including(), opmap);
        OpGeneric.registerOperation(new Op_bag_excluding(), opmap);
        // the following three are special expressions:
        // select
        // reject
        // collect
        // count: inherited from Collection
    }
}

// --------------------------------------------------------
//
// Bag operations.
//
// --------------------------------------------------------

/* union : Bag(T1) x Bag(T2) -> Bag(T1), with T2 <= T1 */
final class Op_bag_union extends OpGeneric {

    public Op_bag_union() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfBag, Type::isTypeOfBag), List.of("Bag(T1)", "Bag(T2)"), List.of("self", "other")));
    }

    public Type matches(Type params[]) {
        if (match(params)) {

            BagType bag1 = (BagType) params[0];
            BagType bag2 = (BagType) params[1];

            Type commonElementType = bag1.elemType().getLeastCommonSupertype(
                    bag2.elemType());

            if (commonElementType != null) {
                return TypeFactory.mkBag(commonElementType);
            }
        }
        return null;
    }


    public String name() {
        return "union";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        BagValue bag1 = (BagValue) args[0];
        BagValue bag2 = (BagValue) args[1];
        return bag1.union(resultType, bag2);
    }
}

// --------------------------------------------------------

/* union : Bag(T1) x Set(T2) -> Bag(T1), with T2 <= T1 */
final class Op_bag_union_set extends OpGeneric {

    public Op_bag_union_set() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfBag, Type::isTypeOfSet), List.of("Bag(T1)", "Set(T2)"), List.of("self", "other")));
    }

    public Type matches(Type params[]) {
        if (match(params)) {
            BagType bag = (BagType) params[0];
            SetType set = (SetType) params[1];

            Type commonElementType = bag.elemType().getLeastCommonSupertype(
                    set.elemType());

            if (commonElementType != null) {
                return TypeFactory.mkBag(commonElementType);
            }
        }
        return null;
    }


    public String name() {
        return "union";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        BagValue bag = (BagValue) args[0];
        SetValue set = (SetValue) args[1];
        return bag.union(resultType, set.asBag());
    }
}

// --------------------------------------------------------

/* intersection : Bag(T1) x Bag(T2) -> Bag(T1), with T2 <= T1 */
final class Op_bag_intersection extends OpGeneric {

    public Op_bag_intersection() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfBag, Type::isTypeOfBag), List.of("Bag(T1)", "Bag(T2)"), List.of("self", "other")));
    }

    public Type matches(Type params[]) {
        if (match(params)) {
            BagType bag1 = (BagType) params[0];

            if (params[1].isTypeOfVoidType()) {
                return bag1;
            }

            BagType bag2 = (BagType) params[1];

            Type commonElementType = bag1.elemType().getLeastCommonSupertype(
                    bag2.elemType());

            if (commonElementType != null) {
                return TypeFactory.mkBag(commonElementType);
            }

        }

        return null;
    }


    public String name() {
        return "intersection";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        BagValue bag1 = (BagValue) args[0];
        //FIXME: Handle null-value
        BagValue bag2 = (BagValue) args[1];
        return bag1.intersection(resultType, bag2);
    }

    @Override
    public String checkWarningUnrelatedTypes(Expression args[]) {
        BagType bag1 = (BagType) args[0].type();
        BagType bag2 = (BagType) args[1].type();

        Type elemType1 = bag1.elemType();
        Type elemType2 = bag2.elemType();

        Type commonElementType = elemType1.getLeastCommonSupertype(elemType2);

        if (!(elemType1.isTypeOfOclAny() || elemType2.isTypeOfOclAny()) && commonElementType.isTypeOfOclAny()) {
            return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) +
                    " can never evaluate to more than an empty bag, " + StringUtil.NEWLINE +
                    "because the element types " + StringUtil.inQuotes(elemType1) +
                    " and " + StringUtil.inQuotes(elemType2) + " are unrelated.";
        }

        return null;
    }
}

// --------------------------------------------------------

/* intersection : Bag(T1) x Set(T2) -> Set(T1), with T2 <= T1 */
final class Op_bag_intersection_set extends OpGeneric {

    public Op_bag_intersection_set() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfBag, Type::isTypeOfSet), List.of("Bag(T1)", "Set(T2)"), List.of("self", "other")));
    }

    public Type matches(Type params[]) {
        if (match(params)) {

            BagType bag = (BagType) params[0];
            SetType set = (SetType) params[1];

            Type commonElementType = bag.elemType().getLeastCommonSupertype(
                    set.elemType());

            if (commonElementType != null) {
                return TypeFactory.mkSet(commonElementType);
            }
        }
        return null;
    }

    public String name() {
        return "intersection";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        BagValue bag = (BagValue) args[0];
        SetValue set = (SetValue) args[1];
        return bag.asSet().intersection(resultType, set);
    }

    @Override
    public String checkWarningUnrelatedTypes(Expression args[]) {
        BagType bag = (BagType) args[0].type();
        SetType set = (SetType) args[1].type();

        Type elemType1 = bag.elemType();
        Type elemType2 = set.elemType();

        Type commonElementType = elemType1.getLeastCommonSupertype(elemType2);

        if (!(elemType1.isTypeOfOclAny() || elemType2.isTypeOfOclAny()) && commonElementType.isTypeOfOclAny()) {
            return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) +
                    " can never evaluate to more then an empty set, " + StringUtil.NEWLINE +
                    "because the element types " + StringUtil.inQuotes(elemType1) +
                    " and " + StringUtil.inQuotes(elemType2) + " are unrelated.";
        }

        return null;
    }
}

// --------------------------------------------------------

/* including : Bag(T1) x T2 -> Bag(T1), with T2 <= T1 */
final class Op_bag_including extends OpGeneric {

    public Op_bag_including() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfBag, param -> true), List.of("Bag(T1)", "T2"), List.of("self", "element")));
    }

    public Type matches(Type params[]) {
        if (match(params)) {
            BagType bag = (BagType) params[0];
            Type commonElementType = bag.elemType().getLeastCommonSupertype(
                    params[1]);

            if (commonElementType != null) {
                return TypeFactory.mkBag(commonElementType);
            }
        }
        return null;
    }

    public String name() {
        return "including";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined())
            return UndefinedValue.instance;
        BagValue bag = (BagValue) args[0];
        return bag.including(resultType, args[1]);
    }
}

// --------------------------------------------------------

/* excluding : Bag(T1) x T2 -> Bag(T1), with T2 <= T1 */
final class Op_bag_excluding extends OpGeneric {

    public Op_bag_excluding() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfBag, param -> true), List.of("Bag(T1)", "T2"), List.of("self", "element")));
    }

    public Type matches(Type params[]) {
        if (match(params)) {
            BagType bag = (BagType) params[0];
            Type commonElementType = bag.elemType().getLeastCommonSupertype(
                    params[1]);

            if (commonElementType != null) {
                return TypeFactory.mkBag(commonElementType);
            }
        }
        return null;
    }

    public String name() {
        return "excluding";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined())
            return UndefinedValue.instance;
        BagValue bag = (BagValue) args[0];
        return bag.excluding(resultType, args[1]);
    }

    @Override
    public String checkWarningUnrelatedTypes(Expression args[]) {
        BagType bag = (BagType) args[0].type();
        Type commonElementType = bag.elemType().getLeastCommonSupertype(args[1].type());

        if (!(bag.elemType().isTypeOfOclAny() || args[1].type().isTypeOfOclAny()) && commonElementType.isTypeOfOclAny()) {
            return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) +
                    " will always evaluate to the same bag, " + StringUtil.NEWLINE +
                    "because the element type " + StringUtil.inQuotes(bag.elemType()) +
                    " and the parameter type " + StringUtil.inQuotes(args[1].type()) + " are unrelated.";
        }

        return null;
    }
}