package asmeta.atgt.experiments;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.asmeta.atgt.rndgenerator.AsmTestGeneratorBySimulation;
import org.asmeta.parser.ASMParser;
import org.asmeta.xt.validator.AsmetaV;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.testseqexport.toAvalla;

// vorrei scrivere un test che generi un test random e lo salvi in avalla, poi lo validi con asmetaV
public class ATGTRandomExperiments {
	
	
		@ParameterizedTest
		@ValueSource(booleans = {true,false})
		public void testRandom(boolean shuffle) throws Exception {
			//String asmPath ="../../../extensions/asmeta.atgt/asmeta.atgt.test/examples/simplechoose.asm";
			String asmPath ="../../../extensions/asmeta.atgt/asmeta.atgt.test/examples/simplechooseBiGDomain.asm";
			// get the ASMETA spec
			AsmCollection asm = ASMParser.setUpReadAsm(new File(asmPath));
			// generate the test
			AsmTestGeneratorBySimulation randomTestGenerator = new AsmTestGeneratorBySimulation(asm, 5, 1);
			AsmTestSuite tests = randomTestGenerator.getTestSuite(shuffle);
			//
			for (AsmTestSequence test: tests) {
				// save to avalla
				// save avalla
				String scenarioName = "scenarios/scenario_simplechoose.avalla";
				toAvalla exporter = new toAvalla(new File(scenarioName), test, new File(asmPath).toString());
				exporter.saveToStream();
				// call the validator
				List<String> results = AsmetaV.execValidation(scenarioName,	AsmetaV.computeCoverage, shuffle);
				System.out.println("results: " + results);

				
			}
			
		}
	
	

}
