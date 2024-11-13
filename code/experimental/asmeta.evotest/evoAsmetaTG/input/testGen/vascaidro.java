
// vascaidro.java automatically generated from ASM2CODE
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import java.util.Map;
import java.util.Set;
import java.util.List;

import org.apache.commons.collections4.Bag;

class vascaidro {
	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	static class Livello {
		private static List<Integer> elems = new ArrayList<>();
		Integer value;

		static Livello valueOf(Integer val) {
			Livello n = new Livello();
			n.value = val;
			return n;
		}

		static Livello valueOf(Livello val) {
			return val;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Livello))
				return false;
			return value.equals(((Livello) obj).value);
		}

		@Override
		public int hashCode() {
			return value.hashCode();
		}
	}

	Livello Livello_elem = new Livello();
	List<Integer> Livello_elems = new ArrayList<>();

	//Metodi di supporto per l'implementazione delle funzioni controlled
	class Fun0Ctrl<D> {
		D oldValue;
		D newValue;

		void set(D d) {
			newValue = d;
		}

		D get() {
			return oldValue;
		}
	}

	static class FunNCtrl<D, C> {
		Map<D, C> oldValues = new HashMap<>();
		Map<D, C> newValues = new HashMap<>();

		void set(D d, C c) {
			newValues.put(d, c);
		}

		C get(D d) {
			return oldValues.get(d);
		}
	}

	//Metodi di supporto per l'implementazione delle funzioni non controlled
	class Fun0<D> {
		D value;

		void set(D d) {
			value = d;
		}

		D get() {
			return value;
		}
	}

	class FunN<D, C> {
		Map<D, C> values = new HashMap<>();

		void set(D d, C c) {
			values.put(d, c);
		}

		C get(D d) {
			return values.get(d);
		}
	}

	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	//Funzione di tipo Controlled
	Fun0Ctrl<Livello> statoLivello = new Fun0Ctrl<>();
	//Funzione di tipo monitored
	Fun0<Boolean> riempi_completamente = new Fun0<>();
	//Funzione di tipo monitored
	Fun0<Boolean> svuota_completamente = new Fun0<>();
	//Funzione di tipo monitored
	Fun0<Boolean> riempi_25_percento = new Fun0<>();
	//Funzione di tipo monitored
	Fun0<Boolean> svuota_25_percento = new Fun0<>();

	// Inizializzazione di funzioni e domini
	vascaidro() {
		//Definizione iniziale dei domini statici
		Livello.elems = Collections.unmodifiableList(Arrays.asList(0, 25, 50, 75, 100));
		Livello_elems = Collections.unmodifiableList(Arrays.asList(0, 25, 50, 75, 100));
		//Definizione iniziale dei domini dinamici
		//Definizione iniziale dei domini astratti con funzini statiche
		//Inizializzazione delle funzioni
		Livello_elem.value = 0;
		statoLivello.oldValue = statoLivello.newValue = Livello_elem;
	}

	// Definizione delle funzioni statiche
	// Conversione delle regole ASM in metodi java
	boolean branch_r_IncDec_master = false;
	boolean branch_r_IncDec_1 = false;
	boolean branch_r_IncDec_2 = false;

	void r_IncDec(Boolean _b) {
		branch_r_IncDec_master = true;
		if (Boolean.TRUE.equals(_b)) {
			branch_r_IncDec_1 = true;
			Livello Livello411506101_s = new Livello();
			Livello411506101_s.value = (//statoLivello.get().value
			(statoLivello.get().value + 25));
			statoLivello.set(Livello411506101_s);
		} else {
			branch_r_IncDec_2 = true;
			Livello Livello1997859171_s = new Livello();
			Livello1997859171_s.value = (//statoLivello.get().value
			(statoLivello.get().value - 25));
			statoLivello.set(Livello1997859171_s);
		}
	}

	boolean branch_r_Main_master = false;
	boolean branch_r_Main_1 = false;
	boolean branch_r_Main_2 = false;
	boolean branch_r_Main_3 = false;
	boolean branch_r_Main_4 = false;
	boolean branch_r_Main_5 = false;
	boolean branch_r_Main_6 = false;
	boolean branch_r_Main_7 = false;
	boolean branch_r_Main_8 = false;
	boolean branch_r_Main_9 = false;
	boolean branch_r_Main_10 = false;
	boolean branch_r_Main_11 = false;
	boolean branch_r_Main_12 = false;

	void r_Main() {
		branch_r_Main_master = true;
		if (Boolean.TRUE.equals(riempi_completamente.get())) {
			branch_r_Main_1 = true;
			Livello Livello422396878_s = new Livello();
			Livello422396878_s.value = (//statoLivello.get().value
			100);
			statoLivello.set(Livello422396878_s);
		} else {
			branch_r_Main_2 = true;
			if (Boolean.TRUE.equals(svuota_completamente.get())) {
				branch_r_Main_3 = true;
				Livello Livello1125736023_s = new Livello();
				Livello1125736023_s.value = (//statoLivello.get().value
				0);
				statoLivello.set(Livello1125736023_s);
			} else {
				branch_r_Main_4 = true;
				if (Boolean.TRUE.equals(riempi_25_percento.get())) {
					branch_r_Main_5 = true;
					if (Boolean.TRUE.equals(((statoLivello.get().value + 25) > 100))) {
						branch_r_Main_6 = true;
						// Empty rule 
					} else {
						branch_r_Main_7 = true;
						r_IncDec(true);
					}
				} else {
					branch_r_Main_8 = true;
					if (Boolean.TRUE.equals(svuota_25_percento.get())) {
						branch_r_Main_9 = true;
						if (Boolean.TRUE.equals(((statoLivello.get().value - 25) < 0))) {
							branch_r_Main_10 = true;
							// Empty rule 
						} else {
							branch_r_Main_11 = true;
							r_IncDec(false);
						}
					} else {
						branch_r_Main_12 = true;
						// Empty rule 
					}
				}
			}
		}
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
		// No controlled functions initialized with monitored ones have been found
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {
		statoLivello.oldValue = statoLivello.newValue;
	}

	//Metodo per l'aggiornamento dell'asm
	void updateASM() {
		r_Main();
		fireUpdateSet();
		initControlledWithMonitored();
	}
}
