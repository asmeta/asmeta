//Forth refinement of Adaptive Exterior Light and Speed Control System
//Direction Blinking
//from ELS-1 to ELS-7

module CarSystem004Blink
import ../../StandardLibrary
import CarSystem004Functions
export *

signature:
	// DOMAINS
	
	
	// FUNCTIONS
	//Parameters setted initially
	controlled blinkLeft: LightPercentage // Direction indicators A_left, B_left and C_left don't blink (0%) or blink with a specific percentage of brightness
	controlled blinkLeftPulseRatio: PulseRatio //* Pulse ratio of left direction indicators
	controlled blinkRight: LightPercentage // Direction indicators A_right, B_right and C_right don't blink (0%) or blink with a specific percentage of brightness
	controlled blinkRightPulseRatio: PulseRatio //* Pulse ratio of right direction indicators
	controlled tailLampLeftStatus: TailLampStatus // Tail lamp left status
	controlled tailLampRightStatus: TailLampStatus // Tail lamp right status	
	monitored threeBlinkingCycle: Boolean // Indicators have flashed for three flashing cycles (true) or less (false)
		
	derived tailLampAsIndicator: Boolean //True if the car is sold in USA or CANADA, it does not have a separate direction ndicator at position C	
	derived pitman5Deflection: PitmanArmUpDown -> Boolean //True if pitman is 5° deflection (UPWARD5 or DOWNWARD5)
	derived pitman5LongDeflection: PitmanArmUpDown -> Boolean //True if pitman is 5° long deflection (UPWARD5_LONG or DOWNWARD5_LONG)
	derived pitman7Deflection: PitmanArmUpDown -> Boolean //True if pitman is 7° deflection (UPWARD7 or DOWNWARD7)
	
	
definitions:
	
	
	// FUNCTION DEFINITIONS
	
	//True if pitman is 5° deflection (UPWARD5 or DOWNWARD5)
	function pitman5Deflection ($position in PitmanArmUpDown) = 
		($position = UPWARD5 or $position = DOWNWARD5)
	
	//True if pitman is 5° long deflection (UPWARD5_LONG or DOWNWARD5_LONG)
	function pitman5LongDeflection ($position in PitmanArmUpDown) = 
		($position = UPWARD5_LONG or $position = DOWNWARD5_LONG)
		
	//True if pitman is 7° deflection (UPWARD7 or DOWNWARD7)
	function pitman7Deflection ($position in PitmanArmUpDown) = 
		($position = UPWARD7 or $position = DOWNWARD7)
	
	//True if the car is sold in USA or CANADA, it does not have a separate direction ndicator at position C
	function tailLampAsIndicator = 
		(marketCode = USA or marketCode = CANADA)
	
	// RULE DEFINITIONS
	
	//Set tail lamp status
	macro rule r_setTailLampLeft ($value in LightPercentage , $status in TailLampStatus) = 
		par
			tailLampLeftStatus := $status 
			tailLampLeftBlinkValue := setOverVoltageValueLight($value)
		endpar
	
	macro rule r_setTailLampRight ($value in LightPercentage , $status in TailLampStatus) = 
		par
			tailLampRightStatus := $status 
			tailLampRightBlinkValue := setOverVoltageValueLight($value)
		endpar
	
	//Turn on/off blink left
	macro rule r_BlinkLeft ($value in LightPercentage, $pulse in PulseRatio) = 
		par
			blinkLeft := setOverVoltageValueLight($value)
			blinkLeftPulseRatio := $pulse
			//ELS-6
			if (tailLampAsIndicator) then
				if ($value = 0) then
					r_setTailLampLeft[0,FIX]
				else
					r_setTailLampLeft[50,BLINK] 
				endif
			endif 
		endpar
		
	macro rule r_BlinkRight ($value in LightPercentage, $pulse in PulseRatio) = 
		par
			blinkRight := setOverVoltageValueLight($value)
			blinkRightPulseRatio := $pulse
			//ELS-6
			if (tailLampAsIndicator) then
				if ($value = 0) then
					r_setTailLampRight[0,FIX] 
				else
					r_setTailLampRight[50,BLINK] 
				endif
			endif 
		endpar
	
	//ELS-1
	macro rule r_RunDirectionBlinking ($position in PitmanArmUpDown) =
		par
			//when new Direction Blinking starts, delete value in the buffer and set the current value as running request
			pitmanArmUpDown_Buff := NEUTRAL_UD 
			pitmanArmUpDown_RunnReq := $position 
			//If pitman is downward of 5° or 7° turn left
			if (turnLeft($position)) then
				r_BlinkLeft[100,PULSE11] 	
			endif
			//If pitman is downward of 5° or 7° turn right
			if (turnRight($position)) then
				r_BlinkRight[100,PULSE11] 
			endif
		endpar
	
	//Interrupt blinking	
	macro rule r_InterruptBlinking =
		par
			pitmanArmUpDown_RunnReq := NEUTRAL_UD
			//If pitman is downward interrupt turn left
			if (turnLeft(pitmanArmUpDown_RunnReq)) then
				r_BlinkLeft[0,NOPULSE] 
			endif
			//If pitman is upward interrupt turn right
			if (turnRight(pitmanArmUpDown_RunnReq)) then
				r_BlinkRight[0,NOPULSE] 
			endif
		endpar
			
	macro rule r_CompleteFlashingCycle =
		par
			//ELS-1
			if (pitman7Deflection(pitmanArmUpDown_RunnReq)) then
				if (pitmanArmUpDown = NEUTRAL_UD) then
					r_InterruptBlinking[] 
				endif
			endif
			//ELS-2
			if (pitman5Deflection(pitmanArmUpDown_RunnReq)) then
				if (threeBlinkingCycle = true) then
					r_InterruptBlinking[] 
				endif
			endif
			//ELS-4
			if (pitman5LongDeflection(pitmanArmUpDown_RunnReq)) then
				if (pitmanArmUpDown = NEUTRAL_UD) then
					r_InterruptBlinking[] 
				endif
			endif
		endpar
	
	macro rule r_DirectionBlinking =
		//Function available only if key is on ON position
		if (keyState = KEYINIGNITIONONPOSITION) then
			//No request is running
				if (pitmanArmUpDown_RunnReq = NEUTRAL_UD) then
					//Run current request if present
					if (pitmanArmUpDown != NEUTRAL_UD) then
						r_RunDirectionBlinking[pitmanArmUpDown] 
					//Run request in buffer if present
					else 
						if (pitmanArmUpDown_Buff != NEUTRAL_UD) then
							r_RunDirectionBlinking[pitmanArmUpDown_Buff] 
						endif
					endif
				//ELS-7 Request is running and current request is activated
				else 
					if (pitmanArmUpDown_RunnReq != NEUTRAL_UD and pitmanArmUpDown != NEUTRAL_UD) then
						//Running request direction is equal as current request
						if ((turnLeft(pitmanArmUpDown_RunnReq) and turnLeft(pitmanArmUpDown)) or (turnRight(pitmanArmUpDown_RunnReq) and  turnRight(pitmanArmUpDown))) then
							if (not(pitman5LongDeflection(pitmanArmUpDown_RunnReq))) then 
								par
									pitmanArmUpDown_RunnReq := pitmanArmUpDown 
									pitmanArmUpDown_Buff := NEUTRAL_UD
								endpar
							endif
						else 
							//Running request and current request are different
							if (pitmanArmUpDown_RunnReq != pitmanArmUpDown) then
								par
									r_InterruptBlinking[] 
									pitmanArmUpDown_Buff := pitmanArmUpDown
								endpar
							endif
						endif
					//Request is running and no other request is activated
					else 
						if (pitmanArmUpDown_RunnReq != NEUTRAL_UD) then
							r_CompleteFlashingCycle[]  
						endif
					endif
				endif
		//If key is not on ON position interrupt current blinking if exists
		else
			if (pitmanArmUpDown_RunnReq != NEUTRAL_UD) then
				par
					r_InterruptBlinking[] 
					pitmanArmUpDown_RunnReq := NEUTRAL_UD
					pitmanArmUpDown_Buff := NEUTRAL_UD
				endpar
			endif
		endif	