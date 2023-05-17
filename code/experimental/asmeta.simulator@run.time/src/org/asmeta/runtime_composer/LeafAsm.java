package org.asmeta.runtime_composer;

import java.io.File;
import java.util.Map.Entry;

import org.asmeta.parser.ASMParser;
import org.asmeta.runtime_simulator.SimulatorRT;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.value.Value;

import asmeta.AsmCollection;

public class LeafAsm extends Composition {

	SimulatorRT s1;
	MFReaderWithSettableMon mon;
	String name;

	public LeafAsm(String asm1) throws Exception {
		mon = new MFReaderWithSettableMon();
		Environment env = new Environment(mon);
		AsmCollection asc1 = ASMParser.setUpReadAsm(new File(asm1));
		name = asc1.getMain().getName();
		s1 = new SimulatorRT(name, asc1, env);
	}

	@Override
	public UpdateSet eval() {
		System.out.println("");
		System.out.println("");
		System.out.println("Running " + name);// + " current state" + s1.getCurrentState());
		UpdateSet up=s1.run(1);
		return up;
	}

	// copy from updateSet to s2
	public void copyMonitored(UpdateSet up) {
		// System.out.println("copying " + up + " in " + name);
		LeafAsm lc = this;
		String out = "";
		out += "UpdateSet = {";
		for (Entry<Location, Value> l : up) {
			Location loc = l.getKey();
			String locName = loc.getSignature().getName();
			// set the mon funtion to the mon func reader
			mon.add(l);
			out += l + "; ";
		}
		System.out.println(out.substring(0, out.length() - 2) + "}");
	}

	@Override
	public String toString() {
		return "ASM:" + name;
	}

	

}

