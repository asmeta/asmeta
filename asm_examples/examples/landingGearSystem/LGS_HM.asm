//http://www.irit.fr/ABZ2014/landing_system.pdf

//Landing Gear System - Fourth refinement

asm LGS_HM

import ../../STDL/StandardLibrary
import ../../STDL/CTLLibrary
import ../../STDL/LTLLibrary

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

	derived gearsExtended: Boolean
	derived gearsRetracted: Boolean
	derived doorsClosed: Boolean
	derived doorsOpen: Boolean
	derived gearsShockAbsorber: Boolean

	derived aGearExtended: Boolean
	derived aGearRetracted: Boolean
	derived aDoorClosed: Boolean
	derived aDoorOpen: Boolean

	dynamic monitored timeout: Boolean
	dynamic controlled anomaly: Boolean

	derived greenLight: Boolean
	derived orangeLight: Boolean
	derived redLight: Boolean

definitions:
	function gearsExtended = (forall $s in LandingSet with gearsExtended($s))
	function gearsRetracted = (forall $s in LandingSet with gearsRetracted($s))
	function doorsClosed = (forall $s in LandingSet with doorsClosed($s))
	function doorsOpen = (forall $s in LandingSet with doorsOpen($s))

	function aGearExtended = (exist $s in LandingSet with gearsExtended($s))
	function aGearRetracted = (exist $s in LandingSet with gearsRetracted($s))
	function aDoorClosed = (exist $s in LandingSet with doorsClosed($s))
	function aDoorOpen = (exist $s in LandingSet with doorsOpen($s))
	function gearsShockAbsorber = (forall $s in LandingSet with gearsShockAbsorber($s))

	function greenLight = gears = EXTENDED
	function orangeLight = gears = EXTENDING or gears = RETRACTING
	function redLight = anomaly

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
				if doorsClosed then
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
					if doorsOpen then
						par
							openDoorsElectroValve := false
							doors := OPEN
						endpar
					endif
				case OPEN:
					switch gears
						case EXTENDED:
							if gearsShockAbsorber then
								par
									retractGearsElectroValve := true
									gears := RETRACTING
								endpar
							endif
						case RETRACTING:
							if gearsRetracted then
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
					if doorsOpen then
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
							if gearsExtended then
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

	rule r_healthMonitoring =
		par
			if openDoorsElectroValve and aDoorClosed and timeout then
				anomaly := true
			endif
			if openDoorsElectroValve and not(doorsOpen) and timeout then
				anomaly := true
			endif
			if closeDoorsElectroValve and aDoorOpen and timeout then
				anomaly := true
			endif
			if closeDoorsElectroValve and not(doorsClosed) and timeout then
				anomaly := true
			endif

			if retractGearsElectroValve and aGearExtended and timeout then
				anomaly := true
			endif
			if retractGearsElectroValve and not(gearsRetracted) and timeout then
				anomaly := true
			endif
			if extendGearsElectroValve and aGearRetracted and timeout then
				anomaly := true
			endif
			if extendGearsElectroValve and not(gearsExtended) and timeout then
				anomaly := true
			endif
		endpar

	//sensors must be true infinitely often (necessary for property verification)
	JUSTICE timeout or gearsExtended
	JUSTICE timeout or gearsRetracted
	JUSTICE timeout or doorsClosed
	JUSTICE timeout or doorsOpen
	JUSTICE gearsShockAbsorber
	//JUSTICE timeout
	//imposing the constraints on the single sensors is to weak 
	/*JUSTICE gearsExtended(FRONT)
	JUSTICE gearsExtended(LEFT)
	JUSTICE gearsExtended(RIGHT)
	JUSTICE gearsRetracted(FRONT)
	JUSTICE gearsRetracted(LEFT)
	JUSTICE gearsRetracted(RIGHT)
	JUSTICE doorsClosed(FRONT)
	JUSTICE doorsClosed(LEFT)
	JUSTICE doorsClosed(RIGHT)
	JUSTICE doorsOpen(FRONT)
	JUSTICE doorsOpen(LEFT)
	JUSTICE doorsOpen(RIGHT)
	JUSTICE gearsShockAbsorber(FRONT)
	JUSTICE gearsShockAbsorber(LEFT)
	JUSTICE gearsShockAbsorber(RIGHT)*/

	//sono anche tradotti in proprietà CTL 
	invariant over gears, doors: (gears = EXTENDING or gears = RETRACTING) implies doors = OPEN
	invariant over gears, doors: doors = CLOSED implies (gears = EXTENDED or gears = RETRACTED)

	/*CTLSPEC ag((doors = CLOSED and gears = EXTENDED and ag(handle = DOWN)) implies ag(doors = CLOSED and gears = EXTENDED))
	CTLSPEC ag((doors = CLOSED and gears = RETRACTED and ag(handle = UP)) implies ag(doors = CLOSED and gears = RETRACTED))
	CTLSPEC ag((gears = EXTENDING and handle = DOWN) implies ax(gears = EXTENDED))
	CTLSPEC ag((gears = EXTENDING and handle = UP) implies ax(gears = RETRACTING))
	CTLSPEC ag((gears = RETRACTING and handle = UP) implies ax(gears = RETRACTED))
	CTLSPEC ag((gears = RETRACTING and handle = DOWN) implies ax(gears = EXTENDING))

	CTLSPEC ag((gears = EXTENDED and doors = CLOSING and handle = DOWN) implies ax(doors = CLOSED))
	CTLSPEC ag((gears = EXTENDED and doors = CLOSING and handle = UP) implies ax(doors = OPENING))
	CTLSPEC ag((gears = RETRACTED and doors = OPENING and handle = DOWN) implies ax(doors = OPEN))
	CTLSPEC ag((gears = RETRACTED and doors = OPENING and handle = UP) implies ax(doors = CLOSING))*/

	CTLSPEC ag((generalElectroValve and eg(not(anomaly))) implies ef(not(generalElectroValve) and not(anomaly)))


	//R11_bis
	//CTLSPEC ag(ag(handle = DOWN) implies af(gears = EXTENDED and doors = CLOSED)) //WRONG
	//CTLSPEC ag(eg(handle = DOWN) implies ef(gears = EXTENDED and doors = CLOSED)) //TOO WEAK
	//LTLSPEC g(g(handle = DOWN) implies f(gears = EXTENDED and doors = CLOSED)) //it fails because of the anomaly
	LTLSPEC g(g(handle = DOWN and not(anomaly)) implies f(gears = EXTENDED and doors = CLOSED))
	//R12_bis
	//CTLSPEC ag(ag(handle = UP) implies af(gears = RETRACTED and doors = CLOSED)) //WRONG
	//CTLSPEC ag(eg(handle = UP) implies ef(gears = RETRACTED and doors = CLOSED)) //TOO WEAK
	//LTLSPEC g(g(handle = UP) implies f(gears = RETRACTED and doors = CLOSED)) //it fails because of the anomaly
	LTLSPEC g(g(handle = UP and not(anomaly)) implies f(gears = RETRACTED and doors = CLOSED))
	//R21
	//CTLSPEC ag(ag(handle = DOWN) implies ax(ag(gears != RETRACTING))) //WRONG
	//CTLSPEC ag(eg(handle = DOWN and not(anomaly)) implies ex(eg(gears != RETRACTING))) //TOO WEAK
	//LTLSPEC g(g(handle = DOWN) implies x(g(gears != RETRACTING))) //it fails because of the anomaly
	LTLSPEC g(g(handle = DOWN and not(anomaly)) implies x(g(gears != RETRACTING)))
	//R22
	//CTLSPEC ag(ag(handle = UP) implies ax(ag(gears != EXTENDING))) //WRONG
	//CTLSPEC ag(eg(handle = UP and not(anomaly)) implies ex(eg(gears != EXTENDING))) //TOO WEAK
	//LTLSPEC g(g(handle = UP) implies x(g(gears != EXTENDING))) //it fails because of the anomaly
	LTLSPEC g(g(handle = UP and not(anomaly)) implies x(g(gears != EXTENDING)))

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

	//R61
	CTLSPEC ag((openDoorsElectroValve and aDoorClosed and timeout) implies ax(ag(anomaly)))
	LTLSPEC g((openDoorsElectroValve and aDoorClosed and timeout) implies x(g(anomaly)))
	//R62
	CTLSPEC ag((closeDoorsElectroValve and aDoorOpen and timeout) implies ax(ag(anomaly)))
	LTLSPEC g((closeDoorsElectroValve and aDoorOpen and timeout) implies x(g(anomaly)))
	//R63
	CTLSPEC ag((retractGearsElectroValve and aGearExtended and timeout) implies ax(ag(anomaly)))
	LTLSPEC g((retractGearsElectroValve and aGearExtended and timeout) implies x(g(anomaly)))
	//R64
	CTLSPEC ag((extendGearsElectroValve and aGearRetracted and timeout) implies ax(ag(anomaly)))
	LTLSPEC g((extendGearsElectroValve and aGearRetracted and timeout) implies x(g(anomaly)))

	//R71
	CTLSPEC ag((openDoorsElectroValve and not(doorsOpen) and timeout) implies ax(ag(anomaly)))
	LTLSPEC g((openDoorsElectroValve and not(doorsOpen) and timeout) implies x(g(anomaly)))
	//R72
	CTLSPEC ag((closeDoorsElectroValve and not(doorsClosed) and timeout) implies ax(ag(anomaly)))
	LTLSPEC g((closeDoorsElectroValve and not(doorsClosed) and timeout) implies x(g(anomaly)))
	//R73
	CTLSPEC ag((retractGearsElectroValve and not(gearsRetracted) and timeout) implies ax(ag(anomaly)))
	LTLSPEC g((retractGearsElectroValve and not(gearsRetracted) and timeout) implies x(g(anomaly)))
	//R74
	CTLSPEC ag((extendGearsElectroValve and not(gearsExtended) and timeout) implies ax(ag(anomaly)))
	LTLSPEC g((extendGearsElectroValve and not(gearsExtended) and timeout) implies x(g(anomaly)))

	//Deadlock freeness
	CTLSPEC ag(ef(handle = UP)) //the handle can always become UP
	CTLSPEC ag(ef(handle = DOWN)) //the handle can always become DOWN
	LTLSPEC g(anomaly implies g(anomaly)) //it's not possible to recover from anomaly
	CTLSPEC ag(anomaly implies ag(anomaly)) //it's not possible to recover from anomaly
	LTLSPEC f(not(anomaly))

	main rule r_Main =
		if not(anomaly) then
			par
				if handle = UP then
					r_retractionSequence[]
				else
					r_outgoingSequence[]
				endif
				r_healthMonitoring[]
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
	function anomaly = false