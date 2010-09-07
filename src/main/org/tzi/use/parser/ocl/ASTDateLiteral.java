package org.tzi.use.parser.ocl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.ocl.expr.ExpConstDate;
import org.tzi.use.uml.ocl.expr.Expression;

public class ASTDateLiteral extends ASTExpression {

	private Token token;
	
	public ASTDateLiteral(Token t)
	{
		this.token = t;
	}

	@Override
	public Expression gen(Context ctx) throws SemanticException {
		
		Date dateValue = parseDate(token.getText());

		if (dateValue == null)
			throw new SemanticException(token, "Date value is invalid");
		
		return new ExpConstDate(dateValue);
	}
	
	/**
	 * Basic checks for valid date
	 * @param v
	 * @return
	 */
	private Date parseDate(String v)
	{
		try {
			String[] dateParts = v.substring(1, v.length() - 1).split("-");

			if (dateParts.length < 3) return null;
		
			int year = Integer.parseInt(dateParts[0]);
			int month = Integer.parseInt(dateParts[1]);
			int day = Integer.parseInt(dateParts[2]);

			Calendar c = new GregorianCalendar(year, month - 1, day);

			return new Date(c.getTimeInMillis());
			
		} catch (Exception ex) {
			return null;
		}		
	}

	@Override
	public void getFreeVariables(HashSet<String> freeVars) {
		
	}
	
	@Override
	public String toString() {
		return token.getText();
	}
}
