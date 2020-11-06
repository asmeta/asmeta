// testSignature.java automatically generated from ASM2CODE

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

abstract class testSignature_sig {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo astratto
	static class NumCard {

		static List<NumCard> elems = new ArrayList<NumCard>();
		static List<String> val = new ArrayList<String>();

		NumCard(String a) {
			elems.add(this);
			val.add(a);
		}

		String ToString(NumCard a) {
			if (elems.contains(a)) {
				return val.get(elems.lastIndexOf(a));
			}

			else
				return null;
		}

		static NumCard get(String a) {
			if (val.contains(a)) {

				return elems.get(val.lastIndexOf(a));
			}

			else
				return null;
		}

	}

	List<String> NumCard_lista = new ArrayList<String>();
	List<NumCard> NumCard_Class = new ArrayList<NumCard>();

	//Variabile di tipo astratto

	static class Sfortuna {

		static List<Sfortuna> elems = new ArrayList<Sfortuna>();
		static List<String> val = new ArrayList<String>();

		Sfortuna(String a) {
			elems.add(this);
			val.add(a);
		}

		String ToString(Sfortuna a) {
			if (elems.contains(a)) {
				return val.get(elems.lastIndexOf(a));
			}

			else
				return null;
		}

		static Sfortuna get(String a) {
			if (val.contains(a)) {

				return elems.get(val.lastIndexOf(a));
			}

			else
				return null;
		}

	}

	List<String> Sfortuna_lista = new ArrayList<String>();
	List<Sfortuna> Sfortuna_Class = new ArrayList<Sfortuna>();

	//Variabile di tipo astratto

	static class Dinam {

		static List<Dinam> elems = new ArrayList<Dinam>();
		List<String> val = new ArrayList<String>();

		Dinam(String a) {
			elems.add(this);
			val.add(a);
		}

		String ToString(Dinam a) {
			if (elems.contains(a)) {
				return val.get(elems.lastIndexOf(a));
			}

			else
				return null;
		}

		Dinam get(String a) {
			if (val.contains(a)) {

				return elems.get(val.lastIndexOf(a));
			}

			else
				return null;
		}

	}

	List<String> Dinam_lista = new ArrayList<String>();
	List<Dinam> Dinam_Class = new ArrayList<Dinam>();

	//Variabile di tipo Concreto o Enumerativo

	static class Numeri {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	Numeri Numeri_elem = new Numeri();

	List<Integer> Numeri_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static class Parole {

		static List<String> elems = new ArrayList<String>();

		String value;
	}

	Parole Parole_elem = new Parole();

	List<String> Parole_elems = new ArrayList<String>();

	//Variabile di tipo Concreto o Enumerativo

	static class Naturali {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	Naturali Naturali_elem = new Naturali();

	List<Integer> Naturali_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static class Oggetto {

		static List<Object> elems = new ArrayList<Object>();

		Object value;
	}

	Oggetto Oggetto_elem = new Oggetto();

	List<Object> Oggetto_elems = new ArrayList<Object>();

	//Variabile di tipo Concreto o Enumerativo

	class NumeriD {

		List<Integer> elems = new ArrayList<Integer>();

		Integer value;

		NumeriD(Integer i) {
			value = i;
		}

	}

	List<Integer> NumeriD_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static enum Color {
		RED, GREEN, BLUE
	}

	List<Color> Color_lista = new ArrayList<Color>();

	//Variabile di tipo Concreto o Enumerativo

	static enum Type {
		LATTE, THE, CAFFE, ACQUA
	}

	List<Type> Type_lista = new ArrayList<Type>();

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
	static List<Integer> dominioS1 = new ArrayList<Integer> ();

	//Funzione di tipo statico
	static List<NumCard> dominioS2 = new ArrayList<NumCard>();

	//Funzione di tipo statico
	static List<Naturali> dominioS3 = new ArrayList<Naturali>();

	//Funzione di tipo statico
	static List<Color> dominioS4 = new ArrayList<Color>();

	//Funzione di tipo statico
	static Pair<Integer, String> dominioS6;

	//Funzione di tipo statico
	static Pair<NumCard, Boolean> dominioS7;

	//Funzione di tipo statico
	static Pair<Naturali, NumCard> dominioS8;

	//Funzione di tipo statico
	static Pair<Color, String> dominioS9;

	//Funzione di tipo statico
	static Pair<String, Integer> dominioS10;

	//Funzione di tipo statico
	static Triplet<String, Integer, String> dominioS11;

	//Funzione di tipo statico
	static Quartet<String, Integer, String, Integer> dominioS12;

	//Funzione di tipo statico
	static Quintet<String, Integer, String, Integer, String> dominioS13;

	//Funzione di tipo statico
	static Sextet<String, Integer, String, Integer, String, Integer> dominioS14;

	//Funzione di tipo statico
	static Septet<String, Integer, String, Integer, String, Integer, String> dominioS15;

	//Funzione di tipo statico
	static Octet<String, Integer, String, Integer, String, Integer, String,
			Integer> dominioS16;

	//Funzione di tipo statico
	static Ennead<String, Integer, String, Integer, String, Integer, String,
			Integer, String> dominioS17;

	//Funzione di tipo statico
	static Decade<String, Integer, String, Integer, String, Integer, String,
			Integer, String, Integer> dominioS18;

	//Funzione di tipo statico
	static Set<Integer> dominioS19 = new HashSet<Integer>();

	//Funzione di tipo statico
	static Set<NumCard> dominioS20 = new HashSet<NumCard>();

	//Funzione di tipo statico
	static Set<Naturali> dominioS21 = new HashSet<Naturali>();

	//Funzione di tipo statico
	static Set<Color> dominioS22 = new HashSet<Color>();

	//Funzione di tipo statico
	static Bag<Integer> dominioS23 = new HashBag<Integer>();

	//Funzione di tipo statico
	static Bag<NumCard> dominioS24 = new HashBag<NumCard>();

	//Funzione di tipo statico
	static Bag<Naturali> dominioS25 = new HashBag<Naturali>();

	//Funzione di tipo statico
	static Bag<Color> dominioS26 = new HashBag<Color>();

	//Funzione di tipo statico
	static Map<Integer, String> dominioS27 = new HashMap<Integer, String>();

	//Funzione di tipo statico
	static Map<NumCard, Boolean> dominioS28 = new HashMap<NumCard, Boolean>();

	//Funzione di tipo statico
	static Map<Naturali, NumCard> dominioS29 = new HashMap<Naturali, NumCard>();

	//Funzione di tipo statico
	static Map<Color, String> dominioS30 = new HashMap<Color, String>();

	//Funzione di tipo Controlled
	List<Integer> dominioC1_elem = new ArrayList<Integer>();

	zeroC<List<Integer> > dominioC1 = new zeroC<>();

	//Funzione di tipo Controlled
	Pair<Integer, String> dominioC2_elem;
	zeroC<Pair<Integer, String>> dominioC2 = new zeroC<>();

	//Funzione di tipo Controlled
	Triplet<String, Integer, String> dominioC3_elem;
	zeroC<Triplet<String, Integer, String>> dominioC3 = new zeroC<>();

	//Funzione di tipo Controlled
	Set<Integer> dominioC4_elem = new HashSet<Integer>();

	zeroC<Set<Integer> > dominioC4 = new zeroC<>();

	//Funzione di tipo Controlled
	Bag<Integer> dominioC5_elem = new HashBag<Integer>();

	zeroC<Bag<Integer> > dominioC5 = new zeroC<>();

	//Funzione di tipo Controlled
	Map<Integer, String> dominioC6_elem = new HashMap<Integer, String>();

	zeroC<Map<Integer, String>> dominioC6 = new zeroC<>();

	//Funzione di tipo monitored
	List<Integer> dominioM1_elem = new ArrayList<Integer>();

	zero<List<Integer> > dominioM1 = new zero<>();

	//Funzione di tipo monitored
	Pair<Integer, String> dominioM2_elem;
	zero<Pair<Integer, String>> dominioM2 = new zero<>();

	//Funzione di tipo monitored
	Triplet<String, Integer, String> dominioM3_elem;
	zero<Triplet<String, Integer, String>> dominioM3 = new zero<>();

	//Funzione di tipo monitored
	Set<Integer> dominioM4_elem = new HashSet<Integer>();

	zero<Set<Integer> > dominioM4 = new zero<>();

	//Funzione di tipo monitored
	Bag<Integer> dominioM5_elem = new HashBag<Integer>();

	zero<Bag<Integer> > dominioM5 = new zero<>();

	//Funzione di tipo monitored
	Map<Integer, String> dominioM6_elem = new HashMap<Integer, String>();

	zero<Map<Integer, String>> dominioM6 = new zero<>();

	//Funzione di tipo out
	List<Integer> dominioO1_elem = new ArrayList<Integer>();

	zero<List<Integer> > dominioO1 = new zero<>();

	//Funzione di tipo out
	Pair<Integer, String> dominioO2_elem;
	zero<Pair<Integer, String>> dominioO2 = new zero<>();

	//Funzione di tipo out
	Triplet<String, Integer, String> dominioO3_elem;
	zero<Triplet<String, Integer, String>> dominioO3 = new zero<>();

	//Funzione di tipo out
	Set<Integer> dominioO4_elem = new HashSet<Integer>();

	zero<Set<Integer> > dominioO4 = new zero<>();

	//Funzione di tipo out
	Bag<Integer> dominioO5_elem = new HashBag<Integer>();

	zero<Bag<Integer> > dominioO5 = new zero<>();

	//Funzione di tipo out
	Map<Integer, String> dominioO6_elem = new HashMap<Integer, String>();

	zero<Map<Integer, String>> dominioO6 = new zero<>();

	//Funzione di tipo derived
	List<Integer> dominioD1 = new ArrayList<Integer>();

	//Funzione di tipo derived
	Pair<Integer, String> dominioD2;

	//Funzione di tipo derived
	Triplet<String, Integer, String> dominioD3;

	//Funzione di tipo derived
	Set<Integer> dominioD4 = new HashSet<Integer>();

	//Funzione di tipo derived
	Bag<Integer> dominioD5 = new HashBag<Integer>();

	//Funzione di tipo derived
	Map<Integer, String> dominioD6 = new HashMap<Integer, String>();

	//Funzione di tipo statico
	static NumCard funAbS;

	//Funzione di tipo Controlled
	zeroC<NumCard> funAbC = new zeroC<>();

	//Funzione di tipo monitored
	zero<NumCard> funAbM = new zero<>();

	//Funzione di tipo out
	zero<NumCard> funAbO = new zero<>();

	//Funzione di tipo derived
	NumCard funAbD;

	//Funzione di tipo statico
	abstract
	Integer funS1();

	//Funzione di tipo statico
	abstract
	Color funS2();

	//Funzione di tipo statico
	abstract
	Oggetto funS3();

	//Funzione di tipo statico
	abstract
	Color funS4(Integer param0_funS4);

	//Funzione di tipo statico
	abstract
	Oggetto funS5(Color param0_funS5);

	//Funzione di tipo statico
	abstract
	NumCard funS6(Oggetto param0_funS6);

	//Funzione di tipo statico
	abstract
	Integer funS7(NumCard param0_funS7);

	//Funzione di tipo statico
	abstract
	Integer funS8(Oggetto param0_funS8, NumCard param1_funS8,
			Integer param2_funS8, Color param3_funS8);

	//Funzione di tipo Controlled
	zeroC<Integer> funC1 = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Color> funC2 = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Oggetto> funC3 = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<NumCard> funC4 = new zeroC<>();

	//Funzione di tipo Controlled
	nC<Integer, Color> funC5 = new nC<>();

	//Funzione di tipo Controlled
	nC<Color, Oggetto> funC6 = new nC<>();

	//Funzione di tipo Controlled
	nC<Oggetto, NumCard> funC7 = new nC<>();

	//Funzione di tipo Controlled
	nC<NumCard, Integer> funC8 = new nC<>();

	//Funzione di tipo Controlled
	Quartet<Oggetto, NumCard, Integer, Color> funC9_elem;
	nC<Quartet<Oggetto, NumCard, Integer, Color>, Integer> funC9 = new nC<>();

	//Funzione di tipo monitored
	zero<Integer> funM1 = new zero<>();

	//Funzione di tipo monitored
	zero<Color> funM2 = new zero<>();

	//Funzione di tipo monitored
	zero<Oggetto> funM3 = new zero<>();

	Oggetto funM3_supporto = new Oggetto();

	//Funzione di tipo monitored
	zero<NumCard> funM4 = new zero<>();

	//Funzione di tipo monitored
	n<Integer, Color> funM5 = new n<>();

	//Funzione di tipo monitored
	n<Color, Oggetto> funM6 = new n<>();

	//Funzione di tipo monitored
	n<Oggetto, NumCard> funM7 = new n<>();

	//Funzione di tipo monitored
	n<NumCard, Integer> funM8 = new n<>();

	//Funzione di tipo monitored
	Quartet<Oggetto, NumCard, Integer, Color> funM9_elem;
	n<Quartet<Oggetto, NumCard, Integer, Color>, Integer> funM9 = new n<>();

	//Funzione di tipo out
	zero<Integer> funO1 = new zero<>();

	//Funzione di tipo out
	zero<Color> funO2 = new zero<>();

	//Funzione di tipo out
	zero<Oggetto> funO3 = new zero<>();

	//Funzione di tipo out
	zero<NumCard> funO4 = new zero<>();

	//Funzione di tipo out
	n<Integer, Color> funO5 = new n<>();

	//Funzione di tipo out
	n<Color, Oggetto> funO6 = new n<>();

	//Funzione di tipo out
	n<Oggetto, NumCard> funO7 = new n<>();

	//Funzione di tipo out
	n<NumCard, Integer> funO8 = new n<>();

	//Funzione di tipo out
	Quartet<Oggetto, NumCard, Integer, Color> funO9_elem;
	n<Quartet<Oggetto, NumCard, Integer, Color>, Integer> funO9 = new n<>();

	//Funzione di tipo derived
	abstract
	Integer funD1();

	//Funzione di tipo derived
	abstract
	Color funD2();

	//Funzione di tipo derived
	abstract
	Oggetto funD3();

	//Funzione di tipo derived
	abstract
	Color funD4(Integer param0_funD4);

	//Funzione di tipo derived
	abstract
	Oggetto funD5(Color param0_funD5);

	//Funzione di tipo derived
	abstract
	NumCard funD6(Oggetto param0_funD6);

	//Funzione di tipo derived
	abstract
	Integer funD7(NumCard param0_funD7);

	//Funzione di tipo derived
	abstract
	Integer funD8(Oggetto param0_funD8, NumCard param1_funD8,
			Integer param2_funD8, Color param3_funD8);

	//Funzione di tipo Controlled
	zeroC <Boolean> bool = new zeroC<>();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */

	abstract
	void r_prova();

	abstract
	void r_prova2();

	abstract
	void r_prova3(Integer _a);

	abstract
	void r_prova3(Integer _b, NumCard _c);

	abstract
	void r_prova3(Integer _d, NumCard _e, Color _f);

	abstract
	void r_prova3(Integer _g, NumCard _h, Color _i, Naturali _l);

	abstract
	void r_prova3(Numeri _m, Numeri _n);

	abstract
	void r_Main();

}

class testSignature extends testSignature_sig {

	// Inizializzazione di funzioni e domini

	testSignature() {

		//Definizione iniziale dei domini statici

		Parole.elems = Collections.unmodifiableList(Arrays.asList("sos", "sas"));
		Parole_elems = Collections.unmodifiableList(Arrays.asList("sos", "sas"));
		//setto la lista di elementi di supporto della classe enumerativa
		for(Color i : Color.values())
		Color_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(Type i : Type.values())
		Type_lista.add(i);

		//Definizione iniziale dei domini dinamici

		//Definizione iniziale dei domini astratti con funzini statiche

		funAbS = new NumCard("funAbS");
		NumCard_lista.add("funAbS");
		NumCard_Class.add(funAbS);

		//Inizializzazione delle funzioni

	}

	// Definizione delle funzioni statiche

	// Conversione delle regole ASM in metodi java

	@Override
	void r_prova() {
		;
	}

	@Override
	void r_prova2() {
		;
	}

	@Override
	void r_prova3 (Integer _a) {
		;
	}

	@Override
	void r_prova3 (Integer _b, NumCard _c) {
		;
	}

	@Override
	void r_prova3 (Integer _d, NumCard _e, Color _f) {
		;
	}

	@Override
	void r_prova3 (Integer _g, NumCard _h, Color _i, Naturali _l) {
		;
	}

	@Override
	void r_prova3 (Numeri _m, Numeri _n) {
		;
	}

	@Override
	void r_Main() {
		;
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {

		dominioC1.oldValue = dominioC1.newValue;
		dominioC2.oldValue = dominioC2.newValue;
		dominioC3.oldValue = dominioC3.newValue;
		dominioC4.oldValue = dominioC4.newValue;
		dominioC5.oldValue = dominioC5.newValue;
		dominioC6.oldValue = dominioC6.newValue;
		funAbC.oldValue = funAbC.newValue;
		funC1.oldValue = funC1.newValue;
		funC2.oldValue = funC2.newValue;
		funC3.oldValue = funC3.newValue;
		funC4.oldValue = funC4.newValue;
		funC5.oldValues = funC5.newValues;
		funC6.oldValues = funC6.newValues;
		funC7.oldValues = funC7.newValues;
		funC8.oldValues = funC8.newValues;
		funC9.oldValues = funC9.newValues;
		bool.oldValue = bool.newValue;
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


