package org.asmeta.atgt.testoptimizer;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.asmeta.avallaxt.AvallaStandaloneSetup;
import org.asmeta.avallaxt.AvallaStandaloneSetupGenerated;
import org.asmeta.avallaxt.avalla.Scenario;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Injector;

import atgt.coverage.AsmTestSequence;
import atgt.specification.location.Location;

public class AvallaToTestConverterTest {

	@Test
	@Ignore
	public void testFromAvallaToTestSequence() throws IOException {
		String avallaPath = "examples/optimizer/scenario.avalla";
		// TODO: Fails when run as plain JUnit in Eclipse probably due to some classpath conflict:
		Injector injector = new AvallaStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet rs = injector.getInstance(XtextResourceSet.class);
		rs.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		Resource resource = rs.getResource(URI.createFileURI(avallaPath), true);
		resource.load(rs.getLoadOptions());
		Scenario s = (Scenario) resource.getContents().get(0);
		
		AvallaToTestConverter converter = new AvallaToTestConverter();
		AsmTestSequence test = converter.convert(s);
		for (Map<Location, String> t : test.allInstructions()) {
			System.out.println("--");
			for (Entry<Location, String> entry : t.entrySet()) {
				System.out.println(entry.getKey() + ", " + entry.getValue());
			}
		}

	}

}
