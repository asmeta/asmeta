
asm EuclideInt

import ../STDL/StandardLibrary

signature:
domain Num subsetof Integer
	dynamic controlled numA: Num
	dynamic controlled numB: Num

definitions:

 domain Num={1 : 50}
 
 
	main rule r_Main =
		if(numA != numB) then
			if(numA > numB) then
				numA := numA - numB
			else
				numB := numB - numA
			endif
		endif

default init s0:
	function numA = 25 
	function numB = 5 
