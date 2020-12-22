//Seventh refinement of Adaptive Exterior Light and Speed Control System
//Adaptive cruise control and distance warning
//from SCS-18 to SCS-26

module CarSystem007AdaptiveCruiseC
import ../../StandardLibrary
import ../CarSystem006/CarSystem006Functions
export *

signature:
	// DOMAINS
	enum domain RangeRadarState = {READY | DIRTY | NOTREADY}

	domain RangeRadarSensor subsetof Integer
	domain SafetyDistance subsetof Real // Safety distance
	domain BrakePressure subsetof Integer
	domain TimeImpactBrake subsetof Integer
	
	enum domain Aceleration = {ACCP5 | DECM2 | NOACC}
	
	// FUNCTIONS


	derived adaptiveCruiseControlActivated: Boolean //Depending whether cruiseControlMode = CCM2 or not
	//derived adaptiveCruiseControlDeactivated: Boolean
	controlled acousticWarningOn: Boolean //Acoustic warning command True, False
	controlled visualWarningOn: Boolean
	monitored rangeRadarState: RangeRadarState
	monitored rangeRadarSensor: RangeRadarSensor
	derived obstacleAhead: Boolean 
	monitored safetyDistance: Real //secondi
	monitored speedVehicleAhead: CurrentSpeed //Speed of preceding vehicle in unity
	controlled speedVehicleAhead_Prec: CurrentSpeed //Speed of precedeing vehicle in state n-1
	controlled setSafetyDistance: Integer //metri
	derived bothVehicleStanding: Boolean
	controlled brakePressure: BrakePressure // Brake pressure
	controlled acceleration: Integer //m/s^2
	controlled acousticCollisionSignals: Boolean // acoustic signal SCS-21
	derived brakingDistance: Integer //in metri
	monitored setTargetFromAccDec: CurrentSpeed //new targhet when obstacle ahead
	derived manualSpeed: Boolean // True if user changes desired speed manually	
	
	
definitions:	

	domain RangeRadarSensor = {0 : 200} //0 = no dectected obstacle in the travel corridor
									//1-200 = distance in meters of obstacle detected in the travel corridor

	// FUNCTION DEFINITIONS
	
	function adaptiveCruiseControlActivated = 
		(cruiseControlMode = CCM2)
		
	//function adaptiveCruiseControlDeactivated = 
	//	(sCSLever = BACKWARD_SCS or not cruiseControlMode = CCM2)
	
	function obstacleAhead = 
		(rangeRadarState = READY and rangeRadarSensor != 0)
		
	function bothVehicleStanding = 
		(currentSpeed = 0 and speedVehicleAhead = 0)

	function brakingDistance = 
	 	rtoi((currentSpeed/10)/3.6 * (currentSpeed/10)/3.6 * (1/6))  //SCS-21
//====================================
//It is true if user changes desired speed manually	
	function manualSpeed = 
		(sCSLever = UPWARD5_SCS or sCSLever = UPWARD7_SCS or sCSLever = DOWNWARD5_SCS or sCSLever = DOWNWARD7_SCS)
	
	
	// RULE DEFINITIONS
	

	//SCS-23 
	//@PE_MAPE_CC
	macro rule r_CalculateSafetyDistancePlan_CC = 
			if (currentSpeed<200) then
				par
					speedVehicleAhead_Prec := speedVehicleAhead
					if (speedVehicleAhead > speedVehicleAhead_Prec and currentSpeed != 0) then
							setSafetyDistance := rtoi(3.0 * (currentSpeed/36))
					else if (speedVehicleAhead < 200 and speedVehicleAhead > 0) then
						setSafetyDistance := rtoi(2.5 * (currentSpeed/36))  	// div 10 -> from unity to km/h, div3.6 -> from km/h to m/s
						else if (bothVehicleStanding) then
							setSafetyDistance := 2
							endif
						endif
					endif
				endpar
			endif
		
	//SCS-24
	//@PE_MAPE_CC
	macro rule r_SafetyDistanceByUser =
		if not adaptiveCruiseControlActivated then
			if (currentSpeed > 200) then
				if (sCSLever = HEAD) then
					setSafetyDistance := rtoi((currentSpeed/10)/3.6*(safetyDistance))
				endif
			endif
		endif
	
	//SCS-21
	//@PE_MAPE_CC
	macro rule r_CollisionDetection =
		 par
			 if (rangeRadarSensor < brakingDistance)  then //SCS-21 checks if adaptation is necessary
		 	 	acousticCollisionSignals := true
		 	 endif
		 	 if (rangeRadarSensor > brakingDistance and acousticCollisionSignals = true)  then //SCS-21 checks if adaptation is necessary
		 	 	acousticCollisionSignals := false
		 	 endif
	 	 endpar
	
	//@P_MAPE_CC
	macro rule r_AcceleratePlan_CC =
	   	if (currentSpeed < setVehicleSpeed) then
	   	par
	   	  	acceleration := 2
	   	  	//Assumption: The scs lever has the priority when modifing setvehiclespeed
	   	  		if (not manualSpeed) then
		   	  		if (desiredSpeed > setVehicleSpeed) then
		   	  			if (setTargetFromAccDec> speedVehicleAhead) then
		   	  				setVehicleSpeed := speedVehicleAhead
		   	  			else
		   	  				setVehicleSpeed := setTargetFromAccDec
		   	  			endif
		   	  		endif
		   	  	endif
	   	  	endpar
	   	  else
	   	  	acceleration := 0
	   	  endif  
	
	//@P_MAPE_CC
	macro rule r_DeceleratePlan_CC =
	   	  if (currentSpeed != 0) then
	   	  	par
	   	  		acceleration := -5
	   	  		if (not manualSpeed) then
	   	  			setVehicleSpeed := setTargetFromAccDec
	   	  		endif
	   	  	endpar
	   	  else
	   	  	acceleration := 0
	   	  endif
	   	  
	//@PE_MAPE_CC
	macro rule r_WarningPlan_CC =
	  par
	  	if (itor(rangeRadarSensor) < ((currentSpeed/10)/3.6)*1.5) then 
	  		visualWarningOn := true 
	  	else if (visualWarningOn) then
		  		visualWarningOn := false
		  	endif
	  	endif
	  	if (itor(rangeRadarSensor) < ((currentSpeed/10)/3.6)*0.8) then  
	  		acousticWarningOn:= true 
	  	else if (acousticWarningOn) then
		  		acousticWarningOn := false
		  	endif
	  	endif 	  
	  endpar
	   	  
	//@MA_MAPE_CC
	//All MAPE computations of the MAPE loop are executed within one single ASM-step machine.
	//Assumption: r_AcceleratePlan_CC[] when distance nearest obstacle(rangeRadarSensor) > Safety distance (setSafetyDistance)
	//viceversa for r_DeceleratePlan_CC[] 
	macro rule r_Monitor_Analyze_CC =
	 if adaptiveCruiseControlActivated then
	   par
	   //if (obstacleAhead and rangeRadarSensor<setSafetyDistance) then //before
	 	 if (obstacleAhead and rangeRadarSensor>setSafetyDistance) then //SCS-22 checks if adaptation is necessary
	 		r_AcceleratePlan_CC[] 
	 	 endif
	 //if (obstacleAhead and rangeRadarSensor>setSafetyDistance) then //before
	 	 if (obstacleAhead and rangeRadarSensor<setSafetyDistance) then //SCS-20 checks if adaptation is necessary
	 		r_DeceleratePlan_CC[] 
	 	 endif
	 	 r_CollisionDetection[] 
	 	 if obstacleAhead then //SCS-25, SCS-26 distance warning (if necessary)
	 	 	r_WarningPlan_CC[] 
	 	 endif
	 	 r_CalculateSafetyDistancePlan_CC[] //SCS-23
	   endpar
	 endif 
		
	
	macro rule r_MAPE_CC = //MAPE loop may start and stop
	//par 
	    r_Monitor_Analyze_CC[] 
		//if adaptiveCruiseControlDeactivated then //SCS-12
		//	r_SetModifySpeed[setVehicleSpeed] endif 
	//endpar
			
	