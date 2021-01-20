asm safePillbox
import ../StandardLibrary
import pillbox_sanitiser

signature:
	//*************************************************
	// DOMAINS
	//*************************************************
	
	//*************************************************
	// FUNCTIONS
	//*************************************************
	//IN from Pillbox
	monitored isPillMissed: Compartment -> Boolean //pill missed
	monitored pillTakenWithDelay: Compartment -> Boolean // is true if the patient takes the pill but it causes mintointerfer violation
	monitored actual_time_consumption: Compartment -> Seq(Natural) //Which time is the pill taken
	monitored day: Integer //Day number since first day of use
	
	//OUT to Pillbox
	out setNewTime: Prod(Compartment, Natural) -> Boolean //pill is missed and new time must be set = previous + delay,
	// if mintointerfer is violated this is false and the time is set to the original value
	out newTime: Prod(Compartment, Natural) -> Natural //new pill time
	out skipNextPill: Prod(Compartment,Compartment) -> Boolean //is next compartment skipped? It can be that more than one next compartments are skipped (current compartment, skip compartment)
	
	
	//Time management
	// The systemTime is expressed as the number of hours passed since the 01/01/1970
	monitored systemTime: Natural //Time in minutes since midnight
	
	//*************************************************
	// STATIC VARIABLES
	//*************************************************
	static compartment2: Compartment
	static compartment3: Compartment	
	static compartment4: Compartment
	
definitions:
	//*************************************************
	// FUNCTIONS DEFINITIONS
	//*************************************************
	
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
		
	rule r_enforce = 
	  forall $compartment in Compartment do 
	  par
	  	if isPillMissed($compartment) then //M
			//Missed pill check if the new time causes invariant violation
			 if ((forall $c in next($compartment) with 
			   	(  (iton((at(time_consumption($c),drugIndex($c)) - at(time_consumption($compartment),drugIndex($compartment))-ntoi(deltaDelay(name($compartment)))))
				>= (minToInterferer(name($compartment),name($c))) and $c!=$compartment) or (iton((at(time_consumption($c),nextDrugIndex($c)) - at(time_consumption($compartment),drugIndex($compartment))-ntoi(deltaDelay(name($compartment))))) 
				>= (minToInterferer(name($compartment),name($c))) and $c=$compartment)
				))) then //A
			  			par
			  				setNewTime ($compartment, drugIndex($compartment)):= true
			  				newTime ($compartment, drugIndex($compartment)):= at(time_consumption($compartment),drugIndex($compartment))+deltaDelay(name($compartment))
			  			endpar //if not update new time of pill otherwise skip the pill
			  else
			  	par
			  		setNewTime ($compartment, drugIndex($compartment)):= false
			  		newTime ($compartment, drugIndex($compartment)):= at(time(name($compartment)),drugIndex($compartment))
			  	endpar 
			 endif
		endif
	 	if pillTakenWithDelay($compartment) then
			//pill taken later compared the timecompartment
			//for all next pills that cause invariant violation because the current has been taken after set time -> skip pill
			forall $c2 in next($compartment) with 
			   	(  ((iton(at(time_consumption($c2),drugIndex($c2)) - (systemTime mod 1440n)) //it should be actual_time_consumption, but since it is updated in the next state I use systemTime here
				<= (minToInterferer(name($compartment),name($c2)))) and $c2!=$compartment) or ((iton(at(time_consumption($c2),nextDrugIndex($c2)) - (systemTime mod 1440n))
				<= (minToInterferer(name($compartment),name($c2)))) and $c2=$compartment)
				) do
					skipNextPill($compartment, $c2):= true
					//skip next pill -> rule must be in pillbox
		endif
		endpar
	
	//Reset all skipPill to false
	rule r_resetMidnight =
		if (rtoi(systemTime/1440n))> day then
			forall $c in Compartment do
				par
					setNewTime($c, drugIndex($c)) := false 
					forall $c2 in Compartment with $c!=$c2 do
						skipNextPill ($c, $c2) := false
				endpar
		endif
				
	rule r_NORMAL_FUNCT =  par r_keepPrevLiths[] r_enforce[] r_resetMidnight[] endpar //r_CompartmentMgmt[] 
    
  
		
	
    //*************************************************
	// MAIN Rule
	//*************************************************	
	main rule r_Main =  
	  //transition from INIT to NORMAL
		if state = INIT then
			r_INIT[] //Medicine knowledge initialization depending on how the pillbox has been filled
		else		
			r_NORMAL_FUNCT[]
		endif
	  


		
init s0:	//This init state is correct, it does not generate any invariant violation
	//Controlled function that indicates the status of the system
	function state = INIT
	
		
	/* Medicine knowledge initialization from an external prescription (e.g., a JSON file)*/
	 function medicine_list = ["aspirine","moment","fosamax"]
	 function amount($medicine in String) =
		switch($medicine)
			case "aspirine" : 1n
			case "moment" : 2n
			case "fosamax" : 1n
		endswitch	
		
	function time($m in String) =
		switch($m)
			case "aspirine" : [960n]
			case "moment" : [780n, 1140n]
			case "fosamax" : [410n]//[180n] modificato per far si che si riprogrammi una volta sola e poi non può più essere riprogrammato
		endswitch
	
	function minTimeToIntake($medicine in String) =
		switch($medicine)
			case "aspirine" : 360n
			case "moment" : 120n
			case "fosamax" : 360n
			otherwise 0n
		endswitch 
	
	function minToInterferer($m in String, $n in String) =
 	 if ($m = $n) then
 			minTimeToIntake($m)
	 else
		switch ($m,$n)//(($m,$n))
			case ("moment","aspirine") : 60n
			case ("aspirine","moment") : 60n
			case ("fosamax","aspirine") : 360n
			case ("fosamax","moment") : 360n
			case ("aspirine","fosamax") : 360n
			case ("moment","fosamax") : 360n
			otherwise 0n
		endswitch 	
	 endif
	
	function deltaDelay($medicine in String) =
		switch($medicine)
			case "aspirine" : 60n
			case "moment" : 60n
			case "fosamax" : 60n
			otherwise 0n
		endswitch 
		
	function setNewTime($c in Compartment, $index in Natural) = false
	function newTime($c in Compartment, $index in Natural) = 0n
	function skipNextPill($c in Compartment, $c2 in Compartment) = false
	
	//Initialization of day
	function day = 0	
	// Initialization of the SystemTime
	function systemTime = 0n

	// Turn-off all the led of the Compartments
	function redLed($compartment in Compartment) = OFF
	// Initialization of the time consumption for a compartment
	function time_consumption($compartment in Compartment) = //time(name($compartment))
		switch($compartment)
			case compartment2 : [960n]
			case compartment3 : [780n, 1140n]
			case compartment4 : [410n]
		endswitch 
	// Insert a drug in each compartment	
	function name($compartment in Compartment) = //id(medicineIn($compartment))
		switch($compartment)
			case compartment2 : "aspirine"
			case compartment3 : "moment"
			case compartment4 : "fosamax"
		endswitch
	// Every compartment has an index starting from 0
	function drugIndex($compartment in Compartment) = 0n

	// Initialization of the actual time consumption for a compartment
	function actual_time_consumption($compartment in Compartment) = //time(name($compartment))
		switch($compartment)
			case compartment2 : [0n]
			case compartment3 : [0n, 0n]
			case compartment4 : [0n]
		endswitch 	

init s1:	//This init state is correct, it does not generate any invariant violation
	//Controlled function that indicates the status of the system
	function state = INIT
	
		
	/* Medicine knowledge initialization from an external prescription (e.g., a JSON file)*/
	 function medicine_list = ["aspirine","moment","fosamax"]
	 function amount($medicine in String) =
		switch($medicine)
			case "aspirine" : 1n
			case "moment" : 2n
			case "fosamax" : 1n
		endswitch	
		
	function time($m in String) =
		switch($m)
			case "aspirine" : [960n]
			case "moment" : [780n, 1140n]
			case "fosamax" : [350n]
		endswitch
	
	function minTimeToIntake($medicine in String) =
		switch($medicine)
			case "aspirine" : 360n
			case "moment" : 120n
			case "fosamax" : 360n
			otherwise 0n
		endswitch 
	
	function minToInterferer($m in String, $n in String) =
 	 if ($m = $n) then
 			minTimeToIntake($m)
	 else
		switch ($m,$n)//(($m,$n))
			case ("moment","aspirine") : 60n
			case ("aspirine","moment") : 60n
			case ("fosamax","aspirine") : 360n
			case ("fosamax","moment") : 360n
			case ("aspirine","fosamax") : 360n
			case ("moment","fosamax") : 360n
			otherwise 0n
		endswitch 	
	 endif
	
	function deltaDelay($medicine in String) =
		switch($medicine)
			case "aspirine" : 60n
			case "moment" : 60n
			case "fosamax" : 60n
			otherwise 0n
		endswitch 
		
	function setNewTime($c in Compartment, $index in Natural) = false
	function newTime($c in Compartment, $index in Natural) = 0n
	function skipNextPill($c in Compartment, $c2 in Compartment) = false
	
	//Initialization of day
	function day = 0	
	// Initialization of the SystemTime
	function systemTime = 0n

	// Turn-off all the led of the Compartments
	function redLed($compartment in Compartment) = OFF
	// Initialization of the time consumption for a compartment
	function time_consumption($compartment in Compartment) = //time(name($compartment))
		switch($compartment)
			case compartment2 : [960n]
			case compartment3 : [780n, 1140n]
			case compartment4 : [350n]
		endswitch 
	// Insert a drug in each compartment	
	function name($compartment in Compartment) = //id(medicineIn($compartment))
		switch($compartment)
			case compartment2 : "aspirine"
			case compartment3 : "moment"
			case compartment4 : "fosamax"
		endswitch
	// Every compartment has an index starting from 0
	function drugIndex($compartment in Compartment) = 0n

	// Initialization of the actual time consumption for a compartment
	function actual_time_consumption($compartment in Compartment) = //time(name($compartment))
		switch($compartment)
			case compartment2 : [0n]
			case compartment3 : [0n, 0n]
			case compartment4 : [0n]
		endswitch 	
				
default init s2:	//This init state is correct, it does not generate any invariant violation
	//Controlled function that indicates the status of the system
	function state = INIT
	
		
	/* Medicine knowledge initialization from an external prescription (e.g., a JSON file)*/
	 function medicine_list = ["aspirine","moment","fosamax"]
	 function amount($medicine in String) =
		switch($medicine)
			case "aspirine" : 1n
			case "moment" : 2n
			case "fosamax" : 1n
		endswitch	
		
	function time($m in String) =
		switch($m)
			case "aspirine" : [960n]
			case "moment" : [780n, 1140n]
			case "fosamax" : [410n]
		endswitch
	
	function minTimeToIntake($medicine in String) =
		switch($medicine)
			case "aspirine" : 360n
			case "moment" : 120n
			case "fosamax" : 360n
			otherwise 0n
		endswitch 
	
	function minToInterferer($m in String, $n in String) =
 	 if ($m = $n) then
 			minTimeToIntake($m)
	 else
		switch ($m,$n)//(($m,$n))
			case ("moment","aspirine") : 60n
			case ("aspirine","moment") : 60n
			case ("fosamax","aspirine") : 360n
			case ("fosamax","moment") : 360n
			case ("aspirine","fosamax") : 360n
			case ("moment","fosamax") : 360n
			otherwise 0n
		endswitch 	
	 endif
	
	function deltaDelay($medicine in String) =
		switch($medicine)
			case "aspirine" : 60n
			case "moment" : 60n
			case "fosamax" : 60n
			otherwise 0n
		endswitch 
		
	function setNewTime($c in Compartment, $index in Natural) = false
	function newTime($c in Compartment, $index in Natural) = 0n
	function skipNextPill($c in Compartment, $c2 in Compartment) = false
	
	//Initialization of day
	function day = 0	
	// Initialization of the SystemTime
	function systemTime = 0n

	// Turn-off all the led of the Compartments
	function redLed($compartment in Compartment) = OFF
	// Initialization of the time consumption for a compartment
	function time_consumption($compartment in Compartment) = //time(name($compartment))
		switch($compartment)
			case compartment2 : [960n]
			case compartment3 : [780n, 1140n]
			case compartment4 : [410n]
		endswitch 
	// Insert a drug in each compartment	
	function name($compartment in Compartment) = //id(medicineIn($compartment))
		switch($compartment)
			case compartment2 : "aspirine"
			case compartment3 : "moment"
			case compartment4 : "fosamax"
		endswitch
	// Every compartment has an index starting from 0
	function drugIndex($compartment in Compartment) = 0n

	// Initialization of the actual time consumption for a compartment
	function actual_time_consumption($compartment in Compartment) = //time(name($compartment))
		switch($compartment)
			case compartment2 : [0n]
			case compartment3 : [0n, 0n]
			case compartment4 : [0n]
		endswitch 			
		