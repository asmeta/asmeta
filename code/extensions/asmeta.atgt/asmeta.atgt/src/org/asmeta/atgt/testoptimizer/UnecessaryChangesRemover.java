package org.asmeta.atgt.testoptimizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.State;
import org.asmeta.simulator.main.AsmModelNotFoundException;
import org.asmeta.simulator.main.MainRuleNotFoundException;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.readers.MonFuncReader;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.EnumValue;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.Value;

import asmeta.AsmCollection;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.util.DomainsSwitch;
import atgt.coverage.AsmTestSequence;
import tgtlib.definitions.expression.type.Variable;

// removes changes of monitored values that are unnecessary
// i.e. that are asked by the simulator
public class UnecessaryChangesRemover extends TestOptimizer {
	
	static private Logger log = Logger.getLogger(UnecessaryChangesRemover.class); 

	private AsmCollection asms;

	public UnecessaryChangesRemover(AsmCollection asms) {
		this.asms = asms;
	}

	@Override
	public void optimize(AsmTestSequence asmTest) {
		//
		try {
			// simulate
			int nSteps = asmTest.allInstructions().size();
			CheckWhatAsked monFuncReader = new CheckWhatAsked(asmTest);
			Simulator simulator = new Simulator(asms.getMain().getName(), asms, new Environment(monFuncReader));
			for (int i = 0; i < nSteps; i++) {
				simulator.run(1);
				monFuncReader.nextState();
			}
		} catch (AsmModelNotFoundException | MainRuleNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // o size +1???
	}

	class CheckWhatAsked extends MonFuncReader {

		private AsmTestSequence asmTest;
		int currentStateIndex;
		// using string - be careful with functions n-ary
		List<String> asked;
		Map<String,String> lastvalues = new HashMap<>();

		public CheckWhatAsked(AsmTestSequence asmTest) {
			this.asmTest = asmTest;
			currentStateIndex = 0;
			asked = new ArrayList<>();
			fillIn();
		}

		@Override
		public Value readValue(org.asmeta.simulator.Location location, State state) {
			String val = lastvalues.get(location.toString());
			assert val != null;
			log.debug("asking for " + location  + " value " + val);
			StringToValue sv = new StringToValue(val);
			asked.add(location.toString());
			log.debug("domain " + location.getSignature().getCodomain());
			return sv.doSwitch(location.getSignature().getCodomain());
		}

		void nextState() {
			//System.out.println("asked only " + asked);
			// remove the variables that are not asked
			Map<? extends Variable, String> currentState = asmTest.getState(currentStateIndex);
			List<? extends Variable> keySet = new ArrayList<>(currentState.keySet());
			for (Variable var : keySet) {
				if (!asked.contains(var.toString()) && !var.isControlled()) {
					log.debug("removing " + var.toString());
					currentState.remove(var);
				} else {
					log.debug("keeping " + var.toString());
				}
			}
			currentStateIndex++;
			asked.clear();
			fillIn();
		}

		private void fillIn() {
			// fill last values with the new state
			if (currentStateIndex < asmTest.allInstructions().size()) {
				Map<? extends Variable, String> map = asmTest.getState(currentStateIndex);
				for (Entry<? extends Variable, String> o : map.entrySet()) {
					lastvalues.put(o.getKey().toString(), o.getValue());
				}
			}
		}
	}

	// convert a string to a value according to the domain of the location
	class StringToValue extends DomainsSwitch<Value> {

		private String s;

		StringToValue(String s) {
			this.s = s;
		}

		@Override
		public Value caseIntegerDomain(IntegerDomain object) {
			return new IntegerValue(Integer.parseInt(s));
		}

		@Override
		public Value caseBooleanDomain(BooleanDomain object) {
			return BooleanValue.parserBooleanValue(s);
		}

		@Override
		public Value caseEnumTd(EnumTd en) {
			for (EnumElement e : en.getElement()) {
				if (e.getSymbol().equals(s))
					return new EnumValue(s);
			}
			throw new RuntimeException("enum not found in domain");
		}

	}

}
