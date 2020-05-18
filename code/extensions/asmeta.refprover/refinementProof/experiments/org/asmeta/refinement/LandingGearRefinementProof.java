package org.asmeta.refinement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LandingGearRefinementProof extends ProofTest {

	@Before
	public void beforeTest() {
		basePath = "../../../../asm_examples/examples/landingGearSystem/";
	}

	@Test
	public void test1_FirstRefinement() throws Exception {
		Set<String> functionsToCheck = new HashSet<String>(Arrays.asList(new String[]{"doors0", "gears0"}));
		proveRefinement("LGS_GM.asm", "LGS_EV.asm", functionsToCheck, true, true);
	}

	@Test
	public void test2_SecondRefinement() throws Exception {
		Set<String> functionsToCheck = new HashSet<String>(Arrays.asList(new String[]{"doors0", "gears0"}));
		proveRefinement("LGS_EV.asm", "LGS_SE.asm", functionsToCheck, true, true);
	}

	@Test
	public void test2a_From_LGS_GM_to_LGS_SE() throws Exception {
		Set<String> functionsToCheck = new HashSet<String>(Arrays.asList(new String[]{"doors0", "gears0"}));
		proveRefinement("LGS_GM.asm", "LGS_SE.asm", functionsToCheck, true, true);
	}

	@Test
	public void test3_ThirdRefinement() throws Exception {
		Set<String> functionsToCheck = new HashSet<String>(Arrays.asList(new String[]{"doors0", "gears0"}));
		proveRefinement("LGS_SE.asm", "LGS_3L.asm", functionsToCheck, true, true);
	}

	//@Test
	public void test4_FourthRefinement() throws Exception {
		closeConsoleStream();
		Set<String> functionsToCheck = new HashSet<String>(Arrays.asList(new String[]{"doors0", "gears0"}));
		proveRefinement("LGS_3L.asm", "LGS_HM.asm", functionsToCheck, true, true);
	}
}