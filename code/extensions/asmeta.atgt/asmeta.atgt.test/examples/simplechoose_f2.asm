// 
asm simplechoose_f2
import StandardLibrary
	
signature:
    enum domain Letter = {AA, BB, CC} 
	controlled myLetter: Letter -> Boolean

definitions:
	main rule r_Main =
	par
		choose $i in Letter do
			if $i = AA then myLetter($i) := true
			else myLetter($i) := false
			endif
		if myLetter(AA) then myLetter($i) := false
		endif
	endpar


default init s0:
