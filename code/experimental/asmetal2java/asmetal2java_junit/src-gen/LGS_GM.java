// LGS_GM.java automatically generated from ASM2CODE

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


abstract class LGS_GM_sig {
	
	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	
	static enum HandleStatus {UP, DOWN}
				
	List<HandleStatus> HandleStatus_lista = new ArrayList<HandleStatus>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static enum DoorStatus {CLOSED, OPENING, OPEN, CLOSING}
				
	List<DoorStatus> DoorStatus_lista = new ArrayList<DoorStatus>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static enum GearStatus {RETRACTED, EXTENDING, EXTENDED, RETRACTING}
				
	List<GearStatus> GearStatus_lista = new ArrayList<GearStatus>();
	
	
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
	zero<HandleStatus> handle = new zero<>();
	
	
	//Funzione di tipo Controlled
	zeroC <DoorStatus> doors = new zeroC<>();
	
	
	//Funzione di tipo Controlled
	zeroC <GearStatus> gears = new zeroC<>();
	
	
	
	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	
	abstract void r_closeDoor();
	
	abstract void r_retractionSequence();
	
	abstract void r_outgoingSequence();
	
	abstract void r_Main();
	
}



class LGS_GM extends LGS_GM_sig {
	
	// Inizializzazione di funzioni e domini
	
	LGS_GM(){
	
     //Definizione iniziale dei domini statici
     
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(HandleStatus i : HandleStatus.values())
	 HandleStatus_lista.add(i);
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(DoorStatus i : DoorStatus.values())
	 DoorStatus_lista.add(i);
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(GearStatus i : GearStatus.values())
	 GearStatus_lista.add(i);
	 
	
	 //Definizione iniziale dei domini dinamici
	 
	
	 //Definizione iniziale dei domini astratti con funzini statiche
	 
	
	 //Inizializzazione delle funzioni
	 
	 doors.oldValue = doors.newValue = DoorStatus.CLOSED;
	 gears.oldValue = gears.newValue = GearStatus.EXTENDED;
	
	}
	
    // Definizione delle funzioni statiche
	
	// Conversione delle regole ASM in metodi java
	
	@Override
	void r_closeDoor(){
		if(doors.get()==DoorStatus.OPEN){
			doors.set(DoorStatus.CLOSING);
		}else if(doors.get()==DoorStatus.CLOSING){
			doors.set(DoorStatus.CLOSED);
		}else if(doors.get()==DoorStatus.OPENING){
			doors.set(DoorStatus.CLOSING);
		}
	}
	
	@Override
	void r_retractionSequence(){
			if ((gears.get() != GearStatus.RETRACTED)){ 
			if(doors.get()==DoorStatus.CLOSED){
				doors.set(DoorStatus.OPENING);
			}else if(doors.get()==DoorStatus.CLOSING){
				doors.set(DoorStatus.OPENING);
			}else if(doors.get()==DoorStatus.OPENING){
				doors.set(DoorStatus.OPEN);
			}else if(doors.get()==DoorStatus.OPEN){
				if(gears.get()==GearStatus.EXTENDED){
					gears.set(GearStatus.RETRACTING);
				}else if(gears.get()==GearStatus.RETRACTING){
					gears.set(GearStatus.RETRACTED);
				}else if(gears.get()==GearStatus.EXTENDING){
					gears.set(GearStatus.RETRACTING);
				}
			}
			}else{
			r_closeDoor();
		}
	}
	
	@Override
	void r_outgoingSequence(){
			if ((gears.get() != GearStatus.EXTENDED)){ 
			if(doors.get()==DoorStatus.CLOSED){
				doors.set(DoorStatus.OPENING);
			}else if(doors.get()==DoorStatus.OPENING){
				doors.set(DoorStatus.OPEN);
			}else if(doors.get()==DoorStatus.CLOSING){
				doors.set(DoorStatus.OPENING);
			}else if(doors.get()==DoorStatus.OPEN){
				if(gears.get()==GearStatus.RETRACTED){
					gears.set(GearStatus.EXTENDING);
				}else if(gears.get()==GearStatus.EXTENDING){
					gears.set(GearStatus.EXTENDED);
				}else if(gears.get()==GearStatus.RETRACTING){
					gears.set(GearStatus.EXTENDING);
				}
			}
			}else{
			r_closeDoor();
		}
	}
	
	@Override
	void r_Main(){
			if ((handle.get() == HandleStatus.UP)){ 
			r_retractionSequence();
			}else{
			r_outgoingSequence();
		}
	}
	
	
	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored(){
    }
	
	// applicazione dell'aggiornamento del set
	void fireUpdateSet(){
		
	  doors.oldValue = doors.newValue;
	  gears.oldValue = gears.newValue;
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


