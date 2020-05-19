//http://www.irit.fr/ABZ2014/landing_system.pdf

asm LandingGearSystemGroundThreeLandingSets

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLlibrary

signature:
	enum domain LandingSet = {FRONT | LEFT | RIGHT}
	enum domain HandleStatus = {UP | DOWN}
	enum domain DoorStatus = {CLOSED | OPENING | OPEN | CLOSING}
	enum domain GearStatus = {RETRACTED | EXTENDING | EXTENDED | RETRACTING}
	dynamic monitored handle: HandleStatus
	dynamic controlled doors: LandingSet -> DoorStatus
	dynamic controlled gears: LandingSet -> GearStatus

definitions:

	rule r_closeDoor($s in LandingSet) =
		switch doors($s)
			case OPEN:
				doors($s) := CLOSING
			case CLOSING:
				doors($s) := CLOSED
			case OPENING:
				doors($s) := CLOSING
		endswitch

	rule r_retractingLandingSequence($s in LandingSet) =
		if gears($s) != RETRACTED then
			switch doors($s)
				case CLOSED:
					doors($s) := OPENING
				case CLOSING:
					doors($s) := OPENING
				case OPENING:
					doors($s) := OPEN
				case OPEN:
					switch gears($s)
						case EXTENDED:
							gears($s) := RETRACTING
						case RETRACTING:
							gears($s) := RETRACTED
						case EXTENDING:
							gears($s) := RETRACTING
					endswitch
			endswitch
		else
			r_closeDoor[$s]
		endif

	rule r_extendingLandingSequence($s in LandingSet) =
		if gears($s) != EXTENDED then
			switch doors($s)
				case CLOSED:
					doors($s) := OPENING
				case OPENING:
					doors($s) := OPEN
				case OPEN:
					switch gears($s)
						case RETRACTED:
							gears($s) := EXTENDING
						case EXTENDING:
							gears($s) := EXTENDED
						case RETRACTING:
							gears($s) := EXTENDING
					endswitch
			endswitch
		else
			r_closeDoor[$s]
		endif

	//i tre landing set sono uguali
	invariant over gears: gears(FRONT) = gears(LEFT) and gears(LEFT) = gears(RIGHT)
	invariant over doors: doors(FRONT) = doors(LEFT) and doors(LEFT) = doors(RIGHT)
	invariant over gears, doors: (forall $s in LandingSet with (gears($s) = EXTENDING or gears($s) = RETRACTING) implies doors($s) = OPEN)
	invariant over gears, doors: (forall $s in LandingSet with doors($s) = CLOSED implies (gears($s) = EXTENDED or gears($s) = RETRACTED))

	CTLSPEC ag(gears(FRONT) = gears(LEFT) and gears(LEFT) = gears(RIGHT))
	CTLSPEC ag(doors(FRONT) = doors(LEFT) and doors(LEFT) = doors(RIGHT))
	CTLSPEC (forall $s in LandingSet with ag((doors($s) = CLOSED and gears($s) = EXTENDED and ag(handle = DOWN)) implies ag(doors($s) = CLOSED and gears($s) = EXTENDED)))
	CTLSPEC (forall $s in LandingSet with ag((doors($s) = CLOSED and gears($s) = RETRACTED and ag(handle = UP)) implies ag(doors($s) = CLOSED and gears($s) = RETRACTED)))
	CTLSPEC (forall $s in LandingSet with ag((gears($s) = EXTENDING or gears($s) = RETRACTING) implies doors($s) = OPEN))
	CTLSPEC (forall $s in LandingSet with ag(doors($s) = CLOSED implies (gears($s) = EXTENDED or gears($s) = RETRACTED)))
	CTLSPEC (forall $s in LandingSet with ag((gears($s) = EXTENDING and handle = DOWN) implies ax(gears($s) = EXTENDED)))
	CTLSPEC (forall $s in LandingSet with ag((gears($s) = EXTENDING and handle = UP) implies ax(gears($s) = RETRACTING)))
	CTLSPEC (forall $s in LandingSet with ag((gears($s) = RETRACTING and handle = UP) implies ax(gears($s) = RETRACTED)))
	CTLSPEC (forall $s in LandingSet with ag((gears($s) = RETRACTING and handle = DOWN) implies ax(gears($s) = EXTENDING)))

	CTLSPEC (forall $s in LandingSet with ag((gears($s) = EXTENDED and doors($s) = CLOSING and handle = DOWN) implies ax(doors($s) = CLOSED)))
	CTLSPEC (forall $s in LandingSet with ag((gears($s) = EXTENDED and doors($s) = CLOSING and handle = UP) implies ax(doors($s) = OPENING)))
	CTLSPEC (forall $s in LandingSet with ag((gears($s) = RETRACTED and doors($s) = OPENING and handle = DOWN) implies ax(doors($s) = OPEN)))
	CTLSPEC (forall $s in LandingSet with ag((gears($s) = RETRACTED and doors($s) = OPENING and handle = UP) implies ax(doors($s) = CLOSING)))

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