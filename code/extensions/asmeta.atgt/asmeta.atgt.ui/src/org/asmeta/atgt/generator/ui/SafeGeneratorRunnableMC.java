package org.asmeta.atgt.generator.ui;

import java.util.List;

import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.atgt.generator.SaveResults;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmTestSuite;

public class SafeGeneratorRunnableMC extends  SafeGeneratorRunnable<AsmTSGeneratorLaunchConfigurationMC>{
	
	
	public SafeGeneratorRunnableMC(AsmTSGeneratorLaunchConfigurationMC config, IWorkbenchWindow window)
			throws PartInitException {
		super(config, window);
	}
	protected AsmTestSuite generateTestSuite() throws Exception {
		List<AsmCoverageBuilder> coverageCriteria = CriteriaEnum.getCoverageCriteria(config.coverageCriteria);
		System.out.println(config.asmetaSpecPath.toString() + "  -  " + config.computeCoverage + "  -  "
				+ coverageCriteria);
		
		NuSMVtestGenerator generator = new NuSMVtestGenerator(config.asmetaSpecPath.toString(),
				config.computeCoverage);
		mc.writeMessage("generating the test with coverage criteria " + coverageCriteria);
		// generate all the possible tests
		AsmTestSuite result = generator.generateAbstractTests(coverageCriteria, Integer.MAX_VALUE, ".*");
		return result;
	}
	protected void savetoavalla(AsmTestSuite result) {
		String stringForFile = SaveResults
				.toStringForFile(config.computeCoverage, config.coverageCriteria, config.formats);
		SaveResults.saveResults(result, config.asmetaSpecPath.toString(), config.formats, stringForFile);
	}


}
