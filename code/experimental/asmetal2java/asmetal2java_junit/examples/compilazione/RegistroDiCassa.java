// RegistroDiCassa.java automatically generated from ASM2CODE

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


abstract class RegistroDiCassa_sig {
	
	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo astratto
	
	static class Pizza {
		
	static List<Pizza> elems = new ArrayList<Pizza>();
	static List<String> val = new ArrayList<String>();
	
	Pizza (String a)
	{
	    elems.add(this);
	    val.add(a);
	}
	
	String ToString(Pizza a)
	{
	 if(elems.contains(a))
	 {
	   return val.get(elems.lastIndexOf(a));
	 }
	
	else return null;
	}
	
	static Pizza get(String a)
	{
	  if(val.contains(a))
	  {
	 
	  	return elems.get(val.lastIndexOf(a));
	  } 
	 
	  else return null;
	 }
	
	}
	
	List<String> Pizza_lista = new ArrayList<String>();
	List<Pizza> Pizza_Class = new ArrayList<Pizza>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static enum Stati {ATTENDI_ORDINAZIONI, SCEGLI_SE_AGGIUNGERE_PIZZA, CHIUSO, SCEGLI_TIPO_DI_PIZZA, PIZZASTANDARD_SELEZIONATA, ALTRAPIZZA_SELEZIONATA}
				
	List<Stati> Stati_lista = new ArrayList<Stati>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static enum Servizio {NEWORDINE, EXIT}
				
	List<Servizio> Servizio_lista = new ArrayList<Servizio>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static enum AggiungiPizza {SI, NO}
				
	List<AggiungiPizza> AggiungiPizza_lista = new ArrayList<AggiungiPizza>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static enum SelezioneTipoDiPizza {STANDARD, OTHER}
				
	List<SelezioneTipoDiPizza> SelezioneTipoDiPizza_lista = new ArrayList<SelezioneTipoDiPizza>();
	
	
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
	zeroC <Pizza> pizzaCorrente = new zeroC<>();
	
	
	//Funzione di tipo Controlled
	zeroC <Stati> statoCassa = new zeroC<>();
	
	
	//Funzione di tipo Controlled
	zeroC <Object> outMess = new zeroC<>();
	
	
	//Funzione di tipo statico
	abstract Integer getPrezzo (Pizza param0_getPrezzo);
	
	//Funzione di tipo monitored
	zero<Servizio> servizioSelezionato = new zero<>();
	
	
	//Funzione di tipo monitored
	zero<Pizza> pizzaInserita = new zero<>();
	
	
	//Funzione di tipo monitored
	zero<AggiungiPizza> sceltaDiAggiuntaPizza = new zero<>();
	
	
	//Funzione di tipo monitored
	zero<SelezioneTipoDiPizza> sceltaDelTipoPizza = new zero<>();
	
	
	//Funzione di tipo monitored
	zero<Integer> insertQuantita = new zero<>();
	
	
	//Funzione di tipo monitored
	zero<Integer> insertPrezzo = new zero<>();
	
	
	//Funzione di tipo statico
	static Pizza margherita;
	
	//Funzione di tipo statico
	static Pizza marinara;
	
	//Funzione di tipo statico
	static Pizza capricciosa;
	
	//Funzione di tipo Controlled
	zeroC <Integer> totale = new zeroC<>();
	
	
	
	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	
	abstract void r_aggiungiPizzaStandardAlTotale();
	
	abstract void r_aggiungiAlTotale();
	
	abstract void r_attendiOrdinazioni();
	
	abstract void r_scegliSeAggiungerePizza();
	
	abstract void r_scegliTipoDiPizza();
	
	abstract void r_pizzaStandardSelezionata();
	
	abstract void r_altraPizzaSelezionata();
	
	abstract void r_Main();
	
}



class RegistroDiCassa extends RegistroDiCassa_sig {
	
	// Inizializzazione di funzioni e domini
	
	RegistroDiCassa(){
	
     //Definizione iniziale dei domini statici
     
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(Stati i : Stati.values())
	 Stati_lista.add(i);
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(Servizio i : Servizio.values())
	 Servizio_lista.add(i);
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(AggiungiPizza i : AggiungiPizza.values())
	 AggiungiPizza_lista.add(i);
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(SelezioneTipoDiPizza i : SelezioneTipoDiPizza.values())
	 SelezioneTipoDiPizza_lista.add(i);
	 
	
	 //Definizione iniziale dei domini dinamici
	 
	
	 //Definizione iniziale dei domini astratti con funzini statiche
	 
	 margherita = new Pizza("margherita");
	 Pizza_lista.add("margherita");
	 Pizza_Class.add(margherita);
	 marinara = new Pizza("marinara");
	 Pizza_lista.add("marinara");
	 Pizza_Class.add(marinara);
	 capricciosa = new Pizza("capricciosa");
	 Pizza_lista.add("capricciosa");
	 Pizza_Class.add(capricciosa);
	
	 //Inizializzazione delle funzioni
	 
	 totale.oldValue = totale.newValue = 0;
	 statoCassa.oldValue = statoCassa.newValue = Stati.ATTENDI_ORDINAZIONI;
	
	}
	
    // Definizione delle funzioni statiche
	Integer getPrezzo(Pizza _c){ 	if(_c==margherita) 
			return 4;
		else if(_c==marinara)
			return 3;
		else if(_c==capricciosa)
			return 5;
	 return null;
	}
	
	// Conversione delle regole ASM in metodi java
	
	@Override
	void r_aggiungiPizzaStandardAlTotale(){
		{//seq
			totale.set((totale.get() + (getPrezzo(pizzaCorrente.get()) * insertQuantita.get())));
			totale.oldValues = totale.newValues;
			
			fireUpdateSet();
			outMess.set("prezzo totale aggiornato");
			outMess.oldValues = outMess.newValues;
			
			fireUpdateSet();
		}//endseq
	}
	
	@Override
	void r_aggiungiAlTotale(){
		{//seq
			totale.set((totale.get() + (insertQuantita.get() * insertPrezzo.get())));
			totale.oldValues = totale.newValues;
			
			fireUpdateSet();
			outMess.set("prezzo totale aggiornato");
			outMess.oldValues = outMess.newValues;
			
			fireUpdateSet();
		}//endseq
	}
	
	@Override
	void r_attendiOrdinazioni(){
		if ((statoCassa.get() == Stati.ATTENDI_ORDINAZIONI)){ 
			{ //par
				if ((servizioSelezionato.get() == Servizio.EXIT)){ 
					{ //par
						statoCassa.set(Stati.CHIUSO);
						outMess.set("Registro di cassa chiuso!");
					}//endpar
				}
				if ((servizioSelezionato.get() == Servizio.NEWORDINE)){ 
					{ //par
						totale.set(0);
						statoCassa.set(Stati.SCEGLI_SE_AGGIUNGERE_PIZZA);
						outMess.set("Scegli se aggiungere una nuova pizza");
					}//endpar
				}
			}//endpar
		}
	}
	
	@Override
	void r_scegliSeAggiungerePizza(){
		if ((statoCassa.get() == Stati.SCEGLI_SE_AGGIUNGERE_PIZZA)){ 
			{ //par
				if ((sceltaDiAggiuntaPizza.get() == AggiungiPizza.SI)){ 
					{ //par
						statoCassa.set(Stati.SCEGLI_TIPO_DI_PIZZA);
						outMess.set("Scegli il tipo di pizza desiderata:");
					}//endpar
				}
				if ((sceltaDiAggiuntaPizza.get() == AggiungiPizza.NO)){ 
					{//seq
						outMess.set("Il totale :");
						outMess.oldValues = outMess.newValues;
						
						fireUpdateSet();
						outMess.set(totale.get());
						outMess.oldValues = outMess.newValues;
						
						fireUpdateSet();
						statoCassa.set(Stati.ATTENDI_ORDINAZIONI);
						statoCassa.oldValues = statoCassa.newValues;
						
						fireUpdateSet();
					}//endseq
				}
			}//endpar
		}
	}
	
	@Override
	void r_scegliTipoDiPizza(){
		if ((statoCassa.get() == Stati.SCEGLI_TIPO_DI_PIZZA)){ 
			{ //par
				if ((sceltaDelTipoPizza.get() == SelezioneTipoDiPizza.STANDARD)){ 
					{ //par
						statoCassa.set(Stati.PIZZASTANDARD_SELEZIONATA);
						outMess.set("Inserisci il nome di una pizza dell'elenco:");
					}//endpar
				}
				if ((sceltaDelTipoPizza.get() == SelezioneTipoDiPizza.OTHER)){ 
					{ //par
						statoCassa.set(Stati.ALTRAPIZZA_SELEZIONATA);
						outMess.set("Inserisci il nome di una nuova pizza:");
					}//endpar
				}
			}//endpar
		}
	}
	
	@Override
	void r_pizzaStandardSelezionata(){
		if ((statoCassa.get() == Stati.PIZZASTANDARD_SELEZIONATA)){ 
				if (	Pizza.elems.stream().anyMatch(c -> c.ToString(c).equals(pizzaInserita.get().ToString(c)))
				){ 
				{//seq
					pizzaCorrente.set(pizzaInserita.get());
					pizzaCorrente.oldValues = pizzaCorrente.newValues;
					
					fireUpdateSet();
					r_aggiungiPizzaStandardAlTotale();
					
					fireUpdateSet();
					statoCassa.set(Stati.SCEGLI_SE_AGGIUNGERE_PIZZA);
					statoCassa.oldValues = statoCassa.newValues;
					
					fireUpdateSet();
				}//endseq
				}else{
				{//seq
					outMess.set("Questa pizza non  presente in elenco!");
					outMess.oldValues = outMess.newValues;
					
					fireUpdateSet();
					statoCassa.set(Stati.SCEGLI_SE_AGGIUNGERE_PIZZA);
					statoCassa.oldValues = statoCassa.newValues;
					
					fireUpdateSet();
				}//endseq
			}
		}
	}
	
	@Override
	void r_altraPizzaSelezionata(){
		if ((statoCassa.get() == Stati.ALTRAPIZZA_SELEZIONATA)){ 
			{//seq
				r_aggiungiAlTotale();
				
				fireUpdateSet();
				statoCassa.set(Stati.SCEGLI_SE_AGGIUNGERE_PIZZA);
				statoCassa.oldValues = statoCassa.newValues;
				
				fireUpdateSet();
			}//endseq
		}
	}
	
	@Override
	void r_Main(){
		{//seq
			r_attendiOrdinazioni();
			
			fireUpdateSet();
			{ //par
				r_scegliSeAggiungerePizza();
				r_scegliTipoDiPizza();
				r_pizzaStandardSelezionata();
				r_altraPizzaSelezionata();
			}//endpar
			fireUpdateSet();
		}//endseq
	}
	
	
	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored(){
    }
	
	// applicazione dell'aggiornamento del set
	void fireUpdateSet(){
		
	  pizzaCorrente.oldValue = pizzaCorrente.newValue;
	  statoCassa.oldValue = statoCassa.newValue;
	  outMess.oldValue = outMess.newValue;
	  totale.oldValue = totale.newValue;
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


