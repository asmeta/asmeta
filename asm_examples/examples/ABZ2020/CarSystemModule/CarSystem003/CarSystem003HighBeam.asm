//Third refinement of Adaptive Exterior Light and Speed Control System
//Hight Beam
//from ELS-30 to ELS-38

module CarSystem003HighBeam
import ../../StandardLibrary
import CarSystem003Domains
import ../CarSystem002/CarSystem002Functions
export *

signature:
	
	enum domain CameraState = {CAMERA_READY | CAMERA_DIRTY | CAMERA_NOTREADY} // Camera state
	//enum domain CruiseControlMode = {CCM0 | CCM1 | CCM2}
	enum domain PitmanArmForthBack = {BACKWARD | FORWARD | NEUTRAL_FB} // Pitman arm positions - horizontal position
   
	domain HighBeamMotor  subsetof Integer // High beam illumination distance
	//0=65,1=100,2 = 120, 3 = 140, 4 = 160, 5= 180, 6 = 200, 7=220, etc. See table at page 23.
	

	// FUNCTIONS
	
	
	monitored cameraState: CameraState // Camera state: ready, dirty or not ready
	monitored oncomingTraffic: Boolean // Camera signal to detect oncoming vehicles. True -> vehicles oncoming, False -> no vehicles
	monitored pitmanArmForthBack: PitmanArmForthBack // Position of the pitman arm - horizontal position
	//monitored cruiseControlMode: CruiseControlMode // State of cruise control
	controlled highBeamOn: Boolean // High beam headlights (left and right) are on (True) or not (False)
	controlled highBeamRange: HighBeamRange // High beam luminous strenght
	controlled highBeamMotor: HighBeamMotor // Control the high beam illumination - 20m step size
	//controlled lightRotarySwitchPrevious: LightSwitch // Position of the light rotary switch in the previous state
	controlled pitmanArmForthBackPrevious: PitmanArmForthBack // Position of the pitman arm - horizontal position - in the previous state
	//controlled setVehicleSpeed: CurrentSpeed // Desired speed in case an adaptive cruise control is part of the vehicle
	
	derived adaptiveHighBeamActivated: Boolean
	derived adaptiveHighBeamDeactivated: Boolean
	derived headlampFlasherActivated: Boolean //Temporary activation of the high beam (without engaging, so-called flasher)
	derived headlampFlasherDeactivated: Boolean
	derived headlampFixedActivated: Boolean //Fixed activation of the high beam
	derived headlampFixedDeactivated: Boolean
	derived drivesFasterThan: Prod(CurrentSpeed,Integer) -> Boolean
	derived lightIlluminationDistance: CurrentSpeed -> HighBeamMotor
	derived luminousStrength: CurrentSpeed -> HighBeamRange
	derived luminousStrengthDecrease: CurrentSpeed -> HighBeamRange
	derived calculateSpeed: CurrentSpeed
	
	static percentageHBM: Integer -> HighBeamMotor
	
definitions:
	// DOMAIN DEFINITIONS
	domain HighBeamMotor = {0 :14} 
	
	// FUNCTION DEFINITIONS
	
	//Formulas for graphs in Figure 7 and 8 (as "reverse engineered") 
	//Figure 7
	//f(x) = x^2.2 * 0.0025 + 95     if x <= 171
	//f(x) = 300     if x > 171
	//Figure 8
	//f(x) = (7*x+60)/9

	function percentageHBM($x in Integer) =
	    if $x <= 65 then 0
	    else if $x <= 100 then 1
	    else if $x <= 120 then 2
	    else if $x <= 140 then 3
	    else if $x <= 160 then 4
	    else if $x <= 180 then 5
	    else if $x <= 200 then 6
	    else if $x <= 220 then 7
	    else if $x <= 240 then 8
	    else if $x <= 260 then 9
	    else if $x <= 280 then 10
	    else if $x <= 300 then 11
	    else if $x <= 320 then 12
	    else if $x <= 340 then 13
	    else if $x <= 360 then 14
	    else 14
	    endif endif endif endif endif endif endif endif endif endif endif endif endif endif endif
	
	//ELS-36    
	//Formulas for graphs in Figure 7 and 8 (as "reverse engineered") 
	function lightIlluminationDistance($y in CurrentSpeed) =
		let ($x = $y/10) in
		//let ($x = $y * .1) in
		//let ($x = 5.0)
		//let ($x = $y) in
			if ($x <= 171.0 ) then percentageHBM(rtoi($x * $x * 2.0 * 0.00025 + 95.0))
			else 11 //300 m
			endif
		endlet
	 
	function luminousStrength($x in CurrentSpeed) = 
	if $x <= 1200 then rtoi((7*$x/10 + 60.0)/9.0)
	else 100
	endif
	
	function luminousStrengthDecrease($x in CurrentSpeed) = 
	if $x <= 1200 then rtoi(((7*$x/10 + 60.0)/9.0)*0.7)
	else 100
	endif
	
	//ELS-37 If an adaptive cruise control is part of the vehicle, the light illumination distance is not calculated upon the actual 
   //vehicle speed but the target speed provided by the advanced cruise control.
	function calculateSpeed =
	  if (cruiseControlMode=CCM2) then setVehicleSpeed 
	  else  currentSpeed
	  endif
	
	function drivesFasterThan($speed in CurrentSpeed, $x in Integer) = 
	     $speed >= $x //faster than $x km/h        
	
	//ELS32 If the light rotary switch is in position Auto, the adaptive high beam is activated by moving the pitman arm to the back
	function adaptiveHighBeamActivated =
		(lightRotarySwitch = AUTO and  engineOn(keyState) and pitmanArmForthBack = BACKWARD)
	
	//ELS-38 If the pitman arm is moved again in the horizontal neutral position, the adaptive high beam headlight is deactivated.	
	function adaptiveHighBeamDeactivated =
		(lightRotarySwitch = AUTO and  pitmanArmForthBack = NEUTRAL_FB and pitmanArmForthBackPrevious = BACKWARD)
	
	function headlampFlasherActivated = 
	 	(pitmanArmForthBack = FORWARD and pitmanArmForthBackPrevious = NEUTRAL_FB)
	 	
	function headlampFlasherDeactivated = 
	 	(pitmanArmForthBack = NEUTRAL_FB and pitmanArmForthBackPrevious = FORWARD)
	
	function headlampFixedActivated = 
		(pitmanArmForthBack = BACKWARD and lightRotarySwitch = ON and (keyState = KEYINSERTED or engineOn(keyState)))
		
	function headlampFixedDeactivated =
		//( (pitmanArmForthBack = NEUTRAL_FB and pitmanArmForthBackPrevious = BACKWARD and lightRotarySwitch = ON) or lightRotarySwitch = OFF or keyState = NOKEYINSERTED)
    ( (pitmanArmForthBack = NEUTRAL_FB and pitmanArmForthBackPrevious = BACKWARD and lightRotarySwitch = ON) or 
    ((lightRotarySwitch = OFF or keyState = NOKEYINSERTED) and not headlampFlasherActivated))


	macro rule r_set_high_beam_headlights($v in Boolean, $d in HighBeamMotor, $l in HighBeamRange) =
		par
			highBeamOn := $v
			highBeamMotor := $d 
			highBeamRange := $l 
		endpar
		
	macro rule r_Manual_high_beam_headlights = 
		par
			//ELS-30
			if headlampFlasherActivated then
				r_set_high_beam_headlights[true,14,100] //max illumination area 360m, 100% luminous strenght (percentage)
			endif
			//ELS-30-31
			if headlampFlasherDeactivated or headlampFixedDeactivated then
			    highBeamOn := false
			endif
			//ELS-31
			if headlampFixedActivated then
				r_set_high_beam_headlights[true,7,100] //illumination area of 220m, 100% luminous strenght (percentage)
			endif
	    endpar
		
	//ELS-33 @E_MAPE_HBH
	//Sets the values, as calculated by the planner, for the lighting high beam actuators: highBeamOn to activate and deactivate the high beam, 
	//highBeamRange to control the high beam luminous, and highBeamMotor to control the high beam illumination distance.  
	macro rule r_Execute_HBH ($setHighBeam in Boolean,  $setHighBeamMotor in HighBeamMotor, $setHighBeamRange in HighBeamRange) =
	   	r_set_high_beam_headlights[$setHighBeam,$setHighBeamMotor,$setHighBeamRange]
					
	//ELS-33 @P_MAPE_HBH
	//Plans street illumination according to the characteristic curves for light illumination distance and for luminous strength 
	//depending on the vehicle speed
	macro rule r_IncreasingPlan_HBH =
	  let ($d = lightIlluminationDistance(calculateSpeed), $l = luminousStrength(calculateSpeed)) in
	  	  r_Execute_HBH[true,$d,$l]
	  endlet
	    
	//ELS-34 @P_MAPE_HBH
	//Reduce street illumination by reducing the area of illumination to 65 meters by an adjustment of the headlight position 
	//as well as by reduction of the luminous strength to 30%. 
	//depending on the vehicle speed
	macro rule r_DecreasingPlan_HBH =
	  let ($l = luminousStrengthDecrease(calculateSpeed)) in
	   	  r_Execute_HBH[true,0,$l]
	endlet
  
	    
	//ELS-33-34-35 @MA_MAPE_HBH
	//All MAPE computations of the MAPE loop are executed within one single ASM-step machine.
	//Note that we do not model the time constraints ('within 2 seconds', 'within 0.5 seconds')
	macro rule r_Monitor_Analyze_HBH =
	 if adaptiveHighBeamActivated then
	 	par	
	 	 if drivesFasterThan(currentSpeed,300) and not oncomingTraffic then //ELS-33 ELS-35 (checks if adaptation is necessary)
	 		//the street should be illuminated accordingly
	 		r_IncreasingPlan_HBH[]
	 	 endif
	 	 if oncomingTraffic then //ELS-34 (checks if adaptation is necessary) 
	 		//an activated high beam headlight is reduced to low beam headlight.
	 		r_DecreasingPlan_HBH[]
	 	 endif
	 	endpar
	 endif
	
	macro rule r_MAPE_HBH = //MAPE loop may start and stop
		par 
		    r_Monitor_Analyze_HBH[] 
			if adaptiveHighBeamDeactivated then highBeamOn := false	endif //ELS-38 If the pitman arm is moved again in the horizontal neutral position, 														  //the adaptive high beam headlight is deactivated.
		endpar

	macro rule r_HighBeam =
		par 
			pitmanArmForthBackPrevious := pitmanArmForthBack 
			r_Manual_high_beam_headlights[] 
			r_MAPE_HBH[] 
		endpar

	
