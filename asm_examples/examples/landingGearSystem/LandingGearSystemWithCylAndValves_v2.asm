//http://www.irit.fr/ABZ2014/landing_system.pdf

//first refinement
//doors and gears are derived

asm LandingGearSystemWithCylAndValves_v2

import ../../STDL/StandardLibrary
import ../../STDL/CTLlibrary
import ../../STDL/LTLlibrary

signature:
	enum domain HandleStatus = {UP | DOWN}
	enum domain DoorStatus = {CLOSED | OPENING | OPEN | CLOSING}
	enum domain GearStatus = {RETRACTED | EXTENDING | EXTENDED | RETRACTING}
	enum domain CylinderStatus = {CYLINDER_RETRACTED | CYLINDER_EXTENDING | CYLINDER_EXTENDED | CYLINDER_RETRACTING}
	dynamic monitored handle: HandleStatus

	derived doors: DoorStatus
	derived gears: GearStatus
	
	dynamic controlled cylindersDoors: CylinderStatus
	dynamic controlled cylindersGears: CylinderStatus

	dynamic controlled generalElectroValve: Boolean
	dynamic controlled openDoorsElectroValve: Boolean
	dynamic controlled closeDoorsElectroValve: Boolean
	dynamic controlled retractGearsElectroValve: Boolean
	dynamic controlled extendGearsElectroValve: Boolean

definitions:
	/*function cylindersDoors =
		switch doors
			case OPEN:
				CYLINDER_EXTENDED
			case OPENING:
				CYLINDER_EXTENDING
			case CLOSING:
				CYLINDER_RETRACTING
			case CLOSED:
				CYLINDER_RETRACTED
		endswitch

	function cylindersGears =
		switch gears
			case RETRACTED:
				CYLINDER_RETRACTED
			case EXTENDING:
				CYLINDER_EXTENDING
			case EXTENDED:
				CYLINDER_EXTENDED
			case RETRACTING:
				CYLINDER_RETRACTING
		endswitch*/

	function doors =
		switch cylindersDoors
			case CYLINDER_EXTENDED:
				OPEN
			case CYLINDER_EXTENDING:
				OPENING
			case CYLINDER_RETRACTING:
				CLOSING
			case CYLINDER_RETRACTED:
				CLOSED
		endswitch

	function gears =
		switch cylindersGears
			case CYLINDER_RETRACTED:
				RETRACTED
			case CYLINDER_EXTENDING:
				EXTENDING
			case CYLINDER_EXTENDED:
				EXTENDED
			case CYLINDER_RETRACTING:
				RETRACTING
		endswitch

	rule r_closeDoor =
		switch doors
			case OPEN:
				par
					closeDoorsElectroValve := true
					//doors := CLOSING
					cylindersDoors := CYLINDER_RETRACTING
				endpar
			case CLOSING:
				par
					generalElectroValve := false //quando si chiude la porta vuol dire che una manovra è terminata. quindi si può spegnere la valvola generale
					closeDoorsElectroValve := false
					//doors := CLOSED
					cylindersDoors := CYLINDER_RETRACTED
				endpar
			case OPENING:
				par
					closeDoorsElectroValve := true
					openDoorsElectroValve := false
					//doors := CLOSING
					cylindersDoors := CYLINDER_RETRACTING
				endpar
		endswitch

	rule r_retractionSequence =
		if gears != RETRACTED then
			switch doors
				case CLOSED:
					par
						generalElectroValve := true //si apre la valvola generale quando la porta è chiusa con le ruote estese (e si riceve il segnale UP)
						openDoorsElectroValve := true
						//doors := OPENING
						cylindersDoors := CYLINDER_EXTENDING
					endpar
				case CLOSING:
					par
						closeDoorsElectroValve := false
						openDoorsElectroValve := true
						//doors := OPENING
						cylindersDoors := CYLINDER_EXTENDING
					endpar
				case OPENING:
					par
						openDoorsElectroValve := false
						//doors := OPEN
						cylindersDoors := CYLINDER_EXTENDED
					endpar
				case OPEN:
					switch gears
						case EXTENDED:
							par
								retractGearsElectroValve := true
								//gears := RETRACTING
								cylindersGears := CYLINDER_RETRACTING
							endpar
						case RETRACTING:
							par
								retractGearsElectroValve := false
								//gears := RETRACTED
								cylindersGears := CYLINDER_RETRACTED
							endpar
						case EXTENDING:
							par
								extendGearsElectroValve := false
								retractGearsElectroValve := true
								//gears := RETRACTING
								cylindersGears := CYLINDER_RETRACTING
							endpar
					endswitch
			endswitch
		else
			r_closeDoor[]
		endif

	rule r_outgoingSequence =
		if gears != EXTENDED then
			switch doors
				case CLOSED:
					par
						generalElectroValve := true //si apre la valvola generale quando la porta è chiusa con le ruote retratte (e si riceve il segnale DOWN)
						openDoorsElectroValve := true
						//doors := OPENING
						cylindersDoors := CYLINDER_EXTENDING
					endpar
				case OPENING:
					par
						openDoorsElectroValve := false
						//doors := OPEN
						cylindersDoors := CYLINDER_EXTENDED
					endpar
				case CLOSING:
					par
						closeDoorsElectroValve := false
						openDoorsElectroValve := true
						//doors := OPENING
						cylindersDoors := CYLINDER_EXTENDING
					endpar
				case OPEN:
					switch gears
						case RETRACTED:
							par
								extendGearsElectroValve := true
								//gears := EXTENDING
								cylindersGears := CYLINDER_EXTENDING
							endpar
						case EXTENDING:
							par
								extendGearsElectroValve := false
								//gears := EXTENDED
								cylindersGears := CYLINDER_EXTENDED
							endpar
						case RETRACTING:
							par
								extendGearsElectroValve := true
								retractGearsElectroValve := false
								//gears := EXTENDING
								cylindersGears := CYLINDER_EXTENDING
							endpar
					endswitch
			endswitch
		else
			r_closeDoor[]
		endif

	//sono anche tradotti in proprietà CTL 
	invariant over gears, doors: (gears = EXTENDING or gears = RETRACTING) implies doors = OPEN
	invariant over gears, doors: doors = CLOSED implies (gears = EXTENDED or gears = RETRACTED)

	CTLSPEC ag((doors = CLOSED and gears = EXTENDED and ag(handle = DOWN)) implies ag(doors = CLOSED and gears = EXTENDED))
	CTLSPEC ag((doors = CLOSED and gears = RETRACTED and ag(handle = UP)) implies ag(doors = CLOSED and gears = RETRACTED))
	CTLSPEC ag((gears = EXTENDING and handle = DOWN) implies ax(gears = EXTENDED))
	CTLSPEC ag((gears = EXTENDING and handle = UP) implies ax(gears = RETRACTING))
	CTLSPEC ag((gears = RETRACTING and handle = UP) implies ax(gears = RETRACTED))
	CTLSPEC ag((gears = RETRACTING and handle = DOWN) implies ax(gears = EXTENDING))

	CTLSPEC ag((gears = EXTENDED and doors = CLOSING and handle = DOWN) implies ax(doors = CLOSED))
	CTLSPEC ag((gears = EXTENDED and doors = CLOSING and handle = UP) implies ax(doors = OPENING))
	CTLSPEC ag((gears = RETRACTED and doors = OPENING and handle = DOWN) implies ax(doors = OPEN))
	CTLSPEC ag((gears = RETRACTED and doors = OPENING and handle = UP) implies ax(doors = CLOSING))

	CTLSPEC ag(generalElectroValve implies ef(not(generalElectroValve)))
	
	//R11_bis
	//CTLSPEC ag(ag(handle = DOWN) implies af(gears = EXTENDED and doors = CLOSED)) //WRONG
	//CTLSPEC ag(eg(handle = DOWN) implies ef(gears = EXTENDED and doors = CLOSED)) //TOO WEAK
	LTLSPEC g(g(handle = DOWN) implies f(gears = EXTENDED and doors = CLOSED))
	//R12_bis
	//CTLSPEC ag(ag(handle = UP) implies af(gears = RETRACTED and doors = CLOSED)) //WRONG
	//CTLSPEC ag(eg(handle = UP) implies ef(gears = RETRACTED and doors = CLOSED)) //TOO WEAK
	LTLSPEC g(g(handle = UP) implies f(gears = RETRACTED and doors = CLOSED))
	//R21
	//CTLSPEC ag(ag(handle = DOWN) implies ax(ag(gears != RETRACTING))) //WRONG
	//CTLSPEC ag(eg(handle = DOWN) implies ex(eg(gears != RETRACTING))) //TOO WEAK
	LTLSPEC g(g(handle = DOWN) implies x(g(gears != RETRACTING)))
	//R22
	//CTLSPEC ag(ag(handle = UP) implies ax(ag(gears != EXTENDING))) //WRONG
	//CTLSPEC ag(eg(handle = UP) implies ex(eg(gears != EXTENDING))) //TOO WEAK
	LTLSPEC g(g(handle = UP) implies x(g(gears != EXTENDING)))

	//R31
	CTLSPEC ag((extendGearsElectroValve or retractGearsElectroValve) implies doors = OPEN)
	LTLSPEC g((extendGearsElectroValve or retractGearsElectroValve) implies doors = OPEN)
	//R32
	CTLSPEC ag((openDoorsElectroValve or closeDoorsElectroValve) implies (gears = RETRACTED or gears = EXTENDED))
	LTLSPEC g((openDoorsElectroValve or closeDoorsElectroValve) implies (gears = RETRACTED or gears = EXTENDED))
	//R41
	CTLSPEC ag(not(openDoorsElectroValve and closeDoorsElectroValve))
	LTLSPEC g(not(openDoorsElectroValve and closeDoorsElectroValve))
	//R42
	CTLSPEC ag(not(extendGearsElectroValve and retractGearsElectroValve))
	LTLSPEC g(not(extendGearsElectroValve and retractGearsElectroValve))
	//R51
	CTLSPEC ag((openDoorsElectroValve or closeDoorsElectroValve or extendGearsElectroValve or retractGearsElectroValve) implies generalElectroValve)
	LTLSPEC g((openDoorsElectroValve or closeDoorsElectroValve or extendGearsElectroValve or retractGearsElectroValve) implies generalElectroValve)

	//Deadlock freeness
	CTLSPEC ag(ef(handle = UP)) //the handle can always become UP
	CTLSPEC ag(ef(handle = DOWN)) //the handle can always become DOWN
	//LTLSPEC (forall $s in DoorStatus with g(f(doors = $s))) //this fails
	//LTLSPEC (forall $s in GearStatus with g(f(gears = $s))) //this fails
	CTLSPEC (forall $s in DoorStatus with ag(ef(doors = $s)))
	CTLSPEC (forall $s in GearStatus with ag(ef(gears = $s)))

	main rule r_Main =
		if handle = UP then
			r_retractionSequence[]
		else
			r_outgoingSequence[]
		endif

default init s0:
	//function doors = CLOSED
	//function gears = EXTENDED
	function cylindersDoors = CYLINDER_RETRACTED
	function cylindersGears = CYLINDER_EXTENDED
	function generalElectroValve = false
	function extendGearsElectroValve = false
	function retractGearsElectroValve = false
	function openDoorsElectroValve = false
	function closeDoorsElectroValve = false