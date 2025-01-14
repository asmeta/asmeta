package asmeta.asm2java.formatter;

import static org.junit.Assert.*;

import org.junit.Test;

import asmeta.asm2java.formatter.FormatterImpl;

public class TestReplaceAll {

	private static FormatterImpl formatter = new FormatterImpl();
	
	@Test
	public void test() {
		assertEquals("a\nb", formatter.replaceDoubleNL("a\n   \nb"));		
	}
}
