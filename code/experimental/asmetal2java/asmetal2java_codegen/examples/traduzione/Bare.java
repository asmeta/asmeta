// Bare.java automatically generated from ASM2CODE

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

abstract class Bare_sig {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	static enum DomainEnumerative {
		DD, CC, EE
	}

	List<DomainEnumerative> DomainEnumerative_lista = new ArrayList<
			DomainEnumerative>();

	//Variabile di tipo Concreto o Enumerativo

	static class D1 {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	D1 D1_elem = new D1();

	List<Integer> D1_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static class D2 {

		static List<Triplet<String, <Integer> , D1>> elems = new ArrayList<Triplet<String, <Integer> , D1>>();

		Triplet<String, <Integer> , D1> value;
	}

	D2 D2_elem = new D2();

	List<Triplet<String, <Integer> , D1>> D2_elems = new ArrayList<Triplet<String, <Integer> , D1>>();

	//Variabile di tipo Concreto o Enumerativo

	static class D3 {

		static List<<Triplet<String, Integer, String>> > elems = new ArrayList<<Triplet<String, Integer, String>> >();

		<Triplet<String, Integer, String>> value;
	}

	D3 D3_elem = new D3();

	List<<Triplet<String, Integer, String>> > D3_elems = new ArrayList<<Triplet<String, Integer, String>> >();

	//Variabile di tipo Concreto o Enumerativo

	static class D4 {

		static List<Pair<String, D1>> elems = new ArrayList<Pair<String, D1>>();

		Pair<String, D1> value;
	}

	D4 D4_elem = new D4();

	List<Pair<String, D1>> D4_elems = new ArrayList<Pair<String, D1>>();

	//Variabile di tipo Concreto o Enumerativo

	static class D5 {

		static List<String> elems = new ArrayList<String>();

		String value;
	}

	D5 D5_elem = new D5();

	List<String> D5_elems = new ArrayList<String>();

	//Variabile di tipo Concreto o Enumerativo

	static class D6 {

		static List<Pair<String, String>> elems = new ArrayList<
				Pair<String, String>>();

		Pair<String, String> value;
	}

	D6 D6_elem = new D6();

	List<Pair<String, String>> D6_elems = new ArrayList<Pair<String, String>>();

	//Variabile di tipo Concreto o Enumerativo

	static class D7 {

		static List<<<Integer> > > elems = new ArrayList<<<Integer> > >();

		<<Integer> > value;
	}

	D7 D7_elem = new D7();

	List<<<Integer> > > D7_elems = new ArrayList<<<Integer> > >();

	//Variabile di tipo Concreto o Enumerativo

	class D8 {

		List<Integer> elems = new ArrayList<Integer>();

		Integer value;

		D8(Integer i) {
			value = i;
		}

	}

	List<Integer> D8_elems = new ArrayList<Integer>();

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
	//Funzione di tipo statico
	abstract
	D1 staticFunction();

	//Funzione di tipo statico
	static Pair<Integer, Integer> staticFunction2;

	//Funzione di tipo statico
	static Pair<Integer, D1> staticFunction4;

	//Funzione di tipo statico
	abstract
	Integer staticFunction3(Integer param0_staticFunction3);

	//Funzione di tipo statico
	abstract
	ArrayListInteger staticFunction5(
			ArrayList<Pair<Integer, Integer>> param0_staticFunction5);

	//Funzione di tipo Controlled
	zeroC<Integer> controlledFunction1 = new zeroC<>();

	//Funzione di tipo Controlled
	nC<Integer, Integer> controlledFunction2 = new nC<>();

	//Funzione di tipo Controlled
	Triplet<D1, D1, D1> controlledFunction3_elem;
	nC<Triplet<D1, D1, D1>, Integer> controlledFunction3 = new nC<>();

	//Funzione di tipo Controlled
	zeroC<String> controlledFunction4 = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<D1> controlledFunction5 = new zeroC<>();

	//Funzione di tipo derived
	abstract
	Integer derivedFunction();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */

	abstract
	void r_Parameter(Integer _param, D5 _param2);

	abstract
	void r_skipprova();

	abstract
	void r_Main();

}

class Bare extends Bare_sig {

	// Inizializzazione di funzioni e domini

	Bare() {

		//Definizione iniziale dei domini statici

		D1.elems = Collections.unmodifiableList(Arrays.asList(1, 2, 3));
		D1_elems = Collections.unmodifiableList(Arrays.asList(1, 2, 3));
		D2.elems = Collections.unmodifiableList(Arrays.asList(make_tuple("ciao", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20), 1), make_tuple("prova", 2), 3)));
		D2_elems = Collections.unmodifiableList(Arrays.asList(make_tuple("ciao", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20), 1), make_tuple("prova", 2), 3)));
		D5.elems = Collections.unmodifiableList(Arrays.asList("ciao", "prova", "casa"));
		D5_elems = Collections.unmodifiableList(Arrays.asList("ciao", "prova", "casa"));
		D4.elems = Collections.unmodifiableList(Arrays.asList(make_tuple("ciao", 2), make_tuple("prova", 6)));
		D4_elems = Collections.unmodifiableList(Arrays.asList(make_tuple("ciao", 2), make_tuple("prova", 6)));
		D6.elems = Collections.unmodifiableList(Arrays.asList(make_tuple("ciao", "ciao"), make_tuple("prova", "prova")));
		D6_elems = Collections.unmodifiableList(Arrays.asList(make_tuple("ciao", "ciao"), make_tuple("prova", "prova")));
		D3.elems = Collections.unmodifiableList(Arrays.asList(make_tuple("prova", 4, "prova2"), make_tuple("provax", 5, "provax2")), make_tuple("prova1", 4, "prova12"), make_tuple("provax1", 5, "provax12"))));
		D3_elems = Collections.unmodifiableList(Arrays.asList(make_tuple("prova", 4, "prova2"), make_tuple("provax", 5, "provax2")), make_tuple("prova1", 4, "prova12"), make_tuple("provax1", 5, "provax12"))));
		D7.elems = Collections.unmodifiableList(Arrays.asList(1, 5, 2), 5, 9, 8), 5, 8))));
		D7_elems = Collections.unmodifiableList(Arrays.asList(1, 5, 2), 5, 9, 8), 5, 8))));
		//setto la lista di elementi di supporto della classe enumerativa
		for(DomainEnumerative i : DomainEnumerative.values())
		DomainEnumerative_lista.add(i);

		//Definizione iniziale dei domini dinamici

		D8_elems=(1, 2, 5);

		//Definizione iniziale dei domini astratti con funzini statiche

		//Inizializzazione delle funzioni

		controlledFunction1.oldValue = controlledFunction1.newValue = 5;
		controlledFunction4.oldValue = controlledFunction4.newValue = "provafunzione";

		for(int _x=0; _x < D1.elems.size(); _x++ ) {

			D1_elem.value = D1.elems.get(_x);

			for(int _y=0; _y < D1.elems.size(); _y++ ) {

				D1_elem.value = D1.elems.get(_y);

				for(int _z=0; _z < D1.elems.size(); _z++ ) {

					D1_elem.value = D1.elems.get(_z);

					Integer a ((_x.value + _y.value) + _z.value);

					controlledFunction3.oldValues.put(_x,a);
					controlledFunction3.newValues.put(_x,a);
				}}}

	}

	// Definizione delle funzioni statiche
	D1 staticFunction() {

		D1 supp = new D1();

		supp.value = 5;

		return supp;
	}
	Pair<Integer, Integer> staticFunction2() {return make_tuple(2, 5);}
	Integer staticFunction3(Integer _a) {return (10 + _a);}
	Integer staticFunction5(<Pair<Integer, Integer>> _x) {return (10 + 5);}
	Integer derivedFunction() {return (10 + staticFunction3(5));}

	// Conversione delle regole ASM in metodi java

	@Override
	void r_Parameter (Integer _param, D5 _param2) {
		{ //par
			if ((controlledFunction1.get() < 5)) {
				controlledFunction35);
			}
			if ((staticFunction().value < 5)) {
				;
			}
			if ((controlledFunction4.get() != "prova")) {
				;
			}
			if ((controlledFunction5.get().value
			== 1)) {
				;
			}
			if ((controlledFunction3 == 5)) {
				;
			}
		} //endpar
	}

	@Override
	void r_skipprova() {
		;
	}

	@Override
	void r_Main() {
		r_skipprova();
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {

		controlledFunction1.oldValue = controlledFunction1.newValue;
		controlledFunction2.oldValues = controlledFunction2.newValues;
		controlledFunction3.oldValues = controlledFunction3.newValues;
		controlledFunction4.oldValue = controlledFunction4.newValue;
		controlledFunction5.oldValue = controlledFunction5.newValue;
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


