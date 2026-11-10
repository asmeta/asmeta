package org.asmeta.atgt.testoptimizer;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.asmeta.avallaxt.avalla.Check;
import org.asmeta.avallaxt.avalla.Element;
import org.asmeta.avallaxt.avalla.Pick;
import org.asmeta.avallaxt.avalla.Scenario;
import org.asmeta.avallaxt.avalla.Set;
import org.asmeta.avallaxt.avalla.Step;
import org.asmeta.avallaxt.AvallaStandaloneSetup;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Injector;

import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSeqContent;
import atgt.coverage.AsmTestSequence;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.type.DummyType;
import tgtlib.specification.location.AsmVariable;
import tgtlib.specification.location.Location.VarKind;
import tgtlib.specification.location.PickedVariable;

/**
 * This class converts an Avalla scenario to an AsmTestSequence. 
 * Each state of the AsmTestSequence is populated with the values of the monitored (set) and controlled (check) locations, as well as the picked variables.
 * stepuntil, exec, execblock are not supported.
 * 
 * Originally made to be used by AvallaOptimizer: we store equality check statements directly as assignments so that can be optimized.
 * Other check statements are stored in a separate map, which can be retrieved with getOtherChecks().
 */
@SuppressWarnings("deprecation")
public class AvallaToTestConverter {
	// Simple equality checks (e.g., "available(COFFEE) = 10") become controlled assignments; other checks are kept in a separate map.
	private static final Pattern EQUALITY = Pattern.compile(
			"^\\s*([A-Za-z_][A-Za-z0-9_]*)(?:\\s*\\(\\s*([A-Za-z_][A-Za-z0-9_]*(?:\\s*,\\s*[A-Za-z_][A-Za-z0-9_]*)*)\\s*\\))?\\s*=\\s*([A-Za-z_][A-Za-z0-9_]*|-?[0-9]+|\"[^\"]*\")\\s*$");
	
	private final Map<Integer, List<Check>> otherChecks = new HashMap<>();
	
	private final EnumConstCreator ids = new EnumConstCreator();
	// Type to be used for controlled assignments
	private final DummyType type = new DummyType("for controlled used in checks");
	
	private final Scenario scenario;
	private AsmTestSequence testSequence;

	public AvallaToTestConverter(String avallaPath) throws IOException {
		// Convert the Avalla scenario to a Scenario object
		Injector injector = new AvallaStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet rs = injector.getInstance(XtextResourceSet.class);
		rs.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		Resource resource = rs.getResource(URI.createFileURI(new File(avallaPath).getAbsolutePath()), true);
		resource.load(rs.getLoadOptions());
		if (!resource.getErrors().isEmpty() || resource.getContents().isEmpty()
				|| !(resource.getContents().get(0) instanceof Scenario)) {
			throw new IOException("Invalid Avalla scenario: " + avallaPath + " " + resource.getErrors());
		}
		scenario = (Scenario) resource.getContents().get(0);
	}
	
	public Scenario getScenario() {
		return scenario;
	}

	/**
	 * Get an AsmTestSequence from the Avalla file. 
	 * Checks that are not simple equality checks are preserved in the otherChecks map.
	 *
	 * @return the AsmTestSequence
	 */
	public AsmTestSequence convert() {
		// If already converted, return the test sequence
		if (testSequence != null) 
			return testSequence;
		
		Map<Integer, List<Check>> checks = new HashMap<>();
		boolean previous = AsmTestSeqContent.preserveExplicitAssignments;
		AsmTestSeqContent.preserveExplicitAssignments = true;
		try {
			AsmTestSequence test = new AsmTestSequence(new AsmTestCondition(scenario.getName(), null));
			test.addState();
			int state = 0;
			for (Element element : scenario.getElements()) {
				if (element instanceof Set set) {
					test.addAssignment(set.getLocation(), set.getValue(), VarKind.MONITORED);
				} else if (element instanceof Step) {
					test.addState();
					state++;
				} else if (element instanceof Check check) {
					Matcher equality = EQUALITY.matcher(check.getExpression());
					if (equality.matches()) {
						addCheck(test, equality);
					} else {
						if (!checks.containsKey(state)) {
						    checks.put(state, new ArrayList<>());
						}
						checks.get(state).add(check);
					}
				} else if (element instanceof Pick pick) {
					test.addAssignment(new PickedVariable(pick.getVar(), pick.getRule()), pick.getValue());
				} else {
					throw new RuntimeException("Element " + element.getClass() + " not yet supported in AvallaToTestConverter");
				}
			}
			otherChecks.putAll(checks);
			testSequence = test;
			return testSequence;
		} finally {
			AsmTestSeqContent.preserveExplicitAssignments = previous;
		}
	}

	private void addCheck(AsmTestSequence test, Matcher equality) {
		String name = equality.group(1);
		String arguments = equality.group(2);
		String value = equality.group(3);
		
		if (arguments == null) {
			AsmVariable variable = new AsmVariable(ids.createIdExpression(name, type), null);
			variable.setControlled();
			test.addAssignment(variable, value);
		} else {
			List<IdExpression> argumentIds = new ArrayList<>();
			for (String argument : arguments.split(",")) {
				argumentIds.add(ids.createIdExpression(argument.trim(), type));
			}
			FunctionTerm location = new FunctionTerm(ids.createIdExpression(name, type), type, argumentIds);
			test.addAssignment(location, value, VarKind.CONTROLLED);
		}
	}

	// Checks not expressible as assignments can be written back in their original state.
	public Map<Integer, List<Check>> getOtherChecks() {
		return otherChecks;
	}
}
