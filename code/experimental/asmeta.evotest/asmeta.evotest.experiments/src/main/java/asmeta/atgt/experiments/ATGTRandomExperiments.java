package asmeta.atgt.experiments;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

import org.asmeta.atgt.rndgenerator.AsmTestGeneratorBySimulation;
import org.asmeta.parser.ASMParser;
import org.asmeta.xt.validator.AsmetaV;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.testseqexport.toAvalla;

// vorrei scrivere un test che generi un test random e lo salvi in avalla, poi lo validi con asmetaV
public class ATGTRandomExperiments {
	
	
		@Test
		public void testRandom() throws Exception {
			boolean shuffle = true;
			String asmPath ="C:\\Users\\garganti\\codefromrepos\\asmeta\\asmeta\\code\\extensions\\asmeta.atgt\\asmeta.atgt.test\\examples\\simplechoose.asm";
			// get the ASMETA spec
			AsmCollection asm = ASMParser.setUpReadAsm(new File(asmPath));
			// generate the test
			AsmTestGeneratorBySimulation randomTestGenerator = new AsmTestGeneratorBySimulation(asm, 40, 1);
			AsmTestSuite tests = randomTestGenerator.getTestSuite(shuffle);
			//
			for (AsmTestSequence test: tests) {
				// save to avalla
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				toAvalla ta = new toAvalla(output, test, "filename", "scenarioname");
				ta.saveToStream();
				// check if it is valid
				//AsmetaV.execValidation(path.toString(), AsmetaV.computeCoverage, csvPath, shuffle);

				
			}
			
		}
	
	

}
