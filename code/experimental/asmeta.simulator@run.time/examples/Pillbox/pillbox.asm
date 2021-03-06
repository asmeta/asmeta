asm pillbox

import ../StandardLibrary

// Fourth refinement level of the Pill Box. We further added the function actualConsTime to record the actual assumption time of pills 

signature:
	//*************************************************
	// DOMAINS
	//*************************************************
	abstract domain Compartment
	
	enum domain LedLights = {OFF | ON | BLINKING}

	//*************************************************
	// FUNCTIONS
	//*************************************************
	dynamic monitored openSwitch: Compartment -> Boolean
	dynamic out opened: Compartment -> Boolean //Controlled
	dynamic out redLed: Compartment -> LedLights //Controlled
	dynamic out outMess: Compartment -> String //Controlled
	dynamic out logMess: Compartment -> String //Controlled
	dynamic out time_consumption: Compartment -> Seq(Natural) //Controlled
	dynamic out name: Compartment -> String //Controlled
	dynamic out drugIndex: Compartment -> Natural //Controlled
	dynamic out actual_time_consumption: Compartment -> Seq(Natural) //Which time is the pill taken //Controlled
	dynamic out skipPill: Compartment -> Seq(Boolean) //If true skip pill otherwise no //Controlled
	dynamic out day: Integer //Controlled
	
	out nextDrugIndex: Compartment -> Natural // next drug index of the given compartment //Derived
	//Patient has missed the pill
	out isPillMissed: Compartment -> Boolean//Derived
	out pillTakenWithDelay: Compartment -> Boolean // is true if the patient takes the pill (compartment opened) //Derived
	
	// The systemTime is expressed as the number of hours passed since the 01/01/1970
	dynamic monitored systemTime: Natural //Time in minutes since midnight
	dynamic out compartmentTimer: Compartment -> Natural//Controlled
	static tenMinutes: Integer
	
	//NEW
	monitored setNewTime: Compartment -> Boolean //when pill is missed if true set new time, otherwise reset original time
	monitored newTime: Compartment -> Natural //new time when pill is missed
	monitored skipNextPill: Prod(Compartment,Compartment) -> Boolean //pill taken with delay if next pill causes minToInterfer violation is true
	
	derived next: Compartment ->  Powerset(Compartment) 
	
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
	function tenMinutes = 10

	function next($compartment in Compartment) =  
		{$c in Compartment | (at(time_consumption($c),drugIndex($c)) > at(time_consumption($compartment),drugIndex($compartment))) : $c}
	
	//*************************************************
	// RULE DEFINITIONS
	//*************************************************
	
	//These two rules are required because
	/*if redLed($compartment) = OFF then if (at(time_consumption($compartment),drugIndex($compartment))<systemTime) then r_pillToBeTaken[$compartment] endif endif
quindi ogni volta controlla se il tempo della medicina � inferiore al systemTime e se � cos� dice che � da prendere... Per� se ho medA = 100 e med B=200, a 100 prendo medA ma quando arrivo a 200 mi dice che devo prendere sia medA che medB */	
	//Pill is taken 	
	turbo rule r_PillTaken($compartment in Compartment) =
		skipPill($compartment) := replaceAt(skipPill($compartment),drugIndex($compartment),true)
	//Pill is missed 	
	turbo rule r_PillMissed($compartment in Compartment) =
		skipPill($compartment) := replaceAt(skipPill($compartment),drugIndex($compartment),true)
		
	// Rule that implement the writing on the log file
	rule r_writeToFile($compartment in Compartment) = skip
	
	// Rule to set the led red ON when the pill has to be taken
	rule r_pillToBeTaken($compartment in Compartment) =
		par
			if redLed($compartment) != ON then compartmentTimer($compartment) := systemTime endif
			redLed($compartment) := ON 
			outMess($compartment) := "Take " + name($compartment)			
		endpar	
		
	// Rule to set the red led blinking, after the compartment opening
	rule r_compartmentOpened($compartment in Compartment) =
		par
			if redLed($compartment) != BLINKING and outMess($compartment) != ("Close " + name($compartment) + " in 10 minutes") then compartmentTimer($compartment) := systemTime endif
			redLed($compartment) := BLINKING
			outMess($compartment) := "Close " + name($compartment) + " in 10 minutes"	//Pill taken
			logMess($compartment) := name($compartment) + " taken"			
			//Hypothesis: the pill is taken when compartment is opened
			actual_time_consumption($compartment) := replaceAt(actual_time_consumption($compartment),drugIndex($compartment),(systemTime mod 1440n))	
		endpar
	
	// Rule to take the system back to the initial state when the compartment is open and redLed is ON
	rule r_pillTaken_compartmentOpened($compartment in Compartment) =
		par
			redLed($compartment) := OFF
			outMess($compartment) := ""
			compartmentTimer($compartment) := systemTime
			drugIndex($compartment) := nextDrugIndex($compartment)
			r_PillTaken($compartment)
		endpar
	
				  	
	//Set skip pill to true	
	turbo rule r_skipNextPill($compartment in Compartment, $c2 in Compartment) =
		par
			if $c2!=$compartment then
				skipPill($c2) := replaceAt(skipPill($c2),drugIndex($c2),true)	
			else
				skipPill($c2) := replaceAt(skipPill($c2),nextDrugIndex($c2),true)
			endif
			drugIndex($c2) := nextDrugIndex($c2)
		endpar
			
	// Rule to handle the closing of a compartment when the RED Led is blinking
	rule r_compartmentClosed($compartment in Compartment) =
		par
			redLed($compartment) := OFF
			outMess($compartment) := ""
			compartmentTimer($compartment) := systemTime
			drugIndex($compartment) := nextDrugIndex($compartment)
			r_PillTaken($compartment)
			forall $c2 in next($compartment) with skipNextPill($compartment, $c2) do
				r_skipNextPill($compartment,  $c2)
		endpar
	
	
	
	//Update pill time
	turbo rule  r_setNewTime ($compartment in Compartment, $deltaTime in Natural)=
		time_consumption($compartment) := replaceAt(time_consumption($compartment),drugIndex($compartment), $deltaTime)
	
	turbo rule r_resetOriginalPillTime($compartment in Compartment, $time in Natural)=			  		
	  	par
			drugIndex($compartment) := nextDrugIndex($compartment)
			r_PillMissed($compartment)
			//reset to the original time
			//r_setNewTime[$compartment, $time]
			time_consumption($compartment) := replaceAt(time_consumption($compartment),drugIndex($compartment), $time)
	  	endpar
			  	
			  	
			  		
	// Rule to take the system back to the initial state when the Blinking timeout is passed
	rule r_timeOutExpired_missedPill($compartment in Compartment) =
		par
			redLed($compartment) := OFF
			outMess($compartment) := ""
			logMess($compartment) := name($compartment) + " missed"	
			compartmentTimer($compartment) := systemTime
			//In case of missed pill the index is updated in pillbox_sanitizer only if the new pill time violates the invariant (See SafePillbox)
			//drugIndex($compartment) := drugIndex($compartment) + 1n
			//r_PillMissed($compartment)
			r_writeToFile[$compartment] 
			//NEW
			if setNewTime($compartment) then
				r_setNewTime($compartment, newTime($compartment))
			else
				r_resetOriginalPillTime($compartment, newTime($compartment))
			endif
		endpar	
		
	// Rule to take the system back to the initial state when the Blinking timeout is passed and the compartment has not been closed
	rule r_timeOutExpired_compartmentOpened($compartment in Compartment) =
		par
			redLed($compartment) := OFF
			outMess($compartment) := ""
			logMess($compartment) := name($compartment) + " compartment not closed"
			compartmentTimer($compartment) := systemTime
			drugIndex($compartment) := nextDrugIndex($compartment)
			r_PillTaken($compartment)
			r_writeToFile[$compartment]
		endpar	
		
	// Rule to set the red led blinking, after the compartment opening
	rule r_takeInTimeout($compartment in Compartment) =
		par
			if redLed($compartment) != BLINKING and outMess($compartment) != ("Take " + name($compartment) + " in 10 minutes") then compartmentTimer($compartment) := systemTime endif
			redLed($compartment) := BLINKING
			outMess($compartment) := "Take " + name($compartment) + " in 10 minutes"		
		endpar
	
	//Reset all skipPill to false
	rule r_resetMidnight =
		if (rtoi(systemTime/1440n))> day then
		par
		forall $c in Compartment do
			switch($c)
				case compartment2 : skipPill($c):=[false]
				case compartment3 : skipPill($c):=[false, false]
				case compartment4 : skipPill($c):=[false]
			endswitch 
			day := day+1
		endpar
		endif
	
	
	//Compartment management rule
	main rule r_Main =
	par
		r_resetMidnight[]
		forall $compartment in Compartment do
		//new: (at(skipPill($compartment),drugIndex($compartment))=true) -> do only if pill must be taken (it is not skipped)
			if (drugIndex($compartment)<iton(length(time_consumption($compartment)))) and (at(skipPill($compartment),drugIndex($compartment))=false) then
				par					
					// Set the status of the compartment
					if not opened($compartment) and openSwitch($compartment) then opened($compartment) := true endif
					if opened($compartment) and not openSwitch($compartment) then opened($compartment) := false endif
					
					// Starting from the IDLE state, the pill has to be taken 
					if redLed($compartment) = OFF then if (at(time_consumption($compartment),drugIndex($compartment))<(systemTime mod 1440n)) then r_pillToBeTaken[$compartment] endif endif //systemTime
					// It is open, drug to be taken, it becomes closed
					if redLed($compartment) = ON and not(systemTime-compartmentTimer($compartment)>=tenMinutes) and opened($compartment) and not openSwitch($compartment) then r_pillTaken_compartmentOpened[$compartment] endif
					// It is closed drug to be taken and it becomes open     
					if redLed($compartment) = ON and not opened($compartment) and openSwitch($compartment) then r_compartmentOpened[$compartment] endif
					// It is closed drug to be taken and timeout     
					if redLed($compartment) = ON then 
						if systemTime-compartmentTimer($compartment)>=tenMinutes then 
							if opened($compartment) then r_compartmentOpened[$compartment] else if not openSwitch($compartment) then r_takeInTimeout[$compartment] endif endif 
						endif 
					endif
					// It is blinking, and it becomes closed (or remains closed) or timeout   
					if redLed($compartment) = BLINKING then 
						if not openSwitch($compartment) and opened($compartment) then r_compartmentClosed[$compartment] 
						else 
							if systemTime-compartmentTimer($compartment)>tenMinutes then
								if openSwitch($compartment) then r_timeOutExpired_compartmentOpened[$compartment] else r_timeOutExpired_missedPill[$compartment] endif
							else 
								if openSwitch($compartment) then r_compartmentOpened[$compartment] endif
							endif 
						endif 
					endif
				endpar
	/*		else
			//Added because if the next pill that generates invariant violation because of delay in taking previous pill is the same type the index needed to be updated
			//Ex: 0 - 1 - 2 ->  0 is late index is moved to 1 (r_compartmentClosed) but 1 has skipPill equals to true so I need to increment the index again
			//furthermore drugIndex must be different from nextdrugindex because means that there is only one pill for that compartment
				if (at(skipPill($compartment),drugIndex($compartment))=true) and (drugIndex($compartment) != nextDrugIndex($compartment))then
					drugIndex($compartment) := nextDrugIndex($compartment)
				endif*/
			endif
	endpar
			
				
default init s0:	//This init state is correct, it does not generate any invariant violation
	/* PillBox initialization */
	// Initialization of the SystemTime
	function systemTime = 0n
	// Each compartment's timer starts from 0
	function compartmentTimer($compartment in Compartment) = 0n
	// Controlled function that indicates the status of the compartment
	function opened($compartment in Compartment) = false		
	// Reset the output display message and the log message
	function outMess($compartment in Compartment) = ""
	function logMess($compartment in Compartment) = ""
	// Turn-off all the led of the Compartments
	function redLed($compartment in Compartment) = OFF
	// Initialization of the time consumption for a compartment
	function time_consumption($compartment in Compartment) = //time(name($compartment))
		switch($compartment)
			case compartment2 : [960n]
			case compartment3 : [780n, 1140n]
			case compartment4 : [410n]//410 working, 710 + actual time consumption violate minToInterfer
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
	
	// Initialization of the skip pill for a compartment
	function skipPill($compartment in Compartment) = //time(name($compartment))
		switch($compartment)
			case compartment2 : [false]
			case compartment3 : [false, false]
			case compartment4 : [false]
		endswitch 
	
	//Initialization of day	
	function day = 0	
	
	function nextDrugIndex($compartment in Compartment) = 	let ( $i = drugIndex($compartment) + 1n ) in 
		if  $i < iton(length(time_consumption($compartment)))  then $i else 0n endif
		endlet 
		
	function isPillMissed ($compartment in Compartment) =  (drugIndex($compartment)<iton(length(time_consumption($compartment)))) and
		  		 redLed($compartment) = BLINKING and 
		  			(not(not openSwitch($compartment) and opened($compartment))) and
		  				(systemTime-compartmentTimer($compartment)>tenMinutes) and
		  					(not openSwitch($compartment))

	// Assuming pill is taken when compartment is closed
	function pillTakenWithDelay($compartment in Compartment) =  ((drugIndex($compartment)<iton(length(time_consumption($compartment)))) and (at(skipPill($compartment),drugIndex($compartment))=false))
	and redLed($compartment) = BLINKING and (not openSwitch($compartment) and opened($compartment))

init s1:	//This init state is correct, it does not generate any invariant violation
	/* PillBox initialization */
	// Initialization of the SystemTime
	function systemTime = 0n
	// Each compartment's timer starts from 0
	function compartmentTimer($compartment in Compartment) = 0n
	// Controlled function that indicates the status of the compartment
	function opened($compartment in Compartment) = false		
	// Reset the output display message and the log message
	function outMess($compartment in Compartment) = ""
	function logMess($compartment in Compartment) = ""
	// Turn-off all the led of the Compartments
	function redLed($compartment in Compartment) = OFF
	// Initialization of the time consumption for a compartment
	function time_consumption($compartment in Compartment) = //time(name($compartment))
		switch($compartment)
			case compartment2 : [960n]
			case compartment3 : [780n, 1140n]
			case compartment4 : [710n]//710 + actual time consumption violate minToInterfer
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
	
	// Initialization of the skip pill for a compartment
	function skipPill($compartment in Compartment) = //time(name($compartment))
		switch($compartment)
			case compartment2 : [false]
			case compartment3 : [false, false]
			case compartment4 : [false]
		endswitch 
	
	//Initialization of day	
	function day = 0	
	
	function nextDrugIndex($compartment in Compartment) = 	let ( $i = drugIndex($compartment) + 1n ) in 
		if  $i < iton(length(time_consumption($compartment)))  then $i else 0n endif
		endlet 
		
	function isPillMissed ($compartment in Compartment) =  (drugIndex($compartment)<iton(length(time_consumption($compartment)))) and
		  		 redLed($compartment) = BLINKING and 
		  			(not(not openSwitch($compartment) and opened($compartment))) and
		  				(systemTime-compartmentTimer($compartment)>tenMinutes) and
		  					(not openSwitch($compartment))

	// Assuming pill is taken when compartment is closed
	function pillTakenWithDelay($compartment in Compartment) =  ((drugIndex($compartment)<iton(length(time_consumption($compartment)))) and (at(skipPill($compartment),drugIndex($compartment))=false))
	and redLed($compartment) = BLINKING and (not openSwitch($compartment) and opened($compartment))
	