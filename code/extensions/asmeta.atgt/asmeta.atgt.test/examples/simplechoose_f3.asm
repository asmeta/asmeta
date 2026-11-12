// with a guard that is traslated to INVAR with a function
asm simplechoose_f3
import StandardLibrary
	
signature:
    enum domain Letter = {AA, BB, CC} 
	controlled myLetter: Letter -> Boolean

definitions:

	// check if this is traslated correctly
    invariant  inv_b0 over myLetter: myLetter(AA)

	main rule r_Main =
	    par
	    myLetter(AA) := true
		choose $i in Letter with $i = AA or myLetter($i) do
			if $i != AA then myLetter($i) := false endif
		endpar


default init s0:
