// cassaforte.java automatically generated from ASM2CODE

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


abstract class cassaforte_sig {
	
	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	
	static class  Sensore{
					
				      static List<Integer> elems = new ArrayList<Integer>();
	
	                  Integer value;
					}
					
					Sensore Sensore_elem = new Sensore();
					
					List<Integer> Sensore_elems = new ArrayList<Integer>();
	
	
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
	zero<Sensore> switchSensore = new zero<>();
	
	Sensore switchSensore_supporto = new Sensore();
	
	//Funzione di tipo Controlled
	nC<Sensore, Boolean> statoSensore = new nC<>();
	
	
	//Funzione di tipo derived
	abstract Boolean adiacenti (Sensore param0_adiacenti);
	
	//Funzione di tipo derived
	abstract Boolean pericolo();
	
	
	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	
	abstract void r_Main();
	
}



class cassaforte extends cassaforte_sig {
	
	// Inizializzazione di funzioni e domini
	
	cassaforte(){
	
     //Definizione iniziale dei domini statici
     
	 Sensore.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3));
	 Sensore_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3));
	 
	
	 //Definizione iniziale dei domini dinamici
	 
	
	 //Definizione iniziale dei domini astratti con funzini statiche
	 
	
	 //Inizializzazione delle funzioni
	 
	 
	 for(int _s=0; _s < Sensore.elems.size(); _s++ ){
	 	
	 	
	 	Sensore_elem.value = Sensore.elems.get(_s);
	 
	 Boolean a  = true;
	 				
	 				      statoSensore.oldValues.put(Sensore_elem,a);
	 				      statoSensore.newValues.put(Sensore_elem,a);
	 }
	
	}
	
    // Definizione delle funzioni statiche
	Boolean adiacenti(Sensore _s){return /*conditionalTerm*/
		((_s.value == 0))
		?
			statoSensore.get(3) && statoSensore.get(1)
		:
			/*conditionalTerm*/
				((_s.value == 3))
				?
					statoSensore.get(0) && statoSensore.get(2)
				:
					statoSensore.get((_s.value + 1)) && statoSensore.get((_s.value - 1))
	;}
	Boolean pericolo(){ return 
	  /*<--- forAllTerm*/
		Sensore_lista.stream().allMatch(c -> ! adiacenti(_s.value) && ! statoSensore.get(_s.valc));
	}
	
	// Conversione delle regole ASM in metodi java
	
	@Override
	void r_Main(){
		if ((statoSensore.get(switchSensore.get().value) == false)){ 
				statoSensore.set(switchSensore.get(), ! statoSensore.get(switchSensore.get().value));
		} else if (adiacenti(switchSensore.get())){ 
			statoSensore.set(switchSensore.get(), ! statoSensore.get(switchSensore.get().value));
		}
	}
	
	
	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored(){
    }
	
	// applicazione dell'aggiornamento del set
	void fireUpdateSet(){
		
	  statoSensore.oldValues = statoSensore.newValues;
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


