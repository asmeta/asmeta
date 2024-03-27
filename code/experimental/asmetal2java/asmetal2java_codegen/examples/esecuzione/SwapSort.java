// SwapSort.java automatically generated from ASM2CODE

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

abstract class SwapSort_sig {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	static class VectDomain {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	VectDomain VectDomain_elem = new VectDomain();

	List<Integer> VectDomain_elems = new ArrayList<Integer>();

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
	nC<Integer, Integer> vect = new nC<>();

	//Funzione di tipo Controlled
	nC<VectDomain, Integer> vect2 = new nC<>();

	//Funzione di tipo statico
	abstract
	Integer n();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */

	abstract
	void r_swap(Integer _x, Integer _y);

	abstract
	void r_swapSort();

	abstract
	void r_Main();

}

class SwapSort extends SwapSort_sig {

	// Inizializzazione di funzioni e domini

	SwapSort() {

		//Definizione iniziale dei domini statici

		VectDomain.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		VectDomain_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

		//Definizione iniziale dei domini dinamici

		//Definizione iniziale dei domini astratti con funzini statiche

		//Inizializzazione delle funzioni

		//NOT IMPLEMENTED IN Java (FunctionToCpp line 50)

		for(int _x=0; _x < VectDomain.elems.size(); _x++ ) {

			VectDomain_elem.value = VectDomain.elems.get(_x);

			Integer a = null;
			if(VectDomain_elem.value.equals(0))
			a = 5;
			else if(VectDomain_elem.value.equals(1))
			a = 7;
			else if(VectDomain_elem.value.equals(2))
			a = 1;
			else if(VectDomain_elem.value.equals(3))
			a = 8;
			else if(VectDomain_elem.value.equals(4))
			a = 3;
			else if(VectDomain_elem.value.equals(5))
			a = 2;
			else if(VectDomain_elem.value.equals(6))
			a = 6;
			else if(VectDomain_elem.value.equals(7))
			a = 4;
			else if(VectDomain_elem.value.equals(8))
			a = 8;
			else if(VectDomain_elem.value.equals(9))
			a = 9;
			;

			vect2.oldValues.put(VectDomain_elem,a);
			vect2.newValues.put(VectDomain_elem,a);
		}

	}

	// Definizione delle funzioni statiche
	Integer n() {return 10;}

	// Conversione delle regole ASM in metodi java

	@Override
	void r_swap (Integer _x, Integer _y) {
		{ //par
			_x = (_y);

			_y = (_x);

		} //endpar
	}

	@Override
	void r_swapSort() {
		List<Integer> point0 = new ArrayList<Integer>();
		List<Integer> point1 = new ArrayList<Integer>();
		point0 = Collections.unmodifiableList(Arrays.asList (0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		for(Integer _i : point0) {
			point1 = Collections.unmodifiableList(Arrays.asList (0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
			for(Integer _j : point1) {
				if((_i < _j) && (vect.get(_i) > vect.get(_j))) {
					point0.add(_i);
					point1.add(_j);
				}
			}
		}
		int rndm = ThreadLocalRandom.current().nextInt(0, point0.size());
		{
			Integer _i = point0.get(rndm);
			Integer _j = point1.get(rndm);
			if(point0.size()>0) {
				r_swap(vect.get(_i), vect.get(_j));
			}
		}
	}

	@Override
	void r_Main() {
		r_swapSort();
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {

		vect.oldValues = vect.newValues;
		vect2.oldValues = vect2.newValues;
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


