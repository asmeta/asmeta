// Ascensore.java automatically generated from ASM2CODE

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


abstract class Ascensore_sig {
	
	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	
	static enum Piano {UNO, DUE}
				
	List<Piano> Piano_lista = new ArrayList<Piano>();
	
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



class Ascensore extends Ascensore_sig {
	
	// Inizializzazione di funzioni e domini
	
	Ascensore(){
	
     //Definizione iniziale dei domini statici
     
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(Piano i : Piano.values())
	 Piano_lista.add(i);
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(Porta i : Porta.values())
	 Porta_lista.add(i);
	 
	
	 //Definizione iniziale dei domini dinamici
	 
	
	 //Definizione iniziale dei domini astratti con funzini statiche
	 
	
	 //Inizializzazione delle funzioni
	 
	 statoPiano.oldValue = statoPiano.newValue = Piano.UNO;
	 statoPorta.oldValue = statoPorta.newValue = Porta.CHIUSA;
	
	}
	
    // Definizione delle funzioni statiche
	
	// Conversione delle regole ASM in metodi java
	
	@Override
	void r_Main(){
		if (signalPorta.get()){ 
				if ((statoPiano.get() == Piano.UNO) && (statoPorta.get() == Porta.CHIUSA)){ 
						{ //par
							statoPiano.set(Piano.UNO);
							statoPorta.set(Porta.APERTA);
						}//endpar
				} else if ((statoPiano.get() == Piano.DUE) && (statoPorta.get() == Porta.CHIUSA)){ 
					{ //par
						statoPiano.set(Piano.UNO);
						statoPorta.set(Porta.APERTA);
					}//endpar
				}
		} else if (! signalPorta.get()){ 
			{ //par
				statoPorta.set(Porta.CHIUSA);
				if ((statoPiano.get() == Piano.UNO)){ 
						statoPiano.set(Piano.DUE);
				} else if ((statoPiano.get() == Piano.DUE)){ 
					statoPiano.set(Piano.UNO);
				}
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


