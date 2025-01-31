//http://www.irit.fr/ABZ2014/landing_system.pdf

//Landing Gear System - Third refinement

asm LGS_3L

import STDL/StandardLibrary

signature:
	enum domain LandingSet = {FRONT | LEFT | RIGHT}
	enum domain HandleStatus = {UP | DOWN}
	enum domain DoorStatus = {CLOSED | OPENING | OPEN | CLOSING}
	enum domain GearStatus = {RETRACTED | EXTENDING | EXTENDED | RETRACTING}
	enum domain CylinderStatus = {CYLINDER_RETRACTED | CYLINDER_EXTENDING | CYLINDER_EXTENDED | CYLINDER_RETRACTING}
	dynamic monitored handle: HandleStatus
	dynamic controlled doors: DoorStatus
	dynamic controlled gears: GearStatus
	
	derived cylindersDoors: CylinderStatus
	derived cylindersGears: CylinderStatus

	dynamic controlled generalElectroValve: Boolean
	dynamic controlled openDoorsElectroValve: Boolean
	dynamic controlled closeDoorsElectroValve: Boolean
	dynamic controlled retractGearsElectroValve: Boolean
	dynamic controlled extendGearsElectroValve: Boolean

	//sensors
	//gearsExtended is true if the corresponding gear is locked in the extended position and false in the other case
	dynamic monitored gearsExtended: LandingSet -> Boolean
	//gearsRetracted is true if the corresponding gear is locked in the retracted position and false in the other case
	dynamic monitored gearsRetracted: LandingSet -> Boolean
	//doorsClosed($s) is true if and only if the corresponding door is locked closed
	dynamic monitored doorsClosed: LandingSet -> Boolean
	//doorsOpen is true if and only if the corresponding door is locked open
	dynamic monitored doorsOpen: LandingSet -> Boolean
	//gearsShockAbsorber is true if and only if the aircfraft is on ground
	dynamic monitored gearsShockAbsorber: LandingSet -> Boolean

	derived gearsAllExtended: Boolean
	derived gearsAllRetracted: Boolean
	derived doorsAllClosed: Boolean
	derived doorsAllOpen: Boolean
	derived gearsAllShockAbsorber: Boolean

definitions:
	function gearsAllExtended = (forall $s in LandingSet with gearsExtended($s))
	function gearsAllRetracted = (forall $s in LandingSet with gearsRetracted($s))
	function doorsAllClosed = (forall $s in LandingSet with doorsClosed($s))
	function doorsAllOpen = (forall $s in LandingSet with doorsOpen($s))
	function gearsAllShockAbsorber = (forall $s in LandingSet with gearsShockAbsorber($s))

	function cylindersDoors =
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
		endswitch

	rule r_closeDoor =
		switch doors
			case OPEN:
				par
					closeDoorsElectroValve := true
					doors := CLOSING
				endpar
			case CLOSING:
				if doorsAllClosed then
					par
						generalElectroValve := false
						closeDoorsElectroValve := false
						doors := CLOSED
					endpar
				endif
			case OPENING:
				par
					closeDoorsElectroValve := true
					openDoorsElectroValve := false
					doors := CLOSING
				endpar
		endswitch

	rule r_retractionSequence =
		if gears != RETRACTED then
			switch doors
				case CLOSED:
					par
						generalElectroValve := true
						openDoorsElectroValve := true
						doors := OPENING
					endpar
				case CLOSING:
					par
						closeDoorsElectroValve := false
						openDoorsElectroValve := true
						doors := OPENING
					endpar
				case OPENING:
					if doorsAllOpen then
						par
							openDoorsElectroValve := false
							doors := OPEN
						endpar
					endif
				case OPEN:
					switch gears
						case EXTENDED:
							if gearsAllShockAbsorber then
								par
									retractGearsElectroValve := true
									gears := RETRACTING
								endpar
							endif
						case RETRACTING:
							if gearsAllRetracted then
								par
									retractGearsElectroValve := false
									gears := RETRACTED
								endpar
							endif
						case EXTENDING:
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

	rule r_outgoingSequence =
		if gears != EXTENDED then
			switch doors
				case CLOSED:
					par
						generalElectroValve := true
						openDoorsElectroValve := true
						doors := OPENING
					endpar
				case OPENING:
					if doorsAllOpen then
						par
							openDoorsElectroValve := false
							doors := OPEN
						endpar
					endif
				case CLOSING:
					par
						closeDoorsElectroValve := false
						openDoorsElectroValve := true
						doors := OPENING
					endpar
				case OPEN:
					switch gears
						case RETRACTED:
							par
								extendGearsElectroValve := true
								gears := EXTENDING
							endpar
						case EXTENDING:
							if gearsAllExtended then
								par
									extendGearsElectroValve := false
									gears := EXTENDED
								endpar
							endif
						case RETRACTING:
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
	function generalElectroValve = false
	function extendGearsElectroValve = false
	function retractGearsElectroValve = false
	function openDoorsElectroValve = false
	function closeDoorsElectroValve = false
