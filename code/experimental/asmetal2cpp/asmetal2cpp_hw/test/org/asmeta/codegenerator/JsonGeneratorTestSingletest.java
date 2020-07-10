package org.asmeta.codegenerator;

import org.junit.Test;

public class JsonGeneratorTestSingletest extends JsonGeneratorAbstractClass {
	
	@Test
	public void testSIS() throws Exception {
		testGenerator("examples/SIS/SIS.uasm",false);
	}
	@Test
	public void testAllBindings() throws Exception {
		testGenerator("examples/allBindings/allBindings.uasm",false);
	}
	@Test
	public void testBasicDomain() throws Exception {
		testGenerator("examples/basicDomain/basicDomain.uasm",false);
	}
	@Test
	public void testCarSystem() throws Exception {
		testGenerator("examples/CarSystem/CarSystem_lev2_complete.uasm",false);
	}
	@Test
	public void testEnums() throws Exception {
		testGenerator("examples/enums/enums.uasm",false);
	}
	@Test
	public void testExtendableDomain() throws Exception {
		testGenerator("examples/extendableDomain/extendableDomain.uasm",false);
	}
	@Test
	public void testExtendableDomain2() throws Exception {
		testGenerator("examples/extendableDomain2/extendableDomain2.uasm",false);
	}
	
	@Test
	public void testcounter() throws Exception {
		testGenerator("C:\\Users\\Silvia\\Documents\\Sourceforge\\ASMETA\\code\\experimental\\asmetal2cpp\\asmetal2cpp_codegen\\examples\\asmeta_examples\\Counter.asm",false);
	}
	
	@Test
	public void testPB() throws Exception {
		testGenerator("F:\\Dati-Andrea\\runtime-EclipseApplication\\Pllbox_Ard\\pillbox.asm",true);
	}

}
