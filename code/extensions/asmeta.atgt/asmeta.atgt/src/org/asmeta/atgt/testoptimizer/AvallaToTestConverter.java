package org.asmeta.atgt.testoptimizer;

import org.asmeta.avallaxt.avalla.AvallaFactory;
import org.asmeta.avallaxt.avalla.Element;
import org.asmeta.avallaxt.avalla.Scenario;
import org.asmeta.avallaxt.avalla.Set;
import org.asmeta.avallaxt.avalla.Step;

import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;

public class AvallaToTestConverter {

	AsmTestSequence convert(Scenario s) {
		// no test predicate - use the name of the scenario
		AsmTestSequence test = new AsmTestSequence(new AsmTestCondition(s.getName(), null));
		test.addState();
		for (Element e : s.getElements()) {
			if (e instanceof Set set) {
				test.addAssignment(set.getLocation(), set.getValue());
			} else if (e instanceof Step) {
				test.addState();
			} else {
				throw new RuntimeException("Element " + e.getClass());
			}

		}
		return test;
	}

}
