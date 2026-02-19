package org.asmeta.xt.validator;

import static org.junit.Assert.*;

import org.asmeta.xt.validator.StatementToStringBuffer;
import org.junit.Test;

public class TestStatementStringBuffer {

	@Test
	public void testChanged() {
		String result = StatementToStringBuffer.putEqInsteadOf("a = (4,5)");
		assertEquals("eq(a,(4,5))", result);
		result = StatementToStringBuffer.putEqInsteadOf("a = (b + 2)");
		assertEquals("eq(a,(b + 2))", result);
		// This would result in a validation error because it is not translated to eq
		//result = StatementToStringBuffer.putEqInsteadOf("a = (b = (4,5))");
		//assertEquals("a = (eq(b, (4,5)))",result);
		// This is translated to eq but in a wrong way
		//result = StatementToStringBuffer.putEqInsteadOf("f(a = (2,1))");
		//assertEquals("f(eq(a,(2,1)))", result);
	}
	
	@Test
	public void testNotChanged() {
		String result = StatementToStringBuffer.putEqInsteadOf("a = b");
		assertEquals("a = b", result);
		result = StatementToStringBuffer.putEqInsteadOf("a = b+5");
		assertEquals("a = b+5", result);
		result = StatementToStringBuffer.putEqInsteadOf("c and a = b+5");
		assertEquals("c and a = b+5", result);
		result = StatementToStringBuffer.putEqInsteadOf("alive(male1) and age(male1) = 20n");
		assertEquals("alive(male1) and age(male1) = 20n", result);
		result = StatementToStringBuffer.putEqInsteadOf("(c >= 4) = true");
		assertEquals("(c >= 4) = true", result);
	}

}
