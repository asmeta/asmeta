// Used to test the pick statement with step until and monitored functions
asm incDec

import StandardLibrary

signature:
	domain CounterDomain subsetof Integer 
	controlled counter: CounterDomain
	controlled nInc: Integer
	monitored input: Integer

definitions:
	domain CounterDomain = {-100 : 100}

	main rule r_Main =
		choose $b in Boolean with true do
			if (input > 5) then
				if ($b) then par counter := counter + 1 nInc := nInc + 1 endpar
				else counter := counter - 1 endif
			else
				skip
			endif
	

default init s0:
	function nInc = 0
	function counter = 0
