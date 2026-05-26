package asmeta.asmeta.ai.propgen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import asmeta.ai.propgen.ASMtoNLOperation;
import asmeta.ai.propgen.NLtoTLOperation;
import asmeta.ai.propgen.PropertyType;
import asmeta.ai.propgen.TLtoNLOperation;
import asmeta.ai.propgen.llm.LlmClient;

class OperationClockTestHelper {

	private static final String ASM_PATH = "./resources/AdvancedClock.asm";

	static void fromASMtoNLClock(LlmClient llm) throws Exception {
		int nProp = 3;
		List<String> properties = new ASMtoNLOperation(llm).generate(ASM_PATH, nProp, false);

		System.out.println("===== From ASM to NL properties =====");
		for (String prop : properties) {
			System.out.println(prop);
		}

		assertEquals(nProp, properties.size());
	}

	static void fromNLtoTLClock(LlmClient llm) throws Exception {
		String prop = "in every possible reachable, state when the seconds function reaches the value 59,"
				+ " it is set to 0 in the next state";
		String ctl = new NLtoTLOperation(llm).generate(ASM_PATH, prop, PropertyType.CTLPROP, false);

		System.out.println("===== From NL to CTL property =====");
		System.out.println(ctl);

		assertTrue(ctl.toLowerCase().contains("ag"));
	}

	static void fromTLtoNLClock(LlmClient llm) throws Exception {
		String prop = "ctlspec ag (eq(seconds,59) implies ax(eq(seconds,0)))";
		String explanation = new TLtoNLOperation(llm).explain(ASM_PATH, prop, false);

		System.out.println("===== From CTL to NL property =====");
		System.out.println(explanation);

		assertFalse(explanation.isBlank());
	}

}
