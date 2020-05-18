package org.asmeta.avallaxt.tests.validation;

import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.asmeta.avallaxt.AvallaStandaloneSetup;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Injector;

public class TestParserAndValidation {

	protected enum PossibleFaults {
			NONE, NOTEXISTS, OTHERS
		}

	public TestParserAndValidation() {
		super();
	}

	protected boolean test(String path, PossibleFaults f) {
		assertTrue(Files.exists(Paths.get(path)));
		System.out.print("checking " + path);
		try {
			Injector injector = new AvallaStandaloneSetup().createInjectorAndDoEMFRegistration();
			ResourceSet rs = injector.getInstance(ResourceSet.class);
			Resource resource = rs.getResource(URI.createURI(path), true);
			resource.load(null);
			// parse the resource
			IParser parser = injector.getInstance(IParser.class);
			IParseResult pResult = parser.parse(new FileReader(path));
			if (!pResult.hasSyntaxErrors())
				System.out.println(" parser ok");
			else {
				System.out.println(" parser errors ");
				// print errors		
				for (INode issue : pResult.getSyntaxErrors()) {
					System.out.println(issue.getSyntaxErrorMessage());
				}
	
				return false;
			}
			// now apply the validation rules
			IResourceValidator validator = injector.getInstance(IResourceValidator.class);
			List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
			if (issues.isEmpty()) {
				System.out.print(" validation ok");
				return true;
			} else {
				System.out.print(" validation errors");
				for (Issue issue : issues) {
					System.out.println(issue.getSeverity() + " " + issue.getMessage());
				}
				// assert(f != PossibleFaults.NOTEXISTS || issues.contains(arg0));
				return false;
			}
		} catch (Exception e) {
			System.err.println(" problems " + e.getMessage());
			return false;
		}
	
	}

}