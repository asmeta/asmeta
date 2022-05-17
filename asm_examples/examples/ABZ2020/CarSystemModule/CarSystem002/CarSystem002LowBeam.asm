//Second refinement of Adaptive Exterior Light and Speed Control System
//Low Beam headlights
//from ELS-14 to ELS-23
//ELS-28 and ELS-29

module CarSystem002LowBeam
import ../../StandardLibrary
import CarSystem002Functions
export *

signature:
	
	// DOMAINS
	enum domain LeftRight = {LEFT | RIGHT}
	
//====================================
	domain Brightness subsetof Integer // Brightness domain
	
	// FUNCTIONS
	
//====================================		
	monitored brightnessSensor: Brightness // The sensor provides the measured outside brightness 
	monitored passed30Sec: Boolean // Are 30 seconds passed?
	monitored passed5Sec: Boolean // Are 5 seconds passed?
	monitored passed3Sec: Boolean // Are 3 seconds passed?
	monitored darknessModeSwitchOn: Boolean // Position of the Darkness Switch, available only at armored vehicles. True -> ON, False -> OFF
	
	controlled lowBeamLeft: LightPercentage // Low beam headlight left is off (0%) or is on with a specific percentage of brightness
	controlled lowBeamRight: LightPercentage // Low beam headlight right is off (0%) or is on with a specific percentage of brightness
	controlled parkingLightON : Boolean //parking light is on

	controlled tailLampLeftFixValue: LightPercentage // Tail lamp left is off (0%) or is on with a specific percentage of brightness
	controlled tailLampRightFixValue: LightPercentage // Tail lamp right is off (0%) or is on with a specific percentage of brightness
//====================================			
	derived lowBeamLightingOn: Boolean // Low beam lights are on or off
	derived parkingLight: Boolean //Parking light on or off
	derived ambientLightingConditionOn: Boolean // Ambient lighting is available
	derived ambientLightingAvailable: Boolean // Ambient lighting is activated
	//It is obtained by the value assumed by tail lamp in blinking mode and tail lamp as position lamp
	derived tailLampRight: LightPercentage // Tail lamp right is off (0%) or is on with a specific percentage of brightness. 
	derived tailLampLeft: LightPercentage // Tail lamp right is off (0%) or is on with a specific percentage of brightness
	derived brakeIsOn: Boolean //Brake lamps are on
	
definitions:
	// DOMAIN DEFINITIONS
//====================================		
	domain Brightness = {0 :300} // 100000  Si può ridurre a 300? (valore massimo da specifica)
	
	
	
	// FUNCTION DEFINITIONS
		
//====================================			
		
	//Low beam lights are on or off
	function lowBeamLightingOn = 
		(lowBeamLeft != 0 and lowBeamRight != 0) 
	
	//Activate parking light if true
	function parkingLight = 	
		(keyState = NOKEYINSERTED and lightRotarySwitch = ON and (pitmanArmUpDown = UPWARD7 or pitmanArmUpDown = DOWNWARD7)) 
			
	
	//ELS-21
	//ambient lighting is not activated if it is true but the vehicle is armored vehicle with darknewss mode on ELS-21
	function ambientLightingAvailable = 
		(ambientLighting  and not (armoredVehicle and darknessModeSwitchOn )) 
	
	//ambient light if active is activated only if this function is true
	function ambientLightingConditionOn = 
	(engineOn(keyState_Previous) and (not engineOn(keyState)) and brightnessSensor<200) 
			
	//ELS-23
	//Tail lamp value
	function tailLampLeft =
		if (tailLampLeftBlinkValue != 0 and not parkingLightON ) then
			tailLampLeftBlinkValue
		else
			tailLampLeftFixValue
		endif
		
	function tailLampRight =
		if (tailLampRightBlinkValue != 0  and not parkingLightON ) then
			tailLampRightBlinkValue
		else
			tailLampRightFixValue
		endif
	
	function brakeIsOn = 
		 (brakeLampLeft != 0 and brakeLampRight != 0 and brakeLampCenter != 0)			
	
	// RULE DEFINITIONS
	

	//Set low beam tail lamp on off
	macro rule r_LowBeamTailLampLeftOnOff ($value in LightPercentage) =
		par
			lowBeamLeft := $value
			tailLampLeftFixValue := $value 
		endpar
		
	//Set low beam tail lamp on off	
	macro rule r_LowBeamTailLampRightOnOff ($value in LightPercentage) =
		par
			lowBeamRight := $value
			tailLampRightFixValue := $value
		endpar
	
	//ELS-22
	macro rule r_LowBeamTailLampOnOff ($value in LightPercentage) =
		par
			r_LowBeamTailLampLeftOnOff[$value] 
			r_LowBeamTailLampRightOnOff[$value] 
		endpar
	
	//Turn on parking light ELS-28
	macro rule r_parkingLight = 
		par
			if (pitmanArmUpDown = UPWARD7) then
				par
					r_LowBeamTailLampLeftOnOff[0] 
					r_LowBeamTailLampRightOnOff[10] 
				endpar
			endif
			if (pitmanArmUpDown = DOWNWARD7) then
				par
					r_LowBeamTailLampLeftOnOff[10] 
					r_LowBeamTailLampRightOnOff[0]
				endpar 
			endif
		endpar
	
	macro rule 	r_LowBeamHeadlights =
		par
		//ELS-14
		//LowBeamON.avalla
			if (lightRotarySwitch = ON and engineOn(keyState)) then
				r_LowBeamTailLampOnOff[100] 
			endif
		//ELS-19
		//LowBeamONWithAmbientLight.avalla
			if (ambientLightingAvailable and ambientLightingConditionOn) then
				if ((not lowBeamLightingOn)) then
					r_LowBeamTailLampOnOff[100] 
				endif
			endif
		//ELS-19	
		//LowBeamOFFonAmbientLight.avalla
			if (ambientLightingAvailable and lowBeamLightingOn and (not engineOn(keyState))) then
				if (passed30Sec) then
		//ParkingLightONAmbientLight.avalla
					if (parkingLight) then
						par
							r_parkingLight[] //	ELS-28
							parkingLightON := true
						endpar
					else
		//LowBeamOFFonAmbientLight.avalla
						r_LowBeamTailLampOnOff[0] 
					endif
				else
		//ParkingLightONAmbientLightno30sec
					if (parkingLight) then
						parkingLightON := true
					endif
				endif
			endif
		//ELS-15
		//LowBeamONKey.avalla
			//if not (ambientLightingAvailable and ambientLightingConditionOn) then
			if  (not ambientLightingAvailable) then
				par
					if (keyState = KEYINSERTED and lightRotarySwitchPrevious != ON and lightRotarySwitch = ON) then
						r_LowBeamTailLampOnOff[50] 
					endif
					if (not daytimeLight) then
						par
		//LowBeamOFFPowerOFFKey.avalla
							if (keyState = KEYINSERTED and engineOn(keyState_Previous) and (not engineOn(keyState)) and lightRotarySwitchPrevious = ON and lightRotarySwitch = ON) then
								r_LowBeamTailLampOnOff[0] 
							endif
		//LowBeamOFFPowerOFFNoKey.avalla
							if (keyState = NOKEYINSERTED and lightRotarySwitchPrevious = ON and lightRotarySwitch = ON and (not parkingLight)) then
								r_LowBeamTailLampOnOff[0]
							endif
						endpar
					endif
				endpar
			endif
		//ELS-17
		//LowBeamONWithDaylight.avalla
			if (daytimeLight) then
			//	if ((not ambientLightingAvailable) or (not (ambientLightingAvailable and ambientLightingConditionOn)))  then 			
				if (not ambientLightingAvailable or not ambientLightingConditionOn)  then 
					//if ((keyState = KEYINSERTED or keyState = KEYINIGNITIONONPOSITION) and ((not engineOn(keyState_Previous)) and engineOn = true)) then
					if ((not engineOn(keyState_Previous)) and engineOn(keyState)) then
						par
							if ((not lowBeamLightingOn)) then
								r_LowBeamTailLampOnOff[100] 
							endif
							//Turn off parking light
		//ParkingLightOFF.avalla
							if (parkingLightON) then
								parkingLightON := false
							endif
						endpar
					endif
				endif
			endif
	
			//ELS-16 ELS-17, ELS-16 has priority over ELS-17
			if (not ambientLightingAvailable) then
			//ELS-16
		//LowBeamAutoOFFonSwitch.avalla	
				if ((not engineOn(keyState_Previous)) and (not engineOn(keyState)) and lightRotarySwitch = AUTO and lightRotarySwitchPrevious != AUTO) then
					r_LowBeamTailLampOnOff[0] 
				else
		//LowBeamAutoOFFonDaylight.avalla
					if ((not parkingLight) and daytimeLight and keyState = NOKEYINSERTED and (keyState_Previous = KEYINSERTED or keyState_Previous = KEYINIGNITIONONPOSITION)) then
						r_LowBeamTailLampOnOff[0] 
					endif
				endif
			endif
		//ELS-18
		//LowBeamAutoON.avalla
			if (lightRotarySwitch = AUTO and engineOn(keyState) and brightnessSensor<200) then 
				if ((not lowBeamLightingOn)) then
					r_LowBeamTailLampOnOff[100] 
				endif
			endif
		//LowBeamAutoOFF.avalla
			if (lightRotarySwitch = AUTO and engineOn(keyState) and brightnessSensor>250) then
				if (lowBeamLightingOn and passed3Sec) then
					r_LowBeamTailLampOnOff[0] 
				endif
			endif
		//ELS-28
		//ParkingLightONnoAmbient.avalla
			if (not ambientLightingAvailable and parkingLight) then
				par
					r_parkingLight[] 
					parkingLightON := true
				endpar
			endif
		//	if (parkingLightON) then
		//		r_parkingLight[] 
		//	endif
		endpar
	
	
		
	// INVARIANTS
	
	
	//PROPERTIES
		
	// INVARIANTS
	
	
	//PROPERTIES
