// Rubrica.java automatically generated from ASM2CODE

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


abstract class Rubrica_sig {
	
	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo astratto
	
	static class Contact {
		
	static List<Contact> elems = new ArrayList<Contact>();
	static List<String> val = new ArrayList<String>();
	
	Contact (String a)
	{
	    elems.add(this);
	    val.add(a);
	}
	
	String ToString(Contact a)
	{
	 if(elems.contains(a))
	 {
	   return val.get(elems.lastIndexOf(a));
	 }
	
	else return null;
	}
	
	static Contact get(String a)
	{
	  if(val.contains(a))
	  {
	 
	  	return elems.get(val.lastIndexOf(a));
	  } 
	 
	  else return null;
	 }
	
	}
	
	List<String> Contact_lista = new ArrayList<String>();
	List<Contact> Contact_Class = new ArrayList<Contact>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static enum State {SCELTA, INSCONTACT, DELCONTACT, ERROR}
				
	List<State> State_lista = new ArrayList<State>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static enum Service {INS, DEL}
				
	List<Service> Service_lista = new ArrayList<Service>();
	
	
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
	zeroC <State> rubricaState = new zeroC<>();
	
	
	//Funzione di tipo Controlled
	List<String>  contacts_elem = new ArrayList<String> ();
					
	zeroC<List<String> > contacts = new zeroC<>();
	
	
	//Funzione di tipo Controlled
	zeroC <Object> outmex = new zeroC<>();
	
	
	//Funzione di tipo monitored
	zero<Service> selectedService = new zero<>();
	
	
	//Funzione di tipo monitored
	zero<Contact> selectedContact = new zero<>();
	
	
	//Funzione di tipo statico
	static Contact contact1;
	
	//Funzione di tipo statico
	static Contact contact2;
	
	//Funzione di tipo statico
	static Contact contact3;
	
	
	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	
	abstract void r_scelta();
	
	abstract void r_ins();
	
	abstract void r_del();
	
	abstract void r_main();
	
}



class Rubrica extends Rubrica_sig {
	
	// Inizializzazione di funzioni e domini
	
	Rubrica(){
	
     //Definizione iniziale dei domini statici
     
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(State i : State.values())
	 State_lista.add(i);
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(Service i : Service.values())
	 Service_lista.add(i);
	 
	
	 //Definizione iniziale dei domini dinamici
	 
	
	 //Definizione iniziale dei domini astratti con funzini statiche
	 
	 contact1 = new Contact("contact1");
	 Contact_lista.add("contact1");
	 Contact_Class.add(contact1);
	 contact2 = new Contact("contact2");
	 Contact_lista.add("contact2");
	 Contact_Class.add(contact2);
	 contact3 = new Contact("contact3");
	 Contact_lista.add("contact3");
	 Contact_Class.add(contact3);
	
	 //Inizializzazione delle funzioni
	 
	 rubricaState.oldValue = rubricaState.newValue = State.SCELTA;
	
	}
	
    // Definizione delle funzioni statiche
	
	// Conversione delle regole ASM in metodi java
	
	@Override
	void r_scelta(){
		if ((selectedService.get() == Service.INS)){ 
				rubricaState.set(State.INSCONTACT);
		} else if ((selectedService.get() == Service.DEL)){ 
			rubricaState.set(State.DELCONTACT);
		}
	}
	
	@Override
	void r_ins(){
		if ((rubricaState.get() == State.INSCONTACT)){ 
			{ //par
				outmex.set("Contact inserted!");
				rubricaState.set(State.SCELTA);
			}//endpar
		}
	}
	
	@Override
	void r_del(){
		if ((rubricaState.get() == State.DELCONTACT)){ 
			{ //par
				outmex.set("Contact deleted!");
				rubricaState.set(State.SCELTA);
			}//endpar
		}
	}
	
	@Override
	void r_main(){
		{//seq
			r_scelta();
			
			fireUpdateSet();
			{ //par
				r_ins();
				r_del();
			}//endpar
			fireUpdateSet();
		}//endseq
	}
	
	
	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored(){
    }
	
	// applicazione dell'aggiornamento del set
	void fireUpdateSet(){
		
	  rubricaState.oldValue = rubricaState.newValue;
	  contacts.oldValue = contacts.newValue;
	  outmex.oldValue = outmex.newValue;
	}
	
	//Metodo per l'aggiornamento dell'asm
	void UpdateASM()
	{
		r_main();
		fireUpdateSet();
		initControlledWithMonitored();
	}
	
	public static void main(String[] args) {
		}
	
}


