package org.asmeta.parser;

import org.asmeta.parser.util.AsmetaTermPrinter;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;


/** all the specs in test/parser and test/simulator should parse (and simulate)
 * otherwise should go to test/errors
 * 
 * @author garganti
 *
 */
class AsmParserTest_StaticAbstractContants extends AsmParserTest {

	@Test 
	void parserTests(){
		AsmCollection asm = testOneAsmFile("test/parser/StaticConstsAbstractDomain.asm");
		Term guard = ((ConditionalRule)asm.getMain().getMainrule().getRuleBody()).getGuard();
		System.out.println("guard: " + guard);
		System.out.println(AsmetaTermPrinter.getAsmetaTermPrinter(false).visit(guard));
	}
}
