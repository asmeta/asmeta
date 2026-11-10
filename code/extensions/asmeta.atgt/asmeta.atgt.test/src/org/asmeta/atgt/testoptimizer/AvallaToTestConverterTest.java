package org.asmeta.atgt.testoptimizer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.asmeta.avallaxt.avalla.Check;
import org.junit.jupiter.api.Test;
import atgt.coverage.AsmTestSequence;
import tgtlib.specification.location.Location;
import tgtlib.specification.location.PickedVariable;

public class AvallaToTestConverterTest {

	@Test
	public void testFromAvallaToTestSequence() throws IOException {
		String avallaPath = "examples/optimizer/scenario.avalla";		
		AvallaToTestConverter converter = new AvallaToTestConverter(avallaPath);
		AsmTestSequence test = converter.convert();
		List<Map<Location, String>> instructions = test.allInstructions();
		Map<Integer, List<Check>> otherChecks = converter.getOtherChecks();
		
		assertNotNull(test);
		assertTrue(instructions.size() == 6);

		int i = 0;
		for (Map<Location, String> state : instructions) {
			System.out.println("State " + i);
			for (Entry<Location, String> entry : state.entrySet()) {
				Location loc = entry.getKey();
				String value = entry.getValue();
				if (loc instanceof PickedVariable pv) {
					System.out.println("\t Variable to PICK:\t" + loc + (pv.getInRuleDecl() != null ? " in " + pv.getInRuleDecl() : "") + " = " + value);
				}
				if (loc.isMonitored()) {
					System.out.println("\t monitored to SET:\t" + loc + " = " + value);
				} else if (loc.isControlled()) {
					System.out.println("\t controlled to CHECK:\t" + loc + " = " + value);
				}
			}
			if (otherChecks.containsKey(i)) {
				for (Check check : otherChecks.get(i)) {
					System.out.println("\t non equality to CHECK:\t" + check.getExpression());
				}
			}
			i++;
		}

	}

}
