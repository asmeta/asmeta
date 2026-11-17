package org.asmeta.cbdcomposer.parser;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.asmeta.cbdcomposer.jjparser.ASTSimplePipe;
import org.asmeta.cbdcomposer.jjparser.CompositionFormulaParser;
import org.asmeta.cbdcomposer.jjparser.Node;
import org.asmeta.cbdcomposer.jjparser.ParseException;
import org.asmeta.cbdcomposer.jjparser.SimpleNode;
import org.junit.Test;

public class CompositionFormulaParserTest {

	@Test
	public void testStart() throws ParseException {
		StringReader sr = new StringReader("aa | bb");
		CompositionFormulaParser cf = new CompositionFormulaParser(sr);
		SimpleNode n = cf.parse();
		Node content = n.jjtGetChild(0);
		assertTrue(content instanceof ASTSimplePipe);
		ASTSimplePipe pipe = (ASTSimplePipe) content;
		assertEquals(2,pipe.jjtGetNumChildren());
	}

}
