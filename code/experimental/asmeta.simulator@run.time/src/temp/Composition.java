package temp;

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

public abstract class Composition {

	//evalbis aggiunta per return multipli
	abstract UpdateSet eval();

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
	
	NComposition(Composition...c)
	{
		this.c=new ArrayList<>();
		for(Composition i:c)
		{
			this.c.add(i);
		}
	}
	
	protected void copyMonitored(UpdateSet up){
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
class LeafAsm extends Composition {

	SimulatorRT s1;
	MFReaderWithSettableMon mon;
	String name;

	LeafAsm(String asm1) throws Exception {
		mon = new MFReaderWithSettableMon();
		Environment env = new Environment(mon);
		AsmCollection asc1 = ASMParser.setUpReadAsm(new File(asm1));
		name = asc1.getMain().getName();
		s1 = new SimulatorRT(name, asc1, env);
	}

	@Override
	UpdateSet eval() {
		System.out.println("Running " + name);// + " current state" + s1.getCurrentState());
		//UpdateSet[]up=new UpdateSet[1];
		UpdateSet up=s1.run(1);
		return up;
	}

	// copy from updateSet to s2
	protected void copyMonitored(UpdateSet up) {
		// System.out.println("copying " + up + " in " + name);
		//LeafAsm lc = this;
		//String out = "";
		//out += "UpdateSet " + name + "= {";
		for (Entry<Location, Value> l : up) {
			//Location loc = l.getKey();
			//String locName = loc.getSignature().getName();
			// set the mon funtion to the mon func reader
			mon.add(l);
			//out += l + "; ";
		}
		/*if (!up.isEmpty())
			System.out.println(out.substring(0, out.length() - 2) + "}");
		else
			System.out.println(out.substring(0, out.length() - 2)+ "{}");*/
	}

	@Override
	public String toString() { //stampa update set: controlled e out e monitored
		//return "ASM " + name + ": "+ s1.getCurrentState().getOutLocs() + " "+ s1.getCurrentState().+ " "+ s1.getCurrentState().getContrLocs();
		return "ASM " + name + ": "+ s1.getCurrentState().getOutLocs();
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
	//we don't know in advance the number of arguments
	//so we use varargs
	PipeN(Composition...asm) throws Exception{
		super(asm);
	}
	
	@Override //probabilmente giusto
	UpdateSet eval() {
		UpdateSet up =c.get(0).eval();
		//for starts from 2nd pipe element
		for(int i=1; i<c.size(); i++)
		{
			c.get(i).copyMonitored(up);
			up=c.get(i).eval();	
		}
		c.get(c.size()-1).copyMonitored(up);//così update corretto
		return up;
	}
	
	@Override
	public String toString() {
		String string=c.get(0).toString();
		//for starts from 2nd pipe element
		for(int i=1; i<c.size(); i++)
		{
			string=string+"|"+c.get(i).toString();
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
		//double eval to simulate parallel

		UpdateSet up1=c1.eval();
		//TODO: COPIO LE MONITORATE ACQUISITE PER C1 IN C2
		//POI FA STEP C1 E STEP C2
		UpdateSet up2=c2.eval();
		c2.copyMonitored(up1);
		c1.copyMonitored(up2);
		//results union
		up1.union(up2);
		return up1;
	}
	
	@Override
	public String toString() {
		return c1.toString() + "<||>" + c2.toString();
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
class ParN extends NComposition{
	ParN(Composition...asm) throws Exception{
		super(asm);
	}

	@Override
	UpdateSet eval() {
		UpdateSet up=c.get(0).eval();
		for(int i = 1; i<c.size();i++)
		{
			UpdateSet tempUp=c.get(i).eval();
			up.union(tempUp);
		}
		c.get(c.size()-1).copyMonitored(up);
		//System.out.println(up.toString());
		return up;
	}
	
	@Override
	public String toString() {
		String stringa=c.get(0).toString();
		for(int i=1; i<c.size(); i++)
		{
			stringa=stringa+"||"+c.get(i);
		}
		return stringa;
	}
}
