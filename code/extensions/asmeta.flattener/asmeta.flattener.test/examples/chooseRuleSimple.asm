// 
asm chooseRuleSimple

import ../../../../../asm_examples/STDL/StandardLibrary
	
signature:
    enum domain Letter = {AA, BB, CC} 
	controlled myLetter: Letter
definitions:

	rule r_withguard =
		choose $i in Letter with $i = AA do
			myLetter := $i
	

	// simplest
	main rule r_Main =
		choose $i in Letter do
			myLetter := $i

default init s0:
