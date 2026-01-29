package org.asmeta.atgt.testoptimizer;

import java.io.File;
import java.util.Collections;
import java.io.FileNotFoundException;

import org.asmeta.atgt.generator.FormatsEnum;
import org.asmeta.atgt.generator.SaveResults;
import org.asmeta.avallaxt.AvallaStandaloneSetup;
import org.asmeta.avallaxt.avalla.Scenario;
import org.asmeta.parser.ASMParser;
import org.asmeta.xt.validator.AsmetaV;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Injector;

public class AvallaOptimizer {

	/**
	 * Optimize an avalla removing unecessary set and check statements. The new
	 * optimized avalla is placed in the same directory of the original one.
	 */
	// FIXME: not working, the test in AvallaOptimizerTest fails
	public void optimize(String avallaPath) throws Exception {
		// check avallaPath exists and is .avalla
		File avalla = new File(avallaPath);
		if (!avalla.exists()) {
			throw new FileNotFoundException(avallaPath +  " does not exist");
		}
		if (!avalla.getName().endsWith(AsmetaV.SCENARIO_EXTENSION)) {
			throw new RuntimeException(avallaPath + " is not a valid .avalla file");
		}
		// Obtain the Scenario
		// TODO: Fails when run via the JUnit test in Eclipse probably due to some classpath conflict:
		Injector injector = new AvallaStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet rs = injector.getInstance(XtextResourceSet.class);
		rs.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		Resource resource = rs.getResource(URI.createFileURI(avallaPath), true);
		resource.load(rs.getLoadOptions());
		Scenario s = (Scenario) resource.getContents().get(0);
		// TODO: Get in some way the path to the asm (from the load statement) and parse it
		String loadStatement = s.getSpec(); // TODO: check if this works
		AsmCollection asm = ASMParser.setUpReadAsm(new File(loadStatement));
		// Convert the Scenario to AsmTestSequence
		AvallaToTestConverter converter = new AvallaToTestConverter();
		AsmTestSequence test = converter.convert(s);
		AsmTestSuite ts = AsmTestSuite.getEmptyTestSuite();
		ts.addTest(test);
		// Remove redundant checks
		TestOptimizer optimizer = UnchangedRemover.allRemover;
		optimizer.optimize(test);
		// Remove redundant sets
		optimizer = new UnecessaryChangesRemover(asm);
		optimizer.optimize(test);
		// Write a new Avalla with the optimized test sequence
		String newAvallaPath = avallaPath.replace(AsmetaV.SCENARIO_EXTENSION, "_optimized" + AsmetaV.SCENARIO_EXTENSION);
		SaveResults.saveResults(ts, newAvallaPath, Collections.singleton(FormatsEnum.AVALLA), "", false); // TODO: check if last parameter is false or true
	}
}
