package tgtlib.definitions.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class NumericLiteralTest {

	@Test void numericLiteral() {
		NumericLiteral nl = new NumericLiteral(Integer.valueOf(3));
		assertEquals("3", nl.getIdString());
	}
	
}
