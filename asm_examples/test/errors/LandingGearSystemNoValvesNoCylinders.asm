//http://www.irit.fr/ABZ2014/landing_system.pdf

asm LandingGearSystemNoValvesNoCylinders

import ../../../STDL/StandardLibrary

signature:
	enum domain LandingSet = {FRONT | LEFT | RIGHT}
	enum domain Mode = {NOMINAL | EMERGENCY}
	enum domain HandlePosition = {UP | DOWN | OFF} //c'Ã¨ anche OFF?
	enum domain AnalogicalSwitchPosition = {OPEN | CLOSED}
	domain SensorNumber subsetof Integer
	domain ComputingModule subsetof Integer

	//The system is controlled digitally in nominal mode and analogically in emergency mode
	dynamic controlled systemMode: Mode

	//SENSORI
	//input discrete values
	//monitorati non va bene perchÃ© devono essere messi ad undef
	//ci vogliono delle monitorate per reperire i valori? 
	dynamic controlled handle: SensorNumber -> HandlePosition //PILOT INTERFACE
	dynamic controlled analogicalSwitch: SensorNumber -> AnalogicalSwitchPosition
	//gearExtended(i,x) is true if the corresponding gear is locked in the extended position and false in the other case
	dynamic controlled gearExtended: Prod(LandingSet, SensorNumber) -> Boolean
	//gearRetracted(i,x) is true if the corresponding gear is locked in the retracted position and false in the other case
	dynamic controlled gearRetracted: Prod(LandingSet, SensorNumber) -> Boolean
	//gearShockAbsorber(i,x) is returned by a sensor implemented directly on the
	//corresponding gear (see Figure 11). It is true if and only if the aircraft is on ground
	dynamic controlled gearShockAbsorber: Prod(LandingSet, SensorNumber) -> Boolean //true if on ground
	//doorClosed(i,x) is true if and only if the corresponding door is locked closed
	dynamic controlled doorClosed: Prod(LandingSet, SensorNumber) -> Boolean
	//doorOpen(i,x) is true if and only if the corresponding door is locked open
	dynamic controlled doorOpen: Prod(LandingSet, SensorNumber) -> Boolean
	//circuitPressurized(i) is returned by a pressure sensor on the hydraulic circuit
	//between the general electro-valve and the maneuvering electro-valve (see Figure 4).
	//circuitPressurized(i) is true if and only if the pressure is high in this
	//part of the hydraulic circuit
	dynamic controlled circuitPressurized: SensorNumber -> Boolean
	
	//non sono derivate, sono controllate
	//determinano il valore in base ai valori dei tre sensori (pag. 16)
	dynamic controlled handle: HandlePosition //PILOT INTERFACE
	dynamic controlled analogicalSwitch: AnalogicalSwitchPosition
	dynamic controlled gearExtended: LandingSet -> Boolean
	dynamic controlled gearRetracted: LandingSet -> Boolean
	dynamic controlled gearShockAbsorber: LandingSet -> Boolean
	dynamic controlled doorClosed: LandingSet -> Boolean
	dynamic controlled doorOpen: LandingSet -> Boolean
	dynamic controlled circuitPressurized: Boolean

	//monitorate per modellare la lettura del valore dei sensori
	dynamic monitored handleMon: SensorNumber -> HandlePosition //PILOT INTERFACE
	dynamic monitored analogicalSwitchMon: SensorNumber -> AnalogicalSwitchPosition
	dynamic monitored gearExtendedMon: Prod(LandingSet, SensorNumber) -> Boolean
	dynamic monitored gearRetractedMon: Prod(LandingSet, SensorNumber) -> Boolean
	dynamic monitored gearShockAbsorberMon: Prod(LandingSet, SensorNumber) -> Boolean //true if on ground
	dynamic monitored doorClosedMon: Prod(LandingSet, SensorNumber) -> Boolean
	dynamic monitored doorOpenMon: Prod(LandingSet, SensorNumber) -> Boolean
	dynamic monitored circuitPressurizedMon: SensorNumber -> Boolean

	//electrical orders (of the two components)
	dynamic controlled generalEV: ComputingModule -> Boolean
	dynamic controlled closeEV: ComputingModule -> Boolean
	dynamic controlled openEV: ComputingModule -> Boolean
	dynamic controlled retractEV: ComputingModule -> Boolean
	dynamic controlled extendEV: ComputingModule -> Boolean

	//composition of the electrical orders coming from the components
	derived generalEV: Boolean
	derived closeEV: Boolean
	derived openEV: Boolean
	derived retractEV: Boolean
	derived extendEV: Boolean

	//global boolean variables to the cockpit
	derived gearsLockedDown: ComputingModule -> Boolean
	derived gearsManeuvering: ComputingModule -> Boolean
	derived anomaly: ComputingModule -> Boolean

	//sono previste dalla specifiche?
	//forse no (al loro posto ci sono le tre luci "greenLight", "orangeLight" e "redLight")
	//composition of the global boolean variables to the cockpit
	/*derived gearsLockedDown: Boolean
	derived gearsManeuvering: Boolean
	derived anomaly: Boolean*/

	//PILOT INTERFACE
	//If gearsLockedDown(k) (for some k) is sent to the pilot
	//interface with the value true, then the green light "gears are locked down" is on
	derived greenLight: Boolean // true if on
	//If gearsManeuvering(k) (for some k) is sent to the pilot interface with the value
	//true, then the orange light "gears maneuvering" is on
	derived orangeLight: Boolean // true if on
	//If anomaly(k) (for some k) is sent to the pilot interface with the value true,
	//then the red light "landing gear system failure" is on
	derived redLight: Boolean // true if on

definitions:
	domain SensorNumber = {1 : 3}
	domain ComputingModule = {1 : 2}

	//These corresponding electrical orders outgoing from
	//the two modules are physically produced on the same electrical line. The implicit
	//composition of two outputs is an electrical â€œORâ€� as shown in Figure 5. For
	//instance, let us consider the general EV parameter. If the two modules produce
	//the same value on general EV1 and general EV2 , then this value is produced to
	//the general electro-valve. Otherwise, if only one of them is true (because of a
	//failure somewhere in the digital part), then the value true is produced to the
	//electro-valve, even if it is not the correct value. The problem will anyway be
	//detected at the next cycle when the module that produced the false value will
	//detect an unexpected behavior with respect to its own orders. Then it will inform
	//the pilot of a potential anomaly in the system.
	function generalEV = generalEV(1) or generalEV(2)
	function closeEV = closeEV(1) or closeEV(2)
	function openEV = openEV(1) or openEV(2)
	function retractEV = retractEV(1) or retractEV(2)
	function extendEV = extendEV(1) or extendEV(2)

	//gears_locked_down_k = true if and only if the three gears are seen as locked in
	//extended position (sensor gear_extended[x] = true forall x in {front, right, left})
	function gearsLockedDown($m in ComputingModule) = (forall $s in LandingSet with gearExtended($s))

	//gears_maneuvering_k = true if and only if at least one door or one gear is
	//maneuvering, i.e., at least one door is not locked in closed position or one
	//gear is not locked in extension or retraction position
	function gearsManeuvering($m in ComputingModule) =
		(exist $s in LandingSet with not(doorClosed($s)) or not(gearExtended($s)) or not(gearRetracted($s)))

	//The second objective of the control software is to detect anomalies and to inform
	//the pilot. Anomalies are caused by failures on hydraulic equipment, electrical
	//components, or computing modules.
	//An anomaly is detected each time a sensor is definitely considered as invalid.
	function anomaly($m in ComputingModule) =
		(isUndef(handle) or isUndef(analogicalSwitch) or isUndef(circuitPressurized) or
		 (exist $s in LandingSet with isUndef(gearExtended($s)) or isUndef(gearRetracted($s)) or
		 								isUndef(gearShockAbsorber($s)) or isUndef(doorClosed($s)) or
		 								isUndef(doorOpen($s)))
		)

	//If gearsLockedDown(k) (for some k) is sent to the pilot
	//interface with the value true, then the green light "gears are locked down" is on
	function greenLight = gearsLockedDown(1) or gearsLockedDown(2)
	//If gearsManeuvering(k) (for some k) is sent to the pilot interface with the value
	//true, then the orange light "gears maneuvering" is on
	function orangeLight = gearsManeuvering(1) or gearsManeuvering(2)
	//If anomaly(k) (for some k) is sent to the pilot interface with the value true,
	//then the red light "landing gear system failure" is on.
	//Expected behavior in case of anomaly. Whenever an anomaly is detected, the
	//system is globally considered as invalid. The data anomaly(k) = true is sent to the
	//pilot interface (where k is the part number of the module that has detected the
	//anomaly). This message is then maintained forever. The effect of this action is
	//to put on the red light "landing gear system failure".
	//Otherwise (no anomaly ever happened), the data anomaly(k) = false is sent
	//and maintained to the pilot interface. The effect of this action is to keep off the
	//red light "landing gear system failure".
	function redLight = anomaly(1) or anomaly(2)

	rule r_sensors =
		//- If at t the three channels are considered as valid and are equal, then the
		//value considered by the control software is this common value.
		//- If at t one channel is different from the two others for the first time (i.e.,
		//the three channels were considered as valid up to t), then this channel is
		//considered as invalid and is definitely eliminated. Only the two remaining
		//channels are considered in the future. At time t, the value considered by the
		//control software is the common value of the two remaining channels.
		//- If a channel has been eliminated previously, and if at t the two remaining
		//channels are not equal, then the sensor is definitely considered as invalid.
		//mettiamo a undef un sensore non piÃ¹ valido
		let ($x1 = if isDef(handle(1)) then handleMon(1) else undef endif,
			 $x2 = if isDef(handle(2)) then handleMon(2) else undef endif,
			 $x3 = if isDef(handle(3)) then handleMon(3) else undef endif
		    ) in
			if $x1 = $x2 and $x2 = handle(3) then
				handle = $x1
			else if $x1 = $x2 then
				if isDef($x1) then
					par
						handle = $x1
						handle(3) = undef //it could be already undef
					endpar
				else
					handle = $x3 //handle(3) is def
				endif
			else if $x1 = $x3 then
				if isDef($x1) then
					par
						handle = $x1
						handle(2) = undef //it could be already undef
					endpar
				else
					handle = $x2 //handle(2) is def
				endif
			else if $x2 = $x3 then
				if isDef($x2) then
					par
						handle = $x2
						$x1 = undef //it could be already undef
					endpar
				else
					handle = $x1 //handle(1) is def
				endif
			else
				par
					handle = undef
					handle(1) = undef
					handle(2) = undef
					handle(3) = undef
				endpar
			endif endif endif endif
		endlet

		if analogicalSwitch(1) = analogicalSwitch(2) and analogicalSwitch(2) = analogicalSwitch(3) then
			analogicalSwitch = analogicalSwitch(1)
		else if analogicalSwitch(1) = analogicalSwitch(2) then
			if isDef(analogicalSwitch(1)) then
				par
					analogicalSwitch = analogicalSwitch(1)
					analogicalSwitch(3) = undef //it could be already undef
				endpar
			else
				analogicalSwitch = analogicalSwitch(3) //analogicalSwitch(3) is def
			endif
		else if analogicalSwitch(1) = analogicalSwitch(3) then
			if isDef(analogicalSwitch(1)) then
				par
					analogicalSwitch = analogicalSwitch(1)
					analogicalSwitch(2) = undef //it could be already undef
				endpar
			else
				analogicalSwitch = analogicalSwitch(2) //analogicalSwitch(2) is def
			endif
		else if analogicalSwitch(2) = analogicalSwitch(3) then
			if isDef(analogicalSwitch(2)) then
				par
					analogicalSwitch = analogicalSwitch(2)
					analogicalSwitch(1) = undef //it could be already undef
				endpar
			else
				analogicalSwitch = analogicalSwitch(1) //analogicalSwitch(1) is def
			endif
		else
			par
				analogicalSwitch = undef
				analogicalSwitch(1) = undef
				analogicalSwitch(2) = undef
				analogicalSwitch(3) = undef
			endpar
		endif endif endif endif

		if circuitPressurized(1) = circuitPressurized(2) and circuitPressurized(2) = circuitPressurized(3) then
			circuitPressurized = circuitPressurized(1)
		else if circuitPressurized(1) = circuitPressurized(2) then
			if isDef(circuitPressurized(1)) then
				par
					circuitPressurized = circuitPressurized(1)
					circuitPressurized(3) = undef //it could be already undef
				endpar
			else
				circuitPressurized = circuitPressurized(3) //circuitPressurized(3) is def
			endif
		else if circuitPressurized(1) = circuitPressurized(3) then
			if isDef(circuitPressurized(1)) then
				par
					circuitPressurized = circuitPressurized(1)
					circuitPressurized(2) = undef //it could be already undef
				endpar
			else
				circuitPressurized = circuitPressurized(2) //circuitPressurized(2) is def
			endif
		else if circuitPressurized(2) = circuitPressurized(3) then
			if isDef(circuitPressurized(2)) then
				par
					circuitPressurized = circuitPressurized(2)
					circuitPressurized(1) = undef //it could be already undef
				endpar
			else
				circuitPressurized = circuitPressurized(1) //circuitPressurized(1) is def
			endif
		else
			par
				circuitPressurized = undef
				circuitPressurized(1) = undef
				circuitPressurized(2) = undef
				circuitPressurized(3) = undef
			endpar
		endif endif endif endif

		forall $s in LandingSet with true do
			par
				if gearExtended($s,1) = gearExtended($s,2) and gearExtended($s,2) = gearExtended($s,3) then
					gearExtended($s) = gearExtended($s,1)
				else if gearExtended($s,1) = gearExtended($s,2) then
					if isDef(gearExtended($s,1)) then
						par
							gearExtended($s) = gearExtended($s,1)
							gearExtended($s,3) = undef //it could be already undef
						endpar
					else
						gearExtended($s) = gearExtended($s,3) //gearExtended($s,3) is def
					endif
				else if gearExtended($s,1) = gearExtended($s,3) then
					if isDef(gearExtended($s,1)) then
						par
							gearExtended($s) = gearExtended($s,1)
							gearExtended($s,2) = undef //it could be already undef
						endpar
					else
						gearExtended($s) = gearExtended($s,2) //gearExtended($s,2) is def
					endif
				else if gearExtended($s,2) = gearExtended($s,3) then
					if isDef(gearExtended($s,2)) then
						par
							gearExtended($s) = gearExtended($s,2)
							gearExtended($s,1) = undef //it could be already undef
						endpar
					else
						gearExtended($s) = gearExtended($s,1) //gearExtended($s,1) is def
					endif
				else
					par
						gearExtended($s) = undef
						gearExtended($s,1) = undef
						gearExtended($s,2) = undef
						gearExtended($s,3) = undef
					endpar
				endif endif endif endif

				if gearRetracted($s,1) = gearRetracted($s,2) and gearRetracted($s,2) = gearRetracted($s,3) then
					gearRetracted($s) = gearRetracted($s,1)
				else if gearRetracted($s,1) = gearRetracted($s,2) then
					if isDef(gearRetracted($s,1)) then
						par
							gearRetracted($s) = gearRetracted($s,1)
							gearRetracted($s,3) = undef //it could be already undef
						endpar
					else
						gearRetracted($s) = gearRetracted($s,3) //gearRetracted($s,3) is def
					endif
				else if gearRetracted($s,1) = gearRetracted($s,3) then
					if isDef(gearRetracted($s,1)) then
						par
							gearRetracted($s) = gearRetracted($s,1)
							gearRetracted($s,2) = undef //it could be already undef
						endpar
					else
						gearRetracted = gearRetracted($s,2) //gearRetracted($s,2) is def
					endif
				else if gearRetracted($s,2) = gearRetracted($s,3) then
					if isDef(gearRetracted($s,2)) then
						par
							gearRetracted($s) = gearRetracted($s,2)
							gearRetracted($s,1) = undef //it could be already undef
						endpar
					else
						gearRetracted($s) = gearRetracted($s,1) //gearRetracted($s,1) is def
					endif
				else
					par
						gearRetracted($s) = undef
						gearRetracted($s,1) = undef
						gearRetracted($s,2) = undef
						gearRetracted($s,3) = undef
					endpar
				endif endif endif endif

				if gearShockAbsorber($s,1) = gearShockAbsorber($s,2) and gearShockAbsorber($s,2) = gearShockAbsorber($s,3) then
					gearShockAbsorber($s) = gearShockAbsorber($s,1)
				else if gearShockAbsorber($s,1) = gearShockAbsorber($s,2) then
					if isDef(gearShockAbsorber($s,1)) then
						par
							gearShockAbsorber($s) = gearShockAbsorber($s,1)
							gearShockAbsorber($s,3) = undef //it could be already undef
						endpar
					else
						gearShockAbsorber($s) = gearShockAbsorber($s,3) //gearShockAbsorber($s,3) is def
					endif
				else if gearShockAbsorber($s,1) = gearShockAbsorber($s,3) then
					if isDef(gearShockAbsorber($s,1)) then
						par
							gearShockAbsorber($s) = gearShockAbsorber($s,1)
							gearShockAbsorber($s,2) = undef //it could be already undef
						endpar
					else
						gearShockAbsorber($s) = gearShockAbsorber($s,2) //gearShockAbsorber($s,2) is def
					endif
				else if gearShockAbsorber($s,2) = gearShockAbsorber($s,3) then
					if isDef(gearShockAbsorber($s,2)) then
						par
							gearShockAbsorber($s) = gearShockAbsorber($s,2)
							gearShockAbsorber($s,1) = undef //it could be already undef
						endpar
					else
						gearShockAbsorber($s) = gearShockAbsorber($s,1) //gearShockAbsorber($s,1) is def
					endif
				else
					par
						gearShockAbsorber($s) = undef
						gearShockAbsorber($s,1) = undef
						gearShockAbsorber($s,2) = undef
						gearShockAbsorber($s,3) = undef
					endpar
				endif endif endif endif

				if doorClosed($s,1) = doorClosed($s,2) and doorClosed($s,2) = doorClosed($s,3) then
					doorClosed($s) = doorClosed($s,1)
				else if doorClosed($s,1) = doorClosed($s,2) then
					if isDef(doorClosed($s,1)) then
						par
							doorClosed($s) = doorClosed($s,1)
							doorClosed($s,3) = undef //it could be already undef
						endpar
					else
						doorClosed($s) = doorClosed($s,3) //doorClosed($s,3) is def
					endif
				else if doorClosed($s,1) = doorClosed($s,3) then
					if isDef(doorClosed($s,1)) then
						par
							doorClosed($s) = doorClosed($s,1)
							doorClosed($s,2) = undef //it could be already undef
						endpar
					else
						doorClosed($s) = doorClosed($s,2) //doorClosed($s,2) is def
					endif
				else if doorClosed($s,2) = doorClosed($s,3) then
					if isDef(doorClosed($s,2)) then
						par
							doorClosed($s) = doorClosed($s,2)
							doorClosed($s,1) = undef //it could be already undef
						endpar
					else
						doorClosed($s) = doorClosed($s,1) //doorClosed($s,1) is def
					endif
				else
					par
						doorClosed($s) = undef
						doorClosed($s,1) = undef
						doorClosed($s,2) = undef
						doorClosed($s,3) = undef
					endpar
				endif endif endif endif

				if doorOpen($s,1) = doorOpen($s,2) and doorOpen($s,2) = doorOpen($s,3) then
					doorOpen($s) = doorOpen($s,1)
				else if doorOpen($s,1) = doorOpen($s,2) then
					if isDef(doorOpen($s,1)) then
						par
							doorOpen($s) = doorOpen($s,1)
							doorOpen($s,3) = undef //it could be already undef
						endpar
					else
						doorOpen($s) = doorOpen($s,3) //doorOpen($s,3) is def
					endif
				else if doorOpen($s,1) = doorOpen($s,3) then
					if isDef(doorOpen($s,1)) then
						par
							doorOpen($s) = doorOpen($s,1)
							doorOpen($s,2) = undef //it could be already undef
						endpar
					else
						doorOpen($s) = doorOpen($s,2) //doorOpen($s,2) is def
					endif
				else if doorOpen($s,2) = doorOpen($s,3) then
					if isDef(doorOpen($s,2)) then
						par
							doorOpen($s) = doorOpen($s,2)
							doorOpen($s,1) = undef //it could be already undef
						endpar
					else
						doorOpen($s) = doorOpen($s,1) //doorOpen($s,1) is def
					endif
				else
					par
						doorOpen($s) = undef
						doorOpen($s,1) = undef
						doorOpen($s,2) = undef
						doorOpen($s,3) = undef
					endpar
				endif endif endif endif
			endpar

	rule r_analogicalSwitch = 
		if not(analogicalSwitchFailure) then
			switch analogicalSwitchState
				case OPEN:
					if handle then
						par
							analogicalSwitchState := INTERMEDIATE1
							analogicalSwitchOut := false //superfluo
						endpar 
					endif
				case INTERMEDIATE1:
					//sono passati 0.8 secondi
					if openToClosedPeriodPassed then
						analogicalSwitchState := CLOSED
						analogicalSwitchOut := analogicalSwitchIn
						analogicalSwitchStateFromOutside := CLOSED
					endif
				case CLOSED
					//sono passati 20 secondi
					if closingPeriodPassed then
						analogicalSwitchState := INTERMEDIATE2
						analogicalSwitchOut := false
						analogicalSwitchStateFromOutside := OPEN
					endif
				case INTERMEDIATE2
					if handle then
						par
							analogicalSwitchState := INTERMEDIATE1
							analogicalSwitchOut := false
						endpar
					else if closedToOpenPeriodPassed then //sono passati 1.2 secondi
						analogicalSwitchState := OPEN
						analogicalSwitchOut := false //superfluo
						analogicalSwitchStateFromOutside := OPEN //superfluo
					endif
		else
			if analogicalSwitchState = CLOSED then
				par
					analogicalSwitchState := BLOCKED_CLOSED
					analogicalSwitchOut := analogicalSwitchIn //superfluo?
					analogicalSwitchStateFromOutside := CLOSED
				endpar
			else
				par
					analogicalSwitchState := BLOCKED_OPEN
					analogicalSwitchOut := false //superfluo
					analogicalSwitchStateFromOutside := OPEN //superfluo
				endpar
			endif
		endif

	//giusto?
	invariant over gearsLockedDown: (forall $m in ComputingModule with gearsLockedDown($m) != gearsManeuvering($m))

	main rule r_Main =
		par
			r_sensors[]
			r_analogicalSwitch[]
		endpar

default init s0:
	function systemMode = NOMINAL
