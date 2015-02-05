package org.tzi.use.parser.testsuite;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.testsuite.MAssert;
import org.tzi.use.uml.sys.testsuite.MAssertPre;

public class ASTAssertPre extends ASTAssert {

	private ASTExpression source;
	private Token operationName;
	private List<ASTExpression> arguments = new ArrayList<ASTExpression>();
	private Token conditionName = null;
	
	public ASTAssertPre(Token start, Token end, boolean shouldBeValid,
						ASTExpression source, Token operationName) {
		super(start, end, shouldBeValid);
		this.source = source;
		this.operationName = operationName;
	}

	public void setConditionName(Token name) {
		this.conditionName = name;
	}
	
	public void addArg(ASTExpression arg) {
		this.arguments.add(arg);
	}
	
	@Override
	public MAssert gen(Context ctx) throws SemanticException {
		// source of operation call must denote object
        Expression objExp = source.gen(ctx);
        Type t = objExp.type();
        if (! t.isTypeOfClass() )
            throw new SemanticException(source.getStartToken(), 
                                        "Expected expression with object type, " + 
                                        "found type `" + t + "'.");

        MClass cls = (MClass)t;

        // find operation
        String opname = operationName.getText();
        MOperation op = cls.operation(opname, true);
        if (op == null )
            throw new SemanticException(operationName, "No operation `" + opname +
                                        "' found in class `" + cls.name() + "'.");

        VarDeclList params = op.paramList();
        if (params.size() != arguments.size() )
            throw new SemanticException(operationName, 
                                        "Number of arguments does not match declaration of operation `" + 
                                        opname + "' in class `" + cls.name() + "'. Expected " +
                                        params.size() + " argument" +
                                        ( params.size() == 1 ? "" : "s" ) + ", found " + arguments.size() + ".");

        // generate argument expressions
        Expression[] argExprs = new Expression[arguments.size()];
        int i = 0;
        
        for (ASTExpression astExpr : arguments) {
            argExprs[i] = astExpr.gen(ctx);
            if (! argExprs[i].type().conformsTo(params.varDecl(i).type()) )
                throw new SemanticException(operationName, "Type mismatch in argument " + i +
                                            ". Expected type `" + params.varDecl(i).type() + "', found `" +
                                            argExprs[i].type() + "'.");    
            i++;
        }
        
		MAssertPre result = new MAssertPre(getPosition(),
				getExpressionString(), getMessage(), shouldBeValid(), objExp,
				op, argExprs);
        
        if (this.conditionName != null) {
        	String cndName = this.conditionName.getText();
        	MPrePostCondition condition = null;
        	
        	for (MPrePostCondition cnd : op.preConditions()) {
        		if ( cndName.equals(cnd.name()) ) {
        			condition = cnd;
        			break;
        		}
        	}
        	
        	if (condition == null) {
				throw new SemanticException(this.conditionName,
						"Undefined pre condition `" + conditionName.getText()
								+ "' for operation `" + op.name() + "'.");
        	}
        	
        	result.setCondition(condition);
        }
		
        return result;
	}

}
