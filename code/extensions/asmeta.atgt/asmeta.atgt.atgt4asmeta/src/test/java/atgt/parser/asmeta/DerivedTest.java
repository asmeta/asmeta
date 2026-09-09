package atgt.parser.asmeta;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.util.Enumeration;

import atgt.parser.ParseSpecsAsmm;

import org.junit.jupiter.api.Test;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Variable;

class DerivedTest{


	/**
	 * Test read asm mcc.
	 */
	@Test void derived() {
		File derivedF = ParseSpecsAsmm.getFileSpec("derived.asm");
		ASMSpecification derS = AsmMLoaderTest.loadSpec(derivedF);
		assertNotNull(derS);
		Enumeration<Variable> allVariables = derS.allVariables();
		System.out.println(allVariables.nextElement());
		System.out.println(allVariables.nextElement());
		System.out.println(allVariables.nextElement());
			//System.out.println(cc.getAxiom().iterator().next().toString());
		//assertEquals(5, cc.getAxiom().size());
		//assertTrue(cc.getVariable("mode").isControlled());
		//assertTrue(cc.getVariable("lever").isMonitored());
	}
	

	
}

