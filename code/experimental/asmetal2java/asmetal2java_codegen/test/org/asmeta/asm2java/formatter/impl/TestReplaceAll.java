package org.asmeta.asm2java.formatter.impl;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestReplaceAll {

	private static FormatterImpl formatter = new FormatterImpl();
	
	@Test
	public void test() {
		assertEquals("a\nb", formatter.replaceDoubleNL("a\n   \nb"));		
	}
}
