// diceNoProbability.java automatically generated from ASM2CODE

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


abstract class diceNoProbability_sig {
	
	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	
	static class  Number{
					
				      static List<Integer> elems = new ArrayList<Integer>();
	
	                  Integer value;
					}
					
					Number Number_elem = new Number();
					
					List<Integer> Number_elems = new ArrayList<Integer>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static class  BetDomain{
					
				      static List<Integer> elems = new ArrayList<Integer>();
	
	                  Integer value;
					}
					
					BetDomain BetDomain_elem = new BetDomain();
					
					List<Integer> BetDomain_elems = new ArrayList<Integer>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static class  Money{
					
				      static List<Integer> elems = new ArrayList<Integer>();
	
	                  Integer value;
					}
					
					Money Money_elem = new Money();
					
					List<Integer> Money_elems = new ArrayList<Integer>();
	
	
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
	//Funzione di tipo monitored
	zero<BetDomain> bet = new zero<>();
	
	BetDomain bet_supporto = new BetDomain();
	
	//Funzione di tipo Controlled
	zeroC <Money> playerBudget = new zeroC<>();
	
	
	//Funzione di tipo Controlled
	zeroC <Money> bankBudget = new zeroC<>();
	
	
	//Funzione di tipo derived
	abstract Money gain (BetDomain param0_gain);
	
	
	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	
	abstract void r_game();
	
	abstract void r_Main();
	
}



class diceNoProbability extends diceNoProbability_sig {
	
	// Inizializzazione di funzioni e domini
	
	diceNoProbability(){
	
     //Definizione iniziale dei domini statici
     
	 Number.elems = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4, 5, 6));
	 Number_elems = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4, 5, 6));
	 BetDomain.elems = Collections.unmodifiableList(Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
	 BetDomain_elems = Collections.unmodifiableList(Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
	 Money.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
	 Money_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
	 
	
	 //Definizione iniziale dei domini dinamici
	 
	
	 //Definizione iniziale dei domini astratti con funzini statiche
	 
	
	 //Inizializzazione delle funzioni
	 
	 
	 Money_elem.value = 5;
	 
	 playerBudget.oldValue = playerBudget.newValue = Money_elem;
	 
	 
	 Money_elem.value = 5;
	 
	 bankBudget.oldValue = bankBudget.newValue = Money_elem;
	 
	
	}
	
    // Definizione delle funzioni statiche
	Money gain(BetDomain _b){
						
						Money supp = new Money();
						supp.value = /*conditionalTerm*/
							((_b.value < 5) || (_b.value > 9))
							?
								2
							:
								1
						;
						
						return supp;
					}
	
	// Conversione delle regole ASM in metodi java
	
	@Override
	void r_game(){
		List<Number> point0 = new ArrayList<Number>();
		List<Number> point1 = new ArrayList<Number>();
		for(Number _x : Number_lista)
		for(Number _y : Number_lista)
		if(true){
		point0.add(_x);
		point1.add(_y);
		}
		int rndm = ThreadLocalRandom.current().nextInt(0, point0.size());
		{
		Number _x = point0.get(rndm);
		Number _y = point1.get(rndm);
		  if(point0.size()>0){
				if (((_x.value + _y.value) == bet.get().value)){ 
				{ //par
					Money Money_s = new Money();
					Money_s.value = (//playerBudget.get().value
					(playerBudget.get().value
					 + gain((_x.value + _y.value))));
								   playerBudget.set(Money_s);
					Money Money_s = new Money();
					Money_s.value = (//bankBudget.get().value
					(bankBudget.get().value
					 - gain((_x.value + _y.value))));
								   bankBudget.set(Money_s);
				}//endpar
				}else{
				{ //par
					Money Money_s = new Money();
					Money_s.value = (//playerBudget.get().value
					(playerBudget.get().value
					 - gain((_x.value + _y.value))));
								   playerBudget.set(Money_s);
					Money Money_s = new Money();
					Money_s.value = (//bankBudget.get().value
					(bankBudget.get().value
					 + gain((_x.value + _y.value))));
								   bankBudget.set(Money_s);
				}//endpar
			}
			 }
		}
	}
	
	@Override
	void r_Main(){
		if ((playerBudget.get().value
		 >= 2) && (bankBudget.get().value
		 >= 2)){ 
			r_game();
		}
	}
	
	
	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored(){
    }
	
	// applicazione dell'aggiornamento del set
	void fireUpdateSet(){
		
	  playerBudget.oldValue = playerBudget.newValue;
	  bankBudget.oldValue = bankBudget.newValue;
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


