// testDefinition2.java automatically generated from ASM2CODE

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.Bag;
import java.util.concurrent.ThreadLocalRandom;
import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Octet;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

abstract class testDefinition2_sig {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo astratto
	static class Lift {

		static List<Lift> elems = new ArrayList<Lift>();
		static List<String> val = new ArrayList<String>();

		Lift(String a) {
			elems.add(this);
			val.add(a);
		}

		String ToString(Lift a) {
			if (elems.contains(a)) {
				return val.get(elems.lastIndexOf(a));
			}

			else
				return null;
		}

		static Lift get(String a) {
			if (val.contains(a)) {

				return elems.get(val.lastIndexOf(a));
			}

			else
				return null;
		}

	}

	List<String> Lift_lista = new ArrayList<String>();
	List<Lift> Lift_Class = new ArrayList<Lift>();

	//Variabile di tipo Concreto o Enumerativo

	static enum Actors {
		FERRYMAN, GOAT, CABBAGE, WOLF
	}

	List<Actors> Actors_lista = new ArrayList<Actors>();

	//Variabile di tipo Concreto o Enumerativo

	static enum Side {
		LEFT, RIGHT
	}

	List<Side> Side_lista = new ArrayList<Side>();

	//Variabile di tipo Concreto o Enumerativo

	static enum Product {
		COFFEE, TEA, MILK
	}

	List<Product> Product_lista = new ArrayList<Product>();

	//Variabile di tipo Concreto o Enumerativo

	static class QuantityDomain {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	QuantityDomain QuantityDomain_elem = new QuantityDomain();

	List<Integer> QuantityDomain_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static class CoinDomain {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	CoinDomain CoinDomain_elem = new CoinDomain();

	List<Integer> CoinDomain_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static class State {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	State State_elem = new State();

	List<Integer> State_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static class RowsWorld {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	RowsWorld RowsWorld_elem = new RowsWorld();

	List<Integer> RowsWorld_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static class ColumnsWorld {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	ColumnsWorld ColumnsWorld_elem = new ColumnsWorld();

	List<Integer> ColumnsWorld_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static enum DoorStatus {
		CLOSED, OPENING, OPEN, CLOSING
	}

	List<DoorStatus> DoorStatus_lista = new ArrayList<DoorStatus>();

	//Variabile di tipo Concreto o Enumerativo

	static enum GearStatus {
		RETRACTED, EXTENDING, EXTENDED, RETRACTING
	}

	List<GearStatus> GearStatus_lista = new ArrayList<GearStatus>();

	//Variabile di tipo Concreto o Enumerativo

	static enum Dir {
		UP, DOWN
	}

	List<Dir> Dir_lista = new ArrayList<Dir>();

	//Variabile di tipo Concreto o Enumerativo

	static enum StateS {
		HALTING, MOVING
	}

	List<StateS> StateS_lista = new ArrayList<StateS>();

	//Variabile di tipo Concreto o Enumerativo

	static class Floor {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	Floor Floor_elem = new Floor();

	List<Integer> Floor_elems = new ArrayList<Integer>();

	//Metodi di supporto per l'implementazione delle funzioni controlled

	class zeroC<Domain> {

		Domain oldValue;
		Domain newValue;

		void set(Domain d) {

			newValue = d;
		}

		Domain get() {

			return oldValue;
		}
	}

	static class nC<Domain, Codomain> {

		Map<Domain, Codomain> oldValues = new HashMap<>();
		Map<Domain, Codomain> newValues = new HashMap<>();

		void set(Domain d, Codomain c) {

			newValues.put(d, c);
		}

		Codomain get(Domain d) {

			return oldValues.get(d);
		}
	}

	//Metodi di supporto per l'implementazione delle funzioni non controlled

	class zero<Domain> {

		Domain Value;

		void set(Domain d) {

			Value = d;
		}

		Domain get() {

			return Value;
		}
	}

	class n<Domain, Codomain> {

		Map<Domain, Codomain> Values = new HashMap<>();

		void set(Domain d, Codomain c) {

			Values.put(d, c);
		}

		Codomain get(Domain d) {

			return Values.get(d);
		}
	}

	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	//Funzione di tipo Controlled
	zeroC<Integer> numA = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Integer> numB = new zeroC<>();

	//Funzione di tipo Controlled
	nC<Product, QuantityDomain> available = new nC<>();

	//Funzione di tipo Controlled
	zeroC<CoinDomain> coins = new zeroC<>();

	//Funzione di tipo Controlled
	nC<Actors, Side> position = new nC<>();

	//Funzione di tipo Controlled
	zeroC<Integer> num_fibo = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Integer> indice = new zeroC<>();

	//Funzione di tipo Controlled
	List<Integer> succ_fibo_elem = new ArrayList<Integer>();

	zeroC<List<Integer> > succ_fibo = new zeroC<>();

	//Funzione di tipo monitored
	zero<Boolean> high = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> low = new zero<>();

	//Funzione di tipo Controlled
	zeroC<State> ctl_state = new zeroC<>();

	//Funzione di tipo Controlled
	Pair<RowsWorld, ColumnsWorld> alive_elem;
	nC<Pair<RowsWorld, ColumnsWorld>, Boolean> alive = new nC<>();

	//Funzione di tipo Controlled
	zeroC<DoorStatus> doors = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<GearStatus> gears = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> generalElectroValve = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> openDoorsElectroValve = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> closeDoorsElectroValve = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> retractGearsElectroValve = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> extendGearsElectroValve = new zeroC<>();

	//Funzione di tipo Controlled
	nC<Lift, Dir> direction = new nC<>();

	//Funzione di tipo Controlled
	nC<Lift, StateS> ctlState = new nC<>();

	//Funzione di tipo Controlled
	nC<Lift, Floor> floor = new nC<>();

	//Funzione di tipo statico
	static Lift lift1;

	//Funzione di tipo statico
	static Lift lift2;

	//Funzione di tipo Controlled
	zeroC<Integer> controlledfunction = new zeroC<>();

	//Funzione di tipo monitored
	zero<Integer> monitoredfunction = new zero<>();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */

	abstract
	void r_Main();

}

class testDefinition2 extends testDefinition2_sig {

	// Inizializzazione di funzioni e domini

	testDefinition2() {

		//Definizione iniziale dei domini statici

		State.elems = Collections.unmodifiableList(Arrays.asList(0, 1));
		State_elems = Collections.unmodifiableList(Arrays.asList(0, 1));
		RowsWorld.elems = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4));
		RowsWorld_elems = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4));
		ColumnsWorld.elems = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4));
		ColumnsWorld_elems = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4));
		//setto la lista di elementi di supporto della classe enumerativa
		for(Actors i : Actors.values())
		Actors_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(Side i : Side.values())
		Side_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(Product i : Product.values())
		Product_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(DoorStatus i : DoorStatus.values())
		DoorStatus_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(GearStatus i : GearStatus.values())
		GearStatus_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(Dir i : Dir.values())
		Dir_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(StateS i : StateS.values())
		StateS_lista.add(i);

		//Definizione iniziale dei domini dinamici

		//Definizione iniziale dei domini astratti con funzini statiche

		lift1 = new Lift("lift1");
		Lift_lista.add("lift1");
		Lift_Class.add(lift1);
		lift2 = new Lift("lift2");
		Lift_lista.add("lift2");
		Lift_Class.add(lift2);

		//Inizializzazione delle funzioni

		CoinDomain_elem.value = 0;

		coins.oldValue = coins.newValue = CoinDomain_elem;

		for(Product _p: Product.values()) {
			QuantityDomain a = new QuantityDomain();

			a.value = 10;
			available.oldValues.put(_p,a);
			available.newValues.put(_p,a);
		}
		numA.oldValue = numA.newValue = 6409;
		numB.oldValue = numB.newValue = 3289;
		for(Actors _a: Actors.values()) {
			Side a = Side.LEFT;

			position.oldValues.put(_a,a);
			position.newValues.put(_a,a);
		}
		indice.oldValue = indice.newValue = 2;
		num_fibo.oldValue = num_fibo.newValue = 0;

		State_elem.value = 0;

		ctl_state.oldValue = ctl_state.newValue = State_elem;

		high.Value = false;
		low.Value = false;
		doors.oldValue = doors.newValue = DoorStatus.CLOSED;
		gears.oldValue = gears.newValue = GearStatus.EXTENDED;
		generalElectroValve.oldValue = generalElectroValve.newValue = false;
		extendGearsElectroValve.oldValue = extendGearsElectroValve.newValue = false;
		retractGearsElectroValve.oldValue = retractGearsElectroValve.newValue = false;
		openDoorsElectroValve.oldValue = openDoorsElectroValve.newValue = false;
		closeDoorsElectroValve.oldValue = closeDoorsElectroValve.newValue = false;
		for(Lift _l: Lift.elems) {
			Floor a = new Floor();

			a.value = 0;
			floor.oldValues.put(_l,a);
			floor.newValues.put(_l,a);
		}
		for(Lift _l: Lift.elems) {
			Dir a = Dir.UP;

			direction.oldValues.put(_l,a);
			direction.newValues.put(_l,a);
		}
		for(Lift _l: Lift.elems) {
			StateS a = StateS.HALTING;

			ctlState.oldValues.put(_l,a);
			ctlState.newValues.put(_l,a);
		}

	}

	// Definizione delle funzioni statiche

	// Conversione delle regole ASM in metodi java

	@Override
	void r_Main() {
		;
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
		controlledfunction.oldValue = controlledfunction.newValue = (monitoredfunction.get() + 1);
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {

		numA.oldValue = numA.newValue;
		numB.oldValue = numB.newValue;
		available.oldValues = available.newValues;
		coins.oldValue = coins.newValue;
		position.oldValues = position.newValues;
		num_fibo.oldValue = num_fibo.newValue;
		indice.oldValue = indice.newValue;
		succ_fibo.oldValue = succ_fibo.newValue;
		ctl_state.oldValue = ctl_state.newValue;
		alive.oldValues = alive.newValues;
		doors.oldValue = doors.newValue;
		gears.oldValue = gears.newValue;
		generalElectroValve.oldValue = generalElectroValve.newValue;
		openDoorsElectroValve.oldValue = openDoorsElectroValve.newValue;
		closeDoorsElectroValve.oldValue = closeDoorsElectroValve.newValue;
		retractGearsElectroValve.oldValue = retractGearsElectroValve.newValue;
		extendGearsElectroValve.oldValue = extendGearsElectroValve.newValue;
		direction.oldValues = direction.newValues;
		ctlState.oldValues = ctlState.newValues;
		floor.oldValues = floor.newValues;
		controlledfunction.oldValue = controlledfunction.newValue;
	}

	//Metodo per l'aggiornamento dell'asm
	void UpdateASM()
	{
		r_Main();
		fireUpdateSet();
		initControlledWithMonitored();
	}

public static void main(String[] args) {
	}

}


