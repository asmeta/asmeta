/*
 Until the controlled function contrC is lower than the monitored function,
 increase the controlled function. Initially the controlled function is equal
 to 3.
 */
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