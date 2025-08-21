package org.tzi.use.uml.ocl.expr.operations;

import com.google.common.collect.Multimap;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ParamInfo;
import org.tzi.use.uml.ocl.type.*;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.SequenceValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StandardOperationsSequence {
	public static void registerTypeOperations(Multimap<String, OpGeneric> opMap) {
		// operations on Sequence
        // count: inherited from Collection
		OpGeneric.registerOperation(new Op_sequence_union(), opMap);
		OpGeneric.registerOperation(new Op_sequence_append(), opMap);
		OpGeneric.registerOperation(new Op_sequence_prepend(), opMap);
		OpGeneric.registerOperation(new Op_sequence_insertAt(), opMap);
		OpGeneric.registerOperation(new Op_sequence_subSequence(), opMap);
		OpGeneric.registerOperation(new Op_sequence_at(), opMap);
		OpGeneric.registerOperation(new Op_sequence_indexOf(), opMap);
		OpGeneric.registerOperation(new Op_sequence_first(), opMap);
		OpGeneric.registerOperation(new Op_sequence_last(), opMap);
		OpGeneric.registerOperation(new Op_sequence_including(), opMap);
		OpGeneric.registerOperation(new Op_sequence_excluding(), opMap);
		OpGeneric.registerOperation(new Op_sequence_reverse(), opMap);
		OpGeneric.registerOperation(new Op_sequence_shuffle(), opMap);
	}
}

// --------------------------------------------------------
//
// Sequence operations.
//
// --------------------------------------------------------

/* union : Sequence(T1) x Sequence(T2) -> Sequence(T1), with T2 <= T1 */
final class Op_sequence_union extends OpGeneric {

    public Op_sequence_union() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfSequence, Type::isTypeOfSequence), List.of("Sequence(T1)", "Sequence(T2)"), List.of("self", "other")));
    }

    public Type matches(Type[] params) {
        if (match(params)) {
            SequenceType sequence1 = (SequenceType) params[0];
            SequenceType sequence2 = (SequenceType) params[1];

            Type commonElementType = sequence1.elemType()
                    .getLeastCommonSupertype(sequence2.elemType());

            if (commonElementType != null) {
                return TypeFactory.mkSequence(commonElementType);
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
        SequenceValue sequence1 = (SequenceValue) args[0];
        SequenceValue sequence2 = (SequenceValue) args[1];
        return sequence1.union(resultType, sequence2);
    }
}

// --------------------------------------------------------

/* append : Sequence(T) x T -> Sequence(T) */
final class Op_sequence_append extends OpGeneric {

    public Op_sequence_append() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfSequence, param -> true), List.of("Sequence(T1)", "T2"), List.of("self", "element")));
    }

    public Type matches(Type[] params) {
        if (match(params)) {
            SequenceType seqType = (SequenceType) params[0];
            Type commonElementType = seqType.elemType()
                    .getLeastCommonSupertype(params[1]);

            if (commonElementType != null) {
                return TypeFactory.mkSequence(commonElementType);
            }
        }
        return null;
    }


    public String name() {
        return "append";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined()) {
            return UndefinedValue.instance;
        }
        SequenceValue seq = (SequenceValue) args[0];
        return seq.append(resultType, args[1]);
    }
}

// --------------------------------------------------------

/* prepend : Sequence(T) x T -> Sequence(T) */
final class Op_sequence_prepend extends OpGeneric {

    public Op_sequence_prepend() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfSequence, param -> true), List.of("Sequence(T1)", "T2"), List.of("self", "element")));
    }

    public Type matches(Type[] params) {
        if (match(params)) {
            SequenceType seqType = (SequenceType) params[0];
            Type commonElementType = seqType.elemType()
                    .getLeastCommonSupertype(params[1]);

            if (commonElementType != null) {
                return TypeFactory.mkSequence(commonElementType);
            }
        }
        return null;
    }


    public String name() {
        return "prepend";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined()) {
            return UndefinedValue.instance;
        }
        SequenceValue seq = (SequenceValue) args[0];
        return seq.prepend(resultType, args[1]);
    }
}

// --------------------------------------------------------

/* insertAt : Sequence(T) x Integer x T -> Sequence(T) */
final class Op_sequence_insertAt extends OpGeneric {

    public Op_sequence_insertAt() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfSequence, Type::isTypeOfInteger, param -> true), List.of("Sequence(T1)", "Integer", "T2"), List.of("self", "index", "element")));
    }

    public Type matches(Type[] params) {
        if (match(params)) {
            SequenceType seqType = (SequenceType) params[0];
            Type commonElementType = seqType.elemType()
                    .getLeastCommonSupertype(params[2]);

            if (commonElementType != null) {
                return TypeFactory.mkSequence(commonElementType);
            }
        }
        return null;
    }


    public String name() {
        return "insertAt";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SequenceValue seq = (SequenceValue) args[0];
        SequenceValue res = seq.insertAt(resultType, (IntegerValue) args[1], args[2]);

        if (res == null)
            return UndefinedValue.instance;
        else
            return res;
    }
}

// --------------------------------------------------------

/* subSequence : Sequence(T) x Integer x Integer -> Sequence(T) */
final class Op_sequence_subSequence extends OpGeneric {

    public Op_sequence_subSequence() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfSequence, Type::isTypeOfInteger, Type::isTypeOfInteger), List.of("Sequence(T1)", "Integer", "Integer"), List.of("self", "start", "end")));
    }

    public Type matches(Type[] params) {
        return (match(params)) ? params[0] : null;
    }

    public String name() {
        return "subSequence";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SequenceValue seq = (SequenceValue) args[0];
        int lower = ((IntegerValue) args[1]).value();
        int upper = ((IntegerValue) args[2]).value();

        if (lower > upper)
            return UndefinedValue.instance;

        Value res;
        try {
            res = seq.subSequence(resultType, lower - 1, upper);
        } catch (IndexOutOfBoundsException e) {
            res = UndefinedValue.instance;
        }
        return res;
    }
}

// --------------------------------------------------------

/* at : Sequence(T) x Integer -> T */
final class Op_sequence_at extends OpGeneric {

    public Op_sequence_at() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfSequence, Type::isTypeOfInteger), List.of("Sequence(T1)", "Integer"), List.of("self", "Index")));
    }

    public Type matches(Type[] params) {
        if (match(params)) {
            SequenceType seq = (SequenceType) params[0];
            return seq.elemType();
        }
        return null;
    }

    public String name() {
        return "at";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SequenceValue seq = (SequenceValue) args[0];
        IntegerValue n = (IntegerValue) args[1];

        Value res;
        try {
            res = seq.get(n.value() - 1);
        } catch (IndexOutOfBoundsException e) {
            res = UndefinedValue.instance;
        }
        return res;
    }
}

// --------------------------------------------------------

/* first : Sequence(T) -> T */
final class Op_sequence_first extends OpGeneric {

    public Op_sequence_first() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfSequence), List.of("Sequence(T1)"), List.of("self")));
    }

    public Type matches(Type[] params) {
        if (match(params)) {
            SequenceType seq = (SequenceType) params[0];
            return seq.elemType();
        }
        return null;
    }


    public String name() {
        return "first";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SequenceValue seq = (SequenceValue) args[0];
        if (seq.isEmpty())
            return UndefinedValue.instance;
        return seq.get(0);
    }
}

// --------------------------------------------------------

/* last : Sequence(T) -> T */
final class Op_sequence_last extends OpGeneric {

    public Op_sequence_last() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfSequence), List.of("Sequence(T1)"), List.of("self")));
    }

    public Type matches(Type[] params) {
        if (match(params)) {
            SequenceType seq = (SequenceType) params[0];
            return seq.elemType();
        }
        return null;
    }


    public String name() {
        return "last";
    }

    public int kind() {
        return OPERATION;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        SequenceValue seq = (SequenceValue) args[0];
        if (seq.isEmpty())
            return UndefinedValue.instance;
        return seq.get(seq.size() - 1);
    }
}

// --------------------------------------------------------

/* including : Sequence(T) x T -> Sequence(T) */
final class Op_sequence_including extends OpGeneric {

    public Op_sequence_including() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfSequence, param -> true), List.of("Sequence(T1)", "T2"), List.of("self", "element")));
    }

    public Type matches(Type[] params) {
        if (match(params)) {
            SequenceType seqType = (SequenceType) params[0];

            Type commonElementType = seqType.elemType()
                    .getLeastCommonSupertype(params[1]);

            if (commonElementType != null) {
                return TypeFactory.mkSequence(commonElementType);
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
        SequenceValue seq = (SequenceValue) args[0];
        return seq.append(resultType, args[1]);
    }
}

// --------------------------------------------------------

/* excluding : Sequence(T) x T -> Sequence(T) */
final class Op_sequence_excluding extends OpGeneric {

    public Op_sequence_excluding() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfSequence, param -> true), List.of("Sequence(T1)", "T2"), List.of("self", "element")));
    }

    public Type matches(Type[] params) {
        if (match(params)) {
            SequenceType seqType = (SequenceType) params[0];
            Type commonElementType = seqType.elemType()
                    .getLeastCommonSupertype(params[1]);

            if (commonElementType != null) {
                return TypeFactory.mkSequence(commonElementType);
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
        SequenceValue seq = (SequenceValue) args[0];
        return seq.excluding(resultType, args[1]);
    }

    @Override
    public String checkWarningUnrelatedTypes(Expression[] args) {
        CollectionType col = (CollectionType) args[0].type();

        Type commonElementType = col.elemType().getLeastCommonSupertype(args[1].type());

        if (!(col.elemType().isTypeOfOclAny() || args[1].type().isTypeOfOclAny()) && commonElementType.isTypeOfOclAny()) {
            return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) +
                    " will always evaluate to the same sequence, " + StringUtil.NEWLINE +
                    "because the element type " + StringUtil.inQuotes(col.elemType()) +
                    " and the parameter type " + StringUtil.inQuotes(args[1].type()) + " are unrelated.";
        }

        return null;
    }
}

/* indexOf : Sequence(T) x T -> Integer */
final class Op_sequence_indexOf extends OpGeneric {

    public Op_sequence_indexOf() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfSequence, param -> true), List.of("Sequence(T1)", "T2"), List.of("self", "element")));
    }

    public Type matches(Type[] params) {
        if (match(params)) {
            SequenceType seqType = (SequenceType) params[0];

            Type commonElementType = seqType.elemType()
                    .getLeastCommonSupertype(params[1]);

            if (commonElementType != null) {
                return TypeFactory.mkInteger();
            }
        }
        return null;
    }


    public String name() {
        return "indexOf";
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

        SequenceValue seq = (SequenceValue) args[0];

        int index = seq.indexOf(args[1]);
        if (index == -1)
            return UndefinedValue.instance;
        else
            return IntegerValue.valueOf(index + 1);
    }

    @Override
    public String checkWarningUnrelatedTypes(Expression[] args) {
        CollectionType col = (CollectionType) args[0].type();

        Type commonElementType = col.elemType().getLeastCommonSupertype(args[1].type());

        if (!(col.elemType().isTypeOfOclAny() || args[1].type().isTypeOfOclAny()) && commonElementType.isTypeOfOclAny()) {
            return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) +
                    " will always evaluate to undefined, " + StringUtil.NEWLINE +
                    "because the element type " + StringUtil.inQuotes(col.elemType()) +
                    " and the parameter type " + StringUtil.inQuotes(args[1].type()) + " are unrelated.";
        }

        return null;
    }
}

/* reverse : Sequence(T) -> Sequence(T) */
final class Op_sequence_reverse extends OpGeneric {

    public Op_sequence_reverse() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfSequence), List.of("Sequence(T1)"), List.of("self")));
    }

    public Type matches(Type[] params) {
        if (match(params)) {
            return params[0];
        }
        return null;
    }


    public String name() {
        return "reverse";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined()) return UndefinedValue.instance;

        SequenceValue col = (SequenceValue) args[0];
        ArrayList<Value> elements = new ArrayList<>(col.collection());
        Collections.reverse(elements);

        return new SequenceValue(col.elemType(), elements);
    }
}

/* Not OCL: shuffle : Sequence(T) -> Sequence(T) */
final class Op_sequence_shuffle extends OpGeneric {

    public Op_sequence_shuffle() {
        setParamInfo(new ParamInfo(List.of(Type::isTypeOfSequence), List.of("Sequence(T1)"), List.of("self")));
    }

    public Type matches(Type[] params) {
        if (match(params)) {
            return params[0];
        }
        return null;
    }

    public String name() {
        return "shuffle";
    }

    public int kind() {
        return SPECIAL;
    }

    public boolean isInfixOrPrefix() {
        return false;
    }

    public Value eval(EvalContext ctx, Value[] args, Type resultType) {
        if (args[0].isUndefined()) return UndefinedValue.instance;

        SequenceValue col = (SequenceValue) args[0];
        ArrayList<Value> elements = new ArrayList<>(col.collection());
        Collections.shuffle(elements);

        return new SequenceValue(col.elemType(), elements);
    }
}