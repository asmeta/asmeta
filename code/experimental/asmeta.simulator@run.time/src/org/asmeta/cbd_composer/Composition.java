package org.asmeta.cbd_composer;

import java.io.File;
import org.asmeta.runtime_container.SimulationContainer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.asmeta.animator.MyState;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.Defs;
import org.asmeta.runtime_simulator.AsmetaSserviceRun;
import org.asmeta.runtime_simulator.InfoAsmetaService;
import org.asmeta.runtime_simulator.RunMode;
import org.asmeta.runtime_simulator.SimulatorRT;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
//import org.asmeta.simulator.LocationSet.WrappedDomain;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.main.Simulator.InvariantTreament;
import org.asmeta.simulator.readers.InteractiveMFReader;
import org.asmeta.simulator.readers.MonFuncReader;
import org.asmeta.simulator.util.MonitoredFinder;
import org.asmeta.simulator.value.ReserveValue;
import org.asmeta.simulator.value.Value;
import org.eclipse.emf.common.util.EList;

import asmeta.AsmCollection;
import asmeta.definitions.DynamicFunction;
import asmeta.definitions.Function;
import asmeta.structure.FunctionDefinition;

public abstract class Composition {

	// evalbis aggiunta per return multipli
	abstract UpdateSet eval();

	abstract UpdateSet eval(boolean cdb) throws CompositionException;

	abstract UpdateSet eval(boolean cdb, Map<String, String> mon) throws CompositionException;

	abstract void copyMonitored(UpdateSet update);
	
	protected void checkInvariantException(InvalidInvariantException e)
			throws PreconditionViolationException, PostconditionViolationException, InvariantViolationException {
		System.out.println(e.getInvariant());
		EList<Function> constFunList = e.getInvariant().getConstrainedFunction();
		System.out.println(e.getInvariant().getConstrainedFunction());
		boolean isMon= false;
		boolean isOut=false;
		boolean isOther=false;
		for (Function f : constFunList) {
			if (Defs.isMonitored(f))
				isMon=true;
			else if (Defs.isOut(f))
				isOut=true;
			else
				isOther=true;
		}
		if (isMon==true && isOut==false && isOther==false)
			// System.out.println("Precondition violation over " + f.getName());
			throw new PreconditionViolationException("Assumption violation over " + e.getInvariant().getName());
		else if (isMon==false && isOut==true && isOther==false)
			// System.out.println("Postcondition violation over " + f.getName());
			throw new PostconditionViolationException("Guarantee violation over "+ e.getInvariant().getName());
		else
			// System.out.println("Invariant violation over " + f.getName());
			throw new InvariantViolationException("Invariant violation over " + e.getInvariant().getName());
	}
}

abstract class BiComposition extends Composition {
	Composition c1, c2;

	BiComposition(Composition c1, Composition c2) {

		this.c1 = c1;
		this.c2 = c2;
	}

	protected void copyMonitored(UpdateSet up) {
		// System.out.println("copying update set " + up + " in " + c1.toString() + "
		// and in " + c2.toString());
		c1.copyMonitored(up);
		c2.copyMonitored(up);
	}

}

//nuovo codice
abstract class NComposition extends Composition {
	ArrayList<Composition> c;

	NComposition(Composition... c) {
		this.c = new ArrayList<>();
		for (Composition i : c) {
			this.c.add(i);
		}
	}

	protected void copyMonitored(UpdateSet up) {
		for (int i = 0; i < c.size(); i++) {
			c.get(i).copyMonitored(up);
		}
	}
}

class MFReaderWithSettableMon extends MonFuncReader {

	Map<String, Value> monStoredValues = new HashMap<>();

	private InteractiveMFReader interact;

	MFReaderWithSettableMon() {
		interact = new InteractiveMFReader(System.in, System.out);
	}

	@Override
	public Value readValue(Location location, State state) {
		// check if already stored by another execution
		if (monStoredValues.get(location.toString()) != null) {
			// System.out.println(location + " INPUT from another asm");
			return monStoredValues.get(location.toString());
		}
		return interact.read(location, state);
	}

	// add the location set by another asm
	public void add(Entry<Location, Value> l) {
		// System.out.println("storing " + l.getKey() + " = " + l.getValue());
		monStoredValues.put(l.getKey().toString(), l.getValue());
	}

}

// terminal node of the composition
class LeafAsm extends Composition {

	SimulatorRT s1;
	MFReaderWithSettableMon mon;
	String name;
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
	LeafAsm(String asm1, boolean interactive) throws Exception {
		File asm = new File(asm1);
		if (!asm.exists()) {
			throw new RuntimeException("asm file " + asm1 + " not found");
		}
		AsmCollection asc1 = ASMParser.setUpReadAsm(asm);
		name = asc1.getMain().getName(); 
		mon = new MFReaderWithSettableMon(); //FIX by Patrizia 8 nov 2025 mon is always used (in interactive mode or not) for copyMonitored
		if (interactive) {
			//mon = new MFReaderWithSettableMon(); 
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
	UpdateSet eval() {
		System.out.println("Running " + name);// + " current state" + s1.getCurrentState());
		// UpdateSet[]up=new UpdateSet[1];
		UpdateSet up = s1.run(1);
		return up;
	}

	// @Override by Patrizia
	UpdateSet eval(boolean cdb, Map<String, String> locationValue) throws CompositionException {
		// SimulationContainer->AsmetaSservice->InfoAsmetaSservice->SimulatorRT
		// and AsmetaServiceRun to read monitored values from a map
		UpdateSet ups = null;
		try {
			InfoAsmetaService is1 = simulatorMap.get(1);
			// FIX by Patrizia 8 nov 2025: add mon resulting from copyMonitored() in
			// locationValue
			//System.out.println("Copied monnitored locations: "+mon.monStoredValues.toString());
			if (!mon.monStoredValues.isEmpty()) {
				Map<String, String> tmp = new HashMap<>();
				// iterate over mon locations and copy the string version of each location in
				// tmp
				for (Entry<String, Value> pair : mon.monStoredValues.entrySet()) {
					if (pair.getValue() instanceof org.asmeta.simulator.value.StringValue)
						//add "" when the function is string
						tmp.put(pair.getKey(), "\""+ pair.getValue().toString()+"\"");
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
			//Print out locations
			System.out.println("Out locations " + name  + ": " + state.getOutLocs().toString());
			/*for (Entry<Location, Value> pair : state.getOutLocs().entrySet())
				System.out.println(pair.getKey() + " " + pair.getValue().toString());*/
			/*System.out.println("PRINT STATE: " + name );
			for (Entry<Location, Value> pair : state.getLocationMap().entrySet())
				System.out.println(pair.getKey() + " " + pair.getValue().toString());*/
		} catch (InvalidInvariantException e) {
			checkInvariantException(e);
			throw e;
		}

		return ups;
	}

	

	@Override
	UpdateSet eval(boolean cdb) throws CompositionException {
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

/*
 * class Pipe extends BiComposition {
 * 
 * Pipe(Composition asm1, Composition asm2) throws Exception { super(asm1,
 * asm2); }
 * 
 * @Override UpdateSet eval() { // run della prima UpdateSet up[0] = c1.eval();
 * // copio i riulstati c2.copyMonitored(up[0]); // eseguo s2 c2.eval(); return
 * up; }
 * 
 * @Override public String toString() { return c1.toString() + "|" +
 * c2.toString(); } }
 */
//new code |
class PipeN extends NComposition {
	// we don't know in advance the number of arguments
	// so we use varargs
	PipeN(Composition... asm) throws Exception {
		super(asm);
	}

	@Override // probabilmente giusto
	UpdateSet eval() {
		UpdateSet up = c.get(0).eval();
		// for starts from 2nd pipe element
		for (int i = 1; i < c.size(); i++) {
			c.get(i).copyMonitored(up);
			up = c.get(i).eval();
		}
		c.get(c.size() - 1).copyMonitored(up);// così update corretto
		return up;
	}

	@Override
	UpdateSet eval(boolean cdb) throws CompositionException {
		return evalByCheckingContract(cdb, null);
	}

	@Override
	UpdateSet eval(boolean cdb, Map<String, String> mon) throws CompositionException {
		return evalByCheckingContract(cdb, mon);

	}

	private UpdateSet evalByCheckingContract(boolean cdb, Map<String, String> mon) throws CompositionException {
		UpdateSet up = null;
		if (cdb) {
			try {
				if (mon == null) {
					up = c.get(0).eval(true);
					for (int i = 1; i < c.size(); i++) {
						c.get(i).copyMonitored(up);
						up = c.get(i).eval(true);
					}
					c.get(c.size() - 1).copyMonitored(up);
				} else {
					up = c.get(0).eval(true, mon);
					for (int i = 1; i < c.size(); i++) {
						c.get(i).copyMonitored(up);
						up = c.get(i).eval(true, mon);
					}
					c.get(c.size() - 1).copyMonitored(up);
				}

			} catch (InvalidInvariantException e) {
				checkInvariantException(e);
			}
		} else {
			up = eval();
		}
		return up;
	}

	@Override
	public String toString() {
		String string = c.get(0).toString();
		// for starts from 2nd pipe element
		for (int i = 1; i < c.size(); i++) {
			string = " ( "+string + " | " + c.get(i).toString()+" ) ";
		}
		return string;
	}

}

//<|>
class BiPipeHalfDup extends BiComposition {

	BiPipeHalfDup(Composition asm1, Composition asm2) throws Exception {
		super(asm1, asm2);
	}

	@Override
	UpdateSet eval() {
		// run first node
		UpdateSet up = c1.eval();
		// result copied in the second node
		c2.copyMonitored(up);
		// run second node
		up = c2.eval();
		// dalla seconda alla prima
		c1.copyMonitored(up);
		// result copied in the first node
		return up;
	}

	@Override
	public String toString() {
		return " ( "+c1.toString() + " <|> " + c2.toString()+" ) ";
	}

	
	
	@Override
	UpdateSet eval(boolean cdb) throws CompositionException {
		return evalByCheckingContract(cdb, null);
	}

	@Override
	UpdateSet eval(boolean cdb, Map<String, String> mon) throws CompositionException {
		return evalByCheckingContract(cdb, mon);

	}

	private UpdateSet evalByCheckingContract(boolean cdb, Map<String, String> mon) throws CompositionException {
		UpdateSet up = null;
		if (cdb) {
			try {
				if (mon == null) {
					up = c1.eval(true);
					c2.copyMonitored(up);
					up = c2.eval(true);
					c1.copyMonitored(up);
				}
				else {
					up = c1.eval(true,mon);
					c2.copyMonitored(up);
					up = c2.eval(true,mon);
					c1.copyMonitored(up);
				}
		}
		catch (InvalidInvariantException e) {
			checkInvariantException(e);
		}
			
		} else {
			up = eval();
		}
		return up;// if cdb check inv else eval(); return eval();
	
	}
}

//inserisci nella parte finale tesi come possibilità di nuove implementazioni
//<|>
/*
 * class NPipeHalfDup extends NComposition {
 * 
 * NPipeHalfDup(Composition...asm) throws Exception { super(asm); }
 * 
 * @Override UpdateSet eval() { UpdateSet up=c.get(0).eval(); //andata for(int
 * i=1; i<c.size(); i++) { c.get(i).copyMonitored(up); up=c.get(i).eval(); }
 * //ritorno for(int i=c.size()-2; i>0; i--) { c.get(i).copyMonitored(up);
 * up=c.get(i).eval(); } c.get(0).copyMonitored(up);
 * 
 * return up; }
 * 
 * @Override public String toString() { String stringa=c.get(0).toString();
 * for(int i=1; i<c.size(); i++) { stringa=stringa+"<|>"+c.get(i); } return
 * stringa; } }
 */

//<||>
class BiPipeFullDup extends BiComposition {

	BiPipeFullDup(Composition asm1, Composition asm2) throws Exception {
		super(asm1, asm2);
	}

	@Override
	UpdateSet eval() {
		// double eval to simulate parallel

		UpdateSet up1 = c1.eval();
		// COPIO LE MONITORATE ACQUISITE PER C1 IN C2
		// POI FA STEP C1 E STEP C2
		UpdateSet up2 = c2.eval();
		c2.copyMonitored(up1);
		c1.copyMonitored(up2);
		// results union
		up1.unionOnlyOutFun(up2);
		return up1;
	}

	@Override
	public String toString() {
		return " ( "+c1.toString() + "<||>" + c2.toString()+" ) ";
		
	}

	@Override
	UpdateSet eval(boolean cdb) throws CompositionException {
		return evalByCheckingContract(cdb, null);
	}

	@Override
	UpdateSet eval(boolean cdb, Map<String, String> mon) throws CompositionException {
		return evalByCheckingContract(cdb, mon);
	}

	private UpdateSet evalByCheckingContract(boolean cdb, Map<String, String> mon) throws CompositionException {
		UpdateSet up1 = null;
		UpdateSet up2 = null;
		if (cdb) {
			try {
				if (mon == null) {
					up1 = c1.eval(true);
					up2 = c2.eval(true);
					c2.copyMonitored(up1);
					c1.copyMonitored(up2);
					up1.unionOnlyOutFun(up2);
				}
				else {
					up1 = c1.eval(true,mon);
					up2 = c2.eval(true,mon);
					c2.copyMonitored(up1);
					c1.copyMonitored(up2);
					up1.unionOnlyOutFun(up2);
				}
		}
		catch (InvalidInvariantException e) {
			checkInvariantException(e);
		}
			
		} else {
			up1 = eval();
		}
		return up1;// if cdb check inv else eval(); return eval();
	
	}
}

/*
 * class Par extends BiComposition {
 * 
 * Par(Composition asm1, Composition asm2) throws Exception { super(asm1, asm2);
 * }
 * 
 * @Override UpdateSet eval() { // run della prima UpdateSet up1 = c1.eval();
 * UpdateSet up2 = c2.eval(); up1.union(up2);
 * System.out.println(up1.toString()); return up1;//up1 unione up2; }
 * 
 * @Override public String toString() { return c1.toString() + "||" +
 * c2.toString(); }
 * 
 * }
 */

//nuovo codice
class ParN extends NComposition {
	ParN(Composition... asm) throws Exception {
		super(asm);
	}

	@Override
	UpdateSet eval() {
		UpdateSet up = c.get(0).eval();
		for (int i = 1; i < c.size(); i++) {
			UpdateSet tempUp = c.get(i).eval();
			up.unionOnlyOutFun(tempUp);
		}
		c.get(c.size() - 1).copyMonitored(up);
		// System.out.println(up.toString());
		return up;
	}

	@Override
	UpdateSet eval(boolean cdb) throws CompositionException {
		return evalByCheckingContract(cdb, null);
		
	}

	@Override
	public String toString() {
		String stringa = c.get(0).toString();
		for (int i = 1; i < c.size(); i++) {
			stringa = " ( "+stringa + " || " + c.get(i)+" ) ";
		}
		return stringa;
	}

	@Override
	UpdateSet eval(boolean cdb, Map<String, String> mon) throws CompositionException {
		return evalByCheckingContract(cdb, mon);
	}

	private UpdateSet evalByCheckingContract(boolean cdb, Map<String, String> mon) throws CompositionException {
		UpdateSet up = null;
		if (cdb) {
			try {
				if (mon == null) {
					up = c.get(0).eval(true);
					for (int i = 1; i < c.size(); i++) {
						UpdateSet tempUp = c.get(i).eval(true);
						up.unionOnlyOutFun(tempUp);
					}
					c.get(c.size() - 1).copyMonitored(up);
				}
				else {
					up = c.get(0).eval(true,mon);
					for (int i = 1; i < c.size(); i++) {
						UpdateSet tempUp = c.get(i).eval(true,mon);
						up.unionOnlyOutFun(tempUp);
					}
					c.get(c.size() - 1).copyMonitored(up);
				}
		}
		catch (InvalidInvariantException e) {
			checkInvariantException(e);
		}
			
		} else {
			up = eval();
		}
		return up;// if cdb check inv else eval(); return eval();
	
	}
}
