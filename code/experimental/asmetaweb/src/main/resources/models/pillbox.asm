asm pillbox

import ../libraries/StandardLibrary

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
	//OUT to Safe Pillbox
	out redLed: Compartment -> LedLights
	out time_consumption: Compartment -> Seq(Natural)
	out name: Compartment -> String
	out drugIndex: Compartment -> Natural 
	out actual_time_consumption: Compartment -> Seq(Natural) //Which time is the pill taken 
	out day: Integer //Controlled
	//Patient has missed the pill
	out isPillMissed: Compartment -> Boolean
	out pillTakenWithDelay: Compartment -> Boolean // is true if the patient takes the pill (compartment opened) 
	
	//IN from Safe Pillbox
	monitored setNewTime: Compartment -> Boolean //if true set new time when pill is missed 
	monitored setOriginalTime: Compartment -> Boolean //when pill is missed if true reset original time, otherwise set  new time
	monitored newTime: Compartment -> Natural //new time when pill is missed
	monitored skipNextPill: Prod(Compartment,Compartment) -> Boolean //pill taken with delay if next pill causes minToInterfer violation is true
	monitored skipNextPill: Compartment -> Boolean //SafePillbox detected that pill taken with delay
	
	//System functions
	controlled opened: Compartment -> Boolean 
	controlled skipPill: Compartment -> Seq(Boolean) //If true skip pill otherwise no 
	controlled compartmentTimer: Compartment -> Natural
	
	//IN from patient
	monitored openSwitch: Compartment -> Boolean
	
	//OUT to patient 
	out outMess: Compartment -> String
	out logMess: Compartment -> String
	
	//Time management
	// The systemTime is expressed as the number of hours passed since the 01/01/1970
	monitored systemTime: Natural //Time in minutes since midnight
	
	derived next: Compartment ->  Powerset(Compartment) //Set of next compartmentes
	derived nextDrugIndex: Compartment -> Natural // next drug index of the given compartment 
	
	
	//*************************************************
	// STATIC VARIABLES
	//*************************************************
	static compartment1: Compartment
	static compartment2: Compartment	
	
	static tenMinutes: Integer
	
	
definitions:
	//*************************************************
	// FUNCTIONS DEFINITIONS
	//*************************************************
	function tenMinutes = 10

	function next($compartment in Compartment) =  
		{$c in Compartment | (at(time_consumption($c),drugIndex($c)) > at(time_consumption($compartment),drugIndex($compartment))) : $c}
	
	function nextDrugIndex($compartment in Compartment) = 	let ( $i = drugIndex($compartment) + 1n ) in 
		if  $i < iton(length(time_consumption($compartment)))  then $i else 0n endif
		endlet 
		
	//*************************************************
	// RULE DEFINITIONS
	//*************************************************
	
	//These two rules are required because
	/*if redLed($compartment) = OFF then if (at(time_consumption($compartment),drugIndex($compartment))<systemTime) then r_pillToBeTaken[$compartment] endif endif
quindi ogni volta controlla se il tempo della medicina � inferiore al systemTime e se � cos� dice che � da prendere... Per� se ho medA = 100 e med B=200, a 100 prendo medA ma quando arrivo a 200 mi dice che devo prendere sia medA che medB */	
		
	// Rule that implement the writing on the log file
	rule r_writeToFile($compartment in Compartment) = skip
	
	// Rule to set the led red ON when the pill has to be taken
	rule r_pillToBeTaken($compartment in Compartment) =
		par
			if redLed($compartment) != ON then compartmentTimer($compartment) := systemTime endif
			redLed($compartment) := ON 
			outMess($compartment) := "Take " + name($compartment)		
			logMess($compartment) :=""	
		endpar	
		
	// Rule to set the red led blinking, after the compartment opening
	rule r_compartmentOpened($compartment in Compartment) =
		par
			if redLed($compartment) != BLINKING and outMess($compartment) != ("Close " + name($compartment) + " in 10 minutes") then compartmentTimer($compartment) := systemTime endif
			redLed($compartment) := BLINKING
			outMess($compartment) := "Close " + name($compartment) + " in 10 minutes"	//Pill taken
			logMess($compartment) := ""
			//Hypothesis: the pill is taken when compartment is opened
			actual_time_consumption($compartment) := replaceAt(actual_time_consumption($compartment),drugIndex($compartment),(systemTime mod 1440n))	
		endpar
	
	// Rule to take the system back to the initial state when the compartment is open and redLed is ON
	rule r_pillTaken_compartmentOpened($compartment in Compartment) =
		par
			redLed($compartment) := OFF
			outMess($compartment) := ""
			logMess($compartment) := name($compartment) + " not closed"
			compartmentTimer($compartment) := systemTime
			drugIndex($compartment) := nextDrugIndex($compartment)
			skipPill($compartment) := replaceAt(skipPill($compartment),drugIndex($compartment),true)
		endpar
	
				  	
	// Rule to handle the closing of a compartment when the RED Led is blinking
	rule r_compartmentClosed($compartment in Compartment) =
		par
			redLed($compartment) := OFF
			outMess($compartment) := name($compartment) + " taken"		
			logMess($compartment) := name($compartment) + " taken"	
			compartmentTimer($compartment) := systemTime
			drugIndex($compartment) := nextDrugIndex($compartment)
			pillTakenWithDelay($compartment) := true
			skipPill($compartment) := replaceAt(skipPill($compartment),drugIndex($compartment),true)
		endpar
	
 		
	// Rule to take the system back to the initial state when the Blinking timeout is passed
	rule r_timeOutExpired_missedPill($compartment in Compartment) =
		par
			redLed($compartment) := OFF
			outMess($compartment) := name($compartment) + " missed"
			logMess($compartment) := name($compartment) + " missed"	
			compartmentTimer($compartment) := systemTime
			isPillMissed($compartment) := true
			//In case of missed pill the index is updated in pillbox_sanitizer only if the new pill time violates the invariant (See SafePillbox)
			//drugIndex($compartment) := drugIndex($compartment) + 1n
			//r_PillMissed[$compartment]
			r_writeToFile[$compartment] 
			
		endpar	
		
	// Rule to take the system back to the initial state when the Blinking timeout is passed and the compartment has not been closed
	rule r_timeOutExpired_compartmentOpened($compartment in Compartment) =
		par
			redLed($compartment) := OFF
			outMess($compartment) := ""
			logMess($compartment) := name($compartment) + " compartment not closed"
			compartmentTimer($compartment) := systemTime
			drugIndex($compartment) := nextDrugIndex($compartment)
			skipPill($compartment) := replaceAt(skipPill($compartment),drugIndex($compartment),true)
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
				case compartment1 : skipPill($c):=[false]
				case compartment2 : skipPill($c):=[false, false]
			endswitch 
			day := day+1
		endpar
		endif
	
		//Set skip pill to true	
	rule r_skipNextPill($compartment in Compartment, $c2 in Compartment) =
		par
			if $c2!=$compartment then
				skipPill($c2) := replaceAt(skipPill($c2),drugIndex($c2),true)	
			else
				skipPill($c2) := replaceAt(skipPill($c2),nextDrugIndex($c2),true)
			endif
			outMess($compartment) := name($compartment) + " skipped"		
			logMess($compartment) := name($compartment) + " skipped"
			drugIndex($c2) := nextDrugIndex($c2)
		endpar
			
			
	rule r_checkTimeUpdates($compartment in Compartment)=
	//NEW
		par
			if setNewTime($compartment) then
				par	
					if setOriginalTime($compartment) then
						par
							drugIndex($compartment) := nextDrugIndex($compartment)
							skipPill($compartment) := replaceAt(skipPill($compartment),drugIndex($compartment),true)
							time_consumption($compartment) := replaceAt(time_consumption($compartment),drugIndex($compartment), newTime($compartment))
							outMess($compartment) := name($compartment) + " not rescheduled"		
							logMess($compartment) := name($compartment) + " not rescheduled"		
						endpar
					else
						par
							time_consumption($compartment) := replaceAt(time_consumption($compartment),drugIndex($compartment), newTime($compartment))
							outMess($compartment) := name($compartment) + " rescheduled"		
							logMess($compartment) := name($compartment) + " rescheduled"
						endpar
					endif
					isPillMissed($compartment):=false
				endpar
			endif
			if skipNextPill($compartment) then
				par
					pillTakenWithDelay($compartment) := false
					forall $c2 in next($compartment) with skipNextPill($compartment, $c2) do
						r_skipNextPill[$compartment,  $c2]
				endpar
			endif
		endpar
	
	
	//Compartment management rule
	main rule r_Main =
	forall $compartment in Compartment do
		if setNewTime($compartment) or skipNextPill($compartment) then
			r_checkTimeUpdates[$compartment]
		else
		par
			r_resetMidnight[]
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
		endif
			
				
 init s0:	//This init state is correct, it does not generate any invariant violation
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
			case compartment1 : [410n]
			case compartment2 : [780n, 1140n]
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
	
	// Initialization of the skip pill for a compartment
	function skipPill($compartment in Compartment) = //time(name($compartment))
		switch($compartment)
			case compartment1 : [false]
			case compartment2 : [false, false]
		endswitch 
	
	//Initialization of day	
	function day = 0	
		
	/*function isPillMissed ($compartment in Compartment) =  (drugIndex($compartment)<iton(length(time_consumption($compartment)))) and
		  		 redLed($compartment) = BLINKING and 
		  			(not(not openSwitch($compartment) and opened($compartment))) and
		  				(systemTime-compartmentTimer($compartment)>tenMinutes) and
		  					(not openSwitch($compartment))

	// Assuming pill is taken when compartment is closed
	function pillTakenWithDelay($compartment in Compartment) =  ((drugIndex($compartment)<iton(length(time_consumption($compartment)))) and (at(skipPill($compartment),drugIndex($compartment))=false))
	and redLed($compartment) = BLINKING and (not openSwitch($compartment) and opened($compartment))*/

default init s1:	//This init state is correct, it does not generate any invariant violation
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
			case compartment1 : [350n]
			case compartment2 : [780n, 1140n]
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
	
	// Initialization of the skip pill for a compartment
	function skipPill($compartment in Compartment) = //time(name($compartment))
		switch($compartment)
			case compartment1 : [false]
			case compartment2 : [false, false]
		endswitch 
	
	//Initialization of day	
	function day = 0	
			
	/*function isPillMissed ($compartment in Compartment) =  (drugIndex($compartment)<iton(length(time_consumption($compartment)))) and
		  		 redLed($compartment) = BLINKING and 
		  			(not(not openSwitch($compartment) and opened($compartment))) and
		  				(systemTime-compartmentTimer($compartment)>tenMinutes) and
		  					(not openSwitch($compartment))

	// Assuming pill is taken when compartment is closed
	function pillTakenWithDelay($compartment in Compartment) =  ((drugIndex($compartment)<iton(length(time_consumption($compartment)))) and (at(skipPill($compartment),drugIndex($compartment))=false))
	and redLed($compartment) = BLINKING and (not openSwitch($compartment) and opened($compartment))*/

init s2:	//This init state is correct, it does not generate any invariant violation

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
			case compartment1 : [410n]
			case compartment2 : [780n, 1140n]
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
	
	// Initialization of the skip pill for a compartment
	function skipPill($compartment in Compartment) = //time(name($compartment))
		switch($compartment)
			case compartment1 : [false]
			case compartment2 : [false, false]
		endswitch 
	
	//Initialization of day	
	function day = 0	
		
/*	function isPillMissed ($compartment in Compartment) =  (drugIndex($compartment)<iton(length(time_consumption($compartment)))) and
		  		 redLed($compartment) = BLINKING and 
		  			(not(not openSwitch($compartment) and opened($compartment))) and
		  				(systemTime-compartmentTimer($compartment)>tenMinutes) and
		  					(not openSwitch($compartment))

	// Assuming pill is taken when compartment is closed
	function pillTakenWithDelay($compartment in Compartment) =  ((drugIndex($compartment)<iton(length(time_consumption($compartment)))) and (at(skipPill($compartment),drugIndex($compartment))=false))
	and redLed($compartment) = BLINKING and (not openSwitch($compartment) and opened($compartment))*/
	