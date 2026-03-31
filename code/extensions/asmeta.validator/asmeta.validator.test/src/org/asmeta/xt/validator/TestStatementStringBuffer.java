package org.asmeta.xt.validator;

import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TestStatementStringBuffer {

	@Test void changed() {
		String result = StatementToStringBuffer.putEqInsteadOf("a = (4,5)");
		assertEquals("eq(a,(4,5))", result);
		result = StatementToStringBuffer.putEqInsteadOf("a = {2,3}");
		assertEquals("eq(a,{2,3})", result);
		result = StatementToStringBuffer.putEqInsteadOf("a = [(4,5),(2,3)]");
		assertEquals("eq(a,[(4,5),(2,3)])", result);
		result = StatementToStringBuffer.putEqInsteadOf("a = []");
		assertEquals("eq(a,[])", result);
		// This would result in a validation error because it is not translated to eq
		//result = StatementToStringBuffer.putEqInsteadOf("a = (b = (4,5))");
		//assertEquals("a = (eq(b, (4,5)))",result); // fails
		// This is translated to eq but in a wrong way
		//result = StatementToStringBuffer.putEqInsteadOf("f(a = (2,1))");
		//assertEquals("f(eq(a,(2,1)))", result); // fails
	}

	@Test void notChanged() {
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
		result = StatementToStringBuffer.putEqInsteadOf("(forall $j2 in Job with st($j2) = FIN)");
		assertEquals("(forall $j2 in Job with st($j2) = FIN)", result);
		result = StatementToStringBuffer.putEqInsteadOf("forall $j2 in Job with st($j2) = FIN");
		assertEquals("forall $j2 in Job with st($j2) = FIN", result);
		result = StatementToStringBuffer.putEqInsteadOf("st(JOB1) = FIN");
		assertEquals("st(JOB1) = FIN", result);
		result = StatementToStringBuffer.putEqInsteadOf("a = (b + 2)");
		assertEquals("a = (b + 2)", result);
		result = StatementToStringBuffer.putEqInsteadOf("a = (and(b, c))");
		assertEquals("a = (and(b, c))", result);
	}

}
