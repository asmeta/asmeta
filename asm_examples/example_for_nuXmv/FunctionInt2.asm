
asm FunctionInt2

import ../STDL/StandardLibrary

signature:
    domain Num subsetof Integer
	monitored numA: Num
	dynamic controlled numB: Num
	
	derived functionprova: Num -> Boolean
	

definitions:

 domain Num={-10 : 10}
 
 function functionprova($a in Num) =
 	if ($a > 0) then
 		true
 	else
 		false
 	endif
 
	main rule r_Main =
		if (functionprova(numA)) then
			numB:=numA
		else
			numB:=-numA
		endif

				

default init s0: 
	function numB = 1 
