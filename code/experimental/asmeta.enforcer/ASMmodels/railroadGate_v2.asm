asm railroadGate_v2

import StandardLibrary

signature:
	enum domain LightState = {FLASH | OFF}
	enum domain GateState = {CLOSED | OPENED | CLOSING | OPENING}
	dynamic controlled light: LightState
	dynamic controlled gate: GateState

definitions:

	rule r_lightOff =
		//We can decide to turn on the light 
		choose $l in LightState with true do
			light := $l

	rule r_gateClosed =
		if(gate = CLOSED) then
			choose $g in GateState with $g = CLOSED or $g = OPENING do
				gate := $g
		endif

	rule r_gateClosing =
		//se e' closing non posso modificare la luce
		if(gate = CLOSING) then
			choose $g1 in GateState with $g1 = CLOSING or $g1 = CLOSED do
				gate := $g1
		endif

	rule r_gateOpening =
		//se e' OPENING non posso modificare la luce
		if(gate = OPENING) then
			choose $g2 in GateState with $g2 = OPENING or $g2 = OPENED do
				gate := $g2
		endif

	rule r_gateOpened =
		if(gate = OPENED) then
			choose $i in {1:3} with true do
				switch $i
					case 1: light := OFF
					case 2: gate := CLOSING
					case 3: gate := OPENED
				endswitch
				/*if($i = 1) then
					light := OFF
				else
					if($i = 2) then
						gate := CLOSING
					else
						gate := OPENED
					endif
				endif*/
		endif

	main rule r_Main =
		if(light = OFF) then
			r_lightOff[]
		else
			par
				r_gateClosed[]
				r_gateClosing[]
				r_gateOpening[]
				r_gateOpened[]
			endpar
		endif

default init s0:
	function gate = OPENED
	function light = OFF