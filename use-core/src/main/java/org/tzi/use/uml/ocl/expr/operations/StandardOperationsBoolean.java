package org.tzi.use.uml.ocl.expr.operations;

import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;

import com.google.common.collect.Multimap;

public class StandardOperationsBoolean {
	public static void registerTypeOperations(Multimap<String, OpGeneric> opmap) {
		 // operations on Boolean
		OpGeneric.registerOperation(new Op_boolean_or(), opmap);
		OpGeneric.registerOperation(new Op_boolean_xor(), opmap);
		OpGeneric.registerOperation(new Op_boolean_and(), opmap);
		OpGeneric.registerOperation(new Op_boolean_not(), opmap);
		OpGeneric.registerOperation(new Op_boolean_implies(), opmap);
		OpGeneric.registerOperation(new Op_boolean_toString(), opmap);
	}

	// --------------------------------------------------------
	//
	// Boolean operations.
	//
	// --------------------------------------------------------
	
	/* or : Boolean x Boolean -> Boolean */
	public final static class Op_boolean_or extends BooleanOperation {
		public String name() {
			return "or";
		}
	
		public boolean isInfixOrPrefix() {
			return true;
		}
	
		public Type matches(Type params[]) {
			return (params.length == 2 && 
					params[0].isKindOfBoolean(VoidHandling.INCLUDE_VOID) && 
					params[1].isKindOfBoolean(VoidHandling.INCLUDE_VOID)) ? params[0] : null;
		}
	
		public Value evalWithArgs(EvalContext ctx, Expression args[]) {
			Value v1 = args[0].eval(ctx);
			Value v2 = null;
			
			if (ctx.isEnableEvalTree())
				v2 = args[1].eval(ctx);
			
			if (v1.isDefined()) {
				boolean b1 = ((BooleanValue) v1).value();
				if (b1)
					return BooleanValue.TRUE;
				else {
					if (!ctx.isEnableEvalTree())
						v2 = args[1].eval(ctx);
					
					return v2;
				}
			} else {
				if (!ctx.isEnableEvalTree())
					v2 = args[1].eval(ctx);
				
				if (v2.isDefined()) {
					boolean b2 = ((BooleanValue) v2).value();
					if (b2)
						return BooleanValue.TRUE;
				}
				return UndefinedValue.instance;
			}
		}
	}
	
	// --------------------------------------------------------
	
	/* xor : Boolean x Boolean -> Boolean */
	public final static class Op_boolean_xor extends BooleanOperation {
		public String name() {
			return "xor";
		}
	
		public boolean isInfixOrPrefix() {
			return true;
		}
	
		public Type matches(Type params[]) {
			return (params.length == 2 && 
					params[0].isKindOfBoolean(VoidHandling.INCLUDE_VOID) && 
					params[1].isKindOfBoolean(VoidHandling.INCLUDE_VOID)) ? params[0] : null;
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
	public final static class Op_boolean_and extends BooleanOperation {
		public String name() {
			return "and";
		}
	
		public boolean isInfixOrPrefix() {
			return true;
		}
	
		public Type matches(Type params[]) {
			return (params.length == 2 && 
					params[0].isKindOfBoolean(VoidHandling.INCLUDE_VOID) && 
					params[1].isKindOfBoolean(VoidHandling.INCLUDE_VOID)) ? params[0] : null;
		}
	
		public Value evalWithArgs(EvalContext ctx, Expression args[]) {
			Value v1 = args[0].eval(ctx);
			Value v2 = null;
			
			if (ctx.isEnableEvalTree())
				v2 = args[1].eval(ctx);
			
			if (v1.isDefined()) {
				boolean b1 = ((BooleanValue) v1).value();
				if (!b1)
					return BooleanValue.FALSE;
				else {
					if (!ctx.isEnableEvalTree())
						v2 = args[1].eval(ctx);
					
					return v2;
				}
			} else {
				if (!ctx.isEnableEvalTree())
					v2 = args[1].eval(ctx);
				
				if (v2.isDefined()) {
					boolean b2 = ((BooleanValue) v2).value();
					if (!b2)
						return BooleanValue.FALSE;
				}
				return UndefinedValue.instance;
			}
		}
	}
	
	// --------------------------------------------------------
	
	/* not : Boolean -> Boolean */
	public final static class Op_boolean_not extends BooleanOperation {
		public String name() {
			return "not";
		}
	
		public boolean isInfixOrPrefix() {
			return true;
		}
	
		public Type matches(Type params[]) {
			return (params.length == 1 && params[0]
					.isKindOfBoolean(VoidHandling.INCLUDE_VOID)) ? params[0]
					: null;
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
	public final static class Op_boolean_implies extends BooleanOperation {
		public String name() {
			return "implies";
		}
	
		public boolean isInfixOrPrefix() {
			return true;
		}
	
		public Type matches(Type params[]) {
			return (params.length == 2
					&& params[0].isKindOfBoolean(VoidHandling.INCLUDE_VOID) && params[1]
						.isKindOfBoolean(VoidHandling.INCLUDE_VOID)) ? params[0]
					: null;
		}
	
		public Value evalWithArgs(EvalContext ctx, Expression args[]) {
			Value v1 = args[0].eval(ctx);
			Value v2 = null;
			
			if (ctx.isEnableEvalTree())
				v2 = args[1].eval(ctx);
			
			if (v1.isDefined()) {
				boolean b1 = ((BooleanValue) v1).value();
				if (!b1)
					return BooleanValue.TRUE;
				else {
					if (!ctx.isEnableEvalTree())
						v2 = args[1].eval(ctx);
					
					return v2;
				}
			} else {
				if (!ctx.isEnableEvalTree())
					v2 = args[1].eval(ctx);
				
				if (v2.isDefined()) {
					boolean b2 = ((BooleanValue) v2).value();
					if (b2)
						return BooleanValue.TRUE;
				}
				return UndefinedValue.instance;
			}
		}
	}
	
	//--------------------------------------------------------
	
	/* toString : Boolean -> String */
	public final static class Op_boolean_toString extends OpGeneric {
		public String name() {
			return "toString";
		}
	
		public int kind() {
			return OPERATION;
		}
	
		public boolean isInfixOrPrefix() {
			return false;
		}
	
		public Type matches(Type params[]) {
			return params.length == 1
					&& params[0].isKindOfBoolean(VoidHandling.INCLUDE_VOID) ? TypeFactory
					.mkString() : null;
		}
	
		public Value eval(EvalContext ctx, Value[] args, Type resultType) {
			BooleanValue v = (BooleanValue)args[0];
			
			return new StringValue(Boolean.toString(v.value()));
		}
	}
}
