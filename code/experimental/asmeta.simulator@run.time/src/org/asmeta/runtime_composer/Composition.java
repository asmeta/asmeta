package org.asmeta.runtime_composer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.asmeta.parser.ASMParser;
import org.asmeta.runtime_simulator.SimulatorRT;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.readers.InteractiveMFReader;
import org.asmeta.simulator.readers.MonFuncReader;
import org.asmeta.simulator.value.Value;

import asmeta.AsmCollection;
import asmeta.structure.FunctionDefinition;

abstract public class Composition {

	public abstract UpdateSet eval();

	public abstract void copyMonitored(UpdateSet update);
}

abstract class BiComposition extends Composition {
	Composition c1, c2;

	BiComposition(Composition c1, Composition c2) {
		this.c1 = c1;
		this.c2 = c2;
	}

	public void copyMonitored(UpdateSet up) {
		// System.out.println("copying update set " + up + " in " + c1.toString() + "
		// and in " + c2.toString());
		c1.copyMonitored(up);
		c2.copyMonitored(up);
		
	}

}

//nuovo codice
abstract class NComposition extends Composition {
	ArrayList<Composition> c;
	
	NComposition(Composition...c)
	{
		this.c=new ArrayList<>();
		for(Composition i:c)
		{
			this.c.add(i);
		}
	}
	
	public void copyMonitored(UpdateSet up){
		for(int i=0; i<c.size(); i++)
		{
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

