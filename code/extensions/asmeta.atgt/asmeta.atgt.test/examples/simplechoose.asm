// 
asm simplechoose
import StandardLibrary
	
signature:
    enum domain Letter = {AA, BB, CC} 
	controlled myLetter: Letter
definitions:
	main rule r_Main =
		choose $i in Letter do
			myLetter := $i

default init s0:
