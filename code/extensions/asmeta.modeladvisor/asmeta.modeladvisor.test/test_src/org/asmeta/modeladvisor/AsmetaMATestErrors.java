package org.asmeta.modeladvisor;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.asmeta.nusmv.MapVisitor;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AsmetaMATestErrors {

	@Test
	public void testExampleWrongFail() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/exampleWrongFail.asm");
		asmetaMA.setAllMetapropertiesExecution();
		asmetaMA.runCheck();
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testABZ20CaseStudy() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("/Users/parcaini/Desktop/testiAsmeta/ABZ2020_casestudy/Casestudy/ASM model/Car System MC/CarSystem007.asm");
		asmetaMA.setMetapropertiesExecution(true, false, false, false, false, false, false);
		asmetaMA.runCheck();
	}
	
	// this example passes the test for model checking but not the model advisor
	// the meta propeties are too long????
	@Test
	public void testBenInf() throws Exception {
//		Logger.getLogger(MapVisitor.class).setLevel(Level.ALL);
//		Logger.getLogger(AsmetaMA.class).setLevel(Level.ALL);
//		Logger.getRootLogger().addAppender(new ConsoleAppender(new SimpleLayout()));
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/unibgStudents/trackerID2.asm");
		asmetaMA.setMetapropertiesExecution(true, false, false, false, false, false, false);
		asmetaMA.runCheck();
	}

	
}