package org.asmeta.modeladvisor;

import org.junit.Test;

public class AsmetaMATestErrors {

	@Test
	public void testExampleWrongFail() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/exampleWrongFail.asm");
		asmetaMA.setAllMetapropertiesExecution();
		asmetaMA.runCheck();
	}

	@Test
	public void testABZ20CaseStudy() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("/Users/parcaini/Desktop/testiAsmeta/ABZ2020_casestudy/Casestudy/ASM model/Car System MC/CarSystem007.asm");
		asmetaMA.setMetapropertiesExecution(true, false, false, false, false, false, false);
		asmetaMA.runCheck();
	}
}