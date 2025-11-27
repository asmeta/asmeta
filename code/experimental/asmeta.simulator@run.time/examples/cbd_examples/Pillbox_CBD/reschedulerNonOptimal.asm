asm reschedulerNonOptimal
import  ../StandardLibrary
import knowledge
signature:
	//*************************************************
	// DOMAINS
	//*************************************************
	enum domain States = {INIT | NORMAL}
	
	//*************************************************
	// FUNCTIONS
	//*************************************************
	
	
	//IN from Pillbox
	monitored isPillMissed: Compartment -> Boolean //pill missed
	monitored pillTakenWithDelay: Compartment -> Boolean // is true if the patient takes the pill but it causes mintointerfer violation
	monitored actual_time_consumption: Compartment -> Seq(Natural) //Which time is the pill taken
	monitored day: Integer //Day number since first day of use
	monitored time_consumption: Compartment -> Seq(Natural)
	monitored drugIndex: Compartment -> Natural
	monitored name: Compartment -> String
	monitored redLed: Compartment -> LedLights 
	

	//OUT to Pillbox
	out setNewTime: Compartment -> Boolean //if true set new time when pill is missed 
	out setOriginalTime: Compartment -> Boolean //pill is missed and new time must be set = previous + delay,
	// if mintointerfer is violated this is false and the time is set to the original value
	out newTime: Compartment -> Natural //new pill time
	out skipNextPill: Prod(Compartment,Compartment) -> Boolean //is next compartment skipped? It can be that more than one next compartments are skipped (current compartment, skip compartment)
	out skipNextPill: Compartment -> Boolean //SafePillbox detected that pill taken with delay
	
	
	controlled state: States
	controlled amount: String -> Natural
	controlled minTimeToIntake: String -> Natural 
	controlled minToInterferer: Prod(String,String) -> Natural
	controlled time: String -> Seq(Natural)
	controlled medicine_list: Seq(String)
	controlled deltaDelay: String -> Natural
	controlled nextDrugIndexN: Compartment ->Natural
	
	//*************************************************
	// STATIC VARIABLES
	//*************************************************
	static compartment1: Compartment
	static compartment2: Compartment	
	
	//time
	monitored pillboxSystemTime: Natural

	controlled tempComputation: Boolean
	derived computeDer: Boolean
	
definitions:
	//*************************************************
	// FUNCTIONS DEFINITIONS
	//*************************************************
	
		function computeDer = (forall $compartment in Compartment with
 	((forall $c in next($compartment) with 
		( isPillMissed($compartment) and ((iton((at(time_consumption($c),drugIndex($c)) - 
			at(time_consumption($compartment),drugIndex($compartment))-ntoi(deltaDelay(name($compartment)))
		))
		>= (minToInterferer(name($compartment),name($c))) and $c!=$compartment) or 
		(iton((at(time_consumption($c),nextDrugIndex($c)) - at(time_consumption($compartment),drugIndex($compartment))-
			ntoi(deltaDelay(name($compartment)))
		)) 
		>= (minToInterferer(name($compartment),name($c))) and $c=$compartment))
		))
	 implies ((newTime ($compartment) = at(time_consumption($compartment),drugIndex($compartment))+deltaDelay(name($compartment))))))
	
	function next($compartment in Compartment) =  
		{$c in Compartment | (at(time_consumption($c),drugIndex($c)) > at(time_consumption($compartment),drugIndex($compartment))) : $c}
	
    //***************Derived function declared in PrescriptionParameters_Knowledge module
	function minTimeToIntake($medicine in Medicine) = minTimeToIntake(id($medicine))
    function minToInterferer($m in Medicine, $n in Medicine) = minToInterferer(id($m),id($n))
    function time($m in Medicine) = time(id($m))
    function deltaDelay($m in Medicine) = deltaDelay(id($m))
    function nextDrugIndex($compartment in Compartment) = 	let ( $i = drugIndex($compartment) + 1n ) in 
		if  $i < iton(length(time_consumption($compartment)))  then $i else 0n endif
		endlet 
	
  
    //*************************************************
	// RULE DEFINITIONS
	//*************************************************

	rule r_rescheduler = 
	  forall $compartment in Compartment do 
	  par
	  	if isPillMissed($compartment) then //M
		  	par
		  		setNewTime ($compartment):= true
				//Missed pill update new consumption time without checking overlap with next pills
				//This causes an invariant violation in configMgr when the consumption time
				//is updated and the mintoInterferer is not satisfied
				setOriginalTime ($compartment):= false
				newTime ($compartment):= at(time_consumption($compartment),drugIndex($compartment))+deltaDelay(name($compartment))
			endpar
		else
			setNewTime ($compartment):= false	
		endif
	 	if pillTakenWithDelay($compartment) then
			//pill taken later compared the timecompartment
			//for all next pills that cause invariant violation because the current has been 
			//taken after set time -> skip pill
			forall $c2 in next($compartment) do 
			   if	(((iton(at(time_consumption($c2),drugIndex($c2)) - (pillboxSystemTime mod 1440n)) //it should be actual_time_consumption, but since it is updated in the next state I use pillboxSystemTime here
				<= (minToInterferer(name($compartment),name($c2)))) and $c2!=$compartment) or 
				((iton(at(time_consumption($c2),nextDrugIndex($c2)) - (pillboxSystemTime mod 1440n))
				<= (minToInterferer(name($compartment),name($c2)))) and $c2=$compartment)
				) then
					par
						skipNextPill($compartment, $c2):= true
						skipNextPill($compartment) := true
					endpar
				// skip next pill -> rule must be in pillbox
				endif
		else
			par
				forall $c3 in next($compartment) do 
					skipNextPill($compartment, $c3):= false
				skipNextPill($compartment) := false
			endpar
		endif
		endpar
		
// Reset all skipPill to false
rule r_resetMidnight =
		if (rtoi(pillboxSystemTime/1440n))> day then
			forall $c in Compartment do
				par
					setNewTime($c) := false 
					forall $c2 in Compartment with $c!=$c2 do
						skipNextPill ($c, $c2) := false
				endpar
endif
	
	rule r_evaluate_output_pill=
		 forall $compartment in Compartment do 
			 forall $c2 in next($compartment) do 
				 if setNewTime($compartment) and skipNextPill($compartment) and skipNextPill($compartment, $c2) then
					skip
				 endif
	
				
	rule r_NORMAL_FUNCT =  //par r_keepPrevLiths[] r_enforce[] r_resetMidnight[] r_evaluate_output_pill[] endpar //r_CompartmentMgmt[] 
    		par  r_rescheduler[] r_resetMidnight[] r_evaluate_output_pill[] endpar

//If pill is missed and there is no overlap with next pills, delayed it
invariant inv_G_newTime over newTime: (forall $compartment in Compartment with
 	(
 		(forall $c in next($compartment) with 
 			(isPillMissed($compartment) and 
 				((iton((at(time_consumption($c),drugIndex($c)) - 
			at(time_consumption($compartment),drugIndex($compartment))-ntoi(deltaDelay(name($compartment)))		))
		>= (minToInterferer(name($compartment),name($c))) and $c!=$compartment) 
		or (iton((at(time_consumption($c),nextDrugIndex($c)) - at(time_consumption($compartment),drugIndex($compartment))-
			ntoi(deltaDelay(name($compartment))))) 
		>= (minToInterferer(name($compartment),name($c))) and $c=$compartment)))
	 implies ((newTime ($compartment) = at(time_consumption($compartment),drugIndex($compartment))+deltaDelay(name($compartment))))
	 )))


//If pill overlaps with next pills, skip the next pill that overlaps
invariant inv_G_skipNextPill over skipNextPill(Compartment): (forall $compartment in Compartment with
 	(
 		(forall $c in next($compartment) with 
		(pillTakenWithDelay($compartment) and ((iton(at(time_consumption($c),drugIndex($c)) - (pillboxSystemTime mod 1440n)) //it should be actual_time_consumption, but since it is updated in the next state I use pillboxSystemTime here
		<= (minToInterferer(name($compartment),name($c)))) and $c!=$compartment) or 
		((iton(at(time_consumption($c),nextDrugIndex($c)) - (pillboxSystemTime mod 1440n))
		<= (minToInterferer(name($compartment),name($c)))) and $c=$compartment)
		) 
	 implies (skipNextPill($compartment, $c)= true and
						skipNextPill($compartment)= true))))
 


/* For a medication in a given compartment and slot that has been taken, the difference 
between its ACTUAL intake time and the intake time of the subsequent medications 
taken (in the next slot of the same compartment or in another compartment) must be 
greater than or equal to the medication's minToInterferer value with the subsequent medications */
invariant inv_A_actual_time over Compartment: (forall $compartment in Compartment with 
	(forall $c in next($compartment) with 
    	((at(actual_time_consumption($c),drugIndex($c))!= 0n and at(actual_time_consumption($compartment),drugIndex($compartment))!= 0n ) implies
    	(  (iton((at(actual_time_consumption($c),drugIndex($c)) - at(actual_time_consumption($compartment),drugIndex($compartment)))) 
			>= (minToInterferer(name($compartment),name($c))) and $c!=$compartment) or (iton((at(actual_time_consumption($c),nextDrugIndex($c)) - at(actual_time_consumption($compartment),drugIndex($compartment)))) 
			>= (minToInterferer(name($compartment),name($c))) and $c=$compartment)
		))
    ))
	
	
	
    //*************************************************
	// MAIN Rule
	//*************************************************	
	main rule r_Main = 
		if state=INIT then
		par
			forall $c in Compartment do
			 	par
			 		skipNextPill($c):=false	 
			 		setNewTime($c):=false
			 	endpar
			state:=NORMAL
		endpar
		else
		par 
		 tempComputation:=computeDer
		 r_NORMAL_FUNCT[]
		endpar
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
		
	
		
	function setNewTime($c in Compartment) = false
	function newTime($c in Compartment) = 0n
	function skipNextPill($c in Compartment, $c2 in Compartment) = false
	function skipNextPill($c in Compartment) = false
	
	//Initialization of day
	function day = 0	
	// Initialization of the SystemTime
	function pillboxSystemTime = 0n

	// Turn-off all the led of the Compartments
	function redLed($compartment in Compartment) = OFF
	// Initialization of the time consumption for a compartment
	function time_consumption($compartment in Compartment) = //time(name($compartment))
		switch($compartment)
			case compartment1 : [360n]
			case compartment2 : [730n, 1140n]
		endswitch 
	// Insert a drug in each compartment	
	function name($compartment in Compartment) = //id(medicineIn($compartment))
		switch($compartment)
			case compartment1 : "fosamax"
			case compartment2 : "moment"
		endswitch
	// Every compartment has an index starting from 0
	function drugIndex($compartment in Compartment) = 0n

	// Initialization of the actual time consumption for a compartment
	function actual_time_consumption($compartment in Compartment) = //time(name($compartment))
		switch($compartment)
			case compartment1 : [0n]
			case compartment2 : [0n, 0n]
		endswitch 	
		
