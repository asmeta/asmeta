package asmeta.fmvclib.model;

import java.util.ArrayList;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
//import org.asmeta.simulator.TermEvaluator;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.readers.RandomMFReader;
import org.junit.Test;

import asmeta.structure.FunctionInitialization;
import asmeta.structure.Initialization;

public class InitialStateFillerTest {
	
	private static final String ASM_FILE = "examples/aman.asm";

//	@Test
//	public void testFiller() throws Exception {
//		RandomMFReader reader = new RandomMFReader();
//		Environment.timeMngt = TimeMngt.use_java_time;
//		Environment environment = new Environment(reader);
//		Simulator sim = Simulator.createSimulator(ASM_FILE, environment);
//		sim.run(0);
//		InitialStateFiller.fillSimulatorState(sim, environment);
//	}
	
	@Test	
	public void testState() throws Exception {
		AsmetaFMVCModel.ASM_PATH = "examples/";
		Environment.timeMngt = TimeMngt.use_java_time;
		Environment env = new Environment(new RandomMFReader());
		Simulator sim = Simulator.createSimulator(ASM_FILE, env);
		sim.run(0);
		State s = sim.getCurrentState();
		//TermEvaluator tm = new TermEvaluator(s, null, null);
		
		Initialization initialization = sim.getAsmModel().getDefaultInitialState();
		InitialStateVisitor visitor = new InitialStateVisitor();
		// Visit the function initialization part
		for (FunctionInitialization init : initialization.getFunctionInitialization()) {
			visitor.visitInit(init);
		}

		
		for (FunctionInitialization fInit : sim.getAsmModel().getDefaultInitialState().getFunctionInitialization()) {
			
			ArrayList<Location> locations = new InitialStateVisitor().getLocations(fInit.getInitializedFunction());
			for(Location l : locations)
				try {
					System.out.println(env.read(l, s));
				} catch (AssertionError e) {
					System.err.println(l);
				}
			
			/*
			Map<Integer, Value[]> variableValuesMap = new HashMap<>(); 
			if (fInit.getVariable().size()>0) {
				int count = 0;
				for (VariableTerm v : fInit.getVariable()) {
					variableValuesMap.put(count, (Value[]) ((Set<Value>)tm.getValues(v.getDomain()).getValue()).toArray());
				}				
			}
			
			
			//Map<Location, Value> functionValue = sim.getCurrentState().read(fInit.getInitializedFunction());*/
		}
	}
	
}
