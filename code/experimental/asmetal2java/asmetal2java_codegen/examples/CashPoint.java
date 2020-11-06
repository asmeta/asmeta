// CashPoint.java automatically generated from ASM2CODE

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

abstract class CashPoint_sig {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
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

	//Variabile di tipo Concreto o Enumerativo

	static enum TillState {
		WAITCARD, WAITPIN, VALIDPIN
	}

	List<TillState> TillState_lista = new ArrayList<TillState>();

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
	nC<Till, TillState> tillState = new nC<>();

	//Funzione di tipo Controlled
	nC<Account, Integer> balance = new nC<>();

	//Funzione di tipo Controlled
	nC<Card, Boolean> legalCard = new nC<>();

	//Funzione di tipo Controlled
	nC<Card, Integer> daily_withdraw_sum = new nC<>();

	//Funzione di tipo Controlled
	nC<Till, Card> insertedCard = new nC<>();

	//Funzione di tipo monitored
	n<Till, Integer> enteredPin = new n<>();

	//Funzione di tipo monitored
	n<Till, Integer> amount = new n<>();

	//Funzione di tipo statico
	abstract
	Integer withdrawLimit();

	//Funzione di tipo statico
	abstract
	Integer encodedPin(Card param0_encodedPin);

	//Funzione di tipo statico
	abstract
	Account cardAccount(Card param0_cardAccount);

	//Funzione di tipo monitored
	zero<Date> currentDate = new zero<>();

	//Funzione di tipo Controlled
	nC<Card, Date> cardLastUseDate = new nC<>();

	//Funzione di tipo derived
	abstract
	Boolean stableState();

	//Funzione di tipo statico
	static Card card1;

	//Funzione di tipo statico
	static Card card2;

	//Funzione di tipo statico
	static Till till1;

	//Funzione di tipo statico
	static Account account1;

	//Funzione di tipo statico
	static Date monday;

	//Funzione di tipo statico
	static Date tuesday;

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */

	abstract
	void r_insertedCard(Till _t, Card _c);

	abstract
	void r_insertCard(Till _t);

	abstract
	void r_enterPin(Till _t);

	abstract
	void r_validPin(Till _t);

	abstract
	void r_main();

}

class CashPoint extends CashPoint_sig {

	// Inizializzazione di funzioni e domini

	CashPoint() {

		//Definizione iniziale dei domini statici

		//setto la lista di elementi di supporto della classe enumerativa
		for(TillState i : TillState.values())
		TillState_lista.add(i);

		//Definizione iniziale dei domini dinamici

		//Definizione iniziale dei domini astratti con funzini statiche

		card1 = new Card("card1");
		Card_lista.add("card1");
		Card_Class.add(card1);
		card2 = new Card("card2");
		Card_lista.add("card2");
		Card_Class.add(card2);
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

		//Inizializzazione delle funzioni

		for(Till _m: Till.elems) {
			TillState a = TillState.WAITCARD;

			tillState.oldValues.put(_m,a);
			tillState.newValues.put(_m,a);
		}
		for(Card _m: Card.elems) {
			Boolean a = true;

			legalCard.oldValues.put(_m,a);
			legalCard.newValues.put(_m,a);
		}
		for(Account _a: Account.elems) {
			Integer a chooseone(null);

			balance.oldValues.put(_a,a);
			balance.newValues.put(_a,a);
		}
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
	Integer encodedPin(Card _c) {if(_c==card1)
		return 1;
		else if(_c==card2)
		return 2;
		return null;
	}
	Account cardAccount(Card _c) {if(_c==card1)
		return account1;
		else if(_c==card2)
		return account1;
		return null;
	}
	Integer withdrawLimit() {return 1000;}

	// Conversione delle regole ASM in metodi java

	@Override
	void r_insertedCard (Till _t, Card _c) {
		if ((tillState.get(_t) == TillState.WAITCARD)) {
			{ //par
				tillState.set(_t, TillState.WAITPIN);
				insertedCard.set(_t, _c);
			} //endpar
		}
	}

	@Override
	void r_insertCard (Till _t) {
		List<Card> point0 = new ArrayList<Card>();
		for(Card _c : Card.elems)
		if(true) {
			point0.add(_c);
		}
		int rndm = ThreadLocalRandom.current().nextInt(0, point0.size());
		{
			Card _c = point0.get(rndm);
			if(point0.size()>0) {
				r_insertedCard(_t, _c);
			}
		}
	}

	@Override
	void r_enterPin (Till _t) {
		if ((tillState.get(_t) == TillState.WAITPIN)) {
			if (legalCard.get(insertedCard.get(_t))) {
				if ((enteredPin.get(_t) == encodedPin(insertedCard.get(_t)))) {
					{ //par
						if ((cardLastUseDate.get(insertedCard.get(_t)) != currentDate.get())) {
							{ //par
								daily_withdraw_sum.set(insertedCard.get(_t), 0);
								cardLastUseDate.set(insertedCard.get(_t), currentDate.get());
							} //endpar
						}
						tillState.set(_t, TillState.VALIDPIN);
					} //endpar
				}
			}
		}
	}

	@Override
	void r_validPin (Till _t) {
		if ((tillState.get(_t) == TillState.VALIDPIN)) {
			{
				auto _acc = cardAccount(insertedCard.get(_t));
				if ((amount.get(_t) > 0) && (amount.get(_t) <= balance.get(_acc)) && (daily_withdraw_sum.get(insertedCard.get(_t)) < (withdrawLimit() - amount.get(_t)))) {
					{ //par
						balance.set(_acc, (balance.get(_acc) - amount.get(_t)));
						daily_withdraw_sum.set(insertedCard.get(_t), (daily_withdraw_sum.get(insertedCard.get(_t)) - amount.get(_t)));
					} //endpar
				}
			}
		}
	}

	@Override
	void r_main() {
		List<Till> point0 = new ArrayList<Till>();
		for(Till _till : Till.elems)
		if(true) {
			point0.add(_till);
		}
		int rndm = ThreadLocalRandom.current().nextInt(0, point0.size());
		{
			Till _till = point0.get(rndm);
			if(point0.size()>0) {
				{ //par
					r_insertCard(_till);
					r_enterPin(_till);
					r_validPin(_till);
				} //endpar
			}
		}
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {

		tillState.oldValues = tillState.newValues;
		balance.oldValues = balance.newValues;
		legalCard.oldValues = legalCard.newValues;
		daily_withdraw_sum.oldValues = daily_withdraw_sum.newValues;
		insertedCard.oldValues = insertedCard.newValues;
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


