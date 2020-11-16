// population.java automatically generated from ASM2CODE

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

abstract class population_sig {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo astratto
	static class Person {

		static List<Person> elems = new ArrayList<Person>();
		List<String> val = new ArrayList<String>();

		Person(String a) {
			elems.add(this);
			val.add(a);
		}

		String ToString(Person a) {
			if (elems.contains(a)) {
				return val.get(elems.lastIndexOf(a));
			}

			else
				return null;
		}

		Person get(String a) {
			if (val.contains(a)) {

				return elems.get(val.lastIndexOf(a));
			}

			else
				return null;
		}

	}

	List<String> Person_lista = new ArrayList<String>();
	List<Person> Person_Class = new ArrayList<Person>();

	//Variabile di tipo Concreto o Enumerativo

	static enum GenderDomain {
		MALE, FEMALE
	}

	List<GenderDomain> GenderDomain_lista = new ArrayList<GenderDomain>();

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
	nC<Person, GenderDomain> gender = new nC<>();

	//Funzione di tipo Controlled
	nC<Person, Integer> age = new nC<>();

	//Funzione di tipo Controlled
	nC<Person, Boolean> alive = new nC<>();

	//Funzione di tipo Controlled
	nC<Person, Person> mother = new nC<>();

	//Funzione di tipo Controlled
	nC<Person, Person> father = new nC<>();

	//Funzione di tipo statico
	static Person male1;

	//Funzione di tipo statico
	static Person female1;

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */

	abstract
	void r_reproduce(Person _p);

	abstract
	void r_dead(Person _p);

	abstract
	void r_Main();

}

class population extends population_sig {

	// Inizializzazione di funzioni e domini

	population() {

		//Definizione iniziale dei domini statici

		//setto la lista di elementi di supporto della classe enumerativa
		for(GenderDomain i : GenderDomain.values())
		GenderDomain_lista.add(i);

		//Definizione iniziale dei domini dinamici

		//Definizione iniziale dei domini astratti con funzini statiche

		male1 = new Person("male1");
		Person_lista.add("male1");
		Person_Class.add(male1);
		female1 = new Person("female1");
		Person_lista.add("female1");
		Person_Class.add(female1);

		//Inizializzazione delle funzioni

		for(Person _p: Person.elems) {
			mother.oldValues.put(_p,_p);
			mother.newValues.put(_p,_p);
		}
		for(Person _p: Person.elems) {
			father.oldValues.put(_p,_p);
			father.newValues.put(_p,_p);
		}
		for(Person _p: Person.elems) {
			Boolean a = true;

			alive.oldValues.put(_p,a);
			alive.newValues.put(_p,a);
		}
		for(Person _p: Person.elems) {
			GenderDomain a;

			gender.oldValues.put(_p,a);
			gender.newValues.put(_p,a);
		}
		for(Person _p: Person.elems) {
			Integer a = new Integer();

			a.value;
			age.oldValues.put(_p,a);
			age.newValues.put(_p,a);
		}

	}

	// Definizione delle funzioni statiche

	// Conversione delle regole ASM in metodi java

	@Override
	void r_reproduce (Person _p) {
		if ((gender.get(_p) == GenderDomain.FEMALE) && (age.get(_p) >= 13) && (age.get(_p) <= 50)) {
			List<Integer> point0 = new ArrayList<Integer>();
			point0 = Collections.unmodifiableList(Arrays.asList (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100));
			for(Integer _x : point0) {
				if(true) {
					point0.add(_x);
				}
			}
			int rndm = ThreadLocalRandom.current().nextInt(0, point0.size());
			{
				Integer _x = point0.get(rndm);
				if(point0.size()>0) {
					if ((_x <= 30)) {
						List<Person> point0 = new ArrayList<Person>();
						for(Person _father : Person.elems)
						if(alive.get(_father) && (gender.get(_father) == GenderDomain.MALE) && (age.get(_father) >= 13)) {
							point0.add(_father);
						}
						int rndm = ThreadLocalRandom.current().nextInt(0, point0.size());
						{
							Person _father = point0.get(rndm);
							if(point0.size()>0) {
								static class Person {

									static List<Person> elems = new ArrayList<Person>();
									List<String> val = new ArrayList<String>();

									Person (String a)
									{
										elems.add(this);
										val.add(a);
									}

									String ToString(Person a)
									{
										if(elems.contains(a))
										{
											return val.get(elems.lastIndexOf(a));
										}

										else return null;
									}

									Person get(String a)
									{
										if(val.contains(a))
										{

											return elems.get(val.lastIndexOf(a));
										}

										else return null;
									}

								}

								List<String> Person_lista = new ArrayList<String>();
								List<Person> Person_Class = new ArrayList<Person>();
								_child = new static class Person {

									static List<Person> elems = new ArrayList<Person>();
									List<String> val = new ArrayList<String>();

									Person (String a)
									{
										elems.add(this);
										val.add(a);
									}

									String ToString(Person a)
									{
										if(elems.contains(a))
										{
											return val.get(elems.lastIndexOf(a));
										}

										else return null;
									}

									Person get(String a)
									{
										if(val.contains(a))
										{

											return elems.get(val.lastIndexOf(a));
										}

										else return null;
									}

								}

								List<String> Person_lista = new ArrayList<String>();
								List<Person> Person_Class = new ArrayList<Person>();
								();
								List<GenderDomain> point0 = new ArrayList<GenderDomain>();
								for(GenderDomain _g : GenderDomain_lista)
								if(true) {
									point0.add(_g);
								}
								int rndm = ThreadLocalRandom.current().nextInt(0, point0.size());
								{
									GenderDomain _g = point0.get(rndm);
									if(point0.size()>0) {
										{ //par
											age.set(_child, 0);
											alive.set(_child, true);
											gender.set(_child, _g);
											mother.set(_child, _p);
											father.set(_child, _father);
										} //endpar
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	void r_dead (Person _p) {
		List<Integer> point0 = new ArrayList<Integer>();
		point0 = Collections.unmodifiableList(Arrays.asList (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100));
		for(Integer _x : point0) {
			if(true) {
				point0.add(_x);
			}
		}
		int rndm = ThreadLocalRandom.current().nextInt(0, point0.size());
		{
			Integer _x = point0.get(rndm);
			if(point0.size()>0) {
				if ((_x > 95)) {
					alive.set(_p, false);
				}
			}
		}
	}

	@Override
	void r_Main() {
		for(Person _p : Person.elems)
		if(alive.get(_p)) {
			{ //par
				age.set(_p, (age.get(_p) + 1));
				r_reproduce(_p);
				r_dead(_p);
			} //endpar
		}
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {

		gender.oldValues = gender.newValues;
		age.oldValues = age.newValues;
		alive.oldValues = alive.newValues;
		mother.oldValues = mother.newValues;
		father.oldValues = father.newValues;
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


