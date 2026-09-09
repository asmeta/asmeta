package atgt.parser.asmeta;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import atgt.parser.ParseSpecsAsmm;

import org.junit.jupiter.api.Test;
import atgt.specification.ASMSpecification;
import atgt.specification.statement.RuleDeclaration;
import atgt.specification.statement.StatementToStringVisitor;
import atgt.translator.StatementToSPINVisitor;

class ChooseTest{


	/**
	 * Test read asm mcc.
	 */
	@Test void chooseRule() {
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

