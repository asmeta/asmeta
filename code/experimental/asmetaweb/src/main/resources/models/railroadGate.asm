asm railroadGate
//versione con tre monitorate: "event" indica se l'evento e' associato al gate o a light
//"lightMon" e "gateMon" catturano gli eventi di light e di gate.
//Grazie alla lazy evaluation, in ogni stato viene richiesto o il valore di "lightMon"
//o quello di "gateMon" 

//traces
//open, off, flashing, closing, closed, opening, open, off. (ok)
//open, off, flashing, closing, closed, off, opening, open. (no: opening ma non flashing)
//open, off, closing, flashing, closed, opening, open, off. (no: closing ma non flashing)

import ../libraries/StandardLibrary

signature:
	enum domain LightState = {FLASHING | OFF}
	enum domain GateState = {CLOSED | OPENED | CLOSING | OPENING}
	enum domain EventDomain = {LIGHT | GATE}
	dynamic controlled light: LightState
	dynamic controlled gate: GateState
	dynamic controlled gateStatusUpdateOk: Boolean
	dynamic monitored lightMon: LightState
	dynamic monitored gateMon: GateState
	dynamic monitored event: EventDomain

definitions:

	//Always in the future, when the gate is closing, is closed or is opening,
	//the light flashes.
	invariant over gate: (gate = CLOSING or gate = CLOSED or gate = OPENING) implies light = FLASHING
	//invariant over gate: gate != OPENED iff light = FLASHING
	
	//In the future, the light is off only when the gate is open.
	invariant over gate: light = OFF implies gate = OPENED 
	
	//The gate cannot be "closed" after "opening"
	invariant over gate: gateStatusUpdateOk
	
	main rule r_Main =
		//il valore della monitorata event e' sempre richiesto
		if(event = LIGHT) then
			light := lightMon //lazy evaluation di lightMon
			
		else
			par
				gate := gateMon //lazy evaluation di gateMon
				if( (gate=OPENED and (gateMon=CLOSED or gateMon=OPENING)) or
					(gate=OPENING and (gateMon=CLOSED or gateMon=CLOSING)) or
					(gate=CLOSED and (gateMon=OPENED or gateMon=CLOSING)) or
					(gate=CLOSING and (gateMon=OPENED or gateMon=OPENING))
					) then
					gateStatusUpdateOk := false
				else
					gateStatusUpdateOk := true
				endif
			endpar
		endif

default init s0:
	function light = OFF
	function gate = OPENED
	function gateStatusUpdateOk = true