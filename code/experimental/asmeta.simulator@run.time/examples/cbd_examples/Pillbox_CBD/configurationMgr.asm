asm configurationMgr

import ../StandardLibrary
import knowledge
export *

signature:
	//*************************************************
	// DOMAINS
	//*************************************************
	enum domain States = {INIT | NORMAL}
	
	
	//*************************************************
	// FUNCTIONS
	//*************************************************
	
	controlled state: States
	controlled prevRedLed: Compartment -> LedLights
	controlled amount: String -> Natural
	controlled minTimeToIntake: String -> Natural 
	controlled minToInterferer: Prod(String,String) -> Natural
	controlled time: String -> Seq(Natural)
	controlled medicine_list: Seq(String)
	controlled deltaDelay: String -> Natural
	controlled nextDrugIndexN: Compartment ->Natural
	
	derived ledStatusUpdateOk: Compartment -> Boolean

	//IN from Pillbox
	monitored time_consumption: Compartment -> Seq(Natural)
	monitored name: Compartment -> String
	monitored drugIndex: Compartment -> Natural
	monitored redLed: Compartment -> LedLights
	
	//*************************************************
	// STATIC VARIABLES
	//*************************************************
	static compartment1: Compartment
	static compartment2: Compartment	
	
definitions:
	//*************************************************
	// FUNCTIONS DEFINITIONS
	//*************************************************
	
	//***************Derived function declared in PrescriptionParameters_Knowledge module
	function minTimeToIntake($medicine in Medicine) = minTimeToIntake(id($medicine))
    function minToInterferer($m in Medicine, $n in Medicine) = minToInterferer(id($m),id($n))
    function time($m in Medicine) = time(id($m))
    function deltaDelay($m in Medicine) = deltaDelay(id($m))
    function nextDrugIndex($compartment in Compartment) = 	let ( $i = drugIndex($compartment) + 1n ) in 
		if  $i < iton(length(time_consumption($compartment)))  then $i else 0n endif
		endlet 
    //***************Derived function declared in this module
	function ledStatusUpdateOk($compartment in Compartment) = ((prevRedLed($compartment) = OFF and redLed($compartment)= BLINKING) or
			    (prevRedLed($compartment) = BLINKING and redLed($compartment)= ON))
	
	function next($compartment in Compartment) =  
		{$c in Compartment | (at(time_consumption($c),drugIndex($c)) > at(time_consumption($compartment),drugIndex($compartment))) : $c}
		

    rule r_keepPrevLiths =
	    forall $compartment in Compartment do
	    par
	       //Keep previous led status
		   prevRedLed($compartment) := redLed($compartment)
		   nextDrugIndexN($compartment):=nextDrugIndex($compartment)
		endpar
	
	//*************************************************
	// RULE DEFINITIONS
	//*************************************************
	//Initial startup and knowledge creation
	rule r_INIT =
		par
			forall $s in asSet(medicine_list) do
				extend Medicine with $m do
						id($m) := $s
			state := NORMAL
		endpar
    			
    			
    rule r_NORMAL_FUNCT =   r_keepPrevLiths[] 
    		
    
          	 
	//*************************************************
	// INVARIANT DEFINITIONS
	//*************************************************
    //The only valid traces of a led light are the sequences: OFF, ON, BLINKING and BLINKING, OFF
    invariant inv_A_redLed over redLed: ( forall $c in Compartment with not ledStatusUpdateOk($c) ) 
    
    //Knowledge consistency:
    //Pills type consistency between the prescription and the compartments of the pillbox
    invariant inv_A_medicineList over name(Compartment):  ( forall $c in Compartment with ( contains(medicine_list,name($c))))
    invariant inv_A_compName over Compartment: (forall $m in asSet(medicine_list) with (exist $c in Compartment with name($c)=$m))

    
    
    //Pills amount consistency: the amount of pills to intake and the number of the corresponding compartment slots must be the same
   	invariant inv_A_CompSize over Compartment: ( forall $c in Compartment with iton(length(time_consumption($c))) = amount(name($c)) )
    
    
    //Pills time consistency: the time schedule of the compartment's slots must be the same of those scheduled for the medicine and there must be no interferences
     invariant inv_A_CompMed over Compartment, Medicine: state=INIT implies (forall $c in Compartment with ((
    	(drugIndex($c) < iton(length(time_consumption($c))) and
    	at(time_consumption($c),drugIndex($c)) = (at(time(name($c)),drugIndex($c)))
    	))))
   
    /*For a medication in a given compartment and slot that has been taken, the difference 
between its intake time and the intake time of the subsequent medications 
taken (in the next slot of the same compartment or in another compartment) must be 
greater than or equal to the medication's minToInterferer value with the subsequent medications */
   invariant inv_A_timeConsumption over Compartment: (forall $compartment in Compartment with 
	(forall $c in next($compartment) with 
    	((at(time_consumption($c),drugIndex($c))!= 0n and at(time_consumption($compartment),drugIndex($compartment))!= 0n ) implies
    	(  (iton((at(time_consumption($c),drugIndex($c)) - at(time_consumption($compartment),drugIndex($compartment)))) 
			>= (minToInterferer(name($compartment),name($c))) and $c!=$compartment) or (iton((at(time_consumption($c),nextDrugIndex($c)) - at(time_consumption($compartment),drugIndex($compartment)))) 
			>= (minToInterferer(name($compartment),name($c))) and $c=$compartment)
		))
    ))
	//*************************************************
	// MAIN Rule
	//*************************************************	
	main rule r_Main =  
	  //transition from SETUP to NORMAL mode
		if state = INIT then
			r_INIT[] //Medicine knowledge initialization depending on how the pillbox has been filled
		else
			r_NORMAL_FUNCT[]
		endif
	
	default init s0:	//This init state is correct, it does not generate any invariant violation
	//Controlled function that indicates the status of the system
	function state = INIT
	
		
	/* Medicine knowledge initialization from an external prescription (e.g., a JSON file)*/
	 function medicine_list = ["fosamax","moment"]
	 function amount($medicine in String) =
		switch($medicine)
			case "moment" : 2n
			case "fosamax" : 1n
		endswitch	
		
	function time($m in String) =
		switch($m)
			case "moment" : [730n, 1140n]
			case "fosamax" : [360n]
		endswitch
	
	function minTimeToIntake($medicine in String) =
		switch($medicine)
			case "moment" : 120n
			case "fosamax" : 360n
			otherwise 0n
		endswitch 
	
	function minToInterferer($m in String, $n in String) =
 	 if ($m = $n) then
 			minTimeToIntake($m)
	 else
		switch ($m,$n)//(($m,$n))
			case ("fosamax","moment") : 360n
			case ("moment","fosamax") : 360n
			otherwise 0n
		endswitch 	
	 endif
	
	function deltaDelay($medicine in String) =
		switch($medicine)
			case "moment" : 60n
			case "fosamax" : 60n
			otherwise 0n
		endswitch 
	

	
	