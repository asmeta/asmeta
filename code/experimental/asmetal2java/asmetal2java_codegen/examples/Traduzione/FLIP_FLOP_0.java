// FLIP_FLOP_0.java automatically generated from ASM2CODE

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

abstract class FLIP_FLOP_0_sig {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	static class State {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	State State_elem = new State();

	List<Integer> State_elems = new ArrayList<Integer>();

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
	zeroC<State> ctl_state = new zeroC<>();

	//Funzione di tipo monitored
	zero<Boolean> high = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> low = new zero<>();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */

	abstract
	void r_Fsm();

	abstract
	void r_Main();

}

class FLIP_FLOP_0 extends FLIP_FLOP_0_sig {

	// Inizializzazione di funzioni e domini

	FLIP_FLOP_0() {

		//Definizione iniziale dei domini statici

		State.elems = Collections.unmodifiableList(Arrays.asList(0, 1));
		State_elems = Collections.unmodifiableList(Arrays.asList(0, 1));

		//Definizione iniziale dei domini dinamici

		//Definizione iniziale dei domini astratti con funzini statiche

		//Inizializzazione delle funzioni

		State_elem.value = 0;

		ctl_state.oldValue = ctl_state.newValue = State_elem;

		high.Value = false;
		low.Value = false;

	}

	// Definizione delle funzioni statiche

	// Conversione delle regole ASM in metodi java

	@Override
	void r_Fsm() {
		if ((ctl_state.get().value
						== 0)) {
			State State_s = new State();
			State_s.value = (	//ctl_state.get().value
					1);
			ctl_state.set(State_s);
		} else {
			State State_s = new State();
			State_s.value = (	//ctl_state.get().value
					0);
			ctl_state.set(State_s);
		}
	}

	@Override
	void r_Main() {
		{	//seq
			r_Fsm();
			r_Fsm();
		}	//endseq
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {

		ctl_state.oldValue = ctl_state.newValue;
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


