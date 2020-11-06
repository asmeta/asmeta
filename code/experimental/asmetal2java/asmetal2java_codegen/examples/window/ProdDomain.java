// ProdDomain.java automatically generated from ASM2CODE

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

abstract class ProdDomain_sig {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	static class Second {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	Second Second_elem = new Second();

	List<Integer> Second_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static class Minute {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	Minute Minute_elem = new Minute();

	List<Integer> Minute_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static class Hour {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	Hour Hour_elem = new Hour();

	List<Integer> Hour_elems = new ArrayList<Integer>();

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
	Triplet<Second, Minute, Hour> time_elem;
	nC<Triplet<Second, Minute, Hour>, Second> time = new nC<>();

	//Funzione di tipo Controlled
	zeroC<Integer> number = new zeroC<>();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */

	abstract
	void r_Main();

}

class ProdDomain extends ProdDomain_sig {

	// Inizializzazione di funzioni e domini

	ProdDomain() {

		//Definizione iniziale dei domini statici

		Second.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59));
		Second_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59));
		Minute.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59));
		Minute_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59));
		Hour.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23));
		Hour_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23));

		//Definizione iniziale dei domini dinamici

		//Definizione iniziale dei domini astratti con funzini statiche

		//Inizializzazione delle funzioni

		for(int _x=0; _x < Second.elems.size(); _x++ ) {

			Second_elem.value = Second.elems.get(_x);

			for(int _y=0; _y < Minute.elems.size(); _y++ ) {

				Minute_elem.value = Minute.elems.get(_y);

				for(int _z=0; _z < Hour.elems.size(); _z++ ) {

					Hour_elem.value = Hour.elems.get(_z);

					Second a = new Second();

					a.value = 0;
					time_elem = new Triplet<Second,Minute,Hour>(Second_elem,Minute_elem,Hour_elem);
					time.oldValues.put(time_elem,a);
					time.newValues.put(time_elem,a);
				}}}

	}

	// Definizione delle funzioni statiche

	// Conversione delle regole ASM in metodi java

	@Override
	void r_Main() {
		if ((true))
		System.out.println();
		time_elem = null;
		Second_elem.value = 0;
		Minute_elem.value = 0;
		Hour_elem.value = 0;
		time_elem = new Triplet<Second,Minute,Hour>(Second_elem,Minute_elem,Hour_elem);
		if((time.get(time_elem).value //time
						== 0)) {
			time_elem = null;
			Second_elem.value = 1;
			Minute_elem.value = 3;
			Hour_elem.value = 2;
			time_elem = new Triplet<Second,Minute,Hour>(Second_elem,Minute_elem,Hour_elem);
			Second sup = new Second();
			sup.value = ( //time
					1);
			time.set(time_elem, sup);
		}
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {

		time.oldValues = time.newValues;
		number.oldValue = number.newValue;
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


