//version of ComfortableHeating_ref adapted for model checking

asm ComfortableHeating_ref_MC
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/LTLlibrary

signature:
	//ComfortableHeatingMAPE
	domain HeaterMdA subsetof Agent
	domain MainCHMgA subsetof Agent
	domain IntTempMgA subsetof Agent
	
	//domains added in refinement
	enum domain HeatingStatus = {FAIRLY_HOT | VERY_HOT | OFF}
	domain Temperature subsetof Integer
	enum domain Time = {EARLY_MORN | MORN_AFT | EVENING}

	derived startMainCHM: MainCHMgA -> Boolean
	derived startMainCHA: MainCHMgA -> Boolean
	//derived startMainCHP: MainCHMgA -> Boolean
	//derived startMainCHE: MainCHMgA -> Boolean
	controlled heaterManagedByIntTemp: IntTempMgA -> HeaterMdA
	//derived startIntTempM: IntTempMgA -> Boolean
	derived startIntTempE: IntTempMgA -> Boolean
	//I: IntTemp.M -> MainCH.M [*-ONE,1]
	controlled sgnIntTempMMainCHM: Prod(IntTempMgA, MainCHMgA) -> Boolean
	controlled fromIntTempMtoMainCHM: IntTempMgA -> MainCHMgA
	//controlled fromMainCHMtoIntTempM: MainCHMgA -> Powerset(IntTempMgA)
	//I: MainCH.E -> IntTemp.E [1,*-ALL]
	controlled sgnMainCHEIntTempE: Prod(MainCHMgA, IntTempMgA) -> Boolean
	//controlled fromMainCHEtoIntTempE: MainCHMgA -> Powerset(IntTempMgA)
	controlled fromIntTempEtoMainCHE: IntTempMgA -> MainCHMgA
	//ComfortableHeating
	static hs0: HeaterMdA
	static hs1: HeaterMdA
	static ch: MainCHMgA
	static h0: IntTempMgA
	static h1: IntTempMgA

	//functions added in refinement
	monitored roomTemp: IntTempMgA -> Temperature //room sensor
	controlled roomTempSaved: IntTempMgA -> Temperature //room sensor
	monitored timePassed: Boolean
	controlled time: Time
	derived avgTemp: MainCHMgA -> Temperature
	derived computeHeating : MainCHMgA -> HeatingStatus
	controlled desiredHeating: MainCHMgA -> HeatingStatus //heating status to set
	//shared between managed and managing agents
	controlled setHeatingStatus: HeaterMdA -> HeatingStatus

	//**** Function for the coordination of the overall system ****
	//derived startMainM: Boolean
	//derived adaptationRequiredMainA: Boolean
	monitored sensorsActivatedHeater: HeaterMdA -> Boolean //Used to trigger the model execution; it means some sensors are ready for reading

	//Used to strictly sequence different MAPE loop executions
	//(just an example of a MAPE execution schema)
	controlled loopCompletedIT: IntTempMgA -> Boolean

definitions:
	domain Temperature = {0..100}
	
	function avgTemp($a in MainCHMgA) =
		idiv((roomTempSaved(h0) + roomTempSaved(h1)),2)

	function computeHeating($a in MainCHMgA) =
		if avgTemp($a) < 10 then
			VERY_HOT
		else
			if avgTemp($a) < 18 then
				FAIRLY_HOT
			else
				OFF
			endif
		endif

	function startMainCHM($b in MainCHMgA) =
		sgnIntTempMMainCHM(h0, $b) and sgnIntTempMMainCHM(h1, $b)

	function startMainCHA($b in MainCHMgA) =
		neq(desiredHeating($b), computeHeating($b))

	function startIntTempE($b in IntTempMgA) =
		//sgnMainCHEIntTempE(fromIntTempEtoMainCHE($b), $b)
		sgnMainCHEIntTempE(ch, $b)

	rule r_start($signal in Boolean) =
		if $signal = false then 
			$signal := true 
		endif

	rule r_Heater =
		if isDef(setHeatingStatus(self)) then
			setHeatingStatus(self) := undef //signal reset
		endif

	rule r_CleanUp_MainCHE =
		skip 

	rule r_MainCHE =
		par
			desiredHeating(self) := computeHeating(self)
			par
				sgnMainCHEIntTempE(self, h0) := true
				sgnMainCHEIntTempE(self, h1) := true
			endpar
			r_CleanUp_MainCHE[]
		endpar

	rule r_CleanUp_MainCHA =
		skip

	rule r_CleanUp_LoopCompletedCHA =
		//if no adaptation was necessary, cleaning of loop completion flags is done here
		par
			loopCompletedIT(h0) := false
			loopCompletedIT(h1) := false
		endpar

	rule r_MainCHA =
		if startMainCHA(self) then
			par
				r_MainCHE[] //@E_MAPE_CH direct invocation (waterfall), without planning
				r_CleanUp_MainCHA[]
			endpar
		else
			r_CleanUp_LoopCompletedCHA[] //for loop coordination
		endif

	rule r_CleanUp_MainCHM =
		par
			sgnIntTempMMainCHM(h0, self) := false
			sgnIntTempMMainCHM(h1, self) := false
		endpar

	//MAPE_CH: ...
	rule r_MainCHM =
		if startMainCHM(self) then
			par
				r_MainCHA[] //@A_MAPE_CH direct invocation (waterfall)
				r_CleanUp_MainCHM[]
			endpar
		endif

	rule r_MainCH =
		r_MainCHM[] //@M_MAPE_CH direct invocation (waterfall)

	rule r_CleanUp_IntTempM =
		loopCompletedIT(self) := false // reset

	//Saves the room temperature into the knowledge based on the
	//registered value of the room temperature sensor 
	rule r_SaveSensorsData_Heater =
		roomTempSaved(self) := roomTemp(self)

	rule r_IntTempM =
		if loopCompletedIT(self) then //***coordination***: for sequencing different MAPE loops executions
			if sensorsActivatedHeater(heaterManagedByIntTemp(self)) then
				par
					r_SaveSensorsData_Heater[]
					r_start[sgnIntTempMMainCHM(self,ch)] //A_MAPE_CH indirect invocation
					r_CleanUp_IntTempM[]
				endpar
			endif
		endif

	rule r_TriggerActuators_Heater($s in HeaterMdA, $b in IntTempMgA) =
		setHeatingStatus($s) := desiredHeating(ch)

	rule r_CleanUp_IntTempE =
		par
			sgnMainCHEIntTempE(fromIntTempEtoMainCHE(self), self) := false
			loopCompletedIT(self) := true
		endpar

	rule r_IntTempE =
		if startIntTempE(self) then
			par
				let ($x = heaterManagedByIntTemp(self)) in
					r_TriggerActuators_Heater[$x, self] //Actions are executed by invoking effectors (actuators)
				endlet
				r_CleanUp_IntTempE[]
			endpar
		endif

	rule r_IntTemp =
		par
			r_IntTempM[]
			r_IntTempE[]
		endpar

	rule r_advanceTime =
		if timePassed then
			switch time
				case EARLY_MORN:
					time := MORN_AFT
				case MORN_AFT:
					time := EVENING
				case EVENING:
					time := EARLY_MORN
			endswitch
		endif

	INVAR roomTemp(h0) < 50 and roomTemp(h1) < 50
	
	LTLSPEC g(avgTemp(ch) < 18 implies f(setHeatingStatus(hs0) != OFF and setHeatingStatus(hs1) != OFF))
	LTLSPEC g(setHeatingStatus(hs0) != OFF implies o(avgTemp(ch) < 18))
	LTLSPEC g(setHeatingStatus(hs1) != OFF implies o(avgTemp(ch) < 18))
	LTLSPEC not f(setHeatingStatus(hs0) = FAIRLY_HOT)//this is expected to be false
	LTLSPEC not f(setHeatingStatus(hs0) = VERY_HOT)//this is expected to be false
	LTLSPEC not f(setHeatingStatus(hs1) = FAIRLY_HOT)//this is expected to be false
	LTLSPEC not f(setHeatingStatus(hs1) = VERY_HOT)//this is expected to be false

	main rule r_main =
		par
			program(hs0)
			program(hs1)
			program(ch)
			program(h0)
			program(h1)
		endpar

default init s0:
	function sgnIntTempMMainCHM($a in IntTempMgA, $b in MainCHMgA) = false
	function sgnMainCHEIntTempE($a in MainCHMgA, $b in IntTempMgA) = false
	function fromIntTempMtoMainCHM($a in IntTempMgA) = ch

	/*function fromMainCHMtoIntTempM($a in MainCHMgA) =
		switch($a)
			case ch: {h0, h1}
		endswitch*/

	/*function fromMainCHEtoIntTempE($a in MainCHMgA) =
		switch($a)
			case ch: {h0, h1}
		endswitch*/

	function fromIntTempEtoMainCHE($a in IntTempMgA) = ch

	function heaterManagedByIntTemp($x in IntTempMgA) =
		/*switch($x)
			case h0: hs0
			case h1: hs1
		endswitch*/
		if $x = h0 then
			hs0
		else
			hs1
		endif

	function loopCompletedIT ($a in IntTempMgA) = true
	function time = EARLY_MORN
	function desiredHeating($a in MainCHMgA) = OFF

	agent MainCHMgA: r_MainCH[]
	agent IntTempMgA: r_IntTemp[]
	agent HeaterMdA: r_Heater[]
