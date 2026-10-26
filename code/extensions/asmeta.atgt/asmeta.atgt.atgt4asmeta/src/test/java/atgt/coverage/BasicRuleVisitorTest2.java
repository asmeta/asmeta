package atgt.coverage;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.Test;

import atgt.parser.asmeta.AsmetaLLoader;
import atgt.project.AsmProject;
import atgt.specification.ASMSpecification;

class BasicRuleVisitorTest2 {

	@Test void test() throws Exception {
		AsmetaLLoader xmipar = new AsmetaLLoader();
		//ASMSpecification SP = xmipar.read(new File("atgt_examples/nestedIf.asm"));
		ASMSpecification SP = xmipar.read(new File("atgt_examples/phd_master_flat2.asm"));
		assertNotNull(SP);
		AsmCoverage ct = RootCoverage.STRUCT_COV.getTPTree(SP);
		for( AsmTestCondition tp: ct.allTPs()) {
			System.out.println(tp);
		}
	}

}
