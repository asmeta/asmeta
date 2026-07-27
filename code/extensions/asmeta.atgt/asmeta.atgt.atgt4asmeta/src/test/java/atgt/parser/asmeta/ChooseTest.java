package atgt.parser.asmeta;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;

import atgt.parser.ParseSpecsAsmm;
import atgt.specification.ASMSpecification;
import atgt.specification.statement.RuleDeclaration;
import atgt.specification.statement.StatementToStringVisitor;
import atgt.translator.StatementToSPINVisitor;

public class ChooseTest{
	
	
	/**
	 * Test read asm mcc.
	 */
	@Test
	public void testChooseRule() {
		File derivedF = ParseSpecsAsmm.getFileSpec("fuzzyCounterChoose.asm");
		ASMSpecification derS = AsmMLoaderTest.loadSpec(derivedF);
		assertNotNull(derS);
		for(RuleDeclaration r: derS.allRules()){
			System.out.println(r.accept(new StatementToStringVisitor()));
			System.out.println(" spin ");
			System.out.println(r.accept(new StatementToSPINVisitor()));			
		}
	}
	

	
}

