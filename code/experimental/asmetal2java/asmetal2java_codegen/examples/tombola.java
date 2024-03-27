
// tombola.java automatically generated from ASM2CODE
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

abstract class tombola_sig {
	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	static class Numero {
		static List<Integer> elems = new ArrayList<Integer>();
		Integer value;
	}

	Numero Numero_elem = new Numero();
	List<Integer> Numero_elems = new ArrayList<Integer>();

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
	nC<Numero, Boolean> uscito = new nC<>();

	//Funzione di tipo statico
	abstract Boolean cartellaGiocatoreA(Numero param0_cartellaGiocatoreA);

	//Funzione di tipo statico
	abstract Boolean cartellaGiocatoreB(Numero param0_cartellaGiocatoreB);

	//Funzione di tipo Controlled
	nC<Numero, Boolean> uscitoSuCartellaGiocatoreA = new nC<>();
	//Funzione di tipo Controlled
	nC<Numero, Boolean> uscitoSuCartellaGiocatoreB = new nC<>();

	//Funzione di tipo derived
	abstract Boolean cartellaGiocatoreACompleta();

	//Funzione di tipo derived
	abstract Boolean cartellaGiocatoreBCompleta();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	abstract void r_Main();
}

class tombola extends tombola_sig {
	// Inizializzazione di funzioni e domini
	tombola() {
		//Definizione iniziale dei domini statici
		Numero.elems = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
				17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30));
		Numero_elems = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
				17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30));
		//Definizione iniziale dei domini dinamici
		//Definizione iniziale dei domini astratti con funzini statiche
		//Inizializzazione delle funzioni
		for (int _n = 0; _n < Numero.elems.size(); _n++) {
			Numero_elem.value = Numero.elems.get(_n);
			Boolean a = false;
			uscito.oldValues.put(Numero_elem, a);
			uscito.newValues.put(Numero_elem, a);
		}
		for (int _n = 0; _n < Numero.elems.size(); _n++) {
			Numero_elem.value = Numero.elems.get(_n);
			Boolean a = false;
			uscitoSuCartellaGiocatoreA.oldValues.put(Numero_elem, a);
			uscitoSuCartellaGiocatoreA.newValues.put(Numero_elem, a);
		}
		for (int _n = 0; _n < Numero.elems.size(); _n++) {
			Numero_elem.value = Numero.elems.get(_n);
			Boolean a = false;
			uscitoSuCartellaGiocatoreB.oldValues.put(Numero_elem, a);
			uscitoSuCartellaGiocatoreB.newValues.put(Numero_elem, a);
		}
	}

	// Definizione delle funzioni statiche
	Boolean cartellaGiocatoreA(Numero _n) {
		return (_n.value == 2) || (_n.value == 7) || (_n.value == 12) || (_n.value == 18) || (_n.value == 25);
	}

	Boolean cartellaGiocatoreB(Numero _n) {
		return (_n.value == 1) || (_n.value == 5) || (_n.value == 14) || (_n.value == 18) || (_n.value == 26);
	}

	Boolean cartellaGiocatoreACompleta() {
		return uscitoSuCartellaGiocatoreA.get(2) && uscitoSuCartellaGiocatoreA.get(7)
				&& uscitoSuCartellaGiocatoreA.get(12) && uscitoSuCartellaGiocatoreA.get(18)
				&& uscitoSuCartellaGiocatoreA.get(25);
	}

	Boolean cartellaGiocatoreBCompleta() {
		return uscitoSuCartellaGiocatoreB.get(1) && uscitoSuCartellaGiocatoreB.get(5)
				&& uscitoSuCartellaGiocatoreB.get(14) && uscitoSuCartellaGiocatoreB.get(18)
				&& uscitoSuCartellaGiocatoreB.get(26);
	}

	// Conversione delle regole ASM in metodi java
	@Override
	void r_Main() {
		if (!cartellaGiocatoreACompleta() && !cartellaGiocatoreBCompleta()) {
			List<Numero> point0 = new ArrayList<Numero>();
			for (Numero _n : Numero_lista)
				if (!uscito.get(_n.value)) {
					point0.add(_n);
				}
			int rndm = ThreadLocalRandom.current().nextInt(0, point0.size());
			{
				Numero _n = point0.get(rndm);
				if (point0.size() > 0) {
					{ //par
						uscito.set(_n, true);
						if (cartellaGiocatoreA(_n)) {
							uscitoSuCartellaGiocatoreA.set(_n, true);
						}
						if (cartellaGiocatoreB(_n)) {
							uscitoSuCartellaGiocatoreB.set(_n, true);
						}
					} //endpar
				}
			}
		}
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {
		uscito.oldValues = uscito.newValues;
		uscitoSuCartellaGiocatoreA.oldValues = uscitoSuCartellaGiocatoreA.newValues;
		uscitoSuCartellaGiocatoreB.oldValues = uscitoSuCartellaGiocatoreB.newValues;
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
