package org.asmeta.cbdcomposer.compositions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.asmeta.cbdcomposer.CompositionException;
import org.asmeta.parser.ASMParser;
import org.asmeta.runtime_simulator.AsmetaSserviceRun;
import org.asmeta.runtime_simulator.InfoAsmetaService;
import org.asmeta.runtime_simulator.RunMode;
import org.asmeta.runtime_simulator.SimulatorRT;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.value.Value;

import asmeta.AsmCollection;

// terminal node of the composition
public class LeafAsm extends Composition {

	SimulatorRT s1;
	MFReaderWithSettableMon mon;
	public String name;
	// FIX 08/11/2025 BY PATRIZIA
	Map<Integer, InfoAsmetaService> simulatorMap;
	AsmetaSserviceRun runner;
	// AsmetaSserviceRun with id=1 to provide monitored function values via a map

	// monitored functions are handled in an interactive way by CLI
	/*
	 * LeafAsm(String asm1) throws Exception { mon = new MFReaderWithSettableMon();
	 * Environment env = new Environment(mon); AsmCollection asc1 =
	 * ASMParser.setUpReadAsm(new File(asm1)); name = asc1.getMain().getName(); s1 =
	 * new SimulatorRT(name, asc1, env); }
	 */

	// NEW by Patrizia: to read monitored function values from CLI or from a map by
	// using InfoAsmetaService e AsmetaSserviceRun
	public LeafAsm(String asm1, boolean interactive) throws Exception {
		File asm = new File(asm1);
		if (!asm.exists()) {
			throw new RuntimeException("asm file " + asm1 + " not found");
		}
		AsmCollection asc1 = ASMParser.setUpReadAsm(asm);
		name = asc1.getMain().getName();
		mon = new MFReaderWithSettableMon(); // FIX by Patrizia 8 nov 2025 mon is always used (in interactive mode or
												// not) for copyMonitored
		if (interactive) {
			// mon = new MFReaderWithSettableMon();
			Environment env = new Environment(mon); // from CLI
			s1 = new SimulatorRT(name, asc1, env);
		} else {
			simulatorMap = new HashMap<Integer, InfoAsmetaService>();// NEW from a map
			runner = new AsmetaSserviceRun(1, simulatorMap);
			Environment env = new Environment(runner);
			s1 = new SimulatorRT(name, asc1, env);
			InfoAsmetaService is1 = new InfoAsmetaService(s1);
			simulatorMap.put(1, is1);
		}
		s1.setShuffleFlag(true); // to allow non-deterministic behavior of choose rule
	}

	@Override
	public
	UpdateSet eval() {
		System.out.println("Running " + name);// + " current state" + s1.getCurrentState());
		// UpdateSet[]up=new UpdateSet[1];
		UpdateSet up = s1.run(1);
		return up;
	}

	// @Override by Patrizia
	public UpdateSet eval(boolean cdb, Map<String, String> locationValue) throws CompositionException {
		// SimulationContainer->AsmetaSservice->InfoAsmetaSservice->SimulatorRT
		// and AsmetaServiceRun to read monitored values from a map
		UpdateSet ups = null;
		try {
			InfoAsmetaService is1 = simulatorMap.get(1);
			// FIX by Patrizia 8 nov 2025: add mon resulting from copyMonitored() in
			// locationValue
			// System.out.println("Copied monnitored locations:
			// "+mon.monStoredValues.toString());
			if (!mon.monStoredValues.isEmpty()) {
				Map<String, String> tmp = new HashMap<>();
				// iterate over mon locations and copy the string version of each location in
				// tmp
				for (Entry<String, Value> pair : mon.monStoredValues.entrySet()) {
					if (pair.getValue() instanceof org.asmeta.simulator.value.StringValue)
						// add "" when the function is string
						tmp.put(pair.getKey(), "\"" + pair.getValue().toString() + "\"");
					else
						tmp.put(pair.getKey(), pair.getValue().toString());
					locationValue.putAll(tmp);
				}
			}
			is1.setLocationValue(locationValue);
			System.out.println("Monitored locations " + name + ": " + locationValue.toString());

			runner.run(RunMode.RUN_ONE_STEP); // run one step
			is1.incContSim(); // increase counter of simulation steps
			// MyState state = is1.getState();
			// From Mystate to UpdateSet
			State state = is1.getSim().getCurrentState();
			Map<Location, Value> map = state.getLocationMap(); // return the current location map
			ups = new UpdateSet(); // tricky: create an UpdateSet containing the location set of s1
			ups.applyLocationUpdates(map);
			// Print out locations
			System.out.println("Out locations " + name + ": " + state.getOutLocs().toString());
			/*
			 * for (Entry<Location, Value> pair : state.getOutLocs().entrySet())
			 * System.out.println(pair.getKey() + " " + pair.getValue().toString());
			 */
			/*
			 * System.out.println("PRINT STATE: " + name ); for (Entry<Location, Value> pair
			 * : state.getLocationMap().entrySet()) System.out.println(pair.getKey() + " " +
			 * pair.getValue().toString());
			 */
		} catch (InvalidInvariantException e) {
			checkInvariantException(e);
			throw e;
		}

		return ups;
	}

	@Override
	public UpdateSet eval(boolean cdb) throws CompositionException {
		System.out.println("Running " + name);// + " current state" + s1.getCurrentState());
		UpdateSet up = null;
		if (cdb) {
			try {
				up = s1.run(1);
			}
			// try {up=s1.runUntil(s1,1,InvariantTreament.CHECK_CONTINUE).updateSet;} TODO
			// continue when invariant violation
			catch (InvalidInvariantException e) {
				checkInvariantException(e);
			}
		} else
			up = eval();
		return up;
	}

	// copy from updateSet to s2
	protected void copyMonitored(UpdateSet up) {
		// System.out.println("copying " + up + " in " + name);
		// LeafAsm lc = this;
		// String out = "";
		// out += "UpdateSet " + name + "= {";
		for (Entry<Location, Value> l : up) {
			// Location loc = l.getKey();
			// String locName = loc.getSignature().getName();
			// set the mon funtion to the mon func reader
			mon.add(l);
			// out += l + "; ";
		}
		/*
		 * if (!up.isEmpty()) System.out.println(out.substring(0, out.length() - 2) +
		 * "}"); else System.out.println(out.substring(0, out.length() - 2)+ "{}");
		 */
	}

	@Override
	public String toString() { // stampa update set: controlled e out e monitored
		// return "ASM " + name + ": "+ s1.getCurrentState().getOutLocs() + " "+
		// s1.getCurrentState().+ " "+ s1.getCurrentState().getContrLocs();
		return "ASM " + name + ": " + s1.getCurrentState().getOutLocs();
	}

}