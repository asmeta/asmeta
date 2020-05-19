//prove di costruzione di un while
asm while

import ../../STDL/StandardLibrary

signature:
	monitored fooA: Boolean
	monitored fooB: Boolean
	controlled whileContrGgt0Exec: Boolean
	controlled contrC: Integer
	controlled contrD: Integer

definitions:
	rule r_ruleWhileBody =
		par
			contrC := contrC - 1
			contrD := contrD + 2
		endpar
	
	rule r_ruleA =
		if(fooA) then
			
			//"contrC > 0" e' la condizione del while
			if(contrC > 0 and not(whileContrGgt0Exec)) then
				par
					r_ruleWhileBody[]
					whileContrGgt0Exec := true
				endpar
			endif
		endif

	rule r_ruleB =
		if(fooB) then
			r_ruleA[]
		endif

	//questa dovrebbe essere la vera main
	rule r_Main2 =
		if(fooA) then
			r_ruleB[]
		endif

	//la main rule viene modificata per gestire il while
	main rule r_Main = 
		par
			r_Main2[]
			
			//questo dovrebbe eseguire il while 
			if(contrC > 0) then
				r_ruleWhileBody[]
			else
				whileContrGgt0Exec := false
			endif
		endpar

default init s0:
	function whileContrGgt0Exec = false
	function contrC = 3
	function contrD = 0