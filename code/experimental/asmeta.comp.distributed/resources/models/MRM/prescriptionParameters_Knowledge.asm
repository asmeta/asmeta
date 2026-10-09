module prescriptionParameters_Knowledge

import ../StandardLibrary
export *

signature:
	//*************************************************
	// DOMAINS
	//*************************************************
	dynamic abstract domain Medicine
	

	//*************************************************
	// FUNCTIONS
	//*************************************************
	controlled id: Medicine -> String
	derived minTimeToIntake: Medicine -> Natural //amount of time to wait at least for the next intake of the same medicine
	derived minToInterferer: Prod(Medicine,Medicine) -> Natural //indicates the minimum separation (in terms of minutes) from the medicine M to the interferer N
	derived interferesWith: Prod(Medicine,Medicine) -> Boolean //indicates whether medicine M interferes with medicine N or not
	derived time: Medicine ->  Seq(Natural) //time schedule to intake the pill 
	derived amount: Medicine -> Natural //number of pills per day
	derived deltaDelay: Medicine -> Natural //delta time added when pill is missed
	
	definitions:
	
	//*************************************************
	// FUNCTIONS DEFINITIONS
	//*************************************************
	
	//Take Fosamax as an example. The patient should not take anything within 30 minutes after taking the medicine. 
	//Thus, the minimum separation parameter of Fosamax to any interferer is 0.5 hour
    
	//function minFrInterferer($n in Medicine, $m in Medicine) =  minToInterferer($m,$n)
 	function interferesWith($n in Medicine, $m in Medicine)= (minToInterferer($m,$n) > 0n)
 	//function next($m in Medicine) = ( forall $x in Medicine with time($x) = )  
	//***************Derived function declared in PrescriptionParameters_Knowledge module

    