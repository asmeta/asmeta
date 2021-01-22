package org.asmeta.runtime_simulator;

import java.util.HashMap;
import java.util.Map;
import org.asmeta.animator.MyState;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.readers.InteractiveMFReader;
import org.asmeta.simulator.util.InputMismatchException;
import org.asmeta.simulator.util.Parser;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.EnumValue;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.RealValue;
import org.asmeta.simulator.value.ReserveValue;
import org.asmeta.simulator.value.SequenceValue;
import org.asmeta.simulator.value.SetValue;
import org.asmeta.simulator.value.StringValue;
import org.asmeta.simulator.value.TupleValue;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.Function;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.definitions.domains.StringDomain;
import asmeta.definitions.domains.UndefDomain;


/**
 * 
 * @author Simone Giusso, Patrizia Scandurra
 * Create by AsmetaSservice for run Simulator. This class is also used for create environment (see start method of AsmetaSservice).
 */

public class AsmetaSserviceRunNotSing extends InteractiveMFReader{
	
	private static int id;	//Simulator's id given by AsmetaSservice
	private String locationToFind; //Location to find in list, set by readValue
	private static Map<Location, Value> monitored; //I must save here the monitored function thanks to readValue because after run the monitored are deleted. It's static because extend.
	private Map<Integer, InfoAsmetaService> simulatorMap;	//Map id -> created instance of the simulator (see InfoAsmetaService)
	
	public AsmetaSserviceRunNotSing(int id, Map<Integer, InfoAsmetaService> simulatorMap) {
		super(System.in, System.out);
		this.id = id;
		monitored = new HashMap<Location, Value>();
		this.simulatorMap = simulatorMap;
	}
	
	public AsmetaSserviceRunNotSing( Map<Integer,InfoAsmetaService> simulatorMap) {
		super(System.in, System.out);
		this.simulatorMap = simulatorMap;
	}
	
	public void run(RunMode mode) {
		SimulatorRT sim = simulatorMap.get(id).getSim();
		
		
		if(mode == RunMode.RUN_ONE_STEP)
			sim.run(1);
		else if(mode == RunMode.RUN_UNTIL_EMPTY)
			sim.runUntilEmpty();
		
		state = sim.getCurrentState(); 
		
		State previousState = sim.previousState;
		
		//Set previous state
		simulatorMap.get(id).setPreviousState(new MyState(previousState.getContrLocs(false), null));
		//System.out.println("\nPatrizia: monitored locs in the current state: "+state.getMonLocsState().toString());
		
		//Update current State
		simulatorMap.get(id).setState(new MyState(state.getContrLocs(false), monitored));
		//System.out.println("\nPatrizia: monitored locs in the current state: "+state.getMonLocsState().toString());
		//System.out.println("\nPatrizia: monitored locs in the current state (as re-built): "+monitored.toString());
	}
	
	/**
	 * Convert string value only for monitored functions.
	 * In this method I must save location and value of the monitored function because in the simulator this value is deleted
	 */
	@Override
	public Value readValue(Location location, State state) {
		Function func = location.getSignature(); //e.g.: isPillMissed; location.getElements() e.g. (compartment2)
		locationToFind = location.toString(); //e.g.: isPillMissed(compartment2)
		//System.out.println("Patrizia func "+func.getName()+" codomain: "+func.getCodomain().getName() +" locationToFind: " + locationToFind);
		Value value =  visit(func.getCodomain());
		//System.out.println("Patrizia value found: "+ value.toString());
		monitored.put(location, value);
		//System.out.println("Patrizia monitored: "+monitored.toString()+"\n");
		return value;
	}
	//Not overriden version of readValue:
	//Function func = location.getSignature();
	//out.println("Insert a " + domainPrinter.visit(func.getCodomain()) + " for " + location.toString() + ":");
	//return visit(func.getCodomain());
	
	/**
	 * Set the value of location only for monitored
	 */
	//Patrizia 2021: il metodo si occupa di settare il valore di una locazione monitorata. 
	//L'override di questo metodo è necessario perchè di default l'input stream è la console. In questo caso invece la
	//funzione monitorata viene già acquisita dalla mappa dell'input fornito dall'utente in AsmetaSservice
	@Override
	public void readLine() {
		Map<String, String> map = simulatorMap.get(id).getLocationValue();
		//System.out.println("\nPatrizia User input map: "+map.toString());
		for(Map.Entry<String, String> m: map.entrySet()) {	//Find the value of a particular location in list
			if (m.getKey().equals(locationToFind)) {
				//System.out.println("\nPatrizia set-line: "+m.getValue()+" for function "+ locationToFind);
				setLine(m.getValue());
				return;	//I Suppose that I found it
			}
		}
		//System.out.println("\nPatrizia line for function "+ locationToFind + " not found!");
		//E quindi la linea di stream da cui leggere resta settata al valore precedente.
	}
	
	@Override
	public IntegerValue visit(IntegerDomain domain) throws InputMismatchException {
		IntegerValue value = null;
		readLine();
		value = new Parser(getLine()).visit(domain);


		return value;
	}

	@Override
	public RealValue visit(RealDomain domain) throws InputMismatchException {
		RealValue value = null;

		readLine();
		value = new Parser(getLine()).visit(domain);

		return value;
	}

	@Override
	public BooleanValue visit(BooleanDomain domain) throws InputMismatchException {
		BooleanValue value = null;

		readLine();
		value = new Parser(getLine()).visit(domain);
		
		return value;
	}

	@Override
	public UndefValue visit(UndefDomain domain) throws InputMismatchException {
		UndefValue value = null;
		getOut().println("Insert an undef constant:");
		
		readLine();
		value = new Parser(getLine()).visit(domain);
		
		return value;
	}

	@Override
	public StringValue visit(StringDomain domain) throws InputMismatchException {
		StringValue value = null;
		
		readLine();
		value = new Parser(getLine()).visit(domain);
		
		return value;
	}

	@Override
	public TupleValue visit(ProductDomain domain) throws InputMismatchException {
		TupleValue value = null;

		readLine();
		value = new Parser(getLine()).visit(domain);
		
		return value;
	}

	@Override
	public SetValue visit(PowersetDomain domain) throws InputMismatchException {
		SetValue value = null;
		getOut().println("Insert a set:");

		readLine();
		value = new Parser(getLine()).visit(domain);
		
		return value;
	}

	@Override
	public SequenceValue visit(SequenceDomain domain) throws InputMismatchException {
		SequenceValue value = null;
		
		readLine();
		value = new Parser(getLine()).visit(domain);
		
		return value;
	}

	@Override
	public ReserveValue visit(AbstractTd domain) throws InputMismatchException {
		ReserveValue value = null;

		readLine();
		value = new Parser(getLine()).visit(domain);
	
		if (!checkAbstract(domain, value)) {
			throw new InputMismatchException("The constant " + value + " doesn't belong to concrete domain " + domain.getName());
		}

		return value;
	}

	@Override
	public EnumValue visit(EnumTd domain) throws InputMismatchException {
		EnumValue value = null;
		

		readLine();
		value = new Parser(getLine()).visit(domain);

		if (!checkEnum(domain, value)) {
			throw new InputMismatchException("The constant " + value + " doesn't belong to concrete domain " + domain.getName());
		}

		return value;
	}

	@Override
	public Value visit(ConcreteDomain domain) throws InputMismatchException {
		Value value = null;

		readLine();
		value = new Parser(getLine()).visit(domain);

		if (!checkConcrete(domain, value)) {
			throw new InputMismatchException("The constant " + value + " doesn't belong to concrete domain " + domain.getName());
		}
			
		return value;
	}

}
