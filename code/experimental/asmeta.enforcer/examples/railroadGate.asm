asm railroadGate

import StandardLibrary

signature:
	enum domain LightState = {FLASH | OFF}
	enum domain GateState = {CLOSED | OPENED | CLOSING | OPENING}
	dynamic controlled light: LightState
	dynamic controlled gate: GateState

definitions:

	main rule r_Main =
		//if the light is OFF the gate must be OPENED 
		if(light = OFF) then
			choose $l in LightState with true do
				light := $l
		else
			par
				//se e' closed non posso modificare la luce
				if(gate = CLOSED) then
					choose $g in GateState with $g = CLOSED or $g = OPENING do
						gate := $g
				endif
				//se e' closing non posso modificare la luce
				if(gate = CLOSING) then
					choose $g1 in GateState with $g1 = CLOSING or $g1 = CLOSED do
						gate := $g1
				endif
				//se e' OPENING non posso modificare la luce
				if(gate = OPENING) then
					choose $g2 in GateState with $g2 = OPENING or $g2 = OPENED do
						gate := $g2
				endif
				if(gate = OPENED) then
					choose $i in {1:3} with true do
					if($i = 1) then
						light := OFF
					else
						if($i = 2) then
							gate := CLOSING
						else
							gate := OPENED
						endif
					endif
				endif
			endpar
		endif

default init s0:
	function gate = OPENED
	function light = OFF