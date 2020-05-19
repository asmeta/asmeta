// COCO DARIO

asm Prova

import ../STDL/StandardLibrary

signature:
	enum domain State = { ACCESO | SPENTO }
	dynamic controlled switchState : State
	dynamic monitored buttonPressed: Boolean
definitions:		
	
	macro rule r_switch_on =
		if(switchState = ACCESO) then
			if(buttonPressed = true) then
					switchState := SPENTO			
			endif
		endif	
		
	macro rule r_switch_off =
		if(switchState = SPENTO) then
			if(buttonPressed = true) then
					switchState := ACCESO			
			endif
		endif

	main rule r_main = 
		par
			r_switch_on[]
			r_switch_off[]		 	
		endpar
		
	default init s0:
		function switchState = SPENTO
		
		