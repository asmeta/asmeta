
// Counter.java automatically generated from ASM2CODE
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

abstract class Counter_sig {
	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
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
	//Funzione di tipo monitored
	zero<Boolean> signal = new zero<>();
	//Funzione di tipo Controlled
	zeroC<Integer> counter = new zeroC<>();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	abstract void r_Main();
}

class Counter extends Counter_sig {
	// Inizializzazione di funzioni e domini
	Counter() {
		//Definizione iniziale dei domini statici
		//Definizione iniziale dei domini dinamici
		//Definizione iniziale dei domini astratti con funzini statiche
		//Inizializzazione delle funzioni
		counter.oldValue = counter.newValue = 0;
	}

	// Definizione delle funzioni statiche
	// Conversione delle regole ASM in metodi java
	@Override
	void r_Main() {
		if (signal.get()) {
			counter.set((counter.get() + 1));
		}
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {
		counter.oldValue = counter.newValue;
	}

	//Metodo per l'aggiornamento dell'asm
	void UpdateASM() {
		r_Main();
		fireUpdateSet();
		initControlledWithMonitored();
	}

	public static void main(String[] args) {
	}
}
