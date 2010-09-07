package org.tzi.use.parser.testsuite;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.testsuite.MAssert;
import org.tzi.use.uml.sys.testsuite.MAssertPost;

public class ASTAssertPost extends ASTAssert {
	private Token conditionName = null;
		
	public ASTAssertPost(Token start, Token end, boolean shouldBeValid) {
		super(start, end, shouldBeValid);
	}

	public void setConditionName(Token name) {
		this.conditionName = name;
	}
	
	@Override
	public MAssert gen(Context ctx) throws SemanticException {
		MOperationCall opCall = ctx.systemState().system().getLastOperationCall();
		
		if (opCall == null)
			throw new SemanticException(getPosition(), "No operation call for assert post!");
		
		MAssertPost result = new MAssertPost(getPosition(), getExpressionString(), getMessage(), shouldBeValid());
		
		if (this.conditionName != null) {
			MPrePostCondition condition = null;
			String cndName = conditionName.getText();
			
			for (MPrePostCondition post :  opCall.getOperation().postConditions()) {
				if (cndName.equals(post.name())) {
					condition = post;
					break;
				}
			}
			
			if (condition == null) {
				throw new SemanticException(conditionName,
						"Unknown post condition `" + cndName
								+ "' for operation `"
								+ opCall.getOperation().toString() + "'");
			}
			
			result.setCondition(condition);
		}
		
		return result;
	}

}
