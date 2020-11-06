// ferrymanSimulator.java automatically generated from ASM2CODE

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

abstract class ferrymanSimulator_sig {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	static enum Actors {
		FERRYMAN, GOAT, CABBAGE, WOLF
	}

	List<Actors> Actors_lista = new ArrayList<Actors>();

	//Variabile di tipo Concreto o Enumerativo

	static enum Side {
		LEFT, RIGHT
	}

	List<Side> Side_lista = new ArrayList<Side>();

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
	nC<Actors, Side> position = new nC<>();

	//Funzione di tipo monitored
	zero<Actors> carry = new zero<>();

	//Funzione di tipo derived
	abstract
	Side oppositeSide(Side param0_oppositeSide);

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */

	abstract
	void r_carry(Actors _a);

	abstract
	void r_Main();

}

class ferrymanSimulator extends ferrymanSimulator_sig {

	// Inizializzazione di funzioni e domini

	ferrymanSimulator() {

		//Definizione iniziale dei domini statici

		//setto la lista di elementi di supporto della classe enumerativa
		for(Actors i : Actors.values())
		Actors_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(Side i : Side.values())
		Side_lista.add(i);

		//Definizione iniziale dei domini dinamici

		//Definizione iniziale dei domini astratti con funzini statiche

		//Inizializzazione delle funzioni

		for(Actors _a: Actors.values()) {
			Side a = Side.LEFT;

			position.oldValues.put(_a,a);
			position.newValues.put(_a,a);
		}

	}

	// Definizione delle funzioni statiche
	Side oppositeSide(Side _s) {return /*conditionalTerm*/
		((_s == Side.LEFT))
		?
		Side.RIGHT
		:
		Side.LEFT
		;}

	// Conversione delle regole ASM in metodi java

	@Override
	void r_carry (Actors _a) {
		if ((position.get(Actors.FERRYMAN) == position.get(_a))) {
			{ //par
				position.set(_a, oppositeSide(position.get(_a)));
				position.set(Actors.FERRYMAN, oppositeSide(position.get(Actors.FERRYMAN)));
			} //endpar
		}
	}

	@Override
	void r_Main() {
		r_carry(carry.get());
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {

		position.oldValues = position.newValues;
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


