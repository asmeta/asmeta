package org.asmeta.atgt.generator;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.atgt.coverage.AsmetaAsSpec;
import org.asmeta.atgt.coverage.AsmetaConditionExtractor;
import org.asmeta.flattener.RemoveArgumentsFlattener;
import org.asmeta.flattener.rule.ChooseRuleINVARFlattener;
import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.RuleEvaluator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestSuite;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;
import atgt.testseqexport.toAvalla;
import tgtlib.specification.ParseException;

// compare the 
public class NuSMVAsmTestGeneratorComparison {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Logger.getLogger(AsmTestGenerator.class).setLevel(Level.ALL);
		Logger.getLogger(AsmetaConditionExtractor.class).setLevel(Level.ALL);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSingleFile() throws Exception {
		//testSingleFile("examples/ImpiantoSemaforico.asm");
		//testSingleFile("../../../../asm_examples\\examples\\landingGearSystem\\LGS_EV.asm");
		//testSingleFile("..\\..\\..\\..\\asm_examples\\examples\\traffic_light\\oneWayTrafficLightUsingEnum.asm");
		//testSingleFile("..\\..\\..\\..\\asm_examples\\PillBox\\Level1\\pillbox_1.asm");
//		testSingleFile("../../../../asm_examples/stereoacuity/certifierRaff2.asm");
//		testSingleFile("../../../../asm_examples/examples/simple_example/population.asm");
		//testSingleFile("../../../../asm_examples/examples/sleepingBarber/sleepingBarber.asm");	
		//testSingleFile("../../../../asm_examples/examples/SemaforiAttraversamentoPedonale.asm");
		testSingleFile("../../../../asm_examples/examples/coffeeVendingMachine/coffeeVendingMachine.asm");
	}
	
	@Test
	public void testSimpleChooseFile() throws Exception {
		Logger.getLogger(ChooseRuleINVARFlattener.class).setLevel(Level.ALL);
		Logger.getLogger(RemoveArgumentsFlattener.class).setLevel(Level.ALL);
		RuleEvaluator.UndefGuardsEvaluatedAsFalse = true;
		// this has no tps
		//testSingleFile("examples/simplechoose.asm");
		//testSingleFile("examples/simplechooseBiGDomain.asm");
		//testSingleFile("examples/simplechoose_f.asm");
		//testSingleFile("examples/simplechoose_f2.asm");
		testSingleFile("examples/simplechoose_f3.asm");
	}

	
	private void testSingleFile(String spec) throws Exception {
//		NuSMVASMSpecTestGenerator gen1 = new NuSMVASMSpecTestGenerator(spec);
//		AsmTestSuite test = gen1.generateAbstractTests(1, ".*");
//		System.out.println(test.getTests());
//		System.out.println(test.getTests().get(0).getState(0));
//		// stesso con altra classe
		NuSMVAsmetaTestGenerator gen2 = new NuSMVAsmetaTestGenerator(spec, false);
		AsmTestSuite test = gen2.generateAbstractTests(1, ".*");
		System.out.println(test.getTests());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		new toAvalla(outputStream, test.getTests().get(0), "", "").save();
		String avallaContent = outputStream.toString();
		System.err.println(avallaContent);
	}

	@Test
	public void testAllExperimentsEvoavalla() throws IOException {
		//Path listFile = Path.of("model_list_atgt_valid.txt");
		extracted("model_list.txt");
	}
	@Test
	public void testChooseExperimentsEvoavalla() throws IOException {
		//Path listFile = Path.of("model_list_atgt_valid.txt");
		extracted("model_list_choose.txt");
	}
	
	
	

	private void extracted(String string) throws IOException {
		Path listFile = Path.of(string);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("analysys_generation.txt", true)));
		try (BufferedReader paths = Files.newBufferedReader(listFile)) {
			String line;
			while ((line = paths.readLine()) != null) {
				String filePath = line.trim();
				if (filePath.isEmpty() || filePath.startsWith("//")) {
					continue;
				}
				String pathname = "../../../../asm_examples/"+filePath;
				File asmSpec = new File(pathname);
				// OLD
				String resOld = "";
				String resNew = "";
				try {
					NuSMVASMSpecTestGenerator gen1 = new NuSMVASMSpecTestGenerator(asmSpec.toString());
					// generate one test
					AsmTestSuite test = gen1.generateAbstractTests(1, ".*");
					resOld = "ntests: " + test.getTests().size() + "- nsteps: " + test.getTests().get(0).getContent().allInstructions().size();				
				} catch (ParseException e) {
					resOld = "Error";
				} catch (AssertionError e) {
					resOld = "AssertionError " + e.getLocalizedMessage();
				} catch (RuntimeException e) {
					resOld = "RuntimeException " + e.getLocalizedMessage();
				} catch (Exception e) {
					resOld = "Exception " + e.getLocalizedMessage();
				}
				try {
					// NEW
					NuSMVAsmetaTestGenerator gen2 = new NuSMVAsmetaTestGenerator(asmSpec.toString(), false);
					AsmTestSuite test = gen2.generateAbstractTests(1, ".*");
					resNew = "ntests: " + test.getTests().size() + "- nsteps: " + test.getTests().get(0).getContent().allInstructions().size();				
				}  catch (AssertionError e) {
					resNew = "AssertionError" + e.getLocalizedMessage();
				}catch (Exception e) {
					resNew = "Error " + e.getLocalizedMessage();
				}
				out.println(pathname + "\t-->\n\t" + resOld + "\n\t" + resNew);
				out.flush();
			}
		}
		out.close();
	}
		
}
