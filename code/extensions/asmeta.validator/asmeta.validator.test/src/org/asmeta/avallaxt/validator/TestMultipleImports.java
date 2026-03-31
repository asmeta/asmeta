package org.asmeta.avallaxt.validator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaPrinterForAvalla;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestMultipleImports extends TestValidator {

	@Test void multipleImports1() throws Exception {
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem003/scenari/HighBeamFlasherONOFF.avalla", true,
				false, true);
	}

	@Test void multipleImports2() throws Exception {
		test(ASM_EXAMPLES_EXAMPLES + 
				"ABZ2020/CarSystemModule/CarSystem003/scenari/HighBeamFixedONOFF.avalla", true,
				false, true);
	}

	@Test void multipleImports3() throws Exception {
		test(ASM_EXAMPLES_EXAMPLES
				+ "ABZ2020/CarSystemModule/CarSystem003/scenari/HighBeamFlasherONOFF.avalla", true, false, true);
	}

	@Test void multipleImports4() throws Exception {
		// test("../../../../asm_examples/examples/ABZ2020/CarSystemModule/CarSystem001/scenari/TipBlinking.avalla",
		// false, false);
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem001/scenari/HWExecutedRunning.avalla");
	}

	@Test void multipleCarSystem1() throws Exception {
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem001/scenari/", true, true, true);
	}

	@Test void multipleCarSystem2() throws Exception {
		// some problems in these tests
		//test("../../../../asm_examples/examples/ABZ2020/CarSystemModule/CarSystem002/scenari/LowBeamOFFonAmbientLight.avalla");
		//test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem002/scenari/", true, false);
	}

	@Test void multipleCarSystem() throws Exception {
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem003/scenari/", true, true, true);
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem004/scenari/", true, true, true);
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem005/scenari/", true, true, true);
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem006/scenari/", true, true, true);
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem007/scenari/", true, true, true);
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem009/scenari/", true, true, true);
	}

	@BeforeAll
	static void setupLoggers() {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.OFF);
		Logger.getLogger(AsmetaPrinterForAvalla.class).setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		Logger.getRootLogger().setLevel(Level.OFF);
	}

}
