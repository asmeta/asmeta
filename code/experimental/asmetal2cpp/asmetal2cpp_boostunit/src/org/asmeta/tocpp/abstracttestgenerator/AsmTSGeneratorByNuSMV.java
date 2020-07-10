package org.asmeta.tocpp.abstracttestgenerator;

import java.io.File;

import asm_mbtJunit.JUnitTestGenerator;
import asm_mbtJunit.nusmv.JunitNuSMVtestGenerator;
import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSuite;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;

public class AsmTSGeneratorByNuSMV extends AsmTestGenerator {

	private String asmfile;

	public AsmTSGeneratorByNuSMV(String asmfile) {
		this.asmfile = asmfile;
		assert new File(asmfile).exists();
	}

	@Override
	public AsmTestSuite getTestSuite() {
		ASMSpecification asmSpec = new AsmetaLLoader().read(new File(asmfile));
		assert asmfile != null;
		AsmCoverage ct = new JUnitTestGenerator.MBTCoverage().getTPTree(asmSpec);
		// queue all the tps
		for (AsmTestCondition tp : ct.allTPs()) {
			tp.setToVerify(true);
			//System.out.println(tp.getCondition().toString());
		}
		boolean coverageTp = false;
		try {
			AsmTestSuite result = JunitNuSMVtestGenerator.generateTests(asmfile, ct, coverageTp, asmSpec);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AsmTestSuite.getEmptyTestSuite();		
	}

}
