// sluiceGateGround.java automatically generated from ASM2CODE

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

abstract class sluiceGateGround_sig {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	static class Minutes {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	Minutes Minutes_elem = new Minutes();

	List<Integer> Minutes_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static enum PhaseDomain {
		FULLYCLOSED, FULLYOPEN
	}

	List<PhaseDomain> PhaseDomain_lista = new ArrayList<PhaseDomain>();

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
	zeroC<PhaseDomain> phase = new zeroC<>();

	//Funzione di tipo derived
	abstract
	Minutes openPeriod();

	//Funzione di tipo derived
	abstract
	Minutes closedPeriod();

	//Funzione di tipo monitored
	n<Minutes, Boolean> passed = new n<>();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */

	abstract
	void r_open();

	abstract
	void r_shut();

	abstract
	void r_Main();

}

class sluiceGateGround extends sluiceGateGround_sig {

	// Inizializzazione di funzioni e domini

	sluiceGateGround() {

		//Definizione iniziale dei domini statici

		Minutes.elems = Collections.unmodifiableList(Arrays.asList(10, 170));
		Minutes_elems = Collections.unmodifiableList(Arrays.asList(10, 170));
		//setto la lista di elementi di supporto della classe enumerativa
		for(PhaseDomain i : PhaseDomain.values())
		PhaseDomain_lista.add(i);

		//Definizione iniziale dei domini dinamici

		//Definizione iniziale dei domini astratti con funzini statiche

		//Inizializzazione delle funzioni

		phase.oldValue = phase.newValue = PhaseDomain.FULLYCLOSED;

	}

	// Definizione delle funzioni statiche
	Minutes openPeriod() {

		Minutes_elem.value = 10;

		return Minutes_elem;
	}
	Minutes closedPeriod() {

		Minutes_elem.value = 170;

		return Minutes_elem;
	}

	// Conversione delle regole ASM in metodi java

	@Override
	void r_open() {
		;
	}

	@Override
	void r_shut() {
		;
	}

	@Override
	void r_Main() {
		{ //par
			if ((phase.get() == PhaseDomain.FULLYCLOSED)) {
				if (passed.get(closedPeriod())) {
					{ //par
						r_open();
						phase.set(PhaseDomain.FULLYOPEN);
					} //endpar
				}
			}
			if ((phase.get() == PhaseDomain.FULLYOPEN)) {
				if (passed.get(openPeriod())) {
					{ //par
						r_shut();
						phase.set(PhaseDomain.FULLYCLOSED);
					} //endpar
				}
			}
		} //endpar
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {

		phase.oldValue = phase.newValue;
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


