module CarSystem000CommonFunctions
import ../../StandardLibrary
import CarSystem000CommonDomains
export *

signature:
	//FUNCTIONS
	monitored currentSpeed: CurrentSpeed // Current speed of the vehicle
	monitored keyState: KeyPosition // Position of the key
	controlled keyState_Previous: KeyPosition // Position of the key in the previous state
	controlled setVehicleSpeed: CurrentSpeed // Cruise control speed
	monitored cruiseControlMode: CruiseControlMode

	static engineOn: KeyPosition -> Boolean

definitions:

	function engineOn ($key in KeyPosition)=
		($key = KEYINIGNITIONONPOSITION)
