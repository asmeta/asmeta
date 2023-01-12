// testDefinition.java automatically generated from ASM2CODE

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

abstract class testDefinition_sig {

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
			}

			else
				return null;
		}

		static Till get(String a) {
			if (val.contains(a)) {

				return elems.get(val.lastIndexOf(a));
			}

			else
				return null;
		}

	}

	List<String> Till_lista = new ArrayList<String>();
	List<Till> Till_Class = new ArrayList<Till>();

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
			}

			else
				return null;
		}

		static Card get(String a) {
			if (val.contains(a)) {

				return elems.get(val.lastIndexOf(a));
			}

			else
				return null;
		}

	}

	List<String> Card_lista = new ArrayList<String>();
	List<Card> Card_Class = new ArrayList<Card>();

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
			}

			else
				return null;
		}

		static Date get(String a) {
			if (val.contains(a)) {

				return elems.get(val.lastIndexOf(a));
			}

			else
				return null;
		}

	}

	List<String> Date_lista = new ArrayList<String>();
	List<Date> Date_Class = new ArrayList<Date>();

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
			}

			else
				return null;
		}

		static Account get(String a) {
			if (val.contains(a)) {

				return elems.get(val.lastIndexOf(a));
			}

			else
				return null;
		}

	}

	List<String> Account_lista = new ArrayList<String>();
	List<Account> Account_Class = new ArrayList<Account>();

	//Variabile di tipo Concreto o Enumerativo

	static class CoinDomain {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	CoinDomain CoinDomain_elem = new CoinDomain();

	List<Integer> CoinDomain_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static enum Product {
		COFFEE, TEA, MILK
	}

	List<Product> Product_lista = new ArrayList<Product>();

	//Variabile di tipo Concreto o Enumerativo

	static enum TillState {
		WAITCARD, WAITPIN, VALIDPIN
	}

	List<TillState> TillState_lista = new ArrayList<TillState>();

	//Variabile di tipo Concreto o Enumerativo

	static class QuantityDomain {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	QuantityDomain QuantityDomain_elem = new QuantityDomain();

	List<Integer> QuantityDomain_elems = new ArrayList<Integer>();

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
	zeroC<Second> seconds = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Minute> minutes = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Hour> hours = new zeroC<>();

	//Funzione di tipo Controlled
	nC<Card, Integer> daily_withdraw_sum = new nC<>();

	//Funzione di tipo Controlled
	nC<Till, TillState> tillState = new nC<>();

	//Funzione di tipo Controlled
	nC<NumCard, Integer> balance = new nC<>();

	//Funzione di tipo Controlled
	nC<NumCard, Boolean> accessible = new nC<>();

	//Funzione di tipo Controlled
	nC<Product, QuantityDomain> available = new nC<>();

	//Funzione di tipo Controlled
	zeroC<CoinDomain> coins = new zeroC<>();

	//Funzione di tipo Controlled
	nC<Card, Date> cardLastUseDate = new nC<>();

	//Funzione di tipo statico
	static NumCard card1;

	//Funzione di tipo statico
	static NumCard card2;

	//Funzione di tipo statico
	static NumCard card3;

	//Funzione di tipo statico
	static Card card11;

	//Funzione di tipo statico
	static Card card21;

	//Funzione di tipo statico
	static Date monday;

	//Funzione di tipo statico
	static Date tuesday;

	//Funzione di tipo statico
	static Account account1;

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */

	abstract
	void r_main();

}

class testDefinition extends testDefinition_sig {

	// Inizializzazione di funzioni e domini

	testDefinition() {

		//Definizione iniziale dei domini statici

		Second.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59));
		Second_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59));
		Minute.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59));
		Minute_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59));
		Hour.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23));
		Hour_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23));
		//setto la lista di elementi di supporto della classe enumerativa
		for(Product i : Product.values())
		Product_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(TillState i : TillState.values())
		TillState_lista.add(i);

		//Definizione iniziale dei domini dinamici

		//Definizione iniziale dei domini astratti con funzini statiche

		card1 = new NumCard("card1");
		NumCard_lista.add("card1");
		NumCard_Class.add(card1);
		card2 = new NumCard("card2");
		NumCard_lista.add("card2");
		NumCard_Class.add(card2);
		card3 = new NumCard("card3");
		NumCard_lista.add("card3");
		NumCard_Class.add(card3);
		card11 = new Card("card11");
		Card_lista.add("card11");
		Card_Class.add(card11);
		card21 = new Card("card21");
		Card_lista.add("card21");
		Card_Class.add(card21);
		monday = new Date("monday");
		Date_lista.add("monday");
		Date_Class.add(monday);
		tuesday = new Date("tuesday");
		Date_lista.add("tuesday");
		Date_Class.add(tuesday);
		account1 = new Account("account1");
		Account_lista.add("account1");
		Account_Class.add(account1);

		//Inizializzazione delle funzioni

		for(NumCard _c: NumCard.elems) {
			Integer a = null;
			if(_c.ToString(_c).equals("card1"))
			a = 3000;
			else if(_c.ToString(_c).equals("card2"))
			a = 1652;
			else if(_c.ToString(_c).equals("card3"))
			a = 548;
			;

			balance.oldValues.put(_c,a);
			balance.newValues.put(_c,a);
		}
		for(NumCard _c: NumCard.elems) {
			Boolean a = true;

			accessible.oldValues.put(_c,a);
			accessible.newValues.put(_c,a);
		}

		CoinDomain_elem.value = 0;

		coins.oldValue = coins.newValue = CoinDomain_elem;

		for(Product _p: Product.values()) {
			QuantityDomain a = new QuantityDomain();

			a.value = 10;
			available.oldValues.put(_p,a);
			available.newValues.put(_p,a);
		}
		for(Till _m: Till.elems) {
			TillState a = TillState.WAITCARD;

			tillState.oldValues.put(_m,a);
			tillState.newValues.put(_m,a);
		}

		Second_elem.value = 0;

		seconds.oldValue = seconds.newValue = Second_elem;

		Minute_elem.value = 0;

		minutes.oldValue = minutes.newValue = Minute_elem;

		Hour_elem.value = 0;

		hours.oldValue = hours.newValue = Hour_elem;

		for(Card _c: Card.elems) {
			Integer a = 0;

			daily_withdraw_sum.oldValues.put(_c,a);
			daily_withdraw_sum.newValues.put(_c,a);
		}
		for(Card _c: Card.elems) {
			Date monday = new Date("monday");

			cardLastUseDate.oldValues.put(_c,monday);
			cardLastUseDate.newValues.put(_c,monday);
		}

	}

	// Definizione delle funzioni statiche

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

		seconds.oldValue = seconds.newValue;
		minutes.oldValue = minutes.newValue;
		hours.oldValue = hours.newValue;
		daily_withdraw_sum.oldValues = daily_withdraw_sum.newValues;
		tillState.oldValues = tillState.newValues;
		balance.oldValues = balance.newValues;
		accessible.oldValues = accessible.newValues;
		available.oldValues = available.newValues;
		coins.oldValue = coins.newValue;
		cardLastUseDate.oldValues = cardLastUseDate.newValues;
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


