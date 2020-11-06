// testDefinition4.java automatically generated from ASM2CODE

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

abstract class testDefinition4_sig {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	static class WaterpressureType {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	WaterpressureType WaterpressureType_elem = new WaterpressureType();

	List<Integer> WaterpressureType_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static class Delta {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	Delta Delta_elem = new Delta();

	List<Integer> Delta_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static enum Switch {
		ON, OFF
	}

	List<Switch> Switch_lista = new ArrayList<Switch>();

	//Variabile di tipo Concreto o Enumerativo

	static enum Pressure {
		TOOLOW, NORMAL, HIGH
	}

	List<Pressure> Pressure_lista = new ArrayList<Pressure>();

	//Variabile di tipo Concreto o Enumerativo

	static enum PhaseDomain {
		FULLYCLOSED, FULLYOPEN
	}

	List<PhaseDomain> PhaseDomain_lista = new ArrayList<PhaseDomain>();

	//Variabile di tipo Concreto o Enumerativo

	static class VectDomain {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	VectDomain VectDomain_elem = new VectDomain();

	List<Integer> VectDomain_elems = new ArrayList<Integer>();

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
	nC<Integer, Integer> vect = new nC<>();

	//Funzione di tipo Controlled
	nC<VectDomain, Integer> vect2 = new nC<>();

	//Funzione di tipo Controlled
	zeroC<WaterpressureType> waterpressure = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Pressure> pressure = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> overridden = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Switch> safetyInjection = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<PhaseDomain> phase = new zeroC<>();

	//Funzione di tipo monitored
	zero<Switch> reset = new zero<>();

	//Funzione di tipo monitored
	zero<Switch> block = new zero<>();

	//Funzione di tipo monitored
	zero<Delta> delta = new zero<>();

	Delta delta_supporto = new Delta();

	//Funzione di tipo statico
	abstract
	WaterpressureType low();

	//Funzione di tipo statico
	abstract
	WaterpressureType permit();

	//Funzione di tipo statico
	abstract
	WaterpressureType maxwp();

	//Funzione di tipo statico
	abstract
	WaterpressureType minwp();

	//Funzione di tipo statico
	abstract
	Integer n();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */

	abstract
	void r_Main();

}

class testDefinition4 extends testDefinition4_sig {

	// Inizializzazione di funzioni e domini

	testDefinition4() {

		//Definizione iniziale dei domini statici

		WaterpressureType.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100));
		WaterpressureType_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100));
		Delta.elems = Collections.unmodifiableList(Arrays.asList(-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5));
		Delta_elems = Collections.unmodifiableList(Arrays.asList(-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5));
		VectDomain.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		VectDomain_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		//setto la lista di elementi di supporto della classe enumerativa
		for(Switch i : Switch.values())
		Switch_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(Pressure i : Pressure.values())
		Pressure_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(PhaseDomain i : PhaseDomain.values())
		PhaseDomain_lista.add(i);

		//Definizione iniziale dei domini dinamici

		//Definizione iniziale dei domini astratti con funzini statiche

		//Inizializzazione delle funzioni

		WaterpressureType_elem.value = 3;

		waterpressure.oldValue = waterpressure.newValue = WaterpressureType_elem;

		pressure.oldValue = pressure.newValue = Pressure.TOOLOW;
		reset.Value = Switch.OFF;
		block.Value = Switch.OFF;
		overridden.oldValue = overridden.newValue = false;
		safetyInjection.oldValue = safetyInjection.newValue = Switch.OFF;
		phase.oldValue = phase.newValue = PhaseDomain.FULLYCLOSED;
		//NOT IMPLEMENTED IN Java (FunctionToCpp line 50)

		for(int _x=0; _x < VectDomain.elems.size(); _x++ ) {

			VectDomain_elem.value = VectDomain.elems.get(_x);

			Integer a = null;
			if(VectDomain_elem.value.equals(0))
			a = 5;
			else if(VectDomain_elem.value.equals(1))
			a = 7;
			else if(VectDomain_elem.value.equals(2))
			a = 1;
			else if(VectDomain_elem.value.equals(3))
			a = 8;
			else if(VectDomain_elem.value.equals(4))
			a = 3;
			else if(VectDomain_elem.value.equals(5))
			a = 2;
			else if(VectDomain_elem.value.equals(6))
			a = 6;
			else if(VectDomain_elem.value.equals(7))
			a = 4;
			else if(VectDomain_elem.value.equals(8))
			a = 8;
			else if(VectDomain_elem.value.equals(9))
			a = 9;
			;

			vect2.oldValues.put(VectDomain_elem,a);
			vect2.newValues.put(VectDomain_elem,a);
		}

	}

	// Definizione delle funzioni statiche
	WaterpressureType low() {

		WaterpressureType supp = new WaterpressureType();

		supp.value = 9;

		return supp;
	}
	WaterpressureType permit() {

		WaterpressureType supp = new WaterpressureType();

		supp.value = 10;

		return supp;
	}
	WaterpressureType maxwp() {

		WaterpressureType supp = new WaterpressureType();

		supp.value = 20;

		return supp;
	}
	WaterpressureType minwp() {

		WaterpressureType supp = new WaterpressureType();

		supp.value = 0;

		return supp;
	}
	Integer n() {return 10;}

	// Conversione delle regole ASM in metodi java

	@Override
	void r_Main() {
		;
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {

		vect.oldValues = vect.newValues;
		vect2.oldValues = vect2.newValues;
		waterpressure.oldValue = waterpressure.newValue;
		pressure.oldValue = pressure.newValue;
		overridden.oldValue = overridden.newValue;
		safetyInjection.oldValue = safetyInjection.newValue;
		phase.oldValue = phase.newValue;
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


