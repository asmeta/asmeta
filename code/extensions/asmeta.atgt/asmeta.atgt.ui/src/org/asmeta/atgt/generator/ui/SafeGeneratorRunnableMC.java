package org.asmeta.atgt.generator.ui;

import java.util.List;

import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.atgt.generator.SaveResults;
import org.eclipse.ui.PartInitException;

import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmTestSuite;

public class SafeGeneratorRunnableMC extends  SafeGeneratorRunnable{
	
	
	public SafeGeneratorRunnableMC(ATGTLaunchConfigurationDelegate config)
			throws PartInitException {
		super("Generation of the test suite with model checker", config);
	}
	protected AsmTestSuite generateTestSuite() throws Exception {
		List<AsmCoverageBuilder> coverageCriteria = CriteriaEnum.getCoverageCriteria(config.coverageCriteria);
		System.out.println(config.asmetaSpecPath.toString() + "  -  " + config.computeCoverage + "  -  "
				+ coverageCriteria);
		
		NuSMVtestGenerator generator = new NuSMVtestGenerator(config.asmetaSpecPath.toString(),
				config.computeCoverage);
		// TODO use names of the  
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
		mc.writeMessage("generating the test with coverage criteria " + message);
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
