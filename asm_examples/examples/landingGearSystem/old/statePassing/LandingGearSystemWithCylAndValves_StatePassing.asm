//http://www.irit.fr/ABZ2014/landing_system.pdf

//c'è un passaggio di stato tra l'attivazione del sensore della porta/gear il cambiamento di stato della porta/gear 

asm LandingGearSystemWithCylAndValves_StatePassing

import ../../../../STDL/StandardLibrary
import ../../../../STDL/CTLLibrary

signature:
	enum domain HandleStatus = {UP | DOWN}
	enum domain DoorStatus = {CLOSED | OPENING | OPEN | CLOSING}
	enum domain GearStatus = {RETRACTED | EXTENDING | EXTENDED | RETRACTING}
	enum domain CylinderStatus = {CYLINDER_RETRACTED | CYLINDER_EXTENDING | CYLINDER_EXTENDED | CYLINDER_RETRACTING}
	dynamic monitored handle: HandleStatus
	dynamic controlled doors: DoorStatus
	dynamic controlled gears: GearStatus
	
	derived cylinderDoorStatus: CylinderStatus
	derived cylinderGearStatus: CylinderStatus

	dynamic controlled generalElectroValve: Boolean
	dynamic controlled openDoorsElectroValve: Boolean
	dynamic controlled closeDoorsElectroValve: Boolean
	dynamic controlled retractGearsElectroValve: Boolean
	dynamic controlled extendGearsElectroValve: Boolean

definitions:
	function cylinderDoorStatus =
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

	function cylinderGearStatus =
		switch gears
			case RETRACTED:
				CYLINDER_RETRACTED
			case EXTENDING:
				CYLINDER_EXTENDING
			case EXTENDED:
				CYLINDER_EXTENDED
			case RETRACTING:
				CYLINDER_RETRACTING
		endswitch

	rule r_closeDoor =
		switch doors
			case OPEN:
				if not(closeDoorsElectroValve) then
					closeDoorsElectroValve := true
				else
					doors := CLOSING
				endif
			case CLOSING:
				par
					generalElectroValve := false //quando si chiude la porta vuol dire che una manovra è terminata. quindi si può spegnere la valvola generale
					closeDoorsElectroValve := false
					doors := CLOSED
				endpar
			case OPENING:
				/*if openDoorsElectroValve then
					par
						closeDoorsElectroValve := true
						openDoorsElectroValve := false
					endpar
				else
					doors := CLOSING
				endif*/
				par
					closeDoorsElectroValve := true
					openDoorsElectroValve := false
					doors := CLOSING
				endpar
		endswitch

	rule r_retractingLandingSequence =
		if gears != RETRACTED then
			switch doors
				case CLOSED:
					if not(generalElectroValve) then
						par
							generalElectroValve := true //si apre la valvola generale quando la porta è chiusa con le ruote estese (e si riceve il segnale UP)
							openDoorsElectroValve := true
						endpar
					else
						doors := OPENING
					endif
				case CLOSING:
					/*if closeDoorsElectroValve then
						par
							closeDoorsElectroValve := false
							openDoorsElectroValve := true
						endpar
					else
						doors := OPENING
					endif*/
					par
						closeDoorsElectroValve := false
						openDoorsElectroValve := true
						doors := OPENING
					endpar
				case OPENING:
					par
						openDoorsElectroValve := false
						doors := OPEN
					endpar
				case OPEN:
					switch gears
						case EXTENDED:
							if not(retractGearsElectroValve) then
								par
									closeDoorsElectroValve := false //per risolvere il problema dovuto al passaggio di stato
									retractGearsElectroValve := true
								endpar
							else
								gears := RETRACTING
							endif
						case RETRACTING:
							par
								retractGearsElectroValve := false
								gears := RETRACTED
							endpar
						case EXTENDING:
							/*if extendGearsElectroValve then
								par
									extendGearsElectroValve := false
									retractGearsElectroValve := true
								endpar
							else
								gears := RETRACTING
							endif*/
							par
								extendGearsElectroValve := false
								retractGearsElectroValve := true
								gears := RETRACTING
							endpar
					endswitch
			endswitch
		else
			r_closeDoor[]
		endif

	rule r_extendingLandingSequence =
		if gears != EXTENDED then
			switch doors
				case CLOSED:
					if not(generalElectroValve) then
						par
							generalElectroValve := true //si apre la valvola generale quando la porta è chiusa con le ruote retratte (e si riceve il segnale DOWN)
							openDoorsElectroValve := true
						endpar
					else
						doors := OPENING
					endif
				case OPENING:
					par
						openDoorsElectroValve := false
						doors := OPEN
					endpar
				case OPEN:
					switch gears
						case RETRACTED:
							if not(extendGearsElectroValve) then
								par
									closeDoorsElectroValve := false //per risolvere il problema dovuto al passaggio di stato
									extendGearsElectroValve := true
								endpar
							else
								gears := EXTENDING
							endif
						case EXTENDING:
							par
								extendGearsElectroValve := false
								gears := EXTENDED
							endpar
						case RETRACTING:
							/*if retractGearsElectroValve then
								par
									extendGearsElectroValve := true
									retractGearsElectroValve := false
								endpar
							else
								gears := EXTENDING
							endif*/
							par
								extendGearsElectroValve := true
								retractGearsElectroValve := false
								gears := EXTENDING
							endpar
					endswitch
			endswitch
		else
			r_closeDoor[]
		endif

	//sono anche tradotti in proprietà CTL 
	/*invariant over gears, doors: (gears = EXTENDING or gears = RETRACTING) implies doors = OPEN
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
	CTLSPEC ag((gears = RETRACTED and doors = OPENING and handle = UP) implies ax(doors = CLOSING))*/
	
	//R11_bis
	CTLSPEC ag((handle = UP and ax(ag(handle = DOWN))) implies af(gears = EXTENDED and doors = CLOSED))
	//R12_bis
	CTLSPEC ag((handle = DOWN and ax(ag(handle = UP))) implies af(gears = RETRACTED and doors = CLOSED))
	//R21
	CTLSPEC ag(ag(handle = DOWN) implies ax(ag(gears != RETRACTING)))
	//R22
	CTLSPEC ag(ag(handle = UP) implies ax(ag(gears != EXTENDING)))

	//R31
	CTLSPEC ag((extendGearsElectroValve or retractGearsElectroValve) implies doors = OPEN)
	//R32
	CTLSPEC ag((openDoorsElectroValve or closeDoorsElectroValve) implies (gears = RETRACTED or gears = EXTENDED))
	//R41
	CTLSPEC ag(not(openDoorsElectroValve and closeDoorsElectroValve))
	//R42
	CTLSPEC ag(not(extendGearsElectroValve and retractGearsElectroValve))
	//R51
	CTLSPEC ag((openDoorsElectroValve or closeDoorsElectroValve or extendGearsElectroValve or retractGearsElectroValve) implies generalElectroValve)

	main rule r_Main =
		if handle = UP then
			par
				extendGearsElectroValve := false //per risolvere il problema dovuto al passaggio di stato
				r_retractingLandingSequence[]
			endpar
		else
			par
				retractGearsElectroValve := false //per risolvere il problema dovuto al passaggio di stato
				r_extendingLandingSequence[]
			endpar
		endif

default init s0:
	function doors = CLOSED
	function gears = EXTENDED
	function generalElectroValve = false
	function extendGearsElectroValve = false
	function retractGearsElectroValve = false
	function openDoorsElectroValve = false
	function closeDoorsElectroValve = false