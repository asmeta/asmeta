
// Contatore_U_DA_H.java automatically generated from ASM2CODE
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

abstract class Contatore_U_DA_H_sig {
	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	static class Unita {
		static List<Integer> elems = new ArrayList<Integer>();
		Integer value;
	}

	Unita Unita_elem = new Unita();
	List<Integer> Unita_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo
	static class Decine {
		static List<Integer> elems = new ArrayList<Integer>();
		Integer value;
	}

	Decine Decine_elem = new Decine();
	List<Integer> Decine_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo
	static class Centinaia {
		static List<Integer> elems = new ArrayList<Integer>();
		Integer value;
	}

	Centinaia Centinaia_elem = new Centinaia();
	List<Integer> Centinaia_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo
	static class Migliaia {
		static List<Integer> elems = new ArrayList<Integer>();
		Integer value;
	}

	Migliaia Migliaia_elem = new Migliaia();
	List<Integer> Migliaia_elems = new ArrayList<Integer>();

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
	zero<Boolean> click = new zero<>();
	//Funzione di tipo Controlled
	zeroC<Unita> unit = new zeroC<>();
	//Funzione di tipo Controlled
	zeroC<Decine> da = new zeroC<>();
	//Funzione di tipo Controlled
	zeroC<Centinaia> h = new zeroC<>();
	//Funzione di tipo Controlled
	zeroC<Migliaia> m = new zeroC<>();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	abstract void r_upMigliaia();

	abstract void r_upCentinaia();

	abstract void r_upDecine();

	abstract void r_upUnita();

	abstract void r_Contatore();
}

class Contatore_U_DA_H extends Contatore_U_DA_H_sig {
	// Inizializzazione di funzioni e domini
	Contatore_U_DA_H() {
		//Definizione iniziale dei domini statici
		Unita.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		Unita_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		Decine.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		Decine_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		Centinaia.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		Centinaia_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		Migliaia.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		Migliaia_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		//Definizione iniziale dei domini dinamici
		//Definizione iniziale dei domini astratti con funzini statiche
		//Inizializzazione delle funzioni
		click.Value = false;
		Unita_elem.value = 0;
		unit.oldValue = unit.newValue = Unita_elem;
		Decine_elem.value = 0;
		da.oldValue = da.newValue = Decine_elem;
		Centinaia_elem.value = 0;
		h.oldValue = h.newValue = Centinaia_elem;
		Migliaia_elem.value = 0;
		m.oldValue = m.newValue = Migliaia_elem;
	}

	// Definizione delle funzioni statiche
	// Conversione delle regole ASM in metodi java
	@Override
	void r_upMigliaia() {
		Migliaia Migliaia_s = new Migliaia();
		Migliaia_s.value = (//m.get().value
		((m.get().value + 1) % 10));
		m.set(Migliaia_s);
	}

	@Override
	void r_upCentinaia() {
		{ //par
			if ((h.get().value == 9)) {
				r_upMigliaia();
			}
			Centinaia Centinaia_s = new Centinaia();
			Centinaia_s.value = (//h.get().value
			((h.get().value + 1) % 10));
			h.set(Centinaia_s);
		} //endpar
	}

	@Override
	void r_upDecine() {
		{ //par
			if ((da.get().value == 9)) {
				r_upCentinaia();
			}
			Decine Decine_s = new Decine();
			Decine_s.value = (//da.get().value
			((da.get().value + 1) % 10));
			da.set(Decine_s);
		} //endpar
	}

	@Override
	void r_upUnita() {
		{ //par
			if ((unit.get().value == 9)) {
				r_upDecine();
			}
			Unita Unita_s = new Unita();
			Unita_s.value = (//unit.get().value
			((unit.get().value + 1) % 10));
			unit.set(Unita_s);
		} //endpar
	}

	@Override
	void r_Contatore() {
		if (click.get()) {
			r_upUnita();
		}
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {
		unit.oldValue = unit.newValue;
		da.oldValue = da.newValue;
		h.oldValue = h.newValue;
		m.oldValue = m.newValue;
	}

	//Metodo per l'aggiornamento dell'asm
	void UpdateASM() {
		r_Contatore();
		fireUpdateSet();
		initControlledWithMonitored();
	}

	public static void main(String[] args) {
	}
}
