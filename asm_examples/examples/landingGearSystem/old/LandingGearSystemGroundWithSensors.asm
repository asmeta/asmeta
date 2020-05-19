//http://www.irit.fr/ABZ2014/landing_system.pdf

asm LandingGearSystemGroundWithSensors

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLlibrary

//i sensori non bastano per sapere se i gear sono retracting o extending:
//bisogna sapere anche lo stato dei cilindri dei gear

signature:
	enum domain HandleStatus = {UP | DOWN}
	enum domain DoorStatus = {CLOSED | OPENING | OPEN | CLOSING}
	enum domain GearStatus = {RETRACTED | EXTENDING | EXTENDED | RETRACTING}
	dynamic monitored handle: HandleStatus
	dynamic controlled doors: DoorStatus
	dynamic controlled gears: GearStatus
	//gearsExtended is true if the corresponding gear is locked in the extended position and false in the other case
	dynamic controlled gearsExtended: Boolean
	//gearsRetracted is true if the corresponding gear is locked in the retracted position and false in the other case
	dynamic controlled gearsRetracted: Boolean
	//doorsClosed is true if and only if the corresponding door is locked closed
	dynamic controlled doorsClosed: Boolean
	//doorsOpen is true if and only if the corresponding door is locked open
	dynamic controlled doorsOpen: Boolean

definitions:

	rule r_closeDoor =
		switch doors
			case OPEN:
				par
					doors := CLOSING
					doorsOpen := false
				endpar
			case CLOSING:
				par
					doors := CLOSED
					doorsClosed := true
				endpar
			case OPENING:
				doors := CLOSING
		endswitch

	rule r_retractingLandingSequence =
		if gears != RETRACTED then
			switch doors
				case CLOSED:
					par
						doors := OPENING
						doorsClosed := false
					endpar
				case CLOSING:
					doors := OPENING
				case OPENING:
					par
						doors := OPEN
						doorsOpen := true
					endpar
				case OPEN:
					switch gears
						case EXTENDED:
							par
								gears := RETRACTING
								gearsExtended := false
							endpar
						case RETRACTING:
							par
								gears := RETRACTED
								gearsRetracted := true
							endpar
						case EXTENDING:
							gears := RETRACTING
					endswitch
			endswitch
		else
			r_closeDoor[]
		endif

	rule r_extendingLandingSequence =
		if gears != EXTENDED then
			switch doors
				case CLOSED:
					par
						doors := OPENING
						doorsClosed := false
					endpar
				case OPENING:
					par
						doors := OPEN
						doorsOpen := true
					endpar
				case OPEN:
					switch gears
						case RETRACTED:
							par
								gears := EXTENDING
								gearsRetracted := false
							endpar
						case EXTENDING:
							par
								gears := EXTENDED
								gearsExtended := true
							endpar
						case RETRACTING:
							gears := EXTENDING
					endswitch
			endswitch
		else
			r_closeDoor[]
		endif

	invariant over gears, doors: (gears = EXTENDING or gears = RETRACTING) implies doors = OPEN
	invariant over gears, doors: doors = CLOSED implies (gears = EXTENDED or gears = RETRACTED)

	//for sensors
	invariant over gearsExtended,gearsRetracted: not(gearsExtended and gearsRetracted)
	invariant over doorsOpen,doorsClosed: not(doorsOpen and doorsClosed)

	CTLSPEC ag((doors = CLOSED and gears = EXTENDED and ag(handle = DOWN)) implies ag(doors = CLOSED and gears = EXTENDED))
	CTLSPEC ag((doors = CLOSED and gears = RETRACTED and ag(handle = UP)) implies ag(doors = CLOSED and gears = RETRACTED))
	CTLSPEC ag((gears = EXTENDING or gears = RETRACTING) implies doors = OPEN)
	CTLSPEC ag(doors = CLOSED implies (gears = EXTENDED or gears = RETRACTED))
	CTLSPEC ag((gears = EXTENDING and handle = DOWN) implies ax(gears = EXTENDED))
	CTLSPEC ag((gears = EXTENDING and handle = UP) implies ax(gears = RETRACTING))
	CTLSPEC ag((gears = RETRACTING and handle = UP) implies ax(gears = RETRACTED))
	CTLSPEC ag((gears = RETRACTING and handle = DOWN) implies ax(gears = EXTENDING))

	CTLSPEC ag((gears = EXTENDED and doors = CLOSING and handle = DOWN) implies ax(doors = CLOSED))
	CTLSPEC ag((gears = EXTENDED and doors = CLOSING and handle = UP) implies ax(doors = OPENING))
	CTLSPEC ag((gears = RETRACTED and doors = OPENING and handle = DOWN) implies ax(doors = OPEN))
	CTLSPEC ag((gears = RETRACTED and doors = OPENING and handle = UP) implies ax(doors = CLOSING))

	//for sensors
	CTLSPEC ag(not(gearsExtended and gearsRetracted))
	CTLSPEC ag(not(doorsOpen and doorsClosed))
	CTLSPEC ag(doorsOpen implies not(doorsClosed))
	CTLSPEC ag(doorsClosed implies not(doorsOpen))
	CTLSPEC ag((not(doorsOpen) and not(doorsClosed)) iff (doors = OPENING or doors = CLOSING))
	CTLSPEC ag((doors = OPENING or doors = CLOSING) implies (gearsExtended or gearsRetracted))

	main rule r_Main =
		if handle = UP then
			r_retractingLandingSequence[]
		else
			r_extendingLandingSequence[]
		endif

default init s0:
	function doors = CLOSED
	function gears = EXTENDED
	function gearsExtended = true
	function gearsRetracted = false
	function doorsClosed = true
	function doorsOpen = false