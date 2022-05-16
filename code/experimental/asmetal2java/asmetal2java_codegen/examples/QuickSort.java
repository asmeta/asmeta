// QuickSort.java automatically generated from ASM2CODE

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

abstract class QuickSort_sig {

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
	//Funzione di tipo Controlled
	List<Integer> f_elem = new ArrayList<Integer>();

	zeroC<List<Integer> > f = new zeroC<>();

	//Funzione di tipo statico
	abstract
	ArrayList<Integer> qsort(ArrayList<Integer> param0_qsort);

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */

	abstract
	void r_main();

}

class QuickSort extends QuickSort_sig {

	// Inizializzazione di funzioni e domini

	QuickSort() {

		//Definizione iniziale dei domini statici

		//Definizione iniziale dei domini dinamici

		//Definizione iniziale dei domini astratti con funzini statiche

		//Inizializzazione delle funzioni

	}

	// Definizione delle funzioni statiche
	ArrayList<Integer> qsort(ArrayList<Integer> _seq) {return /*conditionalTerm*/
		((length(_seq) == 0))
		?
		)
		:

		[&]() {**<--- letTerm**
			auto _pivot = first(_seq);
			return union(union(qsort(caso sequenza da trattare

					), caso sequenza da trattare

			), qsort(caso sequenza da trattare

			));
		}()
		;}

	// Conversione delle regole ASM in metodi java

	@Override
	void r_main() {
		f_elem = Collections.unmodifiableList(Arrays.asList(qsort(5, 1, 8, 10, 2, 4, 7, 3, 6, 9)));
		f.set(f_elem);
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {

		f.oldValue = f.newValue;
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


