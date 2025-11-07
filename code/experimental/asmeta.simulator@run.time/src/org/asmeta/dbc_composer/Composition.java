package org.asmeta.dbc_composer;

import java.io.File;
import org.asmeta.runtime_container.SimulationContainer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.main.Simulator.InvariantTreament;
import org.asmeta.simulator.readers.InteractiveMFReader;
import org.asmeta.simulator.readers.MonFuncReader;
import org.asmeta.simulator.value.Value;
import org.eclipse.emf.common.util.EList;

import asmeta.AsmCollection;
import asmeta.definitions.DynamicFunction;
import asmeta.definitions.Function;
import asmeta.structure.FunctionDefinition;

public abstract class Composition {

	// evalbis aggiunta per return multipli
	abstract UpdateSet eval();

	abstract UpdateSet eval(boolean dbc) throws CompositionException;

	abstract void copyMonitored(UpdateSet update);
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

	// Map<Location,Value> monStoredValues = new HashMap<>();
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

	//SimulatorRT s1; FIX 31/10/2025 BY PATRIZIA AND SILVIA
	//AsmetaSserviceRun???? con id=1
	SimulatorRT s1;
	MFReaderWithSettableMon mon;
	String name;
	Map<Integer, InfoAsmetaService> simulatorMap; //NEW
	AsmetaSserviceRun runner; //NEW

	/* Original: monitored functions are handled in an interactive way by CLI
	LeafAsm(String asm1) throws Exception {
		mon = new MFReaderWithSettableMon();
		Environment env = new Environment(mon);
		AsmCollection asc1 = ASMParser.setUpReadAsm(new File(asm1));
		name = asc1.getMain().getName();
		s1 = new SimulatorRT(name, asc1, env);	
	}
	*/
	
	//NEW tentative to allow feeding of monitored functions from a map by using InfoAsmetaService e AsmetaSserviceRun
	LeafAsm(String asm1) throws Exception {
	   //mon = new MFReaderWithSettableMon();
	   //Environment env = new Environment(mon); //Like in the original version
		
		simulatorMap = new HashMap<Integer, InfoAsmetaService>();//NEW
		AsmCollection asc1 = ASMParser.setUpReadAsm(new File(asm1));
		name = asc1.getMain().getName();
		runner = new AsmetaSserviceRun(1,simulatorMap);
		Environment env = new Environment(runner); //NEW 
		s1 = new SimulatorRT(name, asc1, env);	
		s1.setShuffleFlag(true); //to allow non-deterministic behavior of choose rule
		InfoAsmetaService is1 = new InfoAsmetaService(s1);
		simulatorMap.put(1,is1);
	
	}
	
	@Override
	UpdateSet eval() {
		System.out.println("Running " + name);// + " current state" + s1.getCurrentState());
		// UpdateSet[]up=new UpdateSet[1];
		UpdateSet up = s1.run(1);
		return up;
	}

	//@Override
	UpdateSet eval(boolean dbc, Map<String, String> locationValue) throws CompositionException {
		
		//SimulationContainer->AsmetaSservice->InfoAsmetaSservice->SimulatorRT
		//and AsmetaServiceRun that wraps SimulatorRT
		
		//InfoAsmetaService is1 = new InfoAsmetaService(s1);
		//is1.setLocationValue(locationValue);
		//simulatorMap.put(1,is1);				
		InfoAsmetaService is1 = simulatorMap.get(1);
		is1.setLocationValue(locationValue);
		System.out.println(locationValue.toString());
		//AsmetaSserviceRun runner = new AsmetaSserviceRun(1,simulatorMap);
		runner.run(RunMode.RUN_ONE_STEP); //run one step
		is1.incContSim(); //increase counter of simulation steps
		//MyState state = is1.getState();
		//Come passare da Mystate a UpdateSet?
		State state = is1.getSim().getCurrentState();
		Map<Location,Value> map = state.getLocationMap(); // return the current location map
		System.out.println(locationValue.toString());
		UpdateSet ups = new UpdateSet(); //tricky: create an UpdateSet containing the location set of s1
		ups.applyLocationUpdates(map);
		System.out.println(ups.toString());

		return ups; 
	}
	
	@Override
	UpdateSet eval(boolean dbc) throws CompositionException {
		System.out.println("Running " + name);// + " current state" + s1.getCurrentState());
		UpdateSet up = null;
		if (dbc) {
			try {
				up = s1.run(1);
			}
			// try {up=s1.runUntil(s1,1,InvariantTreament.CHECK_CONTINUE).updateSet;} TODO
			// continue when invariant violation
			catch (InvalidInvariantException e) {
				System.out.println(e.getInvariant());
				EList<Function> constFunList = e.getInvariant().getConstrainedFunction();
				System.out.println(e.getInvariant().getConstrainedFunction());
				for (Function f : constFunList) {
					if (Defs.isMonitored(f))
						// System.out.println("Precondition violation over " + f.getName());
						throw new PreconditionViolationException("Precondition violation over " + f.getName());
					else if (Defs.isOut(f))
						// System.out.println("Postcondition violation over " + f.getName());
						throw new PostconditionViolationException("Postcondition violation over " + f.getName());
					else
						// System.out.println("Invariant violation over " + f.getName());
						throw new InvariantViolationException("Invariant violation over " + f.getName());
				}
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
	UpdateSet eval(boolean dbc) throws CompositionException {
		UpdateSet up = null;
		if (dbc) {
			try {
				up = c.get(0).eval(true);
				for (int i = 1; i < c.size(); i++) {
					c.get(i).copyMonitored(up);
					up = c.get(i).eval(true);
				}
				c.get(c.size() - 1).copyMonitored(up);
			} catch (InvalidInvariantException e) {
				System.out.println(e.getInvariant());
				EList<Function> constFunList = e.getInvariant().getConstrainedFunction();
				System.out.println(e.getInvariant().getConstrainedFunction());
				for (Function f : constFunList) {
					if (Defs.isMonitored(f))
						// System.out.println("Precondition violation over " + f.getName());
						throw new PreconditionViolationException("Precondition violation over " + f.getName());
					else if (Defs.isOut(f))
						// System.out.println("Postcondition violation over " + f.getName());
						throw new PostconditionViolationException("Postcondition violation over " + f.getName());
					else
						// System.out.println("Invariant violation over " + f.getName());
						throw new InvariantViolationException("Invariant violation over " + f.getName());
				}
			}
		} else {
			up = eval();
		}
		return up;// if dbc check inv else eval(); return eval();
	}

	@Override
	public String toString() {
		String string = c.get(0).toString();
		// for starts from 2nd pipe element
		for (int i = 1; i < c.size(); i++) {
			string = string + "|" + c.get(i).toString();
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
		return c1.toString() + "<|>" + c2.toString();
	}

	@Override
	UpdateSet eval(boolean dbc) throws CompositionException {
		UpdateSet up = null;
		if (dbc) {
			try {
				up = c1.eval(true);
				c2.copyMonitored(up);
				up = c2.eval(true);
				c1.copyMonitored(up);
			} catch (InvalidInvariantException e) {
				System.out.println(e.getInvariant());
				EList<Function> constFunList = e.getInvariant().getConstrainedFunction();
				System.out.println(e.getInvariant().getConstrainedFunction());
				for (Function f : constFunList) {
					if (Defs.isMonitored(f))
						// System.out.println("Precondition violation over " + f.getName());
						throw new PreconditionViolationException("Precondition violation over " + f.getName());
					else if (Defs.isOut(f))
						// System.out.println("Postcondition violation over " + f.getName());
						throw new PostconditionViolationException("Postcondition violation over " + f.getName());
					else
						// System.out.println("Invariant violation over " + f.getName());
						throw new InvariantViolationException("Invariant violation over " + f.getName());
				}
			}
		} else {
			up = eval();
		}
		return up;// if dbc check inv else eval(); return eval();
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

//nuovo codice <||>
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
		up1.union(up2);
		return up1;
	}

	@Override
	public String toString() {
		return c1.toString() + "<||>" + c2.toString();
	}

	@Override
	UpdateSet eval(boolean dbc) throws CompositionException {
		UpdateSet up1 = null;
		UpdateSet up2 = null;
		if (dbc) {
			try {
				up1 = c1.eval(true);
				up2 = c2.eval(true);
				c2.copyMonitored(up1);
				c1.copyMonitored(up2);
				up1.union(up2);
			} catch (InvalidInvariantException e) {
				System.out.println(e.getInvariant());
				EList<Function> constFunList = e.getInvariant().getConstrainedFunction();
				System.out.println(e.getInvariant().getConstrainedFunction());
				for (Function f : constFunList) {
					if (Defs.isMonitored(f))
						// System.out.println("Precondition violation over " + f.getName());
						throw new PreconditionViolationException("Precondition violation over " + f.getName());
					else if (Defs.isOut(f))
						// System.out.println("Postcondition violation over " + f.getName());
						throw new PostconditionViolationException("Postcondition violation over " + f.getName());
					else
						// System.out.println("Invariant violation over " + f.getName());
						throw new InvariantViolationException("Invariant violation over " + f.getName());
				}
			}
		} else {
			up1 = eval();
		}
		return up1;// if dbc check inv else eval(); return eval();
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
			up.union(tempUp);
		}
		c.get(c.size() - 1).copyMonitored(up);
		// System.out.println(up.toString());
		return up;
	}
	
	@Override
	UpdateSet eval(boolean dbc) throws CompositionException {
		UpdateSet up = null;
		if (dbc) {
			try {
				up = c.get(0).eval(true);
				for (int i = 1; i < c.size(); i++) {
					UpdateSet tempUp = c.get(i).eval(true);
					up.union(tempUp);
				}
				c.get(c.size() - 1).copyMonitored(up);
			} catch (InvalidInvariantException e) {
				System.out.println(e.getInvariant());
				EList<Function> constFunList = e.getInvariant().getConstrainedFunction();
				System.out.println(e.getInvariant().getConstrainedFunction());
				for (Function f : constFunList) {
					if (Defs.isMonitored(f))
						// System.out.println("Precondition violation over " + f.getName());
						throw new PreconditionViolationException("Precondition violation over " + f.getName());
					else if (Defs.isOut(f))
						// System.out.println("Postcondition violation over " + f.getName());
						throw new PostconditionViolationException("Postcondition violation over " + f.getName());
					else
						// System.out.println("Invariant violation over " + f.getName());
						throw new InvariantViolationException("Invariant violation over " + f.getName());
				}
			}
		} else {
			up = eval();
		}
		return up;// if dbc check inv else eval(); return eval();
	}

	
	@Override
	public String toString() {
		String stringa = c.get(0).toString();
		for (int i = 1; i < c.size(); i++) {
			stringa = stringa + "||" + c.get(i);
		}
		return stringa;
	}

}
