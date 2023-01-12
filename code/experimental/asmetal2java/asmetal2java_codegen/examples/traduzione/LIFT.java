// LIFT.java automatically generated from ASM2CODE

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

abstract class LIFT_sig {

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

	static enum Dir {
		UP, DOWN
	}

	List<Dir> Dir_lista = new ArrayList<Dir>();

	//Variabile di tipo Concreto o Enumerativo

	static class Floor {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	Floor Floor_elem = new Floor();

	List<Integer> Floor_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static enum State {
		HALTING, MOVING
	}

	List<State> State_lista = new ArrayList<State>();

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
	nC<Lift, Dir> direction = new nC<>();

	//Funzione di tipo Controlled
	nC<Lift, State> ctlState = new nC<>();

	//Funzione di tipo Controlled
	nC<Lift, Floor> floor = new nC<>();

	//Funzione di tipo derived
	abstract
	Boolean attracted(Dir param0_attracted, Lift param1_attracted);

	//Funzione di tipo derived
	abstract
	Boolean canContinue(Dir param0_canContinue, Lift param1_canContinue);

	//Funzione di tipo Controlled
	Pair<Lift, Integer> hasToDeliverAt_elem;
	nC<Pair<Lift, Integer>, Boolean> hasToDeliverAt = new nC<>();

	//Funzione di tipo Controlled
	Pair<Integer, Dir> existsCallFromTo_elem;
	nC<Pair<Integer, Dir>, Boolean> existsCallFromTo = new nC<>();

	//Funzione di tipo monitored
	Pair<Lift, Integer> insideCall_elem;
	n<Pair<Lift, Integer>, Boolean> insideCall = new n<>();

	//Funzione di tipo monitored
	Pair<Integer, Dir> outsideCall_elem;
	n<Pair<Integer, Dir>, Boolean> outsideCall = new n<>();

	//Funzione di tipo statico
	abstract
	Integer plusorminus(Dir param0_plusorminus);

	//Funzione di tipo statico
	abstract
	Dir opposite(Dir param0_opposite);

	//Funzione di tipo statico
	abstract
	Integer ground();

	//Funzione di tipo statico
	abstract
	Integer top();

	//Funzione di tipo statico
	static Lift lift1;

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */

	abstract
	void r_costraintHasToDeliver();

	abstract
	void r_costraintCallFromTo();

	abstract
	void r_Depart(Lift _l);

	abstract
	void r_Continue(Lift _l);

	abstract
	void r_CancelRequest(Floor _f, Dir _d, Lift _l);

	abstract
	void r_Stop(Lift _l);

	abstract
	void r_Change(Lift _l);

	abstract void r_Fsm (Lift _l, State _s1, Boolean _cond, Caso relativo ai RuleDomain _rule, State _s2);

	abstract
	void r_Main();

}

class LIFT extends LIFT_sig {

	// Inizializzazione di funzioni e domini

	LIFT() {

		//Definizione iniziale dei domini statici

		Floor.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2));
		Floor_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2));
		//setto la lista di elementi di supporto della classe enumerativa
		for(Dir i : Dir.values())
		Dir_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(State i : State.values())
		State_lista.add(i);

		//Definizione iniziale dei domini dinamici

		//Definizione iniziale dei domini astratti con funzini statiche

		lift1 = new Lift("lift1");
		Lift_lista.add("lift1");
		Lift_Class.add(lift1);

		//Inizializzazione delle funzioni

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
			State a = State.HALTING;

			ctlState.oldValues.put(_l,a);
			ctlState.newValues.put(_l,a);
		}
		for(Lift _l: Lift.elems) {
			//NOT IMPLEMENTED IN Java (FunctionToCpp line 50)
			//NOT IMPLEMENTED IN Java (FunctionToCpp line 50)

		}

		// Definizione delle funzioni statiche
		Integer ground() {return 0;}
		Integer top() {return 2;}
		Integer plusorminus(Dir _d) {return /*conditionalTerm*/
			((_d == Dir.UP))
			?
			1
			:
			-1
			;}
		Dir opposite(Dir _d) {return /*conditionalTerm*/
			((_d == Dir.UP))
			?
			Dir.DOWN
			:
			Dir.UP
			;}
		Boolean canContinue(Dir _d, Lift _l) {return (_d == Dir.UP) && attracted(Dir.UP, _l) && ! hasToDeliverAt && ! existsCallFromTo || (_d == Dir.DOWN) && attracted(Dir.DOWN, _l) && ! hasToDeliverAt && ! existsCallFromTo;}
		Boolean attracted(Dir _d, Lift _l) {return /*conditionalTerm*/
			((_d == Dir.UP))
			?
			/*conditionalTerm*/
			( Floor.elems.stream().anyMatch(c -> c.equals(loor.get(_l).value
							) && hasToDeliverAt || existsCallFromTo || existsCallFromT))
			)
			?
			true
			:
			false
			:
			/*conditionalTerm*/
			( Floor.elems.stream().anyMatch(c -> c.equals(loor.get(_l).value
					) && hasToDeliverAt || existsCallFromTo || existsCallFromT))
			)
			?
			true
			:
			false
			;}

		// Conversione delle regole ASM in metodi java

		@Override
		void r_costraintHasToDeliver() {
			for(Floor _f : Floor.elems)
			for(Lift _l : Lift.elems)
			if(hasToDeliverAt && (ctlState.get(_l) == State.HALTING) && (floor.get(_l).value
			== _f.value)) {
				hasToDeliverAtfalse);
			}
		}

		@Override
		void r_costraintCallFromTo() {
			for(Floor _f : Floor.elems)
			for(Dir _d : Dir_lista)
			for(Lift _l : Lift.elems)
			if(existsCallFromTo && (_f.value == ground()) && (_d == Dir.DOWN) || (_f.value == top()) && (_d == Dir.UP) || (ctlState.get(_l) == State.HALTING) && (floor.get(_l).value
			== _f.value)) {
				existsCallFromTofalse);
			}
		}

		@Override
		void r_Depart (Lift _l) {
			Floor Floor_s = new Floor();
			Floor_s.value = (	 //floor.get(_l).value
			(floor.get(_l).value
			+ plusorminus(direction.get(_l))));
			floor.set(_l, Floor_s);
		}

		@Override
		void r_Continue (Lift _l) {
			Floor Floor_s = new Floor();
			Floor_s.value = (	 //floor.get(_l).value
			(floor.get(_l).value
			+ plusorminus(direction.get(_l))));
			floor.set(_l, Floor_s);
		}

		@Override
		void r_CancelRequest (Floor _f, Dir _d, Lift _l) {
			{ //par
				hasToDeliverAtfalse);
				existsCallFromTofalse);
			} //endpar
		}

		@Override
		void r_Stop (Lift _l) {
			r_CancelRequest(floor.get(_l).value
			.value, direction.get(_l), _l);
		}

		@Override
		void r_Change (Lift _l) {
			{ //par
				direction.set(_l, opposite(direction.get(_l)));
				r_CancelRequest(floor.get(_l).value
				.value, opposite(direction.get(_l)), _l);
			} //endpar
		}

		@Override
		void r_Fsm (Lift _l, State _s1, Boolean _cond, Caso relativo ai RuleDomain _rule, State _s2) {
			if ((ctlState.get(_l) == _s1) && _cond) {
				{ //par
					Caso TermAs rulectlState.set(_l, _s2);
				} //endpar
			}
		}

		@Override
		void r_Main() {
			for(Lift _l : Lift.elems)
			if(true) {
				{ //seq
					for(Floor _f : Floor.elems)
					if(true) {
						{ //par
							if ((outsideCall[make_tuple(_f.value, Dir.DOWN)] == true)) {
								existsCallFromTotrue);
							}
							if ((outsideCall[make_tuple(_f.value, Dir.UP)] == true)) {
								existsCallFromTotrue);
							}
							if ((insideCall[make_tuple(_l, _f.value)] == true)) {
								hasToDeliverAttrue);
							}
						} //endpar
					}
					r_costraintCallFromTo();
					r_costraintHasToDeliver();
					{ //par
						r_Fsm(_l, State.HALTING, attracted(direction.get(_l), _l), RuleAsRTerm, State.MOVING);
						r_Fsm(_l, State.MOVING, canContinue(direction.get(_l), _l), RuleAsRTerm, State.MOVING);
						r_Fsm(_l, State.MOVING, ! canContinue(direction.get(_l), _l), RuleAsRTerm, State.HALTING);
						r_Fsm(_l, State.HALTING, ! attracted(direction.get(_l), _l) && attracted(opposite(direction.get(_l)), _l), RuleAsRTerm, State.HALTING);
					} //endpar
				} //endseq
			}
		}

		// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
		void initControlledWithMonitored() {
		}

		// applicazione dell'aggiornamento del set
		void fireUpdateSet() {

			direction.oldValues = direction.newValues;
			ctlState.oldValues = ctlState.newValues;
			floor.oldValues = floor.newValues;
			hasToDeliverAt.oldValues = hasToDeliverAt.newValues;
			existsCallFromTo.oldValues = existsCallFromTo.newValues;
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


