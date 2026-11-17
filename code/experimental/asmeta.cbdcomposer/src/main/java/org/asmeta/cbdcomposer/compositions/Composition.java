package org.asmeta.cbdcomposer.compositions;

import org.asmeta.runtime_container.SimulationContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.asmeta.animator.MyState;
import org.asmeta.cbdcomposer.CompositionException;
import org.asmeta.cbdcomposer.InvariantViolationException;
import org.asmeta.cbdcomposer.PostconditionViolationException;
import org.asmeta.cbdcomposer.PreconditionViolationException;
import org.asmeta.parser.util.Defs;
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
import org.asmeta.simulator.value.ReserveValue;
import org.asmeta.simulator.value.Value;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.DynamicFunction;
import asmeta.definitions.Function;
import asmeta.structure.FunctionDefinition;

public abstract class Composition {

	// evalbis aggiunta per return multipli
	public abstract UpdateSet eval();

	public abstract UpdateSet eval(boolean cdb) throws CompositionException;

	public abstract UpdateSet eval(boolean cdb, Map<String, String> mon) throws CompositionException;

	abstract void copyMonitored(UpdateSet update);

	protected void checkInvariantException(InvalidInvariantException e)
			throws PreconditionViolationException, PostconditionViolationException, InvariantViolationException {
		System.out.println(e.getInvariant());
		EList<Function> constFunList = e.getInvariant().getConstrainedFunction();
		System.out.println(e.getInvariant().getConstrainedFunction());
		boolean isMon = false;
		boolean isOut = false;
		boolean isOther = false;
		for (Function f : constFunList) {
			if (Defs.isMonitored(f))
				isMon = true;
			else if (Defs.isOut(f))
				isOut = true;
			else
				isOther = true;
		}
		if (isMon == true && isOut == false && isOther == false)
			// System.out.println("Precondition violation over " + f.getName());
			throw new PreconditionViolationException("Assumption violation over " + e.getInvariant().getName());
		else if (isMon == false && isOut == true && isOther == false)
			// System.out.println("Postcondition violation over " + f.getName());
			throw new PostconditionViolationException("Guarantee violation over " + e.getInvariant().getName());
		else
			// System.out.println("Invariant violation over " + f.getName());
			throw new InvariantViolationException("Invariant violation over " + e.getInvariant().getName());
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
