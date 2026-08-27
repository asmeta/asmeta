package atgt.parser.asmgofer;

import java.io.StringReader;

import tgtlib.definitions.expression.Expression;

public class AsmExpressionParser {

	/**
	 * Method parse.
	 * @param tcS String
	 * @return Expression
	 * @throws ParseException
	 */
	public static Expression parse(String tcS) throws ParseException {
		StringReader sr = new StringReader(tcS);
		Expression e = new AsmGoferParser(sr).logicExpression();
		return e;
	}

}
