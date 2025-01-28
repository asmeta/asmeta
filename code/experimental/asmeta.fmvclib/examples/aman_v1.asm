// ABZ 2023 - fMVC

asm aman_v1

import StandardLibrary
import CTLLibrary
import TimeLibrary

signature:
	// DOMAINS
	domain Time subsetof Integer
	domain ZoomValue subsetof Integer
	domain Minutes subsetof Integer
	domain Hours subsetof Integer
	abstract domain Airplane
	enum domain Status = {UNSTABLE, STABLE, FREEZE}
	enum domain Color = {YELLOW, CYAN, WHITE}
	enum domain PTCOAction = {UP, DOWN, NONE, HOLD}
	
	// FUNCTIONS
	// Landing sequence: it should be bijective partially defined
	controlled landingSequence: Time -> Airplane
	// Inverse function for landingSequence: it gives the remaining
	// time for the airplane landing
	derived landingTime: Airplane -> Time
	
	// output aman 
//	monitored delay: Airplane -> Time
	// The status communicated by the PLAN component to AMAN
	monitored statusInput: Airplane -> Status
	// The status shown on the interface
	controlled statusOutput: Airplane -> Status  
	
	// Interaction with the GUI
	monitored zoom: ZoomValue	
	monitored selectedAirplane : Airplane
	monitored action: PTCOAction
	monitored timeToLock: Time
	// The number of moves to be done (UP or DOWN)
	monitored numMoves: Time
	
	controlled blocked: Time -> Boolean	
	controlled zoomValue : ZoomValue
	controlled timeShown: Time -> Minutes
	controlled landingSequenceColor: Time -> Color
	controlled lastTimeUpdated : Minutes
	controlled mins : Minutes
	controlled hours : Hours
	
	static color: Status -> Color
	
	// Recursive function
	derived search: Prod(Airplane,Time) -> Time
	// Function checking whether an airplane can be moved in the new position
	derived canBeMovedUp: Prod(Airplane,Time) -> Boolean
	derived canBeMovedDown: Prod(Airplane,Time) -> Boolean
	
	static a1: Airplane
	static a2: Airplane
	static a3: Airplane
	static a4: Airplane
	 
definitions:
	
	// DOMAIN DEFINITIONS
	domain Time = {0 : 45}
	domain ZoomValue = {15 : 45}
	domain Hours = {0 : 23}
	domain Minutes = {0 : 59}
	
	// FUNCTION DEFINITIONS
	// The color represents the status of an airplane
	function color($s in Status) = 
		if $s = UNSTABLE then YELLOW
		else if $s = STABLE then CYAN
		else if $s = FREEZE then WHITE
		endif endif endif

	// The function searches the airplane with the specified landing time
	function search($a in Airplane, $t in Time) = 
		if landingSequence($t) = $a then $t else 
		if $t > zoomValue then undef
		else search($a, $t+1) endif endif

	function landingTime($a in Airplane) = search($a,0)
	
	function canBeMovedUp($airplane in Airplane, $nMov in Time) =
		let ($currentLT = landingTime($airplane)) in
			if ($currentLT + $nMov) <= 45 and (landingSequence($currentLT + $nMov) != undef) then false
			else if ($currentLT + $nMov + 1) <= 45 and (landingSequence($currentLT + $nMov + 1) != undef) then false
			else if ($currentLT + $nMov + 2) <= 45 and (landingSequence($currentLT + $nMov + 2) != undef) then false
			else if ($currentLT + $nMov + 3) <= 45 and (landingSequence($currentLT + $nMov + 3) != undef) then false 
			else true endif endif endif endif 
			endlet
		
	function canBeMovedDown($airplane in Airplane, $nMov in Time) =
		let ($currentLT = landingTime($airplane)) in
			if ($currentLT - $nMov) >= 0 then
			if (landingSequence($currentLT - $nMov) != undef) then false
				else  
					if ($currentLT - $nMov - 1) >= 0 then 
						if (landingSequence($currentLT - $nMov - 1) != undef) then false
						else 
							if ($currentLT - $nMov - 2) >= 0 then 
								if (landingSequence($currentLT - $nMov - 2) != undef) then false
								else 
									if ($currentLT - $nMov - 3) >= 0 then 
										if (landingSequence($currentLT - $nMov - 3) != undef) then false 
										else true endif 
									else true endif 
								endif 
							else true endif 
						endif
					else true endif endif endif
		endlet
	

	// RULE DEFINITIONS
	// the PLAN ATCo decides to move up an airplane
	rule r_moveUp($a in Airplane, $manual in Boolean, $nMov in Time) =
		let ($currentLT = landingTime($a)) in
		if ($currentLT != undef) then
			if $currentLT < zoomValue and not blocked($currentLT + $nMov) and canBeMovedUp($a, $nMov) then 
			par  
				landingSequence($currentLT + $nMov):= $a
				landingSequence($currentLT):= undef
				landingSequenceColor($currentLT + $nMov) := landingSequenceColor($currentLT)
				landingSequenceColor($currentLT) := WHITE
			endpar 
			endif 
		endif endlet
	
	// the PLAN ATCo decides to move down an airplane
	rule r_moveDown($a in Airplane, $manual in Boolean, $nMov in Time) =
		let ($currentLT = landingTime($a)) in
		if ($currentLT != undef) then
			// The function is called by AMAN -> It is ok to execute without checking anything
			if ($currentLT <= 0 and not $manual) then
				par
					landingSequence($currentLT):= undef
					landingSequenceColor($currentLT) := WHITE
				endpar
			else
				if ($currentLT-$nMov) >= 0 and not blocked($currentLT - $nMov) and not $manual then
				par
					landingSequence($currentLT - $nMov):= $a
					landingSequence($currentLT):= undef
					landingSequenceColor($currentLT - $nMov) := landingSequenceColor($currentLT)
					landingSequenceColor($currentLT) := WHITE
				endpar				
				else if $currentLT > 0 and not blocked($currentLT - $nMov)  and canBeMovedDown($a, $nMov) then 
				par  
					landingSequence($currentLT - $nMov):= $a
					landingSequence($currentLT):= undef
					landingSequenceColor($currentLT - $nMov) := landingSequenceColor($currentLT)
					landingSequenceColor($currentLT) := WHITE
				endpar endif endif endif endif endlet
		
	// the PLAN ATCo decides to put an airplane on hold -> The airplane has to be removed 
	// from the landing sequence and the color of the corresponding cell is set to white
	rule r_hold($a in Airplane) = 
		let ($currentLT = landingTime($a)) in
		if ($currentLT != undef) then
			par
				landingSequence($currentLT) := undef
				landingSequenceColor($currentLT) := WHITE
			endpar
			endif endlet
		
	// Update the zoom value shown on the GUI
	rule r_update_zoom = 
		if zoom < 15 then zoomValue := 15
		else if zoom > 45 then zoomValue := 45
		else if mod(zoom, 5) = 0 then 
		zoomValue := zoom endif endif endif
	
	// Update the current time
	rule r_update_time = 
		par
			mins := mCurrTimeMins mod 60
			hours := mCurrTimeHours mod 24
		endpar
		
	// Update the times shown in the timeline
	rule r_update_time_shown =
		par
			forall $t in Time do
				timeShown($t) := mod(mCurrTimeMins + $t + 1, 60)
			// If times have been shifted, shift all the airplanes too
			if lastTimeUpdated != mCurrTimeMins then
				par
					lastTimeUpdated := mCurrTimeMins mod 60
					forall $a in Airplane do r_moveDown[$a, false, 1]
					forall $time in Time with $time > 0 do blocked($time - 1) := blocked($time) 
				endpar
			endif
		endpar
		
	// Update the locks depending on user input
	rule r_update_lock =
		if timeToLock != undef then
			if landingSequence(timeToLock) = undef then blocked(timeToLock) := not (blocked(timeToLock)) endif endif
		
			     
	// INVARIANTS AND PROPERTIES
	// REQ16: The zoom value cannot be bigger than 45 and smaller than 15
	CTLSPEC ag(zoomValue >= 15 and zoomValue<=45)
	// REQ19: The value displayed next to the zoom slider must belong to the list of seven acceptable values for the zoom
	CTLSPEC ag(zoomValue = 15 or zoomValue = 20 or zoomValue = 25 or zoomValue = 30 or zoomValue = 35 or zoomValue = 40 or zoomValue = 45)  
	// REQ5: Aircraft labels should not overlap
	CTLSPEC (forall $t1 in Airplane, $t2 in Airplane with ag($t1 != $t2 implies abs(landingTime($t1)-landingTime($t2))>=3))
	// REQ6: An aircraft label cannot be moved into a blocked time period;
	CTLSPEC (forall $t1 in Airplane with ag(not blocked(landingTime($t1))))

	// MAIN RULE
	main rule r_Main =
		par		
			// Update GUI
			r_update_lock[]
			r_update_zoom[]
			r_update_time[]
			r_update_time_shown[]
			
			// Move airplanes
			if toString(selectedAirplane) != "undef" then
				if action = UP then r_moveUp[selectedAirplane, true, numMoves] else
				if action = DOWN then r_moveDown[selectedAirplane, true, numMoves] else
				if action = HOLD then r_hold[selectedAirplane] endif endif endif 
			endif
		endpar

// INITIAL STATE
default init s0:
	function landingSequence($t in Time) = if $t = 5 then a1 else 
										   if $t = 2 then a2 else
										   if $t = 18 then a3 else
										   if $t = 35 then a4 else 
										   undef endif endif endif endif
	function zoomValue = 30
	function action = NONE
	function selectedAirplane = undef
	function timeShown($t in Time) = ($t + 1)
	function lastTimeUpdated = mCurrTimeMins
	//function statusOutput($t in Airplane) = if $t = a1 then UNSTABLE else if $t = a2 then FREEZE else STABLE endif endif	
	function landingSequenceColor($t in Time) = if $t = 5 then YELLOW else
												if $t = 2 then CYAN else
												WHITE
												endif endif
		// The following definition should be used
		//color(statusOutput(landingSequence($t)))
	function blocked($t in Time) = if $t = 6 then true else false endif
	function mins = 0
	function hours = 0
	
