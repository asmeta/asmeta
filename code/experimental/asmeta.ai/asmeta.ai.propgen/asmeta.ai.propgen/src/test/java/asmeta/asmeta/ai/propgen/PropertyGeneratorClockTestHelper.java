package asmeta.asmeta.ai.propgen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import asmeta.ai.propgen.PropertyGenerator;
import asmeta.ai.propgen.PropertyGenerator.PropertyType;

class PropertyGeneratorClockTestHelper {

	private static final String ASM_PATH = "./src/test/resources/AdvancedClock.asm";

	static void fromASMtoNLClock(PropertyGenerator pg) throws Exception {
		int nProp = 3;
		List<String> properties = pg.fromASMtoNL(ASM_PATH, nProp, false);
		
		System.out.println("===== From ASM to NL properties =====");
		for (String prop : properties) {
			System.out.println(prop);
		}
		
		assertEquals(nProp, properties.size());
	}

	static void fromNLtoTLClock(PropertyGenerator pg) throws Exception {
		String prop = "in every possible reachable, state when the seconds function reaches the value 59,"
				+ " it is set to 0 in the next state";
		String ctl = pg.fromNLtoTL(ASM_PATH, prop, PropertyType.CTLPROP, false);
		
		System.out.println("===== From NL to CTL property =====");
		System.out.println(ctl);
		
		assertTrue(ctl.toLowerCase().contains("ag"));		
	}

	static void fromTLtoNLClock(PropertyGenerator pg) throws Exception {
		String prop = "ctlspec ag (eq(seconds,59) implies ax(eq(seconds,0)))";
		String explanation = pg.fromTLtoNL(ASM_PATH, prop, false);
		
		System.out.println("===== From CTL to NL property =====");
		System.out.println(explanation);
		
		assertFalse(explanation.isBlank());
	}

}
