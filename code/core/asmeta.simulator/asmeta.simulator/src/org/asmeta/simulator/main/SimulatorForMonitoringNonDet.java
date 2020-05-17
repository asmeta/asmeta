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
 * It builds a simulator which uses, as monitored function reader, a list of strings.
 * 
 */
public class SimulatorForMonitoringNonDet extends SimulatorAllUpdateSets {

	public SimulatorForMonitoringNonDet(String modelName, AsmCollection asmp,
			Environment env) throws AsmModelNotFoundException,
			MainRuleNotFoundException {
		super(modelName, asmp, env);
	}

	public static SimulatorAllUpdateSets createSimulator(String modelPath,
			ArrayList<Map<Location, String>> list) throws Exception {
		Environment env = new Environment(new ListMonFuncReader(list));
		return createSimulator(modelPath, env);
	}

	public static SimulatorAllUpdateSets createSimulatorNewMonitored(String modelPath,
			MonitoredValues listMonsForAsmSimulator) throws Exception {
		Environment env = new Environment(new ListMonFuncReaderNew(listMonsForAsmSimulator));
		return createSimulator(modelPath, env);
	}

	/*public static SimulatorAllUpdateSets createSimulator(String asmPath,
			MonitoredValues listMonsForAsmSimulator, AsmCollection asmetaPackage) throws Exception {
		Environment env = new Environment(new ListMonFuncReaderNew(listMonsForAsmSimulator));
		return createSimulator(asmPath, env, asmetaPackage);
	}*/

	public static SimulatorAllUpdateSets createSimulator(String asmPath,
			MonitoredValues map) throws Exception {
		Environment env = new Environment(new ListMonFuncReaderNew(map));
		return createSimulator(asmPath, env);
	}

	/*public static SimulatorAllUpdateSets createSimulator(String asmPath, AsmCollection asmetaPackage) throws Exception {
		Environment env = new Environment(new InteractiveMFReader(System.in, System.out));
		return createSimulator(asmPath, env, asmetaPackage);
	}*/
}