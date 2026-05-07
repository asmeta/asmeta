asm inc
import StandardLibrary

signature:
	domain SmallInt subsetof Integer
	
	controlled prevFuncInc: Integer
	monitored myinput : SmallInt
	out funcInc: Integer

definitions:
	domain SmallInt = {2:3}
	
 	main rule r_Main =
 		//funcInc := myinput + 1
 		let ($newVal = myinput + prevFuncInc + 1) in
            par
                funcInc:= $newVal
                prevFuncInc:= $newVal
            endpar
        endlet

default init s0:
    function prevFuncInc = 0