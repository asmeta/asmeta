//http://www.irit.fr/ABZ2014/landing_system.pdf

asm LandingGearSystemGroundWithSensorsAndCylindersThreeLandingSets2

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLlibrary

//i sensori non bastano per sapere se i gear sono retracting o extending:
//bisogna sapere anche lo stato dei cilindri dei gear

//con cilindri (viene modellato il fatto che un cilindro
//puo' essere mezzo esteso)

signature:
	enum domain LandingSet = {FRONT | LEFT | RIGHT}
	enum domain HandleStatus = {UP | DOWN}
	enum domain DoorStatus = {CLOSED | OPENING | OPEN | CLOSING}
	enum domain GearStatus = {RETRACTED | EXTENDING | EXTENDED | RETRACTING}
	enum domain CylinderStatus = {CYLINDER_RETRACTED | CYLINDER_EXTENDING | CYLINDER_EXTENDED | CYLINDER_RETRACTING}
	dynamic monitored handle: HandleStatus
	dynamic controlled doors: LandingSet -> DoorStatus
	dynamic controlled gears: LandingSet -> GearStatus
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
		switch doors($s)
			case OPEN:
				if cylinderDoorStatus($s) = CYLINDER_EXTENDED then
					cylinderDoorStatus($s) := CYLINDER_RETRACTING
				else
					par
						doors($s) := CLOSING
						doorsOpen($s) := false
					endpar
				endif
			case CLOSING:
				par
					doors($s) := CLOSED
					doorsClosed($s) := true
					cylinderDoorStatus($s) := CYLINDER_RETRACTED
				endpar
			case OPENING:
				if cylinderDoorStatus($s) = CYLINDER_EXTENDING then
					cylinderDoorStatus($s) := CYLINDER_RETRACTING
				else
					doors($s) := CLOSING
				endif
		endswitch

	rule r_retractingLandingSequence($s in LandingSet) =
		if gears($s) != RETRACTED then
			switch doors($s)
				case CLOSED:
					if cylinderDoorStatus($s) = CYLINDER_RETRACTED then
						cylinderDoorStatus($s) := CYLINDER_EXTENDING
					else
						par
							doors($s) := OPENING
							doorsClosed($s) := false
						endpar
					endif
				case CLOSING:
					if cylinderDoorStatus($s) = CYLINDER_RETRACTING then
						cylinderDoorStatus($s) := CYLINDER_EXTENDING
					else
						doors($s) := OPENING
					endif
				case OPENING:
					par
						doors($s) := OPEN
						doorsOpen($s) := true
						cylinderDoorStatus($s) := CYLINDER_EXTENDED
					endpar
				case OPEN:
					switch gears($s)
						case EXTENDED:
							if cylinderGearStatus($s) = CYLINDER_EXTENDED then
								cylinderGearStatus($s) := CYLINDER_RETRACTING
							else
								par
									gears($s) := RETRACTING
									gearsExtended($s) := false
								endpar
							endif	
						case RETRACTING:
							par
								gears($s) := RETRACTED
								gearsRetracted($s) := true
								cylinderGearStatus($s) := CYLINDER_RETRACTED
							endpar
						case EXTENDING:
							if cylinderGearStatus($s) = CYLINDER_EXTENDING then
								cylinderGearStatus($s) := CYLINDER_RETRACTING
							else
								gears($s) := RETRACTING
							endif
					endswitch
			endswitch
		else
			r_closeDoor[$s]
		endif

	rule r_extendingLandingSequence($s in LandingSet) =
		if gears($s) != EXTENDED then
			switch doors($s)
				case CLOSED:
					if cylinderDoorStatus($s) = CYLINDER_RETRACTED then
						cylinderDoorStatus($s) := CYLINDER_EXTENDING
					else
						par
							doors($s) := OPENING
							doorsClosed($s) := false
						endpar
					endif
				case OPENING:
					par
						doors($s) := OPEN
						doorsOpen($s) := true
						cylinderDoorStatus($s) := CYLINDER_EXTENDED
					endpar
				case OPEN:
					switch gears($s)
						case RETRACTED:
							if cylinderGearStatus($s) = CYLINDER_RETRACTED then
								cylinderGearStatus($s) := CYLINDER_EXTENDING
							else
								par
									gears($s) := EXTENDING
									gearsRetracted($s) := false
								endpar
							endif
						case EXTENDING:
							par
								gears($s) := EXTENDED
								gearsExtended($s) := true
								cylinderDoorStatus($s) := CYLINDER_EXTENDED
							endpar
						case RETRACTING:
							if cylinderGearStatus($s) = CYLINDER_RETRACTING then
								cylinderGearStatus($s) := CYLINDER_EXTENDING
							else
								gears($s) := EXTENDING
							endif
					endswitch
			endswitch
		else
			r_closeDoor[$s]
		endif

	//i tre landing set sono uguali
	invariant over gears: gears(FRONT) = gears(LEFT) and gears(LEFT) = gears(RIGHT)
	invariant over doors: doors(FRONT) = doors(LEFT) and doors(LEFT) = doors(RIGHT)
	invariant over gearsExtended: gearsExtended(FRONT) = gearsExtended(LEFT) and gearsExtended(LEFT) = gearsExtended(RIGHT)
	invariant over gearsRetracted: gearsRetracted(FRONT) = gearsRetracted(LEFT) and gearsRetracted(LEFT) = gearsRetracted(RIGHT)
	invariant over doorsOpen: doorsOpen(FRONT) = doorsOpen(LEFT) and doorsOpen(LEFT) = doorsOpen(RIGHT)
	invariant over doorsClosed: doorsClosed(FRONT) = doorsClosed(LEFT) and doorsClosed(LEFT) = doorsClosed(RIGHT)
	invariant over cylinderGearStatus: cylinderGearStatus(FRONT) = cylinderGearStatus(LEFT) and cylinderGearStatus(LEFT) = cylinderGearStatus(RIGHT)
	invariant over cylinderDoorStatus: cylinderDoorStatus(FRONT) = cylinderDoorStatus(LEFT) and cylinderDoorStatus(LEFT) = cylinderDoorStatus(RIGHT)

	invariant over gears, doors: (forall $s in LandingSet with (gears($s) = EXTENDING or gears($s) = RETRACTING) implies doors($s) = OPEN)
	invariant over gears, doors: (forall $s in LandingSet with doors($s) = CLOSED implies (gears($s) = EXTENDED or gears($s) = RETRACTED))

	invariant over cylinderGearStatus: (forall $s in LandingSet with cylinderGearStatus($s) = CYLINDER_EXTENDED implies gears($s) = EXTENDED)
	invariant over cylinderGearStatus: (forall $s in LandingSet with cylinderGearStatus($s) = CYLINDER_RETRACTED implies gears($s) = RETRACTED)
	invariant over cylinderGearStatus: (forall $s in LandingSet with gears($s) = EXTENDING implies (cylinderGearStatus($s) = CYLINDER_EXTENDING or cylinderGearStatus($s) = CYLINDER_RETRACTING))
	invariant over cylinderDoorStatus: (forall $s in LandingSet with cylinderDoorStatus($s) = CYLINDER_EXTENDED implies doors($s) = OPEN)
	invariant over cylinderDoorStatus: (forall $s in LandingSet with cylinderDoorStatus($s) = CYLINDER_RETRACTED implies doors($s) = CLOSED)
	//invariant over cylinderGearStatus, cylinderDoorStatus: (forall $s in LandingSet with (cylinderGearStatus($s) = CYLINDER_EXTENDING or cylinderGearStatus($s) = CYLINDER_RETRACTING) implies cylinderDoorStatus($s) = CYLINDER_EXTENDED)

	//for sensors
	invariant over gearsExtended,gearsRetracted: (forall $s in LandingSet with not(gearsExtended($s) and gearsRetracted($s)))
	invariant over doorsOpen,doorsClosed: (forall $s in LandingSet with not(doorsOpen($s) and doorsClosed($s)))

	CTLSPEC ag(gears(FRONT) = gears(LEFT) and gears(LEFT) = gears(RIGHT))
	CTLSPEC ag(doors(FRONT) = doors(LEFT) and doors(LEFT) = doors(RIGHT))
	CTLSPEC ag(gearsExtended(FRONT) = gearsExtended(LEFT) and gearsExtended(LEFT) = gearsExtended(RIGHT))
	CTLSPEC ag(gearsRetracted(FRONT) = gearsRetracted(LEFT) and gearsRetracted(LEFT) = gearsRetracted(RIGHT))
	CTLSPEC ag(doorsOpen(FRONT) = doorsOpen(LEFT) and doorsOpen(LEFT) = doorsOpen(RIGHT))
	CTLSPEC ag(doorsClosed(FRONT) = doorsClosed(LEFT) and doorsClosed(LEFT) = doorsClosed(RIGHT))

	CTLSPEC (forall $s in LandingSet with ag((doors($s) = CLOSED and gears($s) = EXTENDED and ag(handle = DOWN)) implies ag(doors($s) = CLOSED and gears($s) = EXTENDED)))
	CTLSPEC (forall $s in LandingSet with ag((doors($s) = CLOSED and gears($s) = RETRACTED and ag(handle = UP)) implies ag(doors($s) = CLOSED and gears($s) = RETRACTED)))
	CTLSPEC (forall $s in LandingSet with ag((gears($s) = EXTENDING or gears($s) = RETRACTING) implies doors($s) = OPEN))
	CTLSPEC (forall $s in LandingSet with ag(doors($s) = CLOSED implies (gears($s) = EXTENDED or gears($s) = RETRACTED)))
	CTLSPEC (forall $s in LandingSet with ag((gears($s) = EXTENDING and handle = DOWN) implies ax(gears($s) = EXTENDED)))
	CTLSPEC (forall $s in LandingSet with ag((gears($s) = EXTENDING and handle = UP) implies ax(cylinderGearStatus($s) = CYLINDER_RETRACTING)))
	CTLSPEC (forall $s in LandingSet with ag((gears($s) = RETRACTING and handle = UP) implies ax(gears($s) = RETRACTED)))
	CTLSPEC (forall $s in LandingSet with ag((gears($s) = RETRACTING and handle = DOWN) implies ax(cylinderGearStatus($s) = CYLINDER_EXTENDING)))

	CTLSPEC (forall $s in LandingSet with ag((gears($s) = EXTENDED and doors($s) = CLOSING and handle = DOWN) implies ax(doors($s) = CLOSED)))
	CTLSPEC (forall $s in LandingSet with ag((gears($s) = EXTENDED and doors($s) = CLOSING and handle = UP) implies ax(cylinderDoorStatus($s) = CYLINDER_EXTENDING)))
	CTLSPEC (forall $s in LandingSet with ag((gears($s) = RETRACTED and doors($s) = OPENING and handle = DOWN) implies ax(doors($s) = OPEN)))
	CTLSPEC (forall $s in LandingSet with ag((gears($s) = RETRACTED and doors($s) = OPENING and handle = UP) implies ax(cylinderDoorStatus($s) = CYLINDER_RETRACTING)))

	//for sensors
	CTLSPEC (forall $s in LandingSet with ag(not(gearsExtended($s) and gearsRetracted($s))))
	CTLSPEC (forall $s in LandingSet with ag(not(doorsOpen($s) and doorsClosed($s))))
	CTLSPEC (forall $s in LandingSet with ag(doorsOpen($s) implies not(doorsClosed($s))))
	CTLSPEC (forall $s in LandingSet with ag(doorsClosed($s) implies not(doorsOpen($s))))
	CTLSPEC (forall $s in LandingSet with ag((not(doorsOpen($s)) and not(doorsClosed($s))) iff (doors($s) = OPENING or doors($s) = CLOSING)))
	CTLSPEC (forall $s in LandingSet with ag((doors($s) = OPENING or doors($s) = CLOSING) implies (gearsExtended($s) or gearsRetracted($s))))

	main rule r_Main =
		forall $s in LandingSet with true do
			if handle = UP then
				r_retractingLandingSequence[$s]
			else
				r_extendingLandingSequence[$s]
			endif

default init s0:
	function doors($s in LandingSet) = CLOSED
	function gears($s in LandingSet) = EXTENDED
	function gearsExtended($s in LandingSet) = true
	function gearsRetracted($s in LandingSet) = false
	function doorsClosed($s in LandingSet) = true
	function doorsOpen($s in LandingSet) = false
	function cylinderDoorStatus($s in LandingSet) = CYLINDER_RETRACTED
	function cylinderGearStatus($s in LandingSet) = CYLINDER_EXTENDED