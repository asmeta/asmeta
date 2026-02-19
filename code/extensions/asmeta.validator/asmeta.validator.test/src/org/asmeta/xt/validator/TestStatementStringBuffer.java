package org.asmeta.xt.validator;

import static org.junit.Assert.*;

import org.asmeta.xt.validator.StatementToStringBuffer;
import org.junit.Test;

public class TestStatementStringBuffer {

	@Test
	public void test() {
		String result = StatementToStringBuffer.putEqInsteadOf("a = b");
		assertEquals("a = b",result);
		result = StatementToStringBuffer.putEqInsteadOf("a = b+5");
		assertEquals("a = b+5",result);
		result = StatementToStringBuffer.putEqInsteadOf("c and a = b+5");
		assertEquals("c and a = b+5",result);
		result = StatementToStringBuffer.putEqInsteadOf("a = (4,5)");
		assertEquals("eq(a,(4,5))",result);
	}

}
