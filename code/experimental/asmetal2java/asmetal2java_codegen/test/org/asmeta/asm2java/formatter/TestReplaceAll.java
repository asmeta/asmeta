package org.asmeta.asm2java.formatter;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestReplaceAll {

	@Test
	public void test() {
		assertEquals("a\nb", Formatter.replaceDoubleNL("a\n   \nb"));		
	}
}
