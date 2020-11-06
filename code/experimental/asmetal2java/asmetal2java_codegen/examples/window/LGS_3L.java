// LGS_3L.java automatically generated from ASM2CODE

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

abstract class LGS_3L_sig {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	static enum LandingSet {
		FRONT, LEFT, RIGHT
	}

	List<LandingSet> LandingSet_lista = new ArrayList<LandingSet>();

	//Variabile di tipo Concreto o Enumerativo

	static enum HandleStatus {
		UP, DOWN
	}

	List<HandleStatus> HandleStatus_lista = new ArrayList<HandleStatus>();

	//Variabile di tipo Concreto o Enumerativo

	static enum DoorStatus {
		CLOSED, OPENING, OPEN, CLOSING
	}

	List<DoorStatus> DoorStatus_lista = new ArrayList<DoorStatus>();

	//Variabile di tipo Concreto o Enumerativo

	static enum GearStatus {
		RETRACTED, EXTENDING, EXTENDED, RETRACTING
	}

	List<GearStatus> GearStatus_lista = new ArrayList<GearStatus>();

	//Variabile di tipo Concreto o Enumerativo

	static enum CylinderStatus {
		CYLINDER_RETRACTED,
		CYLINDER_EXTENDING,
		CYLINDER_EXTENDED,
		CYLINDER_RETRACTING
	}

	List<CylinderStatus> CylinderStatus_lista = new ArrayList<CylinderStatus>();

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
	zeroC<DoorStatus> doors = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<GearStatus> gears = new zeroC<>();

	//Funzione di tipo derived
	abstract
	CylinderStatus cylindersDoors();

	//Funzione di tipo derived
	abstract
	CylinderStatus cylindersGears();

	//Funzione di tipo Controlled
	zeroC<Boolean> generalElectroValve = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> openDoorsElectroValve = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> closeDoorsElectroValve = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> retractGearsElectroValve = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> extendGearsElectroValve = new zeroC<>();

	//Funzione di tipo monitored
	n<LandingSet, Boolean> gearsExtended = new n<>();

	//Funzione di tipo monitored
	n<LandingSet, Boolean> gearsRetracted = new n<>();

	//Funzione di tipo monitored
	n<LandingSet, Boolean> doorsClosed = new n<>();

	//Funzione di tipo monitored
	n<LandingSet, Boolean> doorsOpen = new n<>();

	//Funzione di tipo monitored
	n<LandingSet, Boolean> gearsShockAbsorber = new n<>();

	//Funzione di tipo derived
	abstract
	Boolean gearsAllExtended();

	//Funzione di tipo derived
	abstract
	Boolean gearsAllRetracted();

	//Funzione di tipo derived
	abstract
	Boolean doorsAllClosed();

	//Funzione di tipo derived
	abstract
	Boolean doorsAllOpen();

	//Funzione di tipo derived
	abstract
	Boolean gearsAllShockAbsorber();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */

	abstract
	void r_closeDoor();

	abstract
	void r_retractionSequence();

	abstract
	void r_outgoingSequence();

	abstract
	void r_Main();

}

class LGS_3L extends LGS_3L_sig {

	// Inizializzazione di funzioni e domini

	LGS_3L() {

		//Definizione iniziale dei domini statici

		//setto la lista di elementi di supporto della classe enumerativa
		for(LandingSet i : LandingSet.values())
		LandingSet_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(HandleStatus i : HandleStatus.values())
		HandleStatus_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(DoorStatus i : DoorStatus.values())
		DoorStatus_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(GearStatus i : GearStatus.values())
		GearStatus_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(CylinderStatus i : CylinderStatus.values())
		CylinderStatus_lista.add(i);

		//Definizione iniziale dei domini dinamici

		//Definizione iniziale dei domini astratti con funzini statiche

		//Inizializzazione delle funzioni

		doors.oldValue = doors.newValue = DoorStatus.CLOSED;
		gears.oldValue = gears.newValue = GearStatus.EXTENDED;
		generalElectroValve.oldValue = generalElectroValve.newValue = false;
		extendGearsElectroValve.oldValue = extendGearsElectroValve.newValue = false;
		retractGearsElectroValve.oldValue = retractGearsElectroValve.newValue = false;
		openDoorsElectroValve.oldValue = openDoorsElectroValve.newValue = false;
		closeDoorsElectroValve.oldValue = closeDoorsElectroValve.newValue = false;

	}

	// Definizione delle funzioni statiche
	Boolean gearsAllExtended() {return
		/*<--- forAllTerm*/
		LandingSet_lista.stream().allMatch(c -> gearsExtended.get(c));
	}
	Boolean gearsAllRetracted() {return
		/*<--- forAllTerm*/
		LandingSet_lista.stream().allMatch(c -> gearsRetracted.get(c));
	}
	Boolean doorsAllClosed() {return
		/*<--- forAllTerm*/
		LandingSet_lista.stream().allMatch(c -> doorsClosed.get(c));
	}
	Boolean doorsAllOpen() {return
		/*<--- forAllTerm*/
		LandingSet_lista.stream().allMatch(c -> doorsOpen.get(c));
	}
	Boolean gearsAllShockAbsorber() {return
		/*<--- forAllTerm*/
		LandingSet_lista.stream().allMatch(c -> gearsShockAbsorber.get(c));
	}
	CylinderStatus cylindersDoors() {if(doors.get()==DoorStatus.OPEN)
		return CylinderStatus.CYLINDER_EXTENDED;
		else if(doors.get()==DoorStatus.OPENING)
		return CylinderStatus.CYLINDER_EXTENDING;
		else if(doors.get()==DoorStatus.CLOSING)
		return CylinderStatus.CYLINDER_RETRACTING;
		else if(doors.get()==DoorStatus.CLOSED)
		return CylinderStatus.CYLINDER_RETRACTED;
		return null;
	}
	CylinderStatus cylindersGears() {if(gears.get()==GearStatus.RETRACTED)
		return CylinderStatus.CYLINDER_RETRACTED;
		else if(gears.get()==GearStatus.EXTENDING)
		return CylinderStatus.CYLINDER_EXTENDING;
		else if(gears.get()==GearStatus.EXTENDED)
		return CylinderStatus.CYLINDER_EXTENDED;
		else if(gears.get()==GearStatus.RETRACTING)
		return CylinderStatus.CYLINDER_RETRACTING;
		return null;
	}

	// Conversione delle regole ASM in metodi java

	@Override
	void r_closeDoor() {
		if(doors.get()==DoorStatus.OPEN) {
			{ //par
				closeDoorsElectroValve.set(true);
				doors.set(DoorStatus.CLOSING);
			} //endpar
		} else if(doors.get()==DoorStatus.CLOSING) {
			if (doorsAllClosed()) {
				{ //par
					generalElectroValve.set(false);
					closeDoorsElectroValve.set(false);
					doors.set(DoorStatus.CLOSED);
				} //endpar
			}
		} else if(doors.get()==DoorStatus.OPENING) {
			{ //par
				closeDoorsElectroValve.set(true);
				openDoorsElectroValve.set(false);
				doors.set(DoorStatus.CLOSING);
			} //endpar
		}
	}

	@Override
	void r_retractionSequence() {
		if ((gears.get() != GearStatus.RETRACTED)) {
			if(doors.get()==DoorStatus.CLOSED) {
				{ //par
					generalElectroValve.set(true);
					openDoorsElectroValve.set(true);
					doors.set(DoorStatus.OPENING);
				} //endpar
			} else if(doors.get()==DoorStatus.CLOSING) {
				{ //par
					closeDoorsElectroValve.set(false);
					openDoorsElectroValve.set(true);
					doors.set(DoorStatus.OPENING);
				} //endpar
			} else if(doors.get()==DoorStatus.OPENING) {
				if (doorsAllOpen()) {
					{ //par
						openDoorsElectroValve.set(false);
						doors.set(DoorStatus.OPEN);
					} //endpar
				}
			} else if(doors.get()==DoorStatus.OPEN) {
				if(gears.get()==GearStatus.EXTENDED) {
					if (gearsAllShockAbsorber()) {
						{ //par
							retractGearsElectroValve.set(true);
							gears.set(GearStatus.RETRACTING);
						} //endpar
					}
				} else if(gears.get()==GearStatus.RETRACTING) {
					if (gearsAllRetracted()) {
						{ //par
							retractGearsElectroValve.set(false);
							gears.set(GearStatus.RETRACTED);
						} //endpar
					}
				} else if(gears.get()==GearStatus.EXTENDING) {
					{ //par
						extendGearsElectroValve.set(false);
						retractGearsElectroValve.set(true);
						gears.set(GearStatus.RETRACTING);
					} //endpar
				}
			}
		} else {
			r_closeDoor();
		}
	}

	@Override
	void r_outgoingSequence() {
		if ((gears.get() != GearStatus.EXTENDED)) {
			if(doors.get()==DoorStatus.CLOSED) {
				{ //par
					generalElectroValve.set(true);
					openDoorsElectroValve.set(true);
					doors.set(DoorStatus.OPENING);
				} //endpar
			} else if(doors.get()==DoorStatus.OPENING) {
				if (doorsAllOpen()) {
					{ //par
						openDoorsElectroValve.set(false);
						doors.set(DoorStatus.OPEN);
					} //endpar
				}
			} else if(doors.get()==DoorStatus.CLOSING) {
				{ //par
					closeDoorsElectroValve.set(false);
					openDoorsElectroValve.set(true);
					doors.set(DoorStatus.OPENING);
				} //endpar
			} else if(doors.get()==DoorStatus.OPEN) {
				if(gears.get()==GearStatus.RETRACTED) {
					{ //par
						extendGearsElectroValve.set(true);
						gears.set(GearStatus.EXTENDING);
					} //endpar
				} else if(gears.get()==GearStatus.EXTENDING) {
					if (gearsAllExtended()) {
						{ //par
							extendGearsElectroValve.set(false);
							gears.set(GearStatus.EXTENDED);
						} //endpar
					}
				} else if(gears.get()==GearStatus.RETRACTING) {
					{ //par
						extendGearsElectroValve.set(true);
						retractGearsElectroValve.set(false);
						gears.set(GearStatus.EXTENDING);
					} //endpar
				}
			}
		} else {
			r_closeDoor();
		}
	}

	@Override
	void r_Main() {
		if ((handle.get() == HandleStatus.UP)) {
			r_retractionSequence();
		} else {
			r_outgoingSequence();
		}
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {

		doors.oldValue = doors.newValue;
		gears.oldValue = gears.newValue;
		generalElectroValve.oldValue = generalElectroValve.newValue;
		openDoorsElectroValve.oldValue = openDoorsElectroValve.newValue;
		closeDoorsElectroValve.oldValue = closeDoorsElectroValve.newValue;
		retractGearsElectroValve.oldValue = retractGearsElectroValve.newValue;
		extendGearsElectroValve.oldValue = extendGearsElectroValve.newValue;
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


