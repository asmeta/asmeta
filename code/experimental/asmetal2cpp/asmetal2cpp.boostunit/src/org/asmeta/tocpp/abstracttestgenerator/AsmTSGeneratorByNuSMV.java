package org.asmeta.tocpp.abstracttestgenerator;

import java.io.File;

import org.asmeta.atgt.generator2.AsmTestGenerator;

import asm_mbtJunit.JUnitTestGenerator;
import asm_mbtJunit.nusmv.JunitNuSMVtestGenerator;
import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSuite;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;
import tgtlib.specification.ParseException;

public class AsmTSGeneratorByNuSMV extends AsmTestGenerator {

	private String asmfile;

	public AsmTSGeneratorByNuSMV(String asmfile) {
		this.asmfile = asmfile;
		assert new File(asmfile).exists();
	}

	@Override
	public AsmTestSuite getTestSuite() {
		ASMSpecification asmSpec;
		try {
			asmSpec = new AsmetaLLoader().read(new File(asmfile));
			assert asmfile != null;
			AsmCoverage ct = new JUnitTestGenerator.MBTCoverage().getTPTree(asmSpec);
			// queue all the tps
			for (AsmTestCondition tp : ct.allTPs()) {
				tp.setToVerify(true);
				// System.out.println(termTran.getCondition().toString());
			}
			boolean coverageTp = false;
			AsmTestSuite result = JunitNuSMVtestGenerator.generateTests(asmfile, ct, coverageTp, asmSpec);
			return result;
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AsmTestSuite.getEmptyTestSuite();
	}

}
