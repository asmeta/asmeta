
asm FunctionInt

import ../STDL/StandardLibrary

signature:
    domain Num subsetof Integer
	dynamic controlled numA: Num
	dynamic controlled numB: Num
	
	derived functionprova: Num -> Boolean
	

definitions:

 domain Num={1 : 50}
 
 function functionprova($a in Num) =
 	if ($a < 25) then
 		true
 	else
 		false
 	endif
 
	main rule r_Main =
		choose $x in Num with true do
		par
			numA:=$x
			if (functionprova(numA)) then
				numB:=1
			else
				numB:=50
			endif
		endpar
				

default init s0:
	function numA = 25 
	function numB = 1 
