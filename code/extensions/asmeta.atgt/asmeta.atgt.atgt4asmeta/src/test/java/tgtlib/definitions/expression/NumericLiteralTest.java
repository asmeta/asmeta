package tgtlib.definitions.expression;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NumericLiteralTest {

	@Test
	public void testNumericLiteral() {
		NumericLiteral nl = new NumericLiteral(Integer.valueOf(3));
		assertEquals("3", nl.getIdString());
	}
	
}
