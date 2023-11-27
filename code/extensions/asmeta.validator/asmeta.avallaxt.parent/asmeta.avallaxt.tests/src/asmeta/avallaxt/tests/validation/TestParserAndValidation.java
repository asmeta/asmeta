package asmeta.avallaxt.tests.validation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import asmeta.avallaxt.AvallaStandaloneSetup;

public class TestParserAndValidation {

	String PossibleFaults_NONE = "NO ERROR";

	public TestParserAndValidation() {
		super();
	}

	// check a scenario and return the error - if any
	// with severity and the type of error
	protected String checkPossibleFaults(String path) {
		assertTrue(Files.exists(Paths.get(path)));
		System.out.println("checking " + path);
		try {
			Injector injector = new AvallaStandaloneSetup().createInjectorAndDoEMFRegistration();
			ResourceSet rs = injector.getInstance(ResourceSet.class);
			Resource resource = rs.getResource(URI.createURI(path), true);
			resource.load(null);
			// parse the resource
			IParser parser = injector.getInstance(IParser.class);
			IParseResult pResult = parser.parse(new FileReader(path));
			if (!pResult.hasSyntaxErrors())
				System.out.println("parser ok");
			else {
				System.out.print("parser errors: ");
				// print errors		
				for (INode issue : pResult.getSyntaxErrors()) {
					System.out.println(issue.getSyntaxErrorMessage());
				}	
				fail();
			}
			// now apply the validation rules
			IResourceValidator validator = injector.getInstance(IResourceValidator.class);
			List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
			if (issues.isEmpty()) {
				System.out.println(" validation ok");
				return PossibleFaults_NONE;
			} else {
				// use set of strings to avoid suplicates
				String error_msg = "";
				Set<String> errors = new HashSet<>();
				for (Issue issue : issues) {
					String issueMsg = issue.getSeverity() + " " + issue.getMessage();
					if (errors.contains(issueMsg)) continue;
					error_msg += issueMsg;
					errors.add(issueMsg);
				}
				System.out.print(" validation errors: " + error_msg);
				return error_msg;
			}
		} catch (Exception e) {
			System.err.println(" problems " + e.getMessage());
			fail();
		}
		fail();
		return null;
	
	}

}