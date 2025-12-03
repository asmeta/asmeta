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
import org.asmeta.simulator.main.Simulator.InvariantTreament;
import org.asmeta.simulator.readers.MonFuncReader;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.EnumValue;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.ReserveValue;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;

import asmeta.AsmCollection;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.util.DomainsSwitch;
import atgt.coverage.AsmTestSequence;
import tgtlib.definitions.expression.Undef;
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
			// supespend the checking of invariants
			InvariantTreament temp = Simulator.checkInvariants;
			Simulator.checkInvariants = InvariantTreament.NO_CHECK;
			for (int i = 0; i < nSteps; i++) {
				simulator.run(1);
				monFuncReader.nextState();
			}
			// reset the position as before
			Simulator.checkInvariants = temp;
		} catch (AsmModelNotFoundException | MainRuleNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // o size +1???
	}

	//
	// leave only the functions required by the simulator
	//
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
			asked.add(location.toString());
			// if it is undef
			if (val.equals(UndefValue.UNDEF.toString())) {
				return UndefValue.UNDEF;
			}
			//
			StringToValue sv = new StringToValue(val);
			Domain codomain = location.getSignature().getCodomain();
			log.debug("domain " + codomain);
			Value doSwitch = sv.doSwitch(codomain);
			assert doSwitch != null : "location " +  location + " value as string " +  val + " in codomain " + codomain.getClass();
			return doSwitch;
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
		public Value caseConcreteDomain(ConcreteDomain cd) {
			Value val = doSwitch(cd.getTypeDomain());
			assert val != null;
			return val;
		}

		@Override
		public Value caseAbstractTd(AbstractTd atd) {
			return new ReserveValue(s);
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
				if (e.getSymbol().equals(s)) {
					return new EnumValue(s);
				}
			}
			// if it is undef
			if (s.equals(Undef.UNDEF.toString())) {
				return UndefValue.UNDEF;
			}
			throw new RuntimeException("enum " + s +  " not found in domain "+ en.getName() + " elements " + en.getElement());
		}

	}

}
