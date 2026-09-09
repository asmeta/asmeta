package tgtlib.definitions.normalform.cnf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tgtlib.definitions.expression.BinaryExpression.mkBinExpr;
import static tgtlib.definitions.expression.Operator.AND;
import static tgtlib.definitions.expression.Operator.NOT;
import static tgtlib.definitions.expression.Operator.OR;
import static tgtlib.definitions.expression.UnaryExpression.mkUnExpr;

import tgtlib.definitions.expression.Expression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;

class CNFExpressionToDimacsTest {

	@Test void toDimacs() {
		IdExpressionCreator iecc = new IdExpressionCreator();
		IdExpression x1 = iecc.createIdExpression("x1", null);
		IdExpression x2 = iecc.createIdExpression("x2", null);
		IdExpression x3 = iecc.createIdExpression("x3", null);
		IdExpression x4 = iecc.createIdExpression("x4", null);
		Expression c1 = mkBinExpr(x1, OR, mkBinExpr(x3, OR, mkUnExpr(NOT,x4)));
		Expression c3 = mkBinExpr(x2, OR, mkUnExpr(NOT,x3));
		Expression res = mkBinExpr(c1, AND, mkBinExpr(x4, AND, c3));
		CNFExpression cnf = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(res);
		assertEquals("[x1x3~x4, x4, x2~x3]", cnf.toString());
		Dimacs dimacs = cnf.toDimacs();
		assertEquals(3,dimacs.getnClauses());
		assertEquals(4,dimacs.getnVariables());
	}
}