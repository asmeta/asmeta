package atgt.coverage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.atgt.generator.coverage.AsmetaAsSpec;
import org.asmeta.atgt.generator.coverage.AsmetaCoverageBuilder;
import org.asmeta.atgt.generator.AsmTestGenerator.MBTCoverage;
import org.asmeta.parser.ASMParser;
import org.junit.Test;

import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;

public class CoverageTest {

	
	@Test
	public void testGetTPTreeMVM() throws Exception {
		generateCoverageFor("examples\\mvm0.asm");
	}
	/**
	 * @param ex
	 * @throws Exception
	 */
	private void generateCoverageFor(String asmSpec) throws Exception {		
		List<AsmetaCoverageBuilder> coverageCriteria = new ArrayList<>();
		for (CriteriaEnum c : CriteriaEnum.values()) {
			// skip 3 wise and two wise monitored
//			if (c == CriteriaEnum.COMBINATORIAL_ALL)
//				continue;
			if (c == CriteriaEnum.THREEWISE_ALL)
				continue;
			coverageCriteria.add(c.criteria);
		}
		// build the tree depending on the criteria
		AsmetaAsSpec spec = AsmetaAsSpec.read(new File(asmSpec));
		AsmCoverage ct = new MBTCoverage(coverageCriteria).getTPTree(spec);
		ct.allTPs().forEach(x -> System.out.println(x.getName() + " " + x.getCondition()));
	}

	
}
