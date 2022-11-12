// ascensore.java automatically generated from ASM2CODE

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


abstract class ascensore_sig {
	
	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	
	static class  Piano{
					
				      static List<Integer> elems = new ArrayList<Integer>();
	
	                  Integer value;
					}
					
					Piano Piano_elem = new Piano();
					
					List<Integer> Piano_elems = new ArrayList<Integer>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static enum Porta {CHIUSA, APERTA}
				
	List<Porta> Porta_lista = new ArrayList<Porta>();
	
	
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
	zeroC <Piano> statoPiano = new zeroC<>();
	
	
	//Funzione di tipo Controlled
	zeroC <Porta> statoPorta = new zeroC<>();
	
	
	//Funzione di tipo monitored
	zero<Boolean> signalPorta = new zero<>();
	
	
	
	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	
	abstract void r_Main();
	
}



class ascensore extends ascensore_sig {
	
	// Inizializzazione di funzioni e domini
	
	ascensore(){
	
     //Definizione iniziale dei domini statici
     
	 Piano.elems = Collections.unmodifiableList(Arrays.asList(1, 2));
	 Piano_elems = Collections.unmodifiableList(Arrays.asList(1, 2));
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(Porta i : Porta.values())
	 Porta_lista.add(i);
	 
	
	 //Definizione iniziale dei domini dinamici
	 
	
	 //Definizione iniziale dei domini astratti con funzini statiche
	 
	
	 //Inizializzazione delle funzioni
	 
	 
	 Piano_elem.value = 1;
	 
	 statoPiano.oldValue = statoPiano.newValue = Piano_elem;
	 
	 statoPorta.oldValue = statoPorta.newValue = Porta.CHIUSA;
	
	}
	
    // Definizione delle funzioni statiche
	
	// Conversione delle regole ASM in metodi java
	
	@Override
	void r_Main(){
		if ((statoPiano.get().value
		 == 1)){ 
					if (signalPorta.get()){ 
					statoPorta.set(Porta.APERTA);
					}else{
					{ //par
						statoPorta.set(Porta.CHIUSA);
						Piano Piano_s = new Piano();
						Piano_s.value = (//statoPiano.get().value
						2);
									   statoPiano.set(Piano_s);
					}//endpar
				}
		} else 	if (signalPorta.get()){ 
			statoPorta.set(Porta.APERTA);
			}else{
			{ //par
				statoPorta.set(Porta.CHIUSA);
				Piano Piano_s = new Piano();
				Piano_s.value = (//statoPiano.get().value
				1);
							   statoPiano.set(Piano_s);
			}//endpar
		}
	}
	
	
	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored(){
    }
	
	// applicazione dell'aggiornamento del set
	void fireUpdateSet(){
		
	  statoPiano.oldValue = statoPiano.newValue;
	  statoPorta.oldValue = statoPorta.newValue;
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


