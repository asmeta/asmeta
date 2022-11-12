// coffeeVendingMachine.java automatically generated from ASM2CODE

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


abstract class coffeeVendingMachine_sig {
	
	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	
	static enum CoinType {HALF, ONE}
				
	List<CoinType> CoinType_lista = new ArrayList<CoinType>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static enum Product {COFFEE, TEA, MILK}
				
	List<Product> Product_lista = new ArrayList<Product>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static class  QuantityDomain{
					
				      static List<Integer> elems = new ArrayList<Integer>();
	
	                  Integer value;
					}
					
					QuantityDomain QuantityDomain_elem = new QuantityDomain();
					
					List<Integer> QuantityDomain_elems = new ArrayList<Integer>();
	
	//Variabile di tipo Concreto o Enumerativo
	
	static class  CoinDomain{
					
				      static List<Integer> elems = new ArrayList<Integer>();
	
	                  Integer value;
					}
					
					CoinDomain CoinDomain_elem = new CoinDomain();
					
					List<Integer> CoinDomain_elems = new ArrayList<Integer>();
	
	
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
	zeroC <CoinDomain> coins = new zeroC<>();
	
	
	//Funzione di tipo Controlled
	nC<Product, QuantityDomain> available = new nC<>();
	
	
	//Funzione di tipo monitored
	zero<CoinType> insertedCoin = new zero<>();
	
	
	
	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	
	abstract void r_serveProduct (Product _p);
	
	abstract void r_Main();
	
}



class coffeeVendingMachine extends coffeeVendingMachine_sig {
	
	// Inizializzazione di funzioni e domini
	
	coffeeVendingMachine(){
	
     //Definizione iniziale dei domini statici
     
	 QuantityDomain.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
	 QuantityDomain_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
	 CoinDomain.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25));
	 CoinDomain_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25));
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(CoinType i : CoinType.values())
	 CoinType_lista.add(i);
	 //setto la lista di elementi di supporto della classe enumerativa
	 for(Product i : Product.values())
	 Product_lista.add(i);
	 
	
	 //Definizione iniziale dei domini dinamici
	 
	
	 //Definizione iniziale dei domini astratti con funzini statiche
	 
	
	 //Inizializzazione delle funzioni
	 
	 
	 CoinDomain_elem.value = 0;
	 
	 coins.oldValue = coins.newValue = CoinDomain_elem;
	 
	 for(Product _p: Product.values()){
	 QuantityDomain a = new QuantityDomain();
	 			    
	 			           a.value  = 10;
	 available.oldValues.put(_p,a);
	 available.newValues.put(_p,a);
	 }
	
	}
	
    // Definizione delle funzioni statiche
	
	// Conversione delle regole ASM in metodi java
	
	@Override
	void r_serveProduct (Product _p){
		{ //par
			QuantityDomain QuantityDomain_s = new QuantityDomain();
			QuantityDomain_s.value = (//available.get(_p).value
			(available.get(_p).value
			 - 1));
						   available.set(_p, QuantityDomain_s);
			CoinDomain CoinDomain_s = new CoinDomain();
			CoinDomain_s.value = (//coins.get().value
			(coins.get().value
			 + 1));
						   coins.set(CoinDomain_s);
		}//endpar
	}
	
	@Override
	void r_Main(){
		if ((coins.get().value
		 < 25)){ 
				if ((insertedCoin.get() == CoinType.HALF)){ 
				if ((available.get(Product.MILK).value
				 > 0)){ 
					r_serveProduct(Product.MILK);
				}
				}else{
				List<Product> point0 = new ArrayList<Product>();
				for(Product _p : Product_lista)
				if((_p != Product.MILK) && (available.get(_p).value
				 > 0)){
				point0.add(_p);
				}
				int rndm = ThreadLocalRandom.current().nextInt(0, point0.size());
				{
				Product _p = point0.get(rndm);
				  if(point0.size()>0){
					r_serveProduct(_p);
					 }
				}
			}
		}
	}
	
	
	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored(){
    }
	
	// applicazione dell'aggiornamento del set
	void fireUpdateSet(){
		
	  coins.oldValue = coins.newValue;
	  available.oldValues = available.newValues;
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


