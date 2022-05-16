//http://www.irit.fr/ABZ2014/landing_system.pdf

//nessuna gestione degli errori, dei sensori, delle valvole, dei cilindri
//gestisce le operazioni di apertura/chiusura.
//Permette di invertire la marcia
//Simula scenario 13

//Landing Gear System - Ground model

asm LGS_GM

import STDL/StandardLibrary

// signature 
signature:
	enum domain HandleStatus = {UP | DOWN}
	enum domain DoorStatus = {CLOSED | OPENING | OPEN | CLOSING}
	enum domain GearStatus = {RETRACTED | EXTENDING | EXTENDED | RETRACTING}
	dynamic monitored handle: HandleStatus
	dynamic controlled doors: DoorStatus
	dynamic controlled gears: GearStatus

definitions:
    // closing the door
	rule r_closeDoor =
		switch doors
			case OPEN:
				doors := CLOSING
			case CLOSING:
				doors := CLOSED
			case OPENING:
				doors := CLOSING
		endswitch

	rule r_retractionSequence =
		if gears != RETRACTED then
			switch doors
				case CLOSED:
					doors := OPENING
				case CLOSING:
					doors := OPENING
				case OPENING:
					doors := OPEN
				case OPEN:
					switch gears
						case EXTENDED:
							gears := RETRACTING
						case RETRACTING:
							gears := RETRACTED
						case EXTENDING:
							gears := RETRACTING
					endswitch
			endswitch
		else
			r_closeDoor[]
		endif

	rule r_outgoingSequence =
		if gears != EXTENDED then
			switch doors
				case CLOSED:
					doors := OPENING
				case OPENING:
					doors := OPEN
				case CLOSING:
					doors := OPENING
				case OPEN:
					switch gears
						case RETRACTED:
							gears := EXTENDING
						case EXTENDING:
							gears := EXTENDED
						case RETRACTING:
							gears := EXTENDING
					endswitch
			endswitch
		else
			r_closeDoor[]
		endif

	//sono anche tradotti in proprietà CTL 
	invariant over gears, doors: (gears = EXTENDING or gears = RETRACTING) implies doors = OPEN
	invariant over gears, doors: doors = CLOSED implies (gears = EXTENDED or gears = RETRACTED)

	main rule r_Main =
		if handle = UP then
			r_retractionSequence[]
		else
			r_outgoingSequence[]
		endif

default init s0:
	function doors = CLOSED
	function gears = EXTENDED
