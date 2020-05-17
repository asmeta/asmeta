package org.asmeta.simulator.main;

import java.util.ArrayList;
import java.util.Map;

import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.readers.ListMonFuncReader;
import org.asmeta.simulator.readers.ListMonFuncReaderNew;
import org.asmeta.simulator.readers.MonitoredValues;

import asmeta.AsmCollection;

/**
 * Crea un simulatore a cui, come interprete di monitorate, viene passata una
 * lista di stringhe.
 * 
 */
public class SimulatorForMonitoring extends Simulator {

	public SimulatorForMonitoring(String modelName, AsmCollection asmp, Environment env)
			throws AsmModelNotFoundException, MainRuleNotFoundException {
		super(modelName, asmp, env);
	}

	public static Simulator createSimulator(String modelPath, ArrayList<Map<Location, String>> list) throws Exception {
		Environment env = new Environment(new ListMonFuncReader(list));
		return createSimulator(modelPath, env);
	}

	public static Simulator createSimulator(String modelPath, MonitoredValues map) throws Exception {
		Environment env = new Environment(new ListMonFuncReaderNew(map));
		return createSimulator(modelPath, env);
	}

	public static Simulator createSimulator(String asmPath, MonitoredValues listMonsForAsmSimulator,
			AsmCollection asmetaPackage) throws Exception {
		Environment env = new Environment(new ListMonFuncReaderNew(listMonsForAsmSimulator));
		return createSimulator(asmPath, env, asmetaPackage);
	}
}