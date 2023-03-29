// ABZ 2023 - fMVC

asm aman0

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary
import ../../../../../asm_examples/STDL/LTLlibrary
export *

signature:
	// DOMAINS
	domain Time subsetof Integer
	domain ZoomValue subsetof Integer
	abstract domain Airplane
	enum domain Status = {UNSTABLE, STABLE, FREEZE}
	enum domain Color = {YELLOW, CYAN, WHITE}
	enum domain PTCOAction = {UP, DOWN, NONE, HOLD}
	
	// FUNCTIONS
	// Landing sequence: it should be bijective partially defined
	controlled landingSequence: Time -> Airplane
	
	// The status shown on the interface
	controlled statusOutput: Airplane -> Status  
	
	// Interaction with the GUI
	monitored zoom: ZoomValue	
	monitored selectedAirplane : Airplane
	monitored action: PTCOAction
	monitored timeToLock: Time
	
	controlled blocked: Time -> Boolean	
	controlled zoomValue : ZoomValue
	controlled landingSequenceColor: Time -> Color
	
	// Recursive function
	static search: Prod(Airplane,Time) -> Time
	// Function checking whether an airplane can be moved in the new position
	static canBeMovedUp: Airplane -> Boolean
	static canBeMovedDown: Airplane -> Boolean
	
	static a1: Airplane
	static a2: Airplane
	static a3: Airplane
	static a4: Airplane
	 
definitions:
	
	// DOMAIN DEFINITIONS
	domain Time = {0 : 10}
	domain ZoomValue = {15 : 45}
	
	// FUNCTION DEFINITIONS
	// The function searches the airplane with the specified landing time
	function search($a in Airplane, $t in Time) = 
		if landingSequence($t) = $a then $t else 
		if $t >= 10 then -1 else 
		if $t < 10 then search($a, $t+1) 
		else -1 endif endif endif
	
	function canBeMovedUp($airplane in Airplane) =
		let ($currentLT = search($airplane, 0)) in
			if ($currentLT + 1) <= 10 then if not isUndef(landingSequence($currentLT + 1)) then false
			else if ($currentLT + 2) <= 10 then if not isUndef(landingSequence($currentLT + 2)) then false
			else if ($currentLT + 3) <= 10 then if not isUndef(landingSequence($currentLT + 3)) then false
			else if ($currentLT + 4) <= 10 then if not isUndef(landingSequence($currentLT + 4)) then false 
			else true endif endif endif endif endif endif endif endif
			endlet 
		
	function canBeMovedDown($airplane in Airplane) =
		let ($currentLT = search($airplane, 0)) in
			if ($currentLT - 1) >= 0 then
			if not isUndef(landingSequence($currentLT - 1)) then false
				else  
					if ($currentLT - 2) >= 0 then 
						if not isUndef(landingSequence($currentLT - 2)) then false
						else 
							if ($currentLT - 3) >= 0 then 
								if not isUndef(landingSequence($currentLT - 3)) then false
								else 
									if ($currentLT - 4) >= 0 then 
										if not isUndef(landingSequence($currentLT - 4)) then false 
										else true endif 
									else true endif 
								endif 
							else true endif 
						endif
					else true endif endif endif
		endlet
	

	// RULE DEFINITIONS
	// the PLAN ATCo decides to move up an airplane
	rule r_moveUp($a in Airplane, $manual in Boolean) =
		let ($currentLT = search($a, 0)) in
		if $currentLT != -1 and $currentLT < 10 then
			let ($blk = blocked($currentLT + 1)) in
				if $currentLT < zoomValue and not $blk and canBeMovedUp($a) then 
				par  
					landingSequence($currentLT + 1):= $a
					landingSequence($currentLT):= undef
					landingSequenceColor($currentLT + 1) := landingSequenceColor($currentLT)
					landingSequenceColor($currentLT) := WHITE
				endpar 
				endif 
			endlet
		endif endlet 
			
	// the PLAN ATCo decides to move down an airplane
	rule r_moveDown($a in Airplane, $manual in Boolean) =
		let ($currentLT = search($a, 0)) in
		let ($blk = blocked($currentLT - 1)) in
		if $currentLT != -1 then
			// The function is called by AMAN -> It is ok to execute without checking anything
			if ($currentLT <= 0 and not $manual) then
				par
					landingSequence($currentLT):= undef
					landingSequenceColor($currentLT) := WHITE
				endpar
			else
				if ($currentLT-1) >= 0 and not $blk and not $manual then
				par
					landingSequence($currentLT - 1):= $a
					landingSequence($currentLT):= undef
					landingSequenceColor($currentLT - 1) := landingSequenceColor($currentLT)
					landingSequenceColor($currentLT) := WHITE
				endpar				
				else if $currentLT > 0 and not $blk and canBeMovedDown($a) then 
				par  
					landingSequence($currentLT - 1):= $a
					landingSequence($currentLT):= undef
					landingSequenceColor($currentLT - 1) := landingSequenceColor($currentLT)
					landingSequenceColor($currentLT) := WHITE
				endpar endif endif endif endif endlet endlet
						
	// the PLAN ATCo decides to put an airplane on hold -> The airplane has to be removed 
	// from the landing sequence and the color of the corresponding cell is set to white
	rule r_hold($a in Airplane) = 
		let ($currentLT = search($a, 0)) in
		if $currentLT != -1 then
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
		
	// Update the locks depending on user input
	rule r_update_lock =
		if not isUndef(timeToLock) then
			if isUndef(landingSequence(timeToLock)) then blocked(timeToLock) := not (blocked(timeToLock)) endif endif
			     
	// INVARIANTS AND PROPERTIES
	// REQ16: The zoom value cannot be bigger than 45 and smaller than 15
	LTLSPEC g(zoomValue >= 15 and zoomValue<=45)
	// REQ19: The value displayed next to the zoom slider must belong to the list of seven acceptable values for the zoom 
	LTLSPEC g(zoomValue = 15 or zoomValue = 20 or zoomValue = 25 or zoomValue = 30 or zoomValue = 35 or zoomValue = 40 or zoomValue = 45)  
	// REQ5: Aircraft labels should not overlap
	LTLSPEC (forall $t1 in Airplane, $t2 in Airplane with g(($t1 != $t2 and search($t1, 0) != -1 and search($t2, 0) != -1 and not isUndef(search($t1, 0)) and not isUndef(search($t2, 0))) implies ((search($t1, 0)-search($t2, 0)>=3) or (search($t1, 0)-search($t2, 0)<=-3))))
	// REQ6: An aircraft label cannot be moved into a blocked time period;
	LTLSPEC (forall $a in Airplane, $t in Time with g(search($a, 0) = $t implies not blocked($t)))

	// MAIN RULE
	main rule r_Main =
		par		
			// Update GUI
			if action = NONE then r_update_lock[] endif
			r_update_zoom[]
			
			// Move airplanes
			if selectedAirplane = a1 then
				if action = UP then r_moveUp[a1, true] else
				if action = DOWN then r_moveDown[a1, true] else
				if action = HOLD then r_hold[a1] endif endif endif 
			endif
			if selectedAirplane = a2 then
				if action = UP then r_moveUp[a2, true] else
				if action = DOWN then r_moveDown[a2, true] else
				if action = HOLD then r_hold[a2] endif endif endif 
			endif
			if selectedAirplane = a3 then
				if action = UP then r_moveUp[a3, true] else
				if action = DOWN then r_moveDown[a3, true] else
				if action = HOLD then r_hold[a3] endif endif endif 
			endif
			if selectedAirplane = a4 then
				if action = UP then r_moveUp[a4, true] else
				if action = DOWN then r_moveDown[a4, true] else
				if action = HOLD then r_hold[a4] endif endif endif 
			endif
		endpar

// INITIAL STATE
default init s0:
	function landingSequence($t in Time) = if $t = 5 then a1 else 
										   if $t = 2 then a2 else 
										   undef endif endif
	function zoomValue = 30
	function action = NONE
	function selectedAirplane = undef
	function statusOutput($t in Airplane) = if $t = a1 then UNSTABLE else if $t = a2 then FREEZE else STABLE endif endif	
	function landingSequenceColor($t in Time) = if $t = 5 then YELLOW else
												if $t = 2 then CYAN else
												WHITE
												endif endif
	function blocked($t in Time) = if $t = 6 then true else false endif
	