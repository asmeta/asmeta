package org.asmeta.cbdcomposer;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.asmeta.cbdcomposer.parser.ASTSimplePipe;
import org.asmeta.cbdcomposer.parser.CompositionFormulaParser;
import org.asmeta.cbdcomposer.parser.Node;
import org.asmeta.cbdcomposer.parser.ParseException;
import org.asmeta.cbdcomposer.parser.SimpleNode;
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
