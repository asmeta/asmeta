//Forth refinement  of Adaptive Exterior Light and Speed Control System
//Hazard warning light
//from ELS-8 to ELS-13

module CarSystem004HW
import ../../StandardLibrary
import CarSystem004Blink
export *

signature:
	// DOMAINS
	
	
	// FUNCTIONS
	//Parameters setted initially
	monitored hazardWarningSwitchOn: Boolean // Position of the Hazard Warning Light Switch. True -> ON, False -> OFF
	controlled hazardWarningSwitchOn_Runn: Boolean // Hazard Warning Light Switch is running or not
	controlled hazardWarningSwitchOn_Start: Boolean // Start Hazard Warning Light Switch in the next cycle
	
definitions:
	
	// RULE DEFINITIONS
	
	//Deactivate Hazard Warning
	macro rule r_InterruptHazardWarning =
		par
			hazardWarningSwitchOn_Runn := false
			r_BlinkLeft[0,NOPULSE]
			r_BlinkRight[0,NOPULSE]
		endpar
	
	//Set Hazard Warning Pulse ratio ELS-8 ELS-9
	macro rule r_AdaptHazardWarningPulseRatio =
		if (keyState = KEYINIGNITIONONPOSITION or keyState = KEYINSERTED) then
			par
				r_BlinkLeft[100,PULSE11] 
				r_BlinkRight[100,PULSE11] 
			endpar
		else
			par
				r_BlinkLeft[100,PULSE12] 
				r_BlinkRight[100,PULSE12] 
			endpar
		endif
	
	//ELS-13 Save pitmanarm request if hazard warning is running, only blinking (no tip-blinking) 
	macro rule r_SavePitmanArmUpDownReq =
		if (pitmanArmUpDown = DOWNWARD7 or pitmanArmUpDown = UPWARD7 or pitmanArmUpDown = NEUTRAL_UD) then
			pitmanArmUpDown_Buff := pitmanArmUpDown
		endif
	
	//ELS-8
	macro rule r_StartHazardWarning =
		par
			hazardWarningSwitchOn_Start:=false
			hazardWarningSwitchOn_Runn := true
			r_AdaptHazardWarningPulseRatio[] 
		endpar
		
	macro rule r_HazardWarningLight =
		// If Hazard warning is running
		if (hazardWarningSwitchOn_Runn) then
			par
				//If user wants to turn off hazard warning
				if ((not hazardWarningSwitchOn)) then
					par
						hazardWarningSwitchOn_Runn := false
						r_InterruptHazardWarning[]
					endpar
				else
					r_AdaptHazardWarningPulseRatio[]
				endif
				//If HW is running save the request from pitman arm UpDown into the buffer
				r_SavePitmanArmUpDownReq[] 
			endpar
		else // If HW is not running
			// If the user wants to activate hazard warning and he didn't push the button in the previous state
			if ((not hazardWarningSwitchOn_Start)) then
				if (hazardWarningSwitchOn) then
					par 
						//Interrupt Blinking if running ELS-13
						if (pitmanArmUpDown_RunnReq != NEUTRAL_UD) then
							r_InterruptBlinking[] 
						endif
						//Start HW in the next state because of ELS-11
						hazardWarningSwitchOn_Start := true
						//If request from pitman arm UpDown arrives save it into the buffer
						r_SavePitmanArmUpDownReq[] 
					endpar
				else
					r_DirectionBlinking[] 
				endif
			else 
				par
					//Start HW
					r_StartHazardWarning[] 
					//If request from pitman arm UpDown arrives save it into the buffer
					r_SavePitmanArmUpDownReq[] 
				endpar
			endif
		endif
