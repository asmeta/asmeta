package org.asmeta.avallaxt.validator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaPrinterForAvalla;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMultipleImports extends TestValidator {

	@Test
	public void testMultipleImports1() throws Exception {
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem003/scenari/HighBeamFlasherONOFF.avalla", true,
				false);
	}

	@Test
	public void testMultipleImports2() throws Exception {
		test(ASM_EXAMPLES_EXAMPLES + 
				"ABZ2020/CarSystemModule/CarSystem003/scenari/HighBeamFixedONOFF.avalla", true,
				false);
	}

	@Test
	public void testMultipleImports3() throws Exception {
		test(ASM_EXAMPLES_EXAMPLES
				+ "ABZ2020/CarSystemModule/CarSystem003/scenari/HighBeamFlasherONOFF.avalla", true, false);
	}

	@Test
	public void testMultipleImports4() throws Exception {
		// test("../../../../asm_examples/examples/ABZ2020/CarSystemModule/CarSystem001/scenari/TipBlinking.avalla",
		// false, false);
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem001/scenari/HWExecutedRunning.avalla");
	}

	@Test
	public void testMultipleCarSystem1() throws Exception {
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem001/scenari/", true, true);
	}

	@Test
	public void testMultipleCarSystem2() throws Exception {
		// some problems in these tests
		//test("../../../../asm_examples/examples/ABZ2020/CarSystemModule/CarSystem002/scenari/LowBeamOFFonAmbientLight.avalla");
		//test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem002/scenari/", true, false);
	}

	@Test
	public void testMultipleCarSystem() throws Exception {
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem003/scenari/", true, true);
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem004/scenari/", true, true);
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem005/scenari/", true, true);
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem006/scenari/", true, true);
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem007/scenari/", true, true);
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem009/scenari/", true, true);
	}

	@BeforeClass
	public static void setupLoggers() {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.OFF);
		Logger.getLogger(AsmetaPrinterForAvalla.class).setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
	}

}
