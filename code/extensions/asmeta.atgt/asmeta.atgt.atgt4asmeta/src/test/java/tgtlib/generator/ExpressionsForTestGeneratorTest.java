package tgtlib.generator;

import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.UnaryExpression;

public class ExpressionsForTestGeneratorTest {

	protected static IdExpressionCreator iecc = new IdExpressionCreator();
	protected static IdExpression a = iecc.createIdExpression("a", null);
	protected static IdExpression b = iecc.createIdExpression("b", null);
	protected static IdExpression c = iecc.createIdExpression("c", null);
	protected static Expression nota = UnaryExpression.mkUnExpr(Operator.NOT, a);
	protected static Expression notb = UnaryExpression.mkUnExpr(Operator.NOT, b);
	protected static Expression notc = UnaryExpression.mkUnExpr(Operator.NOT, c);
	// this is a contradictinon, easy to detect
	protected static Expression aandnota = BinaryExpression.mkBinExpr(a, Operator.AND,nota);
	protected static Expression aorb = BinaryExpression.mkBinExpr(a, Operator.OR,b);
	protected static Expression aandb = BinaryExpression.mkBinExpr(a, Operator.AND,b);
	protected static Expression axorb = BinaryExpression.mkBinExpr(a, Operator.XOR,b);

	
}
