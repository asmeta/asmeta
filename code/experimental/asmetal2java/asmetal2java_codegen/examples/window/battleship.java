// battleship.java automatically generated from ASM2CODE

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


abstract class battleship_sig {
	
	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	
	static class  Coord{
					
				      static List<Integer> elems = new ArrayList<Integer>();
	
	                  Integer value;
					}
					
					Coord Coord_elem = new Coord();
					
					List<Integer> Coord_elems = new ArrayList<Integer>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static enum PlayerDom {PLAYERA, PLAYERB}
				
	List<PlayerDom> PlayerDom_lista = new ArrayList<PlayerDom>();
	
	
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
	Pair<Coord, Coord> boardA_elem;
	nC<Pair<Coord, Coord>, Boolean> boardA = new nC<>();
	
	
	//Funzione di tipo Controlled
	Pair<Coord, Coord> boardB_elem;
	nC<Pair<Coord, Coord>, Boolean> boardB = new nC<>();
	
	
	//Funzione di tipo Controlled
	zeroC <PlayerDom> turnPlayerA = new zeroC<>();
	
	
	//Funzione di tipo derived
	abstract Boolean isWinner (PlayerDom param0_isWinner);
	
	
	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	
	abstract void r_Main();
	
}



class battleship extends battleship_sig {
	
	// Inizializzazione di funzioni e domini
	
	battleship(){
	
     //Definizione iniziale dei domini statici
     
	 Coord.elems = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4));
	 Coord_elems = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4));
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(PlayerDom i : PlayerDom.values())
	 PlayerDom_lista.add(i);
	 
	
	 //Definizione iniziale dei domini dinamici
	 
	
	 //Definizione iniziale dei domini astratti con funzini statiche
	 
	
	 //Inizializzazione delle funzioni
	 
	 
	 for(int _r=0; _r < Coord.elems.size(); _r++ ){
	 	
	 	
	 	Coord_elem.value = Coord.elems.get(_r);
	 
	 
	 for(int _c=0; _c < Coord.elems.size(); _c++ ){
	 	
	 	
	 	Coord_elem.value = Coord.elems.get(_c);
	 
	 Boolean a (_r.value == 2);
	 				
	 				      boardA.oldValues.put(_r,a);
	 				      boardA.newValues.put(_r,a);
	 }}
	 
	 for(int _r=0; _r < Coord.elems.size(); _r++ ){
	 	
	 	
	 	Coord_elem.value = Coord.elems.get(_r);
	 
	 
	 for(int _c=0; _c < Coord.elems.size(); _c++ ){
	 	
	 	
	 	Coord_elem.value = Coord.elems.get(_c);
	 
	 Boolean a (_c.value == 3);
	 				
	 				      boardB.oldValues.put(_r,a);
	 				      boardB.newValues.put(_r,a);
	 }}
	 turnPlayerA.oldValue = turnPlayerA.newValue = PlayerDom.PLAYERA;
	
	}
	
    // Definizione delle funzioni statiche
	Boolean isWinner(PlayerDom _p){return /*conditionalTerm*/
		((_p == PlayerDom.PLAYERA))
		?
			
			  /*<--- forAllTerm*/
				Coord_lista.stream().allMatch(c -> ! boac));
				Coord_lista.stream().allMatch(c -> ! boac));
		:
			
			  /*<--- forAllTerm*/
				Coord_lista.stream().allMatch(c -> ! boac));
				Coord_lista.stream().allMatch(c -> ! boac));
	;}
	
	// Conversione delle regole ASM in metodi java
	
	@Override
	void r_Main(){
		if (! isWinner(PlayerDom.PLAYERA) && ! isWinner(PlayerDom.PLAYERB)){ 
				if ((turnPlayerA.get() == PlayerDom.PLAYERA)){ 
				{ //par
					List<Coord> point0 = new ArrayList<Coord>();
					List<Coord> point1 = new ArrayList<Coord>();
					for(Coord _r1 : Coord_lista)
					for(Coord _c1 : Coord_lista)
					if(true){
					point0.add(_r1);
					point1.add(_c1);
					}
					int rndm = ThreadLocalRandom.current().nextInt(0, point0.size());
					{
					Coord _r1 = point0.get(rndm);
					Coord _c1 = point1.get(rndm);
					  if(point0.size()>0){
						boardBfalse);
						 }
					}turnPlayerA.set(PlayerDom.PLAYERB);
				}//endpar
				}else{
				{ //par
					List<Coord> point0 = new ArrayList<Coord>();
					List<Coord> point1 = new ArrayList<Coord>();
					for(Coord _r2 : Coord_lista)
					for(Coord _c2 : Coord_lista)
					if(true){
					point0.add(_r2);
					point1.add(_c2);
					}
					int rndm = ThreadLocalRandom.current().nextInt(0, point0.size());
					{
					Coord _r2 = point0.get(rndm);
					Coord _c2 = point1.get(rndm);
					  if(point0.size()>0){
						boardAfalse);
						 }
					}turnPlayerA.set(PlayerDom.PLAYERA);
				}//endpar
			}
		}
	}
	
	
	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored(){
    }
	
	// applicazione dell'aggiornamento del set
	void fireUpdateSet(){
		
	  boardA.oldValues = boardA.newValues;
	  boardB.oldValues = boardB.newValues;
	  turnPlayerA.oldValue = turnPlayerA.newValue;
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


