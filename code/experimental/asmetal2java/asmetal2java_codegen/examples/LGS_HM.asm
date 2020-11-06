//http://www.irit.fr/ABZ2014/landing_system.pdf

//Landing Gear System - Fourth refinement

asm LGS_HM

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

	dynamic monitored timeout: Boolean

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
