package org.tzi.use.uml.ocl.extension;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jruby.embed.EvalFailedException;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.operations.OpGeneric;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.Log;
import org.tzi.use.util.NullWriter;
import org.tzi.use.util.rubyintegration.RubyHelper;

public class ExtensionOperation extends OpGeneric {

	public static class Parameter {
		private String name;
		private String typeName;
		private Type type;
		
		public Parameter(String name, String typeName) {
			this.name = name;
			this.typeName = typeName;
		}

		public String getName() {
			return name;
		}

		public String getTypeName() {
			return typeName;
		}

		public Type getType() {
			return type;
		}

		public void setType(Type type) {
			this.type = type;
		}
	}
	
	private boolean isInfixOrPrefix = false;
	private String name;
	
	private String resultTypeName;
	private Type resultType;
	
	private String sourceTypeName;
	private Type sourceType;
	
	private List<Parameter> parameter = new ArrayList<Parameter>();
	
	private String operationBody;
	
	public ExtensionOperation(String sourceTypeName, String name, String resultType, String body) {
		this.sourceTypeName = sourceTypeName;
		this.name = name;
		this.resultTypeName = resultType;
		this.operationBody = body;
	}
	
	public void addParameter(String name, String typeName) {
		this.parameter.add(new Parameter(name, typeName));
	}
	
	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		ScriptEngineManager m = new ScriptEngineManager();
        ScriptEngine rubyEngine = m.getEngineByName("jruby");
        
        if (rubyEngine == null)
            throw new RuntimeException("Did not find the ruby engine. Please verify your classpath");
       
        ScriptContext context = rubyEngine.getContext();
        context.setErrorWriter(new NullWriter());

        context.setAttribute("self", RubyHelper.useValueToRubyValue(args[0]), ScriptContext.ENGINE_SCOPE);
        
        for (int i = 0; i < parameter.size(); i++) {
        	context.setAttribute(parameter.get(i).getName(), 
        				RubyHelper.useValueToRubyValue(args[i + 1]), ScriptContext.ENGINE_SCOPE);
        }

        try{
            Object result = rubyEngine.eval(operationBody, context);
            Value resultValue = RubyHelper.rubyValueToUseValue(result, resultType);
            
            // Wrong result type!
            if (!resultValue.type().conformsTo(this.resultType)) {
				Log.warn("Extension method `" + name
						+ "' returned wrong type! Expected `"
						+ this.resultType.toString() + "' got `"
						+ resultValue.type().toString() + "'");
				return UndefinedValue.instance;
            } else {
            	return resultValue;
            }
            
        } catch (ScriptException e) {
            Log.error(e.getMessage());
        } catch (EvalFailedException e) {
        	Log.error(e.getMessage());
        }
        
        return UndefinedValue.instance;
	}

	@Override
	public boolean isInfixOrPrefix() {
		return isInfixOrPrefix;
	}

	@Override
	public int kind() {
		return OpGeneric.OPERATION;
	}

	@Override
	public Type matches(Type[] params) {
		if (!params[0].conformsTo(this.sourceType)) {
			return null;
		}
		
		for (int i = 1; i < params.length; i++) {
			Type givenType = params[i];
			Type requiredType = parameter.get(i - 1).getType();
			
			if (!givenType.conformsTo(requiredType) ) {
				return null;
			}
		}
		
		return resultType;
	}

	@Override
	public String name() {
		return name;
	}
		
	public void initialize() {
		this.sourceType = ExtensionManager.getInstance().getType(this.sourceTypeName);
		if (this.sourceType == null)
			throw new RuntimeException("Unknown source type '" + this.sourceType + "'");
		
		this.resultType = ExtensionManager.getInstance().getType(this.resultTypeName);
		if (this.resultType == null)
			throw new RuntimeException("Unknown result type '" + this.resultType + "'");
		
		for (Parameter par : this.parameter) {
			par.setType(ExtensionManager.getInstance().getType(par.getTypeName()));
			
			if (par.getType() == null)
				throw new RuntimeException("Unknown parameter type '" + this.resultType + "'");
		}
	}
}
