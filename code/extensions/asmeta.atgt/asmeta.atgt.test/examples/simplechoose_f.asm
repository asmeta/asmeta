// use the choosen var as an argument of the function
asm simplechoose_f
import StandardLibrary
	
signature:
    enum domain Letter = {AA, BB, CC} 
	controlled myLetter: Letter -> Boolean

definitions:
	main rule r_Main =
		choose $i in Letter do
			 if $i = AA then myLetter($i) := true
			 else myLetter($i) := false
			 endif

default init s0:
