package tgtlib.definitions.expression.parser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import tgtlib.definitions.expression.Expression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.NegExpression;
import tgtlib.definitions.expression.NumericLiteral;
import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.EnumConstCreator;

// non boolean exressions
class ExpressionParserTest {

	@Test void parseNumbers() throws Exception {
		EnumConstCreator ecc = new EnumConstCreator();
		// negation expressions
		Expression e = ExpressionParser.parse("-3",ecc);
		assertInstanceOf(NegExpression.class, e);
		assertInstanceOf(NumericLiteral.class, ((NegExpression) e).getOperand());
		
	}

	@Test void parse2Booleans() throws Exception {
		EnumConstCreator ecc = new EnumConstCreator();
		// negation expressions
		Expression e = ExpressionParser.parseAsBooleanExpression("a",ecc);
		assertInstanceOf(IdExpression.class, e);
		Expression e2 = ExpressionParser.parseAsBooleanExpression("a",ecc);
		assertSame(e, e2);
	}

	@Test void parseReparseOK() throws Exception {
		EnumConstCreator ecc = new EnumConstCreator();
		// negation expressions
		Expression e = ExpressionParser.parseAsBooleanExpression("a",ecc);
		assertInstanceOf(IdExpression.class, e);
		Expression e2 = ExpressionParser.parse("a",ecc);
		assertSame(e, e2);
	}

	@Test void parseReparseWrong() throws Exception {
		EnumConstCreator ecc = new EnumConstCreator();
		Expression e = ExpressionParser.parseAsBooleanExpression("a", ecc);
		assertInstanceOf(IdExpression.class, e);
		assertThrows(RuntimeException.class, () -> {
			IdExpression e2 = ecc.createIdExpression("a", new BoundType("t", 3, 5));
		});
	}

	@Test void parseNotNot() throws Exception {
		EnumConstCreator ecc = new EnumConstCreator();
		// negation expressions
		// fix the parser !!!
		Expression e = ExpressionParser.parseAsBooleanExpression("not not UML",ecc);
		assertInstanceOf(IdExpression.class, e);
		IdExpression e2 = ecc.createIdExpression("a", new BoundType("t",3,5));
	}
	
	

}
