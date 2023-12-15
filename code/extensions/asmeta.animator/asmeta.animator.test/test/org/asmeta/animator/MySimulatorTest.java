package org.asmeta.animator;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Map;

import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.readers.RandomMFReader;
import org.asmeta.simulator.value.Value;
import org.junit.Test;

import asmeta.AsmCollection;

/** test that the initial state is set during the simulation even at the first step*/
public class MySimulatorTest {
	
	String init;

	@Test
	public void testRun() throws Exception {
		//Logger.getLogger(TermEvaluator.class).setLevel(Level.ALL);
		File string = new File("examples/coffeeVendingMachineNC.asm");
		final AsmCollection asmp = ASMParser.setUpReadAsm(string);
		Environment env = new Environment(new RandomMFReader());
		
		SimulatorForAnimator simulator = new SimulatorForAnimator("coffeeVendingMachineNC", asmp, env, new VisualizationSimulationI() {

			@Override
			public void setInvalidIvariantText(String s) {
				System.out.println("inavalid invariant " + s);
				
			}

			@Override
			public void setInitValues(Map<Location, Value> locationsPrevSet2) {
				System.out.println(locationsPrevSet2);
				// 
				setInit(locationsPrevSet2.toString());
			}
			
		});
		simulator.run(1);
		// check that all the variables have been inizialized
		assertEquals("{available(TEA)=10, available(COFFEE)=10, available(MILK)=10, coins=0}", init);
		
	}

	protected void setInit(String string) {
		init = string;
	}
}
