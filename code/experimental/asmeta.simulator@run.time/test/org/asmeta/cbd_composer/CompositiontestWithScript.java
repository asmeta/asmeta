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
		ComposerCLI comp = new ComposerCLI("examples/SmartMultiHome/FireSystem.asmsh",true);
		comp.runcomposition();
	}
	
	@Test
	public void testPillbox() {
		ComposerCLI comp = new ComposerCLI("examples/Pillbox_composition/pillboxComp2.asmsh",false);
		comp.runcomposition();
	}
	
	@Test
	public void cross1() {
		ComposerCLI comp = new ComposerCLI("examples/trafficLightCoSimCross/nopedestriannotram.asmsh",true);
		comp.runcomposition();
	}
	
	@Test
	public void cross2() {
		ComposerCLI comp = new ComposerCLI("examples/trafficLightCoSimCross/pedestriannotram.asmsh",true);
		comp.runcomposition();
	}
	
	@Test
	public void cross3() {
		ComposerCLI comp = new ComposerCLI("examples/trafficLightCoSimCross/nopedestriantram.asmsh",true);
		comp.runcomposition();
	}
	
	@Test
	public void cross4() {
		ComposerCLI comp = new ComposerCLI("examples/trafficLightCoSimCross/pedestrian2notram.asmsh",true);
		comp.runcomposition();
	}
	
	@Test
	public void cross5() {
		ComposerCLI comp = new ComposerCLI("examples/trafficLightCoSimCross/pedestriantram.asmsh",true);
		comp.runcomposition();
	}
	
	@Test
	public void cross6() {
		ComposerCLI comp = new ComposerCLI("examples/trafficLightCoSimCross/pedestriantramtogether.asmsh",true);
		comp.runcomposition();
	}
	
	@Test
	public void cross7() {
		ComposerCLI comp = new ComposerCLI("examples/trafficLightCoSimCross/pedestrianbeforetram.asmsh",true);
		comp.runcomposition();
	}
	
	@Test
	public void cross8() {
		ComposerCLI comp = new ComposerCLI("examples/trafficLightCoSimCross/pedestrianaftertram.asmsh",true);
		comp.runcomposition();
	}
	
	@Test
	public void cross9() {
		ComposerCLI comp = new ComposerCLI("examples/trafficLightCoSimCross/nopedestriantram2.asmsh",true);
		comp.runcomposition();
	}
}	