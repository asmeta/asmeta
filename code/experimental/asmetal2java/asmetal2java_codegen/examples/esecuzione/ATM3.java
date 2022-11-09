// ATM3.java automatically generated from ASM2CODE

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


abstract class ATM3_sig {
	
	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo astratto
	
	static class NumCard {
		
	static List<NumCard> elems = new ArrayList<NumCard>();
	static List<String> val = new ArrayList<String>();
	
	NumCard (String a)
	{
	    elems.add(this);
	    val.add(a);
	}
	
	String ToString(NumCard a)
	{
	 if(elems.contains(a))
	 {
	   return val.get(elems.lastIndexOf(a));
	 }
	
	else return null;
	}
	
	static NumCard get(String a)
	{
	  if(val.contains(a))
	  {
	 
	  	return elems.get(val.lastIndexOf(a));
	  } 
	 
	  else return null;
	 }
	
	}
	
	List<String> NumCard_lista = new ArrayList<String>();
	List<NumCard> NumCard_Class = new ArrayList<NumCard>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static enum State {AWAITCARD, AWAITPIN, CHOOSE, OUTOFSERVICE, CHOOSEAMOUNT, STANDARDAMOUNTSELECTION, OTHERAMOUNTSELECTION}
				
	List<State> State_lista = new ArrayList<State>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static class  MoneySize{
					
				      static List<Integer> elems = new ArrayList<Integer>();
	
	                  Integer value;
					}
					
					MoneySize MoneySize_elem = new MoneySize();
					
					List<Integer> MoneySize_elems = new ArrayList<Integer>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static enum Service {BALANCE, WITHDRAWAL, EXIT}
				
	List<Service> Service_lista = new ArrayList<Service>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static enum MoneySizeSelection {STANDARD, OTHER}
				
	List<MoneySizeSelection> MoneySizeSelection_lista = new ArrayList<MoneySizeSelection>();
	
	
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
	zeroC <NumCard> currCard = new zeroC<>();
	
	
	//Funzione di tipo Controlled
	zeroC <State> atmState = new zeroC<>();
	
	
	//Funzione di tipo statico
	abstract Integer pin (NumCard param0_pin);
	
	//Funzione di tipo Controlled
	nC<NumCard, Boolean> accessible = new nC<>();
	
	
	//Funzione di tipo Controlled
	zeroC <Integer> moneyLeft = new zeroC<>();
	
	
	//Funzione di tipo Controlled
	nC<NumCard, Integer> balance = new nC<>();
	
	
	//Funzione di tipo Controlled
	zeroC <Integer> numOfBalanceChecks = new zeroC<>();
	
	
	//Funzione di tipo monitored
	zero<NumCard> insertedCard = new zero<>();
	
	
	//Funzione di tipo monitored
	zero<Integer> insertedPin = new zero<>();
	
	
	//Funzione di tipo monitored
	zero<Service> selectedService = new zero<>();
	
	
	//Funzione di tipo monitored
	zero<MoneySize> insertMoneySizeStandard = new zero<>();
	
	MoneySize insertMoneySizeStandard_supporto = new MoneySize();
	
	//Funzione di tipo monitored
	zero<Integer> insertMoneySizeOther = new zero<>();
	
	
	//Funzione di tipo monitored
	zero<MoneySizeSelection> standardOrOther = new zero<>();
	
	
	//Funzione di tipo derived
	abstract Boolean allowed (NumCard param0_allowed, Integer param1_allowed);
	
	//Funzione di tipo statico
	static NumCard card1;
	
	//Funzione di tipo statico
	static NumCard card2;
	
	//Funzione di tipo statico
	static NumCard card3;
	
	//Funzione di tipo statico
	abstract Integer minMoney();
	
	//Funzione di tipo statico
	abstract Integer maxPrelievo();
	
	
	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	
	abstract void r_subtractFrom (NumCard _c, Integer _m);
	
	abstract void r_goOutOfService();
	
	abstract void r_insertcard();
	
	abstract void r_enterPin();
	
	abstract void r_chooseService();
	
	abstract void r_chooseAmount();
	
	abstract void r_grantMoney (Integer _m);
	
	abstract void r_processMoneyRequest (Integer _m);
	
	abstract void r_prelievo();
	
	abstract void r_Main();
	
}



class ATM3 extends ATM3_sig {
	
	// Inizializzazione di funzioni e domini
	
	ATM3(){
	
     //Definizione iniziale dei domini statici
     
	 MoneySize.elems = Collections.unmodifiableList(Arrays.asList(10, 20, 40, 50, 100, 150, 200));
	 MoneySize_elems = Collections.unmodifiableList(Arrays.asList(10, 20, 40, 50, 100, 150, 200));
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(State i : State.values())
	 State_lista.add(i);
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(Service i : Service.values())
	 Service_lista.add(i);
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(MoneySizeSelection i : MoneySizeSelection.values())
	 MoneySizeSelection_lista.add(i);
	 
	
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
	
	 //Inizializzazione delle funzioni
	 
	 atmState.oldValue = atmState.newValue = State.AWAITCARD;
	 moneyLeft.oldValue = moneyLeft.newValue = 1000;
	 for(NumCard _c: NumCard.elems){
	 Integer a = null;	
	 	if(_c.ToString(_c).equals("card1"))
	 		a   = 3000;
	 	else if(_c.ToString(_c).equals("card2"))
	 		a   = 1652;
	 	else if(_c.ToString(_c).equals("card3"))
	 		a   = 548;
	 ;
	 				
	 				      balance.oldValues.put(_c,a);
	 				      balance.newValues.put(_c,a);
	 }
	 for(NumCard _c: NumCard.elems){
	 Boolean a  = true;
	 				
	 				      accessible.oldValues.put(_c,a);
	 				      accessible.newValues.put(_c,a);
	 }
	
	}
	
    // Definizione delle funzioni statiche
	Integer minMoney(){return 200;}
	Integer maxPrelievo(){return 1000;}
	Integer pin(NumCard _c){ 	if(_c==card1) 
			return 1;
		else if(_c==card2)
			return 2;
		else if(_c==card3)
			return 3;
	 return null;
	}
	Boolean allowed(NumCard _c, Integer _m){return (balance.get(_c) >= _m);}
	
	// Conversione delle regole ASM in metodi java
	
	@Override
	void r_subtractFrom (NumCard _c, Integer _m){
		balance.set(_c, (balance.get(_c) - _m));
	}
	
	@Override
	void r_goOutOfService(){
		atmState.set(State.OUTOFSERVICE);
	}
	
	@Override
	void r_insertcard(){
		if ((atmState.get() == State.AWAITCARD)){ 
			if (	NumCard.elems.stream().anyMatch(c -> c.ToString(c).equals(insertedCard.get().ToString(c)))
			){ 
				{ //par
					currCard.set(insertedCard.get());
					atmState.set(State.AWAITPIN);
				}//endpar
			}
		}
	}
	
	@Override
	void r_enterPin(){
		if ((atmState.get() == State.AWAITPIN)){ 
				if ((insertedPin.get() == pin(currCard.get())) && accessible.get(currCard.get())){ 
				{ //par
					atmState.set(State.CHOOSE);
					numOfBalanceChecks.set(0);
				}//endpar
				}else{
				atmState.set(State.AWAITCARD);
			}
		}
	}
	
	@Override
	void r_chooseService(){
		if ((atmState.get() == State.CHOOSE)){ 
			{ //par
				if ((selectedService.get() == Service.BALANCE)){ 
						if ((numOfBalanceChecks.get() == 0)){ 
						numOfBalanceChecks.set((numOfBalanceChecks.get() + 1));
						}else{
						atmState.set(State.AWAITCARD);
					}
				}
				if ((selectedService.get() == Service.WITHDRAWAL)){ 
					atmState.set(State.CHOOSEAMOUNT);
				}
				if ((selectedService.get() == Service.EXIT)){ 
					atmState.set(State.AWAITCARD);
				}
			}//endpar
		}
	}
	
	@Override
	void r_chooseAmount(){
		if ((atmState.get() == State.CHOOSEAMOUNT)){ 
			{ //par
				if ((standardOrOther.get() == MoneySizeSelection.STANDARD)){ 
					atmState.set(State.STANDARDAMOUNTSELECTION);
				}
				if ((standardOrOther.get() == MoneySizeSelection.OTHER)){ 
					atmState.set(State.OTHERAMOUNTSELECTION);
				}
			}//endpar
		}
	}
	
	@Override
	void r_grantMoney (Integer _m){
		{ //par
			accessible.set(currCard.get(), false);
			r_subtractFrom(currCard.get(), _m);
			moneyLeft.set((moneyLeft.get() - _m));
			atmState.set(State.AWAITCARD);
		}//endpar
	}
	
	@Override
	void r_processMoneyRequest (Integer _m){
		if (allowed(currCard.get(), _m)){ 
			r_grantMoney(_m);
		}
	}
	
	@Override
	void r_prelievo(){
		{ //par
			if ((atmState.get() == State.STANDARDAMOUNTSELECTION)){ 
				if (	MoneySize.elems.stream().anyMatch(c -> c.equals(insertMoneySizeStandard.get().value))
				){ 
					if ((insertMoneySizeStandard.get().value <= moneyLeft.get())){ 
						r_processMoneyRequest(insertMoneySizeStandard.get().value);
					}
				}
			}
			if ((atmState.get() == State.OTHERAMOUNTSELECTION)){ 
				if (((insertMoneySizeOther.get() % 10) == 0)){ 
					if ((insertMoneySizeOther.get() <= moneyLeft.get())){ 
						r_processMoneyRequest(insertMoneySizeOther.get());
					}
				}
			}
		}//endpar
	}
	
	@Override
	void r_Main(){
			if ((moneyLeft.get() < minMoney())){ 
			r_goOutOfService();
			}else{
			{ //par
				r_insertcard();
				r_enterPin();
				r_chooseService();
				r_chooseAmount();
				{//seq
					r_prelievo();
					if (! accessible.get(currCard.get())){ 
						accessible.set(currCard.get(), true);
						accessible.oldValues = accessible.newValues;
					}
				}//endseq
			}//endpar
		}
	}
	
	
	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored(){
    }
	
	// applicazione dell'aggiornamento del set
	void fireUpdateSet(){
		
	  currCard.oldValue = currCard.newValue;
	  atmState.oldValue = atmState.newValue;
	  accessible.oldValues = accessible.newValues;
	  moneyLeft.oldValue = moneyLeft.newValue;
	  balance.oldValues = balance.newValues;
	  numOfBalanceChecks.oldValue = numOfBalanceChecks.newValue;
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


