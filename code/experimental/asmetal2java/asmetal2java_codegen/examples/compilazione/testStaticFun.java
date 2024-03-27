
// testStaticFun.java automatically generated from ASM2CODE
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

abstract class testStaticFun_sig {
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
			} else
				return null;
		}

		static NumCard get(String a) {
			if (val.contains(a)) {
				return elems.get(val.lastIndexOf(a));
			} else
				return null;
		}
	}

	List<String> NumCard_lista = new ArrayList<String>();
	List<NumCard> NumCard_Class = new ArrayList<NumCard>();

	//Variabile di tipo astratto
	static class Account {
		static List<Account> elems = new ArrayList<Account>();
		static List<String> val = new ArrayList<String>();

		Account(String a) {
			elems.add(this);
			val.add(a);
		}

		String ToString(Account a) {
			if (elems.contains(a)) {
				return val.get(elems.lastIndexOf(a));
			} else
				return null;
		}

		static Account get(String a) {
			if (val.contains(a)) {
				return elems.get(val.lastIndexOf(a));
			} else
				return null;
		}
	}

	List<String> Account_lista = new ArrayList<String>();
	List<Account> Account_Class = new ArrayList<Account>();

	//Variabile di tipo astratto
	static class Card {
		static List<Card> elems = new ArrayList<Card>();
		static List<String> val = new ArrayList<String>();

		Card(String a) {
			elems.add(this);
			val.add(a);
		}

		String ToString(Card a) {
			if (elems.contains(a)) {
				return val.get(elems.lastIndexOf(a));
			} else
				return null;
		}

		static Card get(String a) {
			if (val.contains(a)) {
				return elems.get(val.lastIndexOf(a));
			} else
				return null;
		}
	}

	List<String> Card_lista = new ArrayList<String>();
	List<Card> Card_Class = new ArrayList<Card>();

	//Variabile di tipo astratto
	static class Till {
		static List<Till> elems = new ArrayList<Till>();
		static List<String> val = new ArrayList<String>();

		Till(String a) {
			elems.add(this);
			val.add(a);
		}

		String ToString(Till a) {
			if (elems.contains(a)) {
				return val.get(elems.lastIndexOf(a));
			} else
				return null;
		}

		static Till get(String a) {
			if (val.contains(a)) {
				return elems.get(val.lastIndexOf(a));
			} else
				return null;
		}
	}

	List<String> Till_lista = new ArrayList<String>();
	List<Till> Till_Class = new ArrayList<Till>();

	//Variabile di tipo astratto
	static class Date {
		static List<Date> elems = new ArrayList<Date>();
		static List<String> val = new ArrayList<String>();

		Date(String a) {
			elems.add(this);
			val.add(a);
		}

		String ToString(Date a) {
			if (elems.contains(a)) {
				return val.get(elems.lastIndexOf(a));
			} else
				return null;
		}

		static Date get(String a) {
			if (val.contains(a)) {
				return elems.get(val.lastIndexOf(a));
			} else
				return null;
		}
	}

	List<String> Date_lista = new ArrayList<String>();
	List<Date> Date_Class = new ArrayList<Date>();

	//Variabile di tipo Concreto o Enumerativo
	static enum Side {
		LEFT, RIGHT
	}

	List<Side> Side_lista = new ArrayList<Side>();

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
	nC<NumCard, Integer> balance = new nC<>();

	//Funzione di tipo statico
	abstract Integer minMoney();

	//Funzione di tipo statico
	abstract Integer maxPrelievo();

	//Funzione di tipo derived
	abstract Boolean allowed(NumCard param0_allowed, Integer param1_allowed);

	//Funzione di tipo statico
	abstract Integer withdrawLimit();

	//Funzione di tipo statico
	abstract Integer encodedPin(Card param0_encodedPin);

	//Funzione di tipo statico
	abstract Account cardAccount(Card param0_cardAccount);

	//Funzione di tipo derived
	abstract Side oppositeSide(Side param0_oppositeSide);

	//Funzione di tipo statico
	static Card card1;
	//Funzione di tipo statico
	static Card card2;
	//Funzione di tipo statico
	static Card card3;
	//Funzione di tipo statico
	static Till till1;
	//Funzione di tipo statico
	static Account account1;
	//Funzione di tipo statico
	static Date monday;
	//Funzione di tipo statico
	static Date tuesday;
	//Funzione di tipo statico
	static NumCard car1;

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	abstract void r_main();
}

class testStaticFun extends testStaticFun_sig {
	// Inizializzazione di funzioni e domini
	testStaticFun() {
		//Definizione iniziale dei domini statici
		//setto la lista di elementi di supporto della classe enumerativa
		for (Side i : Side.values())
			Side_lista.add(i);
		//Definizione iniziale dei domini dinamici
		//Definizione iniziale dei domini astratti con funzini statiche
		card1 = new Card("card1");
		Card_lista.add("card1");
		Card_Class.add(card1);
		card2 = new Card("card2");
		Card_lista.add("card2");
		Card_Class.add(card2);
		card3 = new Card("card3");
		Card_lista.add("card3");
		Card_Class.add(card3);
		till1 = new Till("till1");
		Till_lista.add("till1");
		Till_Class.add(till1);
		account1 = new Account("account1");
		Account_lista.add("account1");
		Account_Class.add(account1);
		monday = new Date("monday");
		Date_lista.add("monday");
		Date_Class.add(monday);
		tuesday = new Date("tuesday");
		Date_lista.add("tuesday");
		Date_Class.add(tuesday);
		car1 = new NumCard("car1");
		NumCard_lista.add("car1");
		NumCard_Class.add(car1);
		//Inizializzazione delle funzioni
	}

	// Definizione delle funzioni statiche
	Integer encodedPin(Card _c) {
		if (_c == card1)
			return 1;
		else if (_c == card2)
			return 2;
		return null;
	}

	Account cardAccount(Card _c) {
		if (_c == card1)
			return account1;
		else if (_c == card2)
			return account1;
		return null;
	}

	Integer withdrawLimit() {
		return 1000;
	}

	Integer minMoney() {
		return 200;
	}

	Integer maxPrelievo() {
		return 1000;
	}

	Boolean allowed(NumCard _c, Integer _m) {
		return (balance.get(_c) >= _m);
	}

	Side oppositeSide(Side _s) {
		return /*conditionalTerm*/
		((_s == Side.LEFT)) ? Side.RIGHT : Side.LEFT;
	}

	// Conversione delle regole ASM in metodi java
	@Override
	void r_main() {
		;
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {
		balance.oldValues = balance.newValues;
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
