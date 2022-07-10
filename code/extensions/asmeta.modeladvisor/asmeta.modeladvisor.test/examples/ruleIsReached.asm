asm ruleIsReached

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC | DD}
	dynamic controlled fooA: EnumDom -> Boolean
	dynamic monitored mon: Boolean

definitions:
	
	main  rule r_Main =
		par
			fooA(AA) := true //always reached
			if(not(fooA(AA))) then //always reached 
				fooA(BB) := false //never reached
			endif
			forall $x in EnumDom with $x!=BB do //always reached
				fooA(CC) := false //always reached
			if(mon) then //always reached
				fooA(DD) := not(fooA(DD)) //sometimes (but not always) reached
			endif
		endpar

default init s0:
	function fooA($x in EnumDom) = true
