package atgt.coverage;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import atgt.parser.asmeta.AsmetaLLoader;
import atgt.project.AsmProject;
import atgt.specification.ASMSpecification;
import tgtlib.specification.ParseException;

public class BasicRuleVisitorTest2 {

	@Test
	public void test() throws ParseException {
		AsmetaLLoader xmipar = new AsmetaLLoader();
		//ASMSpecification SP = xmipar.read(new File("atgt_examples/nestedIf.asm"));
		ASMSpecification SP = xmipar.read(new File("atgt_examples/phd_master_flat2.asm"));
		Assert.assertNotNull(SP);
		AsmCoverage ct = RootCoverage.STRUCT_COV.getTPTree(SP);
		for( AsmTestCondition tp: ct.allTPs()) {
			System.out.println(tp);
		}
	}

}
