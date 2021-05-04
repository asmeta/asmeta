package org.asmeta.codegenerator;

import org.junit.Test;

public class JsonGeneratorTestSingletest extends HWIntegratorAbstractClass {
	
	@Test
	public void testSIS() throws Exception {
		generateJsonConfiguration("examples/SIS/SIS.uasm",false);
	}
	@Test
	public void testAllBindings() throws Exception {
		generateJsonConfiguration("examples/allBindings/allBindings.uasm",false);
	}
	@Test
	public void testBasicDomain() throws Exception {
		generateJsonConfiguration("examples/basicDomain/basicDomain.uasm",false);
	}
	@Test
	public void testCarSystem() throws Exception {
		generateJsonConfiguration("examples/CarSystem/CarSystem_lev2_complete.uasm",false);
	}
	@Test
	public void testEnums() throws Exception {
		generateJsonConfiguration("examples/enums/enums.uasm",false);
	}
	@Test
	public void testExtendableDomain() throws Exception {
		generateJsonConfiguration("examples/extendableDomain/extendableDomain.uasm",false);
	}
	@Test
	public void testExtendableDomain2() throws Exception {
		generateJsonConfiguration("examples/extendableDomain2/extendableDomain2.uasm",false);
	}
	
	@Test
	public void testcounter() throws Exception {
		generateJsonConfiguration("C:\\Users\\Silvia\\Documents\\Sourceforge\\ASMETA\\code\\experimental\\asmetal2cpp\\asmetal2cpp_codegen\\examples\\asmeta_examples\\Counter.asm",false);
	}
	
	@Test
	public void testPB() throws Exception {
		generateJsonConfiguration("F:\\Dati-Andrea\\runtime-EclipseApplication\\Pllbox_Ard\\pillbox.asm",true);
	}

	
	@Test
	public void testMVM() throws Exception {
		generateJsonConfiguration("D:\\AgHome\\progettidaSVNGIT\\mvm-asmeta\\VentilatoreASM\\Ventilatore000.asm",true);
	}

	
}
