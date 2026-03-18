package org.asmeta.atgt.generator.ui;

import java.util.List;

import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.atgt.generator.SaveResults;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmTestSuite;

public class SafeGeneratorRunnableMC extends  SafeGeneratorRunnable{


	public SafeGeneratorRunnableMC(ATGTLaunchConfigurationDelegate config, IWorkbenchWindow window)
			throws PartInitException {
		super("Generation of the test suite with model checker", config, window);
	}
	@Override
	protected AsmTestSuite generateTestSuite() throws Exception {
		List<AsmCoverageBuilder> coverageCriteria = CriteriaEnum.getCoverageCriteria(config.coverageCriteria);
		ATGTActivator.log.debug(config.asmetaSpecPath.toString() + "  -  " + config.computeCoverage + "  -  "
				+ coverageCriteria);

		NuSMVtestGenerator generator = new NuSMVtestGenerator(config.asmetaSpecPath.toString(),
				config.computeCoverage);
		// get the names of the criteria
		String message = "";
		for (AsmCoverageBuilder aconv: coverageCriteria) {
			if (!message.isEmpty()) {
				message += ", ";
			}
			if (aconv instanceof atgt.coverage.RuleBasedCoverageBuilder) {
				message+= ((atgt.coverage.RuleBasedCoverageBuilder)aconv).getName();
			} else {
				message+= aconv.getCoveragePrefix();
			}
		}

		mc.writeMessage("generating the test with coverage criteria: " + message);
		// generate all the possible tests
		AsmTestSuite result = generator.generateAbstractTests(coverageCriteria, Integer.MAX_VALUE, ".*");
		return result;
	}
	@Override
	protected void savetoavalla(AsmTestSuite result) {
		String stringForFile = SaveResults
				.toStringForFile(config.computeCoverage, config.coverageCriteria, config.formats);
		SaveResults.saveResults(result, config.asmetaSpecPath.toString(), config.formats, stringForFile);
	}


}
