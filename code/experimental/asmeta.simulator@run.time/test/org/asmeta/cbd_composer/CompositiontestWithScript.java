package org.asmeta.cbd_composer;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.cbd_composer.BiPipeFullDup;
import org.asmeta.cbd_composer.BiPipeHalfDup;
import org.asmeta.cbd_composer.Composition;
import org.asmeta.cbd_composer.CompositionException;
import org.asmeta.cbd_composer.LeafAsm;
import org.asmeta.cbd_composer.ParN;
import org.asmeta.cbd_composer.Parser;
import org.asmeta.cbd_composer.PipeN;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.main.Simulator;
import org.junit.Test;

public class CompositiontestWithScript {

	static String path = "examples/cbd_examples/";
	
	@Test
	public void testSmartHome() {
		ComposerCLI2 comp = new ComposerCLI2("examples/SmartMultiHome/FireSystem.asmsh",false);
		comp.runcomposition();
	}
	
	@Test
	public void testPillbox() {
		ComposerCLI2 comp = new ComposerCLI2("examples/Pillbox_composition/pillboxComp2.asmsh",false);
		comp.runcomposition();
	}
}	