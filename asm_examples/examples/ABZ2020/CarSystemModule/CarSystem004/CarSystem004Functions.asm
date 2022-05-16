//Ground model of Adaptive Exterior Light and Speed Control System
//Direction Blinking
//Hazard warning light
//from ELS-1 to ELS-13

module CarSystem004Functions
import ../../StandardLibrary
import CarSystem004Domains
import ../CarSystem002/CarSystem002Functions
export *

signature:

	// FUNCTIONS
	monitored currentVoltage: Voltage // Current voltage
	derived subVoltage: Boolean //Depending on currentVoltage returns true if subvoltage is present or false otherwise
	derived overVoltage: Boolean //Depending on currentVoltage returns true if overvoltage is present or false otherwise
	derived overVoltageMaxValueLight: LightPercentage //Maximum light intensity in case of Overvoltage in percentage
	derived overVoltageMaxValueHighBeam: HighBeamRange //Maximum light intensity in case of Overvoltage in percentage
	derived setOverVoltageValueLight: Integer -> LightPercentage
	derived setOverVoltageValueHighBeam: HighBeamRange -> HighBeamRange
	
	//controlled setVehicleSpeed: CurrentSpeed // Desired speed in case an adaptive cruise control is part of the vehicle
	//monitored cruiseControlMode: CruiseControlMode // State of cruise control
definitions:
	
	
	// FUNCTION DEFINITIONS
	
	
	function subVoltage =
		(currentVoltage < 80)
	
	function overVoltage =
		(currentVoltage > 140)

	function overVoltageMaxValueLight = 
		rtoi((1000-(currentVoltage-145)*20)/10)
	
	//ELS-47	
	function setOverVoltageValueLight ($value in Integer) =  //$value in LightPercentage
		if (overVoltage and $value > overVoltageMaxValueLight) then
			overVoltageMaxValueLight
		else
			$value 
		endif
		
	//ELS-47	
	function setOverVoltageValueHighBeam ($value in HighBeamRange) = 
		if (overVoltage and $value > overVoltageMaxValueHighBeam) then
			overVoltageMaxValueHighBeam
		else
			$value 
		endif
	
	function overVoltageMaxValueHighBeam =
		rtoi((1000-(currentVoltage-145)*20)/10)
