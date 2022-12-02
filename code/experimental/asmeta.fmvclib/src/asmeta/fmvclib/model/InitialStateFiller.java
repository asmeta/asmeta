package asmeta.fmvclib.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.value.Value;

import asmeta.structure.FunctionInitialization;

public class InitialStateFiller {
	
	@SuppressWarnings("rawtypes")
	public static State fillSimulatorState(Simulator sim) {
		
		Map<Location, Value> locationAssignments = new HashMap<>();
		
		for (FunctionInitialization f : sim.getAsmModel().getDefaultInitialState().getFunctionInitialization()) {
			Map<Location, Value> functionValue = sim.getCurrentState().read(f.getInitializedFunction());
			System.out.println("\t" + f.getInitializedFunction().getName());
			System.out.println("\t" + functionValue);
			for (Entry<Location, Value> entry : functionValue.entrySet()) {
				locationAssignments.put(entry.getKey(), entry.getValue());
			}
		}
		
		return null;		
	}
	
}
