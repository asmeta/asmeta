//http://www.irit.fr/ABZ2014/landing_system.pdf

asm LandingGearSystemGroundWithSensorsAndCylindersThreeLandingSets3

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLlibrary

//i sensori non bastano per sapere se i gear sono retracting o extending:
//bisogna sapere anche lo stato dei cilindri dei gear

//con cilindri (viene modellato il fatto che un cilindro
//puo' essere mezzo esteso)

//senza funzioni "doors" e "gears" (non previste nel testo)

signature:
	enum domain LandingSet = {FRONT | LEFT | RIGHT}
	enum domain HandleStatus = {UP | DOWN}
	enum domain CylinderStatus = {CYLINDER_RETRACTED | CYLINDER_EXTENDING | CYLINDER_EXTENDED | CYLINDER_RETRACTING}
	dynamic monitored handle: HandleStatus
	//gearsExtended is true if the corresponding gear is locked in the extended position and false in the other case
	dynamic controlled gearsExtended: LandingSet -> Boolean
	//gearsRetracted is true if the corresponding gear is locked in the retracted position and false in the other case
	dynamic controlled gearsRetracted: LandingSet -> Boolean
	//doorsClosed($s) is true if and only if the corresponding door is locked closed
	dynamic controlled doorsClosed: LandingSet -> Boolean
	//doorsOpen is true if and only if the corresponding door is locked open
	dynamic controlled doorsOpen: LandingSet -> Boolean

	dynamic controlled cylinderDoorStatus: LandingSet -> CylinderStatus
	dynamic controlled cylinderGearStatus: LandingSet -> CylinderStatus

definitions:

	rule r_closeDoor($s in LandingSet) =
		switch cylinderDoorStatus($s)
			case CYLINDER_EXTENDED:
				par
					cylinderDoorStatus($s) := CYLINDER_RETRACTING
					doorsOpen($s) := false
				endpar
			case CYLINDER_RETRACTING:
				par
					doorsClosed($s) := true
					cylinderDoorStatus($s) := CYLINDER_RETRACTED
				endpar
			case CYLINDER_EXTENDING:
				cylinderDoorStatus($s) := CYLINDER_RETRACTING
		endswitch

	rule r_retractingLandingSequence($s in LandingSet) =
		if cylinderGearStatus($s) != CYLINDER_RETRACTED then
			switch cylinderDoorStatus($s)
				case CYLINDER_RETRACTED:
					par
						cylinderDoorStatus($s) := CYLINDER_EXTENDING
						doorsClosed($s) := false
					endpar
				case CYLINDER_RETRACTING:
					cylinderDoorStatus($s) := CYLINDER_EXTENDING
				case CYLINDER_EXTENDING:
					par
						doorsOpen($s) := true
						cylinderDoorStatus($s) := CYLINDER_EXTENDED
					endpar
				case CYLINDER_EXTENDED:
					switch cylinderGearStatus($s)
						case CYLINDER_EXTENDED:
							par
								cylinderGearStatus($s) := CYLINDER_RETRACTING
								gearsExtended($s) := false
							endpar	
						case CYLINDER_RETRACTING:
							par
								gearsRetracted($s) := true
								cylinderGearStatus($s) := CYLINDER_RETRACTED
							endpar
						case CYLINDER_EXTENDING:
							cylinderGearStatus($s) := CYLINDER_RETRACTING
					endswitch
			endswitch
		else
			r_closeDoor[$s]
		endif

	rule r_extendingLandingSequence($s in LandingSet) =
		if cylinderDoorStatus($s) != CYLINDER_EXTENDED then
			switch cylinderDoorStatus($s)
				case CYLINDER_RETRACTED:
					par
						cylinderDoorStatus($s) := CYLINDER_EXTENDING
						doorsClosed($s) := false
					endpar
				case CYLINDER_EXTENDING:
					par
						doorsOpen($s) := true
						cylinderDoorStatus($s) := CYLINDER_EXTENDED
					endpar
				case CYLINDER_EXTENDED:
					switch cylinderGearStatus($s)
						case CYLINDER_RETRACTED:
							par
								cylinderGearStatus($s) := CYLINDER_EXTENDING
								gearsRetracted($s) := false
							endpar
						case CYLINDER_EXTENDING:
							par
								gearsExtended($s) := true
								cylinderDoorStatus($s) := CYLINDER_EXTENDED
							endpar
						case CYLINDER_RETRACTING:
							cylinderGearStatus($s) := CYLINDER_EXTENDING
					endswitch
			endswitch
		else
			r_closeDoor[$s]
		endif

	//i tre landing set sono uguali
	invariant over gearsExtended: gearsExtended(FRONT) = gearsExtended(LEFT) and gearsExtended(LEFT) = gearsExtended(RIGHT)
	invariant over gearsRetracted: gearsRetracted(FRONT) = gearsRetracted(LEFT) and gearsRetracted(LEFT) = gearsRetracted(RIGHT)
	invariant over doorsOpen: doorsOpen(FRONT) = doorsOpen(LEFT) and doorsOpen(LEFT) = doorsOpen(RIGHT)
	invariant over doorsClosed: doorsClosed(FRONT) = doorsClosed(LEFT) and doorsClosed(LEFT) = doorsClosed(RIGHT)
	invariant over cylinderGearStatus: cylinderGearStatus(FRONT) = cylinderGearStatus(LEFT) and cylinderGearStatus(LEFT) = cylinderGearStatus(RIGHT)
	invariant over cylinderDoorStatus: cylinderDoorStatus(FRONT) = cylinderDoorStatus(LEFT) and cylinderDoorStatus(LEFT) = cylinderDoorStatus(RIGHT)

	//for sensors
	invariant over gearsExtended,gearsRetracted: (forall $s in LandingSet with not(gearsExtended($s) and gearsRetracted($s)))
	invariant over doorsOpen,doorsClosed: (forall $s in LandingSet with not(doorsOpen($s) and doorsClosed($s)))

	CTLSPEC ag(gearsExtended(FRONT) = gearsExtended(LEFT) and gearsExtended(LEFT) = gearsExtended(RIGHT))
	CTLSPEC ag(gearsRetracted(FRONT) = gearsRetracted(LEFT) and gearsRetracted(LEFT) = gearsRetracted(RIGHT))
	CTLSPEC ag(doorsOpen(FRONT) = doorsOpen(LEFT) and doorsOpen(LEFT) = doorsOpen(RIGHT))
	CTLSPEC ag(doorsClosed(FRONT) = doorsClosed(LEFT) and doorsClosed(LEFT) = doorsClosed(RIGHT))

	//for sensors
	CTLSPEC (forall $s in LandingSet with ag(not(gearsExtended($s) and gearsRetracted($s))))
	CTLSPEC (forall $s in LandingSet with ag(not(doorsOpen($s) and doorsClosed($s))))
	CTLSPEC (forall $s in LandingSet with ag(doorsOpen($s) implies not(doorsClosed($s))))
	CTLSPEC (forall $s in LandingSet with ag(doorsClosed($s) implies not(doorsOpen($s))))

	main rule r_Main =
		forall $s in LandingSet with true do
			if handle = UP then
				r_retractingLandingSequence[$s]
			else
				r_extendingLandingSequence[$s]
			endif

default init s0:
	function gearsExtended($s in LandingSet) = true
	function gearsRetracted($s in LandingSet) = false
	function doorsClosed($s in LandingSet) = true
	function doorsOpen($s in LandingSet) = false
	function cylinderDoorStatus($s in LandingSet) = CYLINDER_RETRACTED
	function cylinderGearStatus($s in LandingSet) = CYLINDER_EXTENDED