package atgt.parser.asmeta;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Enumeration;

import org.junit.Test;

import atgt.parser.ParseSpecsAsmm;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Variable;

public class DerivedTest{
	
	
	/**
	 * Test read asm mcc.
	 */
	@Test
	public void testDerived() {
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

