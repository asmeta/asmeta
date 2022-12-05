package asmeta.fmvclib.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.TermEvaluator;
import org.asmeta.simulator.ValueAssignment;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.value.Value;

import asmeta.structure.FunctionInitialization;

public class InitialStateFiller {

//	@SuppressWarnings("rawtypes")
//	public static State fillSimulatorState(Simulator sim, Environment env) {
//		
//		Map<Location, Value> locationAssignments = new HashMap<>();
//		
//		for (FunctionInitialization f : sim.getAsmModel().getDefaultInitialState().getFunctionInitialization()) {
//			Map<Location, Value> functionValue = sim.getCurrentState().read(f.getInitializedFunction());
//			System.out.println("\t" + f.getInitializedFunction().getName());
//			System.out.println("\t" + functionValue);
//			
//			if (functionValue.size() == 0) {
//				ValueAssignment newAssignment = new ValueAssignment();
//				TermEvaluator te;
//				// If arity is 0, we do not need to add the assignments
//				if (f.getInitializedFunction().getArity() == 1) {
//					Value[] arguments = location.getElements();				
//					List<?> variables = f.getVariable();
//					newAssignment.put(variables, arguments);
//				} else if (f.getInitializedFunction().getArity() > 1) {
//					Value[] arguments = location.getElements();				
//					List<?> variables = f.getVariable();
//					newAssignment.put(variables, arguments);
//				}
//
//				te = new TermEvaluator(sim.getCurrentState(), env, newAssignment);
//				System.out.println("----" + te.visit(f.getBody()));
//			}
//			
//			for (Entry<Location, Value> entry : functionValue.entrySet()) {
//				locationAssignments.put(entry.getKey(), entry.getValue());
//			}
//		}
//		
//		return null;		
//	}

}
