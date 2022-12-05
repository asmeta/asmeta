package temp;

import java.io.File;
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

	abstract UpdateSet eval();

	abstract void copyMonitored(UpdateSet u);

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
	ArrayList<Composition> c=new ArrayList<>();
	
	NComposition(Composition...c)
	{
		for(Composition i:c)
		{
			this.c.add(i);
		}
	}
	
	protected void copyMonitored(UpdateSet up){
		while(c.iterator().hasNext())
		{
			c.iterator().next().copyMonitored(up);
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
		return s1.run(1);
	}

	// dall'update set copia in s2
	protected void copyMonitored(UpdateSet up) {
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

class Pipe extends BiComposition {

	Pipe(Composition asm1, Composition asm2) throws Exception {
		super(asm1, asm2);
	}

	@Override
	UpdateSet eval() {
		// run della prima
		UpdateSet up = c1.eval();
		// copio i riulstati
		c2.copyMonitored(up);
		// eseguo s2
		c2.eval();
		return up;
	}

	@Override
	public String toString() {
		return c1.toString() + "|" + c2.toString();
	}
}

//nuovo Codice
class PipeN extends NComposition {
	PipeN(Composition...asm) throws Exception{
		super(asm);
	}

	@Override
	UpdateSet eval() {
		UpdateSet up =c.get(0).eval();
		//for parte da 2° elemento pipe
		for(int i=1; i<c.size(); i++)
		{
			c.get(i).copyMonitored(up);
			up=c.get(i).eval();	
		}
		return up;
	}
	
	@Override
	public String toString() {
		String string=c.get(0).toString();
		//for parte da secondo elemento in parallelo
		for(int i=1; i<c.size(); i++)
		{
			string=string+"|"+c.get(i).toString();
		}
		return string;
	}
}

class BiPipeHalfDup extends BiComposition {

	BiPipe(Composition asm1, Composition asm2) throws Exception {
		super(asm1, asm2);
	}

	@Override
	UpdateSet eval() {
		// run della prima
		UpdateSet up = c1.eval();
		// copio i riulstati
		c2.copyMonitored(up);
		// eseguo s2
		up = c2.eval();
		// dalla seconda alla prima
		c1.copyMonitored(up);
		return up;
	}

	@Override
	public String toString() {
		return c1.toString() + "<|>" + c2.toString();
	}
}

//nuovo codice <||>
class BiPipeFullDup extends BiComposition {

	BiPipeFullDup(Composition asm1, Composition asm2) throws Exception {
		super(asm1, asm2);
	}
	
	UpdateSet[] evalbis() {
		UpdateSet[]up=new UpdateSet[2];
		
		//full duplex pag. 9 CompositionalSimulationConfPaper
		// 1° run
		up[0] = c1.eval();
		up[1] = c2.eval();
		
		// copio i riusltati
		c2.copyMonitored(up[0]);
		c1.copyMonitored(up[1]);
		//UpdateSet ris=eval(up[0],c2)+(up[1],c1);
		
		return up;
	}
	
	@Override
	public String toString() {
		return c1.toString() + "<||>" + c2.toString();
	}

	//metodo non utilizzato
	@Override
	UpdateSet eval() {
		// TODO Auto-generated method stub
		return null;
	}
}

class Par extends BiComposition {

	Par(Composition asm1, Composition asm2) throws Exception {
		super(asm1, asm2);
	}

	@Override
	UpdateSet eval() {
		// run della prima
		UpdateSet up1 = c1.eval();
		UpdateSet up2 = c2.eval();
		up1.union(up2);
		System.out.println(up1.toString());
		return up1;//up1 unione up2;
	}
	
	@Override
	public String toString() {
		return c1.toString() + "||" + c2.toString();
	}

}

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
			UpdateSet upAppo=c.get(i).eval();
			up.union(upAppo);
		}
		System.out.println(up.toString());
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
