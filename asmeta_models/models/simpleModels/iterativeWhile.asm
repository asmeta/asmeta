asm iterativeWhile

import ../../STDL/StandardLibrary

signature:
	controlled contrC: Integer
	monitored mon: Integer
	monitored doWhile: Boolean

definitions:
	main rule r_Main =
		if(doWhile) then 
			while(contrC < mon) do
				contrC := contrC + 1
		else
			contrC := 0
		endif

default init s0:
	function contrC = 3